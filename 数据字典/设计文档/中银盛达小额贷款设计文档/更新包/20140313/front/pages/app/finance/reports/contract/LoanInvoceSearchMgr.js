/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 还款计划查询 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.LoanInvoceSearchMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.LoanInvoceSearchMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,
			getAppCmpt : this.getAppCmpt,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			getAppGrid2 : this.getAppGrid2,
			getCenterPnl: this.getCenterPnl,
			destroyCmpts :this.destroyCmpts,
			changeSize : this.changeSize, 
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
	},
	/**
	 * 组装
	 * @return {}
	 */
	getAppCmpt : function(){
		var _this = this;
		var panelMain = new Ext.Panel({
			border : false,
			items:[_this.getQueryFrm(),_this.getToolBar(),_this.getCenterPnl()]
		});
		return panelMain;
	},
	getCenterPnl : function(){
		var _this = this;
		var centerPnl = new Ext.Panel({
			layout:'border',width : CLIENTWIDTH-230,height : CLIENTHEIGHT-220,border : false,
			items :[
					_this.getAppGrid(),_this.getAppGrid2()
					]
		});
		return centerPnl;
	},
	changeSize : function(whArr){
		var width = whArr[0]-2;
		var height = whArr[1]-3;
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		
		var txt_code = FormUtil.getTxtField({fieldLabel : '放款单编号',name:'code',width:150});
		var txt_ccode = FormUtil.getTxtField({fieldLabel: '借款合同编号',name: 'ccode',width : 150});
		
		var cbo_eqopAmount = FormUtil.getEqOpLCbox({name:'eqopAmount'});
		var txt_appAmount = FormUtil.getMoneyField({
		    fieldLabel: '贷款金额',
		    name:'appAmount',
		    width:70
		});
		var comp_appAmount = FormUtil.getMyCompositeField({
			 fieldLabel: '贷款金额',width:150,sigins:null,
			 name:'comp_appAmount',
			 items : [cbo_eqopAmount,txt_appAmount]
		});
		
		var txt_payName = FormUtil.getTxtField({fieldLabel: '收款人名称',name: 'payName',width : 150});
		
		var txt_regBank = FormUtil.getTxtField({fieldLabel: '开户行',name: 'regBank',width : 150});
		var txt_account = FormUtil.getTxtField({fieldLabel: '收款帐号',name: 'account',width : 150});
		var txt_payAmount = FormUtil.getMoneyField({fieldLabel: '放款金额',name: 'payAmount',width : 150});
		var txt_prate = FormUtil.getDoubleField({fieldLabel: '放款手续费率',name: 'prate',width : 150});
		var txt_cashier = FormUtil.getTxtField({fieldLabel: '出纳人员',name: 'cashier',width : 150});
		
		var layout_fields = [{cmns:'THREE',fields:[txt_code,txt_ccode,comp_appAmount,txt_payName,txt_regBank,
			txt_account,txt_payAmount,txt_prate,txt_cashier
		]}];
		var queryFrm = FormUtil.createLayoutFrm({region:'north'},layout_fields);
		this.globalMgr.queryFrm = queryFrm;
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},{
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.queryFrm.reset();
			}
		},{type:"sp"},{
			token : '放款单详情',
			text : Btn_Cfgs.LOANINVOCE_DATEIL_BTN_TXT,
			iconCls:'page_detail',
			tooltip:Btn_Cfgs.LOANINVOCE_DATEIL_TIP_BTN_TXT,
			handler : function(){
				var selId = self.globalMgr.appgrid.getSelId();
				if(selId){
					self.globalMgr.winEdit.show({key:"详情",optionType:OPTION_TYPE.DETAIL,self:self});
				}
			}
		},{
			token : '还款计划导出',
			text : Btn_Cfgs.PANLE_EXCEL_BTN_TXT,
			iconCls:'page_exportxls',
			tooltip:Btn_Cfgs.PANLE_EXCEL__TIP_BTN_TXT,
			handler : function(){
					self.globalMgr.doExport(self);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		self.globalMgr.toolBar = toolBar;
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
		var a=0.00;
			var structure_1 = [{
			    header: '放款单id',
			    hidden :true,
			    name: 'id'
			},
			{
			    header: '贷款申请单ID',
			     hidden :true,
			    name: 'formId'
			},
			{
			    header: '借款合同ID',
			     hidden :true,
			    name: 'contractId'
			},
			{
			    header: '放款单合同编号',
			    name: 'code'
			},
			{
			    header: '客户类型',
			    name: 'custType',
			    renderer :function(val){
			    	switch(val){
					  	case "0" :{
					  		val = '个人客户';
					  		break;
					  	}case "1" :{
					  		val = '企业客户';
					  		break;
					  	}
					}
			        return val;
			    }
			},
			{
			    header: '客户名称',
			    name: 'name'
			},
			{
			    header: '贷款金额',
			    name: 'appAmount',
			    renderer: function(val) {
			       	 val = Cmw.getThousandths(val);
			        return val;
			    }
			},
			{
			    header: '收款人名称',
			    name: 'payName'
			},
			{
			    header: '开户行',
			    name: 'regBank'
			},
			{
			    header: '收款帐号',
			    name: 'account'
			},
			{
			    header: '放款金额',
			    name: 'payAmount',
			    renderer: function(val) {
			       	val = Cmw.getThousandths(val);
			        return val;
			    }
			},
			{
			    header: '未放款余额',
			    name: 'unAmount',
			 	 renderer: function(val) {
			       	val = Cmw.getThousandths(val);
			        return val;
			    }
			},
			{
			    header: '放款手续费率',
			    name: 'prate',
			    renderer: function(val) {
	       			return (val) ? val+'%' : '';
			    }
			},
			{
			    header: '出纳人员',
			    name: 'cashier'
			},
			{
			    header: '实际放款日期',
			    name: 'realDate',
			    renderer : function(val){
			    	return (val=='')? '没有通过审批' : val;
			    }
			}
			];
			var appgrid = new Ext.ux.grid.AppGrid({
				title : "放款单表",
			    structure: structure_1,
			    url: './fcLoanInvoce_loanInvoceQuery.action',
			    needPage: true,
			     width : (CLIENTWIDTH-8)/3,
			    height : 150,
			    region: 'west',
			    isLoad: true,
			    keyField: 'id',
			    listeners :{
			    	'render':function(){
			    		_this.globalMgr.query(_this);
			    	}
			    }
			});
				appgrid.addListener("rowclick",function(appgrid,rowIndex,event){
		   var record = appgrid.getStore().getAt(rowIndex);
		   if(record==null){
		   	return;
		   }
		   var selId = appgrid.getSelId();
//			var contractId = appgrid.getCmnVals('contractId').contractId;
			_this.globalMgr.appgrid2.reload({loanInvoceId:selId});
		});
		_this.globalMgr.appgrid=appgrid;
			_this.globalMgr.appgrid = appgrid;
			return appgrid;
	},
	/**
	 * 还款计划表
	 */
	getAppGrid2 : function(){
		var _this = this;
			var structure_1 = [{
		    header: '期数',
		    name: 'phases',
		    width:50
		},
		{
		    header: '放款单ID',
		     hidden :true,
		    name: 'loanInvoceId'
		},
		{
		    header: '应还款日期',
		    name: 'xpayDate'
		},
		{
		    header: '利息',
		    name: 'interest',
		    renderer: function(val) {
		        switch (val) {
		        case 0:
		            val = '0';
		            break;
		
		        }
		        return val;
		    }
		},
		{
		    header: '本金',
		    name: 'principal',
		    renderer: function(val) {
		        switch (val) {
		        case 0:
		            val = '0';
		            break;
		
		        }
		        return val;
		    }
		},
		{
		    header: '管理费',
		    name: 'mgrAmount',
		    renderer: function(val) {
		        switch (val) {
		        case 0:
		            val = '0';
		            break;
		
		        }
		        return val;
		    }
		},
		{
		    header: '应付合计',
		    name: 'totalAmount',
		    renderer: function(val) {
		        switch (val) {
		        case 0:
		            val = '0';
		            break;
		
		        }
		        return val;
		    }
		},
		{
		    header: '剩余本金',
		    name: 'reprincipal',
		    renderer: function(val) {
		        switch (val) {
		        case 0:
		            val = '0';
		            break;
		
		        }
		        return val;
		    }
		}];
		var appgrid = new Ext.ux.grid.AppGrid({
			title:'还款计划表',
		    structure: structure_1,
		    url: './fcChildPlan_list.action',
		    needPage: true,
		    region: 'center',
		    isLoad: false,
		    keyField: 'id'
		});
		_this.globalMgr.appgrid2 = appgrid;
		 return appgrid;
	},
	
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		width : null,
		height :null,
		detailPanel_1 : null,
		queryFrm : null,
		appgrid2 :null,
		appgrid : null,
		activeKey : null,
		toolBar : null,
		appform : null,
		activeKey : null,
		getQparams : function(_this){
			var params = _this.globalMgr.queryFrm.getValues() || {};
			return params;
		},
		/**
		 * 导出Excel
		 * @param {} _this
		 */
		doExport : function(_this){
			var selId = _this.globalMgr.appgrid.getSelId();
			if(selId){
				var params = {};
				var contractId = _this.globalMgr.appgrid.getCmnVals('contractId').contractId;
				var token = _this.params.nodeId;
				params.contractId = contractId;
				params.loanInvoceId = selId;
				EventManager.doExport(token,params);
			}else{
				ExtUtil.alert({msg:"请选择放款单表中数据！"});
				return;
			}
			
		},
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.globalMgr.appgrid;
				
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule="LoanInvoceDetail";
					Cmw.importPackage('pages/app/finance/reports/contract/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
				
			}
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.globalMgr.getQparams(_this);
			if(params) {
				EventManager.query(_this.globalMgr.appgrid,params);
			}
		}
	}
});
