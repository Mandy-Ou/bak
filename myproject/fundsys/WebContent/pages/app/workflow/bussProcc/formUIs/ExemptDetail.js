/**
 * 息费豁免详情面板
 */

define(function(require, exports) {

	exports.viewUI = {
		appMainPanel : null,
		detailPanel : null,
		params : null,
		ToolBar : null,/* 返回 */
		attachMentFs : null,/*--->附件<-----*/
		freeGrid : null,
		customerDialog : null,
		contractId : null,/* 顾客的id */
		currGrid : null,/* 当前显示的Grid */
		isBackAmount : null,
		overGrid : null,
		etype : null,
		appDate : null,
		PrepaymentGrid : null,
		managerId : null,
		sysId : null,
		id : null,
		btnBack : null,
		apptabtreewinId : null,
		tab : null,
		DetailTab : null,

		/**
		 * 创建组建：设置参数
		 */
		setParams : function(params) {
			var _this = this;
			_this.params = params;
			this.contractId = params.contractId;
			this.managerId = params.managerId;
			this.etype = params.etype;
			this.managerId = params.managerId;
			this.isBackAmount = params.isBackAmount;
			this.appDate = params.appDate;
			this.sysId = params.sysid;
			this.id = params.id;
			this.apptabtreewinId = params.apptabtreewinId;
		},

		/**
		 * 创建主面板
		 */

		getMainUI : function(tab, params) {
			this.tab = tab;
			var _this = this;
			this.setParams(params);
			this.contractId = params.contractId;
			if (!this.appMainPanel) {
				/* step 1 --创建主面板<--- */
				this.createAppDetailPnl();
				this.appMainPanel = new Ext.Panel();
				this.appMainPanel.add(this.getToolBar());
				this.customerPanel = new Ext.Panel({
							// collapsible : true,
							collapsed : false,
							border : false
						});
				this.customerPanel.add(this.getToolBar());
				this.appMainPanel.add(this.detailPanel);
				this.createGridPnl();
				/* step 2 --把创建的面板复制给面板对象<--- */
			}
			if (tab) {
				tab.notify = function() {
					_this.setParams(params);
					_this.refresh(params);
				}
			}
			return this.appMainPanel;
		},

		/**
		 * 创建三个表格
		 */

		createGridPnl : function() {
			var freeGrid = this.createFreeGrid();
			var PrepaymentGrid = this.createPrepaymentGrid();
			var PlanGrid = this.createPlanGrid();
			this.detailPanel.add(freeGrid);
			this.detailPanel.add(PrepaymentGrid);
			this.detailPanel.add(PlanGrid);
		},

		/**
		 * 返回工具栏
		 */

		getToolBar : function() {
			var _this = this;
			// _this.custType = _this.params.custType;
			var toolBar = null;
			var barItems = [{
						text : "返回",
						iconCls : Btn_Cfgs.PREVIOUS_CLS,
						tooltip : "返回",
						handler : function() {
							_this.showBackTab();
						}
					}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'left',
						controls : barItems
					});
			toolBar
					.addText("<span style='color:#416AA3; ;font-weight:bold;'>息费豁免详情</span>");
			var buttons = toolBar.getButtons();
			this.btnBack = buttons[0]
			return toolBar;
		},

		/**
		 * 返回
		 */

		showBackTab : function() {
			var _this = this;
			var tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
			var tabUrl = CUSTTAB_ID.bussProcc_auditMainUITab.url
			var title = '息费豁免业务审批';
			var apptabtreewinId = _this.params["apptabtreewinId"];
			this.apptabtreewinId = apptabtreewinId;
			Cmw.hideTab(apptabtreewinId, _this.tab);
			var params = {
				sysid : _this.sysId,
				sysId : _this.sysId,
				applyId : _this.id,
				contractId : _this.contractId,
				bussProccCode : 'B0003'
			};
			Cmw.activeTab(apptabtreewinId, tabId, params, tabUrl, title);
		},

		/**
		 * 客户信息面板
		 */
		createAppDetailPnl : function() {
			var _this = this;
			var htmlArrs_1 = [
					'<tr><th col="custType">客户类型</th> <td col="custType" >&nbsp;</td><th col="custName">客户名称</th> <td col="custName" >&nbsp;</td><th col="cardNum" id="label_cardType">证件类型</th> <td col="cardNum" >&nbsp;</td></tr>',
					'<tr><th col="contractCode">借款合同号</th> <td col="contractCode" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th col="payDate">贷款期限</th> <td col="payDate" >&nbsp;</td></tr>',
					'<tr><th col="borBank">借款银行</th> <td col="borBank" >&nbsp;</td><th col="borAccount">借款账号</th> <td col="borAccount" >&nbsp;</td><th col="accName">帐户户名</th> <td col="accName" >&nbsp;</td></tr>',
					'<tr><th col="etype">豁免类别:</th> <td col="etype" >&nbsp;</td><th col="isBackAmount">是否返还息费：</th> <td col="isBackAmount" >&nbsp;</td><th col="cardNum" id="label_cardType">证件类型</th> <td col="cardNum" >&nbsp;</td></tr>',
					'<tr><th col="appDate">申请日期</th> <td col="appDate" >&nbsp;</td></tr>'];
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
						jsonData['appDate'] = _this.appDate;
						jsonData["custType"] = Render_dataSource
								.custTypeRender(jsonData["custType"]);
						Ext.get('label_cardType').update(jsonData["cardType"]);
						var rateType = jsonData["rateType"];
						var etype = jsonData["etype"];
						var isBackAmount = jsonData["isBackAmount"]
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
						jsonData["etype"] = Render_dataSource
								.exempt_etypeRender(_this.etype);
						jsonData["isBackAmount"] = Render_dataSource
								.exempt_isBackAmountRender(_this.isBackAmount);

						var appAmount = jsonData["appAmount"];
						jsonData["appAmount"] = Cmw.getThousandths(appAmount)
								+ "元&nbsp;&nbsp;<br/><span style='color:red;font-weight:bold;'>("
								+ Cmw.cmycurd(appAmount) + ")</span>";
						// _this.freeRload(jsonData);
						_this.showOrHidGrid(jsonData);
						var sysId = _this.params.sysid;
						var formId = _this.id;
						if (!formId) {
							formId = -1;
						}
						var params = {
							formId : formId,
							formType : ATTACHMENT_FORMTYPE.FType_33,
							sysId : sysId,
							isNotDisenbaled : true
						};
						_this.attachMentFs.reload(params);
					}
				}
			}];

			var cfg = {
				height : 500,
				detailCfgs : detailCfgs_1,
				isLoad : false
			};

			var detailPanel = new Ext.ux.panel.DetailPanel(cfg);
			_this.createAttachMentFs();
			detailPanel.add(this.attachMentFs);
			this.detailPanel = detailPanel;
			return detailPanel;
		},

		/**
		 * 刷新加载方法
		 */
		freeRload : function(jsonData) {
			var etype = this.etype;
			var contractId = jsonData.contractId;
			var optionType = OPTION_TYPE.EDIT;
			this.freeGrid.reload({
						etype : etype,
						contractId : contractId,
						optionType : optionType
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

			var sumTitleCfg = this.getSumTitleCfg();
			this.PrepaymentGrid = new Ext.ux.grid.AppGrid({
						sumLabelId : sumTitleCfg.id,
						title : '提前还款息费豁免列表' + sumTitleCfg.html,
						structure : structure_1,
						url : './fcExeItems_list.action',
						needPage : false,
						isLoad : false,
						height : 300,
						keyField : 'id'
					});
			return this.PrepaymentGrid;
		},

		/**
		 * 数据列表的表格
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
			this.freeGrid = new Ext.ux.grid.AppGrid({
						title : "息费豁免详情",
						structure : structure_1,
						url : './fcExeItems_list.action',
						keyField : 'id',
						height : 500,
						isLoad : false
					});
			return this.freeGrid;
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
			var sumTitleCfg = this.getSumTitleCfg();
			this.planGrid = new Ext.ux.grid.AppGrid({
						sumLabelId : sumTitleCfg.id,
						title : '正常/逾期息费豁免列表' + sumTitleCfg.html,
						structure : structure_1,
						url : './fcExeItems_list.action',
						needPage : false,
						isLoad : false,
						height : 300,
						keyField : 'id'
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

			this.overGrid = new Ext.ux.grid.AppGrid({
						title : '正常/逾期息费豁免列表',
						structure : structure_1,
						url : './fcExeItems_list.action',
						keyField : 'id',
						needPage : false,
						isLoad : false,
						height : 500,
						params : {
							contractId : -1
						}
					});
			return this.overGrid;
		},

		/**
		 * 显示或隐藏Grid
		 */

		showOrHidGrid : function(jsonData) {
			var _this = this;

			var params = {
				optionType : OPTION_TYPE.EDIT,
				etype : _this.etype
			};
			switch (_this.etype) {

				case '1' : {/* 放款手续费 */
					this.PrepaymentGrid.hide();
					this.overGrid.hide();
					if (this.freeGrid.hidden) {
						this.freeGrid.show();
						this.freeGrid.reload(params);
					}
					break;
				}

				case '2' : {/* 提前还款息费豁免 */
					this.freeGrid.hide();
					this.overGrid.hide();
					this.detailPanel.add(this.PrepaymentGrid);
					this.PrepaymentGrid.show();
					if (this.PrepaymentGrid.hidden) {
						this.PrepaymentGrid.show();
						this.freeGrid.reload(params);
					}
					break;
				}
				case '3' : {/* 正常/逾期还款息费豁免 */
					this.PrepaymentGrid.hide();
					this.freeGrid.hide();
					if (this.overGrid.hidden) {
						this.overGrid.show();
						this.overGrid.reload(params);
					}
					break;
				}

			}
		},

		/**
		 * 刷新页面
		 */
		refresh : function(params) {
			var _this = this;
			_this.detailPanel.reload({
						contractId : _this.contractId
					});
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
						dir : 'exempt_path',
						isSave : true
					});
		},

		/**
		 * 隐藏或显示控件
		 */
		showOrHidControls : function(etype) {
			this.showOrHidFields(etype);
			this.showOrHidGrid(etype);
		},

		/**
		 * 销毁组件
		 */
		destroy : function() {
			if (null != this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
			if (this.overGrid != null) {
				this.overGrid.destroy();
				this.overGrid = null;
			}
			if (this.freeGrid != null) {
				this.freeGrid.destroy();
				this.freeGrid = null;
			}
		}
	}
});