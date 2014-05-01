/**
 * 流转路径条件编辑窗口
 * @author smartplatform_auto
 * @date 2013-04-12 01:21:25
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		stint : null,/*启用条件限制*/
		limitVals : null,/*限制值*/
		tranName : null,/*流转方向名称*/
		currNodeName : null,/*当前节点名称*/
		idMgr : {
			fstUserId : Ext.id(null,'fstUserId'),
			fstRoleId : Ext.id(null,'fstRoleId'),
			currNodeSpanId : Ext.id(null,'currNodeSpanId'),
			transNameSpanId : Ext.id(null,'transNameSpanId'),
			currNodeUserSpanId : Ext.id(null,'currNodeUserSpanId'),
			transNameUserSpanId : Ext.id(null,'transNameUserSpanId')
		},
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
			this.stint = parentCfg.stint;
			this.limitVals = parentCfg.limitVals;
			this.tranName = parentCfg.tranName;
			this.currNodeName = parentCfg.currNodeName;
		},
		createAppWindow : function(){
			var _this = this;
			this.appFrm = this.createAppFrm();
			this.appWin = new Ext.ux.window.MyWindow({width:600,height:200,title:'条件配置信息编辑&nbsp;&nbsp;<span style="color:red;">[配置条件发生更改后，须点击“保存配置按钮才能生效”]</span>',
			items:[this.appFrm],
			listeners : {'hide':function(win){
				_this.resetData(win);
			}}
			});
		},
		/**
		 * 创建表单面板
		 */
		createAppFrm : function(){
			var _this = this;
			var rad_stint = FormUtil.getRadioGroup({
			 				fieldLabel : '条件限制类型',name:'stint',allowBlank : false,width:500,
							items : [{boxLabel : '无条件限制', name:'stint',inputValue:1, checked: true},
									{boxLabel : '按角色限制', name:'stint',inputValue:2},
									{boxLabel : '按用户限制', name:'stint',inputValue:3}],
							listeners:{
								change : function(rdgp,checked){
									var val = rdgp.getValue();
									_this.showFieldSet(val);
								}
							}});
			var cbog_users = ComboxControl.getUserCboGrid({fieldLabel:'用户列表',name:'userIds',width:500,hideLabel:true,isCheck:true,callback:function(cboGrid,vals){
					_this.setLimitVals(vals);			
				}});
			var cbog_roles = ComboxControl.getRoleCboGrid({fieldLabel:'角色列表',name:'roleIds',width:500,hideLabel:true,isCheck:true,callback:function(cboGrid,vals){
					_this.setLimitVals(vals);			
				}});
		
			var titleUsers = '当'+this.getSpanHtml(this.idMgr.currNodeUserSpanId)+'环节用户属于以下范围时，才有'
						+this.getSpanHtml(this.idMgr.transNameUserSpanId)+'的权限';
			var fst_users = FormUtil.getFieldSet({id:this.idMgr.fstUserId,title:titleUsers,items:[cbog_users],hidden:true});
			
			var titleRoles = '当'+this.getSpanHtml(this.idMgr.currNodeSpanId)+'环节用户的角色属于以下范围时，才有'
						+this.getSpanHtml(this.idMgr.transNameSpanId)+'的权限';
			var fst_roles = FormUtil.getFieldSet({id:this.idMgr.fstRoleId,title:titleRoles,items:[cbog_roles],hidden:true});
			var buttons = this.createButtons();
			var appFrm = FormUtil.createFrm({
							title : '流程走向条件限制信息编辑',url:'./sysMenu_save.action',labelWidth:100,
							items:[rad_stint,fst_users,fst_roles],
							buttonAlign : 'center',
							buttons : buttons
				});
			return appFrm;
		},
		/**
		 * 设置限制值
		 */
		setLimitVals : function(vals){
			if(!vals){
				this.limitVals = null;
			}else{
				var arr = vals.split("##");
				if(null == arr || arr.length == 0){
					this.limitVals = null;
				}else{
					this.limitVals = arr[0];
				}
			}
		},
		/**
		 * 根据 条件限制类型 显示角色或用户FieldSet对象
		 */
		showFieldSet : function(stint){
			var roleShow = false;
			var userShow = false;
			switch(stint){
				case 1 :{/*按用户限制*/
					roleShow = false;
					userShow = false;
					break;
				}case 2 :{/*按角色限制*/
					roleShow = true;
					userShow = false;
					break;
				}case 3 :{/*按用户限制*/
					roleShow = false;
					userShow = true;
					break;
				}
			}
			var fstRole = Ext.getCmp(this.idMgr.fstRoleId);
			var fstUser = Ext.getCmp(this.idMgr.fstUserId);
			fstRole.setVisible(roleShow);
			fstUser.setVisible(userShow);
			if(roleShow){
				Ext.get(this.idMgr.currNodeSpanId).update(this.currNodeName);
				Ext.get(this.idMgr.transNameSpanId).update(this.tranName);
			}
			if(userShow){
				Ext.get(this.idMgr.currNodeUserSpanId).update(this.currNodeName);
				Ext.get(this.idMgr.transNameUserSpanId).update(this.tranName);
			}
			this.stint = stint;
			this.limitVals = null;
			var cbog_users = this.appFrm.findFieldByName('userIds');
			var cbog_roles = this.appFrm.findFieldByName('roleIds');
			cbog_users.reset();
			cbog_roles.reset();
		},
		getSpanHtml : function(spanId){
			return '<span style="color:red;">[<span id="'+spanId+'"></span>]</span>';
		},
		createButtons : function(){
			var _this = this;
			var parentCfg = this.parentCfg;
			var btnSave = new Ext.Button({text:'确定',handler:function(){
				if(!parentCfg.callback){
					ExtUtil.alert({msg:'必须提供 callback 回调函数!'});
					return;
				}
				var stintVal =_this.stint;
				if(!stintVal){
					ExtUtil.alert({msg:'条件限制类型不能为空!'});
					return;
				}
				switch(stintVal){
					case 2 :{
						var roleIds = _this.appFrm.getValueByName('roleIds');
						if(!roleIds){
							ExtUtil.alert({msg:'限制的角色不能为空!'});
							return;
						}
						break;
					}case 3 :{
						var userIds = _this.appFrm.getValueByName('userIds');
						if(!userIds){
							ExtUtil.alert({msg:'限制的用户不能为空!'});
							return;
						}
						break;
					}
				}
				var limitVals = _this.limitVals
				var formData = {stint:stintVal,limitVals:limitVals};
				var flag = parentCfg.callback(formData);
				if(flag) _this.appWin.hide();
			}});
			var btnClose = new Ext.Button({text:'关闭',handler:function(){
				_this.appWin.hide();
			}});
			return [btnSave,btnClose];
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			this.appWin.show(this.parent.getEl());
			this.setValue();
		},
		setValue : function(){
			this.resetData();
			this.appFrm.setFieldValues({stint:this.stint});
			var stintVal =this.stint;
			var cbogObj = null;
			var url = null;
			var ids = this.limitVals;
			var idKey = null;
			var nameKey = null;
			if(!ids) return;
			switch(stintVal){
				case 2 :{/*角色*/
					idKey = "id";
					nameKey = "name";
					cbogObj = this.appFrm.findFieldByName('roleIds');
					url = './sysRole_list.action';
					var fstRole = Ext.getCmp(this.idMgr.fstRoleId);
					fstRole.show();
					break;
				}case 3 :{/*用户*/
					idKey = "userId";
					nameKey = "userName";
					cbogObj = this.appFrm.findFieldByName('userIds');
					url = './sysUser_list.action';
					var fstUser = Ext.getCmp(this.idMgr.fstUserId);
					fstUser.show();
					break;
				}
			}
			EventManager.get(url,{params:{ids:ids},sfn:function(json_data){
				 if(!json_data || json_data.totalSize == 0) return;
				 var list = json_data.list;
				 var idArr = [];
				 var nameArr = [];
				 for(var i=0,count=list.length; i<count; i++){
				 	var data = list[i];
				 	idArr[i] = data[idKey];
				 	nameArr[i] = data[nameKey];
				 }
				 var val = idArr.join(",")+"##"+nameArr.join(",");
				 cbogObj.setValue(val);
			}});
		},
		/**
		 * 重置
		 */
		resetData : function(win){
			this.appFrm.reset();
			var fstRole = Ext.getCmp(this.idMgr.fstRoleId);
			var fstUser = Ext.getCmp(this.idMgr.fstUserId);
			fstRole.hide();
			fstUser.hide();
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if(this.appFrm) this.appFrm = null;
			if(this.appWin){
				this.appWin.close();	//关闭并销毁窗口
				this.appWin = null;		//释放当前窗口对象引用
			}
		}
	}
})