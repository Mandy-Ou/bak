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
		uuid : Cmw.getUuid(),/* 用于新增时，临时代替申请单ID */
		applyId : null,/* 申请单ID */
		btnIdObj : {
			btnChoseCust : Ext.id(null, 'btnChoseCust'),/* 客户选择 */
			btnTempSave : Ext.id(null, 'btnTempSave'),/* 暂存申请单 */
			btnSave : Ext.id(null, 'btnSave') /* 提交申请单 */
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
			var htmlArrs_1 = [
					'<tr><th colspan="6" style="font-weight:bold;text-align:left;font-size:33px" ><center>撤资申请</center></th><tr>',
					'<tr><th col="code">编号</th> <td col="code" >&nbsp;</td><th col="name">委托人姓名</th> <td col="name" >&nbsp;</td><th col="cardNum">身份证</th> <td col="cardNum" >&nbsp;</td></tr>',
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
				prevUrl : './fuEntrustCust_getContract.action',
				nextUrl : './fuEntrustCust_getContract.action',
				params : {
			// id : _this.applyId
				},
				callback : {
					sfn : function(jsonData) {
						_this.applyPanel.setFieldValue("contractId",
								jsonData.id);
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
		createFormPanel : function() {
			var _this = this;
			var hide_id = FormUtil.getHidField({
						name : 'entrustCustId',
						fieldLabel : '委托客户ID'
					});// 隐藏字段id
			var hide_entrustCustId = FormUtil.getHidField({
						name : 'entrustContractId',
						fieldLabel : '委托合同ID'
					});// 隐藏字段id
	
			var txt_procId = FormUtil.getHidField({
						fieldLabel : '流程实例ID',
						name : 'procId'
					});
			var txt_breed = FormUtil.getHidField({
						fieldLabel : '子业务流程ID',
						name : 'breed'
					});
			
			var txt_code = FormUtil.getReadTxtField({
				fieldLabel : '委托人姓名',
				name : 'name',
				"width" : "125"
			});
			var mon_appAmount = FormUtil.getReadTxtField({
						fieldLabel : '委托金额',
						name : 'appAmount',
						"allowBlank" : false,
						"width" : 160,
						autoBigAmount : true,
						unitText : '大写金额',
						unitStyle : 'margin-left:2px;color:red;font-weight:bold'
					});
			
			var mon_appAmountss = FormUtil.getMoneyField({
				fieldLabel : '撤资金额',
				name : 'bamount',
				"allowBlank" : false,
				"width" : 160,
				autoBigAmount : true,
				unitText : '大写金额',
				unitStyle : 'margin-left:2px;color:red;font-weight:bold'
			});
			var mon_appAmounsstss = FormUtil.getMoneyField({
				fieldLabel : '违约金额',
				name : 'wamount',
				"allowBlank" : false,
				"width" : 160,
				autoBigAmount : true,
				unitText : '大写金额',
				unitStyle : 'margin-left:2px;color:red;font-weight:bold'
			});
			var bdat_endDate = FormUtil.getDateField({
						fieldLabel : '撤资日期',
						name : 'backDate',
						"allowBlank" : false,
						"width" : 125
					});
			var txt_prange = FormUtil.getRadioGroup({
						fieldLabel : '是否期满',
						name : 'isNotExpiration',
						"allowBlank" : false,
						"width" : '150',
						"maxLength" : 50,
						"items" : [{
									"boxLabel" : "未满",
									"name" : "isNotExpiration",
									"inputValue" : 0
								}, {
									"boxLabel" : "满期",
									"name" : "isNotExpiration",
									"inputValue" : 1
								}]
					});

			
			var txt_textarea = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'clause',
						"width" : "300"
					});
			/*----------- 基本合同信息设置 ------------*/
			var fset_1 = FormUtil.createLayoutFieldSet({
						title : '基本合同信息'/* ,height:800 */
					}, [
							{
								cmns : FormUtil.CMN_THREE,
								fields : [txt_code,mon_appAmount,mon_appAmountss,mon_appAmounsstss,bdat_endDate,txt_prange]
							},txt_textarea,hide_id,hide_entrustCustId,txt_procId,txt_breed]);
			var formDiyContainer = new Ext.Container({
						layout : 'fit'
					});
			var layout_fields = [fset_1, formDiyContainer];
			var btnTempSaveHtml = this.getButtonHml(this.btnIdObj.btnTempSave,
					'暂存合同');
			var btnSaveHtml = this.getButtonHml(this.btnIdObj.btnSave, '提交合同');
			var title = '撤资申请合同信息&nbsp;&nbsp;' + btnTempSaveHtml + btnSaveHtml
			/* + '提示：[<span style="color:red;">带"*"的为必填项，金额默认单位为 "元"</span>]' */;
			var frm_cfg = {
				title : title,
				formDiyCfg : {
					sysId : _this.sysId,/* 系统ID */
					formdiyCode : FORMDIY_IND.EntrustContract,
					/* * 引用Code --> 对应 * ts_Formdiy * 中的业务引用键：recode
																 */
					formIdName : 'id',/* 对应表单中的ID Hidden 值 */
					container : formDiyContainer
					/* 自定义字段存放容器 */
				},
				autoScroll : true,
				url : './fuBamountApply_save.action',
				labelWidth : 115
			};
			var applyForm = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
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
						title : '相关附件上传',
						isLoad : false,
						dir : 'extension_path',
						isSave : true
					});
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
				Cmw.importPackage('pages/app/dialogbox/BmountApplyDialogbox',
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
			var name=data.name;
			var appAmount=data.appAmount;
			var entrustContract=data.entrustContractId;
			var entrustCustId = _this.applyPanel.findFieldByName("entrustCustId");
			var entrustContractId = _this.applyPanel.findFieldByName("entrustContractId");
			var names = _this.applyPanel.findFieldByName("name");
			var appAmounts = _this.applyPanel.findFieldByName("appAmount");
			appAmounts.setValue(appAmount);
			entrustCustId.setValue(customerId);
			names.setValue(name);
			entrustContractId.setValue(entrustContract);
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
						tabId = CUSTTAB_ID.tempBamountApplyMgrTabId;
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
											contractId : contractId,
											procId : procId,
											bussProccCode : 'B6666'
										};
										tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
										url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
										var title = '业务审批';
										Cmw.activeTab(apptabtreewinId, tabId,params, url, title);}
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
			var applyId = this.params.applyId;
			var contractId = this.params.contractId;
			if (optionType && optionType == OPTION_TYPE.EDIT) {/* 修改 */
				var errMsg = [];// ["修改时传参发生错误："];
				if (!applyId) {
					errMsg[errMsg.length] = "必须传入参数\"applyId\"!<br/>";
				}
//				if (!contractId) {
//					errMsg[errMsg.length] = "必须传入参数\"contractId\"!<br/>";
//				}
				if (null != errMsg && errMsg.length > 0) {
					errMsg = "修改时传参发生错误：<br/>" + errMsg.join(" ");
					ExtUtil.alert({
								msg : errMsg
							});
					return;
				}
				this.applyPanel.enable();
				this.applyPanel.reset();

//				this.customerPanel.reload({
//							contractId : contractId
//						});
//				this.applyPanel.setValues('./fuEntrustCust_get.action', {
//							params : {
//								id : _this.applyId
//							},
//							sfn : function(json_data) {
//								_this.applyPanel.setFieldValue("contractId",json_data.id);
//
//							}
//						});
//				this.attachMentFs.reload(this.getAttachParams(applyId));
				
				this.applyPanel.setValues('./fuBamountApply_get.action', {
					params : {
						id : applyId
					},
					sfn : function(json_data) {
						_this.applyPanel.setFieldValue("contractId",json_data.id);},
						ffn : function(json_data) {
							
						}
				});
			} else {/* 新增 */
				// this.attachMentFs.params = this.getAttachParams(this.uuid);
				this.applyPanel.setJsonVals({
							breed : breed
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
