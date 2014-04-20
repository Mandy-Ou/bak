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
					EventManager.query(self.appgrid, {name : name});
					}
				}
			}, {
				token : "查询",
				text : Btn_Cfgs.QUERY_BTN_TXT,
				iconCls : 'page_query',
				tooltip : Btn_Cfgs.QUERY_TIP_BTN_TXT,
				key : Btn_Cfgs.QUERY_FASTKEY,
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
				handler : function() {toolBar.resets();}
			}, {
				type : "sp"
			}, {
				token : "添加",
				text : Btn_Cfgs.INSERT_BTN_TXT,
				iconCls : 'page_add',
				tooltip : Btn_Cfgs.INSERT_TIP_BTN_TXT,
				key : Btn_Cfgs.INSERT_FASTKEY,
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
				handler : function() {
					var id = self.appgrid.getSelId();
					if (!id)
						return;
					FormUtil.enableFrm(self.appform);
					EventManager.add("./oaCitemsCfg_get.action?id=" + id,self.appform, {sfn : function(formdate) {
							self.appform.setFieldValues(formdate);
							this.refresh(id);
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
				handler : function() {
					EventManager.enabledData('./oaCitemsCfg_enabled.action', {type : 'grid',cmpt : self.appgrid,
						optionType : OPTION_TYPE.ENABLED,self : self
				});
			}
		}, {
				token : "禁用",
				text : Btn_Cfgs.DISABLED_BTN_TXT,
				iconCls : 'page_disabled',
				tooltip : Btn_Cfgs.DISABLED_TIP_BTN_TXT,
				key : Btn_Cfgs.DISABLED_FASTKEY,
				handler : function() {
				EventManager.disabledData('./oaCitemsCfg_disabled.action', {type : 'grid',cmpt : self.appgrid,
					optionType : OPTION_TYPE.DISABLED,self : self
				});
			}
		}, {
				token : "删除",
				text : Btn_Cfgs.DELETE_BTN_TXT,
				iconCls : 'page_delete',
				tooltip : Btn_Cfgs.DELETE_TIP_BTN_TXT,
				key : Btn_Cfgs.DELETE_FASTKEY,
				handler : function() {
				EventManager.deleteData('./oaCitemsCfg_delete.action', {type : 'grid',cmpt : self.appgrid,
					optionType : OPTION_TYPE.DEL,self : self
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
								break ;
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
				}, {
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
					isLoad : true,//瞬时加载
					url : './oaCitemsCfg_list.action'
				});
		return _appgrid;
	},
	refresh : function(id) {
		var activeKey = this.globalMgr.activeKey;
		// 瞬时刷新数据
		var _self = this;
		EventManager.query(_self.appgrid, null);
//		EventManager.query(_self.globalMgr._appgrid,id);
		
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
								EventManager.frm_reset(appform, [id,code,name,remark,this]);
								txt_costStander.disable();
								fieldSetAppGrid.enable();
							} else if (newValue.inputValue == "1") {
								EventManager.frm_reset(appform, [id,code,name,remark,this]);
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
					maxLength : 200
//					multiline : true
				})

		var fieldSetAppGrid = self.notify();

		var fieldset = {
			xtype : 'fieldset',
			title : '按职位限制',
			name : 'samount',
			allowBlank : false,
			autoHeight : true,
			width : 330,
			// defaults: {width: 300},
			items : [fieldSetAppGrid]
		};
		var appform = new Ext.ux.form.AppForm({
					title : '费用项编辑',
					url : './oaCitemsCfg_save.action',
					items : [id, code, name, limitType, txt_costStander,fieldset, remark],
					width : 200,
					buttons : [
						{text : '保存',
						handler : function() {
						EventManager.frm_save(appform, 
							{beforeMake : function(beforeData){
							var store =  self.globalMgr._appgrid.getStore(); 
							var count = store.getCount();
							var datas = [];
							if(count>0){
								for(var i = 0 ;i<count;i++){
									var record = store.getAt(i);
									var name = record.get("name");
									var jobId =record.get("jobId");
									var samount = record.get("samount");
									var params = {name : name,jobId :jobId,samount : samount};
									datas.push(params);
									}
									beforeData.store = Ext.encode(datas);
								}
							},sfn : function(data) {
								appform.reset();
								EventManager.query(self.appgrid,null);
								FormUtil.disabledFrm(appform);	
									}
								}
							);}
						},{
						text : '重置',
						handler : function() {EventManager.frm_reset(appform, [id, code]);
							}
					}]
				});
			// 禁用表单元素
			FormUtil.disabledFrm(appform);
			this.globalMgr.appform = appform;
			return appform;
	},
	notify : function() {
		var _self = this;
		this.globalMgr.activeKey = null;
		var ttbar = [{
					token : Btn_Cfgs.POST_ADD_BTN_TXT,// "添加职位"
					text : Btn_Cfgs.POST_ADD_BTN_TXT,
					iconCls : 'page_add',
					tooltip : Btn_Cfgs.POST_ADD_BTN_TXT,
					key : Btn_Cfgs.POST_ADD_BTN_TXT,
					handler : function() {
						_self.globalMgr.winEdit.show({key : this.token,_self : _self,_appgrid :_self.globalMgr._appgrid});
					}
				}, {
					//TODO 点击编辑的时候还有bug CitemsCfg.js
					token : Btn_Cfgs.POST_EDIT_BTN_TXT,
					text : Btn_Cfgs.POST_EDIT_BTN_TXT,
					iconCls : 'page_edit',
					tooltip : Btn_Cfgs.POST_EDIT_TIP_BTN_TXT,
					key : Btn_Cfgs.POST_EDIT_BTN_FASTKEY,
					handler : function() {
						var gridVal = _self.appgrid.getSelRowVal(_self.appgrid,_self.appgrid);
						_self.globalMgr.winEdit.show({key : this.token,_self : _self,gridVal :gridVal});
					}
				}]
		
		var appGridToolBar = new Ext.ux.toolbar.MyCmwToolbar({
					aligin : 'left',
					controls : ttbar
				});
		var structure = [{
					header : 'jobId',
					name : 'jobId',
					hidden : true
				}, {
					header : '职位',
					name : 'name',
					width : 75
				}, {
					header : '费用标准（单位：元）',
					name : 'samount',
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
		this.globalMgr._appgrid = _appgrid;
		return _appgrid;
	},
	globalMgr : {
		_appgrid : null,
		appform : null,
		activeKey : null,
		//用来判断用户点击了哪个按钮，用来显示相对应的页面
		tokenMgr:{
				POST_EDIT_BTN_TXT : '编辑职位',
				POST_ADD_BTN_TXT :'添加职位'
			},
		winEdit : {
			show : function(parentCfg) {
				var _this = parentCfg._self;
				//获取  定义的数据
				var tokenMgr = _this.globalMgr.tokenMgr;
				//获取用户点击的按钮
				var winkey = parentCfg.key;
				_this.globalMgr.activeKey = winkey;
				var parent = _this.appgrid;
				parentCfg.parent = parent;
				//判断缓存中是否有数据
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else {
					var winModule=null;
					//根据用户点击的按钮进行选择
					switch(winkey){
						case tokenMgr.POST_ADD_BTN_TXT :{
							    winModule = "CitemsCfgEdit";
								break;
							}	
						case tokenMgr.POST_EDIT_BTN_TXT : {
								winModule = "CitemsCfgAmend";
								break;
							}
						}
					Cmw.importPackage('pages/app/oa/citems/' + winModule,function(module) {
							_this.appCmpts[winkey] = module.WinEdit;
							_this.appCmpts[winkey].show(parentCfg);
					});
				}
			}
		}
	}
});
