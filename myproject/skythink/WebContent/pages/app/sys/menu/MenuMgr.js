Ext.namespace("skythink.cmw.sys");
/**
 * 菜单管理UI
 * @author cmw
 * @date 2012-11-02
 * */ 
skythink.cmw.sys.menuMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.menuMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreeGridView({
			tab : tab,
			params : params,
			hasTopSys : true,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			notify : this.notify,
			refresh : this.refresh
		});
	},
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token : "新增菜单",
			text : Btn_Cfgs.MENU_ADD_BTN_TXT,
			iconCls:Btn_Cfgs.MENU_ADD_BTN_CLS,
			tooltip:Btn_Cfgs.MENU_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"新增菜单",self:self});
			}
		},{
			token : "修改菜单",
			text : Btn_Cfgs.MENU_EDIT_BTN_TXT,
			iconCls:Btn_Cfgs.MENU_EDIT_BTN_CLS,
			tooltip:Btn_Cfgs.MENU_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"修改菜单",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : "菜单详情",
			text : Btn_Cfgs.MENU_detail_BTN_TXT,
			iconCls:Btn_Cfgs.MENU_detail_BTN_CLS,
			tooltip:Btn_Cfgs.MENU_detail_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"菜单详情",optionType:OPTION_TYPE.DETAIL,self:self});
			}
		},{type:"sp"},{
			token : "起用菜单",
			text : Btn_Cfgs.MENU_ENABLED_BTN_TXT,
			iconCls:Btn_Cfgs.MENU_ENABLED_BTN_CLS,
			tooltip:Btn_Cfgs.MENU_ENABLED_TIP_BTN_TXT,
			handler : function(){
  				EventManager.enabledData('./sysMenu_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用菜单",
			text : Btn_Cfgs.MENU_DISABLED_BTN_TXT,
			iconCls:Btn_Cfgs.MENU_DISABLED_BTN_CLS,
			tooltip:Btn_Cfgs.MENU_DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./sysMenu_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除菜单",
			text : Btn_Cfgs.MENU_DEL_BTN_TXT,
			iconCls:Btn_Cfgs.MENU_DEL_BTN_CLS,
			tooltip:Btn_Cfgs.MENU_DEL_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.activeKey = "删除菜单";
				EventManager.deleteData('./sysMenu_delete.action',{type:'tree',cmpt:self.apptree,self:self});
			}
		},{type:"sp"},{
			token : "新增按钮",
			text : Btn_Cfgs.MODULE_ADD_BTN_TXT,
			iconCls:Btn_Cfgs.MODULE_ADD_BTN_CLS,
			tooltip:Btn_Cfgs.MODULE_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"新增按钮",self:self});
			}
		},{
			token : "修改按钮",
			text : Btn_Cfgs.MODULE_EDIT_BTN_TXT,
			iconCls:Btn_Cfgs.MODULE_EDIT_BTN_CLS,
			tooltip:Btn_Cfgs.MODULE_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"修改按钮",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : "删除按钮",
			text : Btn_Cfgs.MODULE_DEL_BTN_TXT,
			iconCls:Btn_Cfgs.MODULE_DEL_BTN_CLS,
			tooltip:Btn_Cfgs.MODULE_DEL_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./sysModule_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppGrid : function(){
		var self = this;
	  	var structure = [
			{header : '所属菜单',name : 'menuId',width:100},
			{header : '按钮编码',name : 'code',width:95},
			{header : '按钮名称',name : 'name',width:150},
			{header : '按钮样式',name : 'iconCls',width:100},
			{header : '功能描述',name : 'remark',width:300}
			];
			
		var _appgrid = new Ext.ux.grid.AppGrid({
			title : '按钮列表',
			tbar : this.toolBar,
			structure : structure,
			url : './sysModule_list.action',
			needPage : false,
			isLoad : false,
			keyField : 'id'
		});
		return _appgrid;
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
		var sysId = this.globalMgr.sysid;
		var nodeId = this.apptree.getSelId();
		if(nodeId.indexOf("C")!=-1 || nodeId.indexOf("m")!=-1 ){
			nodeId = nodeId.substr(1);
		}
		if(activeKey){
			if(activeKey == "新增菜单"){
				var cfg = {id : data.id,text : data.name};//获得id和name
				if(data.iconCls) cfg = data.iconCls;
				this.apptree.addChild2CurNode(cfg);
				this.apptree.reload({sysid:sysId});
				this.apptree.expandAll();
			}else if(activeKey == "修改菜单"){
				var curNode = this.apptree.curNode;
				var opTxt = Msg_AppCaption.disabled_text;
				var text =  curNode.text;
				var name = data.name;
				if(text.indexOf(opTxt)!=-1 && name){
					name = name + opTxt;
				}
				curNode.setText(name);
				if(data.iconCls) curNode.setIcon(data.iconCls);
			}else if(activeKey == "删除菜单"){
				this.apptree.curNode.remove();
				this.apptree.reload({sysid:sysId});
			}else{//新增/修改按钮刷新
						this.appgrid.reload({menuId:nodeId});	
			}
//			this.appgrid.reload({menuId:nodeId});
		}else{
			switch(optionType){
				case OPTION_TYPE.DISABLED: /* 禁用  */
				case OPTION_TYPE.ENABLED:{ /* 起用  */
					var opTxt = Msg_AppCaption.disabled_text;
					var ids = data.ids;
					ids = ids.split(",");
					for(var i=0,count=ids.length; i<count; i++){
						var node = this.apptree.getNodeById(ids[i]);
						var nodeTxt = node.text;
						if(OPTION_TYPE.DISABLED == optionType){
							nodeTxt+=opTxt;
						}else{//可用时,替换"【已禁用】"
							nodeTxt = nodeTxt.replace(opTxt,"");
						}
						node.setText(nodeTxt);
					}
					break;
				}case OPTION_TYPE.DEL:{ /* 删除按钮刷新  */
					this.appgrid.reload({menuId:nodeId});
					break;
				}default:{
					this.apptree.reload();
				}
			}
		}
		this.globalMgr.activeKey = null;
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_list.action?action=1',
				isLoad : false,width:200,enableDD:false,autoScroll :true});
		_apptree.addListener({'click' : function(node,e){//加入一个单击事件
			var nodeId = node.id+"";
			var leaf = node.leaf;
			var menuId = -1;
			if((nodeId && (nodeId.indexOf('C') == -1 && nodeId.indexOf('root_') == -1)) && leaf){
				menuId = nodeId;
			}
			self.appgrid.reload({menuId:menuId});
		}});
		return _apptree;
	},
	/**
	 * 当点击顶部的 系统图标 时，会触发 notify 方法。
	 * @param {} data 选中的 系统图标数据。
	 * 			{id : '',name:'',code:'',url:''} 等。
	 * 			具体值请看 SystemEntity 的 getFields() 方法
	 */
	notify : function(data){
		var sysId = data.id;
		this.globalMgr.sysid=sysId;
		this.apptree.reload({sysid:sysId});
	},
	globalMgr : {
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		sysid:null,
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent =(winkey=="修改按钮") ? _this.appgrid : _this.apptree;
				parentCfg.parent = parent;
				var selId = parent.getSelId();
				if(!selId && winkey!="新增菜单"||(!selId && winkey=="新增菜单")){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
					return;
				}
				selId+="";
				if(selId.indexOf('C') != -1 && winkey!="新增菜单"){	//如果是选择的是卡片节点
					ExtUtil.alert({msg:Msg_SysTip.msg_noMenuNode});
					return;
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="新增菜单"||winkey=="修改菜单"){
						winModule="MenuEdit";
					}else if(winkey=="新增按钮"||winkey=="修改按钮"){
						winModule="ModuleEdit";
					}if(winkey=="菜单详情"){
						winModule="MenuDetail";
					}
					Cmw.importPackage('pages/app/sys/menu/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
					 	if(winkey=="菜单详情"){
					 		_this.globalMgr.activeKey  = null;
					 	}
			  		});
				}
			}
		}
		
	}
});

