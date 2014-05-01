Ext.namespace("cmw.skythink");
/**
 * 正常还款金额流水
 * 
 */ 
cmw.skythink.NomalOverMgr = function(){
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.NomalOverMgr,Ext.util.MyObservable,{
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
		
		var layout_fields = [{cmns:'THREE',fields:[rad_custType,txt_custName,txt_code,txt_payName,txt_payBank,txt_payAccount]}]

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
		},{/*详情*/
			token : '详情',
			text : Btn_Cfgs.DETAIL_BTN_TXT,
			iconCls:Btn_Cfgs.MENU_detail_BTN_CLS,
			tooltip:Btn_Cfgs.DETAIL_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:'详情'});
			}
		},{/*导出*/
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:Btn_Cfgs.EXPORT_CLS,
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doExport(_this);
			}}];
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
		    header: '未收合计',
		    name: 'ztotalAmount',
		    width: 75,
		    renderer : function(val){return Cmw.getThousandths(val);}
		},
		{
		    header: '还款状态',
		    name: 'state',
		    width: 75,
		    renderer : function(val){
		    	switch(val){
		    		case '4': val = "部分收款"; break;
		    		case '6': val = "结清"; break;
		    	}
		    	return val;
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
			
		var continentGroupRow = [{header: '合同信息', colspan: 2, align: 'center'},
			{header: '客户信息', colspan: 2, align: 'center'},
			{header: '还款账号信息', colspan: 3, align: 'center'},
			{header: '期数', colspan: 2, align: 'center'},
			{header: '本金', colspan: 2, align: 'center'},
			{header: '利息', colspan: 2, align: 'center'},
			{header: '管理费', colspan: 2, align: 'center'},
			{header: '合计', colspan: 2, align: 'center'},
			{header: '', colspan: 4, align: 'center'}];
		 var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [continentGroupRow]
	    });

		var appgrid_1 = new Ext.ux.grid.AppGrid({
			tbar :_this.toolBar,
		    structure: structure_1,
		    url: './fcNomalDeduct_RepDetail.action',
		    needPage: true,
		    keyField: 'contractId',
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
			   	}, 	
			   	rowdblclick : function() {
				//鼠标双击事件
				var selRow = appgrid_1 .getSelRow();//获取对象所在的行
				if(selRow != null){
					_this.globalMgr.winEdit.show({self : _this,key : '详情'});
				}
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
			params.excelType = 2;
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
				var selId = parent.getSelId();
				if(!selId){
					return;
				}
				parent.sysId = _this.globalMgr.sysId;
				parentCfg.parent = parent;
				var winModule=null;
				if(winkey && winkey == '详情'){
					if(!parent.getSelId())return;
					winModule = "NomalOverDetail";
					showWindow();
				}
				function showWindow(){
					if(_this.appCmpts[winkey]){
						_this.appCmpts[winkey].show(parentCfg);
					}else{ 
						Cmw.importPackage('pages/app/finance/deduct/turnover/nomalover/'+winModule,function(module) {
						 	_this.appCmpts[winkey] = module.WinEdit;
						 	_this.appCmpts[winkey].show(parentCfg);
				  		});
					}
				}
			}
		}
		
	}
	
});
