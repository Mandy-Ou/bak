Ext.namespace("cmw.skythink");
cmw.skythink.WorkLogMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.WorkLogMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : params,
			hasTopSys : false,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			appCmpts :{}
		});
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var self =this;
		var txt_orgtype = FormUtil.getLCboField({
		    fieldLabel: '费用所属组织类型',
		    name: 'orgtype',
		    "width": 125,
		    data:[["","所有"],["1","部门"],["2","门店"]]
		   /* items : [{
		    	"boxLabel" : "出差",
				"name" : "orgtype",
				"inputValue" : "1"
		    },{
		    	"boxLabel" : "其他",
				"name" : "orgtype",
				"inputValue" : "2"
		    }]*/
		});
		
		var txt_xstatus = FormUtil.getLCboField({
		    fieldLabel: '费用报销状态',
		    data:[["","所有"],["1","未报销"],["2","部分报销"],["3","已报销"]],
		    name: 'xstatus'
		});
		
		var txt_affairType = FormUtil.getLCboField({
		    fieldLabel: '事务类型',
		    name: 'affairType',
		    data:[["","所有"],["1","出差"],["2","其他"]],
		    "width": 125
		});
		
		var layout_fields = [{
		    cmns: FormUtil.CMN_THREE,
		    fields: [txt_orgtype, txt_xstatus, txt_affairType]
		}];
		var frm_cfg = {
		    url: './oaLogistics_save.action',
		    labelWidth : 150
		};
		var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
		return appform_1;
	},
	/**
	 * 菜单栏
	 * @return {}
	 */
		getToolBar : function(){
		var self = this;
		var _self = this;
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
				toolBar.resets();
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
				self.globalMgr.winEdit.show({key:Btn_Cfgs.MODIFY_BTN_TXT,optionType:OPTION_TYPE.EDIT,self:self,selId :_self.globalMgr.appgrid.getSelId(),row :_self.globalMgr.appgrid.getSelRow()});
			}
		},{type:"sp"},{
			token : "启用",
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./oaWorkLog_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : "禁用",
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./oaWorkLog_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : "删除",
				text : Btn_Cfgs.DELETE_BTN_TXT,
				iconCls : 'page_delete',
				tooltip : Btn_Cfgs.DELETE_TIP_BTN_TXT,
				key : Btn_Cfgs.DELETE_FASTKEY,
				handler : function() {
				EventManager.deleteData('./oaWorkLog_delete.action', {type : 'grid',cmpt : self.appgrid,
					optionType : OPTION_TYPE.DEL,self : self
				});
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
			    header: '可用标识',
			    name: 'isenabled',
			    renderer : Render_dataSource.isenabledRender
			},
			{
			    header: '日期范围',
			    name: 'startAndEndDate',
			    width : 150,
			    renderer :function(value, cellmeta, record){
			    	for(var i in record){
//			    		Cmw.print(record[i]);
			    	}
			    	value = record.get('startDate')+"至"+record.get('endDate');
			    	return value;
			    }
			    
			},
			{
				header: '事务类型',
			    name: 'affairType',
			    renderer : function(val) {
						switch (val) {
							case "1" :
								val = "出差";
								break;
							case "2" :
								val = "其他";
								break;
						}
						return val;
					}
			},
			{
			    header: '费用项',
			    name: 'citemId'
			},
			{
			    header: '费用金额',
			    name: 'amount'
			},
			{
			    header: '费用报销状态',
			    name: 'xstatus',
			    renderer : function(val) {
						switch (val) {
							case "1" :
								val = "未报销";
								break;
							case "2" :
								val = "部分报销";
								break;
							case "3" :
								val = "已报销";
								break;
						}
						return val;
				}
			},
			{
			    header: '费用所属部门',
			    name: 'inorgId'
			},
			{
			    header: '事由',
			    name: 'reason',
			    width : 200
			},{
			    header: '起始时间',
			    name: 'startDate',
			    format : 'm-d',
			    width : 200,
			    hidden : true
			},{
			    header: '截止时间',
			    name: 'endDate',
			    format : 'm-d',
			    width : 200,
			    hidden : true
			},{
			    header: '费用所属部门的id',
			    name: 'inorg',
			    hidden :true
			}];
			var _this=this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '工作日志',
			    tbar:_this.getToolBar(),
			    structure: structure_1,
			    url: './oaWorkLog_list.action',
			    needPage: true,
			    isLoad: true,
			    keyField: 'id'
			});
			_this.globalMgr.appgrid = appgrid_1;
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
		var _self = this;
		EventManager.query(_self.appgrid, null);
	},
	
	globalMgr : {
		appgrid : null ,
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
				parentCfg.selId = parentCfg.selId;
				parentCfg.row = parentCfg.row;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
						if(winkey == Btn_Cfgs.MODIFY_BTN_TXT){
							if(winkey == Btn_Cfgs.MODIFY_BTN_TXT && !parentCfg.selId ){
								ExtUtil.alert({msg:"请选择表格中的数据！！！"});return ; 
							}
						}
					winModule="WorkLogEdit";
					Cmw.importPackage('pages/app/oa/todo/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

