/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]----->彭登浩
 */
Ext.namespace("cmw.skythink");
/**
 *企业客户贷款发放 UI smartplatform_auto 2013-01-16 09:48:16
 */ 
cmw.skythink.FreeMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.FreeMgr,Ext.util.MyObservable,{
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
			prefix : Ext.id()	
		});
	},
	
	/**
	 * 获取查询Form 表单
	 */
	getQueryFrm : function(){
		var _this =this;
		var txt_custType = FormUtil.getLCboField({fieldLabel : '客户类型',name:'custType',width : 150,data:[["0","个人客户"],["1","企业客户"]]});
		var txt_name = FormUtil.getTxtField({fieldLabel : '客户名称',name:'name',width : 150});
		
		var txt_ccode = FormUtil.getTxtField({
		    fieldLabel: '放款合同号',
		    name : 'ccode',
		    width : 150
		});
		
		var txt_accName = FormUtil.getTxtField({
		    fieldLabel: '还款人名称',
		    name: 'accName',
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
		
		var txt_startDate = FormUtil.getDateField({name:'startDate',width:90});
		var  txt_endDate = FormUtil.getDateField({name:'endDate',width:90});
		var comp_payDate = FormUtil.getMyCompositeField({
			itemNames : 'startDate,endDate',
			sigins : null,
			 fieldLabel: '合约放款日',width:200,sigins:null,
			 name:'comp_payDate',
			 items : [txt_startDate,txt_endDate]
		});
		
		var layout_fields = [{cmns:'THREE',fields:[txt_custType,txt_name,txt_ccode,txt_accName,comp_payAmount,comp_payDate]}];

		var queryFrm = FormUtil.createLayoutFrm(null,layout_fields);
		
		return queryFrm;
	},
	
	/**
	 * 查询工具栏
	 */
	getToolBar : function(){
		var _this = this;
		var toolBar = null;
		var barItems = [{/*查询*/
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.query(_this);
			}
		},{/*重置*/
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				_this.queryFrm.reset();
			}
		},{type:"sp"},{/*放款手续费收取*/
			token : '放款手续费收取',
			text : Btn_Cfgs.DO_FREE_BTN_TXT,
			iconCls:Btn_Cfgs.DO_FREE_CLS,
			tooltip:Btn_Cfgs.DO_FREE_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'放款手续费收取'});
			}
		},{/*导入放款手续费文件收款*/
			token : '导入放款手续费文件收款',
			text : Btn_Cfgs.FILE_FREE_BTN_TXT,
			iconCls:Btn_Cfgs.FILE_FREE_CLS,
			tooltip:Btn_Cfgs.FILE_FREE_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'导入放款手续费文件收款'});
			}
		},{/*导出放款手续费文件*/
			token : '导出放款手续费文件',
			text : Btn_Cfgs.EXPORT_FREE_BTN_TXT,
			iconCls:Btn_Cfgs.EXPORT_FREE_CLS,
			tooltip:Btn_Cfgs.EXPORT_FREE_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doExport(_this);
			}
		}/*,{
			token : '豁免申请',
			text : "豁免申请",
//			iconCls:Btn_Cfgs.EXPORT_FREE_CLS,
			tooltip:Btn_Cfgs.EXPORT_FREE_TIP_BTN_TXT,
			handler : function(){
//				_this.globalMgr.doExport(_this);
			}
		},{
			token : '豁免',
			text : "豁免",
//			iconCls:Btn_Cfgs.EXPORT_FREE_CLS,
			tooltip:Btn_Cfgs.EXPORT_FREE_TIP_BTN_TXT,
			handler : function(){
//				_this.globalMgr.doExport(_this);
			}
		}*/];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	/**
	 * 获取Grid 对象
	 */
	getAppGrid : function(){
		var _this = this;
		
		var structure_1 = [{
			    header: '收款状态',
			    name: 'status',
			    width:60,
			    renderer : function(val){
			    	 var status = Render_dataSource.statusRender(val);
			    	 return status;
			    }
			},
			{
			    header: '豁免状态',
			    name: 'exempt',
			    width:60,
			    renderer : function(val){
			    	 var exempt = Render_dataSource.exemptRender(val);
			    	 return exempt;
			    }
			},
			{
			    header: '客户类型',
			    name: 'custType',
			    width:60,
			    renderer : function(val){
			    	 var custType = Render_dataSource.custTypeRender(val);
			    	 return custType;
			    }
			},
			{
			    header: '客户名称',
			    name: 'name',
			    width:60
			},
			{
			    header: '手续费率',
			    name: 'prate',
			    width:60,
			    renderer: function(val) {
			        return val ? val+'%' : '0';
			    }
			},
			{
			    header: '应收手续费',
			    name: 'freeamount',
			   	width:100,
				renderer: Render_dataSource.moneyRender
			},
			{
			    header: '已手续费',
			    name: 'yamount',
			    width:100,
				renderer: Render_dataSource.moneyRender
			},
			{
			    header: '未收手续费',
			    name: 'notamount',
			    width:100,
				renderer: Render_dataSource.moneyRender
			},
			{
			    header: '最后收款日期',
			    name: 'lastDate',
			    width:80
			},
			{
			    header: '实际放款日期',
			    name: 'realDate',
			    width:80
			},
			{
			    header: '放款单编号',
			    name: 'ccode'
			},
			{
			    header: '已放款金额',
			    name: 'payAmount',
			    width:100,
				renderer: Render_dataSource.moneyRender
			},
			{
			    header: '借款合同编号',
			    name: 'code'
			},
			{
			    header: '贷款金额',
			    name: 'appAmount',
				 renderer: Render_dataSource.moneyRender
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
			    header: '还款人名称',
			    name: 'accName',
			    width:60
			},
			{
			    header: '开户行',
			    name: 'regBank',
			    width:60
			},
			{
			    header: '还款帐号',
			    name: 'payAccount',
			    width:100
			},
			{
			    header: 'contractId',
			    name: 'contractId',
			    hidden : true,
			    width:100
			}
			];
		
			
			var continentGroupRow = [{header: '状态', colspan: 4, align: 'center'},
				{header: '客户信息', colspan: 2, align: 'center'},
				{header: '收取手续费信息', colspan: 5, align: 'center'},
				{header: '放款单信息', colspan: 3, align: 'center'},
				{header: '借款合同信息', colspan: 6, align: 'center'},
				{header: '还款帐号信息', colspan: 4, align: 'center'}];
			 var group = new Ext.ux.grid.ColumnHeaderGroup({
		        rows: [continentGroupRow]
		    });
    
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar :_this.toolBar,
			    structure: structure_1,
			    url: './fcFree_list.action',
			    needPage: true,
			    keyField: 'id',
			    selectType : "check",
			    plugins: group,
			    gatherCfg : {
		    	gatherOffset : "status",
		    	gatherCmns : [{cmn:'freeamount',dp:2},
		    		{cmn:'yamount',dp:2},
		    		{cmn:'notamount',dp:2},
		    		{cmn:'payAmount',dp:2},
		    		{cmn:'appAmount',dp:2}]
			    },isLoad: false,
			    listeners : {
				   	render : function(grid){
				   	 _this.globalMgr.query(_this);
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
		activeKey : null,
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
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
			params.excelType = 1;
			var token = _this.params.nodeId;
			EventManager.doExport(token,params);
		},
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent = _this.appgrid;
				parent.sysId = _this.globalMgr.sysId;
				parentCfg.parent = parent;
				var winModule=null;
				var bussKey = LockManager.keys.FreePayKey;/*手续费收取的钥匙*/
				parentCfg.bussKey = bussKey;
				LockManager.applyLock(bussKey);
				if(winkey && winkey == "导入放款手续费文件收款"){/*导入放款手续费文件收款*/
					winModule = "FreeChargeImportEdit";
					showWindow();
				}else{
					LockManager.isLock(parent,"name",function(){
						switch(winkey){
							case Btn_Cfgs.DO_FREE_BTN_TXT:{//放款手续费收款
								var selIds = parent.getSelIds();
								if(!selIds){
									return;
								}
								var selIdsArr = [];
								selIdsArr = selIds.split(",");
								if(selIdsArr.length == 1){
									parent.contractId = _this.appgrid.getCmnVals("contractId").contractId;
									winModule = "FreeChargeEdit";
									winkey = "FreeChargeEdit";
								}
								else{
									winModule = "FreeChargeBatchEdit";
									winkey = "FreeChargeBatchEdit";
								}
								break;
							}
						}
						showWindow();
					});
				}
				
				function showWindow(){
					if(_this.appCmpts[winkey]){
						_this.appCmpts[winkey].show(parentCfg);
					}else{ 
						Cmw.importPackage('pages/app/finance/deduct/free/'+winModule,function(module) {
						 	_this.appCmpts[winkey] = module.WinEdit;
						 	_this.appCmpts[winkey].show(parentCfg);
				  		});
					}
				}
			}
		}
	}
});

