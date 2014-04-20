Ext.namespace("skythink.cmw.sys");
/**
 * 组架机构管理 UI
 * @author cmw
 * @date 2012-11-02
 * */ 
skythink.cmw.sys.RoleMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.RoleMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreePanelView({
			tab : tab,
			params : params,
			hasTopSys : true,
			getToolBar : this.getToolBar,
			getAppPanel : this.getAppPanel,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			notify : this.notify,
			refresh : this.refresh
		});
	},
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token : '添加角色',
			text : Btn_Cfgs.ROLE_ADD_BTN_TXT,
			iconCls:Btn_Cfgs.ROLE_ADD_BTN_CLS,
			tooltip:Btn_Cfgs.ROLE_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加角色",optionType:OPTION_TYPE.ADD,self:self});
			}
		},{
			token : '编辑角色',
			text : Btn_Cfgs.ROLE_EDIT_BTN_TXT,
			iconCls:Btn_Cfgs.ROLE_EDIT_BTN_CLS,
			tooltip:Btn_Cfgs.ROLE_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑角色",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{type:"sp"},{
			token : "复制角色",
			text : Btn_Cfgs.ROLE_COPY_BTN_TXT,
			iconCls:Btn_Cfgs.ROLE_COPY_BTN_CLS,
			tooltip:Btn_Cfgs.ROLE_COPY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"复制角色",self:self});
			}
		},{
			token : "保存角色",
			text : Btn_Cfgs.ROLE_SAVE_BTN_TXT,
			iconCls:Btn_Cfgs.ROLE_SAVE_BTN_CLS,
			tooltip:Btn_Cfgs.ROLE_SAVE_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.saveRoleRights(self);
			}
		},{type:"sp"},{
			token : "启用角色",
			text : Btn_Cfgs.ROLE_ENABLED_BTN_TXT,
			iconCls:Btn_Cfgs.ROLE_ENABLED_BTN_CLS,
			tooltip:Btn_Cfgs.ROLE_ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./sysRole_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用角色",
			text : Btn_Cfgs.ROLE_DISABLED_BTN_TXT,
			iconCls:Btn_Cfgs.ROLE_DISABLED_BTN_CLS,
			tooltip:Btn_Cfgs.ROLE_DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./sysRole_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除角色",
			text : Btn_Cfgs.ROLE_DEL_BTN_TXT,
			iconCls:Btn_Cfgs.ROLE_DEL_BTN_CLS,
			tooltip:Btn_Cfgs.ROLE_DEL_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./sysRole_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {menuId : this.params.nodeId}});
		return toolBar;
	},
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppPanel : function(){
		var self = this;
		var _rightTreePanel = new Ext.ux.tree.MyTree({url:'./sysTree_roleright.action?action=2',isLoad : false,isCheck:true,width:200,enableDD:false,autoScroll:true});
		return _rightTreePanel;
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
			if(activeKey && activeKey == "保存角色权限"){
				this.apptree.reload();
			}else{
				this.apptree.reload();
			}
		this.globalMgr.activeKey = null;
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_rolelist.action',isLoad : true,rootText : '角色列表',width:200,
			enableDD:false,autoScroll:true});
		_apptree.addListener('click',function(node){
			self.globalMgr.checkRight(node,self);
		});
		return _apptree
	},
	/**
	 * 当点击顶部的 系统图标 时，会触发 notify 方法。
	 * @param {} data 选中的 系统图标数据。
	 * 			{id : '',name:'',code:'',url:''} 等。
	 * 			具体值请看 SystemEntity 的 getFields() 方法
	 */
	notify : function(data){
		var self = this;
		var sysId = data.id;
		var curNode = this.apptree.curNode;
		var checkState = this.apppanel.checkState;
		self.globalMgr.sysId = sysId;
		var callback = function(){
			self.globalMgr.checkRight(curNode,self);
		};
		if(checkState && curNode){
			ExtUtil.confirm({msg:'角色【'+curNode.text+'】的权限数据已修改，是否保存角色权限数据?',
				fn:function(){
					self.globalMgr.saveRoleRights(self,callback);
			}});
			self.apppanel.checkState = false;
		}else{
			if(!curNode){
				this.apppanel.reload({sysid:sysId});
			}else{
				this.apppanel.reload({sysid:sysId},callback);
			}
		}
	},
	/**
	 * 当点击顶部的 系统图标 时，会触发 notify 方法。
	 * @param {} data 选中的 系统图标数据。
	 * 			{id : '',name:'',code:'',url:''} 等。
	 * 			具体值请看 SystemEntity 的 getFields() 方法
	 */
	
	globalMgr : {
		sysId : null,/*系统ID*/
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
				var parent=_this.apptree;
				parentCfg.parent = parent;
				var selId = parent.getSelId();
				var isNotRoot=null;
				if(selId!=null){
					isNotRoot = selId.indexOf("root_");
				}
				
				if((!selId && winkey != "添加角色" )||(isNotRoot !=-1 && winkey != "添加角色")){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
					return;
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}
				else{
					var winModule=null;
					if(winkey=="添加角色"||winkey=="编辑角色"){
						winModule="RoleEdit";
					}else{
						winModule="RoleCopy";
					}
					Cmw.importPackage('pages/app/sys/role/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		/**
		 * 保存角色权限
		 */
		saveRoleRights : function(self,callback){
			var roleId = self.apptree.getSelId();
			if(!roleId){
				ExtUtil.alert({msg:'请从左边树中选择要配置权限的角色！'});
				return;
			}
			var checkIds = self.apppanel.getCheckIds(true);
			if(!checkIds || checkIds.length == 0){
				ExtUtil.alert({msg:'请从右边权限树中勾选相应的权限！'});
				return;
			}
			var accordionRights = [];
			var menuRights = [];
			var moduleRights = [];
			for(var i=0,count=checkIds.length; i<count; i++){
				var checkId = checkIds[i];
				if(checkId.indexOf('C') != -1){
					accordionRights[accordionRights.length] = checkId;
				}else if(checkId.indexOf('M') != -1){
					menuRights[menuRights.length] = checkId;
				}else if(checkId.indexOf('D') != -1){
					moduleRights[moduleRights.length] = checkId;
				}
			}
			accordionRights = accordionRights.join(",");
			accordionRights = accordionRights.replaceAll('C','');
			
			menuRights = menuRights.join(",");
			menuRights = menuRights.replaceAll('M','');
			
			moduleRights = moduleRights.join(",");
			moduleRights = moduleRights.replaceAll('D','');
			var cfg = {params:{sysId : self.globalMgr.sysId,roleId:roleId,accordionRights : accordionRights,menuRights : menuRights, moduleRights : moduleRights},
			sfn:function(json_data){
				if(callback) callback();
				Cmw.unmask(self);
				Ext.tip.msg("提示","“"+role+"”的权限数据保存成功！");
			},
			ffn : function(json_data){
				Cmw.unmask(self);
			}};
			
			var role = self.apptree.getSelText();
			Cmw.mask(self,"正在保存“"+role+"”的权限数据，请稍等...");
			EventManager.get('./sysRight_save.action',cfg);
		},
		/**
		 * 选中当前角色相应的权限
		 * @param {} node
		 */
		checkRight : function(node,self){
			var isLoad = self.apppanel.isLoad;
			if(!isLoad) return;
			
			var roleId = node.id;
			if(roleId.indexOf("root_") != -1) return;
			var cfg = {params:{roleId:roleId,sysId:self.globalMgr.sysId},
			sfn:function(json_data){
				var list = json_data.list;
				var checkIds = list[0];
				self.apppanel.checkNodeByIds(checkIds);
				Cmw.unmask(self);
			},
			ffn : function(json_data){
				Cmw.unmask(self);
			}};
			
			var role = node.text;
			Cmw.mask(self,"正在加载“"+role+"”的权限数据，请稍等...");
			EventManager.get('./sysRight_get.action',cfg);
		}
	}
});

