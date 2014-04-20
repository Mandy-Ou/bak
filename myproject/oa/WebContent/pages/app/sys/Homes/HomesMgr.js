Ext.namespace("skythink.cmw.sys");
/**
 * 组架机构管理 UI
 * @author 彭登浩
 * @date 2012-11-02
 * */ 
skythink.cmw.sys.HomesMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.HomesMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreePanelView({
			TREE_WIDTH : 300,
			tab:tab,
			params:params,
			getToolBar : this.getToolBar,
			getAppPanel : this.getAppPanel,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token :'添加',
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加",optionType:OPTION_TYPE.ADD,self:self});
			}
		},{
			token :'编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token :'设为默认',
			text : Btn_Cfgs.HOMES_DEFAULT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.HOMES_Default_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.isdefault ="设为默认";
				var curNode = self.apptree.getCurNode();
				if(curNode){
					var nodeTxt = curNode.text;
					var isNotMr = nodeTxt.indexOf("默认");
					if(isNotMr!=-1){
						 ExtUtil.alert({msg:'该节点已经是默认状态！'});
						 return;
					}
				}
				var noid =self.globalMgr.noid;
				var subid = noid.substring(0,1);
				var id = noid.substring(1);
				var name= self.apptree.getSelText();
				if(subid=="C"){
					ExtUtil.confirm({title:'系统提示',msg:'确定把'+name+"设置为默认的国家",fn:btuclick});
				}else if(subid=="P"){
					ExtUtil.confirm({title:'系统提示',msg:'确定把'+name+"设置为默认的省份",fn:btuclick});
				}else if(subid=="Y"){
					ExtUtil.confirm({title:'系统提示',msg:'确定把'+name+"设置为默认的城市",fn:btuclick});
				}else if(subid=="R"){
					ExtUtil.confirm({title:'系统提示',msg:'确定把'+name+"设置为默认的地区",fn:btuclick});
				}
				function btuclick(){
					if(arguments && arguments[0] == 'yes'){
						if(subid=="C"){
						 	EventManager.get('./sysCountry_isdefault.action?id='+id,{sfn:function(json_data){self.refresh(OPTION_TYPE.ISDEFAULT,json_data);}});
						}else if(subid=="P"){
							EventManager.get('./sysProvince_isdefault.action?id='+id,{sfn:function(json_data){self.refresh(OPTION_TYPE.ISDEFAULT,json_data);}});
						}else if(subid=="Y"){
							EventManager.get('./sysCity_isdefault.action?id='+id,{sfn:function(json_data){self.refresh(OPTION_TYPE.ISDEFAULT,json_data);}});
						}else if(subid=="R"){
							EventManager.get('./sysRegion_isdefault.action?id='+id,{sfn:function(json_data){self.refresh(OPTION_TYPE.ISDEFAULT,json_data);}});
						}
					} else{
						return;
					}
				};}
		},{
			token :'启用',
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				var noid =self.globalMgr.noid;
				var subid = noid.substring(0,1)
				if(subid=="C"){
					EventManager.enabledData('./sysCountry_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
				}else if(subid=="P"){
					EventManager.enabledData('./sysProvince_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
				}else if(subid=="Y"){
					EventManager.enabledData('./sysCity_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
				}else if(subid=="R"){
					EventManager.enabledData('./sysRegion_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
				}
			}
		},{
			token : '禁用',
			text :Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				var noid =self.globalMgr.noid;
				var subid = noid.substring(0,1)
				if(subid=="C"){
					EventManager.disabledData('./sysCountry_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
				}else if(subid=="P"){
					EventManager.disabledData('./sysProvince_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
				}else if(subid=="Y"){
					EventManager.disabledData('./sysCity_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
				}else if(subid=="R"){
					EventManager.disabledData('./sysRegion_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
				}
			}
		},{type:"sp"},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				var noid =self.globalMgr.noid;
				var lf= self.apptree. getCurNode().hasChildNodes();
				var name= self. apptree. getSelText();
				var subid = noid.substring(0,1)
				if(lf){
					ExtUtil.alert({msg:name+"不是子节点不能删除"});
				}else if(subid=="C" && !lf){
					EventManager.deleteData('./sysCountry_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
				}else if(subid=="P"&& !lf){
					EventManager.deleteData('./sysProvince_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
				}else if(subid=="Y"&& !lf){
					EventManager.deleteData('./sysCity_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
				}else if(subid=="R"&& !lf){
					EventManager.deleteData('./sysRegion_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
				}
		}}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppPanel : function(){
		var self = this;
		return new Ext.Panel();
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
		var noid =this.globalMgr.noid;
		var subid = noid.substring(0,1);
		if(activeKey && optionType != OPTION_TYPE.ISDEFAULT){
			activeKey=activeKey+subid;
			if(activeKey == "添加r"||activeKey == "添加C"||activeKey == "添加P"||activeKey == "添加Y"||activeKey == "添加R"){
				var icon = null;
				if (activeKey == "添加r"){
					icon = "extlibs/ext-3.3.0/resources/images/icons/world.png";
				}else if(activeKey == "添加C"){
					icon = "extlibs/ext-3.3.0/resources/images/icons/award_star_gold_3.png";
				}else if(activeKey == "添加P"){
					icon = "extlibs/ext-3.3.0/resources/images/icons/rosette.png";
				}else if(activeKey == "添加Y"){
					icon = "extlibs/ext-3.3.0/resources/images/icons/map.png"
				}
				var cfg = {id : data.id,text : data.name,icon: icon};//获得id和name
				if(activeKey=="添加r"){
					var country = new Ext.tree.TreeNode(cfg)
					this.apptree.root.appendChild(country);
				}else{
					this.apptree.addChild2CurNode(cfg);
				}
			}else if(activeKey == "编辑C"||activeKey == "编辑P"||activeKey == "编辑Y"||activeKey == "编辑R"){
				var curNode = this.apptree.curNode;
				var opTxt = Msg_AppCaption.disabled_text;
				var text =  curNode.text;
				var name = data.name;
				var isenabled = data.isenabled;
				if(isenabled==0){
					name = name + opTxt;
				}
				curNode.setText(name);
			}
			else{
				this.apptree.reload();
			}
		}
		else{
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
				}case OPTION_TYPE.DEL:{ /* 删除  */
					 this.apptree.curNode.remove();
					break;
				}case OPTION_TYPE.ISDEFAULT:{
					var id = data.id;
						var node = this.apptree.getCurNode();
						var nodeTxt = node.text;
						var id = node.id.substring(0,1);
						switch(id){
							case 'C' : {
								nodeTxt = this.globalMgr.getBrothersNode('【默认国家】',node,nodeTxt);
								 break;
							}
							case 'P' : {
								nodeTxt = this.globalMgr.getBrothersNode('【默认省份】',node,nodeTxt);
								break;
							}
							case 'Y' : {
								nodeTxt = this.globalMgr.getBrothersNode('【默认城市】',node,nodeTxt);
								break;
							}
							case 'R' : {
								nodeTxt = this.globalMgr.getBrothersNode('【默认地区】',node,nodeTxt);
							   	break;
							}
						}
						node.setText(nodeTxt);break;
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
		var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_arealist.action',isLoad : true,rootText : '区域列表',width:200,enableDD:false});
		_apptree.expandAll();
		_apptree.addListener('click',function(node){
			var id = node.id;
			self.globalMgr.noid=id;
		});
		return _apptree
	},
	/**
	 * 当点击顶部的 系统图标 时，会触发 notify 方法。
	 * @param {} data 选中的 系统图标数据。
	 * 			{id : '',name:'',code:'',url:''} 等。
	 * 			具体值请看 SystemEntity 的 getFields() 方法
	 */
	
	globalMgr : {
		/**
	 * 得到所有的兄弟节点
	 * @return {}
	 */
	getBrothersNode : function(mrtext,node,nodeTxt){
		 var childNodes = [];
	     childNodes = node.parentNode.childNodes;
	     for(var i = 0,cnt = childNodes.length;i<cnt;i++){
	     	var mj = childNodes[i].text.indexOf(mrtext);
	     	if(mj!=-1){
	     		var dd = childNodes[i].text.replace(mrtext,"");
	     		childNodes[i].setText(dd);
	     		break;
	     	}
	     }
 		nodeTxt += mrtext;
     	return nodeTxt;
	},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		noid : null,
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent=_this.apptree;
				parentCfg.parent = parent;
				if(!parent.getSelId()){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
					return;
				}
				var subnoid=parent.getSelId().substring(0,1);
				var winkey=winkey+subnoid;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加r"){
						winModule="CountryEdit";
					}else if(winkey=="添加C"){
						winModule="ProvinceEdit";
					}else if(winkey=="添加P"){
						winModule="CityEdit";
					}else if(winkey=="添加Y"){
						winModule="RegionEdit";
					}else if(winkey=="添加R"){
						ExtUtil.alert({msg:"地区节点不能"+_this.globalMgr.activeKey});
						return;
					}else if(winkey=="编辑r"){
						ExtUtil.alert({msg:"根节点不能"+_this.globalMgr.activeKey});
						return;
					}else if(winkey=="编辑C"){
						winModule="CountryEdit";
					}else if(winkey=="编辑P" ){
						winModule="ProvinceEdit";
					}else if(winkey=="编辑Y"){
						winModule="CityEdit";
					}else if(winkey=="编辑R"){
						winModule="RegionEdit";
					}
					Cmw.importPackage('pages/app/sys/Homes/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
		
	}
});

