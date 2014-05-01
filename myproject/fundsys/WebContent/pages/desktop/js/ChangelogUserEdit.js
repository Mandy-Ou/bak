/**
 * 基础数据新增或修改页面
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
		fset_roleset : null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.MyWindow({width:300,height : 140,title : '切换用户',//getUrls:this.getUrls,
			items:[this.appFrm]});
		},
		show : function(_parentCfg){
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
			var txt_userName = FormUtil.getTxtField({fieldLabel : NEED+Msg_LoginWindow.field_userName,name:'userName',width:125,allowBank : false});
			/*，密碼*/
			var txt_passWord = FormUtil.getTxtField({fieldLabel :NEED+Msg_LoginWindow.field_passWord	,name:'passWord',
			width:125,inputType : 'password',allowBank : false,enableKeyEvents:true,
			listeners : { 
		        	 keyup:function(password, e){
                     if(e.getKey() == 13){
                     	_this.changeUser(appform);
                     }}}
			});
			
			var btnSave = new Ext.Button({text : '切换',handler : function(){
				_this.changeUser(appform);
	}});
	
	
	var btnReset = new Ext.Button({text:Msg_LoginWindow.button_clear,handler : function(){appform.getForm().reset()}});
	 var btnPanel = this.getBtnPanel([btnSave,btnReset]);
			var layout_fields  = [txt_userName,txt_passWord,btnPanel];
			var frm_cfg = {height:80};
		    var appform = FormUtil.createLayoutFrm(frm_cfg,layout_fields);
			return appform;
		},
		getBtnPanel:function (buttons){
			var panel = new Ext.Panel({ buttonAlign : 'center',buttons : buttons});
			return panel;
		},
		changeUser : function(appform){
			if(!appform.getForm().isValid()){
							ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:Msg_SysTip.msg_nopassdata});
							return;
						}
						var userName = appform.getValueByName("userName");
						if(userName == CURENT_USER){
							 ExtUtil.alert({title:'',msg:'输入的是当前用户,不需要切换！'});
							 return;
						}
						var progress = ExtUtil.progress(); //显示加载中的进度条
						var vals = null;
						if(appform){
							//获取表单值
						  	vals = appform.getForm().getValues(true);
						}
						
						var action = {params:vals};
						action.sfn = function(data){
//							var uuid = Ext.getCmp('uuid');
//							if(uuid) uuid = uuid.getValue();
							//var urlstr = BASE_PATH+'/pages/main.jsp?uuid='+uuid;
							var urlstr = BASE_PATH+'/index.jsp?TXR_SWITCHINGSYSTEM=TXR_SWITCHINGSYSTEM';
							window.location.href = urlstr;
						};
						action.ffn = function(data){
							progress.hide();	//失败则隐藏进度条
							ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:data.msg});
						};
						EventManager.get("./login.action",action);
		}
	};
	
});