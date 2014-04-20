Ext.namespace("Cmw.app.widget");
/**
 *  Grid Form  抽象视图类
 * 其UI结构为：
 * 	1. 左边为 Tree
 *  2. 右边为 Panel 
 *  须实现抽象方法如下：
 *  	a). getToolBar() //返回工具栏元素配置
 *  	b).	getAppTree()   //获取Grid对象
 *  	c).	getAppPanel()	//获取表单对象
 *  	
 */
Cmw.app.widget.AbsTreePanelView = Ext.extend(Ext.Panel,{
	toolBar : null,
	apptree : null,
	apppanel : null,
	globalMgr : null,
	appCmpts : {},//--> 存放 该视图用到的所有 Windows 对象//
	layoutPnl : null,	//布局面板
	layoutWH : [],	//布局的实际宽高,[300,450] ---> 300 为宽  ， 450 为高
	WIDTH : 470,	//Tab 面板默认宽度
	HEIGHT : 900,	//Tab 面板默认高度
	TREE_WIDTH : 200,	//树的宽度
	hasTopSys : false,		//是否有顶部系统面板
	topSyspanel : null, //顶部系统面板
	item1 : null,	//放Grid的面板
	item2 : null,	//放Form 的面板
	// private
	initComponent : function() {
		this.appCmpts = {};
		Cmw.app.widget.AbsTreeGridView.superclass.initComponent.call(this);
		this.initialize();
	},
	
	// private
	initEvents : function() {
		Cmw.app.widget.AbsTreeGridView.superclass.initEvents.call(this);
		this.initlisteners();
	},
	/**
	 * 组件初始化方法
	 */
	initialize : function(){
		this.createTopBar();	//创建工具栏对象
		this.createTree();	//创建Tree对象
		this.createGrid();	//创建Grid对象
		this.buildCmpts();	//开始组装以上创建的所有组件
	},
	/**
	 * 初始化事件
	 */
	initlisteners : function(){
		var self = this;
		this.apppanel.addListener("afterrender",function(cmpt){
			self.changeSize(self.getLayoutPnlWH());
			self.doLayout();
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
		var _iW = _w-this.TREE_WIDTH;
		this.item1.setHeight(_h);
		this.apptree.setHeight(_h);
		this.apppanel.setHeight(_h);
		this.item2.setHeight(_h);
		this.item2.setWidth(_iW);
		this.apppanel.setWidth(_iW);
	},
	/**
	 * 组装组件
	 */
	buildCmpts : function(){
		this.autoScroll=true;

		this.item1 = new Ext.Panel({region:'center',items : [this.apptree]});
//		this.apppanel = this.getTestGrid();
		var cfg = {region:'east',items : [this.apppanel]};
		if(this.toolBar){
			cfg.tbar = this.toolBar;
		}
		//tbar : this.toolBar,
		this.item2 = new Ext.Panel(cfg);
		
		
		var _items = [];
		if(this.hasTopSys){
			this.topSyspanel = Cmw.createSysPanel(this);
			//this.topSyspanel = this.createSysPanel(this);
			_items = [this.topSyspanel,this.item1,this.item2];
		}else{
			_items = [this.item1,this.item2];
		}
		
		var _width = this.WIDTH - this.TREE_WIDTH;	// 放Grid面板的宽度 
		this.item2["width"] = _width; // 放Grid面板的宽度 
		this.apptree.setWidth(this.TREE_WIDTH);
		var self = this;
		this.layoutPnl = new Ext.Panel({
			layout:'border',
			autoScroll:false,
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
//		var barItems = this.getTBtnsCfgs();
		this.toolBar = this.getToolBar();//ExtUtil.getTBar({aligin:'right',tBarButtons:barItems});
	},
	/**
	 * 创建Grid 面板对象
	 */
	createGrid : function(){
		if(!this["getAppPanel"]){
			ExtUtil.error({title : "创建组件错误",msg : "创建 Panel 时，须重写 getAppPanel 方法，返回一个 Grid 对象！"});
			return;
		}
		this.apppanel = this.getAppPanel();
	},
	/**
	 * 创建 Tree 对象
	 */
	createTree : function(){
		if(!this["getAppTree"]){
			ExtUtil.error({title : "创建组件错误",msg : "创建  Tree 时，须重写 getAppTree 方法，返回一个 TreePanel 对象！"});
			return;
		}
		this.apptree = this.getAppTree();
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
		this.apppanel.destroy();
		this.apptree.destroy();
		if(this.appCmpts){
			for(var key in this.appCmpts){
				var appCmpt = this.appCmpts[key];
				if(appCmpt["destroy"]) appCmpt.destroy();
				delete this.appCmpts[key];
			}
			this.appCmpts = {};
		}
		this.destroy();
	}
});