/**
 * 费用报销单
 * 
 * @author liting
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		appForm : null,
		tab : null,
		params : null,
		createButton : null,
		createAttachMentFs : null,
		appgrid_1 : null,
		appgrid_2 : null,
		toolbar : null,
		globalMgr : null,
		appCmpts :{},
		/**
		 * 设置参数
		 */
		setParams : function(tab, params) {
			this.tab = tab;
			this.params = params;
		},
		/**
		 * 主UI 界面开始点
		 */
		getMainUI : function(tab, params) {
			this.setParams(tab, params);
			if (!this.appMainPanel) {
				this.craeteMainPnl();
			}
			var _this = this;
			tab.notify = function(_tab) {
				_this.setParams(_tab, _tab.params);
				_this.refresh();
			};
			return this.appMainPanel;
		},
		/**
		 * 创建主面板
		 */
		craeteMainPnl : function() {
			this.appForm = this.createFrmPnl();
			this.appgrid_1 = this.createDetailPnl();
			this.appgrid_2 = this.createDetailPnl2();
			this.appForm.add(this.tabPal());
			var appPanel = new Ext.Panel({
			autoHeight: true});
			appPanel.add(this.appForm);
			
			this.appForm.add(this.createAttachMentFs(this));
			var buttonPanel = this.createButton();
			this.toolbar = this.createToolBar();
			this.appForm.add(buttonPanel);
			this.appMainPanel = appPanel;
			
			return this.appMainPanel;
		},
		/**
		 * 创建tab面板
		 */
		tabPal : function() {
			var _this = this;
			var tabPanel = new Ext.TabPanel({
						items : [_this.appgrid_1, _this.appgrid_2],
						activeTab : 0
					});
/*			tabPanel.on('tabchange',function(){
				if(_this.appgrid_2.render){
//					alert("sss");
//					_this.appgrid_2.
				}else if(_this.appgrid_1.render){
					alert("asd");
				}
			});*/
			return tabPanel;
		},
		/**
		 * form表单
		 */
		createFrmPnl : function() {
			var _this = this;
			var wd = 240;
			//TODO 1.“出差报销”，“报销单编号”，“出差日期”去掉。
			/*var txt_code = FormUtil.getTxtField({
						fieldLabel : '报销单编号',
						name : 'code',
						"width" : wd,
						"maxLength" : "20"
					});*/

			/*var rad_travelType = FormUtil.getRadioGroup({
						fieldLabel : '出差报销',
						name : 'Isenabled',
						"width" : wd,
						"allowBlank" : false,
						"items" : [{
									"boxLabel" : "出差报销",
									"name" : "Isenabled",
									"inputValue" : "1"
								}, {
									"boxLabel" : "非出差报销",
									"name" : "Isenabled",
									"inputValue" : "2"
								}]
					});*/
			/*var txt_travelcode = FormUtil.getTxtField({
						fieldLabel : '报销单编号',
						name : 'code',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : "20"
					});*/
			/*var comp_travelopDate = FormUtil.getMyCompositeField({
						itemNames : 'startDate,endDate',
						sigins : null,
						fieldLabel : '出差日期',
						width : 240,
						sigins : null,
						name : 'comp_opDate',
						items : [txt_startDate, {
									xtype : 'displayfield',
									value : '至'
								}, txt_endDate]
					});*/
			
			//2. 加“费用所属日期”字段  costBelongDate,是一个"起，止日期框";字段为costStartDate，costEndDate
			var txt_procid = FormUtil.getHidField({
						name : 'procid',
						value : '-1'
					});
			var txt_code = FormUtil.getHidField({
						name : 'code',
						"value" : 1
					})
			var txt_costStartDate = FormUtil.getDateField({
						fieldLabel : '起始时间',
						name : 'costStartDate',
						width : 110
					});
			var txt_costEndDate = FormUtil.getDateField({
						name : 'costEndDate',
						width : 110
					});
			var txt_costBelongDate = FormUtil.getMyCompositeField({
						itemNames : 'costStartDate,costEndDate',
						fieldLabel : '费用所属日期',
						width:350,
						"allowBlank" :false,
						items : [txt_costStartDate, {
									xtype : 'displayfield',
									value : '至'
								}, txt_costEndDate]
						});
			var txt_loanMan = FormUtil.getReadTxtField({
						fieldLabel : '报销人',
						name : 'costMan'/*, 
						"width" : wd*/
					});
			var bdat_modifyTime = FormUtil.getDateField({
						fieldLabel : '报销日期',
						name : 'costDate'/*,
						"width" : wd*/
					});
			var txt_deptId = FormUtil.getTxtField({
						fieldLabel : '部门',
						name : 'deptId'/*,
						"width" : wd*/
					});
			var txa_travelreason = FormUtil.getTxtField({
						fieldLabel : '单据张数',
						name : 'reason'/*,
						"width" : wd*/
					});
			var txt_startDate = FormUtil.getDateField({
						name : 'startDate',
						width : 100
					});
			var txt_endDate = FormUtil.getDateField({
						name : 'endDate',
						width : 100
					});
			var rad_travelSel = FormUtil.getRadioGroup({
						fieldLabel : '是否有借支单',
						name : 'Isenabled',
						"width" : wd,
						"allowBlank" : false,
						"items" : [{
									"boxLabel" : "有借支单",
									"name" : "Isenabled",
									"inputValue" : "1"
								}, {
									"boxLabel" : "无借支单",
									"name" : "Isenabled",
									"inputValue" : "2"
								}]
					});
					//TODO 3. 付款方式 字段由财务填写。
			var rad_loanType =FormUtil.getTxtField({
						fieldLabel : '付款方式',
						name : 'loanType',
						"width" : wd,
						"allowBlank" : false		
			});
			/*var rad_loanType = FormUtil.getRadioGroup({
						fieldLabel : '付款方式',
						name : 'loanType',
						"width" : wd,
						"allowBlank" : false,
						"items" : [{
									"boxLabel" : "现金",
									"name" : "loanType",
									"inputValue" : "1"
								}, {
									"boxLabel" : "电汇",
									"name" : "loanType",
									"inputValue" : "2"
								}, {
									"boxLabel" : "支票",
									"name" : "loanType",
									"inputValue" : "3"
								}]
					});*/
			var txa_monBank = FormUtil.getTxtField({
						fieldLabel : '收款帐号',
						name : 'reason',
						"width" : wd,
						"allowBlank" : false
					});
			var txa_monBankId = FormUtil.getTxtField({
						fieldLabel : '帐号名',
						name : 'reason',
						"width" : wd,
						"allowBlank" : false
					});
			var txa_monBanN = FormUtil.getTxtField({
						fieldLabel : '开户行',
						name : 'reason',
						"width" : wd,
						"allowBlank" : false
					});

			var mon_amount = FormUtil.getMoneyField({
						fieldLabel : '报销总金额',
						name : 'amount',
//						"width" : wd,
						listeners : {
							change : function(e, newValue, oldValue) {
								var newValue = Cmw.cmycurd(newValue);
								mon_amdouDa.setValue(newValue);
							}
						}
					});
			var mon_amdouDa = FormUtil.getReadTxtField({
						fieldLabel : '金额大写',
						name : 'damountd'/*,
						"width" : wd*/
					});
					//TODO 4. "原借款金额" 改为 "原借款余额"。
			var txt_amount = FormUtil.getMoneyField({
						fieldLabel : '原借款余额',
						name : 'amount',
//						width : wd,
						allowBlank : false,
						value : 0,
						listeners : {
							change : function(e, newValue, oldValue) {
								var newValue = Cmw.cmycurd(newValue);
								mon_amdou.setValue(newValue);
							}
						}
					});
			var mon_amdou = FormUtil.getReadTxtField({
						fieldLabel : '原借款余额大写',
						name : 'damountd'/*,
						"width" : wd*/
					});

			var txt_amoFiled = FormUtil.getMoneyField({
						fieldLabel : '应退余款',
						name : 'amount',
//						width : wd,
						allowBlank : false,
						value : 0,
						listeners : {
							change : function(e, newValue, oldValue) {
								var newValue = Cmw.cmycurd(newValue);
								mon_adoufile.setValue(newValue);
							}
						}
					});
			var mon_adoufile = FormUtil.getReadTxtField({
						fieldLabel : '应退余款金额大写',
						name : 'amounts'/*,
						"width" : wd*/
					});
			var txa_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						"width" : 860
					});
			/*var oneLine = [mon_amount, txt_amount, txt_amoFiled];
			var twoLine = [mon_amdouDa, mon_amdou, mon_adoufile];*/
			//TODO  5. 付款方式，帐号名，收款帐号，开户行，默认收缩起来。
			var  loanManInfo=[{
						 cmns : FormUtil.CMN_TWO,
						 fields : [txa_monBanN,txa_monBankId, rad_loanType,txa_monBank]
					}];
			var loanManfieldset = FormUtil.createLayoutFieldSet({
						collapsed :true,
						title : '用户信息填写',
						labelWidth : 110,
						TWO_WIDTH : 400
					},loanManInfo);
			var layout_fields = [{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_loanMan , txt_deptId , bdat_modifyTime , 
								txt_amount , txa_travelreason, rad_travelSel, mon_amount ,txt_amoFiled,
								txt_costBelongDate,txt_code]
					},txa_remark , loanManfieldset];
				/*fields : [txt_code, bdat_modifyTime, txt_loanMan, txt_deptId,
						rad_travelType, txa_travelreason, txt_travelcode,
						comp_travelopDate, rad_travelSel, txa_monBank,
						txa_monBankId, rad_loanType, oneLine, twoLine]
			}, txa_monBanN, txa_remark];*/
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var filset = FormUtil.createLayoutFieldSet({
						title : "费用报销审批单"
					}, layout_fields);
			var frm_cfg = {
						url : './oaCostOff_save.action',
						labelWidth : 115,
						autoHeight: true,
						items : [new Ext.Container({
							html : '<div style = "height : 40px;"><div style = "left : 650px;color : blue ; position : fixed ;font-size : 30px;z-index : 99">费用报销单</div></div>'
//						html : "<font size='6' color='blue' position><center>费用报销单</center></font>"
				}), filset]
			};
			var applyForm = FormUtil.createFrm(frm_cfg);
			this.appForm = applyForm;
			// _this.attachMentFs.reload(params);
			return this.appForm;
		},
		/**
		 * 刷新方法
		 */
		refresh : function() {
			//TODO  费用报销项 表结构还没整理好
//				var _this=this;
//				EventManager.add("./sysUser_get.action?userId="+, _this.appForm,{sfn : function(formdate) {
//					_this.appForm.setFieldValues(formdate);
//				}
//			});
		},
		/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
		createAttachMentFs : function(_this) {
			/*
			 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录， mortgage_path
			 * 定义在 resource.properties 资源文件中 isSave : 附件上传后，是否保存到 ts_attachment
			 * 表中。 true : 保存 params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中
			 * ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
			 * params 可在 reload 方法中提供 ----
			 */
			var attachMentFs = new Ext.ux.AppAttachmentFs({
						title : '相关材料附件',
						isLoad : false,
						dir : 'mort_path',
						isSave : true,
						isNotDisenbaled : true
					});
			return attachMentFs;
		},

		createButton : function() {
			var _self=this;
			var button_save = new Ext.Button({
						text : '保存',
						width : 100,
						handler : function(){
							EventManager.frm_save(_self.appForm,{beforeMake : function(beforeData){
								var costManVal = _self.appForm.getValueByName("costMan").split("##")[0];
								Cmw.print(costManVal);
//								_self.globalMgr.costMan=costManVal;
								Cmw.print(_self.globalMgr.costMan.getValue()+"...........");
								
							}});		
						}			
					})
			var button_reset = new Ext.Button({
						text : '重置',
						width : 100
					})
			var buttons = [button_save, button_reset];
			var btnPanel = new Ext.Panel({
						buttonAlign : 'center',
						buttons : buttons
					});
			return btnPanel;
		},

		/**
		 * 创建详情面板
		 */
		createDetailPnl : function() {
			var _this = this;
			var structure_1 = [{
						header : '费用项',
						name : 'citemId'
					}, {
						header : '用途',
						name : 'useDesc'
					}, {
						header : '金额',
						name : 'amount'
					}, {
						header : '备注',
						name : 'remark'
					},{
						header : '核算部门',
						name : 'deptid'
					}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
						title : '报销费用项',
						structure : structure_1,
						needPage : false,
						width : true,
						height : 150,
						tbar : _this.createToolBar(),
						isLoad : false,
						keyField : 'id',
						url : ''
					});
			this.globalMgr._appgrid=appgrid_1;
			return appgrid_1;
		},
		/**
		 * 创建详情面板
		 */
		/**
		 * 创建详情面板
		 */
				//TODO 6. 将Tab选项卡 “核销借款单” 改为 “借款单明细”， 借款金额 改为 借款余额,本次报销金额 改为 本次冲销金额。
		createDetailPnl2 : function() {
			var selId = null;
			var _this = this;
			var structure_2 = [{
						header : '借款单编号',
						name : 'code'
					}, {
						header : '借款单日期',
						name : 'data'
					}, {
						header : '借款余额',
						name : 'amount'
					}, {
						header : '本次冲销金额',
						name : 'useDesc'
					}, {
						header : '状态',
						name : 'citemId'
					}, {
						header : '备注',
						name : 'remark'
					}];
			var appgrid_2 = new Ext.ux.grid.AppGrid({
						//title : '核销借款单',	
						title : '借款单明细',
						structure : structure_2,
						needPage : false,
						width : true,
						tbar : _this.createTwoToolBar(),
						height : 150,
						isLoad : false,
						keyField : 'id',
						url : ''
					});
			this.globalMgr._appgrid=appgrid_2;
			return appgrid_2;
		},
		/**
		 * 创建toolbar工具栏
		 * 
		 */
		createToolBar : function() {
			var _self = this;
			//TODO 7.Tab "报销费用项" 加一个 “选择出差日志”的按钮， 报销费用项中加一列“核算部门”字段，可选择部门数据。 表格中的“费用项”，“用途”，“金额”，“核算部门”必填。
			var barItems = [{
						token : '添加报销费用单',
						text :'添加报销费用单',  /*-- 选择申请单--*/
						iconCls : Btn_Cfgs.INSERT_APPLY_CLS,
						tooltip : Btn_Cfgs.INSERT_APPLY_TIP_BTN_TXT,
						handler : function(){
							_self.globalMgr.winEdit.show({key : this.token,_self : _self,_appgrid :_self.globalMgr._appgrid});
						}
					}, {
						token : '删除报销单',
						text : '删除报销单', /*-- 删除 --*/
						iconCls : Btn_Cfgs.INSERT_DEL_BTN_CLS,
						tooltip : Btn_Cfgs.INSERT_DEL_TIP_BTN_TXT
					},{
						//DEPARTMENT_ADD_BTN_TXT : '添加部门',
						token : '选择出差日志',
						text : '选择出差日志' , /*-- 选择申请单--*/
						iconCls : Btn_Cfgs.INSERT_OVERTIME_CLS,
						tooltip : Btn_Cfgs.INSERT_DEL_TIP_BTN_TXT
					}];
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'left',
						controls : barItems
					});
			return appBar;
		},
		//借款单明细的工具栏
		createTwoToolBar : function() {
			var _self = this;
			//TODO 7.Tab "报销费用项" 加一个 “选择出差日志”的按钮， 报销费用项中加一列“核算部门”字段，可选择部门数据。 表格中的“费用项”，“用途”，“金额”，“核算部门”必填。
			/*var barItems = [{
						text : Btn_Cfgs.INSERT_APPLY_BTN_TXT, -- 选择申请单--
						iconCls : Btn_Cfgs.INSERT_APPLY_CLS,
						tooltip : Btn_Cfgs.INSERT_APPLY_TIP_BTN_TXT
					}, {
						text : Btn_Cfgs.INSERT_DEL_BTN_TXT, -- 删除 --
						iconCls : Btn_Cfgs.INSERT_DEL_BTN_CLS,
						tooltip : Btn_Cfgs.INSERT_DEL_TIP_BTN_TXT
					}];*/
			
			var barItems = [{
						token : '添加借款单',
						text :'添加借款单', /*-- 选择申请单--*/
						iconCls : Btn_Cfgs.INSERT_APPLY_CLS,
						tooltip : Btn_Cfgs.INSERT_APPLY_TIP_BTN_TXT,
						handler :function(){
							_self.globalMgr.winEdit.show({key : this.token,_self : _self});
						}
					}, {
						token : '删除借款单',
						text : '删除借款单', /*-- 删除 --*/
						iconCls : Btn_Cfgs.INSERT_DEL_BTN_CLS,
						tooltip : Btn_Cfgs.INSERT_DEL_TIP_BTN_TXT
					}];
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'left',
						controls : barItems
					});
			return appBar;
		},
		/**
		 * 组件销毁方法
		 */
		destroy : function() {
			if (this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		},
		globalMgr :{
			_appgrid : null,
			appform : null,
			activeKey : null,
			costMan : null,
			tokenMgr:{
				 INSERT_COSTITEMS_LIST : '添加报销费用单'	,
				 INSERT_CLOANRELATION_LIST : '添加借款单'
			},
		winEdit : {
			show : function(parentCfg) {
				var _this = parentCfg._self;
				var tokenMgr = _this.globalMgr.tokenMgr;
				var winkey = parentCfg.key;//parentCfg.key ： 添加报销费用单
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;//获取当前表格
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else {
					var winModule=null;
					switch(winkey){
						case tokenMgr.INSERT_OUTLAY_LIST :{
							    winModule = "CostItemsEdit";
								break;
							}
						case token.INSERT_CLOANRELATION_LIST :{
								winModule = 'CloanRelationEdit';
								break;
							}	
						}
					Cmw.importPackage('pages/app/workflow/bussProcc/formUIs/' + winModule,function(module) {
							_this.appCmpts[winkey] = module.WinEdit;
							_this.appCmpts[winkey].show(parentCfg);
					});
				}
			}
		}
		
		}
	}
});