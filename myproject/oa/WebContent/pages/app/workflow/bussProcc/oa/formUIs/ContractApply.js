/**
 * 合同申请
 * 
 * @author liting
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		appForm : null,
		tab : null,
		params : null,
		createAttachMentFs : null,
		attachMentFs:null,
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
			var appPanel = new Ext.Panel({
						autoHeight : true
					});
			appPanel.add(this.appForm);
			this.attachMentFs=this.createAttachMentFs(this)
			this.appForm.add(this.attachMentFs);
			this.appMainPanel = appPanel;
			return this.appMainPanel;
		},
		/**
		 * form表单
		 */
		createFrmPnl : function() {
			var _this = this;
			var wd = 200;
			var txt_procId = FormUtil.getHidField({
						fieldLabel : '流程实例ID',
						name : 'procId',
						value : '-1'
					});
			var txt_Isenabled = FormUtil.getHidField({
						name : 'Isenabled',
						value : '-1'	
					});
			var txt_code = FormUtil.getReadTxtField({
						fieldLabel : '合同编号',
						name : 'code',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : "20"
					});

			var bdat_getDate = FormUtil.getDateField({
						fieldLabel : '申请日期',
//						//"width" : wd,
						"allowBlank" : false
					});

			var txt_leaveMan = FormUtil.getReadTxtField({
				fieldLabel : '经办人',
				name : 'submitMan',
				//"width" : wd,
				"allowBlank" : false
			});

			var txt_deptId = FormUtil.getHidField({
						fieldLabel : '部门ID',
						name : 'deptId'/*,
						"width" : wd*/
					});
			var txt_deptName = FormUtil.getReadTxtField({
				fieldLabel : '部门',
				name : 'deptName'
				//"width" : wd,
			});
			
			var txt_name = FormUtil.getTxtField({
						fieldLabel : '合同名称',
						name : 'name',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : "100"
					});
			var structure = [{header: '合同单位列表',name: 'name',width:200}];
			var txt_storeId = new Ext.ux.grid.AppComBoxGrid({
						gridWidth :200,
						fieldLabel : '合同单位',
						name:'contractId',
						structure:structure,
					    dispCmn:'name',
					    isAll:true,
					    url : './oaBillUnit_list.action',
					    needPage : false,
					    isLoad:true,
						allowBlank : false/*,
						"width" : wd*/
						});
			var txt_job = FormUtil.getTxtField({
						fieldLabel : '对方单位',
						name : 'job',
						"width" :wd,
						"maxLength" : "100"
					});

			var bdat_appDate = FormUtil.getDateField({
						fieldLabel : '提交日期',
						name : 'appDate',
						//"width" : wd,
						"allowBlank" : false
					});

			var  comp_travelopDate= FormUtil.getIntegerField({
						fieldLabel : '合同份数',
						name : 'count',
						"value" : "0",
						"maxLength" : 10
					});
					
			var int_count = FormUtil.getMyCompositeField({
						itemNames : 'count',
						sigins : null,
						fieldLabel : '合同份数',
						width : wd,
						name : 'count',
						items : [comp_travelopDate, {
							xtype : 'displayfield',
							value : '份'
						}]
				});
			var txt_paysType = FormUtil.getRCboField({
						fieldLabel : '合同类型',
						name : 'ctype',
						register : REGISTER.GvlistDatas,
						restypeId : '300005',
						allowBlank : false/*,
						"width" : wd*/
					});
			var bdat_endDate = FormUtil.getDateField({
						fieldLabel : '有效期限',
						name : 'endDate',
						//"width" : wd,
						"allowBlank" : false,
						"maxLength" : "100"
					});

			var mon_amount = FormUtil.getMoneyField({
						fieldLabel : '合同金额',
						name : 'amount',
						"value:" : "0"
					});
			var txt_payType = FormUtil.getRCboField({
						fieldLabel : '付款方式',
						name : 'payType',
						register : REGISTER.GvlistDatas,
						restypeId : '300003',
						allowBlank : false
					});

			var txa_content = FormUtil.getTAreaField({
						fieldLabel : '合同主要内容',
						name : 'content',
						"width" :860,
						"maxLength" : "200"
					});

			var txa_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						"width" :860
					});

			var layout_fields = [{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_code, txt_name,txt_storeId,txt_paysType,int_count, mon_amount, txt_job, txt_leaveMan,txt_deptName,bdat_getDate,
						bdat_appDate, bdat_endDate, txt_payType,txt_procId]
					},txa_content, txa_remark,txt_deptId];
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var filset = FormUtil.createLayoutFieldSet({
						title : "合同申请审批单"
					}, layout_fields);
			var frm_cfg = {
				height : true,
				url : './oaContractApply_save.action',
				labelWidth : 115,
				items : [new Ext.Container({
					html : '<div style = "height : 40px;"><div style = "left : 650px;color : blue ; position : fixed ;font-size : 30px;z-index : 99">合同申请单</div></div>'
//					html : "<font size='11' color='blue' valign='top'><center>合同申请单</center></font>"
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
				var _this=this;
				EventManager.add("./oaContractApply_add.action", _this.appForm,{sfn : function(formdate) {
					_this.appForm.setFieldValues(formdate);
				}
			});
		},
		/**
		 * 创建附件FieldSet 对象
		 * 
		 * @return {}
		 */
		createAttachMentFs : function(_this,params) {
			/*
			 * ----- 参数说明： isLoad 是否实例化后马上加载数据 dir : 附件上传后存放的目录， mortgage_path
			 * 定义在 resource.properties 资源文件中 isSave : 附件上传后，是否保存到 ts_attachment
			 * 表中。 true : 保存 params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中
			 * ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔} 当 isLoad = false 时，
			 * params 可在 reload 方法中提供 ----
			 */
			var dir="contract_apply_path";
			var cfg = {title:'相关材料附件',isLoad:false,dir : dir,isSave:true,isNotDisenbaled:true};
			if(!params){
				var uuid = Cmw.getUuid();
				params = {formType:ATTACHMENT_FORMTYPE.FType_12,sysId:-1,formId : uuid,isNotDisenbaled:true};
			}
			cfg.params = params;
			var attachMentFs = new Ext.ux.AppAttachmentFs(cfg);
			return attachMentFs;
		},
		/**
		 * 保存方法
		 */
		saveData:function(callback){
			var _this=this;
			var cfg={
				sfn:function(formData){
					var applyId = formData.id;
					var entityName = "ContractApplyEntity";
					callback(applyId,entityName);
					var params = {formType:ATTACHMENT_FORMTYPE.FType_12,sysId:-1,formId : formData.id,isNotDisenbaled:true};
					_this.attachMentFs.updateTempFormId(params,true);
				}
			}
			EventManager.frm_save(_this.appForm,cfg);		
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