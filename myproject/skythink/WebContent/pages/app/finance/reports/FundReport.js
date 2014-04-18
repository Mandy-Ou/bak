/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]----->彭登浩
 */
Ext.namespace("skythink.cmw.report");
/**
 * 贷款发放利率表 UI chengmingwei 2013-08-05 17:39
 */ 
skythink.cmw.report.FundReport = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.report.FundReport,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
			hasTopSys : false,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			destroys : this.destroys
		});
	},
	
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var _this =this;
		var dt = new Date();
		var dat_loanyear = FormUtil.getDateField({ fieldLabel: '<span style="color:red;">(提示：起始日期不能小于当前日期)</span>起始日期',name:'startDate',width:100,value:dt});
		var layout_fields = [{cmns:FormUtil.CMN_TWO,fields:[dat_loanyear]}];
		var queryFrm = FormUtil.createLayoutFrm({labelWidth:250},layout_fields);
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var _this = this;
		var toolBar = null;
		var barItems = [ {
			type : 'label',text : '<span style="color:red;font-weight:bold;">[提示：资金头寸分析的是从起始日期到未来指定的天数里资金的回笼情况]</span>'
        },{/*查询*/
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
		    header: '未来周期',
		    name: 'cycleDate',
		    width: 65,
			renderer: function(val) {
		       return val+"天";
		    }
		},{
		    header: '截止日期',
		    name: 'endDate',
		    width: 100
		},
		{
		    header: '回笼资计合计',
		    name: 'sumTotalAmount',
		    width: 150,
			renderer: function(val) {
		       return Cmw.getThousandths(val)+"元";
		    }
		},
		{
		    header: '自有可用资金',
		    name: 'uamount',
		    width: 150,
			renderer: function(val) {
		       return Cmw.getThousandths(val)+"元";
		    }
		},
		{
		    header: '本金',
		    name: 'principal',
		    width: 150,
			renderer: function(val) {
		       return Cmw.getThousandths(val)+"元";
		    }
		},
		{
		    header: '利息',
		    name: 'interest',
		    width : 150,
			renderer: function(val) {
		       return Cmw.getThousandths(val)+"元";
		    }
		},
		{
		    header: '管理费',
		    name: 'mgrAmount',
		    width : 150,
			renderer: function(val) {
		       return Cmw.getThousandths(val)+"元";
		    }
		}];
		
			
	    
		var appgrid_1 = new Ext.ux.grid.AppGrid({
			tbar :this.toolBar,
		    structure: structure_1,
		    url: './fcFundReport_list.action',
		    needPage: true,
		    keyField: 'item',
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
			if(!params.day) params["day"] = 7;
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

