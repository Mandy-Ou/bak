/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 还款方式公式设置 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.PayTypeMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.PayTypeMgr,Ext.util.MyObservable,{
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
		var cbo_isenabled = FormUtil.getLCboField({fieldLabel : '可标识',name:'isenabled',data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.isenabled_datas)});
		var txt_code = FormUtil.getTxtField({fieldLabel : '编号',name:'code'});
 		var txt_name = FormUtil.getTxtField({fieldLabel : '还款方式',name:'name'});
		var layout_fields = [{cmns:'THREE',fields:[cbo_isenabled,txt_code,txt_name]}]
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
		},
			{type:"sp"},{
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				self.queryFrm.reset();
			}
		},{
			token : '添加',
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:Btn_Cfgs.INSERT_CLS,
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加",self:self});
			}
		},{type:"sp"},{
			token : '编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:Btn_Cfgs.MODIFY_CLS,
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : '详情',
			text : Btn_Cfgs.DETAIL_BTN_TXT,
			iconCls:Btn_Cfgs.DETAIL_CLS,
			tooltip:Btn_Cfgs.DETAIL_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"详情",optionType:OPTION_TYPE.DETAIL,self:self});
			}
		}
		,{type:"sp"},{
			token : '起用',
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:Btn_Cfgs.ENABLED_CLS,
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./fcPayType_enabled.action',{cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{type:"sp"},{
			token : '禁用',
			text :  Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls: Btn_Cfgs.DISABLED_CLS,
			tooltip: Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./fcPayType_disabled.action',{cmpt:self.appgrid,self:self});
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./fcPayType_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
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
		var structure_1 = [{
		    header: '可用标识',
		    name: 'isenabled',
		    width : 60,
		    renderer : function(val){
		    	return Render_dataSource.isenabledRender(val);
		    }
		},
		{
		    header: '编号',
		    name: 'code',
		    width : 100
		},
		{
		    header: '还款方式',
		    name: 'name',
		    width: 200
		},
		{
		    header: '当期应还利息',
		    name: 'rateDispFormula',
		    width: 200,
		    hidden:true
		},
		{
		    header: '当期应还本金',
		    name: 'amoutDispFormula',
		    width: 200,
		    hidden:true
		},
		{
		    header: '当期还本付息',
		    name: 'raDispFormula',
		    width: 200,
		    hidden:true
		},
		{
		    header: '提前还款息费公式说明',
		    name: 'pdfa',
		    width: 225
		},
		{
		    header: '提前还款利息计算公式',
		    name: 'prfa',
		    width: 225
		},
		{
		    header: '备注',
		    name: 'remark',
		    width: 200
		}];
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    title: '还款方式列表',
		    tbar : this.toolBar,
		    structure: structure_1,
		    url: './fcPayType_list.action',
		    needPage: true,
		    isLoad: true,
		    keyField: 'id'
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
		activeKey : null,
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		},
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				if(winkey !== "添加"){
					var id = parent.getSelId();
					if(!id) return;
				}
				
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey == "详情"){
						winModule="PayTypeDetail";
					}else{
						winModule="PayTypeEdit";
					}
					
					Cmw.importPackage('pages/app/finance/fcinit/payType/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

