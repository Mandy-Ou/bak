/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 放款通知书
 */
skythink.cmw.workflow.bussforms.PamountRecordsMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.PamountRecordsMgr,Ext.util.MyObservable,{
	initModule : function(tab,params){
		/*由必做或选做业务菜单传入的回調函数，主要功能：
		 * finishBussCallback : 当业务表单保存后，更新必做或选做事项为已做,
		 * unFinishBussCallback : 当删除业务表单后，取消已做标识
		 * */
		var finishBussCallback = tab.finishBussCallback;
		var unFinishBussCallback = tab.unFinishBussCallback;
		this.module = new Cmw.app.widget.AbsPanelView({
			tab : tab,
			params : params,/*apptabtreewinId,tabId,sysid*/
			getAppCmpt : this.getAppCmpt,
			getToolBar : this.getToolBar,
			createForm : this.createForm,
			createButton : this.createButton,
			createDetailPnl:this.createDetailPnl,
			getAppGrid : this.getAppGrid,
			getAppGrid_2 : this.getAppGrid_2,
			getAppForm : this.getAppForm,
			changeSize : this.changeSize,
			destroyCmpts : this.destroyCmpts,
			globalMgr : this.globalMgr,
			refresh : this.refresh,
			prefix : Ext.id(),
			finishBussCallback : finishBussCallback,
			unFinishBussCallback : unFinishBussCallback
		});
	},
	/**
	 * 获取组件方法
	 */
	getAppCmpt : function(){
		var _this = this;
		var selId  = null;
		var appPanel = new Ext.Panel({autoScroll:true,border : false,id:'panel1'});
		var tabs = new Ext.TabPanel({activeTab: 0,disabled : true});
		var TrePanel=FormUtil.getLayoutPanel([.66,.33,.33],[_this.createDetailPnl(),this.getAppGrid_2()]);
		appPanel.add({items:[this.getAppGrid(),this.getToolBar(),TrePanel]});
		return appPanel;
	},
	/**
	 * 刷新方法
	 * @param {} optionType
	 * @param {} data
	 */
	refresh:function(optionType,data){},
	/**
	 * 组件大小改变方法
	 * @param {} whArr
	 */
	changeSize : function(whArr){
		var width = CLIENTWIDTH - Ext.getCmp(ACCORDPNLID);
		var height = CLIENTHEIGHT - 180;
		this.appPanel.setWidth(width);
		this.appPanel.setHeight(height);
		},
			/**
			 * 查询工具栏
			 */
			getToolBar : function() {
				var self = this;
				var barItems = [{
			text : Btn_Cfgs.PRO_TIP_BTN_TXT,
//			iconCls:'page_add',
			tooltip:Btn_Cfgs.PRO_TIP_BTN_TXT,
			token : '项目费用详情',
			handler : function(){
				var that = this;
					self.globalMgr.winEdit.show({key:that.token,self:self});
				}},{
			text : Btn_Cfgs.PROTIP_TIP_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.PROTIP_TIP_BTN_TXT,
			token : '开始收款',
			handler : function(){
				var that = this;
				var  ids=self.globalMgr.appgrid.getSelId("请选择数据之后开始收款");
				if(!ids)return;self.globalMgr.winEdit.show({key:that.token,self:self});}}];
				toolBar = new Ext.ux.toolbar.MyCmwToolbar({
							aligin : 'left',
							controls : barItems,
							rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]
							}
						});
				return toolBar;
			},
			getAppGrid : function() {
				var _this = this;
				var structure_1 = [{
							header : 'id',
							name : 'id'
							,hidden:true},{
							header : '状态',
							name : 'status',
							width : 65,
							renderer : function(val) {
								switch (val) {
									case '0' :
										val = '未收';
										break;
									case '1' :
										val = '部分收款';
										break;
									case '2' :
										val = '结清';
										break;
								}
								return val;
							}
						}, {
							header : '费用类型',
							name : 'itemType',
							width : 135
						}, {
							header : '收支方向',
							name : 'direction',
							width : 135,
							renderer : function(val) {
								switch (val) {
									case '1' :
										val = '收入';
										break;
									case '2' :
										val = '支出';
										break;
								}
								return val;
							}
						}, {
							header : '金额',
							name : 'amount',
							renderer : function(val) {
								val = Cmw.getThousandths(val);
								return val;
							}
						}, {
							header : '实收金额',
							name : 'yamount',
							width : 65,
							renderer : function(val) {
								val = Cmw.getThousandths(val);
								return val;
							}
						}, {
							header : '最后收款日期',
							name : 'lastDate'
						}, {
							header : '创建人',
							name : 'creator',
							width : 135
						}, {
							header : '创建日期',
							name : 'createTime'
						}, {
							header : '备注',
							name : 'remark'
						}];
				var appgrid = new Ext.ux.grid.AppGrid({
							structure : structure_1,
							title : '项目费用列表',
							url : './fcProjectAmunt_list.action',
							params : {
								formId : _this.globalMgr.formId
							},
							needPage : false,
							isLoad : true,
							height : 220,
							keyField : 'id'});
				_this.globalMgr.appgrid = appgrid;
				return appgrid;
			},
			getAppGrid_2 : function() {
				var _this = this;
				var structure_1 = [ {
							header : '实收金额',
							name : 'amount',
							width : 65,
							renderer : function(val) {
								val = Cmw.getThousandths(val);
								return val;
							}
						}, {
							header : '收/付款日期',
							name : 'rectDate'
						}, {
							header : '收/付款人',
							name : 'creator',
							width : 135
						}, {
							header : '收款日期日期',
							name : 'createTime'
						}];
				var appgrid_2 = new Ext.ux.grid.AppGrid({
							structure : structure_1,
							title : '项目费用收支历史记录',
							url : './fcPamountRecords_list.action',
							needPage : false,
							height : 479,
							keyField : 'id'
						});
				_this.globalMgr.appgrid_2 = appgrid_2;
				return appgrid_2;
			},
			/**
			 * 创建详情面板
			 */
			getAppForm : function() {
				var _this = this;
				var txt_id = FormUtil.getHidField({
							fieldLabel : 'id',
							name : 'id',
							"width" : 135
						});
				var txt_formid = FormUtil.getHidField({
							fieldLabel : 'paid',
							name : 'paid',
							"width" : 135
						});

				var rad_order = FormUtil.getRadioGroup({
							fieldLabel : '收支方向',
							"width" : 200,
							allowBlank : false,
							id : 'rad_orderid',
							name : 'payType',
							items : [{
										boxLabel : '现金',
										name : 'payType',
										inputValue : 1,
										checked : true
									}, {
										boxLabel : '转帐',
										name : 'payType',
										inputValue : 2
									}],
						listeners : {
							'change' : ChangePrange
						}
						});
					function ChangePrange(){
					var paytype=rad_order.getValue();
					if(paytype==1){
						txt_bankAccount.setDisabled(true);
						txt_bankAccount.setValue("");
						cbog_accountId.setDisabled(true);
						cbog_accountId.setValue("");
					}else{
						txt_bankAccount.setDisabled(false);
						cbog_accountId.setDisabled(false);
					}
				}
				var txt_bankAccount = FormUtil.getTxtField({
							fieldLabel : '银行帐号',
							name : 'bankAccount',
							"width" : 200,disabled:true
						});

				var cbog_accountId = ComboxControl.getAccountCboGrid({
							fieldLabel : '付款银行',
							name : 'accountId',
							allowBlank : false,
							// readOnly : true,
							isAll : false,
							"width" : 200,
							disabled:true,
							params : {
								isIncome : 1,
								sysId : _this.sysId
							}
						});
				var num_cat = FormUtil.getMoneyField({
							fieldLabel : '金额',
							name : 'amount',
							"width" : 200,
							allowBlank : false,
							unitText : '元'
						});
				var dat_rectDate = FormUtil.getDateField({
						fieldLabel : '收/付款日期',
						name : 'rectDate',
						"width" : 200,
						"allowBlank" : false,
						editable : false,
						"maxLength" : 50
					});
				var layout_fields = [txt_id,txt_formid , {cmns : FormUtil.CMN_TWO,
								fields : [rad_order,cbog_accountId, txt_bankAccount, num_cat,dat_rectDate]}];
				var frm_cfg = {
					labelWidth : 110,
					height : 400,
					title : '本次收款信息录入',
					url : './fcPamountRecords_save.action'
				};
				var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
				return appform_1;
			},
			createButton : function() {
				var self = this;
				var button_save = new Ext.Button({
							text : '收款',
							width : 100,
							listeners : {
								"click" : function() {
									cfg = {
										sfn : function(Json_data) {
							self.globalMgr.appgrid.reload();
							self.globalMgr.appgrid_2.reload();
							self.globalMgr.detailPanel_1.reload();
							
											}}
				EventManager.frm_save(self.globalMgr.appform_1, cfg);}}});
				var buttons = [button_save];
				var btnPanel = new Ext.Panel({
							buttonAlign : 'center',
							buttons : buttons});
				return btnPanel;
			},
			/**
			 * 创建详情面板
			 */
			createDetailPnl : function() {
				var _this = this;
				var sysId = this.globalMgr.sysId;
			var htmlArrs_1 = [
						'<tr><th col="itemType">费用类型</th> <td col="itemType" >&nbsp;</td><th col="direction">收支方向</th> <td col="direction"  >&nbsp;</td></tr>',
						'<tr><th col="amount">金额</th> <td col="amount" >&nbsp;</td><th col="yamount">实收[付]金额</th> <td col="yamount"  >&nbsp;</td></tr>',
						'<tr><th col="lastDate">最后收款日期</th> <td col="lastDate" >&nbsp;</td><th col="status">状态</th> <td col="status"  >&nbsp;</td></tr>',
						'<tr><th col="remark">备注</th> <td col="remark"  colspan=3 >&nbsp;</td></tr>'];
				var detailCfgs_1 = [{
							cmns : 'TWO',
							/* ONE , TWO , THREE */
							model : 'single',
//							title:'项目费用详情',
							labelWidth : 90,
							/* title : '#TITLE#', */
							// 详情面板标题
							/* i18n : UI_Menu.appDefault, */
							// 国际化资源对象
							htmls : htmlArrs_1,
							url : '#GETURLCFG#',
							prevUrl : '#PREURLCFG#',
							nextUrl : '#NEXTURLCFG#',
							params : {
							},
					callback : {
						sfn : function(jsonData) {
						Cmw.print(jsonData);
								switch (jsonData["direction"]) {
									case '1' :
										jsonData["direction"] = '收入';
										break;
									case '2' :
										jsonData["direction"] = '支出';
										break;
								}
								switch (jsonData["status"]) {
									case '0' :
										jsonData["status"]= '未收';
										break;
									case '1' :
										jsonData["status"]= '部分收款';
										break;
									case '2' :
										jsonData["status"]= '结清';
										break;
								}
							
						}
					}
				}];
				var detailPanel_1 = new Ext.ux.panel.DetailPanel({
							autoWidth : true,
							detailCfgs : detailCfgs_1,
							border : false,
							attachLoad : function(params, cmd) {
								/* 在面板执行上一条，下一条查询时，会调用此事件 to do .. */
								// Cmw.print(params);
								// Cmw.print(cmd);
							}
						});
				_this.globalMgr.detailPanel_1 = detailPanel_1;
				var appform_1=this.getAppForm();
				_this.globalMgr.appform_1 = appform_1;
				var AddButton = this.createButton();
				_this.globalMgr.AddButton = AddButton;
				appform_1.add(AddButton);
				detailPanel_1.add(appform_1);
				return detailPanel_1;
			},	
			destroyCmpts : function() {

			},
			globalMgr : {
				appform_1 : null,
				AddButton : null,
				tabs : null,
				height : null,
				custType : null,
				AddBtn : null,
				DelBtn : null,
				EditBtn : null,
				TJBtn : null,
				id : null,
				appgrid_2:null,
				activeKey : null,
				formId : this.params.applyId,
				appgrid : null,
				unAmount : null,
				sysId : this.params.sysid,
				detailPanel_1 : null,
				winEdit : {
					show : function(parentCfg) {
						var _this = parentCfg.self;
						var winkey = parentCfg.key;
						if(winkey=="开始收款"){
						var json_date=_this.globalMgr.appgrid.getCmnVals("id,status,itemType,direction,amount,yamount,lastDate,remark")
						_this.globalMgr.detailPanel_1.reload({json_data:json_date},true);
						var paid= _this.globalMgr.appform_1.findFieldByName("paid");
						paid.setValue(json_date.id);
							}
						_this.globalMgr.activeKey = winkey;
						var parent = {};
						_this.globalMgr.formId = _this.params.applyId;
						parent.formId = _this.globalMgr.formId;
						parent.appgrid = _this.globalMgr.appgrid;
						parent.AddBtn = _this.globalMgr.AddBtn;
						parent.sysId = _this.globalMgr.sysId;
						parentCfg.parent = parent;
					}
				}
			}
		});

