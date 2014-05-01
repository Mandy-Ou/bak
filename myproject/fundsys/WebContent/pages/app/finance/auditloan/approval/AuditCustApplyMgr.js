/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 个人客户贷款审批 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.AuditCustApplyMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.AuditCustApplyMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			id : ComptIdMgr.auditCustApplyMgrId,
			tab : tab,
			params : params,
			hasTopSys : false,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		var txt_name = FormUtil.getTxtField({fieldLabel : '客户姓名',name:'name',width : 150});
		var cbo_cardType = FormUtil.getRCboField({
		    fieldLabel: '证件类型',
		    name: 'cardType',
		    "width": 150,
		    allDispTxt : Lcbo_dataSource.allDispTxt,
		    register : REGISTER.GvlistDatas,
		    restypeId : '100002'
		    //"url": "./sysGvlist_cbodatas.action?restypeId=100002"
		});
		
		var txt_cardNum = FormUtil.getTxtField({
		    fieldLabel: '证件号码',
		    name : 'cardNum',
		    width : 150
		});
		
		var txt_phone = FormUtil.getTxtField({
		    fieldLabel: '手机',
		    name: 'phone',
		    width: 150
		});
		
		var txt_contactTel = FormUtil.getTxtField({
		    fieldLabel: '联系电话',
		    name: 'contactTel',
		     width: 150
		});
		
			
		var int_yearLoan = FormUtil.getIntegerField({
		    fieldLabel: '贷款期限(年)',
		    name: 'yearLoan',
		    width: 30
		});
		
		var int_monthLoan = FormUtil.getIntegerField({
		    fieldLabel: '贷款期限(月)',
		    name: 'monthLoan',
		     width: 30
		});
		
		var int_dayLoan = FormUtil.getIntegerField({
		    fieldLabel: '贷款期限(日)',
		    name: 'dayLoan',
		    width: 30
		});
		
		var cbo_eqoploanLimit = FormUtil.getEqOpLCbox({name:'eqopLoanLimit'});
		var comp_loanLimit = FormUtil.getMyCompositeField({
			 fieldLabel: '贷款期限',name:'limitLoan',width:215,sigins:null,
			 items : [cbo_eqoploanLimit,int_yearLoan,
			 	{xtype : 'displayfield',value : '年',width : 6},
			 	int_monthLoan,
			 	{xtype : 'displayfield',value : '月',width : 6},
			 	int_dayLoan,
			 	{xtype : 'displayfield',value : '日',width : 6}
			 	]
		});
		
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
		
		var cbo_payType = FormUtil.getLCboField({
		    fieldLabel: '还款方式',
		    name : 'payType',
		    width : 170,
		    data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.payType_datas)
		});
		
		var txt_breed = new Ext.ux.form.AppComboxImg({//FormUtil.getTxtField({
		    fieldLabel: '业务品种',
		    name: 'breed',
		    "width": 200,
		    valueField : 'id',
			displayField : 'name',
		    url : './sysVariety_cbolist.action',
		    params : {sysId : this.params.sysid},
		    allDispTxt : Lcbo_dataSource.allDispTxt
		});
		
		var layout_fields = [{cmns:'THREE',fields:[txt_name,cbo_cardType,txt_cardNum,txt_phone,txt_contactTel,comp_loanLimit,comp_appAmount,cbo_payType,txt_breed]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{/*查询*/
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},{/*重置*/
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				self.queryFrm.reset();
			}
		},{type:"sp"},{/*审批*/
			token : '审批',
			text : Btn_Cfgs.AUDIT_BTN_TXT,
			iconCls:Btn_Cfgs.AUDIT_CLS,
			tooltip:Btn_Cfgs.AUDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.submitApplyForm(self);
			}
		}
		/*,{
			text : Btn_Cfgs.TURN_BTN_TXT,
			iconCls:Btn_Cfgs.TURN_CLS,
			tooltip:Btn_Cfgs.TURN_TIP_BTN_TXT,
			handler : function(){
				alert("转办未实现");
			}
		}*/];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var self = this;
			var structure_1 = [
			{header: '客户类型',name: 'custType',hidden: true},
			{header: '客户ID',name: 'customerId',hidden: true},
			{header: '审批状态',name: 'state', width: 90,
			 renderer: function(val) {
		       return Render_dataSource.applyStateRender(val);
		    }},
			{header: '项目申请编号',name: 'code', width: 90},
			{header: '客户编号',name: 'custCode'},
			{header: '客户名称',name: 'name'},
			{header: '证件类型',	name: 'cardType' ,width: 65},
			{header: '证件号码',name: 'cardNum'},
			{header: '手机',name: 'phone'},
			{header: '联系电话',name: 'contactTel'},
			{header: '业务品种', name: 'breed'},
			{header: '贷款金额', name: 'appAmount' ,width: 65,
			 renderer: function(val) {
		       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
		    }},
			{header: '贷款期限',  name: 'loanLimit' ,width: 65},
			{header: '还款方式',  name: 'payType' ,width: 160},
			{header: '利率类型',  name: 'rateType',width: 65,
			 renderer: function(val) {
		       return Render_dataSource.rateTypeRender(val);
		    }},
			{header: '贷款利率',  name: 'rate' ,width: 65,
			 renderer: function(val) {
		       return val ? val+'%' : '';
		    }},
			{header: '期限种类',  name: 'limitType',width: 65},
			{header: '贷款方式',  name: 'loanType',width: 65},
			{header: '行业分类',  name: 'inType',width: 65},
			{header: '借款主体',  name: 'loanMain',width: 65}
			];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar :self.toolBar,
			    structure: structure_1,
			    url: './fcApply_auditlist.action',
			    needPage: true,
			    isLoad: true,
			    keyField: 'id',
			    params : {custType : '0'},
			    width:600
			});
		return appgrid_1;
		
	},
	
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	
	globalMgr : {
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues() || {};
			params.custType = 0;
			EventManager.query(_this.appgrid,params);
		},
		/**
		 * 审批申请单	
		 * @param {} _this
		 */
		submitApplyForm : function(_this){
			var applyId = _this.appgrid.getSelId();
			if(!applyId) return;
			var codeObj = _this.appgrid.getCmnVals("code");
			 ExtUtil.confirm({title:'提示',msg:'确定审批编号为："'+codeObj.code+'"的贷款申请单?',fn:function(){
		 	  		if(arguments && arguments[0] != 'yes') return;
		 	  		var params = _this.appgrid.getCmnVals(["customerId","custType"]);
					params["applyId"] = applyId;
					params["procId"] = null;
					params["isnewInstance"] = true;
					var tabId = CUSTTAB_ID.flow_auditMainUITab.id;
					var url =  CUSTTAB_ID.flow_auditMainUITab.url;
					var title =  '业务审批';
					var apptabtreewinId = _this.params["apptabtreewinId"];
					var tab = Ext.getCmp(tabId);
					if(tab){
						Cmw.hideTab(apptabtreewinId,tab);
					}
					Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
			 }});
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				
			}
		}
	}
});

