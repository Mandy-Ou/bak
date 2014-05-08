/**
 * 委托申请 #DESCRIPTION#
 * 
 * @author smartplatform_auto
 * @date 2013-01-06 07:00:32
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增 */
		appFrm : null,
		appWin : null,
		custType : null,
		customerId : null,
		sysid : null,
		applyId : null,
		entrustCustId : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysid = parentCfg.parent.sysid;
			this.applyId = parentCfg.parent.applyId;
			this.entrustCustId = parentCfg.parent.entrustCustId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function() {
			var _this = this;
//			EventManager.get('./fuAmountApply_add.action', {
//				params : {},
//				sfn : function(json_data) {
//					
//				}
//			})
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({
						autoScroll : true,
						width : 1050,
						getUrls : this.getUrls,
						appFrm : this.appFrm,
						isNotSetVs : true,
						optionType : this.optionType,
						refresh : this.refresh
					});
		},
		/**
		 * 显示弹出窗口
		 * 
		 * @param _parentCfg
		 *            弹出之前传入的参数
		 */
		show : function(_parentCfg) {
			if (_parentCfg)
				this.setParams(_parentCfg);
			if (!this.appWin) {
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			// if(!this.appWin) return;
			this.appWin.close(); // 关闭并销毁窗口
			this.appWin = null; // 释放当前窗口对象引用
		},
		/**
		 * 获取各种URL配置 addUrlCfg : 新增 URL editUrlCfg : 修改URL preUrlCfg : 上一条URL
		 * preUrlCfg : 下一条URL
		 */
		
		getUrls : function() {
			var urls = {};
			var _this = exports.WinEdit;
			var parent = exports.WinEdit.parent;
			var applyId = parent.applyid;
			var  onel=parent.onel;
			var  entrustCustId=onel.entrustCustId;
			var entrustCustVal = _this.appFrm.findFieldByName("entrustCustId");
			entrustCustVal.setValue(entrustCustId);
			var applyIdVal = _this.appFrm.findFieldByName("applyId");
			applyIdVal.setValue(applyId);
			var cfg = null;
			// 1 : 新增 , 2:修改
			if (this.optionType == OPTION_TYPE.ADD) {
				/*--- 修改URL --*/
				cfg = {
					params : {
						id : applyId
					},
					sfn : function(json_data) {
						_this.appFrm.setVs(json_data);
//						_this.appFrm.setVs(json_data);
						var doDate = _this.appFrm.findFieldByName('doDate');
						doDate.enable();
						var birthday=	_this.appFrm.findFieldByName("payDate");
						var data=new Date(json_data["payDate"]);
						birthday.setValue(data);
						var _endDate=	_this.appFrm.findFieldByName("endDate");
						var _data=new Date(json_data["endDate"]);
						_endDate.setValue(_data);
						var _endDatedoDate=	_this.appFrm.findFieldByName("doDate");
						if(json_data["doDate"]){
							var _datadoDate=new Date(json_data["doDate"]);
							_endDatedoDate.setValue(_datadoDate);
						}
						var uuid = Cmw.getUuid();
						var _code = _this.appFrm.findFieldByName("code");
						if (_code) {
							var _codes = 'E' + uuid;
							_code.setValue(_codes);
						}
						if(json_data["id"]){
							var _id=	_this.appFrm.findFieldByName("id");
							_id.setValue("");
						}
						if(json_data["id"]){
							var _id=	_this.appFrm.findFieldByName("applyId");
							_id.setValue(json_data["id"]);
						}
					}
				};
				urls[URLCFG_KEYS.ADDURLCFG] = {
					url : './fuAmountApply_get.action',
					cfg : cfg
				};
		} else {
				/*--- 修改URL --*/
				cfg = {
					params : {
						applyId : applyId
					},
					sfn : function(json_data) {
						var birthday=	_this.appFrm.findFieldByName("payDate");
						var data=new Date(json_data["payDate"]);
						birthday.setValue(data);
						var _endDate=	_this.appFrm.findFieldByName("endDate");
						var _data=new Date(json_data["endDate"]);
						_endDate.setValue(_data);
						var _endDatedoDate=	_this.appFrm.findFieldByName("doDate");
						if(json_data["doDate"]){
							var _datadoDate=new Date(json_data["doDate"]);
							_endDatedoDate.setValue(_datadoDate);
						}
					}
				};
				urls[URLCFG_KEYS.GETURLCFG] = {
					url : './fuEntrustContract_get.action',
					cfg : cfg
				};
			}
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {
				url : './fuEntrustContract_prev.action',
				cfg : cfg
			};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {
				url : './fuEntrustContract_next.action',
				cfg : cfg
			};
			this.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT + ","
					+ Btn_Cfgs.NEXT_BTN_TXT);
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data) {
			var _this = exports.WinEdit;
			if (_this.parentCfg.self.refresh)
				_this.parentCfg.self.refresh(_this.optionType, data);
		},
		/**
		 * 创建Form表单
		 */
		createForm : function() {
			var _this = this;
			var hide_id = FormUtil.getHidField({
						name : 'id',
						fieldLabel : 'id'
					});// 隐藏字段id
			var hide_entrustCustId = FormUtil.getHidField({
						name : 'entrustCustId',
						fieldLabel : 'entrustCustId'
					});// 隐藏字段id
			var hide_applyId = FormUtil.getHidField({
						name : 'applyId',
						fieldLabel : 'applyId'
					});// 申请单ID
			var txt_code = FormUtil.getTxtField({
						fieldLabel : '合同编号',
						name : 'code',
						"width" : "125"
					});
			var txt_procId = FormUtil.getHidField({
						fieldLabel : '流程实例ID',
						name : 'procId'
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
						fieldLabel : '委托生效日期',
						name : 'payDate',
						"allowBlank" : false,
						"width" : 125,
						listeners : {
							'change' : payDateChangeListener
						}
					});
			var bdat_endDate = FormUtil.getDateField({
						fieldLabel : '委托失效日期',
						name : 'endDate',
						"allowBlank" : false,
						"width" : 125
					});
			bdat_endDate.disable();// 禁用失效日期
			/* 根据放款日期算出贷款截止日期 */
			var int_yearLoan = FormUtil.getTxtField({
					name : 'yearLoan',
					width : 38,
					"allowBlank" : false,
					listeners : {
						'change' : payDateChangeListener
					}
				});
			var int_monthLoan = FormUtil.getTxtField({
						name : 'monthLoan',
						width : 38,
						"allowBlank" : false,
						listeners : {
							'change' : payDateChangeListener
						}
					});
			var txt_yearLoan = FormUtil.getMyCompositeField({
						fieldLabel : '贷款期限',
						width : 215,
						sigins : null,
						itemNames :"yearLoan,monthLoan",
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
				var payDates = bdat_payDate.getValue();// 生效日期
				var endDate = bdat_endDate.getValue();// 失效日期
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
						data : [["1", "%"], ["2", "‰"]]
					});
			var txt_rate = FormUtil.getMyCompositeField({
						itemNames : 'rateType,rate,unint',
						sigins : null,
						fieldLabel : '贷款利率',
						width : rightH,
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
						"width" : "188"
					});
			
			var setdayTypeChangeListener = function(combox, newValue, oldValue) {
				var disabled = false;
				int_payDay.reset();
				if (newValue == "1") {
					EventManager.get('./sysSysparams_getParamsName.action', {
								params : {
									recode : 'PAYDAY_SET',
									sysid : _this.sysid
								},
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
//					int_payDay.setDisabled(disabled);
				} /* 以实际放款日作为结算日时，禁用 */
				else if (newValue == "2") {
					EventManager.get('./sysSysparams_getParamsName.action', {
								params : {
									recode : 'PAYDAY_SET',
									sysid : _this.sysid
								},
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
						fieldLabel : '结息日类型',
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
						id : _this.payDayId,
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
						"allowBlank" : false
					});
			var txt_prange =  FormUtil.getRadioGroup({
				fieldLabel : '委托产品范围',
				name : 'prange',
				"allowBlank" : false,
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
		var PraVals = txt_prange.getValue();
		var PraVal=Ext.getCmp(_id);
		if (PraVals && PraVals == 1) {
		PraVal.setValue(true);
		txt_products.setRawValue("");
		txt_products.setDisabled(true);
		}else{
		txt_products.setDisabled(false);
		PraVal.setValue(false);
		}
		}
			var _id=Ext.id();
			var txt_products = new Ext.ux.form.AppComboxImg({// FormUtil.getTxtField({
				fieldLabel : '委托产品',
				name : 'products',
				"width" : 200,
				id:_id,
				valueField : 'id',
				displayField : 'name',
				url : './fuEntrustCust_getName.action?id=' +self.sysId,
//				params : {
//					sysId : this.params.sysid
//				},
				allDispTxt : Lcbo_dataSource.allDispTxt,validate : function(){
					var valid = true;
					var val = this.getValue();
//					valid = val ? true : false;
					return valid;
				}
			});
			
			var txt_doDate = FormUtil.getDateField({
						fieldLabel : '合同签订日期',
						name : 'doDate'
					});
			var txt_textarea = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						maxLength : 120,
						"width" : "872"
					});
			/*----------- 基本合同信息设置 ---------*/
			var layout_fields =[
							{cmns : FormUtil.CMN_THREE,
								fields : [txt_code, txt_yearLoan,
										mon_appAmount, bdat_payDate,
										bdat_endDate, txt_rate, txt_payBank,
										txt_payAccount, txt_accName,
										txt_remark, txt_payDay, mon_iamounts,
										txt_prange,txt_products,txt_doDate]
							}, txt_textarea, hide_id, hide_entrustCustId,txt_procId,hide_applyId];
			var frm_cfg = {
				title : '委托合同信息编辑'+ '<span style="color:red">（提示：如果合同编号不输入，系统会自动生成合同编号；否则，就以用户输入的合同编号为准）</span>',
				labelWidth : 110,
				autoHeight : true,
				url : './fuEntrustContract_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;

		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function() {},
		/**
		 * 上一条
		 */
		preData : function() {

		},
		/**
		 * 下一条
		 */
		nextData : function() {

		},
		/**
		 * 保存数据
		 */
		saveData : function() {
			
		},
		/**
		 * 重置数据
		 * 
		 */
		resetData : function() {
		}
	};
});
