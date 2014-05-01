/**
 * 委托客户详情
 * 
 * @author liting
 */
define(function(require, exports) {
	exports.moduleUI = {
		appPanel : null,
		params : null,
		applyId : null,
		custType : null,
		customerId : null,
		LookDateilPnl : null,
		oneCustDetailPnlId : Ext.id(null, "oneCustDetailApply"),
//		entCustDetailPnlId : Ext.id(null, "entCustDetailApply"),
		activePnlId : null,/* 当前激活的面板ID */
		callback : null,/* 回调函数 */
		prefix : Ext.id(),
		falg : false,
		json_data : null,
		/**
		 * 获取业务模块
		 * 
		 * @param params
		 *            由菜单点击所传过来的参数 {tabId : tab 对象ID,apptabtreewinId :
		 *            系统程序窗口ID,sysid : 系统ID}
		 */
		getModule : function(params) {
			if (!this.appPanel) {
				this.appPanel = new Ext.Panel({
							border : false
						});
			}
			this.show(params);
			return this.appPanel;
		},
		/**
		 * 设置参数
		 */
		setParams : function(params) {
			this.params = params;
			this.applyId = this.params.applyId;
			this.custType = this.params.custType;
			this.customerId = this.params.customerId;
		},
		/**
		 * 显示面板并加载数据
		 */
		show : function(params) {
			this.setParams(params);
			this.loadData();
		},
		/**
		 * 加载客户信息数据
		 */
		loadData : function() {
			var _this = this;
			EventManager.get('./fcApply_detail.action', {
						params : {
							applyId : _this.applyId,
							formdiy_id : FORMDIY_IND.FROMDIY_APPLY
						},
						sfn : function(json_data) {
							_this.json_data = json_data;
							var custType = json_data.custType;
							_this.custType = custType;
							_this.customerId = json_data.customerId;
							var activePanel = _this.showCustPanle(custType,
									json_data);
							activePanel.reload({
										json_data : json_data
									}, true);
							if (_this.callback)
								_this.callback(json_data);
						}
					});
		},
		showCustPanle : function(custType) {
			var activePnl = null;
			var activePnlId = null;
			var isAdd = true;
			if (!Ext.isNumber(custType)) {
				custType = parseInt(custType);
			}
					activePnlId = this.oneCustDetailPnlId;
					var activePnl = Ext.getCmp(activePnlId);
					if (!activePnl) {
						activePnl = this.getOneCustDetailPanel();
						isAdd = true;
					} else {
						isAdd = false;
					}
			if (isAdd) {
				this.appPanel.add(activePnl);
				this.appPanel.doLayout();
			}
			activePnl.show();
			var hidPnlId = null;
			this.activePnlId = activePnlId;
			
				hidPnlId = this.oneCustDetailPnlId;
//			var hidPnl = Ext.getCmp(hidPnlId);
//			if (hidPnl)
//				hidPnl.hide();
			return activePnl;
		},
		/**
		 * 获取个人项目基本信息
		 */
		getOneCustDetailPanel : function() {
			var _this = this;
			var htmlArrs_1 = ['<tr><th col="code">合同编号</th> <td col="code" >&nbsp;</td><th col="payBank">收款银行</th> <td col="payBank" >&nbsp;</td><th col="payAccount">收款账号</th> <td col="payAccount" >&nbsp;</td></tr>', '<tr><th col="accName">账户名</th> <td col="accName" >&nbsp;</td><th col="appAmount">委托金额</th> <td col="appAmount" >&nbsp;</td><th col="productsId">委托产品</th> <td col="productsId" >&nbsp;</td></tr>', '<tr><th col="doDate">合同签订日期</th> <td col="doDate" >&nbsp;</td><th col="payDate">委托生效日期</th> <td col="payDate" >&nbsp;</td><th col="endDate">委托失效日期</th> <td col="endDate" >&nbsp;</td></tr></tr>'];
			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 90,
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './fuEntrustContract_list.action',
				params : {
					applyId : -1
				},
				callback : {
					sfn : function(jsonData) {
						var guaId = jsonData["GuaId"];
			        	if(guaId){
			        		jsonData["GuaId"] =guaId.split("##")[1];
			        		guaId = _this.addAHtml(jsonData, "GuaId","单击查看第三方担保人详情");
			        	}
			        	var mrate = jsonData["mrate"];
			        	if(mrate){
			        		jsonData["mrate"] =mrate+"%";
			        	}
						var applyId = jsonData["id"];
						var applyAid = _this.addAHtml(jsonData, "code",
								"单击查看贷款申请详情");
						var customerAid = _this.addAHtml(jsonData, "name",
								"单击查看客户信息详情");
						var custTabId = CUSTTAB_ID.customerInfoDetailTab.id;
						var url = CUSTTAB_ID.customerInfoDetailTab.url;
						var custTitle = '客户详情';
						var CustomerId = jsonData["customerId"];
						var baseId = jsonData["baseId"];

						var customerParams = {
							customerId : CustomerId,
							applyId : _this.applyId,
							customerInfoId : baseId,
							parent : _this.appPanel
						}
						_this.viewDetailInfo(customerAid, custTabId,
								customerParams, url, custTitle);
								
						_this.guaDetailInfo(guaId, custTabId,
								customerParams, url, custTitle);
						// 查看申请单详情
						var applyTabId = CUSTTAB_ID.detailApplyFormTabId.id;
						var applyurl = CUSTTAB_ID.detailApplyFormTabId.url;
						var title = '个人客户申请单详情';
						var custType = jsonData["custType"];
						var applyParams = {
							id : applyId,
							applyId : applyId,
							customerId : CustomerId,
							custType : custType
						};
						_this.viewDetailInfo(applyAid, applyTabId, applyParams,
								applyurl, title);
						// jsonData["custType"] = "个人客户";
//						jsonData["maristal"] = Render_dataSource.gvlistRender(
//								'100003', jsonData["maristal"]);
//						jsonData["sex"] = Render_dataSource
//								.sexDetailRender(jsonData["sex"]);
//						jsonData["name"] = jsonData["name"] + ","
//								+ jsonData["sex"] + "," + jsonData["maristal"]/* +","+jsonData["custType"] */;
//						jsonData["cardType"] = Render_dataSource.gvlistRender(
//								'100002', jsonData["cardType"])
//								+ ":" + jsonData["cardNum"];
//						var appAmount = jsonData["appAmount"];
//						jsonData["appAmount"] = Cmw.getThousandths(appAmount)
//								+ '元&nbsp;&nbsp;<span style="color:red;"></br>(大写：'
//								+ Cmw.cmycurd(appAmount) + ')</span>';
//						jsonData["loanLimit"] = _this.getLoanLimit(jsonData)
//								+ ","
//								+ Render_dataSource.rateTypeRender(jsonData["rateType"])
//								+ "," + jsonData["rate"] + '%';

//						 _this.renderDispData(jsonData);
					}
				}
			}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
				id : this.oneCustDetailPnlId,
				detailCfgs : detailCfgs_1,
				isLoad : false,
				border : false
			});
			return detailPanel;
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
		/**
		 * 查看担保人详情
		 */
		guaDetailInfo : function(eleId, tabId, params, url, title,activeTab){
			var _this = this;
			if (!eleId)
				return;
			apptabtreewinId = _this.params["apptabtreewinId"];
			params.apptabtreewinId = apptabtreewinId;
			params.dispaly = true;
			params["isnewInstance"] = true;
			params.activeTab = 2;
			var delay = new Ext.util.DelayedTask(function() {
						var ele = Ext.get(eleId);
						if (!ele)
							return;
						ele.on('click', function(e) {
							if(!params.activeTab) params.activeTab = 2;
									Cmw.activeTab(apptabtreewinId, tabId,
											params, url, title);
								});
					});

			delay.delay(150);
		},
		/**
		 * 查看详情
		 * 
		 * @param {}
		 *            _this
		 */
		viewDetailInfo : function(eleId, tabId, params, url, title) {
			var _this = this;
			if (!eleId)
				return;
			apptabtreewinId = _this.params["apptabtreewinId"];
			params.apptabtreewinId = apptabtreewinId;
			params.dispaly = true;
			params["isnewInstance"] = true;
			var delay = new Ext.util.DelayedTask(function() {
						var ele = Ext.get(eleId);
						if (!ele)
							return;
						ele.on('click', function(e) {
								if(params.activeTab) delete params.activeTab;
									Cmw.activeTab(apptabtreewinId, tabId,
											params, url, title);
								});
					});

			delay.delay(150);
		},
//		/**
//		 * 获取贷款期限
//		 */
//		getLoanLimit : function(jsonData) {
//			var yearLoan = jsonData["yearLoan"];
//			var monthLoan = jsonData["monthLoan"];
//			var dayLoan = jsonData["dayLoan"];
//			var arr = [];
//			if (yearLoan && yearLoan > 0) {
//				arr[arr.length] = yearLoan + '年';
//			}
//			if (monthLoan && monthLoan > 0) {
//				arr[arr.length] = monthLoan + '个月';
//			}
//			if (dayLoan && dayLoan > 0) {
//				arr[arr.length] = dayLoan + '天';
//			}
//			return (arr.length > 0) ? arr.join("") : "";
//		},
//		resize : function(adjWidth, adjHeight) {
//			this.appPanel.setWidth(adjWidth);
//			this.appPanel.setHeight(adjHeight);
//		},
		/**
		 * 获取详情面板的高度
		 */
		getHeight : function() {
			var height = 145;
			if (this.activePnlId) {
				var activePnl = Ext.getCmp(this.activePnlId);
				if (!activePnl || !activePnl.rendered) {
					return height;
				} else {
					var appEl = activePnl.el;
					height = appEl.getComputedHeight() + 4;// 要把边框的4像素算进来
				}
			} else {
				if (!this.appPanel || !this.appPanel.rendered) {
					return height;
				}
				var appEl = this.appPanel.el;
				if (!appEl)
					return height;
				height = appEl.getComputedHeight() + 4;
			}
			return height;
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (null != this.appPanel) {
				this.appPanel.destroy();
				this.appPanel = null;
			}
		}
	};
});