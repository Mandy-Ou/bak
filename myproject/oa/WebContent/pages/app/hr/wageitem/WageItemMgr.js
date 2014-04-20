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
			text : Btn_Cfgs.WAGEPPLAN_LABEL_TEXT
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
				EventManager.add("./hrWageItem_add.action",self.appform,{sfn:function(formdate){
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
				EventManager.add("./hrWageItem_get.action?id="+id,self.appform,{sfn:function(formdate){
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
				EventManager.enabledData('./hrWageItem_enabled.action',{type:'grid',cmpt:self.appgrid});
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
				EventManager.disabledData('./hrWageItem_disabled.action',{type:'grid',cmpt:self.appgrid});
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
				EventManager.deleteData('./hrWageItem_delete.action',{type:'grid',cmpt:self.appgrid});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取Grid 对象
	 */
	getAppGrid : function(){
		var structure_1 = [{
		    header: '可用标志',
		    renderer : Render_dataSource.isenabledRender,
		    name: 'isenabled'
		},{
		    header: '编号',
		    name: 'code'
		},
		{
		    header: '项目名称',
		    name: 'name'
		},
		{
		    header: '项目方向',
		    name: 'direction'
		},
		{
		    header: '备注',
		    name: 'remark'
		},{
		    header: 'ID',
		    hidden:true,
		    name: 'id'
		}];
		var _this=this;
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    title: '工资项列表',
		    tbar:_this.toolBar,
		    structure: structure_1,
		    url: './hrWageItem_list.action',
		    needPage: false,
		    isLoad: true,
		    keyField: 'id'
		});
		return appgrid_1;
	},
	/**
	 *  获取 Form 表单对象
	 */
	getAppForm : function(){
		var self = this;
		var hid_id=FormUtil.getHidField({
			fieldLabel:"ID",
			name:"id"
		})
		var txt_code = FormUtil.getTxtField({
		    fieldLabel: '编号',
		    name: 'code',
		    "width": 125,
		    "allowBlank": false,
		    "maxLength": 50
		});
		
		var txt_name = FormUtil.getTxtField({
		    fieldLabel: '项目名称',
		    name: 'name',
		    "width": 125,
		    "allowBlank": false,
		    "maxLength": 50
		});
		
		var cbo_direction = FormUtil.getLCboField({
		    fieldLabel: '项目方向',
		    name: 'direction',
		    "width": 125,
		    "allowBlank": false,
		    "maxLength": 50,
		    "data": [["1", "加项"], ["2", "减项"]]
		});
		
		var txa_remark = FormUtil.getTAreaField({
		    fieldLabel: '备注',
		    name: 'remark',
		    "width": 125,
		    "maxLength": 50
		});
		
		var layout_fields = [
		txt_code, txt_name, cbo_direction, txa_remark,hid_id];
		var frm_cfg = {
		    title: '工资项编辑',
		    url: './hrWageItem_save.action',
		    buttons :[{text:'保存',handler : function(){
				self.appgrid.params = self.toolBar.getValues();
				 EventManager.frm_save(appform_1,{sfn : function(formDatas){
					 self.appgrid.reload(); 
					 appform.reset();
					 FormUtil.disabledFrm(appform_1);
				 }});
			}},{text:'重置',handler : function(){
				 EventManager.frm_reset(appform_1,[code]);
			}}]
		};
		var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
		//禁用表单元素
		FormUtil.disabledFrm(appform_1);
		return appform_1;
	}
});

