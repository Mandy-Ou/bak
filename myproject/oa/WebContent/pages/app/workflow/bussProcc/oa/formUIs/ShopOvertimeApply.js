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
		createButton : null,
		createAttachMentFs : null,
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
		 * 创建主面板
		 */
		craeteMainPnl : function() {
			this.appForm = this.createFrmPnl();
			var appPanel = new Ext.Panel({autoHeight: true});
			appPanel.add(this.appForm);
			this.appForm.add(this.createAttachMentFs(this));
			var buttonPanel = this.createButton();
			this.appForm.add(buttonPanel);
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
						fieldLabel : 'procId',
						name : 'procId',
						value : '-1'
			});
			var txt_code = FormUtil.getReadTxtField({
						fieldLabel : '申请单编号',
						name : 'code',
						width : wd,
						"allowBlank" : false,
						"maxLength" : "20"
					});
			_this.globalMgr.txt_code = txt_code;
			var txt_overMan = FormUtil.getReadTxtField({
						fieldLabel : '申请人',
						name : 'overMan',
						"allowBlank" : false
					});
			_this.globalMgr.txt_overMan = txt_overMan;
			var txt_deptId = FormUtil.getReadTxtField({
						fieldLabel : '部门',
						name : 'deptId',
						"allowBlank" : false
					});
			_this.globalMgr.txt_deptId = txt_deptId;
			var bdat_appDate = FormUtil.getDateField({
						fieldLabel : '申请日期',
						name : 'appDate',
						"allowBlank" : false
					});
					
			var txt_overType = FormUtil.getTxtField({
						fieldLabel : '加班地点',
						name : 'overType',
						"allowBlank" : false
					});
					
			var txa_remark = FormUtil.getTAreaField({
						fieldLabel : '备注',
						name : 'remark',
						"width" : 860
					});
			//添加借款单的按钮
			var txt_buttonList = [{
						token : Btn_Cfgs.INSERT_BTN_TXT,// "添加"
						text : Btn_Cfgs.INSERT_BTN_TXT,
						iconCls : 'page_add',
						tooltip : Btn_Cfgs.INSERT_TIP_BTN_TXT,
						key : Btn_Cfgs.INSERT_FASTKEY,
						handler : function() {
							/**
							 * 不使用第一种，行号不会产生变化
							 */
//							loanManGrid.addRecord({overMan : '请选择',overtimeType : '常规' , startDate : new Date() , endDate : new Date() ,ohours :0 ,reason : '说明' });
							loanManGrid.getStore().add(new Ext.data.Record({}));
//							loanManGrid.addRecord({});
							}
					},{
						token : Btn_Cfgs.DELETE_BTN_TXT,// "删除"
						text : Btn_Cfgs.DELETE_BTN_TXT,
						iconCls : 'page_delete',
						tooltip : Btn_Cfgs.DELETE_TIP_BTN_TXT,
						key : Btn_Cfgs.DELETE_FASTKEY,
						handler : function() {
//							var id =this.globalMgr._appgrid.get
//							if (!id)
//							return;
							alert(loanManGrid.getSelId());
						}
					}]

			
			function gd_callback(grid,value,selRows){
//					alert(loanManGrid.getStore().getCount());
//					alert(selRows[0].get('name'));				
					var store = loanManGrid.getStore();
					var record = store.getAt(_this.globalMgr.addRow)//获取当前行
					var fieldid = value.split("##")[0];//获取id
					var fieldvalue = value.split("##")[1];//获取名称
					var fieldname = record.set(_this.globalMgr.addField,fieldvalue);//对当前行的某个字段进行赋值
					var fieldnameid = record.set("id",fieldid);//对当前行的某个字段进行赋值
			};
			
			var structure = [{header: '员工列表',name: 'name',width:200}];
			var txa_name =new Ext.ux.grid.AppComBoxGrid({
						gridWidth :200,
						fieldLabel : '加班人',
						name : 'overMan',
						structure:structure,
					    dispCmn:'name',
					    isAll:true,
					    url : './hrEmployee_list.action',
					    needPage : false,
					    isLoad:true,
						allowBlank : false,
						callback : gd_callback
					});		
			//对加班类型数据的key一个隐藏
			var txt_overtimeId = FormUtil.getHidField({
					id : 'overtimeId',
					name : 'overtimeId'
			});
			//对加班类型数据的value一个隐藏
			_this.globalMgr.overtimeId = txt_overtimeId;
			var txt_overtimeName = FormUtil.getHidField({
					id : 'overtimeName',
					name : 'overtimeName'
			});
			var txt_overtimeType = FormUtil.getLCboField({
						fieldLabel : '加班类型',
						id : 'overtimeType',
						name:'overtimeType',
						data:[["0","常规"],["1","节假日加班"]],
						allowBlank : false,
						width:100,
						listeners:{
               			 'select':function(combo, record){
               			 	Ext.getCmp('overtimeId').text = combo.getValue();//获取的key
            				Ext.getCmp('overtimeName').text = combo.getRawValue();//获取的value
               			 	}
               			 }
					});
						
			var txt_startDate = FormUtil.getDateFormatField({
						fieldLabel : '加班起始日期',
						id :  'startDate',
						format : 'Y-m-d H:i:s',
						name:'startDate',
						isdeaulft:true /*,//默认值为今天,
						//页面显示出来的格式不对的时候可以使用
						renderer:Ext.util.Format.dateRenderer('y-m-d H:i:s')*/
					});
			var txt_endDate = FormUtil.getDateFormatField({
						fieldLabel : '截止时间',
						id : 'endDate',
						name : 'endDate',
						format : 'Y-m-d H:i:s',
						isdeaulft:true ,
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
									ExtUtil.info({msg:"结束日期不能小于开始日期"});
									return false;
									}	
								}
								var store = loanManGrid.getStore();
								var record = store.getAt(_this.globalMgr.addRow)//获取当前行
								var hours = record.set("ohours",txt_ohours.getValue());
							}}
					});
			var txt_ohours = FormUtil.getIntegerField({
						fieldLabel : '小时',
						name : 'ohours',
						value : "0"
					});
			var txt_reason = FormUtil.getTAreaField({
						fieldLabel : '原因说明',
						name : 'reason' 						
					});
			//表格中的header，标题
			var loanManInfo = [{
						id : 'id',
						name : 'id',
						hidden : true
					},{
						header : '加班人',//overMan
						name : 'overMan',
						width : 100
					}, {
						header : '加班类型',//overtimeType
						name : 'overtimeType',
						width : 150
					},{
						header : '加班起始时间',
						id : 'startDate',
						name : 'startDate',
						format : 'Y-m-d H:i:s',
						//页面的显示格式
						renderer:Ext.util.Format.dateRenderer('y-m-d H:i:s'),
//						type:'date',
//          			dateFormat: 'Y-m-d\'T\'HH:mm:ss',
						width : 125
					},{
						header : '加班截止时间',
						id : 'endDate',
						name : 'endDate',
						renderer:Ext.util.Format.dateRenderer('y-m-d H:i:s'),
						format : 'Y-m-d H:i:s',
						width : 125
					},{
						header : '加班时长(小时)',
						id : 'ohours',
						name : 'ohours',
						width : 100
					},{
						header : '原因说明',
						name : 'reason',
						width : 300
					},{
						id : 'overtimeId',
						name : 'overtimeId',
						hidden : true
					},{
						id : 'overtimeName',
						name : 'overtimeName',
						hidden : true
					}]
			//添加人员的表格
			var loanManGrid = new Ext.ux.grid.MyEditGrid({
					id : 'addgrid',
//					url  : './hrEmployee_list.action',
					editEles : {1 : txa_name , 2 :txt_overtimeType ,3 : txt_startDate ,4 : txt_endDate , 6: txt_reason },
					tbar : txt_buttonList,
					structure : loanManInfo,
					height : 180,
					isLoad : true,
					needPage : false,
					listeners :{
						'beforeedit':function(e){
							/**
							 * 获取点击的当前行索引
							 */
							var row = e.row;
							/**
							 * 获取要赋值的列名
							 */
							var field = e.field;
							/*alert(row);
							alert(field);*/
							_this.globalMgr.addRow = row ;
							_this.globalMgr.addField = field;							
						}
					} 
				});
				loanManGrid.on('validateedit', function(e) {
       				 if(e.field =='overtimeType'){
           		 		e.value = Ext.getCmp('overtimeName').text;
           		 		var store = loanManGrid.getStore();
						var record = store.getAt(_this.globalMgr.addRow)//获取当前行
           		 		record.set("overtimeId",Ext.getCmp('overtimeId').text);
           		 		 }
					});	
			this.globalMgr._appgrid=loanManGrid;
			
			//门店加班单的添加人员的fieldset
			var loanManfieldset =new Ext.form.FieldSet({
					collapsible :true ,
					collapsed :true,
					title : '加班员工添加列表',
					width :1000,
					items : [loanManGrid]
			})
			_this.globalMgr.loanManGrid = loanManGrid;
			var layout_fields = [{                                                                   
							cmns : FormUtil.CMN_THREE,                                                     
							fields : [txt_code,  txt_overMan, txt_deptId ,bdat_appDate , txt_overType ,txt_procId]           
						},loanManfieldset,txa_remark];                                                      	
			
			var filset = FormUtil.createLayoutFieldSet({
						title : "还款审批单"
					}, layout_fields);
					
			var frm_cfg = {
						autoHeight: true,
						url : './oaOvertimeApply_save.action',
						labelWidth : 115,
						items : [new Ext.Container({
							html : '<div style = "height : 40px;"><div style = "left : 650px;color : blue ; position : fixed ;font-size : 30px;z-index : 99">还款单</div></div>'
						}), filset]
					};
					
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			var applyForm = FormUtil.createFrm(frm_cfg);
			this.appForm = applyForm;
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
			var _this = this ;
			var button_save = new Ext.Button({
						text : '保存',
						width : 100 ,
						handler :function(){
							EventManager.frm_save(_this.appForm,{beforeMake : function(beforeData){
							//获取表格中的数据，进行处理
							var store = _this.globalMgr.loanManGrid.getStore();
							var count = store.getCount();
							var datas = [];
							if(count > 0){
							for(var i=0; i<count; i++){
									var record = store.getAt(i);
									var id =  record.get('id')//员工id
									var overMan = record.get('overMan');//员工名称
									var startDate = record.get('startDate').format('Y-m-d H:i:s');//加班起始时间
									var endDate = record.get('endDate').format('Y-m-d H:i:s');//加班截止时间
									var overtimeType = record.get('overtimeType');//加班类型
									var ohours = record.get('ohours');//加班时间
									var reason = record.get('reason');//原因说明
									var overtimeId = record.get('overtimeId');
									var params = {id : id,overMan :overMan,startDate : startDate,
												endDate : endDate,overtimeType : overtimeType,ohours : ohours,
									 			reason :reason,overtimeId : overtimeId};
									datas.push(params);
									}
								beforeData.store = Ext.encode(datas);
								}
							}
						},{sfn:function(jsonData){
								_this.refresh(jsonData);
						},ffn:function(jsonData){}});
						}
					})
			var button_reset = new Ext.Button({
						text : '重置',
						width : 100 ,
						handler : function(){
								EventManager.frm_reset(_self.appForm,[_self.globalMgr.txt_code,_self.globalMgr.txt_deptId,_self.globalMgr.txt_overMan]);
						}
					})
			var buttons = [button_save, button_reset];
			var btnPanel = new Ext.Panel({
						buttonAlign : 'center',
						buttons : buttons
					});
			return btnPanel;
		},

		globalMgr : {
				appForm : null ,
				_appgrid : null ,	
				txt_code : null , 
				txt_overMan : null ,
				txt_deptId : null ,
				addRow : null,
				addField : null,
				loanManGrid : null,
				overtimeId : null
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