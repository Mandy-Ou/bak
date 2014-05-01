/**
 * 应用程序主窗口 Window 中是一个 ViewPort
 * 其UI结构为：
 * 	1. 左边是选项卡树
 *  2. 点击树出现TabPanel选项卡
 */
Ext.namespace("Ext.ux.cmw");
Ext.ux.cmw.AppTabTreeWin = Ext.extend(Ext.Window, {
	accordUrl : './sysAccordion_navigate.action',
	params : null,
	tempParams : null,/*临时参数缓存对象*/
	layout : 'fit',	//布局为自适应
	isHidden : false,	//标识左边面板和顶部面板是否隐藏了。 false : 隐藏， true : 未隐藏
	modal : true,		// true 后面的东西不能点击 
	constrain : true,	//true : 限制窗口的整体不会越过浏览器边界
	constrainHeader : true,	//true : 只保证窗口的顶部不会越过浏览器边界，而窗口的内容部分可以超出浏览器的边界
	closeAction : 'close',
	maximizable : true, //是否启用最大化按钮 true: 启用 false : 禁用
	minimizable : true,	//启用最小化按钮
	maximized : true,	//窗口 show 完后，就最大化
	westPanel : null,	//左边卡边面板对象
	centerPanel : null,	//中间内容面板
	tabId_Prefix : 'apptab-',/*tab ID前缀*/
	treeId_Prefix : 'bnrtree-',/*树ID前缀*/
	topPanel : new Ext.Panel({
				region:'north',layout:'fit',height:45,border:false,frame:true,items : [{layout:'fit',
				html : '<center><h1 style="font-size:24px;">企业协同办公系统</h1></center>'}]
			}),
	/*新建一个 mask 加载对象*/
	loadMask : null,
    /**
     * 初始化组件方法
     */
    initComponent : function(){
    	this.initListeners();
    	Ext.ux.cmw.AppTabTreeWin.superclass.initComponent.call(this);
    	this.initViewPort();
    },
	/**
	 * 初始化相关事件
	 */
    initEvents : function(){
    	  Ext.ux.cmw.AppTabTreeWin.superclass.initEvents.call(this);
    	  this.on("resize",this.winResize);
    	  this.centerPanel.on('tabchange', this.changeTab,this);
    },
    initListeners : function(){
    	var listeners = EventManager.WinMgr.attachWinZindexListeners();
    	this.listeners = listeners;
    },
    /**
     * 创建ViewPort布局
     */
    initViewPort : function(){
    	// Loading 加载
    	this.loadMask = new Ext.LoadMask(document.body,{msg : '界面加载中...'});
    	this.loadMask.show();
    	var layoutPnl = this.buildCmpts();
    	this.add(layoutPnl);
    	this.loadMask.hide();
    	var self = this;
    	this.centerPanel.addListener('render',function(pnl){
    		self.loadMask = new Ext.LoadMask(pnl.body,{msg : '界面加载中...'}); 
    	});
    },
    /**
     * 组装控件
     */
    buildCmpts : function(){
    	//左边卡片菜单面板
    	this.westPanel = this.createAccordPanels();
    	this.centerPanel = this.createMainTabPanel();
    	//布局面板
    	
    	var layoutPnl = new Ext.Panel({
    		border : false,
    		layout : 'border',
//			autoScroll : true,
			items : [this.westPanel,this.centerPanel/*,{
				region:'south',layout:'fit',height:25,frame:true,items : [{layout:'fit',
				html : '<center><h1>©&nbsp;2010 - 2015 skythink.com. 广州天思信息技术有限公司版权所有 [ 广ICP备05023329号 ]</h1></center>'}]
			}*/]
    	});
    	return layoutPnl;
    },
    /**
     * 创建卡片树
     */
    createAccordPanels : function(){
    	var self = this;
    	   var accordion = new Ext.Panel({
    	   	id : ACCORDPNLID,
            region:'west',
            margins:'5 0 0 5',
            split:true,
//            collapseMode:'mini',
            width: 210,
            layout:'accordion',
            layoutConfig: {
		        animate: true
		    },
            items: [{title: "<b>快捷方式面板</b>"}]
        });
    	//创建卡片Panel后 ,调用  createMenuTree 添加菜单
//    	for(var i=0; i<5; i++){
        //params
	        EventManager.get(this.accordUrl,{
	        	params : this.params,
				sfn:function(json_data){
					if(null == json_data || json_data.length==0){
						ExtUtil.warn({title:Msg_SysTip.title_appwarn,msg:Msg_SysTip.msg_nohavadata});
						return;
					}
					var len = json_data.length;
					for(var i=0; i<len; i++){
						var obj = json_data[i];
						var item = self.createMenuTree(obj);
						accordion.add(item);
						if(i == (len-1)){ 
							accordion.doLayout(true); 
							//调度代码。
							item.expand(true);
						}
					}
				}
	        });
//    	}
        return accordion;
    },
    /**
     * 创建TabPanel 选项卡
     * @param node 当前点击的树节点
     */
    createMainTabPanel : function(){
    	var homePanel = this.getHomePanel();
    	var _items = [];
    	if(homePanel) _items[0] = homePanel;
    	var tabPanel = new Ext.TabPanel({
    		border:false,
			region : 'center',
			enableTabScroll : true,
			activeTab : 0,
			margins : '0 5 0 0',
			defaults: {autoScroll:true},
       		plugins: [this.getTabContextMenu(),Ext.plugin.DragDropTabs],
			items : _items
		});
    	return tabPanel;
    },
     /**
     * 获取首页面板对象
     */
    getHomePanel : function(){
    	var params = this.params;
    	params.apptabtreewinId = this.id;
    	var sysId = params.sysid;
    	if(!sysId) ExtUtil.alert({msg:'在创建首页时， sysId 为空，无法为首页面板设置ID。'});
    	var homeId = HOME_ID + sysId;
    	var homePanel = new Ext.Panel({
    			id : homeId,
				title : '我的桌面',
				border : false,
				autoHeight : true,
				listeners : {'afterrender':function(panel){
					Cmw.importPackage('pages/app/sys/home/HomeModsMgr',function(module) {
					 	module.viewUI.show(homePanel,params);
			  		});
				}}
			});
		return homePanel;
    },
    /**
     * 创建菜单树
     */
     createMenuTree : function(obj){
    	var cfg = {};
		var id = obj.id;
		cfg["id"] = this.treeId_Prefix+id;
		cfg["title"] = "<b>"+obj.text+"</b>";
		if(obj.iconCls){
			cfg["iconCls"] = obj.iconCls;
		} 
		if(id.indexOf('C') != -1) id = id.replace('C','');
		var url = obj.jsArray;
    	//此处加载应用程序UI
		var root = new Ext.tree.AsyncTreeNode({
						text : '系统菜单',
						draggable : false,
						loader : new Ext.tree.TreeLoader({
							dataUrl : url,
							baseParams : {accordionId:id}
						})
					});
		var ref = {id : 'refresh',myid:id,qtip:Msg_AppCaption.menu_refresh,handler:function(){
			root.reload();
		}};
		cfg["tools"] = [ref];
		cfg["rootVisible"] = false;
		cfg["expType"] = 1;
		var tree = new Ext.ux.tree.MyTree(cfg);
		tree.setRootNode(root);
		
		this.addTreeListeners(tree);
		return tree;
    },
    /**
     * 为 tree 添加事件
     * @param {} tree 要添加事件的树对象
     */
	addTreeListeners : function(tree){
		var self = this;
		//渲染后展开节点
		tree.addListener("render",function(cmpt){
			tree.getRootNode().expand();
		});
		//单击事件绑定函数
		tree.addListener("click",function(node,e){
			self.menuActionClick(node,e);
		});
		//右键菜单事件
		tree.addListener("contextmenu",function(node,e){

		});
	},
	/**
	 * 
	 * 菜单点击事件
	 * @param {} node 点击的节点
	 * @param {} e 点击事件源
	 */
	menuActionClick : function(node,e){
		/*如果是父节点则返回 false,不做任何处理*/
		if(!node.leaf) return false;
		var attrs = node.attributes;
		var custTabId = attrs.tabId;
		var cached = attrs.cached;
		//根据 tabId 得到 tab组件
		var tabId = this.tabId_Prefix+(custTabId?custTabId : node.id);
		var tab =  this.centerPanel.getComponent(tabId);
		//如果该 tab 项存在就只需激活即可
		if(tab){
			this.centerPanel.setActiveTab(tab);
			if(tab["notify"]) tab.notify(tab);
			return;
		}
	
		
		var tabTitle = node.text;
		var jsArr = attrs.jsArray;
		var loadType = attrs.loadType;
		if(!loadType) loadType = 1;	/*标准加载方式*/
		if(!jsArr){
			ExtUtil.error({msg:'找不到此菜单的界面文件，无法加载此功能UI！'});
			return;
		}
		var _loadMask = this.loadMask;
		_loadMask.show();
		var tabItem = new Ext.Panel({id : tabId,title : tabTitle,
		autoScroll : true,layout : 'fit',border : false,closable : true,cached:cached});
		tab = this.centerPanel.add(tabItem);
		var treeId = null;
		if(node.getOwnerTree){
			var tree = node.getOwnerTree();
			treeId = (tree && tree["id"]) ? tree.id : null;
		}
		if(!treeId && attrs["treeId"]){
			treeId = attrs["treeId"];
		} 
		//将树ID绑定到相应的 tabItem 上
		if(treeId) tab["treeId"] = treeId;
		var _this = this;
		var model = null;
		if(loadType && loadType == 1){	//标准 localXHR 方式加载
			var jsStr = "";
			//加载模块js预置变量名
			 Ext.Ajax.request({
				method:'POST',//为了不丢失js文件内容，所以要选择post的方式加载js文件
				url: Cmw.getUrl(jsArr),   
				scope: this,   
				success: function(response){
					jsStr = response.responseText;
					//获取模块类  
					this[node.id] = eval(jsStr);  
					//实例化模块类
					tab = _this.getModelParamsToTab(tab,tabId,node);
					model = new this[node.id](tab);
					
					//TabPannel 改变时，重新初始化
					_this.fireModeListeners(tab,model);
					_this.centerPanel.setActiveTab(tab);
					if(tab["notify"]) tab.notify(tab);
					_loadMask.hide();
				},
				failure:function(response){
					Ext.Msg.show({
						title: "错误信息",
						msg:"加载页面核心文件时发生错误！",
						buttons:Ext.MessageBox.OK,
						icon: Ext.MessageBox.ERROR
					});
					_loadMask.hide();
				}
			 });
		}else{	//用 seaJs 方式加载
			jsArr = jsArr.replace(".js","");
			this.loadUiBysea(jsArr,tab,tabId,node);
		}
	},
	/**
	 * 用 seaJs 的方式加载UI界面
	 * @param {} url	JS文件地址
	 * @param {} tab	Tab 面板对象
	 * @param {} tabId	Tab Id
	 * @param {} node   Tree Node 对象
	 */
	loadUiBysea : function(url,tab,tabId,node){
		var _this = this;
		url = url.replace(".js","");
		Cmw.importPackage(url,function(module) {
			 	model = module.viewUI;
			 	//实例化模块类
				tab = _this.getModelParamsToTab(tab,tabId,node);
			 	var viewUI = model.getMainUI(tab,tab.params);
			 	tab.add(viewUI);
				//TabPannel 改变时，重新初始化
				_this.fireModeListeners(tab,model);
				_this.centerPanel.setActiveTab(tab);
				if(tab["notify"]) tab.notify(tab);
				_this.loadMask.hide();
	  	});
	},
	getModelParamsToTab : function(tab,tabId,node){
		var model_params = {};
		Ext.apply(model_params,this.params);
		if(this.tempParams){
			Ext.apply(model_params,this.tempParams);
			this.tempParams = null;
		}
		model_params["tabId"] = tabId;
		model_params["treeId"] = tab["treeId"];
		if(node){
			model_params["nodeId"] = node.id;
			/* CURR_NODE_KEY 定义在 constant.js */
			model_params[CURR_NODE_KEY] = node;		
		}
		model_params["apptabtreewinId"] = this.id;
		var tabParams = tab.params;
		if(tabParams){
			var temp = {};
			Ext.apply(temp,model_params);
			Ext.apply(temp,tabParams);
			tabParams = temp;
		}else{
			tabParams = model_params;		
		}
		var menuParams = this.getMenuParams(node);
		var _params = {};
		if(menuParams) Ext.apply(_params,menuParams);
		Ext.apply(_params,tabParams);
		tab.params = _params;
		return tab;
	},
	/**
	 * 获取菜单中指定的初始参数
	 * @param node 树节点
	 */
	getMenuParams : function(node){
		var attrs = null;
		var menuParams = null;
		if(node) {
			attrs = node.attributes;
			menuParams = attrs.params;
		}
		if(!menuParams || menuParams.indexOf("=") == -1) return null;
		var parsGps = menuParams.split("&");
		if(null == parsGps || parsGps.length == 0) return null;
		var _params = {};
		for(var i=0,count=parsGps.length; i<count; i++){
			var parKv = parsGps[i].split("=");
			var parName = parKv[0];
			var parVal = parKv[1];
			_params[parName] = parVal;
		}
		return _params;
	},
	fireModeListeners : function(tab,model){
		tab.on('resize',function(_tab, adjWidth, adjHeight, rawWidth,rawHeight){
			if(model["resize"])model.resize(adjWidth,adjHeight,_tab);
		});
		tab.on('destroy',function(_tab){
			if(model["destroy"])model.destroy(_tab);
		});
		tab.on('beforedestroy',function(_tab){
			var cached = _tab.cached;
			if(cached==1){//缓存的组件
				_tab.hide();
				return false;
			}
			return true;
		});
	},
	/**
	 * Tab change 的实现
	 * */
	changeTab : function(tab,newtab){
		var treeId = newtab["treeId"];
		if(!treeId) return;
		var currTree = Ext.getCmp(treeId);
		if(!currTree) return;
		currTree.expand();
		//如果存在相应树节点，就选中,否则就清空选择状态
        var nodeId = newtab.id.replace(this.tabId_Prefix,'');
        var node = currTree.getNodeById(nodeId);
        if(node){
            currTree.getSelectionModel().select(node);
        }else{ 
            currTree.getSelectionModel().clearSelections();
        }
	},
	/**
	 * 根据自定义 tabId 激活相应的 Tab 
	 * @param {} custTabId	自定义 tabId 
	 * @param {} params 所传参数
	 * @param String url 如果不从菜单表中取数据，可通过此URL自定义加载指定的JS 界面文件
	 * @param String tabTitle Tab 标题
	 */
	activeByCustTabId : function(custTabId,params,url,tabTitle){
		//根据 tabId 得到 tab组件
		var tabId = this.tabId_Prefix+custTabId;
		var tab =  this.centerPanel.getComponent(tabId);
		var isnewInstance = null;
		if(params){
			 isnewInstance = params.isnewInstance;
		}
		if(isnewInstance && tab){
			tab.destroy();
			this.centerPanel.remove(tab);
			tab = null;
		}
		//如果该 tab 项存在就只需激活即可
		if(tab){
			if(!params) params = {};
			var newParams = {};
			if(params) Ext.apply(newParams,params);
			Ext.apply(newParams,this.params);
			tab.params = newParams;
			this.centerPanel.setActiveTab(tab);
			if(tab["notify"]) tab.notify(tab);
			return;
		}else{
			if(url){
				this.loadMask.show();
				var newParams = {};
				Ext.apply(newParams,this.params);
				if(params) Ext.apply(newParams,params);
				//if(params) Ext.apply(params,this.params);
				var tabItem = new Ext.Panel({id : tabId,title : tabTitle,
				autoScroll : true,layout : 'fit',border : false,closable : true,cached:1,params:newParams});
				tab = this.centerPanel.add(tabItem);
				this.loadUiBysea(url,tab,tabId);
				return;
			}
		}
		
		var _this = this;
		var tatItem = Ext.getCmp(this.tabId_Prefix+custTabId);
		 EventManager.get('./sysMenu_single.action',{params:{tabId:custTabId},sfn:function(json_data){
		 	if(!json_data) return;
			if(params){
				if(!_this.tempParams) _this.tempParams = {};
				Ext.apply(_this.tempParams, params);
			}
		 	var nodeCfg = json_data;
		 	var treeId = _this.treeId_Prefix+nodeCfg.accordionId;
		 	nodeCfg["treeId"] = treeId;
		 	var node = new Ext.tree.TreeNode(nodeCfg);
		 	_this.menuActionClick(node,null);
		 }});
	},
	/**
	 * 隐藏指定的 TabItem 对象
	 * @param tab 要隐藏的 Tab 对象
	 */
	hideTabItem : function(tab){
		this.centerPanel.hideTabStripItem(tab);
	},
	/**
	 * 显示指定的 TabItem 对象
	 * @param tab 要显示的 Tab 对象
	 */
	showTabItem : function(tab){
		this.centerPanel.unhideTabStripItem(tab);
	},
	/**
	 * 为 TabPanel 添加右键菜单
	 * @return {}
	 */
	getTabContextMenu : function(){
		var self = this;
		var itemAll = new Ext.menu.Item({text : '全屏',handler : function(){
    		this.isHidden = false;
    		if(self.westPanel) self.westPanel.setVisible(this.isHidden);
    		if(self.topPanel) self.topPanel.setVisible(this.isHidden);
    		var winEl = self.getEl();
    		var winWidth = winEl.getComputedWidth()-1;
    		self.setWidth(winWidth);
    		itemAll.disable();
    		itemUnAll.enable();
    	}});
    	
    	var itemUnAll = new Ext.menu.Item({
	    	text : '退出全屏',handler : function(){
	    		this.isHidden = true;
	    		this.disable();
	    		if(self.westPanel) self.westPanel.setVisible(this.isHidden);
	    		if(self.topPanel) self.topPanel.setVisible(this.isHidden);
	    		var winWidth = self.getEl().getComputedWidth();
    			self.setWidth(winWidth+1);
    			itemUnAll.disable();
    			itemAll.enable();
	    	}
    	});
    	
		var tabMenu = new Ext.ux.TabCloseMenu({closeTabText:'关闭当前',closeOtherTabsText:'关闭其它',closeAllTabsText:'关闭所有',appendItems:[itemAll,itemUnAll]});
    	return tabMenu;
	},
	/**
	 * Window 窗体大小变动时触发
	 */
	winResize : function(win,width,height){
		this.centerPanel.doLayout();
		win.doLayout();
	}	
});
//注册成xtype以便能够延迟加载   
Ext.reg('appwindow', Ext.ux.cmw.AppTabTreeWin);

Ext.ns("Ext.plugin");

Ext.plugin.DragDropTabs = (function() {
	var TabProxy = Ext.extend(Ext.dd.StatusProxy, {
		constructor: function(config) {
			TabProxy.superclass.constructor.call(this, config);
			// Custom class needed on the proxy so the tab can
			// be dropped in and retain its styles
			this.el.addClass("dd-tabpanel-proxy");
		},
		reset: function(config) {
			TabProxy.superclass.reset.apply(this, arguments);
			this.el.addClass("dd-tabpanel-proxy");
			this.ghost.removeClass(["x-tab-strip-top","x-tab-strip-bottom"]);
		}
	}),
	    TabDragZone = Ext.extend(Ext.dd.DragZone, {
		constructor: function(tp, config) {
			this.tabpanel = tp;
			this.ddGroup = tp.ddGroup;
			this.proxy = new TabProxy();
			TabDragZone.superclass.constructor.call(this, tp.strip, config);
		},
		getDragData: function(e) {
			var t = this.tabpanel.findTargets(e);
			if(t.el && ! t.close) {
				return {
					ddel: t.el,
					tabpanel: this.tabpanel,
					repairXY: Ext.fly(t.el).getXY(),
					item: t.item,
					index: this.tabpanel.items.indexOf(t.item)
				}
			}
			return false;
		},
		
		onInitDrag: function() {
			TabDragZone.superclass.onInitDrag.apply(this, arguments);
			var strip = this.tabpanel.strip,
			    ghostClass;
			// In order for the tab to appear properly within the
			// ghost, we need to add the correct class from the
			// tab strip
			if(strip.hasClass("x-tab-strip-top")) {
				ghostClass = "x-tab-strip-top";
			} else if(strip.hasClass("x-tab-strip-bottom")) {
				ghostClass = "x-tab-strip-bottom";
			}
			this.proxy.getGhost().addClass(ghostClass);
			return true;
		},
		getRepairXY: function() {
			return this.dragData.repairXY;
		}
	}),
	    TabDropTarget = Ext.extend(Ext.dd.DropTarget, {
		constructor: function(tp, config) {
			this.tabpanel = tp;
			this.ddGroup = tp.ddGroup;
			TabDropTarget.superclass.constructor.call(this, tp.strip, config);
		},
		notifyDrop: function(dd, e, data) {
			var tp = this.tabpanel,
			    t = tp.findTargets(e),
			    i = tp.items.indexOf(t.item),
			    activeTab = tp.getActiveTab();
			if(tp === data.tabpanel) {
				tp.suspendEvents();
			}
			data.tabpanel.remove(data.item, false);
			tp.insert(i, data.item);
			tp.setActiveTab(activeTab);
			if(tp === data.tabpanel) {
				tp.resumeEvents();
				tp.fireEvent("tabmove", tp, i, data.index);
			}
			return true;
		}
	}),
	    // Methods attached to the TabPanel
	    onTabPanelRender = function() {
		this.dragZone = new TabDragZone(this);
		this.dropTarget = new TabDropTarget(this);
	},
	    onTabPanelInitEvents = function() {
		this.mun(this.strip, "mousedown", this.onStripMouseDown, this);
		this.mon(this.strip, "click", this.onStripMouseDown, this);
	},
	    onTabPanelDestroy = function() {
		Ext.destroy(this.dragZone, this.dropTarget);
		if(this.dragZone) {
			this.dragZone.destroy();
		}
		if(this.dropTarget) {
			this.dropTarget.destroy();
		}
	};
	
	return {
		init: function(tp) {
			Ext.applyIf(tp, {
				ddGroup:"TabPanelDD",
				tabSwitchOnClick: false
			});
			tp.addEvents("tabmove");
			tp.afterMethod("onRender", onTabPanelRender);
			if(tp.tabSwitchOnClick) {
				tp.afterMethod("initEvents", onTabPanelInitEvents);
			}
			tp.afterMethod("onDestroy", onTabPanelDestroy);
		}
	};
})();