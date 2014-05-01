/**
 * 委托合同暂存详情
 */
define(function(require, exports) {
	exports.moduleUI = {
		appPanel : null,
		appCmpts : null,
		params : null,
		applyId : null,
		orderDetailPnlId : Ext.id(null, "ExtensionDetailApply"),
		callback : null,/* 回调函数 */
		PreviewPnl : null,
		idMgr : null,
		/**
		 * 获取业务模块
		 * 
		 * @param params
		 *            由菜单点击所传过来的参数 {tabId : tab 对象ID,apptabtreewinId :
		 *            系统程序窗口ID,sysid : 系统ID}
		 */
		getModule : function(params) {
			if (!this.appPanel) {
				this.appPanel = new Ext.Panel({border : false});
				this.initIdMgr();
			}
			this.show(params);
			return this.appPanel;
		},
		initIdMgr : function(){
			this.idMgr = {
				custTypeId : Ext.id(null,"custType")
			};		
		},
		/**
		 * 设置参数
		 */
		setParams : function(params) {
			this.params = params;
			this.applyId = this.params.applyId;
		},
		/**
		 * 显示面板并加载数据
		 */
		show : function(params) {
			this.setParams(params);
			this.loadData();
		},
		/**
		 * 获取选中的记录参数
		 */
		getParams : function() {
			var parent = exports.WinEdit.parent;
			var selId = parent.getSelId();
			var selId = null;
			if (parent) {
				selId = parent.getSelId();
			} else {
				ExtUtil.alert({
							msg : '请传入 parent 对象！'
						});
			}
			var params = {
				id : selId
			};
			return params;
		},
		/**
		 * 加载客户信息数据
		 */
		loadData : function() {
			var _this = this;
			var applyId = this.applyId;
			EventManager.get('./fuAmountApply_detail.action', {//fuEntrustContract_list
						params : {
							id : applyId
						},
						sfn : function(json_data) {
							var activePanel = Ext.getCmp(_this.orderDetailPnlId);
							if (!activePanel) {
								activePanel = _this.getOrderDetailPanel();
								_this.appPanel.add(activePanel);
								_this.appPanel.doLayout();
							}
							activePanel.reload({
										json_data : json_data
									}, true);
							if (_this.callback)
								_this.callback(json_data);
						}
					});
		},
		newhtmlArrs : function() {
			var htmlArrs_1 = ['<tr><th col="code">申请单编号</th> <td col="code" >&nbsp;</td><th col="payBank">收款银行</th> <td col="payBank" >&nbsp;</td><th col="payAccount">收款账号</th> <td col="payAccount" >&nbsp;</td></tr>',
					'<tr><th col="accName">账户名</th> <td col="accName" >&nbsp;</td><th col="appAmount">委托金额</th> <td col="appAmount" >&nbsp;</td><th col="productsId">委托产品</th> <td col="productsId" >&nbsp;</td></tr>',
					'<tr><th col="doDate">合同签订日期</th> <td col="doDate" >&nbsp;</td><th col="payDate">委托生效日期</th> <td col="payDate" >&nbsp;</td><th col="endDate">委托失效日期</th> <td col="endDate" >&nbsp;</td></tr></tr>'];
			return htmlArrs_1;
		},
		getOrderDetailPanel : function() {
			var _this = this;
			var htmlArrs_1 = null;
			htmlArrs_1 = _this.newhtmlArrs();
			var detailCfgs_1 = [{
						cmns : 'THREE',
						/* ONE , TWO , THREE */
						model : 'single',
						labelWidth : 90,
						// 详情面板标题
						/* i18n : UI_Menu.appDefault, */
						// 国际化资源对象
						htmls : htmlArrs_1,
						url : './fuAmountApply_detail.action',
						params : {
						},
						callback : {
							sfn : function(jsonData) {
								jsonData["appAmount"]=Cmw.getThousandths(jsonData["appAmount"])+'元';
								_this.renderDispData(jsonData);
							}
						}
					}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
						id : this.orderDetailPnlId,
//						title : '展期申请单信息',
						border : false,
						isLoad : false,
//						collapsible : true,
						detailCfgs : detailCfgs_1
					});
			return detailPanel;
		},
		renderDispData : function(jsonData) {
			var _this = this;
//			var codeid = this.addAHtml(jsonData, 'code', '点击查看申请单详情！');//为展期申请单添加连接
			var custTabId = CUSTTAB_ID.customerInfoDetailTab.id;
			var url =  CUSTTAB_ID.customerInfoDetailTab.url;
			var custTitle =  '客户详情';
			var customerId = jsonData["customerId"];
			var customerInfoId = jsonData["baseId"];
			var applyId = jsonData["applyId"];
			var customerParams = {customerId:customerId,customerInfoId:customerInfoId,parent : _this.appPanel};
			_this.viewDetailInfo(/*custNameId,*/custTabId,customerParams,url,custTitle);
			var sysid = _this.params.sysId			
			/*	
			var loanContractParams = {sysid:sysid,formId:applyId};
			_this.viewLoanContractInfo(loanCodeId,loanContractParams);
			*/
            var extensionParams = {sysid:sysid,formId:jsonData["id"]};
//			_this.viewExtensionDetailInfo(codeid,extensionParams);
			var params = {
				isAapply : true,
				parent : {selId : jsonData["id"]}};},
		resize : function(adjWidth, adjHeight) {
			this.appPanel.setWidth(adjWidth);
			this.appPanel.setHeight(adjHeight);
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
		 * 展期申请单详情
		 */
		viewExtensionDetailInfo : function(aid,extensionParams){
			var _this = this;
			var delay = new Ext.util.DelayedTask(function(){
				var ele = Ext.get(aid);
				if(!ele) return;
				ele.on('click',function(e){
					extensionParams.ele = ele;
					if(_this.appExtenCmpt){
						 _this.appExtenCmpt.show(extensionParams);
					}else{
						Cmw.importPackage('pages/app/funds/entrustcontract/EntrustcustDetail.js',function(mode){
						 _this.appExtenCmpt = mode.WinEdit;
						  _this.appExtenCmpt.show(extensionParams);
						});
					}
				});
			});
			delay.delay(150);
		},
		/**
		 * aId 是需要添加的a标签连接
		 * PreviewParams 所需要的参数
		 * url 需要导入的路径
		 */
		viewDetailInfo : function(eleId,tabId,params,url,title) {
			var _this = this;
			if(!eleId) return;
			apptabtreewinId = _this.params["apptabtreewinId"];
			params.apptabtreewinId = apptabtreewinId;
			params.dispaly  = false;
			
			var delay = new Ext.util.DelayedTask(function(){
				var ele = Ext.get(eleId);
				if(!ele) return;
				ele.on('click',function(e){
					Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
				});
			});
			delay.delay(150);
		},
		/**
		 * 获取详情面板的高度
		 */
		getHeight : function() {
			var height = 145;
			if (!this.appPanel || !this.appPanel.rendered) {
				return height;
			}
			var appEl = this.appPanel.el;
			if (!appEl)
				return height;
			height = appEl.getComputedHeight();
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