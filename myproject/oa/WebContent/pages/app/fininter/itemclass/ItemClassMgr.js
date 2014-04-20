/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("skythink.cmw.fininter");
/**
 * 核算项类别配置管理 UI smartplatform_auto 2013-03-29 16:16:16
 */ 
skythink.cmw.fininter.ItemClassMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.fininter.ItemClassMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			hasTopSys : true,
			sysParams : {restype:'FINSYS'},
			gridTitle : '财务系统核算项配置列表',
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
						{token : '业务对象关联',text:Fs_Btn_Cfgs.BUSSOBJECT_BTN_TXT,iconCls:Fs_Btn_Cfgs.BUSSOBJECT_BTN_CLS,handler:function(){
							self.globalMgr.show(self,this);
						}},{token : '同步数据',text:Fs_Btn_Cfgs.SYNCHRONOUS_BTN_TXT,iconCls:Fs_Btn_Cfgs.SYNCHRONOUS_BTN_CLS,handler:function(){
							self.globalMgr.synchronousData(self);
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
		var txt_code= FormUtil.getTxtField({fieldLabel : '核算项代码',name:'code'});
		var txt_name = FormUtil.getTxtField({fieldLabel : '核算项名称',name:'name'});
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
						{header:'核算项代码' ,name:'code' ,width:100},
						{header:'核算项名称' ,name:'name' ,width:180},
						{header:'业务对象' ,name:'bussObject' ,width:180},
						{header:'核算项ID' ,name:'refId' ,width:100},
						{header:'同步日期' ,name:'createTime' ,width:135}
						];
		var appgrid = new Ext.ux.grid.AppGrid({
			title : this.gridTitle,
			tbar : this.toolBar,
			structure : structure,
			url : './fsItemClass_list.action',
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
		bObjectDialog : null,
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			if(!params) params = {};
			params["finsysId"] = _this.globalMgr.finsyscfgId;
			if(params) {
				EventManager.query(_this.appgrid,params);
			}
		},
		/**
		 * 数据同步方法
		 * @param {} _this
		 */
		synchronousData : function(_this){
			 ExtUtil.confirm({title:Fs_Msg_SysTip.title_appconfirm,msg:Fs_Msg_SysTip.msg_synchronousData,
			 fn:function(btn){
			 	if(btn != 'yes') return;
			 		Cmw.mask(_this,Fs_Msg_SysTip.msg_synchronousDataing);
					EventManager.get('./fsItemClass_synchronous.action',{params:{finsysId:_this.globalMgr.finsyscfgId},
					 sfn:function(json_data){
						_this.globalMgr.query(_this);
					 	Cmw.unmask(_this);
					 	ExtUtil.alert({msg:Fs_Msg_SysTip.msg_synchronousSuccess});
					 },ffn:function(json_data){
					 	Cmw.unmask(_this);
					 	ExtUtil.alert({msg:Fs_Msg_SysTip.msg_synchronousFailure});
					 }});
			 }});
		},
		show : function(_this,button){
			var id = _this.appgrid.getSelId();
			if(!id) return;
			var btnEl = button.el;
			var parentCfg = {
				params : {finsysId:this.finsyscfgId,isenabled:1},
				parent : btnEl,
				callback : function(selIds,selTxts){
					 EventManager.get('./fsItemClass_update.action',{params:{id:id,bussObjectId:selIds},sfn:function(json_data){
					 	_this.globalMgr.query(_this);
					 	ExtUtil.alert({msg:Msg_SysTip.msg_dataSucess});
					 },ffn:function(json_data){
					 	ExtUtil.alert({msg:Msg_SysTip.msg_dataFailure});
					 }});
				}
			};
			
			if(!this.bObjectDialog){
				Cmw.importPackage('pages/app/dialogbox/BussObjectDialogbox',function(module) {
				 	_this.globalMgr.bObjectDialog = module.DialogBox;
				 	_this.globalMgr.bObjectDialog.show(parentCfg);
		  		});
			}else{
				this.bObjectDialog.show(parentCfg);
			}
		}
	}
});

