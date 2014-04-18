/**
 * 收入支出管理(编辑)
 * 
 * @author smartplatform_auto
 * @date 2013-01-23 07:16:22
 */
define(function(require, exports) {
			exports.WinEdit = {
				parentCfg : null,
				parent : null,
				optionType : OPTION_TYPE.CUT,/* 默认为新增 */
				appFrm : null,
				appWin : null,
				sysId :  null,
				setParentCfg : function(parentCfg) {
					this.parentCfg = parentCfg;
					// --> 如果是Grid ，应该修改此处
					this.parent = parentCfg.parent;
					this.sysId=this.parent.sysId;
					this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
					
				},
				createAppWindow : function() {
					this.appFrm = this.createForm();
					this.appWin = new Ext.ux.window.AbsEditWindow({
								prevNextBtnHiden : false,
								width : 350,
								optionType : this.optionType, 
								getUrls : this.getUrls,
								appFrm : this.appFrm,
								optionType : this.optionType,
								refresh : this.refresh
							});
				},
				/**
				 * 显示弹出窗口
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
					this.appWin.close(); // 关闭并销毁窗口
					this.appWin = null; // 释放当前窗口对象引用
				},
				/**
				 * 获取各种URL配置 addUrlCfg : 新增 URL editUrlCfg : 修改URL preUrlCfg :
				 * 上一条URL preUrlCfg : 下一条URL
				 */
				getUrls : function() {
					var urls = {};
					var _this = exports.WinEdit;
					var parent = exports.WinEdit.parent;
					var cfg = null;
					// 1 : 新增 , 2:修改
					if (this.optionType == OPTION_TYPE.ADD) { // --> 新增
						/*--- 添加 URL --*/
						cfg = {
							params : {},
							defaultVal : {sysId:_this.sysId}
						};
						urls[URLCFG_KEYS.ADDURLCFG] = {
							url : './fcFundsWater_add.action',
							cfg : cfg
						};
					} else {
						/*--- 修改URL --*/
						var selId = parent.getSelId();
						cfg = {
							params : {
								id : selId
							},
							defaultVal : {sysId:_this.sysId},
							sfn : function(json_Data){
								var waterType = json_Data.waterType;
								 waterType = Render_dataSource.DailRender(waterType.toString());
								 _this.appFrm.findFieldByName('waterType').setValue(waterType);
							},
							ffn : function(json_Data){
								
							}
						};
						urls[URLCFG_KEYS.GETURLCFG] = {
							url : './fcFundsWater_getDatilManage.action',
							cfg : cfg
						};
					}
					var id = this.appFrm.getValueByName("id");
					cfg = {
						params : {
							id : id
						}
					};
					/*--- 上一条 URL --*/
					urls[URLCFG_KEYS.PREURLCFG] = {
						url : './fcFundsWater_prev.action',
						cfg : cfg
					};
					/*--- 下一条 URL --*/
					urls[URLCFG_KEYS.NEXTURLCFG] = {
						url : './fcFundsWater_next.action',
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
					var txt_id = FormUtil.getHidField({
						    fieldLabel: 'ID',
						    name: 'id',
						    width:180,
						    hidden:true
						});
						var txss_id = FormUtil.getHidField({
						    fieldLabel: 'waterType',
						    name: 'waterType',
						    width:180,
						    hidden:true,
						      renderer : function(val){
		    					return Render_dataSource.DailRender(val);}});
						var Sys_id = FormUtil.getTxtField({
					    fieldLabel: 'sysId',
					    name: 'sysId',
					    hidden:true,
					    width:180
					});
						var cbo_inType = FormUtil.getReadTxtField({
							fieldLabel : '费用类型',
							name : 'otherSort',
							width:180,
							readOnly :true,
							register : REGISTER.GvlistDatas,
							restypeId : '180006'
							,
							 "url" :"./sysGvlist_cbodatas.action?restypeId=180006"
							});
							var txt_raDispFormula = FormUtil.getTxtField({
								fieldLabel : '收款金额',
								width:180,
								name : 'amounts'
							});
							var txt_raDispEdit = FormUtil.getReadTxtField({
								fieldLabel : '收支类型',
								width:180,
								readOnly :true,
								name : 'waterType'
		    				});
							var callback = function(cboGrid,selVals){
								var grid = cboGrid.grid;
								var record = grid.getSelRow();
								var account = record.get("account");
								if(account){
									txt_bankAccount.setValue(account);
								}
							}
							var cbog_accountId = ComboxControl.getAccountCboGrid({
								fieldLabel:'银行',name:'bankName',
								allowBlank : false,readOnly:true,
								isAll:true,width:180,callback:callback,
								params : {isIncome:1,sysId : this.sysId}
							});
							var txt_bankAccount = FormUtil.getReadTxtField({
							    fieldLabel: '银行帐号',
							    width:180,
							    name: 'bankAccount'
							});
							var txt_raParams = FormUtil.getDateField({
										fieldLabel : '收款日期',
										sigins : null,
										width:180,
										name : 'opdate'
									});
							var txa_remark = FormUtil.getTAreaField({
								fieldLabel : '备注',
								name : 'remark',
								width:180,
								"maxLength" : 180
							});
							var layout_fields = [txt_raDispEdit,Sys_id,txss_id,txt_id,cbo_inType, txt_raDispFormula,
								cbog_accountId,txt_bankAccount, txt_raParams, txa_remark];
							var frm_cfg = {
								title : '日常收支',
								labelWidth : 100,
								url : './fcFundsWater_chargefree.action'
							};
					var appform_1 = FormUtil.createLayoutFrm(frm_cfg,layout_fields);

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
				 * 重置数据
				 */
				resetData : function() {
				}
			};
		});
