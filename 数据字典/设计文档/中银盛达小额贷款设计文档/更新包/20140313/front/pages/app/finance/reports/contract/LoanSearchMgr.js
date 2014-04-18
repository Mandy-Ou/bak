/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 借款合同查询 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.LoanSearchMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.LoanSearchMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
			hasTopSys : false,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			getAppGrid2 : this.getAppGrid2,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		var wd = 180;
		var Txt_code = FormUtil.getTxtField({fieldLabel : '合同编号',name:'code',width:wd});
		var Txt_accName = FormUtil.getTxtField({fieldLabel : '帐户户名',name:'accName',width:wd});
		var Txt_payAccount = FormUtil.getTxtField({fieldLabel : '还款帐号',name:'payAccount',width:wd});
		var Txt_payBank = FormUtil.getTxtField({fieldLabel : '还款银行',name:'payBank',width:wd});
		var Txt_borAccount = FormUtil.getTxtField({fieldLabel : '借款帐号',name:'borAccount',width:wd});
		var Txt_borBank = FormUtil.getTxtField({fieldLabel : '借款人银行',name:'borBank',width:wd});
		
		var txt_mgrtype = FormUtil.getLCboField({fieldLabel : '管理费收取方式',name:'mgrtype',width:wd,data: Lcbo_dataSource.mgrtype_dates});
		var rad_isadvance = FormUtil.getRadioGroup({
						fieldLabel : '是否提前收息',
						name : 'isadvance',
						"width" : 125,
						"maxLength" : 10,
						"items" : [{
									"boxLabel" : "否",
									"name" : "isadvance",
									"inputValue" : 0
								}, {
									"boxLabel" : "是",
									"name" : "isadvance",
									"inputValue" : 1
								}]
					});
			var rad_rateType = FormUtil.getRadioGroup({
						fieldLabel : '利率类型',
						name : 'rateType',
						width:wd,
						"maxLength" : 10,
						listeners : {
							change : function(rdgp,checked){
								_this.loadRateDatas();
							}
						},
						items : [{
									"boxLabel" : "月利率",
									"name" : "rateType",
									"inputValue" : 1,
									checked : true
								}, {
									"boxLabel" : "日利率",
									"name" : "rateType",
									"inputValue" : 2
								}, {
									"boxLabel" : "年利率",
									"name" : "rateType",
									"inputValue" : 3
								}]
					});
		var txt_payType = FormUtil.getLCboField({fieldLabel : '还款方式',name:'payType',width:wd,data: Lcbo_dataSource.payType_datas});
		var layout_fields = [{cmns:'THREE',fields:[Txt_code,Txt_accName,Txt_payAccount,Txt_payBank,
		Txt_borAccount,Txt_borAccount,Txt_borBank,txt_mgrtype,rad_isadvance,rad_rateType,txt_payType]}]

		var queryFrm = FormUtil.createLayoutFrm({bbar:self.getToolBar()},layout_fields);
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
				self.queryFrm.reset("custType");
			}
		},{type:"sp"},{
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:'page_exportxls',
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.doExport(self);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var self = this;
		var wd = 150;
		var rwd = 80;
			var structure_1 = [	{
			    header: '合同编号',
			     width : wd,
			    name: 'code'
			},{
			    header: '客户类型',
			    name: 'custType',
			    renderer : Render_dataSource.custTypeRender
			},{
			    header: '帐户户名',
			    name: 'accName'
			},{
			    header: '合同签订日期',
			     width : rwd,
			    name: 'doDate'
			},
			{
			    header: '管理费收取方式',
			    name: 'mgrtype',
			    width : 100,
			   	renderer: Render_dataSource. mgrtype_render
			},
			{
			    header: '是否预收息',
			    name: 'isadvance',
			    width : rwd,
			    renderer: Render_dataSource.isRequiredRender
			},{
			    header: '利率类型',
			    width : rwd,
			    name: 'rateType',
			    renderer: Render_dataSource.rate_limitsRender
			},
			{
			    header: '还款方式',
			    name: 'payType',
			    width : 160
			},
			{
			    header: '贷款截止日期',
			    name: 'endDate'
			},
			{
			    header: '每期还款日(号)',
			    name: 'payDay',
			     width : rwd
			},
			{
			    header: '放款日期',
			    name: 'payDate'
			},
			{header: '贷款期限',  name: 'loanLimit' ,width: 65},
			{
			    header: '还款帐号',
			    name: 'payAccount',
			    width : wd
			},
			{
			    header: '还款银行',
			    width : wd,
			    name: 'payBank'
			},
			{
			    header: '借款帐号',
			     width : wd,
			    name: 'borAccount'
			},
			{
			    header: '借款人银行',
			     width : wd,
			    name: 'borBank'
			},
			{
			    header: '贷款金额',
			    name: 'appAmount',
			    renderer: Render_dataSource.moneyRender
			},
			{
			    header: '滞纳金利率',
			    name: 'frate',
			     width : rwd,
			    renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			    header: '罚息利率',
			    name: 'urate',
			    width : rwd,
			    renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			    header: '提前还款费率',
			    name: 'arate',
			    width : rwd,
			   renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			    header: '放款手续费率',
			    name: 'prate',
			    width : rwd,
			    renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			    header: '管理费率',
			    name: 'mrate',
			    width : rwd,
			    renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			    header: '贷款利率',
			    name: 'rate',
			    width : rwd,
			    renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},{
			    header: '合同中未涉及条款',
			    width : wd,
			    name: 'clause'
			}];
			var appgrid = new Ext.ux.grid.AppGrid({
//				tbar :self.toolBar,
			    structure: structure_1,
			    url: './fcLoanContract_list.action',
			    needPage: true,
			    autoScroll:true,
			    keyField: 'id',
			     region: 'east',
			     items :{
			     	region: 'center',
			     	itmes:[self.getAppGrid2()]
			     }
//			     ,
//			    listeners  :{
//			    	'dblclick' : function(){
//			    		self.globalMgr.width = this.getWidth();
//			    		this.setWidth(500);
//			    	},
//			    	'click':function(){
//			    		this.setWidth(self.globalMgr.width);
//			    	}
//			    }
			});
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
		    width : 500,height : 500,
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
		activeKey : null,
		appform : null,
		appgrid2 : null,
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
			return params;
		},
		/**
		 * 导出Excel
		 * @param {} _this
		 */
		doExport : function(_this){
			var params = this.getQparams(_this);
			var token = _this.params.nodeId;
			EventManager.doExport(token,params);
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		}
	}
});
