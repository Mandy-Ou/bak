Ext.namespace("skythink.cmw.sys");
/**
 * 桌面权限配置管理 UI
 * @author cmw
 * @date 2012-11-02
 * */ 
skythink.cmw.sys.RoleModMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.RoleModMgr,Ext.util.MyObservable,{
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
		var _this = this;
		var barItems = [{
			token : "保存权限",
			text : '保存权限',
			iconCls:'page_add',
			tooltip:'保存权限',
			handler : function(){
				_this.globalMgr.saveRoleRights(_this);
			}
		},{
			token : "删除权限",
			text : '删除权限',
			iconCls:'page_delete',
			tooltip:'删除指定角色的所有桌面模块权限',
			handler : function(){
				var sysId = _this.globalMgr.sysId;
				var currNode = _this.apptree.curNode;
				var role = currNode.text;
				var roleId = currNode.id;
				if(!role){
					ExtUtil.alert({msg:'要删除权限，请从左边树中选择相应的角色!'});
					return;
				}
				
				ExtUtil.confirm({msg:'确定删除角色【'+role+'】的桌面模块权限数据?',
					fn:function(btn){
						if(!btn || btn != 'yes') return;
						var params = {roleId : roleId, sysId : sysId};
						var cfg = {
							params : params,
							sfn : function(json_data){
								  Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_dataSucess);
								 _this.globalMgr.checkRight(currNode, _this);
								Cmw.unmask(_this);
							},ffn : function(){
								Cmw.unmask(_this);
							}
						};
						Cmw.mask(_this,'正在删除桌面模块数据...');
						EventManager.get('./sysRoleMod_delete.action',cfg);
				}});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppPanel : function(){
		var appPanel = new Ext.Panel({border:false,title:'桌面模块权限配置',style:'padding:5px;padding-top:0px;margin:0px;'});
		return appPanel;
	},
	/**
	 * 当新增、修改、删除、起用、禁用 
	 * 数据保存成功后会执行此方法刷新父页面
	 * @param optionType 操作类型 参考 constant.js 文件中的 "OPTION_TYPE" 常量值
	 * @param {} data 
	 */
	refresh:function(optionType,data){
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({url:'./sysTree_rolelist.action',isLoad : true,rootText : '角色列表',width:200});
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
		var _this = this;
		var sysId = data.id;
		var curNode = this.apptree.curNode;
		var checkState = this.apppanel.checkState;
		_this.globalMgr.sysId = sysId;
		if(!curNode){
			this.globalMgr.reload(_this,{sysId:sysId});
		}else{
			this.globalMgr.reload(_this,{sysId:sysId,roleId : curNode.id});
		}
	},
	/**
	 * 当点击顶部的 系统图标 时，会触发 notify 方法。
	 * @param {} data 选中的 系统图标数据。
	 * 			{id : '',name:'',code:'',url:''} 等。
	 * 			具体值请看 SystemEntity 的 getFields() 方法
	 */
	
	globalMgr : {
		chkgroupId_Prefix : 'chkgroupId',
		sysId : null,/*系统ID*/
		/**
		 * 加载模块数据
		 * @param {} params
		 * @param {} callback
		 */
		reload : function(_this, params, callback){
			var cfg = {
				params : params,
				sfn : function(json_data){
					_this.globalMgr.addFieldSets(_this, json_data);
					if(callback) callback();
					Cmw.unmask(_this);
				},ffn : function(){
					Cmw.unmask(_this);
				}
			};
			Cmw.mask(_this,'正在加载桌面模块数据...');
			EventManager.get('./sysRoleMod_list.action',cfg);
		},
		addFieldSets : function(_this, json_data){
			var chkMustName = 'mustMods';
			var chkOpName = 'opMods';
			this.clearAll(_this);
			var deskMods = json_data.deskMods;
			if(!deskMods || deskMods.length == 0) return;
			var mustItems = [];/*必选*/
			var opItems = [];/*可选*/
			for(var i=0,count=deskMods.length; i<count; i++){
				var deskMod = deskMods[i];
				var isdefault = deskMod.isdefault;
				var title = deskMod.title;
				var code = deskMod.code;
				var data = {boxLabel : title, inputValue : code};
				if(isdefault == 1){/*默认*/
					data.name = chkMustName;
					data.checked = true;
					data.disabled = true;
					mustItems[mustItems.length] = data;
				}else{
					data.name = chkOpName;
					opItems[opItems.length] = data;
				}
			}
			_this.globalMgr.addFieldSet2Form(_this,'默认必配桌面模块',chkMustName,mustItems);
			_this.globalMgr.addFieldSet2Form(_this,'选配桌面模块',chkOpName,opItems);
			_this.apppanel.doLayout();
			var roleMods = json_data.roleMods;
			if(null == roleMods || roleMods.length == 0) return;
			var codes = [];
			for(var i=0,count=roleMods.length; i<count; i++){
				var roleMod = roleMods[i];
				codes[i] = roleMod.code;
			}
			var opGroup = Ext.getCmp(_this.globalMgr.chkgroupId_Prefix+"_"+chkOpName+_this.globalMgr.sysId);
			if(opGroup){
				opGroup.setValue(codes.join(","));
			}
		},
		/**
		 * 清除所有
		 * @param {} _this
		 */
		clearAll : function(_this){
			var fieldSetArr = _this.apppanel.findByType("fieldset");
			if(!fieldSetArr || fieldSetArr.length == 0) return;
			for(var i=0,count=fieldSetArr.length; i<count; i++){
				var fieldSetObj = fieldSetArr[i];
				_this.apppanel.remove(fieldSetObj);
			}
		},
		addFieldSet2Form : function(_this, title, name, items){
			if(!items || items.length == 0) return;
			var id = _this.globalMgr.chkgroupId_Prefix+"_"+name+_this.globalMgr.sysId;
			var checkBoxGroup =  FormUtil.getCheckGroup({fieldLabel : title,id:id,name:name,hideLabel:true,autoWidth:true,columns:5,items : items,
			listeners : {'change':function(group,checked ){
				var curNode = _this.apptree.curNode;
				if(curNode) _this.apppanel.checkState = true;
			}}});
			
			var fieldSet = FormUtil.getFieldSet({title:title,items : checkBoxGroup});
			_this.apppanel.add(fieldSet);
		},
		/**
		 * 保存角色权限
		 */
		saveRoleRights : function(_this,callback){
			var roleId = _this.apptree.getSelId();
			if(!roleId){
				ExtUtil.alert({msg:'请从左边树中选择要配置桌面模块权限的角色！'});
				return;
			}
			var opGroup = Ext.getCmp(_this.globalMgr.chkgroupId_Prefix+"_opMods"+_this.globalMgr.sysId);
			if(!opGroup) return;
			var checkIds = opGroup.getValue();
			if(!checkIds || checkIds.length == 0){
				ExtUtil.alert({msg:'请从右边权限树中勾选相应的桌面模块权限！'});
				return;
			}
			var mustIds = null;
			var mustGroup = Ext.getCmp(_this.globalMgr.chkgroupId_Prefix+"_mustMods"+_this.globalMgr.sysId);
			if(mustGroup){
				mustGroup.enable();
				mustIds = mustGroup.getValue();
				mustGroup.disable();
			}
			var modeCodes = mustIds ? mustIds +','+ checkIds : checkIds;
			var role = _this.apptree.getSelText();
			var sysId = _this.globalMgr.sysId;
			var cfg = {params:{sysId:sysId,roleId:roleId,modeCodes : modeCodes},
			sfn:function(json_data){
				if(callback) callback();
				Cmw.unmask(_this);
				Ext.tip.msg("提示","“"+role+"”的桌面权限数据保存成功！");
			},
			ffn : function(json_data){
				Cmw.unmask(_this);
			}};
		
			Cmw.mask(_this,"正在保存“"+role+"”的桌面权限数据，请稍等...");
			EventManager.get('./sysRoleMod_save.action',cfg);
		},
		/**
		 * 选中当前角色相应的权限
		 * @param {} node
		 */
		checkRight : function(node,_this){
			var roleId = node.id;
			if(roleId.indexOf("root_") != -1) return;
			var cfg = {params:{roleId:roleId,sysId:_this.globalMgr.sysId},
			sfn:function(json_data){
				_this.globalMgr.selectCheckBox(_this, json_data);	
				Cmw.unmask(_this);
			},
			ffn : function(json_data){
				Cmw.unmask(_this);
			}};
			
			var role = node.text;
			Cmw.mask(_this,"正在加载“"+role+"”的权限数据，请稍等...");
			EventManager.get('./sysRoleMod_get.action',cfg);
		},
		selectCheckBox : function(_this,json_data){
			_this.apppanel.checkState = false;
			var roleMods = json_data.roleMods;
			var codes = [];
			if(roleMods && roleMods.length > 0){
				for(var i=0,count=roleMods.length; i<count; i++){
					var roleMod = roleMods[i];
					codes[i] = roleMod.modCode;
				}
			}
			var opGroup = Ext.getCmp(_this.globalMgr.chkgroupId_Prefix+"_opMods"+_this.globalMgr.sysId);
			if(opGroup){
				if(codes && codes.length > 0){
					opGroup.setValue(codes.join(","));
				}else{
					opGroup.reset();
				}
			}
		}
	}
});

