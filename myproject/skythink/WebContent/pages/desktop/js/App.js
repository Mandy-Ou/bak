/*!
 * Ext JS Library 3.2.0
 * Copyright(c) 2006-2010 Ext JS, Inc.
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.app.App = function(cfg){
    Ext.apply(this, cfg);
    this.addEvents({
        'ready' : true,
        'beforeunload' : true
    });

    Ext.onReady(this.initApp, this);
};

Ext.extend(Ext.app.App, Ext.util.Observable, {
    isReady: false,
    startMenu: null,
    modules: null,

    getStartConfig : function(){

    },
	loadDatas : function(callback){},
    initApp : function(){
    	var self = this;
    	this.loadDatas(function(){
    		self.startConfig = self.startConfig || self.getStartConfig();
	        self.desktop = new Ext.Desktop(self);
			self.launcher = self.desktop.taskbar.startMenu;
			
			self.modules = self.getModules();
	        if(self.modules){
	            self.initModules(self.modules);
	        }
	
	        self.init();
	
	        Ext.EventManager.on(window, 'beforeunload', self.onUnload, self);
			self.fireEvent('ready', self);
	        self.isReady = true;	
    	});
//    	this.startConfig = this.startConfig || this.getStartConfig();
//
//        this.desktop = new Ext.Desktop(this);
//
//		this.launcher = this.desktop.taskbar.startMenu;
//
//		this.modules = this.getModules();
//        if(this.modules){
//            this.initModules(this.modules);
//        }
//
//        this.init();
//
//        Ext.EventManager.on(window, 'beforeunload', this.onUnload, this);
//		this.fireEvent('ready', this);
//        this.isReady = true;
    },

    getModules : Ext.emptyFn,
    init : Ext.emptyFn,

    initModules : function(ms){
		for(var i = 0, len = ms.length; i < len; i++){
            var m = ms[i];
            this.launcher.add(m.launcher);
            m.app = this;
        }
    },

    getModule : function(name){
    	var ms = this.modules;
    	for(var i = 0, len = ms.length; i < len; i++){
    		if(ms[i].id == name || ms[i].appType == name){
    			return ms[i];
			}
        }
        return '';
    },

    onReady : function(fn, scope){
        if(!this.isReady){
            this.on('ready', fn, scope);
        }else{
            fn.call(scope, this);
        }
    },

    getDesktop : function(){
        return this.desktop;
    },

    onUnload : function(e){
        if(this.fireEvent('beforeunload', this) === false){
            e.stopEvent();
        }
    }
});