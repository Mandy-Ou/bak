/**
 * 同心日信息科技有限责任公司产品 
 *  联系电话：18064179050 程明卫[系统设计师]
 *  QQ:340360491
 */
Ext.namespace("cmw.oa");
/**
 * @description 已办的借款申请单管理 UI 
 * @date 2014-02-08 20:12
 */ 
cmw.oa.HistoryLoanApplyMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.oa.HistoryLoanApplyMgr,Ext.util.MyObservable,{
	queryFields : null,
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			id : ComptIdMgr.auditLoanApplyMgrId,
			tab:tab,
			params:params,
			getQueryFrm : this.getQueryFrm,
			getToolBar : this.getToolBar,
			getAppGrid : this.getAppGrid,
			globalMgr : this.globalMgr,
			notify : this.notify,
			refresh : this.refresh
		});
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var _this = this;
		var tokenMgr = _this.globalMgr.tokenMgr;
		var barItems = [{token:tokenMgr.QUERY_TXT,text:Btn_Cfgs.QUERY_BTN_TXT,iconCls:Btn_Cfgs.QUERY_CLS,handler:function(){
						_this.globalMgr.query(_this);
					}},
					{token:tokenMgr.RESET_TXT,text:Btn_Cfgs.RESET_BTN_TXT,iconCls:Btn_Cfgs.RESET_CLS,handler:function(){
						_this.queryFrm.reset();
					}},
					{type:'sp'},/*编辑*/
					{token:tokenMgr.EDIT_TXT,text:Btn_Cfgs.MODIFY_BTN_TXT,iconCls:Btn_Cfgs.MODIFY_CLS,handler:function(){
						_this.globalMgr.doApplyByOp(_this);
					}},/*详情*/
					{token:tokenMgr.DETAIL_TXT,text:Btn_Cfgs.DETAIL_BTN_TXT,iconCls:Btn_Cfgs.DETAIL_CLS,handler:function(){
						_this.globalMgr.winEdit.show({key:this.token,optionType:OPTION_TYPE.DETAIL,self:_this});
					}},/*查看*/ 
					{token:tokenMgr.SUBMIT_TXT,text:Btn_Cfgs.VIEW_BTN_TXT,iconCls:Btn_Cfgs.VIEW_CLS,handler:function(){
						_this.globalMgr.submitApplyForm(_this);
					}}];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : false,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var width = 200;
		
		var cbo_status = FormUtil.getLCboField({fieldLabel : '状态',name:'status',data:Lcbo_dataSource.getAllDs(Lcbo_dataSource.auditStatus_datas)});
 		var txt_code = FormUtil.getTxtField({fieldLabel : '编号',name:'code',width:width});
		var txt_loanMan = FormUtil.getTxtField({fieldLabel : '借款人',name:'loanMan',width:width});
 		var rad_rendType = FormUtil.getRadioGroup({fieldLabel : '借款类型',name:'rendType',width:width,
							items : [{boxLabel : '无限制', name:'rendType',inputValue:0,width:90},
								{boxLabel : '备用金', name:'rendType',inputValue:1,width:90},
							    {boxLabel : '借款', name:'rendType',inputValue:2,width:90}]});
				
		var eqopLcbox = FormUtil.getEqOpLCbox({name:'eqop'});
		var txt_amount = FormUtil.getDoubleField({fieldLabel : '借款金额',name:'amount',width:60});
		var cmpt_amount = FormUtil.getMyCompositeField({fieldLabel:'借款金额',name:'cmpt_amount',width:width,
			itemNames : 'eqop,amount',sigins:null,items:[eqopLcbox,txt_amount]});
		
		var startDate = FormUtil.getDateField({fieldLabel : '借款日期',name:'startDate',width:90});
		var endDate = FormUtil.getDateField({fieldLabel : '借款日期',name:'endDate',width:90});
		
		var cmpt_loanDate = FormUtil.getMyCompositeField({fieldLabel:'借款日期',name:'cmpt_loanDate',width:width,
			itemNames : 'startDate,endDate',sigins:null,items:[startDate,{xtype:'displayfield',value:'至'},endDate]});
		
		 var layout_fields = [{cmns:FormUtil.CMN_THREE,fields:[cbo_status,txt_code,txt_loanMan,rad_rendType,cmpt_amount,cmpt_loanDate]}]

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		return queryFrm;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
		var structure_1 = [{
			    header: '编号',
			    name: 'code',
			    width: 125
			},{
			    header: '状态',
			    name: 'status',
			    width: 80,
			    renderer : function(val){
			    	return OaRender_dataSource.flowStatusRender(val);
			    }
			},
			{
			    header: '借款人',
			    name: 'loanMan',
			    width: 80
			},
			{
			    header: '借款类型',
			    name: 'rendType',
			    renderer : function(val){
			    	return OaRender_dataSource.rendTypeRender(val);
			    }
			},
			{
			    header: '借款金额',
			    name: 'amount',
			    width: 85,
			    renderer : function(val){
			    	return OaRender_dataSource.moneyRender(val);
			    }
			},
			{
			    header: '借款日期',
			    name: 'loanDate',
			    width: 85
			},
			{
			    header: '借款事由',
			    name: 'reason',
			    width: 200
			},
			{
			    header: '还款类型',
			    name: 'loanType',
			    width: 75
			},
			{
			    header: '预计还款日期',
			    name: 'payDate',
			    width: 85
			},
			{
			    header: '借款帐号',
			    name: 'accountNum'
			},
			{
			    header: '帐户名',
			    name: 'accountName',
			    width: 85
			},
			{
			    header: '开户行',
			    name: 'bankName',
			    width: 125
			},{
			    header: '当前环节',
			    name: 'currStage',
			    width:120
			},{
			    header: '下一环节',
			    name: 'nextStage',
			    width:110
			},
			{
			  header: '子业务流程ID',
			    name: 'breed',
			    hidden:true,
			    width: 1
			},{
			  header: 'OA流程实例ID',
			    name: 'oaprocInstanceId',
			    hidden:true,
			    width: 1
			}];
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    title: '已审借款申请单列表',
		    tbar : this.toolBar,
		    structure: structure_1,
		    url: './oaLoanApply_auditHistory.action',
		  //  needPage: true,
		    isLoad : false,
		    keyField: 'id',
		    listeners:{
		    	render : function(grid){_this.globalMgr.query(_this);}
		    }
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
		if(optionType && optionType == OPTION_TYPE.DEL){/*删除完后减少暂存提醒数量*/
			
		}
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		sysId : null,	/*系统ID*/
		/**
		 * 按钮唯一标识码
		 * @type 
		 */
		tokenMgr : {
			QUERY_TXT : '查询',
			RESET_TXT : '重置',
			EDIT_TXT : '编辑',
			DETAIL_TXT : '查看详情',
			SUBMIT_TXT : '提交',
			DELETE_TXT : '删除'
		},
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		activeKey : null,
		sysId : null,
		/**
		 * 添加/编辑申请单	
		 * @param {} _this
		 */
		doApplyByOp : function(_this,op){
			var params = {applyId:null,optionType:OPTION_TYPE.EDIT};
			var applyId = _this.appgrid.getSelId();
			if(!applyId) return;
			params.applyId = applyId;
			var dataObj = _this.appgrid.getCmnVals("breed,oaprocInstanceId");
			var bussProccId = dataObj.breed;
			var oaprocInstanceId = dataObj.oaprocInstanceId;
			params.bussProccId = bussProccId;
			params.oaprocInstanceId = oaprocInstanceId;
			//params.isnewInstance = true;
			
			var tabId = OA_NEWWORK_ID_PREFIX+bussProccId;
			var tabTitle = "借款申请";
			var url= Flow_CustForm_Url.OaBussProccBase;
			Cmw.activeTabByOa(tabId,params,url,tabTitle,OA_UIActiveType.tipInstance);
		},
		/**
		 * 提交申请单	
		 * @param {} _this
		 */
		submitApplyForm : function(_this){
			var applyId = _this.appgrid.getSelId();
			if(!applyId) return;
			var dataObj = _this.appgrid.getCmnVals("code");
			var sysId = _this.params.sysid;
			 ExtUtil.confirm({title:'提示',msg:'确定提交编号为："'+dataObj.code+'"的借款申请单?',fn:function(){
		 	  		if(arguments && arguments[0] != 'yes') return;
					var params = {applyId:applyId,procId:null,bussProccCode:'OA0001'};
					var title =  '借款申请单审批';
					Cmw.activeAuditFlowTab(params,title,OA_UIActiveType.tipInstance);
			 }});
		},
		winEdit : {
			show : function(parentCfg){
				var winkey = parentCfg.key;
				var _this =  parentCfg.self;
				var parent = _this.appgrid;
				var selId  = parent.getSelId();
				if(!selId) return;
				parentCfg.parent = parent;
				var sysId = _this.params.sysid;
				parentCfg.params = {
					sysId : sysId,
					id : selId
				}
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
						Cmw.importPackage('pages/app/workflow/bussProcc/oa/formUIs/detail/LoanApplyDetail',function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		},
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var params = _this.queryFrm.getValues();
			if(!params) params = {};
			EventManager.query(_this.appgrid,params);
		}
	}
});

