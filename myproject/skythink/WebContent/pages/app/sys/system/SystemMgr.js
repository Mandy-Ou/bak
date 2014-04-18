/*九洲科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.sys");
/*初始化通值数据模型 实例*/ 
skythink.cmw.sys.SystemMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.SystemMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGFormView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			gfCfg:{formAlign:'right',wOrh:300},
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			getAppForm : this.getAppForm
		});	
		
	},
	
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
			type : 'label',
			text : Btn_Cfgs.SYSTEM_LABEL_TEXT
		},{
			type : 'search',
			name : 'name',
			onTrigger2Click:function(){
				var params = toolBar.getValues();
				EventManager.query(self.appgrid,params);
			}
		},{
			token : "查询",
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			key : Btn_Cfgs.QUERY_FASTKEY,
//			listeners : {"afterrender":function(){
//				FKeyMgr.setkey(this);
//			}},
			handler : function(){
				var params = toolBar.getValues();
				EventManager.query(self.appgrid,params);
			}
		},{
			token : "重置",
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			key : Btn_Cfgs.RESET_FASTKEY,
//			listeners : {"afterrender":function(){
//				FKeyMgr.setkey(this);
//			}},
			handler : function(){
				toolBar.resets();
			}
		},{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			key : Btn_Cfgs.INSERT_FASTKEY,
//			listeners : {"afterrender":function(){
//				FKeyMgr.setkey(this);
//			}},
			handler : function(){
				FormUtil.enableFrm(self.appform);
				EventManager.add("./sysSystem_add.action",self.appform,{sfn:function(formdate){
					self.appform.setFieldValues(formdate);
				}});
			}
		},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			key : Btn_Cfgs.MODIFY_FASTKEY,
//			listeners : {"afterrender":function(){
//				FKeyMgr.setkey(this);
//			}},
			handler : function(){
				var id = self.appgrid.getSelId();
//				if(!id) return;
				FormUtil.enableFrm(self.appform);
				EventManager.add("./sysSystem_get.action?id="+id,self.appform,{sfn:function(formdate){
					self.appform.setFieldValues(formdate);
				}});
			}
		},{type:"sp"},{
			token : "启用",
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			key : Btn_Cfgs.ENABLED_FASTKEY,
//			listeners : {"afterrender":function(){
//				FKeyMgr.setkey(this);
//			}},
			handler : function(){
				EventManager.enabledData('./sysSystem_enabled.action',{type:'grid',cmpt:self.appgrid});
			}
		},{
			token : "禁用",
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			key : Btn_Cfgs.DISABLED_FASTKEY,
//			listeners : {"afterrender":function(){
//				FKeyMgr.setkey(this);
//			}},
			handler : function(){
				EventManager.disabledData('./sysSystem_disabled.action',{type:'grid',cmpt:self.appgrid});
			}
		},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			key : Btn_Cfgs.DELETE_FASTKEY,
			listeners : {"afterrender":function(){
				FKeyMgr.setkey(this);
			}},
			handler : function(){
				EventManager.deleteData('./sysSystem_delete.action',{type:'grid',cmpt:self.appgrid});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取Grid 对象
	 */
	getAppGrid : function(){
		//系统编号,系统名称,更新方式,自动更新时间,系统类型,排列顺序,系统简介
		//code,name,typeup,autotime,systype,orderNo,desc
	  var structure = [
			{header : 'id',name : 'id',hidden : true},
			{header : '可用标识',name : 'isenabled',width:60,renderer :Render_dataSource.isenabledRender},
			{header : '系统编号',name : 'code',width:75},
			{header : '系统名称',name : 'name',width:100},
			{header : '系统地址',name : 'url',width:150},
			{header : '更新方式',name : 'typeup',width:80,renderer : Render_dataSource.typeupRenderer},
			{header : '自动更新时间',name : 'autotime',width:100},
			{header : '系统类型',name : 'systype',renderer : Render_dataSource.systypeRenderer},
			{header : '排列顺序',id:'orderNo',name : 'orderNo',width:60},
			{header : '系统简介',id:'synopsis',name : 'synopsis',width:300}
			];
		var _appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			height:500,
			url : './sysSystem_list.action'
		});
		return _appgrid;
	},
	/**
	 *  获取 Form 表单对象
	 */
	getAppForm : function(){
		var self = this;
		var id = FormUtil.getHidField({name:'id'});
		var isenabled = FormUtil.getHidField({name:'isenabled'});
		var code = FormUtil.getTxtField({fieldLabel : '系统编号',name:'code',width:150});
		var name = FormUtil.getTxtField({fieldLabel : '系统名称',name:'name',allowBlank : false,length:30,width:150,maxLength:30});
		var mnemonic = FormUtil.getReadTxtField({fieldLabel : '拼音助记码',name:'mnemonic',width:150,maxLength:30});
		var icon = FormUtil.getMyImgChooseField({
			    fieldLabel: '系统图标',
			    width : 150,
			    name: 'icon',
			    showIconTypes : [2,3],
			    length:200,
			    maxLength:200
			});
//		var icon = FormUtil.getTxtField({fieldLabel : '系统图标',name:'icon',length:200,width:150,maxLength:200});
		var url = FormUtil.getTxtField({fieldLabel : '系统地址',name:'url',length:200,width:150});
		var typeup = FormUtil.getRadioGroup({fieldLabel : '更新方式',allowBlank : false,columns: 1,name:'typeup',
					items : [{boxLabel : '自动更新', name:'typeup',inputValue:0, checked: true},
					         {boxLabel : '手动更新', name:'typeup',inputValue:1}]});
		var autotime = FormUtil.getIntegerField({fieldLabel : '自动更新时间',name:'autotime',width : 50,tabTip :'如果是自动更新，此处必须输入自动更新的时间。例如：2【则凌晨 2点更新】'});
		var systype = FormUtil.getLCboField({fieldLabel : '系统类型',name:'systype',
			data:[["0","试运行系统"],["1","正式上线系统"],["2","SAS在线系统"],["3","同心日在线试用系统"]],allowBlank : false,width:150});
		var orderNo = FormUtil.getIntegerField({fieldLabel : '排列顺序',name:'orderNo',width : 50});
		var synopsis  = FormUtil.getTAreaField({fieldLabel : '系统简介',name:'synopsis',width:150,height:100,maxLength:255});
		
		var appform =  new Ext.ux.form.AppForm({
			title : '系统信息编辑',
			url : Cmw.getUrl('./sysSystem_save.action'),
			items:[id,isenabled,code,name,mnemonic,icon,url,systype,typeup,autotime,orderNo,synopsis],
			width:250,
			buttons :[{text:'保存',handler : function(){
				self.appgrid.params = self.toolBar.getValues();
				 EventManager.frm_save(appform,{sfn : function(formDatas){
					 self.appgrid.reload(); 
					 appform.reset();
					 FormUtil.disabledFrm(appform);
				 }});
			}},{text:'重置',handler : function(){
				 EventManager.frm_reset(appform,[code]);
			}}]
		});
		//禁用表单元素
		FormUtil.disabledFrm(appform);
		return appform;
	}
});

