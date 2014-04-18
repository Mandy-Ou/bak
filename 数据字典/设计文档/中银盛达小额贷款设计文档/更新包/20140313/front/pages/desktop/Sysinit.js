/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */

// Sample desktop configuration
MyDesktop = new Ext.app.App({
	init :function(){
		Ext.QuickTips.init();
	},
	winArrs : [],
	getModules : function(){
		//---> 可参照下面绑定固定的系统 <---//
		this.winArrs[this.winArrs.length] = new MyDesktop.AccordionWindow();
		return this.winArrs;
	},

    // config for the start menu
	/**
	 *
	 * @return {}
	 */
    getStartConfig : function(){
        return {
            title:  Msg_AppCaption.menu_user+CURENT_USER,
            iconCls: 'user-active',
            toolItems: [
            /*	{
                text:Msg_AppCaption.menu_sysset,
                iconCls:'settings',
                scope:this
            },'-',*/
            	{
                text:'切换用户',
                iconCls:'settings',
                scope:this,
                handler  : this.changelog
            },'-',{
                text:Msg_AppCaption.menu_logout,
                iconCls:'logout',
                scope:this,
                handler : this.logout
            }
            /*,{
            	text:'切换用户',
                iconCls:'settings',
                scope:this,
                handler  : this.changelog
            }*/]
        };
    },
    /**
     * 切换用户
     */
    changelog:function(){
    	ExtUtil.confirm({title:'提示',msg:'确定切换用户?',fn:function(){
		 	 if(arguments && arguments[0] != 'yes') return;
		 	 var _this = this;
		 	 var parentCfg = {};
		 	 Cmw.importPackage('pages/desktop/js/ChangelogUserEdit',function(module) {
			 	_this.ChangelogUserEdit = module.WinEdit;
				_this.ChangelogUserEdit.show(parentCfg);
	  		});
		 	
		}});
    },
    /**
     *  退出系统
     */
    logout : function(){
    	ExtUtil.confirm({title:'提示',msg:'确定退出系统?',fn:function(){
		 	 if(arguments && arguments[0] != 'yes') return;
		 	 EventManager.exist();
		}});
    },
    /**
     * 加载用户桌面系统图标数据
     * @param {} callback
     */
    loadDatas : function(callback){
    	var self = this;
    	var url = './sysSystem_desklist.action';
    	var cfg = {sfn:function(json_data){
    		self.createApps(json_data);
    		callback();
    		EventManager.get('./sysUser_editPassWord.action',{params:{userId : CURRENT_USERID},sfn:function(json_data){
    			if(json_data.passWord==111){
    				 var _this = this;
				 	 var parentCfg = {params : json_data};
				 	 Cmw.importPackage('pages/desktop/js/EditPassWord',function(module) {
					 	_this.ChangelogUserEdit = module.WinEdit;
						_this.ChangelogUserEdit.show(parentCfg);
			  		});
    			}
    		}});
    	}};
		EventManager.get(url,cfg);
    },
    /**
     * 加载 系统到桌面
     * @param {} json_data
     */
    createApps : function(json_data){
    	var totalSize = json_data.totalSize;
    	if(!totalSize) return;
    	var htmlArr = ['<dl class="x-shortcuts" style="float:left;">'];
    	var list = json_data.list;
    	for(var i=totalSize-1; i>=0; i--){
    		var data = list[i];
    		var shorctArr = this.getShortcut(data);
    		htmlArr[htmlArr.length] = shorctArr.join(" ");
    		if((i+1)%8 == 0){
    			htmlArr[htmlArr.length] = '</dl><dl class="x-shortcuts">';
    		}
    	}
    	htmlArr[htmlArr.length] = '</dl>';
    	Ext.getDom("x-appsys-shortcuts").innerHTML = htmlArr.join(" ");
    },
    getShortcut : function(data){
    	var id = data.id;
    	var name = data.name;
    	var url = data.url;
		var icon = data.icon;
		var style = "";
    	if(icon){
    		style = 'style="background-image: url('+icon+');filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src=\''+icon+'\', sizingMethod=\'scale\');"';
    	}
    	var eleId = id+"-win";
    	var shortcutArr = [
    	 '<dt id="'+eleId+'-shortcut">',
         '   <a href="#"><img src="./pages/desktop/images/s.gif" '+style+' id="'+eleId+'-img"/>',
         '   	<div>'+name+'</div>' +
         '	 </a>',
         ' </dt>'
    	];
    	var image = Ext.get(eleId+'-img');
    	 new Ext.dd.DD(image, 'pic'); 
    	var appWindow = new MyDesktop.AppSystem({id:eleId,text:name,sysdata : data});
//    	if(name == '系统基础平台'){
//			this.winArrs[this.winArrs.length] = new MyDesktop.GridWindow();
//    	}
    	this.winArrs[this.winArrs.length] = appWindow;
    	return shortcutArr;
    }
});

/*
 * Example windows
 */
MyDesktop.AppSystem = Ext.extend(Ext.app.Module, {
    id:null,
    text : '同心日业务系统',
    sysdata : null,
    init : function(){
        this.launcher = {
            text: this.text,
            iconCls:'icon-grid',
            handler : this.createWindow,	/*点击弹出OA系统窗口事件*/
            scope: this
        }
    },
    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow(this.id);
        if(!win){
        	var self = this;
            var callback = function(baseConfig){
            	var sysdata = self.sysdata;
            	var config = {
	                id: self.id,
	                title:self.text+'<span style=\"color:red\">('+Msg_AppCaption.menu_user+CURENT_USER+')</span>',
	                maximized : true,
	                minimizable : true
            	};
            	Ext.applyIf(config,baseConfig);
            	var systype = sysdata.systype;
            	var url = sysdata.url;
            	var winNew = null;
            	if(url && (!systype || systype==0 || systype==1)){
            		config.params ={sysid:sysdata.id};
            		eval("winNew = new "+url+"(config)");
            	}else{
            		url += "?sysid="+sysdata.id;
            		var iframeId="iframe_"+sysdata.id;
            		config.html = '<iframe id="'+iframeId+'"   frameborder="0" marginheight="0" marginwidth="0" frameborder="0" scrolling="auto" width="100%" height="100%"></iframe>';
            		winNew = new Ext.Window(config);
            		var flag = false;
            		winNew.addListener("show",function(win){
            			if(flag) return;
            			Cmw.$(iframeId).src = url;
            			flag = true;
            		},this);
            	}
            	return winNew;
            };
            win = desktop.createWindow(null,callback);
        }
        win.show();
    }
});

MyDesktop.AppWindow = Ext.extend(Ext.ux.cmw.AppTabTreeWin,{
        shim:false,
        animCollapse:false,
        closeAction : 'close',
         width : 800,
   		 height : 600,
       	accordUrl : './sysAccordion_navigate.action?action=0'
});

/**
 * 系统基础平台 CODE
 * @class MyDesktop.SysPlatformWindow
 * @extends Ext.ux.cmw.AppTabTreeWin
 */
MyDesktop.SysPlatformWindow = Ext.extend(Ext.ux.cmw.AppTabTreeWin,{
    shim:false,
    animCollapse:false,
    width : 800,
    height : 600,
    closeAction : 'close',
   	accordUrl : './sysAccordion_list.action?action=0',
     /**
     * 组装控件
     */
    buildCmpts : function(){
    	this.centerPanel = this.createMainTabPanel();
    	this.centerPanel.setHeight(800);
    	//布局面板
    	var layoutPnl = new Ext.Panel({
			autoScroll : true,
			items : [this.centerPanel]
    	});
    	var self = this;
    	this.addListener("show",function(win){
    		var width = win.getWidth();
    		var height = win.getHeight();
    		self.centerPanel.setHeight(height-35);
    	});
    	return layoutPnl;
    },
    /**
     * 获取首页面板对象
     */
    getHomePanel : function(){
		var homePanel = new Ext.Panel({
			title : '首页',
			border : false
		});
		var self = this;
		EventManager.get(this.accordUrl,{params:this.params,sfn:function(json_data){
			if(null == json_data.totalSize || json_data.list.length == 0) return;
			var list = json_data.list;
			for(var i=0; i<json_data.totalSize; i++){
				var data = list[i];
				var accordionId = data.id;
				var name = data.name;
				var iconCls = data.iconCls;
				var url = data.url
				var cfg = {title:name,autoScroll:true,border : false};
				if(iconCls) cfg.iconCls  = iconCls;
				var sysPanel = new Ext.Panel(cfg);
				homePanel.add(sysPanel);
				self.addMenus(self,url,accordionId,sysPanel);
				homePanel.doLayout();
			}
		}});
		return homePanel;
    },
    addMenus : function(self,url,accordionId,sysPanel){
    		EventManager.get(url,{params:{accordionId:accordionId,isenabled:1},sfn:function(json_data){
				if(null == json_data.totalSize || json_data.list.length == 0) return;
				var list = json_data.list;
				for(var i=list.length-1; i>=0; i--){
					var data = list[i];
					var src = data.biconCls;
					var text = data.name;
					if(!src){
						src = data.iconCls;
						src = src || './pages/desktop/images/grid48x48.png';
					}
					var node = {id:data.menuId,text:text,leaf:true,attributes:{jsArray:data.jsArray}};
					var boxHtml = "<div class='system_box'><img src='./"+src+"'/><br/><span>"+text+"</span></div>";
					var sysBox = new  Ext.BoxComponent({
						html : boxHtml,
						node : node,
						style : {
							float : 'left',
							padding : '5px',
							marginBottom : '5px'
						},
						listeners:{
					      'render': function(cmpt){
					      		var ele = cmpt.el;
					      		ele.on({
					      			'click' : {
					      				fn : function(){
					      					if(sysPanel.selEle){
					      						sysPanel.selEle.removeClass('selected_sysbox');
					      					}
					      					ele.addClass('selected_sysbox');
					      					sysPanel.selEle = ele;
					      					self.menuActionClick(cmpt.node,null);
					      				}
					      			}
					      		});
					      }
					    }
					});
					sysPanel.add(sysBox);
					sysPanel.doLayout();
				};
    		}
    		});
    }
});



MyDesktop.AccordionWindow = Ext.extend(Ext.app.Module, {
    id:'acc-win',
    init : function(){
        this.launcher = {
            text: 'Accordion Window',
            iconCls:'accordion',
            handler : this.createWindow,
            scope: this
        }
    },

    createWindow : function(){
        var desktop = this.app.getDesktop();
        var win = desktop.getWindow('acc-win');
        if(!win){
            win = desktop.createWindow({
                id: 'acc-win',
                title: 'Accordion Window',
                width:250,
                height:400,
                iconCls: 'accordion',
                shim:false,
                animCollapse:false,
                constrainHeader:true,

                tbar:[{
                    tooltip:{title:'Rich Tooltips', text:'Let your users know what they can do!'},
                    iconCls:'connect'
                },'-',{
                    tooltip:'Add a new user',
                    iconCls:'user-add'
                },' ',{
                    tooltip:'Remove the selected user',
                    iconCls:'user-delete'
                }],

                layout:'accordion',
                border:false,
                layoutConfig: {
                    animate:false
                },

                items: [
                    new Ext.tree.TreePanel({
                        id:'im-tree',
                        title: 'Online Users',
                        loader: new Ext.tree.TreeLoader(),
                        rootVisible:false,
                        lines:false,
                        autoScroll:true,
                        tools:[{
                            id:'refresh',
                            on:{
                                click: function(){
                                    var tree = Ext.getCmp('im-tree');
                                    tree.body.mask('Loading', 'x-mask-loading');
                                    tree.root.reload();
                                    tree.root.collapse(true, false);
                                    setTimeout(function(){ // mimic a server call
                                        tree.body.unmask();
                                        tree.root.expand(true, true);
                                    }, 1000);
                                }
                            }
                        }],
                        root: new Ext.tree.AsyncTreeNode({
                            text:'Online',
                            children:[{
                                text:'Friends',
                                expanded:true,
                                children:[{
                                    text:'Jack',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Brian',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Jon',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Tim',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Nige',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Fred',
                                    iconCls:'user',
                                    leaf:true
                                },{
                                    text:'Bob',
                                    iconCls:'user',
                                    leaf:true
                                }]
                            },{
                                text:'Family',
                                expanded:true,
                                children:[{
                                    text:'Kelly',
                                    iconCls:'user-girl',
                                    leaf:true
                                },{
                                    text:'Sara',
                                    iconCls:'user-girl',
                                    leaf:true
                                },{
                                    text:'Zack',
                                    iconCls:'user-kid',
                                    leaf:true
                                },{
                                    text:'John',
                                    iconCls:'user-kid',
                                    leaf:true
                                }]
                            }]
                        })
                    }), {
                        title: 'Settings',
                        html:'<p>Something useful would be in here.</p>',
                        autoScroll:true
                    },{
                        title: 'Even More Stuff',
                        html : '<p>Something useful would be in here.</p>'
                    },{
                        title: 'My Stuff',
                        html : '<p>Something useful would be in here.</p>'
                    }
                ]
            });
        }
        win.show();
    }
});
