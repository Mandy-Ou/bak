/**
 * 菜单新增或修改页面
 * 
 * @author 程明卫
 * @date 2012-07-12
 * 个客户资料详情
 */
define(function(require, exports) {
	exports.MainEdit = {
		customerDialog : null, // 管客户弹出窗口
		CustInfoPanel : null,
		parentCfg : null,
		parent : null,
		appMainPanel : null,
		tabPanel : null,
		baseFormPanel : null,
		optionType : null,/* 默认为新增 */
		addressPnlId : null,
		formDatas : null,
		sysId : null, /* 系统ID */
		customerId : null,
		code : null,
		params : null,
		cbo_inArea : null,
		consortPnlId : null,
		custappgrid : null,
		detailPnlArray : [],
		attachMentFs : null,// 个人资料附件
		constattachMentFs : null,// 配偶附件
		addressaFPnl : null,/* 住宅附件面板id */
		addressaFPnl : null,/* 住宅附件面板id */
		estateaFPnl : null,/* 房产物业附件面板 ID */
		workaFPnl : null,/* 职业附件面板ID */
		companyaFPnl : null,/* 旗下公司附件面板ID */
		contactoraFPnl : null,/* 主要联系人附件面板ID */
		creditInfoaFPnl : null,/* 个人信用附件面板ID */
		otherInfoaFPnl : null,/* 其他信息附件面板ID */
		GuaCustomerPnl:null,/*第三方担保*/
		createGuaCustomerPnlId : null,
		tabIdMgr : {/* Tab Id 定义 */
			custinfoPnlId : Ext.id(null, 'custinfoPnlId'), /* 个人详细资料面板ID */
			consortPnlId : Ext.id(null, 'consortPnlId'),/* 配偶资料面板ID */
			GuaCustomerPnlId : Ext.id(null, 'GuaCustomerPnlId'),/*
																 * 第三方担保人ID //
																 */
			addressPnlId : Ext.id(null, 'addressPnlId'),/* 住宅面板ID */
			estatePnlId : Ext.id(null, 'estatePnlId'),/* 房产物业面板ID */
			workPnlId : Ext.id(null, 'workPnlId'),/* 职业面板ID */
			companyPnlId : Ext.id(null, 'companyPnlId'),/* 旗下公司面板ID */
			contactorPnlId : Ext.id(null, 'contactor'),/* 主要联系人面板ID */
			creditInfoPnlId : Ext.id(null, 'creditInfoPnlId'),/* 个人信用面板ID */
			otherInfoPnlId : Ext.id(null, 'otherInfoPnlId')
			/* 其他信息面板ID */
		},
		formIdMgr : {/* formPnl Id 定义 */
			GuaCustomerFrmId : Ext.id(null, 'GuaCustomerFrmId'),/* 第三方担保人ID */
			addressFrmId : Ext.id(null, 'addressFrmId'),/* 住宅面板 Frm ID */
			estateFrm : Ext.id(null, 'estateFrm'),/* 房产物业面板Frm ID */
			workFrmId : Ext.id(null, 'workFrmId'),/* 职业面板Frm ID */
			companyFrmId : Ext.id(null, 'companyFrmId'),/* 旗下公司面板Frm ID */
			contactorFrmId : Ext.id(null, 'contactorFrmId'),/* 主要联系人面板 Frm ID */
			creditInfoFrmId : Ext.id(null, 'creditInfoFrmId'),/* 个人信用面板 Frm ID */
			otherInfoFrmId : Ext.id(null, 'otherInfoFrmId')
			/* 其他信息面板 Frm ID */
		},
		setParentCfg : function(parentCfg) {
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.custappgrid = parentCfg.appgrid;
			this.optionType = parentCfg.optionType;
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
				this.customerInfoId = this.params.id;
				if (!this.customerInfoId) {
					ExtUtil.alert({
								msg : "编辑客户资料时，请提供   customerInfoId 值"
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
			this.resets();
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
			if (length > 0) {
				for (var i = 0; i < length; i++) {
					this.detailPnlArray[i].reload({
								formId : -1,
								custType : 0
							});
				}
			}
			var id = this.baseFormPanel.getValueByName("id");
			// 个人客户主面板
			var custInfoAppForm = Ext.getCmp(this.tabIdMgr.custinfoPnlId);
			if (custInfoAppForm) {
				custInfoAppForm.reset();
				if (this.optionType == OPTION_TYPE.EDIT) {
					this.setTabItemValue(custInfoAppForm);
				}
			}
			// 配偶资料
			var consortAppForm = Ext.getCmp(this.tabIdMgr.consortPnlId);
			if (consortAppForm) {
				consortAppForm.reset();
				if (this.optionType == OPTION_TYPE.EDIT) {
					this.setTabItemValue(consortAppForm);
				}
			}

			// 第三方担保资料
			var addCustAppGrid = Ext.getCmp(this.tabIdMgr.GuaCustomerPnlId);
			var addCustAppFrm = Ext.getCmp(this.formIdMgr.GuaCustomerFrmId);
			this.gridAndFrmRest(addCustAppGrid, addCustAppFrm);

			// 住宅资料
			var addressAppGrid = Ext.getCmp(this.tabIdMgr.addressPnlId);
			var addressAppFrm = Ext.getCmp(this.formIdMgr.addressFrmId);
			this.gridAndFrmRest(addressAppGrid, addressAppFrm);
			// 房产物业资料
			var estateAppGrid = Ext.getCmp(this.tabIdMgr.estatePnlId);
			var estateAppFrm = Ext.getCmp(this.formIdMgr.estateFrm);
			this.gridAndFrmRest(estateAppGrid, estateAppFrm);

			// 职业资料
			var workAppGrid = Ext.getCmp(this.tabIdMgr.workPnlId);
			var workAppFrm = Ext.getCmp(this.formIdMgr.workFrmId);
			this.gridAndFrmRest(workAppGrid, workAppFrm);
			// 旗下公司
			var companyAppGrid = Ext.getCmp(this.tabIdMgr.companyPnlId);
			var companyAppFrm = Ext.getCmp(this.formIdMgr.companyFrmId);
			this.gridAndFrmRest(companyAppGrid, companyAppFrm);
			// 主要联系人
			var contactorAppGrid = Ext.getCmp(this.tabIdMgr.contactorPnlId);
			var contactorAppFrm = Ext.getCmp(this.formIdMgr.contactorFrmId);
			this.gridAndFrmRest(contactorAppGrid, contactorAppFrm);
			// 个人信用资料
			var creditInfoAppGrid = Ext.getCmp(this.tabIdMgr.creditInfoPnlId);
			var creditInfoAppFrm = Ext.getCmp(this.formIdMgr.creditInfoFrmId);
			this.gridAndFrmRest(creditInfoAppGrid, creditInfoAppFrm);

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
		 * 清除附件
		 */
		AfRestLoad : function(formId) {
			var params = null;
			var _this = this;
			var customerInfoAf = this.attachMentFs;// 个人资料附件
			if (customerInfoAf) {
				_this.updateOrResetAttachment(customerInfoAf, false);
			}
			var consortAf = this.constattachMentFs;// 配偶附件
			if (consortAf) {
				_this.updateOrResetAttachment(consortAf, false);
			}

		},
		/**
		 * 重置Frm
		 */
		gridAndFrmRest : function(AppGrid, AppFrm) {
			if (AppGrid && AppFrm) {
				AppGrid.removeAll();
				EventManager.frm_reset(AppFrm);
				if (this.optionType == OPTION_TYPE.EDIT) {
					this.setTabItemValue(AppGrid);
				}
			}
		},

		setValues : function() {
			this.resets();
			if (this.optionType === OPTION_TYPE.ADD) { // 添加时赋值
				EventManager.frm_reset(this.baseFormPanel);
				this.baseFormPanel.setVs(this.formDatas);
				var regman = this.params.regman;
				var registerTime = this.params.registerTime;
				var fields = this.baseFormPanel
						.findFieldsByName("regman,registerTime");
				fields[0].setValue(regman);
				fields[1].setValue(registerTime);
			} else { // 修改时赋值
				var fields = this.baseFormPanel.setVs(this.params);
				this.tabPanel.enable();
			}
			return;
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
						title : '个人客户基本信息编辑',
						width : pw,
						height : ph,
						border : false
					});
		},
		createBaseFormPanel : function() {
			var self = this;
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
						fieldLabel : '姓名',
						name : 'name',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 20
					});

			var txt_serialNum = FormUtil.getReadTxtField({
						fieldLabel : '客户流水号',
						name : 'serialNum',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : "20"
					});

			var cbo_cardType = FormUtil.getRCboField({
						fieldLabel : '证件类型',
						name : 'cardType',
						"width" : 135,
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
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : "50"
					});

			var rad_sex = FormUtil.getRadioGroup({
						fieldLabel : '性别',
						name : 'sex',
						"width" : 135,
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
						"allowBlank" : false,
						"width" : 135
					});

			var layout_fields = [hid_baseId, hid_id, hid_department, {
				cmns : FormUtil.CMN_THREE,
				fields : [txt_code, txt_name, txt_serialNum, cbo_cardType,
						txt_cardNum, rad_sex, txt_regman, txt_registerTime]
			}];

			var frm_cfg = {
				url : './crmCustBase_save.action',
				labelWidth : {
					autoWidth : true
				},
				buttonAlign : 'center',
				border : false
			};
			this.baseFormPanel = FormUtil.createLayoutFrm(frm_cfg,
					layout_fields);
			var _this = this;
			var btnSave = new Ext.Button({
				text : '保存基本资料',
				handler : function() {
					EventManager.frm_save(_this.baseFormPanel, {
						beforeMake : function(formData) {
							formData.custType = "0";
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
								if (_this.optionType == OPTION_TYPE.ADD) {
									var afCust = _this.attachMentFs;
									var params = {
										formId : id,
										formType : ATTACHMENT_FORMTYPE.FType_11,
										sysId : _this.params.sysId
									};
									afCust.setDisabled(false);
									if (_this.optionType == OPTION_TYPE.ADD) {
										afCust.reload(params);
									}
								}
							}
							if (_this.custappgrid) {
								_this.custappgrid.reload();
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
									title : '个人详细资料',
									itemId : this.tabIdMgr.custinfoPnlId,
									autoScroll : true
								}, {
									title : '配偶资料',
									itemId : this.tabIdMgr.consortPnlId,
									autoScroll : true
								}, {
									title : '第三方担保人信息',
									itemId : this.tabIdMgr.GuaCustomerPnlId,
									autoScroll : true
								}, {
									title : '住宅资料',
									itemId : this.tabIdMgr.addressPnlId,
									autoScroll : true
								}, {
									title : '房产物业资料',
									itemId : this.tabIdMgr.estatePnlId,
									autoScroll : true
								}, {
									title : '职业资料',
									itemId : this.tabIdMgr.workPnlId,
									autoScroll : true
								}, {
									title : '旗下公司资料',
									itemId : this.tabIdMgr.companyPnlId,
									autoScroll : true
								}, {
									title : '主要联系人资料',
									itemId : this.tabIdMgr.contactorPnlId,
									autoScroll : true
								}, {
									title : '个人信用资料',
									itemId : this.tabIdMgr.creditInfoPnlId,
									autoScroll : true
								}, {
									title : '其它信息',
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
							var tabTitle = tab["title"];
							if (!tabItem) {
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
			var baseId = this.baseFormPanel.getValueByName("baseId");
			var _this = this;
			var params = {
				customerId : this.params.id,
				baseId : baseId
			};
			if (xtype == 'appform') {// FormPanel 加载数据
				if (this.optionType == OPTION_TYPE.EDIT) {

					switch (itemId) {
						case this.tabIdMgr.custinfoPnlId : {
							var id = panel.getValueByName('id');
							if (id != this.params.id) {
								Cmw.mask(panel, "正在加载数据,请稍后...");
								url = './crmCustomerInfo_get.action';
							} else {
								return;
							}
							break;
						}
						case this.tabIdMgr.consortPnlId : {
							var customerId = panel.getValueByName('customerId');
							if (customerId != this.params.id) {
								Cmw.mask(panel, "正在加载数据,请稍后...");
								url = './crmConsort_get.action';
							} else {
								return;
							}
							break;
						}
					}
					panel.reset();
					params = {
						id : params.customerId
					};
					panel.setValues(url, {
						params : params,
						sfn : function(formData) {
							if (itemId == _this.tabIdMgr.consortPnlId) {
								if (formData.id) {
									params = {
										formId : formData.id,
										formType : ATTACHMENT_FORMTYPE.FType_12,
										sysId : _this.params.sysId
									};
									var consortAf = _this.constattachMentFs;// 配偶附件
									consortAf.reload(params);
								}
							} else {
								if (formData.id) {
									params = {
										formId : formData.id,
										formType : ATTACHMENT_FORMTYPE.FType_11,
										sysId : _this.params.sysId
									};
									_this.attachMentFs.reload(params);// 加载个人附件
								}
							}
							Cmw.unmask(panel);
						}
					});

				} else {
					return;
				}
			} else {// Grid 加载数据
				if (panel == Ext.getCmp(_this.tabIdMgr.otherInfoPnlId)) {
					params.custType = 0;
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
				case this.tabIdMgr.custinfoPnlId : { /* 个人详细资料 */
					tatItem = this.createCustInfoPanel(tab, itemId, width,height);
					break;
				}
				case this.tabIdMgr.consortPnlId : { /* 配偶资料 */
					tatItem = this.createConsortPanel(tab, itemId, width,height);
					break;
				}
				case this.tabIdMgr.GuaCustomerPnlId : { /* 第三方担保人资料 */
					tatItem = this.createGuaCustomerPnlId(tab, itemId, width,height);
					break;
				}
				case this.tabIdMgr.addressPnlId : { /* 住宅资料 */
					tatItem = this.createaddressPnlId(tab, itemId, width,height);
					break;
				}
				case this.tabIdMgr.estatePnlId : { /* 房产物业资料 */
					tatItem = this.createestatePnlId(tab, itemId, width, height);
					break;
				}
				case this.tabIdMgr.workPnlId : { /* 职业资料 */
					tatItem = this.createworkPnlId(tab, itemId, width, height);
					break;
				}
				case this.tabIdMgr.companyPnlId : { /* 旗下公司资料 */
					tatItem = this.createcompanyPnlId(tab, itemId, width,height);
					break;
				}
				case this.tabIdMgr.contactorPnlId : { /* 主要联系人资料 */
					tatItem = this.createcontactorPnlId(tab, itemId, width,height);
					break;
				}
				case this.tabIdMgr.creditInfoPnlId : { /* 个人信用资料 */
					tatItem = this.createcreditInfoPnlId(tab, itemId, width,height);
					break;
				}
				case this.tabIdMgr.otherInfoPnlId : { /* 个人信用资料 */
					tatItem = this.creatotherInfoPnlId(tab, itemId, width,height);
					break;
				}
			}
			return tatItem;
		},
		/**
		 * 个人详细资料
		 */
		createCustInfoPanel : function(tab, itemId, width, height) {
			var wd = 139;
			var hid_baseId = FormUtil.getHidField({
						fieldLabel : '客户基础信息ID',
						name : 'baseId'
					});

			var hid_id = FormUtil.getHidField({
						fieldLabel : '个人客户ID',
						name : 'id'
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
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : 10
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

			var txt_job = FormUtil.getTxtField({
						fieldLabel : '职务',
						name : 'job',
						"width" : wd,
						"maxLength" : "20"
					});

			var txt_phone = FormUtil.getTxtField({
						fieldLabel : '手机',
						name : 'phone',
						regexText : '请输入正确的手机号码！',
						vtype : 'mobile',
						"width" : wd,
						"maxLength" : 15
					});

			var txt_contactTel = FormUtil.getTxtField({
						fieldLabel : '联系电话',
						name : 'contactTel',
						"width" : wd,
						vtype : 'telephone',
						"maxLength" : "15"
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

			var txt_accAddress = FormUtil.getTxtField({
						fieldLabel : '祖籍地址',
						name : 'accAddress',
						"width" : 260,
						"allowBlank" : true,
						"maxLength" : "100"
					});

			var cbo_inArea = FormUtil.getMyComboxTree({
						fieldLabel : '现居住地区',
						name : 'inArea',
						url : './sysTree_arealist.action',
						disabled : false,
						isAll : true,
						width : 160,
						height : 350,
						hideBtnOk : true,
						isNeedDbClick : false
					});
			cbo_inArea.comboxTree(cbo_inArea);

			var txt_inAddress = FormUtil.getTxtField({
						fieldLabel : '街道地址',
						name : 'inAddress',
						"width" : 240,
						"maxLength" : "100"
					});

			var sit_address = FormUtil.getMyCompositeField({
						fieldLabel : '现居住地区',
						sigins : null,
						height : 25,
						width : 800,
						itemNames : "inArea,inAddress,accAddress",
						name : 'comp_inArea',
						items : [cbo_inArea, {
									xtype : 'displayfield',
									value : '街道地址',
									width : 50
								}, txt_inAddress, {
									xtype : 'displayfield',
									value : '祖籍地址',
									width : 50
								}, txt_accAddress]
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
						fields : [txt_contactor, txt_phone, txt_contactTel,
								txt_email, txt_zipcode, txt_fax, txt_qqmsnNum,
								txt_workAddress, txt_workOrg]
					}, sit_address]);
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
							EventManager.frm_save(_this.CustInfoPanel, {
										beforeMake : function(formData) {
											var baseId = _this.baseFormPanel
													.getValueByName('baseId');
											var id = _this.baseFormPanel
													.getValueByName('id');
											formData.baseId = baseId;
											formData.id = id;
										},
										sfn : function(formData) {
											if (_this.custappgrid) {
												_this.custappgrid.reload();
											}
										}
									});
						}
					});
			var btnReset = new Ext.Button({
						text : '重置',
						handler : function() {
							_this.BtnReset(_this.CustInfoPanel, [hid_baseId,
											hid_id]);
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
				url : './crmCustomerInfo_save.action',
				formDiyCfg : {
					sysId : _this.sysId,/* 系统ID */
					formdiyCode : FORMDIY_IND.FORMDIY_CUSTOMER_INFO,/*
																	 * 引用Code
																	 * --> 对应
																	 * ts_Formdiy
																	 * 中的业务引用键：recode
																	 */
					formIdName : 'id',/* 对应表单中的ID Hidden 值 */
					container : formDiyContainer
					/* 自定义字段存放容器 */
				}
			};
			var btnPanel = this.getBtnPanel([btnSave, btnReset]);
			var dir = 'customerinfo_path';
			this.attachMentFs = this.createAttachMentFs(this, dir);
			var layout_fields = [
					hid_baseId,
					hid_id,
					{
						cmns : FormUtil.CMN_THREE,
						fields : [bdat_birthday, int_age, bdat_cendDate,
								cbo_maristal, cbo_nation, cbo_degree, txt_job,
								rad_accNature, cbo_hometown]
					}, fset_secuity, formDiyContainer, txt_remark,
					this.attachMentFs,
					btnPanel];
			this.CustInfoPanel = FormUtil.createLayoutFrm(frm_cfg,
					layout_fields, {
						autoScroll : true
					});
			// sit_address.setWidth(480);

			// this.CustInfoPanel.add(this.attachMentFs);

			return this.CustInfoPanel;
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
		 * formpanel 中按钮居中方法
		 */
		getBtnPanel : function(buttons) {
			var panel = new Ext.Panel({
						buttonAlign : 'center',
						buttons : buttons
					});
			return panel;
		},
		/**
		 * 在点击修改或者是点击保存之后要进行一次加载附件
		 */
		LoadAf : function(entity, formData, updateTempFormId, cleanAll) {
			var remberId = null;
			var formType = null;
			var _this = this;
			var formItemd = this.formIdMgr;
			var attPnl = null;
			var formId = null;
			if (entity == "Address") {
				formId = formItemd.addressFrmId;
				attPnl = _this.addressaFPnl;
				formType = ATTACHMENT_FORMTYPE.FType_13;
			}
			if (entity == "Estate") {
				formId = formItemd.estateFrm;
				attPnl = _this.estateaFPnl;
				formType = ATTACHMENT_FORMTYPE.FType_14;
			}
			if (entity == "Work") {
				formId = formItemd.workFrmId;
				attPnl = _this.workaFPnl;
				formType = ATTACHMENT_FORMTYPE.FType_15;
			}
			if (entity == "CustCompany") {
				formId = formItemd.companyFrmId;
				attPnl = _this.companyaFPnl;
				formType = ATTACHMENT_FORMTYPE.FType_16;
			}
			if (entity == "Contactor") {
				formId = formItemd.contactorFrmId;
				attPnl = _this.contactoraFPnl;
				formType = ATTACHMENT_FORMTYPE.FType_17;
			}
			if (entity == "CreditInfo") {
				formId = formItemd.creditInfoFrmId;
				attPnl = _this.creditInfoaFPnl;
				formType = ATTACHMENT_FORMTYPE.FType_18;
			}
			if (entity == "OtherInfo") {
				formId = formItemd.otherInfoFrmId;
				attPnl = _this.otherInfoaFPnl;
				formType = ATTACHMENT_FORMTYPE.FType_28;
			}
			if (entity == "GuaCustomer") {
				formId = formItemd.GuaCustomerFrmId;
				attPnl = _this.GuaCustomerPnl;
				formType = ATTACHMENT_FORMTYPE.FType_35;
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
							params : {id : formData.id},
							sfn : function(json_Data) {
								params.formId = json_Data.id;
								attPnl.reload(params);
							}
						});
			}
		},
		/**
		 * 创建toolBar
		 */
		createToolBar : function(appform, appgrid, entity) {
			var self = this;
			var barItems = [{
				text : '保存',
				iconCls : 'page_save',
				tooltip : '保存',
				handler : function() {
					if (entity == "OtherInfo") {
						var otherName = appform.getValueByName("otherName");
						if (otherName == null || otherName == "") {
							ExtUtil.alert({
										msg : "请添加数据后进行保存！"
									});
							return;
						}
					}
					EventManager.frm_save(appform, {
						beforeMake : function(formData) {
							var id = self.baseFormPanel.getValueByName('id');
							var baseId = self.baseFormPanel.getValueByName('baseId');
							if (entity == "OtherInfo") {
								formData.formType = ATTACHMENT_FORMTYPE.FType_28;
								formData.custType = 0;
							}
							if(entity=="GuaCustomer"){
								var formId = self.formIdMgr.GuaCustomerFrmId;
								var GuaCustomerFrmPnl = Ext.getCmp(formId);
								var guaId = GuaCustomerFrmPnl.getValueByName("id");
								formData.id = guaId;
							}else {
//								formData.id = id;
							}
							formData.customerId = id;
							
							
							formData.baseId = baseId;
							formData.custType = 0;
						},
						sfn : function(formData) {
							FormUtil.disabledFrm(appform);
							var id = self.baseFormPanel.getValueByName('id');
							var baseId = self.baseFormPanel.getValueByName('baseId');
							if (entity == "OtherInfo") {
								appgrid.reload({
											customerId : id,
											id : id,
											baseId:baseId,
											custType : 0
										});
							} else {
								appgrid.reload({
											customerId : id,baseId:baseId,
											custType : 0
										});
							}
							var Fields = appform.findFieldsByName("customerId");
							EventManager.frm_reset(appform, Fields);
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
					EventManager.frm_reset(appform);
					FormUtil.enableFrm(appform);
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
					var id = appgrid.getSelId();
					if (id != null) {
						FormUtil.enableFrm(appform);
					} else {
						ExtUtil.alert({
									msg : " 请选择表格中的数据"
								});
						return;
					}
					self.LoadAf(entity, {id : id});
				}
			}, {
				text : Btn_Cfgs.RESET_BTN_TXT,
				iconCls : Btn_Cfgs.RESET_CLS,
				handler : function() {
					var Fields = appform.findFieldsByName("id,customerId");
					EventManager.frm_reset(appform, Fields);
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
								cmpt : appgrid,
								params:{baseId:baseId}
							});
				}
			}];
			var toolBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : barItems
					});
			return toolBar;
		},

		/**
		 * 组装grid
		 */
		cmpGrid : function(itemId, structure_1, entity) {
			var self = this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
						id : itemId,
						structure : structure_1,
						url : './crm' + entity + '_list.action',
						needPage : false,
						height : 135,
						isLoad : false,
						border : false,
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
			return appgrid_1
		},
		/**
		 * 组装form面板
		 */
		cmptAppForm : function(layout_fields, entity, width, height,
				formDiyContainer) {
			var _this = this;
			var formItemd = this.formIdMgr;
			var formId = null;
			var formType = null;
			var formdiyCode = null;
			if (entity == "Address") {
				formId = formItemd.addressFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_13;
				formdiyCode = FORMDIY_IND.FORMDIY_ADDRESS;
			}
			if (entity == "Estate") {
				formId = formItemd.estateFrm;
				formType = ATTACHMENT_FORMTYPE.FType_14;
				formdiyCode = FORMDIY_IND.FORMDIY_ESTATE;
			}
			if (entity == "Work") {
				formId = formItemd.workFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_15;
				formdiyCode = FORMDIY_IND.FORMDIY_WORK;
			}
			if (entity == "CustCompany") {
				formId = formItemd.companyFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_16;
				formdiyCode = FORMDIY_IND.FORMDIY_CUSTCOMPANY;
			}
			if (entity == "Contactor") {
				formId = formItemd.contactorFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_17;
				formdiyCode = FORMDIY_IND.FORMDIY_CONTACTOR;
			}
			if (entity == "CreditInfo") {
				formId = formItemd.creditInfoFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_18;
				formdiyCode = FORMDIY_IND.FORMDIY_CREDITINFO;
			}
			if (entity == "OtherInfo") {
				formId = formItemd.otherInfoFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_28;
				formdiyCode = FORMDIY_IND.FORMDIY_CUSROMER_OTHERINFO;
			}
			if (entity == "GuaCustomer") {
				formId = formItemd.GuaCustomerFrmId;
				formType = ATTACHMENT_FORMTYPE.FType_35;
				formdiyCode = FORMDIY_IND.FORMDIY_CUST;
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
					formdiyCode : formdiyCode,
					/*
					 * 引用Code --> 对应 ts_Formdiy 中的业务引用键：recode
					 */
					formIdName : 'id',/* 对应表单中的ID Hidden 值 */
					container : formDiyContainer
					/* 自定义字段存放容器 */
				},
				url : './crm' + entity + '_save.action'
			};
			var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			appform.addListener("render", function() {
						FormUtil.disabledFrm(appform);
					});
			var dir = 'customerinfo_path';
			var aFM = this.createAttachMentFs(this, dir);
			this.detailPnlArray.push(aFM);
			var _this = this;
			if (entity == "Address") {
				_this.addressaFPnl = aFM;
			}
			if (entity == "Estate") {
				_this.estateaFPnl = aFM;
			}
			if (entity == "Work") {
				_this.workaFPnl = aFM;
			}
			if (entity == "CustCompany") {
				_this.companyaFPnl = aFM;
			}
			if (entity == "Contactor") {
				_this.contactoraFPnl = aFM;
			}
			if (entity == "CreditInfo") {
				_this.creditInfoaFPnl = aFM;
			}
			if (entity == "OtherInfo") {
				_this.otherInfoaFPnl = aFM;
			}
			if (entity == "GuaCustomer") {
				_this.GuaCustomerPnl = aFM;
			}
			appform.add(aFM);
			return appform
		},
		/**
		 * 配偶资料面板
		 */
		createConsortPanel : function(tab, itemId, width, height) {
			var wd = 135;
			var hid_id = FormUtil.getHidField({
						fieldLabel : '配偶ID',
						name : 'id'
					});
			var hid_baseId = FormUtil.getHidField({
						fieldLabel : '个人客户信息ID',
						name : 'customerId'
					});
			var bdat_name = FormUtil.getTxtField({
						fieldLabel : '姓名',
						name : 'name',
						"width" : wd,
						"maxLength" : "50",
						"allowBlank" : false
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
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : 10
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

			var txt_job = FormUtil.getTxtField({
						fieldLabel : '职务',
						name : 'job',
						"width" : wd,
						"allowBlank" : false,
						emptyText : '没有则填无',
						"maxLength" : "20"
					});

			var txt_phone = FormUtil.getTxtField({
						fieldLabel : '手机',
						name : 'phone',
						vtype : 'mobile',
						"width" : wd,
						"maxLength" : 20
					});

			var txt_contactTel = FormUtil.getTxtField({
						fieldLabel : '电话',
						vtype : 'telephone',
						name : 'contactTel',
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

			var txt_qqmsnNum = FormUtil.getTxtField({
						fieldLabel : 'QQ或MSN号码',
						name : 'qqmsnNum',
						vtype : 'qq',
						"width" : wd,
						"maxLength" : 50
					});

			var txt_workOrg = FormUtil.getTxtField({
						fieldLabel : '现工作单位',
						name : 'conjobunit',
						"width" : wd,
						"maxLength" : "50"
					});

			var txt_workAddress = FormUtil.getTxtField({
						fieldLabel : '单位地址',
						name : 'workAddress',
						"width" : 600,
						"maxLength" : "100"
					});

			var txt_coninterest = FormUtil.getTxtField({
						fieldLabel : '个人爱好',
						name : 'coninterest',
						"width" : wd,
						"maxLength" : "100"
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 600,
						height : 50
					});
			var fset_secuity = FormUtil.createLayoutFieldSet({
						title : '联系信息及爱好',
						width : 820
					}, [{
								cmns : FormUtil.CMN_THREE,
								fields : [txt_phone, txt_contactTel, txt_email]
							}, {
								cmns : FormUtil.CMN_THREE,
								fields : [txt_qqmsnNum, txt_coninterest,
										txt_workOrg]
							}, txt_workAddress, txt_remark]);

			var _this = this;
			var btnSave = new Ext.Button({
				text : '保存',
				handler : function() {
					EventManager.frm_save(_this.consortPnlId, {
								beforeMake : function(formData) {
									var id = _this.baseFormPanel
											.getValueByName('id');
									formData.customerId = id;
								},
								sfn : function(formData) {
									var id = formData.id;
									var fields = _this.consortPnlId
											.findFieldsByName('id')
									fields[0].setValue(id);

									var params = {
										formId : formData.id,
										formType : ATTACHMENT_FORMTYPE.FType_12,
										sysId : _this.params.sysId
									};
									_this.updateOrResetAttachment(
											_this.constattachMentFs, params);
								}
							});
				}
			});
			var btnReset = new Ext.Button({
						text : '重置',
						handler : function() {
							_this.BtnReset(_this.consortPnlId, [hid_id,
											hid_baseId]);
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
					formdiyCode : FORMDIY_IND.FORMDIY_CONSORT,
					/*
					 * 引用Code --> 对应 ts_Formdiy 中的业务引用键：recode
					 */
					formIdName : 'id',/* 对应表单中的ID Hidden 值 */
					container : formDiyContainer
					/* 自定义字段存放容器 */
				},
				url : './crmConsort_save.action'
			};
			var btnPanel = this.getBtnPanel([btnSave, btnReset]);
			var params = null;
			var formId = null;
			var dir = 'customerinfo_path';
			this.constattachMentFs = this.createAttachMentFs(this, dir);
			var layout_fields = [
					hid_baseId,
					hid_id,
					{
						cmns : FormUtil.CMN_THREE,
						fields : [bdat_name, bdat_birthday, int_age, txt_job,
								cbo_nation, cbo_degree, cbo_cardType,
								txt_cardNum, cbo_hometown]
					}, fset_secuity, formDiyContainer, txt_remark,
					this.constattachMentFs, btnPanel];
			this.consortPnlId = FormUtil
					.createLayoutFrm(frm_cfg, layout_fields);
			this.consortPnlId.addListener("afterrender", function() {
						var panel = Ext.getCmp(itemId);
						_this.setTabItemValue(panel);
					});
			return this.consortPnlId;
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
		 * 住宅资料面板
		 */
		createaddressPnlId : function(tab, itemId, width, height) {
			var self = this;
			var Address = "Address";
			var wd = 135;
			var structure_1 = [{
						header : '住宅id',
						name : 'id',
						hidden : true
					}, {
						header : '个人客户ID',
						name : 'customerId',
						width : wd,
						hidden : true
					}, {
						header : '住宅地址',
						name : 'address',
						width : 150
					}, {
						header : '默认通讯地址',
						name : 'isDefault',
						width : 80,
						renderer : Render_dataSource.FormdiyRender
					}, {
						header : '住宅类别',
						name : 'housType',
						width : 80,
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100007', val);
						}
					}, {
						header : '居住方式',
						name : 'resideType',
						width : 80,
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100008', val);
						}
					}, {
						header : '主要联系人',
						name : 'contactor',
						width : wd
					}, {
						header : '与本人关系',
						name : 'relation',
						width : wd
					}, {
						header : '主要联系人电话 ',
						name : 'tel',
						width : wd
					}, {
						header : '主要联系人手机',
						name : 'phone',
						width : wd
					}, {
						header : '邮编',
						name : 'zipcode',
						width : wd
					}, {
						header : '住宅居住人数',
						name : 'manCount',
						width : wd
					}];
			var appgrid_1 = this.cmpGrid(itemId, structure_1, Address);

			var txt_id = FormUtil.getHidField({
						fieldLabel : '住宅id',
						name : 'id',
						"width" : "125"
					});

			var txt_customerId = FormUtil.getHidField({
						fieldLabel : '个人客户ID',
						name : 'customerId',
						"width" : wd
					});

			var txt_address = FormUtil.getTxtField({
						fieldLabel : '住宅地址',
						name : 'address',
						"width" : 190,
						"allowBlank" : false
					});

			var rad_isDefault = FormUtil.getRadioGroup({
						fieldLabel : '默认通讯地址',
						name : 'isDefault',
						"width" : 100,
						allowBlank : false,
						items : [{
									boxLabel : "否",
									name : "isDefault",
									inputValue : 0
								}, {
									boxLabel : "是",
									name : "isDefault",
									inputValue : 1
								}]
					});

			var cbo_housType = FormUtil.getRCboField({
						fieldLabel : '住宅类别',
						name : 'housType',
						"width" : wd,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						restypeId : '100007'
					});

			var cbo_resideType = FormUtil.getRCboField({
						fieldLabel : '居住方式',
						name : 'resideType',
						"width" : wd,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						restypeId : '100008'
					});

			var txt_contactor = FormUtil.getTxtField({
						fieldLabel : '主要联系人',
						name : 'contactor',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : "30"
					});

			var txt_relation = FormUtil.getTxtField({
						fieldLabel : '与本人关系',
						name : 'relation',
						"width" : wd,
						"maxLength" : "50"
					});

			var txt_tel = FormUtil.getTxtField({
						fieldLabel : '主要联系人电话 ',
						vtype : 'telephone',
						name : 'tel',
						"width" : wd,
						"maxLength" : "20"
					});

			var txt_phone = FormUtil.getTxtField({
						fieldLabel : '主要联系人手机',
						name : 'phone',
						vtype : 'mobile',
						"width" : wd,
						"maxLength" : "20"
					});

			var txt_zipcode = FormUtil.getTxtField({
						fieldLabel : '邮编',
						name : 'zipcode',
						"width" : wd,
						vtype : 'postcode',
						"maxLength" : "10"
					});

			var int_manCount = FormUtil.getIntegerField({
						fieldLabel : '住宅居住人数',
						name : 'manCount',
						"width" : wd,
						value : 0.00,
						"maxLength" : 10,
						unitText : '人'
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 600,
						height : 50
					});

			var layout_fields = [txt_id, txt_customerId, {
						cmns : FormUtil.CMN_TWO_LEFT,
						fields : [txt_address, rad_isDefault]
					}, {
						cmns : FormUtil.CMN_THREE,
						fields : [cbo_housType, cbo_resideType, txt_contactor,
								txt_relation, txt_tel, txt_phone, txt_zipcode,
								int_manCount]
					}, formDiyContainer, txt_remark];

			var appform_2 = this.cmptAppForm(layout_fields, Address, width,
					height, formDiyContainer);

			txt_address.setWidth(460);
			/**
			 * 工具栏
			 */

			var toolBar = this.createToolBar(appform_2, appgrid_1, Address);

			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_2]
					})
			return appgridpanel_1;
		},
		/**
		 * 房产物业资料
		 */
		createestatePnlId : function(tab, itemId, width, height) {
			var self = this;
			var Estate = "Estate";
			var wd = 90;
			var fwd = 125;
			var structure_1 = [{
						header : '个人客户ID',
						name : 'customerId',
						hidden : true
					}, {
						header : '房产地址',
						name : 'address',
						width : 150
					}, {
						header : '购买方式',
						name : 'buyType',
						"width" : 80,
						renderer : Render_dataSource.buyTypeRender
					}, {
						header : '购买日期',
						name : 'buyDate',
						"width" : wd
					}, {
						header : '建筑年份',
						"width" : wd,
						name : 'houseYear',
						renderer : function(val) {
							return val + '年';
						}
					}, {
						header : '建筑面积(平方米）',
						"width" : wd,
						name : 'area'
					}, {
						header : '购买价格(万元)',
						"width" : wd,
						name : 'price',
						renderer : Render_dataSource.wmoneyRender
					}, {
						header : '按揭银行',
						"width" : wd,
						name : 'mortBank'
					}, {
						header : '贷款年限',
						"width" : wd,
						name : 'loanYear'
					}, {
						header : '每月供款',
						"width" : wd,
						name : 'contributions',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '贷款总额',
						"width" : wd,
						name : 'loanAmount',
						renderer : Render_dataSource.wmoneyRender
					}, {
						header : '尚欠余额',
						"width" : wd,
						name : 'zAmount',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '已付分期',
						"width" : wd,
						name : 'installments'
					}, {
						header : '总分期期数',
						"width" : wd,
						name : 'totalInstall'
					}, {
						header : '居住时长',
						"width" : wd,
						name : 'runtime'
					}, {
						header : '受供养人数',
						"width" : wd,
						name : 'supCount'
					}, {
						header : '与谁居住',
						"width" : wd,
						name : 'whoLived'
					}];
			var appgrid_1 = this.cmpGrid(itemId, structure_1, Estate);

			var hid_id = FormUtil.getTxtField({
						fieldLabel : 'id',
						name : 'id',
						"width" : fwd,
						hidden : true,
						"maxLength" : "200"
					});

			var hid_customerId = FormUtil.getTxtField({
						fieldLabel : '个人客户ID',
						name : 'customerId',
						"width" : fwd,
						hidden : true,
						"maxLength" : "200"
					});

			var txt_address = FormUtil.getTxtField({
						fieldLabel : '房产地址',
						name : 'address',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : "200"
					});

			var rad_buyType = FormUtil.getRadioGroup({
						fieldLabel : '购买方式',
						name : 'buyType',
						"width" : fwd,
						"allowBlank" : false,
						"maxLength" : "200",
						"items" : [{
									"boxLabel" : "按揭",
									"name" : "buyType",
									inputValue : 0
								}, {
									"boxLabel" : "一次性",
									"name" : "buyType",
									inputValue : 1
								}]
					});

			var bdat_buyDate = FormUtil.getDateField({
						fieldLabel : '购买日期',
						name : 'buyDate',
						"width" : fwd,
						"allowBlank" : false
					});

			var int_houseYear = FormUtil.getIntegerField({
						fieldLabel : '建筑年份',
						name : 'houseYear',
						"width" : fwd,
						maxLength : 4,
						minLength : 4,
						"maxLength" : 10
					});

			var dob_area = FormUtil.getIntegerField({
						fieldLabel : '建筑面积(平方米)',
						name : 'area',
						"width" : fwd,
						value : 0,
						"decimalPrecision" : "2"
					});

			var dob_price = FormUtil.getMoneyField({
						fieldLabel : '购买价格',
						name : 'price',
						"width" : fwd + 30,
						value : 0.00,
						unitText : '万元'
					});

			var txt_mortBank = FormUtil.getTxtField({
						fieldLabel : '按揭银行',
						name : 'mortBank',
						"width" : fwd,
						"maxLength" : "100"
					});

			var txt_loanYear = FormUtil.getIntegerField({
						fieldLabel : '贷款年限',
						name : 'loanYear',
						"width" : fwd + 10,
						"maxLength" : "10",
						value : 0,
						unitText : '年'
					});

			var dob_contributions = FormUtil.getMoneyField({
						fieldLabel : '每月供款',
						name : 'contributions',
						"width" : fwd + 15,
						value : 0.00,
						unitText : '元'
					});

			var dob_loanAmount = FormUtil.getMoneyField({
						fieldLabel : '贷款总额',
						name : 'loanAmount',
						"width" : fwd + 30,
						value : 0.00,
						unitText : '万元'
					});

			var dob_zAmount = FormUtil.getMoneyField({
						fieldLabel : '尚欠余额',
						name : 'zAmount',
						"width" : fwd + 30,
						value : 0.00,
						unitText : '万元'
					});

			var int_installments = FormUtil.getIntegerField({
						fieldLabel : '已付分期',
						name : 'installments',
						"width" : fwd + 10,
						"maxLength" : 10,
						value : 0,
						unitText : '期'
					});

			var int_totalInstall = FormUtil.getIntegerField({
						fieldLabel : '总分期期数',
						name : 'totalInstall',
						"width" : fwd + 10,
						"maxLength" : 10,
						value : 0,
						unitText : '期'
					});

			var txt_runtime = FormUtil.getIntegerField({
						fieldLabel : '居住时长',
						name : 'runtime',
						"width" : fwd + 15,
						"maxLength" : "10",
						value : 0,
						unitText : '年'
					});

			var int_supCount = FormUtil.getIntegerField({
						fieldLabel : '受供养人数',
						name : 'supCount',
						"width" : fwd + 10,
						"maxLength" : 10,
						value : 0,
						unitText : '人'
					});

			var txt_whoLived = FormUtil.getTxtField({
						fieldLabel : '与谁居住',
						name : 'whoLived',
						"width" : fwd,
						"maxLength" : "50"
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 600,
						height : 50
					});

			var layout_fields = [hid_id, hid_customerId, {
						cmns : FormUtil.CMN_TWO_LEFT,
						fields : [txt_address, rad_buyType]
					}, {
						cmns : FormUtil.CMN_THREE,
						fields : [bdat_buyDate, int_houseYear, dob_area,
								dob_price, txt_mortBank, txt_loanYear,
								dob_contributions, dob_loanAmount, dob_zAmount,
								int_installments, int_totalInstall,
								txt_runtime, int_supCount, txt_whoLived]
					}, formDiyContainer, txt_remark];

			var appform_3 = this.cmptAppForm(layout_fields, Estate, width,
					height, formDiyContainer);

			var toolBar = this.createToolBar(appform_3, appgrid_1, Estate);

			txt_address.setWidth(460);
			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_3],
						autoScroll : true
					});
			return appgridpanel_1;
		},

		/**
		 * 创建职业面板
		 */
		createworkPnlId : function(tab, itemId, width, height) {
			var Work = "Work";
			var self = this;
			var structure_1 = [{
						header : '个人客户ID',
						name : 'customerId',
						width : 125,
						hidden : true
					}, {
						header : '单位名称',
						name : 'orgName',
						width : 125
					}, {
						header : '所在部门',
						name : 'dept',
						width : 125
					}, {
						header : '职务',
						name : 'job',
						width : 80
					}, {
						header : '服务年数',
						name : 'syears',
						width : 60
					}, {
						header : '每月收入',
						name : 'income',
						width : 100,
						renderder : Render_dataSource.moneyRender
					}, {
						header : '每月支薪日(日)',
						name : 'payDay',
						width : 125,
						renderer : Render_dataSource.moneyRender
					}, {
						header : '支付方式',
						name : 'payment',
						width : 100
					}, {
						header : '行业类别',
						name : 'industry',
						width : 125
					}, {
						header : '单位地址',
						name : 'address',
						width : 150
					}, {
						header : '邮编',
						name : 'zipcode',
						width : 100
					}, {
						header : '单位性质',
						name : 'nature',
						width : 125,
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100009', val)
						}
					}, {
						header : '单位电话',
						name : 'tel',
						width : 135
					}, {
						header : '单位传真',
						name : 'fax',
						width : 125
					}, {
						header : '网址',
						name : 'url',
						width : 150
					}];
			var appgrid_1 = this.cmpGrid(itemId, structure_1, Work);

			var hid_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id',
						hidden : true,
						"width" : 125
					});

			var hid_customerId = FormUtil.getHidField({
						fieldLabel : '个人客户ID',
						name : 'customerId',
						hidden : true,
						"width" : 125
					});

			var txt_orgName = FormUtil.getTxtField({
						fieldLabel : '单位名称',
						name : 'orgName',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : "100"
					});

			var txt_dept = FormUtil.getTxtField({
						fieldLabel : '所在部门',
						name : 'dept',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : "100"
					});

			var txt_job = FormUtil.getTxtField({
						fieldLabel : '职务',
						name : 'job',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : "50"
					});

			var int_syears = FormUtil.getIntegerField({
						fieldLabel : '服务年数',
						name : 'syears',
						"width" : 135,
						"allowBlank" : false,
						value : 0,
						"maxLength" : 10,
						unitText : '年'
					});

			var int_income = FormUtil.getMoneyField({
						fieldLabel : '每月收入',
						name : 'income',
						"width" : 135,
						value : 0.00,
						"allowBlank" : false,
						unitText : '元'
					});

			var int_payDay = FormUtil.getIntegerField({
						fieldLabel : '每月支薪日',
						name : 'payDay',
						"width" : 135,
						value : 0,
						"maxLength" : 10,
						unitText : '日'
					});

			var txt_payment = FormUtil.getTxtField({
						fieldLabel : '支付方式',
						name : 'payment',
						"width" : 125,
						"maxLength" : "50"
					});

			var txt_industry = FormUtil.getTxtField({
						fieldLabel : '行业类别',
						name : 'industry',
						"width" : 125,
						"maxLength" : "50"
					});

			var txt_address = FormUtil.getTxtField({
						fieldLabel : '单位地址',
						name : 'address',
						"width" : 125,
						"maxLength" : "200"
					});

			var txt_zipcode = FormUtil.getTxtField({
						fieldLabel : '邮编',
						name : 'zipcode',
						vtype : 'postcode',
						"width" : 125,
						"maxLength" : "10"
					});

			var txt_nature = FormUtil.getRCboField({
						fieldLabel : '单位性质',
						name : 'nature',
						register : REGISTER.GvlistDatas,
						restypeId : '100009',
						"width" : 125
					});

			var txt_tel = FormUtil.getTxtField({
						fieldLabel : '单位电话',
						name : 'tel',
						vtype : 'telephone',
						"width" : 125,
						"maxLength" : "20"
					});

			var txt_fax = FormUtil.getTxtField({
						fieldLabel : '单位传真',
						name : 'fax',
						"width" : 125,
						"maxLength" : "20"
					});

			var txt_url = FormUtil.getTxtField({
						fieldLabel : '网址',
						name : 'url',
						vtype : 'url',
						"width" : 125,
						"maxLength" : "100"
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 600,
						height : 50
					});
			var layout_fields = [hid_id, hid_customerId, {
						cmns : FormUtil.CMN_TWO_LEFT,
						fields : [txt_orgName, txt_dept]
					}, {
						cmns : FormUtil.CMN_THREE,
						fields : [txt_job, int_syears, int_income, int_payDay,
								txt_payment, txt_industry, txt_address,
								txt_zipcode, txt_nature, txt_tel, txt_fax,
								txt_url]
					}, formDiyContainer, txt_remark];

			var appform_1 = this.cmptAppForm(layout_fields, Work, width,
					height, formDiyContainer);

			txt_orgName.setWidth(450);

			var toolBar = this.createToolBar(appform_1, appgrid_1, Work);

			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1],
						autoScroll : true
					})
			return appgridpanel_1;
		},
		/**
		 * 个人旗下/企业关联公司信息
		 */

		createcompanyPnlId : function(tab, itemId, width, height) {
			var self = this;
			var CustCompany = "CustCompany";
			var structure_1 = [{
						header : '客户类型',
						name : 'custType',
						width : 80,
						renderer : Render_dataSource.custTypeRender
					}, {
						header : '个人/企业客户ID',
						name : 'customerId',
						hidden : true
					}, {
						header : '公司名称',
						name : 'cconame'
					}, {
						header : '公司性质',
						name : 'ccokind',
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100009', val);
						}
					}, {
						header : '法人',
						name : 'legal'
					}, {
						header : '营业执照号',
						name : 'offNum'
					}, {
						header : '组织机构代码证',
						width : 120,
						name : 'orgNum'
					}, {
						header : '国税编号',
						name : 'nationNum',
						width : 120
					}, {
						header : '地税编号',
						name : 'landNum',
						width : 120
					}, {
						header : '成立时间',
						name : 'regDate'
					}, {
						header : '注册资本',
						name : 'regcaptial'
					}, {
						header : '注册币种',
						name : 'currency',
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100014', val);
						}
					}, {
						header : '联系人',
						name : 'linkman'
					}, {
						header : '联系电话',
						name : 'ccoTel'
					}, {
						header : '经营场所',
						name : 'premises'
					}, {
						header : '月供/月租金',
						name : 'monthly',
						renderder : Render_dataSource.moneyRender
					}, {
						header : '员工人数(人)',
						name : 'empCount',
						width : 80
					}, {
						header : '全年盈利',
						name : 'profit'
					}, {
						header : '经营地址',
						name : 'ccoaaddress'
					}];
			var appgrid_1 = this.cmpGrid(itemId, structure_1, CustCompany);
			var txt_cconame = FormUtil.getTxtField({
						fieldLabel : '公司名称',
						"allowBlank" : false,
						name : 'cconame',
						"width" : 135
					});

			var rcbo_ccokind = FormUtil.getRCboField({
						fieldLabel : '公司性质',
						name : 'ccokind',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50,
						register : REGISTER.GvlistDatas,
						restypeId : '100009'
					});

			var txt_legal = FormUtil.getTxtField({
						fieldLabel : '法人',
						name : 'legal',
						"width" : 145,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_offNum = FormUtil.getTxtField({
						fieldLabel : '营业执照号',
						name : 'offNum',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_orgNum = FormUtil.getTxtField({
						fieldLabel : '组织机构代码证',
						name : 'orgNum',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var bdat_regDate = FormUtil.getDateField({
						fieldLabel : '成立时间',
						name : 'regDate',
						"width" : 145,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_regcaptial = FormUtil.getMoneyField({
						fieldLabel : '注册资本',
						name : 'regcaptial',
						"width" : 135,
						value : 0.00,
						"maxLength" : 50
					});

			var rcbo_currency = FormUtil.getRCboField({
						fieldLabel : '注册币种',
						name : 'currency',
						"width" : 135,
						"maxLength" : 50,
						register : REGISTER.GvlistDatas,
						restypeId : '100014'
					});

			var txt_linkman = FormUtil.getTxtField({
						fieldLabel : '联系人',
						name : 'linkman',
						"width" : 135,
						"maxLength" : 50
					});

			var txt_ccoTel = FormUtil.getTxtField({
						fieldLabel : '联系电话',
						vtype : 'telephone',
						name : 'ccoTel',
						"width" : 135,
						"maxLength" : 50
					});

			var txt_premises = FormUtil.getTxtField({
						fieldLabel : '经营场所',
						name : 'premises',
						"width" : 135,
						"maxLength" : 50
					});

			var txt_monthly = FormUtil.getMoneyField({
						fieldLabel : '月供/月租金',
						name : 'monthly',
						"width" : 145,
						value : 0.00,
						"maxLength" : 50,
						unitText : '元'
					});

			var txt_empCount = FormUtil.getIntegerField({
						fieldLabel : '员工人数',
						name : 'empCount',
						"width" : 145,
						value : 0,
						"maxLength" : 50,
						unitText : '人'
					});

			var txt_profit = FormUtil.getMoneyField({
						fieldLabel : '全年盈利',
						name : 'profit',
						"width" : 145,
						value : 0.00,
						"maxLength" : 50,
						unitText : '元'
					});

			var txt_ccoaaddress = FormUtil.getTxtField({
						fieldLabel : '经营地址',
						name : 'ccoaaddress',
						"width" : 460,
						"maxLength" : 50
					});

			var rad_custType = FormUtil.getRadioGroup({
						fieldLabel : '客户类型',
						name : 'custType',
						"width" : 135,
						"maxLength" : 50,
						"allowBlank" : false,
						"items" : [{
									"boxLabel" : "个人",
									"name" : "custType",
									"inputValue" : 0
								}, {
									"boxLabel" : "企业",
									"name" : "custType",
									"inputValue" : 1
								}]
					});

			var hid_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id',
						"width" : 135,
						"maxLength" : 50
					});

			var hid_customerId = FormUtil.getHidField({
						fieldLabel : '个人/企业客户ID',
						name : 'customerId',
						"width" : 135,
						"maxLength" : 50
					});

			var txt_nationNum = FormUtil.getTxtField({
						fieldLabel : '国税编号',
						name : 'nationNum',
						"width" : 135,
						"maxLength" : 50
					});

			var txt_landNum = FormUtil.getTxtField({
						fieldLabel : '地税编号',
						name : 'landNum',
						"width" : 145,
						"maxLength" : 50
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 600,
						height : 50
					});
			var layout_fields = [hid_id, hid_customerId, {
				cmns : FormUtil.CMN_THREE,
				fields : [txt_cconame, rcbo_ccokind, txt_legal, txt_offNum,
						txt_orgNum, bdat_regDate, rad_custType, txt_regcaptial,
						rcbo_currency]
			}, {
				cmns : FormUtil.CMN_TWO,
				fields : [txt_ccoTel, txt_premises]
			}, {
				cmns : FormUtil.CMN_THREE,
				fields : [txt_monthly, txt_empCount, txt_profit]
			}, {
				cmns : FormUtil.CMN_THREE,
				fields : [txt_linkman, txt_nationNum, txt_landNum]
			}, txt_ccoaaddress, formDiyContainer, txt_remark];

			var appform_1 = this.cmptAppForm(layout_fields, CustCompany, width,
					height, formDiyContainer);

			txt_premises.setWidth(470);

			var toolBar = this.createToolBar(appform_1, appgrid_1, CustCompany);

			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1],
						autoScroll : true
					})
			return appgridpanel_1;
		},
		/**
		 * @author 李听 
		 * 第三方担保人(个人资料详情)
		 */
		
		createGuaCustomerPnlId : function(tab, itemId, width, height) {
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
			var appgrid_1 = new Ext.ux.grid.AppGrid({
						structure : structure_1,
						needPage : false,
						isLoad : false,
						keyField : 'id'
					});
			var appgrid_1 = this.cmpGrid(itemId, structure_1, GuaCustomer);

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
					callback : function(id,data){
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
			
			var appform_1 = this.cmptAppForm(layout_fields, GuaCustomer, width,
					height,formDiyContainer);
			var toolBar = this.createToolBar(appform_1, appgrid_1, GuaCustomer);
			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1],
						autoScroll : true
					});
			return appgridpanel_1;
		},

		/**
		 * 联系人资料
		 */
		createcontactorPnlId : function(tab, itemId, width, height) {
			var self = this;
			var Contactor = "Contactor";
			var structure_1 = [{
						header : '个人客户ID',
						hidden : true,
						name : 'customerId'
					}, {
						header : '姓名',
						name : 'name'
					}, {
						header : '身份证号码',
						name : 'idcard'
					}, {
						header : '关系',
						name : 'relation'
					}, {
						header : '出生日期',
						name : 'birthday'
					}, {
						header : '年龄(岁)',
						name : 'age'
					}, {
						header : '手机',
						name : 'phone'
					}, {
						header : '住宅电话',
						name : 'tel'
					}, {
						header : '住宅住址',
						name : 'address'
					}, {
						header : '单位名称 ',
						name : 'orgName'
					}, {
						header : '职务',
						name : 'job'
					}, {
						header : '单位电话',
						name : 'orgTel'
					}];
			var appgrid_1 = this.cmpGrid(itemId, structure_1, Contactor);

			var hid_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id',
						"width" : 135,
						"maxLength" : 50
					});

			var hid_customerId = FormUtil.getHidField({
						fieldLabel : '个人客户ID',
						name : 'customerId',
						"width" : 135,
						"maxLength" : 50
					});

			var txt_name = FormUtil.getTxtField({
						fieldLabel : '姓名',
						name : 'name',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_idcrad = FormUtil.getTxtField({
						fieldLabel : '身份证号',
						vtype : 'IDCard',
						name : 'idcrad',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_relation = FormUtil.getTxtField({
						fieldLabel : '关系',
						name : 'relation',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var bdat_birthday = FormUtil.getDateField({
						fieldLabel : '出生日期',
						name : 'birthday',
						"width" : 135
					});

			var int_age = FormUtil.getIntegerField({
						fieldLabel : '年龄',
						vtype : 'age',
						name : 'age',
						"width" : 145,
						value : 0,
						"maxLength" : 50,
						unitText : '岁'
					});

			var txt_phone = FormUtil.getTxtField({
						fieldLabel : '手机',
						vtype : 'mobile',
						name : 'phone',
						"width" : 135,
						"maxLength" : 50
					});

			var txt_tel = FormUtil.getTxtField({
						fieldLabel : '住宅电话',
						name : 'tel',
						vtype : 'telephone',
						"width" : 135,
						"maxLength" : 50
					});

			var txt_address = FormUtil.getTxtField({
						fieldLabel : '住宅住址',
						name : 'address',
						"width" : 475,
						"maxLength" : 200
					});

			var txt_orgName = FormUtil.getTxtField({
						fieldLabel : '单位名称 ',
						name : 'orgName',
						"width" : 475,
						"maxLength" : 50
					});

			var txt_job = FormUtil.getTxtField({
						fieldLabel : '职务',
						name : 'job',
						"width" : 135,
						"maxLength" : 50
					});

			var txt_orgTel = FormUtil.getTxtField({
						fieldLabel : '单位电话',
						name : 'orgTel',
						vtype : 'telephone',
						"width" : 135,
						"maxLength" : 50
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						width : 475,
						height : 50
					});
			var layout_fields = [hid_id, hid_customerId, {
				cmns : FormUtil.CMN_THREE,
				fields : [txt_name, txt_idcrad, txt_relation, bdat_birthday,
						int_age, txt_phone, txt_tel, txt_job, txt_orgTel]
			}, txt_orgName, txt_address, txt_remark, formDiyContainer];

			var appform_1 = this.cmptAppForm(layout_fields, Contactor, width,
					height, formDiyContainer);

			var toolBar = this.createToolBar(appform_1, appgrid_1, Contactor);

			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1],
						autoScroll : true
					});
			return appgridpanel_1;
		},
		/**
		 * 个人信用资料
		 */

		createcreditInfoPnlId : function(tab, itemId, width, height) {
			var CreditInfo = "CreditInfo";
			var self = this;
			var structure_1 = [{
						header : '个人客户ID',
						name : 'customerId',
						hidden : true
					}, {
						header : '信用类型',
						name : 'creitType',
						renderer : function(val) {
							return Render_dataSource
									.gvlistRender('100010', val);
						}
					}, {
						header : '数量',
						name : 'count'
					}, {
						header : '总贷款额度',
						name : 'totalAmount',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '每月供款',
						name : 'monthAmount',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '总贷款余额',
						name : 'balance',
						renderer : Render_dataSource.moneyRender
					}, {
						header : '信用描述',
						width : 150,
						name : 'remark'
					}];
			var appgrid_1 = this.cmpGrid(itemId, structure_1, CreditInfo);

			var hid_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id',
						"width" : 135,
						"maxLength" : 50
					});

			var hid_customerId = FormUtil.getHidField({
						fieldLabel : '个人客户ID',
						name : 'customerId',
						"width" : 135,
						"maxLength" : 50
					});

			var rcbo_creitType = FormUtil.getRCboField({
						fieldLabel : '信用类型',
						name : 'creitType',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50,
						register : REGISTER.GvlistDatas,
						restypeId : '100010'
					});

			var txt_count = FormUtil.getMoneyField({
						fieldLabel : '数量',
						name : 'count',
						"width" : 135,
						"allowBlank" : false,
						"maxLength" : 50
					});

			var txt_totalAmount = FormUtil.getMoneyField({
						fieldLabel : '总贷款额度',
						name : 'totalAmount',
						"width" : 145,
						value : 0.00,
						"allowBlank" : false,
						"maxLength" : 50,
						unitText : '元'
					});

			var txt_monthAmount = FormUtil.getMoneyField({
						fieldLabel : '每月供款',
						name : 'monthAmount',
						"width" : 145,
						value : 0.00,
						"maxLength" : 50,
						unitText : '元'
					});

			var txt_balance = FormUtil.getMoneyField({
						fieldLabel : '总贷款余额',
						name : 'balance',
						"width" : 145,
						value : 0.00,
						"maxLength" : 50,
						unitText : '元'
					});
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var txt_remark = FormUtil.getTAreaField({
						fieldLabel : '信用描述',
						name : 'remark',
						width : 600,
						height : 50
					});
			var layout_fields = [hid_id, hid_customerId, {
				cmns : FormUtil.CMN_THREE,
				fields : [rcbo_creitType, txt_count, txt_totalAmount,
						txt_monthAmount, txt_balance]
			}, formDiyContainer, txt_remark];

			var appform_1 = this.cmptAppForm(layout_fields, CreditInfo, width,
					height, formDiyContainer);

			var toolBar = this.createToolBar(appform_1, appgrid_1, CreditInfo);

			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid_1, toolBar, appform_1],
						autoScroll : true
					})
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
			var appgrid = this.cmpGrid(itemId, structure, OtherInfo);
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

			var appform = this.cmptAppForm(layout_fields, OtherInfo, width,
					height, formDiyContainer);
			var toolBar = this.createToolBar(appform, appgrid, OtherInfo);
			var appgridpanel_1 = new Ext.Panel({
						items : [appgrid, toolBar, appform],
						autoScroll : true
					})
			return appgridpanel_1;
		},
		/**
		 * 
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