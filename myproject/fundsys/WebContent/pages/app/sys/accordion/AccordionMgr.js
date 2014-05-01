/*九洲科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.sys");
/*初始化通值数据模型 实例*/ 
skythink.cmw.sys.AccordionMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.AccordionMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGFormView({
			tab : tab,
			params : params,
			gfCfg:{formAlign:'right',wOrh:280},//表单在右边,宽度为250
			hasTopSys : true,//系统顶部
			getToolBar : this.getToolBar,//表格上面的按钮工具栏
			getAppGrid : this.getAppGrid,//表格
			getAppForm : this.getAppForm,//表单
			notify : this.notify,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	notify : function(data){
		var sysId = data.id;//取到系统图标的id号
		var sysName = data.name;//取到系统图标的Name
		this.globalMgr.sysid=sysId;
		EventManager.query(this.appgrid,{sysid: sysId});
		var txtsysid=this.appform.findFieldByName('sysid');
		var txtsysname=this.appform.findFieldByName('sysname');
		FormUtil.disabledFrm(this.appform);
		EventManager.frm_reset(this.appform);
		txtsysid.setValue(sysId);
		txtsysname.setValue(sysName);
	},
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
			type : 'label',
			text : Btn_Cfgs.ACCORDION_LABEL_TEXT
		},{
			type : 'search',
			name : 'name',
			onTrigger2Click:function(){
				var params = toolBar.getValues();
				var name = params.name;
				var sysid =self.globalMgr.sysid;
				if(name==""){
					EventManager.query(self.appgrid,{sysid: sysid});
				}else{
					EventManager.query(self.appgrid,{name : name});
				}
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
				var sysid =self.globalMgr.sysid;
				if(name==""){
					EventManager.query(self.appgrid,{sysid: sysid});
				}else{
					EventManager.query(self.appgrid,{name : name});
				}			
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
				var sysidVal = self.appform.getValueByName('sysid');
				var sysnameVal = self.appform.getValueByName('sysname');
				EventManager.add("./sysAccordion_add.action",self.appform,{sysid:sysidVal,sysname:sysnameVal},{sfn:function(formdate){
					self.appform.setFieldValues(formdate);
				}});
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
				EventManager.add("./sysAccordion_get.action?id="+id,self.appform,{sfn:function(formdate){
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
				EventManager.enabledData('./sysAccordion_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
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
				EventManager.disabledData('./sysAccordion_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
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
				EventManager.deleteData('./sysAccordion_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
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
			{header : '',name : 'id',hidden : true},
			{header : '',name : 'sysid',hidden : true},
			{header : '可用标识',name : 'isenabled',width:60,renderer :Render_dataSource.isenabledRender},
			{header : '卡片编码',name : 'code',width:75},
			{header : '卡片名称',name : 'name',width:100},
			{header : '图标样式',name : 'iconCls',width:75},
			{header : '加载类型',name : 'type',renderer : function(val){
				switch (val) {
				case "0":
					val = "树";
					break;
				case "1":
					val = "按钮";
					break;
				default:
					val = "暂未设置";
					break;
				}
				return val;
			}},
			{header : '数据URL',id:'url',name : 'url',width:120},
			{header : '排列顺序',id:'orderNo',name : 'orderNo',width:60},
			{header : '备注',id:'remark',name : 'remark',width:300}
			];
		var _appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			height:500,
			isLoad : false,
			url : './sysAccordion_list.action'
		});
		return _appgrid;
	},
	refresh:function(optionType,data){
		var data=this.appgrid.getCmnVals("sysid");
		var sysid= data.sysid;
		EventManager.query(this.appgrid,{sysid:sysid});
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
		var sysid = FormUtil.getHidField({fieldLabel : '系统id',name:'sysid'});
		var sysname = FormUtil.getReadTxtField({fieldLabel : '所属系统',name:'sysname',width : 150});
		var code = FormUtil.getReadTxtField({fieldLabel : '卡片编码',name:'code',width : 150});
		var name = FormUtil.getTxtField({fieldLabel : '卡片名名称',name:'name',allowBlank : false,width : 150,maxLength:50});
		var iconCls = FormUtil.getTxtField({fieldLabel : '图标样式',name:'iconCls',allowBlank : true,width : 150,maxLength:50});
		var type = FormUtil.getRadioGroup({fieldLabel : '加载类型',allowBlank : false,name:'type',
					items : [{boxLabel : '树', name:'type',inputValue:0, checked: true},{boxLabel : '按钮', name:'type',inputValue:1,width : 150}]});
		var url = FormUtil.getTxtField({fieldLabel : '数据URL',name:'url',allowBlank : true,width : 150,maxLength:200});
		var orderNo = FormUtil.getIntegerField({fieldLabel : '排列顺序',name:'orderNo',width : 150});
		var remark  = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width : 150,maxLength:200});
		
		var appform = new Ext.ux.form.AppForm({
			title : '卡片菜单信息编辑',
			url : Cmw.getUrl('./sysAccordion_save.action'),
			items : [hid_isenabled,id,sysid,sysname,code,name,iconCls,type,url,orderNo,remark],
			width:200,
			buttons :[{text:'保存',handler : function(){
				self.appgrid.params = self.toolBar.getValues();
				 EventManager.frm_save(appform,{sfn:function(data){
					 var sysid =data.sysid;
					  appform.reset();
					 EventManager.query(self.appgrid,{sysid : sysid});
					 FormUtil.disabledFrm(appform);
				 }});
			}},{text:'重置',handler : function(){
				 EventManager.frm_reset(appform,[sysname,code,sysid]);
			}}]
		});
		
		//禁用表单元素
		FormUtil.disabledFrm(appform);
		return appform;
	},
	globalMgr : {
		sysid:null
	}
});

