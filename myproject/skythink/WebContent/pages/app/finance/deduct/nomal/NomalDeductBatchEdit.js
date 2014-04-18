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
		gridTitle : '待收款客户列表',
		bussKey : null,/*钥匙KEY*/
		ids : null,/*待收款的收款单ID列表*/
		sysId : null,
		uamount : null,
		total :  null,
		vtempCode : VOUCHERTEMP_CODE.NOMALDEDUCT,/*正常扣收凭证模板编号 constant.js定义*/
		setParams:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.bussKey = parentCfg.bussKey;
			if(!this.bussKey){
				ExtUtil.error({msg:'bussKey 不能为空!'});
			}
			this.params = this.parentCfg.params;
		},
		createAppWindow : function(){
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({width:800,height:372,tbar:this.apptbar,items : [this.mainPanel],
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
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function(){
			var _this = this;
			this.appFrm.reset();
			this.appFrm.findFieldByName('rectDate').setValue(CURENT_DATE());
			var ids = this.parent.getSelIds();
			var params = {
				key : this.bussKey,
				ids : ids
			};
			this.appGrid.reload(params,function(records){
				_this.gatherTotalAmount(records);
			});
		},
		/**
		 * 汇总金额
		 */
		gatherTotalAmount : function(records){
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
			this.total = totalAmount;
			this.ids = idArr.join(",");
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
			var mainPanel = new Ext.Panel({border:false,autoScroll:true,items:[batchGrid,appForm]});
			return mainPanel;
		},
		/**
		 * 创建批处理GRID
		 */
		createBatchGrid : function(){
			var structure_1 = [{header: '借款合同号', name: 'code'},
			{header: '客户类型',name: 'custType',width: 60,renderer : function(val){return Render_dataSource.custTypeRender(val);}},
			{header: '客户名称', name: 'name'},
			{header: '本期期数',	name: 'phases',width: 60},
			{header: '应还日期',name: 'xpayDate', width: 90},
			{ header: '应收合计',name: 'ztotalAmount', width: 90,renderer : function(val){return Cmw.getThousandths(val);}},
			{header: '本次实收合计', name: 'rtotalAmount',width: 90,renderer : function(val,metaData){
				metaData.style='color:blue;font-weight:bold;';
				return Cmw.getThousandths(val);
			}},
			{ header: '合同ID', name: 'contractId', hideable : true, hidden : true}];
			var num_rtotalAmount =  FormUtil.getMoneyField({name : 'rtotalAmount',allowBlank : false});
			var appgrid = new Ext.ux.grid.MyEditGrid({
				title : '待收款客户列表',
			    structure: structure_1,
			    url: './fcNomalDeduct_obtain.action',
			    needPage: false,
			    isLoad: false,
			    autoWidth : true,
			    height:250,
			    editEles : {6:num_rtotalAmount},
			    keyField: 'id'
			});
			
			appgrid.on('afteredit', afterEdit, this );
			function afterEdit(e) {
			    e.record.commit();
			    var store = appgrid.getStore();
			    var records = store.getRange(0,store.getCount());
			    this.gatherTotalAmount(records);
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
				_this.uamount = record.get("uamount");
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
					
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbog_accountId, txt_bankAccount, dat_rectDate]
			}];
			var frm_cfg = {
				title:'收款信息录入', 
			    url: './fcNomalDeduct_receivables.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform;
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
			if(parseFloat(_this.appAmount)>parseFloat(_this.uamount)){
				ExtUtil.warn({msg:"选的放款账户余额已不足，请选择其他账号进行放款！"});
				return;
			}
			var txt_bankAccount = this.appFrm.findFieldByName("bankAccount");
			var bankAccount = txt_bankAccount.getValue();
			if(!bankAccount){
				ExtUtil.alert({msg:'没有银行账号，请重新选择银行!'});
				return;
			}
			var batchDatas = this.getBatchDatas();
			_this.mask("正在收款...");
			EventManager.frm_save(this.appFrm,{beforeMake : function(formDatas){
				formDatas.ids = _this.ids;
				var isVoucher = _this.apptbar.getValue("isVoucher");
				formDatas.isVoucher = isVoucher ? 1 : 0;/* 1 : 表示自动生成财务凭证*/
				formDatas.vtempCode = _this.vtempCode;
				formDatas.sysId = _this.sysId;
				formDatas.key = _this.bussKey;
				formDatas.batchDatas = Ext.encode(batchDatas);
			},sfn:function(formDatas){
				_this.close();
			 	_this.refresh();
			 	_this.unmask();
			},ffn : function(formDatas) {
				_this.unmask();
			}
			});
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
		 * 获取本次批量收款金额
		 */
		getBatchDatas : function(){
			var store = this.appGrid.getStore();
			var arr = [];
			for(var i=0,count = store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id = record.id;
				var contractId = record.get('contractId');
				var rtotalAmount = record.get('rtotalAmount');
				if(!rtotalAmount || rtotalAmount<0) continue;
				arr[arr.length] = {id : id,contractId : contractId, rtotalAmount : rtotalAmount};
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
