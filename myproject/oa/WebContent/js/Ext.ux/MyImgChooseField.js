Ext.namespace("Ext.ux");
Ext.ux.form.MyImgChooseField = Ext.extend(Ext.form.TwinTriggerField,{
	ImageFormulWin : null,
	winWidth : 300,
	height : 250,
	selVal : null,
	serverImgFodlerUrl : "",
	isLoad : true,
	hasSearch : false,
	trigger1Class:'x-form-clear-trigger',
	trigger2Class:'x-form-search-trigger',
	autoScroll : true,
	showIconTypes : null,/*显示的的图标类型,如果有以数组形式提供，参见 ts_Icon(图标表)如：[1:标准48*48大图标,2:扁平大图标,3:小图标] 暂未用到*/
  	onTrigger1Click : function(){
  		this.reset();
    },
	onTrigger2Click : function(){
		if(!this.ImageFormulWin){
			this.ImageFormulWin = this.createFormulaWin();
		}
		var el = this.el;
		var _this = this;
		this.ImageFormulWin.show(el,function(data){
			var url = data.url;
			_this.setValue(url);
		});
	},
	 createFormulaWin : function(){
		return ImgChooserLazySingleton.getInstace();
	 },
	 /**
	 * 清空数据
	 */
	clear : function(){
		if(this.ImageFormulWin && this.ImageFormulWin.rendered) this.MyImgChooseField.reset();
	},
	setValue : function(v){
		this.clear();
		if(!v && v !== 0) return;
		var _this = this;
		this.selVal = v;
 		Ext.ux.form.MyImgChooseField.superclass.setValue.call(_this,v);
	},
	getValue : function(){
		return this.selVal;
	},
	reset : function(){
		Ext.ux.form.MyImgChooseField.superclass.setValue.call(this,"");
		if(this.selVal) this.selVal=null;
		this.clear();
	}
});
Ext.reg('myImgChooseField',  Ext.ux.form.MyImgChooseField);  
