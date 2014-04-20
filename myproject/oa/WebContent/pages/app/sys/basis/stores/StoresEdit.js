/**
 * 门店
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
		bigImgId : new Ext.id(null,"bigImgId"),
		storeImg:null,
		tabPanel:null,
		tabElement:{},
		dltType:null,
		storeId:null,
		appCmpts:[],
		imgAttId:null,
		selImgId:null,
		cmp:null,
		uploadWin:null,
		controlType:null,
		STORE_TITLE:{
			titleImg:"门店图片",
			titleOrd:"门店订货规则",
			titleEmp:"门店员工",
			titleDed:"提成方案",
			titleCou:"专柜档案"	
		},
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
				this.appFrm = this.createForm();
				this.tabElement.panel1=this.getPanel1();
				this.tabElement.panel2=this.getPanel2();
				this.tabElement.panel3=this.getPanel3();
				this.tabElement.panel4=this.getPanel4();
				this.tabElement.panel5=this.getPanel5();
				var _this=this;
				this.tabPanel =this.getTabPanel();
			this.appWin = new Ext.ux.window.MyWindow({
				title:"门店信息编辑",
				items:[_this.tabPanel],
				width:780
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
			this.tabPanel.setActiveTab(0);
			this.getUrls();
		},
		/**
		 * 获取订单限制方式单选按钮表单
		 */
		getOrdering:function(){
			var _this=this;
			var rad_storeOrder = FormUtil.getRadioGroup({
			    fieldLabel: '门店订单限制方式',
			    name: 'controlType',
			    "width": 500,
			    "maxLength": 50,
			    "items": [{
			        "boxLabel": "按月订货次数限制",
			        "name": "controlType",
			        checked:true,
			        "inputValue": "1"
			    },
			    {
			        "boxLabel": "按订货类型次数限制",
			        "name": "controlType",
			        "inputValue": "2"
			    },
			    {
			        "boxLabel": "无限制",
			        "name": "controlType",
			        "inputValue": "3"
			    }]
			});
		var Butn = new Ext.Button({
				text: '保存',
				name:'btn',
				buttonAlign : 'center',
				handler:function(){
			switch(rad_storeOrder.getValue()){
				case '1':
					_this.tabElement.orderAppgrid.disable();
					_this.tabElement.form2.enable();
					break;
				case '2':
					_this.tabElement.orderAppgrid.enable();
					_this.tabElement.form2.disable();
					break;
				default :
					_this.tabElement.orderAppgrid.disable();
					_this.tabElement.form2.disable();
					break;
			}
			var cfg={
				beforeMake : function(formData){
					formData.id=_this.storeId;
				}
			};
			_this.controlType=rad_storeOrder.getValue();
			EventManager.frm_save(appform_1,cfg);
		}
			});
			
			var controlType_btns = FormUtil.getMyCompositeField({
				fieldLabel: '门店订单限制方式',
				itemsOne: true ,
				sigins:null,
				itemNames : 'controlType,btn',
		    	name: 'conTypeBtn',
		    	width:650,
		    	items:[rad_storeOrder,{xtype:'displayfield',width:1},Butn]
	    	});
			
		var layout_fields = [controlType_btns];
		var frm_cfg = {
		    url: './oaStores_updateConType.action', 
		    labelWidth:130
		};
		var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
		return appform_1;
		},
		/**
		 * 最高月订票次数编辑表单
		 */
		getForm2:function(){
			var _this=this;
			var txt_maxCount = FormUtil.getTxtField({
			    fieldLabel: '月最高订货次数',
			    name: 'maxCount',
			    unitText:"次",
			    value:'0',
			    "width": "80"
			});
			
			var hid_id=FormUtil.getHidField({
				fieldLabel: 'ID',
			    name: 'id'
			});
			
			var hid_otype=FormUtil.getHidField({
				fieldLabel: '订货类型',
				value:"-1",
			    name: 'otype'
			});
		/*业务引用键*/
			var txt_Recode = FormUtil.getTxtField({fieldLabel : '业务引用键',name:'recode',width:130,allowBlank:false,maxLength:50});
			/*buuton*/
			var Butn = new Ext.Button({
				text: '保存',
				handler:function(){
					var cfg = {
						beforeMake : function(formData){
							formData.storeId=_this.storeId;
							formData.controlType=_this.controlType;
						},
						sfn : function(formData){
						}
					};
					EventManager.frm_save(appform_1,cfg);
				}
			});
//			var rec_btn = FormUtil.getMyCompositeField({fieldLabel: '业务引用键',allowBlank:false,sigins:null,itemNames:'recode',
//			    name: 'rec_btn',width:100,items:[txt_Recode,{xtype:'displayfield',width:1},Butn]});
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_maxCount,Butn,hid_id,hid_otype]
			}];
			var frm_cfg = {
			    title: '按月订货次数限制',
			    labelWidth:120,
			    url: './oaOrderRule_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * 门店订货规则表格
		 */
		 getOrderAppgrid:function(){
		 	var _this=this;
		 	var structure_1 = [{
			    header: '订货类型',
			    name: 'otypeName'
			},
			{
			    header: '最大订货次数',
			    name: 'maxCount'
			},
			{
			    header: 'ID',
			    hidden:true,
			    name: 'id'
			},
			{
			    header: '创建人',
			    name: 'empName'
			},
			{
			    header: '创建日期',
			    name: 'createTime'
			},
			{
			    header: '备注',
			    name: 'remark'
			}];
		var appgrid_1 = new Ext.ux.grid.AppGrid({
		    title: '按订货类型次数限制',
		    tbar:_this.getCountTbar(),
		    structure: structure_1,
		    url: './oaOrderRule_list.action',
		    height:300,
		    needPage: false,
		    isLoad: false,
		    keyField: 'id'
		});
		return appgrid_1;
		 },
		/**
		 * 创建tab面板
		 */
		 getTabPanel:function(){
		 	var _this=this;
			var tabs = new Ext.TabPanel({
			    activeTab: 0,
			    height:400,
			    items:[_this.appFrm,_this.tabElement.panel1,_this.tabElement.panel2,_this.tabElement.panel3,_this.tabElement.panel4,_this.tabElement.panel5]
			});
			tabs.on("tabchange",function(){
	    		var title=this.activeTab.title;
				if(title==_this.STORE_TITLE.titleImg){
					_this.imgOnclick();
				}else if(title==_this.STORE_TITLE.titleOrd){
					var frm=_this.tabElement.ordering;
					if(!_this.controlType)_this.controlType=3;
					frm.findFieldByName("conTypeBtn").setValue({controlType:_this.controlType});
					if(_this.controlType=='1'){
						var form2=_this.tabElement.form2;
						_this.tabElement.orderAppgrid.disable();	
						form2.enable();
						_this.setFrm(form2,"./oaOrderRule_getBySid.action",{storeId:_this.storeId,otype:-1});
					}else if(_this.controlType=='2'){
						_this.tabElement.orderAppgrid.enable();
						_this.tabElement.form2.disable();
						_this.tabElement.orderAppgrid.reload({storeId:_this.storeId});	
					}else{
						_this.tabElement.form2.disable();
					}
				}else if(title==_this.STORE_TITLE.titleEmp){
					_this.tabElement.grid3.reload({storeId:_this.storeId});	
				}else if(title==_this.STORE_TITLE.titleDed){
					_this.tabElement.grid4.reload({storeId:_this.storeId});
				}else if(title==_this.STORE_TITLE.titleCou){
					_this.tabElement.grid5.reload({storeId:_this.storeId});
				}
	    	});
			return tabs;
		 },
		 setFrm:function(frm,url,params){
			var parent = exports.WinEdit.parent;
			var _this=exports.WinEdit;
			frm.setValues(url,{
				params:params,
				sfn:function(jsondata){
					_this.tabElement.form5.findFieldByName("storeId").setValue(_this.storeId);
				}
			});
			
		
		 },
		 /**
		  *门店图片面板
		  */
		 getPanel1:function(){
		 	var _this=this;
			var tb = new Ext.Panel({
		 		title:_this.STORE_TITLE.titleImg,
		 		id:'imgs',
		 		bbar:_this.getTbar()
			});
			var p=_this.getImgPanel();
			_this.tabElement.pnl=_this.getImgPanel2();
			var layoutCmnPanel = FormUtil.getLayoutPanel([.85,.15],[p,_this.tabElement.pnl]);
			tb.add(layoutCmnPanel);            
			return tb;
		 },
		 /**
		  * 门店小图面板
		  */
		 getImgPanel2:function(){
		 	var p2=new Ext.Panel({
				id:"imgMin",
				width:110,
				height:360,
				autoScroll:true,
				html:""
			});
			return p2;
		 },
		 /**
		  * 门店大图面板
		  */
		 getImgPanel:function(){
		 	var _this=this;
		 		var p=new Ext.Panel({
		 			width:700,
		 			height:360,
		 			id :  _this.bigImgId,
		 			html:""
				});
			return p;
		 },
		 /**
		  * 给图片绑定点击事件，点击切换大图面板中的图片
		  */
		 imgOnclick:function(){
		 	var _this=this;
		 	var imgs="";
		 	EventManager.get("./sysAttachment_list.action",
				{params:{formType:ATTACHMENT_FORMTYPE.FType_8,formId:this.storeId},
				sfn : function(jsonData){
				 	var imgIds=[];
				 	var ids=[];
					//给小图面板添加图片
					for(var i=0;i<jsonData.list.length ;i++){
						var fileName=jsonData.list[i].fileName;
						var filePath=jsonData.list[i].filePath;
						var imgId=fileName.substring(0,fileName.indexOf("."));
						imgIds[i]=imgId;
						ids[i]=jsonData.list[i].id;
						imgs+="<img id="+imgId+" width='108px' height='65px' src='"+filePath+"'>";
					}
		 			var htmlContent="<div id='imgDiv'>"+imgs+"</div>";
		 			Ext.get("imgMin").update(htmlContent,true,function(){
			 			//给图片添加点击事件
			 			for(var i=0;i<imgIds.length;i++){
			 				var imgEle=Ext.get(imgIds[i]);
			 				imgEle.index=i;
			 				imgEle.on('click',function(cmp){
								var bigImg = Ext.getCmp(_this.bigImgId);
								if(_this.imgAttId && Ext.get(_this.imgAttId)){
									Ext.get(_this.imgAttId).dom.setAttribute("style","");
								}
								_this.imgAttId=imgIds[this.index];
								_this.selImgId=ids[this.index];
								var imgDom=Ext.get(imgIds[this.index]).dom;
								imgDom.setAttribute("style"," border: solid 2px #F00");
								var src=imgDom.getAttribute("src");
								bigImg.update('<div style="text-align:center"><img height="360" width="700" src='+src+'></div>');
							});	
			 			}
		 			});
				}
			});	

		 },
		 /**
		  * 门店订货规则面板
		  */
		  getPanel2:function(){
		 	var _this=this;
			var tb = new Ext.Panel({
		 		title:_this.STORE_TITLE.titleOrd
			});
			_this.tabElement.orderAppgrid=_this.getOrderAppgrid();
			_this.tabElement.ordering=_this.getOrdering();
			_this.tabElement.form2=_this.getForm2();
			tb.add(_this.tabElement.ordering);
			tb.add(_this.tabElement.form2);
			tb.add(_this.tabElement.orderAppgrid);
			_this.tabElement.orderAppgrid.disable();
			return tb;
		 },
		 /**
		  * 门店员工面板
		  */
		  getPanel3:function(){
		 	var _this=this;
			var tb = new Ext.Panel({
		 		title:_this.STORE_TITLE.titleEmp
			});
			_this.tabElement.grid3=_this.getGrid2();
			tb.add(_this.tabElement.grid3);
			return tb;
		 }, 
		 /**
		  * 提成方案面板
		  */
		  getPanel4:function(){
		 	var _this=this;
			var tb = new Ext.Panel({
		 		title:_this.STORE_TITLE.titleDed
			});
			_this.tabElement.grid4=_this.getDeductGrid();
			tb.add(_this.tabElement.grid4);
			return tb;
		 },
		  /**
		  * 专柜档案面板
		  */
		  getPanel5:function(){
		 	var _this=this;
			var tb = new Ext.Panel({
		 		title:_this.STORE_TITLE.titleCou
			});
			_this.tabElement.grid5=_this.getCabinetGrid();
			tb.add(_this.tabElement.grid5);
			_this.tabElement.form5=_this.getCabForm();
			_this.tabElement.form5.disable();
			tb.add(_this.tabElement.form5);
			return tb;
		 },
		 /**
		  * 订货类型次数限制菜单栏
		  */
		getCountTbar:function(){
		var self = this;
		var _this=exports.WinEdit;
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
				self.openWin({key:this.token,optionType:OPTION_TYPE.EDIT,self:self,me:this});
			}
		},{
			token : "删除",
			text : Btn_Cfgs.DELETE_BTN_TXT,
			iconCls:'page_delete',
			tooltip:Btn_Cfgs.DELETE_TIP_BTN_TXT,
			handler : function(){
				EventManager.deleteData('./oaOrderRule_delete.action',{type:'grid',cmpt:_this.tabElement.orderAppgrid,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
	
		  },
		getTbar:function(){
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "上传图片",
			text : Btn_Cfgs.UPLOAD_IMG_LABEL_TEXT,
			iconCls:'page_add',
			tooltip:Btn_Cfgs.INSERT_TIP_BTN_TXT,
			handler : function(){
				self.showUploadWin();
			}
		},{
			token : "删除图片",
			text : Btn_Cfgs.DELETE_IMG_LABEL_TEXT,
			iconCls:'page_edit',
			tooltip:Btn_Cfgs.MODIFY_TIP_BTN_TXT,
			handler : function(){
				ExtUtil.confirm({title:Msg_SysTip.title_appconfirm,msg:Msg_SysTip.msg_deleteData,fn:function(btn){
					if(!btn || btn != 'yes') return;
					EventManager.get('./sysAttachment_delete.action',{
						params:{ids:self.selImgId,isenabled:-1},sfn:function(){
							var imgDiv=Ext.get("imgDiv").dom;
							var imgObj=Ext.get(self.imgAttId).dom;
							imgDiv.removeChild(imgObj);
							Ext.tip.msg(Msg_SysTip.title_appconfirm, Msg_SysTip.msg_dataSucess);
						}
					});
				}})
			}
		},{type:"sp"},{
			token : "关闭",
			text : Btn_Cfgs.CLOSE_IMG_LABEL_TEXT,
			iconCls:'page_enabled',
			tooltip:Btn_Cfgs.ENABLED_TIP_BTN_TXT,
			handler : function(){
				self.appWin.hide();
			}
		}];
		var toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
	},
	/**
	 * 显示上传窗口
	 */
	showUploadWin :	function(){
		var _this = this;
		if(!this.uploadWin){
			this.uploadWin = new Ext.ux.window.MyUpWin({
			title:"上传图片",
			label:"请选择文件",
			width:450,
			dir:"stores_img_path",
			allowFiles:"allowFiles_pic_ext",
			sfn:function(fileInfos){
				var filePath = null;
				if(fileInfos){
					var fileInfo = fileInfos[0];
					filePath = fileInfo.serverPath;
				}
				var imgDiv=Ext.get("imgDiv");
				var imgObj=document.createElement("img");
				imgObj.setAttribute("src",filePath);
				imgObj.setAttribute("width","108px");
				imgObj.setAttribute("height","65px");
				var str=filePath.split("/");
				var fileName=str[str.length-1];
				var imgId=fileName.substring(0,fileName.indexOf("."));
				imgObj.setAttribute("id",imgId);
				imgDiv.appendChild(imgObj);
				var newImg=Ext.get(imgId);
				newImg.on('click',function(cmp){
					var bigImg = Ext.getCmp(_this.bigImgId);
					var src = newImg.dom.getAttribute("src");
					if(_this.imgAttId && Ext.get(_this.imgAttId))
						Ext.get(_this.imgAttId).dom.setAttribute("style","");
					_this.imgAttId=imgId;
					Ext.get(_this.imgAttId).dom.setAttribute("style"," border: solid 2px #F00");
					bigImg.update('<div><img height="360" width="700" src='+src+'></div>');
				});
				_this.savaStoreImg(fileName,filePath);
			}});
		}
		this.uploadWin.show();
	},
	/**
	 * 保存图片附件到数据库
	 */
	savaStoreImg:function(fileName,filePath){
		var params={};
		params.sysId=-1;
		params.formType=ATTACHMENT_FORMTYPE.FType_8;
		params.formId=this.storeId;
		params.atype=2;
		params.fileName=fileName;
		params.filePath=filePath;
		EventManager.get("./sysAttachment_save.action",
			{params:params,sfn : function(jsonData){
			}
		});	

	},
	/**
	 * 门店员工表格
	 */
		getGrid2:function(){
			var _this=this;
			var structure_1 = [{
			    header: '员工编号',
			    name: 'code'
			},{
				header:'id',
				hidden:true,
				name:'id'
			},
			{
			    header: '姓名',
			    name: 'name'
			},
			{
			    header: '性别',
			    renderer:Render_dataSource.sex_hrRender,
			    name: 'sex'
			},
			{
			    header: '身份证号码',
			    name: 'idcard'
			},
			{
			    header: '联络电话',
			    name: 'ctel'
			},
			{
			    header: '行政职务',
			    name: 'positionName'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    url: './hrEmployee_list.action',
			    height:400,
			    tbar:_this.getEmpTbar(),
			    needPage: false,
			    isLoad: false,
			    keyField: 'id',
			    listeners : {
				rowdblclick : function() {
				//鼠标双击事件
				var selRow = appgrid_1.getSelRow();//获取对象所在的行
				if(selRow!=null){
					_this.globalMgr.winEdit.show({key:"查看员工详情",self:_this,me:this});
				}
					}
				}
			});
				return appgrid_1;	
		},
		getEmpTbar:function(){
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "查看员工详情",
			text : Btn_Cfgs.DETAIL_EMP_BTN_TXT,
			iconCls:Btn_Cfgs.DETAIL_EMP_CLS,
			tooltip:Btn_Cfgs.DETAIL_EMP_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.token,self:self,me:this});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
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
			var selId = parent.getSelId();
			_this.storeId=selId;
			this.appFrm.setValues("./oaStores_get.action",{
				params:{id:selId},
				sfn:function(jsondata){
					_this.controlType=jsondata.controlType;
//					_this.appFrm.setVs(jsondata);
				}
			});
			
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
			if(_this.parentCfg.self.refresh)_this.parentCfg.self.refresh(_this.optionType,data);
			if(_this.dltType=="删除提成方案")_this.tabElement.grid4.reload({storeId:_this.storeId});
			if(_this.dltType=="删除专柜")_this.tabElement.grid5.reload({storeId:_this.storeId});
		},
//		sltDzGrid:function(){var structure_1 = [{
//			    header: '姓名',
//			    name: 'empName'
//			},
//			{
//			    header: '店长联系电话',
//			    name: 'dzTel'
//			},
//			{
//			    header: '店面名称',
//			    name: 'name'
//			}];
//			var appgrid_1 = new Ext.ux.grid.AppGrid({
//			    structure: structure_1,
//			    url: 'undefined',
//			    needPage: false,
//			    isLoad: false,
//			    height:220,
//			    keyField: 'id'
//			});
//			var appgridpanel_1 = new Ext.Panel({
//			    items: [appgrid_1]
//			});
//				return appgridpanel_1;			
//			},
//			sltLxrGrid:function(){
//				var structure_1 = [{
//				    header: '联系人',
//				    name: 'lxrId'
//				},
//				{
//				    header: '联系人电话',
//				    name: 'lxrTel'
//				},
//				{
//				    header: '店面名称',
//				    name: 'name'
//				}];
//				var appgrid_1 = new Ext.ux.grid.AppGrid({
//				    structure: structure_1,
//				    url: 'undefined',
//				    needPage: false,
//				    isLoad: false,
//				    height:220,
//				    keyField: 'id'
//				});
//				return appgrid_1;
//			},
//			sltMdmcGrid:function(){
//				var structure_1 = [{
//				    header: '店面名称',
//				    name: 'name'
//				},
//				{
//				    header: '门店编号',
//				    name: 'code'
//				},
//				{
//				    header: '门店属性',
//				    name: 'ptype'
//				},
//				{
//				    header: '门店等级',
//				    name: 'slevel'
//				}];
//				var appgrid_1 = new Ext.ux.grid.AppGrid({
//				    structure: structure_1,
//				    url: 'undefined',
//				    height:220,
//				    needPage: false,
//				    isLoad: false,
//				    keyField: 'id'
//				});
//				return appgrid_1;
//			},
//			sltMdzjGrid:function(){
//				var structure_1 = [{
//				    header: '门店总监',
//				    name: 'directorId'
//				},
//				{
//				    header: '店面地址',
//				    name: 'address'
//				}];
//				var appgrid_1 = new Ext.ux.grid.AppGrid({
//					height:220,
//					structure: structure_1,
//				    url: 'undefined',
//				    needPage: false,
//				    isLoad: false,
//				    keyField: 'id'
//				});
//				return appgrid_1;
//			},
//			sltQyjlGrid:function(){
//				var structure_1 = [{
//			    header: '区域经理',
//			    name: 'mgrId'
//			},
//			{
//			    header: '门店区域',
//			    name: 'areaId'
//			}];
//			var appgrid_1 = new Ext.ux.grid.AppGrid({
//			    structure: structure_1,
//			    height:220,
//			    url: 'undefined',
//			    needPage: false,
//			    isLoad: false,
//			    keyField: 'id'
//			});
//			return appgrid_1;
//			},
//			sltKpdvGrid:function(){
//				var structure_1 = [{
//			    header: '开票单位',
//			    name: 'oorgId'
//			}];
//			var appgrid_1 = new Ext.ux.grid.AppGrid({
//			    structure: structure_1,
//			    url: 'undefined',
//			    height:220,
//			    needPage: false,
//			    isLoad: false,
//			    keyField: 'id'
//			});
//			return appgrid_1;
//			},
//			sltHkdvGrid:function(){
//				var structure_1 = [{
//				    header: '回款单位',
//				    name: 'rorgId'
//				}];
//				var appgrid_1 = new Ext.ux.grid.AppGrid({
//				    structure: structure_1,
//				    url: 'undefined',
//				    height:220,
//				    needPage: false,
//				    isLoad: false,
//				    keyField: 'id'
//				});
//				return appgrid_1;
//			},
//			sltJegsGrid:function(){
//			var structure_1 = [{
//			    header: '显示公式',
//			    name: 'dispExpress'
//			},
//			{
//			    header: '实际公式表达式',
//			    name: 'express'
//			}];
//			var appgrid_1 = new Ext.ux.grid.AppGrid({
//			    structure: structure_1,
//			    url: 'undefined',
//			    needPage: false,
//			    height:220,
//			    isLoad: false,
//			    keyField: 'id'
//			});
//			return appgrid_1;
//			},
//			sltWlgsGrid:function(){
//				var structure_1 = [{
//			    header: '物流公司名称',
//			    name: 'name'
//			},
//			{
//			    header: '联系人',
//			    name: 'cman'
//			},
//			{
//			    header: '网站',
//			    name: 'website'
//			},
//			{
//			    header: '配送路线',
//			    name: 'rway'
//			}];
//			var appgrid_1 = new Ext.ux.grid.AppGrid({
//			    structure: structure_1,
//			    url: 'undefined',
//			    needPage: false,
//			    height:220,
//			    isLoad: false,
//			    keyField: 'id'
//			});
//			return appgrid_1;
//			},
		/**
		 * 创建Form表单
		 */
		createForm : function(){
				var txt_code = FormUtil.getTxtField({
			    fieldLabel: '门店编号',
			    name: 'code',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "20"
			});
			
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '店面名称',
			    name: 'name',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "50"
			});
//			
//			var txt_storeArea = FormUtil.getReadTxtField({
//			    fieldLabel: '店面区域',
//			    name: 'storeArea',
//			    "allowBlank": false,
//			    "width": "125"
//			});
			
			var txt_address = FormUtil.getTxtField({
			    fieldLabel: '店面地址',
			    name: 'address',
			    "width": 125,
			    "maxLength": "500"
			});
			
			var cbo_ptype = FormUtil.getLCboField({
			    fieldLabel: '门店属性',
			    name: 'ptype',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [["1", "商场门店"], ["2", "独立店"], ["", ""]]
			});
			var cbo_slevel = FormUtil.getRCboField({
			    fieldLabel: '门店等级',
			    name: 'slevel',
			    "width": 125,
			    "allowBlank": false,
			    register : REGISTER.GvlistDatas, restypeId : '100001'
			});
			var cbo_stype = FormUtil.getLCboField({
			    fieldLabel: '门店类型',
			    name: 'stype',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": 10,
			    "data": [["1", "自营店"], ["2", "直营店"], ["3", "加盟店"]]
			});

			var txt_dzId = ComboxControl.getEmpCboGrid({fieldLabel:'店长',name:'dzId',allowBlank:false,callback:function(id,selValues,records){
				var record = records[0];
				var ctel = record.get("ctel");
				txt_dzTel.setValue(ctel);
			}});
			    
			var txt_dzTel = FormUtil.getReadTxtField({
			    fieldLabel: '店长联系电话',
			    name: 'dzTel',
			    "width": 125,
			    "maxLength": "20"
			});

			var txt_directorId = ComboxControl.getEmpCboGrid({fieldLabel:'门店总监',name:'directorId',callback:function(id,selValues,records){}});
			
			var txt_lxrId = ComboxControl.getEmpCboGrid({fieldLabel:'联系人',name:'lxrId',callback:function(id,selValues,records){
				var record = records[0];
				var ctel = record.get("ctel");
				txt_lxrTel.setValue(ctel);
			}});
	    
			var txt_lxrTel = FormUtil.getReadTxtField({
			    fieldLabel: '联系人电话',
			    name: 'lxrTel',
			    "width": 125,
			    "maxLength": "20"
			});
			
			var txt_mgrId = ComboxControl.getEmpCboGrid({fieldLabel:'区域经理',name:'mgrId',callback:function(id,selValues,records){}});
			
			   
			var barOorg = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strOorg = [
				{header: '名称', name: 'name',width:100},
				{header: '客户类别',name: 'custType',width:60},
				{header: '价格等级', name: 'plevelId',width:80}];
			var txt_wlgs = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '所属部门',
			    name: 'indeptId',
			    barItems : barOorg,
			    structure:strOorg,
			    dispCmn:'name',
			    isAll:true,
			    url : './oaLogistics_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			
			var barArea = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strArea = [
				{header: '名称', name: 'name',width:100},
				{header: '价格等级', name: 'plevelId',width:80}];
			var txt_areaId = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '门店区域',
			    name: 'areaId',
			     barItems : barOorg,
			    structure:strOorg,
			    dispCmn:'name',
			    isAll:true,
			    url : './oaTradArea_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    "width": 125
			});
			
			var bdat_startDate = FormUtil.getDateField({
			    fieldLabel: '开店日期',
			    disabled :true,
			    name: 'startDate',
			    "width": 125
			});
			var bdat_endDate = FormUtil.getDateField({
			    fieldLabel: '撤店日期',
			    disabled :true,
			    name: 'endDate',
			    "width": 125
			});
			
			var barOorg = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strOorg = [
				{header: '名称', name: 'name',width:100},
				{header: '客户类别',name: 'custType',width:60},
				{header: '价格等级', name: 'plevelId',width:80}];
			var txt_oorgId = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '客户',
			    name: 'oorgId',
			    barItems : barOorg,
			    structure:strOorg,
			    dispCmn:'name',
			    isAll:true,
			    url : './oaBillUnit_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			   
			var txt_orderAmount = FormUtil.getMoneyField({
			    fieldLabel: '订货额度',
			    name: 'orderAmount',
			    unitText:"元",
			    "width": 120
			});
//			var txt_rorg = FormUtil.getTxtField({
//			    fieldLabel: '回款单位',
//			    name: 'rorgId',
//			    "width": 320
//			});
//			var Butn_rorg = new Ext.Button({text: '.....'});
//			Butn_rorg.on('click',function(){
//				new Ext.Window({
//					title:'选择回款单位',
//					renderTo:'storeAddWin',
//					items:[_this.sltHkdvGrid()],
//					height:250,
//					width:300
//				}).show();
//				
//			});
//			var txt_rorgId = FormUtil.getMyCompositeField({fieldLabel: '回款单位',sigins:null,itemNames:'dzId',
//			    name: 'Butn_rorg',width:350,items:[txt_rorg,{xtype:'displayfield',width:1},Butn_rorg]});
//			   
			var barOorg = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strOorg = [
				{header: '名称', name: 'name',width:100},
				{header: '客户类别',name: 'custType',width:60},
				{header: '价格等级', name: 'plevelId',width:80}];
			var txt_wlgs = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '物流公司',
			    name: 'wlgsId',
			    barItems : barOorg,
			    structure:strOorg,
			    dispCmn:'name',
			    isAll:true,
			    url : './oaLogistics_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			   
			var int_dhDays = FormUtil.getIntegerField({
			    fieldLabel: '到货天数',
			    name: 'dhDays',
			    "width": 120,
			    unitText:"天",
			    "maxLength": 10
			});
			var txt_standAmount = FormUtil.getMoneyField({
			    fieldLabel: '标准陈列额',
			    name: 'standAmount',
			    unitText:"元",
			    "width": 120
			});
			
			var barFormula = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var strFormula = [
				{header: '显示公式', name: 'dispExpress',width:100},
				{header: '实际公式表达式',name: 'express',width:100}];
			var txt_mzFormulaId = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '免责金额公式',
			    name: 'mzFormulaId',
			    barItems : barFormula,
			    structure:strFormula,
			    dispCmn:'name',
			    isAll:true,
			    url : './sysFormula_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 400
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": 620,
			    height:50,
			    "maxLength": 50
			});
			
			var rdo_stockMaintain=FormUtil.getRadioGroup({
				fieldLabel:"库存维护",
				name:"stockMaintain",
			    "width": 120,
			    "maxLength": 50,
			    "items": [{
			        "boxLabel": "起用",
			        "name": "stockMaintain",
			        "inputValue": "1",
			        "listeners":{
			        	"check":function(){
			        		rdo_StockOwe.disable();	
			        	}
			        }
			    },
			    {
			        "boxLabel": "不起用",
			        "checked":true,
			        "name": "stockMaintain",
			        "inputValue": "2",
			        "listeners":{
			        	"check":function(){
			        		rdo_StockOwe.enable();
			        		rdo_StockOwe.setValue("2");
			        	}
			        }
			    }]
			});
			
			var rdo_StockOwe=FormUtil.getRadioGroup({
				fieldLabel:"库存允许负数",
				name:"stockOwe",
			    "width": 120,
			    "maxLength": 50,
			    "items": [{
			        "boxLabel": "允许",
			        "name": "stockOwe",
			        "inputValue": "1"
			    },
			    {
			        "boxLabel": "不允许",
			        "checked":true,
			        "name": "stockOwe",
			        "inputValue": "2"
			    }]
			});
			rdo_StockOwe.disable();	
			
			var hid_id=FormUtil.getHidField({
				fieldLabel:"ID",
				name:"id"
			});
			
			var hid_controlType=FormUtil.getHidField({
				fieldLabel:"订单限制方式",
				name:"controlType",
				value:"0"
			});
			
			var hid_indeptId=FormUtil.getHidField({
				fieldLabel:"所属部门",
				name:"indeptId",
				value:"-1"
			});
			var _this=this;
			var layout_fields = [{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code, txt_name,txt_areaId,txt_address]
			}, 
			{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbo_ptype, cbo_slevel, cbo_stype, txt_dzId, txt_dzTel, txt_directorId,
			    txt_lxrId, txt_lxrTel, txt_mgrId, bdat_startDate,bdat_endDate,txt_oorgId, txt_orderAmount,
			    txt_wlgs, txt_standAmount,int_dhDays,rdo_stockMaintain,rdo_StockOwe]
			},
			txt_mzFormulaId, txa_remark,_this.createButton(),hid_id,hid_controlType,hid_indeptId];
			var frm_cfg = {
				labelWidth:100,
			    title: '编辑门店',
			    height:1000,
			    url: './oaStores_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
				return appform_1;
				},
			/**
			 * 提成方案表格
			 */
			getDeductGrid:function(){
				var _this=this;
				var structure_1 = [{
				    header: '职位名称',
				    name: 'postName'
				},{
				    header: '业务分类',
				    renderer:Render_dataSource.deductPlan_category,
				    name: 'category'
				},
				{
				    header: '提成方案',
				    name: 'formulaName'
				},
				{
				    header: 'ID',
				    hidden:true,
				    name: 'id'
				},
				{
				    header: '创建人',
				    name: 'creator'
				},
				{
				    header: '创建日期',
				    name: 'createTime'
				},{
				    header: '备注',
				    name: 'remark'
				}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    tbar:_this.getDeductTbar(),
			    url: './oaDeductPlan_list.action',
			    needPage: false,
			    height:400,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
			},
		/**
		 * 提成方案菜单栏
		 */
		getDeductTbar:function(){
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "添加提成方案",
			text : Btn_Cfgs.ADD_DEDUCT_PLAN_BTN_TXT,
			iconCls:Btn_Cfgs.ADD_DEDUCT_PLAN_CLS,
			tooltip:Btn_Cfgs.ADD_DEDUCT_PLAN_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.token,self:self,me:this});
			}
		},{type:"sp"},{
			token : "编辑提成方案",
			text : Btn_Cfgs.EDIT_DEDUCT_PLAN_BTN_TXT,
			iconCls:Btn_Cfgs.EDIT_DEDUCT_PLAN_CLS,
			tooltip:Btn_Cfgs.EDIT_DEDUCT_PLAN_TIP_BTN_TXT,
			handler : function(){
				self.globalMgr.winEdit.show({key:this.token,optionType:OPTION_TYPE.EDIT,self:self,me:this});
			}
		},{type:"sp"},{
			token : "删除提成方案",
			text : Btn_Cfgs.DELETE_DEDUCT_PLAN_BTN_TXT,
			iconCls:Btn_Cfgs.DELETE_DEDUCT_PLAN_CLS,
			tooltip:Btn_Cfgs.DELETE_DEDUCT_PLAN_TIP_BTN_TXT,
			handler : function(){
				self.dltType="删除提成方案";
				EventManager.deleteData('./oaDeductPlan_delete.action',{type:'grid',cmpt:self.tabElement.grid4,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
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
			show : function(parentCfg){
				var _this =  parentCfg.self;
				var winkey=parentCfg.key;
				parentCfg.storeId=_this.storeId;
				var winModule=null;
				if(winkey=="添加提成方案"||winkey=="编辑提成方案"){
					parentCfg.parent = _this.tabElement.grid4;
					if(winkey=="编辑提成方案"){
						var selId=parentCfg.parent.getSelId();
						if(!selId)return;
					}
					winModule="DeductPlanEdit";
				}else if(winkey=='查看员工详情'){
					parentCfg.parent = _this.tabElement.grid3;
					var selId=parentCfg.parent.getSelId();
					if(!selId)return;
					winModule="EmployeeDetail";
				}else{
						winModule="";
				}	
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					Cmw.importPackage('pages/app/sys/basis/stores/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
		},
		/**
		 * 专柜档案表格
		 */
		getCabinetGrid:function(){
			var _this=this;
			var structure_1 = [{
			    header: '专柜编号',
			    name: 'code'
			},
			{
			    header: '专柜分类',
			    name: 'classifyName'
			},
			{
			    header: '专柜名称',
			    name: 'ctypeName'
			},
			{
			    header: 'id',
			    hidden:true,
			    name: 'id'
			},
			{
			    header: '价格',
			    name: 'price'
			},
			{
			    header: '陈列金额',
			    name: 'sprice'
			},
			{
			    header: '购买日期',
			    name: 'buyDate'
			},
			{
			    header: '使用日期',
			    name: 'useDate'
			},
			{
			    header: '备注',
			    name: 'remark'
			}];
			var appgrid_1 = new Ext.ux.grid.AppGrid({
			    structure: structure_1,
			    height:180,
			    url: './oaCabinet_list.action',
			    tbar:_this.getCabTbar(),
			    needPage: false,
			    isLoad: false,
			    keyField: 'id'
			});
			return appgrid_1;
		},
		/**
		 * 专柜菜单栏
		 */
		getCabTbar:function(){
		var self = this;
		var toolBar = null;
		var barItems = [{type:"sp"},{
			token : "添加专柜",
			text : Btn_Cfgs.ADD_CABINET_PLAN_BTN_TXT,
			iconCls:Btn_Cfgs.ADD_CABINET_PLAN_CLS,
			tooltip:Btn_Cfgs.ADD_CABINET_PLAN_TIP_BTN_TXT,
			handler : function(){
				var frm=self.tabElement.form5;
				frm.enable();
				self.setFrm(frm,"./oaCabinet_add.action",{});
			}
		},{type:"sp"},{
			token : "编辑专柜",
			text : Btn_Cfgs.EDIT_CABINET_PLAN_BTN_TXT,
			iconCls:Btn_Cfgs.EDIT_CABINET_PLAN_CLS,
			tooltip:Btn_Cfgs.EDIT_CABINET_PLAN_TIP_BTN_TXT,
			handler : function(){
				var selId=self.tabElement.grid5.getSelId();
				if(!selId)return;
				var frm=self.tabElement.form5;
				self.tabElement.form5.enable();
				self.setFrm(frm,"./oaCabinet_get.action",{id:selId});
			}
		},{type:"sp"},{
			token : "删除专柜",
			text : Btn_Cfgs.DELETE_CABINET_PLAN_BTN_TXT,
			iconCls:Btn_Cfgs.DELETE_CABINET_PLAN_CLS,
			tooltip:Btn_Cfgs.DELETE_CABINET_PLAN_TIP_BTN_TXT,
			handler : function(){
				self.dltType=this.token;
				EventManager.deleteData('./oaCabinet_delete.action',
					{type:'grid',cmpt:self.tabElement.grid5,optionType:OPTION_TYPE.DEL,self:self});
			}
		}];
		toolBar = new Ext.ux.toolbar.MyCmwToolbar({aligin:'left',controls:barItems});
		toolBar.hideButtons(Btn_Cfgs.SETDATARIGHT_BTN_TXT);
		return toolBar;
		},
		/**
		 * 专柜表单
		 */
		getCabForm:function(){
			var self=this;
			var toolBar=null;
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '专柜编号',
			    name: 'code',
			    "width": "125",
			    "allowBlank": false
			});
			
			var txt_classifyId = FormUtil.getRCboField({
			    fieldLabel: '专柜分类',
			    name: 'classifyId',
			    "allowBlank": false,
			    "width": 125,
			    register : REGISTER.GvlistDatas, restypeId : '100006'
			});
			
			var cbo_ctypeId = FormUtil.getRCboField({
			    fieldLabel: '类别名称',
			    name: 'ctypeId',
			    "allowBlank": false,
			    "width": 125,
			    "allowBlank": false,
			    register : REGISTER.GvlistDatas, restypeId : '100007'
			});
			
			var txt_price = FormUtil.getMoneyField({
			    fieldLabel: '价格',
			    name: 'price',
			    "width": 120,
			    "allowBlank": false,
			    "maxLength": 50,
			    unitText:'元'
			});
			
			var txt_sprice = FormUtil.getMoneyField({
			    fieldLabel: '陈列金额',
			    name: 'sprice',
			    "width": 120,
			    "maxLength": 50,
			    "allowBlank": false,
			    unitText:'元'
			});
			
			var bdat_buyDate = FormUtil.getDateField({
			    fieldLabel: '购买日期',
			    name: 'buyDate',
			    "width": 125,
			    "maxLength": 50
			});
			
			var bdat_useDate = FormUtil.getDateField({
			    fieldLabel: '使用日期',
			    name: 'useDate',
			    "width": 125
			});
			
			var txa_remark = FormUtil.getTAreaField({
			    fieldLabel: '备注',
			    name: 'remark',
			    "width": "620",
			    height:30,
			    onTrigger2Click:function(){
					var params = toolBar.getValues();
				}
			});
			var _this=this;
			var button_save = new Ext.Button({
				text : '保存',
				width : 80,
				handler:function(){
					var _this = exports.WinEdit;
					var cfg = {
						sfn : function(formData){
							appform_1.reset();
							_this.tabElement.grid5.reload({storeId:_this.storeId});
							appform_1.disable();
						}
					};
					EventManager.frm_save(appform_1,cfg);
				}
			});
			var button_reset = new Ext.Button({
						text : '重置',
						width : 80,
						listeners:{"click":function(){//添加事件  
							appform_1.reset();	
						}}
					})
			var buttons = [button_save, button_reset];
			var btnPanel = new Ext.Panel({buttonAlign : 'center',buttons:buttons});
			
			var hid_id = FormUtil.getHidField({
				fieldLabel:"id",
				name:"id"
			});
			
			var hid_store = FormUtil.getHidField({
				fieldLabel:"门店ID",
				name:"storeId"
			});
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_code, txt_classifyId, cbo_ctypeId, txt_price, txt_sprice, bdat_buyDate,hid_store]
			},
			bdat_useDate, txa_remark,btnPanel,hid_id];
			var frm_cfg = {
			    title: '专柜档案信息编辑',
			    url: './oaCabinet_save.action'
			};
			var appform_1 = FormUtil.createLayoutFrm(frm_cfg, layout_fields);
			return appform_1;
		},
		/**
		 * 专柜按钮
		 */
		getTBtn:function(){
			var p=new Ext.Panel({
			})	
			var btnSave=new Ext.Button({text:SAVE_BTN_TXT,x:50});
			var btnReset=new Ext.Button({
			rext:RESET_BTN_TXT,x:50});
			btnReset.on({
				handler:function(){
					toolBar.reset();	
				}
			});
			return p;
		},
		/**
		 * 门店按钮
		 */
		createButton : function() {
			var _this=this;
			var button_save = new Ext.Button({
						text : '保存',
						width : 80,
						handler:function(){
							_this.saveData();
						}
				});
			var button_reset = new Ext.Button({
						text : '重置',
						width : 80,
						listeners:{"click":function(){//添加事件  
							_this.appFrm.reset();	
						}}
					})
			var buttons = [button_save, button_reset];
			var btnPanel = new Ext.Panel({buttonAlign : 'center',buttons:buttons});
			return btnPanel;
		},
		openWin:function(cfg){
			var _this=this;
			var parent=_this.tabElement.orderAppgrid;
			cfg.parent=parent;
			cfg.storeId=_this.storeId;
			if(cfg.key=='编辑'){
				var selId=parent.getSelId();
				if(!selId){
					return ;	
				}
				cfg.selId=selId;
			}
		Cmw.importPackage('pages/app/sys/basis/stores/OrderRuleEdit',function(module) {
			_this.cmp=module.WinEdit;
			module.WinEdit.show(cfg);
		});
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
			var _this = exports.WinEdit;
			var cfg = {
				sfn : function(formData){
//					_this.appWin.hide();
					_this.parent.reload({areaId:_this.parentCfg.treId});
				}
			};
			EventManager.frm_save(_this.appFrm,cfg);
		
		
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
