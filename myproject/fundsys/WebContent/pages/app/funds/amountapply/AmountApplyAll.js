/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 展期一览表 UI smartplatform_auto 2013-09-24 16:19:16
 */ 
cmw.skythink.AuditExtensionAll = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.AuditExtensionAll,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			id : ComptIdMgr.auditExtensionMgrId,
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
			
		
		var cbo_eqopAmount = FormUtil.getEqOpLCbox({name:'eqopAmount'});
		var txt_endAmount = FormUtil.getMoneyField({
		    fieldLabel: '贷款金额',
		    name:'endAmount',
		    width:70
		});
		var comp_appAmount = FormUtil.getMyCompositeField({
			 fieldLabel: '贷款金额',width:150,sigins:null,itemNames:'eqopAmount,endAmount',
			 name:'comp_appAmount',
			 items : [cbo_eqopAmount,txt_endAmount]
		});
		var txt_startDate1 = FormUtil.getDateField({name:'startDate1',width:90});
		var txt_endDate1 = FormUtil.getDateField({name:'endDate1',width:90});
		var comp_estartDate = FormUtil.getMyCompositeField({
			itemNames : 'startDate1,endDate1',
			sigins : null,
			 fieldLabel: '展期起始日',width:210,sigins:null,
			 name:'comp_estartDate',
			 items : [txt_startDate1,{xtype:'displayfield',value:'至'},txt_endDate1]
		});
		
		var txt_startDate2 = FormUtil.getDateField({name:'startDate2',width:90});
		var txt_endDate2 = FormUtil.getDateField({name:'endDate2',width:90});
		var comp_eendDate = FormUtil.getMyCompositeField({
			itemNames : 'startDate2,endDate2',
			sigins : null,
			 fieldLabel: '展期截止日',width:210,sigins:null,
			 name:'comp_estartDate',
			 items : [txt_startDate2,{xtype:'displayfield',value:'至'},txt_endDate2]
		});
		
		
		var cbo_eqextAmount = FormUtil.getEqOpLCbox({name:'eqextAmount'});
		var txt_endAmount = FormUtil.getMoneyField({
		    fieldLabel: '展期金额',
		    name:'extAmount',
		    width:70
		});
		var comp_extAmount = FormUtil.getMyCompositeField({
			 fieldLabel: '展期金额',width:150,sigins:null,itemNames:'eqextAmount,extAmount',
			 name:'comp_extAmount',
			 items : [cbo_eqextAmount,txt_endAmount]
		});
		
		
		var layout_fields = [{cmns:'THREE',fields:[cbo_custType,txt_custName,comp_appAmount,comp_estartDate,comp_eendDate,comp_extAmount]}]

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
		},{type:"sp"},{/*审批*/
			token : '查看详情',
			text : Btn_Cfgs.AUDIT_LOOK_DETAIL_BTN_TXT,
			iconCls:Btn_Cfgs.DETAIL_CLS,
			tooltip:Btn_Cfgs.AUDIT_LOOK_DETAIL_TIP_TXT,
			handler : function(){
				_this.globalMgr.submitApplyForm(_this);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){var _this = this;
				var structure_1 = [{
					header : 'entrustCustId',
					name : 'entrustCustId',
					hidden : true
				},{
					header : '状态',
					name : 'status',
					width : 52,
					renderer : function(val) {
						return Render_dataSource.entrusIsenRender(val);
					}
				}, {
					header : '合同编号',
					name : 'code'
				}, {
					header : '收款银行',
					name : 'payBank'
				}, {
					header : '收款账号',
					name : 'payAccount'
				}, {
					header : '账户名',
					name : 'accName'
				}, {
					header : '委托金额',
					name : 'appAmount',
			renderer: function(val) {
		    return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';}
				}, {
					header : '委托产品',
					name : 'productsId'
				}, {
					header : '合同签订日期',
					name : 'doDate'
				}, {
					header : '委托生效日期',
					name : 'payDate'
				}, {
					header : '委托失效日期',
					name : 'endDate'
				}];
				var appgrid = new Ext.ux.grid.AppGrid({// 创建表格
					title : '增资申请一览表',
					tbar : this.toolBar,
					structure : structure_1,
				    url: './fuAmountApply_auditAll.action',
					needPage : true,
					keyField : 'id',
					isLoad : false,
					listeners : {
						render : function(grid) {
							_this.globalMgr.query(_this);
						}
					}
				});
				return appgrid;

			},
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		if(!this.appgrid) return;
		if(this.appgrid && this.appgrid.rendered){
			this.globalMgr.query(this);
			this.globalMgr.activeKey = null;
		}else{
			var _this = this;
			this.appgrid.addListener('render',function(){_this.globalMgr.query(_this); alert("render");});
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
			var tabId = CUSTTAB_ID.extensionApplyTabId;
			var apptabtreewinId = _this.params["apptabtreewinId"];
			Cmw.activeTab(apptabtreewinId,tabId,params);
		},
		/**
		 * 提交申请单	
		 * @param {} _this
		 */
		submitApplyForm : function(_this){
			var applyId = _this.appgrid.getSelId();
			if(!applyId) return;
			var codeObj = _this.appgrid.getCmnVals("code,contractId,entrustCustId");
			var sysId = _this.params.sysid;
			var contractId = codeObj.contractId;
			var entrustCustId = codeObj.entrustCustId;
			 ExtUtil.confirm({title:'提示',msg:'确定审批编号为："'+codeObj.code+'"的增资申请单?',fn:function(){
		 	  		if(arguments && arguments[0] != 'yes') return;
		 	  		var params = {entrustCustId:entrustCustId,isnewInstance:true,sysId:sysId,applyId:applyId,contractId:contractId,procId:null,bussProccCode:'B0001'};
					tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
					url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
					var title =  '展期业务审批';
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

