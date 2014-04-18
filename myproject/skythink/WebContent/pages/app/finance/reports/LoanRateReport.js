/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]----->彭登浩
 */
Ext.namespace("skythink.cmw.report");
/**
 * 贷款发放利率表 UI chengmingwei 2013-08-05 17:39
 */ 
skythink.cmw.report.LoanRateReport = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.report.LoanRateReport,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
			hasTopSys : false,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var _this =this;
		var dt = new Date();
		var dat_loanyear = FormUtil.getDateField({ fieldLabel: '放贷年份',name:'loanYear',width:90 ,format :'Y',value : dt});
		var chk_rateType = FormUtil.getRadioGroup({fieldLabel : '利率类型',name:'rateType',width:250,
			items : [{boxLabel : '月利率', name:'rateType',inputValue:1, checked: true},
					{boxLabel : '年利率', name:'rateType',inputValue:3},
					{boxLabel : '日利率', name:'rateType',inputValue:2}]});
		var layout_fields = [{cmns:FormUtil.CMN_TWO,fields:[dat_loanyear,chk_rateType]}];
		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var _this = this;
		var toolBar = null;
		var barItems = [{/*查询*/
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
	getAppGrid : function(){
		var _this = this;
		
		var structure_1 = [{
		    header: '月份',
		    name: 'monthItem',
		    width: 80
		},
		{
		    header: '平均利率',
		    name: 'mavgRate',
		    width: 65,
			renderer: function(val) {
		       return (val && val>0) ? val+'%' : val;
		    }
		},
		{
		    header: '最高利率',
		    name: 'mmaxRate',
		    width: 65,
			renderer: function(val) {
		       return (val && val>0) ? val+'%' : val;
		    }
		},
		{
		    header: '发生额',
		    name: 'mmaxAmount',
		    width : 120,
			renderer: function(val) {
		       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
		    }
		},
		{
		    header: '最低利率',
		    name: 'mminRate',
		    width: 65,
			renderer: function(val) {
		       return (val && val>0) ? val+'%' : val;
		    }
		},
		{
		    header: '发生额',
		    name: 'mminAmount',
		    width : 120,
			renderer: function(val) {
		       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
		    }
		},
		{
		    header: '平均利率',
		    name: 'yavgRate',
		    width: 65,
			renderer: function(val) {
		       return (val && val>0) ? val+'%' : val;
		    }
		},
		{
		    header: '最高利率',
		    name: 'ymaxRate',
		    width: 65,
			renderer: function(val) {
		       return (val && val>0) ? val+'%' : val;
		    }
		},
		{
		    header: '发生额',
		    name: 'ymaxAmount',
		    width : 120,
			renderer: function(val) {
		       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
		    }
		},
		{
		    header: '最低利率',
		    name: 'yminRate',
		    width: 65,
			renderer: function(val) {
		       return (val && val>0) ? val+'%' : val;
		    }
		},
		{
		    header: '发生额',
		    name: 'yminAmount',
		    width : 120,
			renderer: function(val) {
		       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
		    }
		}];
		
			
		var continentGroupRow = [{header: '', colspan: 2, align: 'center'},
			{header: '月份数据', colspan: 5, align: 'center'},{header: '年度累计数据', colspan: 5, align: 'center'}];
		 var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [continentGroupRow]
	    });
	    
		var appgrid_1 = new Ext.ux.grid.AppGrid({
			tbar :this.toolBar,
		    structure: structure_1,
		    url: './fcLoanRateReport_list.action',
		    needPage: false,
		    keyField: 'item',
		    plugins: group,
		    isLoad: false,
		    listeners : {
		    	render : function(grid){
		    		_this.globalMgr.query(_this);
		    	}
		    }
		});
		return appgrid_1;
	},
	refresh:function(optionType,data){
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	
	globalMgr : {
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		sysId : this.params.sysid,
		activeKey : null,
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
			params["state"] = 1;
			return params;
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = this.getQparams(_this);
			EventManager.query(_this.appgrid,params);
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

