Ext.namespace("skythink.cmw.sys");
/**
 * 资料清单模板管理主界面
 * @author 彭登涛
 * @date 2012-12-27
 * */ 

skythink.cmw.sys.MatTempMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.MatTempMgr,Ext.util.MyObservable,{
	initModule : function(){
		this.module = new Cmw.app.widget.AbsTreePanelView({
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
		this.globalMgr.tb1.enable();
		this.globalMgr.tb2.enable();
		var sysId = data.id;
		this.globalMgr.sysId=sysId;
		this.apptree.reload({sysId:this.globalMgr.sysId});//点击系统图标时候加载树
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
		var wd = 100;
	  	var structure = [
	  		{header : '可用标识',name : 'isenabled',width:wd,renderer : Render_dataSource.isenabledRender},
			{header : '资料模版id',name : 'tempId',width:wd, hidden : true},
			{
			    header: '标题名称',
			    name: 'name',
			    width:300
			},
			{
			    header: '排序',
			    name: 'orderNo'
			},{
				header:'备注',
				name:'remark',
				width : 300
			}];
		var _appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			url : './sysMatSubjec_list.action',
			width  : 1080,
			height : 280,
			needPage : true,
			isLoad : false,
			keyField : 'id'
		});
		_appgrid.addListener('rowclick',function(){
			self.globalMgr.appgrid2.reload({subjectId:_appgrid.getSelId()})
		});
		return _appgrid;
	},
	
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppPanel : function(){
		var self = this;
		var panel = new Ext.Panel({title:'模板标题列表'});
			panel.add(this.globalMgr.getToolBar(self));
		var _panel = new Ext.Panel();
			this.globalMgr.appgrid = this.getAppGrid();
			_panel.add(this.globalMgr.appgrid);
		var _paneltwo = new Ext.Panel({title:'模板清单项列表'});
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
			if(activeKey == "添加清单项" || activeKey == "编辑清单项" || activeKey =="禁用清单项" || activeKey =="起用清单项" || activeKey =="删除清单项"){
				this.globalMgr.appgrid2.reload({subjectId:this.globalMgr.appgrid.getSelId()});
			}else if(activeKey == "添加模板" || activeKey == "编辑模板" ||activeKey =="预览模板" ){
				this.apptree.reload({sysId:this.globalMgr.sysId});
			}else if(activeKey=="添加标题"||activeKey=="编辑标题" || activeKey =="禁用标题" || activeKey =="起用标题" || activeKey =="删除标题"){
				this.globalMgr.appgrid.reload({tempId:this.globalMgr.tempId});
			}else{
			switch(optionType){
				case OPTION_TYPE.DISABLED: /* 禁用  */
				case OPTION_TYPE.ENABLED:{ /* 起用  */
					var opTxt = Msg_AppCaption.disabled_text;
					var ids = data.ids;
					ids = ids.split(",");
					if(activeKey =="禁用模板" || activeKey =="起用模板" || activeKey =="删除模板"){
						for(var i=0,count=ids.length; i<count; i++){
						var node = this.apptree.getNodeById(ids[i]);
						var nodeTxt = node.text;
						if(OPTION_TYPE.DISABLED == optionType){
							nodeTxt+=opTxt;
						}else{//可用时,替换"【已禁用】
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
		var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_matTempList.action',isLoad : true,width:200});
		_apptree.addListener('click',function(node){
			var id = node.id;
			var subid = id.substring(1);
			self.globalMgr.tempId = subid;
			EventManager.query(self.globalMgr.appgrid,{tempId:subid});
			self.globalMgr.appgrid2.removeAll();
//			EventManager.query(self.globalMgr.appgrid2,{tempId:subid});
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
		getToolBar : function(self){
			var barItems = [{
				text:'预览模板',
			    iconCls:'page_preview',
				tooltip:"预览模板",
			    handler : function(){
					    var sysid = self.globalMgr.sysId;
						self.globalMgr.winEdit.show({key:"预览模板",self:self,sysid:sysid});
			  }
			},{
				text : "添加模板",
				iconCls:'page_add',
				tooltip:"添加模板",
				handler : function(){
					var sysid = self.globalMgr.sysId;
					self.globalMgr.winEdit.show({key:"添加模板",self:self,sysid:sysid});
				}
			},{
				text : "编辑模板",
				iconCls:'page_edit',
				tooltip:"编辑模板",
				handler : function(){
					self.globalMgr.winEdit.show({key:"编辑模板",optionType:OPTION_TYPE.EDIT,self:self});
				}
			},{
				text:'复制模板',
			    iconCls:Btn_Cfgs.ROLE_COPY_BTN_CLS,
			    tooltip:"复制模板",
			    handler : function(){
			    	self.globalMgr.winEdit.show({key:"复制模板",optionType:OPTION_TYPE.EDIT,self:self})
			  }   
			},{type:"sp"},{
				text : "起用模板",
				iconCls:'page_enabled',
				tooltip:"起用模板",
				handler : function(){
					self.globalMgr.activeKey ="起用模板";
					EventManager.enabledData('./sysMatTemp_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
				}
			},{
				text : "禁用模板",
				iconCls:'page_disabled',
				tooltip:"禁用模板",
				handler : function(){
					self.globalMgr.activeKey ="禁用模板";
					EventManager.disabledData('./sysMatTemp_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
				}},{
				text : "删除模板",
				iconCls:'page_delete',
				tooltip:"删除模板",
				handler : function(){
					self.globalMgr.activeKey ="删除模板";
					EventManager.deleteData('./sysMatTemp_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
				}
				},{type:"sp"},{
				token : "刷新模板标题",
				text : "刷新模板标题",
				iconCls:Btn_Cfgs.FIELDCUSTOM_QUERY_CLS,
				tooltip:"刷新模板标题",
				handler : function(){
					EventManager.query(self.globalMgr.appgrid,{tempId:self.globalMgr.tempId});
				}
			},{
				text : "添加标题",
				iconCls:'page_add',
				tooltip:"添加标题",
				handler : function(){
					self.globalMgr.winEdit.show({key:"添加标题",self:self});
				}
			},{type:"sp"},{
				text : "编辑标题",
				iconCls:'page_edit',
				tooltip:"编辑标题",
				handler : function(){
					self.globalMgr.winEdit.show({key:"编辑标题",optionType:OPTION_TYPE.EDIT,self:self});
				}
			},{
				text : "起用标题",
				iconCls:'page_enabled',
				tooltip:"起用标题",
				handler : function(){
					self.globalMgr.activeKey ="起用标题";
					EventManager.enabledData('./sysMatSubjec_enabled.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
				}
			},{type:"sp"},{
				text : "禁用标题",
				iconCls:'page_disabled',
				tooltip:"禁用标题",
				handler : function(){
					self.globalMgr.activeKey ="禁用标题";
					EventManager.disabledData('./sysMatSubjec_disabled.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
				}
			},{
				text : "删除标题",
				iconCls:'page_delete',
				tooltip:"删除标题",
				handler : function(){
					self.globalMgr.activeKey ="删除标题";
					EventManager.deleteData('./sysMatSubjec_delete.action',{type:'grid',cmpt:self.globalMgr.appgrid,optionType:OPTION_TYPE.DEL,self:self});
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			self.globalMgr.tb1= toolBar;
			toolBar.disable();
			return toolBar;
		},
		getToolBar2 : function(self){
			var barItems = [{
				token : "刷新模板清单项",
				text : "刷新模板清单项",
				iconCls:Btn_Cfgs.FIELDCUSTOM_QUERY_CLS,
				tooltip:"刷新模板清单项",
				handler : function(){
					var id = self.globalMgr.appgrid.getSelId();
					if(!id){
						return;
					}else{
						EventManager.query(self.globalMgr.appgrid2,{subjectId:id});
					}
				}
			},{
				text : "添加清单项",
				iconCls:'page_add',
				tooltip:"添加清单项",
				handler : function(){
					self.globalMgr.winEdit.show({key:"添加清单项",self:self});
//					var sysid = self.globalMgr.sysId;
//					self.globalMgr.winEdit.show({key:"添加清单项",self:self,sysid:sysid});
				}
			},{
				text : "编辑清单项",
				iconCls:'page_edit',
				tooltip:"编辑清单项",
				handler : function(){
					self.globalMgr.winEdit.show({key:"编辑清单项",optionType:OPTION_TYPE.EDIT,self:self});
				}
			},{
				text : "起用清单项",
				iconCls:'page_enabled',
				tooltip:"起用清单项",
				handler : function(){
					self.globalMgr.activeKey ="起用清单项";
					EventManager.enabledData('./sysMatParams_enabled.action',{type:'grid',cmpt:self.globalMgr.appgrid2,optionType:OPTION_TYPE.ENABLED,self:self});
				}
			},{
				text : "禁用清单项",
				iconCls:'page_disabled',
				tooltip:"禁用清单项",
				handler : function(){
					self.globalMgr.activeKey ="禁用清单项";
					EventManager.disabledData('./sysMatParams_disabled.action',{type:'grid',cmpt:self.globalMgr.appgrid2,optionType:OPTION_TYPE.DISABLED,self:self});
				}
			},{
				text : "删除清单项",
				iconCls:'page_delete',
				tooltip:"删除清单项",
				handler : function(){
					self.globalMgr.activeKey ="删除清单项";
					EventManager.deleteData('./sysMatParams_delete.action',{type:'grid',cmpt:self.globalMgr.appgrid2,optionType:OPTION_TYPE.DEL,self:self});
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			self.globalMgr.tb2= toolBar;
			toolBar.disable();
			return toolBar;
		},
			/**
			 * 获取自定义属性Grid 对象
			 */
			getAppGrid2 : function(){
				var self = this;
				var wd = 100;
			  	var structure = [
			  		{header : '可用标识',name : 'isenabled',width:wd,renderer :  Render_dataSource.isenabledRender},
		  			{
					    header: '资料标题ID',
					    hidden : true,
					    name: 'subjectId'
					},
					{
					    header: '资料项名称',
					    name: 'name',
					     width:300
					},
					{
					    header: '是否必填项',
					    name: 'allowBlank',
					    renderer : Render_dataSource.isRequiredRender
					},
					{
					    header: '是否支持附件',
					    name: 'isAttach',
					    renderer : Render_dataSource.isRequiredRender
					},
					{
					    header: '是否需要备注',
					    name: 'isRemark',
					    renderer : Render_dataSource.isRequiredRender
					},
					{
					    header: '排序',
					    name: 'orderNo'
					},{
					    header: '备注',
					    name: 'remark',
					    width : 250
					}
					];
				var _appgrid = new Ext.ux.grid.AppGrid({
					tbar : this.toolBar,
					structure : structure,
					url : './sysMatParams_list.action',
					width  : 1070,
					height : 330,
					needPage : true,
					isLoad : false,
					keyField : 'id'
				});
				return _appgrid;
			},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		tb1 : null,
		tb2 : null,
		activeKey : null,
		sysId : null,
		appgrid : null,
		appgrid2 :null,
		tempId : null,
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var errMsg = null;
				var parent = null;
				//判断是树还是叶子
				switch(winkey){
					case  "添加标题":{
					   parent = _this.apptree;
				       break;
					}case  "编辑标题":{
					   parent = _this.globalMgr.appgrid;
				       break;
					}case  "编辑模板":{
					   parent = _this.apptree;
					   errMsg = Msg_SysTip.msg_noSelNode;
					   break;
					}case  "编辑清单项":{
						parent = _this.globalMgr.appgrid2;
						break;
					}case  "添加清单项":{
						   parent = _this.apptree;
						   parentCfg.appGrid = _this.globalMgr.appgrid;
						    if(!parentCfg.appGrid.getSelId("请选中模板标题表中的数据！")){return;};
						   errMsg = Msg_SysTip.msg_noSelNode;
						   break;
					}case "复制模板":{
						 parent = _this.apptree;
						 errMsg = Msg_SysTip.msg_noSelNode;
					}case "预览模板":{
						 parent = _this.apptree;
						 errMsg = Msg_SysTip.msg_noSelNode;
					}
					
				}
				if(winkey != "添加模板" && !parent.getSelId()){
					if(errMsg){
						ExtUtil.alert({msg:errMsg});
					}
					return;
				}
				parentCfg.parent = parent;
				parentCfg.params = {sysId : _this.globalMgr.sysId}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);//缓存.如果重复则直接跳到下dm
				}else{ 
					var winModule=null;
					if(winkey=="添加模板" || winkey=="编辑模板"){
						winModule="MatTempEdit";
					}else if(winkey=="添加标题"||winkey=="编辑标题"){
						winModule="MatSubjectEdit";
					}else if(winkey=="添加清单项"||winkey=="编辑清单项"){
						winModule="MatParamsEdit";
					}else if(winkey == "复制模板"){
						winModule = "FrmMatTempCopyEdit";
					}else if(winkey == "预览模板"){
						winModule = "PreviewTemplateEdit";
					}
					   Cmw.importPackage('pages/app/sys/mat/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

