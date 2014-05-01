/**
 * 增资审批
 */
Ext.namespace("cmw.skythink");
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
				var self = this;
				var txt_Name = FormUtil.getTxtField({
							fieldLabel : '银行帐号',
							name : 'payAccount'
						});
				var txt_doDate = FormUtil.getDateField({
							name : 'doDate',
							fieldLabel : '合同签订日期'
						});
				var txt_startDate1 = FormUtil.getDateField({
							name : 'payDate',
							width : 90
						});
				var txt_endDate1 = FormUtil.getDateField({
							name : 'endDate',
							width : 90
						});
				var comp_appDate = FormUtil.getMyCompositeField({
							itemNames : 'payDate,endDate',
							sigins : null,
							fieldLabel : '委托日期范围',
							width : 210,
							sigins : null,
							name : 'comp_estartDate',
							items : [txt_startDate1, {
										xtype : 'displayfield',
										value : '至'
									}, txt_endDate1]
						});
				var txt_code = FormUtil.getTxtField({
							fieldLabel : '编号',
							name : 'code'
						});
				var txt_contactTel = FormUtil.getTxtField({
							fieldLabel : '账户名',
							name : 'accName'
						});
		
				var cbo_eqopAmount = FormUtil.getEqOpLCbox({
							name : 'eqopAmount'
						});
				var txt_endAmount = FormUtil.getMoneyField({
							fieldLabel : '委托金额',
							name : 'appAmount',
							width : 70
						});
				var txt_inAddress = FormUtil.getMyCompositeField({
							fieldLabel : '委托金额',
							width : 150,
							sigins : null,
							itemNames : 'eqopAmount,appAmount',
							name : 'comp_appAmount',
							items : [cbo_eqopAmount, txt_endAmount]
						});
				var layout_fields = [{
					cmns : 'THREE',
					fields : [txt_code, txt_Name, txt_contactTel, txt_doDate,
							comp_appDate, txt_inAddress]
				}]
				var queryFrm = FormUtil.createLayoutFrm(null, layout_fields);
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
	getAppGrid : function(){	var _this = this;
				var structure_1 = [{
					header : 'entrustCustId',
					name : 'entrustCustId',
					hidden : true
				},{
					header : '状态',
					name : 'status',
					width : 52,
					renderer : function(val) {
						return Render_dataSource.entrusIsenRender(val);
					}
				}, {
					header : '合同编号',
					name : 'code'
				}, {
					header : '收款银行',
					name : 'payBank'
				}, {
					header : '收款账号',
					name : 'payAccount'
				}, {
					header : '账户名',
					name : 'accName'
				}, {
					header : '委托金额',
					name : 'appAmount',
			renderer: function(val) {
		    return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';}
				}, {
					header : '委托产品',
					name : 'productsId'
				}, {
					header : '合同签订日期',
					name : 'doDate'
				}, {
					header : '委托生效日期',
					name : 'payDate'
				}, {
					header : '委托失效日期',
					name : 'endDate'
				}];
				var appgrid = new Ext.ux.grid.AppGrid({// 创建表格
					title : '增资申请审批',
					tbar : this.toolBar,
					structure : structure_1,
					url : './fuAmountApply_auditlist.action',
					needPage : true,
					keyField : 'id',
					isLoad : false,
					listeners : {
						render : function(grid) {
							_this.globalMgr.query(_this);
						}
					}
				});
				return appgrid;
			
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
			var codeObj = _this.appgrid.getCmnVals("code,contractId,entrustCustId");
			var sysId = _this.params.sysid;
			var contractId = codeObj.contractId;
			var entrustCustId = codeObj.entrustCustId;
			 ExtUtil.confirm({title:'提示',msg:'确定提交编号为："'+codeObj.code+'"的展期申请单?',fn:function(){
		 	  		if(arguments && arguments[0] != 'yes') return;
					var params = {isnewInstance:true,sysId:sysId,applyId:applyId,contractId:contractId,procId:null,bussProccCode:'B8888',entrustCustId:entrustCustId};
					tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
					url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
					var title =  '业务办理';
					var apptabtreewinId = _this.params["apptabtreewinId"];
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

