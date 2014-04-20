/*同心日科技公司版权所有 V1.0版本*/
Ext.namespace("skythink.cmw.sys");
skythink.cmw.sys.ReportTempMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.ReportTempMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGFormView({
			tab:tab,
			params:params,
			gfCfg:{formAlign:'right',wOrh:300},//表单在右边,宽度为250
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
		this.globalMgr.sysId=sysId;
		FormUtil.disabledFrm(this.appform);
		this.globalMgr.btnChose.disable();
		EventManager.frm_reset(this.appform);
		this.refresh();
	},
	getToolBar : function(){
		var _this = this;
		var toolBar = null;
		var barItems = [{
			type : 'label',
			text : Btn_Cfgs.REPORTTEMP_LABEL_TXT
		},{
			token : "查询",
			type : 'search',
			name : 'name',
			onTrigger2Click:function(){
				_this.refresh();
			}
		},{
			token : "重置",
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				toolBar.resets();
			}
		},{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				FormUtil.enableFrm(_this.appform);
				_this.globalMgr.btnChose.enable();
				_this.appform.reset();
				var hidSysId = _this.appform.findFieldByName('sysId');
				hidSysId.setValue(_this.globalMgr.sysId);
				var txt_token = _this.appform.findFieldByName('token');
				txt_token.setParams({action:1,sysid : _this.globalMgr.sysId});
			}
		},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				var id = _this.appgrid.getSelId();
				if(!id) return;
				FormUtil.enableFrm(_this.appform);
				_this.globalMgr.btnChose.enable();
				EventManager.add("./sysReportTemp_get.action?id="+id,_this.appform,null,function(formDatas){
					var txt_token = _this.appform.findFieldByName('token');
					txt_token.setParams({action:1,sysid : _this.globalMgr.sysId});
					
				});
			}
		},{type:"sp"},{
			token : "启用",
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./sysReportTemp_delete.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.ENABLED,self:_this});
			}
		},{
			token : "禁用",
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./sysReportTemp_delete.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.DISABLED,self:_this});
				 self.apptree.reload();
			}
		},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./sysReportTemp_delete.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.DEL,self:_this});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取Grid 对象
	 */
	getAppGrid : function(){
		var structure = [{
		    header: '可用标识',
		    name: 'isenabled',
		    width:65,
		    renderer :function(val){
		     return Render_dataSource.isenabledRender(val);
		    }
		},
		{
		    header: '报表名称',
		    name: 'name',
		    width:200
		},
		{
		    header: '报表类型',
		    name: 'type',
		    width:'80',
		    renderer: function(val) {
		        switch (val) {
		        case '1':
		            val = 'Excel报表';
		            break;
		        }
		        return val;
		    }
		},
		{
		    header: '功能名称',
		    name: 'funName',
		     width:'125'
		},
		{
		    header: '模板路径',
		    name: 'url',
		    width: 200
		},
		{
		    header: '上传人',
		    name: 'creator'
		},
		{
		    header: '上传时间',
		    name: 'createTime'
		},
		{
		    header: '备注',
		    name: 'remark',
		    width: 200
		}];
		var _appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			height:500,
			isLoad : false,
			needPage: true,
			keyField : 'id',
			url : './sysReportTemp_list.action'
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
		
		var txt_id = FormUtil.getHidField({
		    fieldLabel: 'ID',
		    name: 'id'
		});
		var hid_isenabled = FormUtil.getHidField({name:'isenabled'});
		var txt_sysId = FormUtil.getHidField({
		    fieldLabel: '系统ID',
		    name: 'sysId'
		});
		
		var txt_name = FormUtil.getTxtField({
		    fieldLabel: '报表名称',
		    name: 'name',
		    "width": 180,
		    "allowBlank": false,
		    "maxLength": "50"
		});
		
//		var int_type = FormUtil.getIntegerField({
//		    fieldLabel: '报表类型',
//		    name: 'type',
//		    "width": 180,
//		    "allowBlank": false,
//		    "value": "1",
//		    "maxLength": 10
//		});
		
		/*数据过滤ID列表*/	
		var txt_token = FormUtil.getMyComboxTree({fieldLabel : '功能名称',name:'token',
		url:'./sysTree_list.action',disabled:true,isAll:true,width:180,height:350});
		
		var cpo_url = FormUtil.getMyUploadField({fieldLabel: '上传模板', name: 'url',width: 180,sigins:null,
		    allowBlank: false,dir : 'xls_report_path',allowFiles:"allowFiles_xlsreport_ext"});
		this.globalMgr.btnChose = cpo_url.btnChose.disable();
		  var remark  = FormUtil.getTAreaField({fieldLabel : '备注',name:'remark',width : 180,maxLength:200});
		var layout_fields = [txt_id,hid_isenabled,txt_sysId, txt_name, txt_token, cpo_url,remark];
		var frm_cfg = {
		    title: '报表模板信息编辑',
		    url: Cmw.getUrl('./sysReportTemp_save.action'),
		    buttons :[{text:'保存',handler : function(){
		    			_this.toolBar.disable();
						EventManager.frm_save(appform,{sfn:function(data){
							 FormUtil.disabledFrm(appform);
							  EventManager.frm_reset(appform);
							 cpo_url.btnChose.disable();
							 _this.refresh();
						},ffn:function(data){_this.toolBar.enable();}});
					}},{text:'重置',handler : function(){
						 EventManager.frm_reset(appform,[txt_id,txt_sysId]);
					}}]
		};
		var appform = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
		//禁用表单元素
		FormUtil.disabledFrm(appform);
		return appform;
	},
	globalMgr : {
		sysId:null,
		btnChose : null
	}
});

