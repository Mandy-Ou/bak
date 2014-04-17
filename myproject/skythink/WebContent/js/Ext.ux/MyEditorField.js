Ext.namespace("Ext.ux.form");
/**
 * 富文本编辑器控件
 * 扩展 Ueditor 百度富文本
 * @class Ext.ux.form.MyEditorField
 * @extends Ext.form.TextArea
 *  
 */
Ext.ux.form.MyEditorField = Ext.extend(Ext.form.TextArea,{
	islabelToTop : false, /*FieldLable 是否放在顶部*/
 	labelSeparator : ':',
 	unitStyle : null,	/*单位样式*/
 	unitEl : null,
 	editor : null,	/*富文本对象*/
 	uploadJson : './UploadServlet?tagSource=kdeditor&allowFiles_ext=allowFiles_pic_ext',
 	imgDir : 'kd_path',
 	fileManagerJson : './controls/kindeditor/jsp/file_manager_json.jsp?dir=kd_path',
 	isBaseCfg : false,/*是否只显示最基本的工具栏 isBaseCfg : false (显示所有), true : 显示最简单的工具栏 */
 	autoHeightEnabled : false,/*当输入内容超过实际高度时，是否自动长高  false : 不长高,true : 自动长高*/
 	initComponent : function(){
		Ext.ux.form.MyEditorField.superclass.initComponent.call(this);
		this.init();
	 },
	onRender : function(ct, position) {
		  Ext.ux.form.MyEditorField.superclass.onRender.call(this, ct, position);
		  if (this.islabelToTop) {
		  	var unitCfg = {
		      tag : 'div',
		      html : this.fieldLabel+this.labelSeparator,
		      cls:'x-form-unit'
		     };
		     if(this.unitStyle) unitCfg.style = this.unitStyle;//'color:red';
			 var labelDom = Ext.DomHelper.createDom(unitCfg);
			 this.unitEl = ct.insertFirst(labelDom);
		  }
	},
	init : function(){
		if(this.islabelToTop){
			 this.hideLabel = true;
		}
		if(this.hideLabel){
			this.width+=50;
		}
		this.addListener("render",this.renderEditor);
	},
	/**
	 * 渲染富文本编辑器
	 * @param {} textArea
	 */
	renderEditor : function(textArea){
		var width = this.width;
		var height = this.height;
		var dom = textArea.el.dom;
		var _this = this;
		var name = _this.name;
		var eleIdStr = "#"+this.id;
		if(this.name) eleIdStr = 'textarea[name="'+this.name+'"]';
		var cfg = this.getConfig();
		this.editor = KindEditor.create(eleIdStr, cfg);
		if(_this.emptyText){
			  _this.editor.html(_this.emptyText);
		}
	},
	/**
	 * 获取编辑器配置参数
	 */
	getConfig : function(){
		var eWidth = "100%";
		if(this.width){
			eWidth = this.width +"px";
		}
		var eHeight = "100%";
		if(this.height){
			eHeight = this.height +"px";
		}
		var cfg = {
			allowFileManager : true,
			width : eWidth,
			filterMode:false,
			height : eHeight,
			uploadJson : this.uploadJson,
			imgDir : this.imgDir,
			fileManagerJson : this.fileManagerJson
		};
		return cfg;
	},
	/**
	 * 赋值
	 */
	setValue : function(v){
		var value = (!v) ? "&nbsp;" : v; 
		this.editor.html(value);
		 Ext.ux.form.MyEditorField.superclass.setValue.call(this, v);
	},
	/**
	 * 取值
	 */
	getValue : function(){
		return  this.editor.html();
	},
	reset : function(){
		this.editor.html('');
		Ext.ux.form.MyEditorField.superclass.reset.call(this);
	},
	validate : function(){
		var flag = true;
		if(this.hasOwnProperty("allowBlank") && !this.allowBlank){
			if (this.editor.isEmpty()) {
			     flag = false;
			}
			var count = this.editor.count();
			flag = (count>0);
			if(!flag){
				if((this.maxLength && this.maxLength>0) && count>this.maxLength){
					 flag = false;
				}
			}
		}
		return flag;
	},
	destroy : function(){
		this.editor.remove();
		this.editor = null;
		Ext.ux.form.MyEditorField.superclass.destroy.call(this);
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('myeditorfield', Ext.ux.form.MyEditorField);  