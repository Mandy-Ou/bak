/**
 * 借款申请单详情
 * @author 程明卫
 * @Date 2014-02-03
 */
define(function(require, exports) {
	exports.moduleUI = {
		appPanel : null,
		params : null,
		applyId : null,
		orderDetailPnlId : Ext.id(null, "PrepaymentDetailApply"),
		detailWin : null,/*详情弹出窗*/
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
			var applyId = this.applyId;
			EventManager.get('./oaLoanApply_detail.action', {
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
							if (_this.callback) _this.callback(json_data);
						}
					});
		},
		newhtmlArrs : function() {
			var htmlArrs_1 = [
					'<tr><th col="code">编号</th> <td col="code" >&nbsp;</td><th col="loanMan">借款人</th> <td col="loanMan" >&nbsp;</td><th col="amount">借款金额</th> <td col="amount" >&nbsp;</td></tr>',
					'<tr><th col="loanDate">借款日期</th> <td col="loanDate" >&nbsp;</td><th col="loanType">还款类型</th> <td col="loanType" >&nbsp;</td><th col="payDate">预计还款日期</th> <td col="payDate" >&nbsp;</td></tr></tr>',
					'<tr><th col="reason">借款事由</th> <td col="reason"  colspan=5 >&nbsp;</td></tr>'];
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
						url : './oaLoanApply_detail.action',
						params : {
							id : -1
						},
						callback : {
							sfn : function(jsonData) {
								_this.renderDispData(jsonData);
							}
						}
					}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
						id : this.orderDetailPnlId,
						title : '借款申请单审批',
						border : false,
						isLoad : false,
						collapsible : true,
						detailCfgs : detailCfgs_1
					});
			return detailPanel;
		},
		renderDispData : function(jsonData) {
			var _this = this;
			var codeid = this.addAHtml(jsonData, 'code', '点击查看借款申请单详情！');//为申请单添加连接
			this.viewDetailInfo(codeid);
			var loanMan = jsonData["loanMan"];
			var deptName = jsonData["deptName"];
			var amount =  jsonData["amount"];
			var loanDate = jsonData["loanDate"];
			var rendType = jsonData["rendType"];
			var loanType = jsonData["loanType"];
			
			jsonData["loanMan"] = loanMan + "&nbsp;&nbsp;<span style='color:red;font-weight:bolid;'>("+deptName+")</span>";
			jsonData["amount"] = Cmw.getThousandths(amount) +"&nbsp;"+"<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(amount)+")</span>";
			rendType = OaRender_dataSource.rendTypeRender(rendType+"");
			jsonData["loanDate"] = loanDate + "&nbsp;&nbsp;<span style='color:red;font-weight:bolid;'>("+rendType+")</span>";
			jsonData["loanType"] = OaRender_dataSource.gvlistRender('300003',loanType);
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
		 * 查看详情
		 * @param {} _this
		 */
		viewDetailInfo : function(eleId){
			if(!eleId) return;
			var _this = this;
			var sysid = this.params.sysid;
			var delay = new Ext.util.DelayedTask(function(){
				var ele = Ext.get(eleId);
				ele.on('click',function(e){
					var _pars = {id:_this.applyId,sysid:sysid};
					var parentCfg = {parent:ele,params:_pars};
					if(_this.detailWin){
						_this.detailWin.show(parentCfg);
					}else{
						Cmw.importPackage('pages/app/workflow/bussProcc/oa/formUIs/detail/LoanApplyDetail',function(module) {
						 	_this.detailWin = module.WinEdit;
						 	_this.detailWin.show(parentCfg);
			  			});
					}
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
			if(null != this.detailWin){
				this.detailWin.destroy();
				this.detailWin = null;
			}
			if (null != this.appPanel) {
				this.appPanel.destroy();
				this.appPanel = null;
			}
		}
	};
});