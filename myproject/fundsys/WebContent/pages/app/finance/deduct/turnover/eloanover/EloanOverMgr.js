/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]----->彭登浩
 */
Ext.namespace("cmw.skythink");
/**
 *企业客户贷款发放 UI smartplatform_auto 2013-01-16 09:48:16
 */ 
cmw.skythink.EloanOverMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.EloanOverMgr,Ext.util.MyObservable,{
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
		var _this =this;
		var txt_name = FormUtil.getTxtField({fieldLabel : '企业名称',name:'name',width : 150});
		var txt_kind = FormUtil.getTxtField({
		    fieldLabel: '企业性质',
		    name: 'kind',
		    "width": 150
		});
		
		var txt_ccode = FormUtil.getTxtField({
		    fieldLabel: '借款合同号',
		    name : 'ccode',
		    width : 150
		});
		
		var txt_payName = FormUtil.getTxtField({
		    fieldLabel: '收款人名称',
		    name: 'payName',
		    width: 150
		});
		
		
		var txt_regBank = FormUtil.getTxtField({
		    fieldLabel: '开户行',
		    name: 'regBank',
		     width: 150
		});
		
		var txt_account = FormUtil.getTxtField({
		    fieldLabel: '收款帐号',
		    name: 'account',
		     width: 150
		});	
	
		
		var cbo_eqopAmount = FormUtil.getEqOpLCbox({name:'eqopAmount'});
		var txt_payAmount = FormUtil.getMoneyField({
		    fieldLabel: '放款金额',
		    name:'payAmount',
		    width:70
		});
		
		var comp_payAmount = FormUtil.getMyCompositeField({
			 fieldLabel: '放款金额',width:150,sigins:null,
			 itemNames:"eqopAmount,payAmount",
			 name:'comp_payAmount',	
			 items : [cbo_eqopAmount,txt_payAmount]
		});
		
		var txt_startDate = FormUtil.getDateField({name:'startDate',width:90/*,isdeaulft:true*/});
		var dt = new Date().add(Date.DAY, 6);
		var  txt_endDate = FormUtil.getDateField({name:'endDate',width:90/*,value : dt*/});
		var comp_payDate = FormUtil.getMyCompositeField({
			itemNames : 'startDate,endDate',
			sigins : null,
			 fieldLabel: '合约放款日',width:210,sigins:null,
			 name:'comp_payDate',
			 items : [txt_startDate,{xtype:'displayfield',value:'至'},txt_endDate]
		});
		
		var layout_fields = [{cmns:'THREE',fields:[txt_name,txt_kind,txt_ccode,txt_payName,txt_regBank,txt_account,comp_payAmount,comp_payDate]}];

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		
		return queryFrm;
	},
	/**
	 * 查询工具栏
	 */
	getToolBar : function() {
		var _this = this;
		var toolBar = null;
		var barItems = [{/* 查询 */
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls : 'page_query',
			tooltip : Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function() {
				_this.globalMgr.query(_this);
			}
		}, {
			/* 重置 */
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls : 'page_reset',
			tooltip : Btn_Cfgs.RESET_TIP_BTN_TXT,
			name : 'a',
			handler : function() {
				_this.queryFrm.reset();
			}
		},{type:"sp"},{/*打印放款单*/
			token : '打印放款单',
			text : Btn_Cfgs.LOANINVOCE_DATEI_BTN_TXT,
			iconCls:Btn_Cfgs.PRINT_CLS,
			tooltip:Btn_Cfgs.LOANINVOCE_DATEI_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.showDetailWindow({self:_this,key:'打印放款单',menuId:_this.params.nodeId});
			}
		}, {
			token : '详情',
			text : Btn_Cfgs.MENU_details_BTN_TXT,
			iconCls : Btn_Cfgs.MENU_details_BTN_CLS,
			tooltip : Btn_Cfgs.MENU_details_TIP_BTN_TXT,
			handler : function() {
				_this.globalMgr.winEdit.show({self : _this,key : '详情'});
			}
		}
		,{/*导出*/
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:Btn_Cfgs.EXPORT_CLS,
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doExport(_this);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({
					aligin : 'right',
					controls : barItems,
					rightData : {
						saveRights : true,
						currNode : this.params[CURR_NODE_KEY]
					}
				});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
		
		var structure_1 = [{
			    header: '放款单编号',
			    name: 'code',
			    width : 135
			},
			{
			    header: '合同编号',
			    name: 'ccode',
			     width : 135
			},
			{
			    header: '企业名称',
			    name: 'name',
			    width:135
			},
			{
			    header: '企业性质',
			    name: 'kind',
			    width:80,
			    renderer :function(val){
			    	var val = Render_dataSource.gvlistRender('100009',val);
			    	if(val=='暂未设置') val='';
			    	return val;
			    }
			},
			{
			    header: '业务品种',
			    name: 'vname',
			    width:100
			},
			{
			    header: '贷款金额',
			    name: 'appAmount',
			     width : 135,
				 renderer: function(val) {
			       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
			    }
			},
			 {header: '贷款期限(年)',  name: 'yearLoan',hidden: true ,hideable : true},
		    {header: '贷款期限(月)',  name: 'monthLoan',hidden: true ,hideable : true},
		    {header: '贷款期限(日)',  name: 'dayLoan',hidden: true ,hideable : true},
			{header: '贷款期限',  name: 'loanLimit' ,width: 65,
			 renderer: function(val,metaData,record) {
			 	var loanLimit = Render_dataSource.loanLimitRender(record.data);
		       return loanLimit;
		    }},
			{
			    header: '收款人名称',
			    name: 'payName',
			    width:65
			},
			{
			    header: '开户行',
			    name: 'regBank',
			    width:100
			},
			{
			    header: '收款帐号',
			    name: 'account',
			    width:135
			},
			{
			    header: '放款金额',
			    name: 'payAmount',
			    width:135,
				renderer: function(val) {
			       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
			    }
			},
			{
			    header: '手续费率',
			    name: 'prate',
			    width:65,
			    renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			   header : '实际放款日',
				name : 'realDate',
			    width:100
			}];
		
			
			var continentGroupRow = [{header: '合同信息', colspan: 12, align: 'center'},
				{header: '收款帐号信息', colspan: 3, align: 'center'},{header: '放款信息', colspan: 3, align: 'center'}];
			 var group = new Ext.ux.grid.ColumnHeaderGroup({
		        rows: [continentGroupRow]
		    });
		    
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar :this.toolBar,
			    structure: structure_1,
			    url: './fcLoanInvoceQuery_list.action',
			    needPage: true,
			    keyField: 'id',
			    selectType : "check",
			    plugins: group,
			     gatherCfg : {
		    	gatherOffset : "code",
		    	gatherCmns : [{cmn:'appAmount',dp:2},
		    		{cmn:'payAmount',dp:2}]
			    },
			    isLoad: false,
			    listeners : {
			    	render : function(grid){
			    		_this.globalMgr.query(_this);
			    	},
			    		rowdblclick : function() {
							//鼠标双击事件
							var selRow = appgrid_1 .getSelRow();//获取对象所在的行
							if(selRow!=null){_this.globalMgr.winEdit.show({self : _this,key : '详情'});}
						}
			    }
			});
		return appgrid_1;
	},
	refresh:function(optionType,data){
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		/**
		 * 当前激活的按钮文本
		 * @type
		 */
		sysId : this.params.sysid,
		payName:this.params.payName,
		activeKey : null,
		getQparams : function(_this) {
			var params = _this.queryFrm.getValues() || {};
			params.custType = '1';
			params.auditState = '2';/* 已审批通过 */
			params.state = '1';/* 放款 */
			/*-- 附加桌面传递的参数  CODE START --*/
			if (_this.params && _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY]) {
				var deskParams = _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY];
				if (deskParams) {
					Ext.applyIf(params, deskParams);
					_this.params[DESK_MOD_TAG_QUERYPARAMS_KEY] = null;
				}
			}/*-- 附加桌面传递的参数  CODE END --*/
			return params;
		},
		 showDetailWindow:function(parentCfg){
		 		var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				var sysId=_this.params.sysid;
				var winModule=null;
		 			winModule = "LoanPrintDetail";
					if(!parent.getSelId())return;
					if(_this.appCmpts[winkey]){
						_this.appCmpts[winkey].show(parentCfg);
					}else{ 
						EventManager.get("./fcFuntempCfg_getTidByMid.action",{params:{menuId:parentCfg.menuId},sfn : function(jsonData){
							parentCfg.tempId=jsonData.list[0].tempId;
							Cmw.importPackage('pages/app/finance/deduct/loan/'+winModule,function(module) {
							 	_this.appCmpts[winkey] = module.WinEdit;
							 	_this.appCmpts[winkey].show(parentCfg);
					  		});
						}});
					}
				},
		winEdit : {
			show : function(parentCfg) {
				var _this = parentCfg.self;
				var key=parentCfg.key;
				var parent = _this.appgrid;
				var sysId=_this.params.sysid;
				var payName=_this.params.payName;
				parentCfg.parent=parent;
				parentCfg.sysId=sysId;
				parentCfg.payName=payName;
					if(_this.appCmpts[key]){
					if(!parent.getSelId())return;
					_this.appCmpts[key].show(parentCfg);
					}else{ 
					if(!parent.getSelId())return;
				Cmw.importPackage('pages/app/finance/deduct/turnover/eloanover/EloanOverDetail',function(module) {
				 	_this.appCmpts[key] = module.WinEdit;
				 	_this.appCmpts[key].show(parentCfg);
						  		});
							}
					}},
		/**
		 * 查询方法
		 * 
		 * @param {}
		 *            _this
		 */
		query : function(_this) {
			var params = this.getQparams(_this);
			EventManager.query(_this.appgrid, params);
		},
		/**
		 * 导出Excel
		 * @param {} _this
		 */
		doExport : function(_this){
			var params = this.getQparams(_this);
			params.excelType = 2;
			var token = _this.params.nodeId;
			EventManager.doExport(token,params);
		}
	}
});

