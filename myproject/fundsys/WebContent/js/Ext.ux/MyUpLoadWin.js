/**
 * 封装 Ext Window 
 * 一个带有上传功能的 Windows 控件
 */
Ext.namespace("Ext.ux.window");

Ext.ux.window.MyUpWin = Ext.extend(Ext.Window,{
	idprefix : null,
	title : '上传图片',
	finshHiden : true, /*上传完成后是否隐藏，true:隐藏 false : 不隐藏*/
	label : '上传图片',
	labelWidth:80,
	url : './UploadServlet', /*上传的URL*/
	width:400,
	height:115,
	closeAction : 'hide',
	modal : true,		// true 后面的东西不能点击 
	isSave : true,//是否保存到附件表中 [true : 是, false : 否]
	dir : null,	//文件上传目录-->在 resource.properties 文件中定义。
	allowFiles : null,/*允许上传的文件格式*/
	upfield : null,	//上传文件字段
	sfn : null,	//上传成功后，执行的函数
	ffn : null,	//上传失败后，执行的函数
	initComponent : function(){
		this.idprefix = Ext.id();
	 	this.id= 'upwin_'+this.idprefix;
	 	Ext.ux.window.MyUpWin.superclass.initComponent.call(this);
	 	this.createUpLoadFrm();
	 },
	 createUpLoadFrm : function(){
	 	var self = this;
	 	this.upfield = new Ext.form.TextField({fieldLabel:this.label,name:'file_'+this.idprefix,inputType:'file',width:280});
	 	var upFrm = new Ext.form.FormPanel({
	 	labelAlign:'right',
	 	labelWidth:this.labelWidth,
	 	frame:true,
	 	width:this.width,
	 	height:80,
	 	id:'upp_'+this.idprefix,
	 	fileUpload:true,
	 	url:this.url,
	 	items : [this.upfield],
	 	
	 	buttons :[{text:'上传',handler:function(){
	 		var val = self.upfield.getValue();
	 		if(!val){
	 			ExtUtil.alert({msg:'请选择要上传的文件!'});
	 			return;
	 		}
		 	var params = {isSave:self.isSave};
		 	if(self.dir) params.dir = self.dir;
		 	if(self.allowFiles) params.allowFiles = self.allowFiles;
		 	upFrm.getForm().submit({
		 		params : params,
		 		success :function(form,action){
		 			var result = action.result;
//		 			if(result){
//			 			result = Ext.decode(result);
//		 			}
		 			var fileInfos = result.fileInfos;
		 			var success = result.success;
		 			var msgTitle = "成功";
		 			var msgTip = self.title+"成功！";
		 			if(!success){
		 				msgTitle = "失败";
		 				msgTip =  result.msg;
		 			}
		 			if(self.sfn)self.sfn(fileInfos);
		 			Ext.Msg.alert(msgTitle,msgTip,function(){
		 				if(self.finshHiden){
		 					self.hide();
		 				}
		 				self.upfield.reset();
		 			});
		 		}
		 		,failure :function(form,action){
		 			Ext.Msg.alert("失败",self.title+"失败！");
		 			self.upfield.reset();
		 			if(action.result){
//		 				var result = Ext.decode(action.result);
		 				if(self.ffn)self.ffn(result.fileInfos);
		 			}
		 			
		 		}
		 	});
	 	}},
	 	{text:'关闭',handler:function(){
	 		self.hide();
	 	}}]
	 	});
	 	this.add(upFrm);
	 }
});

//注册成xtype以便能够延迟加载   
Ext.reg('myupwin', Ext.ux.window.MyUpWin);  