/**
 * 追加資金
 * 
 * @author 鄭符明
 * @date 2014-03-04 05:35:56
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
								title : '追加资金',
								width : 300,
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
						cfg = {params : {id: selId}};
						urls[URLCFG_KEYS.GETURLCFG] = {url : 'fcOutFunds_get.action',cfg : cfg};
					}
					var id = this.appFrm.getValueByName("id");
					cfg = {
						params : {
							id : id
						}
					};
					/*--- 上一条 URL --*/
					urls[URLCFG_KEYS.PREURLCFG] = {
						url : 'fcOutFunds_prev.action',
						cfg : cfg
					};
					/*--- 下一条 URL --*/
					urls[URLCFG_KEYS.NEXTURLCFG] = {
						url : 'fcOutFunds_next.action',
						cfg : cfg
					};
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
		
					var txt_name = FormUtil.getReadTxtField({
								fieldLabel : '金融机构名称',
								name : 'name',
								"width" : 125
							});
					var txt_totalAmount = FormUtil.getReadTxtField({
								fieldLabel : '贷款金额',
								name : 'totalAmount',
								"width" : 120,
								unitText : '元',
								unitStyle : 'margin-left:2px;color:red;font-weight:bold'
							});
					var txt_nowAmount = FormUtil.getTxtField({
								fieldLabel : '本次追加金额',
								name : 'nowAmount',
								"width" : 120,
								unitText : '元',
								unitStyle : 'margin-left:2px;color:red;font-weight:bold'
								/*listeners : {	
									change : function(){
										txt_totalAmount.setValue(Number(txt_totalAmount.getValue())+Number(txt_nowAmount.getValue()));
									}
								}*/
							});	
						
					var txt_id = FormUtil.getHidField({
								fieldLabel : 'id',
								name : 'id'
							});
					var txt_Isenabled = FormUtil.getHidField({
								fieldLabel : 'Isenabled',
								name : 'Isenabled'
							});

					var int_payDay = FormUtil.getHidField({
								fieldLabel : '<span style="color:red">*</span>每月付息日',
								name : 'payDay'
							});

					var bdat_endDate = FormUtil.getHidField({
								fieldLabel : '贷款截止日期',
								name : 'endDate'
							});

					var bdat_startDate = FormUtil.getHidField({
								fieldLabel : '贷款起始日期',
								name : 'startDate'
							});

					var int_xunint = FormUtil.getHidField({
								fieldLabel : '%',
								name : 'xunint'
							});

					var dob_xrate = FormUtil.getHidField({
								fieldLabel : '担保费率',
								name : 'xrate'
							});
							
					var txt_xratestore = FormUtil.getHidField({
								fieldLabel : '<span style="color:red">*</span>担保费率',
								name : 'xrate'
							});
							
					var dob_rate = FormUtil.getHidField({
								fieldLabel : '贷款利率',
								name : 'rate'
							});
							
					var int_unint = FormUtil.getHidField({
								fieldLabel : '%',
								name : 'unint'
							});
							
					var txt_ratestore = FormUtil.getHidField({
								fieldLabel : '<span style="color:red">*</span>贷款利率',
								name : 'rate'
							});
							
					var txt_uamount = FormUtil.getHidField({
								fieldLabel : '剩余可用金额',
								name : 'uamount'
							});

					var txt_bamount = FormUtil.getHidField({
								fieldLabel : '累计贷出金额',
								name : 'bamount'
							});


					var txt_accountId = FormUtil.getHidField({
								fieldLabel : '账户ID',
								name : 'accountId'
							});

					var int_orgType = FormUtil.getHidField({
								fieldLabel : '机构类型',
								name : 'orgType'
							});
					var txt_companyNum = FormUtil.getHidField({
								fieldLabel : '银行账号',
								name : 'account'
					});
					
					var txt_code = FormUtil.getHidField({
								fieldLabel : '编号',
								name : 'code'
							});
						
					var bdat_lastDate = FormUtil.getHidField({
								fieldLabel : '最后存入时间',
								name : 'lastDate'
							});
					var layout_fields = [txt_name,txt_totalAmount, txt_nowAmount ,
					txt_Isenabled,txt_code,txt_companyNum,int_orgType,txt_accountId,
					txt_bamount,txt_uamount,int_unint,dob_rate,dob_xrate,int_xunint,
					bdat_startDate,bdat_endDate,int_payDay,bdat_lastDate,txt_id];
					var frm_cfg = {
						url : 'fcOutFunds_save.action'
					};
					var appform_1 = FormUtil.createLayoutFrm(frm_cfg,
							layout_fields);

					return appform_1;
				},
				/**
				 * 为表单元素赋值
				 */
				setFormValues : function() {

				},
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
				 *  重置数据 
				 */
				resetData : function() {
				}
			};
		});
