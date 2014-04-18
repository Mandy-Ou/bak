/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 基础数据 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.EcustomerMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.EcustomerMgr,Ext.util.MyObservable,{
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
		var Hid_custType = FormUtil.getHidField({fieldLabel : '客户类型',name:'custType',maxLength : 50,value:1});
		var txt_name = FormUtil.getTxtField({fieldLabel : '企业名称',name:'name',maxLength : 50});
		var txt_code = FormUtil.getTxtField({fieldLabel : '客户编码',name:'code',maxLength : 20});
		var txt_custLevel = FormUtil.getLCboField({fieldLabel : '客户级别',name:'custLevel', data: Lcbo_dataSource.custLevel_dates});
		var txt_tradNumber = FormUtil.getTxtField({fieldLabel : '营业执照号',name:'tradNumber',maxLength : 20});
 		var txt_phone = FormUtil.getTxtField({fieldLabel : '联系人手机',name:'phone',maxLength : 15});
 		var txt_contacttel = FormUtil.getTxtField({fieldLabel : '联系人电话',name:'contacttel',maxLength : 15});
		var layout_fields = [{cmns:'THREE',fields:[Hid_custType,txt_name,txt_code,txt_custLevel,txt_tradNumber,txt_phone,txt_contacttel]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
			type : 'label',text : '<span style="color:#94d6da;font-weight:bold;">[提示：为绿色的数据表示已同步到财务系统！]</span>'
		},{
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},{
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				self.queryFrm.reset("custType");
			}
		},{type:"sp"},{
			token : '添加',
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler:function(){
				self.globalMgr.doApplyByOp(self,OPTION_TYPE.ADD);
			}
		},{
			token : '编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.doApplyByOp(self,OPTION_TYPE.EDIT);
			}
		},{
			token : '详情',
			text : Btn_Cfgs.DETAIL_BTN_TXT,
			iconCls:Btn_Cfgs.DETAIL_CLS,
			tooltip:Btn_Cfgs.DETAIL_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.viewEcustomerDetail(self);
			}
		},{/*同步*/
			token : '同步',
			text : Btn_Cfgs.SYNCHRONOUS_BTN_TXT,
			iconCls:Btn_Cfgs.SYNCHRONOUS_CLS,
			tooltip:Btn_Cfgs.SYNCHRONOUS_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.synchronousData(self);
			}
		},{type:"sp"},{
			token : '启用',
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.enabledData('./crmCustBase_enabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.ENABLED,self:self});
			}
		},{
			token : '禁用',
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls:'page_disabled',
			tooltip:Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			handler : function(){
				EventManager.disabledData('./crmCustBase_disabled.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DISABLED,self:self});
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./crmCustBase_delete.action',{type:'grid',cmpt:self.appgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		},{
			token : '导入',
			text : Btn_Cfgs.EXPORT_IMPORT_BTN_TXT,
			iconCls:'page_detail',
			tooltip:Btn_Cfgs.EXPORT_TIP_IMPORT_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:"导入",optionType:OPTION_TYPE.EDIT,self:self});
			}
		},{
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:'page_exportxls',
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.doExport(self);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		toolBar.hideButtons(Btn_Cfgs.EXPORT_IMPORT_BTN_TXT);
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var self = this;
			var structure_1 = [
			{
				header : '可用标识',name : 'isenabled',width:60,renderer :Render_dataSource.isenabledRender
			},{
			    header: '系统ID',
			    hidden :true,
			    name: 'sysId'
			},
			{
			    header: '流水号',
			    width:145,
			    name: 'serialNum'
			},
			{
			    header: 'ecustomerId',
			    hidden :true,
			    name: 'ecustomerId'
			},
			{
			    header: '客户级别',
			    name: 'custLevel',
			    width:80,
			    renderer : Render_dataSource.custLevelRender
			},
			{
			    header: '客户编号',
			    width : 145,
			    name: 'code'
			},
			{
			    header: '企业名称',
			    width:180,
			    name: 'name'
			},
			{
			    header: '营业执照号',
			    name: 'tradNumber'
			},
			{
			    header: '组织机构编码',
			    name: 'orgcode'
			},
			{
			    header: '所属行业',
			    width:80,
			    name: 'trade'
//			    ,
//			    renderer : function(val){
//			    	return Render_dataSource.gvlistRender('100011',val);
//			    }
			},
			{
			    header: '企业性质',
			    name: 'kind',
			    width:80
//			    ,
//			    renderer : function(val){
//			    	return Render_dataSource.gvlistRender('100009',val);
//			    }
			},
			{
			    header: '联系人',
			    width:60,
			    name: 'contactor'
			},
			{
			    header: '联系人手机',
			    name: 'phone'
			},
			{
			    header: '联系电话',
			    name: 'contactTel'
			},
			{
			    header: '登记人',
			    name: 'regman'
			},
			{
			    header: '登记时间',
			    name: 'registerTime',
			    width : 125
			 },{
			 	header: '财务关联Id',
			    name: 'refId',
			    renderer : function(val,metaData, record, rowIndex, cellIndex, store){
			    	renderColor(val,record,rowIndex,store);
		    		return val;
			    }
			 }];
		function renderColor(val,record,rowIndex,store){
			var task = new Ext.util.DelayedTask(function(){
				var view = appgrid.getView();
				var row = view.getRow(rowIndex);
				if(val){
					row.style.backgroundColor="#94d6da";
				}
			},this);
			task.delay(100);
		}	
		
			var appgrid = new Ext.ux.grid.AppGrid({
				tbar :self.toolBar,
			    structure: structure_1,
			    url: './crmCustBase_list.action?custType='+1,
			    needPage: true,
			    isLoad: true,
			    autoScroll:true,
			    keyField: 'id'
			});
		return appgrid;
	},
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		appform : null,	
		activeKey : null,
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
			return params;
		},
		/**
		 * 导出Excel
		 * @param {} _this
		 */
		doExport : function(_this){
			var params = this.getQparams(_this);
			var token = _this.params.nodeId;
			EventManager.doExport(token,params);
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
		doApplyByOp : function(_this,op){
			var params = {customerId:null,baseId:null};
			if(op == OPTION_TYPE.EDIT){//编辑 时传入申请单ID，客户ID，客户类型
				var baseId = _this.appgrid.getSelId();
				if(!baseId) return;
				var data = _this.appgrid.getCmnVals(["ecustomerId","baseId","code","name","serialNum","tradNumber","orgcode","registerTime","regman"]);
				if(!data["baseId"]) 
				data["baseId"] = baseId;
				params = data;
			}
			params.isnewInstance = true;
			params.appgrid = _this.appgrid;
			var tabId = CUSTTAB_ID.ecustomerInfoEditTabId;
			var apptabtreewinId = _this.params["apptabtreewinId"];
			Cmw.activeTab(apptabtreewinId,tabId,params);
		},
		/**
		 * 查看企业客户详情
		 * @param {} _this
		 */
		viewEcustomerDetail : function(_this){
			var baseId = _this.appgrid.getSelId();
			var data = _this.appgrid.getCmnVals(["ecustomerId"]);
			var params = {ecustomerInfoId:baseId,ecustomerId:data.ecustomerId,baseId:baseId};
			var tabId = CUSTTAB_ID.ecustomerInfoDetailTab.id;
			var url =  CUSTTAB_ID.ecustomerInfoDetailTab.url;
			params.tab = _this.tab;
			params.dispaly  = false;
			params.isnewInstance = true;
			var title =  '企业客户详情';
			var apptabtreewinId = _this.params["apptabtreewinId"];
			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
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
			 		var sysId = _this.params.sysid;
					EventManager.get('./crmCustBase_synchronous.action',{params:{sysId:sysId,custType:Buss_Constant.CustType_1},
					 sfn:function(json_data){
						_this.globalMgr.query(_this);
					 	Cmw.unmask(_this);
					 	ExtUtil.alert({msg:Fs_Msg_SysTip.msg_synchronousSuccess});
					 },ffn:function(json_data){
					 	Cmw.unmask(_this);
					 	var msg = json_data.msg || Fs_Msg_SysTip.msg_synchronousFailure;
					 	ExtUtil.alert({msg:msg});
					 }});
			 }});
		}
	}
		
});
