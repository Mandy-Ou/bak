/**
 * 封装 Ext.form.TriggerField 控件
 * 返回一个含有下拉Grid的控件 
 *  xtype : appform
 */
Ext.namespace("Ext.ux.grid");

Ext.ux.grid.AppComBoxGrid = Ext.extend(Ext.form.TriggerField,{
	editable: false,
	barItems : null,	/* Grid 中显示的工具栏数组配置项  [必须]*/
	structure : null,	/* Grid 中列配置项  [必须]*/
	gridheight:200,
	gridWidth : 340,
	dispCmn:null,	/* Grid 中用来显示在TriggerField 下拉框的列名  [必须]*/
	url:null,	/* Grid 取数URL  [必须]*/
	params : {},/* Grid 加载数据时所带的参数  [非必须]*/
	isLoad : false,/* Grid 是否立即加载数据 默认:true->渲染后就加载数据 [非必须]*/
	isCheck : false,/* Grid 是否显示复选框 ，默认:false->不需要 [非必须]*/
	needPage: true,/* Grid 是否分页 ，默认:false->不需要 [非必须]*/
	keyField: 'id',/* Grid 主键ID，默认:false->不需要 [非必须]*/
	selvals : '',/* 选中的ID值 [私有]*/
	seltxts : '',/* 选中的文本 [私有]*/
	SIGIN : '##',	//ID和文本分隔符
	isAll : false,	//是否将选中节点的 ID和文本一起拼接后返回。 true : 是[返回ID和文本。例如：1001,1002##研发一部,研发二部], false : 只返回ID
	isGetTxt : false,//得到选中节点的文本，不需要id 和 ##
	grid : null,
	menu : null,
	showLoad : false,/*点击显示Grid 就加载数据 , false : 显示不加载,true:一点击弹出Grid 后就加加载数据*/
	hideOnClick : false,	//点击菜单项是不隐藏
	/**
	 * 函数对象，当点击弹出Gird这前。如果此函数返回 true 将表示验证通过，将会显示Grid。否则，不显示。
	 * (提示：只有 AppComboxGrid  可用时，该函数不起作用)
	 * 调用实例 obj.showValidFn(this);
	 * @type 
	 */
	showValidFn : null,	
	callback : null,	//点击确定或双击赋值后回调此函数。例如：callback(this,selvalues);	//将会把选中的值传入此回调函数
	initComponent : function(){
		if(this.hasOwnProperty("allowBlank") && !this.allowBlank){
			this.fieldLabel = NEED + this.fieldLabel;
		}
		Ext.ux.grid.AppComBoxGrid.superclass.initComponent.call(this);
		this.menu = this.createMenu();
	 },
	 initEvents : function() {  
	     Ext.ux.grid.AppComBoxGrid.superclass.initEvents.call(this);
	},
	initListeners : function(){
	},
	onTriggerClick : function(){
		if(this.disabled) return;
		if(this.showValidFn && !this.showValidFn(this))  return;
		if(!this.menu){
			this.menu = this.createMenu();
		}
		this.menu.show(this.el,"tl-bl?");
		if(this.showLoad) this.reload();
	},
	/**
	 * 为下拉框树赋值
	 * @param {} vals 要赋的值
	 */
	setValue : function(vals){
		if(vals && this.disabled){
			this.enable();
		}
		if(vals && vals.indexOf(this.SIGIN) != -1){
			var val_txtArr = vals.split(this.SIGIN);
			if(val_txtArr && val_txtArr.length>1){
				this.selvals = val_txtArr[0];
				this.seltxts = val_txtArr[1];
			}
		}else{
			if(!vals) vals = "";
			this.selvals = vals;
			this.seltxts = vals;
		}
		Ext.ux.grid.AppComBoxGrid.superclass.setValue.call(this,this.seltxts);
	},
	/**
	 * isGetTxt : true s时候，就会返回文本
	 * 当 isAll ： true 时，将会把 ID 和	文本一起拼接返回。
	 * 其格式如下： 1001,1002##研发一部,研发二部
	 * @return {String} 返回选中的节点的ID或 (ID和文本一起返回)
	 */
	getValue : function(){
		if(!this.selvals) return "";
		var val;
		if(this.isGetTxt){
			val = this.seltxts;
		}else{
			val = (this.isAll) ? this.selvals+this.SIGIN+this.seltxts : this.selvals;
		}
		return val; 
	},
	/**
	 * 处理Grid中选中的值
	 */
	doSelVals : function(){
		var selRows = this.grid.getSelRows();
		if(null == selRows || selRows.length == 0) {
			this.menu.hide();
			return;
		}
		var ids = [];
		var txts = [];
		for(var i=0,count=selRows.length; i<count; i++){
			var record = selRows[i];
			ids[i] = record.get(this.keyField) || record.id;
			txts[i] = record.get(this.dispCmn);
		}
		this.selvals = ids.join(",");
		this.seltxts = txts.join(",");
		var value = this.selvals+this.SIGIN+this.seltxts;
		this.setValue(value);
		if(this.callback) this.callback(this,value,selRows);
		this.menu.hide();
	},
	/**
	 * 重置
	 */
	reset : function(){
		Ext.ux.grid.AppComBoxGrid.superclass.reset.call(this);
		this.selvals = null;
		this.seltxts = null;
		//this.params = {};
	},
	setParams : function(_params){
		if(_params){
			 Ext.apply(this.params,_params);
		}
	},
	/**
	 * 重新刷新方法
	 * @param {} _params
	 */
	reload : function(_params){
		var qpars = {};
		Ext.apply(qpars,this.params);
		if(_params) Ext.apply(qpars,_params);
		this.grid.reload(qpars);
		this.grid.getSelectionModel().clearSelections();//取消选中的记录
	},
	/**
	 * 返回选中的文本
	 * 选中多个的情况下，以“，”拼接并返回
	 * @return {}
	 */
	getText : function(){
		return this.seltxts ? this.seltxts : "" ;
	},
	createMenu : function(){
		var grid = this.createGrid();
		this.grid = grid;
		var menu  = new Ext.menu.Menu({items:[grid]});
		return menu;
	},
	createGrid : function(){
		var toolBar = this.createToolBar();
		var width = this.gridWidth ? this.gridWidth : this.width;
		var gridCfg = {
		    width:width,
		    height:this.gridheight,
			tbar : toolBar,
		    structure: this.structure,
		    url: this.url,
		    isLoad : this.isLoad,
		    needPage: this.needPage,
		    params : this.params,
		    keyField: this.keyField
		};
		if(this.isCheck){
			gridCfg.selectType = "check";
		}
		var _this = this;
		var appgrid = new Ext.ux.grid.AppGrid(gridCfg);
		appgrid.addListener('dblclick',function(){
			_this.doSelVals();
		});
		return appgrid;
	},
	createToolBar : function(){
		var _this = this;
		var btnQuery = new Ext.Button({text:Btn_Cfgs.QUERY_BTN_TXT,iconCls:Btn_Cfgs.QUERY_CLS,handler:function(){
			var vals = toolBar.getValues();
			_this.reload(vals);
		}});
		var btnReset = new Ext.Button({text:Btn_Cfgs.RESET_BTN_TXT,iconCls:Btn_Cfgs.RESET_CLS,handler:function(){
			toolBar.resets();
			_this.reset();
		}});
		var btnOk = new Ext.Button({text:Btn_Cfgs.CONFIRM_BTN_TXT,iconCls:Btn_Cfgs.CONFIRM_CLS,handler:function(){
			_this.doSelVals();
		}});
		if(!this.barItems){
			this.barItems = [btnQuery,btnReset,btnOk];
		}else{
			this.barItems[this.barItems.length] = btnQuery;
			this.barItems[this.barItems.length] = btnReset;
			this.barItems[this.barItems.length] = btnOk;
		}
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:this.barItems});
		return toolBar;
	}
});
//注册成xtype以便能够延迟加载   
Ext.reg('appcomboxgrid', Ext.ux.grid.AppComBoxGrid);  