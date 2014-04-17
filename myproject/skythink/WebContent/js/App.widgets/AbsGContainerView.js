Ext.namespace("Cmw.app.widget");
/**
 * Grid 容器视图类
 * 其UI结构为：
 * 	1. 左边是选项卡树
 *  2. 点击树出现TabPanel选项卡  
 */
Cmw.app.widget.AbsGContainerView = Ext.extend(Ext.Panel,{
	queryPnl : null,
	queryFrm : null,
	toolBar : null,
	appgrid : null,
	globalMgr : null,
	appCmpts : null,//--> 存放 该视图用到的所有 Windows 对象//
	querytitle : "查询条件",
	hasTopSys : false,		//是否有顶部系统面板
	sysParams : null,/*顶部系统数据参数*/
	topSyspanel : null, //顶部系统面板
	globalMgr : null,
	appCmpts : {},//--> 存放 该视图用到的所有 Windows 对象//
	// private
	initComponent : function() {
		this.appCmpts = {};
		Cmw.app.widget.AbsGContainerView.superclass.initComponent.call(this);
		this.initialize();
	},
	
	// private
	initEvents : function() {
		Cmw.app.widget.AbsGContainerView.superclass.initEvents.call(this);
		this.initlisteners();
	},
	/**
	 * 组件初始化方法
	 */
	initialize : function(){
		if(this.hasTopSys){
			this.topSyspanel = Cmw.createSysPanel(this);
			this.add(this.topSyspanel);
		}
		this.createQueryPanel();
		this.add(this.queryPnl);
		this.createTopBar();
		this.createGrid();
		this.add(this.appgrid);
		
	},
	/**
	 * 初始化事件
	 */
	initlisteners : function(){
		var self = this;
		this.appgrid.addListener("render",function(){
			var gridwh = self.getGridWH();
			self.appgrid.resize(gridwh);
		});
	},
	/**
	 * 获取Grid 宽高
	 */
	getGridWH : function(pw,ph){
		if(!pw && !ph){
			var el = (this.tab) ? this.tab.el : this.el;
			pw = el.getComputedWidth();
			ph = el.getComputedHeight();
		}
		qph = this.queryPnl.el.getComputedHeight();
		//var pageBarHeight = this.appgrid.needPage ? 50 : 80;
		var gh = ph - qph;
		if(this.topSyspanel){
			var topPnlHeight =  this.topSyspanel.el.getComputedHeight();
			gh -= topPnlHeight;
		}
		//Cmw.print('tab.ph='+ph+',qph='+qph+',grid.qh='+qh);
		return [pw,gh];
	},
	/**
	 * 创建查询面板
	 */
	createQueryPanel : function(){
		var self = this;
		this.queryPnl = new Ext.Panel({title : this.querytitle,frame:true,border:false,collapsible:true,titleCollapse  : true});
		this.queryPnl.addListener("collapse",function(p){
			var gridwh = self.getGridWH();
			self.appgrid.setWidth(gridwh[0]);
			self.appgrid.setHeight(gridwh[1]);
		});
		this.queryPnl.addListener("expand",function(p){
			var gridwh = self.getGridWH();
			self.appgrid.setWidth(gridwh[0]);
			self.appgrid.setHeight(gridwh[1]);
		});
		if(!this["getQueryFrm"]){
			ExtUtil.error({title : "创建组件错误",msg : "创建工具栏时，须重写 getQueryFrm 方法！"});
			return;
		}
		this.queryFrm = this.getQueryFrm();
		Ext.apply(this.queryFrm,{frame:false});
		if(this.queryFrm) this.queryPnl.add(this.queryFrm);
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
	 * Resize 事件，创页面高宽变动时，重写此方法能自动填充页面
	 * @param {} pw 宽
	 * @param {} ph	高
	 */
	resize : function(pw,ph){
		var wh = this.getGridWH();
		wh[1]+=65;
		this.appgrid.resize(wh,true);
	},
	/**
	 * 组件销毁方法
	 */
	destroys : function(){
		this.toolBar.destroy();
		this.appgrid.destroy();
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