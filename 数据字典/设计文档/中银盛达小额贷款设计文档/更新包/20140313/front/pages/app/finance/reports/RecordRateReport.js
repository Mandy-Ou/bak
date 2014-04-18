/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("skythink.cmw.report");
/**
 * 贷款业务结构报表 UI chengmingwei 2013-08-10 22:05
 */ 
skythink.cmw.report.RecordRateReport = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.report.RecordRateReport,Ext.util.MyObservable,{
		initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getToolBar : this.getToolBar,
			getAppCmpt : this.getAppCmpt,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var _this = this;
		var dt = new Date().getLastDateOfMonth();
		var barItems = [
			{type : 'label',text : '<span style="color:red;">[提示：如果没有选择，则默认统计到当月底数据]</span>'},
			{type : 'date',name : 'queryDate',format:'Y-m-d',value : dt},
			{/*查询*/
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.query(_this);
			}
		},{/*重置*/
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				_this.toolBar.resets();
			}
		},{/*导出*/
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:Btn_Cfgs.EXPORT_CLS,
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doExport(_this);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppCmpt : function(){
		var _this = this;
		var reportPanelId = Ext.id(null,"reportPanel");
		var reportPanel = new Ext.Panel({
			tbar : this.toolBar,
			html:"<div id='"+reportPanelId+"'class='ledgerDiv'></div>"
		});
		reportPanel.addListener("afterrender",function(panel){
			_this.globalMgr.createReportGrid(_this, reportPanelId);
		});
		return reportPanel;
	},
	
	refresh:function(optionType,data){
		this.globalMgr.query(this);
	},
	changeSize : function(whArr){
		var h = whArr[1];
		if(h>0) h-=2;
		this.appPanel.setHeight(h);
	},
	destroyCmpts : function(){
		
	},
	globalMgr : {
		/**
		 * 报表Grid
		 * @type 
		 */
		reportGrid : null,
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		sysId : this.params.sysid,
		
		createReportGrid : function(_this,reportPanelId){
			var headTabHtml = [
				'<table width="1400" border="1" cellspacing="0" cellpadding="0">',
				 ' <tr>',
				   ' <td width="100" rowspan="2">合同编号</td>',
				   ' <td width="100" rowspan="2">贷款客户名称</td>',
				   ' <td width="100" rowspan="2">追溯号</td>',
				   ' <td width="100" rowspan="2">借款金额</td>',
				    '<td width="100" rowspan="2">借款日</td>',
				    '<td width="100" rowspan="2">借款期限</td>',
				    '<td width="100" rowspan="2">合同还款约定日 </td>',
				    '<td width="100" rowspan="2">还款额</td>',
				   ' <td width="100" rowspan="2">贷款余额</td>',
				    '<td width="100" rowspan="2">利息计算</td>',
				    '<td colspan="2">贷款利息</td>',
				    '<td width="63" rowspan="2">已收利息</td>',
				    '<td colspan="2">应收利息(财务收入)</td>',
				  '</tr>',
				  '<tr>',
				    '<td width="100">年利息</td>',
				   ' <td width="100">月利息</td>',
				   ' <td width="100">累计</td>',
				   ' <td width="100">10月31日</td>',
				  '</tr>',
				'</table>'   
			];
		
			var cfg = {url : './fcLoanContract_rateRecord.action',
				parentId:reportPanelId,headTabHtml:headTabHtml.join(" "),
				cellWidthArr:[100,100,100,100,100,100,100,100,100,100,100,100,100,100],
				width:1400,offset:9
			};
			var reportGrid = new CmwPivotGrid(cfg);
			_this.globalMgr.reportGrid = reportGrid;
			_this.globalMgr.query(_this);
			return reportGrid;
		},
		
		getQparams : function(_this){
			var params = _this.toolBar.getValues() || {};
			params["state"] = 1;
			return params;
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = this.getQparams(_this);
			this.reportGrid.load(params,_this.tab);
		},
		/**
		 * 导出Excel
		 * @param {} _this
		 */
		doExport : function(_this){
			var title = _this.tab.title;
			var reportHtml = this.reportGrid.getReportHtml();
			var url = './sysHtmlFile_save.action';
			 EventManager.get(url,{params:{reportHtml:reportHtml},sfn:function(json_data){
			 	var filePath = json_data.msg;
			 	title = encodeURIComponent(title);
			 	EventManager.downLoad('./controls/html2xls/excel.jsp',{fileName:title,filePath:filePath});
			 }});
		}
	}
});

