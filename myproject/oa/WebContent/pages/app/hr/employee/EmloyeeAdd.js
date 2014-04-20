/**
 * 员工信息添加
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
		creForm2:null,
		creForm3:null,
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
			var _this=this;
			this.appFrm =this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:780,getUrls:this.getUrls,appFrm : this.appFrm,
			optionType : this.optionType, refresh : this.refresh
			,eventMgr:{saveData:_this.saveData}
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
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './hrEmployee_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './hrEmployee_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './hrEmployee_next.action',cfg :cfg};
			return urls;
		},
		/**
		 * 当数据保存成功后，执行刷新方法
		 * [如果父页面存在，则调用父页面的刷新方法]
		 */
		refresh : function(data){
			var _this = exports.WinEdit;
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
				html:'<div><img id="titleImg" style="width:100px; height:100px" src="images/avatar/1125853.gif" /></div>'
			});
			p.on("afterrender",function(){
				var titleImg=Ext.get("titleImg");
				//面板渲染后，给图片绑定点击事件
				titleImg.on("click",function(){
					_this.showUploadWin(titleImg);
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
					_this.appFrm.findFieldByName("photo").setValue(filePath);
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
			
			var int_iscreate = FormUtil.getRadioGroup({
			    fieldLabel: '创建登录帐号',
			    name: 'iscreate',
			    "width": 125,
			    "items": [{
			        "boxLabel": "是",
			        "name": "iscreate",
			         "checked":"true",
			        "inputValue": "1"
			    },{
			        "boxLabel": "否",
			        "name": "iscreate",
			        "inputValue": "2"
			    }],
			    "maxLength": 10
			});
			
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
			    fields: [txt_bank, txt_account, txt_accountName, int_tryDays, bdat_entryDate, bdat_leaveDate, txt_econtactor, txt_ctel, int_status,rad_empType,dob_wageDay, int_iscreate]
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
			var rad_isBuySocial = FormUtil.getRadioGroup({
			    fieldLabel: '是否购买社保',
			    name: 'isBuySocial ',
			    "width": 125,
			    "maxLength": 50,
			    "items": [{
			        "boxLabel": "是",
			         "checked":"true",
			        "name": "isBuySocial ",
			        "inputValue": "1"
			    },
			    {
			        "boxLabel": "否",
			        "name": "isBuySocial ",
			        "inputValue": "2"
			    }]
			});
			
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
			
			var txt_domicileType = FormUtil.getTxtField({
			    fieldLabel: '户籍类型',
			    name: 'domicileType',
			    "width": "125"
			});
			
			var txt_personalMoney = FormUtil.getTxtField({
			    fieldLabel: '个人金额',
			    name: 'personalMoney',
			    "width": "125"
			});
			
			var txt_unitMoney = FormUtil.getTxtField({
			    fieldLabel: '单位金额',
			    name: 'unitMoney',
			    "width": "125"
			});
			
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
			    fields: [rad_isBuySocial, txt_socialNum, txt_socialOrg, txt_domicileType, txt_personalMoney, txt_unitMoney, bdat_startDate, bdat_endDate]
			}];
			var layoutItems = FormUtil.getLayoutItems(layout_fields);
			var frm_cfg = {
				layout:'form',
			    title: '社保信息',
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
			
			/*提成方案*/
			var barDeduct = [{type:'label',text:'职位'},{type:'txt',name:'postId'}];
			var strDeduct = [
				{header: '职位', name: 'postId',width:60},{header: '提成公式',
				name: 'formulaId',width:200}];
			var txt_deductPlan = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '提成方案',
			    name: 'deductPlan',
			    barItems : barDeduct,
			    structure:strDeduct,
			    dispCmn:'postId',
			    isAll:true,
			    url : './oaDeductPlan_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			var txt_mgrId = FormUtil.getTxtField({
			    fieldLabel: '区域经理',
			    name: 'mgrId',
			    "width": 125
			});
			
			var txt_areaId = FormUtil.getTxtField({
			    fieldLabel: '门店区域',
			    name: 'areaId',
			    "width": 125
			});
			
			var layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_store, txt_Indept, txt_postId,txt_divType, txt_postType, txt_useType]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [rad_isCommission, txt_deductPlan]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [ txt_mgrId, txt_areaId]
			}];
			var layoutItems = FormUtil.getLayoutItems(layout_fields);
			var frm_cfg = {
			     title: '职位信息',
			     layout:'form',
			    items:[layoutItems]
			};
			var p=new Ext.Panel(frm_cfg)
			return p;
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
				_this.globalMgr.activeKey = winkey;
				parentCfg.parent = _this.getGrid2();
				var winModule=null;
				var subnoid=null;
				if(winkey!="添加提成方案"){
					subnoid=parentCfg.parent.getSelId().substring(0,1);
					if(winkey=='查看员工详情')winModule="EmployeeDetail";
				}else{
						winModule="DeductPlanEdit";
				}	
				if(subnoid!=null)
					winkey=winkey+subnoid;
				if(_this.appCmpts[winkey]){
					_this.appCmpts[winkey].show(parentCfg);
				}else{
					Cmw.importPackage('pages/app/sys/stores/'+winModule,function(module) {
					 	_this.appCmpts[winkey] = module.WinEdit;
					 	_this.appCmpts[winkey].show(parentCfg);
			  		});
				}
			}
		}
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
			var cfg = {
				sfn : function(formData){
					if(_this.parent)
						_this.parent.reload();
					_this.appWin.hide();
				}
			}
			EventManager.frm_save(_this.appFrm,cfg);
		},
		/**
		 *  重置数据 
		 */
		resetData : function(){
		}
	};
});
