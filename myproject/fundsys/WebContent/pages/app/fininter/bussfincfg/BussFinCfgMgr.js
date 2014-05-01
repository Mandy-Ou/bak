/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("skythink.cmw.fininter");
/**
 * 业务财务系统映射管理 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
skythink.cmw.fininter.BussFinCfgMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(skythink.cmw.fininter.BussFinCfgMgr,Ext.util.MyObservable,{
		initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getToolBar : this.getToolBar,
			getAppCmpt : this.getAppCmpt,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token : '财务系统映射',
			text : Fs_Btn_Cfgs.SYSTEM_MAPPING_BTN_TXT,
			iconCls:Fs_Btn_Cfgs.SYSTEM_MAPPING_BTN_CLS,
			tooltip:Fs_Btn_Cfgs.SYSTEM_MAPPING_TIP_BTN_TXT,
			handler : function(){
					self.globalMgr.winEdit.show({key:"财务系统映射",self:self});
			}
		},{
			token : "取消映射",
			text :Fs_Btn_Cfgs.SYSTEM_UNMAPPING_BTN_TXT,
			iconCls:Fs_Btn_Cfgs.SYSTEM_UNMAPPING_BTN_CLS,
			tooltip:Fs_Btn_Cfgs.SYSTEM_UNMAPPING_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./fsBussFinCfg_delete.action',{type:'grid',cmpt:self.appPanel,self:self});
			}
		},{type:"sp"},{
			token : '启用',
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:Btn_Cfgs.ENABLED_CLS,
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./fsBussFinCfg_enabled.action',{type:'grid',cmpt:self.appPanel,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{type:"sp"},{
			token : '禁用',
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:Btn_Cfgs.DISABLED_CLS,
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./fsBussFinCfg_disabled.action',{type:'grid',cmpt:self.appPanel,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppCmpt : function(){
		var self = this;
	  	var structure = [
	  		{header : '可用标识',name : 'isenabled',width:60,renderer : Render_dataSource.isenabledRender},
			{header : '业务系统名称',name : 'systemName',width:150},
			{header : '财务系统名称',name : 'finName',width:150},
			{header : '创建人',name : 'creator',width:95},
			{header : '创建日期',name : 'createTime',width:100},
			{header : '备注',name : 'remark',width:350},
			{header : '业务系统ID',name : 'sysId',hidden:true,hideable:true},
			{header : '财务系统ID',name : 'finsysId',hidden:true,hideable:true}
			];
		var toolBar = this.getToolBar();
		var _appgrid = new Ext.ux.grid.AppGrid({
			title : '业务财务系统映射',
			tbar : toolBar,
			structure : structure,
			url : './fsBussFinCfg_list.action',
			needPage : false,
			keyField : 'id',
			isLoad: true
		});
		return _appgrid;
	},
	refresh:function(optionType,data){
		this.globalMgr.query(this);
	},
	changeSize : function(whArr){
		var h = whArr[1];
		if(h>0) h-=2;
		this.appPanel.setHeight(h);
	},
	destroyCmpts : function(){
		
	},
	globalMgr : {
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				parentCfg.parent = _this.appPanel;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					Cmw.importPackage('pages/app/fininter/bussfincfg/BussFinCfgEdit',function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		query : function(self){
			var appGrid = self.appPanel;
			EventManager.query(appGrid);
		}
	}
});

