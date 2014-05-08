/**
 * 汇票信息查询
 * @author 李静
 */
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 基础数据 UI smartplatform_auto 2012-08-10 09:48:16
 */
skythink.cmw.workflow.bussforms.ReceiptMsgAll = function() {
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.workflow.bussforms.ReceiptMsgAll,
		Ext.util.MyObservable, {
			initModule : function(tab, params) {
				this.module = new Cmw.app.widget.AbsGContainerView({
							querytitle : '汇票信息查询',// 重写主题
							tab : tab,
							params : params,
							hasTopSys : false,
							getQueryFrm : this.getQueryFrm,
							getToolBar : this.getToolBar,
							getAppGrid : this.getAppGrid,
							globalMgr : this.globalMgr,
							refresh : this.refresh,
							appCmpts : {}
						});
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
						},{
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
						},{
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
			globalMgr : {
				_appgrid : null,
				activeKey : null,
				appform : null,
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
							 if (winkey == "详情") {
								var selId=parent.getSelId();
								parent.selId = selId;
								if(!selId)return;
								winModule ="/funds/ReceiptMsg/ReceiptMsgDetil";
							}
							Cmw.importPackage('pages/app/' + winModule,
									function(module) {// 导入包的，有重构
										_this.appCmpts[winkey] = module.WinEdit;
										_this.appCmpts[winkey].show(parentCfg);
									});
						}
					}
				}
			},
			/**
			 * 获取查询Form 表单
			 */
			getQueryFrm : function() {
				var self = this;
				var wd = 175;
				// 票号
				var txt_rnum = FormUtil.getTxtField({
							fieldLabel : '票号',
							name : 'rnum'
						});
				// 供票人
				var txt_name = FormUtil.getTxtField({
							fieldLabel : '供票人',
							name : 'name'
						});
				// 业务员
				var txt_serviceMan = FormUtil.getTxtField({
							fieldLabel : '业务员',
							name : 'serviceMan'
						});
				/**
				 * 4.计算金额 txt_operational、txt_amountText、txt_amount
				 */
				var txt_operational = FormUtil.getEqOpLCbox({
							name : 'operational'
						});
				var txt_amountText = FormUtil.getTxtField({
							name : 'amount',
							width : 100
						});
				var txt_amount = FormUtil.getMyCompositeField({
							fieldLabel : '金额',
							sigins : null,
							name : 'amount',
							width : wd,
							items : [txt_operational, txt_amountText]
						});
				/**
				 * 5.计算时间 txt_outDate、txt_endDate、txt_outAndEndDate
				 */
				var txt_outDate = FormUtil.getDateField({
							name : 'outDate',
							width : 95
						});// 资金到账日期
				var txt_endDate = FormUtil.getDateField({
							name : 'endDate',
							width : 95
						});// 资金到账日期
				var txt_outAndEndDate = FormUtil.getMyCompositeField({
							itemNames : 'outDate,endDate',
							fieldLabel : '资金到账日期',
							name : 'fundsDate',
							sigins : null,
							width : 300,
							items : [txt_outDate, {
										xtype : 'displayfield',
										value : '至'
									}, txt_endDate]
						});
				// 6.收票人
				var txt_ticketMan = FormUtil.getTxtField({
							fieldLabel : '收票人',
							name : 'ticketMan'
						});

				var layout_fields = [{
					cmns : 'THREE',
					fields : [txt_rnum, txt_name, txt_serviceMan, txt_amount,
							txt_outAndEndDate, txt_ticketMan]
				}]

				var queryFrm = FormUtil.createLayoutFrm(null, layout_fields);
				return queryFrm;
			},
			/**
			 * 获取Grid 对象
			 */
			getAppGrid : function() {
				var self = this;
				var structure = [{
							header : '编号',
							name : 'code'
						}, {
							header : '票号',
							name : 'rnum'
						}, {
							header : '金额',
							name : 'amount',
							renderer: Render_dataSource.nomoneyRender
						}, {
							header : '利率',
							name : 'rate',
							renderer:Render_dataSource.rateRender
						}, {
							header : '收费',
							name : 'tiamount',
							renderer: Render_dataSource.nomoneyRender
						}, {
							header : '供票人',
							name : 'name'
						}, {
							header : '业务员',
							name : 'empName',
							value : CURENT_EMP
						}, {
							header : '贴现票日期',
							name : 'ticketDate'
						}, {
							header : '收票人',
							name : 'ticketMan'
						}, {
							header : '资金到账日期',
							name : 'fundsDate'
						}, {
							header : '贴现利息',
							name : 'ticketRate',
							renderer:Render_dataSource.rateRender
						}, {
							header : '到账金额',
							name : 'funds',
							renderer: Render_dataSource.nomoneyRender
						}, {
							header : '提成',
							name : 'adultMoney',
							renderer: Render_dataSource.nomoneyRender
						}, {
							header : '利润',
							name : 'interest',
							renderer: Render_dataSource.nomoneyRender
						}, {
							header : '备注',
							name : 'remark'
						}];
				var appgrid = new Ext.ux.grid.AppGrid({
							title : '',
							tbar : this.toolBar,
							structure : structure,
							url : './fuReceiptMsg_list.action',
							needPage : true,
							isLoad : true,
							keyField : 'id'
						});
				self.globalMgr._appgrid = appgrid;
				return appgrid;
			}			
		});