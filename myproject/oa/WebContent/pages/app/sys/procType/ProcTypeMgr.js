Ext.namespace("skythink.cmw.sys");
/**
 * OA 流程分类管理UI
 * @author cmw
 * @date 2013-12-24
 * */ 
skythink.cmw.sys.menuMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.menuMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreeGridView({
			tab : tab,
			params : params,
			hasTopSys : false,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	getToolBar : function(){
		var self = this;
		var barItems = [{
			token : "添加分类",
			text : "添加分类",
			iconCls:Btn_Cfgs.MENU_ADD_BTN_CLS,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.token,self:self});
			}
		},{
			token : "编辑分类",
			text : "编辑分类",
			iconCls:Btn_Cfgs.MENU_EDIT_BTN_CLS,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.token,optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{type:"sp"},{
			token : "起用分类",
			text : "起用分类",
			iconCls:Btn_Cfgs.MENU_ENABLED_BTN_CLS,
			handler : function(){
  				EventManager.enabledData('./oaProcType_enabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用分类",
			text : "禁用分类",
			iconCls:Btn_Cfgs.MENU_DISABLED_BTN_CLS,
			handler : function(){
				EventManager.disabledData('./oaProcType_disabled.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除分类",
			text : "删除分类",
			iconCls:Btn_Cfgs.MENU_DEL_BTN_CLS,
			handler : function(){
				EventManager.deleteData('./oaProcType_delete.action',{type:'tree',cmpt:self.apptree,self:self});
			}
		},{type:"sp"},{
			token : "选择流程",
			text : "选择流程",
			iconCls:Btn_Cfgs.MENU_ADD_BTN_CLS,
			handler : function(){
				self.globalMgr.openDilaog({key:this.token,self:self});
			}
		},{
			token : "删除流程",
			text : "删除流程",
			iconCls:Btn_Cfgs.MENU_DEL_BTN_CLS,
			handler : function(){
				EventManager.deleteData('./oaPtrelation_delete.action',{type:'grid',cmpt:self.appgrid,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppGrid : function(){
		var self = this;
	  		var structure_1 = [	{
			    header: '可用标识',
			    name: 'isenabled',
			    width: 60,
			    renderer: Render_dataSource.isenabledRender
			},{
			    header: '流程编号',
			    name: 'code'
			},
			{
			    header: '流程名称',
			    name: 'name',
			    width: 115
			},
			{
			    header: '是否配置流程',
			    name: 'pdid',
			    width: 90,
			    renderer: function(val) {
			        return !val ? '否' : '是';
			    }
			},
			{
			    header: '适用机构',
			    name: 'useorg',
			    width: 80,
			  	renderer: function(val) {
			        switch (val) {
			        case '0':
			            val = '所有公司';
			            break;
			        case '1':
			            val = '指定公司';
			            break;
			        }
			        return val;
				}
			},
			{
			    header: '指定公司名称',
			    name: 'companyNames',
			    width: 125
			},
			{
			    header: '流程定义ID',
			    name: 'pdid',
			    hidden: true
			}];
			var appgrid = new Ext.ux.grid.AppGrid({
			    title: '业务流程列表',
			    tbar : this.toolBar,
			    structure: structure_1,
			    url: './oaPtrelation_list.action',
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
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
		this.apptree.reload();
		this.globalMgr.activeKey = null;
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({url:'./oaProcType_tree.action?action=1',title:"流程分类列表",
				isLoad : false,width:200,enableDD:false,autoScroll :true});
		_apptree.addListener({'click' : function(node,e){//加入一个单击事件
			var nodeId = node.id;
			self.appgrid.reload({proctypeId:nodeId});
		}});
		return _apptree;
	},
	globalMgr : {
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		sysid:null,
		activeKey : null,
		openDilaog : function(parentCfg){
			var _this = parentCfg.self;
			var winkey=parentCfg.key;
			var nodeId = _this.apptree.getSelId();
			if(!nodeId){
				ExtUtil.alert({msg:'请在左侧选择流程分类!'});
				return;
			}
			parentCfg.callback = function(ids,records){
				 EventManager.get('./oaPtrelation_save.action',{params:{proctypeId:nodeId,bussProccIds :ids},sfn:function(json_data){
				 	_this.appgrid.reload({proctypeId:nodeId});
				 	Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_dataSave);
				 }});		
			}
			if(_this.appCmpts[winkey]){
				_this.appCmpts[winkey].show(parentCfg);
			}else{
				Cmw.importPackage('pages/app/dialogbox/BussProccDialogbox',function(module) {
				 	_this.appCmpts[winkey] = module.DialogBox;
				 	_this.appCmpts[winkey].show(parentCfg);
		  		});
			}
		},
		winEdit : {
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.apptree;
				parentCfg.isCheck = true;
				parentCfg.parent = parent;
				var selId = parent.getSelId();
				if(!selId && winkey!="添加分类"){
					ExtUtil.alert({msg:Msg_SysTip.msg_noSelNode});
					return;
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					Cmw.importPackage('pages/app/sys/procType/ProcTypeEdit',function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
		
	}
});

