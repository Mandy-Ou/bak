/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 逾期还款金额收取 UI
 * @author cmw
 * @date 2013-03-03
 */ 
cmw.skythink.OverdueDeductMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.OverdueDeductMgr,Ext.util.MyObservable,{
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
			items : [{boxLabel : '个人', name:'custType',inputValue:0},{boxLabel : '企业', name:'custType',inputValue:1}]});

		var txt_custName = FormUtil.getTxtField({fieldLabel : '客户名称',name:'name',width : 150});
		
		var txt_code = FormUtil.getTxtField({
		    fieldLabel: '借款合同号',
		    name : 'code',
		    width : 150
		});
		
		var txt_payName = FormUtil.getTxtField({
		    fieldLabel: '还款人名称',
		    name: 'payName',
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
	
		var rad_inouttype = FormUtil.getRadioGroup({fieldLabel : '表内表外', name:'inouttype',
			items : [{boxLabel : '表内', name:'inouttype',inputValue:0},{boxLabel : '表外', name:'inouttype',inputValue:1}]});
		
		var cbo_flevel = FormUtil.getRCboField({fieldLabel : '风险等级',name:'flevel',url:'./fcRiskLevel_cbodatas.action'});
			 
		var layout_fields = [{cmns:'THREE',fields:[rad_custType,txt_custName,txt_code,txt_payName,txt_payBank,txt_payAccount,rad_inouttype,cbo_flevel]}]
		var cfg = {afterrender : function(frm){
			    		_this.globalMgr.query(_this);
			    }};
		var queryFrm = FormUtil.createLayoutFrm(cfg,layout_fields);
		
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
				_this.globalMgr.winEdit.show({self:_this,key:'逾期收款'});
			}
		},{/*导入文件收款*/
			token : '导入文件收款',
			text : Btn_Cfgs.FILE_NOMAL_BTN_TXT,
			iconCls:Btn_Cfgs.FILE_NOMAL_CLS,
			tooltip:Btn_Cfgs.FILE_NOMAL_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'导入文件收款'});
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
		    header: '贷款金额',
		    name: 'appAmount',
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '还款银行',
		    name: 'payBank'
		},
		{
		    header: '还款帐号',
		    name: 'payAccount'
		},
		{
		    header: '还款人',
		    name: 'accName',
		    width: 80
		},
		{
		    header: '本金',
		    name : 'amounts',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '利息',
		    name: 'iamounts',
		    width: 65,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '管理费',
		    name: 'mamounts',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '罚息',
		    name: 'pamounts',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '滞纳金',
		    name: 'damounts',
		    width: 70,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '应收合计',
		    name: 'totalAmounts',
		    width: 75,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},{
		    header: '本月期次',
		    name: 'monthPharses',
		    width: 60,
		    renderer : function(val){return val ? '第'+val+'期' : '';}
		},{
		    header: '累计逾期',
		    name: 'totalPharses',
		    width: 60
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
		    header: '表内表外',
		    name: 'inouttype',
		    width: 65,
		    renderer : function(val){return Render_dataSource.inouttypeRender(val);}
		},
		{
		    header: '风险等级',
		    name: 'riskLevel',
		    width: 65,
		    renderer : function(val,metaData, record, rowIndex, cellIndex, store){
		    	renderColor(record,rowIndex,store);
		    	return val;
		    }
		},
		{
		    header: '预警颜色',
		    name: 'color',
		    width: 65,hidden: true ,hideable : true
		},
		{header: '合同ID', name: 'contractId',hidden: true ,hideable : true}
		];
		/*----------- 为Grid行加背景色  CODE START -------*/
		var arr = [];
		function renderColor(record,rowIndex,store){
			var count = store.getCount();
			var color = record.get('color');
			var code = record.get('code');
			if(rowIndex == 0){
				arr = [];
			}
			arr[arr.length] = [rowIndex,color];
			if(rowIndex+1 < count) return;
			var indexOf = -1;
			if(appgrid_1.gatherCfg.gatherTitle){
				indexOf = code.indexOf(appgrid_1.gatherCfg.gatherTitle);
			}else{
				indexOf = code.indexOf(appgrid_1.gatherTitle);
			}
			if(indexOf == -1) return;
			var task = new Ext.util.DelayedTask(function(){
				var view = appgrid_1.getView();
				for(var i=0,count=arr.length; i<count; i++){
					var data = arr[i];
					var rowIndex = data[0];
					var color = data[1];
					var row = view.getRow(rowIndex);
					if(row) row.style.backgroundColor=color;
				}
			},this);
			task.delay(100);
		}	
		
		function headerListener (grid,cmnIndex,e){
			var store = grid.getStore();
			var newRecord = null;
			for(var i=0,count=store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id = record.id;
				if(id == appgrid_1.gatherIdVal){
					newRecord = record.data;
					store.remove(record);
					break;
				}
			}
			if(newRecord){
				grid.addRecord(newRecord, true);
			}
		}
		/*----------- 为Grid行加背景色  CODE END -------*/
		
		
		var continentGroupRow = [{header: '合同信息', colspan: 6, align: 'center'},
			{header: '还款帐号信息', colspan: 3, align: 'center'},
			{header: '累计应收款(元)', colspan: 6, align: 'center'},
			{header: '还款期数(期)', colspan: 4, align: 'center'},
			{header: ' ', colspan: 4, align: 'center'}];
		 var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [continentGroupRow]
	    });

		var appgrid_1 = new Ext.ux.grid.AppGrid({
			tbar :_this.toolBar,
		    structure: structure_1,
		    url: './fcOverdueDeduct_list.action',
		    needPage: true,
		    keyField: 'id',
		    selectType : "check",
		    plugins: group,
		    isLoad: false,
		    listeners : {
		    	'headerclick' : headerListener,
		    	'headerdblclick' : headerListener,
		    	render : function(frm){
			    		_this.globalMgr.query(_this);
			    }
		    },
		    gatherCfg : {
		    	gatherOffset : "code",
		    	gatherCmns : [{cmn:'appAmount',dp:2},
		    		{cmn:'amounts',dp:2},
		    		{cmn:'iamounts',dp:2},
		    		{cmn:'mamounts',dp:2},
		    		{cmn:'pamounts',dp:2},
		    		{cmn:'damounts',dp:2},
		    		{cmn:'totalAmounts',dp:2}]
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
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent = _this.appgrid;
				parent.sysId  = _this.globalMgr.sysId;
				parentCfg.parent = parent;
				var winModule=null;
				var bussKey = LockManager.keys.OverduePayKey;/*逾期收款的钥匙*/
				parentCfg.bussKey = bussKey;
				LockManager.applyLock(bussKey);
				if(winkey && winkey == '导入文件收款'){
					winModule = "OverdueDeductImportEdit";
					showWindow();
				}else{
					LockManager.isLock(parent,"name",function(){
						switch(winkey){
							case '逾期收款':{
								var selIds = parent.getSelIds();
								if(!selIds)	return;
								var selIdsArr = selIds.split(",");
								if(selIdsArr.length <= 1){
									winModule = "OverdueDeductEdit";
								}else{
									winkey = "逾期批量收款";
									winModule = "OverdueDeductBatchEdit";
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
						Cmw.importPackage('pages/app/finance/deduct/overdue/'+winModule,function(module) {
						 	_this.appCmpts[winkey] = module.WinEdit;
						 	_this.appCmpts[winkey].show(parentCfg);
				  		});
					}
				}
			}
		}
	}
});

