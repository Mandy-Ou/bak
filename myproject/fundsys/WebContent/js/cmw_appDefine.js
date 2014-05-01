/**
 * 应用程序公共JS文件加载类
 */
AppDef = {
	basePath : "../..",	//根路径
	cookies : Ext.util.Cookies, //初始化 cookie 对象
	isLogin : false,	//是否是登录页面
	/**
	 * 加载扩展JS文件
	 */
	loadExtUxFiles : function(){
		var path = this.basePath+"/js/Ext.ux/";
		//注意：此处 "TabCloseMenu.js","AppTabTreeWin.js" 两个JS文件一定不能放前面。否则，会出错。不知道为啥？
		var arrFiles = ["LoginWindow.js","Ext.ux.tot2ivn.AccordionVboxLayout.js","Ext.form.MyVTypes.js",
		"Ext.ux.form.ElasticTextArea.js","Ext.ux.SearchField.js","ProgressBarPager.js","SlidingPager.js","MyImgpPanel.js",
		"PagingMemoryProxy.js","TabCloseMenu.js","AppTabTreeWin.js","MyUpLoadWin.js","MyCmwToolbar.js",
		"MyRadioGroup.js","MyCheckBoxGroup.js","chooser.js","MyCompositeField.js","MyTextField.js","MyDateField.js","SuperDateField.js","MoneyField.js","MyNumberField.js",
		"MyEditorField.js","MyFormulaField.js","MyImgChooseField.js","RemoteCombox.js","AppComBoxTree.js","AppComBoxGrid.js","ImgPanel.js","AppComBoxImg.js",
		"AppGrid.js","MyEditGrid.js","Ext.ux.panel.DetailPanel.js","AppAttachmentFs.js","AppForm.js",
		"MyTree.js","MyWindow.js","MyHtmlEditor.js","ColumnHeaderGroup.js","MyUploadField.js","Ext.ux.form.CheckboxCombo.js",
		"treegrid/TreeGridSorter.js","treegrid/TreeGridColumnResizer.js","treegrid/TreeGridNodeUI.js","treegrid/TreeGridLoader.js",
		"treegrid/TreeGridColumns.js","treegrid/TreeGrid.js","Portal.js","ColorField.js","CmwReportGrid.js","ToastWindowMgr.js"];
		if(this.isLogin){	//如果是登录页面只加载 登录的 JS文件
			this.writeScript(path+arrFiles[0]);
		}else{
			for(var i=1,count = arrFiles.length; i<count; i++){
				this.writeScript(path+arrFiles[i]);
			}
		}
	},
	/**
	 * 加载应用程序组件 JS
	 */
	loadAppwidgetsFiles : function(){
		var path = this.basePath+"/js/App.widgets/";
		var arrFiles = ["AbsGContainerView.js","AbsGFormView.js","AbsTreeGridView.js","AbsTreePanelView.js","AbsPanelView.js","AbsEditWindow.js","AbsDetailWindow.js"];
		for(var i=0,count = arrFiles.length; i<count; i++){
			this.writeScript(path+arrFiles[i]);
		}
	},
	/**
	 * 工具类核心JS文件
	 */
	loadUtilJsFiles : function(){
		var path = this.basePath+"/js/";
		var arrFiles = ["sea.js","cmw_core.js","cmw_fastKeys.js","localXHR.js","ui_util.js","form_util.js","cmw_event.js","string_util.js"];
		for(var i=0,count = arrFiles.length; i<count; i++){
			this.writeScript(path+arrFiles[i]);
		}
	},
	/**
	 * 加载初始化数据JS文件
	 */
	loadBaseDatasJsFiles : function(){
		var path = this.basePath+"/js/datas/";
		var arrFiles = ["GvlistDatas.js","ComboxControl.js","FormDiyDatas.js"];
		for(var i=0,count = arrFiles.length; i<count; i++){
			this.writeScript(path+arrFiles[i]);
		}
	},
	/**
	 * 加载第三方控件JS文件
	 */
	loadControlsJsFiles : function(){
		var path = this.basePath+"/controls/";
		this.writeCssFile(path+"kindeditor/themes/default/default.css");
		this.writeCssFile(path+"lightbox/css/lightbox.css");
		var arrFiles = ["kindeditor/kindeditor-min.js","kindeditor/lang/zh_CN.js",
		"swfupload/swfupload.js","swfupload/uploaderPanel.js","lightbox/js/lightbox.js"];
		for(var i=0,count = arrFiles.length; i<count; i++){
			this.writeScript(path+arrFiles[i]);
		}
	},
	/**
	 * 加载语言JS文件方法
	 */
	loadLangFiles : function(){
		//如果Cookie 中没有设置语言，就以浏览器语言版本作为默认语言值
		var locale = this.getLocale();
		//Ext 国际化文件
		var EXT_LOCAL_JS_FILE =  "/extlibs/ext-3.3.0/locale/ext-lang-" +locale+ ".js";
		//应用程序国际化文件
		var APP_LOCAL_JS_FILE =   "/pages/i18n/app-lang-"+locale+".js";
		var FINSYS_LOCAL_JS_FILE =   "/pages/i18n/finsys-lang-"+locale+".js";/*财务系统*/
		var FS_LOCAL_JS_FILE =   "/pages/i18n/fininter-lang-"+locale+".js";/*财务系统*/
		var arr =[EXT_LOCAL_JS_FILE,APP_LOCAL_JS_FILE,FINSYS_LOCAL_JS_FILE,FS_LOCAL_JS_FILE];
		for(var i=0,count = arr.length; i<count; i++){
			this.writeScript(this.basePath+arr[i]);
		}
	},
	/**
	 * 获取用户当前使用的语言
	 * @return {}
	 */
	getLocale : function(){
		var locale = this.cookies.get("currLanguage") ||  this.getBrowserLanguage();
		return locale;
	},
	/**
	 * 加载CSS皮肤样式文件方法
	 */
	loadSkinFiles : function(){
		var currTheme = this.cookies.get("currTheme") || 'calista';
		this.setStyle(currTheme);
	},
	/**
	 * 加载所有JS文件
	 * @param {} basePath 根路径
	 */
	loadJses : function(basePath){
		if(basePath) this.basePath = basePath;
		this.loadLangFiles();
		this.loadSkinFiles();
		this.loadUtilJsFiles();
		this.loadExtUxFiles();
		if(this.isLogin) return;
		this.loadAppwidgetsFiles();
		this.loadBaseDatasJsFiles();
		this.loadControlsJsFiles();
	},
	/**
	 * 设置系统样式 
	 */
	setStyle : function(sty){
		var url =  this.basePath + "/extlibs/ext-3.3.0/resources/css/";
		if(sty == 'default'){
			url += "ext-all.css";
		}else{
			url += "xtheme-"+sty+".css";
		}
		//currTheme currLanguage
		Ext.util.CSS.swapStyleSheet("theme",url);
		this.cookies.set("currTheme",sty);	//将样式存入  cookie 
	},
	/**
	 * 设置系统语言
	 */
	setLanguage : function(lng){
		var url = window.location.href;
		if(!url) return;
		this.cookies.set("currLanguage",lng);	//将语言存入  cookie
		window.location.href = url;
	},
	/**
	 * 获取浏览器所使用的语言
	 */
	getBrowserLanguage : function(){
		var language = window.navigator.language;
		if(!language) language = window.navigator.browserLanguage;
		if(-1 != language.indexOf("-")){
			var lngs = language.split("-");
			lngs[1] = lngs[1].toUpperCase();
			language = lngs.join("_");
		}
		return language;
	},
	/**
	 * 往页面写 "<link rel=\"stylesheet\" type=\"text/css\" href=\""+src+"\" /> 标记
	 * @param {} src css文件路径
	 */
	writeCssFile : function(src){
		document.write("<link rel=\"stylesheet\" type=\"text/css\" href=\""+src+"\" />");
	},
	/**
	 * 往页面写 "<script src='"+jsArr[i]+"'><\/script>" 标记
	 * @param {} src js文件路径
	 */
	writeScript : function(src){
		document.write("<script src='"+src+"'><\/script>");   
	}
}
