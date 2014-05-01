Ext.namespace("skythink.cmw.sys");
/**
 * 用户管理UI
 * @author cmw
 * @date 2012-11-02
 * */ 
skythink.cmw.sys.UserMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.UserMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreeGridView({
			tab : tab,
			params : params,
			TREE_WIDTH : 250,
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
		var toolBar = null;
		var barItems = [{
			type : 'label',
			text : Btn_Cfgs.USER_LABEL_TEXT
		},{
			type : 'search',
			name : 'empName',
			onTrigger2Click:function(){
				self.globalMgr.query(self,true);
			}
		},{
			token : "查询",
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self,true);
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
			token : "添加用户",
			text : Btn_Cfgs.USER_ADD_BTN_TXT,
			iconCls:Btn_Cfgs.USER_ADD_BTN_CLS,
			tooltip:Btn_Cfgs.USER_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加",self:self});
			}
		},{
			token : "编辑用户",
			text : Btn_Cfgs.USER_EDIT_BTN_TXT,
			iconCls:Btn_Cfgs.USER_EDIT_BTN_CLS,
			tooltip:Btn_Cfgs.USER_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{type:"sp"},{
			token : "设置数据访问权限",
			text : Btn_Cfgs.SETDATARIGHT_BTN_TXT,
			iconCls:Btn_Cfgs.SETDATARIGHT_CLS,
			tooltip:Btn_Cfgs.SETDATARIGHT_TIP_BTN_TXT,
			key : Btn_Cfgs.SETDATARIGHT_FASTKEY,
			handler : function(){
				self.globalMgr.setDataAccess(self);
			}
		},{
			token : "修改密码",
			text : Btn_Cfgs.MODPWD_BTN_TXT,
			iconCls:Btn_Cfgs.MODPWD_CLS,
			tooltip:Btn_Cfgs.MODPWD_TIP_BTN_TXT,
			key : Btn_Cfgs.MODPWD_FASTKEY,
			handler : function(){
				self.globalMgr.winEdit.show({key:Btn_Cfgs.MODPWD_BTN_TXT,optionType:OPTION_TYPE.EDIT,self:self});
			}
		},
//			{
//			token : "职位调整",
//			text : Btn_Cfgs.CHGPOST_BTN_TXT,
//			iconCls:Btn_Cfgs.CHGPOST_CLS,
//			tooltip:Btn_Cfgs.CHGPOST_TIP_BTN_TXT,
//			key : Btn_Cfgs.CHGPOST_FASTKEY,
//			handler : function(){
//				self.globalMgr.winEdit.show({key:"职位调整",optionType:OPTION_TYPE.ADD,self:self});
//			}
//		},
			{type:"sp"},{
			token : "启用用户",
			text : Btn_Cfgs.USER_ENABLED_BTN_TXT,
			iconCls:Btn_Cfgs.USER_ENABLED_BTN_CLS,
			tooltip:Btn_Cfgs.USER_DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./sysUser_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用用户",
			text : Btn_Cfgs.USER_DISABLED_BTN_TXT,
			iconCls:Btn_Cfgs.USER_DISABLED_BTN_CLS,
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./sysUser_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除用户",
			text : Btn_Cfgs.USER_DEL_BTN_TXT,
			iconCls:Btn_Cfgs.USER_DEL_BTN_CLS,
			tooltip:Btn_Cfgs.USER_DEL_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./sysUser_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
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
		var self = this;
	  	var structure = [
	  		{header : '可用标识',name : 'isenabled',width:60,renderer :Render_dataSource.isenabledRender},
			{header : '姓名',name : 'empName',width:90},
			{header : '性别',name : 'sex',width:50,renderer : function(val){
				var val =Render_dataSource.sexRender(val);
				return val;
			}},
			{header : '登录账号',name : 'userName',width:95},
			{header : '数据访问级别',name : 'dataLevel',width:95,renderer : function(val){
				switch (val) {
				case "-1":
					val = "无";
					break;
				case "0":
					val = "本人数据";
					break;
				case "1":
					val = "自定义用户数据";
					break;
				case "2":
					val = "本部门数据";
					break;
				case "3":
					val = "本部门及子部门";
					break;
				case "4":
					val = "自定义部门";
					break;
				case "5":
					val = "本公司数据";
					break;
				case "6":
					val = "自定义公司";
					break;
				case "7":
					val = "无限制";
					break;
				default:
					val = "暂未设置";
					break;
				}
				return val;
			}},
			{header : '职位',name : 'postName',width:90},
			{header : '直属领导',name : 'leaderName',width:90},
			{header : '手机',name : 'phone',width:100},
			{header : '联系电话',name : 'tel',width:100},
			{header : '邮箱',name : 'email',width:160}
			];
			
		var _appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			url : './sysUser_list.action',
			needPage : true,
			isLoad : false,
			keyField : 'userId',
			listeners:{
				  rowdblclick:function(){
				  	self.globalMgr.winEdit.show({key:Btn_Cfgs.DETAIL_BTN_TXT,optionType:OPTION_TYPE.DETAIL,self:self});
				  } 
			}
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
		var postId = null;
		EventManager.query(this.appgrid,{indeptId:this.globalMgr.departId});
		this.globalMgr.activeKey = null;
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_orglist.action',isLoad : true,width:200,enableDD:false,autoScroll :true,params:{isNeedPost:true}});
		_apptree.expandAll(); 
		_apptree.addListener('click',function(node){
			var id = node.id;
			var departId = -1;
			if(id.indexOf('D') != -1){
				departId = id.substring(1);
				self.globalMgr.departId = departId;
			}
			EventManager.query(self.appgrid,{indeptId:departId});
		});
		return _apptree
	},
	globalMgr : {
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		departId : -1,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = null;
				var postId = null;
				var errMsg = null;
				if(winkey == Btn_Cfgs.INSERT_BTN_TXT){
					parent = _this.apptree;
					errMsg = Msg_SysTip.msg_noSelNode;
				}else{
					parent = _this.appgrid;
					parentCfg.indeptId = _this.globalMgr.departId;
				}
				parentCfg.parent = parent;
				var selId = parent.getSelId();
				if(!selId){
					if(errMsg) ExtUtil.alert({msg:errMsg});
					return;
				}
				
				if((winkey==Btn_Cfgs.INSERT_BTN_TXT) && selId.indexOf('D') == -1){	//添加用户时，必须选职位节点
					ExtUtil.alert({msg:"添加用户前，必须选择部门节点！"});
					return;
				}
				
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="修改密码"){
						winModule="PassWordEdit";
					}else if(winkey=="详情"){
						winModule="UserDetail";
					}else if(winkey=="职位调整"){
						winModule="PostChangeEdit";
					}else{
						winModule="UserEdit";
					}
					Cmw.importPackage('pages/app/sys/user/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		/**
		 * 查询	
		 * @param {} self
		 */
		query : function(self,noPostId){
			var params = self.toolBar.getValues();
			if(!noPostId){
				var indeptId = self.apptree.getSelId();
				if(indeptId){
					indeptId = indeptId.substring(1);
					params["indeptId"] = indeptId;
				}
			}
			EventManager.query(self.appgrid,params);
		},
		/**
		 * 设置数据访问权限
		 * @param {} _this
		 */
		setDataAccess : function(_this){
			var userId = _this.appgrid.getSelId();
			if(!userId) return;
			var params = {};
			params["userId"] = userId;
			var tabId = CUSTTAB_ID.userDataAccessEditTab.id;
			var url =  CUSTTAB_ID.userDataAccessEditTab.url;
			var title =  '数据访问权限设置';
			var apptabtreewinId = _this.params["apptabtreewinId"];
			Cmw.print(params);
			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
		}
	}
});

