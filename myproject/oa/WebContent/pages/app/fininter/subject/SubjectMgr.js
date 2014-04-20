/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("skythink.cmw.fininter");
/**
 * 科目配置管理UI
 * @author cmw
 * @date 2013-03-29 20:31
 * */ 
skythink.cmw.fininter.SubjectMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.fininter.SubjectMgr,Ext.util.MyObservable,{
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
			gridTitle : '财务系统科目配置列表',
			notify : this.notify
		});
	},
	getToolBar : function(){
		var self = this;
			var barItems = [
						{token : '刷新',text:Btn_Cfgs.REFRESH_BTN_TXT,iconCls:Btn_Cfgs.REFRESH_CLS,handler:function(){
							self.globalMgr.query(self);
						}},
						{type:'sp'},
						{token : '同步数据',text:Fs_Btn_Cfgs.SYNCHRONOUS_BTN_TXT,iconCls:Fs_Btn_Cfgs.SYNCHRONOUS_BTN_CLS,handler:function(){
							self.globalMgr.synchronousData(self);
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
		//id,code,name,levels,detail,dc,currency,atype,itemClass,parent,refId
	    var structure = [{header : '可用标识',name : 'isenabled',width:60,renderer : Render_dataSource.isenabledRender},
						{header:'科目号' ,name:'code' ,width:100},
						{header:'科目名称' ,name:'name' ,width:100},
						{header:'科目级次' ,name:'levels' ,width:100},
						{header:'明细科目' ,name:'detail' ,width:100,renderer : Fs_Render_dataSource.subject_detailRender},
						{header:'借贷方向' ,name:'dc' ,width:100,renderer : Fs_Render_dataSource.subject_dcRender},
						{header:'币别' ,name:'currency' ,width:100},
						{header:'科目类型' ,name:'atype' ,width:100,renderer : Fs_Render_dataSource.subject_atypeRender},
						{header:'核算项目' ,name:'itemClass' ,width:100},
						{header:'上级科目' ,name:'parent' ,width:100},
						{header:'科目ID' ,name:'refId' ,width:100},
						{header:'同步日期' ,name:'createTime' ,width:135}
						];
		var appgrid = new Ext.ux.grid.AppGrid({
			title : this.gridTitle,
			tbar : this.toolBar,
			structure : structure,
			url : './fsSubject_list.action',
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
	
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({url:'./fsAcctGroup_list.action?action=1',
				isLoad : false,width:200,enableDD:false,autoScroll :true});
		_apptree.addListener({'click' : function(node,e){//加入一个单击事件
			var nodeId = node.id+"";
			self.appgrid.reload({groupId:nodeId});
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
		 * 当前激活的按钮文本	
		 * @type 
		 */
		sysid:null,
		activeKey : null,
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			_this.apptree.reload();
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
					EventManager.get('./fsSubject_synchronous.action',{params:{finsysId:_this.globalMgr.finsyscfgId},
					 sfn:function(json_data){
						_this.globalMgr.query(_this);
					 	Cmw.unmask(_this);
					 	ExtUtil.alert({msg:Fs_Msg_SysTip.msg_synchronousSuccess});
					 },ffn:function(json_data){
					 	Cmw.unmask(_this);
					 	ExtUtil.alert({msg:Fs_Msg_SysTip.msg_synchronousFailure});
					 }});
			 }});
		}
	}
});

