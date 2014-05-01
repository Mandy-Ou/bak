/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 抵押合同抵押合同查询 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.MortSearchMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.MortSearchMgr,Ext.util.MyObservable,{
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
		var self =this;
		var wd = 180;
		var Txt_code = FormUtil.getTxtField({fieldLabel : '抵押合同编号',name:'code',width:wd});
		var Txt_pleman = FormUtil.getTxtField({fieldLabel : '抵押人',name:'pleman',width:wd});
		var Txt_borrCode = FormUtil.getTxtField({fieldLabel : '借款合同号',name:'borrCode',width:wd});
		var Txt_assMan = FormUtil.getTxtField({fieldLabel : '借款人',name:'assMan',width:wd});
		var Txt_appAmount = FormUtil.getMoneyField({fieldLabel : '贷款金额',name:'appAmount',width:wd});
		var Txt_rate = FormUtil.getDoubleField({fieldLabel : '贷款利率',name:'rate',width:wd});
		var layout_fields = [{cmns:'THREE',fields:[Txt_code,Txt_borrCode,Txt_assMan,Txt_appAmount,Txt_rate,Txt_pleman]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
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
	getAppGrid : function(){
		var self = this;
		var wd = 150;
		var rwd = 80;
			var structure_1 = [{
			    header: '抵押合同编号',
			    name: 'code'
			},{
				header: '抵押人',
			    name: 'pleman',
			    width : 180
			},
			{
			    header: '借款合同号',
			    name: 'borrCode',
			    width : 180
			},
			{
			    header: '借款人',
			    name: 'assMan',
			    width : 200	
			},
			{
			    header: '贷款金额',
			    name: 'appAmount',
			     renderer: Render_dataSource.moneyRender
			},
			{
			    header: '贷款利率',
			    name: 'rate',
			     renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			    header: '合同签订日期',
			    name: 'sdate'
			},
			{
			    header: '贷款截止日期',
			    name: 'endDate'
			},
			{
			    header: '贷款起始日期',
			    name: 'startDate'
			},
			{
			    header: '权属编号',
			    name: 'ownerNums'
			},{
			    header: '合同中未涉及条款',
			    name: 'clause',
			    width : 200
			},{
			    header: '备注说明',
			    name: 'remark',
			    width : 250
			}];
			var appgrid = new Ext.ux.grid.AppGrid({
				tbar :self.toolBar,
			    structure: structure_1,
			    url: './fcMortContract_list.action',
			    needPage: true,
			    autoScroll:true,
			    keyField: 'id'
			});
			return appgrid;
	},
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		activeKey : null,
		appform : null,
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
			return params;
		},
		/**
		 * 导出Excel
		 * @param {} _this
		 */
		doExport : function(_this){
			var params = this.getQparams(_this);
			var token = _this.params.nodeId;
			EventManager.doExport(token,params);
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		}
	}
});
