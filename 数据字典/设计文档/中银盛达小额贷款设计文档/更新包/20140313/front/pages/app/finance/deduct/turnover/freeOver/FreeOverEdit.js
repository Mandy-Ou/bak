/**
 * 手续费收取流水记录详情
 * 
 * @date 2013-01-17 15:57:32
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		apptbar : null,
		mainPanel : null,
		appWin : null,
		createDetailPnl : null,
		mount : null,
		detailPanlId : Ext.id(null, 'OneCustLoanDetailPanl'),
		bussKey : null,/* 钥匙KEY */
		sysId : null,
		appgrid_1 : null,
		contractId : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
			this.contractId = parentCfg.parent.contractId;
			var yamount = parentCfg.parent.getCmnVals("yamount");// 已手续收金额
			var freeamount = parentCfg.parent.getCmnVals("freeamount");// 应收手续费金额
			if (!yamount) {
				yamount = 0.00;
			}
			var mt = {};
			this.mount = {
				freeamount : freeamount,
				yamount : yamount
			};
			// this.mount = mt;
		},
		createAppWindow : function() {
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({
						width : 820,
						height : 500,
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
			var loanDetailPnl = Ext.getCmp(this.detailPanlId);
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
			var _this = this;
			var selId = this.parent.getSelId();
			var htmlArrs_1 = [
					'<tr><th col="status">收款状态</th> <td col="status" >&nbsp;</td><th col="code">借款合同号</th> <td col="code" >&nbsp;</td><th col="name">客户姓名</th> <td col="name" >&nbsp;</td></tr>',
					'<tr><th col="accName">还款人名称</th> <td col="accName" >&nbsp;</td><th col="appAmount">贷款金额</th> <td col="appAmount" colspan=3>&nbsp;</td></tr>',
					'<tr><th col="yamount">已收手续费</th> <td col="yamount" >&nbsp;</td><th col="prate">手续费率</th> <td col="prate" >&nbsp;</td><th col="freeamount">应收手续费</th> <td col="freeamount" >&nbsp;</td></tr>',
					'<tr><th col="regBank">开户行</th> <td col="regBank" >&nbsp;</td><th col="payAccount">还款帐号</th> <td col="payAccount" >&nbsp;</td><th col="payAmount">已放款金额</th> <td col="payAmount" >&nbsp;</td></tr>',
					'<tr><th col="loanLimit">贷款期限</th> <td col="loanLimit" >&nbsp;</td><th col="realDate">合约放款日</th> <td col="realDate"  colspan=3 >&nbsp;</td></tr>'];
			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 95,
				title : '客户放款手续费流水信息',
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				url : './fcFree_getLoanRecord.action',
				params : {
					id : selId
				},
				callback : {
					sfn : function(jsonData) {
						jsonData["status"] = Render_dataSource
								.statusRender(jsonData["status"]);
						if (jsonData["yamount"]) {
							jsonData["yamount"] = jsonData["yamount"] + "元";
						}
						jsonData["name"] = Render_dataSource
								.custTypeRender(jsonData["name"])
								+ '<span style="color:red;">('
								+ Render_dataSource
										.custTypeRender(jsonData["custType"])
								+ ')</span>';
						var loanLimit = Render_dataSource
								.loanLimitRender(jsonData);
						jsonData["loanLimit"] = loanLimit;
						var appAmount = jsonData["appAmount"];
						jsonData["appAmount"] = Cmw.getThousandths(appAmount)
								+ '元<br/><span style="color:red;">(大写：'
								+ Cmw.cmycurd(appAmount) + ')</span>';
						var payAmount = jsonData["payAmount"];
						jsonData["payAmount"] = Cmw.getThousandths(payAmount)
								+ '元<br/><span style="color:red;">(大写：'
								+ Cmw.cmycurd(payAmount) + ')</span>';
						var prate = jsonData["prate"];
						if (prate && prate > 0)
							jsonData["prate"] = prate + "%";
						var selId = _this.parent.getSelId();
						_this.appgrid_1.reload({
									id : selId
								});
					}
				}
			}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
						id : this.detailPanlId,
						frame : false,
						width : 800,
						auroHeight : true,
						detailCfgs : detailCfgs_1
					});
			this.appgrid_1 = this.createDetailPnl()
			detailPanel.add(this.appgrid_1);
			return detailPanel;
		},
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function() {
			var parent = exports.WinEdit.parent;
			var selId = null;
			if (parent) {
				selId = parent.getSelId();
			} else {
				ExtUtil.alert({
							msg : '请传入 parent 对象！'
						});
			}
			var structure_1 = [{
						header : '银行名称',
						name : 'bankName'
					}, {
						header : '银行帐号 ',
						name : 'account'
					}, {
						header : '借款合同',
						name : 'code'
					}, {
						header : '实收手续费',
						name : 'yamount',
						renderer : function(val) {
							switch (val) {
								case 0 :
									val = '0';
									break;
							}
							return val;
						},
						  renderer : function(val){return Cmw.getThousandths(val);}
					}
					, {
						header : '应收手续费',
						name : 'freeamount',
						renderer : function(val) {
							switch (val) {
								case 0 :
									val = '0';
									break;
							}
							return val;
						},
					  renderer : function(val){return Cmw.getThousandths(val);}
					}, {
						header : '收款日期',
						name : 'lastDate'
					}];

			var appgrid_1 = new Ext.ux.grid.AppGrid({
						title : '实收手续费流水',
						structure : structure_1,
						height : 300,
						width : true,
						needPage : true,
						isLoad : false,
						keyField : 'id',
						url : './fcFreeRecords_list.action'
					});
			return appgrid_1;
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
