/**
 * 添加金融机构
 * 
 * @author 郑符明
 * @date 2014-03-04 04:56:40
 */
define(function(require, exports) {
			exports.WinEdit = {
				parentCfg : null,
				parent : null,
				optionType : OPTION_TYPE.ADD,/* 默认为新增 */
				appFrm : null,
				appWin : null,
				setParentCfg : function(parentCfg) {
					this.parentCfg = parentCfg;
					// --> 如果是Grid ，应该修改此处
					this.parent = parentCfg.parent;
					this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
				},
				createAppWindow : function() {
					this.appFrm = this.createForm();
					
					this.appWin = new Ext.ux.window.AbsEditWindow({
								title : '金融机构管理',
								width : 900,
								getUrls : this.getUrls,
								appFrm : this.appFrm,
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
						this.setParentCfg(_parentCfg);
					if (!this.appWin) {
						this.createAppWindow();
						
					}
					this.appWin.optionType = this.optionType;
					this.appWin.show();
					EventManager.add("./fcOutFunds_add.action", this.appFrm,{sfn : function(formdata) {
						this.appFrm.setFieldValues(formdata);}});
				},
				/**
				 * 销毁组件
				 */
				destroy : function() {
					if (!this.appWin)
						return;
					this.appWin.close(); // 关闭并销毁窗口
					this.appWin = null; // 释放当前窗口对象引用
				},
				/**
				 * 获取各种URL配置 addUrlCfg : 新增 URL editUrlCfg : 修改URL preUrlCfg :
				 * 上一条URL preUrlCfg : 下一条URL
				 */
				getUrls : function() {
					var urls = {};
					var parent = exports.WinEdit.parent;
					var cfg = null;
					// 1 : 新增 , 2:修改
					if (this.optionType == OPTION_TYPE.ADD) { // --> 新增
						/*--- 添加 URL --*/
						cfg = {
							params : {},
							defaultVal : {}
						};
						urls[URLCFG_KEYS.ADDURLCFG] = {
							url : 'fcOutFunds_add.action',
							cfg : cfg
						};
					} else {
						/*--- 修改URL --*/
						var selId = parent.getSelId();
						cfg = {
							params : {
								id : selId
							}
						};
						urls[URLCFG_KEYS.GETURLCFG] = {
							url : 'fcOutFunds_save.action',
							cfg : cfg
						};
					}
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
					var wd = 185;
					var txt_Isenabled = FormUtil.getHidField({
							fieldLabel : 'Isenabled',
							name : 'Isenabled',
							value : 0
						});
					var bdat_lastDate = FormUtil.getDateField({
								fieldLabel : '最后存入时间',
								name : 'lastDate',
								"width" : wd,
								"allowBlank" : false,
								hidden : true,
								value : new Date()
							});

					var int_payDay = FormUtil.getIntegerField({
								fieldLabel : '<span style="color:red">*</span>每月付息日',
								name : 'payDay',
								"width" : 115,
								unitText : '日',
								unitStyle : 'margin-left:2px;color:red;font-weight:bold',
								"maxLength" : 10
							});

					var bdat_endDate = FormUtil.getDateField({
								fieldLabel : '贷款截止日期',
								name : 'endDate',
								"width" : 135
							});

					var bdat_startDate = FormUtil.getDateField({
								fieldLabel : '贷款起始日期',
								name : 'startDate',
								"width" : 135
							});

					var int_xunint = FormUtil.getLCboField({
								fieldLabel : '%',
								name : 'xunint',
								"width" : 45,
								"allowBlank" : false,
								"value" : "1",
								data:[["1","%"],["2","‰"]],
								"maxLength" : 10
							});

					var dob_xrate = FormUtil.getIntegerField({
								fieldLabel : '担保费率',
								name : 'xrate',
								"width" : 85,
								"allowBlank" : false,
								"value" : "0.0000",
								"decimalPrecision" : "4"
							});
							
					var txt_xratestore = FormUtil.getMyCompositeField({
								itemNames : 'xrate,xunint',
								fieldLabel : '<span style="color:red">*</span>担保费率',
								name : 'xrate',
								width : wd,
								sigins : null,
								items :[dob_xrate, int_xunint ]
							});
							
					var dob_rate = FormUtil.getIntegerField({
								fieldLabel : '贷款利率',
								name : 'rate',
								"width" : 85,
								"allowBlank" : false,
								"value" : "0.0000",
								"decimalPrecision" : "4"
							});
							
					var int_unint = FormUtil.getLCboField({
								fieldLabel : '%',
								name : 'unint',
								"width" : 45,
								"allowBlank" : false,
								"value" : "1",
								data:[["1","%"],["2","‰"]],
								"maxLength" : 10
							});
							
					var txt_ratestore = FormUtil.getMyCompositeField({
								itemNames : 'rate,unint',
								fieldLabel : '<span style="color:red">*</span>贷款利率',
								name : 'rate',
								width : wd,
								sigins : null,
								items :[dob_rate, int_unint ]
							});
							
					var txt_uamount = FormUtil.getTxtField({
								fieldLabel : '剩余可用金额',
								name : 'uamount',
								"width" : 135,
								"allowBlank" : false,
								"value" : "0.00",
								hidden : true
							});

					var txt_bamount = FormUtil.getTxtField({
								fieldLabel : '累计贷出金额',
								name : 'bamount',
								"width" : wd,
								"allowBlank" : false,
								"value" : "0.00",
								hidden : true
							});

					var txt_totalAmount = FormUtil.getTxtField({
								fieldLabel : '贷款金额',
								name : 'totalAmount',
								"width" : 120,
								unitText : '元',
								unitStyle : 'margin-left:2px;color:red;font-weight:bold',
								"allowBlank" : false,
								"value" : "0.00",
								listeners : {
									change :function(){
									txt_uamount.setValue(txt_totalAmount.getValue());
									txt_bamount.setValue(0.00);
									}
								}
							});

					var txt_accountId = FormUtil.getTxtField({
								fieldLabel : '账户ID',
								name : 'accountId',
								"width" : wd,
								"allowBlank" : false,
								hidden :true
							});

					var txt_name = FormUtil.getTxtField({
								fieldLabel : '机构名称',
								name : 'name',
								"width" : wd,
								"allowBlank" : false
					});
					function ck_callback(grid,value,selRows){
							var record = selRows[0];
							txt_companyNum.setValue(record.get('account'));
							txt_accountId.setValue(record.get('id'));
					}
					structure = [{header : "id",name : 'id',hidden : true},
								{header: '公司名称',name: 'bankName',width:200},
								{header : '银行账号',name :'account',width: 200}];
					var txt_bankName = new Ext.ux.grid.AppComBoxGrid({
								gridWidth :420,
								fieldLabel : '<span style="color:red">*</span>借款银行',
								name : 'refmans',
							    structure:structure,
							    dispCmn:'bankName',
							    isAll:true,
							    url : './sysAccount_list.action',
							    needPage : false,
								"maxLength" : "50" ,
							    isLoad:true,
							    width : wd,
							   	callback : ck_callback
					});

					var int_orgType = FormUtil.getRadioGroup({
								fieldLabel : '机构类型',
								name : 'orgType',
								"width" : wd,
								"allowBlank" : false,
								"maxLength" : 10,
								items : [{boxLabel : '银行', name:'orgType',inputValue:1, checked: true},
									{boxLabel : '集合信托', name:'orgType',inputValue:2}]
							});
					var txt_companyNum = FormUtil.getReadTxtField({
								fieldLabel : '银行账号',
								name : 'account',
								"width" : 150,
								"allowBlank" : false,
								"maxLength" : "20"
					});
					
					var txt_code = FormUtil.getReadTxtField({
								fieldLabel : '编号',
								name : 'code',
								"width" : 175,
								"allowBlank" : false,
								"maxLength" : "20"
							});
					var txt_remark = FormUtil.getTAreaField({
								fieldLabel : '备注',
								name : 'remark',
								width : 650,
								"maxLength" : 200
					});
							
					var layout_fields = [{
						cmns : FormUtil.CMN_TWO,
						fields : [txt_code,txt_name]
					},{
						cmns : FormUtil.CMN_THREE,
						fields : [int_orgType,txt_bankName,txt_companyNum,txt_totalAmount, 
								txt_ratestore,txt_xratestore,int_payDay,bdat_startDate,
								bdat_endDate,txt_accountId,txt_bamount, txt_uamount,bdat_lastDate,txt_Isenabled]
					},txt_remark];
					var frm_cfg = {
						labelWidth :85,
						//title : '添加金融机构',
						url : 'fcOutFunds_save.action'
					};
					var appform_1 = FormUtil.createLayoutFrm(frm_cfg,
							layout_fields);

					return appform_1;
				}
			};
		});
