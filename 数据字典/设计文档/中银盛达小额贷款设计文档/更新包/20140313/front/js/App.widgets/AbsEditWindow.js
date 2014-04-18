/**
 * 封装 Ext Window 支持传入不同类型
 * 返回一个带有上传功能的 Windows 控件
 */
Ext.namespace("Ext.ux.window");

Ext.ux.window.AbsEditWindow = Ext.extend(Ext.Window,{
	title : '数据编辑',
	labelWidth:80,
	width:400,
	closeAction : 'hide',
	modal : true,		// true 后面的东西不能点击 
	listeners : EventManager.attachWinZindexListeners(),
	optionType : 0, /* 0:新增,1:修改*/
	refresh : null,	/* 保存成功后调用刷新方法 */
	barItems : [],/* 工具栏上的按钮项 */
	apptbar : null,
	appFrm : null,
	prevNextBtnHiden : false,
	eventMgr : {},/*-- 管理 上一条、下一条、保存、重置、关闭等事件 --*/
	getUrls : null,/*-- 用于提供 新增、修收、上一条、下一条 URL 的函数 --*/
	isNotSetVs :  null,//是否为组合文本框赋值
	isNonLoad : true, /*--用于显示只有一个关闭按钮，并且不需要加载数据，类似于mywin 效果解决，myWin 销毁窗口问题--*/
	initComponent : function(){
	 	Ext.ux.window.AbsEditWindow.superclass.initComponent.call(this);
	 	this.apptbar = this.createBbar();
	 	this.add(this.apptbar);
	 	this.addComponents();
	},
	// private
	initEvents : function() {
		var self = this;
		 Ext.ux.window.AbsEditWindow.superclass.initEvents.call(this);
		 this.addListener("show",function(win){
		 	//----> addUrlCfg : 新增 URL , editUrlCfg : 修改 URL//
		 	var urlKey = null;
		 //	alert("self.optionType="+self.optionType);
		 	if(self.optionType == OPTION_TYPE.ADD){
		 		urlKey = URLCFG_KEYS.ADDURLCFG;	//新增 URL KEY 参见 constant.js
		 		self.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT +","+ Btn_Cfgs.NEXT_BTN_TXT+","+ Btn_Cfgs.RESET_BTN_TXT);
//		 		self.apptbar.showButtons(Btn_Cfgs.RESET_BTN_TXT);
		 	}else{
		 		urlKey = URLCFG_KEYS.GETURLCFG; //修改取某条数据 URL KEY 参见 constant.js
		 		if(!self.prevNextBtnHiden){
			 		self.apptbar.showButtons(Btn_Cfgs.PREVIOUS_BTN_TXT +","+ Btn_Cfgs.NEXT_BTN_TXT);
		 		}else{
		 			self.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT +","+ Btn_Cfgs.NEXT_BTN_TXT+","+Btn_Cfgs.RESET_BTN_TXT);
		 		}
		 		self.apptbar.hideButtons(Btn_Cfgs.RESET_BTN_TXT);
		 		if(!self.isNonLoad){
		 			self.apptbar.hideButtons(Btn_Cfgs.PREVIOUS_BTN_TXT +","+ Btn_Cfgs.NEXT_BTN_TXT+","+Btn_Cfgs.SAVE_BTN_TXT);
		 		}
		 	}
		 	if(self.isNonLoad){
		 		self.loadData(urlKey);
		 	}
		 	//self.loadData(urlKey);
		 },this)
	},
	/**
	 * 添加组件
	 */
	addComponents : function(){
		if(this.appFrm) this.add(this.appFrm);
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
	//	alert("this.optionType="+this.optionType);
//		if(this.optionType != 0){	//修改 
			this.barItems = [{
					text : Btn_Cfgs.PREVIOUS_BTN_TXT,  /*-- 上一条 --*/
					iconCls:Btn_Cfgs.PREVIOUS_CLS,
					tooltip:Btn_Cfgs.PREVIOUS_TIP_BTN_TXT,
					
					handler : function(){
					   if(self.eventMgr.preData){
					   	self.eventMgr.preData(self);
					   }else{
					   	 self.loadData(URLCFG_KEYS.PREURLCFG);
					   }
					}
				},{
					text : Btn_Cfgs.NEXT_BTN_TXT,  /*-- 下一条 --*/
					iconCls:Btn_Cfgs.NEXT_CLS,
					tooltip:Btn_Cfgs.NEXT_TIP_BTN_TXT,
					handler : function(){
					  if(self.eventMgr.nextData){
					   	self.eventMgr.nextData(self);
					   }else{
					   	 self.loadData(URLCFG_KEYS.NEXTURLCFG);
					   }
					}
				},{type:"sp"}];
//		}
		this.barItems[this.barItems.length] = {
				text : Btn_Cfgs.SAVE_BTN_TXT,	/*-- 保存 --*/ 
				iconCls:Btn_Cfgs.SAVE_CLS,
				tooltip:Btn_Cfgs.SAVE_TIP_BTN_TXT,
				key : Btn_Cfgs.SAVE_FASTKEY,
				listeners : {"afterrender":function(){
					//FKeyMgr.setkey(this);
				}},
				handler : function(){
					if(self.eventMgr.saveData){
						self.eventMgr.saveData(self);
					}else{
						var cfg = {
							tb:self.apptbar,
							sfn : function(formData){
								self.resetData();
								if(self.refresh) self.refresh(formData);
								self.hide();
							}
						};
						EventManager.frm_save(self.appFrm,cfg);
					}
				}
		};
		
		this.barItems[this.barItems.length] = {
			text : Btn_Cfgs.RESET_BTN_TXT,  /*-- 重置 --*/
			iconCls:Btn_Cfgs.RESET_CLS,
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){ 
				self.resetData();
			}
		};
		
		this.barItems[this.barItems.length] = {
			text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
			iconCls:Btn_Cfgs.CLOSE_CLS,
			tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
			handler : function(){
				if(self.eventMgr.close){
					self.eventMgr.close(self);
				}else{
					var closeAction = self["closeAction"];
					if(closeAction && closeAction == 'close'){
						self.close();
					}else{
						self.hide();
					}
				}
				self.resetData();
			}
		};
		var barItems = this.changeBarItems(this.barItems);
		if(barItems && barItems.length > 0){
			this.barItems = barItems;
		}
		var appBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:this.barItems});
		return appBar;
	},
	/**
	 * 改变 AbsEditWindow 窗口中默认按钮配置
	 * 子类须重写此方法
	 * @param {} barItems 工具栏配置项
	 */
	changeBarItems : function(barItems){
		return null;
	},
	/**
	 * 重置表单
	 */
	resetData : function(){
		if(this.eventMgr.resetData){
			this.eventMgr.resetData(this);
		}else{
			this.appFrm.reset();			
		}
	},
	/**
	 * 加载数据
	 */
	loadData : function(key){
		if(!this.isNonLoad){
			return;
		}
		if(this.eventMgr.setFormValues){	/*-- 如果提供了自定义实现的赋值方法，就优先执行 --*/
			this.eventMgr.setFormValues();
			return;
		}
		var urls = this.getUrls();
	   	if(!urls){
	   	 	 ExtUtil.warn({title:'错误',msg:'子类中没有提供 getUrls()函数的实现!'});
	   	}
	   	var urlCfg = urls[key];
		this.setValues(key,urlCfg.url,urlCfg.cfg);
	},
	/**
	 * 为表单元素赋值	
	 * 例: appForm.setFormValues('./sysMenu_list.action',	// --> 服务器请求数据时的 URL 地址 //
	 *     { params:{id:'1'},	// --> 服务器请求数据时，后台业务需要的参数值 //
	 *       sfn : function(jsonObj){xxcode ...}, // --> 处理向服务器请求数据成功后要执行的函数 "jsonObj" 服务器返回的 json 对象数据 //
	 * 	     ffn : function(errMsg){xxcode...},	// --> 处理向服务器请求数据失败后要执行的函数 "errMsg" 服务器返回的错误信息参数 //
	 * 	     defaultVal : {name : '企业客户管理',parentName : '客户管理'} // --> 为表单要赋的默认值 json 对象 [可选参数] //
	 *     }
	 * );
	 * @param url 要请求数据的 url 
	 * @param cfg 表单附加的参数对象 
	 */
	setValues : function(key,url,cfg){
		var self = this;
		self.apptbar.disable();
		self.appFrm.el.mask('Loading', 'x-mask-loading');
		var action = {};
		var result = (cfg && cfg["params"]);
		if (result) {
			action.params = cfg.params;
		}
		//--> 当服务器成功返回数据后的回调函数，在回调函数中为表单赋值
		action.sfn = function(json_data){
			self.apptbar.enable();
			self.appFrm.el.unmask();
			if(key == URLCFG_KEYS.PREURLCFG){ //点上一条，如果下一条按钮禁用就启用
				var btns = self.apptbar.getSomeButtons(Btn_Cfgs.NEXT_BTN_TXT);
				if(btns && btns[0].disabled) self.apptbar.enableButtons(Btn_Cfgs.NEXT_BTN_TXT);
			}else if(key == URLCFG_KEYS.NEXTURLCFG){//下一条
				var btns = self.apptbar.getSomeButtons(Btn_Cfgs.PREVIOUS_BTN_TXT);
				if(btns && btns[0].disabled) self.apptbar.enableButtons(Btn_Cfgs.PREVIOUS_BTN_TXT);
			}
			if(json_data[CURSOR_MGR.CURSOR_KEY]){	//如果上一条是第一条或下一条是最后一条处理
				var cursorVal = json_data[CURSOR_MGR.CURSOR_KEY];
				if(CURSOR_MGR.CURSOR_FIRST == cursorVal){	//第一条处理
					self.apptbar.disableButtons(Btn_Cfgs.PREVIOUS_BTN_TXT);
				}else if(CURSOR_MGR.CURSOR_LAST == cursorVal){	//最后一条处理
					self.apptbar.disableButtons(Btn_Cfgs.NEXT_BTN_TXT);
				}
			}else{
				
				var xtype = self.appFrm.getXType();
				if(xtype=='appform'){
					var defaultVal = cfg.defaultVal || {};
					Ext.apply(json_data,defaultVal);
					self.appFrm.reset();
					if(self.isNotSetVs){
						FormDiyManager.setFormDiyFieldValues(self.appFrm);// 这里是加载自定义表单的数据
						self.appFrm.setVs(json_data);
					}else{
						self.appFrm.setFieldValues(json_data);
					}
					//if(cfg && cfg.defaultVal) self.appFrm.setFieldValues(cfg.defaultVal);
				}else{
					var frm = self.appFrm.getForm();
					frm.setValues(json_data);
					if(cfg && cfg.defaultVal) frm.setValues(cfg.defaultVal);
				}
				var appFrm = self.appFrm;
				var formDiyCfg = appFrm.formDiyCfg;
				if(!formDiyCfg){
					if(cfg && cfg.sfn) cfg.sfn(json_data);
				}else{
					FormDiyManager.setFormDiyFieldValues(appFrm,function(data){
						Ext.applyIf(json_data,data);
						if(cfg && cfg.sfn) cfg.sfn(json_data);
					});
				}
			}
		};
		//如果配有失败后处理函数，当数据处理失败后。将把错误信息反馈给错误处理函数
		action.ffn = function(msg){ 
			self.apptbar.enable();
			if(cfg && cfg.ffn)cfg.ffn(msg);
		};
		if(url) EventManager.get(url,action);
	}
});

//注册成xtype以便能够延迟加载   
Ext.reg('abseditwin', Ext.ux.window.AbsEditWindow);