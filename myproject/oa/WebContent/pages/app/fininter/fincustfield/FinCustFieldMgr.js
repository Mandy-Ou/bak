/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("skythink.cmw.fininter");
/**
 * 自定义字段配置管理UI
 * @author cmw
 * @date 2013-03-30 11:16
 * */ 
skythink.cmw.fininter.FinCustFieldMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.fininter.FinCustFieldMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreeGridView({
			tab : tab,
			params : params,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			hasTopSys : true,
			sysParams : {restype:'FINSYS'},
			gridTitle : '财务系统自定义字段配置列表',
			notify : this.notify
		});
	},
	getToolBar : function(){
		var self = this;
		var barItems = [
				{token : '添加业务对象',text:Fs_Btn_Cfgs.BUSSOBJECT_ADD_BTN_TXT,iconCls:Fs_Btn_Cfgs.BUSSOBJECT_ADD_BTN_CLS,handler:function(){
					self.globalMgr.winEdit.show({key:"添加业务对象",self:self});
				}},
				{token : '编辑业务对象',text:Fs_Btn_Cfgs.BUSSOBJECT_EDIT_BTN_TXT,iconCls:Fs_Btn_Cfgs.BUSSOBJECT_EDIT_BTN_CLS,handler:function(){
					self.globalMgr.winEdit.show({key:"编辑业务对象",optionType:OPTION_TYPE.EDIT,self:self});
				}},
				{type:'sp'},
				{token : '起用业务对象',text:Fs_Btn_Cfgs.BUSSOBJECT_ENABLED_BTN_TXT,iconCls:Fs_Btn_Cfgs.BUSSOBJECT_ENABLED_BTN_CLS,handler:function(){
					self.globalMgr.activeKey = '起用业务对象';
					EventManager.enabledData('./fsFinBussObject_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.ENABLED,self:self});
				}},
				{token : '禁用业务对象',text:Fs_Btn_Cfgs.BUSSOBJECT_DISABLED_BTN_TXT,iconCls:Fs_Btn_Cfgs.BUSSOBJECT_DISABLED_BTN_CLS,handler:function(){
					self.globalMgr.activeKey = '禁用业务对象';
					EventManager.disabledData('./fsFinBussObject_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DISABLED,self:self});
				}},
				{token : '删除业务对象',text:Fs_Btn_Cfgs.BUSSOBJECT_DEL_BTN_TXT,iconCls:Fs_Btn_Cfgs.BUSSOBJECT_DEL_BTN_CLS,handler:function(){
					self.globalMgr.activeKey = '删除业务对象';
					EventManager.deleteData('./fsFinBussObject_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.OPTION_TYPE,self:self});
				}},
				{type:'sp'},
				{token : '添加字段',text:Fs_Btn_Cfgs.CUSTFIELD_ADD_BTN_TXT,iconCls:Fs_Btn_Cfgs.CUSTFIELD_ADD_BTN_CLS,handler:function(){
					self.globalMgr.winEdit.show({key:"添加字段",self:self});
				}},
				{token : '编辑字段',text:Fs_Btn_Cfgs.CUSTFIELD_EDIT_BTN_TXT,iconCls:Fs_Btn_Cfgs.CUSTFIELD_EDIT_BTN_CLS,handler:function(){
					self.globalMgr.winEdit.show({key:"编辑字段",optionType:OPTION_TYPE.EDIT,self:self});
				}},
				{token : '起用字段',text:Fs_Btn_Cfgs.CUSTFIELD_ENABLED_BTN_TXT,iconCls:Fs_Btn_Cfgs.CUSTFIELD_ENABLED_BTN_CLS,handler:function(){
					EventManager.enabledData('./fsFinCustField_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
				}},
				{token : '禁用字段',text:Fs_Btn_Cfgs.CUSTFIELD_DISABLED_BTN_TXT,iconCls:Fs_Btn_Cfgs.CUSTFIELD_DISABLED_BTN_CLS,handler:function(){
					EventManager.disabledData('./fsFinCustField_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
				}},
				{token : '删除字段',text:Fs_Btn_Cfgs.CUSTFIELD_DEL_BTN_TXT,iconCls:Fs_Btn_Cfgs.CUSTFIELD_DEL_BTN_CLS,handler:function(){
					EventManager.deleteData('./fsFinCustField_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.OPTION_TYPE,self:self});
				}}
				];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 *  获取 按钮 Grid 对象
	 */
	getAppGrid : function(){
		var self =this;
	    var structure = [{header : '可用标识',name : 'isenabled',width:60,renderer : Render_dataSource.isenabledRender},
						{header:'字段说明' ,name:'name' ,width:100},
						{header:'字段名称' ,name:'field' ,width:100},
						{header:'表列名' ,name:'cmn' ,width:100},
						{header:'数据类型' ,name:'dataType' ,width:100,renderer : Fs_Render_dataSource.finCustField_dataTypeRender},
						{header:'创建日期' ,name:'createTime' ,width:130},
						{header:'备注' ,name:'remark' ,width:300}
						];
		var appgrid = new Ext.ux.grid.AppGrid({
			title : this.gridTitle,
			tbar : this.toolBar,
			structure : structure,
			url : './fsFinCustField_list.action',
			keyField : 'id',
			needPage : true,
			isLoad: false
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
		var winKey = this.globalMgr.activeKey;
		var finsyscfgId = this.globalMgr.finsyscfgId;
		if(winKey == '添加业务对象' || winKey == '编辑业务对象'
		|| winKey == '起用业务对象' || winKey == '禁用业务对象' || winKey == '删除业务对象'){
			this.apptree.reload({finsysId:finsyscfgId});
		}else{
			var bussObjectId = this.apptree.getSelId();
			this.appgrid.reload({bussObjectId:bussObjectId});
		}
		this.globalMgr.activeKey = null;
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({url:'./fsFinBussObject_list.action',
				isLoad : false,width:200,enableDD:false,autoScroll :true});
		_apptree.addListener({'click' : function(node,e){//加入一个单击事件
			var nodeId = node.id+"";
			self.appgrid.reload({bussObjectId:nodeId});
		}});
		return _apptree;
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
		this.apptree.reload({finsysId:finsyscfgId});
	},
	globalMgr : {
		/**
		 * 业务财务系统配置ID
		 * @type 
		 */
		finsyscfgId:null,
		activeKey : null,
		winModule : null,
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winKey = this.valid(parentCfg);
				if(!winKey) return;
				parentCfg.finsysId = _this.globalMgr.finsyscfgId;
				parentCfg.bussObjectId = _this.apptree.getSelId();
				if(_this.appCmpts[winKey]){
					_this.appCmpts[winKey].show(parentCfg);
				}else{
					Cmw.importPackage('pages/app/fininter/fincustfield/'+_this.globalMgr.winModule,function(module) {
					 	_this.appCmpts[winKey] = module.WinEdit;
					 	_this.appCmpts[winKey].show(parentCfg);
			  		});
				}
			},
			/**
			 * 验证
			 * @param {} parentCfg
			 * @return {}
			 */
			valid : function(parentCfg){
				var _this =  parentCfg.self;
				var winKey=parentCfg.key;
				var errMsg = null;
				if(winKey=='添加业务对象'){
					parentCfg.parent = _this.apptree;
					_this.globalMgr.winModule = "FinBussObjectEdit";
				}else if(winKey=='编辑业务对象'){
					parentCfg.parent = _this.apptree;
					_this.globalMgr.winModule = "FinBussObjectEdit";
					if(!_this.apptree.getSelId()){
						winKey = null;
						errMsg = Msg_SysTip.msg_noSelNode;
					}
				}else if(winKey=='添加字段'){
					parentCfg.parent = _this.apptree;
					_this.globalMgr.winModule = "FinCustFieldEdit";
					if(!_this.apptree.getSelId()){
						winKey = null;
						errMsg = Msg_SysTip.msg_noSelNode;
					}
					
				}else{
					parentCfg.parent = _this.appgrid;
					_this.globalMgr.winModule = "FinCustFieldEdit";
					if(!_this.appgrid.getSelId()){
						winKey = null;
						errMsg = Msg_SysTip.msg_noSelRecord;
					}
				}
				_this.globalMgr.activeKey = winKey;
				return winKey;
			}
		}
	}
});

