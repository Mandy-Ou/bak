
/**
 * 加班申请单
 * 
 * @author liting
 */
define(function(require, exports) {
	exports.viewUI = {
		appMainPanel : null,
		appForm : null,
		tab : null,
		gobule : null,
		params : null,
		createButton : null,
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
			var appPanel = new Ext.Panel({autoHeight: true});
			appPanel.add(this.appForm);
			this.attachMentFs=this.createAttachMentFs(this)
			this.appForm.add(this.attachMentFs);
//			var buttonPanel = this.createButton();
//			this.appForm.add(buttonPanel);
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
					fieldLabel : '流程实例id',//方便阅读
					name : 'procId',
					value : '-1'
					});
			var txt_Isenabled = FormUtil.getHidField({
						fieldLabel : '可用状态' , 
						name : 'Isenabled',
						value : '-1'
					});
			var txt_code = FormUtil.getReadTxtField({
						fieldLabel : '编号',
						name : 'code',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : "20"
					});
			_this.gobule.txt_code = txt_code;
			var bdat_appDate = FormUtil.getDateField({
						fieldLabel : '申请日期',
						name : 'appDate',
						"allowBlank" : false
					});

			var txt_overMan = FormUtil.getReadTxtField({
						fieldLabel : '加班人',
						name : 'overMan',
						"allowBlank" : false
					});
			_this.gobule.txt_overMan = txt_overMan;
			var txt_deptId = FormUtil.getHidField({
						fieldLabel : '部门ID',
						name : 'deptId'
					});
			var txt_deptName = FormUtil.getReadTxtField({
						fieldLabel : '部门',
						name : 'deptName'
					});
			_this.gobule.txt_deptId = txt_deptId;
			
			var bdat_startDate = FormUtil.getDateFormatField({
						fieldLabel : '加班起始时间',
						id : 'startDate' ,
						name : 'startDate',
						format : 'Y-m-d H:i',
						"width" : 125,
						"allowBlank" : false
					});

			var bdat_endDate = FormUtil.getDateFormatField({
						fieldLabel : '截止时间',
						id : 'endDate',
						name : 'endDate',
						format : 'Y-m-d H:i',
						"width" : 125,
						"allowBlank" : false,
						listeners:{
							change:function(){								
								var start = Ext.util.Format.date(Ext.getCmp('startDate').getValue(), 'Y-m-d H:i:s');//格式化日期控件值
								var end = Ext.util.Format.date(Ext.getCmp('endDate').getValue(), 'Y-m-d H:i:s');//格式化日期控件值
								if(start != "" && end != ""){
									startSplit = start.split(":"); //分割开始时间
									endSplit = end.split(":"); //分割结束时间
								var starty = startSplit[0].split(" ")[0];//获取开始日期
								var endy = endSplit[0].split(" ")[0];//获取结束日期
								var startday = starty.split("-")[2];
								var endday = endy.split("-")[2];
								var resultday = endday - startday ;
								var starth = startSplit[0].split(" ")[1]; //开始时间小时
								var endh = endSplit[0].split(" ")[1]; //结束时间小时
								var startm = startSplit[1]; //开始时间分钟
								var endm = endSplit[1]; //结束时间分钟
								var resultm = endm - startm;//分钟差
								var resulth = endh - starth;//小时差
								/**
								 * 情况： 1.结束时间（end）大（等）于开始时间（start），开始计算加班小时。
								 * 		  1-1.end=start ，计算分钟之差，如果大于30分钟，按照四舍五入的方法，加班1小时
								 * 		  1-2.end > start ，计算小时时间；再计算分钟之差，如果分钟之差大于30，则加班为end-start+1
								 * 	      1-3.end > start ，计算小时时间，再计算分钟之差，如果分钟之差小于30，则加班时间为end-start
								 */
								
								//开始时间小时 == 结束时间小时
								if(resultday == 0){
									if(resulth == 0 && resultm > 30){
										txt_ohours.setValue(1);
									}else if(resulth > 0 ){
										if(resultm >30){
											txt_ohours.setValue(resulth+1);
										}else{
											txt_ohours.setValue(resulth);
										}
									}else{
										alert("数据出错");
									}
								}else if(resultday > 0){
									if(resulth == 0){
										if(resultm > 30){
											txt_ohours.setValue(1+resultday*24);
										}else{
											txt_ohours.setValue(resultday*24);
										}
									}else if(resulth > 0 ){
										if(resultm >30){
											txt_ohours.setValue(resulth+1+resultday*24);
										}else{
											txt_ohours.setValue(resulth+resultday*24);
										}
									}else{
										alert("数据出错");
									}
								}else{
									Ext.MessageBox.alert("出错了！","结束日期不能小于开始日期");
								}	
								}
							}}});
					
			var txt_ohours = FormUtil.getTxtField({
						fieldLabel : '加班时长(小时)',
						name : 'ohours',
						"width" : 50,
						"allowBlank" : false,
						"value" : "0"
					});
			var bdat_asppDate = FormUtil.getMyCompositeField({
						itemNames : 'startDate,endDate,ohours',
						fieldLabel : '加班时间',
						width : 350,
						allowBlank : false,
						sigins : null,
//						name : 'comp_opDate',
						items : [bdat_startDate, {
									xtype : 'displayfield',
									value : '至'
								}, bdat_endDate, {
									xtype : 'displayfield',
									value : '共'
								},txt_ohours,{
									xtype : 'displayfield',
									value : '小时'
								}]
					});
					
			var txt_overType = FormUtil.getTxtField({
						fieldLabel : '加班地点',
						name : 'address',
						"allowBlank" : false
					});
			var overTimeType =FormUtil.getRadioGroup({
						fieldLabel : '加班类型',
						name : 'overtimeType',
						"allowBlank" : false,
						"width" :wd,
						items : [{
							boxLabel : '常规', 
							name:'overtimeType',
							inputValue:0, 
							checked: true
						},{
							boxLabel : '节假日加班',
							name:'overtimeType',
							inputValue:1}
					]});
			var structure = [{header: '员工列表',name: 'name',width:200}];
			var txt_refmans = new Ext.ux.grid.AppComBoxGrid({
							gridWidth :200,
							fieldLabel : '证明人',
							name : 'refmans',
						    structure:structure,
						    dispCmn:'name',
						    isAll:true,
						    url : './hrEmployee_list.action',
						    needPage : false,
							"maxLength" : "50" ,
						    isLoad:true
					});

			var txa_reason = FormUtil.getTAreaField({
						fieldLabel : '加班原因说明',
						name : 'reason',
						"width" : 860,
						"maxLength" : "200"
					});

			var txa_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						"width" : 860
					});
			var layout_fields = [{
					cmns : FormUtil.CMN_THREE,
					fields : [txt_code, txt_overMan, txt_deptName,bdat_appDate, 
						overTimeType,txt_overType,txt_Isenabled,txt_procId]
				},{
					cmns : FormUtil.CMN_TWO,
					fields : [txt_refmans,bdat_asppDate]
				}, txa_reason, txa_remark,txt_deptId];
				
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			
			var filset = FormUtil.createLayoutFieldSet({
						title : "加班申请审批单"
					}, layout_fields);
					
			var frm_cfg = {
				height : true,
				url : './oaOvertimeApply_save.action',
				labelWidth : 115,
				items : [new Ext.Container({
					html : '<div style = "height : 40px;"><div style = "left : 650px;color : blue ; position : fixed ;font-size : 30px;z-index : 99">加班申请单</div></div>'
//					html : "<font size='11' color='blue' valign='top'><center>加班申请单</center></font>"
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
			EventManager.add("./oaOvertimeApply_add.action", _this.appForm,{sfn : function(formdate) {
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
			var cfg = {title:'相关材料附件',isLoad:true,isSave:true,isNotDisenbaled:true};
			if(!params){
				var uuid = Cmw.getUuid();
				params = {formType:ATTACHMENT_FORMTYPE.FType_13,sysId:-1,formId : uuid,isNotDisenbaled:true};
			}
			cfg.params = params;
			var attachMentFs = new Ext.ux.AppAttachmentFs(cfg);
			return attachMentFs;
		},

//		createButton : function() {
//			var _self = this ;
//			var button_save = new Ext.Button({
//						text : '保存',
//						width : 100 , 
//						handler : function(){
//							EventManager.frm_save(_self.appForm,{sfn:function(jsonData){
//										_self.refresh(jsonData);
//								},ffn:function(jsonData){
//									
//							}});
//						}
//					})
//			var button_reset = new Ext.Button({
//						text : '重置',
//						width : 100 ,
//						handler : function(){
//							EventManager.frm_reset(_self.appForm, [_self.gobule.txt_code,_self.gobule.txt_deptId,_self.gobule.txt_overMan]);
//						}
//					})
//			var buttons = [button_save, button_reset];
//			var btnPanel = new Ext.Panel({
//						buttonAlign : 'center',
//						buttons : buttons
//					});
//			return btnPanel;
//		},

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
//				var amount = _this.appForm.getValueByName("amount");
//				if(!amount || parseFloat(amount) <= 0){
//					ExtUtil.alert({msg:'借款金额必须大于0!'});
//					flag = false;
//				}
				return flag;
			},sfn:function(jsonData){
				var applyId = jsonData.id;
				var entityName = "OvertimeApplyEntity";
				callback(applyId,entityName);
				var params = {formType:ATTACHMENT_FORMTYPE.FType_13,sysId:-1,formId : applyId,isNotDisenbaled:true};
				_this.attachMentFs.updateTempFormId(params,true);
				
			}});
		},
		
		gobule : {
			txt_code : null ,
			txt_overMan : null ,
			txt_deptId : null 
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