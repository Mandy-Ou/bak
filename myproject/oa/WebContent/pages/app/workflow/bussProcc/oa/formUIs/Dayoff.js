/**
 * #DESCRIPTION#
 * @author smartplatform_auto
 * @date 2014-01-18 12:32:59
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		tbar : null ,
		overtimeEmp:{},
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.tbar = this.createTbar();
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.MyWindow({
					tbar : this.tbar,
					width : 600,
					height : 230,
					items : [this.appFrm]
			});
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
		},
		
		createTbar : function(){
				var _self = this;
					var ttbar = [{
						text : Btn_Cfgs.SAVE_BTN_TXT,	/*-- 保存 --*/ 
						iconCls:Btn_Cfgs.SAVE_CLS,
						tooltip:Btn_Cfgs.SAVE_TIP_BTN_TXT,
						handler : function() {
							_self.saveData();	
							EventManager.frm_reset(_self.appFrm);
						}
					}, {
						text : Btn_Cfgs.RESET_BTN_TXT,  /*-- 重置 --*/
						iconCls:Btn_Cfgs.RESET_CLS,
						tooltip:Btn_Cfgs.RESET_TIP_BTN_TXT,
						handler : function() {
							EventManager.frm_reset(_self.appFrm);
						}
					},{
						text : Btn_Cfgs.CLOSE_BTN_TXT,  /*-- 关闭 --*/
						iconCls:Btn_Cfgs.CLOSE_CLS,
						tooltip:Btn_Cfgs.CLOSE_TIP_BTN_TXT,
						handler : function() {
							_self.appWin.hide();
						}
					}]
			
					var toolBar = new Ext.ux.toolbar.MyCmwToolbar({
						aligin : 'right',
						controls : ttbar
					});
					return toolBar;
				},
		/**
		 * 销毁组件
		 */
		destroy : function(){
			if(!this.appWin) return;
			this.appWin.close();	//关闭并销毁窗口
			this.appWin = null;		//释放当前窗口对象引用
		},
		
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parent.refresh)_this.parent.refresh(_this.optionType, data);	
		},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
			var _this=this;
			var txt_overtimeId = FormUtil.getHidField({
					name : 'overtimeId'
					});
			var txt_leaveId = FormUtil.getHidField({
					name : 'leaveId'
					});
			var txt_applyId = FormUtil.getHidField({
				name : 'applyId'
			});
			var txt_overMan = FormUtil.getHidField({
				name : 'overMan'
			});
			var structure = [{
					header : 'Id' , name :'id',hidden : true},{
					header : '加班申请单ID' , name :'overtimeId',hidden : true},{
					header : '加班申请单编号',name: 'code',width:150},{
					header : '加班起始时间',name : 'startDate',width : 125,hidden : true},{
					header : '加班截止时间',name : 'endDate',width : 125,hidden : true},{
					header : '加班时长(小时)' , name : 'ovhours',width :100},{
					header : '已调休时长(小时)' ,name :'yohours' ,width :100}	,
					{header : '加班人',name : 'overMan',width : 125,hidden : true},
					{header : '加班地点',name : 'address',width : 125,hidden : true},
					{header : '加班原因说明',name : 'reason',width : 125,hidden : true},
					{header : '证明人',name : 'refmans',width : 125,hidden : true},
					{header : '加班类型',name : 'refmans',width : 125,hidden : true},
					{header : '调休状态',name : 'xstatus',width : 125,hidden : true},
					{header : '可用标志',name : 'isenabled',width : 125,hidden : true}
					];
			/**
			 * 这里注意：要查询加班人员表（OvertimeEmp） ，
			 * 条件：当前用户  
			 * 结果：获取每条数据的ApplyID获取加班的编号
			 */
			var txt_overtime = new Ext.ux.grid.AppComBoxGrid({
//					gridWidth :800,
				    fieldLabel: '加班申请单编号',
				    name: 'overtime',
				    "width": 125,
				    structure:structure,
				    dispCmn:'code',
				    isAll:true,
				    url : './oaOvertimeEmp_list.action',
				    needPage : false,
				    isLoad:true,
				    "allowBlank": false,
				    //通过回调函数将数据填充到其他的表格中
				    callback : ck_callback
				});	
			txt_overtime.menu.on("show",function(){
				txt_overtime.grid.reload();
			})
				
				
			function ck_callback(grid , value ,selRows){
				var record = selRows[0];
				_this.overtimeEmp=record.json;
				//获取加班人员表的id
				txt_overtimeId.setValue(record.get('overtimeId'));
				//加班起始时间
				txt_startDate.setValue(record.get('startDate'));
				//加班截止时间
				txt_endDate.setValue(record.get('endDate'));
				//原加班时长
				txt_ovhours.setValue(record.get('ovhours'));
				var time = record.get('ovhours')-record.get('yohours');
				//剩余调休时长
				txt_nehours.setValue(time);
				//本次调休时长默认为可以调休时长的最大值
				txt_cuhours.setValue(time);
				//加班起始时间
				txt_startData.setValue(record.get('startDate')+"-"+record.get('endDate'));
				}
			var txt_startDate = FormUtil.getReadTxtField({
				    fieldLabel: '加班起始时间',
				    name: 'startDate',
				    "width": 125,
				    format : 'yyyy年MM月dd日',
				    "allowBlank": false
				});
				
			var txt_endDate = FormUtil.getReadTxtField({
				    fieldLabel: '加班截止时间',
				    name: 'endDate',
				    format : 'yyyy年MM月dd日',
				    "width": 125,
				    "allowBlank": false
				});
			//加班起始时间
			var txt_startData = FormUtil.getHidField({
					fieldLabel : '加班起始时间',
					name : 'startData'
				});
				
			var txt_nehours = FormUtil.getReadTxtField({
				    fieldLabel: '剩余调休时长(小时)',
				    name: 'nehours',
				    "width": 125,
				    "allowBlank": false
				});

			var txt_cuhours = FormUtil.getTxtField({
				    fieldLabel: '本次调休时长(小时)',
				    name: 'cuhours',
				    "width": 125,
				    "allowBlank": false
				});

			var txt_ovhours = FormUtil.getReadTxtField({
				    fieldLabel: '原加班时长(小时)',
				    name: 'ovhours',
				    "width": 125,
				    "allowBlank": false
				});
			
			var int_otype = FormUtil.getRadioGroup({
				    fieldLabel: '销假方式',
				    name: 'otype',
				    "width": 400,
				    "allowBlank": false,
				    items : [{
							"boxLabel" : "所有时长全部销假",
							"name" : "otype",
							"inputValue" : "1",
							listeners:{
								"check":function(){
									if(this.checked){
										txt_cuhours.setValue(txt_nehours.getValue());
										txt_cuhours.disable();
									}
								}
							}
				  		  },{
					  		 "boxLabel" : "部分时长全部销假",
							"name" : "otype",
							"inputValue" : "2",
							checked:true,
							listeners:{
								"check":function(){
									if(this.checked){
										txt_cuhours.enable();
									}
								}
							}
				  		  }]
				});

				var txt_id = FormUtil.getHidField({
					name : 'id'
					});
			var layout_fields = [{
			   	 	cmns: FormUtil.CMN_TWO,
			   	 	fields: [txt_overtime , txt_ovhours ,txt_startDate, txt_endDate]
				}, int_otype ,{
			   	 	cmns: FormUtil.CMN_TWO,
			   	 	fields: [txt_nehours, txt_cuhours]
				},txt_id,txt_overtimeId,txt_leaveId,txt_applyId,txt_overMan];
				
			var frm_cfg = {
			    title: '选择加班申请单',
			    labelWidth : 130 
				};
				
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
		    return appform_1;
			},
					/**
					 * 为表单元素赋值
					 */
					setFormValues : function(){
						
					},
					/**
					 * 上一条
					 */
					preData : function(){
						
					},
					/**
					 * 下一条
					 */
					nextData : function(){
						
					},
					/**
					 * 保存数据
					 */
					saveData : function(){
						var _self = this;
						var _appgrid = _self.parent.globalMgr.appgrid;
						var name_combox = this.appFrm.getValueByName("overtime");
						if(name_combox==''){
							Ext.Msg.alert("提示","请选择数据");
							return false;
						}
						//将借款单编号
						var post_json = name_combox.split("##");
						//加班申请单id
						var overtimeId = this.appFrm.getValueByName("overtimeId");
						//加班申请单编号
						var code = post_json[1];
						//加班起始时间
						var startDate = this.appFrm.getValueByName("startDate");
						//加班截止时间
						var endDate =this.appFrm.getValueByName("endDate");
						//原加班时长(小时)
						var ovhours = this.appFrm.getValueByName('ovhours');
						//本次调休时间
						var cuhours = this.appFrm.getValueByName('cuhours');
						//剩余调休时长(小时)
						var nehours = this.appFrm.getValueByName("nehours") - cuhours;
						//加班起始 ---加班截止时间
						var startData = startDate+"至"+endDate;
						//销假方式
						var otype = this.appFrm.getValueByName('otype');
						
						var dayoff={};
						var frm=this.appFrm;
						dayoff.overtimeId=overtimeId;
						dayoff.otype=otype;
						dayoff.ovhours=ovhours;
						dayoff.cuhours=cuhours;
						dayoff.nehours=nehours;
						var oEmp=this.overtimeEmp;
						oEmp.yohours=ovhours-nehours;
						oEmp.xstatus=(nehours>0)?2:3;
//						overtimeEmp.applyId=frm.getValueByName("applyId");
//						overtimeEmp.overMan=frm.getValueByName("overMan");
							_self.parentCfg._self.callback(dayoff,oEmp);
						/**
						 * 返回json对象
						 */
						var params = {overtimeId : overtimeId, code : code, 
						startDate : startDate , endDate : endDate ,ovhours : ovhours ,
						nehours : nehours ,cuhours :cuhours ,otype : otype ,
						startData : startData,xstatus:oEmp.xstatus,oeId:oEmp.id,yohours:oEmp.yohours};
						var data =[];
						var record;
						data.push(params);
						if(data.length>0){
							for(var i = 0; i<data.length;i++){
								record = new Ext.data.Record(data[i]);		
							}
						}
						/**
						 * 获取表格的数据，进行判断,如果名称相同，弹出提示框，否则进行添加
						 */
						var store = _appgrid.getStore(params);
						var num =record.get(code);
						var count = store.getCount();
						if(count==0) {
							store.insert(count,record);
						}else{
							var numIndex=store.findBy(function(rec){
								return rec.get('code')==code;
							});
							if(numIndex != -1){
								ExtUtil.info({msg:"当前已有数据"});
							}else {
								store.insert(count,record);		
							}	
						}
//						this.refresh(params);
						this.appWin.hide();
					},
					/**
					 *  重置数据 
					 */
					resetData : function(){
					}
			}})