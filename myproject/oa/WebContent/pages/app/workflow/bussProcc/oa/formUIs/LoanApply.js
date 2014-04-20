/**
 * 借款申请单
 * 
 * @author liting
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		appForm : null,
		tab : null,
		params : null,
		attachMentFs : null,
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
			this.appMainPanel = appPanel;
			return this.appMainPanel;
		},
		/**
		 * form表单
		 */
		createFrmPnl : function() {
			var _this = this;
			var wd = 200;
			var txt_id = FormUtil.getHidField({
						fieldLabel : 'id',
						name : 'id'
					});
			var txt_lsenabled = FormUtil.getHidField({
						fieldLabel : '可用标志',
						name : 'isenabled'
					});
			var txt_code = FormUtil.getReadTxtField({
						fieldLabel : '借款单编号',
						name : 'code',
						"width" : wd,
						"maxLength" : "20"
					});
			var bdat_loanDate = FormUtil.getDateField({
						fieldLabel : '借款日期',
						name : 'loanDate',
//						"width" : wd,
						"allowBlank" : false
					});

			var txt_loanMan = FormUtil.getReadTxtField({
						fieldLabel : '借款人',
						name : 'loanMan',
//						"width" : wd,
						"allowBlank" : false
					});
			var txt_deptName = FormUtil.getReadTxtField({
						fieldLabel : '部门',
						name : 'deptName'/*,
						"width" : wd*/
					});
			var mon_amount = FormUtil.getMoneyField({
						fieldLabel : '借款金额',
						name : 'amount',
						width : 85,
						allowBlank : false,
						unitText : '元',
						autoBigAmount : true,
						unitStyle : 'margin-left:4px;color:red;font-weight:bold'
					});

					
			/**
			 * TODO 2.加 “借款类型”单选框，取值[备用金，借款]
			 */
			var borrow_type = FormUtil.getRadioGroup({
						fieldLabel : '借款类型',
						name : 'rendType',
						"allowBlank" : false,
//						"width" : wd,
						"items" : [{
									"boxLabel" : "备用金",
									"name" : "rendType",
									"inputValue" : "1"
								}, {
									"boxLabel" : "借款",
									"name" : "rendType",
									"inputValue" : "2"
								}]

					});
			var bdat_payDate = FormUtil.getDateField({
						fieldLabel : '预计还款日期',
						name : 'payDate',
//						"width" : wd,
						"allowBlank" : false
					});

			/**
			 * TODO 3.借款类型 改为 还款类型。 还款类型的字段为loanType
			 */
			var rad_loanType = FormUtil.getRadioGroup({
				fieldLabel : '还款类型',
				name : 'loanType',
				"allowBlank" : false,
				register : REGISTER.GvlistDatas,
				type : '300003'
			});

			var txt_accountNum = FormUtil.getTxtField({
						fieldLabel : '借款帐号',
						name : 'accountNum',
						"width" : wd,
						"maxLength" : "20"
					});

			var txt_accountName = FormUtil.getTxtField({
						fieldLabel : '帐户名',
						name : 'accountName',
//						"width" : wd,
						"maxLength" : "20"
					});

			var txt_bankName = FormUtil.getTxtField({
						fieldLabel : '开户行',
						name : 'bankName',
						"width" : wd,
						"maxLength" : "60"
					});

			var txa_reason = FormUtil.getTAreaField({
						fieldLabel : '借款事由',
						name : 'reason',
						"width" : 600,
						"maxLength" : "200",
						"allowBlank" : false
					});

			var txa_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
//						"width" : 755,
						"maxLength" : "200",
						"allowBlank" : false
					});
			//流程实例
			var txt_procId= FormUtil.getHidField({
						fieldLabel : '流程实例',
						name : 'procId',
						value : '-1'
				
			});
			var  loanManInfo=[{
						 cmns : FormUtil.CMN_THREE,
						 fields : [txt_accountNum,txt_accountName, txt_bankName]
				}];
			//借款账号、开户行、账户名的fieldset布局
			var loanManfieldset = FormUtil.createLayoutFieldSet({
//   					checkboxToggle: true,//显示checkbox框
   					    collapsed :true,//默认关闭
						title : '账号信息填写',
						labelWidth : 110,
						THREE_WIDTH : 900
				},loanManInfo);
				
			this.attachMentFs = this.createAttachMentFs(this);	
			var layout_fields = [{
				cmns : FormUtil.CMN_THREE,
				fields : [txt_code,txt_loanMan,txt_deptName ]
				},{
					cmns : FormUtil.CMN_THREE,
					fields : [bdat_loanDate,bdat_payDate, mon_amount,
							borrow_type, rad_loanType,
							txt_procId,txt_id, txt_lsenabled]
				},txa_reason,loanManfieldset,this.attachMentFs];
			
			var filset = FormUtil.createLayoutFieldSet({
						title : "借款审批单"
					}, layout_fields);
			var frm_cfg = {
				height : true,
				url : './oaLoanApply_save.action',
				labelWidth : 115,
				items : [new Ext.Container({
						html : '<div style = "height : 40px;"><div style = "left : 650px;color : blue ; position : fixed ;font-size : 30px;z-index : 99">借款单</div></div>'
				}), filset]
			};
			/*
			 * var savefile = new Ext.Button({ title : '保存'
			 * 
			 * });
			 */

			var applyForm = FormUtil.createFrm(frm_cfg);
			this.appForm = applyForm;
			// _this.attachMentFs.reload(params);
			return this.appForm;
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
			var sysId = this.params.sysid;
			var uuid = Cmw.getUuid();
			var _params = this.getAttachParams(uuid);
			var attachMentFs = new Ext.ux.AppAttachmentFs({
						title : '相关材料附件',
						isLoad : false,
						dir : 'mort_path',
						isSave : true,
						params:_params,
						isNotDisenbaled : true
					});
			return attachMentFs;
		},
		/**
		 * 重新加载附件对象的数据
		 * @params formId 业务单ID
		 */
		reloadAttachments : function(formId){
        	if(!formId){
        		ExtUtil.error({msg:'申请单ID参数："formId" 不能为空!'});
        	}
        	var _params = this.getAttachParams(formId);
        	this.attachMentFs.reload(_params);
		},
		/**
		 * 获取附件参数
		 * @params formId 业务单ID
		 */
		getAttachParams : function(formId){
			var sysId = this.params.sysid;
			var _params = {formId:formId,formType:ATTACHMENT_FORMTYPE.FType_11,sysId:sysId,isNotDisenbaled:true};
			return _params;
		},
		/**
		 * 保存表单数据
		 * @param callback 当表单保存成功时的回调函数
		 * @param ffnCallback	当申请表单保存失败时，要删除流程数据
		 * @param procInstanceData Oa 工作流程实例数据,对应Oa_ProcInstance 表数据
		 */
		saveData : function(callback){
			var _this = this;
			EventManager.frm_save(_this.appForm,{
			/**
			 * 验证表单数据合法性
			 */
			valid:function(baseForm){
				var flag = true;
				var amount = _this.appForm.getValueByName("amount");
				if(!amount || parseFloat(amount) <= 0){
					ExtUtil.alert({msg:'借款金额必须大于0!'});
					flag = false;
				}
				return flag;
			},sfn:function(jsonData){
				var applyId = jsonData.id;
				var entityName = "LoanApplyEntity";
				callback(applyId,entityName);
				var params = {formType:ATTACHMENT_FORMTYPE.FType_11,sysId:-1,formId : applyId,isNotDisenbaled:true};
				_this.attachMentFs.updateTempFormId(params,true);
				
			}});
		},
		/**
		 * 刷新方法
		 */
		refresh : function() {
			var _this=this;
			var applyId = this.params.applyId;
			if(applyId){
				EventManager.get('./oaLoanApply_get.action',{params:{id:applyId},sfn:function(json_data){
					_this.appForm.setFieldValues(json_data);
					_this.reloadAttachments(applyId);
				}});
			}else{
				EventManager.add("./oaLoanApply_add.action", _this.appForm,{sfn : function(formdata) {
						_this.appForm.setFieldValues(formdata);
					}
				});
			}
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