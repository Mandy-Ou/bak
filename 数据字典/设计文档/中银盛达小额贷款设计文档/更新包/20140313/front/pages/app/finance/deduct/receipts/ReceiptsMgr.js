/**
 * 同心日信息科技责任有限公司产品 命名空间 联系电话：18064179050 程明卫[系统设计师] 联系电话：18026386909 陈瑾[销售总经理]
 */
Ext.namespace("cmw.skythink");
/**
 * 预收管理 UI smartplatform_auto  彭登浩
 */ 
cmw.skythink.ReceiptsMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.ReceiptsMgr,Ext.util.MyObservable,{
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
		var txt_name = FormUtil.getTxtField({fieldLabel : '客户姓名',name:'name',width : 150});
		 var rad_custType = FormUtil.getRadioGroup({fieldLabel : '客户类型', name:'custType',
			items : [{boxLabel : '个人', name:'custType',inputValue:0},{boxLabel : '企业', name:'custType',inputValue:1}]});
			
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
			 fieldLabel: '合约放款日',width:200,sigins:null,
			 name:'comp_payDate',
			 items : [txt_startDate,txt_endDate]
		});
		
			var ysRatMonth = FormUtil.getIntegerField({
				fieldLabel : '预收利息月数',
				name : 'ysRatMonth',
				"width" : 135,
				unitText : '月'
			});
			var ysRat = FormUtil.getMoneyField({
				fieldLabel : '预收利息金额',
				name : 'ysRat',
				"width" : 135,
				unitText : '元'
			});
			
			var ysMatMonth = FormUtil.getIntegerField({
				fieldLabel : '预收管理费月数',
				name : 'ysMatMonth',
				"width" : 135,
				unitText : '月'
			});
			var ysMat = FormUtil.getMoneyField({
				fieldLabel : '预收管理费金额',
				name : 'ysMat',
				"width" : 135,
				unitText : '元'
			});
			
		var layout_fields = [{cmns:'THREE',fields:[txt_name,rad_custType,txt_payName,
		txt_regBank,txt_account,comp_payAmount,comp_payDate,ysRatMonth,ysRat,ysMatMonth,ysMat]}];

		var queryFrm = FormUtil.createLayoutFrm({labelWidth : 150},layout_fields);
		
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
		},{type:"sp"}/*,{
			token : '打印放款单',
			text : Btn_Cfgs.LOANINVOCE_DATEI_BTN_TXT,
			iconCls:Btn_Cfgs.PRINT_CLS,
			tooltip:Btn_Cfgs.LOANINVOCE_DATEI_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'打印放款单',menuId:_this.params.nodeId});
			}
		}*/,{/*放款*/
			token : '收款',
			text : Btn_Cfgs.DO_NOMAL_BTN_TXT,
			iconCls:Btn_Cfgs.DO_NOMAL_CLS,
			tooltip:Btn_Cfgs.DO_NOMAL_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'收款'});
			}
		}/*,{type:"sp"},{
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:Btn_Cfgs.EXPORT_CLS,
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doExport(_this);
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
			    header: '客户姓名',
			    name: 'name',
			    width:65
			},
			{
			    header: '贷款金额',
			    name: 'appAmount',
			     width : 135,
				 renderer: function(val) {
			       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
			    }
			},
			{
			    header: '放款金额',
			    name: 'payAmount',
			    width:135,
				renderer: function(val,m) {
					m.css='x-grid-back-qs'; 
			       return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
			    }
			},{
				header: '预收利息月数',
		    	width : 135,
		    	name: 'ysRatMonth',
		    	renderer : function(val,m){
		    		m.css = 'x-grid-back-ysRatMonth';;
		    		if(val){
		    			val = val+'个月'
		    			return val;
		    		}
		    	}
			},{
				header: '预收利息',
		    	width : 135,
		    	name: 'ysRat',
		    	renderer : function(val,m){
		    		m.css = 'x-grid-back-ysRat';
		    		if(val){
		    			val =val.replaceAll(",","");
		    			val = Cmw.getThousandths(val)+'元'
		    			return val;
		    		}
		    		
		    	}
			},{
				header: '预收管理费月数',
		    	width : 135,
		    	name: 'ysMatMonth',
		    	renderer : function(val,m){
		    		m.css = 'x-grid-back-ysMatMonth';
		    		if(val){
		    			val = val+'个月'
		    			return val;
		    		}
		    	}
			},{
				header: '预收管理费',
		    	width : 135,
		    	name: 'ysMat', 
		    	renderer : function(val,m){
		    		m.css = 'x-grid-back-ysMat';
		    		if(val){
		    			val =val.replaceAll(",","");
		    			val = Cmw.getThousandths(val)+'元'
		    			return val;
		    		}
		    		
		    	}
			},
			{
				header: '管理费率(%)',
		    	width : 90,
		    	name: 'mrate',
		    	renderer : function(val,m){
		    		if(val || val==0) val += '%';
		    		return val ;
		    	}
		    	
			},{
				header: '贷款利率(%)',
		    	width : 90,
		    	name: 'rate',
		    	renderer : function(val,m){
		    		m.css='x-grid-back-gree'; 
		    		if(val || val==0) val += '%';
		    		return val ;
		    	}
			},
			{header: '内部利率类型',  name: 'inRateType',width: 100,
			 renderer: function(val,m) {
			 	m.css='x-grid-back-red'; 
		       return Render_dataSource.rateTypeRender(val);
		    }},
			{header: '公司内部利率',  name: 'inRate' ,width: 90,
			 	renderer : function(val,m){
		    		m.css='x-grid-back-red'; 
		    		if(val || val==0) val += '%';
		    		return val ;
		    	}
		    },
			{header: '贷款期限',  name: 'loanLimit' ,width: 65},
			{
			    header: '收款人名称',
			    name: 'payName',
			    width:100
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
			    header: '放款日期',
			    name: 'payDate',
			    width:100
			}];
		
			
			var continentGroupRow = [{header: '合同信息', colspan: 12, align: 'center'},
				{header: '收款帐号信息', colspan: 3, align: 'center'},{header: '放款信息', colspan: 5, align: 'center'}];
			 var group = new Ext.ux.grid.ColumnHeaderGroup({
		        rows: [continentGroupRow]
		    });
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar :_this.toolBar,
			    structure: structure_1,
			    url: './fcLoanInvoce_getNeeYs.action',
			    needPage: true,
			    keyField: 'id',
//			    plugins: group,
			    isLoad: false,
		     	gatherCfg : {
			    	gatherOffset : "code",
			    	gatherCmns : [{cmn:'appAmount',dp:2},{cmn:'payAmount',dp:2}]
			    },
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
		tempId:null,
		getQparams : function(_this){
			var params = _this.queryFrm.getValues() || {};
			params.auditState = '2';/*已审批通过*/
			
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
		 * 批量放款时检查合约放款日是否是同一天
		 * @param {} grid
		 * @return {} 
		 */
		checkPayDate : function(grid){
			var flag = true;
			var selRows = grid.getSelRows();
			var prePayDate = null;
			for(var i=0,count=selRows.length; i<count; i++){
				var selRow = selRows[i];
				var payDate = selRow.get("payDate");
				if(!prePayDate){
					prePayDate = payDate;
				}else{
					if(prePayDate != payDate){
						flag = false;
						break;
					}
				}
			}
			if(!flag){
				ExtUtil.alert({msg : '批量放款时，选中记录的合约放款日必须都相同!'});
			}
			return flag;
		},
		winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent = _this.appgrid;
				parent.sysId = _this.globalMgr.sysId;
				parentCfg.parent = parent;
				var winModule=null;
				if(winkey && winkey == '收款'){
					winModule = "ReceiptsEdit";
					if(!parent.getSelId())return;
					showWindow();
				}else if(winkey && winkey == '打印放款单'){
					winModule = "LoanPrintDetail";
					if(!parent.getSelId())return;
					showDetailWindow();
				}
				function showDetailWindow(){
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
				}
				function showWindow(){
					if(_this.appCmpts[winkey]){
						_this.appCmpts[winkey].show(parentCfg);
					}else{ 
						Cmw.importPackage('pages/app/finance/deduct/receipts/'+winModule,function(module) {
						 	_this.appCmpts[winkey] = module.WinEdit;
						 	_this.appCmpts[winkey].show(parentCfg);
				  		});
					}
				}
			}
		}
	}
});

