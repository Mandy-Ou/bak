Ext.namespace("skythink.cmw.sys");
/**
 * 组架机构管理 UI
 * @author 彭登浩
 * @date 2012-11-02
 * */ 
skythink.cmw.sys.OrgMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.OrgMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreePanelView({
			tab : tab,
			params : params,
			TREE_WIDTH : 250,
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
			token : '添加公司',
			text : Btn_Cfgs.COMPANY_ADD_BTN_TXT,
			iconCls : Btn_Cfgs.COMPANY_ADD_BTN_CLS,
			tooltip : Btn_Cfgs.COMPANY_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加公司",optionType:OPTION_TYPE.ADD,self:self});
			}
		},{
			token : '编辑公司',
			text : Btn_Cfgs.COMPANY_EDIT_BTN_TXT,
			iconCls:Btn_Cfgs.COMPANY_EDIT_BTN_CLS,
			tooltip:Btn_Cfgs.COMPANY_EDIE_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑公司",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{type:"sp"},{
			token : '删除公司',
			text : Btn_Cfgs.COMPANY_DEL_BTN_TXT,
			iconCls:Btn_Cfgs.COMPANY_DEL_BTN_CLS,
			tooltip:Btn_Cfgs.COMPANY_DEL_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.activeKey = "删除公司";
				var selId = self.apptree.getSelId();
				if(selId!=null){
					if(selId.indexOf('C')!=-1){
						var curNode = self.apptree.getCurNode();
						var hasChildNodes = curNode.hasChildNodes();
						if(hasChildNodes==false){
	  						EventManager.deleteData('./sysCompany_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
						}else{
							ExtUtil.alert({msg:"对不起，此公司不能删除！"});
							return;
						}
					}else{
						ExtUtil.alert({msg:"请选中公司节点！"});
						return;
					}
				}else{
					ExtUtil.alert({msg:"请选中公司节点！"});
					return;
				}
			}
		},{
			token : '添加部门',
			text : Btn_Cfgs.DEPARTMENT_ADD_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.DEPARTMENT_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"添加部门",optionType:OPTION_TYPE.ADD,self:self});
			}
		},{
			token : '编辑部门',
			text : Btn_Cfgs.DEPARTMENT_EDIT_BTN_TXT,
			iconCls:Btn_Cfgs.DEPARTMENT_EDIT_BTN_CLS,
			tooltip:Btn_Cfgs.DEPARTMENT_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"编辑部门",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{type:"sp"},{
			token : '删除部门',
			text : Btn_Cfgs.DEPARTMENT_DEL_BTN_TXT,
			iconCls:Btn_Cfgs.DEPARTMENT_DEL_BTN_CLS,
			tooltip:Btn_Cfgs.DEPARTMENT_DEL_TIP_BTN_TXT,
			handler : function(){
				var selId = self.apptree.getSelId();
				if(selId!=null){
					if(selId.indexOf('D')!=-1){
						var curNode = self.apptree.getCurNode();
						var hasChildNodes = curNode.hasChildNodes();
						if(hasChildNodes==false){
							EventManager.deleteData('./sysDepartment_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
						}else{
							ExtUtil.alert({msg:"对不起，此部门不能删除！"});
								return;
						}
					}else{
						ExtUtil.alert({msg:"请选中部门节点！"});
						return;
					}
				}else{
					ExtUtil.alert({msg:"请选中部门节点！"});
					return;
				}
			}
		}
//		,{
//			token : '添加职位',
//			text : Btn_Cfgs.POST_ADD_BTN_TXT,
//			iconCls:Btn_Cfgs.POST_ADD_BTN_CLS,
//			tooltip:Btn_Cfgs.POST_ADD_TIP_BTN_TXT,
//			handler : function(){
//				self.globalMgr.winEdit.show({key:"添加职位",optionType:OPTION_TYPE.ADD,self:self});
//			}
//		},{
//			token : '编辑职位',
//			text : Btn_Cfgs.POST_EDIT_BTN_TXT,
//			iconCls:Btn_Cfgs.POST_EDIT_BTN_CLS,
//			tooltip:Btn_Cfgs.POST_EDIT_TIP_BTN_TXT,
//			handler : function(){
//				self.globalMgr.winEdit.show({key:"编辑职位",optionType:OPTION_TYPE.EDIT,self:self});
//			}
//		},{
//			token : '启用职位',
//			text : Btn_Cfgs.POST_ENABLED_BTN_TXT,
//			iconCls:Btn_Cfgs.POST_ENABLED_BTN_CLS,
//			tooltip:Btn_Cfgs.POST_ENABLED_TIP_BTN_TXT,
//			handler : function(){
//				var Node = self.apptree.getSelId();
//				if(Node!=null){
//					if(Node.indexOf("P")==-1){
//						ExtUtil.alert({msg:"请选择职位"});
//						return;
//					}
//					EventManager.enabledData('./sysPost_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
//				}else{
//					ExtUtil.alert({msg:"请选择职位"});
//					return;
//				}
//			}
//		},{
//			token : '禁用职位',
//			text : Btn_Cfgs.POST_DISABLED_BTN_TXT,
//			iconCls:Btn_Cfgs.POST_DISABLED_BTN_CLS,
//			tooltip:Btn_Cfgs.POST_DISABLED_TIP_BTN_TXT,
//			handler : function(){
//				var Node = self.apptree.getSelId();
//				if(Node!=null){
//					if(Node.indexOf("P")==-1){
//						ExtUtil.alert({msg:"请选择职位"});
//						return;
//					}
//					EventManager.disabledData('./sysPost_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
//				}else{
//					ExtUtil.alert({msg:"请选择职位"});
//					return
//				}
//			}
//		},{
//			token : '删除职位',
//			text : Btn_Cfgs.POST_DEL_BTN_TXT,
//			iconCls:Btn_Cfgs.POST_DEL_BTN_CLS,
//			tooltip:Btn_Cfgs.POST_DEL_TIP_BTN_TXT,
//			handler : function(){
//				var Node = self.apptree.getSelId();
//				if(Node!=null){
//					if(Node.indexOf("P")==-1){
//						ExtUtil.alert({msg:"请选择职位！"});
//						return;
//					}
//					EventManager.deleteData('./sysPost_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
//				}else{
//					ExtUtil.alert({msg:"请选择职位！"});
//					return;
//				}
//			}
//		}
		];
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
		if(activeKey){
			if(activeKey == "添加公司"||activeKey == "添加部门"||activeKey == "添加职位"){
				var icon = null;
				if (activeKey == "添加职位"){
					icon = "extlibs/ext-3.3.0/resources/images/icons/award_star_bronze_1-txr.png";
				}else if(activeKey == "添加部门"){
					icon = "extlibs/ext-3.3.0/resources/images/icons/television-txr.png";
				}else if(activeKey == "添加公司"){
					var aff = data.affiliation;
					if(aff==1){
						icon = "extlibs/ext-3.3.0/resources/images/icons/star.png";
					}else{
						icon = "extlibs/ext-3.3.0/resources/images/icons/company.png";
					}
				}
				var cfg = {id : data.id,text : data.name,icon: icon};//获得id和name
				var selId = this.apptree.getSelId();
				if(!selId){
					var company = new Ext.tree.TreeNode(cfg)
					this.apptree.root.appendChild(company);
				}else{
					this.apptree.addChild2CurNode(cfg);
				}
			}else if(activeKey == "编辑公司"||activeKey == "编辑部门"||activeKey == "编辑职位"){
				var curNode = this.apptree.curNode;
				var opTxt = Msg_AppCaption.disabled_text;
				var text =  curNode.text;
				var name = data.name;
				var isenabled = data.isenabled;
				if(!isenabled){
					name = name + opTxt;
				}
				curNode.setText(name);
				if(data.iconCls) curNode.setIcon(data.iconCls);
			}else if(activeKey == "删除公司"||activeKey == "删除部门"||activeKey == "删除职位"){
				this.apptree.curNode.remove();
			}else{
				this.apptree.reload({id:this.apptree.getSelId()});
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
					}case OPTION_TYPE.DEL:{ /* 删除按钮刷新  */
						 this.apptree.curNode.remove();
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
		var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_orglist.action',
		isLoad : true,width:200,enableDD:false,autoScroll :true});
		_apptree.expandAll(); 
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
		 * 当前激活的按钮文本	
		 * @type 
		 */
		msg :null,
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent=_this.apptree;
				parentCfg.parent = parent;
				var selId = parent.getSelId();
				if(selId!=null){
					if(winkey=="编辑公司" && selId.indexOf('C')== -1){
						ExtUtil.alert({msg:"请您选中公司节点再进行编辑！"});
						return;
					} 
					if(winkey=="编辑部门" && selId.indexOf('D')== -1){
						ExtUtil.alert({msg:"请您选中部门节点再进行编辑！"});
						return;
					}
					if(winkey=="编辑职位" && selId.indexOf('P')== -1){
						ExtUtil.alert({msg:"请您选中职位节点再进行编辑！"});
						return;
					}
					var selName = _this.apptree.getCurNode().text;
					if(winkey=="添加公司" && (selId.indexOf('D')!=-1||selId.indexOf('P')!= -1)){
						ExtUtil.alert({msg:"对不起，"+selName+"，不能下面添加公司！"});
						return;
					}
					if(winkey=="添加部门" && (selId.indexOf('P')!= -1||selId.indexOf('root')!= -1)){
						
						ExtUtil.alert({msg:"对不起，"+selName+"不能下面添加部门！"});
						return;
					}
					if(winkey=="添加职位" && selId.indexOf('D')== -1){
						var name = [];
						if(selName.indexOf('【')!=-1){
							name = selName.split("【");
						}else{
							name[0]=selName;
						}
						ExtUtil.alert({msg:"对不起，"+name[0].toString()+"不能下面添加职位！"});
						return;
					}
				}
				if(selId==null){
					if(winkey!="添加公司"){
						ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
						return;
					}
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加公司"||winkey=="编辑公司"){
						winModule="CompanyEdit";
					}else if(winkey=="添加部门"||winkey=="编辑部门"){
						winModule="DepartmentEdit";
					}else if(winkey=="添加职位"||winkey=="编辑职位"){
						winModule="PostEdit";
					}
					Cmw.importPackage('pages/app/sys/org/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
					 	
			  		});
				}
			}
		}
		
	}
});

