/**
 * 逾期还款金额批量收取 窗口
 * @author 程明卫
 * @date 2013-03-06 13:47:32
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		apptbar : null,
		mainPanel : null,
		appGrid : null,/*批量数据显示Grid*/
		appFrm : null,
		appWin : null,
		gridTitle : '待收款客户列表',
		bussKey : null,/*钥匙KEY*/
		ids : null,/*待收款的收款单ID列表*/
		datas : null,/*批量合同的逾期还款计划数据*/
		orderList : null,/*扣收顺序列表*/
		sysId : null,
		contractId : null,
		setParams:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.contractId = parentCfg.parent.contractId;
			this.bussKey = parentCfg.bussKey;
			if(!this.bussKey){
				ExtUtil.error({msg:'bussKey 不能为空!'});
			}
			this.params = this.parentCfg.params;
		},
		createAppWindow : function(){
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({width:800,height:450,tbar:this.apptbar,items : [this.mainPanel],
			listeners : {
				'hide':function(win){
					LockManager.releaseLock();
				}
			}});
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParams(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
			this.appGrid.setTitle(this.gridTitle);
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function(){
			var _this = this;
			this.appFrm.reset();
			this.datas = null;
			this.orderList = null;
			var ids = this.parent.getSelIds();
			var params = {
				key : this.bussKey,
				ids : ids
			};
			this.appGrid.reload(params,function(records){
				_this.gatherTotalAmount(records);
				var dat_rectDate = _this.appFrm.findFieldByName('rectDate');
				var rectDate = dat_rectDate.getValue();
				if(!rectDate){
					rectDate = new Date();
				}
				dat_rectDate.setValue(rectDate);
				_this.caclutePdAmountsByRectDate(dat_rectDate,rectDate);
			});
		},
		/**
		 * 汇总金额
		 */
		gatherTotalAmount : function(records){
			if(!records){
				var store = this.appGrid.getStore();
				var count = store.getCount();
				if(count>0){
					records = store.getRange(0,count-1);
				}
			}
			var totalAmount = 0;
			var count = records.length;
			var idArr = [];
			for(var i=0; i<count; i++){
				var recrod = records[i];
				var id = recrod.id;
				idArr[idArr.length] = id;
				var rtotalAmount = recrod.get("rtotalAmount");
				if(!rtotalAmount) continue;
				totalAmount += parseFloat(rtotalAmount);
			}
			
			this.ids = idArr.join(",");
			this.appFrm.setFieldValue('contractIds',this.ids);
			this.appFrm.setFieldValue('tat',totalAmount);
			var title = this.gridTitle + 
			"&nbsp;&nbsp;<span style='color:red;font-weight:normal;'>【待收款记录:<span style='font-weight:bold;'>"+count+"</span>条，" +
			" 累计收款金额：<span style='font-weight:bold;'>"+Cmw.getThousandths(totalAmount)+"元(大写："+Cmw.cmycurd(totalAmount)+")</span>】</span>";
			this.appGrid.setTitle(title);
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		createToolBar : function(){
			var _this = this;
			var barItems = [{type:'chk',name:'isVoucher',checked:true,boxLabel:Finsys_Btn_Cfgs.AUTOVOUCHER_BOXLABEL},{
				text : Btn_Cfgs.DO_NOMAL_BTN_TXT, /*-- 收款 --*/
				iconCls : Btn_Cfgs.DO_NOMAL_CLS,
				tooltip : Btn_Cfgs.DO_NOMAL_TIP_BTN_TXT,
				handler : function(){
					_this.saveData();	
				}
			},{
				text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
				iconCls:Btn_Cfgs.CLOSE_CLS,
				tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
				handler : function(){
					_this.close();
			}
			}];
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
			return appBar;
		},
		/**
		 * 创建Form表单
		 */
		createMainPanel : function(){
			var _this = this;
			var batchGrid = this.createBatchGrid();
			this.appGrid = batchGrid;
			var appForm = this.createAppForm();
			this.appFrm = appForm;
			var mainPanel = new Ext.Panel({border:false,autoScroll:true,items:[appForm,batchGrid]});
			return mainPanel;
		},
		/**
		 * 创建批处理GRID
		 */
		createBatchGrid : function(){
			var _this = this;
			var structure_1 = [{header: '借款合同号', name: 'code'},
			{header: '客户类型',name: 'custType',width: 60,renderer : function(val){return Render_dataSource.custTypeRender(val);}},
			{header: '客户名称', name: 'name',width:120},
			{header: '累计逾期期数',	name: 'totalPharses',width: 70},
			{header: '风险等级',name: 'riskLevel', width: 90},
			{ header: '应收合计',name: 'totalAmounts', width: 80,renderer : function(val){return Cmw.getThousandths(val);}},
			{header: '本次实收合计', name: 'rtotalAmount',width: 80,renderer : function(val,metaData){
				metaData.style='color:blue;font-weight:bold;';
				return Cmw.getThousandths(val);
			}}];
			var num_rtotalAmount =  FormUtil.getMoneyField({name : 'rtotalAmount',allowBlank : false});
			//num_rtotalAmount.addListener('change',function(field,newVal,oldVal){_this.calculateByOrder(field,newVal,oldVal);});
			var appgrid = new Ext.ux.grid.MyEditGrid({
				title : '待收款客户列表',
			    structure: structure_1,
			    url: './fcOverdueDeduct_obtain.action',
			    needPage: false,
			    isLoad: false,
			    autoWidth : true,
			    height:328,
			    editEles : {6:num_rtotalAmount},
			    keyField: 'contractId'
			});
			
			appgrid.on('afteredit', afterEdit, this );
			function afterEdit(e) {
			    e.record.commit();
			    var store = appgrid.getStore();
			    var records = store.getRange(0,store.getCount());
			    this.gatherTotalAmount(records);
			    var record = e.record;
			    this.calculateByOrder(record);
			};
			return appgrid;
		},
		/**
		 * 创建表单
		 */
		createAppForm : function(){
			var _this = this;
			var hid_contractIds = FormUtil.getHidField({name:'contractIds'});
			var hid_tat = FormUtil.getHidField({fieldLabel : '实收合计',	name : 'tat'});
			var callback = function(cboGrid,selVals){
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
				var account = record.get("account");
				txt_bankAccount.setValue(account);
			}
			var cbog_accountId = ComboxControl.getAccountCboGrid({
				fieldLabel:'收款银行',name:'accountId',
				allowBlank : false,readOnly:true,
				isAll:false,width:130,callback:callback,
				params : {isIncome:1,sysId : _this.sysId}});
			
			var txt_bankAccount = FormUtil.getReadTxtField({
			    fieldLabel: '银行帐号',
			    name: 'bankAccount',
			    "width": 160
			});
			
			var dat_rectDate = FormUtil.getDateField({
				fieldLabel : '收款日期',
				name : 'rectDate',
				"width" : 125,
				"allowBlank" : false,
				editable : false,
				"maxLength" : 50
			});
			dat_rectDate.addListener('select',function(field,newVal){_this.caclutePdAmountsByRectDate(field,newVal);});
					
			var layout_fields = [hid_contractIds,hid_tat,{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbog_accountId, txt_bankAccount, dat_rectDate]
			}];
			var frm_cfg = {
				title:'收款信息录入', 
			    url: './fcOverdueDeduct_receivables.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
		},
		/**
		 * 根据收款日期计算罚息和滞纳金
		 */
		caclutePdAmountsByRectDate : function(field,newVal){
			if(!newVal){
				ExtUtil.alert({msg : '请选择收款日期!'});
				return;
			}
			var count = this.appGrid.getStore().getCount();
			if(count == 0) return;
			
			var _this = this;
			var contractIds = this.appFrm.getValueByName('contractIds');
			var newVal = newVal.format('Y-m-d');
			var params = {contractIds:contractIds,lastDate : newVal};
			this.mask('正在计算逾期应收合计金额...');
			EventManager.get('./fcOverdueDeduct_getPdamounts.action',{params:params,sfn:function(json_data){
				if(!json_data){
					ExtUtil.alert({msg : '无法获取计算后的罚息和滞纳金数据!'});
					_this.unmask();
					return;
				}
				_this.datas = {};
				var store = _this.appGrid.getStore();
				var count = store.getCount();
				var records = [];
				for(var i=0; i<count; i++){
					var record = store.getAt(i);
					records[records.length] = record;
					var contractId = record.id;
					var list = json_data[contractId+""];
					_this.updateGridTotalAmounts(record,list);
				}
				_this.gatherTotalAmount(records);
				_this.unmask();
			},ffn:function(){
				_this.unmask();
			}});
		},
		/**
		 * 更新 Grid 应收，实收合计
		 */
		updateGridTotalAmounts : function(record,latePlans){
			var contractId = record.id;
			var totalAmounts = 0;
			for(var i=0,count=latePlans.length; i<count; i++){
				var plan = latePlans[i];
				var id = plan.id;
				var totalAmount = plan.totalAmount;
				var ytotalAmount = plan.ytotalAmount;
				var penAmount = plan.penAmount;
				var delAmount = plan.delAmount;
				var ypenAmount = plan.ypenAmount;
				var ydelAmount = plan.ydelAmount;
				totalAmounts += (totalAmount - ytotalAmount);
				//id,penAmount,delAmount,ypenAmount,ydelAmount,totalAmount,ytotalAmount,latedays,pdays,ratedays,mgrdays
				var pdays = plan.pdays;
				var ratedays = plan.ratedays;
				var mgrdays = plan.mgrdays;
				//principal,yprincipal,interest,yinterest,mgrAmount,ymgrAmount,	
				var cat = plan.principal-plan.yprincipal;
				var rat = plan.interest-plan.yinterest;
				var mat = plan.mgrAmount-plan.ymgrAmount;
				var pat = plan.penAmount-plan.ypenAmount;
				var dat = plan.delAmount-plan.ydelAmount;
				var tat = StringHandler.forDight(cat+rat+mat+pat+dat, 2);
				var data = {id:id, pdays:pdays, ratedays:ratedays, mgrdays:mgrdays, cat:cat, rat:rat, mat:mat, pat:pat, dat:dat, tat:tat, penAmount:penAmount, delAmount:delAmount};
				if(!this.datas[contractId]){
					this.datas[contractId] = [data];
				}else{
					var arr = this.datas[contractId];
					arr[arr.length] = data;
				}
			}
			totalAmounts = StringHandler.forDight(totalAmounts,2);
			record.set('totalAmounts',totalAmounts);
			record.set('rtotalAmount',totalAmounts);
		},
		/**
		 * 按扣收顺序计算金额
		 */
		calculateByOrder : function(record){
			var _this = this;
			var rtotalAmount = record.get('rtotalAmount');
			var tat = parseFloat(rtotalAmount);//实收合计
			var contractId = record.id;
			if(tat<=0){
				ExtUtil.alert({msg : '实收合计必须大于0!'});
				return;
			}
			this.mask('正在按扣收顺序计算收款金额...');
			/*-- 按扣收顺序收款 --*/
			if(!this.orderList){
				EventManager.get('./fcOrder_getDatas.action',{sfn:function(json_data){
					var totalSize = json_data.totalSize;
					if(!totalSize || totalSize == 0){
						ExtUtil.alert({msg : '扣收顺序基础数据未设置，请通知管理员 或 到 系统初始化--> 扣款优先级 功能中进行设置'});
						_this.unmask();
						return;
					}
					var list = json_data.list;
					_this.orderList = list;
					var fieldsArr = _this.groupOrders(list);
					_this.caculateAmountsByDatas(fieldsArr,tat,contractId);
				},ffn:function(){
					_this.unmask();
				}});
			}else{
				var fieldsArr = this.groupOrders(this.orderList);
				this.caculateAmountsByDatas(fieldsArr,tat,contractId);
			}
		},
		groupOrders : function(list){
			var maxLevelArr = [];
			var nomalLevelArr = [];
			//cat,rat,mat,pat,dat,tat
			var totalSize = list.length;
			for(var i=0; i<totalSize; i++){
				var orderData = list[i];
				var code = orderData.code;
				var level = orderData.level;
				switch(code){
					case ORDER_ENUM.L0001: {/*利息*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'rat');
						break;
					}case ORDER_ENUM.L0002: {/*本金*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'cat');
						break;
					}case ORDER_ENUM.L0003: {/*管理费*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'mat');
						break;
					}case ORDER_ENUM.L0004: {/*罚息*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'pat');
						break;
					}case ORDER_ENUM.L0005: {/*滞纳金*/
						this.setOrderToArr(maxLevelArr, nomalLevelArr, level, 'dat');
						break;
					}
				}
			}
			return [maxLevelArr,nomalLevelArr];
		},
		setOrderToArr : function(maxLevelArr, nomalLevelArr, level, field){
			if(level==2){/*最高优先级*/
				maxLevelArr[maxLevelArr.length] = field;
			}else{
				nomalLevelArr[nomalLevelArr.length] = field;
			}
		},
		/**
		 * 为Grid 上的实收赋值
		 */
		caculateAmountsByDatas : function(fieldsArr,tat,contractId){
			var maxLevelArr = fieldsArr[0];
			var nomalLevelArr = fieldsArr[1];
			var planList = this.datas[contractId];
			tat = this.calculateAmountsByOrder(maxLevelArr,planList,tat);
			this.calculateAmountsByOrder(nomalLevelArr,planList,tat);
			this.unmask();
		},
		/**
		 * 根据扣收顺序计算金额
		 */
		calculateAmountsByOrder : function(fieldArr,planList,tat){
			var len = planList.length;
			if(len == 0) return 0;
		
			for(var j=0; j<len; j++){
				var record = planList[j];
				for(var i=0,count=fieldArr.length; i<count; i++){
					var field = fieldArr[i];
					var amount = record[field] || 0.00;
					if(amount == 0) continue;
					if(tat == 0 && amount > 0){
						record[field] = 0;
						continue;
					}
					
					var result = tat - amount;
					if(result >= 0){
						tat -= amount;
						record[field] = amount;
					}else{
						if(tat>0){
							record[field] = tat;
						}else{
							record[field] = 0;
						}
						tat = 0;
					}
				}
				var _tat = parseFloat(record['cat']) + parseFloat(record['rat']) + parseFloat(record['mat']) + parseFloat(record['pat']) + parseFloat(record['dat']);
				record['tat'] = StringHandler.forDight(_tat,2);
			}
			return tat;
		},
		/**
		 * 保存数据
		 */
		saveData : function(){
			var _this = this;
			var cbodAccountId = this.appFrm.findFieldByName("accountId");
			var accountId = cbodAccountId.getValue();
			if(!accountId){
				ExtUtil.alert({msg:'请选择收款银行!'});
				return;
			}
			var batchDatas = this.getBatchDatas();
			if(null == batchDatas || batchDatas.length == 0){
				ExtUtil.alert({msg:'表格中没有要收款的数据!'});
				return;
			}
			
			var tat = this.appFrm.getValueByName("tat");
			ExtUtil.confirm({msg : '本次收款实收合计：<span style="color:blue;font-weight:bold;">'+tat+'</span>元，确定收款?',fn:function(){
				if(arguments && arguments[0] != 'yes') return;
				save();
			}});
			
			function save(){
				_this.mask('正在保存数据...');
				EventManager.frm_save(_this.appFrm,{beforeMake : function(formDatas){
					formDatas.ids = _this.ids;
					formDatas.key = _this.bussKey;
					var isVoucher = _this.apptbar.getValue("isVoucher");
					formDatas.isVoucher = isVoucher ? 1 : 0;/* 1 : 表示自动生成财务凭证*/
					formDatas.sysId = _this.sysId;
					formDatas.batchDatas = Ext.encode(batchDatas);
				},sfn:function(formDatas){
					_this.close();
				 	_this.refresh();
				 	_this.unmask();
				},ffn:function(){_this.unmask();}});
			}
			
		},
		/**
		 * 获取本次批量收款金额
		 */
		getBatchDatas : function(){
			var datas = this.datas;
			if(!datas){
				ExtUtil.alert({msg:'在调用 getBatchDatas 方法时,this.datas 值为空!'});
				return null;
			}
			var arr = [];
			for(var key in datas){
				var planList = datas[key];
				if(null == planList || planList.length == 0) continue;
				arr = arr.concat(planList);
			}
			return arr;
		},
		mask : function(msg){
			Cmw.mask(this.mainPanel,msg);
			this.apptbar.disable();
		},
		unmask : function(){
			Cmw.unmask(this.mainPanel);
			this.apptbar.enable();
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
			this.appFrm.reset();
		},
		/**
		 * 关闭窗口
		 */
		close : function(){
			this.resetData();
			this.appWin.hide();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		}
	};
});
