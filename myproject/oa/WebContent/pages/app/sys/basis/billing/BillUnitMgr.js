/**
 * 九洲科技公司产品 命名空间 
 * 联系电话：18064179050 程明卫[系统设计师]
 * 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 基础数据 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.BillUnitMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.BillUnitMgr,Ext.util.MyObservable,{
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
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
 		var txt_name = FormUtil.getTxtField({fieldLabel : '开票单位名称',name:'name'});
		var cbo_custType = FormUtil.getLCboField({
			    fieldLabel: '客户类别',
			    name: 'custType',
			    "width": 125,
			    "maxLength": "30",
			    "data": [["", "全部"],["1", "商超"], ["2", "独立店"], ["3", "加盟店"]]
			});

		 var layout_fields = [{cmns:'TWO',fields:[txt_name,cbo_custType]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	/**
	 * 菜单栏
	 * @return {}
	 */
		getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
			token : "查询",
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self,true);
			}
		},{
			token : "重置",
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				self.queryFrm.reset();
			}
		},{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:Btn_Cfgs.INSERT_BTN_TXT,self:self});
			}
		},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:Btn_Cfgs.MODIFY_BTN_TXT,optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{type:"sp"},{
			token : "启用",
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./oaBillUnit_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用",
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./oaBillUnit_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./oaBillUnit_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
		getAppGrid : function(){
			var structure_1 = [{
			    header: '可用标志',
			    name: 'isenabled',
			    renderer: Render_dataSource.isenabledRender
			},
			{
			    header: 'ID',
			    name: 'id',
			    hidden:true
			},{
			    header: '单位编号',
			    name: 'code'
			},
			{
			    header: '单位名称',
			    name: 'name'
			},
			{
			    header: '客户类别',
			    name: 'custType',
			    renderer:function(val){
			    	if(val==1){
			    		return "商超";			    		
			    	}else if(val==2){
			    		return "独立店";			    		
			    	}else if(val==3){
			    		return "加盟店";
			    	}
			    }
			},
			{
			    header: '结帐方式',
			    name: 'settleType'
			},
			{
			    header: '预回款日',
			    name: 'comeDays'
			},
			{
			    header: '销售方式',
			    renderer:Render_dataSource.sale_type_render,
			    name: 'saleType'
			},
			{
			    header: '价格等级',
			    renderer:function(val){
			    	return Render_dataSource.gvlistRender(100003,val);
			    },
			    name: 'plevelId'
			},
			{
			    header: '联系人1',
			    name: 'cman1'
			},
			{
			    header: '联系电话1',
			    name: 'ctel1'
			},
			{
			    header: '合同单位',
			    name: 'contOrg'
			},
			{
			    header: '地址',
			    name: 'address'
			}];
			var _this=this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    tbar:_this.getToolBar(),
			    url: './oaBillUnit_list.action',
			    needPage: true,
			    isLoad: true,
			    keyField: 'id'
			});
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
				parentCfg.parent = _this.appgrid;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
						if(winkey == Btn_Cfgs.MODIFY_BTN_TXT){
							var selId = _this.appgrid.getSelId();
							if(winkey == Btn_Cfgs.MODIFY_BTN_TXT && !selId ){
								ExtUtil.alert({msg:"请选择表格中的数据！！！"}); return ;
							}
						}
						winModule="BillUnitEdit";
					Cmw.importPackage('pages/app/sys/basis/billing/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

