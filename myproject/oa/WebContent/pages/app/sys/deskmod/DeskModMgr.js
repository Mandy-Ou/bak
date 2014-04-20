/*同心日科技公司版权所有 V1.0版本*/
Ext.namespace("skythink.cmw.sys");
/**
 * 桌面模块配置管理
 */
skythink.cmw.sys.DeskModMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.DeskModMgr,Ext.util.MyObservable,{
	initModule : function(){
		this.module = new Cmw.app.widget.AbsGFormView({
			gfCfg:{formAlign:'right',wOrh:525},//表单在右边,宽度为250
			hasTopSys : true,//系统顶部
			getToolBar : this.getToolBar,//表格上面的按钮工具栏
			getAppGrid : this.getAppGrid,//表格
			getAppForm : this.getAppForm,//表单
			notify : this.notify,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			uploadWin : null
		});
	},
	notify : function(data){
		var sysId = data.id;//取到系统图标的id号
		FormUtil.disabledFrm(this.appform);
		EventManager.frm_reset(this.appform);
		this.globalMgr.sysId=sysId;
		this.refresh();
	},
	getToolBar : function(){
		var _this = this;
		var toolBar = null;
		var barItems = [{
			type : 'label',
			text : '模块标题'
		},{
			type : 'search',
			name : 'title',
			width : 200,
			onTrigger2Click:function(){
				_this.refresh();
			}
		},{
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				toolBar.resets();
			}
		},{type:"sp"},{
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				FormUtil.enableFrm(_this.appform);
				_this.appform.reset();
				var hidSysId = _this.appform.findFieldByName('sysId');
				hidSysId.setValue(_this.globalMgr.sysId);
//				var txt_token = _this.appform.findFieldByName('token');
//				txt_token.setParams({action:1,sysid : _this.globalMgr.sysId});
			}
		},{
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				var id = _this.appgrid.getSelId();
				if(!id) return;
				FormUtil.enableFrm(_this.appform);
				EventManager.add("./sysDeskMod_get.action?id="+id,_this.appform,null,function(formDatas){
//					var txt_token = _this.appform.findFieldByName('token');
//					txt_token.setParams({action:1,sysid : _this.globalMgr.sysId});
				});
			}
		},{type:"sp"},{
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./sysDeskMod_delete.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.ENABLED,self:_this});
			}
		},{
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./sysDeskMod_delete.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.DISABLED,self:_this});
				 self.apptree.reload();
			}
		},{
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./sysDeskMod_delete.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.DEL,self:_this});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems});
		return toolBar;
	},
	/**
	 *  获取Grid 对象
	 */
	getAppGrid : function(){
		var structure = [{header: '可用标识',name: 'isenabled',width:60,
		    renderer :function(val){
		     return Render_dataSource.isenabledRender(val);
		    }
		},{
		    header: '系统ID',
		    name: 'sysId',
		    hidden: true
		},
		{
		    header: '模块编号',
		    name: 'code',
		    width: 120
		},
		{
		    header: '模块标题',
		    name: 'title',
		    width: 165
		},
		{
		    header: '标题样式',
		    name: 'cls',
		    width: 60,
		    hidden: true
		},
		{
		    header: '排列顺序',
		    name: 'orderNo',
		    width: 60
		},
		{
		    header: '消息条数',
		    name: 'msgCount',
		    width: 60
		},
		{
		    header: '滚动方式',
		    name: 'dispType',
		    width: 60,
		    renderer :function(val){
		     return Render_dataSource.dispTypeRender(val);
		    }
		},
		{
		    header: '默认模块',
		    name: 'isdefault',
		    width: 60,
		    renderer :function(val){
		     return Render_dataSource.isdefaultRender(val);
		    }
		},
		{
		    header: '显示更多',
		    name: 'ismore',
		    width: 60,
		    renderer :function(val){
		     return Render_dataSource.ismoreRender(val);
		    }
		},
		{
		    header: '链接地址',
		    name: 'url'
		},
		{
		    header: '链接参数',
		    name: 'urlparams'
		},
		{
		    header: '更多url',
		    name: 'moreUrl'
		},
		{
		    header: '加载方式',
		    name: 'loadType',
		    renderer :function(val){
		     return Render_dataSource.loadTypeRender(val);
		    }
		},
		{
		    header: '数据代码',
		    name: 'dataCode',
		    hidden: true,
		    hideable : true
		},
		{
		    header: '显示列名',
		    name: 'dispcmns'
		}];

		var _appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			isLoad : false,
			needPage: true,
			keyField : 'id',
			url : './sysDeskMod_list.action'
		});
		return _appgrid;
	},
	refresh:function(optionType,data){
		if(this.toolBar.disabled){
			this.toolBar.enable();
		}
		var params = this.toolBar.getValues();
		var sysId =this.globalMgr.sysId;
		if(!params) params = {};
		params.sysId = sysId;
		EventManager.query(this.appgrid,params);
	},
	/**
	 *  获取 Form 表单对象
	 */
	getAppForm : function(){
		var _this = this;
		var ONE_WIDTH = 380;
		var txt_id = FormUtil.getHidField({
		    fieldLabel: 'ID',
		    name: 'id'
		});
		
		var txt_sysId = FormUtil.getHidField({
		    fieldLabel: '系统ID',
		    name: 'sysId',
		    "width": 125,
		    "allowBlank": false
		});
		
		var txt_title = FormUtil.getTxtField({
		    fieldLabel: '模块标题',
		    name: 'title',
		    "width": ONE_WIDTH,
		    "allowBlank": false,
		    "maxLength": "50"
		});
		
		var txt_code = FormUtil.getTxtField({
		    fieldLabel: '模块编号',
		    name: 'code',
		    "width": 125,
		    "maxLength": "20"
		});
		
		var txt_cls = FormUtil.getTxtField({
		    fieldLabel: '标题样式',
		    name: 'cls',
		    "width": 125,
		    "maxLength": "20"
		});
		
		var int_orderNo = FormUtil.getIntegerField({
		    fieldLabel: '排列顺序',
		    name: 'orderNo',
		    "width": 125,
		    "allowBlank": false,
		    "value": "0",
		    "maxLength": 10
		});
		
		var int_msgCount = FormUtil.getIntegerField({
		    fieldLabel: '消息条数',
		    name: 'msgCount',
		    "width": 125,
		    "allowBlank": false,
		    "value": "7",
		    "maxLength": 10
		});
		
		var rad_dispType = FormUtil.getRadioGroup({
		    fieldLabel: '滚动方式',
		    name: 'dispType',
		    "width": 180,
		    "allowBlank": false,
		    "maxLength": 10,
		    "items": [{
		        "boxLabel": "不滚动",
		        "name": "dispType",
		        width : 50,
		        "inputValue": "1"
		    },
		    {
		        "boxLabel": "上下",
		        "name": "dispType",
		          width : 50,
		        "inputValue": "2"
		    },
		    {
		        "boxLabel": "左右",
		        "name": "dispType",
		          width : 50,
		        "inputValue": "3"
		    }]
		});
		
		var txt_url = FormUtil.getTxtField({
		    fieldLabel: '链接地址',
		    name: 'url',
		    "width": 125,
		    "maxLength": "50"
		});
		
		var txt_urlparams = FormUtil.getTxtField({
		    fieldLabel: '链接参数',
		    name: 'urlparams',
		    "width": 125,
		    "maxLength": "50"
		});
		
		var rad_isdefault = FormUtil.getRadioGroup({
		    fieldLabel: '默认模块',
		    name: 'isdefault',
		    "width": 125,
		    "allowBlank": false,
		    "maxLength": 10,
		    "items": [{
		        "boxLabel": "默认",
		        "name": "isdefault",
		        "inputValue": "1"
		    },
		    {
		        "boxLabel": "非默认",
		        "name": "isdefault",
		        "inputValue": "2"
		    }]
		});
		
		var rad_ismore = FormUtil.getRadioGroup({
		    fieldLabel: '显示更多',
		    name: 'ismore',
		    "width": 125,
		    "allowBlank": false,
		    "maxLength": 10,
		    "items": [{
		        "boxLabel": "是",
		        "name": "ismore",
		        "inputValue": "1"
		    },
		    {
		        "boxLabel": "否",
		        "name": "ismore",
		        "inputValue": "2"
		    }]
		});
		
		var txt_moreUrl = FormUtil.getTxtField({
		    fieldLabel: '更多url',
		    name: 'moreUrl',
		    "width": 125,
		    "maxLength": "100"
		});
		
		var rad_loadType = FormUtil.getRadioGroup({
		    fieldLabel: '加载方式',
		    name: 'loadType',
		    "width": 180,
		    "allowBlank": false,
		    "maxLength": 10,
		    "items": [{
		        "boxLabel": "sql",
		        "name": "loadType",
		        "inputValue": "1"
		    },
		    {
		        "boxLabel": "hql",
		        "name": "loadType",
		        "inputValue": "2"
		    },
		    {
		        "boxLabel": "方法 ",
		        "name": "loadType",
		        "inputValue": "3"
		    }]
		});
		
		var txt_dispcmns = FormUtil.getTxtField({
		    fieldLabel: '显示列名',
		    name: 'dispcmns',
		    "width": 125,
		    "maxLength": "80"
		});
		
		var txa_dataCode = FormUtil.getTAreaField({
		    fieldLabel: '数据代码',
		    name: 'dataCode',
		    "width": ONE_WIDTH,
		    "allowBlank": false,
		     height:100,
		    "maxLength": "1000"
		});
		
		var txa_dataTemp = FormUtil.getTAreaField({
		    fieldLabel: '数据模板',
		    name: 'dataTemp',
		    "width": ONE_WIDTH,
		    height:130,
		    "maxLength": "500"
		});
		
		var layout_fields = [txt_id,
		txt_sysId, txt_title, {
		    cmns: FormUtil.CMN_TWO,
		    fields: [txt_code, txt_cls, int_orderNo, int_msgCount, rad_dispType, rad_isdefault, txt_url, txt_urlparams, rad_ismore, txt_moreUrl, rad_loadType, txt_dispcmns]
		},txa_dataCode, txa_dataTemp];
		var frm_cfg = {
		    title: '桌面模块配置信息编辑',
		    url: Cmw.getUrl('./sysDeskMod_save.action'),
		    buttons :[{text:'保存',handler : function(){
		    			_this.toolBar.disable();
						EventManager.frm_save(appform,{sfn:function(data){
							 EventManager.frm_reset(appform);
							 FormUtil.disabledFrm(appform);
							 _this.refresh();
						},ffn:function(data){_this.toolBar.enable();}});
					}},{text:'重置',handler : function(){
						 EventManager.frm_reset(appform,[code]);
					}}]
		};
		var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
		//禁用表单元素
		FormUtil.disabledFrm(appform);
		return appform;
	},
	globalMgr : {
		sysId:null
	}
});

