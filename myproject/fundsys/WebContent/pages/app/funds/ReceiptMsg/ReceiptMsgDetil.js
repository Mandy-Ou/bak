/**
 * 汇票信息详情
 * 
 * @author 李静
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		apptbar : null,
		mainPanel : null,
		appWin : null,
		selId:null,
		sysId : null,
		contractId : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.selId=this.parent.selId;
			this.contractId = parentCfg.parent.contractId;
		},
		createAppWindow : function() {
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({
						width : 820,
						height : 230,
						modal : true,
						tbar : this.apptbar,
						items : [this.mainPanel]
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
			this.appWin.show(this.parent.getEl());
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function() {
			var loanDetailPnl = this.mainPanel;
			var selId = this.parent.getSelId();
			 loanDetailPnl.reload({
			 id : selId
			 });
		},
		/**
		 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data) {
			var _this = exports.WinEdit;
			if (_this.parentCfg.self.refresh)
				_this.parentCfg.self.refresh(_this.optionType, data);
		},
		createToolBar : function() {
			var _this = this;
			var barItems = [{
						text : Btn_Cfgs.CLOSE_BTN_TXT, /*-- 关闭 --*/
						iconCls : Btn_Cfgs.CLOSE_CLS,
						tooltip : Btn_Cfgs.CLOSE_TIP_BTN_TXT,
						handler : function() {
							_this.close();
						}
					}];
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : barItems
					});
			return appBar;
		},
		/**
		 * 创建Form表单
		 */
		createMainPanel : function() {
			var _this=this;
			var htmlArrs_1 = [
					'<tr><th colspan="15" style="font-weight:bold;text-align:left;" >&nbsp;&nbsp;汇票详情>>></th><tr>',
					'<tr><th col="code">编号</th> <td col="code" >&nbsp;</td><th col="rnum">票号</th> <td col="rnum" >&nbsp;</td><th col="amount">金额</th> <td col="amount" >&nbsp;</td></tr>',
					'<tr><th col="rate">利率</th> <td col="rate" >&nbsp;</td><th col="tiamount">收费</th> <td col="tiamount" >&nbsp;</td><th col="name">供票人</th> <td col="name" >&nbsp;</td></tr>',
					'<tr><th col="serviceMan">业务员</th> <td col="serviceMan" >&nbsp;</td><th col="ticketDate">贴现票日期</th> <td col="ticketDate" >&nbsp;</td><th col="ticketMan">收票人</th> <td col="ticketMan" >&nbsp;</td></tr>',
					'<tr><th col="fundsDate">资金到账日期</th> <td col="fundsDate" >&nbsp;</td><th col="ticketRate">贴现利息</th> <td col="ticketRate" >&nbsp;</td><th col="funds">到账金额</th> <td col="funds" >&nbsp;</td></tr>',
					'<tr><th col="adultMoney">提成</th> <td col="adultMoney" >&nbsp;</td><th col="interest">利润</th> <td col="interest" >&nbsp;</td><th col="remark">备注</th> <td col="remark" >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
						cmns : 'THREE',
						/* ONE , TWO , THREE */
						model : 'single',
						labelWidth : 90,
						/* title : '#TITLE#', */
						// 详情面板标题
						/* i18n : UI_Menu.appDefault, */
						// 国际化资源对象
						htmls : htmlArrs_1,
						url : './fuReceiptMsg_detail.action',
						params : {
							id : _this.selId
						},
						callback : {
							sfn : function(jsonData) {
									jsonData["rate"]=jsonData["rate"]+"%";
									jsonData["ticketRate"]=jsonData["ticketRate"]+"%";
									jsonData["amount"]= Cmw.getThousandths(jsonData["amount"]) ;
									jsonData["tiamount"]= Cmw.getThousandths(jsonData["tiamount"]) ;
									jsonData["funds"]= Cmw.getThousandths(jsonData["funds"]) ;
									jsonData["adultMoney"]= Cmw.getThousandths(jsonData["adultMoney"]) ;
									jsonData["interest"]= Cmw.getThousandths(jsonData["interest"]) ;
							}
					 	}
					}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
						width : 780,
						detailCfgs : detailCfgs_1
					});
			return detailPanel;
		},
		/**
		 * 关闭窗口
		 */
		close : function() {
			this.appWin.hide();
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			this.appWin.close();
			this.appWin = null; // 释放当前窗口对象引用
		}
	};
});
