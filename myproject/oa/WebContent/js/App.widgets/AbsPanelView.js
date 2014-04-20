Ext.namespace("Cmw.app.widget");
/**
 *  Panel  抽象视图类
 *  
 *  须实现抽象方法如下： 
 *  	a). changeSize([width,height]) //创页面高宽变动时，重写此方法能自动填充页面
 *  	b).	destroyCmpts()   //组件销毁方法
 *  	
 */
Cmw.app.widget.AbsPanelView = Ext.extend(Ext.Panel,{
	layoutWH : [],	//布局的实际宽高,[300,450] ---> 300 为宽  ， 450 为高
	WIDTH : 470,	//Tab 面板默认宽度
	HEIGHT : 900,	//Tab 面板默认高度
	appCmpts :null,//--> 存放 该视图用到的所有 Windows 对象//
	appPanel : null,	//--> 数组 存放初始时要加入的面板组件
	toolBar : null,
	params : null,//由点击菜单传入参数
	hasTopSys : false,		//是否有顶部系统面板
	sysParams : null,/*顶部系统数据参数*/
	topSyspanel : null, //顶部系统面板
	// private
	initComponent : function() {
		this.appCmpts = {};
		Cmw.app.widget.AbsPanelView .superclass.initComponent.call(this);
		this.initialize();
	},
	
	// private
	initEvents : function() {
		Cmw.app.widget.AbsPanelView .superclass.initEvents.call(this);
		this.initlisteners();
	},
	/**
	 * 组件初始化方法
	 */
	initialize : function(){
		this.initValid();
		if(this.hasTopSys){
			this.topSyspanel = Cmw.createSysPanel(this);
			this.add(this.topSyspanel);
		}
		this.createCmpts();
		this.buildCmpts();	//开始组装以上创建的所有组件
	},
	/**
	 * 初始化事件
	 */
	initlisteners : function(){
		var _this = this;
		this.addListener("afterrender",function(cmpt){
			_this.changeSize(_this.getLayoutPnlWH());
		});
	},
	/**
	 * 组装组件
	 */
	buildCmpts : function(){
		this.autoScroll=true;
		if(this.appPanel){
			this.add(this.appPanel);
		}
	},
	initValid : function(){
		if(!this["changeSize"]){
			ExtUtil.error({title : "创建组件错误",msg : "往面板中添加组件时，须重写 调查组件大小的 changeSize([with,height]) 方法！"});
			return;
		}
		if(!this["destroyCmpts"]){
			ExtUtil.error({title : "创建组件错误",msg : "往面板中添加组件时，须重写销毁组件的 destroyCmpts() 方法！"});
			return;
		}
	},
	/**
	 * 创建组件方法
	 */
	createCmpts : function(){
		if(this["getToolBar"]){
			this.toolBar = this.getToolBar();
		}
		if(!this["getAppCmpt"]){
			ExtUtil.error({title : "创建组件错误",msg : "往面板中添加组件时，须重写  getAppCmpt 方法！"});
			return;
		}
		this.appPanel = this.getAppCmpt();
	},
	/**
	 * 获取Grid 宽高
	 */
	getLayoutPnlWH : function(pw,ph){
		if(!pw && !ph){
			var el = (this.tab) ? this.tab.el : this.el;
			pw = el.getWidth();	
			ph = el.getHeight();
			if(this.topSyspanel){
				var topPnlHeight =  this.topSyspanel.el ? this.topSyspanel.getHeight() : 80;
				ph -= topPnlHeight;
			}
		}
		return [pw,ph];
	},
	/**
	 * Resize 事件，创页面高宽变动时，重写此方法能自动填充页面
	 * @param {} pw 宽
	 * @param {} ph	高
	 */
	resize : function(pw,ph){
		if(this["changeSize"]) this.changeSize(this.getLayoutPnlWH());
	},
	/** 
	 * 组件销毁方法
	 */
	destroys : function(){
		if(this.appCmpts){
			for(var key in this.appCmpts){
				var appCmpt = this.appCmpts[key];
				if(appCmpt["destroy"]) appCmpt.destroy();
				delete this.appCmpts[key];
			}
			this.appCmpts = {};
		}
		if(this.appPanel) this.appPanel.destroy();
		//调用自定义组件销毁方法
		if(this["destroyCmpts"]){
		 	this.destroyCmpts();
		}
		this.destroy();
	}
});