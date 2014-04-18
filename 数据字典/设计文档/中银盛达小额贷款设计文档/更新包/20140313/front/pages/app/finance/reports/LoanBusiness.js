/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]----->彭登浩
 */
Ext.namespace("cmw.skythink");
/**
 *民汇业务统计报表 UI smartplatform_auto 2013-01-16 09:48:16
 */ 
cmw.skythink.LoanBusiness = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.LoanBusiness,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,
			getToolBar : this.getToolBar,
			getAppCmpt : this.getAppCmpt,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	changeSize : function(whArr){
		var h = whArr[1];
		if(h>0) h-=2;
		this.appPanel.setHeight(h);
	},
	destroyCmpts : function(){
		
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var dt = new Date();
		var barItems = [
			{type : 'label',text : '<span style="color:red;">[提示：贷款业务统计报表分析的是当年，当月，当周，当天的查询数据]</span>统计时间'},
			{type : 'date',name : 'startDate',value : dt,id : self.globalMgr.startDate},
		{
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},{
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				self.queryFrm.reset("custType");
			}
		},{type:"sp"},{
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:'page_exportxls',
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.doExport(self);
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
		var structure = [
			{header : '统计范围',name : 'between',width:80},
	  		{header : '累计发放贷额',name : 'sumAmount',width:100,renderer:function(val){
	  			return Cmw.getThousandths(val);
	  		}},
			{header : '已还贷款',name : 'loansHave',width:100,renderer:function(val){
				return Cmw.getThousandths(val);
			}},
			{header : '贷款余额',name : 'loans',width:100,renderer:function(val){
				return Cmw.getThousandths(val);
			}},
			{header : '累计收利息',name : 'sumInterest',width:100,renderer:function(val){
				return Cmw.getThousandths(val);
			}},
			{header : '累计收管理费',name : 'sumMat',width:100,renderer:function(val){
				return Cmw.getThousandths(val);
			}},
			{header : '累计收罚息',name : 'sumPat',width:100,renderer:function(val){
				return Cmw.getThousandths(val);
			}},
			{header : '累计收滞纳金',name : 'sumDat',width:100,renderer:function(val){
				return Cmw.getThousandths(val);
			}},
			{header : '手续费',name : 'sumFree',width:100,renderer:function(val){
				return Cmw.getThousandths(val);
			}},
			{header : '总收入',name : 'sum',width:100,renderer:function(val){
				return Cmw.getThousandths(val);
			}}
			];
		var toolBar = this.getToolBar();
		var _appgrid = new Ext.ux.grid.AppGrid({
			title : '贷款业务统计表报',
			tbar : toolBar,
			structure : structure,
			url : './fcLoanBusiness_list.action',
			needPage : false,
			keyField : 'id',
			isLoad: false,
			listeners : {
			   	render : function(grid){
			   	 _this.globalMgr.query(_this);
			   	}
		   }
		});
		this.globalMgr.appgrid = _appgrid;
		return _appgrid;
	},
	refresh:function(optionType,data){
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	
	globalMgr : {
		startDate : Ext.id(null,'startDate'),
		sysId : this.params.sysid,
		appgrid  : null,
		getQparams : function(_this){
			var params = {startDate : null,sysId:null};
			var dateField = Ext.get(_this.globalMgr.startDate)
			if(dateField){
				params.startDate = dateField.getValue();
			}else {
				params.startDate = new Date().format('Y-m-d');
			}
			params.sysId = _this.globalMgr.sysId;
			return params;
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = this.getQparams(_this);
			EventManager.query(_this.globalMgr.appgrid,params);
		},
		/**
		 * 导出Excel
		 * @param {} _this
		 */
		doExport : function(_this){
			var params = this.getQparams(_this);
			var token = _this.params.nodeId;
			EventManager.doExport(token,params);
		}
	}
});

