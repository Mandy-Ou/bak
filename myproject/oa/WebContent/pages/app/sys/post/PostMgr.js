/*九洲科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.sys");
/*职位管理*/ 
skythink.cmw.sys.PostMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.PostMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGFormView({
			tab : tab,
			params : params,
			gfCfg:{formAlign:'right',wOrh:280},//表单在右边,宽度为250
			hasTopSys : false,//系统顶部
			getToolBar : this.getToolBar,//表格上面的按钮工具栏
			getAppGrid : this.getAppGrid,//表格
			getAppForm : this.getAppForm,//表单
			notify : this.notify,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	notify : function(data){
		EventManager.query(this.appgrid);
		FormUtil.disabledFrm(this.appform);
		EventManager.frm_reset(this.appform);
	},
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
			type : 'label',
			text : '职位名称'
		},{
			type : 'search',
			name : 'name',
			onTrigger2Click:function(){
				var params = toolBar.getValues();
				var name = params.name;
				EventManager.query(self.appgrid,{name : name});
			}
		},{
			token : "查询",
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			key : Btn_Cfgs.QUERY_FASTKEY,
			listeners : {"afterrender":function(){
				//FKeyMgr.setkey(this);
			}},
			handler : function(){
				var params = toolBar.getValues();
				var name = params.name;
				EventManager.query(self.appgrid,{name : name});
			}
		},{
			token : "重置",
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			key : Btn_Cfgs.RESET_FASTKEY,
			listeners : {"afterrender":function(){
				//FKeyMgr.setkey(this);
			}},
			handler : function(){
				toolBar.resets();
			}
		},{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			key : Btn_Cfgs.INSERT_FASTKEY,
			listeners : {"afterrender":function(){
				//FKeyMgr.setkey(this);
			}},
			handler : function(){
				FormUtil.enableFrm(self.appform);
				EventManager.add("./sysPost_add.action",self.appform);
			}
		},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			key : Btn_Cfgs.MODIFY_FASTKEY,
			listeners : {"afterrender":function(){
				//FKeyMgr.setkey(this);
			}},
			handler : function(){
				var id = self.appgrid.getSelId();
				if(!id) return;
				FormUtil.enableFrm(self.appform);
				EventManager.add("./sysPost_get.action?id="+id,self.appform,{sfn:function(formdate){
					self.appform.setFieldValues(formdate);
				}});
			}
		},{type:"sp"},{
			token : "启用",
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			key : Btn_Cfgs.ENABLED_FASTKEY,
			listeners : {"afterrender":function(){
				//FKeyMgr.setkey(this);
			}},
			handler : function(){
				EventManager.enabledData('./sysPost_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用",
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			key : Btn_Cfgs.DISABLED_FASTKEY,
			listeners : {"afterrender":function(){
				//FKeyMgr.setkey(this);
			}},
			handler : function(){
				EventManager.disabledData('./sysPost_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			key : Btn_Cfgs.DELETE_FASTKEY,
			listeners : {"afterrender":function(){
				//FKeyMgr.setkey(this);
			}},
			handler : function(){
				EventManager.deleteData('./sysPost_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取Grid 对象
	 */
	getAppGrid : function(){
	  var structure = [
			{header : '可用标识',name : 'isenabled',width:60,renderer :Render_dataSource.isenabledRender},
			{header : '职位编号',name : 'code',width:115},
			{header : '职位名称',name : 'name',width:100},
			{header : '拼音助记码',name : 'mnemonic',width:75},
			{header : '主要职责',name : 'mtask',width:150},
			{header : '创建人',name : 'creator',width:75},
			{header : '创建日期',name : 'createTime',width:75},
			{header : '备注',id:'remark',name : 'remark',width:300}
			];
		var _appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			height:500,
			isLoad : true,
			url : './sysPost_list.action',
			keyField : 'id'
		});
		return _appgrid;
	},
	refresh:function(optionType,data){
		EventManager.query(this.appgrid);
	},
	/**
	 *  获取 Form 表单对象
	 */
	
	getAppForm : function(){
	//卡片编码,卡片名称,图标样式,加载类型,数据URL,排列顺序
	//code,name,iconCls,type,url,orderNo
		var self = this;
		var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
		var id = FormUtil.getHidField({name:'id'});
		var code = FormUtil.getReadTxtField({fieldLabel : '职位编号',name:'code',width : 150});
		var name = FormUtil.getTxtField({fieldLabel : '职位名称',name:'name',allowBlank : false,width : 150,maxLength:50});
		var mnemonic = FormUtil.getTxtField({fieldLabel : '拼音助记码',name:'mnemonic',allowBlank : true,width : 150,maxLength:50});
		var mtask  = FormUtil.getTAreaField({fieldLabel : '主要职责',name:'mtask',width : 150,maxLength:150});
		var remark  = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width : 150,maxLength:150});
		
		var appform = new Ext.ux.form.AppForm({
			title : '卡片菜单信息编辑',
			url : Cmw.getUrl('./sysPost_save.action'),
			items : [hid_isenabled,id,code,name,mnemonic,mtask,remark],
			width:200,
			buttons :[{text:'保存',handler : function(){
				self.appgrid.params = self.toolBar.getValues();
				 EventManager.frm_save(appform,{sfn:function(data){
					  appform.reset();
					 EventManager.query(self.appgrid,{});
					 FormUtil.disabledFrm(appform);
				 }});
			}},{text:'重置',handler : function(){
				 EventManager.frm_reset(appform);
			}}]
		});
		
		//禁用表单元素
		FormUtil.disabledFrm(appform);
		return appform;
	},
	globalMgr : {}
});

