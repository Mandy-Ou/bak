Ext.namespace("skythink.cmw.sys");
/**
 * 流程权限配置管理 UI
 * @author cmw
 * @date 2013-12-24
 * */ 
skythink.cmw.sys.ProcRightMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.ProcRightMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreePanelView({
			tab : tab,
			params : params,
			getToolBar : this.getToolBar,
			getAppPanel : this.getAppPanel,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
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
			tooltip:'删除指定角色的所有流程权限',
			handler : function(){
				var sysId = _this.globalMgr.sysId;
				var currNode = _this.apptree.curNode;
				var role = currNode.text;
				var roleId = currNode.id;
				if(!role){
					ExtUtil.alert({msg:'要删除权限，请从左边树中选择相应的角色!'});
					return;
				}
				
				ExtUtil.confirm({msg:'确定删除角色【'+role+'】的流程权限数据?',
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
						Cmw.mask(_this,'正在删除流程权限数据...');
						EventManager.get('./oaProcRight_delete.action',cfg);
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
		var _this = this;
		var appPanel = new Ext.Panel({border:false,title:'流程权限配置',style:'padding:5px;padding-top:0px;margin:0px;'});
		appPanel.addListener('render',function(pnl){
			_this.globalMgr.reload(_this);
		});
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
	
	globalMgr : {
		chkgroupId_Prefix : 'bussProc_chkgroupId',
		chkgroupIdArr : null,
		sysId : null,/*系统ID*/
		/**
		 * 加载模块数据
		 * @param {} params
		 * @param {} callback
		 */
		reload : function(_this){
			var cfg = {
				params : {},
				sfn : function(json_data){
					_this.globalMgr.addFieldSets(_this, json_data);
					Cmw.unmask(_this);
				},ffn : function(){
					Cmw.unmask(_this);
				}
			};
			Cmw.mask(_this,'正在加载流程数据...');
			EventManager.get('./oaProcType_query.action',cfg);
		},
		addFieldSets : function(_this, json_data){
			this.clearAll(_this);
			this.chkgroupIdArr = [];
			var list = json_data.list;
			if(!list || list.length == 0) return;
			for(var j=0,len=list.length; j<len; j++){
				var fstData = list[j];
				var fstId = fstData.id;
				var fstName = fstData.name;
				var datas = fstData.datas;
				var groupItems = [];
				if(datas && datas.length > 0){
					for(var i=0,count=datas.length; i<count; i++){
						var data = datas[i];
						var pcId = data.id;
						var name = data.name;
						var data = {boxLabel : name, inputValue : pcId};
						groupItems[groupItems.length] = data;
					}
				}
				_this.globalMgr.addFieldSet2Form(_this,fstName,fstId,groupItems);
			}
			_this.apppanel.doLayout();
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
			var id = _this.globalMgr.chkgroupId_Prefix+"_"+name;
			this.chkgroupIdArr[this.chkgroupIdArr.length] = id;
			var checkBoxGroup =  FormUtil.getCheckGroup({fieldLabel : title,id:id,name:name,hideLabel:true,autoWidth:true,columns:5,items : items});
			
			var fieldSet = FormUtil.getFieldSet({title:title,items : checkBoxGroup});
			_this.apppanel.add(fieldSet);
		},
		/**
		 * 保存角色权限
		 */
		saveRoleRights : function(_this,callback){
			var roleId = _this.apptree.getSelId();
			if(!roleId){
				ExtUtil.alert({msg:'请从左边树中选择要配置流程权限的角色！'});
				return;
			}
			var idArr = [];
			var chkgroupIdArr = this.chkgroupIdArr;
			for(var i=0,count=chkgroupIdArr.length; i<count; i++){
				var opGroup = Ext.getCmp(chkgroupIdArr[i]);
				var val = opGroup.getValue();
				if(val) idArr[idArr.length] = val;
			}
			var checkIds = (null == idArr || idArr.length == 0) ? null : idArr.join(",");
			if(!checkIds || checkIds.length == 0){
				ExtUtil.alert({msg:'请从右边勾选相应的流程权限！'});
				return;
			}
			var role = _this.apptree.getSelText();
			var sysId = _this.globalMgr.sysId;
			var cfg = {params:{roleId:roleId,bussProccIds : checkIds},
			sfn:function(json_data){
				if(callback) callback();
				Cmw.unmask(_this);
				Ext.tip.msg("提示","“"+role+"”的流程权限数据保存成功！");
			},
			ffn : function(json_data){
				Cmw.unmask(_this);
			}};
		
			Cmw.mask(_this,"正在保存“"+role+"”的流程权限数据，请稍等...");
			EventManager.get('./oaProcRight_save.action',cfg);
		},
		/**
		 * 选中当前角色相应的权限
		 * @param {} node
		 */
		checkRight : function(node,_this){
			var roleId = node.id;
			if(roleId.indexOf("root_") != -1) return;
			var cfg = {params:{roleId:roleId},
			sfn:function(json_data){
				_this.globalMgr.selectCheckBox(_this, json_data);	
				Cmw.unmask(_this);
			},
			ffn : function(json_data){
				Cmw.unmask(_this);
			}};
			
			var role = node.text;
			Cmw.mask(_this,"正在加载“"+role+"”的权限数据，请稍等...");
			EventManager.get('./oaProcRight_list.action',cfg);
		},
		selectCheckBox : function(_this,json_data){
			_this.apppanel.checkState = false;
			var roleMods = json_data.list;
			var idArr = [];
			if(roleMods && roleMods.length > 0){
				for(var i=0,count=roleMods.length; i<count; i++){
					var roleMod = roleMods[i];
					idArr[i] = roleMod.bussProccId;
				}
			}
			var ids = idArr.join(",");
			var chkgroupIdArr = this.chkgroupIdArr;
			for(var i=0,count=chkgroupIdArr.length; i<count; i++){
				var opGroup = Ext.getCmp(chkgroupIdArr[i]);
				opGroup.reset();
				opGroup.setValue(ids);
			}
		}
	}
});

