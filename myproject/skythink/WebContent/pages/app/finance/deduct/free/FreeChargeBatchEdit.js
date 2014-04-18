/**
 * 个人贷款批量放款
 * @author 程明卫
 * @date 2013-01-17 15:57:32
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
		gridTitle : '待收手续费客户列表',
		bussKey : null,/*钥匙KEY*/
		ids : null,/*待收款续费的放款单ID列表*/
		sysId : null,
		vtempCode : VOUCHERTEMP_CODE.LOANFREE,/*放款手续费收取凭证模板编号 constant.js定义*/
		setParams:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId  = parentCfg.parent.sysId;
			this.bussKey = parentCfg.bussKey;
			if(!this.bussKey){
				ExtUtil.error({msg:'bussKey 不能为空!'});
			}
			this.params = this.parentCfg.params;
		},
		createAppWindow : function(){
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({width:860,height:370,tbar:this.apptbar,items : [this.mainPanel],
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
			this.appWin.show(this.parent.getEl());
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function(){
			var _this = this;
			this.appFrm.reset();
			var bdat_lastDate = this.appFrm.findFieldByName('lastDate').setValue(CURENT_DATE());
			var ids = _this.parent.getSelIds();
			var params = {
				key : _this.bussKey,
				ids : ids
			};
			this.appGrid.reload(params,function(records){
				_this.gatherTotalyamount(records);
			});
		},
		gatherTotalyamount:function(records){
			var totalnotamount = 0;
				var count = records.length;
				var idArr = [];
				for(var i=0; i<count; i++){
					var recrod = records[i];
					var id = recrod.id;
					idArr[idArr.length] = id;
					var notamount = recrod.get("notamount");
					if(!notamount) continue;
					 totalnotamount += parseFloat(notamount);
				}
				this.ids = idArr.join(",");
				var title = this.gridTitle + 
				"&nbsp;&nbsp;<span style='color:red;font-weight:normal;'>【待收手续费记录:<span style='font-weight:bold;'>"+count+"</span>条，" +
				" 累计应收手续费：<span style='font-weight:bold;'>"+totalnotamount.toFixed(2)+"元(大写："+Cmw.cmycurd(totalnotamount)+")</span>】</span>";
				
				this.appGrid.setTitle(title);
				/* 为实际放款日期赋值*/
				
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
				text : Btn_Cfgs.DO_FREE_BTN_TXT,	/*-- 放款 --*/ 
				iconCls:Btn_Cfgs.DO_FREE_CLS,
				tooltip:Btn_Cfgs.DO_FREE_TIP_BTN_TXT,
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
			var mainPanel = new Ext.Panel({border:false,items:[batchGrid,appForm]});
			return mainPanel;
		},
		/**
		 * 创建批处理GRID
		 */
		createBatchGrid : function(){
			var structure_1 = [{
			    header: '收款状态',
			    name: 'status',
		     	renderer : function(val){
			    	 var status = Render_dataSource.statusRender(val);
			    	 return status;
			    }
			},
			{
			    header: '借款合同号',
			    name: 'code',
			    width:100
			},
			{
			    header: '客户姓名',
			    name: 'name',
			    width:60
			},
			{
			    header: '手续费率',
			    name: 'prate',
			    width:60,
			    renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			    header: '应收手续费',
			    name: 'freeamount',
			    width:135,
				renderer: function(val) {
			       return (val && val>0) ?/*Cmw.getThousandths(val)*/val+'元' : '';
			    }
			},
			{
			    header: '已收手续费',
			    name: 'yamount',
			    width:135,
				renderer: function(val) {
			       return (val && val>0) ?/*Cmw.getThousandths(val)*/val+'元' : '';
			    }
			},
			{
			    header: '本次实收手续费',
			    name: 'notamount',
			    width:100,
				renderer: function(val,metaData,record) {
					metaData.style='color:blue;font-weight:bold;';
			       return (val && val>0) ? /*Cmw.getThousandths(val)*/val+'元' : '';
			    }
			},
			{
			    header: '实际放款日期',
			    name: 'realDate',
			    width:80
			},{ header: '合同ID', name: 'contractId', hideable : true, hidden : true}];
			var num_notamount =  FormUtil.getMoneyField({name : 'notamount',allowBlank : false,width:135});
			var appgrid =new Ext.ux.grid.MyEditGrid({
				title : '待放款客户列表',
			    structure: structure_1,
			    url: './fcFree_get.action',
			    needPage: false,
			    isLoad: false,
			    autoScroll: true,
			    autoWidth : true,
			    height:250,
			    editEles : {6:num_notamount},
			    keyField: 'id'
			});
			appgrid.on('afteredit', afterEdit, this );
			function afterEdit(e) {
			    e.record.commit();
			    var store = appgrid.getStore();
			    var records = store.getRange(0,store.getCount());
			    this.gatherTotalyamount(records);
			};
			return appgrid;
		},
		/**
		 * 创建表单
		 */
		createAppForm : function(){
			var _this = this;
			
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
				params : {isIncome:1,sysId:_this.sysId}});
			
			var txt_bankAccount = FormUtil.getReadTxtField({
			    fieldLabel: '银行帐号',
			    name: 'bankAccount',
			    "width": 160
			});
			
			var bdat_lastDate = FormUtil.getDateField({
			    fieldLabel: '收款日期',
			    name: 'lastDate',
			    "width": 100,
			    "allowBlank": false,
			    editable:false,
			    "maxLength": 50
			});

			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbog_accountId, txt_bankAccount, bdat_lastDate]
			}];
			var frm_cfg = {
				title:'放款信息录入',
				labelWidth : 95,
			    url: './fcFree_chargefree.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function(jsonData){
			this.resetData();
			this.appFrm.setFieldValues(jsonData);
			var txt_contractId = this.appFrm.findFieldByName('contractId');
			txt_contractId.setValue(_this.contractId);
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
			EventManager.frm_save(this.appFrm,{beforeMake : function(formDatas){
				formDatas.ids = _this.ids;
				formDatas.key = _this.bussKey;
				var isVoucher = _this.apptbar.getValue("isVoucher");
				formDatas.isVoucher = isVoucher ? 1 : 0;/* 1 : 表示自动生成财务凭证*/
				formDatas.vtempCode = _this.vtempCode;
				formDatas.sysId = _this.sysId;
				formDatas.batchDatas = Ext.encode(batchDatas);
			},sfn:function(formDatas){
				_this.close();
			 	_this.parent.reload();
			}});
		},
		/**
		 * 获取本次批量收款金额
		 */
		getBatchDatas : function(){
			var store = this.appGrid.getStore();
			var arr = [];
			for(var i=0,count = store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id = record.id;
				var notamount = record.get('notamount');
				if(!notamount || notamount<0) continue;
				var contractId = record.get('contractId');
				arr[arr.length] = {id : id, contractId : contractId, yamount : notamount};
			}
			return arr;
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
