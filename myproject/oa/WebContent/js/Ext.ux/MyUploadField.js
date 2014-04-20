/**
 * 封装 Ext CompositeField 类
 * 重写期 getValue 和 setValue 方法
 */
Ext.namespace("Ext.ux.form");
/**
 * 文件上传控件
 * @class  Ext.ux.form.MyUploadField
 * @extends Ext.ux.form.MyCompositeField
 */
Ext.ux.form.MyUploadField = Ext.extend(Ext.ux.form.MyCompositeField,{
	txtFile : null,
	btnChose : null,
	uploadWin : null,
	width : 180,
	winTitle : '上传文件',
	winLabel : '请选择文件',
	callback:null,
	dir : 'xls_report_path',/*Excel报表文件目录*/
	allowFiles:"allowFiles_ext",
	initComponent : function(){
		this.txtName = this.name;
		this.createCmpts();
		this.buildCmpts();
	 	Ext.ux.form.MyUploadField.superclass.initComponent.call(this);
	},
	createCmpts : function(){
		var cfg = {name:this.name};
		if(!this.width) this.width = 180;
		cfg.width = this.width - 60;
		if(this.hasOwnProperty("allowBlank") && !this.allowBlank) cfg.allowBlank = this.allowBlank;
		this.txtFile = FormUtil.getTxtField(cfg);
		var _this = this;
		this.btnChose = new Ext.Button({text : '浏览...',width:50,handler:function(){
			_this.showUploadWin();
		}});
	},
	buildCmpts : function(){
		this.items = [this.txtFile,this.btnChose];
	},
	/**
	 * 显示上传窗口
	 */
	showUploadWin :	function(){
		var _this = this;
		if(!this.uploadWin){
			this.uploadWin = new Ext.ux.window.MyUpWin({title:this.winTitle,label:this.winLabel,width:450,dir:this.dir,allowFiles:this.allowFiles,
			sfn:function(fileInfos){
				var filePath = null;
				if(fileInfos){
					var fileInfo = fileInfos[0];
					filePath = fileInfo.serverPath;
				}
				_this.txtFile.setValue(filePath);
				if(_this.callback)_this.callback(fileInfos);
			}});
		}
		this.uploadWin.show();
	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('myuploadfield', Ext.ux.form.MyUploadField);  