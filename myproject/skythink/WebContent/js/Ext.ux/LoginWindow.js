/**
 * 封装 Ext Window 支持传入不同类型
 * 返回一个带有上传功能的 Windows 控件
 */
Ext.namespace("Ext.ux.window");
Ext.ux.window.LoginWindow = Ext.extend(Ext.Window,{
	title : Msg_LoginWindow.win_title,
	url : '',	//服务器URL
	layout : 'fit',	//布局为自适应
	isHidden : true,
	width : 450,
	height :280,
	modal : true,		// true 后面的东西不能点击 
	constrain : true,	//true : 限制窗口的整体不会越过浏览器边界
	constrainHeader : true,	//true : 只保证窗口的顶部不会越过浏览器边界，而窗口的内容部分可以超出浏览器的边界
	closable : false,
	border : false,
	//closeAction : 'hide',
	maximizable : false, //是否启用最大化按钮 true: 启用 false : 禁用
	minimizable : false,
	draggable : false,//是否支持拖动 true : 可拖动 false : 禁止拖动
	cookies : Ext.util.Cookies, //初始化 cookie 对象
	initComponent : function(){	//初始化控件
		Ext.ux.window.LoginWindow.superclass.initComponent.call(this);
		this.initTip();
		var frm = this.createFrm();
		this.add(frm);
	},
	/**
	 * Form 验证提示
	 */
	initTip:function(){
		Ext.form.Field.prototype.msgTarget = 'qtip';
		Ext.QuickTips.init();
	},
		Login:function(frm){
				if(!frm.getForm().isValid()){
							ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:Msg_SysTip.msg_nopassdata});
							return;
						}
						var progress = ExtUtil.progress(); //显示加载中的进度条
						var vals = null;
						if(frm){
							//获取表单值
						  	vals = frm.getForm().getValues(true);
						}
						var action = {params:vals};
						action.sfn = function(data){
							var uuid = Ext.getCmp('uuid');
							if(uuid) uuid = uuid.getValue();
							//var urlstr = BASE_PATH+'/pages/main.jsp?uuid='+uuid;
							var urlstr = BASE_PATH+'/index.jsp';
							window.location.href = urlstr;
						};
						action.ffn = function(data){
							progress.hide();	//失败则隐藏进度条
							ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:data.msg});
						};
						EventManager.get("./login.action",action);
			},
	/**
	 * 创建 表单Form
	 * @return {}
	 */
	createFrm : function(){
		var _this = this;
		//currTheme currLanguage
		var currTheme = this.cookies.get("currTheme");
		var currLanguage = this.cookies.get("currLanguage");
		var frm =  new Ext.form.FormPanel({
			frame : true,
			autoTabs : true,
			activeTab : 0,
			deferredRender:false,
	        border:false,
			items : [{
				id : 'login_mainTabPanel',
				xtype : 'tabpanel',
				activeTab : 0,
				defaults : {autoHeight : true,bodyStyle : 'padding:10px'},
				items : [
				{title : Msg_LoginWindow.tab_one_title,labelAlign:'right',defaultType:'textfield',
				layout:'form',width:300,frame : true,buttonAlign:'center',
				items:[
				{xtype:'hidden',id:'uuid',name:'uuid',value : new Date().getTime()},
				{fieldLabel : NEED+Msg_LoginWindow.field_userName,name:'userName',width : 250,allowBank : false,cls:'user',value:'admin'
//				,style:{
//					background :'#fffffc url(extlibs/ext-3.3.0/resources/images/icons/role.png) no-repeat left center', paddingLeft: '20px' 
//				}
				},
				{fieldLabel : NEED+Msg_LoginWindow.field_passWord,name:'passWord',width : 250,allowBank : false,inputType : 'password',cls:'key',value:'123456',
				enableKeyEvents:true,
				listeners: {
					
				},
				 listeners : { 
		        	 keyup:function(password, e){
                     if(e.getKey() == 13){
						if(!frm.getForm().isValid()){
							ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:Msg_SysTip.msg_nopassdata});
							return;
						}
						var progress = ExtUtil.progress(); //显示加载中的进度条
						var vals = null;
						if(frm){
							//获取表单值
						  	vals = frm.getForm().getValues(true);
						}
						var action = {params:vals};
						action.sfn = function(data){
							var uuid = Ext.getCmp('uuid');
							if(uuid) uuid = uuid.getValue();
							//var urlstr = BASE_PATH+'/pages/main.jsp?uuid='+uuid;
							var urlstr = BASE_PATH+'/index.jsp';
							window.location.href = urlstr;
						};
						action.ffn = function(data){
							progress.hide();	//失败则隐藏进度条
							ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:data.msg});
						};
						EventManager.get("./login.action",action);
                     }}}
				},
				/*{fieldLabel : NEED+Msg_LoginWindow.field_code,xtype : 'compositefield',
				items : [
						{xtype : 'textfield',name : 'code',allowBank : false,width:80,cls:'key'},
						{xtype : 'compositefield',width : 170,items : [
						{xtype : 'displayfield',value : '<img alt="" src="./servlet/AuthorImage" >',width : 70},
						{xtype : 'checkbox',boxLabel : Msg_LoginWindow.field_isaudot,name : 'isaudot',width : 90}]}
						]
				},*/{
					xtype : 'combo',fieldLabel : Msg_LoginWindow.field_themeSelect,name : 'themeSelect',mode : 'local',
					displayField : 'name',valueField : 'id',triggerAction : 'all',value : currTheme || 'calista',
					store :FormUtil.getSimpleStore(Msg_LoginWindow.store_themeSelect),
					listeners : {'select':function(combo,record,index ){
						var val = record.data["id"];
						AppDef.setStyle(val);
					}}
				},{
					xtype : 'combo',fieldLabel : Msg_LoginWindow.field_language,name : 'language',mode : 'local',
					displayField : 'name',valueField : 'id',triggerAction : 'all',value : 'zh_CN',
					store : FormUtil.getSimpleStore(Msg_LoginWindow.store_language),
					listeners : {'select':function(combo,record,index ){
						var val = record.data["id"];	
						AppDef.setLanguage(val);
					}}
				}],
				buttons : [{
					text:Msg_LoginWindow.button_login,
					enableKeyEvents:true,
//					key : 'Ctrl+L',
//					id : 'login',
//					listeners : {"afterrender":function(){
//						FKeyMgr.setkey(this);
//					}},
					handler : function(){
						if(!frm.getForm().isValid()){
							ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:Msg_SysTip.msg_nopassdata});
							return;
						}
						var progress = ExtUtil.progress(); //显示加载中的进度条
						var vals = null;
						if(frm){
							//获取表单值
						  	vals = frm.getForm().getValues(true);
						}
						var action = {params:vals};
						action.sfn = function(data){
							var uuid = Ext.getCmp('uuid');
							if(uuid) uuid = uuid.getValue();
							//var urlstr = BASE_PATH+'/pages/main.jsp?uuid='+uuid;
							var urlstr = BASE_PATH+'/index.jsp';
							window.location.href = urlstr;
						};
						action.ffn = function(data){
							progress.hide();	//失败则隐藏进度条
							ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:data.msg});
						};
						EventManager.get("./login.action",action);
					}
				},{
					text:Msg_LoginWindow.button_clear,
//					key : 'Ctrl+C',
//					listeners : {"afterrender":function(){
//						FKeyMgr.setkey(this);
//					}},
					handler : function(){
						frm.getForm().reset();
					}
				}]
				},{
				title : Msg_LoginWindow.tab_two_title,frame : true,defaults: {width: 230},
				html: Msg_LoginWindow.tab_two_html
                }
				]
			}
			]
		});
		
		return frm;
	}
});
