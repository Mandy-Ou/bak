Ext.namespace("cmw.skythink");
/**
 * 随借随还还款管理
 */
cmw.skythink.CurrentMgr = function() {
	this.init(arguments[0]);
}

Ext.extend(cmw.skythink.CurrentMgr, Ext.util.MyObservable, {
	initModule : function(tab, params) {
		this.module = new Cmw.app.widget.AbsGContainerView({
					tab : tab,
					params : params,
					hasTopSys : false,
					getQueryFrm : this.getQueryFrm,
					getToolBar : this.getToolBar,
					getAppGrid : this.getAppGrid,
					globalMgr : this.globalMgr,
					refresh : this.refresh
				})
	},
	/**
	 * 创建查询面板
	 */
	getQueryFrm : function() {
		var _this = this;
		var rad_custType = FormUtil.getRadioGroup({
					fieldLabel : '客户类型',
					name : 'custType',
					items : [{
								boxLabel : '个人',
								name : 'custType',
								inputValue : 0
							}, {
								boxLabel : '企业',
								name : 'custType',
								inputValue : 1
							}]
				});

		var txt_custName = FormUtil.getTxtField({
					fieldLabel : '客户名称',
					name : 'name',
					width : 150
				});

		var txt_code = FormUtil.getTxtField({
					fieldLabel : '借款合同号',
					name : 'code',
					width : 150
				});

		var txt_payName = FormUtil.getTxtField({
					fieldLabel : '还款人名称',
					name : 'accName',
					width : 150
				});

		var txt_payBank = FormUtil.getTxtField({
					fieldLabel : '还款银行',
					name : 'payBank',
					width : 150
				});

		var txt_payAccount = FormUtil.getTxtField({
					fieldLabel : '还款帐号',
					name : 'payAccount',
					width : 150
				});
		var layout_fields = [{
			cmns : 'THREE',
			fields : [rad_custType, txt_custName, txt_code, txt_payName,
					txt_payBank, txt_payAccount]
		}]

		var queryFrm = FormUtil.createLayoutFrm(null, layout_fields);

		return queryFrm;
	},
	/**
	 * 创建工具栏
	 */
	getToolBar : function() {
		var _this = this;
		var toolBar = null;
		var barItems = [{/* 查询 */
			token : '查询',
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls : 'page_query',
			tooltip : Btn_Cfgs.QUERY_TIP_BTN_TXT,
			handler : function() {
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
				_this.globalMgr.winEdit.show({self:_this,key:this.token});
			}
		},{/*息费豁免*/
			token : '息费豁免',
			text : Btn_Cfgs.DO_EXEMPT_BTN_TXT,
			iconCls:Btn_Cfgs.DO_EXEMPT_CLS,
			tooltip:Btn_Cfgs.DO_EXEMPT_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:this.token});
			}
		},{/*结清*/
			token : '结清',
			text : Btn_Cfgs.DO_FINISH_BTN_TXT,
			iconCls:Btn_Cfgs.DO_FINISH_CLS,
			tooltip:Btn_Cfgs.DO_FINISH_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.doFinish(_this);
			}
		},/*息费返还*/{
			token : '息费返还',
			text : Btn_Cfgs.DO_RETURN_BTN_TXT,
			iconCls:Btn_Cfgs.DO_RETURN_CLS,
			tooltip:Btn_Cfgs.DO_RETURN_TIP_BTN_TXT,
			handler : function(){
				_this.globalMgr.winEdit.show({self:_this,key:this.token});
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
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({
			aligin : 'right',
			controls : barItems,
			rightData : {
				saveRights : true,
				currNode : this.params[CURR_NODE_KEY]
			}
		});
		return toolBar;
	},
	/**
	 * 创建表格个面板
	 */
	getAppGrid : function() {
		var _this = this;
		var structure_1 = [{
					header : '借款合同号',
					name : 'code'
				}, {
					header : '客户类型',
					name : 'custType',
					width : 60,
					renderer : function(val) {
						return Render_dataSource.custTypeRender(val);
					}
				}, {
					header : '客户名称',
					name : 'name'
				}, {
					header : '贷款金额',
					name : 'appAmount',
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '贷款利率(%)',
					width : 90,
					name : 'rate',
					renderer : function(val, m) {
						m.css = 'x-grid-back-gree';
						if (val || val == 0)
							val += '%';
						return val;
					}
				}, {
					header : '公司内部利率类型',
					name : 'inRateType',
					width : 90,
					renderer : function(val, m) {
						m.css = 'x-grid-back-red';
						if (val || val == 0)
							val = Render_dataSource.rateTypeRender(val);
						return val;
					}
				}, {
					header : '公司内部利率',
					name : 'inRate',
					width : 90,
					renderer : function(val, m) {
						m.css = 'x-grid-back-red';
						if (val || val == 0)
							val = val + '%';
						return val;
					}
				}, {
					header : '还款银行',
					name : 'payBank',
					width : 125
				}, {
					header : '还款帐号',
					name : 'payAccount',
					width : 125
				}, {
					header : '还款人',
					name : 'accName',
					width : 80
				}, {
					header : '已还期数',
					name : 'paydPhases',
					width : 55
				}, {
					header : '总期数',
					name : 'totalPhases',
					width : 45
				}, {
					header : '逾期',
					name : 'totalOverPharses',
					width : 60
				}, {
					header : '应还本金',
					name : 'appAmount',
					width : 85,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '实还本金',
					name : 'yprincipal',
					width : 85,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '应还利息',
					name : 'interest',
					width : 85,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '实还利息',
					name : 'yinterest',
					width : 85,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '返还利息',
					name : 'riamount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '豁免利息',
					name : 'trinterAmount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '应还管理费',
					name : 'mgrAmount',
					width : 85,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '实还管理费',
					name : 'ymgrAmount',
					width : 85,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '返还管理费',
					name : 'rmamount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '豁免管理费',
					name : 'trmgrAmount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '过桥贷管理费',
					name : 'totMat',
					width : 85,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '应收罚息',
					name : 'penAmount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '实收罚息',
					name : 'ypenAmount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '返还罚息',
					name : 'rpamount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '豁免罚息',
					name : 'trpenAmount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '应收滞纳金',
					name : 'delAmount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '实收滞纳金',
					name : 'ydelAmount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '返还滞纳金',
					name : 'rdamount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '豁免滞纳金',
					name : 'trdelAmount',
					width : 75,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '已实收合计',
					name : 'ytotalAmount',
					width : 100,
					renderer : function(val) {
						return Cmw.getThousandths(val);
					}
				}, {
					header : '还款状态',
					name : 'state',
					width : 75,
					renderer : function(val) {
						return Render_dataSource.applyStateRender(val);
					}
				}, {
					header : '贷款期限',
					name : 'loanLimit'
				}, {
					header : '合同ID',
					name : 'contractId',
					hideable : true,
					hidden : true
				}];

		var continentGroupRow = [{
					header : '合同信息',
					colspan : 6,
					align : 'center'
				}, {
					header : '还款帐号信息',
					colspan : 4,
					align : 'center'
				}, {
					header : '还款期数',
					colspan : 3,
					align : 'center'
				}, {
					header : '本金',
					colspan : 2,
					align : 'center'
				}, {
					header : '利息',
					colspan : 4,
					align : 'center'
				}, {
					header : '管理费',
					colspan : 5,
					align : 'center'
				}, {
					header : '罚息',
					colspan : 4,
					align : 'center'
				}, {
					header : '滞纳金',
					colspan : 4,
					align : 'center'
				}, {
					header : '实收合计',
					colspan : 1,
					align : 'center'
				}, {
					header : ' ',
					colspan : 3,
					align : 'center'
				}];
		var group = new Ext.ux.grid.ColumnHeaderGroup({
					rows : [continentGroupRow]
				});
		var appgrid_1 = new Ext.ux.grid.AppGrid({
					tbar : _this.toolBar,
					structure : structure_1,
					url : './fcCurrent_list.action',
					needPage : true,
					keyField : 'contractId',
					isLoad : true,
					plugins : [group],
					listeners : {
						render : function(grid) {
							_this.globalMgr.query(_this);
						}
					}
				});
		return appgrid_1;
	},
	/**
	 * 刷新数据
	 */
	refresh : function(optionType, data) {
		this.globalMgr.query(this);
		this.globalMgr.activeKey = null;
	},
	globalMgr : {
		/**
		 * 当前激活的按钮文本
		 * 
		 * @type
		 */
		sysId : this.params.sysid,
		activeKey : null,
		getQparams : function(_this) {
			var params = _this.queryFrm.getValues() || {};
			/*-- 附加桌面传递的参数  CODE START --*/
			if (_this.params && _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY]) {
				var deskParams = _this.params[DESK_MOD_TAG_QUERYPARAMS_KEY];
				if (deskParams) {
					Ext.applyIf(params, deskParams);
					_this.params[DESK_MOD_TAG_QUERYPARAMS_KEY] = null;
				}
			}/*-- 附加桌面传递的参数  CODE END --*/
			return params;
		},
		/**
		 * 查询方法
		 * 
		 * @param {}
		 *            _this
		 */
		query : function(_this) {
			var params = this.getQparams(_this);
			EventManager.query(_this.appgrid, params);
		},
		/**
		 * 导出Excel
		 * 
		 * @param {}
		 *            _this
		 */
		doExport : function(_this) {
			var params = this.getQparams(_this);
			var token = _this.params.nodeId;
			EventManager.doExport(token, params);
		},
		/**
		 * 结清
		 * 
		 * @param {}
		 *            _this
		 */
		doFinish : function(_this) {
			var selId = _this.appgrid.getSelId();
			if (!selId)
				return;
			var valObj = _this.appgrid
					.getCmnVals("appAmount,yprincipal,ymgrAmount,totFat");
			var appAmount = valObj.appAmount;
			var yprincipal = valObj.yprincipal;
			var ymgrAmount = valObj.ymgrAmount;// 实收管理费
			var totFat = valObj.totFat;
			ymgrAmount = parseFloat(ymgrAmount) + parseFloat(totFat);
			if (appAmount == yprincipal) {
				var msg = this.getMsg(_this.appgrid);
				ExtUtil.confirm({
					msg : msg,
					fn : function(btn) {
						if (btn != 'yes')return;
						/*---> bussType = 2表示结清标识 <---*/

						Ext.Msg.prompt("提示", "请填写结清日期！", function(btn, text) {
							if (btn != 'ok')
								return;
							if(!isDateFormat(text)) {
								ExtUtil.alert({msg : "输入的日期无效！"});
								return;
							}
							Cmw.mask(_this, Msg_SysTip.msg_state_Change);
							EventManager.get('./fcCurrent_settle.action', {
										params : {
											contractId : selId,
											bussType : 2,
											realDate : text
										},
										sfn : function(json_data) {
											Ext.tip.msg(Msg_SysTip.title_appconfirm,Msg_SysTip.msg_state_Settle);
											Cmw.unmask(_this);
											_this.globalMgr.query(_this);
										},
										ffn : function(json_data) {
											Cmw.unmask(_this);
										}
									});
						}, false);

					}
				});
			} else {
				ExtUtil.alert({msg : "该客户贷款的本金未还完不能转化为结清！"});
				return;
			}
			function isDateFormat(txt) { // 是否为合法的日期格式:YYYY-MM-DD
				if (txt == null || txt == "") {
					return false;
				} else {
					var regex = /[0-9]{1,4}-[0-9]{1,2}-[0-9]{1,2}/; // 可按具体格式修改
					if (regex.test(txt)) {
						var noArr = txt.split("-");
						var year = eval(noArr[0]);
						var month = eval(noArr[1]);
						var day = eval(noArr[2]);
						if (year < 1 || month < 1 || month > 12 || day < 1
								|| day > 31) {
							return false;
						}
						if ((month == 4 || month == 6 || month == 9 || month == 11)
								&& day > 30) {
							return false;
						}
						if (month == 2) {
							if ((year % 4 != 0) && day > 29) {
								return false;
							}
							if (year % 4 == 0) {
								if (year % 100 == 0 && year % 400 != 0
										&& day > 29) {
									return false;
								} else if (day > 28) {
									return false;
								}
							}
						}
						return true;
					} else {
						return false;
					}
				}
			}
		},
		getMsg : function(appgrid) {
			var fieldsStr = "name,interest,yinterest,riamount,trinterAmount,"
					+ "mgrAmount,ymgrAmount,rmamount,trmgrAmount,"
					+ "penAmount,ypenAmount,rpamount,trpenAmount,"
					+ "delAmount,ydelAmount,rdamount,trdelAmount"
			var valObj = appgrid.getCmnVals(fieldsStr);
			var name = valObj.name;
			var errArr = [];
			/*----> 利息 interest,yinterest,riamount,trinterAmount ---*/
			var interest = valObj.interest || 0;
			if (Ext.isString(interest))
				interest = parseFloat(interest);
			var yinterest = valObj.yinterest || 0;
			if (Ext.isString(yinterest))
				yinterest = parseFloat(yinterest);
			var riamount = valObj.riamount || 0;
			if (Ext.isString(riamount))
				riamount = parseFloat(riamount);
			var trinterAmount = valObj.trinterAmount || 0;
			if (Ext.isString(trinterAmount))
				trinterAmount = parseFloat(trinterAmount);
			var ziamount = interest - yinterest - trinterAmount + riamount;
			if (ziamount > 0)
				errArr[errArr.length] = "利息:" + ziamount + "元";

			/*----> 管理费 mgrAmount,ymgrAmount,rmamount,trmgrAmount ---*/
			var mgrAmount = valObj.mgrAmount || 0;
			if (Ext.isString(mgrAmount))
				mgrAmount = parseFloat(mgrAmount);
			var ymgrAmount = valObj.ymgrAmount || 0;
			if (Ext.isString(ymgrAmount))
				ymgrAmount = parseFloat(ymgrAmount);
			var rmamount = valObj.rmamount || 0;
			if (Ext.isString(rmamount))
				rmamount = parseFloat(rmamount);
			var trmgrAmount = valObj.trmgrAmount || 0;
			if (Ext.isString(trmgrAmount))
				trmgrAmount = parseFloat(trmgrAmount);
			var zmgrAmount = mgrAmount - ymgrAmount - trmgrAmount + rmamount;
			if (zmgrAmount > 0)
				errArr[errArr.length] = "管理费:" + zmgrAmount + "元";

			/*----> 罚息 penAmount,ypenAmount,rpamount,trpenAmount ---*/
			var penAmount = valObj.penAmount || 0;
			if (Ext.isString(penAmount))
				penAmount = parseFloat(penAmount);
			var ypenAmount = valObj.ypenAmount || 0;
			if (Ext.isString(ypenAmount))
				ypenAmount = parseFloat(ypenAmount);
			var rpamount = valObj.rpamount || 0;
			if (Ext.isString(rpamount))
				rpamount = parseFloat(rpamount);
			var trpenAmount = valObj.trpenAmount || 0;
			if (Ext.isString(trpenAmount))
				trpenAmount = parseFloat(trpenAmount);
			var zpenAmount = penAmount - ypenAmount - trpenAmount + rpamount;
			if (zpenAmount > 0)
				errArr[errArr.length] = "罚息:" + zpenAmount + "元";

			/*----> 滞纳金 delAmount,ydelAmount,rdamount,trdelAmount  ---*/
			var delAmount = valObj.delAmount || 0;
			if (Ext.isString(delAmount))
				delAmount = parseFloat(delAmount);
			var ydelAmount = valObj.ydelAmount || 0;
			if (Ext.isString(ydelAmount))
				ydelAmount = parseFloat(ydelAmount);
			var rdamount = valObj.rdamount || 0;
			if (Ext.isString(rdamount))
				rdamount = parseFloat(rdamount);
			var trdelAmount = valObj.trdelAmount || 0;
			if (Ext.isString(trdelAmount))
				trdelAmount = parseFloat(trdelAmount);
			var zdelAmount = delAmount - ydelAmount - trdelAmount + rdamount;
			if (zdelAmount > 0)
				errArr[errArr.length] = "未收滞纳金:" + zdelAmount + "元";
			var msg = "您确定将选中的记录,转化为结清?";
			if (null != errArr && errArr.length > 0) {
				msg = "选中的客户<span style='font-weight:bold;color:blue;'>["
						+ name
						+ "]</span>还有未收:<br/><span style='padding-left:10px;color:red;'>"
						+ errArr.join(",<br/>") + "</span>。<br/>确定将其转化为结清?";
			}
			return msg;
		},
		winEdit : {
			show : function(parentCfg) {
				var _this = parentCfg.self;
				var winkey = parentCfg.key;
				var parent = _this.appgrid;
				parent.sysId = _this.globalMgr.sysId;
				parentCfg.parent = parent;

				var bussKey = LockManager.keys.CurrentKey;/* 随借随还锁 */
				parentCfg.bussKey = bussKey;
				LockManager.applyLock(bussKey);
				LockManager.isLock(parent, "name", function() {
							var selIds = parent.getSelIds();
							if (!selIds) {
								return;
							}
							showWindow();
						});
				function showWindow() {
					if (_this.appCmpts[winkey]) {
						_this.appCmpts[winkey].show(parentCfg);
					} else {
						var winModule = "CurrentEdit";
						switch (winkey) {
							case "收款" : {
								winModule = "CurrentEdit";
								break;
							}
							case "息费返还" : {
								winModule = "CReturnEdit";
								break;
							}
							case "息费豁免" : {
								winModule = "CExemptEdit";
								break;
							}
						}
						Cmw.importPackage('pages/app/finance/deduct/current/'
										+ winModule, function(module) {
									_this.appCmpts[winkey] = module.WinEdit;
									_this.appCmpts[winkey].show(parentCfg);
								});
					}
				}
			}

		}
	}
});