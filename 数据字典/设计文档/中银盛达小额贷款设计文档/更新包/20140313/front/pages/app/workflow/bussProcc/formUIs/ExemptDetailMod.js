/**
 * 息费豁免业务审批详情信息
 * 
 * @author 彭登浩
 * @date 2013-05-30 9:02
 */
define(function(require, exports) {
	exports.moduleUI = {
		appExtenCmpt : null,
		appPanel : null,
		params : null,
		applyId : null,
		orderDetailPnlId : Ext.id(null, "ExemptDetailApply"),
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
			EventManager.get('./fcExempt_detail.action', {
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
			var htmlArrs_1 = [
					'<tr><th col="code">申请单编号</th> <td col="code" >&nbsp;</td><th col="loanCode">借款合同号</th> <td col="loanCode" >&nbsp;</td><th col="custType" id="'+this.idMgr.custTypeId+'">客户类型</th> <td col="custName" >&nbsp;</td></tr>',
					'<tr><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th col="managerName">经办人</th> <td col="managerName" >&nbsp;</td><th col="payType">原贷款还款方式</th> <td col="payType" >&nbsp;</td></tr>',
					'<tr><th col="etype">豁免类别</th> <td col="etype" >&nbsp;</td><th col="totalAmount">豁免息费合计</th> <td col="totalAmount" >&nbsp;</td><th col="exeDate">豁免期限</th> <td col="exeDate" >&nbsp;</td></tr>'
					];
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
						url : './fcExempt_detail.action',
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
//						title : '息费豁免业务审批',
						border : false,
						isLoad : false,
//						collapsible : true,
						detailCfgs : detailCfgs_1
					});
			return detailPanel;
		},
		renderDispData : function(jsonData) {
			var _this = this;
			var params = {
				isAapply : true,
				parent : {
					selId : jsonData["id"]
				}
			};
			
			var codeid = this.addAHtml(jsonData, 'code', '点击查看申请单详情！');//为展期申请单添加连接
			var loanCodeId = this.addAHtml(jsonData,'loanCode','点击查看借款合同详情！');
			var custNameId = this.addAHtml(jsonData,'custName','点击查看客户资料详情！');
			
			var custTabId = CUSTTAB_ID.customerInfoDetailTab.id;
			var url =  CUSTTAB_ID.customerInfoDetailTab.url;
			var custTitle =  '客户详情';
			var customerId = jsonData["customerId"];
			var customerInfoId = jsonData["baseId"];
			var applyId = jsonData["applyId"];
			var customerParams = {customerId:customerId,customerInfoId:customerInfoId,parent : _this.appPanel};
			_this.viewDetailInfo(custNameId,custTabId,customerParams,url,custTitle);
			
			
			var sysid = _this.params.sysId
			var loanContractParams = {sysid:sysid,formId:applyId};
			_this.viewLoanContractInfo(loanCodeId,loanContractParams);
			
			
			var custType = jsonData['custType'];
			if(!custType){
				custType = '客户名称';
			}else{
				custType = Render_dataSource.custTypeRender(custType);
			}
			var label_custType = Ext.get(this.idMgr.custTypeId);
			if(label_custType) label_custType.update(custType);
			
			var isadvance = jsonData["isadvance"];
			var payType = jsonData["payType"];
			jsonData["payType"] = Render_dataSource.payTypeRender(payType)+"<br>&nbsp;<span style='color:red;font-weight:bold;'>("+"是否预收息:"+Render_dataSource.isadvanceRender(isadvance)+")</span>";
			
			var appAmount = jsonData["appAmount"];
            jsonData["appAmount"] = appAmount +"&nbsp;"+"<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(appAmount)+")</span>";
            
			
            var totalAmount = jsonData["totalAmount"];
            jsonData["totalAmount"] = totalAmount+"<span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(totalAmount)+")</span>";
            
            var startDate = jsonData["startDate"];
            var endDate = jsonData["endDate"];
            if(startDate){
            	jsonData["exeDate"] = startDate+"至"+endDate;
            }
            
            
             var etype = jsonData["etype"];
             var exeItems = jsonData["exeItems"];
            jsonData["etype"] = Render_dataSource.exempt_etypeRender(etype)+"<br><span style='color:red;font-weight:bold;'>(豁免项目:"+Render_dataSource.exempt_exeItemsRender(exeItems)+")</span>";
            
          
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
		viewDetailInfo : function(eleId,tabId,params,url,title){
			var _this = this;
			if(!eleId) return;
			apptabtreewinId = _this.params["apptabtreewinId"];
			params.apptabtreewinId = apptabtreewinId;
			params.dispaly  = false;
			
			var delay = new Ext.util.DelayedTask(function(){
				var ele = Ext.get(eleId);
				ele.on('click',function(e){
					Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
				});
			});
			
			delay.delay(150);
		},
		/**
		 * 显示借款详情
		 */
		viewLoanContractInfo : function(aid,loanContractParams){
			var _this = this;
			var delay = new Ext.util.DelayedTask(function(){
				var ele = Ext.get(aid);
				if(!ele) return;
				ele.on('click',function(e){
					loanContractParams.ele = ele;
					if(_this.appCmpts){
						 _this.appCmpts.show(loanContractParams);
					}else{
						Cmw.importPackage('pages/app/workflow/variety/formUIs/loancontract/LoanContractDetail',function(mode){
						 _this.appCmpts = mode.WinEdit;
						  _this.appCmpts.show(loanContractParams);
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
			if (null != this.appPanel) {
				this.appPanel.destroy();
				this.appPanel = null;
			}
		}
	};
});