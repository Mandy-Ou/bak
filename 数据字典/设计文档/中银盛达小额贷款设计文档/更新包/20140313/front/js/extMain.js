/**
 * ExtJS2.2 示例展示 页面布局JS文件
 */

Ext.BLANK_IMAGE_URL = '../images/public/s.gif';
Ext.QuickTips.init();	//加载快速提示框
/*定义命名空间*/
Ext.namespace('ExtExample');
/*应用程序主类*/
ExtExample.app = function(){};
var opWinID = [];
/*通过继承为 app 类获得事件*/
Ext.extend(ExtExample.app,Ext.util.Observable,{
	/* Header 头部*/
	header : new Ext.BoxComponent({
				region : 'north',
				el : 'north',
				height : '30',
				margins : '5'
			}),
	/* Footer 底部*/
	footer: new Ext.BoxComponent({
		region:'south',
		el:'south',
		height:'25'
	}),
	/*实例菜单树形*/
	menuTree : new Ext.tree.TreePanel({
		title : '当前用户：<b>'+CURENT_USER+'</b>&nbsp;&nbsp;<img id="loginout" alt="退出系统" src="'+BASE_PATH+'/images/icons/fam/key_delete.png"/>',
		region : 'west',
		id : 'extExample-tree',
		autoScroll : true,
		enableDD : true,	//是否支持拖拽
		containerScroll : true,	//是否支持滚动条
		split : true,	//是否支持分割
		width : 200,	//Panel 宽度
		minSize : 175, //可拖动最小宽度
		maxSize : 300,	//可拖动最大宽度
		collapseMode : 'mini', //在分割线处出现按钮
		collapsible : true,
		margins : '0 0 5 5',
			loader : new Ext.tree.TreeLoader({
			dataUrl : './doMenuGetUserMenus.action'
		}),
//		loader : new Ext.tree.TreeLoader({
//			dataUrl : 'extExampleTree.json'
//		}),
		//标题栏上的刷新按钮
		tools : [{
			id : 'refresh',
			handler : function(){
				var tree = Ext.getCmp('extExample-tree');
				tree.root.reload();
			}
		}]
	}),
	/*树形菜单根节点*/
	menuRoot : new Ext.tree.AsyncTreeNode({
		id : 'source',
		text : '系统菜单',
		draggable : false
	}),
	/*页面中间主要显示区域*/
	main : new Ext.TabPanel({
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
		this.menuTree.setRootNode(this.menuRoot);
		this.menuRoot.expand();
		//给树形菜单添加事件
		this.menuTree.on('click',this.menuActionClick,this);
		//给main的tab页面添加tabchange事件
		this.main.on('tabchange', this.changeTab, this);
		var myView = new Ext.Viewport({
			layout : 'border',
			border : false,
			items : [this.header,this.footer,this.main,this.menuTree]
		});
		/*新建一个 mask 加载对象*/
		this.loadMask = new Ext.LoadMask(this.main.body,{msg : '页面加载中...'});
		Ext.get("loginout").dom.onclick=function(){
			Ext.Msg.confirm("退出系统","确认退出当前系统吗？",function(){
					Ext.Ajax.request({
						url : './loginout.action',
						success : function(response){
							var obj = Ext.decode(response.responseText);
							if(obj.success==true){
								window.location.href = BASE_PATH+"/user/login.jsp";
							}
						}
					});
			})
		}
	},
	/*属性菜单的点击事件*/
	menuActionClick : function(node,e){
		if(!node.leaf){	/*如果是父节点则返回 false,不做任何处理*/
			return false;
		}
		//根据 noe 相关属性得到 tab 对象
		var tabId = 'tab-'+node.id;
		var tabTitle = node.text;
		var tablink = node.attributes.link;
		var tabJsArray = node.attributes.jsArray;
		var self = this;
		//根据 tabId 得到 tab组件
		var tab = this.main.getComponent(tabId);
		if(!tab){
            tab = this.main.add(new Ext.Panel({id:tabId,title:tabTitle,autoScroll:true,layout: 'fit',border:false,closable:true}));  	
			tab.on('tabchange', this.changeTab, this);
			this.main.setActiveTab(tab);

			var loadmask = this.loadMask;

			var model;//加载模块js预置变量名

			loadmask.show();
			//panel组建开始异步加载url的html
			tab.load({
				url: tablink,//加载的url
				//加载html成功后的回调函数
				callback:function(a,b,c){
					var jsStr = "";//创建一个空字符串，用来装载接受的js文件内容
					var num = tabJsArray.length;
					for(var i=0;i<num;i++){
						//由于异步加载的html不能以<script src=xxx.js>的方式加载javascript所以在此再调用一个ajax异步加载模块的js文件
						 var tabjs = tabJsArray[i].js;//取得数组中的js文件
						 var currentIndex = i;//获取当前js文件的索引
						 Ext.Ajax.request({   
							method:'POST',//为了不丢失js文件内容，所以要选择post的方式加载js文件
							url: tabJsArray[i].js,   
							scope: this,   
							success: function(response){
								
								jsStr+=response.responseText;//把每次加载的内容都存入jsStr中
								if(currentIndex==num-1){//如果当前索引号为最后一个时开始创建应用的实例
									//获取模块类  
									this[node.id] = eval(jsStr);  
									//实例化模块类
									model = new this[node.id](node.id);
									loadmask.hide(); 
								}		
								//TabPannel 改变时，重新初始化
								tab.on('resize',function(obj, adjWidth, adjHeight, rawWidth,rawHeight){
									if(model["resize"])model.resize();
								});
								
								//之所以要重写tabpanel的destroy函数，是因为在要执行每个实例类的自定义的destroy函数。
								//原因在于，用IE在有些情况下不能完全释放实例。
								//比如“树的高级应用（一）”实例中的window对象，如果该对象不执行destroy函数时再此打开时会报错。
								/*--------- 我在这里绑定了事件好像没有调用 ---------*/
								
								tab.on('destroy',function(){
									model.destroy();
								});
								
							},
							failure:function(response){
								Ext.Msg.show({
									title: "错误信息",
									msg:"加载页面核心文件时发生错误！",
									buttons:Ext.MessageBox.OK,
									icon: Ext.MessageBox.ERROR
								});
								loadmask.hide(); 
							}
						});
					}
					if(b==false){
						Ext.Msg.show({
							title: "错误信息",
							msg:"加载页面超时或是页面连接不正确！",
							buttons:Ext.MessageBox.OK,
							icon: Ext.MessageBox.ERROR
						});
						loadmask.hide(); 
					}
				},
				discardUrl: false,
				nocache: true,
				text: "",
				timeout: 3000,//超时时间30ms
				scripts: true
			});
        }else{
            this.main.setActiveTab(tab);
        }
	},
	/*Tab change 的实现*/
	changeTab : function(tab,newtab){
		//如果存在相应树节点，就选中,否则就清空选择状态
        var nodeId = newtab.id.replace('tab-','');
        var node = this.menuTree.getNodeById(nodeId);
        if(node){
            this.menuTree.getSelectionModel().select(node);
        }else{ 
            this.menuTree.getSelectionModel().clearSelections();
        }  
	}
})
/*-----------页面加载完后,实例化 app 对象--------*/
Ext.onReady(function(){
	var extApp = new ExtExample.app();
	extApp.init();
});