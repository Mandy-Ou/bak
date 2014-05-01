/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 基础数据 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.GvlistMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.GvlistMgr,Ext.util.MyObservable,{
	queryFields : null,
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreeGridView({
			tab : tab,
			params : params,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			notify : this.notify,
			refresh : this.refresh
		});
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token : '添加资源',
			text : Btn_Cfgs.RESTYPE_ADD_BTN_TXT,
			iconCls:Btn_Cfgs.RESTYPE_ADD_BTN_CLS,
			tooltip:Btn_Cfgs.RESTYPE_ADD_TIP_BTN_TXT,
			handler : function(){
					self.globalMgr.winEdit.show({key:"添加资源",self:self});
			}
		},{
			token : '编辑资源',
			text : Btn_Cfgs.RESTYPE_EDIT_BTN_TXT,
			iconCls:Btn_Cfgs.RESTYPE_EDIT_BTN_CLS,
			tooltip:Btn_Cfgs.RESTYPE_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑资源",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{type:"sp"},{
			token : "删除资源",
			text :Btn_Cfgs.RESTYPE_DEL_BTN_TXT,
			iconCls:Btn_Cfgs.RESTYPE_DEL_BTN_CLS,
			tooltip:Btn_Cfgs.RESTYPE_DEL_TIP_BTN_TXT,
			handler : function(){
				if(self.appgrid){
					if(self.appgrid.getStore().getCount()>0){
						ExtUtil.warn({msg:"该资源中有数据不能进行删除！"});
						return;
					}
				}
				self.globalMgr.activeKey = "删除资源";
				EventManager.deleteData('./sysRestype_delete.action',{type:'tree',cmpt:self.apptree,self:self});
			}
		},{
			token : '生成资源文件',
			text : Btn_Cfgs.RESTYPE_PRODUCED_BTN_TXT,
			iconCls:Btn_Cfgs.RESTYPE_PRODUCED_BTN_CLS,
			tooltip:Btn_Cfgs.RESTYPE_PRODUCED_TIP_BTN_TXT,
			handler : function(){
				 var progress = ExtUtil.progress({msg:Msg_SysTip.doSaveResFile}); //'正在生成资源文件...' doSaveResFile
				 EventManager.get('./sysRestype_savefile.action',{sfn:function(json_data){
				 	progress.hide();
				 	 Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_saveResourceSucess);
				 }});
			}
		},{
			type : 'label',
			text : Btn_Cfgs.GVLIST_LABEL_TEXT
		},{
			type : 'search',
			name : 'name',
			onTrigger2Click:function(){
				var params = toolBar.getValues();
				var name = params.name;
				var restypeId =self.globalMgr.restypeId;
				var isNotRoot = null;
				if(restypeId) {
					isNotRoot = restypeId.indexOf("root_");
				}
				if(isNotRoot != null && isNotRoot!=-1){
					ExtUtil.alert({msg:"请选择资源数据节点查询！"});
					return;
				}else{
					if(name=="" && (restypeId != null)){
						EventManager.query(self.appgrid,{restypeId: restypeId});
					}else if(name=="" && (restypeId == null) ){
						return;
					}else{
						EventManager.query(self.appgrid,{name : name});
					}
				}
			}
		},{
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				var params = toolBar.getValues();
				var name = params.name;
				var restypeId =self.globalMgr.restypeId;
				var isNotRoot = null;
				if(restypeId) {
					isNotRoot = restypeId.indexOf("root_")
				}
				if(isNotRoot != null && isNotRoot!=-1){
					ExtUtil.alert({msg:"请选择资源数据节点查询！"});
					return;
				}else{
					if(name=="" && (restypeId != null)){
						EventManager.query(self.appgrid,{restypeId: restypeId});
					}else if(name=="" && (restypeId == null) ){
						return;
					}else{
						EventManager.query(self.appgrid,{name : name});
					}
				}
			}
		},{type:"sp"},{
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:Btn_Cfgs.RESET_CLS,
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				toolBar.resets();
			}
		},{
			token : '添加数据',
			text : Btn_Cfgs.GVLIST_ADD_BTN_TXT,
			iconCls:Btn_Cfgs.GVLIST_ADD_BTN_CLS,
			tooltip:Btn_Cfgs.GVLIST_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加",self:self});
			}
		},{type:"sp"},{
			token : '编辑数据',
			text : Btn_Cfgs.GVLIST_EDIT_BTN_TXT,
			iconCls:Btn_Cfgs.GVLIST_EDIT_BTN_CLS,
			tooltip:Btn_Cfgs.GVLIST_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : '启用数据',
			text : Btn_Cfgs.GVLIST_ENABLED_BTN_TXT,
			iconCls:Btn_Cfgs.GVLIST_ENABLED_BTN_CLS,
			tooltip:Btn_Cfgs.GVLIST_ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./sysGvlist_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{type:"sp"},{
			token : '禁用数据',
			text : Btn_Cfgs.GVLIST_DISENABLED_BTN_TXT,
			iconCls:Btn_Cfgs.GVLIST_DISENABLED_BTN_CLS,
			tooltip:Btn_Cfgs.GVLIST_DISENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./sysGvlist_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : '删除数据',
			text : Btn_Cfgs.GVLIST_DEL_BTN_TXT,
			iconCls:Btn_Cfgs.GVLIST_DEL_BTN_CLS,
			tooltip:Btn_Cfgs.GVLIST_DEL_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./sysGvlist_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var self = this;
	  	var structure = [
	  		{header : '可用标识',name : 'isenabled',width:60,renderer : Render_dataSource.isenabledRender},
			{header : '编号',name : 'code',width:150},
			{header : '所属资源',name : 'rname',width:95},
			{header : '默认名称',name : 'name',width:95},
			{header : '英文名称',name : 'ename',width:90},
			{header : '日文名称',name : 'jname',width:100},
			{header : '台湾名称',name : 'twname',width:100},
			{header : '法文名称',name : 'fname',width:100},
			{header : '韩文名称',name : 'koname',width:100},
			{header : '菜单图标',name : 'biconCls',width:100}
			];
			
		var _appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			url : './sysGvlist_list.action',
			needPage : true,
			keyField : 'id',
			isLoad: false
		});
		return _appgrid;
	},
	/**
	 * 获取 TreePanel 对象
	 * @return {}
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_reslist.action',isLoad : true,
			rootText : '资源列表',width:200,enableDD:false,autoScroll : true});
		_apptree.addListener('click',function(node){
			var id = node.id;
			var isNotRoot = id.indexOf("root_")
			if(isNotRoot != -1){
				return ;
			}
			self.globalMgr.restypeId=id;
			EventManager.query(self.appgrid,{restypeId:id});
		});
		return _apptree
	},
	
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
			if(activeKey =="添加资源" || activeKey =="编辑资源"||activeKey == "删除资源"){
				this.apptree.reload();
			}else{
//				this.globalMgr.query(this);
				var id =this.globalMgr.restypeId ;
				EventManager.query(this.appgrid,{restypeId:id});
			}
		this.globalMgr.activeKey = null;
	},
	
		globalMgr : {
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		restypeId : null,
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = (winkey=="编辑") ? _this.appgrid : _this.apptree;
				parentCfg.parent = parent;
				var selId = parent.getSelId();
				if(parent == _this.apptree){
					var isNotRoot=null;
					if(selId!=null){
						isNotRoot = selId.indexOf("root_");
					}
					if((!selId && winkey != "添加资源") ||(isNotRoot !=-1 && winkey != "添加资源")){
						ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
						return;
					}
				}else{
					if(!selId && winkey == "编辑"){
						ExtUtil.alert({msg:"请选择表格中的数据！"});
						return;	
					}
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加资源"||winkey=="编辑资源"){
						winModule="RestypeEdit";
					}else if(winkey=="添加" || winkey=="编辑"){
						winModule="GvlistEdit";
					}
					Cmw.importPackage('pages/app/sys/gvlist/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		query : function(self){
			var selId  = self.appgrid.getSelId();
			var id = self.apptree.getSelId();
			EventManager.query(self.appgrid,{restypeId:id});
		}
	}
});

