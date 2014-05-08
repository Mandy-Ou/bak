Ext.namespace("cmw.skythink");
/**
 * @author 李听
 * 委托人管理
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
//				var txt_code = FormUtil.getLCboField({
//							 fieldLabel : '状态',
//							 name : 'status',
//							data : [["0", "待提交"], ["1", "审批中"], ["2", "审批通过"], ["3", "审批不通过"]]
//							 });
			var txt_Name = FormUtil.getTxtField({
						fieldLabel : '姓名',
						name : 'name'
					});
			var txt_startDate1 = FormUtil.getDateField({name:'startDate1',width:90});
			var txt_endDate1 = FormUtil.getDateField({name:'endDate1',width:90});
			var comp_appDate = FormUtil.getMyCompositeField({
				 itemNames : 'startDate1,endDate1',
				 sigins : null,
				 fieldLabel: '出生日期',width:210,sigins:null,
				 name:'comp_estartDate',
				 items : [txt_startDate1,{xtype:'displayfield',value:'至'},txt_endDate1]
				});
			var txt_phone = FormUtil.getTxtField({
							fieldLabel : '手机',
							name : 'phone'
						});
						
			var txt_contactTel = FormUtil.getTxtField({
							fieldLabel : '联系电话',
							name : 'contactTel'
						});
			var txt_inAddress = FormUtil.getTxtField({
							fieldLabel : '现居住地址',
							name : 'inAddress',
							width:'200'
						});
						
			var layout_fields = [{
						cmns : 'THREE',
						fields : [/*txt_code,*/ txt_Name,comp_appDate,txt_phone,txt_contactTel,txt_inAddress]
					}]
				var queryFrm = FormUtil.createLayoutFrm(null, layout_fields);
				return queryFrm;
			},
			/**
			 * 查询工具栏
			 */
			getToolBar : function() {
				var self = this;
				var barItems = [ {
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
						}, {
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
						}, {
							token : '编辑',
							text : Btn_Cfgs.MODIFY_BTN_TXT,
							iconCls : 'page_edit',
							tooltip : Btn_Cfgs.MODIFY_TIP_BTN_TXT,
							handler : function() {
									self.globalMgr.winEdit.show({
											key : "编辑",
											self : self,
											optionType : OPTION_TYPE.EDIT
										});
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
						}/*, {
							token : '提交',
							text : Btn_Cfgs.SUBMIT_BTN_TXT,
							iconCls : 'page_confirm',
							tooltip : Btn_Cfgs.SUBMIT_TIP_BTN_TXT,
							handler : function() {
//								self.globalMgr.winEdit.show({
//											key : "提交",
//											optionType : OPTION_TYPE.EDIT,
//											self : self
//										});
							}
						}*/,/*{同步
						token : '同步',
							text : Btn_Cfgs.SYNCHRONOUS_BTN_TXT,
							iconCls : Btn_Cfgs.SYNCHRONOUS_CLS,
							tooltip : Btn_Cfgs.SYNCHRONOUS_TIP_BTN_TXT,
							handler : function() {
//								self.globalMgr.synchronousData(self);
							}
						},*/ {
							type : "sp"
						}, {
							token : '启用',
							text : Btn_Cfgs.ENABLED_BTN_TXT,
							iconCls : 'page_enabled',
							tooltip : Btn_Cfgs.ENABLED_TIP_BTN_TXT,
							handler : function() {
							EventManager.enabledData('./fuEntrustCust_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});							}
						}, {
							token : '禁用',
							text : Btn_Cfgs.DISABLED_BTN_TXT,
							iconCls : 'page_disabled',
							tooltip : Btn_Cfgs.DISABLED_TIP_BTN_TXT,
							handler : function() {
							EventManager.disabledData('./fuEntrustCust_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});							}
						}, {
							token : '删除',
							text : Btn_Cfgs.DELETE_BTN_TXT,
							iconCls : 'page_delete',
							tooltip : Btn_Cfgs.DELETE_TIP_BTN_TXT,
							handler : function() {
									EventManager.deleteData('./fuEntrustCust_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});}
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
							header : '可用标志',
							name : 'isenabled',
							width:52,
							renderer : function(val) {
						return Render_dataSource.entrusIsenRender(val);
					}
						},/* {
							header : '状态',
							name : 'status',
							width:55,
					renderer : function(val) {
						return Render_dataSource.entrusStateRender(val);
					}
						},*/{
							header : '编号',
							name : 'code'
						}, {
							header : '姓名',
							name : 'name'
						},  {
							header : '委托人类型',
							name : 'ctype',
							width:75,
							renderer : function(val) {
							return Render_dataSource.eCusRender(val);
					}
						}, {
							header : '性别',
							name : 'sex',
							width:42,
							renderer : function(val) {
						return Render_dataSource.entrussexRender(val);
					}
						}, {
							header : '身份证号码',
							name : 'cardNum'
						}, {
							header : '出生日期',
							name : 'birthday'
						}, {
							header : '委托金额',
							name : 'appAmount',
							renderer: function(val) {
						    return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';}
						}, {
							header : '委托期限',
							name : 'deadline'/*,
							renderer:function(v,t,r){
								return v+Render_dataSource.entruestRender(r.get("dlimit"));
							}*/
						}, {
							header : 'dlimit',
							name : 'dlimit',
							hidden:true
						},{
							header : '委托产品',
							name : 'products'
						}, {
							header : '手机',
							name : 'phone'
						}, {
							header : '联系电话',
							name : 'contactTel'
						}, {
							header : '居住地址',
							name : 'inAddress'
						}, {
							header : '登记人',
							name : 'creator'
						}, {
							header : '登记日期',
							name : 'createTime'
						}];
				var appgrid = new Ext.ux.grid.AppGrid({// 创建表格
					title : '委托人信息',
					tbar : this.toolBar,
					structure : structure_1,
					url : './fuEntrustCust_list.action',
					needPage : true,
					keyField : 'id',
					isLoad : false,
					listeners : {
						render : function(grid) {
							_this.globalMgr.query(_this);
						}
					}
				});
			_this.globalMgr.appgrid = appgrid;
				return appgrid;
			},
			refresh : function(data) {// 刷新
				this.globalMgr.query(this);
				this.globalMgr.activeKey = null;
				_this.globalMgr.appgrid.reload();
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
				appgrid:null,
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
							if (winkey == "添加") {
							winModule = "funds/entrustcust/EntrustCustAddEdit";
							} else if(winkey == "编辑"){
							var selId=parent.getSelId();
							parent.selId = selId;
							if(!selId)return;
							winModule = "funds/entrustcust/EntrustCustAddEdit";
							}else if (winkey == "详情") {
								var selId=parent.getSelId();
								parent.selId = selId;
								if(!selId)return;
								winModule ="funds/entrustcust/EntrustCustDetil";
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
