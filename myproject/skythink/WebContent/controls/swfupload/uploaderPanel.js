/*
	If all uploads succeed: {"success":true}
	If an upload fails: {"success":false,"error":"Reason for error!"}
*/
var keel={};

keel.UploadPanel = function(cfg){
	this.delUrl = './sysAttachment_delete.action';/*删除的URL*/
	this.hasUpload = false;/*标识有上传个附件*/
	this.closeCallback = null;
	this.width = 510;
	this.height = 200;
	Ext.apply(this,cfg);	
	this.gp = new Ext.grid.GridPanel({
		border :false,
		store: new Ext.data.Store({
			fields:['id','name','type','size','state','percent',"attachmentId"]
		}),
	    columns: [
	    	new Ext.grid.RowNumberer(),
	        {header: '文件名', width: 100, sortable: false,dataIndex: 'name', menuDisabled:true},
	        {header: '类型', width: 70, sortable: false,dataIndex: 'type', menuDisabled:true},
	        {header: '大小', width: 100, sortable: false,dataIndex: 'size', menuDisabled:true,renderer:this.formatFileSize},
	        {header: '进度', width: 150, sortable: false,dataIndex: 'percent', menuDisabled:true,renderer:this.formatProgressBar,scope:this},
	        {header: '状态', width: 70, sortable: false,dataIndex: 'state', menuDisabled:true,renderer:this.formatFileState,scope:this},
	        {header: '&nbsp;',width:40,dataIndex:'id', menuDisabled:true,renderer:this.formatDelBtn},
	         {header: '&nbsp;',width:40,dataIndex:'attachmentId'}//, menuDisabled:true,hidden:true,hiddenable:true}
	    ]			
	});
	
	this.setting = {
		upload_url : this.uploadUrl, 
		flash_url : this.flashUrl,
		file_size_limit : this.fileSize || (1024*50) ,//上传文件体积上限，单位MB
		file_post_name : this.filePostName,
		file_types : this.fileTypes||"*.*",  //允许上传的文件类型 
        file_types_description : "All Files",  //文件类型描述
        file_upload_limit : "0",  //限定用户一次性最多上传多少个文件，在上传过程中，该数字会累加，如果设置为“0”，则表示没有限制 
        //file_queue_limit : "10",//上传队列数量限制，该项通常不需设置，会根据file_upload_limit自动赋值              
		post_params : this.postParams||{savePath:'upload\\'},
		use_query_string : true,
		debug : false,
		button_cursor : SWFUpload.CURSOR.HAND,
		button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
		custom_settings : {//自定义参数
			scope_handler : this
		},
		file_queued_handler : this.onFileQueued,
		swfupload_loaded_handler : function(){},// 当Flash控件成功加载后触发的事件处理函数
		file_dialog_start_handler : function(){},// 当文件选取对话框弹出前出发的事件处理函数
		file_dialog_complete_handler : this.onDiaogComplete,//当文件选取对话框关闭后触发的事件处理
		upload_start_handler : this.onUploadStart,// 开始上传文件前触发的事件处理函数
		upload_success_handler : this.onUploadSuccess,// 文件上传成功后触发的事件处理函数 
		swfupload_loaded_handler : function(){},// 当Flash控件成功加载后触发的事件处理函数  
		upload_progress_handler : this.uploadProgress,
		upload_complete_handler : this.onUploadComplete,
		upload_error_handler : this.onUploadError,
		file_queue_error_handler : this.onFileError
	};
	keel.UploadPanel.superclass.constructor.call(this,{				
		tbar : [
			{text:'添加文件',iconCls:'page_add',ref:'../addBtn',scope:this},'-',
			{text:'上传',ref:'../uploadBtn',iconCls:'page_upload',handler:this.startUpload,scope:this},'-',
			{text:'停止上传',ref:'../stopBtn',iconCls:'page_delete',handler:this.stopUpload,scope:this,disabled:true},'-',
			{text:'删除所有',ref:'../deleteBtn',iconCls:'page_deleteall',handler:this.deleteAll,scope:this},'-',
			{text:'关闭',iconCls:'page_close',handler:this.closeCallback,scope:this}
		],
		layout : 'fit',
		items : [this.gp],
		listeners : {
			'afterrender':function(){
				var em = this.getTopToolbar().get(0).el.child('em');
				var placeHolderId = Ext.id();
				em.setStyle({
					position : 'relative',
					display : 'block'
				});
				em.createChild({
					tag : 'div',
					id : placeHolderId
				});
				this.swfupload = new SWFUpload(Ext.apply(this.setting,{
					button_width : em.getWidth(),
					button_height : em.getHeight(),
					button_placeholder_id :placeHolderId
				}));
				this.swfupload.uploadStopped = false;
				Ext.get(this.swfupload.movieName).setStyle({
					position : 'absolute',
					top : 0,
					left : -2
				});
			},
			scope : this,
			delay : 100
		}
	});
}
Ext.extend(keel.UploadPanel,Ext.Panel,{
	toggleBtn :function(bl){
		this.addBtn.setDisabled(bl);
		this.uploadBtn.setDisabled(bl);
		this.deleteBtn.setDisabled(bl);
		this.stopBtn.setDisabled(!bl);
		this.gp.getColumnModel().setHidden(6,bl);
	},
 	onUploadStart : function(file) {
	   var post_params = this.settings.post_params;  
	   Ext.apply(post_params,{//处理中文参数问题
	   		//fileName : file.name,
	        fileName : encodeURIComponent(file.name),
	        CURRENT_USERID : CURRENT_USERID/*定义在 constant.js 文件中*/
	   });
	   this.setPostParams(post_params);
	},
	
	startUpload : function() {
		if (this.swfupload) {
			if (this.swfupload.getStats().files_queued > 0) {
				this.swfupload.uploadStopped = false;
				this.toggleBtn(true);
				this.swfupload.startUpload();
			}
		}
	},
	formatFileSize : function(_v, celmeta, record) {
		return Ext.util.Format.fileSize(_v);
	},
	formatFileState : function(n){//文件状态
		switch(n){
			case -1 : return '未上传';
			break;
			case -2 : return '正在上传';
			break;
			case -3 : return '<div style="color:red;">上传失败</div>';
			break;
			case -4 : return '上传成功';
			break;
			case -5 : return '取消上传';
			break;
			default: return n;
		}
	},
	formatProgressBar : function(v){
		var progressBarTmp = this.getTplStr(v);
		return progressBarTmp;
	},
	getTplStr : function(v){
		var bgColor = "orange";
	    var borderColor = "#008000";
		return String.format(
			'<div>'+
				'<div style="border:1px solid {0};height:10px;width:{1}px;margin:4px 0px 1px 0px;float:left;">'+		
					'<div style="float:left;background:{2};width:{3}%;height:10px;"><div></div></div>'+
				'</div>'+
			'<div style="text-align:center;float:right;width:40px;margin:3px 0px 1px 0px;height:10px;font-size:12px;">{3}%</div>'+			
		'</div>', borderColor,(90),bgColor, v);
	},
	onUploadComplete : function(file) {
		var me = this.customSettings.scope_handler;
		if(file.filestatus==-4){
			var ds = me.gp.store;
			for(var i=0;i<ds.getCount();i++){
				var record =ds.getAt(i);
				if(record.get('id')==file.id){
					record.set('percent', 100);
					if(record.get('state')!=-3){
						record.set('state', file.filestatus);
					}
					record.commit();
				}
			}
		}
		
		if (this.getStats().files_queued > 0 && this.uploadStopped == false) {
			this.startUpload();
		}else{			
			me.toggleBtn(false);
			me.linkBtnEvent();
		}		
	},
	onFileQueued : function(file) {
		var me = this.customSettings.scope_handler;
		var rec = new Ext.data.Record({
			id : file.id,
			name : file.name,
			size : file.size,
			type : file.type,
			state : file.filestatus,
			percent : 0
		})
		me.gp.getStore().add(rec);
	},
	onUploadSuccess : function(file, serverData) {
		var response = {responseText:serverData};
		var isExist = EventManager.existSystem(response);
		if(isExist) return;
		
		this.hasUpload = true;/*标识有上传记录*/
		var me = this.customSettings.scope_handler;
		me.hasUpload = true;
		var ds = me.gp.store;
		var jsonData = Ext.util.JSON.decode(serverData);
		if (jsonData.success) {			
			for(var i=0;i<ds.getCount();i++){
				var rec =ds.getAt(i);
				if(rec.get('id')==file.id){
					rec.set('state', file.filestatus);
					var fileInfo = jsonData.fileInfos[0];
					if(fileInfo && fileInfo.attachmentId){
						rec.set('attachmentId', fileInfo.attachmentId);
					}
					rec.commit();
				}
			}			
		}else{
			for(var i=0;i<ds.getCount();i++){
				var rec =ds.getAt(i);
				if(rec.get('id')==file.id){
					rec.set('percent', 0);
					rec.set('state', -3);
					rec.commit();
				}
			}
		}
		me.linkBtnEvent();
	},
	uploadProgress : function(file, bytesComplete, totalBytes){//处理进度条
		var me = this.customSettings.scope_handler;
		var percent = Math.ceil((bytesComplete / totalBytes) * 100);
		percent = percent == 100? 99 : percent;
       	var ds = me.gp.store;
		for(var i=0;i<ds.getCount();i++){
			var record =ds.getAt(i);
			if(record.get('id')==file.id){
				record.set('percent', percent);
				record.set('state', file.filestatus);
				record.commit();
			}
		}
	},
	onUploadError : function(file, errorCode, message) {
		var response = {responseText:message};
		var isExist = EventManager.existSystem(response);
		if(isExist) return;
				
		var me = this.customSettings.scope_handler;
		me.linkBtnEvent();
		var ds = me.gp.store;
		for(var i=0;i<ds.getCount();i++){
			var rec =ds.getAt(i);
			if(rec.get('id')==file.id){
				rec.set('percent', 0);
				rec.set('state', file.filestatus);
				rec.commit();
			}
		}
	},
	onFileError : function(file,n){
		switch(n){
			case -100 : tip('待上传文件列表数量超限，不能选择！');
			break;
			case -110 : tip('文件太大，不能选择！');
			break;
			case -120 : tip('该文件大小为0，不能选择！');
			break;
			case -130 : tip('该文件类型不可以上传！');
			break;
		}
		function tip(msg){
			Ext.Msg.show({
				title : '提示',
				msg : msg,
				width : 280,
				icon : Ext.Msg.WARNING,
				buttons :Ext.Msg.OK
			});
		}
	},
	onDiaogComplete : function(){
		var me = this.customSettings.scope_handler;
		me.linkBtnEvent();
	},
	stopUpload : function() {
		if (this.swfupload) {
			this.swfupload.uploadStopped = true;
			this.swfupload.stopUpload();
		}
	},
	deleteAll : function(){
		var ds = this.gp.store;
		var attachmentIds = [];
		for(var i=0;i<ds.getCount();i++){
			var record =ds.getAt(i);
			var file_id = record.get('id');
			var attachmentId = record.get('attachmentId');
			if(attachmentId) attachmentIds[attachmentIds.length] = attachmentId;
			this.swfupload.cancelUpload(file_id,false);			
		}
		
		ds.removeAll();
		this.swfupload.uploadStopped = false;
		this.hasUpload = true;/*标识有上传记录*/
		this.removeServerDatas(attachmentIds);
	},
	formatDelBtn : function(v){
		return "<a href='#' id='"+v+"'  style='color:blue' class='link-btn' ext:qtip='移除该文件'>移除</a>";
	},
	linkBtnEvent : function(){
		Ext.select('a.link-btn',false,this.gp.el.dom).on('click',function(o,e){
			var ds = this.gp.store;
			var attachmentIds = [];
			for(var i=0;i<ds.getCount();i++){
				var rec =ds.getAt(i);
				if(rec.get('id')==e.id){
					var attachmentId = rec.get('attachmentId');
					if(attachmentId) attachmentIds[attachmentIds.length] = attachmentId;
					ds.remove(rec);
				}
			}			
			this.swfupload.cancelUpload(e.id,false);
			this.hasUpload = true;/*标识有上传记录*/
			this.removeServerDatas(attachmentIds);
		},this);
	},
	/**
	 * 删除服务器的附件数据
	 * @param attachmenIds 附件ID数组
	 */
	removeServerDatas : function(attachmentIds){
		if(!attachmentIds || attachmentIds.length <= 0) return;
		attachmentIds = attachmentIds.join(",");
		var params = {isenabled:-1,ids:attachmentIds};
		EventManager.get(this.delUrl,{params:params,sfn:function(json_data){
 			ExtUtil.alert({msg:Msg_SysTip.msg_dataSucess});
 		}});
	}
});
Ext.reg('uploadPanel',keel.UploadPanel);

/**
 * 封装 Ext Window 支持传入不同类型
 * 返回一个带有多文件上传功能的 Windows 控件
 */
Ext.namespace("Ext.ux.window");
Ext.ux.window.SwfUploadWindow = Ext.extend(Ext.Window,{
	title : '文件上传窗',
	layout : 'fit',	//布局为自适应
	width:650,
	height : 300,
	listeners : EventManager.attachWinZindexListeners(),
	closeAction : 'hide',
	maximizable : false, //是否启用最大化按钮 true: 启用 false : 禁用
	minimizable : false,
	url : './UploadServlet',
	params : {dir:'pic_path'},/*上传所需的参数*/
	uploadPanelId : null,/*多文件上传面板ID*/
	hideCallback : null,/*当隐藏时，触发的回调方法*/
	initComponent : function(){
		this.init();
		Ext.ux.window.SwfUploadWindow.superclass.initComponent.call(this);
	 },
	 init : function(){
	 	var _this = this;
	 	this.uploadPanelId = Ext.id(null,'uploadPanelId_');
	 	this.items = [{
	 		id:this.uploadPanelId,
			xtype:'uploadPanel',
			border : false,
			fileSize : 1024*550,//限制文件大小
			uploadUrl : './UploadServlet',
			flashUrl : './controls/swfupload/swfupload.swf',//'./controls/swfupload/swfupload.swf',
			filePostName : 'file', //后台接收参数
			fileTypes : '*.jpg;*.gif;*.png;*.jpeg;*.bmp;*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.pdf;*.htm;*.html;*.txt;*.cvs;',//可上传文件类型
			postParams : this.params, //上传文件存放目录
			closeCallback : function(){
				_this.hide();
			}
		}];
		var _this = this;
		this.listeners = {
			show : function(win){
				var uploadPanel = Ext.getCmp(_this.uploadPanelId);
				uploadPanel.hasUpload = false;
				var store = uploadPanel.gp.store;
				if(store) store.removeAll();
				uploadPanel.setting.post_params = _this.params;
			},
			hide : function(win){
				var uploadPanel = Ext.getCmp(_this.uploadPanelId);
				var hasUpload = uploadPanel.hasUpload;
				if(hasUpload && _this.hideCallback){
					_this.hideCallback();
				}
			}
		};
	 }
});

//注册成xtype以便能够延迟加载   
Ext.reg('swfuploadwindow', Ext.ux.window.SwfUploadWindow);