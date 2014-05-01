/**
 * 汇票详情
 */
define(function(require, exports) {
	exports.moduleUI = {
		appPanel : null,
		appCmpts : null,
		params : null,
		applyId : null,
		orderDetailPnlId : Ext.id(null, "ReceiptDetail"),
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
			Cmw.print(params);
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
			EventManager.get('./fuReceipt_detail.action', {
						params : {
							id : applyId,
							menuId : this.params.menuId,
							sysId : this.params.sysId
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
			var htmlArrs_1 = [    
					'<tr><th col="rnum">票号</th> <td col="rnum" >&nbsp;</td><th col="name">客户姓名</th> <td col="name" >&nbsp;</td><th col="outMan">出票人</th> <td col="outMan" >&nbsp;</td></tr>',
					'<tr><th col="outDate">出票日期</th> <td col="outDate" >&nbsp;</td><th col="endDate">到票日期</th> <td col="endDate" >&nbsp;</td><th col="amount">金额</th> <td col="amount" >&nbsp;</td></tr>',
					'<tr><th col="rtacname">收款人账户名</th> <td col="rtacname" >&nbsp;</td><th col="rtaccount">收款人账号</th> <td col="rtaccount" >&nbsp;</td><th col="rtbank">收款人开户行</th> <td col="rtbank" >&nbsp;</td></tr>',
					'<tr><th col="omaccount">出票人账号</th> <td col="omaccount" >&nbsp;</td><th col="reman">收条接收人</th> <td col="reman" >&nbsp;</td><th col="amount">金额</th> <td col="amount" >&nbsp;</td></tr>',
					'<tr><th col="pbank">付款行</th> <td col="pbank" >&nbsp;</td><th col="rcount">汇票数量(单位:张)</th> <td col="rcount" >&nbsp;</td><th col="recetDate">收条签收日期</th> <td col="recetDate" >&nbsp;</td></tr>'];
				return htmlArrs_1;
		},
		getOrderDetailPanel : function() {
			var _this = this;
			var htmlArrs_1 = null;
			htmlArrs_1 = _this.newhtmlArrs();

			var detailCfgs_1 = [{
						cmns : 'THREE',
						model : 'single',
						labelWidth : 90,
						htmls : htmlArrs_1,
						url : './fuReceipt_detail.action',
						params : {
						},
						callback : {
							sfn : function(jsonData) {
								_this.renderDispData(jsonData);
							}
						}
					}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
						id : this.orderDetailPnlId,
						border : false,
						isLoad : false,
						detailCfgs : detailCfgs_1
					});
			return detailPanel;
		},
		renderDispData : function(jsonData) {
			var _this = this;
			var codeid = this.addAHtml(jsonData, 'rnum', '点击查看申请单详情！');//为汇票审批添加链接
			var custTabId = CUSTTAB_ID.customerInfoDetailTab.id;
			var url =  CUSTTAB_ID.customerInfoDetailTab.url;
			var custTitle =  '汇票收条详情';
			var customerParams = {parent : _this.appPanel};
			_this.viewDetailInfo(custTabId,customerParams,url,custTitle);
			var orgtype  = 3;
			var sysid = _this.params.sysId			
            var extensionParams = {sysid:sysid,formId:jsonData["id"],id : this.applyId,orgtype : orgtype};
			_this.viewExtensionDetailInfo(codeid,extensionParams);
			
			var params = {
				isAapply : true,
				parent : {
					selId : jsonData["id"]
				}
			};
		},
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
		 * 汇票收条详情
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
						Cmw.importPackage('pages/app/funds/receipt/ReceiptEdit.js',function(mode){
						 _this.appExtenCmpt = mode.viewUI;
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