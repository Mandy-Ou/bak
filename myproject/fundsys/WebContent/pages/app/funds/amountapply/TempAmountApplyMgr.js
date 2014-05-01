Ext.namespace("cmw.skythink");
/**
 * @author 李听
 * 增资申请暂存
 */
cmw.skythink.TempAmountApplyMgr = function() {
	this.init(arguments[0]);
}
Ext.extend(cmw.skythink.TempAmountApplyMgr, Ext.util.MyObservable, {
			initModule : function(tab, params) {
				this.module = new Cmw.app.widget.AbsGContainerView({
					id : ComptIdMgr.tempAmountApplyMgrId,
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
			 */	getQueryFrm : function() {
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
			getToolBar : function() {
				var self = this;
				var barItems = [ 	
						{
							token : '查询',
							text : Btn_Cfgs.QUERY_BTN_TXT,
							iconCls : 'page_query',
							tooltip : Btn_Cfgs.QUERY_TIP_BTN_TXT,
							handler : function() {
								self.globalMgr.query(self);
							}
						},  {
							token : '重置',
							text : Btn_Cfgs.RESET_BTN_TXT,
							iconCls : 'page_reset',
							tooltip : Btn_Cfgs.RESET_TIP_BTN_TXT,
							handler : function() {
								self.queryFrm.reset();
							}
						},/* {
						token : '添加',
						text : Btn_Cfgs.INSERT_BTN_TXT,
						iconCls : Btn_Cfgs.INSERT_CLS,
						tooltip : Btn_Cfgs.INSERT_TIP_BTN_TXT,
						handler : function() {
								self.globalMgr.winEdit.show({
											key : "添加",
											self : self,
											optionType : OPTION_TYPE.ADD
										});
							}
						},*/ {
							token : '编辑',
							text : Btn_Cfgs.MODIFY_BTN_TXT,
							iconCls : 'page_edit',
							tooltip : Btn_Cfgs.MODIFY_TIP_BTN_TXT,
							handler : function() {
									self.globalMgr.doApplyByOp(self,OPTION_TYPE.EDIT/*{
											key : "编辑",
											self : self,
											optionType : OPTION_TYPE.EDIT
										}*/);
							}
						}, {
							token : '详情',
							text : Btn_Cfgs.DETAIL_BTN_TXT,
							iconCls : Btn_Cfgs.DETAIL_CLS,
							tooltip : Btn_Cfgs.DETAIL_TIP_BTN_TXT,
							handler : function() {
								self.globalMgr.winEdit.show({
											key : "详情",
											self : self
										});
							}
						}, {
							token : '提交',
							text : Btn_Cfgs.SUBMIT_BTN_TXT,
							iconCls : 'page_confirm',
							tooltip : Btn_Cfgs.SUBMIT_TIP_BTN_TXT,
								handler : function() {
						self.globalMgr.submitApplyForm(self);
					}
						}/*,{同步
						token : '同步',
							text : Btn_Cfgs.SYNCHRONOUS_BTN_TXT,
							iconCls : Btn_Cfgs.SYNCHRONOUS_CLS,
							tooltip : Btn_Cfgs.SYNCHRONOUS_TIP_BTN_TXT,
							handler : function() {
//								self.globalMgr.synchronousData(self);
							}
						}, {
							type : "sp"
						}, {
							token : '启用',
							text : Btn_Cfgs.ENABLED_BTN_TXT,
							iconCls : 'page_enabled',
							tooltip : Btn_Cfgs.ENABLED_TIP_BTN_TXT,
							handler : function() {
							EventManager.enabledData('./fuEntrustContract_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});							}
						}, {
							token : '禁用',
							text : Btn_Cfgs.DISABLED_BTN_TXT,
							iconCls : 'page_disabled',
							tooltip : Btn_Cfgs.DISABLED_TIP_BTN_TXT,
							handler : function() {
							EventManager.disabledData('./fuEntrustContract_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});							}
						}*//*, {
							token : '删除',
							text : Btn_Cfgs.DELETE_BTN_TXT,
							iconCls : 'page_delete',
							tooltip : Btn_Cfgs.DELETE_TIP_BTN_TXT,
							handler : function() {
									EventManager.deleteData('./fuEntrustContract_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});}
						}*/,
							{
							token : '导出',
							text : Btn_Cfgs.EXPORT_BTN_TXT,
							iconCls : 'page_exportxls',
							tooltip : Btn_Cfgs.EXPORT_TIP_BTN_TXT,
							handler : function() {
								self.globalMgr.doExport(self);
							}
						}					
						];
					toolBar = new Ext.ux.toolbar.MyCmwToolbar({
							aligin : 'right',
							controls : barItems,
							rightData : {
								saveRights : true,
								currNode : this.params[CURR_NODE_KEY]
							}
						});
				return toolBar;
			},
			/**
			 * 获取Grid 对象
			 */
			getAppGrid : function() {
				var _this = this;
				var structure_1 = [{
					header : 'entrustCustId',
					name : 'entrustCustId',
					hidden : true
				},{
					header : '申请单编号',
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
					title : '暂存的增资申请',
					tbar : this.toolBar,
					structure : structure_1,
					url : './fuAmountApply_list.action',
					needPage : true,
					keyField : 'id',
					isLoad : true,
					listeners : {
						render : function(grid) {
							_this.globalMgr.query(_this);
						}
					}
				});
				return appgrid;
			},
			refresh : function(data) {// 刷新
				this.globalMgr.query(this);
				this.globalMgr.activeKey = null;
			},
			globalMgr : {
				/**
				 * 查询方法
				 * 
				 * @param {}
				 *            _this
				 */
				query : function(_this) {
					var params = _this.queryFrm.getValues();
					if (params) {
						EventManager.query(_this.appgrid, params);
					}
				},
				getQparams : function(_this) {
					var params = _this.queryFrm.getValues() || {};
					// /*-- 附加桌面传递的参数 CODE START --*/
					if (_this.params&& _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY]) {
						var deskParams = _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY];
						if (deskParams) {
							Ext.applyIf(params, deskParams);
							_this.params[DESK_MOD_TAG_QUERYPARAMS_KEY] = null;
						}
					}
					return params;
				},
				/**
				 * 添加/编辑申请单	
				 * @param {} _this
				 */
				doApplyByOp : function(_this,op){
					var params = {applyId:null,optionType:op};
					if(op == OPTION_TYPE.EDIT){//编辑 时传入申请单ID，客户ID，客户类型
						var applyId = _this.appgrid.getSelId();
						if(!applyId) return;
						params.applyId=applyId;
						params.appgrid= _this.appgrid;
						var entrustCustId = _this.appgrid.getCmnVals(["entrustCustId"]);
						params = Ext.apply(params,entrustCustId);
						var tabId = CUSTTAB_ID.entrustContractTabId;
						var apptabtreewinId = _this.params["apptabtreewinId"];
						Cmw.activeTab(apptabtreewinId,tabId,params);
					}
				},
	/**
		 * 提交申请单	
		 * @param {} _this
		 */
		submitApplyForm : function(_this){
			var applyId = _this.appgrid.getSelId();
			if(!applyId) return;
			var codeObj = _this.appgrid.getCmnVals("code,contractId,entrustCustId");
			var sysId = _this.params.sysid;
			var contractId = codeObj.contractId;
			var entrustCustId = codeObj.entrustCustId;
			 ExtUtil.confirm({title:'提示',msg:'确定提交编号为："'+codeObj.code+'"的增资申请单?',fn:function(){
		 	  		if(arguments && arguments[0] != 'yes') return;
					var params = {entrustCustId:entrustCustId,isnewInstance:true,sysId:sysId,applyId:applyId,contractId:contractId,procId:null,bussProccCode:'B8888'};
					tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
					url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
					var title =  '业务办理';
					var apptabtreewinId = _this.params["apptabtreewinId"];
					Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
			 }});
		},
				/**
				 * 导出Excel
				 * 
				 * @param {}
				 *            _this
				 */
				doExport : function(_this) {
					var params = _this.globalMgr.getQparams(_this);
					var token = _this.params.nodeId;
					EventManager.doExport(token, params);
				},
				isFormula : null,
				/**
				 * 当前激活的按钮文本
				 * @type
				 */
				activeKey : null,
				sysId : this.params.sysid,
				winEdit : {
					show : function(parentCfg) {
						var _this = parentCfg.self;
						var winkey = parentCfg.key;
						_this.globalMgr.activeKey = winkey;
						var parent = _this.appgrid;
						parent.sysId = _this.globalMgr.sysId;
						parentCfg.parent = parent;
						parentCfg.nodeId = _this.params.nodeId;
						if (_this.appCmpts[winkey]) {
							_this.appCmpts[winkey].show(parentCfg);
						} else {
							var winModule = null;
							/*if (winkey == "添加") {
								winModule = "funds/entrustcust/EntrustCustAddEdit";
							} else */ if(winkey == "编辑"){
								var selId=parent.getSelId();
								parentCfg.selId = selId;
								if(!selId)return;
							winModule = "funds/amountapply/AmountApply.js";
							}else if (winkey == "详情") {
								var selId=parent.getSelId();
								parent.selId = selId;
								parentCfg.parent = parent;
								if(!selId)return;
								winModule ="funds/amountapply/AmountApplyDetil";
							}
							Cmw.importPackage('pages/app/' + winModule,
									function(module) {// 导入包的，有重构
										_this.appCmpts[winkey] = module.WinEdit;
										_this.appCmpts[winkey].show(parentCfg);
									});
						}
					}
				}
			}	
		});
