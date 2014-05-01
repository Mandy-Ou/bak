Ext.namespace('skythink.cmw.report');
/**
 * 所有贷款客户贷款台账明细资料报表
 */
skythink.cmw.report.LedgerDeatilReport = function(){
	this.init(arguments[0]);
}
	
Ext.extend(skythink.cmw.report.LedgerDeatilReport,Ext.util.MyObservable,{
	initModule : function(tab,params){
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,
			getToolBar : this.getToolBar,
			getAppCmpt : this.getAppCmpt,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh
		});
	},
	changeSize : function(whArr){
		var w = whArr[0]-6;
		var h = whArr[1];
		if(h>0) h-=2;
		this.appPanel.setWidth(w);
		this.appPanel.setHeight(h);
	},
	destroyCmpts : function(){
		
	},
	/**
	 * 创建工具栏
	 */
	getToolBar : function(){
		var self = this;
		var toolBar = null;
		var barItems = [{
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls:'page_query',
			tooltip:Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.query(self);
			}
		},{type:"sp"},{
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:'page_exportxls',
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.doExport(self);
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return toolBar;
	},
	
	getAppCmpt :function(){
		var wd = 100;
		var structure_1 = [{
				header: '贷款申请贷款编号',
		    	width : wd,
		    	name: 'applyCode'
			},{
				header: '借款合同编号',
		    	width : wd,
		    	name: 'code'
			},{
				header: '借款人名称',
		    	width : wd,
		    	name: 'name'
	    	},{
	    		header: '借款人代码（组织机<br>构代码或个人证件号码）',
		    	width : 155,
		    	name: 'cardNum'
			},{
				header: '营业执照号码',
		    	width : wd,
		    	name: 'patentNumber'
			},{
				header: '地址',
		    	width : 180,
		    	name: 'address'
			},{
				header: '开户银行',
		    	width : wd,
		    	name: 'bankOrg'
			},{
				header: '账号',
		    	width : wd,
		    	name: 'account'
	    	},{
	    		header: '企业规模',
		    	width : wd,
		    	name: 'ecustomScale'
			},{
				header: '负责人',
		    	width : wd,
		    	name: 'contactor'
			},{
				header: '联系电话',
		    	width : wd,
		    	name: 'contacttel'
			},{
				header: '产品类型',
		    	width : wd,
		    	name: 'breedName'
			},{
				header: '信用等级评级结果',
		    	width : wd,
		    	name: 'custLevel'
			},{
				header: '借款合同金额',
		    	width : wd,
		    	name: 'appAmount',
		    	renderer : Render_dataSource.moneyRender
			},{
				header: '发放金额',
		    	width : wd,
		    	name: 'payAmount',//放款单放款金额
		    	renderer : Render_dataSource.moneyRender
			},{
				header: '贷款余额',
		    	width : wd,
		    	name: 'unAmount',//未放款金额
		    	renderer : Render_dataSource.moneyRender
			},{
				header: '预收利息月数',
		    	width : wd,
		    	name: 'ysRatMonth',
		    	renderer : function(val){
		    		if(val){
		    			val = val+'个月'
		    			return val;
		    		}
		    	}
			},{
				header: '预收利息',
		    	width : wd,
		    	name: 'ysRat',
		    	renderer : function(val){
		    		if(val){
		    			val =val.replaceAll(",","");
		    			val = Cmw.getThousandths(val)+'元'
		    			return val;
		    		}
		    		
		    	}
			},{
				header: '预收管理费月数',
		    	width : wd,
		    	name: 'ysMatMonth',
		    	renderer : function(val){
		    		if(val){
		    			val = val+'个月'
		    			return val;
		    		}
		    	}
			},{
				header: '预收管理费',
		    	width : wd,
		    	name: 'ysMat', 
		    	renderer : function(val){
		    		if(val){
		    			val =val.replaceAll(",","");
		    			val = Cmw.getThousandths(val)+'元'
		    			return val;
		    		}
		    		
		    	}
			},{
				header: '实收利息',
		    	width : wd,
		    	name: 'rat', // 实收利息之和
		    	renderer : Render_dataSource.moneyRender
			},{
				header: '（合同期限收息）<br>合计',
		    	width : wd,
		    	name: 'interest', // 还款计划利息之和
		    	renderer : Render_dataSource.moneyRender
			},{
				header: '实收管理费',
		    	width : wd,
		    	name: 'mat', // 实收管理费之和
		    	renderer : Render_dataSource.moneyRender
			},{
				header: '（合同期限<br>收管理费)合计',
		    	width : wd,
		    	name: 'mgrAmount', // 还款计划管理费之和
		    	renderer : Render_dataSource.moneyRender
			},{
				header: '抵押物名称',
		    	width : wd,
		    	name: 'mortName' 
			},{
				header: '抵押物价值',
		    	width : wd,
		    	name: 'mpVal' ,
		    	renderer : Render_dataSource.moneyRender
			},{
				header: '贷款性质',
		    	width : wd,
		    	name: 'loanNature' 
			},{
				header: '发放日期',
		    	width : 80,
		    	name: 'payDate' 
			},{
				header: '到期日期<br>（实际到期）',
		    	width : 80,
		    	name: 'realDate' 
			},{
				header: '合同到期日',
		    	width : 80,
		    	name: 'endDate' 
			},{
				header: '贷款展期<br>到期日期',
		    	width : 80,
		    	name: 'exteDate'
			},{
				header: '主办客<br>户经理',
		    	width : 80,
		    	name: 'managerName' 
			},{
				header: '协办客<br>户经理',
		    	width : 80,
		    	name: 'comanager' 
			},{
				header: '介绍人',
		    	width : 80,
		    	name: 'referrals'
			},{
				header: '借款合同id',
				name: 'loanId',
		    	width : 125,
		    	hidden : true,
		    	renderer : function(val,metaData, record, rowIndex, cellIndex, store){
		    		return val;
			    }
			}
		];

		var _this = this;
		var appgrid_1 = new Ext.ux.grid.AppGrid({
			tbar :this.toolBar,
		    structure: structure_1,
		    url: './fcLedgerDeatil_list.action',
		    needPage: true,
		    keyField: 'id',
		    isLoad: false,
		    listeners : {
		    	render : function(grid){
		    		_this.globalMgr.query(_this);
		    	}
		    }
		});
		this.globalMgr.appgrid = appgrid_1;
		return appgrid_1;
	},
	/**
	 * 刷新
	 */
	refresh : function(){
		
	},
	
	globalMgr : {
		appgrid : null,
		/**
		 * 查询方法
		 * @param {} _this
		 */
		query : function(_this){
			var sysId = _this.params.sysid;
			EventManager.query(_this.globalMgr.appgrid,{sysId:sysId});
		},
		
		doExport : function(_this){
			var params = _this.params;
			 var sysId = _this.params.sysid;
			 params.sysId = sysId;
			var token = _this.params.nodeId;
			EventManager.doExport(token,params);
		}
	},
	
	/**
	 * 销毁组件
	 */
	destroyCmpts : function(){
		
	}
})