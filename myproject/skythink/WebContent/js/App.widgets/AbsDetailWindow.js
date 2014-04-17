/**
 * 封装 Ext Window 支持传入不同类型
 * 返回一个带有上传功能的 Windows 控件
 */
Ext.namespace("Ext.ux.window");

Ext.ux.window.AbsDetailWindow = Ext.extend(Ext.Window,{
	title : '数据详情',
	width:400,
	closeAction : 'hide',
	modal : true,		// true 后面的东西不能点击 
	//listeners : EventManager.attachWinZindexListeners(),
	optionType : OPTION_TYPE.DETAIL, /* 6:详情*/
	refresh : null,	/* 保存成功后调用刷新方法 */
	barItems : [],/* 工具栏上的按钮项 */
	apptbar : null,
	appDetailPanel : null,
	hiddenBtn : false,/* 隐藏 上一条、下一条  btn*/
	getParams : null,/*-- 为详情数据 提供查询的新参数 如： 上一条、下一条 URL 的函数 --*/
	initComponent : function(){
		Ext.ux.window.AbsDetailWindow.superclass.initComponent.call(this);
	 	this.apptbar = this.createBbar();
	 	
	 	this.add(this.apptbar);
	 	if(this.appDetailPanel){
	 		this.appDetailPanel.apptbar = this.apptbar;
	 		if(!this.appDetailPanel["width"] || this.appDetailPanel["width"]<=0)this.appDetailPanel.setWidth(this.width);
	 	} 
	 	this.add(this.appDetailPanel);
	},
	// private
	initEvents : function(){
		var self = this;
		Ext.ux.window.AbsDetailWindow.superclass.initEvents.call(this);
		 this.addListener("beforeshow",function(win){
		 		if(self.hiddenBtn){
		 			self.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT+","+Btn_Cfgs.NEXT_BTN_TXT);
		 		}
		  		var zseed = Ext.WindowMgr.zseed;
		 		Ext.WindowMgr.zseed = 10000;
		 });
		 this.addListener("show",function(win){
		 	self.loadData(null);
		 },this)
	},
	/**
	 * 创建工具栏	
	 * @returns {Ext.ux.toolbar.MyCmwToolbar}
	 */
	createBbar : function(){
		var self = this;
		/* /---> 如果自定义工具栏选项，则采用自定义的配置 <----*/
		if(this.barItems && this.barItems.length > 0){
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:this.barItems});
			return appBar;
		}
		this.barItems = [{
				text : Btn_Cfgs.PREVIOUS_BTN_TXT,  /*-- 上一条 --*/
				iconCls:Btn_Cfgs.PREVIOUS_CLS,
				tooltip:Btn_Cfgs.PREVIOUS_TIP_BTN_TXT,
				handler : function(){
				   	 self.loadData(URLCFG_KEYS.PREURLCFG);
				}
			},{
				text : Btn_Cfgs.NEXT_BTN_TXT,  /*-- 下一条 --*/
				iconCls:Btn_Cfgs.NEXT_CLS,
				tooltip:Btn_Cfgs.NEXT_TIP_BTN_TXT,
				handler : function(){
				   	 self.loadData(URLCFG_KEYS.NEXTURLCFG);
				}
			},
			{type:"sp"},{ 
				text : Btn_Cfgs.PRINT_BTN_TXT,  /*-- 打印--*/
				iconCls:Btn_Cfgs.PRINT_CLS,
				tooltip:Btn_Cfgs.PRINT_TIP_BTN_TXT,
				handler : function(){
					self.print();
				}
			},{
				text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
				iconCls:Btn_Cfgs.CLOSE_CLS,
				tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
				handler : function(){
					self.hide();
				}
			}];
		
		var appBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:this.barItems});
		return appBar;
	},
	print : function(){
		this.appDetailPanel.print();
	},
	/**
	 * 加载数据
	 */
	loadData : function(key){
		var params = this.getParams();
	   	if(!params){
	   	 	 ExtUtil.error({title:'错误',msg:'子类中没有提供 getParams()函数的实现!'});
	   	}
	   	if(!key){
	   		this.appDetailPanel.reload(params);
	   	}else{
	   		if(key == URLCFG_KEYS.PREURLCFG){	//上一条取数据
	   			this.appDetailPanel.prevData(URLCFG_KEYS.PREURLCFG, params);
	   		}else if(key == URLCFG_KEYS.NEXTURLCFG){//下一条取数据
	   			this.appDetailPanel.nextData(URLCFG_KEYS.NEXTURLCFG, params);
	   		}
	   	}
	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('absdetailwin', Ext.ux.window.AbsDetailWindow);  