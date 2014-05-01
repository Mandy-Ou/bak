/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 正常还款金额收取 UI smartplatform_auto 2013-01-16 09:48:16
 * 
 */ 
cmw.skythink.NomalDeductMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.NomalDeductMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsGContainerView({
			tab : tab,
			params : tab.params,
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
		 var rad_custType = FormUtil.getRadioGroup({fieldLabel : '客户类型', name:'custType',
			items : [{boxLabel : '个人', name:'custType',inputValue:0, checked: true},{boxLabel : '企业', name:'custType',inputValue:1}]});

		var txt_custName = FormUtil.getTxtField({fieldLabel : '客户名称',name:'name',width : 150});
		
		var txt_code = FormUtil.getTxtField({
		    fieldLabel: '借款合同号',
		    name : 'code',
		    width : 150
		});
		
		var txt_payName = FormUtil.getTxtField({
		    fieldLabel: '还款人名称',
		    name: 'accName',
		    width: 150
		});
	
		var txt_payBank = FormUtil.getTxtField({
		    fieldLabel: '还款银行',
		    name: 'payBank',
		     width: 150
		});
		
		var txt_payAccount = FormUtil.getTxtField({
		    fieldLabel: '还款帐号',
		    name: 'payAccount',
		     width: 150
		});	
	
		
		var txt_startDate = FormUtil.getDateField({name:'startDate',width:90});
		var  txt_endDate = FormUtil.getDateField({name:'endDate',width:90});
		var comp_payDate = FormUtil.getMyCompositeField({
			itemNames : 'startDate,endDate',
			sigins : null,
			 fieldLabel: '应还款日期',width:210,sigins:null,
			 name:'comp_payDate',
			 items : [txt_startDate,{xtype:'displayfield',value:'至'},txt_endDate]
		});
		
		var layout_fields = [{cmns:'THREE',fields:[rad_custType,txt_custName,txt_code,txt_payName,txt_payBank,txt_payAccount,comp_payDate]}]

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
		},{type:"sp"},{/*收款*/
			token : '收款',
			text : Btn_Cfgs.DO_NOMAL_BTN_TXT,
			iconCls:Btn_Cfgs.DO_NOMAL_CLS,
			tooltip:Btn_Cfgs.DO_NOMAL_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'收款'});
			}
		},{/*导入文件收款*/
			token : '导入文件收款',
			text : Btn_Cfgs.FILE_NOMAL_BTN_TXT,
			iconCls:Btn_Cfgs.FILE_NOMAL_CLS,
			tooltip:Btn_Cfgs.FILE_NOMAL_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'导入文件收款'});
			}
		},{type:"sp"},{/*转逾期*/
			token : '转逾期',
			text : Btn_Cfgs.DO_LATE_BTN_TXT,
			iconCls:Btn_Cfgs.DO_LATE_CLS,
			tooltip:Btn_Cfgs.DO_LATE_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.toLate(_this,1);
			}
		},{/*全部转逾期*/
			token : '全部转逾期',
			text : Btn_Cfgs.DO_ALLLATE_BTN_TXT,
			iconCls:Btn_Cfgs.DO_ALLLATE_CLS,
			tooltip:Btn_Cfgs.DO_ALLLATE_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.toLate(_this,2);
			}
		},{/*导出*/
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
		var structure_1 = [{header: '借款合同号', name: 'code'},
		{
		    header: '客户类型',
		    name: 'custType',
		    width: 60,
		    renderer : function(val){
		    	return Render_dataSource.custTypeRender(val);
		    }
		},
		{
		    header: '客户名称',
		    name: 'name'
		},
		{
		    header: '还款银行',
		    name: 'payBank',
		    width : 125
		},
		{
		    header: '还款帐号',
		    name: 'payAccount',
		    width : 125
		},
		{
		    header: '还款人',
		    name: 'accName',
		    width: 80
		},
		{
		    header: '应还日期',
		    name: 'xpayDate',
		    width: 90
		},
		{
		    header: '本期',
		    name: 'phases',
		    width: 35
		},
		{
		    header: '已还',
		    name: 'paydPhases',
		    width: 35
		},
		{
		    header: '总期数',
		    name: 'totalPhases',
		    width: 45
		},
		{
		    header: '应还本金',
		    name : 'principal',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实还本金',
		    name: 'yprincipal',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '应还利息',
		    name: 'interest',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实还利息',
		    name: 'yinterest',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '应还管理费',
		    name: 'mgrAmount',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实还管理费',
		    name: 'ymgrAmount',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '应收合计',
		    name: 'totalAmount',
		    width: 75,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '实收合计',
		    name: 'ytotalAmount',
		    width: 75,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '还款状态',
		    name: 'status',
		    width: 75,
		    renderer : function(val){
		    	return Render_dataSource.planStatusRender(val);
		    }
		},
		{
		    header: '豁免状态',
		    name: 'exempt',
		    width: 75,
		    renderer : function(val){
		    	return  Render_dataSource.exemptRender(val);
		    }
		},
		{
		    header: '最后还款日',
		    name: 'lastDate'
		},
		{
		    header: '贷款金额',
		    name: 'appAmount',
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '贷款期限',
		    name: 'loanLimit'
		},
		{
		    header: '合同ID',
		    name: 'contractId',
		    hideable : true,
		    hidden : true
		}];
			
		var continentGroupRow = [{header: '合同信息', colspan: 5, align: 'center'},
			{header: '还款帐号信息', colspan: 3, align: 'center'},
			{header: '', colspan: 1, align: 'center'},
			{header: '还款期数', colspan: 3, align: 'center'},
			{header: '本金', colspan: 2, align: 'center'},
			{header: '利息', colspan: 2, align: 'center'},
			{header: '管理费', colspan: 2, align: 'center'},
			{header: '本息费合计', colspan: 2, align: 'center'},
			{header: ' ', colspan: 5, align: 'center'}];
		 var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [continentGroupRow]
	    });

		var appgrid_1 = new Ext.ux.grid.AppGrid({
			tbar :_this.toolBar,
		    structure: structure_1,
		    url: './fcNomalDeduct_list.action',
		    needPage: true,
		    keyField: 'id',
		    selectType : "check",
		    plugins: group,
		    gatherCfg : {
		    	gatherOffset : "code",
		    	gatherCmns : [{cmn:'principal',dp:2},
		    		{cmn:'yprincipal',dp:2},
		    		{cmn:'interest',dp:2},
		    		{cmn:'yinterest',dp:2},
		    		{cmn:'mgrAmount',dp:2},
		    		{cmn:'ymgrAmount',dp:2},
		    		{cmn:'totalAmount',dp:2},
		    		{cmn:'ytotalAmount',dp:2},
		    		{cmn:'appAmount',dp:2}]
		    }, isLoad: false,
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
		sysId  : this.params.sysid,
		activeKey : null,
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
			params.excelType = 1;
			var token = _this.params.nodeId;
			EventManager.doExport(token,params);
		},
		/**
		 * 转逾期
		 * @param {} _this
		 * @param {} op
		 */
		toLate : function(_this,op){
			var params = {};
			if(op == 2){/*全部转逾期*/
				 ExtUtil.confirm({msg:Msg_SysTip.msg_toAllLate,fn:function(btn){
				 	if(btn == 'yes') submit();
				 }});
			}else{/*选中记录转逾期*/
				var selRows = _this.appgrid.getSelRows();
				if(!selRows) return;
				var ids = [];
				var msgArr = [];
				var today = new Date();
				for(var i=0,count=selRows.length; i<count; i++){
					var row = selRows[i];
					var xpayDate = row.get('xpayDate');
					if(Ext.isString(xpayDate)){
						xpayDate = Date.parseDate(xpayDate,'Y-m-d');
					}
					if(xpayDate >= today) continue;
					var id = row.id;
					ids[ids.length] = id;
					var name = row.get("name");
					var phases = row.get("phases");
					msgArr[msgArr.length] = name + ' : 第 '+phases+'期<br/>';
				}
				
				if(!ids || ids.length == 0) return;
				params.ids = ids.join(",");
				var msg = msgArr.join(" ");
				msg += Msg_SysTip.msg_toLate;
				ExtUtil.confirm({msg:msg,fn:function(btn){
				 	if(btn == 'yes') submit();
				}});
			}
			
			function submit(){
				EventManager.get('./fcOverdueDeduct_tolate.action',{params:params,sfn:function(json_data){
					  ExtUtil.alert({msg:Msg_SysTip.msg_dataSucess});
					  _this.globalMgr.query(_this);
				}});
			}
		},
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent = _this.appgrid;
				parent.sysId = _this.globalMgr.sysId;
				//parent.contractId = parent.
				parentCfg.parent = parent;
				var winModule=null;
				var bussKey = LockManager.keys.LoanInvoceKey;/*放款的钥匙*/
				parentCfg.bussKey = bussKey;
				LockManager.applyLock(bussKey);
				if(winkey && winkey == '导入文件收款'){
					winModule = "NomalDeductImportEdit";
					showWindow();
				}else{
					LockManager.isLock(parent,"name",function(){
						switch(winkey){
							case '收款':{
								var selIds = parent.getSelIds();
								if(!selIds)	return;
								var selIdsArr = selIds.split(",");
								if(selIdsArr.length <= 1){
									winModule = "NomalDeductEdit";
								}else{
									winkey = "批量收款";
									winModule = "NomalDeductBatchEdit";
								}
								addContractIds()
								break;
							}
						}
						showWindow();
					});
				}
				/**
				 * 将选中的合同号添加至 parentCfg 对象中
				 */
				function addContractIds(){
					var selRows = parent.getSelRows();
					var contractIds = [];
					for(var i=0,count=selRows.length; i<count; i++){
						var contractId = selRows[i].get("contractId");
						contractIds[contractIds.length] = contractId;
					}
					contractIds = contractIds.join(",");
					parentCfg.contractIds = contractIds;
				}
				
				function showWindow(){
					if(_this.appCmpts[winkey]){
						_this.appCmpts[winkey].show(parentCfg);
					}else{ 
						Cmw.importPackage('pages/app/finance/deduct/nomal/'+winModule,function(module) {
						 	_this.appCmpts[winkey] = module.WinEdit;
						 	_this.appCmpts[winkey].show(parentCfg);
				  		});
					}
				}
			}
		}
	}
});

