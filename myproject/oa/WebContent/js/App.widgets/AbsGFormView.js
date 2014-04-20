Ext.namespace("Cmw.app.widget");
/**
 *  Grid Form  抽象视图类
 * 其UI结构为：
 * 	1. 左边或上面为 GRID
 *  2. 右边或下面为 Form 表单 
 *  须实现抽象方法如下：
 *  	a). getTBtnsCfgs() //返回工具栏元素配置
 *  	b).	getAppGrid()   //获取Grid对象
 *  	c).	getAppForm()	//获取表单对象
 *  	
 */
Cmw.app.widget.AbsGFormView = Ext.extend(Ext.Panel,{
	toolBar : null,
	appgrid : null,
	appform : null,
	globalMgr : null,
	layoutPnl : null,	//布局面板
	layoutWH : [],	//布局的实际宽高,[300,450] ---> 300 为宽  ， 450 为高
	WIDTH : 470,	//Tab 面板默认宽度
	HEIGHT : 900,	//Tab 面板默认高度
	hasTopSys : false,		//是否有顶部系统面板
	topSyspanel : null, //顶部系统面板
	item1 : null,	//放Grid的面板
	item2 : null,	//放Form 的面板
	appCmpts : {},//--> 存放 该视图用到的所有 Windows 对象//
	//form 表单位置，及宽度配置 ,有两种取值方式：如果想让 formPanel 对象在底部，
	//则只需这样设置:gfCfg:{formAlign:'bottom',wOrh:300}	，注：当formPanel 在底部时，wOrh 就应该设为表单的高度值
	gfCfg : {formAlign:'right',wOrh:300},// formPanel  right ---> appgrid left ;  appform bottom  -- > appgrid top
	
	// private
	initComponent : function() {
		Cmw.app.widget.AbsGFormView.superclass.initComponent.call(this);
		this.initialize();
	},
	
	// private
	initEvents : function() {
		Cmw.app.widget.AbsGFormView.superclass.initEvents.call(this);
		this.initlisteners();
	},
	/**
	 * 组件初始化方法
	 */
	initialize : function(){
		this.createTopBar();	//创建工具栏对象
		this.createGrid();	//创建Grid对象
		this.createForm();	//创建Form对象
		this.buildCmpts();	//开始组装以上创建的所有组件
	},
	/**
	 * 初始化事件
	 */
	initlisteners : function(){
		var self = this;
		this.appgrid.addListener("afterrender",function(cmpt){
			self.changeSize(self.getLayoutPnlWH());
		});
	},
	/**
	 * 改变面板大小
	 * @param {} wh
	 */
	changeSize : function(wh){
		var _w = wh[0]-5;
		var _h = wh[1]-2;
		this.layoutPnl.setWidth(_w);
		this.layoutPnl.setHeight(_h);
		var _iW = _w-this.gfCfg.wOrh;
		if(this.topSyspanel){
			var el = this.topSyspanel.el;
			var topSysPnlHeight = (el) ? el.getComputedHeight() : 80;
			if(_h > topSysPnlHeight){
				_h = _h - topSysPnlHeight;
			}
		}
		this.item1.setWidth(_iW);
		this.item1.setHeight(_h);
		this.appgrid.setGH(_h);
		this.appgrid.setGW(_iW);
	},
	/**
	 * 组装组件
	 */
	buildCmpts : function(){
		this.autoScroll=true;
		this.item1 = new Ext.Panel({region:'center',bodyStyle:'height:30%',items : [this.appgrid]});
		this.item2 = new Ext.Panel({region:'east',bodyStyle:'height:30%',items : [this.appform]/*,frame:true*/});
		if(this.gfCfg.formAlign == 'bottom'){
			this.item2["region"] = 'south';
			var _height = this.HEIGHT - this.gfCfg.wOrh;	// 放Grid面板的高度 
			this.item1["height"] = _height;
			this.item2["height"] = this.gfCfg.wOrh; // 放Form面板的高度 
			this.appform.setHeight(this.gfCfg.wOrh);
		}else{	// right
			var _width = this.WIDTH - this.gfCfg.wOrh;	// 放Grid面板的宽度 
			this.item1["width"] = _width;
			this.item2["width"] = this.gfCfg.wOrh; // 放Form面板的宽度 
			this.appform.setWidth(this.gfCfg.wOrh);
		}
		
		var _items = [];
		if(this.hasTopSys){
			this.topSyspanel = Cmw.createSysPanel(this);
			//this.topSyspanel = this.createSysPanel(this);
			_items = [this.topSyspanel,this.item1,this.item2];
		}else{
			_items = [this.item1,this.item2];
		}
		
		var self = this;
		this.layoutPnl = new Ext.Panel({
			layout:'border',
			items :_items
		});
		this.add(this.layoutPnl);
	},
	
	/**
	 * 获取Grid 宽高
	 */
	getLayoutPnlWH : function(pw,ph){
		if(!pw && !ph){
			pw = this.el.getComputedWidth();
			ph = this.el.getComputedHeight();
		}
		return [pw,ph];
	},
	/**
	 * 创建顶部工具栏
	 */
	createTopBar : function(){
		if(!this["getToolBar"]){
			ExtUtil.error({title : "创建组件错误",msg : "创建工具栏时，须重写 getToolBar 方法！"});
			return;
		}
		this.toolBar = this.getToolBar();
	},
	/**
	 * 创建Grid 面板对象
	 */
	createGrid : function(){
		if(!this["getAppGrid"]){
			ExtUtil.error({title : "创建组件错误",msg : "创建 Grid 时，须重写 getAppGrid 方法，返回一个 Grid 对象！"});
			return;
		}
		this.appgrid = this.getAppGrid();
	},
	/**
	 * 创建Form 表单对象
	 */
	createForm : function(){
		if(!this["getAppForm"]){
			ExtUtil.error({title : "创建组件错误",msg : "创建  Form 时，须重写 getAppForm 方法，返回一个 Form 对象！"});
			return;
		}
		this.appform = this.getAppForm();
	},
	/**
	 * Resize 事件，创页面高宽变动时，重写此方法能自动填充页面
	 * @param {} pw 宽
	 * @param {} ph	高
	 */
	resize : function(pw,ph){
		this.changeSize([pw,ph]);
	},
	/**
	 * 组件销毁方法
	 */
	destroys : function(){
		this.toolBar.destroy();
		this.appgrid.destroy();
		this.appform.destroy();
		this.destroy();
	}
});