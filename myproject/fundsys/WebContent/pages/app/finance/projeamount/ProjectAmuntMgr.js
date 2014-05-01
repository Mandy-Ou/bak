/*同心日科技公司Erp 系统 命名空间*/
Ext.namespace("skythink.cmw.workflow.bussforms");
/**
 * 放款通知书
 */
skythink.cmw.workflow.bussforms.ProjectAmuntMgr = function(){
	this.init(arguments[0]);
}

/*-----------继承Observable的事件方法--------*/
Ext.extend(skythink.cmw.workflow.bussforms.ProjectAmuntMgr,Ext.util.MyObservable,{
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
				getAppGrid : this.getAppGrid,
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
			var heightVal=400;
			var tabs = new Ext.TabPanel({
				items : [ {title:"<span style='color:red;'>提示：</span>"
				}],
				height:heightVal,
				html:"<span style='color:red;font-size:22px;'><center>项目状态为：收款中、结清的不能删除<center></span>"
				});
				var tabs_two = new Ext.TabPanel({height:heightVal});
				var TrePanel=FormUtil.getLayoutPanel([.50,.001,.49],[_this.getAppForm(),tabs_two,tabs]);
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
		getToolBar : function(){
			var self = this;
			var barItems = [{
				text : Btn_Cfgs.INSERT_BTN_TXT,
				iconCls:'page_add',
				tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
				token : '添加',
				handler : function(){
					var that = this;
						self.globalMgr.winEdit.show({key:that.token,self:self});
					}
	
			},{
				text : Btn_Cfgs.MODIFY_BTN_TXT,
				iconCls:'page_edit',
				tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
				token : '编辑',
				handler : function(){
					var that = this;
						self.globalMgr.winEdit.show({key:that.token,self:self});
					}
			},{
				text : Btn_Cfgs.DELETE_BTN_TXT,
				iconCls:'page_delete',
				tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
				token : '删除'
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems,rightData : {saveRights : true,currNode : this.params[CURR_NODE_KEY]}});
			return toolBar;
		},
		 getAppGrid:function(){
		 	var _this = this;
			var structure_1 = [{
							header : '状态',
							name : 'state',
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
							width : 135
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
			    structure: structure_1,
			    title:'项目费用列表',
			    url : './fcProjectAmunt_list.action',
			    needPage: false,
			    isLoad: true,
			    height : 220
			});
			_this.globalMgr.appgrid=appgrid;
			return appgrid;
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
						fieldLabel : 'formId',
						name : 'formId',
						"width" : 135
					});
				var txt_products = FormUtil.getRCboField({
						fieldLabel : '费用类型',
						name : 'itemType',
						"width" : 135,
						"allowBlank" : false,
						register : REGISTER.GvlistDatas,
						restypeId : '200007'
					});
				var rad_order = FormUtil.getRadioGroup({
							fieldLabel : '收支方向',
							"width" : 200,
							allowBlank : false,
							id : 'rad_orderid',
							name : 'direction',
							items : [{
										boxLabel : '收入',
										name : 'direction',
										inputValue : 1,
										checked : true
									}, {
										boxLabel : '支出',
										name : 'direction',
										inputValue : 2
									}]
						});

				var num_cat = FormUtil.getMoneyField({
							fieldLabel : '金额',
							name : 'amount',
							allowBlank : false,
							unitText : '元'
						});
				var txt_remark = FormUtil.getTAreaField({
							fieldLabel : '备注',
							name : 'remark',
							height : 50,
							width : 200
						});
				var read_remark = FormUtil.getTAreaField({
					fieldLabel : '备注',
					name : 'remark',
					height : 50,
					width : 400
				});
				var layout_fields = [{
							cmns : FormUtil.CMN_TWO,
							fields : [txt_id, txt_products, rad_order, num_cat,txt_formid]
						},txt_remark];
				var frm_cfg = {
					labelWidth : 110,
					height : 400,
					disabled:true,
					title : '项目费用编辑'+"<span style='color:red;font-size:11px'>提示:项目费用状态为收款中、结清的不能删除</span",
					url : './fcProjectAmunt_save.action',
					params :{formId :_this.globalMgr.formId}
				};
				var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
				_this.globalMgr.appform_1 = appform_1;
				var AddButton = this.createButton();
				_this.globalMgr.AddButton = AddButton;
				appform_1.add(AddButton);
				return appform_1;
			},
		createButton : function() {
				var self=this;
			var button_save = new Ext.Button({
						text : '保存',
						width : 100	,
							listeners : {
								"click" : function() {
								cfg = {sfn : function(Json_data) {
								self.globalMgr.appgrid.reload();
								self.globalMgr.appform_1.setDisabled(true);//保存成功之后进行禁用
								}}
			EventManager.frm_save(self.globalMgr.appform_1, cfg);
			}}
			});
			var button_reset = new Ext.Button({
						text : '重置',
						width : 100
					})
			var buttons = [button_save, button_reset];
			var btnPanel = new Ext.Panel({buttonAlign : 'center',buttons:buttons});
			return btnPanel;
		},

	/**
	 * 组件销毁方法
	 */
	destroyCmpts : function(){
		
	},
	globalMgr : {
		appform_1:null,
		AddButton:null,
		 height : null,
	 	custType:null,
		AddBtn : null,
		DelBtn : null,
		EditBtn : null,
		TJBtn : null,
		id : null,
		activeKey: null,
		formId:this.params.applyId,
		appgrid :null,
		appgrid2 :null,
		unAmount : null,
		sysId : this.params.sysid,
		detailPanel_1 : null,
			winEdit : {
			show : function(parentCfg){
				var _this = parentCfg.self;
				var winkey=parentCfg.key;
				_this.globalMgr.activeKey = winkey;
					var formIds=_this.globalMgr.appform_1.findFieldByName("formId").setValue(_this.globalMgr.formId);
				if(winkey=="添加"){
					_this.globalMgr.appform_1.setDisabled(false);
				}else if(winkey=="编辑"){
					_this.globalMgr.appform_1.setDisabled(false);
					var json_date=_this.globalMgr.appgrid.getCmnVals("id,status,itemType,direction,amount,lastDate")
					Cmw.print(json_date);
						_this.globalMgr.appform_1.findFieldByName("itemType").setValue(json_date.itemType);
						_this.globalMgr.appform_1.findFieldByName("status").setValue(json_date.status);
						_this.globalMgr.appform_1.findFieldByName("direction").setValue(json_date.direction);
						_this.globalMgr.appform_1.findFieldByName("amount").setValue(json_date.amount);
						_this.globalMgr.appform_1.findFieldByName("remark").setValue(json_date.remark);
						_this.globalMgr.appform_1.reload({json_data:json_date},true);
				}
				var parent ={};
				_this.globalMgr.formId = _this.params.applyId;
				parent.formId = _this.globalMgr.formId;
				parent.appgrid = _this.globalMgr.appgrid;
				parent.AddBtn  = _this.globalMgr.AddBtn;
				parent.sysId = _this.globalMgr.sysId;
				parentCfg.parent = parent;
			}
		}
	}
});

