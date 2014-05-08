/**
 * 增资申请页面
 * 
 * @author 李听
 */

define(function(require, exports) {
	exports.viewUI = {
		parentPanel : null,/**/
		appMainPanel : null,
		customerPanel : null,
		applyPanel : null,
		attachMentFs : null,
		customerDialog : null,/* 客户选择弹窗 */
		gopinionDialog : null,/* 担保人意见弹窗 */
		params : null,
		appgrid:null,
		uuid : Cmw.getUuid(),/* 用于新增时，临时代替申请单ID */
		applyId : null,/* 申请单ID */
		selId:null,
		entrustCustId : null,/* 申请单ID */
		btnIdObj : {
			btnChoseCust : Ext.id(null, 'btnChoseCust'),/* 客户选择 */
			btnTempSave : Ext.id(null, 'btnTempSave'),/* 暂存申请单 */
			btnSave : Ext.id(null, 'btnSave')/* 提交申请单 */
		},
		/**
		 * 获取主面板
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
			this.appMainPanel = new Ext.Container({
						items : [this.customerPanel, this.applyPanel]
					});
		},
		createCustomerPanel : function() {
			var _this=this;
			var htmlArrs_1 = [
					'<tr><th colspan="6" style="font-weight:bold;text-align:left;font-size:33px" ><center>增资申请</center></th><tr>',
					'<tr><th col="code">客户编号</th> <td col="code" >&nbsp;</td><th col="name">委托人姓名</th> <td col="name" >&nbsp;</td><th col="cardNum">身份证</th> <td col="cardNum" >&nbsp;</td></tr>',
					'<tr><th col="phone">手机号码</th> <td col="phone" >&nbsp;</td><th col="inAddress">住宅地址</th> <td col="inAddress" >&nbsp;</td><th col="homeNo">住宅号</th> <td col="homeNo" >&nbsp;</td></tr></tr>'];
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
				url : './fuEntrustCust_get.action',
				params : {
//					id : _this.applyId
				},
				callback : {
					sfn : function(jsonData) {
						_this.applyPanel.setFieldValue("contractId",
								jsonData.id);
						_this.renderDispData(jsonData);
					}
				}
			}];
			var btnChoseCustHtml = this.getButtonHml(
					this.btnIdObj.btnChoseCust, '选择委托人');
			this.customerPanel = new Ext.ux.panel.DetailPanel({
						title : '委托人客户资料' + btnChoseCustHtml,
						collapsible : true,
						border : false,
						isLoad : false,
						detailCfgs : detailCfgs_1
					});

			var _this = this;
			this.customerPanel.addListener('afterrender', function(panel) {
						_this.addListenersToCustButtons([{
									btnId : _this.btnIdObj.btnChoseCust,
									fn : function(e, targetEle, obj) {
										/* 选择客户 */
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
		
		renderDispData : function(jsonData) {
			var _this = this;
			var codeid = this.addAHtml(jsonData, 'code', '点击查看申请单详情！');//为展期申请单添加连接
			var sysid = jsonData["id"];		
            var extensionParams = {sysid:sysid,formId:jsonData["id"]};
			_this.viewExtensionDetailInfo(codeid,extensionParams);
			var params = {
				isAapply : true,
				parent : {selId : jsonData["id"]
			}};},
				/**
				 * 展期申请单详情
				 */
				viewExtensionDetailInfo : function(aid,extensionParams){
					var _this = this;
					var delay = new Ext.util.DelayedTask(function(){
						var ele = Ext.get(aid);
						if(!ele) return;
						ele.on('click',function(e){
							extensionParams.ele = ele;
							if(_this.appExtenCmpt){
								 _this.appExtenCmpt.show(extensionParams);
							}else{
								Cmw.importPackage('pages/app/funds/amountapply/AmountDetail.js',function(mode){
								 _this.appExtenCmpt = mode.WinEdit;
								  _this.appExtenCmpt.show(extensionParams);
								});
							}
						});
					});
					delay.delay(150);
				},
		addAHtml : function(jsonData, prop, title) {
					if (!jsonData[prop])
						return null;
					var aId = Ext.id(null, "VIEW_DETAIL_");
					var val = jsonData[prop];
					jsonData[prop] = "<a id='"
							+ aId
							+ "' href='javascript:void(0)'  class='hint--bottom' data-hint='"
							+ title + "'>" + val + "</a>";
					return aId;
				},
		createFormPanel : function() {
			var _this = this;
			var hide_id = FormUtil.getHidField({
						name : 'id',
						fieldLabel : 'id'
					});// 隐藏字段id
			var hide_entrustCustId = FormUtil.getHidField({
						name : 'entrustCustId',
						fieldLabel : 'entrustCustId'
					});// 隐藏字段id
			var txt_code = FormUtil.getTxtField({
						fieldLabel : '合同编号',
						name : 'code',
						"width" : "125"
					});
			var txt_procId = FormUtil.getHidField({
						fieldLabel : '流程实例ID',
						name : 'procId'
					});
			var txt_breed = FormUtil.getHidField({
						fieldLabel : '子业务流程ID',
						name : 'breed'
					});
			var mon_appAmount = FormUtil.getMoneyField({
						fieldLabel : '委托金额',
						name : 'appAmount',
						"allowBlank" : false,
						"width" : 160,
						autoBigAmount : true,
						unitText : '大写金额',
						unitStyle : 'margin-left:2px;color:red;font-weight:bold'
					});
			var rightH = "200";
			var bdat_payDate = FormUtil.getDateField({
						fieldLabel : '生效日期',
						name : 'payDate',
						"allowBlank" : false,
						"width" : 125,
						listeners : {
							'change' : payDateChangeListener
						}
					});
			var bdat_endDate = FormUtil.getDateField({
						fieldLabel : '失效日期',
						name : 'endDate',
						"allowBlank" : false,
						"width" : 125
					});
			var int_yearLoan = FormUtil.getIntegerField({
					name : 'yearLoan',
					width : 38,
					value: '0',
					"allowBlank" : false,
					listeners : {
						'change' : payDateChangeListener
					}
				});
				
			var int_monthLoan = FormUtil.getIntegerField({
						name : 'monthLoan',
						width : 38,
						value: '0',
						"allowBlank" : false,
						listeners : {
							'change' : payDateChangeListener
						}
					});
			
			var comp_loanLimit = FormUtil.getMyCompositeField({
						fieldLabel : '贷款期限',
						width : 215,
						name:'cpt_deadline',
						sigins : null,
						items : [int_yearLoan, {
									xtype : 'displayfield',
									value : '年',
									width : 6
								}, int_monthLoan, {
									xtype : 'displayfield',
									value : '月',
									width : 6
								}]
					});
			/* 根据放款日期算出贷款截止日期 */
			function payDateChangeListener() {
				var year = int_yearLoan.getValue();// 贷款期限年
				var month = int_monthLoan.getValue();// 贷款期限月
				var payDates= bdat_payDate.getValue();// 生效日期
			bdat_endDate.setDisabled(true);// 失效日期
				if (!payDates)
					return;
				var endDateVal = payDates;
				if (year) {
					if (Ext.isString(year))
						year = parseInt(year);
					endDateVal = endDateVal.add(Date.YEAR, year);
				}
				if (month) {
					if (Ext.isString(month))
						month = parseInt(month);
					endDateVal = endDateVal.add(Date.MONTH, month);
				}
				// payDate = new Date(payDate);
				bdat_endDate.setValue(endDateVal);
			}
			var txt_raeete = FormUtil.getTxtField({
						name : 'rate',
						width : 60,
						"allowBlank" : false
					});
			var txt_rateType = FormUtil.getLCboField({
						name : 'rateType',
						width : 60,
						"allowBlank" : false,
						"maxLength" : 50,
						data : [["1", "月利率"], ["2", "日利率"], ["3", "年利率"]]
					});
			var txt_unint = FormUtil.getLCboField({
						name : 'unint',
						width : 60,
						"allowBlank" : false,
						"maxLength" : 50,
						data : [["1", "%"], ["2", "‰"]]//键盘输入千分位符号英文状态下按alt+0137
					});
			var txt_rate = FormUtil.getMyCompositeField({
						itemNames : 'rateType,rate,unint',
						sigins : null,
						fieldLabel : '贷款利率',
						width : rightH,
						sigins : null,
						name : 'c_Date',
						items : [txt_rateType, txt_raeete, txt_unint]
					})

			var txt_payBank = FormUtil.getTxtField({
						fieldLabel : '收款银行',
						name : 'payBank',
						"allowBlank" : false,
						"width" : "125"
					});

			var txt_payAccount = FormUtil.getTxtField({
						fieldLabel : '收款账号',
						name : 'payAccount',
						"allowBlank" : false,
						"width" : "125"
					});

			var txt_accName = FormUtil.getTxtField({
						fieldLabel : '账户名',
						name : 'accName',
						"allowBlank" : false,
						"width" : "150"
					});
			var setdayTypeChangeListener = function(combox, newValue, oldValue) {
				var disabled = false;
				int_payDay.reset();
				if (newValue == "1") {
					var getDates= bdat_payDate.getValue();// 生效日期
					if(getDates){
						var myDate = new Date(getDates);
						int_payDay.setValue(myDate.getDate());
					}
				} /* 以实际放款日作为结算日时，禁用 */
				else if (newValue == "2") {
				EventManager.get('./sysSysparams_getParamsName.action', {
				params : {recode : 'PAYDAY_SET',sysid : _this.params.sysid},
								sfn : function(jsondate) {
									if (jsondate) {
										var val = jsondate.val;
										if (val) {
											int_payDay.setValue(val);
										} else {
											int_payDay.setDisabled(false)
										}
									} else {
										return;
									}
								}
							});
					disabled = true;
				}
				int_payDay.setDisabled(disabled);
			}
			var txt_remark = FormUtil.getLCboField({
						fieldLabel : '结算日',
						name : 'setdayType',
						"allowBlank" : false,
						width : 140,
						maxLength : 50,
						data : Lcbo_dataSource.applyType_datas,
						listeners : {
							'change' : setdayTypeChangeListener
						}
					});

			var int_payDay = FormUtil.getIntegerField({
						fieldLabel : '结算日',
//						id : _this.payDayId,
						name : 'payDay',
						"width" : 100,
						"allowBlank" : false,
						"maxLength" : 10
					});
			var txt_payDay = FormUtil.getMyCompositeField({
						fieldLabel : '结算日',
						sigins : null,
						itemNames : 'setdayType,payDay',
						name : 'compt_payDay',
						"allowBlank" : false,
						width : 220,
						items : [txt_remark, int_payDay, {
									xtype : 'displayfield',
									html : '号'
								}]
					});
			var mon_iamounts = FormUtil.getMoneyField({
						fieldLabel : '每月收益金额',
						name : 'iamount',
						"allowBlank" : false,
						"width" : 150
					});
			var txt_prange = FormUtil.getRadioGroup({
						fieldLabel : '委托产品范围',
						name : 'prange',
						"width" : '150',
						"maxLength" : 50,
						"items" : [{
									"boxLabel" : "所有产品",
									"name" : "prange",
									"inputValue" : "1"
								}, {
									"boxLabel" : "指定产品",
									"name" : "prange",
									"inputValue" : "2"
								}],
						listeners : {
							'change' : ChangePrange
						}
					});
			// 判断选中的产品的范围
			function ChangePrange() {
				var PraVal = txt_prange.getValue();
				if (PraVal && PraVal == 1) {
				var PraVal=Ext.getCmp(aid);
				var ids=PraVal.getRawValue();//comob为下拉框的id
				PraVal.setRawValue("");
				txt_products.setDisabled(true);
				}else{
				txt_products.setDisabled(false);
				}
				}
			var aid=Ext.id();
			var txt_products = new Ext.ux.form.AppComboxImg({// FormUtil.getTxtField({
				fieldLabel : '委托产品',
				name : 'productsId',
				"width" : 150,
				"allowBlank":true,
				id:aid,
				valueField : 'id',
				displayField : 'name',
				url : './fuEntrustCust_getName.action?id=' + _this.sysId,
				params : {
					sysId : this.params.sysid
				},
				allDispTxt : Lcbo_dataSource.allDispTxt,validate : function(){
					var valid = true;
					var val = this.getValue();
//					valid = val ? true : false;
					return valid;
				}
			});
			var txt_doDate = FormUtil.getDateField({
						fieldLabel : '合同签订日期',
						name : 'doDate',
						"width" : 150
					});
			var _isd=Ext.id();
			var txt_textarea = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						id:_isd,
						maxLength : '120',
						"width" : "600"
					});
			/*----------- 基本合同信息设置 ------------*/
			var fset_1 = FormUtil.createLayoutFieldSet({
						title : '增资申请信息'/* ,height:800 */
					}, [
							{
								cmns : FormUtil.CMN_THREE,
								fields : [txt_code, comp_loanLimit,
										mon_appAmount, bdat_payDate,
										bdat_endDate, txt_rate, txt_payBank,
										txt_payAccount, txt_accName,
										txt_remark, txt_payDay, mon_iamounts,
										txt_prange, txt_products, txt_doDate]
							},txt_textarea, hide_id, hide_entrustCustId,
							txt_breed,
							txt_procId]);
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var layout_fields = [fset_1, formDiyContainer];
			var btnTempSaveHtml = this.getButtonHml(this.btnIdObj.btnTempSave,
					'暂存增资申请');
			var btnSaveHtml = this.getButtonHml(this.btnIdObj.btnSave, '提交增资申请');
			var title = '增资申请信息&nbsp;&nbsp;' + btnTempSaveHtml + btnSaveHtml
			/* + '提示：[<span style="color:red;">带"*"的为必填项，金额默认单位为 "元"</span>]' */;
			var frm_cfg = {
				title : title,
				formDiyCfg : {
					sysId : _this.sysId,/* 系统ID */
					formdiyCode : FORMDIY_IND.EntrustContract,
					formIdName : 'id',/* 对应表单中的ID Hidden 值 */
					container : formDiyContainer
					/* 自定义字段存放容器 */
				},
				autoScroll : true,
				url : './fuAmountApply_save.action',
				labelWidth : 115
			};
			var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			this.attach = this.createAttachMentFs(this);
			applyForm.add(this.attach);
			var _this = this;
			applyForm.addListener('afterRender', function(panel) {
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
		 * 打开客户选择窗口
		 */
		openCustomerDialog : function() {
			var _this = this;
			var _contractId = this.applyPanel.getValueByName("entrustCustId");
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
		 * 将客户数据赋给 客户资料面板
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
				Cmw.importPackage('pages/app/dialogbox/AmountApplyDialogbox',
						function(module) {
							_this.customerDialog = module.DialogBox;
							_this.customerDialog.show(parentCfg);
						});
			}
		},
		/**
		 * 将选择的客户数据赋给客户资料详情面板
		 */
		setChooseVal : function(id, record) {
			var _this = this;
			var customerId = record.get("id");
			var data = record.data;
			var entrustCustId = _this.applyPanel.findFieldByName("entrustCustId");
			entrustCustId.setValue(customerId);
			var breed = _this.applyPanel.findFieldByName("breed");
			breed.setValue(this.breed);
			EventManager.get('./fuAmountApply_add.action', {
						params : {},
						sfn : function(json_data) {
							var _code = _this.applyPanel.findFieldByName("code");
							_code.setValue(json_data.code);
						}
					});
			this.customerPanel.reload({
						json_data : data
					}, true);
			this.applyPanel.enable();
		},

		/**
		 * 保存单据数据
		 * 
		 * @param opType
		 *            [0:暂存,1:提交]
		 */
		
		saveApplyData : function(submitType) {
			// if (!this.validApplyForm()) return;
			var _this = this;
			var currTabId = this.params.tabId;
			var apptabtreewinId = this.params.apptabtreewinId;
			var _this = this;
			EventManager.frm_save(this.applyPanel, {
				beforeMake : function(formDatas) {
					formDatas.submitType = submitType;
					if (_this.uuid)
						formDatas.uuid = _this.uuid;
				},
				sfn : function(formDatas) {
					if (formDatas["applyId"]) {
						_this.applyId = formDatas["applyId"];
						_this.entrustCustId = formDatas["entrustCustId"];
					} else {
						_this.applyId = null;
						_this.entrustCustId = null;
					}
//					if (_this.applyId) {
//						var attachParams = _this.getAttachParams(_this.applyId);
//						_this.attachMentFs.updateTempFormId(attachParams);
//					}
					
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
						tabId = CUSTTAB_ID.tempAamountApplyMgrTabId;
						Cmw.activeTab(apptabtreewinId, tabId, params);
					} else { /* 跳转到 业务审批页面 */
						var code = formDatas["code"];
						var procId = _this.applyPanel.getValueByName("procId");
						var sysId = _this.params.sysid;
						var contractId = _this.applyPanel
								.getValueByName('contractId');
						var msg = '';
						if (code) {
							msg = '确定提交编号为："' + code + '"的增资申请单?';
						} else {
							msg = '确定提交当前增资申请单?';
						}
						ExtUtil.confirm({
									title : '提示',
									msg : msg,
									fn : function() {
										var params = {
											isnewInstance : true,
											sysId : sysId,
											applyId : _this.applyId,
											entrustCustId : _this.entrustCustId,
											contractId : contractId,
											procId : procId,
											bussProccCode : 'B8888'
										};
										tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
										url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
										var title = '业务审批';
										Cmw.activeTab(apptabtreewinId, tabId,
												params, url, title);
									}
								});
					}
				}
			});
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
			this.entrustCustId = params.entrustCustId;
			this.breed = params.breed;
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
//			params.appgrid
//			var applyId = _this.params.applyId;
			var entrustCustId = this.params.entrustCustId;
			var contractId = this.params.contractId;
			if (optionType && optionType == OPTION_TYPE.EDIT) {
				// 修改 
				var errMsg = [];// ["修改时传参发生错误："];
				if (!_this.applyId) {
					errMsg[errMsg.length] = "必须传入参数\"applyId\"!<br/>";
				}
				if (null != errMsg && errMsg.length > 0) {
					errMsg = "修改时传参发生错误：<br/>" + errMsg.join(" ");
					ExtUtil.alert({
								msg : errMsg
							});
				}
				this.applyPanel.enable();
				this.applyPanel.reset();
				this.applyPanel.setValues('./fuAmountApply_get.action', {
							params : {
								id : _this.applyId
							},
							sfn : function(json_data) {
								if(json_data.payDate){
							var payDate=new Date(json_data["payDate"]);
							_this.applyPanel.setFieldValue("payDate",Ext.util.Format.date(payDate,'Y-m-d'));
							}
							if(json_data.endDate){
							var endDate=new Date(json_data["endDate"]);
							_this.applyPanel.setFieldValue("endDate",Ext.util.Format.date(endDate,'Y-m-d'));
							}
							var doDate=new Date(json_data["doDate"]);
							_this.applyPanel.setFieldValue("doDate",Ext.util.Format.date(doDate,'Y-m-d'));
							var cpt_deadline = _this.applyPanel.findFieldByName("cpt_deadline");
							Cmw.print(cpt_deadline);
							var yearLoan = json_data["yearLoan"] || 0;
							var monthLoan = json_data["monthLoan"] || 0;
							cpt_deadline.setValue({yearLoan:yearLoan,monthLoan:monthLoan});
							},
						ffn : function(json_data) {}
						});
//				this.attachMentFs.reload(this.getAttachParams(applyId));
			} else {/* 新增 */
				// this.attachMentFs.params = this.getAttachParams(this.uuid);
				this.applyPanel.setJsonVals({
							breed : this.breed
						});
				this.applyPanel.enable();
				this.applyPanel.disable();
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
				formType : ATTACHMENT_FORMTYPE.FType_37,
				formId : formId
			};
		},
		doResize : function() {

		},
		resize : function(adjWidth, adjHeight) {
			this.appMainPanel.setWidth(adjWidth);
			this.appMainPanel.setHeight(adjHeight);
			},
			/**
			 * 创建附件FieldSet 对象
			 * 
			 * @return {}
			 */
				createAttachMentFs : function(_this) {
					/*
					 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录，
					 * mortgage_path 定义在 resource.properties 资源文件中 isSave :
					 * 附件上传后，是否保存到 ts_attachment 表中。 true : 保存 params :
					 * {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment
					 * 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
					 * params 可在 reload 方法中提供 ----
					 */
					var uuid=Cmw.getUuid();
					var attachMentFs = new Ext.ux.AppAttachmentFs({
								title : '相关材料附件',
								isLoad : false,
								dir : 'mort_path',
								isSave : true,
								isNotDisenbaled : true,
								params:{sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_37,formId:uuid}
							});
					return attachMentFs;
				},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (null != this.appMainPanel) {
				if (this.customerDialog)
					this.customerDialog.destroy();
				if (this.gopinionDialog)
					this.gopinionDialog.destroy();
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		}
	}
});
