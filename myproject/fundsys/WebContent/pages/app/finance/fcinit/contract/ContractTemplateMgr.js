/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 展期协议书查询 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.ExtContractSearchMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.ExtContractSearchMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,
			getAppCmpt : this.getAppCmpt,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			getAppGrid2 : this.getAppGrid2,
			getCenterPnl: this.getCenterPnl,
			destroyCmpts :this.destroyCmpts,
			changeSize : this.changeSize, 
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
		if(this.appPanel != null){
			this.appPanel.destroy();
			this.appPanel = null;
		}
	},
	/**
	 * 组装
	 * @return {}
	 */
	getAppCmpt : function(){
		var _this = this;
		_this.globalMgr.appgrid=_this.getAppGrid();
		_this.globalMgr.appgrid2=_this.getAppGrid2();
		var panelMain = new Ext.Panel({
			border : false,
			items:[_this.getQueryFrm(),_this.getToolBar(),_this.getCenterPnl()]
		});
		return panelMain;
	},
	getCenterPnl : function(){
		var _this = this;
		var centerPnl = new Ext.Panel({
			layout:'border',width : CLIENTWIDTH-130,height : screen.height-300,border : false,
			items :[
				_this.globalMgr.appgrid,_this.globalMgr.appgrid2
					]
		});
		return centerPnl;
	},
	changeSize : function(whArr){
		var width = whArr[0]-2;
		var height = whArr[1]-3;
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		
		var txt_code = FormUtil.getTxtField({fieldLabel : '模板编号',name:'code',width:150});
		var txt_name = FormUtil.getTxtField({fieldLabel: '模板名称',name: 'name',width : 150});
		
		var cbo_custType = FormUtil.getLCboField({
		    fieldLabel: '客户类型',
		    name: 'custType',
		    width : 150,
		    data : Lcbo_dataSource.getAllDs(Lcbo_dataSource.custType_datas)
		});
		
		var layout_fields = [{cmns:'THREE',fields:[txt_code,txt_name,cbo_custType]}];
		var queryFrm = FormUtil.createLayoutFrm({region:'north'},layout_fields);
		this.globalMgr.queryFrm = queryFrm;
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
			token : '添加',
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.text,optionType:OPTION_TYPE.ADD,self:self});
			}
		},{
			token : '编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.text,optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{/*配置模板*/
			token : '配置模板',
			text : Btn_Cfgs.TEMP_COFIG_BTN_TXT,
			iconCls:Btn_Cfgs.TEMP_COFIG_CLS,
			handler : function(){
				self.globalMgr.viewCustomerDetail({key:this.text,self:self});
			}
		},{/*预览模板*/
			token : '预览模板',
			text : Btn_Cfgs.TEMP_VIEW_BTN_TXT,
			iconCls:Btn_Cfgs.TEMP_VIEW_CLS,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.text,self:self});
			}
		},{type:"sp"},{
			token : '起用',
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./fcPrintTemp_enabled.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : '禁用',
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./fcPrintTemp_disabled.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./fcPrintTemp_delete.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		toolBar.hideButtons(Btn_Cfgs.EXPORT_IMPORT_BTN_TXT);
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
			var structure_1 = [{
			    header: '可用标识',
			    name: 'isenabled',width:60,
			    renderer : Render_dataSource.isenabledRender
			},{
			    header: '模板编号',
			    name: 'code'
			},{
			    header: '模板类型',
			    name: 'tempType',
			     renderer: function(val) {
			        switch (val) {
			        case '1':
			            val = '合同模板';
			            break;
			        case '2':
			        	val="通知书模板";
			        	break;
			        default:
			        	val="无限制";
			        }
			        return val;
			    }
			},
			{
			    header: '模板名称',
			    name: 'name'
			},
			{
			    header: '适用客户类型',
			    name: 'custType',
			    renderer: function(val) {
			        switch (val) {
			        case '0':
			            val = '个人客户';
			            break;
			        case '1':
			        	val="企业客户";
			        	break;
			        default:
			        	val="无限制";
			        }
			        return val;
			    }
			},{
			    header: '模板路径',
			    name: 'tempPath'
			},{
			    header: '创建人',
			    name: 'creator'
			},
			{
			    header: '创建日期',
			    name: 'createTime',
			    renderer: function(val) {
			        return val;
			    }
			},{
			    header: '说明',
			    name: 'remark'
			}
			];
			var appgrid = new Ext.ux.grid.AppGrid({
				title : '模板列表',
				tbar :self.toolBar,
			    structure: structure_1,
			    url: './fcPrintTemp_list.action',
			    needPage: true,
			     width : (CLIENTWIDTH+150)/2,
			     region: 'west',
			    height : 150,
			    isLoad: true,
			    keyField: 'id',
			     listeners :{
			    	'render':function(){
			    		_this.globalMgr.getQparams(_this);
			    	}
			    }
			});
			appgrid.addListener("rowclick",function(appgrid,rowIndex,event){
		   var record = appgrid.getStore().getAt(rowIndex);
		   if(record==null){
		   		return;
		   }
		   var id = appgrid.getSelId();
			_this.globalMgr.appgrid2.reload({tempId:id,bussType:1});
		});
			_this.globalMgr.appgrid = appgrid;
		return appgrid;
	},
	getAppGrid2 : function(){
		var _this = this;
		var structure_1 = [{
		    header: '功能标识码',
		    name: 'funTag'},
			{    
			header: '关联菜单菜单',
	    	name: 'name'
			},
			{    
			header: '说明',
	    	name: 'remark'
			}];
		var appgrid = new Ext.ux.grid.AppGrid({
			title:'模板关联功能列表',
		    structure: structure_1,
		    url: './fcFuntempCfg_list.action',
		    needPage: true,
		    region: 'center',
		    isLoad: false,
		    keyField: 'id'
		});
		_this.globalMgr.appgrid2 = appgrid;
		 return appgrid;
	},
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		width : null,
		height :null,
		detailPanel_1 : null,
		queryFrm : null,
		appgrid2 :null,
		appgrid : null,
		activeKey : null,
		toolBar : null,
		appform : null,
		activeKey : null,
		getQparams : function(_this){
			var params = _this.globalMgr.queryFrm.getValues() || {};
			return params;
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.globalMgr.getQparams(_this);
			if(params) {
				EventManager.query(_this.globalMgr.appgrid,params);
			}
		},
		/**
			 * 打开模板配置窗口
			 * @param {} self
			 */
			viewCustomerDetail:function(parentCfg){
				var _this=parentCfg.self;
				var parent = _this.globalMgr.appgrid;
				parentCfg.tempId=parent.getSelId();
				if(!parentCfg.tempId)return;
				parentCfg.parent=parent;
				Cmw.importPackage('pages/app/finance/fcinit/contract/ContractTempConfig',function(module) {
					 	module.WinEdit.show(parentCfg);
			  		});
			},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		msg :null,
		activeKey : null,
		winEdit : {
			/**
			 * 添加、编辑、预览模板
			 * @param {} parentCfg
			 */
			show : function(parentCfg){
				var _this =  parentCfg.self;
					var winModule=null;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.globalMgr.appgrid;
				parentCfg.params = _this.params;
				var selId=null;
				if(winkey==Btn_Cfgs.MODIFY_BTN_TXT){
					parentCfg.parent=parent;
					selId=parent.getSelId();
					if(!selId)return;
				}
				winModule="ContractTemplateEdit";
				if(winkey==Btn_Cfgs.TEMP_VIEW_BTN_TXT){
					parentCfg.parent=parent;
					selId=parent.getSelId();
					if(!selId)return;
					winModule="ContracTempPreview";
				}
					Cmw.importPackage('pages/app/finance/fcinit/contract/'+winModule,function(module) {
						_this.appCmpts[winkey]= module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
		}
		
		
	}
});
