Ext.namespace("cmw.skythink");
/**
 * 逾期还款金额收取流水
 */ 
cmw.skythink.OverdueOverMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.OverdueOverMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : tab.params,
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
		    name: 'payName',
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
		var cfg = {afterrender : function(frm){
			    		_this.globalMgr.query(_this);
			    }};
		var queryFrm = FormUtil.createLayoutFrm(cfg,layout_fields);
		
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
			}},{type:"sp"},{/*查看详情*/
			token : '查看详情',
			text : Btn_Cfgs.DETAIL_BTN_TXT,
			iconCls:Btn_Cfgs.DETAIL_CLS,
			tooltip:Btn_Cfgs.DETAIL_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'详情'});
			}},{/*导出*/
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:Btn_Cfgs.EXPORT_CLS,
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doExport(_this);
			}}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
		/**
	 * 获取Grid 对象
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
		    name: 'accName'
		},
		{
		    header: '贷款金额',
		    name: 'appAmount',
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '还款银行',
		    name: 'payBank'
		},
		{
		    header: '还款帐号',
		    name: 'payAccount'
		},
		{
		    header: '还款人',
		    name: 'accName',
		    width: 80
		},
		{
		    header: '本金',
		    name : 'amounts',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '利息',
		    name: 'iamounts',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '管理费',
		    name: 'mamounts',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '罚息',
		    name: 'pamounts',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '滞纳金',
		    name: 'damounts',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '应收合计',
		    name: 'totalAmounts',
		    width: 75,
		    renderer : function(val){return Cmw.getThousandths(val);}
		}
		];
		
		var continentGroupRow = [{header: '合同信息', colspan: 6, align: 'center'},
			{header: '还款帐号信息', colspan: 3, align: 'center'},
			{header: '累计应收款(元)', colspan: 6, align: 'center'}
			];
		 var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [continentGroupRow]
	    });
		var appgrid_1 = new Ext.ux.grid.AppGrid({
			tbar :_this.toolBar,
		    structure: structure_1,
		    url: './fcOverdueDeduct_RepDetail.action',
		    needPage: true,
		    keyField: 'id',
		    selectType : "check",
		    plugins: group,
		    isLoad: false,
		    listeners : {
		    	render : function(frm){
			    		_this.globalMgr.query(_this);
			    }, 	rowdblclick : function() {
							//鼠标双击事件
							var selRow = appgrid_1 .getSelRow();//获取对象所在的行
							if(selRow!=null){
							_this.globalMgr.winEdit.show({self : _this,key : '详情'});
							}
		}
		    },
		    gatherCfg : {
		    	gatherOffset : "code",
		    	gatherCmns : [{cmn:'appAmount',dp:2},
		    		{cmn:'amounts',dp:2},
		    		{cmn:'iamounts',dp:2},
		    		{cmn:'mamounts',dp:2},
		    		{cmn:'pamounts',dp:2},
		    		{cmn:'damounts',dp:2},
		    		{cmn:'totalAmounts',dp:2}]
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
			params.excelType = 2;
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
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent = _this.appgrid;
				parent.sysId  = _this.globalMgr.sysId;
				parentCfg.parent = parent;
				var winModule=null;
				if(winkey && winkey == '详情'){
					if(!parent.getSelId())return;
					winModule = "OverdurOverDetail";
					showWindow();
				}
				
				function showWindow(){
					if(_this.appCmpts[winkey]){
						if(!parent.getSelId())return;
						_this.appCmpts[winkey].show(parentCfg);
					}else{ 
						if(!parent.getSelId())return;
						Cmw.importPackage('pages/app/finance/deduct/turnover/overdueover/'+winModule,function(module) {
						 	_this.appCmpts[winkey] = module.WinEdit;
						 	_this.appCmpts[winkey].show(parentCfg);
				  		});
					}
				}
			}
		}
	}
});
