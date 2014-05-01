/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 资金追加暂存申请单 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.TempAddApplyMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.TempAddApplyMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			id : ComptIdMgr.tempCustApplyMgrId,
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
		var txt_name = FormUtil.getTxtField({fieldLabel : '客户姓名',name:'name',width : 150});
		
		var txt_loanCode = FormUtil.getTxtField({fieldLabel : '借款合同编号',name:'loanCode',width : 150});
		
		var date_bdate = FormUtil.getDateField({
		    fieldLabel: '原借款日期',
		    name : 'bdate',
		    width : 150
		});
		
		var mon_amount = FormUtil.getMoneyField({
		    fieldLabel: '原借款金额',
		    name: 'amount',
		    width: 150
		});
		
		var mon_bamount = FormUtil.getMoneyField({
		    fieldLabel: '追加金额',
		    name: 'bamount',
		    width: 150
		});
		
		var mon_tamount = FormUtil.getTxtField({
		    fieldLabel: '合计金额',
		    name: 'tamount',
		     width: 150
		});
		
		var date_appDate = FormUtil.getDateField({
		    fieldLabel: '申请日期',
		    name : 'appDate',
		    width : 150
		});
		
		var layout_fields = [{cmns:'THREE',fields:[txt_name,txt_loanCode,mon_amount,mon_bamount,mon_tamount,date_appDate]}]

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
		},{type:"sp"},{/*添加*/
			token : '添加',
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:Btn_Cfgs.INSERT_CLS,
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doApplyByOp(_this,OPTION_TYPE.ADD);
			}
		},{/*编辑*/
			token : '编辑',
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:Btn_Cfgs.MODIFY_CLS,
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doApplyByOp(_this,OPTION_TYPE.EDIT);
			}
		},{
			token : '详情',
			text : Btn_Cfgs.DETAIL_BTN_TXT,
			iconCls:Btn_Cfgs.DETAIL_CLS,
			tooltip:Btn_Cfgs.DETAIL_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.detailApplyForm(_this);
			}
		},{type:"sp"},{/*提交*/
			token : '提交',
			text : Btn_Cfgs.SUBMIT_BTN_TXT,
			iconCls:Btn_Cfgs.SUBMIT_CLS,
			tooltip:Btn_Cfgs.SUBMIT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.submitApplyForm(_this);
			}
		},{
			token : '删除',
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./fuAmountProof_delete.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.DEL,self:_this});
			}
		},{
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:Btn_Cfgs.EXPORT_CLS,
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doExport(_this);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
			var structure_1 = [
			{header: '客户编号',name: 'code', width: 130},
			{header: '状态', name: 'status' ,width: 65,renderer:Render_dataSource.entrusStateRender},
			{header: '客户类型',name: 'custType',renderer:Render_dataSource.custTypeRender},
			{header: '客户ID',name: 'customerId',hidden: true},
			{header: '借款合同ID',name: 'contractId',hidden: true},
//			{header: '客户编号',name: 'custCode', width: 100},
			{header: '客户名称',name: 'custName'},
			{header: '借款合同编号',name: 'loanCode', width: 130},
			{header: '原借款金额',	name: 'amount' ,width: 100,
				renderer: function(val) {
			       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
			    }
		    },
			{header: '原借款日期',name: 'bdate'},
			{header: '追加金额',name: 'bamount',
				renderer: function(val) {
		       		return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
		    	}
			},
			{header: '申请人',name: 'appManName'},
			{header: '申请日期', name: 'appDate'}
			];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar :this.toolBar,
			    structure: structure_1,
			    url: './fuAmountProof_list.action',
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
			this.tab.notify = function(_tab){
				_this.globalMgr.query(_this);
			};
		return appgrid_1;
		
	},
	
	refresh:function(optionType,data){
		var activeKey = this.globalMgr.activeKey;
		
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	
	globalMgr : {
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
			params.custType = 0;
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
			params.userId = CURRENT_USERID;
			var token = _this.params.nodeId;
			params.userId = CURRENT_USERID;
			Cmw.print(params);
			EventManager.doExport(token,params);
		},
		/**
		 * 添加/编辑申请单	
		 * @param {} _this
		 */
		doApplyByOp : function(_this,op){
			var params = {applyId:null,customerId:null,custType:null,appGrid:_this.appgrid};
			if(op == OPTION_TYPE.EDIT){//编辑 时传入申请单ID，客户ID，客户类型
				var applyId = _this.appgrid.getSelId();
				if(!applyId) return;
				var data = _this.appgrid.getCmnVals(["id","customerId","custType"]);
				data["applyId"] = applyId;
				data["appGrid"] = _this.appgrid;
				params = data;
			}
			params.op=op;
			params.isnewInstance = true;
			var tabId = CUSTTAB_ID.AmountProofTabId.id;
			var url = CUSTTAB_ID.AmountProofTabId.url;
			var apptabtreewinId = _this.params["apptabtreewinId"];
			var title =  '资金追加申请';
			EventManager.get("./sysMenu_single.action",{params:{tabId:'AmountProofMgr_TabId_31'},
				sfn : function(jsonData){
					var menuId=jsonData.id;
					if(menuId){
						EventManager.get("./sysBussProcc_getClauseList.action",{params:{menuId:menuId},
							sfn : function(json_data){
								var totalSize = json_data.totalSize;
								var list=json_data.list;
								if(!totalSize || totalSize == 0){
						  			ExtUtil.alert({msg:'当前功能没有配置子业务流程，无法进行业务申请!'});
						  			return;
						  		}
						  		var bpData=list[0];
						  		params.breed = bpData.breed;
								params.pdid = bpData.pdid;
								Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
						}});	
					}
				}});	
		},
		/**
		 * 提交申请单	
		 * @param {} _this
		 */
		submitApplyForm : function(_this){
			var applyId = _this.appgrid.getSelId();
			if(!applyId) return;
			var sysId = _this.params.sysid;
			var codeObj = _this.appgrid.getCmnVals("code");
			 ExtUtil.confirm({title:'提示',msg:'确定提交编号为："'+codeObj.code+'"的资金追加申请单?',fn:function(){
		 	  		if(arguments && arguments[0] != 'yes') return;
		 	  		var contractId = _this.appgrid.getCmnVals(["contractId"]).contractId;
		 	  		var params = {sysId:sysId,applyId:applyId,contractId:contractId,procId:null,bussProccCode:'B0005'};
					var tabId = CUSTTAB_ID.bussProcc_auditMainUITab.id;
					var url = CUSTTAB_ID.bussProcc_auditMainUITab.url;
					var title =  '资金追加审批';
					var apptabtreewinId = _this.params["apptabtreewinId"];
					Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
			 }});
		},
		/**
		 * 申请单详情
		 * @type 
		 */
		
		detailApplyForm : function(_this){
			var applyId = _this.appgrid.getSelId();
			if(!applyId) {
				return;
			}
			var data = _this.appgrid.getCmnVals(["id","customerId","custType"]);
			var params = {};
			params = data;
			data["applyId"] = applyId;
			params["isnewInstance"] = true;
			params.dispaly  = false;
			var tabId = CUSTTAB_ID.detailApplyTabId.id;
			var url =  CUSTTAB_ID.detailApplyTabId.url;
			var title =  '资金追加详情';
			var apptabtreewinId = _this.params["apptabtreewinId"];
			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null
	}
});

