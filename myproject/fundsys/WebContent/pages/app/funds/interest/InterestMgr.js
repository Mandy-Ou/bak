/* 同心日科技公司Erp 系统 命名空间 */
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 利息支付
 */
skythink.cmw.workflow.bussforms.InterestMgr = function() {
	this.init(arguments[0]);
}
/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.InterestMgr, Ext.util.MyObservable,{
			initModule : function(tab, params) {
				/*
				 * 由必做或选做业务菜单传入的回調函数，主要功能： finishBussCallback :
				 * 当业务表单保存后，更新必做或选做事项为已做, unFinishBussCallback :
				 * 当删除业务表单后，取消已做标识·
				 */
				var finishBussCallback = tab.finishBussCallback;
				var unFinishBussCallback = tab.unFinishBussCallback;
				this.module = new Cmw.app.widget.AbsPanelView({
							tab : tab,
							params : params,/* apptabtreewinId,tabId,sysid */
							getAppCmpt : this.getAppCmpt,
							getToolBar : this.getToolBar,
							createDetailPnl : this.createDetailPnl,
							createAppFrm : this.createAppFrm,
							createButton : this.createButton,
							getAppGrid : this.getAppGrid,
							changeSize : this.changeSize,
							destroyCmpts : this.destroyCmpts,
							globalMgr : this.globalMgr,
							refresh : this.refresh,
							prefix : Ext.id(),
							finishBussCallback : finishBussCallback,
							unFinishBussCallback : unFinishBussCallback
						});
			},
			/**
			 * 获取组件方法
			 */
			getAppCmpt : function() {
				var _this = this;
				var appPanel = new Ext.Panel({border : false,autoScroll : true});
				EventManager.get('./fuInterest_getTest.action', {params : {applyId : this.params.applyId},
					sfn : function(json_data) {
						if (!json_data.id) {
							appPanel.update("<H4 style='color:red;font-size:33px'><center>先添加委托合同之后再进行付息</center></H4>");
							appPanel.doLayout();
						} else {
							_this.globalMgr.id=json_data["id"];
							_this.globalMgr.createInterest(json_data,appPanel);
							_this.createDetailPnl(_this);	
							appPanel.add({items : [_this.toolBar,_this.globalMgr.detailPanel_1]});
//							appPanel.doLayout();
						}
					}
				});
				return appPanel;
			},
			/**
			 * 刷新方法
			 * 
			 * @param {}
			 *            optionType
			 * @param {}
			 *            data
			 */
			refresh : function(optionType, data) {
			},
			/**
			 * 组件大小改变方法
			 * 
			 * @param {}
			 *            whArr
			 */
			changeSize : function(whArr) {
				var width = CLIENTWIDTH - Ext.getCmp(ACCORDPNLID);
				var height = CLIENTHEIGHT - 180;
				this.appPanel.setWidth(width);
				this.appPanel.setHeight(height);
			},
			/**
			 * 查询工具栏
			 */
			getToolBar : function() {
				var self = this;
				var barItems = [];
				toolBar = new Ext.ux.toolbar.MyCmwToolbar({
							aligin : 'left',
							controls : barItems,
							rightData : {
								saveRights : true,
								currNode : this.params[CURR_NODE_KEY]
							}
						});
				return toolBar;
			},
			/**
			 * 创建收款表单
			 */
			createAppFrm : function() {
				var _this = this;
				var txt_id = FormUtil.getHidField({
							fieldLabel : 'ID',
							name : 'id'
						});
				var txt_nextDate = FormUtil.getHidField({
							fieldLabel : 'nextDate',
							name : 'nextDate'
						});
				var txt_zmgrAmount = FormUtil.getHidField({
							fieldLabel : 'interestId',
							name : 'interestId'
						});
				var txt_iamount = FormUtil.getHidField({
							fieldLabel : 'iamount',
							name : 'iamount'
						});
				 var callback = function(cboGrid, selVals) {
					var grid = cboGrid.grid;
					var record = grid.getSelRow();
					var account = record.get("account");
					var uamount = record.get("uamount");
					var amt = num_cat.getValue();
					if (parseFloat(amt) > parseFloat(amt)) {
						ExtUtil.warn({msg : "选的放款账户余额已不足，请选择其他账号进行放款！"});
						return;
					}
					txt_bankAccount.setValue(account);
				}
				var cbog_accountId = ComboxControl.getAccountCboGrid({
							fieldLabel : '付款银行',
							name : 'accountId',
							allowBlank : false,
							editable : false,
							isAll : false,
							"width" : 160,
							callback : callback,
							params : {
								isIncome : 1,
								sysId : _this.globalMgr.sysId
							}
						});
				var txt_bankAccount = FormUtil.getTxtField({
							fieldLabel : '银行帐号',
							name : 'bankAccount',
							"width" : 160
						});
				var dat_rectDate = FormUtil.getDateField({
							fieldLabel : '付款日期',
							name : 'rectDate',
							"width" : 125,
							"allowBlank" : false,
							editable : false,
							"maxLength" : 50
						});
				var rad_order = FormUtil.getRadioGroup({
							fieldLabel : '付息方式',
							"width" : 200,
							allowBlank : false,
							id : 'rad_orderid',
							name : 'settleType',
							items : [{
										boxLabel : '银行转帐',
										name : 'settleType',
										inputValue : 1,
										checked : true
									}, {
										boxLabel : '现金',
										name : 'settleType',
										inputValue : 2
									}]
						});
				rad_order.addListener('change', function(radgroup, checked) {
							var PraVal = Ext.getCmp('rad_orderid');// txt_productsid为选中的下拉框的id是唯一的
							var ids = PraVal.getValue();// 获取下拉框的值
							if (ids == 2) {
								cbog_accountId.setDisabled(true);
								txt_bankAccount.setDisabled(true);
								cbog_accountId.setRawValue("");// 设置选中的下拉框的值
							} else {
								cbog_accountId.setDisabled(false);
								txt_bankAccount.setDisabled(false);
							}
						});
				var num_cat = FormUtil.getMoneyField({
							fieldLabel : '付息金额',
							name : 'amt',
							"width" : 160,
							allowBlank : false,
							unitText : '元'
						});
				var num_rat = FormUtil.getMoneyField({
							fieldLabel : '利息',
							name : 'rat',
							allowBlank : false,
							unitText : '元'
						});
				var layout_fields = [txt_id, txt_zmgrAmount, {
					cmns : FormUtil.CMN_THREE,
					fields : [rad_order, cbog_accountId, txt_bankAccount,
							dat_rectDate, num_cat/* ,num_rat */, txt_iamount,/* txt_rectDate, */
							txt_nextDate]
				}];
				var frm_cfg = {
					title : '本次付息信息',
					labelWidth : 95,
					hidden : true,
					url : './fuInterest_save.action'
				};
				var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
				return appform;
			},
			/**
			 * 获取Grid 对象
			 */

			getAppGrid : function() {
				var _this = this;
				var structure_1 = [{
							header : '付息方式',
							name : 'settleType',
							width : 115,
							renderer : function(val) {
								return Render_dataSource
										.InterestSetTypperRender(val);
							}
						}, {
							header : '付息金额',
							name : 'amt',
							width : 115,
							renderer : function(val) {
								return (val && val > 0) ? Cmw
										.getThousandths(val)
										+ '元' : '';
							}
						}, {
							header : '付息日期',
							width : 115,
							name : 'rectDate'
						}];
				var appgrid = new Ext.ux.grid.AppGrid({// 创建表格
					title : '付息信息',
					tbar : this.toolBar,
					structure : structure_1,
					url : './fuInterest_list.action',
					needPage : false,
					keyField : 'id',
					height : 300
						// ,
						// isLoad : false
				});
				return appgrid;
			},
			/**
			 * 创建详情面板
			 */
			createDetailPnl : function(_this) {
				var sysId = _this.globalMgr.sysId;
				var htmlArrs_1 = [
						'<tr><th colspan="7" style="font-weight:bold;text-align:left;font-size:33px" ><center>委托客户付息单</center></th><tr>',
						'<tr><th col="code">委托合同编号</th> <td col="code" >&nbsp;</td><th col="name">委托人姓名</th> <td col="name" >&nbsp;</td><th col="cardNum">身份证</th> <td col="cardNum" >&nbsp;</td></tr>',
						'<tr><th col="phone">手机号</th> <td col="phone" >&nbsp;</td><th col="inAddress">现居住地值</th> <td col="inAddress" >&nbsp;</td><th col="homeNo">住宅号</th> <td col="homeNo" >&nbsp;</td></tr>',
						'<tr><th col="appAmount">委托金额</th> <td col="appAmount" >&nbsp;</td><th col="payDate">委托生效日期</th> <td col="payDate" >&nbsp;</td><th col="endDate">委托失效日期</th> <td col="endDate" >&nbsp;</td></tr></tr>',
						'<tr><th col="rate">利率</th> <td col="rate" >&nbsp;</td><th col="payDay">每月结息日</th> <td col="payDay" >&nbsp;</td><th col="iamount">每月收益金额</th> <td col="iamount" >&nbsp;</td></tr></tr>',
						'<tr><th col="xpayDate">付息日期</th> <td col="xpayDate" >&nbsp;</td><th col="iamount">应付利息</th> <td col="iamount" >&nbsp;</td>'
								+ '<th col="nextDate">下一付息日期</th> <td col="nextDate" >&nbsp;</td></tr>',
						'<tr><th col="yamount">已付息金额</th> <td col="yamount" >&nbsp;</td><th col="status">状态</th> <td col="status" >&nbsp;</td>'
								+ '<th col="lastDate">最后付息日期</th> <td col="lastDate" >&nbsp;</td></tr>'];
				var detailCfgs_1 = [{
					cmns : 'THREE',
					/* ONE , TWO , THREE */
					model : 'single',
					id : 'hideId',
					labelWidth : 110,
					title : '委托合同信息',
					// 详情面板标题
					/* i18n : UI_Menu.appDefault, */
					// 国际化资源对象
					htmls : htmlArrs_1,
					url : './fuInterest_getCusName.action',
					// prevUrl: '#PREURLCFG#',
					// nextUrl: '#NEXTURLCFG#',
					params : {
						id : _this.globalMgr.id,
						applyId : _this.params.applyId
					},
					callback : {
						sfn : function(jsonData) {
							var status = jsonData["status"];
							if (status == 2) {
								_this.globalMgr.appForm.hide();// 所有的付息已经完成
							} else {
								_this.globalMgr.appForm.show();// 所有的付息已经完成
							}
							switch (jsonData["status"]) {
								case '0' :
									jsonData["status"] = '未付息';
									break;
								case '1' :
									jsonData["status"] = '部分付息';
									break;
								case '2' :
									jsonData["status"] = '已付息';
									break;
							}
							var xpayDate = jsonData["xpayDate"];
							jsonData["nextDate"] = new Date(xpayDate).add(Date.MONTH, 1).dateFormat('Y-m-d');
							var interestId = jsonData["id"];
							var nextDate = jsonData["nextDate"];
							var iamount = jsonData["iamount"];
							var yamount = jsonData["yamount"];
							var lastDate = jsonData["lastDate"];
							// if(status==0){
							// _this.globalMgr.AppGrid.hide();
							// }
							jsonData["iamount"] = Cmw.getThousandths(jsonData["iamount"]);
							jsonData["yamount"] = Cmw.getThousandths(jsonData["yamount"]);
							var amt = iamount - yamount;
							if (!lastDate && lastDate != "") {
								_this.globalMgr.appForm.findFieldByName("lastDate").setValue(lastDate);// 最后付息日期
							}
							_this.globalMgr.appForm.findFieldByName("amt").setValue(amt);
							_this.globalMgr.appForm.findFieldByName("interestId").setValue(interestId);
							_this.globalMgr.appForm.findFieldByName("iamount").setValue(iamount);
							_this.globalMgr.appForm.findFieldByName("nextDate").setValue(jsonData["nextDate"]);
							jsonData["xpayDate"] = jsonData["xpayDate"] + ' 号';
							jsonData["nextDate"] = jsonData["nextDate"] + ' 号';
							jsonData["endDate"] = jsonData["endDate"] + ' 号';
							jsonData["payDate"] = jsonData["payDate"] + ' 号';
						}
					}
				}];
				var detailPanel_1 = new Ext.ux.panel.DetailPanel({
							autoWidth : true,
							detailCfgs : detailCfgs_1,
							border : false,
							attachLoad : function(params, cmd) {
								/* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
								// Cmw.print(params);
								// Cmw.print(cmd);
							}
						});
				_this.globalMgr.detailPanel_1 = detailPanel_1;
				var appFrm = this.createAppFrm();
				var AppGridA = this.getAppGrid();
				var AddButton = this.createButton();
				_this.globalMgr.appForm = appFrm;
				_this.globalMgr.AppGrid = AppGridA;
				_this.globalMgr.AddButton = AddButton;
				appFrm.add(AddButton);
				detailPanel_1.add(appFrm);
				detailPanel_1.add(AppGridA);
				return detailPanel_1;
			},
			createButton : function() {
				var self = this;
				var button_save = new Ext.Button({
							text : '付息',
							width : 100,
							listeners : {
								"click" : function() {
									cfg = {
										sfn : function() {
											self.globalMgr.AppGrid.reload();
										}
									}
									EventManager.frm_save(
											self.globalMgr.appForm, cfg);
								}
							}
						})
				var buttons = [button_save];
				var btnPanel = new Ext.Panel({
							buttonAlign : 'center',
							buttons : buttons
						});
				return btnPanel;
			},
			/**
			 * 组件销毁方法
			 */
			destroyCmpts : function() {
			},
			globalMgr : {
				id : null,
				activeKey : null,
				formId : this.params.applyId,
				sysId : this.params.sysid,
				detailPanel_1 : null,
				appForm : null,
				AppGrid : null,
				AddButton : null,
				winEdit : {
					show : function(parentCfg) {
						var _this = parentCfg.self;
						// _this.globalMgr.activeKey = winkey;
						var parent = null;
						parent = {
							sysid : _this.globalMgr.sysId,
							applyid : _this.globalMgr.formId,
							entrustCustId : _this.globalMgr.entrustCustId
						};
						parentCfg.parent = parent;
					}
				},
				/**
				 * 生成还息计划
				 * @param {} json_data
				 */
				createInterest:function(json_data,_this){
					//获取还息计划
					var datas=[];
					EventManager.get("./fuEntrustCust_get.action",{params:{id:json_data.entrustCustId},sfn : function(jsonData){
						if(jsonData.ctype=='2'){
							EventManager.get("./fuInterest_getByParams.action",{params:{entrustContractId:json_data.id},sfn : function(jsonDt){
								if(!jsonDt.totalSize){
									//保存还息计划
									EventManager.get("./fuInterest_saveInterests.action",{params:{entrustContract:Ext.encode(json_data)},sfn : function(jsonData){
										_this.doLayout();
									}});	
								
								}else{
									_this.doLayout();
								}
							}});	
						}
						}
					});	
			}
			}
		});
