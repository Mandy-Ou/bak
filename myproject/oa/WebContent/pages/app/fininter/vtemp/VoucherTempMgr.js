/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("skythink.cmw.fininter");
/**
 * 凭证模板配置管理 UI smartplatform_auto 2013-04-07 17:02:16
 */ 
skythink.cmw.fininter.VoucherTempMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.fininter.VoucherTempMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
			hasTopSys : true,
			sysParams : {restype:'FINSYS'},
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			gridTitle : '凭证模板配置列表',
			notify : this.notify
		});
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [
						{token : '查询',text:Btn_Cfgs.QUERY_BTN_TXT,iconCls:Btn_Cfgs.QUERY_CLS,handler:function(){
							self.globalMgr.query(self);
						}},
						{token : '重置',text:Btn_Cfgs.RESET_BTN_TXT,iconCls:Btn_Cfgs.RESET_CLS,handler:function(){
							self.queryFrm.reset();
						}},
						{type:'sp'},
						{token : '添加模板',text:Fs_Btn_Cfgs.VTEMP_ADD_BTN_TXT,iconCls:Fs_Btn_Cfgs.VTEMP_ADD_BTN_CLS,handler:function(){
							self.globalMgr.winEdit.show({key:"添加模板",self:self});
						}},
						{token : '编辑模板',text:Fs_Btn_Cfgs.VTEMP_EDIT_BTN_TXT,iconCls:Fs_Btn_Cfgs.VTEMP_EDIT_BTN_CLS,handler:function(){
							self.globalMgr.winEdit.show({key:"编辑模板",optionType:OPTION_TYPE.EDIT,self:self});
						}},
						{token : '起用模板',text:Fs_Btn_Cfgs.VTEMP_ENABLED_BTN_TXT,iconCls:Fs_Btn_Cfgs.VTEMP_ENABLED_BTN_CLS,handler:function(){
							EventManager.enabledData('./fsVoucherTemp_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
						}},
						{token : '禁用模板',text:Fs_Btn_Cfgs.VTEMP_DISABLED_BTN_TXT,iconCls:Fs_Btn_Cfgs.VTEMP_DISABLED_BTN_CLS,handler:function(){
							EventManager.disabledData('./fsVoucherTemp_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
						}},
						{token : '删除模板',text:Fs_Btn_Cfgs.VTEMP_DEL_BTN_TXT,iconCls:Fs_Btn_Cfgs.VTEMP_DEL_BTN_CLS,handler:function(){
							EventManager.deleteData('./fsVoucherTemp_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
						}}
						];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		var txt_code = FormUtil.getTxtField({fieldLabel : '凭证模板编号',name:'code'});
		var txt_name = FormUtil.getTxtField({fieldLabel : '凭证模板名称',name:'name'});
		 var layout_fields = [{cmns:FormUtil.CMN_THREE,fields:[txt_code,txt_name]}]
		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var self =this;
	    var structure = [{header : '可用标识',name : 'isenabled',width:60,renderer : Render_dataSource.isenabledRender},
						{header: '凭证模板编号',name: 'code',width: 100},
						{header: '凭证模板名称', name: 'name',width: 180},
						{header: '凭证字', name: 'groupName',width: 55},
						{header: '默认币别',name: 'currency',width: 65},
						{header: '分录方向',name: 'entry',renderer: Fs_Render_dataSource.vt_entryRender},
						{header: '分录最大条数',name: 'maxcount'},
						{header: '批量业务策略',name: 'tactics',width:120,
						 renderer: Fs_Render_dataSource.vt_tacticsRender
						},
						{header: '创建日期',name: 'createTime',width:100
						},
						{header: '创建人',name: 'creator',width:100
						},
						{header: '备注',name: 'remark',width:200
						}];
		var appgrid = new Ext.ux.grid.AppGrid({
			title : this.gridTitle,
			tbar : this.toolBar,
			structure : structure,
			url : './fsVoucherTemp_list.action',
			keyField : 'id',
			needPage : true,
			isLoad: false
		});
		return appgrid;
	},
	/**
	 * 当点击顶部的 系统图标 时，会触发 notify 方法。
	 * @param {} data 选中的 系统图标数据。
	 * 			{id : '',name:'',code:'',url:''} 等。
	 * 			具体值请看 SystemEntity 的 getFields() 方法
	 */
	notify : function(data){
		var finName = data.finName;
		var finsyscfgId = data.finsyscfgId;
		this.globalMgr.finsyscfgId=finsyscfgId;
		var title  = "【"+finName+"】" + this.gridTitle;
		Cmw.print(title);
		this.appgrid.setTitle(title);
		this.globalMgr.query(this);
	},
	/**
	 * 当新增、修改、删除、起用、禁用 
	 * 数据保存成功后会执行此方法刷新父页面
	 * @param optionType 操作类型 参考 constant.js 文件中的 "OPTION_TYPE" 常量值
	 * 	
	 * @param {} data 
	 * 	1).如果是 新增、修改表单数据保存成功的话. data 参数代表的是表单数据json对象
	 *  2).如果是 删除、起用、禁用 数据处理成功的话.data 参数是 ids 值。例如:{ids:'1001,1002,1003'}
	 */
	refresh:function(optionType,data){
		this.globalMgr.query(this);
	},
	globalMgr : {
		finsyscfgId : null,
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		},
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				if(winkey == '编辑模板' && !_this.appgrid.getSelId()){
					return;
				}
						
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				parentCfg.finsysId = _this.globalMgr.finsyscfgId;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule='VoucherTempEdit';
					Cmw.importPackage('pages/app/fininter/vtemp/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

