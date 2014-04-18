/**
 * 九洲科技公司产品 命名空间 
 * 联系电话：18064179050 程明卫[系统设计师]
 * 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 基础数据 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.GvlistMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.GvlistMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
			hasTopSys : false,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [
						{token : '查询',text:Btn_Cfgs.HOLIDAYS_QUERY_BTN_TXT,iconCls:Btn_Cfgs.HOLIDAYS_QUERY_BTN_CLS,tooltip:Btn_Cfgs.HOLIDAYS_QUERY_TIP_BTN_TXT,handler:function(){
							self.globalMgr.query(self);
						}},
						{token : '重置',text:Btn_Cfgs.RESET_BTN_TXT,iconCls:Btn_Cfgs.RESET_CLS,handler:function(){
							self.queryFrm.reset();
						}},
						{token : '节假日初始化',text:Btn_Cfgs.HOLIDAYS_INIT_BTN_TXT,iconCls:Btn_Cfgs.INSERT_CLS,handler:function(){
							self.globalMgr.winEdit.show({key:Btn_Cfgs.HOLIDAYS_INIT_BTN_TXT,self:self});
						}},
						{type:'sp'},
						{token : '添加节假日',text:Btn_Cfgs.HOLIDAYS_ADD_BTN_TXT,iconCls:Btn_Cfgs.HOLIDAYS_ADD_BTN_CLS,tooltip:Btn_Cfgs.HOLIDAYS_ADD_TIP_BTN_TXT,handler:function(){
							self.globalMgr.winEdit.show({key:Btn_Cfgs.INSERT_BTN_TXT,self:self});
						}},
						{token : '编辑节假日',text:Btn_Cfgs.HOLIDAYS_EDIT_BTN_TXT,iconCls:Btn_Cfgs.HOLIDAYS_EDIT_BTN_CLS,tooltip:Btn_Cfgs.HOLIDAYS_EDIT_TIP_BTN_TXT,handler:function(){
							self.globalMgr.winEdit.show({key:Btn_Cfgs.MODIFY_BTN_TXT,optionType:OPTION_TYPE.EDIT,self:self});
						}},
						{type:'sp'},
						{token : '启用',text:Btn_Cfgs.HOLIDAYS_ENABLED_BTN_TXT,iconCls:Btn_Cfgs.HOLIDAYS_ENABLED_BTN_CLS,tooltip:Btn_Cfgs.HOLIDAYS_ENABLED_TIP_BTN_TXT,handler:function(){
							EventManager.enabledData('./sysHolidays_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
						}},
						{token : '禁用',text:Btn_Cfgs.HOLIDAYS_DISABLED_BTN_TXT,iconCls:Btn_Cfgs.HOLIDAYS_DISABLED_BTN_CLS,tooltip:Btn_Cfgs.HOLIDAYS_DISABLED_TIP_BTN_TXT,handler:function(){
							EventManager.disabledData('./sysHolidays_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
						}},
						{token : '删除',text:Btn_Cfgs.HOLIDAYS_DEL_BTN_TXT,iconCls:Btn_Cfgs.HOLIDAYS_DEL_BTN_CLS,tooltip:Btn_Cfgs.HOLIDAYS_DEL_TIP_BTN_TXT,handler:function(){
							EventManager.deleteData('./sysHolidays_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
						}}
						];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		var txt_name = FormUtil.getTxtField({fieldLabel : '节假日名称',name:'name'});
 		var txt_sdate = FormUtil.getDateField({fieldLabel : '节假日期',name:'sdate'});
 		var txt_edate = FormUtil.getDateField({fieldLabel : '至',name:'edate'});
 		var comp_esdate = FormUtil.getMyCompositeField({
			itemNames : 'sdate,edate',
			sigins : null,
			 fieldLabel: '节假日期',width:300,sigins:null,
			 name:'comp_esdate',
			 items : [txt_sdate,{xtype:'displayfield',value:'至'},txt_edate]
		});
		 var rad_isenabled = FormUtil.getRadioGroup({fieldLabel : '可用标识', name:'isenabled',
			items : [{boxLabel : '可用', name:'isenabled',inputValue:1},
				{boxLabel : '禁用', name:'isenabled',inputValue:0}]});

		 var layout_fields = [{cmns:'THREE',fields:[rad_isenabled,txt_name,comp_esdate]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var self =this;
	    var structure = [{header : '可用标识',name : 'isenabled',width:60,renderer : Render_dataSource.isenabledRender},
						{header:'节假日id' ,name:'id' ,width:100,hidden:true},
						{header:'节假日名称' ,name:'name' ,width:100},
						{header:'开始日期' ,name:'sdate' ,width:100,renderer : function(val){
							return self.globalMgr.rederderdate(val);
						}},
						{header:'结束日期' ,name:'edate' ,width:150,renderer : function(val){
							return self.globalMgr.rederderdate(val);
						}},
						{header:'备注' ,name:'remark' ,width:200}];
		var appgrid = new Ext.ux.grid.AppGrid({
			tbar : this.toolBar,
			structure : structure,
			url : './sysHolidays_list.action',
			keyField : 'id',
			needPage : true,
			isLoad: true
		});
		return appgrid;
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
		var activeKey = this.globalMgr.activeKey;
			if(activeKey ==Btn_Cfgs.INSERT_BTN_TXT || activeKey == Btn_Cfgs.MODIFY_BTN_TXT || activeKey == Btn_Cfgs.HOLIDAYS_INIT_BTN_TXT){
				this.appgrid.reload()
			}else {
				this.globalMgr.query(this);
			}
		this.globalMgr.activeKey = null;
	},
	
	globalMgr : {
		rederderdate : function(val){
			var sdate = new Date(val);
			var y = sdate.getFullYear()+"-";
			var m = (sdate.getMonth() + 1)+"-";
			if(sdate.getMonth() + 1<10){
				var m = '0'+(sdate.getMonth() + 1)+"-";
			}
			var d = sdate.getDate();
			if(sdate.getDate()<10){
				d='0'+d;
			}
			var s =y+m+d;
			return s;
		},
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
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		
		activeKey : null,
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				if(winkey == Btn_Cfgs.MODIFY_BTN_TXT){
					var selId = _this.appgrid.getSelId();
					if(winkey == Btn_Cfgs.MODIFY_BTN_TXT && !selId ){
						ExtUtil.alert({msg:"请选择表格中的数据！！！"}); return ;
					}
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey == Btn_Cfgs.INSERT_BTN_TXT || winkey == Btn_Cfgs.MODIFY_BTN_TXT){
						winModule="HolidaysEdit";
					}else if(winkey==Btn_Cfgs.HOLIDAYS_INIT_BTN_TXT){
						winModule="HolidaysinitEdit";
					}
					Cmw.importPackage('pages/app/sys/holidays/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

