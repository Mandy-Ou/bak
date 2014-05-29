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
//			this.getQueryFrm();
			this.getToolBar();
			this.getAppGrid();
			this.appMainlPnl = new Ext.Panel({items:[/*this.queryFrm,*/this.toolBar,this.appgrid]});
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
//		this.queryFrm.setWidth(tabWidth);	
		this.appgrid.setWidth(tabWidth);
//		var queryFrmHeight = this.queryFrm.getHeight();
//		var appGridHieght = CLIENTHEIGHT- 85 - queryFrmHeight;
		this.appgrid.setHeight(800);
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
		var barItems = [{	token : '选择其他',
			text : Btn_Cfgs.QUERYS_BTN_TXT,
			iconCls:'page_holidaysearch',
			tooltip:Btn_Cfgs.QUERYS_TIP_BTN_TXT,
			handler : function(){
						var that = this;
						_this.globalMgr.show({key:that.token,self:_this});}
		},{
				text : Btn_Cfgs.CAPSAVE_LABEL_TEXT,
				iconCls:'page_edit',
				tooltip:Btn_Cfgs.CAPSAVE_LABEL_TEXT,
				token : '保存',
				handler : function(){
					    var that = this;
						_this.globalMgr.winEdit.show({key:that.token,self:self});
					}
			},{
				text : Btn_Cfgs.DELETE_BTN_TXT,
				iconCls:'page_delete',
				tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
				token : '删除',
				handler : function(){
					var store = _this.appgrid.getStore();//获取grid存储的数据
					var id = _this.appgrid.getSelRows();//返回选中的行对象:数组的形式
					store.remove(id);
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
	showCustomerDialog : function() {
			var _this = this;
			var parentCfg = {
				callback : function(record) {
						Cmw.print(record['0'].data);
//						_this.appgrid.addRecord(record['0'].data);	
							for(var i=0;i<record.length;i++){
								_this.appgrid.addRecord(record[i].data);
							}
				}
			};
			if (this.customerDialog) {
				this.customerDialog.show(parentCfg);
			} else {
			var _this = this;
			Cmw.importPackage('pages/app/dialogbox/InterestDialogbox',
			function(module) {_this.customerDialog = module.DialogBox;_this.customerDialog.show(parentCfg);});
			}
		},
	/**
	 *创建appgGrid
	 */
	getAppGrid : function(){
			var _this = this;
			var structure_1 = [{
				header: '付息状态',
				name :'status',
					renderer: function(val) {
				    return Render_dataSource.isInterestRender(val);
				    }
			},{
			  	header: '委托人姓名',
				name :'name'
			},{
				header: '委托合同编号',
				name :'code'
			},{
				header: '委托金额',
				name :'appAmount',
					renderer: function(val) {
				    return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
				    }
			},{
				header :'委托期限',
				name :'yearLoan',
					renderer: function(x,y,z) {
				    return x+'年'+z.get('monthLoan')+'月';
				    }
			},{
				header :'委托期限(月)',
				name :'monthLoan',
				hidden:true
			},{
				header :'利息',
				name :'rate',
					renderer: function(x,y,z) {
  					return x+Render_dataSource.rateUnit_datas(z.get('unint'));
						}
			},{
				header :'利息单位',
				name :'unint',
				hidden:true
			},{
				header :'付息日期',
				name :'xpayDate'
			},{
				header :'付息金额',
				name :'iamount',
					renderer: function(val) {
				    return (val && val>0) ? Cmw.getThousandths(val)+'元' : '';
				    }
			}];
			
			var appgrid_1 = new Ext.ux.grid.AppGrid({
				tbar :_this.toolBar,
			    structure: structure_1,
			    url: './fuInterest_paylist.action?cid='+'1',
			    needPage: false,
			    keyField: 'entrustCustId',
			    isLoad: true,
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
		
		globalMgr : {
		
			show :function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				var parent ={};
				
				parentCfg.parent = parent;				
				
				if(winkey=='选择其他'){
				_this.showCustomerDialog();
				}
				
			
			}
		
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
	
