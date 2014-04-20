Ext.namespace("skythink.cmw.sys");
/**
 * 门店管理UI
 * @author cmw
 * @date 2012-11-02
 * */ 
skythink.cmw.sys.StoresMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.StoresMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreeGridView({
			tab : tab,
			params : params,
			TREE_WIDTH : 250,
			getAppGrid : this.getAppGrid,
			getToolBar : this.getToolBar,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			notify : this.notify,
			refresh : this.refresh
		});
	},
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var areaId=null;
		if(self.apptree){
			if(areaId.indexOf('T') != -1){
				areaId = areaId.substring(1);
			}
		}
		var barItems = [{
			type : 'label',
			text : Btn_Cfgs.STORE_NUMBER_LABEL_TEXT
		},{
			type : 'search',
			name : 'code',
			onTrigger2Click:function(){
//				var params = toolBar.getValues();
				var params={name:this.getValue(), areaId:areaId};
				EventManager.query(self.appgrid,params);
			}
		},{
			type : 'label',
			text : Btn_Cfgs.STORE_NAME_LABEL_TEXT
		},{
			type : 'search',
			name : 'name',
			onTrigger2Click:function(){
//				var params = toolBar.getValues();
				var params={name:this.getValue(),areaId:areaId};
				EventManager.query(self.appgrid,params);
			}
		},{
			token : "查询",
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				var params = toolBar.getValues();
				params.areaId=areaId;
				EventManager.query(self.appgrid,params);
			}
		},{
			token : "重置",
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				toolBar.resets();
			}
		},{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,self:self,me:this});
			}
		},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,optionType:OPTION_TYPE.EDIT,self:self,me:this});
			}
		},{type:"sp"},{
			token : "启用",
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./oaStores_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用",
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./oaStores_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./oaStores_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
	},
	
	/**
	 *  获取 按钮 Grid 对象
	 */
		getAppGrid : function(){
			var _this=this;
			var structure_1 = [{
			    header: 'ID',
			    hidden:true,
			    name: 'id'
			},{
			    header: '可用标志',
			    renderer:Render_dataSource.isenabledRender,
			    name: 'isenabled'
			},
			{
			    header: '门店编号',
			    name: 'code'
			},
			{
			    header: '店面名称',
			    name: 'name'
			},
			{
			    header: '店面地址',
			    name: 'address'
			},
			{
			    header: '门店属性',
			    renderer:Render_dataSource.stores_ptypeRender,
			    name: 'ptype'
			},
			{
			    header: '门店类型',
			    renderer:Render_dataSource.stores_stypeRender,
			    name: 'stype'
			},
			{
			    header: '门店区域',
			    name: 'areaName'
			},
			{
			    header: '店长',
			    name: 'dzName'
			},
			{
			    header: '区域经理',
			    name: 'mgrName'
			},
			{
			    header: '门店总监',
			    name: 'directorName'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    tbar:this.toolBar,
			    url: './oaStores_list.action',
			    height:300,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
	
		},

	/**
	 * 当新增、修改、删除、起用、禁用 
	 * 数据保存成功后会执行此方法刷新父页面
	 * @param optionType 操作类型 参考 constant.js 文件中的 "OPTION_TYPE" 常量值
	 * 	
	 * @param {} data 
	 * 	1).如果是 新增、修改表单数据保存成功的话. data 参数代表的是表单数据json对象
	 *  2).如果是 删除、起用、禁用 数据处理成功的话.data 参数是 ids 值。例如:{ids:'1001,1002,1003'}
	 */
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
//		this.globalMgr.query(this);
		this.appgrid.reload({areaId:this.globalMgr.selId});
//		this.globalMgr.activeKey = null;
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({
			url:'./sysTree_tdsList.action',
			isLoad : true,
			width:200,
			params:{restypeId:100002},
			enableDD:false,
			autoScroll :true
		});
		_apptree.expandAll(); 
		_apptree.addListener('click',function(node){
			var id = node.id;
			var areaId = -1;
			if(id.indexOf("G")==-1){
				if(id.indexOf('T') != -1){
					areaId = id.substring(1);
				}
				self.globalMgr.selId=areaId;
			EventManager.query(self.appgrid,{areaId:areaId});
			}
		});
		return _apptree;
	},
	globalMgr : {
		selId:null,
		openWin:function(cfg){
			var self=cfg.self;
			cfg.parent=self.appgrid;
			var fileName=null;
			if(cfg.key=="添加"){
				var tree=self.apptree;
				var selId=tree.getSelId();
				if(!selId){
					Ext.MessageBox.alert("提示","请从分类下选择分区!");
					return ;
				}
				if(selId.indexOf("G")!=-1){
					Ext.MessageBox.alert("提示","您选择的是分类，请选择分区!");					
				}
				if(selId.indexOf("T")!=-1){
					selId=selId.substring(1);				
				}
				cfg.selId=selId;
				cfg.selText=tree.getSelText();
				fileName="StoresAdd";
			}else{
				var selId=self.appgrid.getSelId();
				if(!selId) return ;
				cfg.selId=selId;
				var treId=self.apptree.getSelId().substring(1);
				cfg.treId=treId;
				fileName="StoresEdit";				
			}
			Cmw.importPackage('pages/app/sys/basis/stores/'+fileName,function(module) {
					 	module.WinEdit.show(cfg);
			  	});
		}
	}
});

