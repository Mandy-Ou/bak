/**
 * #DESCRIPTION#
 * 
 * @author smartplatform_auto
 * @date 2013-08-14 04:22:09
 * 自有资金管理(详情)
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		appGvlistWin : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增 */
		appFrm : null,
		appWin : null,
		sysId : null,
		appGrid : null,
		selId : null,
		appFrm : null,
		toolBar : null,
		globalMgr : null,
		nodeId : null,
		setParentCfg : function(parentCfg) {
			this.parentCfg = parentCfg;
			// --> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.nodeId = parentCfg.nodeId;
			this.sysId = parentCfg.parent.sysId;
			this.selId = parentCfg.parent.getSelId();
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function() {
			this.appFrm = this.getQueryFrm();
			this.toolBar = this.getToolBar();
			this.appGrid = this.createAppGrid();
			var appMain = new Ext.Panel();
			appMain.add(this.appFrm);
			appMain.add(this.appGrid);
			this.globalMgr = this.globalMgr;
			var _this = this;
			this.appWin = new Ext.ux.window.MyWindow ({
						width : 910,
						height :400,modal:true,
						items:[appMain]
					});
			
		},
		/**
		 * 获取查询Form 表单
		 */
		getQueryFrm : function() {
			var self = this;
			var callback = function(cboGrid, selVals) {
				var grid = cboGrid.grid;
				var record = grid.getSelRow();
			}
			var rad_isBackAmount = FormUtil.getRadioGroup({
						fieldLabel : '收支类型',
						name : 'waterType',
						width : 125,
						items : [{
									boxLabel : '支出',
									name : 'waterType',
									inputValue : 1
								}, {
									boxLabel : '收入	',
									name : 'waterType',
									inputValue : 2
								}]
					});

			var txt_raDispFormula = FormUtil.getMoneyField({
						fieldLabel : '收款金额',
						width : 100,
						name : 'amounts'
					});
			var txt_startDate1 = FormUtil.getDateField({name:'startDate',width:90});
			var txt_endDate1 = FormUtil.getDateField({name:'endDate',width:90});
			var comp_appDate = FormUtil.getMyCompositeField({
				itemNames : 'startDate,endDate',
				sigins : null,
				 fieldLabel: '日期',width:210,sigins:null,
				 name:'comp_estartDate',
				 items : [txt_startDate1,{xtype:'displayfield',value:'至'},txt_endDate1]
				});			
			var layout_fields = [{
						cmns : 'THREE',
						fields : [rad_isBackAmount, txt_raDispFormula,comp_appDate]
					}]
			var queryFrm = FormUtil.createLayoutFrm({heigt : 150}, layout_fields);
			return queryFrm;
		},
		/**
		 * 工具栏
		 */
		getToolBar : function() {
			var self = this;
			var barItems = [{
						token : '查询',
						text : Btn_Cfgs.QUERY_BTN_TXT,
						iconCls : 'page_query',
						tooltip : Btn_Cfgs.QUERY_TIP_BTN_TXT,
						handler : function() {
							self.query(self);
						}
					}, {
						token : '重置',
						text : Btn_Cfgs.RESET_BTN_TXT,
						iconCls : 'page_reset',
						tooltip : Btn_Cfgs.RESET_TIP_BTN_TXT,
						handler : function() {
							self.appFrm.reset();
						}
					}, {
						type : "sp"
					},{
						token : '编辑', text : Btn_Cfgs.MODI_BTN_TXT,
						  iconCls : Btn_Cfgs.MODI_CLS, tooltip :
						  Btn_Cfgs.MODI_TIP_BTN_TXT, handler : function(){
							  var id = self.appGrid.getSelId(); 
							  if(!id){
							  		ExtUtil.alert({msg:"请选择表格中的数据！"}); return;
						  	   }
							  self.btnClick({key:"编辑",optionType:OPTION_TYPE.EDIT,self:self}); } }
//					 , {
//						token : '导出',
//						text : Btn_Cfgs.EXPORT_BTN_TXT,
//						iconCls : 'page_exportxls',
//						tooltip : Btn_Cfgs.EXPORT_TIP_BTN_TXT,
//						handler : function() {
//							self.doExport();
//						}
//					}
					];
				toolBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : barItems,
						rightData : {
							saveRights : true
						}
					});
			return toolBar;
		},
		/**
		 * 显示弹出窗口
		 * 
		 * @param _parentCfg
		 *            弹出之前传入的参数
		 */
		show : function(_parentCfg) {
			var _this = this;
			if (_parentCfg)
				this.setParentCfg(_parentCfg);
			if (!this.appWin) {
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show(this.parent.getEl(), function() {
				_this.appGrid.reload({
							ownfundsId : _this.selId
						});
					});

		},
		/**
		 * 改变默认工具栏的设置子类必须重写的方法
		 * 
		 * @author tl
		 */

		changeBarItems : function(barItems) {
			return null;
		},
		/**
		 * 销毁组件
		 */
		destroy : function() {
			this.appWin.close(); // 关闭并销毁窗口
			this.appWin = null; // 释放当前窗口对象引用
		},
		/**
		 * 当数据保存成功后，执行刷新方法 [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data) {
			var _this = exports.WinEdit;
			if (_this.parentCfg.self.refresh)
				_this.parentCfg.self.refresh(_this.optionType, data);
		},
			
		/**
		 * 创建Form表单
		 */
		createAppGrid : function() {
				var _this = this;
			var structure_1 = [{
						header : '收支类型',
						name : 'waterType',
						width : 60,
						renderer : Render_dataSource.TypeRenders
					}, {
						header : '贷出/存入金额',
						name : 'amounts',
						width : 150,
						renderer : function(val) {
							val = Cmw.getThousandths(val)
							return val
						}
					}, {
						header : '银行',
						name : 'bankName',
						width : 150
					}
					, {
						header : '费用类型',
						name : 'name',
						width : 130
					}, {
						header : '银行帐号',
						name : 'account',
						width : 200
					}, {
						header : '业务标识',
						name : 'bussTag',
						width : 150,
						renderer : Render_dataSource.bussTagRender
					}
					, {
						header : '时间',
						name : 'opdate',
						width : 150,
						renderer : Render_dataSource.bussTagRender
					}, {
						header : '备注',
						name : 'remark',
						width : 150,
						renderer : Render_dataSource.bussTagRender
					}
					];
			var appgrid_1 = new Ext.ux.grid.AppGrid({// 创建表格
				structure : structure_1,
				tbar : _this.toolBar,
				url : './fcFundsWater_list.action',
				height : 330,
				needPage : true,
				keyField : 'id',
				isLoad : false
			});
			return appgrid_1;
		},
		/**
		 * 为表单元素赋值
		 */
		setFormValues : function() {

		},
		/**
		 * 上一条
		 */
		preData : function() {

		},
		/**
		 * 下一条
		 */
		nextData : function() {

		},
			getQparams : function() {
				var params = this.appFrm.getValues() || {};
				return params;
			},
			/**
			 * 查询方法
			 * 
			 * @param {}
			 *            _this
			 */
			query : function() {
				var params = this.getQparams();
				if (params) {
					EventManager.query(this.appGrid, params);
				}
			},
			/**
			 * 导出Excel
			 * 
			 * @param {}
			 *            _this
			 */
			doExport : function() {
				var params = this.getQparams();
				var token = this.nodeId;
				EventManager.doExport(token, params);
			},
				btnClick : function(Button,e){
				var self = this;
				var selId=self.selId;
				var data = self.appGrid.getCmnVals("bankName,account,remark");
				var parentCfg = {parent :Button,optionType : OPTION_TYPE.EDIT,self: self,selId:selId,data:data}
				parentCfg.parent=self.appGrid;
				Cmw.importPackage('pages/app/finance/fcinit/ownfunds/FundsAddEdit',function(module) {
				 	self.appGvlistWin = module.WinEdit;
				 	self.appGvlistWin.show(parentCfg);
			  			});
			},
		/**
		 * 重置数据
		 */
			
		resetData : function() {
			var _this=this;
			this.appGrid.reload();
		}
	};
});
