/**
 * 员工信息编辑
 * zsl
 * @author smartplatform_auto
 * @date 2013-11-24 11:09:54
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		appCmpts:{},
		tabPanel:null,
		creForm:null,
		creForm2:null,
		creForm3:null,
		panel1:null,
		panel2:null,
	    panel3:null,
	    panel4:null,
	    panel5:null,
		panel6:null,
		panel7:null,
		wageForm1:null,
		wageGrid1:null,
		orderAppgrid:null,
		form2:null,
		grid2:null,
		awardsForm:null,
		deductGrid:null,
		workForm:null,
		cabinetGrid:null,
		cabForm:null,
		empTbar:null,
		relationGrid:null,
		relationForm:null,
		relationTbar:null,
		attachmentTbar:null,
		deductTbar:null,
		cabTbar:null,
		attachmentGrid:null,
		attachmentForm:null,
		saveFrm:null,
		tabPanel:null,
		empId:null,
		wagePlanId:null,
		wagePlanIdComBox : Ext.id(null,"wagePlanIdComBox"),
		url:null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			/**
			 * 员工信息表单
			 */
			this.creForm2=this.getSecurityForm();
			this.creForm3=this.getPostForm();			
			this.creForm=this.createForm();
			/**
			 * 工资方案面板
			 */
			this.panel1=this.getPanel1();
			/**
			 * 学习经历面板
			 */
			this.panel2=this.getPanel2();
			/**
			 * 奖罚记录面板
			 */
			this.panel3=this.getPanel3();
			/**
			 * 工作经历面板
			 */
			this.panel4=this.getPanel4();
			/**
			 * 家庭成员面板
			 */
			this.panel5=this.getPanel5();
			/**
			 * 社会关系面板
			 */
			this.panel6=this.getPanel6();
			/**
			 * 相关附件面板
			 */
			this.panel7=this.getPanel7();
			var _this=this;
			this.appFrm =this.creForm;
			this.appWin = new Ext.ux.window.AbsEditWindow({id:'empEditWin',width:760,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh,eventMgr:{saveData:this.saveData},
			initComponent : function(){
			 	Ext.ux.window.AbsEditWindow.superclass.initComponent.call(this);
			 	this.apptbar = this.createBbar();
			 	this.add(this.apptbar);
			 	_this.addComponents();
			}
			});
			_this.tabPanel=_this.getTabPanel();
			this.appWin.add(_this.tabPanel);
			this.appWin.on('afterrender',function(){
				_this.wagePlanId=_this.creForm.getValueByName("wagePlanId");
			})
			
		},
		addComponents:function(){
		},
		/**
		 * 显示弹出窗口
		 * @param _parentCfg 弹出之前传入的参数
		 */
		show : function(_parentCfg){
			if(_parentCfg) this.setParentCfg(_parentCfg);
			if(!this.appWin){
				this.createAppWindow();
			}else{
				this.tabPanel.setActiveTab(0);
			}
			this.appWin.optionType = this.optionType;
			this.appWin.show();
		},
		/**
		 * 创建tab面板
		 */
		 getTabPanel:function(){
		 	var _this=this;
		 	var p=new Ext.Panel({
		 		title:'员工基本信息',
		 		items:[_this.creForm]
	 		});
			var tabs = new Ext.TabPanel({
			    activeTab: 0,
			    height:555,
			    items:[p,_this.panel1,_this.panel2,
			    _this.panel3,_this.panel4,_this.panel5,
			    _this.panel6,_this.panel7],
			    listeners:{
			    	"tabchange":function(){
			    		
//		    		 if(_this.saveFrm){
//			    		var isM = false;  
//    					var windowForm = _this.saveFrm;
//			    		var fn = function(c) {  
//					    if(windowForm.isField(c)) {  
//					        if (c.isModify == true) {  
//					           isM = true;  
//					        }  
//					    }  
//					    }  
//					    this.items.each(fn, this); 
//					    alert(isM);
//			    	}
			    		
//			    		var dirty=false;
//			    		 if(_this.saveFrm) {
//			    		 	var from=_this.saveFrm.getForm();
//						 	from.items.each(function(item){
//						  		if(!dirty)dirty = item.isDirty();
//						  	});
//						  	if(dirty)alert(true);
//						 }
			    		var title=this.activeTab.title;
						if(title=="员工基本信息"){
							_this.saveFrm=_this.creForm;
						}else if(title=="工资方案"){
//							_this.wageForm1.reset();
							_this.saveFrm=_this.wageForm1;
							_this.parent=_this.wageGrid1;
							_this.url="./hrWagePlan";
								EventManager.get("./hrEmpPlanItem_list.action",
								{params:{inempId:_this.empId},
								sfn : function(jsonData){
									_this.wageGrid1.loadJsonDatas(jsonData,false);
							}});	
						}else if(title=="学习经历"){
							_this.form2.reset();
							_this.saveFrm=_this.form2;
							_this.parent=_this.orderAppgrid;
							_this.url="./hrStudy";
							_this.saveFrm.setDisabled(true);
						}else if(title=="奖罚记录"){
							_this.awardsForm.reset();
							_this.saveFrm=_this.awardsForm;
							_this.parent=_this.grid2;
							_this.url="./hrAwards";
							_this.saveFrm.setDisabled(true);
						}else if(title=="工作经历"){
							_this.workForm.reset();
							_this.saveFrm=_this.workForm;
							_this.parent=_this.deductGrid;
							_this.url="./hrWorkexp";
							_this.saveFrm.setDisabled(true);
						}else if(title=="家庭成员"){
							_this.cabForm.reset();
							_this.saveFrm=_this.cabForm;
							_this.parent=_this.cabinetGrid;
							_this.url="./hrFamily";
							_this.saveFrm.setDisabled(true);
						}else if(title=="社会关系"){
							_this.relationForm.reset();
							_this.saveFrm=_this.relationForm;
							_this.parent=_this.relationGrid;
							_this.url="./hrRelation";
							_this.saveFrm.setDisabled(true);
						}else if(title=="相关附件"){
							_this.attachmentForm.reset("formType");
							_this.saveFrm=_this.attachmentForm;
							_this.parent=_this.attachmentGrid;
							_this.url="./sysAttachment";
							_this.saveFrm.setDisabled(true);
						}
				if(title!="工资方案")
					_this.parent.reload({inempId:_this.empId,formId:_this.empId,formType:ATTACHMENT_FORMTYPE.FType_7});
				else
					_this.getFrmContent({key:"添加"});
						    	}
						    }
				});
				return tabs;
		 },
		/**
		 * 最高月订票次数编辑表单
		 */
		getForm2:function(){
			var hid_id=FormUtil.getHidField({
		 		fieldLabel:"ID",
		 		name:"id"
		 	});
		 	
			var txt_school = FormUtil.getTxtField({
			    fieldLabel: '学校',
			    name: 'school',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "150"
			});
			
			var txt_special = FormUtil.getTxtField({
			    fieldLabel: '专业',
			    name: 'special',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "150"
			});
			
			var bdat_startdate = FormUtil.getDateField({
			    fieldLabel: '开始日期',
			    name: 'startdate',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txt_certnum = FormUtil.getTxtField({
			    fieldLabel: '毕业证号码',
			    name: 'certnum',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "100"
			});
			
			var bdat_enddate = FormUtil.getDateField({
			    fieldLabel: '截止日期',
			    name: 'enddate',
			    "width": 125,
			    "allowBlank": false
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 620,
			    "maxLength": 50
			});
			
			var hid_empId=FormUtil.getHidField({
		 		fieldLavel:"员工ID",
		 		name:"inempId"
		 	});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_school, txt_special]
			},
			{
			    cmns: FormUtil.CMN_THREE,
			    fields: [bdat_startdate, txt_certnum, bdat_enddate]
			},
			txa_remark,hid_id,hid_empId];
			var frm_cfg = {
			    title: '学习经历信息编辑',
			    url: './hrStudy_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * 学习经历表格
		 */
		 getOrderAppgrid:function(){
		 	var structure_1 = [{
			    header: '学校',
			    name: 'school'
			},
			{
			    header: '专业',
			    name: 'special'
			},
			{
			    header: '毕业证书号码',
			    name: 'certnum'
			},
			{
			    header: '开始日期',
			    name: 'startdate'
			},
			{
			    header: '截止日期',
			    name: 'enddate'
			},
			{
			    header: '备注',
			    name: 'remark'
			},{
			    header: 'ID',
			    hidden:true,
			    name: 'id'
			}];
			var _this=this;
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    title: '学习经历记录',
			    tbar:_this.getCountTbar(),
			    height:343,
			    structure: structure_1,
			    url: './hrStudy_list.action',
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
		 },
		 /**
		  *工资方案面板
		  */
		 getPanel1:function(){
		 	var _this=this;
		 	_this.wageForm1=_this.getWageForm1();
		 	_this.wageGrid1=_this.getWageGrid1();
			var tb2 = new Ext.Panel({
		 		title:"工资方案"
			});
			tb2.add(_this.wageForm1);
			tb2.add(_this.wageGrid1);
			return tb2;
		 },
		 /**
		  * 工资方案form表单
		  */
		 getWageForm1:function(){
		 	var _this=this;
		 	var hid_id=FormUtil.getHidField({
		 		fieldLabel:"ID",
		 		name:"inempId"
		 	});
		 	var txt_nameid = FormUtil.getMyComboxTree({
			    fieldLabel: '工资方案',
			    id : _this.wagePlanIdComBox,
			    name: 'wagePlanId',
			    url:'./sysTree_wageList.action',
				isAll:true,
				width:150,
				height:350
			});
			txt_nameid.menu.on(
					"hide",function(){
						var id=txt_nameid.tree.getSelId();
						if(id.indexOf("W")!=-1){
							id=id.substring(1);
						}
						_this.wagePlanId=txt_nameid.tree.getSelText();
						_this.wageGrid1.reload({wageId:id});
					}
			)
				var layout_fields = [txt_nameid,hid_id];
			var frm_cfg = {
			    url: './hrEmployee_update.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		 },
		 /**
		  * 工资方案可编辑grid
		  */
		 getWageGrid1:function(){
		 	var structure_1 = [{
				header: '项目名称',
				name: 'name'
			},
			{
				header: '项目方向',
				renderer:function(val){
					switch(val){
						case '1':
							return "加项";
							break;
						case '2':
							return  "减项";
							break;
					}
				},
			    name: 'direction'
			},
			{
			    header: '基本金额',
			    name: 'bamount'
			},
//			{
//			    header: '实际金额',
//			    name: 'actualMoney'
//			},
			{
			    header: '备注',
			    name: 'remark'
			},{
				header: 'ID',
				hidden:true,
				name: 'id'
			},{
				header: '项目名称ID',
				hidden:true,
				name: 'itemId'
			},{
				header:"员工方案ID",
				hidden:true,
				name:"empItemId"
			}];
			var txt_bamount=FormUtil.getIntegerField({
				fieldLabel:"基本金额",
				name:"bamount",
				width:125
			});
			
			var txt_remark=FormUtil.getTAreaField({
				fieldLabel:"备注",
				width:300,
				name:"remark",
				maxLength:200
			});
			var appgrid_1 = new Ext.ux.grid.MyEditGrid({
			    title: '工资项配置列表',
			    structure: structure_1,
			    url: './hrPlanItem_list.action',
			    height:485,
			    editEles:{2:txt_bamount,3:txt_remark},
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
		 },
		 /**
		  * 学习经历面板
		  */
		  getPanel2:function(){
		 	var _this=this;
		 	_this.orderAppgrid=_this.getOrderAppgrid();
		 	_this.form2=_this.getForm2();
			var tb = new Ext.Panel({
		 		title:"学习经历"
			});
			tb.add(_this.orderAppgrid);
			tb.add(_this.form2);
			return tb;
		 },
		 /**
		  * 奖罚记录面板 
		  */
		  getPanel3:function(){
		 	var _this=this;
		 	_this.grid2=_this.getGrid2();
		 	_this.awardsForm=_this.getAwardsForm();
			var tb = new Ext.Panel({
		 		title:"奖罚记录"
			});
			tb.add(_this.grid2);
			tb.add(_this.awardsForm);
			return tb;
		 }, 
		 /**
		  * 奖罚记录表单
		  */
		 getAwardsForm:function(){
		 	var hid_id=FormUtil.getHidField({
		 		fieldLabel:"ID",
		 		name:"id"
		 	});
		 	
		 	var hid_empId=FormUtil.getHidField({
		 		fieldLavel:"员工ID",
		 		name:"inempId"
		 	});
		 	
		 	var bdat_adate = FormUtil.getDateField({
			    fieldLabel: '奖惩时间',
			    name: 'adate',
			    "width": 130,
			    "allowBlank": false
			});
			
			var txt_org = FormUtil.getTxtField({
			    fieldLabel: '单位',
			    name: 'org',
			    "width": 225,
			    "allowBlank": false,
			    "maxLength": "60"
			});
			
			var txa_content = FormUtil.getTAreaField({
			    fieldLabel: '内容',
			    name: 'content',
			    "width": 600,
			    "allowBlank": false,
			    "maxLength": "255"
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": "600"
			});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_TWO,
			    fields: [bdat_adate, txt_org]
			},
			txa_content, txa_remark,hid_id,hid_empId];
			var frm_cfg = {
			    title: '奖罚记录信息编辑',
			    trackResetOnLoad:true,
			    url: './hrAwards_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		 },
		 /**
		  * 工作经历面板
		  */
		  getPanel4:function(){
		 	var _this=this;
		 	_this.deductGrid=_this.getDeductGrid();
		 	_this.workForm=_this.getWorkForm();
			var tb = new Ext.Panel({
		 		title:"工作经历"
			});
			tb.add(_this.deductGrid);
			tb.add(_this.workForm);
			return tb;
		 },
		  /**
		  * 家庭成员面板
		  */
		  getPanel5:function(){
		 	var _this=this;
		 	_this.cabinetGrid=_this.getCabinetGrid();
		 	_this.cabForm=_this.getCabForm();
			var tb = new Ext.Panel({
		 		title:"家庭成员"
			});
			tb.add(_this.cabinetGrid);
			tb.add(_this.cabForm);
			return tb;
		 },
		 /**
		  * 学习经历菜单栏
		  */
		getCountTbar:function(){
			var self = this;
			var toolBar = null;
			var barItems = [{type:"sp"},{
				token : "添加",
				text : Btn_Cfgs.INSERT_BTN_TXT,
				iconCls:'page_add',
				tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
				handler : function(){
					self.openWin({key:this.token,self:self,me:this});
				}
			},{
				token : "编辑",
				text : Btn_Cfgs.MODIFY_BTN_TXT,
				iconCls:'page_edit',
				tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
				handler : function(){
					self.openWin({key:this.token,self:self,me:this});
				}
			},{
				token : "删除",
				text : Btn_Cfgs.DELETE_BTN_TXT,
				iconCls:'page_delete',
				tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
				handler : function(){
					EventManager.deleteData('./hrStudy_delete.action',{type:'grid',cmpt:self.parent,optionType:OPTION_TYPE.DEL,self:self});
				}
			}];
			toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
			toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
			return toolBar;
	
		  },
	/**
	 * 奖罚记录表格
	 */
		getGrid2:function(){
			var _this=this;
			_this.empTbar=_this.getEmpTbar();
			var structure_1 = [{
			    header: '奖惩时间',
			    name: 'adate'
			},
			{
			    header: '单位',
			    name: 'org'
			},
			{
			    header: '内容',
			    name: 'content'
			},
			{
			    header: '备注',
			    name: 'remark'
			},{
			    header: 'ID',
			    hidden:true,
			    name: 'id'
			}];
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    structure: structure_1,
		    url: './hrAwards_list.action',
		    height:275,
		    tbar:_this.empTbar,
		    needPage: false,
		    isLoad: false,
		    keyField: 'id'
		});
			return appgrid_1;	
		},
		/**
		 * 奖罚记录菜单栏
		 */
		getEmpTbar:function(){
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./hrAwards_delete.action',{type:'grid',cmpt:self.parent,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
		  },
		   /**
		  * 社会关系面板
		  */
		  getPanel6:function(){
		 	var _this=this;
		 	_this.relationGrid=_this.getRelationGrid();
		 	_this.relationForm=_this.getRelationForm();
			var tb = new Ext.Panel({
		 		title:"社会关系"
			});
			tb.add(_this.relationGrid);
			tb.add(_this.relationForm);
			return tb;
		 },
		 /**
		  * 社会关系表格
		  */
		 getRelationGrid:function(){
		 	var structure_1 = [
	 		{
			    header: '姓名',
			    name: 'name'
			},
			{
			    header: '关系',
			    name: 'relation'
			},
			{
			    header: '年龄',
			    name: 'age'
			},
			{
			    header: '工作单位',
			    name: 'company'
			},
			{
			    header: '职务',
			    name: 'job'
			},
			{
			    header: '联系电话',
			    name: 'contacttell'
			},
			{
			    header: '关系描述',
			    name: 'indesc'
			},
			{
			    header: '备注',
			    name: 'remark'
			},{
			    header: 'ID',
			    hidden:true,
			    name: 'id'
			}];
			var _this=this;
			_this.relationTbar=_this.getRelationTbar();
			var appgrid_1 = new Ext.ux.grid.AppGrid({
					    structure: structure_1,
					    height:290,
					    url: './hrRelation_list.action',
					    tbar:_this.relationTbar,
					    needPage: false,
					    isLoad: false,
					    keyField: 'id'
					});
				return appgrid_1;
					
		 },
		 /**
		  * 社会关系菜单栏
		  */
		 getRelationTbar:function(){
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./hrRelation_delete.action',{type:'grid',cmpt:self.parent,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
		 },
		 /**
		  * 社会关系表单
		  */
		 getRelationForm:function(){
		 	var hid_id=FormUtil.getHidField({
		 		fieldLabel:"ID",
		 		name:"id"
		 	});
		 	
		 	var txt_relation = FormUtil.getTxtField({
			    fieldLabel: '关系',
			    name: 'relation',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "30"
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '姓名',
			    name: 'name',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "30"
			});
			
			var int_age = FormUtil.getIntegerField({
			    fieldLabel: '年龄',
			    name: 'age',
			    "width": 125,
			    "maxLength": 10
			});
			
			var txt_contacttell = FormUtil.getTxtField({
			    fieldLabel: '联系电话',
			    name: 'contacttell',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_job = FormUtil.getTxtField({
			    fieldLabel: '职务',
			    name: 'job',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_company = FormUtil.getTxtField({
			    fieldLabel: '工作单位',
			    name: 'company',
			    "width": 620,
			    "maxLength": "150"
			});
			
			var txt_desc = FormUtil.getTxtField({
			    fieldLabel: '关系描述',
			    name: 'indesc',
			    "width": 620,
			    "maxLength": "150"
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 620,
			    "maxLength": 50
			});
			var hid_empId=FormUtil.getHidField({
		 		fieldLavel:"员工ID",
		 		name:"inempId"
		 	});
		 	
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_relation, txt_name, int_age]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_contacttell, txt_job]
			},
			txt_company, txt_desc, txa_remark,hid_id,hid_empId];
			var frm_cfg = {
			    title: '社会关系信息编辑',
			    trackResetOnLoad:true,
			    url: './hrRelation_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		 },
		 /**
		  * 相关附件面板
		  */
		  getPanel7:function(){
		 	var _this=this;
		 	_this.attachmentGrid=_this.getAttachmentGrid();
		 	_this.attachmentForm=_this.getAttachmentForm();
			var tb = new Ext.Panel({
		 		title:"相关附件"
			});
			tb.add(_this.attachmentGrid);
			tb.add(_this.attachmentForm);
			return tb;
		 },
		  /**
		  * 相关附件表格
		  */
		 getAttachmentGrid:function(){
		 	
			var _this=this;
			var structure_1 = [{
			    header: '可用标识',
			    renderer : Render_dataSource.isenabledRender,
			    name: 'isenabled'
			},
			{
			    header: '文件名称',
			    name: 'fileName'
			},
			{
			    header: '文件大小',
			    name: 'fileSize'
			},{
			    header: '文路件径',
			    name: 'filePath'
			},
			{
			    header: '创建日期',
			    name: 'createTime'
			},
			{
			    header: '创建人',
			    name: 'userName'
			},
			{
			    header: '备注',
			    name: 'remark'
			},{
			    header: 'ID',
			    hidden:true,
			    name: 'id'
			}];
			_this.attachmentTbar=_this.getAttachmentTbar();
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    height:340,
			    url: './sysAttachment_list.action',
			    tbar:_this.attachmentTbar,
			    needPage: false,
			    params: {formId:_this.empId,formType:ATTACHMENT_FORMTYPE.FType_7},
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
		
		 },
		 /**
		  * 相关附件菜单栏
		  */
		 getAttachmentTbar:function(){
		 	
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		}/*,{
			token : "预览",
			text : Btn_Cfgs.PREVIEW_FILE_BTN_TXT,
			iconCls:Btn_Cfgs.PREVIEW_FILE_CLS,
			tooltip:Btn_Cfgs.PREVIEW_FILE_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,optionType:OPTION_TYPE.EDIT,self:self,me:this});
			}
		}*/,{
			token : "下载",
			text : Btn_Cfgs.DOWNLOAD_BTN_TXT,
			iconCls:Btn_Cfgs.DOWNLOAD_CLS,
			tooltip:Btn_Cfgs.DOWNLOAD_TIP_BTN_TXT,
			handler : function(){
				self.download({key:this.token,optionType:OPTION_TYPE.EDIT,self:self,me:this});
			}
		},
			{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./sysAttachment_delete.action',{type:'grid',cmpt:self.parent,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
		 },
		 /**
		  * 相关附件表单
		  */
		 getAttachmentForm:function(){
		 	var hid_id=FormUtil.getHidField({
		 		fieldLabel:"ID",
		 		name:"id"
		 	});
		 	
		 	var hid_sysId=FormUtil.getHidField({
		 		fieldLabel:"系统ID",
		 		name:"sysId"
		 	});
		 	
		 	var txt_formId = FormUtil.getHidField({
			    fieldLabel: '业务单ID',
			    name: 'formId',
			    "width": 125,
			    "maxLength": "50"
			});
			
			var txt_formType = FormUtil.getHidField({
			    fieldLabel: '业务类型',
			    name: 'formType',
			    "width": 125,
			    "maxLength": "50"
			});
			
			var txt_fileName = FormUtil.getTxtField({
			    fieldLabel: '附件名称',
			    name: 'fileName',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "255"
			});
//			
//			var bdat_createTime = FormUtil.getDateField({
//			    fieldLabel: '上传日期',
//			    name: 'createTime',
//			    "width": "125"
//			});
//			
//			var txt_creator = FormUtil.getTxtField({
//			    fieldLabel: '上传人',
//			    name: 'creator',
//			    "width": "125"
//			});
//			
//			var txt_filePath = FormUtil.getTxtField({
//			    fieldLabel: '文件路径',
//			    name: 'filePath',
//			    "width": 125,
//			    "allowBlank": false,
//			    "maxLength": "255"
//			});
			
			var txt_atype = FormUtil.getLCboField({
			    fieldLabel: '附件类型',
			    allowBlank: false,
			    name: 'atype',
			    data:[[0,"word附件"],[1,"excel 附件"],
			    [2,"图片附件"],[3,"ZIP文件"],[4,"XML文件"],[5,"其他"]],
			    "width": "125"
			});
			
			var txt_filePath = FormUtil.getMyUploadField({
				fieldLabel: '文件路径',
				name: 'filePath',
				width: 500,
				sigins:null,
			    allowBlank: false,
			    dir : 'emp_att_path',
			    allowFiles:"allowFiles_ext"
		    });

			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 620,
			    "maxLength": 50
			});
//			
//			var hid_empId=FormUtil.getHidField({
//		 		fieldLavel:"员工ID",
//		 		name:"inempId"
//		 	});
			var _this=this;
			var layout_fields = [ {
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_fileName,txt_atype]
			},
			txt_filePath,txa_remark,hid_id,txt_formId,txt_formType,hid_sysId];
			var frm_cfg = {
			    title: '附件信息编辑',
			    trackResetOnLoad:true,
			    url: './sysAttachment_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		 },
		 download:function(){
		 	var _this=this;
		 	ExtUtil.confirm({msg:Msg_SysTip.msg_downloadAttachmentData,fn:function(btn){
			if(btn != 'yes') return;
			var params = {id:_this.parent.getSelId()};
			EventManager.downLoad("./sysAttachment_download.action",params);
		}});
//		 	alert(_this.parent.getSelId());
//		 	EventManager.get(
//		 		"./sysAttachment_download.action",
//				{params:{id:_this.parent.getSelId()},
//				sfn : function(jsonData){
//					alert("dd");
//				},
//				ffn:function(){
//					alert("aa");	
//				}
//			});	
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
		 * 获取各种URL配置
		 * addUrlCfg : 新增 URL
		 * editUrlCfg : 修改URL
		 * preUrlCfg : 上一条URL
		 * preUrlCfg : 下一条URL
		 */
		getUrls : function(){
			var urls = {};
			var parent = exports.WinEdit.parent;
			var _this=exports.WinEdit;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(this.optionType == OPTION_TYPE.ADD){ //--> 新增
				/*--- 添加 URL --*/	
				cfg = {params:{},defaultVal:{}};
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './hrEmployee_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {params : {id:selId},sfn:function(json_Data){
					_this.empId=json_Data.id;
					_this.wagePlanId = json_Data.wagePlanId;
					var tImg=Ext.get("tImg");
					if(json_Data.photo){
						tImg.dom.setAttribute("src",json_Data.photo)	
					}else{
						tImg.dom.setAttribute("src","images/avatar/1125853.gif")	
					}
				}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './hrEmployee_get.action',cfg : cfg};
			}
			var id = _this.creForm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './hrEmployee_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './hrEmployee_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 为form表单设置值
		 * operating 添加或编辑
		 */
		getFrmContent : function(operating){
			var urls = {};
			var parent = exports.WinEdit.parent;
			var _this=exports.WinEdit;
			var cfg = null;
			// 1 : 新增 , 2:修改
			if(operating.key == "添加"){ //--> 新增
				/*--- 添加 URL --*/			 
				cfg = {params:{},defaultVal:{inempId:_this.empId,wagePlanId:_this.wagePlanId,
				formId:_this.empId,formType:ATTACHMENT_FORMTYPE.FType_7,sysId:-1}};
				urls = {url : _this.url+"_add.action",cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				cfg = {
					params : {id:selId},
					sfn : function(json_data){
						_this.saveFrm.setFieldValues(json_data);
					}
				};
				urls = {url : _this.url+'_get.action',cfg : cfg};
			}
			_this.saveFrm.setValues(urls.url,urls.cfg);
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			_this.parent.reload({inempId:_this.empId,formId:_this.empId});
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);	
		},
		/**
		 * 创建头像面板
		 */
		getTitlePanel:function(){
			var _this=this;
			var p=new Ext.Panel({
				text:"选择头像",
				width:100,
				height:100,
				html:'<div><img id="tImg" style="width:100px; height:100px" src="images/avatar/1125853.gif" /></div>'
			});
			p.on("afterrender",function(){
				var tImg=Ext.get("tImg");
				//面板渲染后，给图片绑定点击事件
				tImg.on("click",function(){
					_this.showUploadWin(tImg);
				});
			});
			return p;
		},
		//显示头像上传窗口
		showUploadWin:function(tImg){
			var _this = this;
			if(!this.uploadWin){
				this.uploadWin = new Ext.ux.window.MyUpWin({
				title:"上传图片",
				label:"请选择文件",
				width:450,
				dir:"emp_photo_img_path",
				allowFiles:"allowFiles_pic_ext",
				sfn:function(fileInfos){
					var filePath = null;
					if(fileInfos){
						var fileInfo = fileInfos[0];
						filePath = fileInfo.serverPath;
					}
					tImg.dom.setAttribute("src",filePath);
					_this.creForm.findFieldByName("photo").setValue(filePath);
				}});
			}
			this.uploadWin.show();
		},
		/**
		 * 员工基本资料form表单
		 */
		createForm : function(){
			var _this=this;
			var hid_id=FormUtil.getHidField({
				fieldLabel:"ID",
		 		name:"id"
			});
			
			var hid_wagePlanId=FormUtil.getHidField({
				fieldLabel:"工资方案ID",
		 		name:"wagePlanId"
			});
			
			var hid_photo=FormUtil.getHidField({
				fieldLabel:"员工照片",
		 		name:"photo"
			});
			
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '员工编号',
			    name: 'code',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "20"
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '姓名',
			    name: 'name',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "50"
			});
			
			var int_sex = FormUtil.getRadioGroup({
			    fieldLabel: '性别',
			    "allowBlank": false,
			    name: 'sex',
			    "width": 125,
			     "items": [{
			        "boxLabel": "未知",
			        "name": "sex",
			        "checked":"true",
			        "inputValue": "-1"
			    },{
			        "boxLabel": "男",
			        "name": "sex",
			        "inputValue": "1"
			    },{
			        "boxLabel": "女",
			        "name": "sex",
			        "inputValue": "2"
			    }],
			    "maxLength": 10
			});
			
			var txt_hometown = FormUtil.getRCboField({
			    fieldLabel: '籍贯',
			    name: 'hometown',
			    "width": 125,
			    register : REGISTER.GvlistDatas,
			    restypeId : '200002',
			    "maxLength": 10
			});
			
			var txt_phone = FormUtil.getTxtField({
			    fieldLabel: '手机号码',
			    name: 'phone',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_marital = FormUtil.getRCboField({
			    fieldLabel: '婚姻状况',
			    name: 'marital',
			    "width": 125,
			    register : REGISTER.GvlistDatas,
			    restypeId : '200003',
			    "maxLength": 10
			});
			
			var txt_idcard = FormUtil.getTxtField({
			    fieldLabel: '身份证号码',
			    name: 'idcard',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_idadd = FormUtil.getTxtField({
			    fieldLabel: '身份证地址',
			    name: 'idadd',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var bdat_birthDay = FormUtil.getDateField({
			    fieldLabel: '出生日期',
			    name: 'birthDay',
			    "width": 125
			});
			
			var txt_degree = FormUtil.getRCboField({
			    fieldLabel: '学历',
			    name: 'degree',
			  	 "width": 125,
			    register : REGISTER.GvlistDatas,
			    restypeId : '200001',
			    "maxLength": 10
			});
			
			var txt_bank = FormUtil.getTxtField({
			    fieldLabel: '开户银行',
			    name: 'bank',
			    "width": 125,
			    "maxLength": "100"
			});
			
			var txt_account = FormUtil.getTxtField({
			    fieldLabel: '银行卡号',
			    name: 'account',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_accountName = FormUtil.getTxtField({
			    fieldLabel: '帐户名',
			    name: 'accountName',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var int_tryDays = FormUtil.getIntegerField({
			    fieldLabel: '试用期(月)',
			    name: 'tryDays',
			    "width": 118,
			    unitText:"个月",
			    "maxLength": 10
			});
			
			var bdat_entryDate = FormUtil.getDateField({
			    fieldLabel: '入职日期',
			    name: 'entryDate',
			    "width": 125,
			    "allowBlank": false
			});
			
			var bdat_leaveDate = FormUtil.getDateField({
			    fieldLabel: '离职日期',
			    name: 'leaveDate',
			    "width": 125
			});
			
			var txt_econtactor = FormUtil.getTxtField({
			    fieldLabel: '紧急联系人',
			    name: 'econtactor',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_ctel = FormUtil.getTxtField({
			    fieldLabel: '联络电话',
			    name: 'ctel',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var int_status = FormUtil.getLCboField({
			    fieldLabel: '员工状态',
			    name: 'status',
			    "width": 125,
			    "data": [["1", "在职"], ["2", "离职"]],
			    "maxLength": 10
			});
			
			var rad_empType = FormUtil.getRadioGroup({
			    fieldLabel: '员工类型',
			    "allowBlank": false,
			    name: 'empType',
			    "width": 150,
			     "items": [{
			        "boxLabel": "正式员工",
			        "name": "empType",
			         "checked":"true",
			        "inputValue": "1"
			    },{
			        "boxLabel": "临促",
			        "name": "empType",
			        "inputValue": "2"
			    }],
			    "maxLength": 10
			});
			
			var dob_wageDay = FormUtil.getMoneyField({
			    fieldLabel: '日工资标准',
			    name: 'wageDay',
			    "width": 120,
			    unitText:"元",
			    "maxLength": 10
			});
			
//			var int_iscreate = FormUtil.getRadioGroup({
//			    fieldLabel: '创建登录帐号',
//			    name: 'iscreate',
//			    "width": 125,
//			    "items": [{
//			        "boxLabel": "是",
//			        "name": "iscreate",
//			         "checked":"true",
//			        "inputValue": "1"
//			    },{
//			        "boxLabel": "否",
//			        "name": "iscreate",
//			        "inputValue": "2"
//			    }],
//			    "maxLength": 10
//			});
			
			var txt_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 620,
			    height:37,
			    "maxLength": 200
			});
			
			var hid_empId=FormUtil.getHidField({
		 		fieldLavel:"员工ID",
		 		name:"inempId"
		 	});
		 	
			var _this=this;
			var layoutFormPanel = new Ext.Panel({items:[_this.getPostForm()]});
			var firstPanel = new Ext.Panel({layout:'form',items:[txt_code,  int_sex,  txt_phone,  bdat_birthDay]});
			var firstPanel2 = new Ext.Panel({layout:'form',items:[txt_name,  txt_hometown,  txt_marital, txt_degree]});
			var layoutCmnPanel = FormUtil.getLayoutPanel([.33,.45,.20],[firstPanel,firstPanel2,_this.getTitlePanel()]);
			var layout_fields = [layoutCmnPanel,{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_idcard,txt_idadd]
			},
			{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_bank, txt_account, txt_accountName, int_tryDays, bdat_entryDate, bdat_leaveDate, txt_econtactor, txt_ctel, int_status,rad_empType,dob_wageDay]
			},
			txt_remark,hid_id,hid_empId,hid_photo];
			var frm_cfg = {
			    title: '员工基本资料',
			    trackResetOnLoad:true,
			    url: './hrEmployee_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			appform_1.add(_this.creForm2);
			appform_1.add(_this.creForm3);
			return appform_1;
		},
		/**
		 * 社保信息form
		 */
		getSecurityForm:function(){
//			var rad_isBuySocial = FormUtil.getRadioGroup({
//			    fieldLabel: '是否购买社保',
//			    name: 'isBuySocial ',
//			    "width": 125,
//			    "maxLength": 50,
//			    "items": [{
//			        "boxLabel": "是",
//			         "checked":"true",
//			        "name": "isBuySocial ",
//			        "inputValue": "1"
//			    },
//			    {
//			        "boxLabel": "否",
//			        "name": "isBuySocial ",
//			        "inputValue": "2"
//			    }]
//			});
			
			var txt_socialNum = FormUtil.getTxtField({
			    fieldLabel: '社保号',
			    name: 'socialNum',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_socialOrg = FormUtil.getTxtField({
			    fieldLabel: '购买社保单位',
			    name: 'socialOrg',
			    "width": 125,
			    "maxLength": "100"
			});
//			
//			var txt_domicileType = FormUtil.getTxtField({
//			    fieldLabel: '户籍类型',
//			    name: 'domicileType',
//			    "width": "125"
//			});
//			
//			var txt_personalMoney = FormUtil.getTxtField({
//			    fieldLabel: '个人金额',
//			    name: 'personalMoney',
//			    "width": "125"
//			});
//			
//			var txt_unitMoney = FormUtil.getTxtField({
//			    fieldLabel: '单位金额',
//			    name: 'unitMoney',
//			    "width": "125"
//			});
			
			var bdat_startDate = FormUtil.getDateField({
			    fieldLabel: '合同开始日期',
			    name: 'startDate',
			    "width": 125
			});
			
			var bdat_endDate = FormUtil.getDateField({
			    fieldLabel: '合同结束日期',
			    name: 'endDate',
			    "width": 125
			});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [ txt_socialNum, txt_socialOrg, bdat_startDate, bdat_endDate]
			}];
			var layoutItems = FormUtil.getLayoutItems(layout_fields);
			var frm_cfg = {
				layout:'form',
			    title: '社保信息',
			    trackResetOnLoad:true,
			    items:[layoutItems]
			};
			var p=new Ext.Panel(frm_cfg)
			return p;
		},
		/**
		 * 职位信息表单
		 */
		getPostForm:function(){
			var _this=this;
			/*所属门店*/
			var barStore = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strStore = [
				{header: '店面名称', name: 'name',width:100},{header: '门店类型',name: 'stype',width:60,renderer: function(val) {
					return Render_dataSource.sexRender(val);
				}},{header: '店长', name: 'dzId',width:80}];
					
			var txt_store = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '所属门店',
			    name: 'storeId',
			    barItems : barStore,
			    structure:strStore,
			    dispCmn:'name',
			    isAll:true,
			    url : './oaStores_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			   
			/*所属部门*/
			var barIndept = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strIndept = [
				{header: '部门名称', name: 'name',width:100},{header: '部门类型',name: 'dtype',width:60,renderer: function(val) {
					return Render_dataSource.dep_dtypeRender(val);
				}},{header: '联系人', name: 'contactor',width:80}];
					
			var txt_Indept = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '<span style="color:red">*</span>所属部门',
			    name: 'indeptId',
			    barItems : barIndept,
			    structure:strIndept,
			    dispCmn:'name',
			    isAll:true,
			    url : './sysDepartment_list.action',
			    allowBlank: false,
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			/*职位*/
			var barPost = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strPost = [
				{header: '岗位名称', name: 'name',width:100},{header: '主要职责',
				name: 'mtask',width:60}];
			var txt_postId = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '<span style="color:red">*</span>职位',
			    name: 'postId',
			    barItems : barPost,
			    structure:strPost,
			    dispCmn:'name',
			    isAll:true,
			    url : './sysPost_list.action',
			    needPage : true,
			    isLoad : true,
			    allowBlank: false,
			    keyField : 'id',
			    width: 125
			});
			
			/*行政职务*/
			var barPosition = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strPosition = [
				{header: '名称', name: 'name',width:100},{header: '主要职责',
				name: 'mtask',width:60}];
			var txt_position = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '行政职务',
			    name: 'position',
			    barItems : barPost,
			    structure:strPost,
			    dispCmn:'name',
			    isAll:true,
			    url : './sysPost_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			var txt_divType = FormUtil.getRCboField({
				fieldLabel : '岗位分工',
				name:'divide',
			 	"width": 125,
			 	register : REGISTER.GvlistDatas,
			    restypeId : '200004',
			    "maxLength": 10
			});
			
			var txt_postType = FormUtil.getRCboField({
				fieldLabel : '岗位类型',
				name:'postType',
				 "width": 125,
				 register : REGISTER.GvlistDatas,
			    restypeId : '200005',
			    "maxLength": 10
			});
			
			var txt_useType = FormUtil.getRCboField({
			    fieldLabel: '用工类型',
			    name: 'useType',
			     "width": 125,
			      register : REGISTER.GvlistDatas,
			    restypeId : '200006',
			    "maxLength": 10
			});
			
			var rad_isCommission = FormUtil.getRadioGroup({
			    fieldLabel: '是否算提成',
			    name: 'isCommission',
			    "width": 125,
			    "maxLength": 50,
			    "items": [{
			        "boxLabel": "是",
			         "checked":"true",
			        "name": "isCommission",
			        "inputValue": "1"
			    },
			    {
			        "boxLabel": "否",
			        "name": "isCommission",
			        "inputValue": "2"
			    }]
			});
			
			/*工资方案*/
			var barDeduct = [{type:'label',text:'方案名称'},{type:'txt',name:'name'}];
			var strDeduct = [
				{header: '方案名称', name: 'name',width:60},{header: '编号',
				name: 'code',width:200}];
			var txt_deductPlan = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '工资方案',
			    name: 'wagePlanId',
			    barItems : barDeduct,
			    structure:strDeduct,
			    dispCmn:'name',
			    isAll:true,
			    url : './hrWagePlan_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			var txt_invoceNum = FormUtil.getTxtField({
			    fieldLabel: 'OA单号',
			    name: 'invoceNum',
			    "width": 125
			});
			
			var txt_ispart = FormUtil.getRadioGroup({
			    fieldLabel: '是否兼职导购',
			    name: 'ispart',
			     "items": [{
			        "boxLabel": "是",
			         "checked":"true",
			        "name": "ispart",
			        "inputValue": "1"
			    },
			    {
			        "boxLabel": "否",
			        "name": "ispart",
			        "inputValue": "2"
			    }],
			    "width": 125
			});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_store, txt_Indept, txt_postId,txt_divType,txt_position, txt_postType]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [ txt_useType, txt_deductPlan]
			},
			{
			    cmns: FormUtil.CMN_THREE,
			    fields: [rad_isCommission, txt_invoceNum, txt_ispart]
			}];
			var layoutItems = FormUtil.getLayoutItems(layout_fields);
			var frm_cfg = {
			     title: '职位信息',
			     trackResetOnLoad:true,
			     layout:'form',
			    items:[layoutItems]
			};
			var p=new Ext.Panel(frm_cfg)
			return p;
		},
			/**
			 * 工作经历表格
			 */
			getDeductGrid:function(){
				var _this=this;
				var structure_1 = [{
				    header: '起始日期',
				    name: 'startDate'
				},
				{
				    header: '截止日期',
				    name: 'endDate'
				},
				{
				    header: '工作单位',
				    name: 'workUnit'
				},
				{
				    header: '部门',
				    name: 'dept'
				},
				{
				    header: '职务',
				    name: 'job'
				},
				{
				    header: '薪金待遇',
				    name: 'salary'
				},
				{
				    header: '证明人及职位',
				    name: 'provetor'
				},
				{
				    header: '公司固定电话',
				    name: 'fixtell'
				},
				{
				    header: '离职原因',
				    name: 'leftreason'
				},
				{
				    header: '直接上司姓名',
				    name: 'supname'
				},
				{
				    header: '直接上司电话',
				    name: 'suptell'
				},
				{
				    header: '主要工作成就',
				    name: 'gain'
				},
				{
				    header: '备注',
				    name: 'remark'
				}];
				_this.deductTbar=_this.getDeductTbar();
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    tbar:_this.deductTbar,
			    url: './hrWorkexp_list.action',
			    height:240,
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
			},
		/**
		 * 工作经历菜单栏
		 */
		getDeductTbar:function(){
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:Btn_Cfgs.INSERT_CLS,
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		},{type:"sp"},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:Btn_Cfgs.MODIFY_CLS,
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		},{type:"sp"},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:Btn_Cfgs.DELETE_CLS,
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./hrStudy_delete.action',{type:'grid',cmpt:self.parent,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
		},
		/**
		 * 工作经历表单
		 */
		getWorkForm:function(){
			var hid_id=FormUtil.getHidField({
		 		fieldLabel:"ID",
		 		name:"id"
		 	});
		 	
			var bdat_startDate = FormUtil.getDateField({
			    fieldLabel: '起始日期',
			    name: 'startDate',
			    "width": 125
			});
			
			var bdat_endDate = FormUtil.getDateField({
			    fieldLabel: '截止日期',
			    name: 'endDate',
			    "width": 125
			});
			
			var txt_workUnit = FormUtil.getTxtField({
			    fieldLabel: '工作单位',
			    name: 'workUnit',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "60"
			});
			
			var txt_dept = FormUtil.getTxtField({
			    fieldLabel: '部门',
			    name: 'dept',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "60"
			});
			
			var txt_job = FormUtil.getTxtField({
			    fieldLabel: '职务',
			    name: 'job',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "60"
			});
			
			var dob_salary = FormUtil.getIntegerField({
			    fieldLabel: '薪金待遇',
			    name: 'salary',
			    "width": 125,
			    "decimalPrecision": "2"
			});
			
			var txt_provetor = FormUtil.getTxtField({
			    fieldLabel: '证明人及职位',
			    name: 'provetor',
			    "width": 125,
			    "maxLength": "150"
			});
			
			var txt_fixtell = FormUtil.getTxtField({
			    fieldLabel: '公司固定电话',
			    name: 'fixtell',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_Supname = FormUtil.getTxtField({
			    fieldLabel: '直接上司姓名',
			    name: 'supname',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_Suptell = FormUtil.getTxtField({
			    fieldLabel: '直接上司电话',
			    name: 'suptell',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_gain = FormUtil.getTxtField({
			    fieldLabel: '主要工作成就',
			    name: 'gain',
			    "width": 620,
			    "maxLength": "200"
			});
			
			var txt_leftreason = FormUtil.getTxtField({
			    fieldLabel: '离职原因',
			    name: 'leftreason',
			    "width": 620,
			    "maxLength": "200"
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 620,
			    "maxLength": 50
			});
			
			var hid_empId=FormUtil.getHidField({
		 		fieldLavel:"员工ID",
		 		name:"inempId"
		 	});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [bdat_startDate, bdat_endDate, txt_workUnit, txt_dept, txt_job, dob_salary]
			},
			{
			    cmns: FormUtil.CMN_TWO_LEFT,
			    fields: [txt_provetor, txt_fixtell, txt_Supname, txt_Suptell]
			},
			txt_gain, txt_leftreason, txa_remark,hid_id,hid_empId];
			var frm_cfg = {
			    title: '工作经历信息编辑',
			    trackResetOnLoad:true,
			    url: './hrWorkexp_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 *全局变量
		 */
		globalMgr : {
		/**
		 * 当前激活的按钮文本	
		 * @type 
		 */
		noid : null,
		activeKey : null,
		winEdit : {
//			show : function(parentCfg){
//				var _this =  parentCfg.self;
//				var winkey=parentCfg.key;
//				_this.globalMgr.activeKey = winkey;
//				parentCfg.parent = _this.getGrid2();
//				var winModule=null;
//				var subnoid=null;
//				if(winkey!="添加提成方案"){
//					subnoid=parentCfg.parent.getSelId().substring(0,1);
//					if(winkey=='查看员工详情')winModule="EmployeeDetail";
//				}else{
//						winModule="DeductPlanEdit";
//				}	
//				if(subnoid!=null)
//					winkey=winkey+subnoid;
//				if(_this.appCmpts[winkey]){
//					_this.appCmpts[winkey].show(parentCfg);
//				}else{
//					Cmw.importPackage('pages/app/sys/stores/'+winModule,function(module) {
//					 	_this.appCmpts[winkey] = module.WinEdit;
//					 	_this.appCmpts[winkey].show(parentCfg);
//			  		});
//				}
//			}
		}
		},
		/**
		 * 家庭成员表格
		 */
		getCabinetGrid:function(){
			var _this=this;
			var structure_1 = [{
			    header: '姓名',
			    name: 'name'
			},
			{
			    header: '关系',
			    name: 'relation'
			},
			{
			    header: '年龄',
			    name: 'age'
			},
			{
			    header: '住址',
			    name: 'address'
			},
			{
			    header: '电话',
			    name: 'tell'
			},
			{
			    header: '工作单位',
			    name: 'company'
			},
			{
			    header: '职务',
			    name: 'job'
			},
			{
			    header: '单位电话',
			    name: 'comptell'
			},
			{
			    header: '备注',
			    name: 'remark'
			},{
			    header: 'ID',
			    hidden:true,
			    name: 'id'
			}];
			_this.cabTbar=_this.getCabTbar();
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    height:290,
			    url: './hrFamily_list.action',
			    tbar:_this.cabTbar,
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
		},
		/**
		 * 家庭成员菜单栏
		 */
		getCabTbar:function(){
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "添加",
			text : Btn_Cfgs.INSERT_BTN_TXT,
			iconCls:Btn_Cfgs.INSERT_CLS,
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		},{type:"sp"},{
			token : "编辑",
			text : Btn_Cfgs.MODIFY_BTN_TXT,
			iconCls:Btn_Cfgs.MODIFY_CLS,
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				self.openWin({key:this.token,self:self,me:this});
			}
		},{type:"sp"},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:Btn_Cfgs.DELETE_CLS,
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./hrFamily_delete.action',{type:'grid',cmpt:self.parent,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
		},
		/**
		 * 家庭成员表单
		 */
		getCabForm:function(){
			var hid_id=FormUtil.getHidField({
		 		fieldLabel:"ID",
		 		name:"id"
		 	});
		 	
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '姓名',
			    name: 'name',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "30"
			});
			
			var txt_relation = FormUtil.getTxtField({
			    fieldLabel: '关系',
			    name: 'relation',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "30"
			});
			
			var int_age = FormUtil.getIntegerField({
			    fieldLabel: '年龄',
			    name: 'age',
			    "width": 125,
			    "maxLength": 10
			});
			
			var txt_tell = FormUtil.getTxtField({
			    fieldLabel: '电话',
			    name: 'tell',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_address = FormUtil.getTxtField({
			    fieldLabel: '住址',
			    name: 'address',
			    "width": 125,
			    "maxLength": "200"
			});
			
			var txt_job = FormUtil.getTxtField({
			    fieldLabel: '职务',
			    name: 'job',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_comptell = FormUtil.getTxtField({
			    fieldLabel: '单位电话',
			    name: 'comptell',
			    "width": 125,
			    "maxLength": "30"
			});
			
			var txt_company = FormUtil.getTxtField({
			    fieldLabel: '工作单位',
			    name: 'company',
			    "width": 620,
			    "maxLength": "150"
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 620,
			    "maxLength": "150"
			});
			
			var hid_empId=FormUtil.getHidField({
		 		fieldLavel:"员工ID",
		 		name:"inempId"
		 	});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_name, txt_relation, int_age]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_tell, txt_address, txt_job, txt_comptell]
			},
			txt_company, txa_remark,hid_id,hid_empId];
			var frm_cfg = {
			    title: '家庭成员信息编辑',
			    trackResetOnLoad:true,
			    url: './hrFamily_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		openWin:function(operating){
			var _this=this;
			if(operating.key=="编辑"&&!this.parent.getSelId()){
				return ;	
			}
			_this.saveFrm.setDisabled(false);
			_this.getFrmContent(operating);
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
			var _this=exports.WinEdit;
			var cfg = {}
			if(_this.parent.getXType()!='appgrid'){
				cfg.beforeMake=function(data){
					data.planItem=Ext.encode(_this.getBatchDatas());
					}
			}
			cfg.sfn = function(formData){
					if(_this.parent==_this.wageGrid1){
							EventManager.get("./hrEmpPlanItem_list.action",
								{params:{inempId:_this.empId},
								sfn : function(jsonData){
									_this.wageGrid1.loadJsonDatas(jsonData,false);
							}});		
					}else if(_this.parent){
						_this.parent.reload({inempId:_this.empId,formId:_this.empId});
					}
					
				}
			EventManager.frm_save(_this.saveFrm,cfg);
		},
		/**
		 * 获得可编辑表格数据
		 */
		getBatchDatas : function(){
			var _this=this;
			var store = _this.parent.getStore();
			var arr = [];
			for(var i=0,count = store.getCount(); i<count; i++){
				var record = store.getAt(i);
				var id =  record.get('empItemId')
				var planitemId = record.get('itemId');
				var inempId = _this.empId;
				var bamount = record.get('bamount');
				var direction = record.get('direction');
				var remark = record.get('remark');
				arr[arr.length] = {id:id,planitemId:planitemId,
				inempId:inempId,bamount:bamount,direction:direction,remark:remark};
			}
			return arr;
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
