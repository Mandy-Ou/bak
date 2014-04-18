/**
 * 修改初始密码页面
 * @author 彭登浩
 * @date 2012-11-20
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		params : null,
		fset_roleset : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			this.params =  parentCfg.params;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.MyWindow({width:300,height : 160,title : '修改密码',closable:true,//getUrls:this.getUrls,
			items:[this.appFrm]});
		},
		show : function(_parentCfg){
			this.setParentCfg(_parentCfg)
			this.createAppWindow();
			this.appWin.show(this);
		},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		},
		
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var _this = this;
			
			/*用戶名*/
			var txt_userName = FormUtil.getReadTxtField({fieldLabel : Msg_LoginWindow.field_userName,name:'userName',width:125,allowBank : false,value:_this.params.userName});
			/*设置密碼*/
			var txt_oldpassWord = FormUtil.getTxtField({fieldLabel :NEED+Msg_LoginWindow.field_oldpassWord	,name:'oldpassWord',
			width:125,inputType : 'password',allowBank : false,enableKeyEvents:true	,
			listeners : { 
		        	 keyup:function(password, e){
                     if(e.getKey() == 13){
                     	_this.changeUser(_this,appform);
                     }}}
			});
			/*设置密碼*/
			var txt_setPassWord = FormUtil.getTxtField({fieldLabel :NEED+Msg_LoginWindow.field_SetPassWord,name:'passWord',
			width:125,inputType : 'password',allowBank : false,enableKeyEvents:true,minLength :6,
			listeners : { 	
		        	 keyup:function(password, e){
                     if(e.getKey() == 13){
                     	_this.changeUser(_this,appform);
                     }}}
			});
			var btnSave = new Ext.Button({text : '确定',handler : function(){
				_this.changeUser(_this,appform);
	}});
	
	
	var btnReset = new Ext.Button({text:Msg_LoginWindow.button_clear,handler : function(){appform.getForm().reset()}});
	 var btnPanel = this.getBtnPanel([btnSave,btnReset]);
			var layout_fields  = [txt_userName,txt_oldpassWord,txt_setPassWord,btnPanel];
			var frm_cfg = {height:80};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
			return appform;
		},
		getBtnPanel:function (buttons){
			var panel = new Ext.Panel({ buttonAlign : 'center',buttons : buttons});
			return panel;
		},
		changeUser : function(_this,appform){
			if(!appform.getForm().isValid()){
							ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:Msg_SysTip.msg_nopassdata});
							return;
						}
						var oldpassWord = appform.getValueByName("oldpassWord");
						var passWord = appform.getValueByName("passWord");
						if(!oldpassWord){
							 ExtUtil.warn({msg:'请输入旧密码！'});
							 return;
						}
						if(!passWord){
							 ExtUtil.warn({msg:'请输入新密码！'});
							 return;
						}
						if(oldpassWord!=111){
							ExtUtil.warn({msg:'旧密码输入错误！'});
							appform.reset("userName"); 
							 return;
						}
						if(passWord == oldpassWord){
							appform.reset("userName"); 
							 ExtUtil.warn({msg:'请输入密码不能与初始密码相同！'});
							 return;
						}
						if(passWord.length<6){
							appform.reset("userName"); 
							 ExtUtil.warn({msg:'请输入新密码不能少于6位！'});
							 return;
						}
						var vals = null;
						if(appform){
							//获取表单值
						  	vals = appform.getForm().getValues(true);
						}
						EventManager.get("./sysUser_savePassWord.action",{params:vals,sfn:function(json_data){
							ExtUtil.alert({msg:"密码修改成功，下次登录请用新密码！",fn:function(){
								_this.appWin.hide();
							}});
						}});
		}
	};
	
});