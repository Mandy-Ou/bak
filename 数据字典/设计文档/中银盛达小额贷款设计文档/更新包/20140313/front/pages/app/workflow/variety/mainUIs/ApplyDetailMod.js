/**
 * 客户贷款详情信息
 * 
 * @author 程明卫
 * @date 2012-12-26
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
		entCustDetailPnlId : Ext.id(null, "entCustDetailApply"),
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
			switch (custType) {
				case 0 : { /* 个人客户 */
					activePnlId = this.oneCustDetailPnlId;
					var activePnl = Ext.getCmp(activePnlId);
					if (!activePnl) {
						activePnl = this.getOneCustDetailPanel();
						isAdd = true;
					} else {
						isAdd = false;
					}
					break;
				}
				case 1 : { /* 企业客户 */
					activePnlId = this.entCustDetailPnlId;
					var activePnl = Ext.getCmp(activePnlId);
					if (!activePnl) {
						activePnl = this.getEntCustDetailPanel();
						isAdd = true;
					} else {
						isAdd = false;
					}
					break;
				}
			}
			if (isAdd) {
				this.appPanel.add(activePnl);
				this.appPanel.doLayout();
			}
			activePnl.show();
			var hidPnlId = null;
			this.activePnlId = activePnlId;
			if (activePnlId == this.oneCustDetailPnlId) {
				hidPnlId = this.entCustDetailPnlId;
			} else {
				hidPnlId = this.oneCustDetailPnlId;
			}
			var hidPnl = Ext.getCmp(hidPnlId);
			if (hidPnl)
				hidPnl.hide();
			return activePnl;
		},
		/**
		 * 获取个人项目基本信息
		 */
		getOneCustDetailPanel : function() {
			var _this = this;
			var htmlArrs_1 = [
					'<tr><th col="code">申请单编号</th> <td col="code" >&nbsp;</td><th col="name">客户简要资料</th> <td col="name" >&nbsp;</td><th col="GuaId">担保人</th> <td col="GuaId" >&nbsp;</td></tr>',
					'<tr><th col="cardType">证件类型</th> <td col="cardType" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th col="loanLimit">贷款信息</th> <td col="loanLimit" >&nbsp;</td></tr>',
					'<tr><th col="payType">还款方式</th> <td col="payType" >&nbsp;</td><th col="managerName">业务经理</th> <td col="managerName" >&nbsp;</td><th col="mrate">管理费利率</th> <td col="mrate" >&nbsp;</td></tr>',
					'<tr><th col="breedName">业务品种</th> <td col="breedName" >&nbsp;</td><th col="comanager">协办经理</th> <td col="comanager" >&nbsp;</td><th col="appdate">申请日期</th> <td col="appdate" >&nbsp;</td></tr>'
					];
			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 90,
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './fcApply_detail.action',
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
			        	}else {
			        		jsonData["mrate"] = "0.00%";
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
						jsonData["maristal"] = Render_dataSource.gvlistRender(
								'100003', jsonData["maristal"]);
						jsonData["sex"] = Render_dataSource
								.sexDetailRender(jsonData["sex"]);
						jsonData["name"] = jsonData["name"] + ","
								+ jsonData["sex"] + "," + jsonData["maristal"]/* +","+jsonData["custType"] */;
						jsonData["cardType"] = Render_dataSource.gvlistRender(
								'100002', jsonData["cardType"])
								+ ":" + jsonData["cardNum"];
						var appAmount = jsonData["appAmount"];
						jsonData["appAmount"] = Cmw.getThousandths(appAmount)
								+ '元&nbsp;&nbsp;<span style="color:red;"></br>(大写：'
								+ Cmw.cmycurd(appAmount) + ')</span>';
						var inRate = jsonData["inRate"];
						var inRateType = jsonData["inRateType"];
						if(inRateType){
							inRateType = "内部利率类型:"+Render_dataSource.rateTypeRender(jsonData["inRateType"]);
						}else{
							inRateType = "";
						}
						if(inRate || inRate ==0){
							inRate = ",<span style='color:red'>(内部利率:"+inRateType+"&nbsp;"+inRate+'%)</span>';
						}else {
							inRate = "";
						}
						
						jsonData["loanLimit"] = _this.getLoanLimit(jsonData)+ ","
								+ Render_dataSource.rateTypeRender(jsonData["rateType"])
								+ "," + jsonData["rate"] + '%' + inRate ;

						// _this.renderDispData(jsonData);
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
		/**
		 * 获取企业客户项目基本信息
		 */
		getEntCustDetailPanel : function() {
			var _this = this;
			var htmlArrs_1 = [
					'<tr><th col="code">申请单编号</th> <td col="code" >&nbsp;</td><th col="name">企业名称</th> <td col="name" >&nbsp;</td><th col="GuaId">担保人</th> <td col="GuaId" >&nbsp;</td></tr>',
					'<tr><th col="kind">企业性质</th> <td col="kind" >&nbsp;</td><th col="regaddress">注册地址</th> <td col="regaddress">&nbsp;</td><th col="loanLimit">贷款信息</th> <td col="loanLimit" >&nbsp;</td></tr>',
					'<tr><th col="breedName">业务品种</th> <td col="breedName" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th col="mrate">管理费利率</th> <td col="mrate" >&nbsp;</td></tr>',
					'<tr><th col="managerName">业务经理</th> <td col="managerName" >&nbsp;</td><th col="comanager">协办经理</th> <td col="comanager" >&nbsp;</td><th col="appdate">申请日期</th> <td col="appdate" >&nbsp;</td></tr>'
					];
			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 90,
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './fcApply_detail.action',
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
			        	}else {
			        		jsonData["mrate"] = "0.00%";
			        	}
			        	
						// regcapital,currency,regaddress,kind
						var applyId = jsonData["id"];
						var applyAid = _this.addAHtml(jsonData, "code",
								"单击查看贷款申请详情");
						var customerAid = _this.addAHtml(jsonData, "name",
								"单击查看企业客户信息详情");
						var custTabId = CUSTTAB_ID.ecustomerInfoDetailTab.id;
						var url = CUSTTAB_ID.ecustomerInfoDetailTab.url;
						var custTitle = '企业客户详情';
						var CustomerId = jsonData["customerId"];
						var baseId = jsonData["baseId"];
						var customerParams = {
							ecustomerId : CustomerId,
							applyId : _this.applyId,
							ecustomerInfoId : baseId,
							parent : _this.appPanel
						}
						_this.viewDetailInfo(customerAid, custTabId,
								customerParams, url, custTitle);
						_this.guaDetailInfo(guaId, custTabId,
								customerParams, url, custTitle);
						// 查看申请单详情
						var applyTabId = CUSTTAB_ID.detailApplyFormTabId.id;
						var applyurl = CUSTTAB_ID.detailApplyFormTabId.url;
						var title = '企业客户申请单详情';
						var custType = jsonData["custType"];
						var applyParams = {
							id : applyId,
							applyId : applyId,
							customerId : CustomerId,
							custType : custType
						};
						_this.viewDetailInfo(applyAid, applyTabId, applyParams,
								applyurl, title);

						var regcapital = jsonData["regcapital"];
						if (regcapital) {
							var currency = jsonData["currency"];
							currency = !currency ? "人民币" : Render_dataSource
									.gvlistRender('100014', currency);
							jsonData["regcapital"] = Cmw
									.getThousandths(regcapital)
									+ '元&nbsp;&nbsp;<span style="color:red;"></br>(大写：'
									+ Cmw.cmycurd(regcapital) + ')</span>';
						} else {
							jsonData["regcapital"] = regcapital;
						}
						var kind = jsonData["kind"];
						jsonData["kind"] = Render_dataSource.gvlistRender(
								'100009', kind)
								+ ",注册资金：" + jsonData["regcapital"];
						var appAmount = jsonData["appAmount"];
						jsonData["appAmount"] = Cmw.getThousandths(appAmount)
								+ '元&nbsp;&nbsp;<span style="color:red;"></br>(大写：'
								+ Cmw.cmycurd(appAmount) + ')</span>';
						
						var inRate = jsonData["inRate"];
						var inRateType = jsonData["inRateType"];
						if(inRateType){
							inRateType = "内部利率类型:"+Render_dataSource.rateTypeRender(jsonData["inRateType"]);
						}else{
							inRateType = "";
						}
						if(inRate || inRate ==0){
							inRate = ",<span style='color:red'>(内部利率:"+inRateType+"&nbsp;"+inRate+'%)</span>';
						}else {
							inRate = "";
						}
						
						jsonData["loanLimit"] = _this.getLoanLimit(jsonData)+ ","
								+ Render_dataSource.rateTypeRender(jsonData["rateType"])
								+ "," + jsonData["rate"] + '%'+inRate;
						// _this.renderDispData(jsonData);
					}
				}
			}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
						id : this.entCustDetailPnlId,
						detailCfgs : detailCfgs_1
					});
			return detailPanel;
		},
		renderDispData : function(jsonData) {
			jsonData["custType"] = (jsonData["custType"] == "0")
					? "个人客户"
					: "企业客户";
			// jsonData["breed"] = jsonData["breedName"] ? jsonData["breedName"]
			// : '&nbsp;';
			jsonData["loanLimit"] = this.getLoanLimit(jsonData);
			// jsonData["payType"] =
			// Render_dataSource.payTypeRender(jsonData["payType"]);
			jsonData["rate"] = jsonData["rate"] + '%';
			var appAmount = jsonData["appAmount"];
			jsonData["appAmount"] = Cmw.getThousandths(appAmount)
					+ '元&nbsp;&nbsp;<span style="color:red;"></br>(大写：'
					+ Cmw.cmycurd(appAmount) + ')</span>';
			jsonData["rateType"] = Render_dataSource
					.rateTypeRender(jsonData["rateType"]);
		},
		/**
		 * 获取贷款期限
		 */
		getLoanLimit : function(jsonData) {
			var yearLoan = jsonData["yearLoan"];
			var monthLoan = jsonData["monthLoan"];
			var dayLoan = jsonData["dayLoan"];
			var arr = [];
			if (yearLoan && yearLoan > 0) {
				arr[arr.length] = yearLoan + '年';
			}
			if (monthLoan && monthLoan > 0) {
				arr[arr.length] = monthLoan + '个月';
			}
			if (dayLoan && dayLoan > 0) {
				arr[arr.length] = dayLoan + '天';
			}
			return (arr.length > 0) ? arr.join("") : "";
		},
		resize : function(adjWidth, adjHeight) {
			this.appPanel.setWidth(adjWidth);
			this.appPanel.setHeight(adjHeight);
		},
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