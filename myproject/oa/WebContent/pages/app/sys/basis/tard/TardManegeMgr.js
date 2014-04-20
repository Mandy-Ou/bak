Ext.namespace("skythink.cmw.sys");
/**
 * 门店分类UI
 * @author cmw
 * @date 2012-11-02
 * */ 
skythink.cmw.sys.TardManegeMgr = function(){
	this.init(arguments[0]);
};

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.TardManegeMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsTreePanelView({
			tab : tab,
			params : params,
			TREE_WIDTH : 250,
			getAppPanel : this.getAppPanel,
			getToolBar : this.getToolBar,
			getAppTree : this.getAppTree,
			globalMgr : this.globalMgr,
			appgrid:this.getGrid2(),
			notify : this.notify,
			refresh : this.refresh
		});
	},
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "添加分类",
			text : Btn_Cfgs.Trad_ADD_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.Trad_INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,self:self});
			}
		},{
			token : "编辑分类",
			text : Btn_Cfgs.Trad_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.Trad_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : "删除分类",
			text : Btn_Cfgs.Trad_DEL_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.Trad_DEL_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.isen="删除分类";
				if(self.appgrid){
					if(self.appgrid.getStore().getCount()>0){
						ExtUtil.warn({msg:"该资源中有数据不能进行删除！"});
						return;
					}
				}
				EventManager.deleteData('./sysGvlist_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
			}
		},{type:"sp"},{
			token : "添加分区",
			text : Btn_Cfgs.Partition_ADD_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.Partition_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,self:self});
			}
		},{
			token : "编辑分区",
			text : Btn_Cfgs.Partition_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.Partition_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : "启用分区",
			text : Btn_Cfgs.Partition_ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.Partition_ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./oaTradArea_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用分区",
			text : Btn_Cfgs.Partition_DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.Partition_DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./oaTradArea_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除分区",
			text : Btn_Cfgs.Partition_DEL_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.Partition_DEL_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./oaTradArea_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
	},
	
	/**
	 *  把 Grid 对象添加到面板上
	 */
		getAppPanel : function(){
			var p=new Ext.Panel({})
		p.add(this.appgrid);
		return p;
		},
		getGrid2:function(){
			var _this=this;
			var structure_1 = [{
		    header: 'ID',
		    name: 'id',
		    hidden:true
			},{
		    header: '可用标志',
		    name: 'isenabled',
		  	renderer : Render_dataSource.isenabledRender
			},
			{
			    header: '分区名称',
			    name: 'name'
			},
			{
			    header: '分区编号',
			    name: 'code'
			},
			{
			    header: '价格等级',
			    renderer:function(val){
			    	return Render_dataSource.gvlistRender(100003,val);
			    },
			    name: 'plevelId'
			},
			{
			    header: '合同单位',
			    name: 'contOrg'
			},
			{
			    header: '正价扣点',
			    name: 'sded'
			},
			{
			    header: '特价扣点',
			    name: 'tded'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '门店分区列表',
			    structure: structure_1,
			    url: './oaTradArea_list.action',
			    width:1000,
			    height:screen.height-315,
			    needPage: true,
			    isLoad: false,
			    keyField: 'id',
			    listeners : {
						rowdblclick : function() {
							//鼠标双击事件
							var selRow = appgrid_1.getSelRow();//获取对象所在的行
							if(selRow!=null){_this.globalMgr.openWin({self : _this,key : '详情'});}
						}
					}
			});
			_this.globalMgr.grd=appgrid_1;
			return appgrid_1;
	
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
		if(this.globalMgr.isen=="删除分类")
			this.apptree.reload();
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	/**
	 *  获取 TreePanel 对象
	 */
	getAppTree : function(){
		var self = this;
		var _apptree = new Ext.ux.tree.MyTree({
			url:'./sysTree_sortList.action',
			isLoad : true,
			width:200,
			params:{restypeId:'100002'},
			enableDD:false,
			autoScroll :true
		});
		_apptree.expandAll(); 
		_apptree.addListener('click',function(node){
			var id = node.id;
			var tradId = -1;
			if(id.indexOf('G') != -1){
				tradId = id.substring(1);
			}
			self.globalMgr.params.tradId=tradId;
			EventManager.query(self.appgrid,{tradId:tradId});
		});
		return _apptree;
	},
	globalMgr : {
		grd:null,
		isen:null,
		params:{},
		openWin:function(cfg){
			var fileName=null;
			var key = cfg.key;
			var appGrid=cfg.self.appgrid;
			cfg.parent=appGrid ;
			cfg.tree=cfg.self.apptree;
			if(key =="添加分类"||key =="编辑分类"){
				fileName="TradGvlistEdit";
				if(key =="编辑分类"){
					var selId=cfg.tree.getSelId();
					if(!selId)return;
				}
			}else if(key =="详情"){
				cfg.parent=cfg.self.globalMgr.grd;
				fileName="TradAreaDetail";
			}else{
				fileName="TradAreaEdit";
				if(key =="编辑分区"){
					var sid=appGrid.getSelId();
					if(!sid)return;
				}
				var selId=cfg.tree.getSelId();
				var selText=cfg.tree.getSelText();
				cfg.selText=selText;
				if(!selId){
					ExtUtil.alert({msg:"没有选中数据节点！"});
					return;
				}else{
					selId=(selId.indexOf("G")!=-1)?selId.substring(1):selId;
					cfg.selId=selId;
				}
			}
			Cmw.importPackage('pages/app/sys/basis/tard/'+fileName,function(module) {
				module.WinEdit.show(cfg);
			});
		},
		
		/**
		 * 查询方法
		 * 
		 * @param {}
		 *            _this
		 */
		query : function(_this) {
			EventManager.query(_this.globalMgr.grd,_this.globalMgr.params);
		}
	}
});

