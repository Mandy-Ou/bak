/**
 * 封装 Ext.form.TriggerField 控件
 * 返回一个含有图片列表的控件 
 *  xtype : appcomboxtree
 */
Ext.namespace("Ext.ux.form");

Ext.ux.form.AppComboxImg = Ext.extend(Ext.form.TriggerField,{
	editable: false,
	menu : null,
	appPanel : null,
	hidenField : null,
	height:300,
	url:null,
	params : {},
	isLoad : true,
	valueField : '',
	displayField : '',
	allDispTxt : '',
	selData : null,
	selIds : null,
	selTxts : null,
	callback : null,/**/
	clickCallback : null,/*单点击时，触发的回调函数*/
	initComponent : function(){
		if(this.hasOwnProperty("allowBlank") && !this.allowBlank){
			this.fieldLabel = NEED + this.fieldLabel;
		}
		Ext.ux.form.AppComboxImg.superclass.initComponent.call(this);
		this.menu = this.createMenu();
	 },
	 initEvents : function() {
	 	this.initListeners();
	     Ext.ux.form.AppComboxImg.superclass.initEvents.call(this);
	},
	initListeners : function(){
		var self = this;
	},
	onTriggerClick : function(){
		if(this.disabled) return;
		if(!this.menu){
			this.menu = this.createMenu();
		}
		if(this.clickCallback){
			var isShow = this.clickCallback(this);
			if(!isShow) return;
		}
		this.menu.show(this.el,"tl-bl?");
		this.appPanel.select();
	},
	/**
	 * 为下拉框树赋值
	 * @param {} selIds 要赋的选中值
	 */
	setValue : function(selIds){
		if(selIds){
			var _this = this;
			this.isLoad = true;
			this.appPanel.loadData(function(imgBox){
				var data = imgBox.data;
				if(data[_this.valueField] != selIds) return false;
				_this.setSelValue(data);
				_this.appPanel.selEle = imgBox.id;
				_this.appPanel.selval = data;
				_this.selIds = selIds;
				return true;
			},this.params);
		}else{
			this.selData = this.appPanel.selval;
			if(!this.selData){
				return;
			}
			this.setSelValue(this.selData);
		}
	},
	setSelValue : function(selData){
		if(this.disabled) this.enable();
		this.selIds = selData[this.valueField];
		this.selTxts = selData[this.displayField];
		if(this.callback) this.callback(selData);
		Ext.ux.form.AppComboxImg.superclass.setValue.call(this,this.selTxts);
	},
	/**
	 * 当 isAll ： true 时，将会把 ID 和	文本一起拼接返回。
	 * 其格式如下： 1001,1002##研发一部,研发二部
	 * @return {String} 返回选中的节点的ID或 (ID和文本一起返回)
	 */
	getValue : function(){
		if(!this.selIds) return "";
		return (!this.selIds) ? "" : this.selIds;
	},
	/**
	 * 重置
	 */
	reset : function(){
		Ext.ux.form.AppComboxImg.superclass.reset.call(this);
		this.isChange = false;
		this.selIds = null;
		this.selTxts = null;
		this.selData = null;
		this.params = {};
		if(this.allDispTxt){
			Ext.ux.form.AppComboxImg.superclass.setValue.call(this,this.allDispTxt);
		}else{
			Ext.ux.form.AppComboxImg.superclass.setValue.call(this,'');
		}
	},
	/**
	 * 返回选中的文本
	 * 选中多个的情况下，以“，”拼接并返回
	 * @return {}
	 */
	getText : function(){
		return this.seltxts ? this.seltxts : "";
	},
	createMenu : function(){
		var appPanel = this.createAppPanel();
		this.appPanel = appPanel;
		var menu  = new Ext.menu.Menu({items:[appPanel]});
		return menu;
	},
	createAppPanel : function(){
		var cfg = {height:this.height,url:this.url,isLoad : this.isLoad,params:this.params,tbar:this.getToolBar()};
		var appPanel = new Ext.ux.ImgPanel(cfg);
		var _this = this;
		appPanel.addListener('render',function(pnl){
			appPanel.getEl().on('dblclick',function(e,obj){
				_this.doValue();
			});
		});
		return appPanel;
	},
	getToolBar : function(){
		var _this = this;
		return [{text : Btn_Cfgs.REFRESH_BTN_TXT,iconCls:Btn_Cfgs.REFRESH_CLS,handler : function(){
			_this.appPanel.loadData();
		}},{text : Btn_Cfgs.CONFIRM_BTN_TXT,tooltip:Btn_Cfgs.DBLCLICK_TIP_BTN_TXT,iconCls:Btn_Cfgs.CONFIRM_CLS,handler : function(){
			_this.doValue();
		}}
		];
	},
	doValue : function(){
		this.selData = this.appPanel.selval;
		if(!this.selData){
			ExtUtil.alert({msg : Msg_SysTip.msg_noSelBreed});
			return;
		}
		this.setSelValue(this.selData);
		this.menu.hide();
	},
	/**
	 * 设置查询参数
	 * @param {} params
	 */
	setParams : function(params){
		this.params = params;
	},
	reload : function(params){
		var self = this;
		if(params) Ext.apply(this.params,params);
		this.isLoad = true;
		this.appPanel.loadData(null,this.params);
	},
	/**
	 * 验证字段值
	 */
	validate : function(){
		var valid = true;
		var val = this.getValue();
		valid = val ? true : false;
		return valid;
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('appcomboximg', Ext.ux.form.AppComboxImg);  