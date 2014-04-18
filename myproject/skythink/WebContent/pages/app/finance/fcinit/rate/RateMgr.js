/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 利率设置 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.RateMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.RateMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
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
		var txt_isenabled = FormUtil.getLCboField({fieldLabel : '可用标识',name:'isenabled',data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.isenabled_datas)});
 		var txt_type = FormUtil.getLCboField({fieldLabel : '利率类型',name:'types',data:Lcbo_dataSource.getAllDs(Lcbo_dataSource.rate_types_datas)});
		var layout_fields = [{cmns:'THREE',fields:[txt_isenabled,txt_type]}]
		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
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
				self.queryFrm.reset();
			}
		},{type:"sp"},{
			token : '添加',
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:Btn_Cfgs.INSERT_CLS,
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加利率",self:self});
			}
		},{
			token : '编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:Btn_Cfgs.MODIFY_CLS,
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				var id = self.appgrid.getSelId();
				if(!id) return;
				self.globalMgr.winEdit.show({key:"编辑利率",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{type:"sp"},{
			token : '起用',
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./fcRate_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{type:"sp"},{
			token : '禁用',
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./fcRate_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./fcRate_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}
		/*,{type:"sp"},{
			token : "添加公式",
			text : Btn_Cfgs.FORMULA_ADD_BTN_TXT,
			iconCls:Btn_Cfgs.FORMULA_ADD_BTN_CLS,
			tooltip:Btn_Cfgs.FORMULA_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加公式",self:self});
			}
		},{
			token : "编辑公式",
			text : Btn_Cfgs.FORMULA_EDIT_BTN_TXT,
			iconCls:Btn_Cfgs.FORMULA_EDIT_BTN_CLS,
			tooltip:Btn_Cfgs.FORMULA_EDIE_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑公式",optionType:OPTION_TYPE.EDIT,self:self});
			}
		}
		,{type:"sp"},{
			token : "起用公式",
			text : Btn_Cfgs.FORMULA_ENABLED_BTN_TXT,
			iconCls:Btn_Cfgs.FORMULA_ENABLED_BTN_CLS,
			tooltip:Btn_Cfgs.FORMULA_ENABLED_TIP_BTN_TXT,
			handler : function(){
				var rowid=self.appgrid.getSelectionModel().getSelected();//获取一行的所有值
				var isFormula= rowid.get("isFormula");
				var formulaId= rowid.get("formulaId");
				if(formulaId == null||formulaId==""){
					ExtUtil.alert({msg:"没有公式不能起用！"});
					return;
				}
				if(isFormula==2){
					ExtUtil.alert({msg:"已经是起用状态！"});
					return;
				}
				EventManager.enabledData('./fcFormula_enabled.action',{cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用公式",
			text : Btn_Cfgs.FORMULA_DISABLED_BTN_TXT,
			iconCls:Btn_Cfgs.FORMULA_DISABLED_BTN_CLS,
			tooltip:Btn_Cfgs.FORMULA_DISABLED_TIP_BTN_TXT,
			handler : function(){
				var rowid=self.appgrid.getSelectionModel().getSelected();//获取一行的所有值
				var isFormula= rowid.get("isFormula");
				var formulaId= rowid.get("formulaId");
				if(formulaId == null||formulaId==""){
					ExtUtil.alert({msg:"没有公式不能禁用！"});
					return;
				}
				if(isFormula==0){
					ExtUtil.alert({msg:"已经是禁用状态！"});
					return;
				}
				EventManager.disabledData('./fcFormula_disabled.action',{cmpt:self.appgrid,self:self});
			}
		}*/];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
			var structure_1 = [{
		    header: '可用标识',
		    name: 'isenabled',
		    width: 60,
		    renderer: Render_dataSource.isenabledRender
			},
			{
			    header: '编号',
			    name: 'code',
			    width: 135
			},{
			    header: '利率类型',
			    name: 'types',
			    width: 125,
			    renderer: Render_dataSource.rate_typesRender
			},
			{
			    header: '利率期限',
			    name: 'limits',
			    width: 125,
			    renderer: Render_dataSource.rate_limitsRender
			},
			{
			    header: '利率值(%)',
			    name: 'val'
			},
			{
			    header: '利率生效日期',
			    name: 'bdate'
			},
			{
			    header: '是否启用公式',
			    name: 'isFormula',
			    width: 125,
			    hidden : true,
			    hideable : true,
			    renderer: Render_dataSource.rate_isFormulaRender
			},
			{
			    header: '公式ID',
			    name: 'formulaId',
			    hidden : true,
			    hideable : true
			},{
		    header: '备注',
		    name: 'remark',
		    width: 350
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '利率设置',
			    tbar : this.toolBar,
			    structure: structure_1,
			    url: './fcRate_list.action',
			    needPage: true,
			    keyField: 'id',
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
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			Cmw.print(params);
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		},
			
		isFormula :null,
		
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加公式"||winkey=="编辑公式"){
						winModule="sys/formula/FormulaEdit";
					}
					else if(winkey=="添加利率" || winkey=="编辑利率"){
						winModule="finance/fcinit/rate/RateEdit";
					}
					Cmw.importPackage('pages/app/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

