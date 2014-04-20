/**
 * 出差申请单
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
			var appPanel = new Ext.Panel({autoHeight: true});
			appPanel.add(this.appForm);
			this.appForm.add(this.createAttachMentFs(this));
			var buttonPanel  = this.createButton();
			this.appForm.add(buttonPanel);
			this.appMainPanel = appPanel;
			return this.appMainPanel;
		},
		/**
		 * form表单
		 */
		createFrmPnl : function() {

				var _this = this;
				var wd=240;
				var txt_code = FormUtil.getTxtField({
				    fieldLabel: '编号',
				    name: 'code',
				    "width": wd,
				    "allowBlank": false,
				    "maxLength": "20"
				});
				
				var bdat_appDate = FormUtil.getDateField({
				    fieldLabel: '申请日期',
				    name: 'appDate',
				    "width": wd,
				    "allowBlank": false
				});
				
				var txt_travelMan = FormUtil.getTxtField({
				    fieldLabel: '出差人',
				    name: 'travelMan',
				    "width": wd,
				    "allowBlank": false
				});
				
				var txt_deptid = FormUtil.getTxtField({
				    fieldLabel: '部门',
				    name: 'deptid',
				    "width": wd
				});
						var txt_startDate = FormUtil.getDateField({name:'startDate',width:220});
						var txt_endDate = FormUtil.getDateField({name:'endDate',width:220});
						var comp_opDate = FormUtil.getMyCompositeField({
					itemNames : 'startDate,endDate',
						sigins : null,
						fieldLabel : '生效日期',
						width : true,
						sigins : null,
						name : 'comp_opDate',
						items : [txt_startDate, {
									xtype : 'displayfield',
									value : '至'
								}, txt_endDate]
					});
				var mon_amount = FormUtil.getMoneyField({
						fieldLabel : '借款金额',
						name : 'amount',
						"width" : wd,
						listeners: {
							change : function(e,newValue,oldValue){
							var newValue=Cmw.cmycurd(newValue);
								mon_amdou.setValue(newValue);
							}
						}
					});
			var mon_amdou = FormUtil.getReadTxtField({
				fieldLabel : '借款金额大写',
				name : 'damountd',
				"width" : wd
					});
				var txt_address = FormUtil.getTxtField({
				    fieldLabel: '出差地点',
				    name: 'address',
				    "width": "771"
				});
				
				var txa_reason = FormUtil.getTAreaField({
				    fieldLabel: '出差事由及目的',
				    name: 'reason',
				    "width": "771"
				});
				
				var txa_remark = FormUtil.getTAreaField({
				    fieldLabel: '备注',
				    name: 'remark',
				    "width": "771"
				});
				
				var layout_fields = [{
				    cmns: FormUtil.CMN_TWO,
				    fields: [txt_code,bdat_appDate,txt_travelMan, txt_deptid,mon_amount,mon_amdou]
				}
				,comp_opDate,txt_address, txa_reason, txa_remark];
				var frm_cfg = {
				    title: '#TITLE#',
				    url: '#SAVEURL#'
				};
				var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
				var filset=FormUtil.createLayoutFieldSet({title:"出差申请审批单"},layout_fields);
				var frm_cfg = {
				title : "出差申请审批单",
				height:true,
				url : './fcExempt_save.action',
				labelWidth : 115,
				items : [new Ext.Container({html:"<font size='11' color='blue' valign='top'><center>出差申请单</center></font>"}),filset]};
				var savefile=new Ext.Button({title:'保存'});
				var applyForm = FormUtil.createFrm(frm_cfg);
				this.appForm=applyForm;
				//	_this.attachMentFs.reload(params);
					return this.appForm;	
				},
		/**
		 * 刷新方法
		 */
		refresh : function() {

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
			var button_save = new Ext.Button({
						text : '保存',
						width : 100					})
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
		destroy : function() {
			if (this.appMainPanel) {
				this.appMainPanel.destroy();
				this.appMainPanel = null;
			}
		}
	}
});