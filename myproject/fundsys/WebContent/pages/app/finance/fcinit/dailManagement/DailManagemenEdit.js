/**
 * 收入支出管理(支出)
 * 
 * @author smartplatform_auto
 * @date 2013-01-23 07:16:22
 */
define(function(require, exports) {
			exports.WinEdit = {
				appGvlistWin : null,
				parentCfg : null,
				parent : null,
				optionType : OPTION_TYPE.CUT,/* 默认为新增 */
				appFrm : null,
				appWin : null,
				rname : null,
				restypeId : null,
				otherSortId : Ext.id(null,'otherSort'),		
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
						cfg = {//waterType资金类型1.放款2.收款
							params : {waterType:'1',recode : '200006'},
							defaultVal : {sysId:_this.sysId,waterType:'1'},
							sfn :function(json_Data){
								if(json_Data){
									var rname = json_Data.rname;
									var restypeId = json_Data.restypeId;
									_this.rname = rname;
									_this.restypeId = restypeId;
								}
								
							}
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
							}
						};
						urls[URLCFG_KEYS.GETURLCFG] = {
							url : './fcFundsWater_get.action',
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
						url : './fcPayType_prev.action',
						cfg : cfg
					};
					/*--- 下一条 URL --*/
					urls[URLCFG_KEYS.NEXTURLCFG] = {
						url : './fcPayType_next.action',
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
					var _this = this;
					var wd = 180;
					
					var txt_id = FormUtil.getHidField({
						    fieldLabel: 'ID',
						    name: 'id',
						    width:wd,
						    hidden:true
						});
						var txss_id = FormUtil.getHidField({
						    fieldLabel: 'waterType',
						    name: 'waterType',
						    width:wd,
						    hidden:true
						});
						var Sys_id = FormUtil.getTxtField({
					    fieldLabel: 'sysId',
					    name: 'sysId',
					    hidden:true,
					    width:wd
					});
						var cbo_inType = FormUtil.getRCboField({
							fieldLabel : '费用类型',
							name : 'otherSort',
							id : _this.otherSortId,
							width:118,	allowBlank : false,
							restypeId : '200006',
							url :"./sysGvlist_cbodatas.action?restypeId=200006"
							});
							/*业务引用键*/
							var Butn = new Ext.Button({text: '添加类型',name:'btn'});
							var rec_btn = FormUtil.getMyCompositeField({fieldLabel: '费用类型',allowBlank:true,itemsOne: true ,sigins:null,itemNames : 'otherSort,btn',
							    name: 'recodeCmp',width:220,items:[cbo_inType,{xtype:'displayfield',width:1},Butn]});
							Butn.on("click",function(Button,e){
								_this.btnClick(Button,e);
							});
							var txt_raDispFormula = FormUtil.getMoneyField({
								fieldLabel : '收款金额',
								width:wd,	allowBlank : false,
								name : 'amounts'
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
								isAll:true,width:wd,callback:callback,
								params : {isIncome:1,sysId : this.sysId}
							});
							var txt_bankAccount = FormUtil.getReadTxtField({
							    fieldLabel: '银行帐号',
							    width:wd,
							    name: 'bankAccount'
							});
							var txt_raParams = FormUtil.getDateField({
										fieldLabel : '收款日期',
										sigins : null,
										width:wd,allowBlank : false,
										name : 'opdate'
									});
							var txa_remark = FormUtil.getTAreaField({
								fieldLabel : '备注',
								name : 'remark',
								width:wd,
								"maxLength" : 200
							});
							var layout_fields = [Sys_id,txss_id,txt_id,rec_btn, txt_raDispFormula,
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
				 * btn的鼠标单击事件
				 */
				btnClick : function(Button,e){
					var self = this;
					var restypeId = this.restypeId;
					var rname = this.rname;
					var parentCfg = {parent :Button,optionType : OPTION_TYPE.ADD, restypeId : restypeId,rname : rname,self: self}
						Cmw.importPackage('pages/app/finance/fcinit/dailManagement/DailAddEdit',function(module) {
						 	self.appGvlistWin = module.WinEdit;
						 	self.appGvlistWin.show(parentCfg);
			  			});
				},
				
				/**
				 * 回调函数处理数据的加载(费用类型)
				 */
				
				callBack : function(callBackData){
					var otherSortCombox = Ext.getCmp(this.otherSortId);
					if(callBackData){
						var params = {rname : callBackData.rname,restypeId : callBackData.restypeId};
						otherSortCombox.reload(params);	
					}
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
