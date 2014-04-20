/**
 * 封装 Ext Window 支持传入不同类型
 * 返回一个带有上传功能的 Windows 控件
 */
Ext.namespace("Ext.ux.window");

Ext.ux.window.MyWindow = Ext.extend(Ext.Window,{
	title : '信息编辑',
	layout : 'fit',	//布局为自适应
	isHidden : true,
	//height : 450,
	modal : true,		// true 后面的东西不能点击 
	listeners : EventManager.attachWinZindexListeners(),
	constrain : true,	//true : 限制窗口的整体不会越过浏览器边界
	constrainHeader : false,	//true : 只保证窗口的顶部不会越过浏览器边界，而窗口的内容部分可以超出浏览器的边界
	closeAction : 'hide',
	maximizable : false, //是否启用最大化按钮 true: 启用 false : 禁用
	minimizable : false,
	draggable : true//是否支持拖动 true : 可拖动 false : 禁止拖动
});

//注册成xtype以便能够延迟加载   
Ext.reg('mywindow', Ext.ux.window.MyWindow);  