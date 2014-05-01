/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("skythink.cmw.report");
/**
 * 贷款余额汇总表报表 UI chengmingwei 2013-08-17 20:03
 */ 
skythink.cmw.report.LoanSortReport = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.report.LoanSortReport,Ext.util.MyObservable,{
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
		var dt = new Date();
		var barItems = [
			{type : 'label',text : '<span style="color:red;">[提示：贷款余额汇总表分析的是年初一月份到当前查询月份的数据]</span>统计月份'},
			{type : 'date',name : 'statMonth',format:'Y-m',value : dt},
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
				_this.queryFrm.reset();
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
			html:"<div id='"+reportPanelId+"' style='height:100%'></div>"
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
				 '<table style="width:680px;" class="report_header_table">',
			     ' <col width="125"><col width="185"><col width="110"><col width="110"><col width="155">',
			        '<tr>',
			          '<th rowspan="2"  colspan="2">项目</th>',
			          '<th colspan="3">本月</th>',
			        '</tr>',
			        '<tr>',
			          '<th>笔数</th>',
			          '<th>贷款本金余额</th>',
			          '<th>占贷款本金总余额比例</th>',
			        '</tr>',
			    '</table>'
			];
		
			var cfg = {url : './fcBalanceReport_list.action',
				parentId:reportPanelId,headTabHtml:headTabHtml.join(" "),
				cellWidthArr:[125,185,110,110,155],
				width:700,offset:2
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

