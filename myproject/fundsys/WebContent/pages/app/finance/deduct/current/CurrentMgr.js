Ext.namespace("cmw.skythink");
/**
 * 随借随还还款管理
 */
cmw.skythink.CurrentMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.CurrentMgr,Ext.util.MyObservable,{
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
		})
	},
	/**
	 * 创建查询面板
	 */
	getQueryFrm : function(){
			var _this =this;
		 var rad_custType = FormUtil.getRadioGroup({fieldLabel : '客户类型', name:'custType',
			items : [{boxLabel : '个人', name:'custType',inputValue:0},{boxLabel : '企业', name:'custType',inputValue:1}]});

		var txt_custName = FormUtil.getTxtField({fieldLabel : '客户名称',name:'name',width : 150});
		
		var txt_code = FormUtil.getTxtField({
		    fieldLabel: '借款合同号',
		    name : 'code',
		    width : 150
		});
		
		var txt_payName = FormUtil.getTxtField({
		    fieldLabel: '还款人名称',
		    name: 'accName',
		    width: 150
		});
	
		var txt_payBank = FormUtil.getTxtField({
		    fieldLabel: '还款银行',
		    name: 'payBank',
		     width: 150
		});
		
		var txt_payAccount = FormUtil.getTxtField({
		    fieldLabel: '还款帐号',
		    name: 'payAccount',
		     width: 150
		});
		var layout_fields = [{cmns:'THREE',fields:[rad_custType,txt_custName,txt_code,txt_payName,txt_payBank,txt_payAccount]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		
		return queryFrm;
	},
	/**
	 * 创建工具栏
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
		},{type:"sp"},{/*收款*/
			token : '收款',
			text : Btn_Cfgs.DO_NOMAL_BTN_TXT,
			iconCls:Btn_Cfgs.DO_NOMAL_CLS,
			tooltip:Btn_Cfgs.DO_NOMAL_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'收款'});
			}
		}/*,{type:"sp"},{
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:Btn_Cfgs.EXPORT_CLS,
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doExport(_this);
			}
		}*/
		];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 创建表格个面板
	 */
	getAppGrid : function(){
		var _this = this;
		var structure_1 = [{header: '借款合同号', name: 'code'},
		{
		    header: '客户类型',
		    name: 'custType',
		    width: 60,
		    renderer : function(val){
		    	return Render_dataSource.custTypeRender(val);
		    }
		},
		{
		    header: '客户名称',
		    name: 'name'
		},
		{
		    header: '贷款金额',
		    name: 'appAmount',
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '还款银行',
		    name: 'payBank',
		    width : 125
		},
		{
		    header: '还款帐号',
		    name: 'payAccount',
		    width : 125
		},
		{
		    header: '还款人',
		    name: 'accName',
		    width: 80
		},
		{
		    header: '已还期数',
		    name: 'paydPhases',
		    width: 55
		},
		{
		    header: '总期数',
		    name: 'totalPhases',
		    width: 45
		},{
		    header: '累计逾期',
		    name: 'totalOverPharses',
		    width: 60
		},
		{
		    header: '应还本金',
		    name : 'appAmount',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实还本金',
		    name: 'yprincipal',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '应还利息',
		    name: 'interest',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实还利息',
		    name: 'yinterest',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '应还管理费',
		    name: 'mgrAmount',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实管理费',
		    name: 'ymgrAmount',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '应收罚息',
		    name: 'penAmount',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实收罚息',
		    name: 'ypenAmount',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '应收滞纳金',
		    name: 'delAmount',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实收滞纳金',
		    name: 'ydelAmount',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实收合计',
		    name: 'ytotalAmount',
		    width: 75,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '还款状态',
		    name: 'state',
		    width: 75,
		     renderer : function(val){return Render_dataSource.applyStateRender(val);}
		},
		{
		    header: '贷款期限',
		    name: 'loanLimit'
		},
		{
		    header: '合同ID',
		    name: 'contractId',
		    hideable : true,
		    hidden : true
		}];
	
		var continentGroupRow = [{header: '合同信息', colspan: 5, align: 'center'},
			{header: '还款帐号信息', colspan: 3, align: 'center'},
			{header: '还款期数', colspan: 3, align: 'center'},
			{header: '本金', colspan: 2, align: 'center'},
			{header: '利息', colspan: 2, align: 'center'},
			{header: '管理费', colspan: 1, align: 'center'},
			{header: '罚息', colspan: 2, align: 'center'},
			{header: '滞纳金', colspan: 2, align: 'center'},
			{header: '实收合计', colspan: 1, align: 'center'},
			{header: ' ', colspan: 3, align: 'center'}];
		 var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [continentGroupRow]
	    });
		var appgrid_1 = new Ext.ux.grid.AppGrid({
			tbar :_this.toolBar,
		    structure: structure_1,
		    url: './fcCurrent_list.action',
		    needPage: true,
		    keyField: 'contractId',
		    isLoad: true,
		    plugins: [group],
		    listeners : {
			   	render : function(grid){
			   		 _this.globalMgr.query(_this);
			   	}
		    }
		});
		return appgrid_1;
	},
	/**
	 * 刷新数据
	 */
	refresh:function(optionType,data){
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr :{
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		sysId  : this.params.sysid,
		activeKey : null,
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
			/*-- 附加桌面传递的参数  CODE START --*/
			if(_this.params && _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY]){
			 	var deskParams = _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY];
			 	if(deskParams){
			 		Ext.applyIf(params,deskParams);
			 		 _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY] = null;
			 	}
			}/*-- 附加桌面传递的参数  CODE END --*/
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
		},
		winEdit :{
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent = _this.appgrid;
				parent.sysId = _this.globalMgr.sysId;
				parentCfg.parent = parent;
				var winModule = null;
				var bussKey = LockManager.keys.CurrentKey;/*随借随还锁*/
				parentCfg.bussKey = bussKey;
				LockManager.applyLock(bussKey);
				LockManager.isLock(parent,"name",function(){
					var selIds = parent.getSelIds();
					if(!selIds){
						return;
					}
					winModule = "CurrentEdit";
					showWindow();
				});
				function showWindow(){
				if(_this.appCmpts[winkey]){
						_this.appCmpts[winkey].show(parentCfg);
					}else{ 
						Cmw.importPackage('pages/app/finance/deduct/current/'+winModule,function(module) {
						 	_this.appCmpts[winkey] = module.WinEdit;
						 	_this.appCmpts[winkey].show(parentCfg);
				  		});
					}	
				}
			}
			
		}
	}
});