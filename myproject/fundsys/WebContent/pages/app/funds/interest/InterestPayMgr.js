/**委托人利息支付 开发者 ： -------- 彭登登浩 Date : 2014-04-04
 * 
 **/
define(function(require, exports) {
	exports.viewUI = {
	appgrid :null,
	queryFrm : null,
	toolBar : null,
	tab : null,
	params : null,
	appMainlPnl : null,
	
	/**
	 * 设置参数
	 */
	setParams : function(tab,params){
		this.tab = tab;
		this.params = params;
	},
	
	
	getMainUI : function(tab, params) {
		var  _this = this;
		this.setParams(tab, params);
		if(!this.appMainlPnl){
			this.getQueryFrm();
			this.getToolBar();
			this.getAppGrid();
			this.appMainlPnl = new Ext.Panel({items:[this.queryFrm,this.toolBar,this.appgrid]});
		}
		this.appMainlPnl.on('afterrender',function(cmpt){
				_this.doResize();
		});
		return this.appMainlPnl;
	},
	/**
	 *  刷新方法
	 */
	refresh :function(){
		
	},
	/**
	 * 改变大小的时候发生的事件
	 */
	doResize : function(){
		var tabWidth = this.tab.getWidth();
		var tabHeight = this.tab.getHeight();
		this.resize(tabWidth, tabHeight);
	},
	/**
	 * 设置面板的大小
	 */
	resize : function(tabWidth,tabHeight){
		this.appMainlPnl.setWidth(tabWidth);
		this.appMainlPnl.setHeight(CLIENTHEIGHT);	
		this.queryFrm.setWidth(tabWidth);	
		this.appgrid.setWidth(tabWidth);
		var queryFrmHeight = this.queryFrm.getHeight();
		var appGridHieght = CLIENTHEIGHT- 85 - queryFrmHeight;
		this.appgrid.setHeight(appGridHieght);
		this.appMainlPnl.doLayout();
	},
	/**
	 * 创建查询面板
	 */
	getQueryFrm : function(){ 
		var txt_Name = FormUtil.getTxtField({fieldLabel : '委托人姓名',name : 'name'});
		var txt_entCode = FormUtil.getTxtField({fieldLabel : '委托人编号',name : 'entCode'});
		var mon_appAmount = FormUtil.getMoneyField({fieldLabel : '委托金额',name : 'appAmount'});
		var dat_xpayDate = FormUtil.getDateField({fieldLabel : '应付息日期',name : 'xpayDate'});
		var mon_iamount = FormUtil.getMoneyField({fieldLabel : '利息金额	',name : 'iamount'});
		var mon_yamount = FormUtil.getMoneyField({fieldLabel : '已付息金额	',name : 'yamount'});
		var mon_riamount = FormUtil.getMoneyField({fieldLabel : '退回利息	',name : 'riamount'});
		var layout_fields = [{cmns : 'THREE',fields : [txt_Name,txt_entCode,mon_appAmount,dat_xpayDate,mon_iamount,mon_yamount,mon_riamount]}];
		this.queryFrm = FormUtil.createLayoutFrm({title:'查询条件'}, layout_fields);
		return this.queryFrm ;
	},
	/**
	 * 创建工具栏
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
				_this.query();
			}
		},{/*重置*/
			token : '重置',
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls:'page_reset',
			tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
			handler : function(){
				_this.queryFrm.reset();
			}
		},{type:"sp"},{/*付款*/
			token : '提交',
			text : Btn_Cfgs.SUBMIT_BTN_TXT,
			iconCls:Btn_Cfgs.SUBMIT_CLS,
			tooltip:Btn_Cfgs.SUBMIT_TIP_BTN_TXT,
			handler : function(){
				_this.submit();
			}
		}/*,{type:"sp"},{
			token : '导出',
			text : Btn_Cfgs.EXPORT_BTN_TXT,
			iconCls:Btn_Cfgs.EXPORT_CLS,
			tooltip:Btn_Cfgs.EXPORT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doExport(_this);
			}
		}*/
		];
		this.toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'right',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
		return this.toolBar;
	},
	/**
	 *创建appgGrid
	 */
	getAppGrid : function(){
			var _this = this;
			var structure_1 = [{
				header: '委托人编号',
				name :'entCode'
			},{
			  	header: '委托人姓名',
				name :'name'
			},{
				header: '委托金额',
				name :'appAmount'
			},{
				header: '可用委托金额',
				name :'uamount'
			},{
				header :'撤资金额',
				name :'bamount'
			},{
				header :'利息金额',
				name :'iamount'
			},{
				header :'已付息金额',
				name :'yamount'
			},{
				header :'退回利息',
				name :'riamount'
			}];
			
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar :_this.toolBar,
			    structure: structure_1,
			    url: './fuInterest_paylist.action',
			    needPage: true,
			    keyField: 'entrustCustId',
			    isLoad: false,
			    listeners : {
				   	render : function(grid){
//				   		 _this.query();
				   	}
			    }
			});
			this.appgrid=  appgrid_1;
			return appgrid_1;
		},
		
		getQparams : function(){
			
		},
		/**
		 * 查询方法
		 */
		query : function(){
			var params = this.getQparams();
			EventManager.query(this.appgrid,params);
		},
		/**
		 * 每日利息提交和审批
		 * @type 
		 */
		submit: function(){
			
		}	
	}
});
	
