/**
 * 放款单的记录
 * @author smartplatform_auto
 * @date 2013-11-23 03:33:21
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增 */
		detailPnl : null, // 详情面板
		appWin : null,
		sysId : null,
		payName : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.sysId;
			this.optionType = parentCfg.optionType || OPTION_TYPE.DETAIL;
		},
		createAppWindow : function() {
			this.detailPnl = this.createDetailPnl();
			this.appWin = new Ext.ux.window.AbsDetailWindow({
					hiddenBtn : true,
						width : 825,
						getParams : this.getParams,
						appDetailPanel : this.detailPnl,
						optionType : this.optionType,
						refresh : this.refresh
					});
		},
		
		/**
		 * 显示弹出窗口
		 * 
		 * @param _parentCfg
		 *            弹出之前传入的参数
		 */
		show : function(_parentCfg) {
			if (_parentCfg)
				this.setParams(_parentCfg);
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
			if (!this.appWin)
				return;
			this.appWin.close(); // 关闭并销毁窗口
			this.appWin = null; // 释放当前窗口对象引用
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
			var params = {id : selId,custType:Buss_Constant.CustType_0};
			return params;
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
		 * 创建详情面板
		 */
		createDetailPnl : function() {
			var _this = this;
			var parent = exports.WinEdit.parent;
			var selId = null;
			if (parent) {
				selId = parent.getSelId();
			} else {
				ExtUtil.alert({
							msg : '请传入 parent 对象！'
						});
			}
			var htmlArrs = null;
			var htmlArrs_1 = [
					'<tr><th col="code">放款单编号</th> <td col="code" >&nbsp;</td><th col="ccode">借款合同号</th> <td col="ccode" >&nbsp;</td><th col="name">客户姓名</th> <td col="name" >&nbsp;</td></tr>',
					'<tr><th col="cashier">放款人</th> <td col="cashier" >&nbsp;</td><th col="prate">手续费</th> <td col="prate" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" >&nbsp;</td></tr>',
					'<tr><th col="regBank">开户行</th> <td col="regBank" >&nbsp;</td><th col="account">收款帐号</th> <td col="account" >&nbsp;</td><th col="payAmount">放款金额</th> <td col="payAmount" >&nbsp;</td></tr>',
					'<tr><th col="rate">贷款利率</th> <td col="rate" >&nbsp;</td><th col="payName">收款人名称</th> <td col="payName" colspan=3>&nbsp;</td></tr>',
					'<tr><th col="prate">放款手续费率</th> <td col="prate" >&nbsp;</td><th col="realDate">实际放款日</th> <td col="realDate"  colspan=3 >&nbsp;</td></tr>',
					'<tr><th col="Gaccouont">放款银行帐号</th> <td col="Gaccouont" >&nbsp;</td><th col="GbankName">放款银行</th> <td col="GbankName"  colspan=3 >&nbsp;</td></tr>',
					FORMDIY_DETAIL_KEY
			];
			var detailCfgs_1 = [{
				 cmns: 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth :95,
				isLoad : false,
				title : '放款记录', 
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './fcLoanInvoceQuery_get.action',
				params : {},
				formDiyCfg : {sysId:_this.sysId,formdiyCode:FORMDIY_IND.FORMDIY_LOANINVOCE,formIdName:'id'},
				callback : {
					sfn : function(jsonData) {
						if(jsonData["rate"]){
			        		var rate = jsonData["rate"];
			        		jsonData["rate"] =rate+'%';
			        	}
		        		if(jsonData["prate"]){	
			        		var prate = jsonData["prate"];
			        		jsonData["prate"] =prate+'%';
			        	}
						var payAccount=jsonData["payAccount"];
						/* 可在对页面进行赋值前，对数据进行转换处理 to do .. */
						jsonData["auditState"] = Render_dataSource.auditStateRender(jsonData["auditState"]);
						jsonData["atype"] = Render_dataSource.atypeRender(jsonData["atype"]);
						if(jsonData["ysMat"]){
							var ysMat = jsonData["ysMat"];
							if(ysMat){
								jsonData["ysMat"] =  Cmw.getThousandths(ysMat)+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(ysMat)+')</span>';
							}else{
								jsonData["ysMat"]  = "";
							}
			        	}
			        	if(jsonData["ysRat"]){
			        		var ysRat = jsonData["ysRat"];
		        			if(ysRat){
		        				jsonData["ysRat"] =  Cmw.getThousandths(ysRat)+'元&nbsp;&nbsp;<span style="color:red;font-weight:bold;">(大写：'+Cmw.cmycurd(ysRat)+')</span>';
		        			}else{
		        				jsonData["ysRat"] = "";
		        			}
			        	}
					}
				}
			}];
			var detailPanel_1 = new Ext.ux.panel.DetailPanel({
						frame : false,
			    		width: true,
						detailCfgs : detailCfgs_1,
						isLoad : false
					});
			return detailPanel_1;
		}
	};
});
