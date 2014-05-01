/**
 * 逾期还款金额收取流水
 */ 
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		params : null,
		parent : null,
		apptbar : null,
		mainPanel : null,
		appFrm : null,
		appWin : null,
		appGrid : null,
		detailPanlId : Ext.id(null, 'OverdueDeductDetailPanl'),
		bussKey : null,/* 钥匙KEY */
		sysId : null,
		setParams : function(parentCfg) {
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.sysId = parentCfg.parent.sysId;
		},
		createAppWindow : function() {
			this.apptbar = this.createToolBar();
			this.mainPanel = this.createMainPanel();
			this.appWin = new Ext.ux.window.MyWindow({
						width : 850,
						height : 610,
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
			if (_parentCfg)this.setParams(_parentCfg);
			if (!this.appWin) {
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
			this.loadData();
		},
		/**
		 * 加载数据
		 */
		loadData : function() {
			var loanDetailPnl = Ext.getCmp(this.detailPanlId);
			var selId = this.parent.getSelId();
			loanDetailPnl.reload({id : selId});
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
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({aligin : 'right',controls : barItems});
			return appBar;
		},
		/**
		 * 创建Form表单
		 */
		createMainPanel : function() {
			var _this = this;
			this.appGrid = this.createAppGrid();
//			this.appFrm = this.createAppFrm();
			var selId = this.parent.getSelId();
				var htmlArrs_1 = [
					'<tr><th col="code">借款合同号</th> <td col="code" >&nbsp;</td><th col="custName">客户名称</th> <td colspan="4" col="custName" >&nbsp;</td><tr>',
					'<tr><th col="payBank">还款银行</th> <td col="payBank" >&nbsp;</td><th col="payAccount">还款帐号</th> <td col="payAccount" >&nbsp;</td><th col="accName">还款人</th> <td col="accName" >&nbsp;</td><tr>',
					'<tr><th col="monthPharses">本月收款期次</th> <td col="monthPharses" >&nbsp;</td><th col="paydPhases">已还期数</th> <td col="paydPhases" >&nbsp;</td><th col="totalPhases">总期数</th> <td col="totalPhases" >&nbsp;</td><tr>',
					'<tr><th colspan="6" style="font-weight:bold;text-align:left;" >&nbsp;&nbsp;累计应收(逾期金额) >>></th><tr>',
					'<tr><th col="amounts">逾期本金</th> <td col="amounts" >&nbsp;</td><th col="iamounts">利息</th> <td col="iamounts" >&nbsp;</td><th col="mamounts">管理费</th> <td col="mamounts" >&nbsp;</td><tr>',
					'<tr><th col="pamounts">罚息</th> <td col="pamounts" >&nbsp;</td><th col="damounts">滞纳金</th> <td col="damounts" >&nbsp;</td><th col="totalAmounts">应收合计</th> <td col="totalAmounts" >&nbsp;</td><tr>'
					];
			var detailCfgs_1 = [{
				cmns : 'THREE',
				/* ONE , TWO , THREE */
				model : 'single',
				labelWidth : 115,
				title : '逾期还款流水信息',
				// 详情面板标题
				/* i18n : UI_Menu.appDefault, */
				// 国际化资源对象
				htmls : htmlArrs_1,
				isLoad:false,
				url : './fcOverdueDeduct_get.action',
				params : {
					id : selId
				},
				callback : {
					sfn : function(jsonData) {
						_this.callback(jsonData);
							var selId = _this.parent.getSelId();
						_this.appGrid.reload({contractId:selId});	
					}
				}
			}];
			var detailPanel = new Ext.ux.panel.DetailPanel({
				id : this.detailPanlId,
				frame : false,
				isLoad : false,
				width : 850,
				detailCfgs : detailCfgs_1
			});
			detailPanel.add(this.appGrid);
			return detailPanel;
		},
		/**
		 * 创建逾期还款Grid
		 */
		createAppGrid : function(){
			
			var structure_1 = [{
			    header: '业务标识',
			    name: 'bussTag',
			    renderer : Render_dataSource.AmountRecords
			},
			{
			    header: '实还',
			    name: 'cat'
			    
			},
			{ 
			    header: '利息',
			    name: 'rat'
			},
			{
			    header: '实收管理费',
			    name: 'mat'
			},{
			    header: '实收罚息',
			    name: 'pat'
			   
			},{
			    header: '实收滞纳金',
			    name: 'dat'
			   
			},{
				    header: '实收合计',
			    name: 'tat'
			   
			},{
				    header: '收款日期',
			    name: 'rectDate'
			   
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '逾期还款流水列表',
			    structure: structure_1,
			    url: './fcAmountRecords_listPay.action',
			    height:380,
			    width: 850,
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
		},
		/**
		 * 当详情面板数据加载完成后触发的回调函数
		 */
		callback : function(jsonData){
			var custType = Render_dataSource.custTypeRender(jsonData.custType);;
			jsonData["custName"] = jsonData["name"]+'<span style="color:red;">('+custType+')</span>';
			var appAmount = jsonData["appAmount"];
			jsonData["appAmount"] = Cmw.getThousandths(appAmount)
					+ '元<br/><span style="color:red;">(大写：'
					+ Cmw.cmycurd(appAmount) + ')</span>';
		},
		/**
		 * 重置数据
		 */
		resetData : function() {
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
			this.appWin.close(); // 关闭并销毁窗口
			this.appWin = null; // 释放当前窗口对象引用
		}
	};
});
