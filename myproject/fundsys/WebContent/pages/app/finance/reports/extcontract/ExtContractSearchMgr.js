/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 展期协议书查询 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.ExtContractSearchMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.ExtContractSearchMgr,Ext.util.MyObservable,{
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
		if(this.appPanel != null){
			this.appPanel.destroy();
			this.appPanel = null;
		}
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
		var wd = 180;
		var txt_code = FormUtil.getTxtField({fieldLabel : '展期协议书编号',name:'code',maxLength : 50,width:wd});
		var txt_loanCode = FormUtil.getTxtField({fieldLabel : '借款合同编号',name:'loanCode',width:wd,maxLength : 50});
		
		var cbo_eqopendAmount = FormUtil.getEqOpLCbox({name:'eqopendAmount'});
		var money_endAmount = FormUtil.getMoneyField({fieldLabel : '原贷款金额',name:'endAmount',width:100});
		var comp_endAmount = FormUtil.getMyCompositeField({
			 fieldLabel: '原贷款金额',width:wd,sigins:null,
			 name:'comp_endAmount',
			 items : [cbo_eqopendAmount,money_endAmount]
		});
		var cbo_eqopextAmount = FormUtil.getEqOpLCbox({name:'eqopextAmountt'});
		var money_extAmount = FormUtil.getMoneyField({fieldLabel : '展期金额',name:'extAmount',width:100});
		
		var comp_extAmount = FormUtil.getMyCompositeField({
			 fieldLabel: '原贷款金额',width:wd,sigins:null,
			 name:'comp_extAmount',
			 items : [cbo_eqopextAmount,money_extAmount]
		});
		var int_yearLoan = FormUtil.getIntegerField({
		    fieldLabel: '展期期限(年)',
		    name: 'yearLoan',
		    width:20
		});
		
		var int_monthLoan = FormUtil.getIntegerField({
		    fieldLabel: '展期期限(月)',
		    name: 'monthLoan',
		      width:20
		});
		
		var int_dayLoan = FormUtil.getIntegerField({
		    fieldLabel: '展期期限(日)',
		    name: 'dayLoan',
		    width:20
		});
		
		var cbo_eqoploanLimit = FormUtil.getEqOpLCbox({name:'eqopLoanLimit'});
		var comp_loanLimit = FormUtil.getMyCompositeField({
			 fieldLabel: '展期期限',name:'limitLoan',width:190,sigins:null,
			 items : [cbo_eqoploanLimit,int_yearLoan,
			 	{xtype : 'displayfield',value : '年',width : 6},
			 	int_monthLoan,
			 	{xtype : 'displayfield',value : '月',width : 6},
			 	int_dayLoan,
			 	{xtype : 'displayfield',value : '日',width : 6}
			 	]
		});
		
		var cbo_payType = FormUtil.getLCboField({
		    fieldLabel: '展期还款方式',
		    name : 'payType',
		    width:wd,
		    data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.payType_datas)
		});
		
		var txt_mgrtype = FormUtil.getLCboField({fieldLabel : '展期管理费收取方式',name:'mgrtype',width:wd,data: Lcbo_dataSource.mgrtype_dates});
		var rad_isadvance = FormUtil.getRadioGroup({
						fieldLabel : '展期是否提前收息',
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
						fieldLabel : '展期利率类型',
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
									"inputValue" : 1
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
		var layout_fields = [
			{cmns:'THREE',
			fields:[txt_code,txt_loanCode,comp_endAmount,comp_extAmount,comp_loanLimit,
			cbo_payType,txt_mgrtype,rad_isadvance,rad_rateType
			]}];

		var queryFrm = FormUtil.createLayoutFrm({region:'north',labelWidth:120},layout_fields);
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
			token : '展期详情',
			text : Btn_Cfgs.EXTCONTRACT_DETAIL_BTN_TXT,
			iconCls:'page_detail',
			tooltip:Btn_Cfgs.EXTCONTRACT_DETAIL_BTN_TIP_TXT,
			handler : function(){
				var selId = self.globalMgr.appgrid.getSelId();
				if(selId){
					self.globalMgr.winEdit.show({key:"详情",optionType:OPTION_TYPE.DETAIL,self:self});
				}
			}
		},{
			token : '展期还款计划导出',
			text : Btn_Cfgs.EXTPLAN_EXCEL_BTN_TXT,
			iconCls:'page_exportxls',
			tooltip:Btn_Cfgs.EXTPLAN_EXCEL_BTN_TIP_TXT,
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
			var structure_1 = [{
			    header: '展期协议书编号',
			    name: 'code',
			    width: 145
			},
			{
			    header: '借款合同号',
			    name: 'loanCode',
			    width: 145
			},
			{
			    header: '原贷款金额',
			    name: 'endAmount',
			    width: 125,
			    renderer: Render_dataSource.moneyRender
			},
			{
			    header: '展期金额',
			    width: 125,
			    name :'extAmount',
			    renderer: Render_dataSource.moneyRender
			},
			{
			    header: '还款方式',
			    name: 'payType',
			    width: 125
//			    renderer:Render_dataSource.payTypeRender
			},
			{
			    header: '展期期限',
			    name: 'loanLimit',
			    width: 125
			},
			{
			    header: '管理费收取方式',
			    name: 'mgrtype',
			    width: 125,
			    renderer: Render_dataSource.mgrtype_render
			},
			{
			    header: '管理费率',
			    name: 'mrate',
			    width: 60,
			    renderer: function(val) {
			        switch (val) {
			        case 0:
			            val = '0';
			            break;
			        }
			        return val+'%';
			    }
			},
			{
			    header: '合同ID',
			    name: 'contractId',
			    hidden:true,
			    width: 125
			},
			{
			    header: '展期利率类型',
			    name: 'rateType',
			    width: 125,
			    renderer: Render_dataSource.rateTypeRender
			},
			{
			    header: '展期贷款利率',
			    name: 'rate',
			    width: 60,
			    renderer: function(val) {
			        switch (val) {
			        case 0:
			            val = '0';
			            break;
			
			        }
			        return val+'%';
			    }
			},
			{
			    header: '展期起始日期',
			    name: 'estartDate',
			    width: 125
			},
			{
			    header: '展期截止日期',
			    name: 'eendDate',
			    width: 125
			},
			{
			    header: '是否预收息',
			    name: 'isadvance',
			    width: 60,
			    renderer: Render_dataSource.isadvanceRender
			},
			{
			    header: '起始日期',
			    name: 'ostartDate',
			    width: 125
			},
			{
			    header: '截止日期',
			    name: 'oendDate',
			    width: 125
			},
			{
			    header: '其他事项',
			    name: 'otherRemark',
			    width: 125
			}];
			var appgrid = new Ext.ux.grid.AppGrid({
				title : '展期表',
				tbar :self.toolBar,
			    structure: structure_1,
			    url: './fcExtContract_list.action?custType='+0,
			    needPage: true,
			     width : (CLIENTWIDTH-8)/3,
			     region: 'west',
			    height : 150,
			    isLoad: true,
			    keyField: 'id',
			     listeners :{
			    	'render':function(){
			    		_this.globalMgr.getQparams(_this);
			    	}
			    }
			});
			appgrid.addListener("rowclick",function(appgrid,rowIndex,event){
		   var record = appgrid.getStore().getAt(rowIndex);
		   if(record==null){
		   		return;
		   }
		   var id = appgrid.getSelId();
			_this.globalMgr.appgrid2.reload({formId:id,actionType : 2});
		});
			_this.globalMgr.appgrid = appgrid;
		return appgrid;
	},
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
			title:'展期还款计划表',
		    structure: structure_1,
		    url: './fcExtPlan_list.action',
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
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.globalMgr.getQparams(_this);
			if(params) {
				EventManager.query(_this.globalMgr.appgrid,params);
			}
		},
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.globalMgr.appgrid;
				
				parentCfg.parent = parent;
				parentCfg.sysId = _this.params.sysId;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule="ExrContractDetail";
					Cmw.importPackage('pages/app/finance/reports/extcontract/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
				
			}
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
				params.extContractId = selId;
				params.actionType = 2;
				EventManager.doExport(token,params);
			}else{
				ExtUtil.alert({msg:"请选择放款单表中数据！"});
				return;
			}
			
		}
	}
});
