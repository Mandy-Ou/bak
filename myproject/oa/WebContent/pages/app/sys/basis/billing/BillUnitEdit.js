/**
 * 开票单位
 * @author smartplatform_auto
 * @date 2013-11-23 03:37:42
 */
define(function(require, exports) {
	exports.WinEdit = {
		parentCfg : null,
		parent : null,
		optionType : OPTION_TYPE.ADD,/* 默认为新增  */
		appFrm : null,
		appWin : null,
		attachmentFs:null, 
		uuid:null,
		setParentCfg:function(parentCfg){
			this.parentCfg=parentCfg;
			//--> 如果是Grid ，应该修改此处
			this.parent = parentCfg.parent;
			this.optionType = parentCfg.optionType || OPTION_TYPE.ADD;
		},
		createAppWindow : function(){
			this.appFrm = this.createForm();
			this.appWin = new Ext.ux.window.AbsEditWindow({width:780,getUrls:this.getUrls,appFrm : this.appFrm,isNotSetVs : true,
			optionType : this.optionType,eventMgr:{saveData:this.saveData}, refresh : this.refresh
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
				_this.attachmentFs.clearAll();
				urls[URLCFG_KEYS.ADDURLCFG] = {url : './oaBillUnit_add.action',cfg : cfg};
			}else{
				/*--- 修改URL --*/
				var selId = parent.getSelId();
				var prs={sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_10,formId:selId,atype:5};
				_this.attachmentFs.reload(prs);
				cfg = {params : {id:selId}};
				urls[URLCFG_KEYS.GETURLCFG] = {url : './oaBillUnit_get.action',cfg : cfg};
			}
			var id = this.appFrm.getValueByName("id");
			cfg = {params : {id:id}};
			/*--- 上一条 URL --*/
			urls[URLCFG_KEYS.PREURLCFG] = {url : './oaBillUnit_prev.action',cfg :cfg};
			/*--- 下一条 URL --*/
			urls[URLCFG_KEYS.NEXTURLCFG] = {url : './oaBillUnit_next.action',cfg :cfg};
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
		 * 创建Form表单
		 */
		createForm : function(){
			var _this=this;
			var hid_id=FormUtil.getHidField({
				fieldLabel:"ID",
				name:"id"
			})
			var cpo_storeName = FormUtil.getMyComboxTree({
			    fieldLabel: '门店分区',
			    name: 'areaId',
			    isAll : true,
			     width: 125,
			     params:{restypeId:100002},
			    "allowBlank": false,
			     url : './sysTree_tdsList.action'
			});
			var tree = cpo_storeName.tree;
			tree.on('click',function(node){
				var code=_this.appFrm.getValueByName("code");
				if(node.id.indexOf("G")!=-1){
					node.expand(true);
//					Ext.Msg.alert("提示","请选择下一层节点");
					return ;
				}else{
					var pnode=node.parentNode;
					txt_areaName.setValue(pnode.text);
//					_this.appFrm.setFieldValues({code:code,areaName:pnode.text});
				}
			});
			cpo_storeName.menu.on("hide",function(){
				if(tree.getSelId().indexOf("G")!=-1&&cpo_storeName.getValue()){
					Ext.Msg.alert("提示","您选择的是分类，分区为下一层节点");
					return ;
				}
			})
//			var txt_areaCode = FormUtil.getTxtField({
//			    fieldLabel: '分区编号',
//			    "allowBlank": false,
//			    name: 'areaCode',
//			    disabled : true,
//			    "width": "125"
//			});
			var txt_areaName = FormUtil.getTxtField({
			    fieldLabel: '门店分类',
			    "allowBlank": false,
			    name: 'areaName',
			    disabled : true,
			    "width": "125"
			});
			var txt_code = FormUtil.getTxtField({
			    fieldLabel: '单位编号',
			    name: 'code',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "20"
			});
			var txt_name = FormUtil.getTxtField({
			    fieldLabel: '单位名称',
			    name: 'name',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "50"
			});
			
			var cbo_custType = FormUtil.getLCboField({
			    fieldLabel: '客户类别',
			    name: 'custType',
			    "width": 125,
			    "allowBlank": false,
			    "maxLength": "30",
			    "data": [["1", "商超"], ["2", "独立店"], ["3", "加盟店"]]
			});
			
			var cbo_settleType = FormUtil.getLCboField({
			    fieldLabel: '结算方式',
			    name: 'settleType',
			    "width": 125,
			    "maxLength": "30",
			    "data": [["1", "现金"], ["2", "支票"]]
			});
			
			var txt_settleCycle = FormUtil.getDoubleField({
			    fieldLabel: '结帐周期',
			    name: 'settleCycle',
			    "width": "125"
			});
			
			var cbo_saleType = FormUtil.getLCboField({
			    fieldLabel: '销售方式',
			    name: 'saleType',
			    "width": 125,
			    "maxLength": 50,
			    "data": [["1", "联营"], ["2", "购销"]]
			});
			var cbo_plevelId = FormUtil.getRCboField({
			    fieldLabel: '价格等级',
			    name: 'plevelId',
			    "width": 125,
			    "maxLength": 50,
			    register : REGISTER.GvlistDatas, restypeId : '100003'
			});
			var txt_comeDays = FormUtil.getIntegerField({
			    fieldLabel: '预回款日',
			    name: 'comeDays',
			    "width": "100",
			    unitText:"号"
			});
			
			var txt_cman1 = FormUtil.getTxtField({
			    fieldLabel: '联系人1',
			    name: 'cman1',
			    "width": "125"
			});
			
			var txt_ctel1 = FormUtil.getTxtField({
			    fieldLabel: '联系电话1',
			    name: 'ctel1',
			    "width": "125"
			});
			
			var txt_cman2 = FormUtil.getTxtField({
			    fieldLabel: '联系人2',
			    name: 'cman2',
			    "width": "125"
			});
			
			var txt_ctel2 = FormUtil.getTxtField({
			    fieldLabel: '联系电话2',
			    name: 'ctel2',
			    "width": "125"
			});
			
			var txt_contOrg = FormUtil.getTxtField({
			    fieldLabel: '合同单位',
			    name: 'contOrg',
			    "width": "125"
			});
			
			var txt_taxnum = FormUtil.getTxtField({
			    fieldLabel: '国税编号',
			    name: 'taxnum',
			    "width": "125"
			});
			
			var txt_address = FormUtil.getTxtField({
			    fieldLabel: '联系地址',
			    name: 'address',
			    "width": "200"
			});
			
			var txt_email = FormUtil.getTxtField({
			    fieldLabel: '邮箱',
			    name: 'email',
			    "width": "125"
			});
			
			var txt_userName = FormUtil.getTxtField({
			    fieldLabel: '用户名',
			    name: 'userName',
			    "width": "125"
			});
			
			var txt_pwd = FormUtil.getTxtField({
			    fieldLabel: '密码',
			    name: 'pwd',
			    "width": "125"
			});
			/*所属业务员*/
			var barItems = [{type:'label',text:'姓名'},{type:'txt',name:'empName'}];
			var structure = [
				{header: '姓名', name: 'empName',width:100},{header: '性别',name: 'sex',width:60,renderer: function(val) {
					return Render_dataSource.sexRender(val);
				}},{header: '手机', name: 'phone',width:80},{header: '联系电话', name: 'tel',width:90}];
					
			var txt_bussMan = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '所属业务员',
			    name: 'bussMan',
			    barItems : barItems,
			    structure:structure,
			    dispCmn:'empName',
			    isAll:true,
			    url : './sysUser_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			/*所属部门*/
			var barDep = [{type:'label',text:'名称'},{type:'txt',name:'name'}];
			var structur = [
				{header: '名称', name: 'name',width:100},{header: '部门负责人',name: 'mperson',width:60,renderer: function(val) {
					return val;
				}},{header: '联系人', name: 'contactor',width:80},{header: '联系电话', name: 'tel',width:90}];
					
			var txt_department = new Ext.ux.grid.AppComBoxGrid({
			    fieldLabel: '所属部门',
			    name: 'department',
			    barItems : barDep,
			    structure:structur,
			    dispCmn:'name',
			    isAll:true,
			    url : './sysDepartment_list.action',
			    needPage : true,
			    isLoad : true,
			    keyField : 'id',
			    width: 125
			});
			
			var txt_tded = FormUtil.getDoubleField({
			    fieldLabel: '正价扣点',
			    name: 'tded',
			    unitText:"%",
			    "width": "125"
			});
			var txt_sded = FormUtil.getDoubleField({
			    fieldLabel: '特价扣点',
			    name: 'sded',
			    unitText:"%",
			    "width": "125"
			});
			
			var txt_credit = FormUtil.getMoneyField({
			    fieldLabel: '信用额度',
			    name: 'credit',
			    unitText:"元",
			    "width": "125"
			});
			
			var rad_control = FormUtil.getRadioGroup({
				fieldLabel : '信用管制',
				name:"control",
				items : [{boxLabel : '是', name:'control',inputValue:1, checked: true},
					{boxLabel : '否', name:'control',inputValue:2}]});
			
			var rad_orderShow = FormUtil.getRadioGroup({
			fieldLabel : '订单中显示',
			name:"orderShow",
			items : [{boxLabel : '是', name:'orderShow',inputValue:1, checked: true},
					{boxLabel : '否', name:'orderShow',inputValue:2}]});
					
			var txt_btel = FormUtil.getTxtField({
			    fieldLabel: '业务员电话',
			    name: 'btel',
			    "width": "125"
			});
			var txt_bdesc = FormUtil.getTxtField({
			    fieldLabel: '开票事项',
			    name: 'bdesc',
			    "width": "125"
			});
			var txt_website = FormUtil.getTxtField({
			    fieldLabel: '网站',
			    name: 'website',
			    "width": "125"
			});
			var txt_baddress = FormUtil.getTxtField({
			    fieldLabel: '发票地址',
			    name: 'baddress'
			});
			
			var txt_startDate = FormUtil.getDateField({name:'startDate',width:120});
			var txt_endDate = FormUtil.getDateField({name:'endDate',width:120});
			var comp_estartDate = FormUtil.getMyCompositeField({
				itemNames : 'startDate,endDate',
				sigins : null,
				 fieldLabel: '往来日期',width:310,sigins:null,
				 name:'comp_estartDate',
				 items : [txt_startDate,{xtype:'displayfield',value:'至'},txt_endDate]
			});
			var txa_remark = FormUtil.getTxtField({
			    fieldLabel: '备注',
			    width:600,
			    height:100,
			    name: 'remark'
			});
			
			this.attachmentFs = this.createAttachMentFs(this);
			var layout_fields = [{
			    cmns: FormUtil.CMN_TWO,
			    fields: [cpo_storeName, txt_areaName]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [txt_code, txt_name]
			},
			{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbo_custType, cbo_saleType, txt_cman1, txt_ctel1, txt_cman2, txt_ctel2]
			},
			{
			    cmns: FormUtil.CMN_TWO,
			    fields: [ txt_email,txt_address,txt_contOrg,txt_website ]
			},
			{
			    cmns: FormUtil.CMN_THREE,
			    fields: [txt_department,txt_bussMan,txt_btel,txt_userName, txt_pwd,rad_orderShow,txt_bdesc,txt_baddress]
			},
			this.attachmentFs
			];
			
			var finance_layout_fields = [{
			    cmns: FormUtil.CMN_THREE,
			    fields: [cbo_plevelId,cbo_settleType,txt_settleCycle, txt_tded, txt_sded,txt_comeDays,txt_taxnum,txt_credit,rad_control]
			},comp_estartDate,txa_remark,hid_id]
			var baseItems = FormUtil.getLayoutItems(layout_fields);
			var financeItems = FormUtil.getLayoutItems(finance_layout_fields);
			var tabPanel = new Ext.TabPanel({border:false,height:360,frame:true,activeItem:0,items:[
			{title:'客户档案基本信息',layout:'form',frame:true,items:baseItems},{title:'财务信息',layout:'form',frame:true,items:financeItems}	
			]});
			var frm_cfg = {
			    url: './oaBillUnit_save.action',
			    items : [tabPanel]
			};
			var appform_1 = FormUtil.createFrm(frm_cfg);
			return appform_1;
		},
		/**
		 * 创建附件FieldSet 对象
		 * @return {}
		 */
		createAttachMentFs : function(_this){
			/* ----- 参数说明： isLoad 是否实例化后马上加载数据 
			 * dir : 附件上传后存放的目录， mortgage_path 定义在 resource.properties 资源文件中
			 * isSave : 附件上传后，是否保存到 ts_attachment 表中。 true : 保存
			 * params : {sysId:系统ID,formType:业务类型,参见 公共平台数据字典中 ts_attachment 中的说明,formId:业务单ID，如果有多个用","号分隔}
			 *    当 isLoad = false 时， params 可在 reload 方法中提供
			 *  ---- */
			var _this=exports.WinEdit;
			_this.uuid=Cmw.getUuid();
//			if(_this.parentCfg.selId)_this.uuid=_this.parentCfg.selId;
			var params={sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_10,formId:_this.uuid,atype:5};
			var attachMentFs = new  Ext.ux.AppAttachmentFs({title:'附件列表',isLoad:true,dir : 'mortgage_path',params:params,isSave:true,isNotDisenbaled:true});
			return attachMentFs;
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
				tb:_this.appWin.apptbar,
				sfn : function(formData){
					var params={sysId:-1,formType:ATTACHMENT_FORMTYPE.FType_10,formId:formData.id,atype:5};
					_this.attachmentFs.updateTempFormId(params,true);
					_this.parent.reload();
					_this.appWin.hide();
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
