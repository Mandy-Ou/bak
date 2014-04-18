/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 自由资金 UI smartplatform_auto 2012-12-22 09:48:16
 */
cmw.skythink.OwnFundsMgr = function() {
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.OwnFundsMgr, Ext.util.MyObservable, {
			initModule : function(tab, params) {
				this.module = new Cmw.app.widget.AbsGContainerView({
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
			getQueryFrm : function() {
				var self = this;
				var txt_isenabled = FormUtil.getLCboField({
							fieldLabel : '可用标识',
							name : 'isenabled',
							data : Lcbo_dataSource
									.getAllDs(Lcbo_dataSource.isenabled_datas)
						});
				var txt_code = FormUtil.getTxtField({
							fieldLabel : '编号',
							name : 'code'
						});
				var txt_bankName = FormUtil.getTxtField({
							fieldLabel : '银行名称',
							name : 'bankName'
						});

				var layout_fields = [{
							cmns : 'THREE',
							fields : [txt_isenabled, txt_code, txt_bankName]
						}]

				var queryFrm = FormUtil.createLayoutFrm(null, layout_fields);

				return queryFrm;
			},
			/**
			 * 查询工具栏
			 */
			getToolBar : function() {
				var self = this;
				var barItems = [{
							token : '查询',
							text : Btn_Cfgs.QUERY_BTN_TXT,
							iconCls : 'page_query',
							tooltip : Btn_Cfgs.QUERY_TIP_BTN_TXT,
							handler : function() {
								self.globalMgr.query(self);
							}
						}, {
							token : '重置',
							text : Btn_Cfgs.RESET_BTN_TXT,
							iconCls : 'page_reset',
							tooltip : Btn_Cfgs.RESET_TIP_BTN_TXT,
							handler : function() {
								self.queryFrm.reset();
							}
						}, {
							type : "sp"
						}, {
							token : '设置账户金额',
							text : Btn_Cfgs.INSERT_BTN_TXT,
							iconCls : Btn_Cfgs.INSERT_CLS,
							tooltip : Btn_Cfgs.INSERT_TIP_BTN_TXT,
							handler : function() {
								self.globalMgr.winEdit.show({
											key : "添加",
											self : self
										});
							}
						}, {
							token : '编辑账户金额',
							text : Btn_Cfgs.MODIFY_BTN_TXT,
							iconCls : Btn_Cfgs.MODIFY_CLS,
							tooltip : Btn_Cfgs.MODIFY_TIP_BTN_TXT,
							handler : function() {
								var id = self.appgrid.getSelId();
								if (!id) {
									ExtUtil.alert({
												msg : "请选择表格中的数据！"
											});
									return;
								}
								self.globalMgr.winEdit.show({
											key : "编辑",
											optionType : OPTION_TYPE.EDIT,
											self : self
										});
							}
						}, {
							type : "sp"
						}, {
							token : '起用',
							text : Btn_Cfgs.ENABLED_BTN_TXT,
							iconCls : 'page_enabled',
							tooltip : Btn_Cfgs.ENABLED_TIP_BTN_TXT,
							handler : function() {
								EventManager.enabledData(
										'./fcOwnFunds_enabled.action', {
											type : 'grid',
											cmpt : self.appgrid,
											optionType : OPTION_TYPE.ENABLED,
											self : self
										});
							}
						}, {
							token : '禁用',
							text : Btn_Cfgs.DISABLED_BTN_TXT,
							iconCls : 'page_disabled',
							tooltip : Btn_Cfgs.DISABLED_TIP_BTN_TXT,
							handler : function() {
								EventManager.disabledData(
										'./fcOwnFunds_disabled.action', {
											type : 'grid',
											cmpt : self.appgrid,
											optionType : OPTION_TYPE.DISABLED,
											self : self
										});
							}
						}, {
							token : '删除',
							text : Btn_Cfgs.DELETE_BTN_TXT,
							iconCls : 'page_delete',
							tooltip : Btn_Cfgs.DELETE_TIP_BTN_TXT,
							handler : function() {
								EventManager.deleteData(
										'./fcOwnFunds_delete.action', {
											type : 'grid',
											cmpt : self.appgrid,
											optionType : OPTION_TYPE.DEL,
											self : self
										});
							}
						}, {
							token : '资金流水明细',
							text : Btn_Cfgs.FUNDSWATER_DATEIL_BIN_TXT,
							iconCls : "page_detail",
							tooltip : Btn_Cfgs.FUNDSWATER_DATEIL_TIP_BTN_TXT,
							handler : function() {
								var id = self.appgrid.getSelId();
								if (!id) {
									ExtUtil.alert({
												msg : "请选择表格中的数据！"
											});
									return;
								}
								self.globalMgr.winEdit.show({
											key : "资金流水明细",
											optionType : OPTION_TYPE.DETAIL,
											self : self
										});
							}
						}, {
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
							header : '可用标识',
							name : 'isenabled',
							width : 60,
							renderer : Render_dataSource.isenabledRender
						}, {
							header : '编号',
							name : 'code',
							width : 150
						}, {
							header : '银行帐号',
							name : 'account',
							width : 150
						}, {
							header : '银行名称',
							name : 'bankName',
							width : 150
						}, {
							header : '总金额',
							name : 'totalAmount',
							width : 150,
							renderer : function(val) {
								val = Cmw.getThousandths(val)
								return val
							}
						}, {
							header : '累计贷出金额',
							name : 'bamount',
							renderer : function(val) {
								val = Cmw.getThousandths(val)
								return val
							}
						}, {
							header : '剩余可用金额',
							name : 'uamount',
							width : 150,
							renderer : function(val) {
								val = Cmw.getThousandths(val)
								return val
							}
						}, {
							header : '放款账户',
							name : 'isPay',
							width : 60,
							renderer : Render_dataSource.isRequiredRender
						}, {
							header : '收款账户',
							name : 'isIncome',
							width : 60,
							renderer : Render_dataSource.isRequiredRender
						}, {
							header : '账户类型',
							name : 'atype',
							width : 125,
							renderer : Render_dataSource.account_atypeRender
						}];
				var appgrid_1 = new Ext.ux.grid.AppGrid({// 创建表格
					title : '自有资金设置',
					tbar : this.toolBar,
					structure : structure_1,
					url : './fcOwnFunds_list.action',
					needPage : true,
					keyField : 'id',
					isLoad : false,
					listeners : {
						render : function(grid) {
							_this.globalMgr.query(_this);
						}
					}
				});
				return appgrid_1;
			},
			refresh : function(optionType, data) {// 刷新
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
					if (_this.params
							&& _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY]) {
						var deskParams = _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY];
						if (deskParams) {
							Ext.applyIf(params, deskParams);
							_this.params[DESK_MOD_TAG_QUERYPARAMS_KEY] = null;
						}
					}
					return params;
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
				 * 
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
							if (winkey == "添加" || winkey == "编辑") {
								winModule = "finance/fcinit/ownfunds/OwnFundsEdit";
							} else if (winkey == "资金流水明细") {
								winModule = "finance/fcinit/ownfunds/FundsWaterDetail";
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
