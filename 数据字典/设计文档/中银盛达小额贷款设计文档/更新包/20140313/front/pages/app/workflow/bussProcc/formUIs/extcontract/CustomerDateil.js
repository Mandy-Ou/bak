/**
 * 展期协议书详情
 */
define(function(require, exports) {
	exports.moduleUI = {
		customerPanel : null,
		params :null,
		that : null,
		isNotHidden : null,
		getModule:function(params){
			this.show(params);
			if(!this.customerPanel){
				this.createDetailPnl();
			}
			
			this.load();
			return this.customerPanel;
		},
		
		load : function(){
			var _this = this;
		},
			
		show : function(params){
			this.params = params;
			this.that = params.that;
			this.isNotHidden = params.isNotHidden
		},
		createDetailPnl : function(){
			var _this = this;
			var htmlArrs_1 = [
					'<tr><th col="custType">客户类型</th> <td col="custType" >&nbsp;</td><th col="custName">客户名称</th> <td col="custName" >&nbsp;</td><th col="cardNum" id="label_cardType">证件类型</th> <td col="cardNum" >&nbsp;</td></tr>',
					'<tr><th col="contractCode">借款合同号</th> <td col="contractCode" >&nbsp;</td><th col="guacontractCode">保证合同号</th> <td col="guacontractCode" >&nbsp;</td><th col="breedName">业务品种</th> <td col="breedName" >&nbsp;</td></tr>',
					'<tr><th col="payTypeName">还款方式</th> <td col="payTypeName" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td><th col="zprincipals">贷款余额</th> <td col="zprincipals" >&nbsp;</td></tr>',
					'<tr><th col="rate">贷款利率</th> <td col="rate" >&nbsp;</td><th col="mrate">管理费率</th> <td col="mrate" >&nbsp;</td><th col="payDate">贷款期限</th> <td col="payDate" >&nbsp;</td></tr></tr>'];
			var detailCfgs_1 = [{
			    cmns: 'THREE',
			    /* ONE , TWO , THREE */
			    model: 'single',
			    labelWidth: 90,
			    /* title : '#TITLE#', */
			    //详情面板标题
			    /*i18n : UI_Menu.appDefault,*/
			    //国际化资源对象
			    htmls: htmlArrs_1,
			    url: './fcExtension_getContract.action',
			    prevUrl: './fcExtension_getContract.action',
			    nextUrl: './fcExtension_getContract.action',
			    params: {
			        contractId: -1
			    },
			    callback: {
			        sfn: function(jsonData) {
			            jsonData["custType"] = Render_dataSource.custTypeRender(jsonData["custType"]);
			            Ext.get('label_cardType').update(jsonData["cardType"]);
			            var rateType = jsonData["rateType"];
			            var rateTypeName = Render_dataSource.rateTypeRender(rateType);
			            jsonData["rate"] = jsonData["rate"]+"%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("+rateTypeName+")</span>";
			            var mgrtypeName = Render_dataSource.mgrtypeRender(jsonData["mgrtype"]);
			            jsonData["mrate"] = jsonData["mrate"]+"%&nbsp;&nbsp;<span style='color:red;font-weight:bold;'>("+mgrtypeName+")</span>";
			            jsonData["payDate"] =  jsonData["payDate"] +"至"+ jsonData["endDate"];
			            var appAmount = jsonData["appAmount"];
			            jsonData["appAmount"] =  Cmw.getThousandths(appAmount)+"元&nbsp;&nbsp;<br/><span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(appAmount)+")</span>";
			             var zprincipals = jsonData["zprincipals"];
			            jsonData["zprincipals"] =  Cmw.getThousandths(zprincipals)+"元&nbsp;&nbsp;<br/><span style='color:red;font-weight:bold;'>("+Cmw.cmycurd(zprincipals)+")</span>";
			        }
			    }
			}];
			this.customerPanel = new Ext.ux.panel.DetailPanel({
				border : false,
			    isLoad : false,
			    hidden:_this.isNotHidden,
			    detailCfgs: detailCfgs_1
			});
			return this.customerPanel;
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if ( null != this.customerPanel) {
				this.customerPanel.destroy();
				this.customerPanel = null;
			}
		}
	}
});