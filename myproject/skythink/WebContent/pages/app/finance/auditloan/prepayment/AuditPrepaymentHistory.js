/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 提前还款审批历史记录 UI smartplatform_auto 2013-09-13 14:58:16
 */ 
cmw.skythink.AuditPrepaymentHistory = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.AuditPrepaymentHistory,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			id : ComptIdMgr.AuditPrepaymentMgrId,
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
	
		var cbo_custType = FormUtil.getLCboField({
		    fieldLabel: '客户类型',
		    name: 'custType',
		    width : 150,
		    data : Lcbo_dataSource.getAllDs(Lcbo_dataSource.custType_datas)
		});
		
		var txt_custName = FormUtil.getTxtField({fieldLabel : '客户姓名',name:'custName',width : 150});
			
		
		var cbo_ptype = FormUtil.getLCboField({
		    fieldLabel: '提前还款类别',
		    name: 'ptype',
		    "width": 125,
		    data : Lcbo_dataSource.getAllDs(Lcbo_dataSource.prepayment_ptype_datas)
		});
			
		var txt_startDate1 = FormUtil.getDateField({name:'startDate1',width:90});
		var txt_endDate1 = FormUtil.getDateField({name:'endDate1',width:90});
		var comp_adDate = FormUtil.getMyCompositeField({
			itemNames : 'startDate1,endDate1',
			sigins : null,
			 fieldLabel: '提前还款日期',width:210,sigins:null,
			 name:'comp_adDate',
			 items : [txt_startDate1,{xtype:'displayfield',value:'至'},txt_endDate1]
		});
		
		var txt_appMan = FormUtil.getTxtField({
		    fieldLabel: '申请人',
		    name: 'appMan',
		    "width": 125
		});
			
		var txt_startDate2 = FormUtil.getDateField({name:'startDate2',width:90});
		var txt_endDate2 = FormUtil.getDateField({name:'endDate2',width:90});
		var comp_appDate = FormUtil.getMyCompositeField({
			itemNames : 'startDate2,endDate2',
			sigins : null,
			 fieldLabel: '申请日期',width:210,sigins:null,
			 name:'comp_appDate',
			 items : [txt_startDate2,{xtype:'displayfield',value:'至'},txt_endDate2]
		});
		
		
		var layout_fields = [{cmns:'THREE',fields:[cbo_custType,txt_custName,cbo_ptype,comp_adDate,txt_appMan,comp_appDate]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var _this = this;
		var toolBar = null;
		var barItems = [{
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.query(_this);
			}
		},{
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				_this.queryFrm.reset();
			}
		}
		,{type:"sp"},{/*审批*/
			token : '查看详情',
			text : Btn_Cfgs.AUDIT_LOOK_DETAIL_BTN_TXT,
			iconCls:Btn_Cfgs.DETAIL_CLS,
			tooltip:Btn_Cfgs.AUDIT_LOOK_DETAIL_TIP_TXT,
			handler : function(){
				_this.globalMgr.submitApplyForm(_this);
			}
		}];
//		,{
//			token : '导出',
//			text : Btn_Cfgs.EXPORT_BTN_TXT,
//			iconCls:Btn_Cfgs.EXPORT_CLS,
//			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
//			handler : function(){
//				_this.globalMgr.doExport(_this);
//			}
//		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
			var structure_1 = [
			{
			    header: '提前还款申请单编号',
			    name: 'code',
			    width: 135
			},
			{
			    header: '客户类型',
			    name: 'custType',
			    width: 65,
			    renderer: function(val) {
			     return	Render_dataSource.custTypeRender(val);
			    }
			},
			{
			    header: '客户名称',
			    name: 'custName'
			},
			{
			    header: '证件类型',
			    name: 'cardType',
			     width: 65
			},
			{
			    header: '证件号码',
			    name: 'cardNum',
			     width: 135
			},
			{
			    header: '借款合同号',
			    name: 'loanCode',
			    width: 135
			},
			{
			    header: '提前还款类别',
			    name: 'ptype',
			    renderer : Render_dataSource.prepayment_ptypeRender
			},
			{
			    header: '提前还款日期',
			    name: 'adDate'
			},
			{
			    header: '手续费率',
			    name: 'frate',
			    renderer: function(val) {
			        return val ? val+'%' : "";
			    }
			},
			{
			    header: '提前还款手续费',
			    name: 'freeamount',
			    renderer: function(val) {
			     return Cmw.getThousandths(val)+"元";
			    }
			},
			{
			    header: '部分提前还款方式',
			    name: 'treatment',
			    renderer: Render_dataSource.prepayment_treatmentRender
			},
			{
			    header: '部分提前还款额',
			    name: 'adamount',
			    renderer: function(val) {
			     return Cmw.getThousandths(val)+"元";
			    }
			},
			{
			    header: '是否退息费',
			    name: 'isretreat',
			    renderer: Render_dataSource.prepayment_isretreatRender
			},
			{
			    header: '退息费金额',
			    name: 'imamount',
			    renderer: function(val) {
			     return Cmw.getThousandths(val)+"元";
			    }
			},
			{
			    header: '提前还款额合计',
			    name: 'totalAmount',
			    renderer: function(val) {
			     return Cmw.getThousandths(val)+"元";
			    }
			},
			{
			    header: '办理日期',
			    name: 'mgrDate'
			},
			{
			    header: '经办人',
			    name: 'managerName'
			},
			{
			    header: '申请日期',
			    name: 'appDate'
			},
			{
			    header: '申请人',
			    name: 'appMan'
			},
			{
			    header: '申请人联系电话',
			    name: 'tel'
			},
			{
			    header: '借款合同ID',
			    name: 'contractId',
			    width: 100,
			    hidden: true
			},
			{
			    header: '流程实例ID',
			    name: 'procId',
			    width: 100,
			    hidden:  true
			}];
			
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar :this.toolBar,
			    structure: structure_1,
			    url: './fcPrepayment_auditHistory.action',
			    needPage: true,
			    keyField: 'id',
			    width:600,
			    isLoad: false,
			    listeners : {
				   	render : function(grid){
				   	 	_this.globalMgr.query(_this);
				   	}
			    }
			});
		return appgrid_1;
	},
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		if(!this.appgrid) return;
		if(this.appgrid && this.appgrid.rendered){
			this.globalMgr.query(this);
			this.globalMgr.activeKey = null;
		}else{
			var _this = this;
			this.appgrid.addListener('render',function(){_this.globalMgr.query(_this);});
		}
	},
	
	globalMgr : {
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
			/*-- 附加桌面传递的参数  CODE START --*/
			if(_this.params && _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY]){
			 	var deskParams = _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY];
			 	if(deskParams){
			 		Ext.applyIf(params,deskParams);
			 		 _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY] = null;
			 	}
			}/*-- 附加桌面传递的参数  CODE END --*/
			return params;
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = this.getQparams(_this);
			EventManager.query(_this.appgrid,params);
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
		 * 添加/编辑申请单	
		 * @param {} _this
		 */
		doApplyByOp : function(_this,op){
			var params = {applyId:null,optionType:op};
			if(op == OPTION_TYPE.EDIT){//编辑 时传入申请单ID，客户ID，客户类型
				var applyId = _this.appgrid.getSelId();
				if(!applyId) return;
				var data = _this.appgrid.getCmnVals(["contractId"]);
				data["applyId"] = applyId;
				params = Ext.apply(params,data);
			}
			var tabId = CUSTTAB_ID.prepaymentApplyTabId;
			var apptabtreewinId = _this.params["apptabtreewinId"];
			Cmw.activeTab(apptabtreewinId,tabId,params);
		},
		/**
		 * 提交申请单	
		 * @param {} _this
		 */
		submitApplyForm : function(_this){
			var applyId = _this.appgrid.getSelId();
			var sysId = _this.params.sysId;
			if(!applyId) return;
			var codeObj = _this.appgrid.getCmnVals("code");
			 ExtUtil.confirm({title:'提示',msg:'确定提交编号为："'+codeObj.code+'"的提前还款申请单?',fn:function(){
		 	  		if(arguments && arguments[0] != 'yes') return;
		 	  		var contractId = _this.appgrid.getCmnVals(["contractId"]).contractId;
					var params = {isnewInstance:true,sysId:sysId,applyId:applyId,contractId:contractId,procId:null,bussProccCode:'B0002'};
					tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
					url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
					var title =  '提前还款业务审批';
					var apptabtreewinId = _this.params["apptabtreewinId"];
					Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
			 }});
		},
		/**
		 * 申请单详情
		 * @type 
		 */
		
		detailApplyForm : function(_this){
//			var applyId = _this.appgrid.getSelId();
//			if(!applyId) {
//				return;
//			}
//			var data = _this.appgrid.getCmnVals(["contractId"]);
//			data["applyId"] = applyId;
//			params = data;
//			params.dispaly  = false;
//			var tabId = CUSTTAB_ID.detailApplyFormTabId.id;
//			var url =  CUSTTAB_ID.detailApplyFormTabId.url;
//			var title =  '个人客户申请单详情';
//			var apptabtreewinId = _this.params["apptabtreewinId"];
//			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null
	}
});

