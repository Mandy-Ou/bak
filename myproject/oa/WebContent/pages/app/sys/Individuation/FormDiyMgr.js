Ext.namespace("skythink.cmw.sys");
/**
 * 表单个性化 UI
 * @author 彭登浩
 * @date 2012-11-02
 * */ 
skythink.cmw.sys.FormDiyMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.FormDiyMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreePanelView({
			tab:tab,
			params:params,
			hasTopSys : true,
			getToolBar : this.getToolBar,
			getAppPanel : this.getAppPanel,
			getAppGrid : this.getAppGrid,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			notify : this.notify,
			refresh : this.refresh
		});
	},
	notify : function(data){
		var self = this;
		var sysId = data.id;
		this.globalMgr.sysId=sysId;
		this.globalMgr.tb1.enable(); 
		this.globalMgr.tb2.enable(); 
		this.apptree.reload({sysid:this.globalMgr.sysId});//点击系统图标时候加载树
	},
	
		getToolBar : function(){
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({hidden:true});
			return toolBar;
		},
	/**
	 * 获取自定义字段Grid 对象
	 */
	getAppGrid : function(){
		var self = this;
		var wd = 90;
	  	var structure = [
	  		{header : '可用标识',name : 'isenabled',width:65,renderer :Render_dataSource.isenabledRender},
			{header : 'formdiyId',name : 'formdiyId',width:wd,hidden : true},
			{header : '字段属性名',name : 'name',width:100},
			{header : '默认名称',name : 'dispName',width:wd},
			{header : '所在行',name : 'row',width:65},
			{header : '所在列',name : 'col',width:65,align:"center"},
			{header : '控制类型',name : 'controlType',width:wd,renderer : Render_dataSource.controlTypeRender},
			{header : '是否必填',name : 'isRequired',width:65,renderer : Render_dataSource.isRequiredRender},
			{header : '最大长度(px)',name : 'maxlength',width:90},
			{header : '宽(px)',name : 'width',width:65},
			{header : '高(px)',name : 'height',width:65},
			{header : '数据源',name : 'datasource',width:wd}
			];
		var _appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			url : './sysFieldCustom_list.action',
			width  : CLIENTWIDTH-self.TREE_WIDTH,
			height : 280,
			needPage : false,
			isLoad : false,
			keyField : 'id'
		});
		return _appgrid;
	},
	
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppPanel : function(){
		var self = this;
		var panel = new Ext.Panel({title:'自定义字段列表'});
			panel.add(this.globalMgr.getToolBar(self));
		var _panel = new Ext.Panel();
			this.globalMgr.appgrid = this.getAppGrid();
			_panel.add(this.globalMgr.appgrid);
		var _paneltwo = new Ext.Panel({title:'字段自定义属性列表'});
			_paneltwo.add(this.globalMgr.getToolBar2(self));
			this.globalMgr.appgrid2 = this.globalMgr.getAppGrid2(self);
			_paneltwo.add( this.globalMgr.appgrid2);
		panel.add(_panel);
		panel.add(_paneltwo);
		return  panel;
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
		if(activeKey){
			if(activeKey == "添加字段属性" || activeKey == "编辑字段属性" || activeKey =="禁用字段属性" || activeKey =="起用字段属性" || activeKey =="删除字段属性"){
				this.globalMgr.appgrid2.reload({formdiyId:this.globalMgr.formdiyId});
			}else if(activeKey == "添加表单" || activeKey == "编辑表单"){
				this.apptree.reload({sysid:this.globalMgr.sysId});
			}else if(activeKey=="添加字段"||activeKey=="编辑字段" || activeKey =="禁用字段" || activeKey =="起用字段" || activeKey =="删除字段"){
				this.globalMgr.appgrid.reload({formdiyId:this.globalMgr.formdiyId});
			}else{
			switch(optionType){
				case OPTION_TYPE.DISABLED: /* 禁用  */
				case OPTION_TYPE.ENABLED:{ /* 起用  */
					var opTxt = Msg_AppCaption.disabled_text;
					var ids = data.ids;
					ids = ids.split(",");
					if(activeKey =="禁用表单" || activeKey =="起用表单" || activeKey =="删除表单"){
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
					}
				}default:{
					this.apptree.reload();
				}
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
		var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_fdlist.action',isLoad : true,width:200,enableDD : true,autoScroll : true});
		_apptree.addListener('click',function(node){
			var selId = node.id;
			var isNotRoot = selId.indexOf("root_");
			if(isNotRoot!=-1) return;
			var id = node.id;
			var subid = id.substring(1);
			self.globalMgr.formdiyId = subid;
			EventManager.query(self.globalMgr.appgrid,{formdiyId:subid});
			EventManager.query(self.globalMgr.appgrid2,{formdiyId:subid});
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
		tb2 :null,
		tb1 :null,
		getToolBar : function(self){
			var barItems = [{
				token : "添加表单",
				text :Btn_Cfgs.FORMDIY_ADD_BTN_TXT,
				iconCls:Btn_Cfgs.INSERT_CLS,
				tooltip:Btn_Cfgs.FORMDIY_ADD_TIP_BTN_TXT,
				handler : function(){
					var sysid = self.globalMgr.sysId;
					self.globalMgr.winEdit.show({key:"添加表单",self:self,sysid:sysid});
				}
			},{
				token : "编辑表单",
				text : Btn_Cfgs.FORMDIY_EDIT_ADD_TXT,
				iconCls:Btn_Cfgs.MODIFY_CLS,
				tooltip:Btn_Cfgs.FORMDIY_EDIT_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.winEdit.show({key:"编辑表单",optionType:OPTION_TYPE.EDIT,self:self});
				}
			},{type:"sp"},{
				token : "起用表单",
				text : Btn_Cfgs.FORMDIY_ENABLED_BTN_TXT,
				iconCls:Btn_Cfgs.ENABLED_CLS,
				tooltip:Btn_Cfgs.FORMDIY_ENABLED_TIP_BTN_TXT,
				handler : function(){
					var selId = self.apptree.getSelId();
					var isNotRoot =null;
					if(selId==null){
						ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
						return;
					}else{
						isNotRoot = selId.indexOf("root_");
						if(isNotRoot!=-1){
							ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
							return;
						}
					}
					self.globalMgr.activeKey ="起用表单";
					EventManager.enabledData('./sysFormdiy_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
				}
			},{
				token : "禁用表单",
				text : Btn_Cfgs.FORMDIY_DISABLED_BTN_TXT,
				iconCls:Btn_Cfgs.DISABLED_CLS,
				tooltip:Btn_Cfgs.FORMDIY_DISABLED_TIP_BTN_TXT,
				handler : function(){
					var selId = self.apptree.getSelId();
					var isNotRoot =null;
					if(selId==null){
						ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
						return;
					}else{
						isNotRoot = selId.indexOf("root_");
						if(isNotRoot!=-1){
							ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
							return;
						}
					}
					self.globalMgr.activeKey ="禁用表单";
					EventManager.disabledData('./sysFormdiy_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
				}
			},{type:"sp"},{
				token : "删除表单",
				text : Btn_Cfgs.FORMDIY_DEL_BTN_TXT,
				iconCls:Btn_Cfgs.DELETE_CLS,
				tooltip:Btn_Cfgs.FORMDIY_DEL_TIP_BTN_TXT,
				handler : function(){
					var selId = self.apptree.getSelId();
					var isNotRoot =null;
					if(selId==null){
						ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
						return;
					}else{
						isNotRoot = selId.indexOf("root_");
						if(isNotRoot!=-1){
							ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
							return;
						}
					}
					self.globalMgr.activeKey ="删除表单";
					EventManager.deleteData('./sysFormdiy_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
				}
			},{
				token : "生成文件",
				text : Btn_Cfgs.FORMDIY_MAKEFILE_BTN_TXT,
				iconCls:Btn_Cfgs.INSERT_CLS,
				tooltip:Btn_Cfgs.FORMDIY_MAKEFILE_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.savefile(self);
				}
			},{type:"sp"},{
				token : "刷新字段列表",
				text : Btn_Cfgs.FIELDCUSTOM_QUERY_BTN_TXT,
				iconCls:Btn_Cfgs.FIELDCUSTOM_QUERY_CLS,
				tooltip:Btn_Cfgs.FIELDCUSTOM_QUERY_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.activeKey ="刷新字段列表";
					self.globalMgr.query(self);
				}
			},{
				token : "添加字段",
				text : Btn_Cfgs.FIELDCUSTOM_ADD_BTN_TXT,
				iconCls:Btn_Cfgs.INSERT_CLS,
				tooltip:Btn_Cfgs.FIELDCUSTOM_ADD_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.winEdit.show({key:"添加字段",self:self});
				}
			},{type:"sp"},{
				token : "编辑字段",
				text : Btn_Cfgs.FIELDCUSTOM_EDIT_ADD_TXT,
				iconCls:Btn_Cfgs.MODIFY_CLS,
				tooltip:Btn_Cfgs.FIELDCUSTOM_EDIT_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.winEdit.show({key:"编辑字段",optionType:OPTION_TYPE.EDIT,self:self});
				}
			},{
				token : "起用字段",
				text : Btn_Cfgs.FIELDCUSTOM_ENABLED_BTN_TXT,
				iconCls:Btn_Cfgs.ENABLED_CLS,
				tooltip:Btn_Cfgs.FIELDCUSTOM_ENABLED_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.activeKey ="起用字段";
					EventManager.enabledData('./sysFieldCustom_enabled.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
				}
			},{type:"sp"},{
				token : "禁用字段",
				text : Btn_Cfgs.FIELDCUSTOM_DISABLED_BTN_TXT,
				iconCls:Btn_Cfgs.DISABLED_CLS,
				tooltip:Btn_Cfgs.FIELDCUSTOM_DISABLED_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.activeKey ="禁用字段";
					EventManager.disabledData('./sysFieldCustom_disabled.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
				}
			},{
				token : "删除字段",
				text : Btn_Cfgs.FIELDCUSTOM_DEL_BTN_TXT,
				iconCls:Btn_Cfgs.DELETE_CLS,
				tooltip:Btn_Cfgs.FIELDCUSTOM_DEL_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.activeKey ="删除字段";
					EventManager.deleteData('./sysFieldCustom_delete.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.DEL,self:self});
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : self.params[CURR_NODE_KEY]}});
			self.globalMgr.tb1= toolBar;
			toolBar.setDisabled(true);
			return toolBar;
		},
		getToolBar2 : function(self){
			var barItems = [{
				token : "刷新属性列表",
				text : Btn_Cfgs.FIELDPROP_QUERY_BTN_TXT,
				iconCls:Btn_Cfgs.FIELDPROP_QUERY_CLS,
				tooltip:Btn_Cfgs.FIELDPROP_QUERY_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.activeKey ="刷新属性列表";
					self.globalMgr.query(self);
				}
			},{
				token : "添加字段属性",
				text : Btn_Cfgs.FIELDPROP_ADD_BTN_TXT,
				iconCls:Btn_Cfgs.INSERT_CLS,
				tooltip:Btn_Cfgs.FIELDPROP_ADD_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.winEdit.show({key:"添加字段属性",self:self});
				}
			},{
				token : "编辑字段属性",
				text : Btn_Cfgs.FIELDPROP_EDIT_ADD_TXT,
				iconCls:Btn_Cfgs.MODIFY_CLS,
				tooltip:Btn_Cfgs.FIELDPROP_EDIT_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.winEdit.show({key:"编辑字段属性",optionType:OPTION_TYPE.EDIT,self:self});
				}
			},{
				token : "起用字段属性",
				text : Btn_Cfgs.FIELDPROP_ENABLED_BTN_TXT,
				iconCls:Btn_Cfgs.ENABLED_CLS,
				tooltip:Btn_Cfgs.FIELDPROP_ENABLED_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.activeKey ="起用字段属性";
					EventManager.enabledData('./sysFieldProp_enabled.action',{type:'grid',cmpt:self.globalMgr.appgrid2,optionType:OPTION_TYPE.ENABLED,self:self});
				}
			},{
				token : "禁用字段属性",
				text : Btn_Cfgs.FIELDPROP_DISABLED_BTN_TXT,
				iconCls:Btn_Cfgs.DISABLED_CLS,
				tooltip:Btn_Cfgs.FIELDPROP_DISABLED_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.activeKey ="禁用字段属性";
					EventManager.disabledData('./sysFieldProp_disabled.action',{type:'grid',cmpt:self.globalMgr.appgrid2,optionType:OPTION_TYPE.DISABLED,self:self});
				}
			},{
				token : "删除字段属性",
				text :Btn_Cfgs.FIELDPROP_DEL_BTN_TXT,
				iconCls:Btn_Cfgs.DELETE_CLS,
				tooltip:Btn_Cfgs.FIELDPROP_DEL_TIP_BTN_TXT,
				handler : function(){
					self.globalMgr.activeKey ="删除字段属性";
					EventManager.deleteData('./sysFieldProp_delete.action',{type:'grid',cmpt:self.globalMgr.appgrid2,optionType:OPTION_TYPE.DEL,self:self});
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : self.params[CURR_NODE_KEY]}});
			self.globalMgr.tb2= toolBar;
			toolBar.setDisabled(true);
			return toolBar;
		},
		/**
		 * 获取自定义属性Grid 对象
		 */
		getAppGrid2 : function(){
			var self = this;
			var wd = 100;
		  	var structure = [
		  		{header : '可用标识',name : 'isenabled',width:60,renderer :Render_dataSource.isenabledRender},
				{header : 'formdiyId',name : 'formdiyId',width:wd,hidden :true},
				{header : '字段属性名',name : 'name',width:wd},
				{header : '标准名称',name : 'standName',width:wd},
				{header : '默认显示名称',name : 'dispName',width:wd},
				{header : '是否必填',name : 'isRequired',width:wd,renderer : Render_dataSource.isRequiredRender},
				{header : '英文名称',name : 'ename',width:wd},
				{header : '日文名称',name : 'jname',width:wd},
				{header : '繁体中文名称',name : 'twname',width:wd},
				{header : '法文名称',name : 'fname',width:wd},
				{header : '韩文名称',name : 'koname',width:wd}
				];
			var _appgrid = new Ext.ux.grid.AppGrid({
				tbar : this.toolBar,
				structure : structure,
				url : './sysFieldProp_list.action',
				width  : CLIENTWIDTH-self.TREE_WIDTH,
				height : 330,
				needPage : false,
				isLoad : false,
				keyField : 'id'
			});
			return _appgrid;
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		sysId : null,
		appgrid : null,
		appgrid2 :null,
		formdiyId : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var parent = parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				Cmw.print(winkey);
				var parent = (winkey=="编辑字段") ? _this.globalMgr.appgrid : _this.apptree;
				if(winkey=="编辑字段属性"){
					parent =  _this.globalMgr.appgrid2;
				}
				parentCfg.parent = parent;
				var selId = parent.getSelId();
				if(parent == _this.apptree){
					var isNotRoot =null;
					if(selId !=null){
						isNotRoot = selId.indexOf("root_");
					}					
					if((selId==null && winkey !="添加表单")||(isNotRoot !=-1 && winkey =="添加字段")||(isNotRoot !=-1 && winkey =="添加字段属性")){
						ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
						return;
					}
				}else{
					if(!selId && winkey == "编辑字段"){
						ExtUtil.alert({msg:"请选择表格中的数据！"});
						return;	
					}
					if(!selId && winkey=="编辑字段属性"){
						ExtUtil.alert({msg:"请选择表格中的数据！"});
						return;	
					}	
					
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{ 
					var winModule=null;
					if(winkey=="添加表单" || winkey=="编辑表单"){
						winModule="FormDiyEdit";
					}else if(winkey=="添加字段"||winkey=="编辑字段"){
						winModule="FieldCustomEdit";
					}else if(winkey=="添加字段属性"||winkey=="编辑字段属性"){
						winModule="FieldPropEdit";
					}
					Cmw.importPackage('pages/app/sys/Individuation/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		query : function(self){
			var id = self.apptree.getSelId();
			var subid =null;
			if(id){
				var isNotRoot = id.indexOf("root_");
				if(isNotRoot!=-1){
					return;
				}else{
					subid = id.substring(1);
				}
			}else{
				subid = null;
				return;
			}
			var activeKey = self.globalMgr.activeKey;
			if(activeKey =="刷新字段列表"){
				EventManager.query(self.globalMgr.appgrid,{formdiyId:subid});
			}else if(activeKey =="刷新属性列表"){
				EventManager.query(self.globalMgr.appgrid2,{formdiyId:subid});
			}
		},
		/**
		 * 生成表单个性化 JS 文件。
		 *  文件位置：js/datas/FormDiyDatas.js
		 * @param {} self
		 */
		savefile : function(self){
			ExtUtil.confirm({msg:Msg_SysTip.msg_doSaveFileCofirm,fn:function(btn){
				if(btn != 'yes') return;
				Cmw.mask(self,Msg_SysTip.msg_doSaveResFile);
				EventManager.get('./sysFormdiy_savefile.action',{sfn:function(json_data){
					Ext.tip.msg(Msg_SysTip.title_appconfirm,Msg_SysTip.msg_saveResourceSucess);
					Cmw.unmask(self);
				},ffn:function(json_data){
					Cmw.unmask(self);
				}});
			}});
		}
	}
});

