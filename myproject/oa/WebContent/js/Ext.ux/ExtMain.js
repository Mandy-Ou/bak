/**
 * ExtJS2.2 示例展示 页面布局JS文件
 */

Ext.BLANK_IMAGE_URL = '../images/public/s.gif';
Ext.QuickTips.init();	//加载快速提示框
/*定义命名空间*/
Ext.namespace('ExtExample');
/*应用程序主类*/
ExtExample.app = function(){};
/*通过继承为 app 类获得事件*/
Ext.extend(ExtExample.app,Ext.util.Observable,{
	params : null,
	tempParams : null,/*临时参数缓存对象*/
	navPnl : null,/*左侧菜单导航栏*/
	centerPanel : null,	//中间内容面板
	topPanel : null,/* Header 头部*/
	tabId_Prefix : 'apptab-',/*tab ID前缀*/
	treeId_Prefix : 'bnrtree-',/*树ID前缀*/
	/*新建一个 mask 加载对象*/
	loadMask : null,
	isHidden : false,
	systemItemId_prefix : 'systemItemId_',
	sysAccordionContainerId_prefix :'sysAccordionContainerId_',/*卡片容器菜单ID前缀*/
	sysAccordionItemId_prefix :'sysAccordionItemId_',/*卡片菜单项ID*/
	sysMenuItemId_prefix : 'sysMenuItemId_',/*菜单前缀*/
	systemUrl : './sysSystem_desklist.action',
	systemIdArr : null,
	systemListDatas : null,
	selectSystemItemId : null,/*当前选中的系统*/
	selectAccordionItemId : null,/*当前选中的卡片菜单ID*/
	currSystemData : null,/*当前选中的系统数据*/
	cacheMgr : {/*缓存器*/
		accordionDatas : null,/*卡片菜单数据，数据格式：{系统ID:[{卡片1},{卡片2}]}*/
		menuDatas : null/*普通菜单数据，数据格式：{卡片ID:[{菜单1},{菜单2}]}*/
	},
	idMgr : {
		systemListId : Ext.id(null,"systemListId"),/*显示系统的 UL 标记的ID*/
		navUserDivId : Ext.id(null,"navUserDivId"),/*用户中心DIV ID*/
		navWorkDivId : Ext.id(null,"navWorkDivId"),/*工作台中心DIV ID*/
		navWorkShortcutsDivId : Ext.id(null,"navWorkShortcutsDivId"),/*快捷方式面板DIV ID*/
		navAccordionDivId : Ext.id(null,"navAccordionDivId"),/*显示卡片菜单的DIV ID*/
		oaAccordionMenusDivId : 'oa_accordion_menus_20131129',/*卡片菜单对应的子菜单*/
		oa_user_functin_logoutId : Ext.id(null,"oa_user_functin_logout")/*用户退出ID*/
	},
	classMgr :{
		headerCls : {
			oa_header_tab : "oa_header_tab",
			oa_header_logo : "oa_header_logo",
			oa_header_systems : "oa_header_systems",
			oa_logo_end_td : "oa_logo_end_td",
			oa_system_focus : "oa_system_focus",
			oa_system_active : "oa_system_active"
		},
		navCls : {
			oa_nav_view : 'oa_nav_view',
			oa_nav_user : 'oa_nav_user',
			oa_nav_work : 'oa_nav_work',
			oa_nav_accordion : 'oa_nav_accordion'
		}
	},
	/**
	 * 获取顶部HTML
	 */
	getHeader : function(){
		var htmlArr = [
			'<table  class="'+this.classMgr.headerCls.oa_header_tab+'"> <tr>',
			' <td class="'+this.classMgr.headerCls.oa_header_logo+'"> <img src="./images/main/logo.gif"/></td>',
			' <td><div class="'+this.classMgr.headerCls.oa_header_systems+'"><ul id="'+this.idMgr.systemListId+'"></ul></div></td>',
			'<td class="'+this.classMgr.headerCls.oa_logo_end_td+'"><img src="./images/main/banner_end_boder.gif" /></td>',
			' </tr></table>'];
		var html = htmlArr.join(" ");
		
		var header = new Ext.BoxComponent({
				region : 'north',
				frame : false,
				html : html
		});
		this.loadSystemDatas();
		return header;
	},
	/**
	 * 获取左侧导航面板
	 */
	getNavPnl : function(){
		var html = this.getNavHtml();
		var _this = this;
		var navPnl = new Ext.Panel({
			region : 'west',
			baseCls : this.classMgr.navCls.oa_nav_view,
			autoScroll : false,
			enableDD : true,	//是否支持拖拽
			containerScroll : false,	//是否支持滚动条
			split : true,	//是否支持分割
			width : 200,	//Panel 宽度
			minSize : 175, //可拖动最小宽度
			maxSize : 300,	//可拖动最大宽度
			collapseMode : 'mini', //在分割线处出现按钮
			//collapsible : true,
			html : html,
			listeners : {
				afterrender : function(pnl){
					_this.setWorkShortcutsHeight(0);
					_this.initListeners();
				}
			}
		});
		return navPnl;
	},
	/**
	 * 初始化绑定事件
	 */
	initListeners : function(){
		var _this = this;
		var oaAccordionMenusDiv = Ext.get(this.idMgr.oaAccordionMenusDivId);
		oaAccordionMenusDiv.on('mouseleave',function(e,t,o){
			oaAccordionMenusDiv.hide(true);
		})
		Ext.getBody().on('click',function(e,t,o){
			_this.hideMenus();
		});
	},
	getNavHtml : function(){
			var htmlArr = [
			'<div id="'+this.idMgr.navUserDivId+'" class="'+this.classMgr.navCls.oa_nav_user+'">',
			'<div class="user_top_cls">',
        	'<span>',
            '	<span class="user_title">',
            '    	用户中心',
            '    </span>',
            '    <span class="user_functin" >',
            '    	<span id="'+this.idMgr.oa_user_functin_logoutId+'" class="oa_user_functin_logout" title="退出系统"></span>',
            '        <span class="oa_user_functin_webqq" title="Web QQ及时通讯"></span>',
            '    </span>',
            '</span>',
        	'</div>',
        	'<div class="oa_user_center_main">',
        	'<div class="user_frame">',
            '	<img src="./images/main/default_user.png" />',
            '</div>',
            '<div class="dosomethine_list">',
            '	<span><strong>消息(8)</strong></span>',
            '    <span><strong>暂存(6)</strong></span>',
            '    <span><strong>待审批(6)</strong></span>',
            '</div>',
        	'</div>',
        	'<p>程明卫(研发部)[技术总部]</p>',
        	'</div>',
			'<div id="'+this.idMgr.navWorkDivId+'" class="'+this.classMgr.navCls.oa_nav_work+'">',
				'<div class="user_top_cls">',
        		'<span>',
            	'<span class="user_title">',
                '	&nbsp;&nbsp;工作台',
                '</span>',
                '<span class="user_functin" >',
                '	<span class="oa_user_functin_shortcut" title="点击切换到快捷方式视图"></span>',
                '    <span class="oa_user_functin_report" title="点击切换到报表视图"></span>',
                '</span>',
            	'</span>',
        		'</div>',
        		'<div id="'+this.idMgr.navWorkShortcutsDivId+'" class="oa_work_shortcuts">',
        		'<!--  OA -->',
	        	'<div class="oa_work_group">',
	            '	<span class="oa_work_group_bk">',
	            '	<span>OA (4个)</span>',
	            '	</span>',
	            '   <ul class="oa_shortcuts_menu_list">',
	            '    	<li>',
	            '        	<img src="./images/main/oa_icons/email.png"/>',
	            '            <span>电子邮件</span>',
	            '        </li>',
	            '        <li>',
	            '        	<img src="./images/main/oa_icons/calendar.png"/>',
	            '            <span>日程安排</span>',
	            '        </li>',
	            '        <li>',
	            '        	<img src="./images/main/oa_icons/address.png"/>',
	            '            <span>通讯录</span>',
	            '        </li>',
	            '        <li>',
	            '        	<img src="./images/main/oa_icons/news.png"/>',
	            '            <span>公司新闻</span>',
	            '        </li>',           
	            '        <span class="clear"></span>',
	            '    </ul>',
	            '</div>',
	        	'<!--  人力资源 -->',
	        	'<div class="oa_work_group">',
	            '	<span class="oa_work_group_bk">',
	            '	<span >人力资源 (2个)</span>',
	            '	</span>',
	            '    <ul class="oa_shortcuts_menu_list">',
	            '    	<li>',
	            '        	<img src="./images/main/oa_icons/manres/hr.png"/>',
	            '            <span>员工档案</span>',
	            '        </li>',
	            '       <li>',
	            '        	<img src="./images/main/oa_icons/manres/exam_manage.png"/>',
	            '            <span>考勤记录</span>',
	            '        </li>',
	            '        <span class="clear"></span>',
	            '    </ul>',
	            '</div>',
	        '</div>',
			'</div>',
			'<div id="'+this.idMgr.navAccordionDivId+'" class="'+this.classMgr.navCls.oa_nav_accordion+'"></div>'
		];
		var html = htmlArr.join("");
		return html;
	},
	/* Footer 底部*/
	footer: new Ext.BoxComponent({
		region:'south',
		height:'25'
	}),
	/*页面中间主要显示区域*/
	centerPanel : new Ext.TabPanel({
			region : 'center',
			enableTabScroll : true,
			activeTab : 0,
			margins : '0 5 5 0',
			items : [new Ext.Panel({
				id : 'workPing',
				title : '首页',
				border : false,
				autoLoad : 'info.html'
			})]
		}),
	/*初始化构造函数*/
	init : function(){
		this.topPanel = this.getHeader();
		//给树形菜单添加事件
		this.navPnl = this.getNavPnl();
		var myView = new Ext.Viewport({
			layout : 'border',
			border : false,
			frame : false,
			items :  [this.topPanel,this.navPnl,this.centerPanel]//[this.topPanel,this.navPnl,this.footer,this.centerPanel]
		});
		/*新建一个 mask 加载对象*/
		this.loadMask = new Ext.LoadMask(this.centerPanel.body,{msg : '页面加载中...'});
	},
	/**
	 * 加载系统菜单数据
	 */
	loadSystemDatas : function(){
		var _this = this;
		this.systemIdArr = [];
		this.systemListDatas = {};
		EventManager.get(Cmw.getUrl(_this.systemUrl),{params:{},sfn:function(json_data){
			var list = json_data.list;
			var systemListEl = Ext.get(_this.idMgr.systemListId);
			var html = "";
			if(!list || list.length == 0){
				html = '<li style="font-weight:bold;color:red;text-align:center;">没有系统方问权限...</li>';
			}else{
				var htmlArr = [];
				for(var i=0,count=list.length; i<count; i++){
					var data = list[i];
					htmlArr[htmlArr.length] = getSysItemHtml(data);
				}
				html = htmlArr.join("");
			}
			systemListEl.update(html,true,bindListeners);
		},ffn:function(json_data){
			ExtUtil.alert({msg:json_data.msg});
		}});
		
		/**
		 * 根据系统数据获取系统项菜单HTML
		 */
		function getSysItemHtml(data){
			var id = data.id;
			var name = data.name;
			var icon = data.icon;
			var systemItemId = _this.systemItemId_prefix+id;
			_this.systemIdArr[_this.systemIdArr.length] = systemItemId;
			_this.systemListDatas[systemItemId] = data;
			var html = ['<li id="'+systemItemId+'"  title="'+name+'">',
                	'<img src="'+icon+'"/>',
                	'<span>'+name+'</span>'];
			return html.join("");
		}
		/**
		 * 为系统图标绑定事件
		 */
		function bindListeners(){
			if(!_this.systemIdArr || _this.systemIdArr.length == 0) return;
			for(var i=0,count=_this.systemIdArr.length; i<count; i++){
				var systemId = _this.systemIdArr[i];
				var systemEl = Ext.get(systemId);
				if(!systemEl) continue;
				var data = _this.systemListDatas[systemId];
				var ops = {systemItemId : systemId,data:data};
				systemEl.addClassOnOver(_this.classMgr.headerCls.oa_system_focus);
				systemEl.on('click',function(e,t,o){
					var systemItemId = o.systemItemId;
					var activeCls = _this.classMgr.headerCls.oa_system_active;
					if(_this.selectSystemItemId && _this.selectSystemItemId != systemItemId){
						Ext.get(_this.selectSystemItemId).removeClass(activeCls);
					}
					var data = o.data;
					Ext.get(systemItemId).addClass(activeCls);
					_this.selectSystemItemId = systemItemId;
					_this.loadAccordionDatas(systemItemId,data);
				},this,ops);
			}			
		}
	},
	/**
	 * 加载卡片菜单
	 */
	loadAccordionDatas : function(systemItemId,data){
		var _this = this;
		this.currSystemData = data;
		var systemName = data.name;
		var sysId = data.id;
		var accordionContainerId = this.sysAccordionContainerId_prefix+sysId;
		var accordionContainer = Ext.get(accordionContainerId);
		if(accordionContainer) return;
		var navAccordionDivEle = Ext.get(this.idMgr.navAccordionDivId);
		navAccordionDivEle.update("");/*清空卡片菜单容器中的所有卡片元素*/
		sysId = sysId+"";
		if(this.cacheMgr.accordionDatas && this.cacheMgr.accordionDatas[sysId]){
			this.createAccordionMenus(this.cacheMgr.accordionDatas[sysId]);
		}else{
			var url = './sysAccordion_list.action';
			EventManager.get(url,{params:{sysid:sysId,actionType:1},sfn:function(json_data){
				  var accordionDatas = json_data.list;
				  if(!accordionDatas || accordionDatas.length == 0) return;
				  _this.createAccordionMenus(accordionDatas);
				  if(! _this.cacheMgr.accordionDatas) _this.cacheMgr.accordionDatas = {};
				  _this.cacheMgr.accordionDatas[sysId] = accordionDatas;
			}});
		}
	},
	/**
	 * 创建卡片菜单
	 * @param {} dataList
	 */
	createAccordionMenus : function(dataList){
		var _this = this;
		var sysId = this.currSystemData.id;
		var systemName = this.currSystemData.name;
		var htmlArr = ['<div class="current_system_title">'+systemName+'</div>','<ul>'];
		var eleDatas = {};
		for(var i=0,count=dataList.length; i<count; i++){
			var data = dataList[i];
			var id = data.id;
			var eleId = this.sysAccordionItemId_prefix + id;
			eleDatas[eleId] = data;
			htmlArr[htmlArr.length] = getAccordionHtml(eleId,data);
		}
		htmlArr[htmlArr.length] = '</ul>';
		var accordionContainerId = this.sysAccordionContainerId_prefix+sysId;
		var html = '<div id="'+accordionContainerId+'">'+htmlArr.join(" ")+'</div>';
		var navAccordionDivEle = Ext.get(this.idMgr.navAccordionDivId);
		var accordionDivHeight = 30 + dataList.length*30;
		navAccordionDivEle.update(html,false,function(){
			bindaccordionListeners();
		});
	
		/**
		 * 获取卡片菜单数据
		 */
		function getAccordionHtml(eleId,data){
			var name = data.name;
			var iconCls = data.iconCls || './images/main/oa_dangan.png';
			return '<li id="'+eleId+'" class="oa_nav_accordion_UL_LI"><img src="'+iconCls+'"/><strong>'+name+'</strong> <span>&gt;</span></li>';
		}
		var cls = "oa_nav_accordion_active";
		function bindaccordionListeners(){
			for(var eleId in eleDatas){
				var data = eleDatas[eleId];
				var accordionId = data.id;
				var accordionName = data.name;
				var url = data.url;
				var op = {url:url,accordionId:accordionId,accordionName:accordionName,eleId:eleId};
				var accordionEle = Ext.get(eleId);
//				accordionEle.on('mouseover',function(e,t,o){
				accordionEle.on('mouseenter',function(e,t,o){
					var eleId = o.eleId;
					var ele = Ext.get(eleId);
					_this.showMenus(ele,o);
				},this,op);
			
			}
			_this.setWorkShortcutsHeight(accordionDivHeight);
		};
	},
	/**
	 * 设置快捷方式面板高度
	 */
	setWorkShortcutsHeight : function(accordionDivHeight){
		var bodyHeight = Ext.getBody().getHeight();
		var topHeight = this.topPanel.getHeight();
		var userDivHeight = Ext.get(this.idMgr.navUserDivId).getHeight();
		var wsheight = bodyHeight - topHeight - userDivHeight - accordionDivHeight - 50;
		Cmw.print("bodyHeight="+bodyHeight+",topHeight="+topHeight+",userDivHeight="+userDivHeight
		+",accordionDivHeight="+accordionDivHeight+",wsheight="+wsheight);
		Ext.get(this.idMgr.navWorkDivId).setHeight(wsheight+30);
		Ext.get(this.idMgr.navWorkShortcutsDivId).setHeight(wsheight);
	},
	showMenus : function(ele,op){
		var _this = this;
		var eleId = op.eleId;
		var url = op.url;
		var accordionName = op.accordionName;
		if(!url){
			ExtUtil.error({msg:"["+accordionName+"] 没有配置加载菜单数据的 url 地址，请在[系统基础平台 -> 系统功能列表 -> 卡片菜单管理] 功能中设置 \"url\" 值!"});
			return;
		}
		var accordionId = op.accordionId;
		var cls = "oa_nav_accordion_active";
		if(!this.selectAccordionItemId){
			this.selectAccordionItemId = eleId;
		}else{
			if(this.selectAccordionItemId == eleId){
				_this.showByAccordionMenu(Ext.get(_this.selectAccordionItemId));
				return;
			}else{
				var preEle = Ext.get(this.selectAccordionItemId);
				if(preEle){
					preEle.removeClass(cls);
//					_this.hideMenus();
				}
				this.selectAccordionItemId = eleId;
			}
		}
		ele.addClass(cls);
		if(this.cacheMgr.menuDatas && this.cacheMgr.menuDatas[accordionId]){
			var menuList = this.cacheMgr.menuDatas[accordionId];
			this.loadMenuDatas(accordionId,menuList);
		}else{
			EventManager.get(url,{params:{accordionId:accordionId,action:0},sfn:function(json_data){
				  var menuList = json_data.list;
				  if(!menuList || menuList.length == 0) return;
				  _this.loadMenuDatas(accordionId,menuList);
				  if(! _this.cacheMgr.menuDatas) _this.cacheMgr.menuDatas = {};
				  _this.cacheMgr.menuDatas[accordionId] = menuList;
			}});
		}
	},
	/**
	 * 加载菜单
	 * @param {} menuList 菜单数据
	 * @param {} params 远程加载参数
	 */
	loadMenuDatas : function(accordionId,menuList){
		var _this = this;
		//id,text,pid,jsArray,icon,leaf,type,accordionId,loadType,tabId,cached,params,modDatas
		var _accordionId = "C"+accordionId;
		var dataArr = [];
		var dataOtherArr = [];
		for(var i=0,count=menuList.length; i<count; i++){
			var data = menuList[i];
			var leaf = data.leaf;
			if(data.pid != _accordionId) continue;
			
			if(data.hasOwnProperty("leaf") && (!leaf || leaf == "false")){
				dataArr[dataArr.length] = data;
			}else{
				dataOtherArr[dataOtherArr.length] = data;
			}
		}
		var meteaCfgArr = [];
		if(dataOtherArr.length > 0 && dataOtherArr.length == menuList.length){
			meteaCfgArr[0] = this.getMenuGroupHtml('默认',dataOtherArr);
		}else{
			if(dataOtherArr.length > 0 && dataOtherArr.length < menuList.length){
				var arr = this.groupMenus(dataArr,menuList);
				if(arr && arr.length > 0) meteaCfgArr = arr;
				meteaCfgArr[meteaCfgArr.length] = this.getMenuGroupHtml('其他',dataOtherArr);
			}else{/*-- 无其它数据 ---*/
				if(dataArr.length == menuList.length){
					meteaCfgArr[0] = this.getMenuGroupHtml('默认',dataArr);
				}else{
					meteaCfgArr = this.groupMenus(dataArr,menuList);
				}
			}
		}
		var menuCfgAlls = {};
		var menuHtmlArr = [];
		var groupTitleCount = meteaCfgArr.length;
		var menuDivHeight = 0;
		for(var i=0,count=meteaCfgArr.length; i<count; i++){
			var cfg = meteaCfgArr[i];
			var menuCfgs = cfg.menuCfgs
			var htmArr = cfg.htmArr;
			Ext.apply(menuCfgAlls,menuCfgs);
			menuHtmlArr[i] = htmArr.join(" ");
			var totalCount = cfg.totalCount;
			var result = parseInt(totalCount/4);
			if(result*4 < totalCount) result++;
			menuDivHeight += result*95;
		}
		var oaAccordionMenusDiv = Ext.get(this.idMgr.oaAccordionMenusDivId);
		if(!oaAccordionMenusDiv) return;
		var height = groupTitleCount*30;
		height += menuDivHeight;
		oaAccordionMenusDiv.setHeight(height);
		oaAccordionMenusDiv.update(menuHtmlArr.join(" "),true,function(){
			_this.showByAccordionMenu(Ext.get(_this.selectAccordionItemId));
			_this.bindMenuListeners(menuCfgAlls);
		});
	},
	/**
	 * 为菜单绑定事件
	 * @param {} menuCfgAlls
	 */
	bindMenuListeners : function(menuCfgAlls){
		var _this = this;
		for(var menuId in menuCfgAlls){
			var menuData = menuCfgAlls[menuId];
			var menuEle = Ext.get(menuId);
			if(!menuEle) continue;
			var op = {menuData:menuData};
			menuEle.addClassOnOver("oa_menu_focus");
			menuEle.addClassOnClick("oa_menu_active");
			menuEle.on("click",function(e,t,o){
				_this.hideMenus();
				var menuData = o.menuData;
				var text = menuData.text;
				var id = menuData.id;
				var node = _this.getClonNode(menuData);
				_this.menuActionClick(node);
			},this,op);
		}
	},
	/**
	 * 隐藏弹出菜单
	 */
	hideMenus : function(){
		var oaAccordionMenusDiv = Ext.get(this.idMgr.oaAccordionMenusDivId);
		oaAccordionMenusDiv.hide(true);
	},
	/**
	 * 对有父菜单的数据进行子菜单数据分组
	 * @param {} dataArr
	 * @param {} menuList
	 * @return {}
	 */
	groupMenus : function(dataArr,menuList){
		var meteaCfgArr = [];
		for(var i=0,count=dataArr.length; i<count; i++){
			var pdata = dataArr[i];
			var id = pdata.id;
			var text = pdata.text;
			var subMenuArr = this.getMenuArrByPid(id,menuList);
			if(!subMenuArr || subMenuArr.length == 0) continue;
			meteaCfgArr[meteaCfgArr.length] = this.getMenuGroupHtml(text,subMenuArr);
		}
		return meteaCfgArr;
	},
	/**
	 * 根据菜单父ID获取其子菜单数据
	 * @param {} pid
	 * @param {} menuList
	 * @return {}
	 */
	getMenuArrByPid : function(pid,menuList){
		var menuArr = [];
		for(var i=0,count=menuList.length; i<count; i++){
			var data = menuList[i];
			var menuPid = data.pid;
			if(menuPid != pid) continue;
			menuArr[menuArr.length] = data;
		}
		return menuArr;
	},
	/**
	 * 获取菜单组
	 * @param {} groupTitle
	 * @param {} menuGroup
	 */
	getMenuGroupHtml : function(groupTitle,menuGroup){
		var menuCfgs = {};
		var count = menuGroup.length;
		var htmlArr = [	'<div class="oa_work_group">',
            	'<span class="oa_work_group_bk">',
            	'<span >'+groupTitle+' ('+count+'个)</span>',
            	'</span>',
                '<ul class="oa_menu_list">'];
                //id,text,pid,jsArray,icon,leaf,type,accordionId,loadType,tabId,cached,params,modDatas
        for(var i=0; i<count; i++){
        	var data = menuGroup[i];
        	var id = data.id;
        	var text = data.text;
        	var icon = data.biconCls || 'images/flat_icons/XWindows Dock.png';
        	var menuEleId = this.sysMenuItemId_prefix + id;
        	var menuHtml = '<li id="'+menuEleId+'"><img src="'+icon+'"/><span>'+text+'</span></li>';
        	htmlArr[htmlArr.length] = menuHtml;
        	menuCfgs[menuEleId] = data;
        }
        htmlArr[htmlArr.length] = '<span class="clear"></span></ul> </div>';
        return {menuCfgs:menuCfgs,htmArr : htmlArr,totalCount:menuGroup.length};  
	},
	/**
	 * 根据卡片项显示菜单位置
	 * @param {} ele	卡片元素
	 */
	showByAccordionMenu : function(ele){
		var right = ele.getRight();
		var bottom = ele.getBottom();
		var top = ele.getTop();
		var menusEle = Ext.get(this.idMgr.oaAccordionMenusDivId);
		menusEle.show();
		var menusHeight = menusEle.getHeight();
		var menusTop = 0;
		if(bottom-menusHeight > 0){
			menusTop = bottom-menusHeight;
		}
		menusEle.setLeft(right);
		menusEle.setTop(menusTop);
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
			if(tab.hidden) this.showTabItem(tab);
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
		var sysid = this.currSystemData.id;
		if(!this.params){
			this.params = {sysid:sysid};
		}
		var model_params = {};
		Ext.apply(model_params,this.params);
		if(this.tempParams){
			Ext.apply(model_params,this.tempParams);
			this.tempParams = null;
		}
		model_params["tabId"] = tabId;
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
	 * 根据自定义 tabId 激活相应的 Tab 
	 * @param {} isByMenuId 是否是能过菜单ID显示面板对象
	 * @param {} custTabId	自定义 tabId 
	 * @param {} params 所传参数
	 * @param String url 如果不从菜单表中取数据，可通过此URL自定义加载指定的JS 界面文件
	 * @param String tabTitle Tab 标题
	 */
	activeByCustTabId : function(isByMenuId,custTabId,params,url,tabTitle){
		//根据 tabId 得到 tab组件
		var tabId = this.tabId_Prefix+custTabId;
		var tab =  this.centerPanel.getComponent(tabId);
		if(tab){
			var uiActiveType = null;
			var isnewInstance = null;
			if(params){
				uiActiveType = params.uiActiveType;
				isnewInstance = params.isnewInstance;
				if(isnewInstance) uiActiveType = OA_UIActiveType.newInstance;
			}
			if(uiActiveType){
				switch(uiActiveType){
					case OA_UIActiveType.activeInstance: {/*当 Tab 对象存在时，激活 Tab 对象。此时，如果此 Tab 有绑定 notify 方法。则调用 notify 方法 */
						var tabParams = tab.params
						if(!tabParams) tabParams = {};
						Ext.apply(tabParams,params);
						tab.params = tabParams;
						if(tab.hidden) this.showTabItem(tab);
						this.centerPanel.setActiveTab(tab);
						if(tab["notify"]) tab.notify(tab);
						return;
					//	break;
					}case OA_UIActiveType.newInstance : { /*当 Tab 对象存在时,先销毁此对象。然后，再重新创建该 Tab 对象*/
						tab.destroy();
						this.centerPanel.remove(tab);
						tab = null;
						break;
					}case OA_UIActiveType.tipInstance: { /*当 Tab 对象存在时,给出此UI存在的消息提示*/
						var title = tab["title"];
						ExtUtil.info({msg:"【"+title+"】界面已打开，要重新打开请先关闭原界面!"});
						return;
						//break;
					}
				}
			}
		}
	
		//如果该 tab 项存在就只需激活即可
//		if(tab){
//			var tabParams = tab.params
//			if(!tabParams) tabParams = {};
//			Ext.apply(tabParams,params);
//			tab.params = tabParams;
//			this.centerPanel.setActiveTab(tab);
//			if(tab["notify"]) tab.notify(tab);
//			return;
//		}else{
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
//		}
		
		var _this = this;
		var _pars = {};
		if(isByMenuId){
			_pars = {menuId : custTabId};
		}else{
			_pars = {tabId : custTabId};
		}
		
		 EventManager.get('./sysMenu_single.action',{params:_pars,sfn:function(json_data){
		 	if(!json_data) return;
			if(params){
				if(!_this.tempParams) _this.tempParams = {};
				Ext.apply(_this.tempParams, params);
			}
		 	var node = _this.getClonNode(json_data);
		 	_this.menuActionClick(node,null);
		 }});
	},
	/**
	 * 获取一个伪造的 Node 节点
	 * @param {} data
	 * @return {}
	 */
	getClonNode : function(data){
		return {id:data.id,text:data.text,leaf:data.leaf,attributes:data};
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
	}
})

/**
 * 用户事件管理
 * @type 
 */
var UserEventMgr = {
	/**
	   * 
     *  退出系统
     */
    logout : function(){
    	ExtUtil.confirm({title:'提示',msg:'确定退出系统?',fn:function(){
		 	 if(arguments && arguments[0] != 'yes') return;
		 	 EventManager.exist();
		}});
    }
}
/*-----------页面加载完后,实例化 app 对象--------*/
var txr_cmw_oaApp = null;
Ext.onReady(function(){
	txr_cmw_oaApp = new ExtExample.app();
	txr_cmw_oaApp.init();
	var logoutEle = Ext.get(txr_cmw_oaApp.idMgr.oa_user_functin_logoutId);
	logoutEle.on('click',function(e,t,o){
		UserEventMgr.logout();
	});
});