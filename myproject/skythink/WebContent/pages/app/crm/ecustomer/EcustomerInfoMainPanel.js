/**
 * 菜单新增或修改页面
 * 
 * @author 程明卫
 * @date 2012-07-12
 * 企业客户资料管理
 */
define(function(require, exports) {
	exports.MainEdit = {
		parentCfg : null,
		parent : null,
		appMainPanel : null,
		tabPanel : null,
		uploadWin : null,
		baseFormPanel : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增 */
		ecustomerInfoId : null,
		detailPnlArray : [],
		formDatas : null,
		params : null,
		appgrid : null,
		sysId : null, /* 系统ID */
		ecustinfoAfId : null, /* 企业客户附件ID */
		tabIdMgr : {/* Tab Id 定义 */
			ecustinfoPnlId : Ext.id(null, 'ecustinfoPnlId'), /* 企业客户详细资料面板ID */
			legalManinfoPnlId : Ext.id(null, 'legalManinfoPnlId'),/* 企业法人资料面板ID */
			// efinancePnlId : Ext.id(null,'efinancePnlId'),/*企业财务状况详情面板ID*/
			ebankPnlId : Ext.id(null, 'ebankPnlId'),/* 企业开户详情面板ID */
			GuaCustomerPnlId : Ext.id(null, 'GuaCustomerPnlId'),/* 第三方担保人面板ID */
			eeqstructPnlId : Ext.id(null, 'eeqstructPnlId'),/* 股权结构详情面板ID */
			ebankBorrPnlId : Ext.id(null, 'ebankBorrPnlId'),/* 银行贷款详情面板ID */
			eownerBorrPnlId : Ext.id(null, 'eownerBorrPnlId'),/* 所有者贷款情况面板ID */
			eclasstInfoPnlId : Ext.id(null, 'eclasstInfoPnlId'),/* 领导班子面板ID */
			eassureInfoPnlId : Ext.id(null, 'eassureInfoPnlId'),/* 企业担保面板ID */
			eassureInfoPnlId : Ext.id(null, 'eassureInfoPnlId'),/* 企业担保面板ID */
			otherInfoPnlId : Ext.id(null, 'otherInfoPnlId')
			/* 其他信息面板ID */
		},
		formIdMgr : {/* Form Id 定义 */
			GuaCustomerFrmId : Ext.id(null, 'GuaCustomerFrmId'),/* 第三方担保人ID */
			ebankFormId : Ext.id(null, 'ebankFormId'),/* 企业开户详情面板中的form ID */
			eeqstructFormId : Ext.id(null, 'eeqstructFormId'),/*
																 * 股权结构详情面板中的form
																 * ID
																 */
			ebankBorrFormId : Ext.id(null, 'ebankBorrFormId'),/*
																 * 银行贷款详情面板中的form
																 * ID
																 */
			eownerBorrFormId : Ext.id(null, 'eownerBorrFormId'),/*
																 * 所有者贷款情况面板中的form
																 * ID
																 */
			eclasstInfoFormId : Ext.id(null, 'eclasstInfoFormId'),/*
																	 * 领导班子面板中的form
																	 * ID
																	 */
			eassureInfoFormId : Ext.id(null, 'eassureInfoFormId'),/*
																	 * 企业担保面板中的form
																	 * ID
																	 */
			otherInfoFrmId : Ext.id(null, 'otherInfoFrmId')
			/* 其他信息面板 Frm ID */
		},
		attachmentPnlIdMgr : {/* 附件Id 定义 */
			ecustinfoAfId : null, /* 企业客户附件ID */
			legalManinfoAfId : null,/* 企业法人附件ID */
			efinanceAfId : null,/* 企业财务状况附件ID */
			ebankAfId : null,/* 企业开户附件ID */
			eeqstructAfId : null,/* 股权结构附件ID */
			ebankBorrAfId : null,/* 银行贷款附件ID */
			eownerBorrAfId : null,/* 所有者贷款附件ID */
			eclasstInfoAfId : null,/* 领导班子附件ID */
			eassureInfoAfId : null,/* 企业担保附件ID */
			otherInfoaFPnl : null,/* 其他信息附件面板ID */
			GuaCustomerPnlId : null/*第三方担保人信息*/
			/* 第三方担保人信息 */
		},
		setParentCfg : function(parentCfg) {
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType;
			this.appgrid = parentCfg.appgrid;
			this.params = parentCfg.params;
			if (!this.params) {
				var msg = (this.optionType == OPTION_TYPE.ADD)
						? Msg_SysTip.msg_addCustomer_noparams
						: Msg_SysTip.msg_editCustomer_noparams;
				ExtUtil.alert({
							msg : msg
						});
				return false;
			}
			if (this.optionType == OPTION_TYPE.ADD) {
				this.formDatas = this.params.formDatas;
				if (!this.formDatas) {
					ExtUtil.alert({
								msg : "添加客户资料时，请提供   formDatas 数据值"
							});
					return false;
				}
			} else {
				this.ecustomerInfoId = this.params.id;
				if (!this.ecustomerInfoId) {
					ExtUtil.alert({
								msg : "编辑客户资料时，请提供   ecustomerInfoId 值"
							});
					return false;
				}
			}
			this.sysId = this.params.sysId;
			if (!this.sysId) {
				ExtUtil.alert({
							msg : "请提供   sysId 值"
						});
				return false;
			}
			return true;
		},
		hide : function() {
			/* step 1 : 重置表单数据，并禁用 */
			// if(this.appMainPanel){
			// this.appMainPanel.hide();
			// }
			this.appMainPanel.hide();
		},
		show : function() {
			/* step 1 : 重置表单数据，并禁用 */
			if (this.appMainPanel) {
				this.appMainPanel.show();
				this.appMainPanel.doLayout();
			}
		},
		resets : function() {
			this.baseFormPanel.reset();
			var length = this.detailPnlArray.length
			if (this.detailPnlArray.length > 0) {
				for (var i = 0; i < length; i++) {
					this.detailPnlArray[i].reload({
								formId : -1,
								custType : 1
							});
				}
			}
			// 企业客户
			var ecustinfoAppForm = Ext.getCmp(this.tabIdMgr.ecustinfoPnlId);
			if (ecustinfoAppForm) {
				ecustinfoAppForm.reset();
				if (this.optionType == OPTION_TYPE.EDIT) {
					this.setTabItemValue(ecustinfoAppForm);
				}
			}
			// 企业法人
			var legalManinAppForm = Ext.getCmp(this.tabIdMgr.legalManinfoPnlId);
			if (legalManinAppForm) {
				legalManinAppForm.reset();
				if (this.optionType == OPTION_TYPE.EDIT) {
					this.setTabItemValue(legalManinAppForm);
				}
			}
			// 企业开户
			var ebankAppGrid = Ext.getCmp(this.tabIdMgr.ebankPnlId);
			var ebankAppForm = Ext.getCmp(this.formIdMgr.ebankFormId);
			this.gridAndFrmRest(ebankAppGrid, ebankAppForm);
			// 第三方担保
			var GuaCustomerPnlId = Ext.getCmp(this.tabIdMgr.GuaCustomerPnlId);
			var GuaCustomerFrmId = Ext.getCmp(this.formIdMgr.GuaCustomerFrmId);
			this.gridAndFrmRest(GuaCustomerPnlId, GuaCustomerFrmId);
			// 股权结构
			var eeqstructAppGrid = Ext.getCmp(this.tabIdMgr.eeqstructPnlId);
			var eeqstructAppForm = Ext.getCmp(this.formIdMgr.eeqstructFormId);
			this.gridAndFrmRest(eeqstructAppGrid, eeqstructAppForm);
			// 银行贷款
			var ebankBorrAppGrid = Ext.getCmp(this.tabIdMgr.ebankBorrPnlId);
			var ebankBorrAppForm = Ext.getCmp(this.formIdMgr.ebankBorrFormId);
			this.gridAndFrmRest(ebankBorrAppGrid, ebankBorrAppForm);
			// 所有者贷款
			var eownerBorrAppGrid = Ext.getCmp(this.tabIdMgr.eownerBorrPnlId);
			var eownerBorrAppForm = Ext.getCmp(this.formIdMgr.eownerBorrFormId);
			this.gridAndFrmRest(eownerBorrAppGrid, eownerBorrAppForm);
			// 领导班子详情页面
			var eclasstInfoAppGrid = Ext.getCmp(this.tabIdMgr.eclasstInfoPnlId);
			var eclasstInfoAppForm = Ext
					.getCmp(this.formIdMgr.eclasstInfoFormId);
			this.gridAndFrmRest(eclasstInfoAppGrid, eclasstInfoAppForm);
			// 企业担保页面
			var eassureInfoAppGrid = Ext.getCmp(this.tabIdMgr.eassureInfoPnlId);
			var eassureInfoAppForm = Ext
					.getCmp(this.formIdMgr.eassureInfoFormId);
			this.gridAndFrmRest(eassureInfoAppGrid, eassureInfoAppForm);

			// 其它信息
			var otherInfoAppGrid = Ext.getCmp(this.tabIdMgr.otherInfoPnlId);
			var otherInfoAppFrm = Ext.getCmp(this.formIdMgr.otherInfoFrmId);
			this.gridAndFrmRest(otherInfoAppGrid, otherInfoAppFrm);

			var isDisabled = false;
			if (this.optionType == OPTION_TYPE.ADD) {
				isDisabled = true;
			}
			this.tabPanel.setDisabled(isDisabled);
		},
		/**
		 * 重置Frm
		 */
		gridAndFrmRest : function(AppGrid, AppFrm) {
			if (AppGrid && AppFrm) {
				AppGrid.removeAll();
				EventManager.frm_reset(AppFrm);
				FormUtil.disabledFrm(AppFrm);
				if (this.optionType == OPTION_TYPE.EDIT) {
					this.setTabItemValue(AppGrid);
				}
			}
		},
		setValues : function() {
			this.resets();
			if (this.optionType == OPTION_TYPE.ADD) { // 添加时赋值
				EventManager.frm_reset(this.baseFormPanel);
				this.baseFormPanel.setVs(this.formDatas);
				var regman = this.params.regman;
				var registerTime = this.params.registerTime;
				var fields = this.baseFormPanel
						.findFieldsByName("regman,registerTime");
				fields[0].setValue(regman);
				fields[1].setValue(registerTime);
			} else { // 修改时赋值
				var fields = this.baseFormPanel.setFieldValues(this.params);
				this.tabPanel.enable();
			}
		},
		/**
		 * 显示弹出窗口
		 * 
		 * @param _parentCfg
		 *            弹出之前传入的参数
		 */
		getAppMainPanel : function(_parentCfg) {
			if (_parentCfg) {
				if (!this.setParentCfg(_parentCfg))
					return;
			}
			if (!this.appMainPanel) {
				this.createAppMainPanel();
				this.createBaseFormPanel();
				this.appMainPanel.add(this.baseFormPanel);
				this.createTabPanel();
				this.appMainPanel.add(this.tabPanel);
			}
			this.setValues();
			return this.appMainPanel;
		},
		/**
		 * 创建主应用程序面板
		 */
		createAppMainPanel : function() {
			var el = this.parent.getEl();
			pw = el.getComputedWidth();
			ph = el.getComputedHeight();
			this.appMainPanel = new Ext.Panel({
						title : '企业客户基本信息编辑',
						width : pw,
						height : ph,
						border : false,
						autoScroll : true
					});
		},
		createBaseFormPanel : function() {
			var hid_baseId = FormUtil.getHidField({
						fieldLabel : '客户基础信息ID',
						name : 'baseId'
					});

			var hid_id = FormUtil.getHidField({
						fieldLabel : '个人客户ID',
						name : 'id'
					});

			var txt_code = FormUtil.getReadTxtField({
						fieldLabel : '客户编号',
						name : 'code',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 20
					});

			var txt_name = FormUtil.getTxtField({
						fieldLabel : '企业名称',
						name : 'name',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 100
					});

			var txt_serialNum = FormUtil.getReadTxtField({
						fieldLabel : '客户流水号',
						name : 'serialNum',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : "20"
					});

			var txt_tradNumber = FormUtil.getTxtField({
						fieldLabel : '营业执照号',
						name : 'tradNumber',
						"width" : 135,
						"allowBlank" : false
					});

			var txt_orgcode = FormUtil.getTxtField({
						fieldLabel : '组织机构代码',
						name : 'orgcode',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : "20"
					});

			var txt_regman = FormUtil.getReadTxtField({
						fieldLabel : '登记人',
						name : 'regman',
						"width" : 135
					});

			var hid_department = FormUtil.getHidField({
						fieldLabel : '部门',
						name : 'department',
						"width" : 135
					});

			var txt_registerTime = FormUtil.getDateField({
						fieldLabel : '登记日期',
						name : 'registerTime',
						readOnly : true,
						"width" : 135
					});
			var hid_cardType = FormUtil.getHidField({
						fieldLabel : '证件类型',
						name : 'cardType',
						"width" : 135
					});
			var layout_fields = [hid_baseId, hid_id, hid_department,
					hid_cardType, {
						cmns : FormUtil.CMN_TWO,
						fields : [txt_code, txt_name]
					}, {
						cmns : FormUtil.CMN_THREE,
						fields : [txt_serialNum, txt_tradNumber, txt_orgcode,
								txt_regman, txt_registerTime]
					}];

			var frm_cfg = {
				labelWidth : 90,
				url : './crmCustBase_save.action?cardType=' + 0,
				border : false,
				buttonAlign : 'center'
			};

			this.baseFormPanel = FormUtil.createLayoutFrm(frm_cfg,
					layout_fields);
			txt_name.setWidth(455);
			var _this = this;
			var btnSave = new Ext.Button({
				text : '保存基本资料',
				handler : function() {
					EventManager.frm_save(_this.baseFormPanel, {
								beforeMake : function(formData) {
									formData.custType = "1";
									formData.sysId = _this.sysId;
								},
								sfn : function(formData) {
									var baseId = formData.baseId;
									var id = formData.id;
									var fields = _this.baseFormPanel
											.findFieldsByName("baseId,id");
									fields[0].setValue(baseId);
									fields[1].setValue(id);
									if (_this.tabPanel.disabled) {
										_this.tabPanel.enable();
									}
									if (_this.appgrid) {
										_this.appgrid.reload();
									}
									if (_this.optionType == OPTION_TYPE.ADD) {
										var params = {
											formId : formData.id,
											formType : ATTACHMENT_FORMTYPE.FType_19,
											sysId : _this.params.sysId
										};
										_this.updateOrResetAttachment(
														_this.attachmentPnlIdMgr.ecustinfoAfId,
														params);
									}
								}
							});
				}
			});
			this.baseFormPanel.addButton(btnSave);
		},
		/**
		 * 创建主 TabPanel 对象
		 */
		createTabPanel : function() {
			var _this = this;
			this.tabPanel = new Ext.TabPanel({
						disabled : true,
						border : false,
						autoScroll : true,
						enableTabScroll : true,
						plugins : [Ext.plugin.DragDropTabs],
						items : [{
									title : '企业客户详细资料',
									itemId : this.tabIdMgr.ecustinfoPnlId,
									autoScroll : true
								}, {
									title : '企业法人资料',
									itemId : this.tabIdMgr.legalManinfoPnlId,
									autoScroll : true
								},
								// {title : '企业财务状况',itemId :
								// this.tabIdMgr.efinancePnlId,autoScroll:
								// true},
								{
									title : '第三方担保详情',
									itemId : this.tabIdMgr.GuaCustomerPnlId,
									autoScroll : true
								}, {
									title : '企业开户详情资料',
									itemId : this.tabIdMgr.ebankPnlId,
									autoScroll : true
								}, {
									title : '股权结构资料',
									itemId : this.tabIdMgr.eeqstructPnlId,
									autoScroll : true
								}, {
									title : '银行贷款详情资料',
									itemId : this.tabIdMgr.ebankBorrPnlId,
									autoScroll : true
								}, {
									title : '所有者贷款资料',
									itemId : this.tabIdMgr.eownerBorrPnlId,
									autoScroll : true
								}, {
									title : '领导班子资料',
									itemId : this.tabIdMgr.eclasstInfoPnlId,
									autoScroll : true
								}, {
									title : '企业担保资料',
									itemId : this.tabIdMgr.eassureInfoPnlId,
									autoScroll : true
								}, {
									title : '其他信息资料',
									itemId : this.tabIdMgr.otherInfoPnlId,
									autoScroll : true
								}]
					});
			this.tabPanel.addListener("render", function(tabPanel) {
						tabPanel.setActiveTab(0);
						var wh = _this.getWH();
						var width = wh[0];
						var height = wh[1];
						tabPanel.setWidth(width);
						tabPanel.setHeight(height);
						tabPanel.doLayout();
					});
			this.tabPanel.addListener('tabchange', function(tabPanel, tab) {
						var itemId = tab["itemId"];
						var panel = Ext.getCmp(itemId);
						if (!panel) {
							var wh = _this.getWH();
							var width = wh[0];
							var height = wh[1];
							var tabItem = _this.getTabItem(tab, itemId, width,
									height);
							if (!tabItem) {
								var tabTitle = tab["title"];
								ExtUtil.error({
											msg : '"' + tabTitle + '"中没有组件可加载!'
										});
								return;
							}
							tab.add(tabItem);
							tab.doLayout();
						}
						_this.setTabItemValue(panel);
					}, this);
		},

		/**
		 * 为TabItem 面板对象赋值
		 */
		setTabItemValue : function(panel) {
			if (!panel)
				return;
			var itemId = panel.id;
			var xtype = panel.getXType();
			this.loadItemValueByXtype(panel, xtype, itemId);
		},
		loadItemValueByXtype : function(panel, xtype, itemId) {
			var _this = this;
			var params = {
				ecustomerId : this.params.id,
				baseId : this.params.baseId,
				custType : 1
			};
			if (xtype == 'appform') {// FormPanel 加载数据
				if (this.optionType == OPTION_TYPE.EDIT) {
					switch (itemId) {
						case this.tabIdMgr.ecustinfoPnlId : {
							var id = panel.getValueByName('id');
							if (id != this.params.id) {
								url = './crmEcustomer_get.action';
							} else {
								return;
							}
							break;
						}
						case this.tabIdMgr.legalManinfoPnlId : {
							if (this.params.id) {
								var id = panel.getValueByName('id');
								if (!id) {
									url = './crmCustomerInfo_getlegalMan.action';
								} else {
									return
								}
							} else {
								return;
							}
							break;
						}
					}
					panel.reset();
					params = {
						id : params.ecustomerId
					};
					panel.setValues(url, {
						params : params,
						sfn : function(formData) {
							if (itemId == _this.tabIdMgr.ecustinfoPnlId) {
								if (formData.id) {
									var Loadparams = {
										formId : formData.id,
										formType : ATTACHMENT_FORMTYPE.FType_19,
										sysId : _this.params.sysId,
										custType : 1
									};
									var ecustinfoAf = _this.attachmentPnlIdMgr.ecustinfoAfId;
									ecustinfoAf.reload(Loadparams);
								}
							} else {
								if (formData.id) {
									var Loadparams = {
										formId : formData.id,
										formType : ATTACHMENT_FORMTYPE.FType_20,
										sysId : _this.params.sysId,
										custType : 1
									};
									_this.attachmentPnlIdMgr.legalManinfoAfId.reload(Loadparams);
								}
							}
						}
					});
				} else {
					return;
				}
			} else {// Grid 加载数据
				if (panel == Ext.getCmp(_this.tabIdMgr.otherInfoPnlId)) {
					params.custType = 1;
					params.customerId = _this.params.id;
				}
				var count = panel.getStore().getCount();// 判断grid 中是否有数据
				if (count == 0 && this.optionType != OPTION_TYPE.ADD) {
					panel.reload(params);
				} else if (count || this.optionType == OPTION_TYPE.EDIT) {
					var customerId = panel.getStore().getAt(0).get("customerId");
					if (params.customerId != customerId) {
						panel.removeAll();
					} else {
						return;
					}
					panel.reload(params);
				} else {
					panel.removeAll();
				}
			}
		},

		getWH : function() {
			var wd = this.appMainPanel.getWidth();
			var hg = this.appMainPanel.getHeight();
			return [wd, hg];
		},
		getTabItem : function(tab, itemId, width, height) {
			var tatItem = null;
			switch (itemId) {
				case this.tabIdMgr.ecustinfoPnlId : { /* 企业客户详细资料 */
					tatItem = this.createEcustinfoPnlId(tab, itemId, width,
							height);
					break;
				}
				case this.tabIdMgr.legalManinfoPnlId : { /* 公司法人资料 */
					tatItem = this.createLegalManinfoPnlId(tab, itemId, width,
							height);
					break;
				}
					// case this.tabIdMgr.efinancePnlId :{ /*企业财务状况*/
					// tatItem =
					// this.createefinancePnlId(tab,itemId,width,height);
					// break;
					// }
				case this.tabIdMgr.GuaCustomerPnlId : { /* 第三方担保详情 */
					tatItem = this.GuaCustomerPnlId(tab, itemId, width, height);
					break;
				}
				case this.tabIdMgr.ebankPnlId : { /* 企业开户详情资料 */
					tatItem = this.createebankPnlId(tab, itemId, width, height);
					break;
				}
				case this.tabIdMgr.eeqstructPnlId : { /* 股权结构资料 */
					tatItem = this.createeeqstructPnlId(tab, itemId, width,
							height);
					break;
				}
				case this.tabIdMgr.ebankBorrPnlId : { /* 银行贷款资料 */
					tatItem = this.createebankBorrPnlId(tab, itemId, width,
							height);
					break;
				}
				case this.tabIdMgr.eownerBorrPnlId : { /* 所有者贷款资料 */
					tatItem = this.createeownerBorrPnlId(tab, itemId, width,
							height);
					break;
				}
				case this.tabIdMgr.eclasstInfoPnlId : { /* 领导班子用资料 */
					tatItem = this.createeclasstInfoPnlId(tab, itemId, width,
							height);
					break;
				}
				case this.tabIdMgr.eassureInfoPnlId : { /* 企业担保用资料 */
					tatItem = this.createeassureInfoPnlId(tab, itemId, width,
							height);
					break;
				}
				case this.tabIdMgr.otherInfoPnlId : { /* 企业担保用资料 */
					tatItem = this.creatotherInfoPnlId(tab, itemId, width,
							height);
					break;
				}
			}
			return tatItem;
		},
		/**
		 * 重置按钮方法
		 */
		BtnReset : function(panel, txtFiels) {
			if (this.optionType == OPTION_TYPE.EDIT) {
				EventManager.frm_reset(panel, txtFiels);
			} else {
				EventManager.frm_reset(panel);
			}
		},

		/**
		 * 创建toolbar
		 */
		createToolBar : function(appform_1, appgrid_1, entity) {
			var self = this;
			var barItems = [{
				text : '保存',
				iconCls : 'page_save',
				tooltip : '保存',
				handler : function() {
					if (entity == "OtherInfo") {
						var otherName = appform_1.getValueByName("otherName");
						if (otherName == null || otherName == "") {
							ExtUtil.alert({
										msg : "请添加数据后进行保存！"
									});
							return;
						}
					}
					var id = self.baseFormPanel.getValueByName('id');
					var baseId = self.baseFormPanel.getValueByName('baseId');
					EventManager.frm_save(appform_1, {
						beforeMake : function(formData) {
//								formData.id = id;
								formData.baseId = baseId;
								formData.custType = 1;
							if (entity == "OtherInfo") {
								formData.formType = ATTACHMENT_FORMTYPE.FType_36;
							}
							if(entity=="GuaCustomer"){
								var formId = self.formIdMgr.GuaCustomerFrmId;
								var GuaCustomerFrmPnl = Ext.getCmp(formId);
								var guaId = GuaCustomerFrmPnl.getValueByName("id");
								formData.id = guaId;
							}
						},
						sfn : function(formData) {
							var ecustomerId = self.baseFormPanel.getValueByName('id');
							if (entity == "OtherInfo") {
								appgrid_1.reload({
											baseId : baseId,
											id:id,
											custType : 1
										});
							} else {
								appgrid_1.reload({
											custType : 1,baseId:baseId
										});
							}
							var Fields = appform_1.findFieldsByName("baseId");
							EventManager.frm_reset(appform_1, Fields);
							self.LoadAf(entity, formData, true, false);
							var datas = {
								id : Cmw.getUuid()
							};
							self.LoadAf(entity, datas, false, true);
						}
					});
				}
			}, {
				text : '添加',
				iconCls : 'page_add',
				tooltip : '添加',
				handler : function() {
					EventManager.frm_reset(appform_1);
					FormUtil.enableFrm(appform_1);
					var formData = {
						id : Cmw.getUuid()
					}
					self.LoadAf(entity, formData, false, true);
				}
			}, {
				text : '修改',
				iconCls : 'page_edit',
				tooltip : '修改',
				handler : function() {
					var id = appgrid_1.getSelId();
					if (id != null) {
						FormUtil.enableFrm(appform_1);
					} else {
						ExtUtil.alert({
									msg : " 请选择表格中的数据"
								});
					}
					self.LoadAf(entity, {id : id});
				}
			}, {
				text : Btn_Cfgs.RESET_BTN_TXT,
				iconCls : Btn_Cfgs.RESET_CLS,
				handler : function() {
					var Fields = appform_1.findFieldsByName("id,ecustomerId");
					EventManager.frm_reset(appform_1, Fields);
				}
			}, {
				text : '删除',
				iconCls : 'page_delete',
				tooltip : '删除',
				handler : function() {
					var baseId = self.baseFormPanel.getValueByName('baseId');
					EventManager.deleteData(
							'./crm' + entity + '_delete.action', {
								type : 'grid',
								cmpt : appgrid_1,
								params :{custType : 1,baseId : baseId}
							});
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : barItems
					});
			return toolBar;
		},
		/**
		 * 在点击修改或者是点击保存之后要进行一次加载附件
		 */
		LoadAf : function(entity, formData, updateTempFormId, cleanAll) {
			var formType = null;
			var _this = this;
			var formItemd = this.formIdMgr;
			var attPnl = null;
			var formId = null;
			if (entity == "Ebank") {
				attPnl = _this.attachmentPnlIdMgr.ebankAfId;
				formId = formItemd.ebankFormId;
				formType = ATTACHMENT_FORMTYPE.FType_22;
			}
			if (entity == "Eeqstruct") {
				attPnl = _this.attachmentPnlIdMgr.eeqstructAfId;
				formId = formItemd.eeqstructFormId;
				formType = ATTACHMENT_FORMTYPE.FType_23;
			}
			if (entity == "EbankBorr") {
				attPnl = _this.attachmentPnlIdMgr.ebankBorrAfId;
				formId = formItemd.ebankBorrFormId;
				formType = ATTACHMENT_FORMTYPE.FType_24;
			}
			if (entity == "EownerBorr") {
				attPnl = _this.attachmentPnlIdMgr.eownerBorrAfId;
				formId = formItemd.eownerBorrFormId;
				formType = ATTACHMENT_FORMTYPE.FType_25;
			}
			if (entity == "Eclass") {
				attPnl = _this.attachmentPnlIdMgr.eclasstInfoAfId;
				formId = formItemd.eclasstInfoFormId;
				formType = ATTACHMENT_FORMTYPE.FType_26;
			}
			if (entity == "GuaCustomer") {
				formId = formItemd.GuaCustomerFrmId;
				attPnl = _this.attachmentPnlIdMgr.GuaCustomerPnlId;
				formType = ATTACHMENT_FORMTYPE.FType_35;
			}
			if (entity == "Eassure") {
				attPnl = _this.attachmentPnlIdMgr.eassureInfoAfId;
				formId = formItemd.eassureInfoFormId;
				formType = ATTACHMENT_FORMTYPE.FType_27;
			}
			if (entity == "OtherInfo") {
				attPnl = _this.attachmentPnlIdMgr.otherInfoaFPnl;
				formId = formItemd.otherInfoFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_29;
			}
			var appform = Ext.getCmp(formId);
			var params = {
				formId : formData.id,
				formType : formType,
				sysId : _this.params.sysId
			};
			if (appform) {
				if (cleanAll) {
					attPnl.cleanAttachAndLoad(params, true);
					return;
				}
				if (updateTempFormId) {
					attPnl.updateTempFormId(params, true);
					return;
				}

				appform.setValues('./crm' + entity + '_get.action', {
							params : {
								id : formData.id
							},
							sfn : function(json_Data) {
								params.formId = json_Data.id;
								attPnl.reload(params);
							}
						});
			}
		},
		/**
		 * 组装appGrid
		 */
		cmptAppGrid : function(entity, itemId, structure) {
			var self = this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
						id : itemId,
						structure : structure,
						url : './crm' + entity + '_list.action',
						height : 135,
						needPage : false,
						border : false,
						isLoad : false,
						keyField : 'id'
					});
			appgrid_1.addListener("afterrender", function() {
						var panel = Ext.getCmp(itemId);
						self.setTabItemValue(panel);
					});
			appgrid_1.addListener("rowclick", function(appgrid_1, rowIndex,
							event) {
						var data = {
							id : null
						};
						var rowId = appgrid_1.getSelId();
						if (rowId) {
							data.id = rowId;
						} else {
							return;
						}
						self.LoadAf(entity, data);
					});
			return appgrid_1;
		},
		/**
		 * 组装appForm
		 */
		cmptAppForm : function(entity, layout_fields, width, height,
				formDiyContainer) {
			var _this = this;
			var formItemd = this.formIdMgr;
			var formId = null;
			var formType = null;
			var formdiyCode = null;
			if (entity == "Ebank") {
				formId = formItemd.ebankFormId
				formType = ATTACHMENT_FORMTYPE.FType_22;
				formdiyCode = FORMDIY_IND.FORMDIY_EBANK;
			}
			if (entity == "Eeqstruct") {
				formId = formItemd.eeqstructFormId;
				formType = ATTACHMENT_FORMTYPE.FType_23;
				formdiyCode = FORMDIY_IND.FORMDIY_EEQSTRUCT;
			}
			if (entity == "EbankBorr") {
				formId = formItemd.ebankFormId;
				formType = ATTACHMENT_FORMTYPE.FType_24;
				formdiyCode = FORMDIY_IND.FORMDIY_EBANKBORR;
			}
			if (entity == "GuaCustomer") {
				formId = formItemd.GuaCustomerFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_36;
				formdiyCode = FORMDIY_IND.FORMDIY_CUST;
			}
			if (entity == "EownerBorr") {
				formId = formItemd.eownerBorrFormId;
				formType = ATTACHMENT_FORMTYPE.FType_25;
				formdiyCode = FORMDIY_IND.FORMDIY_EOWNERBORR;
			}
			if (entity == "Eclass") {
				formId = formItemd.eclasstInfoFormId;
				formType = ATTACHMENT_FORMTYPE.FType_26;
				formdiyCode = FORMDIY_IND.FORMDIY_ECLASS;
			}
			if (entity == "Eassure") {
				formId = formItemd.eassureInfoFormId;
				formType = ATTACHMENT_FORMTYPE.FType_27;
				formdiyCode = FORMDIY_IND.FORMDIY_EASSURE;
			}
			if (entity == "OtherInfo") {
				formId = formItemd.otherInfoFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_29;
				formdiyCode = FORMDIY_IND.FORMDIY_ECUSROMER_OTHERINFO;
			}
			var frm_cfg = {
				id : formId,
				width : width,
				height : height,
				labelWidth : 120,
				autoScroll : true,
				border : false,
				formDiyCfg : {
					sysId : _this.sysId,/* 系统ID */
					formdiyCode : formdiyCode,/*
												 * 引用Code --> 对应 ts_Formdiy
												 * 中的业务引用键：recode
												 */
					formIdName : 'id',/* 对应表单中的ID Hidden 值 */
					container : formDiyContainer
					/* 自定义字段存放容器 */
				},
				url : './crm' + entity + '_save.action'
			};

			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			appform_1.addListener("render", function() {
						FormUtil.disabledFrm(appform_1);
					});

			var dir = 'ecustomerinfo_path';
			var attachMentFs = this.createAttachMentFs(this, dir);
			this.detailPnlArray.push(attachMentFs);
			if (entity == "Ebank") {
				_this.attachmentPnlIdMgr.ebankAfId = attachMentFs;
			}
			if (entity == "Eeqstruct") {
				_this.attachmentPnlIdMgr.eeqstructAfId = attachMentFs;
			}
			if (entity == "EbankBorr") {
				_this.attachmentPnlIdMgr.ebankBorrAfId = attachMentFs;
			}
			if (entity == "EownerBorr") {
				_this.attachmentPnlIdMgr.eownerBorrAfId = attachMentFs;
			}
			if (entity == "Eclass") {
				_this.attachmentPnlIdMgr.eclasstInfoAfId = attachMentFs;
			}
			if (entity == "Eassure") {
				_this.attachmentPnlIdMgr.eassureInfoAfId = attachMentFs;
			}
			if (entity == "OtherInfo") {
				_this.attachmentPnlIdMgr.otherInfoaFPnl = attachMentFs;
			}
			if (entity == "GuaCustomer") {
				_this.attachmentPnlIdMgr.GuaCustomerPnlId = attachMentFs;
			}
			appform_1.add(attachMentFs);
			return appform_1;
		},

		/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
		createAttachMentFs : function(_this, dir, params) {
			/*
			 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录， mortgage_path
			 * 定义在 resource.properties 资源文件中 isSave : 附件上传后，是否保存到 ts_attachment
			 * 表中。 true : 保存 params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中
			 * ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
			 * params 可在 reload 方法中提供 ----
			 */
			var cfg = {
				title : '相关材料附件',
				isLoad : true,
				dir : dir,
				isSave : true
			};
			if (!params) {
				var uuid = Cmw.getUuid();
				params = {
					formType : -1,
					formId : uuid,
					sysId : _this.params.sysId
				};
			}
			cfg.params = params;
			var attachMentFs = new Ext.ux.AppAttachmentFs(cfg);
			return attachMentFs;
		},
		/**
		 * 更新或重置附件
		 * 
		 * @param attachmentFS
		 *            附件FieldSet 对象
		 * @param params
		 *            附件参数,如果不提供此参数，则为重置的临时附件， 即此方法为默认加上以下参数：{formType:-1,formId :
		 *            uuid,sysId:this.params.sysId}
		 */
		updateOrResetAttachment : function(attachmentFS, params) {
			if (params) {/* 如果 opFlag : true ,代表更新附件 */
				attachmentFS.updateTempFormId(params);
			} else {/* 重置附件 */
				var uuid = Cmw.getUuid();
				attachmentFS.params = {
					formType : -1,
					formId : uuid,
					sysId : this.params.sysId
				};
			}
		},
		/**
		 * 企业客户详细资料
		 */
		createEcustinfoPnlId : function(tab, itemId, width, height) {
			var txt_baseId = FormUtil.getHidField({
						fieldLabel : '客户基础信息ID',
						name : 'baseId',
						"width" : 135
					});
			var txt_id = FormUtil.getHidField({
						fieldLabel : '客户ID',
						name : 'id',
						"width" : 135
					});

			var txt_serialNum = FormUtil.getHidField({
						fieldLabel : '流水号',
						name : 'serialNum',
						"width" : 135,
						"maxLength" : "20"
					});

			var txt_shortName = FormUtil.getTxtField({
						fieldLabel : '企业简称',
						name : 'shortName',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : "50"
					});

			var txt_kind = FormUtil.getRCboField({
						fieldLabel : '企业性质',
						name : 'kind',
						"allowBlank" : false,
						"width" : 135,
						register : REGISTER.GvlistDatas,
						restypeId : '100009'
					});

			var txt_trade = FormUtil.getRCboField({
						fieldLabel : '所属行业',
						name : 'trade',
						"allowBlank" : false,
						"width" : 135,
						register : REGISTER.GvlistDatas,
						restypeId : '100011'
					});

			var txt_inAddress = FormUtil.getTxtField({
						fieldLabel : '街道地址',
						name : 'inAddress',
						"width" : 240,
						"allowBlank" : false,
						"maxLength" : "100"
					});

			var txt_address = new Ext.ux.tree.AppComboxTree({
						fieldLabel : '公司地区',
						name : 'address',
						"allowBlank" : false,
						url : './sysTree_arealist.action',
						disabled : false,
						isAll : true,
						width : 160,
						height : 350,
						hideBtnOk : true,
						isNeedDbClick : false
					});
			txt_address.comboxTree(txt_address);

			var bdat_comeTime = FormUtil.getDateField({
						fieldLabel : '成立时间',
						name : 'comeTime',
						"allowBlank" : false,
						"width" : 135
					});

			var sit_address = FormUtil.getMyCompositeField({
						fieldLabel : '公司地区',
						sigins : null,
						height : 25,
						width : 700,
						itemNames : "address,inAddress",
						name : 'comp_inArea',
						items : [txt_address, {
									xtype : 'displayfield',
									value : '街道地址',
									width : 50
								}, txt_inAddress]
					});

			var txt_mnemonic = FormUtil.getTxtField({
						fieldLabel : '拼音助记码',
						name : 'mnemonic',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : "20"
					});

			var txt_tradNumber = FormUtil.getTxtField({
						fieldLabel : '营业执照号',
						name : 'tradNumber',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : "50"
					});

			var txt_orgcode = FormUtil.getTxtField({
						fieldLabel : '组织机构代码',
						name : 'orgcode',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : "190"
					});

			var txt_contactor = FormUtil.getTxtField({
						fieldLabel : '联系人',
						name : 'contactor',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : "190"
					});

			var txt_phone = FormUtil.getTxtField({
						fieldLabel : '联系人手机',
						name : 'phone',
						"width" : 135,
						vtype : 'mobile',
						"maxLength" : "190"
					});

			var txt_contacttel = FormUtil.getTxtField({
						fieldLabel : '联系电话',
						name : 'contacttel',
						vtype : 'telephone',
						"width" : 135,
						"maxLength" : "20"
					});

			var txt_fax = FormUtil.getTxtField({
						fieldLabel : '传真',
						name : 'fax',
						"width" : 135,
						"maxLength" : "20"
					});

			var txt_email = FormUtil.getTxtField({
						fieldLabel : '电子邮件',
						name : 'email',
						vtype : 'email',
						"width" : 135,
						"maxLength" : "30"
					});

			var txt_zipCode = FormUtil.getTxtField({
						fieldLabel : '邮编',
						name : 'zipCode',
						vtype : 'postcode',
						"width" : 135,
						"maxLength" : "10"
					});

			var fset_EcontactInfo = FormUtil.createLayoutFieldSet({
						title : '企业联系信息',
						width : 820
					}, [{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_contactor, txt_contacttel, txt_phone,
								txt_email, txt_fax, txt_zipCode]
					}]);
			var txt_legalMan = FormUtil.getTxtField({
						fieldLabel : '法定代表人',
						name : 'legalMan',
						"width" : 135,
						"maxLength" : "30"
					});

			var txt_legalIdCard = FormUtil.getTxtField({
						fieldLabel : '法人身份证',
						name : 'legalIdCard',
						vtype : 'IDCard',
						"width" : 135,
						"maxLength" : "20"
					});

			var txt_legalTel = FormUtil.getTxtField({
						fieldLabel : '法人联系电话',
						name : 'legalTel',
						vtype : 'telephone',
						"width" : 135,
						"maxLength" : "20"
					});

			var txt_managerName = FormUtil.getTxtField({
						fieldLabel : '总经理',
						name : 'managerName',
						"width" : 135,
						"maxLength" : "30"
					});

			var txt_managerIdCard = FormUtil.getTxtField({
						fieldLabel : '总经理身份证',
						name : 'managerIdCard',
						vtype : 'IDCard',
						"width" : 135,
						"maxLength" : "20"
					});

			var txt_managerTel = FormUtil.getTxtField({
						fieldLabel : '总经理联系电话',
						name : 'managerTel',
						vtype : 'telephone',
						"width" : 135,
						"maxLength" : "20"
					});

			var txt_finaManager = FormUtil.getTxtField({
						fieldLabel : '财务经理',
						name : 'finaManager',
						"width" : 135,
						"maxLength" : "30"
					});

			var txt_finaIdCard = FormUtil.getTxtField({
						fieldLabel : '财务经理身份证',
						name : 'finaIdCard',
						vtype : 'IDCard',
						"width" : 135,
						"maxLength" : "20"
					});

			var txt_finaTel = FormUtil.getTxtField({
						fieldLabel : '财务经理联系电话',
						name : 'finaTel',
						"width" : 135,
						vtype : 'telephone',
						"maxLength" : "20"
					});

			var fset_EmianInfo = FormUtil.createLayoutFieldSet({
						title : '企业职业主要领导人信息',
						width : 820
					}, [{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_legalMan, txt_legalIdCard, txt_legalTel,
								txt_managerName, txt_managerIdCard,
								txt_managerTel, txt_finaManager,
								txt_finaIdCard, txt_finaTel]
					}]);

			var txt_url = FormUtil.getTxtField({
						fieldLabel : '网址',
						name : 'url',
						vtype : 'url',
						"width" : 170,
						"maxLength" : "100"
					});

			var txt_taxNumber = FormUtil.getTxtField({
						fieldLabel : '国税登记号',
						name : 'taxNumber',
						"width" : 135,
						"maxLength" : "30"
					});

			var txt_areaNumber = FormUtil.getTxtField({
						fieldLabel : '地税登记号',
						name : 'areaNumber',
						"width" : 135,
						"maxLength" : "30"
					});

			var txt_licence = FormUtil.getTxtField({
						fieldLabel : '经营许可证',
						name : 'licence',
						"width" : 135,
						"maxLength" : "30"
					});

			var bdat_licencedate = FormUtil.getDateField({
						fieldLabel : '许可证颁发时间',
						name : 'licencedate',
						"width" : 135
					});

			var txt_regaddress = FormUtil.getTxtField({
						fieldLabel : '注册地址',
						name : 'regaddress',
						"width" : 135,
						"maxLength" : "100"
					});

			var txt_regcapital = FormUtil.getMoneyField({
						fieldLabel : '注册资本',
						name : 'regcapital',
						value : 0.00,
						"width" : 145,
						unitText : '元'
					});

			var rcbo_currency = FormUtil.getRCboField({
						fieldLabel : '注册币种',
						name : 'currency',
						"width" : 135,
						"maxLength" : 50,
						register : REGISTER.GvlistDatas,
						restypeId : '100014'
					});

			var txt_incapital = FormUtil.getMoneyField({
						fieldLabel : '实收资本',
						name : 'incapital',
						value : 0.00,
						"width" : 145,
						unitText : '元'
					});

			var txt_patentNumber = FormUtil.getTxtField({
						fieldLabel : '专利证书号码',
						name : 'patentNumber',
						"width" : 135,
						"maxLength" : "30"
					});

			var txt_rent = FormUtil.getMoneyField({
						fieldLabel : '月租金',
						name : 'rent',
						"width" : 145,
						value : 0.00,
						unitText : '元'
					});

			var txt_loanBank = FormUtil.getTxtField({
						fieldLabel : '贷款银行',
						name : 'loanBank',
						"width" : 135,
						"maxLength" : "100"
					});

			var txt_loanNumber = FormUtil.getTxtField({
						fieldLabel : '贷款卡号',
						name : 'loanNumber',
						"width" : 135,
						"maxLength" : "30"
					});

			var txt_loanLog = FormUtil.getTxtField({
						fieldLabel : '贷款卡记录',
						name : 'loanLog',
						"width" : 135,
						"maxLength" : "10"
					});

			var int_manamount = FormUtil.getIntegerField({
						fieldLabel : '企业人数',
						name : 'manamount',
						"width" : 145,
						"maxLength" : 10,
						value : 0,
						unitText : '人'
					});

			var int_empCount = FormUtil.getIntegerField({
						fieldLabel : '在职员工人数',
						name : 'empCount',
						"width" : 145,
						"maxLength" : 10,
						value : 0,
						unitText : '人'
					});

			var int_insCount = FormUtil.getIntegerField({
						fieldLabel : '社保参保人数',
						name : 'insCount',
						"width" : 145,
						"maxLength" : 10,
						value : 0,
						unitText : '人'
					});

			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 780,
						height : 50
					});
			var _this = this;
			var btnSave = new Ext.Button({
				text : '保存',
				handler : function() {
					EventManager.frm_save(_this.appform_1, {
								beforeMake : function(formData) {
									var id = _this.baseFormPanel
											.getValueByName('id');
									var baseId = _this.baseFormPanel
											.getValueByName('baseId');
									var serialNum = _this.baseFormPanel
											.getValueByName('serialNum');
									formData.id = id;
									formData.baseId = baseId;
									formData.serialNum = serialNum;
								},
								sfn : function(formData) {
									if (_this.appgrid) {
										_this.appgrid.reload();
									}
									var params = {
										formId : formData.id,
										formType : ATTACHMENT_FORMTYPE.FType_19,
										sysId : _this.params.sysId
									};
									_this
											.updateOrResetAttachment(
													_this.attachmentPnlIdMgr.ecustinfoAfId,
													params);
								}
							});
				}
			});
			var btnReset = new Ext.Button({
						text : '重置',
						handler : function() {
							_this.BtnReset(_this.appform_1,
									[txt_baseId, txt_id]);
						}
					});

			var dir = 'ecustomerinfo_path';
			this.attachmentPnlIdMgr.ecustinfoAfId = this.createAttachMentFs(
					this, dir);
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var btnPanel = this.getBtnPanel([btnSave, btnReset]);
			var layout_fields = [
					txt_baseId,
					txt_id,
					txt_serialNum,
					{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_shortName, txt_kind, txt_trade]
					},
					{
						cmns : FormUtil.CMN_TWO_LEFT,
						fields : [sit_address, bdat_comeTime]
					},
					fset_EcontactInfo,
					fset_EmianInfo,
					// {cmns:
					// FormUtil.CMN_TWO_LEFT,fields:[txt_url,txt_taxNumber]},
					{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_taxNumber, txt_areaNumber, txt_licence,
								bdat_licencedate, txt_regaddress,
								txt_regcapital, rcbo_currency, txt_incapital,
								txt_patentNumber, txt_rent, txt_loanBank,
								txt_loanNumber, txt_loanLog, int_manamount,
								int_empCount]
					}, {
						cmns : FormUtil.CMN_TWO,
						fields : [int_insCount, txt_url]
					}, formDiyContainer, txt_remark,
					this.attachmentPnlIdMgr.ecustinfoAfId, btnPanel];
			var frm_cfg = {
				id : itemId,
				width : width,
				height : height,
				labelWidth : 90,
				autoScroll : true,
				border : false,
				formDiyCfg : {
					sysId : _this.sysId,/* 系统ID */
					formdiyCode : FORMDIY_IND.FORMDIY_ECUSTOMER_INFO,/*
																		 * 引用Code
																		 * -->
																		 * 对应
																		 * ts_Formdiy
																		 * 中的业务引用键：recode
																		 */
					formIdName : 'id',/* 对应表单中的ID Hidden 值 */
					container : formDiyContainer
					/* 自定义字段存放容器 */
				},
				url : './crmEcustomer_save.action'
			};
			this.appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			txt_url.setWidth(460);
			sit_address.setWidth(650);
			sit_address.setHeight(25);

			// this.appform_1.add(this.attachmentPnlIdMgr.ecustinfoAfId);
			return this.appform_1;
		},
		getBtnPanel : function(buttons) {
			var panel = new Ext.Panel({
						buttonAlign : 'center',
						buttons : buttons,
						autoScroll : true
					});
			return panel;
		},

		/**
		 * 企业法人资料
		 */

		createLegalManinfoPnlId : function(tab, itemId, width, height) {
			var wd = 150;
			var hid_baseId = FormUtil.getHidField({
						fieldLabel : '客户基础信息ID',
						name : 'baseId'
					});

			var hid_id = FormUtil.getHidField({
						fieldLabel : '个人客户ID',
						name : 'id'
					});
			var txt_name = FormUtil.getTxtField({
						fieldLabel : '客户姓名',
						name : 'name',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : 20
					});

			var rad_sex = FormUtil.getRadioGroup({
						fieldLabel : '性别',
						name : 'sex',
						"width" : wd,
						"allowBlank" : false,
						"items" : [{
									"boxLabel" : "男",
									"name" : "sex",
									"inputValue" : 0
								}, {
									"boxLabel" : "女",
									"name" : "sex",
									"inputValue" : 1
								}]
					});

			var rad_accNature = FormUtil.getRadioGroup({
						fieldLabel : '户口性质',
						name : 'accNature',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : 10,
						"items" : [{
									"boxLabel" : "常住",
									"name" : "accNature",
									"inputValue" : 0
								}, {
									"boxLabel" : "暂住",
									"name" : "accNature",
									"inputValue" : 1
								}]
					});
			var cbo_cardType = FormUtil.getRCboField({
						fieldLabel : '证件类型',
						name : 'cardType',
						"width" : wd,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						restypeId : '100002'
					});

			cbo_cardType.on('select', function() {
						if (cbo_cardType.getValue() == 7) {
							txt_cardNum.vtype = 'IDCard';
						} else {
							txt_cardNum.vtype = null;
						}
					});

			var txt_cardNum = FormUtil.getTxtField({
						fieldLabel : '证件号码',
						name : 'cardNum',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : "50"
					});

			var bdat_cendDate = FormUtil.getDateField({
						fieldLabel : '证件到期日期',
						name : 'cendDate',
						"width" : wd,
						"allowBlank" : false
					});

			var bdat_birthday = FormUtil.getDateField({
						fieldLabel : '出生日期',
						name : 'birthday',
						"width" : wd,
						"allowBlank" : false
					});

			var int_age = FormUtil.getIntegerField({
						fieldLabel : '年龄',
						name : 'age',
						vtype : 'age',
						"width" : wd + 10,
						"allowBlank" : false,
						"maxLength" : 10,
						value : '0',
						unitText : '岁'
					});

			var cbo_maristal = FormUtil.getRCboField({
						fieldLabel : '婚姻状况',
						name : 'maristal',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : 10,
						register : REGISTER.GvlistDatas,
						restypeId : '100003'
					});

			var cbo_hometown = FormUtil.getRCboField({
						fieldLabel : '籍贯',
						name : 'hometown',
						"width" : wd,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						restypeId : '100004'
					});

			var cbo_nation = FormUtil.getRCboField({
						fieldLabel : '民族',
						name : 'nation',
						"width" : wd,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						restypeId : '100005'
					});

			var cbo_degree = FormUtil.getRCboField({
						fieldLabel : '学历',
						name : 'degree',
						"width" : wd,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						restypeId : '100006'
					});

			var txt_phone = FormUtil.getTxtField({
						fieldLabel : '手机',
						vtype : 'mobile',
						name : 'phone',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_contactTel = FormUtil.getTxtField({
						fieldLabel : '联系电话',
						name : 'contactTel',
						vtype : 'telephone',
						"width" : wd,
						"maxLength" : "20"
					});

			var txt_email = FormUtil.getTxtField({
						fieldLabel : '电子邮件',
						name : 'email',
						vtype : 'email',
						"width" : wd,
						"maxLength" : "30"
					});

			var txt_contactor = FormUtil.getTxtField({
						fieldLabel : '联系人',
						name : 'contactor',
						"width" : wd,
						"maxLength" : "30"
					});
			var cbo_inArea = new Ext.ux.tree.AppComboxTree({
						fieldLabel : '现居住地区',
						name : 'inArea',
						url : './sysTree_arealist.action',
						disabled : false,
						isAll : true,
						width : 160,
						height : 350,
						hideBtnOk : true
					});
			cbo_inArea.comboxTree(cbo_inArea);

			var txt_inAddress = FormUtil.getTxtField({
						fieldLabel : '街道地址',
						name : 'inAddress',
						"width" : 240,
						"allowBlank" : false,
						"maxLength" : "100"
					});

			var sit_address = FormUtil.getMyCompositeField({
						fieldLabel : '现居住地区',
						sigins : null,
						height : 25,
						width : 800,
						itemNames : "inArea,inAddress",
						name : 'comp_inArea',
						items : [cbo_inArea, {
									xtype : 'displayfield',
									value : '街道地址',
									width : 50
								}, txt_inAddress]
					});

			var txt_zipcode = FormUtil.getTxtField({
						fieldLabel : '邮政编码',
						name : 'zipcode',
						vtype : 'postcode',
						"width" : wd,
						"maxLength" : "10"
					});

			var txt_fax = FormUtil.getTxtField({
						fieldLabel : '传真',
						name : 'fax',
						"width" : wd,
						"maxLength" : "20"
					});

			var txt_qqmsnNum = FormUtil.getTxtField({
						fieldLabel : 'QQ或MSN号码',
						name : 'qqmsnNum',
						vtype : 'qq',
						"width" : wd,
						"maxLength" : 50
					});

			var txt_workOrg = FormUtil.getTxtField({
						fieldLabel : '工作单位',
						name : 'workOrg',
						"width" : wd,
						"maxLength" : "50"
					});

			var txt_workAddress = FormUtil.getTxtField({
						fieldLabel : '单位地址',
						name : 'workAddress',
						"width" : wd,
						"maxLength" : "100"
					});
			var fset_secuity = FormUtil.createLayoutFieldSet({
						title : '联系信息',
						width : 820
					}, [{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_phone, txt_contactTel, txt_email,
								txt_contactor, txt_zipcode, txt_fax]
					}, {
						cmns : FormUtil.CMN_TWO,
						fields : [txt_qqmsnNum, sit_address]
					}, {
						cmns : FormUtil.CMN_TWO,
						fields : [txt_workOrg, txt_workAddress]
					}]);
			sit_address.setWidth(500);
			sit_address.setHeight(25);
			txt_workAddress.setWidth(470);
			/* 备注 */
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 800,
						height : 50
					});
			var _this = this;
			var btnSave = new Ext.Button({
				text : '保存',
				handler : function() {
					EventManager.frm_save(appform_2, {
								beforeMake : function(formData) {
								},
								sfn : function(formData) {
									var id = formData.id;
									var fields = appform_2
											.findFieldsByName("id");
									fields[0].setValue(id);

									var params = {
										formId : formData.id,
										formType : ATTACHMENT_FORMTYPE.FType_20,
										sysId : _this.params.sysId
									};
									_this
											.updateOrResetAttachment(
													_this.attachmentPnlIdMgr.legalManinfoAfId,
													params);
								}
							});
				}
			});
			var btnReset = new Ext.Button({
						text : '重置',
						handler : function() {
							_this.BtnReset(_this.appform_1,
									[txt_baseId, txt_id]);
						}
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var frm_cfg = {
				id : itemId,
				width : width,
				height : height,
				labelWidth : 90,
				autoScroll : true,
				border : false,
				formDiyCfg : {
					sysId : _this.sysId,/* 系统ID */
					formdiyCode : FORMDIY_IND.FORMDIY_LEGAL,/*
															 * 引用Code --> 对应
															 * ts_Formdiy
															 * 中的业务引用键：recode
															 */
					formIdName : 'id',/* 对应表单中的ID Hidden 值 */
					container : formDiyContainer
					/* 自定义字段存放容器 */
				},
				url : './crmCustomerInfo_savelman.action'
			};
			var btnPanel = this.getBtnPanel([btnSave, btnReset]);
			var dir = 'ecustomerinfo_path';
			this.attachmentPnlIdMgr.legalManinfoAfId = this.createAttachMentFs(this, dir);
			var layout_fields = [
					hid_baseId,
					hid_id,
					{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_name, rad_sex, rad_accNature,
								cbo_cardType, txt_cardNum, bdat_cendDate,
								bdat_birthday, int_age, cbo_maristal,
								cbo_hometown, cbo_nation, cbo_degree]
					}, fset_secuity, formDiyContainer, txt_remark,
					this.attachmentPnlIdMgr.legalManinfoAfId, btnPanel];
			appform_2 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			appform_2.addListener("afterrender", function() {
						var panel = Ext.getCmp(itemId);
						_this.setTabItemValue(panel);
					});
			// appform_2.add(this.attachmentPnlIdMgr.legalManinfoAfId);

			return appform_2;
		},

		/**
		 * 企业财务状况
		 */

		createefinancePnlId : function(tab, itemId, width, height) {
			var self = this;
			var structure_1 = [{
						header : '企业客户ID',
						name : 'ecustomerId',
						width : 125
					}, {
						header : '报表类型',
						name : 'reportType',
						width : 125,
						renderer : function(val) {
							switch (val) {
								case 1 :
									val = '1';
									break;

							}
							return val;
						}
					}, {
						header : '截止月份',
						name : 'endDate',
						width : 125,
						renderer : function(val) {
							switch (val) {
								case 0 :
									val = '0';
									break;

							}
							return val;
						}
					}, {
						header : '资产负债表',
						name : 'hasBalance',
						width : 125,
						renderer : function(val) {
							switch (val) {
								case 1 :
									val = '1';
									break;

							}
							return val;
						}
					}, {
						header : '现金流量表',
						name : 'hasCash',
						width : 125,
						renderer : function(val) {
							switch (val) {
								case 1 :
									val = '1';
									break;

							}
							return val;
						}
					}, {
						header : '利润表',
						name : 'hasProfit',
						width : 125,
						renderer : function(val) {
							switch (val) {
								case 1 :
									val = '1';
									break;

							}
							return val;
						}
					}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
						id : itemId,
						structure : structure_1,
						url : './crmEfinance_list.action',
						needPage : false,
						height : 150,
						autoScroll : true,
						isLoad : false,
						keyField : 'id'
					});
			appgrid_1.addListener("afterrender", function() {
						var panel = Ext.getCmp(itemId);
						self.setTabItemValue(panel);
					});

			var barItems = [{
						text : '上传报表',
						iconCls : 'page_add',
						tooltip : '上传报表'
					}, {
						text : '删除',
						iconCls : 'page_delete',
						tooltip : '删除'
					}, {
						text : '报表数据查看',
						iconCls : 'page_add',
						tooltip : '报表数据查看'
					}, {
						text : '报表数据分析',
						iconCls : 'page_add',
						tooltip : '报表数据分析'
					}, {
						text : '下载报表',
						iconCls : 'page_add',
						tooltip : '下载报表'
					}, {
						text : '下载模板',
						iconCls : 'page_add',
						tooltip : '下载模板'
					}];
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : barItems
					});
			var Hid_ecustomerId = FormUtil.getHidField({
						fieldLabel : '企业客户ID',
						name : 'ecustomerId',
						"width" : 125
					});

			var cbo_reportType = FormUtil.getLCboField({
						fieldLabel : '报表类型',
						name : 'reportType',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 10,
						"data" : [["1", "年报表"], ["2", "季报"], ["3", "月报"]]
					});

			var bdat_endDate = FormUtil.getDateField({
						fieldLabel : '截止月份',
						name : 'endDate',
						"width" : 125,
						"allowBlank" : false,
						"value" : "0"
					});

			var txt_regman = FormUtil.getTxtField({
						fieldLabel : '上传人',
						name : 'regman',
						"width" : 125
					});

			var rad_upload = FormUtil.getRadioGroup({
						fieldLabel : '上传方式',
						name : 'upload',
						"width" : 250,
						"allowBlank" : false,
						"maxLength" : 10,
						"items" : [{
									"boxLabel" : "三大报表一起上传",
									"name" : "upload",
									"inputValue" : 0
								}, {
									"boxLabel" : "分批上传",
									"name" : "upload",
									"inputValue" : 1
								}]
					});
			rad_upload.addListener('change', function(rad_upload,
							fset_statements, fset_statements2) {
						var rad = rad_upload.getValue();
						if (rad) {

						} else {

						}
					});
			var compt_upload = FormUtil.getMyCompositeField({
				fieldLabel : '上传方式',
				sigins : null,
				itemNames : 'upload',
				name : 'rad_upload',
				width : 610,
				items : [rad_upload, {
					xtype : 'displayfield',
					width : 400,
					html : '<span style="color:red;">提示:[选择三大报表一起上传时，三大报表必须在一个Excel文件中]</span>'
				}]
			});

			var txt_selectfile = FormUtil.getTxtField({
						fieldLabel : '选择文件',
						name : 'selectfile',
						"width" : 600
					});
			var _this = this;
			var uploadbutn = new Ext.Button({
						text : '浏览',
						width : 80,
						handler : function() {
							_this.uploadProcess();
						}
					});
			var uploadbutn1 = new Ext.Button({
						text : '浏览',
						width : 80,
						handler : function() {
							_this.uploadProcess();
						}
					});
			var uploadbutn2 = new Ext.Button({
						text : '浏览',
						width : 80,
						handler : function() {
							_this.uploadProcess();
						}
					});
			var uploadbutn3 = new Ext.Button({
						text : '浏览',
						width : 80,
						handler : function() {
							_this.uploadProcess();
						}
					});
			var txtsel_uploadbutn = FormUtil.getMyCompositeField({
						fieldLabel : '选择文件',
						sigins : null,
						itemNames : 'selectfile',
						name : 'txt_upload',
						width : 710,
						items : [txt_selectfile, {
									xtype : 'displayfield',
									width : 20
								}, uploadbutn]
					});

			var fset_statements = FormUtil.createLayoutFieldSet({
						title : '三大报表一起上传',
						width : 500,
						collapsed : true
					}, [txtsel_uploadbutn]);

			var txt_hasBalance = FormUtil.getTxtField({
						fieldLabel : '资产负债表',
						name : 'hasBalance',
						"width" : 600
					});
			var txthasbal_uploadbutn = FormUtil.getMyCompositeField({
						fieldLabel : '资产负债表',
						sigins : null,
						itemNames : 'hasBalance',
						name : 'txt_hasBalance',
						width : 710,
						items : [txt_hasBalance, {
									xtype : 'displayfield',
									width : 20
								}, uploadbutn1]
					});

			var txt_hasCash = FormUtil.getTxtField({
						fieldLabel : '现金流量表 ',
						name : 'hasCash',
						"width" : 600
					});
			var txthascas_uploadbutn = FormUtil.getMyCompositeField({
						fieldLabel : '现金流量表',
						sigins : null,
						itemNames : 'hasCash',
						name : 'txt_hasCash',
						width : 710,
						items : [txt_hasCash, {
									xtype : 'displayfield',
									width : 20
								}, uploadbutn2]
					});

			var txt_hasProfit = FormUtil.getTxtField({
						fieldLabel : '利润表',
						name : 'hasProfit',
						"width" : 600
					});
			var txthaspro_uploadbutn = FormUtil.getMyCompositeField({
						fieldLabel : '利润表',
						sigins : null,
						itemNames : 'hasProfit',
						name : 'txt_hasProfit',
						width : 710,
						items : [txt_hasProfit, {
									xtype : 'displayfield',
									width : 20
								}, uploadbutn3]
					});

			var btnSave = new Ext.Button({
						text : '保存',
						handler : function() {
							EventManager.frm_save(_this.appform_3, {
										beforeMake : function(formData) {
										}
									});
						}
					});
			var btnReset = new Ext.Button({
						text : '重置',
						handler : function() {
							EventManager.frm_reset(appform_3);
						}
					});
			var btnPanel = this.getBtnPanel([btnSave, btnReset]);
			var fset_statements2 = FormUtil.createLayoutFieldSet({
						title : '分批上传文件选择',
						width : 750,
						collapsed : true
					}, [txthasbal_uploadbutn, txthascas_uploadbutn,
							txthaspro_uploadbutn]);
			var layout_fields = [{
				cmns : FormUtil.CMN_THREE,
				fields : [Hid_ecustomerId, cbo_reportType, bdat_endDate,
						txt_regman]
			}, compt_upload, fset_statements, fset_statements2, btnPanel];
			var frm_cfg = {
				width : width,
				height : height,
				labelWidth : 90,
				autoScroll : true,
				url : './crmEfinance_save.action'
			};

			var appform_3 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_3],
						autoScroll : true
					})
			return appgridpanel_1;
		},
		uploadProcess : function() {
			if (!this.uploadWin) {
				var _this = this;
				this.uploadWin = new Ext.ux.window.MyUpWin({
							title : '上传报表文件',
							label : '请选择报表文件',
							width : 450,
							dir : 'excel_path',
							sfn : function(fileInfos) {
								_this.submitData(fileInfos);
							}
						});
			}
			this.uploadWin.show();
		},
		submitData : function(fileInfos) {
			var _this = this;
			var filePath = null;
			var size = 0;
			var custName = null;
			if (fileInfos) {
				var fileInfo = fileInfos[0];
				filePath = fileInfo.serverPath;
				custName = fileInfo.custName;
				size = fileInfo.size;
			}
			this.appFrm.findFieldByName('filePath').setValue(filePath);
			var cfg = {
				tb : _this.appWin.apptbar,
				beforeMake : function(formData) {
					formData.inType = _this.parentCfg.bussType;
					formData.custName = custName;
					formData.size = size;
				},
				sfn : function(formData) {
					if (_this.refresh)
						_this.refresh(formData);
					_this.setFormValues(formData);
				}
			};
			EventManager.frm_save(this.appFrm, cfg);
		},
		/**
		 * 企业开户
		 */
		createebankPnlId : function(tab, itemId, width, height) {
			var self = this;
			var Ebank = "Ebank";
			var structure_1 = [{
						header : 'id',
						name : 'id',
						hidden : true
					}, {
						header : '企业客户ID',
						name : 'ecustomerId',
						hidden : true
					}, {
						header : '帐户类型',
						name : 'accountType',
						renderer : Render_dataSource.accountTyperenderer
					}, {
						header : '开户帐号',
						name : 'account'
					}, {
						header : '开户银行',
						name : 'bankOrg'
					}, {
						header : '开户时间',
						name : 'orderDate',
						renderer : Ext.util.Format.dateRenderer('Y-m-d')
					}, {
						header : '月均结算量',
						name : 'setAmount',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '月均存款余额',
						name : 'balance',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '其他情况说明',
						name : 'remark',
						width : 200
					}];

			var txt_id = FormUtil.getTxtField({
						fieldLabel : 'id',
						name : 'id',
						hidden : true,
						width : 135
					});
			var txt_ecustomerId = FormUtil.getTxtField({
						fieldLabel : '企业客户ID',
						name : 'ecustomerId',
						hidden : true,
						"width" : 125
					});

			var cbo_accountType = FormUtil.getLCboField({
						fieldLabel : '帐户类型',
						name : 'accountType',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50,
						"data" : Lcbo_dataSource.accountType_dates
					});

			var txt_account = FormUtil.getTxtField({
						fieldLabel : '开户帐号',
						name : 'account',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_bankOrg = FormUtil.getTxtField({
						fieldLabel : '开户银行',
						name : 'bankOrg',
						"width" : 135
					});

			var bdat_orderDate = FormUtil.getDateField({
						fieldLabel : '开户时间',
						name : 'orderDate',
						"width" : 135
					});

			var mon_setAmount = FormUtil.getMoneyField({
						fieldLabel : '月均结算量',
						name : 'setAmount',
						"width" : 150,
						unitText : '元'
					});

			var mon_balance = FormUtil.getMoneyField({
						fieldLabel : '月均存款余额',
						name : 'balance',
						unitText : '元',
						"width" : 150
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 650,
						height : 50
					});
			var layout_fields = [txt_id, txt_ecustomerId, {
				cmns : FormUtil.CMN_THREE,
				fields : [cbo_accountType, txt_account, txt_bankOrg,
						bdat_orderDate, mon_setAmount, mon_balance]
			}, formDiyContainer, txt_remark];

			var appform_1 = this.cmptAppForm(Ebank, layout_fields, width,
					height, formDiyContainer);
			var appgrid_1 = this.cmptAppGrid(Ebank, itemId, structure_1);
			var toolBar = this.createToolBar(appform_1, appgrid_1, Ebank)

			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1]
					});
			return appgridpanel_1;
		},

		/**
		 * 第三方担保
		 */
		GuaCustomerPnlId : function(tab, itemId, width, height) {
			var self = this;
			var wd = 125;
			var GuaCustomer = "GuaCustomer";
			var structure_1 = [ {
						header : '客户基础信息ID',
						name : 'baseId',
						hidden : true
					},{
						header : '担保人名称',
						name : 'name',
						width : 200
					},{
						header : '是否连带担保责任',
						name : 'isgua',
						width : 160,
						renderer : Render_dataSource.isguaDetailRender
					},{
						header : '联系人',
						name : 'contactor'
					}, {
						header : '证件类型',
						name : 'cardType',
						renderer : Render_dataSource.guaCardTypeRender
					}, {
						header : '证件号码',
						name : 'cardNum'
					}, {
						header : '担保人电话',
						name : 'contactTel'
					}, {
						header : '担保人手机',
						name : 'phone'
					}];
			
			var appgrid_1 = this.cmptAppGrid(GuaCustomer,itemId, structure_1);

			
			var hid_baseId = FormUtil.getHidField({
						fieldLabel : '客户基础信息ID',
						name : 'baseId'
					});

			var hid_id = FormUtil.getHidField({
						fieldLabel : '担保人ID',
						name : 'id'
					});
			
			var txt_name = FormUtil.getTxtField({
						fieldLabel : '担保人名称',
						name : 'name',
						"width" : 300,
						"allowBlank" : false,
						"maxLength" : 100
					});
			var Butn = new Ext.Button({text: '关联的客户',name:'btn'});
			var name_btn = FormUtil.getMyCompositeField({fieldLabel: '担保人名称',allowBlank:false,itemsOne: true ,sigins:null,itemNames : 'name,btn',
		    name: 'nameCmp',width:600,items:[txt_name,{xtype:'displayfield',width:1},Butn]});
			
		    Butn.addListener("click",function(){
					var _this = this;
					var parentCfg = {
					parent :Butn,
					params : {},
					callback : function(data){
						
						if(data){
							var name = "";
							var contactor ="";
							var phone = "";
							var contactTel ="";
							var custType = data.custType;
							var inArea = "";
							var inAddress = "";
							var cardType = "";
							var cardNum = "";
							if(custType ==0){
								EventManager.get('./crmCustomerInfo_get.action',{params:{id:id},sfn : function(json_data){
									name = json_data.name ;
									phone = json_data.phone ;
									contactTel = json_data.contactTel ;
									inArea = json_data.inArea ;
									cardNum = json_data.cardNum;
									inAddress = json_data.inAddress;
									var cardtype = json_data.cardType;
									switch(cardtype){
										case 7 : cardType = 0;break;
										case 8 : cardType = 1;break;
										case 9 : cardType = 2;break;
									}
									txt_name.setValue(name);
									txt_phone.setValue(phone);
									txt_contactTel.setValue(contactTel);
									if(inArea){
										cbo_inArea.setValue(inArea);
									}
									txt_inAddress.setValue(inAddress);
									txt_cardNum.setValue(cardNum);
									cbo_cardType.setValue(cardType);
								}})
							}else {
								EventManager.get('./crmEcustomer_get.action',{params:{id:id},sfn : function(json_data){
									name = json_data.name ;
									phone = json_data.phone ;
									contactTel = json_data.contactTel ;
									var address = json_data.address
									cardType = 3;
									cardNum = json_data.tradNumber;
									inAddress = json_data.inAddress ;
									txt_name.setValue(name);
									txt_phone.setValue(phone);
									txt_contactTel.setValue(contactTel);
									cbo_inArea.setValue(address);
									txt_inAddress.setValue(inAddress);
									txt_cardNum.setValue(cardNum);
									cbo_cardType.setValue(cardType);
								}})
								
							}
							
						}
					}
				};
				if(_this.customerDialog){
						_this.customerDialog.show(parentCfg);
				}else{
					Cmw.importPackage('/pages/app/dialogbox/CustomerDialogbox', function(module) {
						_this.customerDialog = module.DialogBox;
						_this.customerDialog.show(parentCfg);
					});
				}
			});
		    
			var rad_isgua = FormUtil.getRadioGroup({
						fieldLabel : '是否连带担保责任',
						name : 'isgua',
						"width" : wd,
						hidden : true,
						"items" : [{
									"boxLabel" : "是",
									"name" : "isgua",
									"inputValue" : 1,
									checked : true
								}, {
									"boxLabel" : "否",
									"name" : "isgua",
									"inputValue" : 2
								}]
					});
			var txt_relation = FormUtil.getTxtField({
						fieldLabel : '与被担保人关系',
						name : 'relation',
						"width" : wd,
//						"allowBlank" : false,
						"maxLength" : "50"
					});
		var txt_contactor = FormUtil.getTxtField({
				fieldLabel : '担保责任人',
				name : 'contactor',
				"width" : wd,
				"maxLength" : "30"
			});
			var cbo_cardType = FormUtil.getLCboField({
						fieldLabel : '证件类型',
						name : 'cardType',
						"width" : wd,
						"allowBlank" : false,
						data : Lcbo_dataSource.cardType_datas
					});

			var txt_cardNum = FormUtil.getTxtField({
						fieldLabel : '证件号码',
						name : 'cardNum',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : "50"
					});


			
			var txt_phone = FormUtil.getTxtField({
						fieldLabel : '担保人手机',
						name : 'phone',
						"width" : wd,
						"maxLength" : 50
					});

			var txt_contactTel = FormUtil.getTxtField({
						fieldLabel : '担保人电话',
						name : 'contactTel',
						"width" : wd,
						"maxLength" : "20"
					});

			var cbo_inArea = new Ext.ux.tree.AppComboxTree({
						fieldLabel : '现居住地区',
						name : 'inArea',
						url : './sysTree_arealist.action',
						disabled : false,
						isAll : true,
						width : 160,
						height : 350,
						hideBtnOk : true
			});
			cbo_inArea.comboxTree(cbo_inArea);

			var txt_inAddress = FormUtil.getTxtField({
						fieldLabel : '街道地址',
						name : 'inAddress',
						"width" : 240,
						"allowBlank" : false,
						"maxLength" : "100"
					});

			var sit_address = FormUtil.getMyCompositeField({
						fieldLabel : '现居住地区',
						sigins : null,
						height : 25,
						width : 600,
						itemNames : "inArea,inAddress",
						name : 'comp_inArea',
						items : [cbo_inArea, {
									xtype : 'displayfield',
									value : '街道地址',
									width : 50
								}, txt_inAddress]
					});

			sit_address.setWidth(500);
			sit_address.setHeight(25);
			/* 备注 */
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 800,
						height : 50
					});
			var formDiyContainer = new Ext.Container({
				layout : 'fit'
			});
			
			var layout_fields = [
					hid_baseId,
					hid_id,{
						cmns : FormUtil.CMN_TWO_LEFT,
						fields : [name_btn,rad_isgua]
					},{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_relation,cbo_cardType,
						txt_cardNum,txt_phone,txt_contactTel,txt_contactor]
					}, sit_address,txt_remark]; 	
			
				var appform_1 = this.cmptAppForm(GuaCustomer, layout_fields, width,
				height, formDiyContainer);
			var toolBar = this.createToolBar(appform_1, appgrid_1, GuaCustomer);
			
			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1],
						autoScroll : true
					})
			return appgridpanel_1;
		},

		/**
		 * 股权结构资料
		 */
		createeeqstructPnlId : function(tab, itemId, width, height) {
			var self = this;
			var Eeqstruct = "Eeqstruct";
			var structure_1 = [{
						header : 'id',
						name : 'id',
						hidden : true
					}, {
						header : '企业客户ID',
						name : 'ecustomerId',
						hidden : true
					}, {
						header : '出资人名称',
						name : 'name'
					}, {
						header : '出资方式',
						name : 'outType',
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100013', val);
						}
					}, {
						header : '出资额',
						name : 'inAmount',
						renderer : Render_dataSource.wmoneyRender
					}, {
						header : '占比例（%）',
						name : 'percents',
						renderer : function(val) {
							return (val) ? val + '%' : '';
						}
					}, {
						header : '出资时间',
						name : 'storderDate'
					}, {
						header : '其他情况说明',
						name : 'remark',
						width : 250
					}];

			var Hid_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id',
						width : 135
					});

			var Hid_ecustomerId = FormUtil.getHidField({
						fieldLabel : '企业客户ID',
						name : 'baseId',
						width : 135
					});

			var txt_name = FormUtil.getTxtField({
						fieldLabel : '出资人名称',
						name : 'name',
						"allowBlank" : false,
						width : 135
					});

			var rcbo_outType = FormUtil.getRCboField({
						fieldLabel : '出资方式',
						name : 'outType',
						width : 135,
						"maxLength" : 50,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						restypeId : '100013'
					});

			var mon_inAmount = FormUtil.getMoneyField({
						fieldLabel : '出资额',
						name : 'inAmount',
						unitText : '万元',
						width : 135
					});

			var dob_percents = FormUtil.getDoubleField({
						fieldLabel : '占比例',
						name : 'percents',
						width : 140,
						unitText : '%'
					});

			var bdat_storderDate = FormUtil.getDateField({
						fieldLabel : '出资时间',
						name : 'storderDate',
						"allowBlank" : false,
						width : 135
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 650,
						height : 50
					});
			var layout_fields = [Hid_id, Hid_ecustomerId, {
				cmns : FormUtil.CMN_THREE,
				fields : [txt_name, rcbo_outType, mon_inAmount, dob_percents,
						bdat_storderDate]
			}, formDiyContainer, txt_remark];

			var appform_1 = this.cmptAppForm(Eeqstruct, layout_fields, width,
					height, formDiyContainer);
			var appgrid_1 = this.cmptAppGrid(Eeqstruct, itemId, structure_1);
			var toolBar = this.createToolBar(appform_1, appgrid_1, Eeqstruct);

			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1]
					});
			return appgridpanel_1;
		},

		/**
		 * 银行贷款资料
		 */
		createebankBorrPnlId : function(tab, itemId, width, height) {
			var self = this;
			var EbankBorr = "EbankBorr";
			var structure_1 = [{
						header : '企业客户ID',
						name : 'ecustomerId',
						hidden : true
					}, {
						header : '银行名称',
						name : 'name'
					}, {
						header : '借款金额',
						name : 'amount',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '借款期限',
						name : 'limits'
					}, {
						header : '信贷品种',
						name : 'creditBreed'
					}, {
						header : '担保方式',
						name : 'asstype'
					}, {
						header : '贷款分类结果',
						name : 'result'
					}, {
						header : '其他情况说明',
						name : 'remark',
						width : 250
					}];

			var hid_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id',
						width : 135
					});

			var hid_ecustomerId = FormUtil.getHidField({
						fieldLabel : '企业客户ID',
						name : 'ecustomerId',
						"width" : 125
					});

			var txt_name = FormUtil.getTxtField({
						fieldLabel : '银行名称',
						name : 'name',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var mon_amount = FormUtil.getMoneyField({
						fieldLabel : '借款金额',
						name : 'amount',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50,
						unitText : '元'
					});

			var txt_limits = FormUtil.getIntegerField({
						fieldLabel : '借款期限',
						name : 'limits',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 10
					});

			var txt_creditBreed = FormUtil.getTxtField({
						fieldLabel : '信贷品种',
						name : 'creditBreed',
						"width" : 125,
						"maxLength" : 80
					});

			var txt_asstype = FormUtil.getTxtField({
						fieldLabel : '担保方式',
						name : 'asstype',
						"width" : 125,
						"maxLength" : 80
					});

			var txt_result = FormUtil.getTxtField({
						fieldLabel : '贷款分类结果',
						name : 'result',
						"width" : 125,
						"maxLength" : 100
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '其他情况说明',
						name : 'remark',
						width : 650,
						height : 50
					});

			var layout_fields = [hid_id, hid_ecustomerId, {
				cmns : FormUtil.CMN_THREE,
				fields : [txt_name, mon_amount, txt_limits, txt_creditBreed,
						txt_asstype, txt_result]
			}, formDiyContainer, txt_remark];

			var appform_1 = this.cmptAppForm(EbankBorr, layout_fields, width,
					height, formDiyContainer);
			var appgrid_1 = this.cmptAppGrid(EbankBorr, itemId, structure_1);
			var toolBar = this.createToolBar(appform_1, appgrid_1, EbankBorr)

			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1]
					});
			return appgridpanel_1;
		},

		/**
		 * 所有者贷款资料
		 */
		createeownerBorrPnlId : function(tab, itemId, width, height) {
			var self = this;
			var EownerBorr = "EownerBorr";
			var structure_1 = [{
						header : '企业客户ID',
						name : 'ecustomerId',
						hidden : true
					}, {
						header : '所有者类型',
						name : 'onwerType',
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100012', val)
						}
					}, {
						header : '所有者名称',
						name : 'onwer'
					}, {
						header : '银行名称',
						name : 'name'
					}, {
						header : '借款金额',
						name : 'amount',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '借款期限',
						name : 'limits'
					}, {
						header : '信贷品种',
						name : 'creditBreed'
					}, {
						header : '担保方式',
						name : 'asstype'
					}, {
						header : '贷款分类结果',
						name : 'result'
					}, {
						header : '其他情况说明',
						name : 'remark',
						width : 250
					}];

			var hid_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id',
						width : 135
					});

			var hid_ecustomerId = FormUtil.getHidField({
						fieldLabel : '企业客户ID',
						name : 'ecustomerId',
						"width" : 125
					});

			var rcbo_onwerType = FormUtil.getRCboField({
						fieldLabel : '所有者类型',
						name : 'onwerType',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50,
						register : REGISTER.GvlistDatas,
						restypeId : '100012'
					});

			var txt_onwer = FormUtil.getTxtField({
						fieldLabel : '所有者名称',
						name : 'onwer',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_name = FormUtil.getTxtField({
						fieldLabel : '银行名称',
						name : 'name',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var mon_amount = FormUtil.getMoneyField({
						fieldLabel : '借款金额',
						name : 'amount',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50,
						unitText : '元'
					});

			var txt_limits = FormUtil.getTxtField({
						fieldLabel : '借款期限',
						name : 'limits',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_creditBreed = FormUtil.getTxtField({
						fieldLabel : '信贷品种',
						name : 'creditBreed',
						"width" : 125,
						"maxLength" : 80
					});

			var txt_asstype = FormUtil.getTxtField({
						fieldLabel : '担保方式',
						name : 'asstype',
						"width" : 125,
						"maxLength" : 80
					});

			var txt_result = FormUtil.getTxtField({
						fieldLabel : '贷款分类结果',
						name : 'result',
						"width" : 125,
						"maxLength" : 100
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '其他情况说明',
						name : 'remark',
						width : 650,
						height : 50
					});
			var layout_fields = [hid_id, hid_ecustomerId, {
				cmns : FormUtil.CMN_THREE,
				fields : [rcbo_onwerType, txt_onwer, txt_name, mon_amount,
						txt_limits, txt_creditBreed, txt_asstype, txt_result]
			}, formDiyContainer, txt_remark];

			var appform_1 = this.cmptAppForm(EownerBorr, layout_fields, width,
					height, formDiyContainer);
			var appgrid_1 = this.cmptAppGrid(EownerBorr, itemId, structure_1);
			var toolBar = this.createToolBar(appform_1, appgrid_1, EownerBorr)
			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1]
					});
			return appgridpanel_1;
		},

		/**
		 * 领导班子
		 */
		createeclasstInfoPnlId : function(tab, itemId, width, height) {
			var self = this;
			var Eclass = "Eclass";
			var structure_1 = [{
						header : '企业客户ID',
						name : 'ecustomerId',
						hidden : true
					}, {
						header : '领导名称',
						name : 'fugleName'
					}, {
						header : '性别',
						name : 'sex',
						width : 60,
						renderer : Render_dataSource.sexRender
					}, {
						header : '出生日期',
						name : 'birthday',
						renderer : Ext.util.Format.dateRenderer('Y-m-d')
					}, {
						header : '证件类型',
						name : 'cardType',
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100002', val);
						}
					}, {
						header : '证件号码',
						name : 'cardNumer'
					}, {
						header : '籍贯',
						name : 'hometown',
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100004', val);
						}
					}, {
						header : '民族',
						name : 'nation',
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100005', val);
						}
					}, {
						header : '学历',
						name : 'degree',
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100006', val);
						}
					}, {
						header : '职务',
						name : 'job'
					}, {
						header : '是否董事成员',
						name : 'isMember',
						renderer : Render_dataSource.FormdiyRender
					}, {
						header : '联系电话',
						name : 'Tel'
					}, {
						header : '手机',
						name : 'phone'
					}, {
						header : '任职时间',
						name : 'incomeTime',
						renderer : Ext.util.Format.dateRenderer('Y-m-d')
					}];

			var hid_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id',
						"width" : 125
					});

			var hid_ecustomerId = FormUtil.getHidField({
						fieldLabel : '企业客户ID',
						name : 'ecustomerId',
						"width" : 125
					});

			var txt_fugleName = FormUtil.getTxtField({
						fieldLabel : '领导名称',
						name : 'fugleName',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var rad_sex = FormUtil.getRadioGroup({
						fieldLabel : '性别',
						name : 'sex',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50,
						"items" : [{
									"boxLabel" : "女",
									"name" : "sex",
									"inputValue" : 0
								}, {
									"boxLabel" : "男",
									"name" : "sex",
									"inputValue" : 1
								}]
					});

			var rad_isMember = FormUtil.getRadioGroup({
						fieldLabel : '是否董事成员',
						name : 'isMember',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50,
						"items" : [{
									"boxLabel" : "否",
									"name" : "isMember",
									"inputValue" : 0
								}, {
									"boxLabel" : "是",
									"name" : "isMember",
									"inputValue" : 1
								}]
					});

			var rcbo_cardType = FormUtil.getRCboField({
						fieldLabel : '证件类型',
						name : 'cardType',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50,
						register : REGISTER.GvlistDatas,
						restypeId : '100002'
					});

			var txt_cardNumer = FormUtil.getTxtField({
						fieldLabel : '证件号码',
						name : 'cardNumer',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var bdat_birthday = FormUtil.getDateField({
						fieldLabel : '出生日期',
						"allowBlank" : false,
						name : 'birthday',
						"width" : 125
					});

			var txt_job = FormUtil.getTxtField({
						fieldLabel : '职务',
						name : 'job',
						"width" : 125
					});

			var bdat_incomeTime = FormUtil.getDateField({
						fieldLabel : '任职时间',
						name : 'incomeTime',
						"width" : 125
					});

			var rcbo_hometown = FormUtil.getRCboField({
						fieldLabel : '籍贯',
						name : 'hometown',
						"width" : 125,
						"maxLength" : 50,
						register : REGISTER.GvlistDatas,
						restypeId : '100004'
					});

			var rcbo_nation = FormUtil.getRCboField({
						fieldLabel : '民族',
						name : 'nation',
						"width" : 125,
						"maxLength" : 50,
						register : REGISTER.GvlistDatas,
						restypeId : '100005'
					});

			var rcbo_degree = FormUtil.getRCboField({
						fieldLabel : '学历',
						name : 'degree',
						"width" : 125,
						"maxLength" : 50,
						register : REGISTER.GvlistDatas,
						restypeId : '100006'
					});

			var txt_Tel = FormUtil.getTxtField({
						fieldLabel : '联系电话',
						vtype : 'telephone',
						name : 'Tel',
						"width" : 125
					});

			var txt_phone = FormUtil.getTxtField({
						fieldLabel : '手机',
						vtype : 'mobile',
						name : 'phone',
						"width" : 125
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '其他情况说明',
						name : 'remark',
						width : 650,
						height : 50
					});
			var layout_fields = [hid_id, hid_ecustomerId, {
				cmns : FormUtil.CMN_THREE,
				fields : [txt_fugleName, rad_sex, rad_isMember, rcbo_cardType,
						txt_cardNumer, bdat_birthday, txt_job, bdat_incomeTime,
						rcbo_hometown, rcbo_nation, rcbo_degree, txt_Tel,
						txt_phone]
			}, formDiyContainer, txt_remark];

			var appform_1 = this.cmptAppForm(Eclass, layout_fields, width,
					height, formDiyContainer);
			var appgrid_1 = this.cmptAppGrid(Eclass, itemId, structure_1);
			var toolBar = this.createToolBar(appform_1, appgrid_1, Eclass)

			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1]
					});
			return appgridpanel_1
		},

		/**
		 * 企业担保资料
		 */
		createeassureInfoPnlId : function(tab, itemId, width, height) {
			var self = this;
			var Eassure = "Eassure";
			var structure_1 = [{
						hidden : true,
						header : '企业客户ID',
						name : 'ecustomerId'
					}, {
						header : '担保对象',
						name : 'object'
					}, {
						header : '担保金额',
						name : 'amount',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '起始日期',
						name : 'asstartDate'
					}, {
						header : '解除日期',
						name : 'asendDate'
					}, {
						header : '期限',
						name : 'term'
					}, {
						header : '责任比例',
						name : 'inverse'
					}, {
						header : '责任余额',
						name : 'asbalance',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '运营情况',
						name : 'thing'
					}];

			var hid_id = FormUtil.getHidField({
						name : 'id'
					});
			var txt_ecustomerId = FormUtil.getHidField({
						fieldLabel : '企业客户ID',
						name : 'ecustomerId',
						"width" : 125,
						"allowBlank" : false
					});

			var txt_object = FormUtil.getTxtField({
						fieldLabel : '担保对象',
						name : 'object',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : "50"
					});

			var mon_amount = FormUtil.getMoneyField({
						fieldLabel : '担保金额',
						name : 'amount',
						"width" : 135,
						"allowBlank" : false,
						unitText : '元'
					});

			var bdat_asstartDate = FormUtil.getDateField({
						fieldLabel : '起始日期',
						name : 'asstartDate',
						"width" : 125,
						"allowBlank" : false
					});

			var bdat_asendDate = FormUtil.getDateField({
						fieldLabel : '解除日期',
						name : 'asendDate',
						"width" : 125,
						"allowBlank" : false
					});

			var txt_term = FormUtil.getTxtField({
						fieldLabel : '期限',
						name : 'term',
						"width" : 125,
						"allowBlank" : false
					});

			var dob_inverse = FormUtil.getIntegerField({
						fieldLabel : '责任比例',
						name : 'inverse',
						"width" : 125
					});

			var mon_asbalance = FormUtil.getMoneyField({
						fieldLabel : '责任余额',
						name : 'asbalance',
						"width" : 135,
						unitText : '元'
					});

			var txa_thing = FormUtil.getTAreaField({
						fieldLabel : '运营情况',
						name : 'thing',
						"width" : 600,
						height : 50,
						"maxLength" : "200"
					});

			var txa_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						"width" : 600,
						height : 50,
						"maxLength" : "200"
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var layout_fields = [{
				cmns : FormUtil.CMN_THREE,
				fields : [hid_id, txt_ecustomerId, txt_object, mon_amount,
						bdat_asstartDate, bdat_asendDate, txt_term, dob_inverse]
			}, mon_asbalance, txa_thing, formDiyContainer, txa_remark];

			var appform_1 = this.cmptAppForm(Eassure, layout_fields, width,
					height, formDiyContainer);
			var appgrid_1 = this.cmptAppGrid(Eassure, itemId, structure_1);
			var toolBar = this.createToolBar(appform_1, appgrid_1, Eassure)
			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1]
					});
			return appgridpanel_1;
		},

		/**
		 * 创建其他个人信息
		 */
		creatotherInfoPnlId : function(tab, itemId, width, height) {
			var OtherInfo = "OtherInfo";
			var structure = [{
						header : 'id',
						name : 'id',
						hidden : true
					}, {
						header : 'customerId',
						name : 'customerId',
						hidden : true
					}, {
						header : 'formType',
						name : 'formType',
						hidden : true
					}, {
						header : '资料名称',
						name : 'otherName',
						width : 150
					}, {
						header : '上传人',
						id : 'creator',
						name : 'creator',
						width : 80
					}, {
						header : '上传时间',
						id : 'createTime',
						name : 'createTime',
						width : 80
					}, {
						header : '修改时间',
						id : 'modifytime',
						name : 'modifytime',
						width : 80
					}, {
						header : '资料说明',
						id : 'remark',
						name : 'remark',
						width : 300
					}];
			var appgrid = this.cmptAppGrid(OtherInfo, itemId, structure);
			var _this = this;

			var txt_id = FormUtil.getHidField({
						fieldLabel : 'ID',
						name : 'id'
					});
			var hid_customerId = FormUtil.getHidField({
						fieldLabel : '个人客户ID',
						name : 'customerId',
						"width" : 135,
						"maxLength" : 50
					});
			var txt_formType = FormUtil.getHidField({
						fieldLabel : 'formType',
						name : 'formType',
						value : ATTACHMENT_FORMTYPE.FType_28
					});
			var txt_name = FormUtil.getTxtField({
						fieldLabel : '资料名称',
						name : 'otherName',
						"width" : 500,
						allowBlank : false,
						"maxLength" : "50"
					});

			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var remark = FormUtil.getTAreaField({
						fieldLabel : '资料说明',
						name : 'remark',
						height : 50,
						width : 500,
						maxLength : 200
					});
			var layout_fields = [txt_id, hid_customerId, txt_formType,
					txt_name, formDiyContainer, remark];

			var appform = this.cmptAppForm(OtherInfo, layout_fields, width,
					height, formDiyContainer);

			var toolBar = this.createToolBar(appform, appgrid, OtherInfo);
			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid, toolBar, appform],
						autoScroll : true
					})
			return appgridpanel_1;
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			if (null != this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		}
	};
});