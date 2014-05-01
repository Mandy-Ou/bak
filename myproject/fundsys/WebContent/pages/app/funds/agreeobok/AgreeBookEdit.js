/**
 * 借款承诺书#DESCRIPTION#
 * 
 * @date 2013-01-06 07:00:32
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		payDayId : Ext.id(null, 'payDay'),
		params : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增 */
		appFrm : null,
		appWin : null,
		custType : null,
		sysid : null,
		formId : null,
		customerId : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysid = parentCfg.parent.sysid;
			this.formId = parentCfg.parent.applyid;
			this.customerId = parentCfg.parent.customerId
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function() {
			var _this = this;
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
			var self=this;
			var _this = exports.WinEdit;
			var parent = exports.WinEdit.parent;
			var formId = parent.applyid;
			var customerId= parent.customerId;
			var cfg = null;
			var _customerId = _this.appFrm.findFieldByName("customerId");
			// 1 : 新增 , 2:修改
			if (this.optionType == OPTION_TYPE.ADD) { // --> 新增
				/*--- 添加 URL --*/
				cfg = {
					params : {
						formId : formId
					},
					defaultVal : {
						formId :_this.formId
 ,customerId:_this.customerId }
				};
				urls[URLCFG_KEYS.ADDURLCFG] = {
					url : './fuAgreeBook_add.action',
					cfg : cfg
				};
			} else {
				/*--- 修改URL --*/
				cfg = {
					params : {
							formId:_this.formId,customerId:_this.customerId
					},
					sfn : function(json_data) {}
				};
				urls[URLCFG_KEYS.GETURLCFG] = {
					url : './fuAgreeBook_get.action',
					cfg : cfg
				};
			}
			var _id = this.appFrm.getValueByName("formId");
			cfg = {
				params : {
					formId : parent.formId
				}
			};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {
				url : './fuAgreeBook_prev.action',
				cfg : cfg
			};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {
				url : './fuAgreeBook_next.action',
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
			if (_this.parentCfg.self.refresh){
				_this.parentCfg.self.refresh(_this.optionType, data);
			}
		},
		/**
		 * 创建Form表单
		 */
		createForm : function() {
				var _this = this;
				var txt_bman = FormUtil.getTxtField({
							fieldLabel : '出借人',
							name : 'bman',
							"width" : 125,
							"maxLength" : 50
						});
				var hide_id = FormUtil.getHidField({
				fieldLabel : 'id',
				name : 'id',
				"width" : 125,
				"maxLength" : 50
			});
				var txt_yearLoan = FormUtil.getIntegerField({
								fieldLabel : '借款期限(年)',
								name : 'yearLoan',
								width : "50"
							});

					var txt_monthLoan = FormUtil.getIntegerField({
								fieldLabel : '借款期限(月)',
								name : 'monthLoan',
								width : "50"
							});

					var txt_dayLoan = FormUtil.getIntegerField({
								fieldLabel : '借款期限(日)',
								name : 'dayLoan',
								width : "50"
							});

					var txt_yearLoassn = FormUtil.getMyCompositeField({
								fieldLabel : '借款期限',
								width : "200",
								itemNames : 'yearLoan,monthLoan,dayLoan',
								sigins : null,
								items : [txt_yearLoan, {
											xtype : 'displayfield',
											value : '年'
										}, txt_monthLoan, {
											xtype : 'displayfield',
											value : '月',
											width : 6
										}, txt_dayLoan, {
											xtype : 'displayfield',
											value : '日',
											width : 6
										}]
							});
					var txt_overyLoan = FormUtil.getIntegerField({
								fieldLabel : '超过期限(年)',
								name : 'overyLoan',
								"width" : "50"
							});

					var txt_overmLoan = FormUtil.getIntegerField({
								fieldLabel : '超过期限(月)',
								name : 'overmLoan',
								"width" : "50"
							});

					var txt_overdLoan = FormUtil.getIntegerField({
								fieldLabel : '超过期限(日)',
								name : 'overdLoan',
								"width" : "50"
							});
					var txt_overan = FormUtil.getMyCompositeField({
								fieldLabel : '超过期限',
								width : "600",
								itemNames : 'overyLoan,overmLoan,overdLoan',
								sigins : null,
								items : [txt_overyLoan, {
											xtype : 'displayfield',
											value : '年'
										}, txt_overmLoan, {
											xtype : 'displayfield',
											value : '月'
										}, txt_overdLoan, {
											xtype : 'displayfield',
											value : '日'
										}]
							});
						var txt_upratesss = FormUtil.getIntegerField({
							fieldLabel : '上浮利率1',
							name : 'uprate',
							"width" : 100
						});
						var txt_unintsds = FormUtil.getLCboField({
							fieldLabel : '上浮利率单位1',
							name : 'unint',
							"allowBlank" : false,
							"maxLength" : 50,
							data : [ [ "1", "%" ], [ "2", "‰" ] ],
							"width" : 50
						});
			
						var txt_unint = FormUtil.getMyCompositeField({
							itemNames : 'uprate,unint',
							sigins : null,
							"width" : 500,
							fieldLabel : '上浮利率1',
							sigins : null,
							items : [ txt_upratesss, txt_unintsds, {
								xtype : 'displayfield',
								value : '',
								width : 6
							} ]
						})	
	
					
					
					var txt_overyLoan2 = FormUtil.getIntegerField({
								fieldLabel : '超过期限2(年)',
								name : 'overyLoan2',
								"width" : "50"
							});

					var txt_overmLoan2 = FormUtil.getIntegerField({
								fieldLabel : '超过期限2(月)',
								name : 'overmLoan2',
								"width" : "50"
							});
	
						var txt_overdLoan2 = FormUtil.getIntegerField({
									fieldLabel : '超过期限2(日)',
									name : 'overdLoan2',
									"width" : "50"
								});
							
						var txt_ovean2 = FormUtil.getMyCompositeField({
						fieldLabel : '超过期限2',
						width : "600",
						itemNames : 'overyLoan2,overmLoan2,overdLoan2',
						sigins:null,
						items : [txt_overyLoan2, {
									xtype : 'displayfield',
									value : '年',
									width : 6
								}, txt_overmLoan2, {
									xtype : 'displayfield',
									value : '月',
									width : 6
								}, txt_overdLoan2, {
									xtype : 'displayfield',
									value : '日',
									width : 6
								}]
					});
	
				var txt_uprate2esd = FormUtil.getIntegerField({
							fieldLabel : '上浮利率2',
							name : 'uprate2',
							"width" : 100
						});
	
				var txt_unint2sed =FormUtil.getLCboField({
						fieldLabel : '上浮利率单位2',
						name : 'unint2',
						"allowBlank" : false,
						"maxLength" : 50,
						data : [["1", "%"], ["2", "‰"]],"width" :50
					});
					
				var 	txt_unint2=	FormUtil.getMyCompositeField({
						itemNames : 'uprate2,unint2',
						sigins : null,
						"width" : 500,
						fieldLabel : '上浮利率2',
						sigins : null,
							items : [txt_uprate2esd, txt_unint2sed, {
									xtype : 'displayfield',
									value : '',
									width : 6
								}]
					})	
				var txt_lateDays = FormUtil.getIntegerField({
							fieldLabel : '逾期天数',
							name : 'lateDays',
							"width" : "125"
						});
				var txt_ppratedds = FormUtil.getIntegerField({
							fieldLabel : '罚息利率',
							name : 'pprate',
							"width" : 100
						});
				var txt_punintsse =FormUtil.getLCboField({
						fieldLabel : '罚息利率单位1',
						name : 'punint',
						"allowBlank" : false,
						"maxLength" : 50,"width" : 50,
						data : [["1", "%"], ["2", "‰"]]
					});
					
				var txt_punint=	FormUtil.getMyCompositeField({
						itemNames : 'pprate,punint',
						sigins : null,
						"width" : 500,
						fieldLabel : '罚息利率',
						items : [txt_ppratedds, txt_punintsse, {
									xtype : 'displayfield',
									value : '',
									width : 6
								}]
					});
				var txt_domonthes = FormUtil.getTxtField({
							fieldLabel : '处置期限(月)',
							name : 'domonthes',
							"width" : "125"
						});
	
				var txt_comitMan = FormUtil.getTxtField({
							fieldLabel : '承诺人',
							name : 'comitMan',
							"width" : "125"
						});
						
				var txt_customerId = FormUtil.getHidField({
						fieldLabel : 'customerId',
						name : 'customerId',
						"width" : "125"
					});
				var txt_formId = FormUtil.getHidField({
					fieldLabel : 'formId',
					name : 'formId',
					"width" : "125"
				});

				var bdat_comitDate = FormUtil.getDateField({
							fieldLabel : '承诺日期',
							name : 'comitDate',
							"width" : 125
						});
				var layout_fields = [{
					cmns : FormUtil.CMN_THREE,
					fields : [txt_bman,bdat_comitDate,txt_yearLoassn,txt_comitMan,txt_unint,txt_overan ,txt_punint,txt_unint2,txt_ovean2,
							 txt_lateDays, txt_domonthes,txt_punint,txt_customerId,txt_formId,hide_id]}];
			var frm_cfg = {
				title : '借款承诺书信息编辑',
				labelWidth : 110,
				autoHeight : true,
				url : './fuAgreeBook_save.action'
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
		saveData : function() {},
		/**
		 * 重置数据
		 * 
		 */
		resetData : function() {
		}
	};
});
