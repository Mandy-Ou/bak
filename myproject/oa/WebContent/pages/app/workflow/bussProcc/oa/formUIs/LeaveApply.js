/**
 * 请假申请单
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
		appgrid_1 : null,
		dayoff:{},
		attachMentFs:null,
		overtimeEmp:[],
		appCmpts : {},
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
		 * 选择加班记录后，把对象传回来，用于保存时修改加剩余调休时间
		 */
		callback:function(dayoff,overtimeEmp){
			this.overtimeEmp[overtimeEmp.id]=overtimeEmp;
			this.dayoff=dayoff;
		},
		/**
		 * 创建主面板
		 */
		craeteMainPnl : function() {
			this.appForm = this.createFrmPnl();
			this.appgrid_1 = this.createDetailPnl();
			var appPanel = new Ext.Panel({autoHeight: true});
			appPanel.add(this.appForm);
			this.appForm.add(this.appgrid_1);
			this.attachMentFs=this.createAttachMentFs(this);
			this.appForm.add(this.attachMentFs);
			this.toolbar = this.createToolBar();
			this.appMainPanel = appPanel;
			return this.appMainPanel;
		},
		/**
		 * form表单
		 */
		createFrmPnl : function() {
			
			var _this = this;
			var wd = 240;
			var txt_procId = FormUtil.getHidField({
						fieldLabel : '流程实例ID',
						name : 'procId',
						value : '-1'
					});
			var txt_code = FormUtil.getReadTxtField({
						fieldLabel : '申请单编号',
						name : 'code',
						"width" : wd,
						"allowBlank" : false,
						"maxLength" : "20"
					});

			var bdat_appAppLDates = FormUtil.getDateField({
						fieldLabel : '申请日期',
						name : 'appDate',
//						"width" : wd,
						"allowBlank" : false
					});

			var txt_leaveManName = FormUtil.getReadTxtField({
						fieldLabel : '请假人',
						name : 'leaveManName',
//						"width" : wd,
						"allowBlank" : false
					});
			var txt_leaveMan = FormUtil.getHidField({
				fieldLabel : '请假人',
				name : 'leaveMan',
//				"width" : wd,
				"allowBlank" : false
			});

			var txt_deptId = FormUtil.getHidField({
						fieldLabel : '部门',
						name : 'deptId',
						value : '-1'
					});

			var txt_startDate = FormUtil.getDateField({
						id : 'startDate',
						name : 'startDate',
						width : 100
					});
			var txt_endDate = FormUtil.getDateField({
						id : 'endDate',
						name : 'endDate',
						width : 100 , 
						listeners:{
							change:function(){								
								var start = Ext.util.Format.date(Ext.getCmp('startDate').getValue(), 'Y-m-d'/*'Y-m-d H:i:s'*/);//格式化日期控件值
								var end = Ext.util.Format.date(Ext.getCmp('endDate').getValue(), 'Y-m-d'/* 'Y-m-d H:i:s'*/);//格式化日期控件值
								if(start != "" && end != ""){
								var startday = start.split("-")[2];
								var endday = end.split("-")[2];
								var resultday = endday - startday ;
								//开始时间小时 == 结束时间小时
								if(resultday == 0){
									ExtUtil.info({msg:"请假开始时间和结束时间不能一样"});
								}else if(resultday > 0){
									txt_Date.setValue(resultday);
								}else{
									ExtUtil.info({msg:"出错啦！结束日期不能小于开始日期"});
									}	
								}
							}}
					});
			var txt_Date = FormUtil.getReadTxtField({
						name : 'Date',
						width : 50
			});
			var bdat_appDate = FormUtil.getMyCompositeField({
						itemNames : 'startDate,endDate,Date',
						sigins : null,
						fieldLabel : '请假时间',
						width : 350,
						name : 'app',
						items : [txt_startDate, {
									xtype : 'displayfield',
									value : '至'
								}, txt_endDate, {
									xtype : 'displayfield',
									value : '共'
								},txt_Date,{
									xtype : 'displayfield',
									value : '天'
								}]
					});
			//TODO 或者加一个“人事部核定请假天数leaveDate
			var txt_leaveDate = FormUtil.getIntegerField({
							fieldLabel : '人事部核定天数' ,
							name : 'leaveDate',
//							width : 80,
							allowBlank : false,
							value : 0
					});
			var txt_leaveType = FormUtil.getRCboField({
						fieldLabel : '请假类型',
						name : 'leaveType',
						register : REGISTER.GvlistDatas,
						restypeId : '300004',
						allowBlank : false/*,
						"width" : wd*/
					});
					
			var rad_buckleType = FormUtil.getRadioGroup({
						fieldLabel : '扣假形式',
						name : 'buckleType',
						"width" : 400,
						"allowBlank" : false,
						"maxLength" : 10,
						"items" : [{
									"boxLabel" : "扣年假",
									"name" : "buckleType",
									"inputValue" : "1"
								}, {
									"boxLabel" : "扣薪",
									"name" : "buckleType",
									"inputValue" : "2"
								}, {
									"boxLabel" : "调休",
									"name" : "buckleType",
									"inputValue" : "3",
									listeners : {
										'check' : function() {
											if (this.checked) {
												_this.globalMgr.appgrid.enable();
											} else {
												_this.globalMgr.appgrid.disable();
											}
										}
									}
								}, {
									"boxLabel" : "其他",
									"name" : "buckleType",
									"inputValue" : "4"
								}]
					});

			var txa_endDate = FormUtil.getTAreaField({
						fieldLabel : '请假事由',
						name : 'reason',
						"width" : 860,
						"maxLength" : "200"
					});

			var txa_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						"width" : 860
					});

			var layout_fields = [
					{
						cmns : FormUtil.CMN_THREE,
						fields : [txt_code,  txt_leaveManName,bdat_appAppLDates , txt_deptId,txt_procId]
					},bdat_appDate, txt_leaveDate , txt_leaveType, rad_buckleType,txa_endDate,txa_remark,txt_leaveMan];

			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var filset = FormUtil.createLayoutFieldSet({
						title : "用于调休的加班记录"
					}, layout_fields);
			var frm_cfg = {
				height : true,
				url : './oaLeaveApply_save.action',
				labelWidth : 115,
				items : [new Ext.Container({
					html : '<div style = "height : 40px;"><div style = "left : 650px;color : blue ; position : fixed ;font-size : 30px;z-index : 99">请假申请单</div></div>'
//					html : "<font size='6' color='blue'><center>请假申请单</center></font>"
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
				var _self = this;
				EventManager.add("./oaLeaveApply_add.action", _self.appForm,{sfn : function(formdate) {
					_self.appForm.setFieldValues(formdate);
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
				params = {formType:ATTACHMENT_FORMTYPE.FType_14,sysId:-1,formId : uuid,isNotDisenbaled:true};
			}
			cfg.params = params;
			var attachMentFs = new Ext.ux.AppAttachmentFs(cfg);
			return attachMentFs;
		},
		
		/**
		 * 保存表单数据
		 * @param callback 当表单保存成功时的回调函数
		 * @param ffnCallback	当申请表单保存失败时，要删除流程数据
		 * @param procInstanceData Oa 工作流程实例数据,对应Oa_ProcInstance 表数据
		 */
		saveData : function(callback){
			var _this = this;
			var cfg={
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
				},beforeMake : function(beforeData){
					var store =  _this.globalMgr.appgrid.getStore(); 
					var count = store.getCount();
					var datas = [];
					var odata=[];
					if(count>0){
						for(var i = 0 ;i<count;i++){
							var record = store.getAt(i);
							//加班申请单id
							var overtimeId = record.get("overtimeId");
							//销假方式
							var otype = record.get('otype');
							//原加班时长（小时）
							var ovhours =record.get("ovhours");
//									//本次调休时长
							var cuhours = record.get('cuhours');
							//剩余调休时长
							var nehours =  ovhours - cuhours;
//									alert(overtimeId+"加班申请单id...."+otype+"销假方式..."+ovhours+"..."+cuhours+"///"+nehours);
							var params = {overtimeId : overtimeId, otype :otype , ovhours :ovhours , cuhours : cuhours , nehours : nehours };
							var oeId=record.get("oeId");
							var yohours=record.get("yohours");
							var xstatus=record.get("xstatus");
							var oemp={id:oeId,yohours:yohours,xstatus:xstatus};
							datas.push(params);
							odata.push(oemp)
							}
							beforeData.oemp=Ext.encode(odata);
							beforeData.store = Ext.encode(datas);
						}
					},sfn : function(jsonData) {
						var applyId = jsonData.id;
						var entityName = "LeaveApplyEntity";
						callback(applyId,entityName);
						var params = {formType:ATTACHMENT_FORMTYPE.FType_14,sysId:-1,formId : applyId,isNotDisenbaled:true};
						_this.attachMentFs.updateTempFormId(params,true);
					}
			};
			EventManager.frm_save(_this.appForm,cfg);
		},
		
		/**
		 * 创建详情面板
		 */
		createDetailPnl : function() {
			var _this = this;
			var structure_1 = [{
						header : '编号',
						name : 'code',
						width : 150
					}, {
						header : '加班起止时间',
						name : 'startData',
						width : 200
					}, {
						header : 'ID',
						hidden:true,
						name : 'id'
					}, {
						header : '原加班时长',
						name : 'ovhours'
						
					}, {
						header : '剩余调休时长',
						name : 'nehours'
					}, {
						header : '本次调休时长',
						name : 'cuhours'
					}, {
						header : '销假方式',
						hidden:true,
						name : 'otype'
					}, {
						header : '已调休时长',
						hidden:true,
						name : 'yohours'
					}, {
						header : '调休状态',
						hidden:true,
						name : 'xstatus'
					}, {
						header : '加班人员ID',
						hidden:true,
						name : 'oeId'
					}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
						title : '用于调休的加班记录',
						structure : structure_1,
						needPage : false,
						width : true,
						height : 150,
						tbar : _this.createToolBar(),
						isLoad : false,
						keyField : 'id',
						disabled : true//刚开始是不能填写的，只有在扣假形式中选择 ‘调休’才可以
					});
			_this.globalMgr.appgrid = appgrid_1;
			return appgrid_1;
		},
		/**
		 * 创建toolbar工具栏
		 * 
		 */
		createToolBar : function() {
			var _self = this;
			var barItems = [{
						token : Btn_Cfgs.INSERT_OVERTIME_BTN_TXT,
						text : Btn_Cfgs.INSERT_OVERTIME_BTN_TXT, /*-- 选择加班申请 --*/
						iconCls : Btn_Cfgs.INSERT_OVERTIME_CLS,
						tooltip : Btn_Cfgs.INSERT_OVERTIME_TIP_BTN_TXT,
						handler : function(){
							_self.globalMgr.winEdit.show({key : this.token,_self : _self,appgrid_1 :_self.globalMgr.appgrid});
						}
					}, {
						text : Btn_Cfgs.INSERT_DEL_BTN_TXT, /*-- 删除 --*/
						iconCls : Btn_Cfgs.INSERT_DEL_BTN_CLS,
						tooltip : Btn_Cfgs.INSERT_DEL_TIP_BTN_TXT,
						handler : function(){
							var srow=_self.appgrid_1.getSelRow();
							var ds=_self.appgrid_1.getStore();
							ds.remove(srow);
						}
					}];
			var appBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'left',
						controls : barItems
					});
			return appBar;
		},
		
		globalMgr :{
			
			appgrid : null,
			activeKey : null , 
			tokenMgr : {
					INSERT_OVERTIME_BTN_TXT : "选择加班申请单",
					MODIFY_BTN_TXT : "编辑"
			},			
			//点击'添加加班申请单'涉及的导包操作。
			winEdit : {
					show : function(parentCfg) {
						var _this = parentCfg._self;
						//获取  定义的数据
						var tokenMgr = _this.globalMgr.tokenMgr;
						//获取用户点击的按钮
						var winkey = parentCfg.key;
						_this.globalMgr.activeKey = winkey;
						var parent = _this;
						parentCfg.parent = parent;
						//判断缓存中是否有数据
						if(_this.appCmpts[winkey]){
							_this.appCmpts[winkey].show(parentCfg);
						}else {
							var winModule=null;
							//根据用户点击的按钮进行选择
							switch(winkey){
								case tokenMgr.INSERT_OVERTIME_BTN_TXT :{
									    winModule = "Dayoff";
										break;
									}
								case tokenMgr.MODIFY_BTN_TXT :{
										break;
									}	
							}
						Cmw.importPackage('pages/app/workflow/bussProcc/oa/formUIs/' + winModule,function(module) {
							_this.appCmpts[winkey] = module.WinEdit;
							_this.appCmpts[winkey].show(parentCfg);
						});
					}
				}
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