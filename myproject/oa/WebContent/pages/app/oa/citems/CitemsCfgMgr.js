/* 九洲科技公司Erp 系统 命名空间 */
Ext.namespace("skythink.cmw.sys");
/* 初始化通值数据模型 实例 */
skythink.cmw.sys.CitemsCfg = function() {
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.sys.CitemsCfg, Ext.util.MyObservable, {
	initModule : function(tab, params) {
		this.module = new Cmw.app.widget.AbsGFormView({
					title : '费用项配置列表',
					tab : tab,
					params : params,
					gfCfg : {formAlign : 'right',wOrh : 350},// 表单在右边,宽度为350
					getToolBar : this.getToolBar,// 表格上面的按钮工具栏
					getAppGrid : this.getAppGrid,// 表格
					getAppForm : this.getAppForm,// 表单
					notify : this.notify,
					globalMgr : this.globalMgr,
					refresh : this.refresh
				});
	},

	// 表格上面的按钮工具栏
	getToolBar : function() {
		var self = this;
		var toolBar = null;
		var barItems = [{
			type : 'label',
			text : Btn_Cfgs.EXPENSES_LABEL_TEXT
			// 费用项名称
			}, {
			type : 'search',
			name : 'name',
			key : Btn_Cfgs.QUERY_FASTKEY,
			onTrigger2Click : function() {
				var params = toolBar.getValues();
				var name = params.name;
				// 根据输入的值进行查找，如果为空，查询全部
				if (name == "") {
					EventManager.query(self.appgrid, null);
				} else {
					alert(self.appgrid.dom.innerHTML);
					EventManager.query(self.appgrid, {name : name});
				}
			}
		}, {
			token : "查询",
			text : Btn_Cfgs.QUERY_BTN_TXT,
			iconCls : 'page_query',
			tooltip : Btn_Cfgs.QUERY_TIP_BTN_TXT,
			key : Btn_Cfgs.QUERY_FASTKEY,
			listeners : {"afterrender" : function() {/* FKeyMgr.setkey(this) */}
			},
			handler : function() {
				var params = toolBar.getValues();
				var name = params.name;
				// 根据输入的值进行查找，如果为空，查询全部
				if (name == "") {
					EventManager.query(self.appgrid, null);
				} else {
					EventManager.query(self.appgrid, {name : name
					});
				}
			}
		}, {
			token : "重置",
			text : Btn_Cfgs.RESET_BTN_TXT,
			iconCls : 'page_reset',
			tooltip : Btn_Cfgs.RESET_TIP_BTN_TXT,
			key : Btn_Cfgs.RESET_FASTKEY,
			listeners : {"afterrender" : function() {/* // FKeyMgr.setkey(this); */}
			},
			handler : function() {toolBar.resets();}
		}, {
			type : "sp"
		}, {
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls : 'page_add',
			tooltip : Btn_Cfgs.INSERT_TIP_BTN_TXT,
			key : Btn_Cfgs.INSERT_FASTKEY,
			listeners : {"afterrender" : function() {/* // FKeyMgr.setkey(this); */}
			},
			handler : function() {
				FormUtil.enableFrm(self.appform);
				EventManager.add("./oaCitemsCfg_add.action", self.appform, {sfn : function(formdate) {
						self.appform.setFieldValues(formdate);
					}
				});
			}
		}, {
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls : 'page_edit',
			tooltip : Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			key : Btn_Cfgs.MODIFY_FASTKEY,
			listeners : {"afterrender" : function() {/* FKeyMgr.setkey(this);*/}
			},
			handler : function() {
				var id = self.appgrid.getSelId();
				if (!id)
					return;
				FormUtil.enableFrm(self.appform);
				EventManager.add("./oaCitemsCfg_get.action?id=" + id,self.appform, {sfn : function(formdate) {
						self.appform.setFieldValues(formdate);
					}
				});
			}
		}, {
			type : "sp"
		}, {
			token : "启用",
			text : Btn_Cfgs.ENABLED_BTN_TXT,
			iconCls : 'page_enabled',
			tooltip : Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			key : Btn_Cfgs.ENABLED_FASTKEY,
			listeners : {"afterrender" : function() {/* FKeyMgr.setkey(this);*/}
			},
			handler : function() {
				EventManager.enabledData('./oaCitemsCfg_enabled.action', {type : 'grid',cmpt : self.appgrid,optionType : OPTION_TYPE.ENABLED,self : self
				});
			}
		}, {
			token : "禁用",
			text : Btn_Cfgs.DISABLED_BTN_TXT,
			iconCls : 'page_disabled',
			tooltip : Btn_Cfgs.DISABLED_TIP_BTN_TXT,
			key : Btn_Cfgs.DISABLED_FASTKEY,
			listeners : {"afterrender" : function() {/* FKeyMgr.setkey(this);*/}
			},
			handler : function() {
				EventManager.disabledData('./oaCitemsCfg_disabled.action', {
					type : 'grid',
							cmpt : self.appgrid,
							optionType : OPTION_TYPE.DISABLED,
							self : self
						});
			}
		}, {
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls : 'page_delete',
			tooltip : Btn_Cfgs.DELETE_TIP_BTN_TXT,
			key : Btn_Cfgs.DELETE_FASTKEY,
			listeners : {
				"afterrender" : function() {
					// FKeyMgr.setkey(this);
				}
			},
			handler : function() {
				EventManager.deleteData('./oaCitemsCfg_delete.action', {
							type : 'grid',
							cmpt : self.appgrid,
							optionType : OPTION_TYPE.DEL,
							self : self
						});
			}
		}];
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
	 * 获取Grid 对象
	 */
	getAppGrid : function() {
		var structure = [{
					header : '',
					name : 'id',
					hidden : true
				}, {
					header : '可用标识',
					name : 'isenabled',
					width : 75,
					renderer : function(val) {
						switch (val) {
							case "-1" :
								val = "作废";
							case "0" :
								val = "不可用";
								break;
							case "1" :
								val = "可用";
								break;
						}
						return val;
					}
				}, {
					header : '编号',
					name : 'code',
					width : 75
				}, {
					header : '限制条件',
					name : 'limitType',
					width : 90,
					renderer : function(val) {
						switch (val) {
							case "1" :
								val = "通用";
								break;
							case "2" :
								val = "按职位限制";
								break;
						}
						return val;
					}
				}, {
					header : '费用项名称',
					name : 'name',
					width : 100
					// renderer : Render_dataSource.isenabledRender
			}	, {
					header : '说明',
					name : 'remark',
					width : 90
				}, {
					header : '费用项标准',
					name : 'samount'
					// hidden : true
			}];
		var _appgrid = new Ext.ux.grid.AppGrid({
					tbar : this.toolBar,
					structure : structure,
					height : 500,
					isLoad : true,
					url : './oaCitemsCfg_list.action'
				});
		return _appgrid;
	},
	refresh : function() {
		// 瞬时刷新数据
		var self = this;
		EventManager.query(self.appgrid, null);
	},
	/**
	 * 获取 Form 表单对象
	 */

	getAppForm : function() {
		// 卡片编码,卡片名称,图标样式,加载类型,数据URL,排列顺序
		// code,name,iconCls,type,url,orderNo
		var self = this;
		var id = FormUtil.getHidField({
					name : 'id'
				});
		var code = FormUtil.getReadTxtField({
					fieldLabel : '编号',
					name : 'code',
					width : 200,
					maxLength : 20
				});
		var name = FormUtil.getTxtField({
					fieldLabel : '费用项名称',
					name : 'name',
					width : 200,
					maxLength : 30,
					allowBlank : false
				});
		// 费用标准
		var txt_name = FormUtil.getTxtField({
					fieldLabel : '费用标准',
					name : 'samount',
					width : 180
				});

		var txt_costStander = FormUtil.getMyCompositeField({
					fieldLabel : '费用标准',
					name : 'samount',
					width : 200,
					items : [txt_name, {
								xtype : 'displayfield',
								value : '元'
							}],
					allowBlank : false
				});
		var limitType = FormUtil.getRadioGroup({
					fieldLabel : '限制条件',
					allowBlank : false,
					name : 'limitType',
					width : 200,
					items : [{
								boxLabel : '通用',
								name : 'limitType',
								inputValue : "1",
								checked : true
							}, {
								boxLabel : '按职位限制',
								name : 'limitType',
								inputValue : "2"
							}],
					listeners : {
						'change' : function(limit, newValue, oldValue) {
							if (newValue.inputValue == "2") {
								txt_costStander.disable();
								fieldSetAppGrid.enable();
							} else if (newValue.inputValue == "1") {
								txt_costStander.enable();
								fieldSetAppGrid.disable();
							}
						}
					}
				});

		var remark = FormUtil.getTAreaField({
					fieldLabel : '说明',
					name : 'remark',
					width : 200,
					maxLength : 200,
					multiline : true
				})

		var fieldSetAppGrid = self.notify();

		// form表单中的fieldset对象
		/*
		 * var fieldset = FormUtil.createLayoutFieldSet({ title : '按职位限制', width
		 * :200, height : 300 },[ {title : '标准'} ])
		 */

		var fieldset = {
			xtype : 'fieldset',
			title : '按职位限制',
			allowBlank : false,
			autoHeight : true,
			width : 330,
			// defaults: {width: 300},
			items : [fieldSetAppGrid]
			/*
			 * handler : function(){ self.code.disabled=true }
			 */
		};
		var appform = new Ext.ux.form.AppForm({
					title : '费用项编辑',
					url : './oaCitemsCfg_save.action',
					items : [id, code, name, limitType, txt_costStander,
							fieldset, remark],
					width : 200,
					buttons : [{
								text : '保存',
								handler : function() {
									self.appgrid.params = self.toolBar
											.getValues();
									EventManager.frm_save(appform, {
												sfn : function(data) {
													var sysid = data.sysid;
													appform.reset();
													EventManager.query(
															self.appgrid, {
																sysid : sysid
															});
													FormUtil
															.disabledFrm(appform);
												}
											});
								}
							}, {
								text : '重置',
								handler : function() {
									EventManager.frm_reset(appform, [id, code]);
								}
							}]
				});
		// 禁用表单元素
		FormUtil.disabledFrm(appform);
		return appform;
	},
	notify : function() {
		var _self = this;
		this.globalMgr.activeKey = null;
		var ttbar = [{
					token : "添加职位",// 
					text : Btn_Cfgs.POST_ADD_BTN_TXT,
					iconCls : 'page_add',
					tooltip : Btn_Cfgs.POST_ADD_BTN_TXT,
					key : Btn_Cfgs.POST_ADD_BTN_TXT,
					handler : function() {
						_self.globalMgr.winEdit.show({key : this.token,_self : _self});
					}
				}, {
					token : "编辑",
					text : Btn_Cfgs.MODIFY_BTN_TXT,
					iconCls : 'page_edit',
					tooltip : Btn_Cfgs.MODIFY_TIP_BTN_TXT,
					key : Btn_Cfgs.MODIFY_FASTKEY,
					listeners : {
						"afterrender" : function() {
							// FKeyMgr.setkey(this);
						}
					},
					handler : function() {
						var id = self.appgrid.getSelId();
						if (!id)
							return;
						FormUtil.enableFrm(self.appform);
						EventManager.add("./oaCitemsCfg_get.action?id=" + id,
								self.appform, {
									sfn : function(formdate) {
										self.appform.setFieldValues(formdate);
									}
								});
					}
				}]
		
		var appGridToolBar = new Ext.ux.toolbar.MyCmwToolbar({
					aligin : 'left',
					controls : ttbar
				});
		var structure = [{
					header : '',
					name : 'id',
					hidden : true
				}, {
					header : '职位',
					name : 'limitType',
					width : 75
				}, {
					header : '费用标准',
					name : 'samount',
					width : 100
				}, {
					header : '',
					width : 100
				}];

		var _appgrid = new Ext.ux.grid.AppGrid({
					url : './oaCitemsCfg_list.action',
					structure : structure,
					height : 150,
					tbar : appGridToolBar,
					isLoad : false,
					needPage : false,
					disabled : true
				});
		return _appgrid;
	},
	globalMgr : {
		activeKey : null,
		winEdit : {
			show : function(parentCfg) {
				var _this = parentCfg._self;
				var winkey = parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else {
					var winModule=null;
					switch(winkey){
						case "添加职位" :{
							    winModule = "CitemsCfgEdit";
								break;
							}
						}
					Cmw.importPackage('pages/app/oa/citems/' + winModule,function(module) {
							_this.appCmpts[winkey] = module.WinEdit;
							_this.appCmpts[winkey].show(parentCfg);
					});
				}
				/*
				 * if (_this.appCmpts[winkey]) {
				 * _this.appCmpts[winkey].show(parentCfg); }else{ }
				 */
			}
		}

	}
});
