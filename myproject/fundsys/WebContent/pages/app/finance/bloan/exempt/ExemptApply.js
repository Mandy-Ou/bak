/**
 * 息费豁免申请页面
 * 
 * @author 程明卫
 * @date 2013-09-14
 */
define(function(require, exports) {
	exports.viewUI = {
		parentPanel : null,/**/
		appMainPanel : null,
		customerPanel : null,
		applyPanel : null,
		gopinionGrid : null,
		attachMentFs : null,
		customerDialog : null,/* 展期客户选择弹窗 */
		freeGrid : null,/* 放款手续费豁免Grid */
		prepaymentGrid : null,/* 提前还款息费豁免Grid */
		planGrid : null,/* 正常/逾期息费豁免Grid */
		currGrid : null,/* 当前显示的Grid */
		params : null,
		uuid : Cmw.getUuid(),/* 用于新增时，临时代替申请单ID */
		applyId : null,/* 申请单ID */
		seDateDisplayId : Ext.id(null, 'seDateDisplayId'),/* 显示展期期限的控件ID */
		btnIdObj : {
			btnChoseCust : Ext.id(null, 'btnChoseCust'),/* 展期客户选择 */
			btnAddGopinion : Ext.id(null, 'btnAddGopinion'),/* 添加意见 */
			btnEditGopinion : Ext.id(null, 'btnEditGopinion'),/* 修改意见 */
			btnDelGopinion : Ext.id(null, 'btnDelGopinion'),/* 删除意见 */
			btnTempSave : Ext.id(null, 'btnTempSave'),/* 暂存申请单 */
			btnSave : Ext.id(null, 'btnSave')
/* 提交申请单 */
		},
		/**
		 * 获取主面板
		 * 
		 * @param tab
		 *            Tab 面板对象
		 * @param params
		 *            由菜单点击所传过来的参数 {tabId : tab 对象ID,apptabtreewinId :
		 *            系统程序窗口ID,sysid : 系统ID}
		 */
		getMainUI : function(parentPanel, params) {
			this.setParams(parentPanel, params);
			if (!this.appMainPanel) {
				this.createCmpts();
			}
			return this.appMainPanel;
		},
		/**
		 * 创建组件
		 */
		createCmpts : function() {
			this.createCustomerPanel();
			this.createFormPanel();
			var height = this.parentPanel.getHeight() || 550;
			this.appMainPanel = new Ext.Panel({
						autoScroll : true,
						height : height,
						items : [this.customerPanel, this.applyPanel]
					});
		},
		createCustomerPanel : function() {
			var htmlArrs_1 = [
					'<tr><th col="custType">客户类型</th> <td col="custType" >&nbsp;</td><th col="custName">客户名称</th> <td col="custName" >&nbsp;</td><th col="cardNum" id="label_cardType">证件类型</th> <td col="cardNum" >&nbsp;</td></tr>',
					'<tr><th col="contractCode">借款合同号</th> <td col="contractCode" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th col="payDate">贷款期限</th> <td col="payDate" >&nbsp;</td></tr>',
					'<tr><th col="borBank">借款银行</th> <td col="borBank" >&nbsp;</td><th col="borAccount">借款账号</th> <td col="borAccount" >&nbsp;</td><th col="accName">帐户户名</th> <td col="accName" >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 90,
				/* title : '#TITLE#', */
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './fcExempt_getContract.action',
				prevUrl : './fcExempt_getContract.action',
				nextUrl : './fcExempt_getContract.action',
				params : {
					contractId : -1
				},
				callback : {
					sfn : function(jsonData) {
						jsonData["custType"] = Render_dataSource
								.custTypeRender(jsonData["custType"]);
						Ext.get('label_cardType').update(jsonData["cardType"]);
						var rateType = jsonData["rateType"];
						var rateTypeName = Render_dataSource
								.rateTypeRender(rateType);
						jsonData["rate"] = jsonData["rate"]
								+ "%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("
								+ rateTypeName + ")</span>";
						var mgrtypeName = Render_dataSource
								.mgrtypeRender(jsonData["mgrtype"]);
						jsonData["mrate"] = jsonData["mrate"]
								+ "%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("
								+ mgrtypeName + ")</span>";
						jsonData["payDate"] = jsonData["payDate"] + "至"
								+ jsonData["endDate"];
						var appAmount = jsonData["appAmount"];
						jsonData["appAmount"] = Cmw.getThousandths(appAmount)
								+ "元&nbsp;&nbsp;<br/><span style='color:red;font-weight:bold;'>("
								+ Cmw.cmycurd(appAmount) + ")</span>";
					}
				}
			}];
			var btnChoseCustHtml = this.getButtonHml(
					this.btnIdObj.btnChoseCust, '选择客户');
			this.customerPanel = new Ext.ux.panel.DetailPanel({
						title : '豁免客户资料' + btnChoseCustHtml,
						collapsible : true,
						// collapsed : false,
						border : false,
						// width: 780,
						isLoad : false,
						detailCfgs : detailCfgs_1
					});

			var _this = this;
			this.customerPanel.addListener('afterrender', function(panel) {
						_this.addListenersToCustButtons([{
									btnId : _this.btnIdObj.btnChoseCust,
									fn : function(e, targetEle, obj) {
										/* 选择展期客户 */
										_this.openCustomerDialog();
									}
								}]);
					});
			this.customerPanel.on('expand', function(cmpt) {
						_this.doResize();
					});
			this.customerPanel.on('collapse', function(cmpt) {
						_this.doResize();
					});
		},
		createFormPanel : function() {
			var _this = this;
			var txt_id = FormUtil.getHidField({
						fieldLabel : '豁免申请单ID',
						name : 'id'
					});

			var txt_contractId = FormUtil.getHidField({
						fieldLabel : '借款合同ID',
						name : 'contractId'
					});

			var txt_procId = FormUtil.getHidField({
						fieldLabel : '流程实例ID',
						name : 'procId'
					});

			var txt_breed = FormUtil.getHidField({
						fieldLabel : '子业务流程ID',
						name : 'breed'
					});

			var txt_code = FormUtil.getHidField({
						fieldLabel : '编号',
						name : 'code'
					});

			var txt_totalAmount = FormUtil.getHidField({
						fieldLabel : '豁免息费合计',
						name : 'totalAmount'
					});

			var txt_managerId = FormUtil.getHidField({
						fieldLabel : '经办人ID',
						name : 'managerId'
					});

			var cbo_etype = FormUtil.getLCboField({
						fieldLabel : '豁免类别',
						name : 'etype',
						"width" : 140,
						"allowBlank" : false,
						"maxLength" : 30,
						"data" : Lcbo_dataSource.exempt_etype_datas,
						listeners : {
							change : function(field, newVal, oldVal) {
								_this.showOrHidControls(newVal);
							}
						}
					});

			var rad_isBackAmount = FormUtil.getRadioGroup({
						fieldLabel : '是否返还息费',
						name : 'isBackAmount',
						"width" : 125,
						"allowBlank" : false,
						"maxLength" : 10,
						"items" : [{
									"boxLabel" : "是",
									"name" : "isBackAmount",
									"inputValue" : 1
								}, {
									"boxLabel" : "否",
									"name" : "isBackAmount",
									checked : true,
									"inputValue" : 2
								}]
					});

			var chk_exeItems = FormUtil.getCheckGroup({
						fieldLabel : FormUtil.REQUIREDHTML + '豁免项目',
						name : 'exeItems',
						"width" : 500,
						hidden : true,
						"items" : [{
									"boxLabel" : "利息",
									"name" : "exeItems",
									"inputValue" : 2
								}, {
									"boxLabel" : "管理费",
									"name" : "exeItems",
									"inputValue" : 3
								}, {
									"boxLabel" : "罚息",
									"name" : "exeItems",
									"inputValue" : "4"
								}, {
									"boxLabel" : "滞纳金",
									"name" : "exeItems",
									"inputValue" : 5
								}, {
									"boxLabel" : "手续费",
									"name" : "exeItems",
									"inputValue" : 6
								}],
						listeners : {
							change : function() {
								_this.showOrHidHeaders();
							}
						}
					});

			var bdat_startDate = FormUtil.getDateField({
						fieldLabel : '豁免起始日期',
						name : 'startDate',
						"width" : 95,
						listeners : {
							select : function(field, date) {
								var etype = cbo_etype.getValue();
								_this.showOrHidGrid(etype);
							}
						}
					});

			var bdat_endDate = FormUtil.getDateField({
						fieldLabel : '豁免截止日期',
						name : 'endDate',
						"width" : 95,
						listeners : {
							select : function(field, date) {
								var etype = cbo_etype.getValue();
								_this.showOrHidGrid(etype);
							}
						}
					});

			var cmpt_startEndDate = FormUtil.getMyCompositeField({
						fieldLabel : FormUtil.REQUIREDHTML + '豁免起止日期',
						sigins : null,
						itemNames : 'startDate,endDate',
						name : 'cmpt_startEndDate',
						hidden : true,
						width : 220,
						items : [bdat_startDate, {
									xtype : 'displayfield',
									value : '至'
								}, bdat_endDate]
					});

			var bdat_appDate = FormUtil.getDateField({
						fieldLabel : '申请日期',
						name : 'appDate',
						"width" : 125,
						"allowBlank" : false
					});

			var txt_appDept = FormUtil.getTxtField({
						fieldLabel : '申请部门',
						name : 'appDept',
						"width" : 125,
						"allowBlank" : false,
						readOnly : true,
						"maxLength" : "50"
					});

			var txt_manager = FormUtil.getTxtField({
						fieldLabel : '经办人',
						name : 'manager',
						"width" : 125,
						readOnly : true,
						"allowBlank" : false
					});

			var txt_reason = FormUtil.getTAreaField({
						fieldLabel : '豁免申请理由',
						name : 'reason',
						"width" : 475,
						"allowBlank" : false,
						"maxLength" : "1000"
					});

			this.createAttachMentFs();

			var layout_fields = [txt_id, txt_contractId, txt_procId, txt_breed,
					txt_code, txt_totalAmount, txt_managerId, {
						cmns : FormUtil.CMN_TWO,
						fields : [cbo_etype, rad_isBackAmount]
					}, {
						cmns : FormUtil.CMN_TWO_LEFT,
						fields : [chk_exeItems, cmpt_startEndDate]
					}, {
						cmns : FormUtil.CMN_THREE,
						fields : [bdat_appDate, txt_appDept, txt_manager]
					}, {
						cmns : FormUtil.CMN_TWO_LEFT,
						fields : [txt_reason, this.attachMentFs]
					}];

			var btnTempSaveHtml = this.getButtonHml(this.btnIdObj.btnTempSave,
					'暂存申请单');
			var btnSaveHtml = this.getButtonHml(this.btnIdObj.btnSave, '提交申请单');

			var title = '息费豁免申请单信息&nbsp;&nbsp;'
					+ btnTempSaveHtml
					+ btnSaveHtml
					+ '提示：[<span style="color:red;">带"*"的为必填项，金额默认单位为 "元"</span>]';
			var frm_cfg = {
				title : title,
				autoScroll : true,
				url : './fcExempt_save.action',
				labelWidth : 115
			};
			var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			rad_isBackAmount.setWidth(125);
			var _this = this;
			applyForm.addListener('afterrender', function(panel) {
						_this.addListenersToCustButtons([{
									btnId : _this.btnIdObj.btnTempSave,
									fn : function(e, targetEle, obj) {
										_this.saveApplyData(0);
									}
								}, {
									btnId : _this.btnIdObj.btnSave,
									fn : function(e, targetEle, obj) {
										_this.saveApplyData(1);
									}
								}]);
					});
			this.applyPanel = applyForm;
		},
		/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
		createAttachMentFs : function() {
			/*
			 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录， mortgage_path
			 * 定义在 resource.properties 资源文件中 isSave : 附件上传后，是否保存到 ts_attachment
			 * 表中。 true : 保存 params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中
			 * ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
			 * params 可在 reload 方法中提供 ----
			 */
			this.attachMentFs = new Ext.ux.AppAttachmentFs({
						title : '附件上传',
						isLoad : false,
						dir : 'extension_path',
						isSave : true
					});
		},
		/**
		 * 创建手续费豁免Grid
		 */
		createFreeGrid : function() {
			var _this = this;
			var structure_1 = [{
						header : '实际放款日期',
						name : 'realDate',
						width : 85
					}, {
						header : '应收手续费',
						name : 'zfreeamount',
						width : 80,
						renderer : function(val) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '本次豁免手续费',
						name : 'cfat',
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '收款状态',
						name : 'status',
						renderer : Render_dataSource.statusRender
					}, {
						header : '豁免状态',
						name : 'exempt',
						renderer : Render_dataSource.exemptRender
					}];

			var txt_cfat = FormUtil.getMoneyField({
						fieldLabel : '豁免手续费',
						name : 'cfat'
					});

			var sumTitleCfg = this.getSumTitleCfg();
			this.freeGrid = new Ext.ux.grid.MyEditGrid({
						sumLabelId : sumTitleCfg.id,
						title : '放款手续费豁免列表' + sumTitleCfg.html,
						structure : structure_1,
						url : './fcExeItems_list.action',
						needPage : false,
						isLoad : false,
						height : 300,
						keyField : 'id',
						editEles : {
							2 : txt_cfat
						},
						listeners : {
							afteredit : function(e) {
								var tat = e.record.get('cfat') || 0;
								tat = parseFloat(tat);
								var totalAmountCfg = {
									tat : tat
								};
								_this.sumTotalAmount(totalAmountCfg);
							}
						}
					});
		},
		/**
		 * 创建提前还款豁免Grid
		 */
		createPrepaymentGrid : function() {
			var _this = this;
			var structure_1 = [{
						header : '提前还款日期',
						name : 'predDate',
						width : 100
					}, {
						header : '应收手续费',
						name : 'zfreeamount',
						width : 80,
						renderer : function(val) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '豁免手续费',
						name : 'fat',
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '利息',
						name : 'zinterest',
						width : 80,
						renderer : function(val) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '豁免利息',
						name : 'rat',
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '管理费',
						name : 'zmgrAmount',
						width : 80,
						renderer : function(val) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '豁免管理费',
						name : 'mat',
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '罚息',
						name : 'zpenAmount',
						width : 80,
						renderer : function(val) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '豁免罚息',
						name : 'pat',
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '滞纳金',
						name : 'zdelAmount',
						width : 80,
						renderer : function(val) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '豁免滞纳金',
						name : 'dat',
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '应收合计',
						name : 'zytotalAmount',
						renderer : function(val, metea, record) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '豁免合计',
						name : 'tat',
						renderer : function(val, metea, record) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '收款状态',
						name : 'status',
						renderer : Render_dataSource.statusRender
					}, {
						header : '豁免状态',
						name : 'exempt',
						renderer : Render_dataSource.exemptRender
					}];

			var txt_fat = FormUtil.getMoneyField({
						fieldLabel : '豁免提前还款手续费',
						name : 'fat'
					});
			var txt_rat = FormUtil.getMoneyField({
						fieldLabel : '豁免利息',
						name : 'rat'
					});
			var txt_mat = FormUtil.getMoneyField({
						fieldLabel : '豁免管理费',
						name : 'mat'
					});
			var txt_pat = FormUtil.getMoneyField({
						fieldLabel : '豁免罚息',
						name : 'pat'
					});
			var txt_dat = FormUtil.getMoneyField({
						fieldLabel : '豁免滞纳金',
						name : 'dat'
					});

			var sumTitleCfg = this.getSumTitleCfg();
			this.prepaymentGrid = new Ext.ux.grid.MyEditGrid({
						sumLabelId : sumTitleCfg.id,
						title : '提前还款息费豁免列表' + sumTitleCfg.html,
						structure : structure_1,
						url : './fcExeItems_list.action',
						needPage : false,
						isLoad : false,
						height : 300,
						keyField : 'id',
						editEles : {
							2 : txt_fat,
							4 : txt_rat,
							6 : txt_mat,
							8 : txt_pat,
							10 : txt_dat
						},
						listeners : {
							afteredit : function(e) {
								_this.showOrHidHeaders();
							}
						}
					});
		},
		/**
		 * 创建正常/逾期还款豁免Grid
		 */
		createPlanGrid : function() {
			var _this = this;
			var structure_1 = [{
						header : '还款日期',
						name : 'xpayDate',
						width : 85
					}, {
						header : '当期期数',
						name : 'phases',
						width : 65
					}, {
						header : '利息(元)',
						name : 'zinterest',
						width : 80,
						renderer : function(val) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '豁免利息',
						name : 'rat',
						width : 85,
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '管理费(元)',
						name : 'zmgrAmount',
						width : 80,
						renderer : function(val) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '豁免管理费(元)',
						name : 'mat',
						width : 85,
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '罚息(元)',
						name : 'zpenAmount'
					}, {
						header : '豁免罚息(元)',
						name : 'pat',
						width : 85,
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '滞纳金(元)',
						name : 'zdelAmount'
					}, {
						header : '豁免滞纳金(元)',
						name : 'dat',
						width : 85,
						renderer : function(val, metea) {
							metea.css = 'x-grid-edit-back';
							return Cmw.getThousandths(val);
						}
					}, {
						header : '合计(元)',
						name : 'zytotalAmount',
						renderer : function(val, metea, record) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '豁免合计(元)',
						name : 'tat',
						renderer : function(val, metea, record) {
							return Cmw.getThousandths(val);
						}
					}, {
						header : '收款状态',
						name : 'status',
						width : 85,
						renderer : Render_dataSource.planStatusRender
					}, {
						header : '豁免状态',
						name : 'exempt',
						width : 60,
						renderer : Render_dataSource.exemptRender
					}];
			var txt_rat = FormUtil.getMoneyField({
						fieldLabel : '豁免利息',
						name : 'rat'
					});
			var txt_mat = FormUtil.getMoneyField({
						fieldLabel : '豁免管理费',
						name : 'mat'
					});
			var txt_pat = FormUtil.getMoneyField({
						fieldLabel : '豁免罚息',
						name : 'pat'
					});
			var txt_dat = FormUtil.getMoneyField({
						fieldLabel : '豁免滞纳金',
						name : 'dat'
					});

			var sumTitleCfg = this.getSumTitleCfg();
			this.planGrid = new Ext.ux.grid.MyEditGrid({
						sumLabelId : sumTitleCfg.id,
						title : '正常/逾期息费豁免列表' + sumTitleCfg.html,
						structure : structure_1,
						url : './fcExeItems_list.action',
						needPage : false,
						isLoad : false,
						height : 300,
						keyField : 'id',
						editEles : {
							3 : txt_rat,
							5 : txt_mat,
							7 : txt_pat,
							9 : txt_dat
						},
						listeners : {
							afteredit : function(e) {
								_this.showOrHidHeaders();
							}
						}
					});
		},
		/**
		 * 获取汇总标题配置信息
		 */
		getSumTitleCfg : function() {
			var sumLabelId = Ext.id(null, 'sumLabelId');
			var labelHtml = '&nbsp;&nbsp;<span id="' + sumLabelId
					+ '" style="color:red;font-weight:bold;">汇总信息</span>';
			return {
				id : sumLabelId,
				html : labelHtml
			}
		},
		/**
		 * 打开展期客户选择窗口
		 */
		openCustomerDialog : function() {
			var _this = this;
			var _contractId = this.applyPanel.getValueByName("contractId");
			if (_contractId) {
				ExtUtil.confirm({
							title : '提示',
							msg : '确定重新选择新的客户吗?',
							fn : function(btn) {
								if (btn != 'yes')
									return;
								_this.showCustomerDialog();
							}
						});
			} else {
				this.showCustomerDialog();
			}
		},
		/**
		 * 将展期客户数据赋给 展期客户资料面板
		 */
		showCustomerDialog : function() {
			var _this = this;
			var el = Ext.get(this.btnIdObj.btnChoseCust);
			var parentCfg = {
				parent : el,
				callback : function(id, record) {
					_this.setChooseVal(id, record);
				}
			};
			if (this.customerDialog) {
				this.customerDialog.show(parentCfg);
			} else {
				var _this = this;
				Cmw.importPackage('pages/app/dialogbox/ExemptDialogbox',
						function(module) {
							_this.customerDialog = module.DialogBox;
							_this.customerDialog.show(parentCfg);
						});
			}
		},
		/**
		 * 将选择的客户数据赋给展期客户资料详情面板
		 */
		setChooseVal : function(id, record) {
			var data = record.data;
			var formData = {
				contractId : id
			};
			this.customerPanel.reload({
						json_data : data
					}, true);
			this.applyPanel.enable();
			this.applyPanel.setJsonVals(formData);
			var etype = this.applyPanel.getValueByName("etype");
			if (!etype)
				return;
			this.showOrHidGrid(etype);
		},
		/**
		 * 获取豁免金额数据
		 */
		getBatchDatas : function() {
			var etype = this.applyPanel.getValueByName('etype');
			var exeItems = this.applyPanel.getValueByName('exeItems');
			var totalAmount = this.applyPanel.getValueByName('totalAmount');
			var store = this.currGrid.getStore();
			var batchDatas = [];
			switch (parseInt(etype)) {
				case 1 : {/* 放款手续费豁免 */
					var record = store.getAt(0);
					var formId = record.id;
					var fat = record.get("cfat") || 0;
					batchDatas[batchDatas.length] = {
						formId : formId,
						fat : fat,
						totalAmount : totalAmount
					};
					break;
				}
				case 2 : {/* 提前还款息费豁免 */
					var record = store.getAt(0);
					var extItemFields = this.getExtItemFields(exeItems);
					var data = this.getExtItemAmountData(extItemFields, record);
					batchDatas[batchDatas.length] = data;
					break;
				}
				case 3 : {/* 正常/逾期还款息费豁免 */
					var extItemFields = this.getExtItemFields(exeItems);
					for (var i = 0, count = store.getCount(); i < count; i++) {
						var record = store.getAt(i);
						var data = this.getExtItemAmountData(extItemFields,
								record);
						batchDatas[batchDatas.length] = data;
					}
					break;
				}
			}
			batchDatas = Ext.encode(batchDatas);
			return batchDatas;
		},
		getExtItemAmountData : function(extItemFields, record) {
			var data = {};
			data["formId"] = record.id;
			for (var i = 0, count = extItemFields.length; i < count; i++) {
				var field = extItemFields[i];
				data[field] = record.get(field) || 0;
			}
			data["totalAmount"] = record.get("tat") || 0;
			return data;
		},
		/**
		 * 获取豁免字段数组
		 */
		getExtItemFields : function(exeItems) {
			if (Ext.isNumber(exeItems))
				exeItems = exeItems.toString();
			exeItems = exeItems.split(",");
			var fields = [];
			for (var i = 0, count = exeItems.length; i < count; i++) {
				var item = exeItems[i];
				switch (parseInt(item)) {
					case 2 : {/* 利息 */
						fields[fields.length] = "rat";
						break;
					}
					case 3 : {/* 管理费 */
						fields[fields.length] = "mat";
						break;
					}
					case 4 : {/* 罚息 */
						fields[fields.length] = "pat";
						break;
					}
					case 5 : {/* 滞纳金 */
						fields[fields.length] = "dat";
						break;
					}
					case 6 : {/* 提前还款手续费 */
						fields[fields.length] = "fat";
						break;
					}
				}
			}
			return fields;
		},
		/**
		 * 保存单据数据
		 * 
		 * @param opType
		 *            [0:暂存,1:提交]
		 */
		saveApplyData : function(submitType) {
			if (!this.validApplyForm())
				return;
			var _this = this;
			var currTabId = this.params.tabId;
			var apptabtreewinId = this.params.apptabtreewinId;
			var _this = this;
			var batchDatas = this.getBatchDatas();
			EventManager.frm_save(this.applyPanel, {
				beforeMake : function(formDatas) {
					formDatas.submitType = submitType;
					formDatas.batchDatas = batchDatas;
					if (_this.uuid)
						formDatas.uuid = _this.uuid;
				},
				sfn : function(formDatas) {
					if (formDatas["applyId"]) {
						_this.applyId = formDatas["applyId"];
					} else {
						_this.applyId = null;
					}
					if (_this.applyId) {
						var attachParams = _this.getAttachParams(_this.applyId);
						_this.attachMentFs.updateTempFormId(attachParams);
					}
					if (_this.uuid)
						_this.uuid = null;
					if (currTabId) {
						var currTab = Ext.getCmp(currTabId);
						if (currTab)
							currTab.destroy();
					}

					var tabId = null;
					var params = null;
					if (submitType == 0) {
						tabId = CUSTTAB_ID.tempExemptApplyMgrTabId;
						Cmw.activeTab(apptabtreewinId, tabId, params);
					} else { /* 跳转到 业务审批页面 */
						var code = formDatas["code"];
						var procId = _this.applyPanel.getValueByName("procId");
						ExtUtil.confirm({
									title : '提示',
									msg : '确定提交编号为："' + code + '"的展期申请单?',
									fn : function() {
										tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
										params = {
											applyId : _this.applyId,
											procId : procId
										};
										url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
										var title = '息费豁免业务审批';
										Cmw.activeTab(apptabtreewinId, tabId,
												params, url, title);
									}
								});
					}
				}
			});
		},
		/**
		 * 申请表单数据验证
		 */
		validApplyForm : function() {
			var etype_val = this.applyPanel.getValueByName("etype");
			if (!etype_val) {
				ExtUtil.alert({
							msg : "请选择豁免类别!"
						});
				return false;
			}

			var errMsg = [];
			var tempErrMsg = null;
			switch (parseInt(etype_val)) {
				case 1 : {/* 放款手续费豁免 */
					tempErrMsg = this.checkFreeGridData();
					break;
				}
				case 2 : {/* 提前还款息费豁免 */
					tempErrMsg = this.checkPrepaymentGridData();
					break;
				}
				case 3 : {/* 正常/逾期还款息费豁免 */
					tempErrMsg = this.checkPlanGridData();
					break;
				}
			}
			if (tempErrMsg && tempErrMsg.length > 0) {
				errMsg = errMsg.concat(tempErrMsg);
			}
			var totalAmount_val = this.applyPanel.getValueByName("totalAmount");
			if (!totalAmount_val || totalAmount_val <= 0) {
				errMsg[errMsg.length] = "豁免总金额不能小于0元!";
			}

			if (null != errMsg && errMsg.length > 0) {
				var msg = errMsg.join("</br>");
				ExtUtil.alert({
							msg : msg
						});
				return false;
			}
			return true;
		},
		/**
		 * 检查放款手续费输入的合法性
		 */
		checkFreeGridData : function() {
			var errMsg = [];
			var store = this.currGrid.getStore();
			var count = store.getCount();
			if (count < 0) {
				errMsg[errMsg.length] = "系统中找不到当前客户的放款手续费记录!";
			} else {
				var record = store.getAt(0);
				var zfreeamount = record.get("zfreeamount") || 0;
				var cfat = record.get("cfat") || 0;
				if (parseFloat(cfat) > parseFloat(zfreeamount)) {
					errMsg[errMsg.length] = "放款手续费列表中的本次豁免手续费项不能超过应收手续费项!";
				}
			}
			return errMsg;
		},
		/**
		 * 检查提前还款息费豁免Grid输入的合法性
		 */
		checkPrepaymentGridData : function() {
			var errMsg = [];
			var store = this.currGrid.getStore();
			var count = store.getCount();
			if (count < 0) {
				errMsg[errMsg.length] = "系统中找不到当前客户的提前还款记录!";
			} else {
				var record = store.getAt(0);
				var zytotalAmount = record.get("zytotalAmount") || 0;
				var tat = record.get("tat") || 0;
				if (parseFloat(tat) > parseFloat(zytotalAmount)) {
					errMsg[errMsg.length] = "提前还款息费豁免列表中的豁免合计金额为不能超过应收合计金额!";
				}
			}
			return errMsg;
		},
		/**
		 * 检查正常/逾期还款息费豁免Grid输入的合法性
		 */
		checkPlanGridData : function() {
			var errMsg = [];
			var store = this.currGrid.getStore();
			var count = store.getCount();
			if (count < 0) {
				errMsg[errMsg.length] = "系统中找不到当前客户的还款计划表记录!";
			} else {
				for (var i = 0; i < count; i++) {
					var record = store.getAt(i);
					var phases = record.get("phases");
					var zytotalAmount = record.get("zytotalAmount") || 0;
					var tat = record.get("tat") || 0;
					if (parseFloat(tat) > parseFloat(zytotalAmount)) {
						errMsg[errMsg.length] = "正常/逾期息费豁免列表中的第<b>" + phases
								+ "</b>期的豁免合计金额为不能超过应收合计金额!";
					}
				}
			}
			return errMsg;
		},
		/**
		 * 获取自定义按钮 HTML CODE
		 * 
		 * @param id
		 *            按钮ID
		 * @param text
		 *            按钮文本
		 */
		getButtonHml : function(id, text) {
			var html = "&nbsp;&nbsp;<span class='btnBussNodeCls' id='" + id
					+ "'>" + text + "</span>&nbsp;&nbsp;";
			return html;
		},
		/**
		 * 为HTML按钮绑定事件
		 * 
		 * @param btnCfgArr
		 *            按钮配置数组 [{btnId : this.btnIdObj.btnSaveId,fn :
		 *            function(e,targetEle,obj){}}]
		 */
		addListenersToCustButtons : function(btnCfgArr) {
			var _this = this;
			for (var i = 0, count = btnCfgArr.length; i < count; i++) {
				var btnCfg = btnCfgArr[i];
				var btnId = btnCfg.btnId;
				var btnEle = addClass(btnId);
				btnEle.on('click', btnCfg.fn, this);
			}
			/**
			 * 为按钮添加点击和鼠标经过样式
			 */
			function addClass(eleId) {
				var btnEle = Ext.get(eleId);
				btnEle.addClassOnClick("nodeClickCls");
				btnEle.addClassOnOver("x-btn-text");
				return btnEle;
			}
		},
		setParams : function(parentPanel, params) {
			this.parentPanel = parentPanel;
			this.params = params;
			this.applyId = params.applyId;
		},
		refresh : function() {
			if (!this.appMainPanel.rendered) {
				var _this = this;
				this.appMainPanel.addListener('render', function(cmpt) {
							_this.loadDatas();
						});
			} else {
				this.loadDatas();
			}
		},
		/**
		 * 在这里加载数据
		 */
		loadDatas : function() {
			var _this = this;
			var sysId = this.params.sysid;
			var optionType = this.params.optionType;
			var breed = this.params.breed;
			var applyId = this.params.applyId;
			var contractId = this.params.contractId;
			if (optionType && optionType == OPTION_TYPE.EDIT) {/* 修改 */
				var errMsg = [];// ["修改时传参发生错误："];
				if (!applyId) {
					errMsg[errMsg.length] = "必须传入参数\"applyId\"!<br/>";
				}
				if (!contractId) {
					errMsg[errMsg.length] = "必须传入参数\"contractId\"!<br/>";
				}
				if (null != errMsg && errMsg.length > 0) {
					errMsg = "修改时传参发生错误：<br/>" + errMsg.join(" ");
					ExtUtil.alert({
								msg : errMsg
							});
					return;
				}
				this.applyPanel.enable();
				this.applyPanel.reset();
				this.customerPanel.reload({
							contractId : contractId
						});
				this.applyPanel.setValues('./fcExempt_get.action', {
							params : {
								id : applyId
							},
							sfn : function(json_data) {
								_this.showOrHidControls(json_data.etype);
							}
						});
				this.attachMentFs.reload(this.getAttachParams(applyId));
			} else {/* 新增 */
				this.attachMentFs.params = this.getAttachParams(this.uuid);
				this.applyPanel.enable();
				this.applyPanel.disable();
				this.applyPanel.setValues('./fcExempt_add.action', {
							sfn : function(json_data) {
								_this.applyPanel.setFieldValue("breed", breed);
							}
						});
			}
		},
		/**
		 * 获取附件参数
		 * 
		 * @param formId
		 *            业务单ID
		 */
		getAttachParams : function(formId) {
			var sysId = this.params.sysid;
			return {
				sysId : sysId,
				formType : ATTACHMENT_FORMTYPE.FType_33,
				formId : formId
			};
		},
		/**
		 * 隐藏或显示控件
		 */
		showOrHidControls : function(etype) {
			this.showOrHidFields(etype);
			this.showOrHidGrid(etype);
		},
		/**
		 * 显示或隐藏控件
		 */
		showOrHidFields : function(etype) {
			var optionType = this.params.optionType;
			var cmpt_startEndDate = this.applyPanel
					.findFieldByName("cmpt_startEndDate");
			var rad_exeItems = this.applyPanel.findFieldByName("exeItems");
			if (!optionType || optionType == OPTION_TYPE.ADD) {
				cmpt_startEndDate.reset();
				rad_exeItems.reset();
			}
			var exeItemsVal = rad_exeItems.getValue();
			switch (parseInt(etype)) {
				case 1 : {/* 放款手续费 */
					cmpt_startEndDate.hide();
					rad_exeItems.hide();
					break;
				}
				case 2 : {/* 提前还款息费豁免 */
					cmpt_startEndDate.hide();
					rad_exeItems.show();
					rad_exeItems.hideOrShowItems("1", true, true);
					rad_exeItems.hideOrShowItems("6", false, true);
					if (!exeItemsVal)
						rad_exeItems.setValue("2,3,4,5,6");/* 默认选中所有的项目 */
					break;
				}
				case 3 : {/* 正常/逾期还款息费豁免 */
					cmpt_startEndDate.show();
					rad_exeItems.show();
					rad_exeItems.hideOrShowItems("1,6", true, true);
					if (!exeItemsVal)
						rad_exeItems.setValue("2,3,4,5");/* 默认选中所有的项目 */
					break;
				}
			}
		},
		/**
		 * 显示或隐藏Grid
		 */
		showOrHidGrid : function(etype) {
			var _this = this;
			if (this.currGrid)
				this.currGrid.hide();
			var contractId = this.applyPanel.getValueByName("contractId");
			var optionType = this.params.optionType || 1;
			var _params = {
				contractId : contractId,
				optionType : optionType,
				etype : etype
			};
			switch (parseInt(etype)) {
				case 1 : {/* 放款手续费 */
					if (!this.freeGrid) {
						this.createFreeGrid();
						this.appMainPanel.add(this.freeGrid);
					}
					this.currGrid = this.freeGrid;
					break;
				}
				case 2 : {/* 提前还款息费豁免 */
					if (!this.prepaymentGrid) {
						this.createPrepaymentGrid();
						this.appMainPanel.add(this.prepaymentGrid);
					}
					this.currGrid = this.prepaymentGrid;
					break;
				}
				case 3 : {/* 正常/逾期还款息费豁免 */
					var valObj = this.applyPanel
							.getValueByName("cmpt_startEndDate");
					if (valObj)
						Ext.apply(_params, valObj);
					if (!this.planGrid) {
						this.createPlanGrid();
						this.appMainPanel.add(this.planGrid);
					}
					this.currGrid = this.planGrid;
					break;
				}
			}
			if (!this.currGrid)
				return;
			this.appMainPanel.doLayout();
			this.currGrid.show();
			this.currGrid.reload(_params, function() {
						_this.showOrHidHeaders();
					});
		},
		/**
		 * 显示或隐藏Grid的指定列
		 */
		showOrHidHeaders : function() {
			var exeItems = this.applyPanel.getValueByName("exeItems");
			if (!exeItems)
				return;
			if (!this.currGrid)
				return;
			var etype = this.applyPanel.getValueByName("etype");
			var itemMap = {
				'key_2' : {
					cmn1 : 'rat',
					cmn2 : 'zinterest',
					show : false
				},/* 豁免利息 */
				'key_3' : {
					cmn1 : 'mat',
					cmn2 : 'zmgrAmount',
					show : false
				},/* 豁免管理费 */
				'key_4' : {
					cmn1 : 'pat',
					cmn2 : 'zpenAmount',
					show : false
				},/* 豁免罚息 */
				'key_5' : {
					cmn1 : 'dat',
					cmn2 : 'zdelAmount',
					show : false
				}/* 豁免滞纳金 */
			}
			var flag = false;
			switch (parseInt(etype)) {
				case 1 : {/* 放款手续费 */
					break;
				}
				case 2 : {/* 提前还款息费豁免 */
					Ext.apply(itemMap, {
								'key_6' : {
									cmn1 : 'fat',
									cmn2 : 'zfreeamount',
									show : false
								}
							});/* 豁免手续费 */
					flag = true;
					break;
				}
				case 3 : {/* 正常/逾期还款息费豁免 */
					flag = true;
					break;
				}
			}
			if (!flag)
				return;
			var _items = exeItems.split(",");
			if (!_items || _items.length == 0)
				return;
			for (var i = 0, count = _items.length; i < count; i++) {
				var key = "key_" + _items[i];
				if (!itemMap[key])
					continue;
				itemMap[key].show = true;
			}
			var showCmns1 = [];
			var showCmns2 = [];
			var hideCmns = [];
			for (var key in itemMap) {
				var metea = itemMap[key];
				var cmn1 = metea.cmn1;
				var cmn2 = metea.cmn2;
				var show = metea.show;
				if (show) {
					showCmns2[showCmns2.length] = cmn2;
					showCmns1[showCmns1.length] = cmn1;
				} else {
					hideCmns[hideCmns.length] = cmn2;
					hideCmns[hideCmns.length] = cmn1;
				}
			}
			if (showCmns1 && showCmns1.length > 0) {
				this.currGrid.setHeadersVisible(false, showCmns1.join(",")
								+ "," + showCmns2.join(","));
			}
			if (hideCmns && hideCmns.length > 0) {
				this.currGrid.setHeadersVisible(true, hideCmns.join(","));
			}
			this.sumItemsAmount(showCmns1, showCmns2);
		},
		/**
		 * 根据显示的列名，汇总行的合计和豁免合计以及Grid标题栏合计
		 */
		sumItemsAmount : function(showCmns1, showCmns2) {
			var totalAmountCfg = {};
			var store = this.currGrid.getStore();
			var len = showCmns1.length;
			for (var i = 0, count = store.getCount(); i < count; i++) {
				var record = store.getAt(i);
				var ysSumamount = 0;/* 应收合计金额 */
				var ssSumamount = 0;/* 本次豁免合计金额 */
				for (var j = 0; j < len; j++) {
					var cmn1 = showCmns1[j];
					var cmn2 = showCmns2[j];
					var ssamount = record.get(cmn1) || 0;
					ssamount = parseFloat(ssamount);
					ssSumamount += ssamount;
					var ysamount = record.get(cmn2) || 0;
					ysamount = parseFloat(ysamount);
					ysSumamount += ysamount;
					if (!totalAmountCfg[cmn1]) {
						totalAmountCfg[cmn1] = ssamount;
					} else {
						totalAmountCfg[cmn1] += ssamount;
					}
				}
				ysSumamount = StringHandler.forDight(ysSumamount, 2);
				ssSumamount = StringHandler.forDight(ssSumamount, 2);
				record.set("zytotalAmount", ysSumamount);
				record.set("tat", ssSumamount);
				if (!totalAmountCfg["tat"]) {
					totalAmountCfg["tat"] = ssSumamount;
				} else {
					totalAmountCfg["tat"] += ssSumamount;
				}
			}
			// store.commitChanges();
			this.sumTotalAmount(totalAmountCfg);
		},
		/**
		 * 根据记录计算该记录指定列的和
		 * 
		 * @param store
		 *            Grid Store 对象
		 * @param showCmn1
		 *            本次豁免列
		 */
		sumTotalAmount : function(totalAmountCfg) {
			var tat = totalAmountCfg.tat || 0;
			this.applyPanel.setFieldValue("totalAmount", tat);
			var info = "豁免总金额：0 元";
			if (totalAmountCfg.tat || totalAmountCfg.tat === 0) {
				info = "豁免总金额：" + totalAmountCfg.tat + "元";
			}
			var inforItemArr = [];
			if (totalAmountCfg.fat) {
				inforItemArr[inforItemArr.length] = "手续费：" + totalAmountCfg.fat
						+ "元";
			}
			if (totalAmountCfg.rat) {
				inforItemArr[inforItemArr.length] = "利息：" + totalAmountCfg.rat
						+ "元";
			}
			if (totalAmountCfg.mat) {
				inforItemArr[inforItemArr.length] = "管理费：" + totalAmountCfg.mat
						+ "元";
			}
			if (totalAmountCfg.pat) {
				inforItemArr[inforItemArr.length] = "罚息：" + totalAmountCfg.pat
						+ "元";
			}
			if (totalAmountCfg.dat) {
				inforItemArr[inforItemArr.length] = "滞纳金：" + totalAmountCfg.dat
						+ "元";
			}
			if (inforItemArr.length > 0) {
				info += "[" + inforItemArr.join("，") + "]";
			}
			var sumLabelId = this.currGrid.sumLabelId;
			if (!sumLabelId) {
				ExtUtil.alert("Grid 上必须要配置 sumLabelId 参数!");
				return;
			}
			var sumLabel = Ext.get(sumLabelId);
			sumLabel.update("(" + info + ")");
		},
		doResize : function() {

		},
		resize : function(adjWidth, adjHeight) {
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (null != this.appMainPanel) {
				if (this.customerDialog) {
					this.customerDialog.destroy();
					this.customerDialog = null;
				}
				if (this.freeGrid) {
					this.freeGrid.destroy();
					this.freeGrid = null;
				}
				if (this.prepaymentGrid) {
					this.prepaymentGrid.destroy();
					this.prepaymentGrid = null;
				}
				if (this.planGrid) {
					this.planGrid.destroy();
					this.planGrid = null;
				}
				if (this.currGrid)
					this.currGrid = null;
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		}
	}
});
