Ext.namespace("cmw.skythink");
/**
 * @author 李听
 * 暂存的撤资申请
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
			 * 委托人姓名，委托合同编号，委托金额，撤资金额，撤资类型，金额要采用大于小于之类的比较
			 */
				getQueryFrm : function() {
				var self = this;
				var wd=135;
				var txt_code = FormUtil.getTxtField({
							fieldLabel : '委托合同编号',
							name : 'code',
							width:wd
						});
				
				var txt_name = FormUtil.getTxtField({
							fieldLabel : '委托人姓名',
							name : 'name',
							width:wd
						});
		
				var cbo_eqopAmount = FormUtil.getEqOpLCbox({
							name : 'eqopAmount'
						});
				
				var txt_endAmount = FormUtil.getMoneyField({
							fieldLabel : '委托金额',
							name : 'appAmount',
							width : 100
						});
				
				var txt_inAddress = FormUtil.getMyCompositeField({
							fieldLabel : '委托金额',
							width : 175,
							sigins : null,
							name : 'appAmount',
							items : [cbo_eqopAmount, txt_endAmount]
						});
				var cbo_eqopBamount = FormUtil.getEqOpLCbox({
							name : 'eqopBamount'
						});
				
				var txt_endBamount = FormUtil.getMoneyField({
							fieldLabel : '撤资金额',
							name : 'bamount',
							width : 100
						});
				
				var txt_bamount = FormUtil.getMyCompositeField({
							fieldLabel : '撤资金额',
							width : 175,
							sigins : null,
							itemNames : 'eqopBamount,bamount',
							name : 'comp_bamount',
							items : [cbo_eqopBamount, txt_endBamount]
						});
				var txt_isNotExpiration = FormUtil.getRadioGroup({
						fieldLabel : '撤资类型',
						name : 'isNotExpiration',
						//"allowBlank" : false,
						"width" : '135',
						"maxLength" : 50,
						"items" : [{
									"boxLabel" : "正常撤资",
									"name" : "isNotExpiration",
									"inputValue" : 1
									},{
									"boxLabel" : "异常撤资",
									"name" : "isNotExpiration",
									"inputValue" : 0
								}]
					});	
				var layout_fields = [{
					cmns : 'THREE',
					fields : [txt_name, txt_code, txt_inAddress,txt_isNotExpiration,txt_bamount]
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
						},{
							token : '编辑',
							text : Btn_Cfgs.MODIFY_BTN_TXT,
							iconCls : 'page_edit',
							tooltip : Btn_Cfgs.MODIFY_TIP_BTN_TXT,
							handler : function() {
								self.globalMgr.doApplyByOp(self,OPTION_TYPE.EDIT
									);
						
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
						},/*,{同步
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
							EventManager.enabledData('./fuBamountApply_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});							}
						}, {
							token : '禁用',
							text : Btn_Cfgs.DISABLED_BTN_TXT,
							iconCls : 'page_disabled',
							tooltip : Btn_Cfgs.DISABLED_TIP_BTN_TXT,
							handler : function() {
							EventManager.disabledData('./fuBamountApply_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});							}
						}, */{
							token : '删除',
							text : Btn_Cfgs.DELETE_BTN_TXT,
							iconCls : 'page_delete',
							tooltip : Btn_Cfgs.DELETE_TIP_BTN_TXT,
							handler : function() {
									EventManager.deleteData('./fuBamountApply_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});}
						},{
							token : '导出',
							text : Btn_Cfgs.EXPORT_BTN_TXT,
							iconCls : 'page_exportxls',
							tooltip : Btn_Cfgs.EXPORT_TIP_BTN_TXT,
							handler : function() {
								self.globalMgr.doExport(self);
							}
							
						}];
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
					header : '委托合同编号',
					name : 'code'
				}, {
					header : '委托人姓名',
					name : 'name'
				}, {
					header : '委托金额',
					name : 'appAmount',
					renderer: function(val) {
					    return (val && val>0) ? Cmw.getThousandths(val): '';}
				}, {
					header : '委托利率',
					name : 'rate',
					renderer:function(v,d,r){
						return v+Render_dataSource.rateUnit_datas(r.get("unint"));
					}
				}, {
					header : '委托单位',
					name : 'unint',
					renderer:Render_dataSource.rateUnit_datas,
					hidden:true
				},  {
					header : '委托生效日期',
					name : 'payDate'	
				}, {
					header : '委托失效日期',
					name : 'endDate'
				}, {
					header : '撤资金额',
					name : 'bamount',
					renderer: function(val) {
					    return (val && val>0) ? Cmw.getThousandths(val): '';}
				},  {
					header : '撤资类型',
					name : 'isNotExpiration',
					renderer: function(val) {
						switch (val) {
			        		case '-1' :
			          	 		val = "作废";
			          	 		break;
			        		case '0':
			          	 		val = "异常撤资";
			          	 		break;
			        		case '1':
			          	 		val = "正常撤资";
			          	 		break;
			       		 	}
			       			return val;
			   			 }
				}, {
					header : '撤资日期',
					name : 'backDate'
				},{
					header : '违约金额',
					name : 'wamount',
					renderer: function(val) {
					    return (val && val>0) ? Cmw.getThousandths(val): '';}
				}];
				var appgrid = new Ext.ux.grid.AppGrid({// 创建表格
					title : '委托人信息',
					tbar : this.toolBar,
					structure : structure_1,
					url : './fuBamountApply_listAll.action',
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
						var entrustCustId = _this.appgrid.getCmnVals(["entrustCustId"]);
						params = Ext.apply(params,entrustCustId);
						var tabId = CUSTTAB_ID.bamountApplay;
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
			var codeObj = _this.appgrid.getCmnVals("contractId");
			var codes = _this.appgrid.getCmnVals("code");
			var sysId = _this.params.sysid;
			var contractId = codeObj.contractId;
			 ExtUtil.confirm({title:'提示',msg:'确定提交编号为："'+codes.code+'"的增资申请单?',fn:function(){
		 	  		if(arguments && arguments[0] != 'yes') return;
					var params = {isnewInstance:true,sysId:sysId,applyId:applyId,contractId:contractId,procId:null,bussProccCode:'B6666'};
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
						var selId=parent.getSelId();
						parent.sysId = _this.globalMgr.sysId;
						parentCfg.parent = parent;
						parentCfg.nodeId = _this.params.nodeId;
						if (_this.appCmpts[winkey]) {
							_this.appCmpts[winkey].show(parentCfg);
						} else {
							var winModule = null;
							/*if (winkey == "添加") {
								winModule = "funds/entrustcust/EntrustCustAddEdit";
							} else if(winkey == "编辑"){
								if(!selId)return;
							winModule = "funds/entrustcust/EntrustCustAddEdit";
							}else*/ if (winkey == "详情") {
								winModule ="funds/bamount/BamountApplyDetil.js";
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
