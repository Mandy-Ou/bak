/**
 * 九洲科技公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 个人客户暂存申请单 UI smartplatform_auto 2012-08-10 09:48:16
 */ 
cmw.skythink.TempCustApplyMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.TempCustApplyMgr,Ext.util.MyObservable,{
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
		var cbo_cardType = FormUtil.getRCboField({
		    fieldLabel: '证件类型',
		    name: 'cardType',
		    "width": 150,
		    allDispTxt : Lcbo_dataSource.allDispTxt,
		    register : REGISTER.GvlistDatas,
		    restypeId : '100002'
		    //"url": "./sysGvlist_cbodatas.action?restypeId=100002"
		});
		
		var txt_cardNum = FormUtil.getTxtField({
		    fieldLabel: '证件号码',
		    name : 'cardNum',
		    width : 150
		});
		
		var txt_phone = FormUtil.getTxtField({
		    fieldLabel: '手机',
		    name: 'phone',
		    width: 150
		});
		
		var txt_contactTel = FormUtil.getTxtField({
		    fieldLabel: '联系电话',
		    name: 'contactTel',
		     width: 150
		});
		
		var int_yearLoan = FormUtil.getIntegerField({
		    fieldLabel: '贷款期限(年)',
		    name: 'yearLoan',
		    width: 30
		});
		
		var int_monthLoan = FormUtil.getIntegerField({
		    fieldLabel: '贷款期限(月)',
		    name: 'monthLoan',
		     width: 30
		});
		
		var int_dayLoan = FormUtil.getIntegerField({
		    fieldLabel: '贷款期限(日)',
		    name: 'dayLoan',
		    width: 30
		});
		
		var cbo_eqoploanLimit = FormUtil.getEqOpLCbox({name:'eqopLoanLimit'});
		var comp_loanLimit = FormUtil.getMyCompositeField({
			 fieldLabel: '贷款期限',name:'limitLoan',width:215,sigins:null,
			 items : [cbo_eqoploanLimit,int_yearLoan,
			 	{xtype : 'displayfield',value : '年',width : 6},
			 	int_monthLoan,
			 	{xtype : 'displayfield',value : '月',width : 6},
			 	int_dayLoan,
			 	{xtype : 'displayfield',value : '日',width : 6}
			 	]
		});
		
		var cbo_eqopAmount = FormUtil.getEqOpLCbox({name:'eqopAmount'});
		var txt_appAmount = FormUtil.getMoneyField({
		    fieldLabel: '贷款金额',
		    name:'appAmount',
		    width:70
		});
		var comp_appAmount = FormUtil.getMyCompositeField({
			 fieldLabel: '贷款金额',width:150,sigins:null,
			 name:'comp_appAmount',
			 items : [cbo_eqopAmount,txt_appAmount]
		});
		
		var cbo_payType = FormUtil.getLCboField({
		    fieldLabel: '还款方式',
		    name : 'payType',
		    width : 170,
		    data: Lcbo_dataSource.getAllDs(Lcbo_dataSource.payType_datas)
		});
		
		var txt_breed = new Ext.ux.form.AppComboxImg({//FormUtil.getTxtField({
		    fieldLabel: '业务品种',
		    name: 'breed',
		    "width": 200,
		    valueField : 'id',
			displayField : 'name',
		    url : './sysVariety_cbolist.action',
		    params : {sysId : this.params.sysid},
		    allDispTxt : Lcbo_dataSource.allDispTxt
		});
		
		var layout_fields = [{cmns:'THREE',fields:[txt_name,cbo_cardType,txt_cardNum,txt_phone,txt_contactTel,comp_loanLimit,comp_appAmount,cbo_payType,txt_breed]}]

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
				EventManager.deleteData('./fcApply_delete.action',{type:'grid',cmpt:_this.appgrid,optionType:OPTION_TYPE.DEL,self:_this});
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
			{header: '客户类型',name: 'custType',hidden: true},
			{header: '客户ID',name: 'customerId',hidden: true},
			{header: '项目申请编号',name: 'code', width: 100},
			{header: '客户编号',name: 'custCode', width: 100},
			{header: '客户名称',name: 'name'},
			{header: '证件类型',	name: 'cardType' ,width: 65
		    },
			{header: '证件号码',name: 'cardNum'},
			{header: '手机',name: 'phone'},
			{header: '联系电话',name: 'contactTel'},
			{header: '业务品种', name: 'breed'},
			{header: '贷款金额', name: 'appAmount' ,width: 65,
			 renderer: function(val) {
		       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
		    }},
			{header: '贷款期限',  name: 'loanLimit' ,width: 65
		    },
			{header: '还款方式',  name: 'payType' ,width: 160
		    },
			{header: '利率类型',  name: 'rateType',width: 65,
			 renderer: function(val) {
		       return Render_dataSource.rateTypeRender(val);
		    }
		    },
			{header: '贷款利率',  name: 'rate' ,width: 65,
			 	renderer: function(val) {
		       		return val ? val+'%' : '';
		    	}
		    },
			{header: '期限种类',  name: 'limitType',width: 65
		    },
			{header: '贷款方式',  name: 'loanType',width: 65
		    },
			{header: '行业分类',  name: 'inType',width: 65},
			{header: '借款主体',  name: 'loanMain',width: 65
		    }
			];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar :this.toolBar,
			    structure: structure_1,
			    url: './fcApply_list.action',
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
			params.isnewInstance = true;
			var tabId = CUSTTAB_ID.applyformTabId;
			var apptabtreewinId = _this.params["apptabtreewinId"];
			var title =  '贷款申请';
			Cmw.activeTab(apptabtreewinId,tabId,params,null,title);
			Cmw.showTab(apptabtreewinId,tab,title);
		},
		/**
		 * 提交申请单	
		 * @param {} _this
		 */
		submitApplyForm : function(_this){
			var applyId = _this.appgrid.getSelId();
			if(!applyId) return;
			var codeObj = _this.appgrid.getCmnVals("code");
			 ExtUtil.confirm({title:'提示',msg:'确定提交编号为："'+codeObj.code+'"的贷款申请单?',fn:function(){
		 	  		if(arguments && arguments[0] != 'yes') return;
		 	  		var params = _this.appgrid.getCmnVals(["customerId","custType"]);
					params["applyId"] = applyId;
					params["procId"] = null;
					params["isnewInstance"] = true;
					var tabId = CUSTTAB_ID.flow_auditMainUITab.id;
					var url =  CUSTTAB_ID.flow_auditMainUITab.url;
					var title =  '业务审批';
					params["isnewInstance"] = true;
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
			var tabId = CUSTTAB_ID.detailApplyFormTabId.id;
			var url =  CUSTTAB_ID.detailApplyFormTabId.url;
			var title =  '个人客户申请单详情';
			var apptabtreewinId = _this.params["apptabtreewinId"];
			Cmw.activeTab(apptabtreewinId,tabId,params,url,title);
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
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					var winModule=null;
					if(winkey=="添加公式"||winkey=="编辑公式"){
						winModule="sys/formula/FormulaEdit";
					}
					Cmw.importPackage('pages/app/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
	}
});

