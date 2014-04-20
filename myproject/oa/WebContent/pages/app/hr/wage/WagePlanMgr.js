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
			token : "添加工资方案",
			text : Btn_Cfgs.WagePlan_ADD_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.WagePlan_INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,self:self});
			}
		},{
			token : "编辑工资方案",
			text : Btn_Cfgs.WagePlan_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.WagePlan_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : "删除工资方案",
			text : Btn_Cfgs.WagePlan_DEL_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.WagePlan_DEL_TIP_BTN_TXT,
			handler : function(){
				if(self.appgrid){
					if(self.appgrid.getStore().getCount()>0){
						ExtUtil.warn({msg:"该资源中有数据不能进行删除！"});
						return;
					}
				}
				self.globalMgr.activeKey="删除工资方案";
				EventManager.deleteData('./hrWagePlan_delete.action',{type:'tree',cmpt:self.apptree,optionType:OPTION_TYPE.DEL,self:self});
			}
		},{type:"sp"},{
			token : "添加方案项",
			text : Btn_Cfgs.PlanItem_ADD_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.PlanItem_ADD_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,self:self});
			}
		},{
			token : "编辑方案项",
			text : Btn_Cfgs.PlanItem_EDIT_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.PlanItem_EDIT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.openWin({key:this.token,optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : "启用方案项",
			text : Btn_Cfgs.PlanItem_ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.PlanItem_ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./hrPlanItem_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用方案项",
			text : Btn_Cfgs.PlanItem_DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.PlanItem_DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./hrPlanItem_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除方案项",
			text : Btn_Cfgs.PlanItem_DEL_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.PlanItem_DEL_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./hrPlanItem_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
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
			    header: '工资项名称',
			    name: 'name'
			},
			{
			    header: '项目方向',
			    renderer:function(val){
			    	switch(val){
			    		case '1':
			    			return "加项";
			    			break;
			    		case '2':
			    			return "减项"
			    			break;
			    	}
			    },
			    name: 'direction'
			},
			{
			    header: '基本金额',
			    name: 'bamount'
			},
			{
			    header: '备注',
			    name: 'remark'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '门店分区列表',
			    structure: structure_1,
			    url: './hrPlanItem_list.action',
			    width:800,
			    height:screen.height-320,
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
		if(activeKey=="删除工资方案")
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
			url:'./sysTree_wageList.action',
			isLoad : true,
			width:200,
			enableDD:false,
			autoScroll :true
		});
		_apptree.expandAll(); 
		_apptree.addListener('click',function(node){
			var id = node.id;
			var wageId = -1;
			if(id.indexOf('W') != -1){
				wageId = id.substring(1);
			}else{
				wageId = id;
			}
			self.globalMgr.params.wageId=wageId;
			EventManager.query(self.appgrid,{wageId:wageId});
		});
		return _apptree;
	},
	globalMgr : {
		grd:null,
		params:{},
		activeKey:null,
		openWin:function(cfg){
			var fileName=null;
			var key = cfg.key;
			var appGrid=cfg.self.appgrid;
			cfg.parent=appGrid ;
			cfg.tree=cfg.self.apptree;
			if(key =="添加工资方案"||key =="编辑工资方案"){
				fileName="WagePlanEdit";
				if(key =="编辑工资方案"){
					var selId=cfg.tree.getSelId();
					if(!selId)return;
				}
			}else if(key =="详情"){
				cfg.parent=cfg.self.globalMgr.grd;
				fileName="TradAreaDetail";
			}else{
				fileName="WagePlanItemEdit";
				if(key =="编辑方案项"){
					var selId=appGrid.getSelId();
					if(!selId)return;
//					cfg.selId=selId;
				}
				var selId=cfg.tree.getSelId();
				if(!selId){
					ExtUtil.alert({msg:"没有选中数据节点！"});
					return;
				}else{
					selId=(selId.indexOf("W")!=-1)?selId.substring(1):selId;
					cfg.selId=selId;
				}
			}
			Cmw.importPackage('/pages/app/hr/wage/'+fileName,function(module) {
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

