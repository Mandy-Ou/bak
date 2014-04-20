
/**
 * 下拉框控件管理对象
 * @type 
 */
var ComboxControl = {
	/**
	 * 获取帐号下拉框Grid
	 * @param cfg 下拉框Grid 配置参数 必须参数：fieldLabel,name; 可选参数：params,callback
	 *  示例: var cbog_payAccount = ComboxControl.getAccountCboGrid({fieldLabel:'放款银行',name:'accountId'});
	 */
	getAccountCboGrid : function(cfg){
		if(!cfg.fieldLabel){
			cfg.fieldLabel = '银行名称';
		}
		if(!cfg.name){
			cfg.name = "accountId";
			ExtUtil.error({msg:'参数必须传入 name 属性值,否则系统将以 accountId 代替!'});
		}
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = FormUtil.REQUIREDHTML + cfg.fieldLabel;
		/*银行名称*/
		var barItems = [{type:'label',text:'银行名称'},{type:'txt',name:'bankName'}];
		var structure = [{header: '科目编号',name: 'code',width: 125},
			{header: '银行名称',name: 'bankName',width: 125},
			{header: '银行帐号',name: 'account',width: 155},
			{header: '可用金额',name: 'uamount',width: 155,  
				renderer: function(val){
		    	val = Cmw.getThousandths(val)
		    	return val
		    }},
			{header: '账户类型',name: 'atype',width: 155,hidden:true},
			{header: '放款账户',name: 'isPay',width: 155,hidden:true},
			{header: '收款账户',name: 'isIncome',width: 155,hidden:true}
			];
		var gridCfg = {
		    fieldLabel: cfg.fieldLabel,
		    name: cfg.name,
		    barItems : barItems,
		    structure:structure,
		    dispCmn:'bankName',
		    isAll:true,
		    url : './sysAccount_list.action',
		    needPage : true,
		    isLoad : true,
		    gridWidth : 400,
		    keyField : 'id',
		    width : 125
		};
		if(cfg.hasOwnProperty("isAll") && !cfg.allowBlank){
			gridCfg.isAll = cfg.isAll;
		}
		if(cfg.callback){
			gridCfg.callback = cfg.callback;
		}
		if(cfg.params){
			gridCfg.params = cfg.params;
		}
		if(cfg.width){
			gridCfg.width = cfg.width;
		}
		
		var cboGrid = new Ext.ux.grid.AppComBoxGrid(gridCfg);
		return cboGrid;
	},
	/**
	 * 获取用户下拉框Grid
	 * @param cfg 下拉框Grid 配置参数 必须参数：fieldLabel,name; 可选参数：params,callback
	 *  示例: var cbog_users = ComboxControl.getUserCboGrid({fieldLabel:'用户列表',name:'userIds'});
	 */
	getUserCboGrid : function(cfg){
		if(!cfg.fieldLabel){
			cfg.fieldLabel = '姓名';
		}
		if(!cfg.name){
			cfg.name = "userIds";
			ExtUtil.error({msg:'参数必须传入 name 属性值,否则系统将以 inempId 代替!'});
		}
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = FormUtil.REQUIREDHTML + cfg.fieldLabel;
		/*银行名称*/
		var barItems = [{type:'label',text:'员工姓名'},{type:'txt',name:'empName'}];
		var structure = [{header: '姓名',name: 'empName',width: 100},
			{header: '用户账号',name: 'userName',width: 100},
			{header: '性别',name: 'sex',width: 65,renderer:Render_dataSource.sexRender}];
		var gridCfg = {
		    fieldLabel: cfg.fieldLabel,
		    name: cfg.name,
		    barItems : barItems,
		    structure:structure,
		    dispCmn:'userName',
		    isAll:true,
		    url : './sysUser_list.action?isenabled=1',
		    needPage : true,
		    isLoad : true,
		    gridWidth : 400,
		    keyField : 'userId',
		    width : 125
		};
		Ext.apply(gridCfg,cfg);
		var cboGrid = new Ext.ux.grid.AppComBoxGrid(gridCfg);
		return cboGrid;
	},/**
	 * 获取角色下拉框Grid
	 * @param cfg 下拉框Grid 配置参数 必须参数：fieldLabel,name; 可选参数：params,callback
	 *  示例: var cbog_roles = ComboxControl.getRoleCboGrid({fieldLabel:'角色列表',name:'roleIds'});
	 */
	getRoleCboGrid : function(cfg){
		if(!cfg.fieldLabel){
			cfg.fieldLabel = '姓名';
		}
		if(!cfg.name){
			cfg.name = "userIds";
			ExtUtil.error({msg:'参数必须传入 name 属性值,否则系统将以 inempId 代替!'});
		}
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = FormUtil.REQUIREDHTML + cfg.fieldLabel;
		/*角色名称*/
		var barItems = [{type:'label',text:'角色名称'},{type:'txt',name:'name'}];
		var structure = [{header: '角色编号',name: 'code',width: 100},
			{header: '角色名称',name: 'name',width: 100}];
		var gridCfg = {
		    fieldLabel: cfg.fieldLabel,
		    name: cfg.name,
		    barItems : barItems,
		    structure:structure,
		    dispCmn:'name',
		    isAll:true,
		    url : './sysRole_list.action?isenabled=1',
		    needPage : true,
		    isLoad : true,
		    gridWidth : 400,
		    keyField : 'id',
		    width : 125
		};
		Ext.apply(gridCfg,cfg);
		var cboGrid = new Ext.ux.grid.AppComBoxGrid(gridCfg);
		return cboGrid;
	},
	/**
	 * 科目Grid
	 * var cbog_subject =  ComboxControl.getSubjectCboGrid({fieldLabel:'科目',name:'accountId'});
	 * @param {} cfg
	 * @return {}
	 */
	getSubjectCboGrid : function(cfg){
		if(!cfg.fieldLabel){
			cfg.fieldLabel = '科目';
		}
		if(!cfg.name){
			cfg.name = "accountId";
		}
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = FormUtil.REQUIREDHTML + cfg.fieldLabel;
		var barItems = [{type:'label',text:'科目名称'},{type:'txt',name:'name'}];
		var structure = [{header:'科目号' ,name:'code' ,width:75},
						{header:'科目名称' ,name:'name' ,width:100},
						{header:'借贷方向' ,name:'dc' ,width:70,renderer : Fs_Render_dataSource.subject_dcRender},
						{header:'币别' ,name:'currency' ,width:65},
						{header:'科目ID' ,name:'refId' ,width:85}];
		var gridCfg = {
		    fieldLabel:cfg.fieldLabel,
		    name: cfg.name,
		    barItems : barItems,
		    structure:structure,
		    dispCmn:'name',
		    url : './fsSubject_list.action',
		    needPage : true,
		    isAll:true,
		    isLoad : true,
		    gridWidth : 400,
		    keyField : 'refId',
		    width : 125
		};
		if(cfg.hasOwnProperty("isAll") && !cfg.allowBlank){
			gridCfg.isAll = cfg.isAll;
		}
		if(cfg.callback){
			gridCfg.callback = cfg.callback;
		}
		if(cfg.params){
			gridCfg.params = cfg.params;
		}
		if(cfg.width){
			gridCfg.width = cfg.width;
		}
		var cboGrid = new Ext.ux.grid.AppComBoxGrid(gridCfg);
		return cboGrid;
	},
	/**
	 * 财务系统 --> 凭证模板配置 --> 核算项类别
	 * 获取帐号下拉框Grid
	 * @param cfg 下拉框Grid 配置参数 必须参数：fieldLabel,name; 可选参数：params,callback
	 *  示例: var cbog_payAccount = ComboxControl.getItemClassCboGrid({fieldLabel:'核算项',name:'itemClassId'});
	 */
	getItemClassCboGrid : function(cfg){
		if(!cfg.fieldLabel){
			cfg.fieldLabel = '核算项';
		}
		if(!cfg.name){
			cfg.name = "itemClassId";
		}
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = FormUtil.REQUIREDHTML + cfg.fieldLabel;
		var barItems = [{type:'label',text:'核算项名称'},{type:'txt',name:'name'}];
		var structure = [{header: '核算项代码',name: 'code',width: 125},
			{header: '核算项名称',name: 'name',width: 155}];
		var gridCfg = {
		    fieldLabel:cfg.fieldLabel,
		    name: cfg.name,
		    barItems : barItems,
		    structure:structure,
		    dispCmn:'name',
		    isAll:true,
		    url : './fsItemClass_list.action',
		    needPage : true,
		    isLoad : true,
		    gridWidth : 400,
		    keyField : 'refId',
		    width : 125
		};
		if(cfg.hasOwnProperty("isAll") && !cfg.allowBlank){
			gridCfg.isAll = cfg.isAll;
		}
		if(cfg.callback){
			gridCfg.callback = cfg.callback;
		}
		if(cfg.params){
			gridCfg.params = cfg.params;
		}
		if(cfg.width){
			gridCfg.width = cfg.width;
		}
		var cboGrid = new Ext.ux.grid.AppComBoxGrid(gridCfg);
		return cboGrid;
	},
	/**
	 * 自定义字段Grid
	 * @param {} cfg
	 * @return {}
	 */
	getFinCustFieldsCboGrid : function(cfg){
		if(!cfg.fieldLabel){
			cfg.fieldLabel = '核算字段';
		}
		if(!cfg.name){
			cfg.name = "fieldIds";
		}
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = FormUtil.REQUIREDHTML + cfg.fieldLabel;
		var barItems = [{type:'label',text:'字段说明'},{type:'txt',name:'name'}];
		var structure = [{header: '字段说明',name: 'name',width: 120},
			{header: '字段名称',name: 'field',width: 100},
			{header: '表列名',name: 'cmn',width: 100},
			{header: '数据类型',name: 'dataType',width: 80}];
		var gridCfg = {
		    fieldLabel:cfg.fieldLabel,
		    name: cfg.name,
		    barItems : barItems,
		    structure:structure,
		    dispCmn:'name',
		    url : './fsFinCustField_list.action',
		    needPage : false,
		    isAll:true,
		    isLoad : false,
		    gridWidth : 400,
		    keyField : 'id',
		    width : 125,
		    isCheck : true
		};
		if(cfg.hasOwnProperty("isAll") && !cfg.allowBlank){
			gridCfg.isAll = cfg.isAll;
		}
		if(cfg.callback){
			gridCfg.callback = cfg.callback;
		}
		if(cfg.params){
			gridCfg.params = cfg.params;
		}
		if(cfg.width){
			gridCfg.width = cfg.width;
		}
		var cboGrid = new Ext.ux.grid.AppComBoxGrid(gridCfg);
		return cboGrid;
	},
	/*===============  OA系统加入 CODE START  ================*/
	/**
	 * 获取员工下拉框Grid
	 * @param cfg 下拉框Grid 配置参数 必须参数：fieldLabel,name; 可选参数：params,callback
	 *  示例: var cbog_emps = ComboxControl.getEmpCboGrid({fieldLabel:'放款银行',name:'inempId'});
	 */
	getEmpCboGrid : function(cfg){
		if(!cfg.fieldLabel){
			cfg.fieldLabel = '员工姓名';
		}
		if(!cfg.name){
			cfg.name = "inempId";
			ExtUtil.error({msg:'参数必须传入 name 属性值,否则系统将以 inempId 代替!'});
		}
		if(cfg.hasOwnProperty("allowBlank") && !cfg.allowBlank) cfg.fieldLabel = FormUtil.REQUIREDHTML + cfg.fieldLabel;
		/*银行名称*/
		var barItems = [{type:'label',text:'员工姓名'},{type:'txt',name:'name'}];
		var structure = [{header: '员工编号',name: 'code',width: 100},
			{header: '员工姓名',name: 'name',width: 100},
			{header: '性别',name: 'sex',width: 65, renderer : function(val){return OaRender_dataSource.sexRender(val);}},
			{header: '职位',name: 'postName',width: 65},
			{header: '门店名称',name: 'storeName',width: 100},
			{header: '部门名称',name: 'indeptName',width: 100},
			{header: '手机号码',name: 'phone',width: 85},
			{header: '联系电话',name: 'ctel',width: 85},
			{header: '部门名称',name: 'indeptName',width: 85},
			{header: '职位ID',name: 'postId',hidden : true},
			{header: '所属门店ID',name: 'storeId',hidden : true},
			{header: '所属部门ID',name: 'indeptId',hidden : true}];
		
		var gridCfg = {
		    fieldLabel: cfg.fieldLabel,
		    name: cfg.name,
		    barItems : barItems,
		    structure:structure,
		    dispCmn:'name',
		    isAll:true,
		    url : './dgDialogbox_emplist.action',
		    needPage : true,
		    isLoad : true,
		    gridWidth : 500,
		    keyField : 'id',
		    width : 125
		};
		if(cfg.hasOwnProperty("isAll") && !cfg.allowBlank){
			gridCfg.isAll = cfg.isAll;
		}
		if(cfg.callback){
			gridCfg.callback = cfg.callback;
		}
		if(cfg.params){
			gridCfg.params = cfg.params;
		}
		if(cfg.width){
			gridCfg.width = cfg.width;
		}
		var cboGrid = new Ext.ux.grid.AppComBoxGrid(gridCfg);
		return cboGrid;
	}/*===============  OA系统加入 CODE END  ================*/
}

/**
 * 个性化表单管理类
 * 
 * @type 
 */
var FormDiyManager = {
	saveUrl : './sysFieldVal_save.action',
	getUrl : './sysFieldVal_list.action',
	fieldPrefix : 'FORMDIY_',
	colSpan_Sigins : '#COL_SPAN#',
	/**
	 * 为表单自定义字段赋值
	 * @param {} formPanel
	 * @param {} callback
	 */
	setFormDiyFieldValues : function(formPanel,callback){
		var formDiyCfg =  formPanel.formDiyCfg;
		if(!formDiyCfg) return;
		var	formIdName =  formDiyCfg.formIdName;
		var fieldNames = formDiyCfg.fieldNames;
		var formdiyId = formDiyCfg.formdiyId;
		var formId = formPanel.getValueByName(formIdName);
		if(!formId) return;
//		if(!formdiyId){
//			ExtUtil.alert({msg:'formdiyId 的值为空，无法加载自定义字段数据!'});
//			return;
//		}
		var _this = this;
		var params = {formdiyId:formdiyId,formId:formId};
		EventManager.get(this.getUrl,{params:params,sfn:function(json_data){
			if(!json_data) return;
			var flag = true;
			var data = {};
			for(var key in json_data){
				var fieldName = _this.fieldPrefix+key;
				var val = json_data[key];
				data[fieldName] = val;
			}
			formPanel.setJsonVals(data);
			if(callback) callback(json_data);
		}});
	},
	/**
	 * 添加自定义字段和设置字段属性值
	 * @param {} formPanel
	 */
	addFormFieldCfg : function(formPanel){
		var formDiyCfg =  formPanel.formDiyCfg;
		if(!formDiyCfg) return;
		var sysId = formDiyCfg.sysId;
		var formdiyCode = formDiyCfg.formdiyCode;
		var	formIdName =  formDiyCfg.formIdName;
		var container =  formDiyCfg.container;
		var key = sysId+"_"+formdiyCode;
		var diyData = FormdiyDatas[key];
		if(!diyData) return;
		var propsFields = diyData.propsFields;
		this.setFieldProps(formPanel,propsFields);
		var customFields = diyData.customFields;
		var formdiyId = diyData.id;
		var fieldCfg = this.addCustFields(container, customFields, formdiyId);
		if(!fieldCfg) return; 
		formDiyCfg.formdiyId = formdiyId;
		var fieldNames =fieldCfg.fieldNames;
		if(fieldNames){
			formDiyCfg.fieldNames = fieldNames;
		}
		var fields =fieldCfg.fields;
		this.putFieldsToBasicForm(formPanel, fields);
	},
	/**
	 * 设置字段属性值
	 * @param {} formPanel
	 * @param {} propsFields
	 */
	setFieldProps : function(formPanel,propsFields){
		if(null == propsFields || propsFields.length == 0) return;
		//var fields = formPanel.findFieldsByName();
		for(var i=0,count=propsFields.length; i<count; i++){
			var prop = propsFields[i];
			var name = prop.name;
			if(!name) continue;
			var field = formPanel.findFieldByName(name);
			if(!field) continue;
			var fieldLabel = this.getFieldLabel(prop);
			var isRequired = prop.isRequired;
			switch(parseInt(isRequired)){
				case 0 : {
					var oldLabel = field['fieldLabel'];
					if(oldLabel && oldLabel.indexOf('*') != -1){
						fieldLabel = FormUtil.REQUIREDHTML + fieldLabel;
					}
					break;
				}case 1 : {
					break;
				}case 2 : {
					fieldLabel = FormUtil.REQUIREDHTML + fieldLabel;
					break;
				}
			}
			if(isRequired == 1){
				fieldLabel = FormUtil.REQUIREDHTML + fieldLabel;
				this.setAllowBlank(field);
			}
			this.setFieldLabel(field,fieldLabel);
		}
	},
	setFieldLabel : function(field,text) {
//	    if (field.rendered) {
//	      field.el.down('.x-form-label').update(text);
//	    }
//	    field.fieldLabel = text;
	   if (field.rendered) {
         field.el.up('.x-form-item', 10, true).child('.x-form-item-label').update(text);
       } else {
          field.fieldLabel = text;
       }
  	},
	/**
	 * 添加自定义字段
	 * @param {} container 自定义字段存放容器
	 * @param {} customFields 自定义字段
	 * @param {} formdiyId	自定义表单DIYID
	 */
	addCustFields : function(container,customFields,formdiyId){
		var fieldNames = null;
		var fields = null;
		if(null == customFields || customFields.length == 0){
			container.isLoadData = false;
			return null;
		}else{
			var itemsData = this.getGroups(customFields);
			fieldNames = itemsData.fieldNames;
			fields = itemsData.fields;
			var items = itemsData.items;
			if(!items || items.length ==0){
				container.isLoadData = false;
				return null;
			}
			items = FormUtil.getLayoutItems(items);
			var subContainer = new Ext.Container({layout:'form',items:items});
			container.add(subContainer);
		}
		return {fieldNames:fieldNames,fields:fields};
	},
	/**
	 * 将动态添加的字段放入 BasicForm 表单中，以便使验证等方法起作用
	 * @param {} formPanel	表单对象
	 * @param {} fields	字段元素数组
	 */
	putFieldsToBasicForm : function(formPanel,fields){
		if(!fields || fields.length == 0) return;
		var basicForm = formPanel.getForm();
		var items = basicForm.items;
		items.addAll(fields);
	},
	/**
	 * 设置字段必填属性
	 */
	setAllowBlank : function(field){
		field.allowBlank = false;
		field.validate();
	},
	/**
	 * 保存自定义字段数据
	 */
	saveCustFieldVals : function(formPanel,formDatas){
		var formDiyCfg =  formPanel.formDiyCfg;
		if(!formDiyCfg) return;
		var formIdName = formDiyCfg.formIdName;
		if(!formIdName){
			ExtUtil.alert({msg:'当有自定义字段时， formDiyCfg 属性中的  formIdName 属性值 不能为空！'});
			return;
		}
		var formdiyId = formDiyCfg.formdiyId;
		if(!formdiyId) return;
		if(!formDiyCfg.fieldNames) return;
		var namesArr = formDiyCfg.fieldNames.split(",");
		if(null == namesArr || namesArr.length == 0) return;
		var data = {};
		for(var i=0,count=namesArr.length; i<count; i++){
			var name = namesArr[i];
			var fieldName = this.fieldPrefix+name;
			var val = formDatas[fieldName];
			if(!val && val != 0) continue;
			data[name] = val; 
		}
		
		var formId = formDatas[formIdName];
		var custFieldData = {formdiyId:formdiyId,formId:formId,fieldVals:Ext.encode(data)};
		EventManager.get(this.saveUrl,{params:custFieldData,sfn:function(json_data){
		},ffn:function(json_data){
			var msg = json_data.msg;
			if(msg) ExtUtil.alert({msg:msg});
		}});		
	},
	getGroups : function(customFields){
		var rowNum = 1;
		var rowGp = [];
		var fieldNames = [];
		var fields = [];
		for(var i=0,count=customFields.length; i<count; i++){
			var fieldCfg = customFields[i];
			var name = fieldCfg.name;
			fieldNames[i] = name;
			var row = fieldCfg.row;
			var index = rowNum-1;
			var cells = this.getCells(rowGp,index);
			if(null == cells || cells.length == 0){
				rowGp[index] = [];
				cells = rowGp[index];
			}
			if(rowNum != parseInt(row)){
				rowNum=row;
				cells = this.getCells(rowGp,rowGp.length);
			}
			
			var field = this.getField(fieldCfg);
			fields[fields.length] = field;
			cells[cells.length] = field;
		}
		var items = this.getLayOutFields(rowGp);
		return {fieldNames:fieldNames.join(),fields:fields,items:items};
	},
	getCells : function(rowGp,index){
		var cells = rowGp[index];
		if(null == cells || cells.length == 0){
			rowGp[index] = [];
			cells = rowGp[index];
		}
		return cells;
	},
	getLayOutFields : function(rowGp){
		var cmns = 0;
		var items = [];
		var fields = [];
		var count = rowGp.length;
		var lastAdd = count<=1;
		for(var i=0; i<count; i++){
			var row = rowGp[i];
			var len = row.length;
			if(!fields || fields.length == 0){
				fields = fields.concat(row);
				cmns = len;
			}else{
				if(cmns != len){
					items = this.addItems(items, cmns, fields);
					fields = row;
					cmns = len;
				}else{
					fields = fields.concat(row);
				}
				if(i+1 == count) lastAdd = true;
			}
		}
		if(lastAdd){
			items = this.addItems(items, cmns, fields);
		}
		return items;
	},
	addItems : function(items,cmns,fields){
		if(cmns==1){
			items = items.concat(fields);
		}else{
			var cmnsModel = this.getCmns(cmns);
			items[items.length] = {cmns:cmnsModel,fields:fields};	
		}
		return items;
	},
	getCmns : function(cmns){
		var cmnsModel = FormUtil.CMN_TWO;
		switch(cmns){
			case 2 : 
			 cmnsModel = FormUtil.CMN_TWO;
			break;
			case 3 : 
			 cmnsModel = FormUtil.CMN_THREE;
			break;
		}
		return cmnsModel;
	},
	getField : function(fieldCfg){
		var field = null;
		var name = this.fieldPrefix+fieldCfg.name;
		var cfg = {name:name};
		var fieldLabel = this.getFieldLabel(fieldCfg);
		cfg.fieldLabel = fieldLabel;
		var isRequired = fieldCfg.isRequired;
		var isValidate = false;
		if(isRequired && parseInt(isRequired) == 1){
			cfg.allowBlank=false;
			isValidate = true;
		}
		var maxlength = fieldCfg.maxlength;
		if(maxlength) cfg.maxLength=maxlength;
		var width =  fieldCfg.width;
		if(width) cfg.width=width;
		var height=  fieldCfg.height;
		if(height) cfg.height=height;
		var dval=  fieldCfg.dval;
		if(dval) cfg.value=dval;
		var datasource = fieldCfg.datasource;
		var controlType = fieldCfg.controlType;
		switch(parseInt(controlType)){
			case 1 : {/*整数输入框*/
				field = FormUtil.getIntegerField(cfg);
				break;
			}case 2 : {/*小数点输入框*/
				field = FormUtil.getDoubleField(cfg);
				break;
			}case 3 : {/*金额输入框*/
				field = FormUtil.getMoneyField(cfg);
				break;
			}case 4 : {/*日期输入框*/
				field = FormUtil.getDateField(cfg);
				break;
			}case 5 : {/*本地下拉框*/
				cfg.data = this.getLCboDataSource(datasource);
				field = FormUtil.getLCboField(cfg);
				break;
			}case 6 : {/*远程下拉框*/
				cfg.url = datasource;
				field = FormUtil.getRCboField(cfg);
				break;
			}case 7 : {/*多行文本框*/
				field = FormUtil.getTAreaField(cfg);
				break;
			}case 8 : {/*单选框*/
				cfg.items = this.getRorCItems(name,dval,datasource);
				field = FormUtil.getRadioGroup(cfg);
				break;
			}case 9 : {/*复选框*/
				cfg.items = this.getRorCItems(name,dval,datasource);				
				field = FormUtil.getCheckGroup(cfg);
				break;
			}default : {/* 0  或其它 默认为文本框*/
				field = FormUtil.getTxtField(cfg);
			}
		}
		if(isValidate){
			field.allowBlank = false;
			field.validate();
		}
		return field;
	},
	/**
	 * 获取 FieldLabel 数据
	 * @param {} fieldCfg
	 * @return {}
	 */
	getFieldLabel : function(fieldCfg){
		var fieldLabel = fieldCfg.dispName;
		var locale = AppDef.getLocale();
		var dispKey = LOCALE_NAME[locale];
		if(!dispKey || dispKey == 'name') dispKey = "dispName";
		var label = fieldCfg[dispKey];
		if(label) fieldLabel = label;
		return fieldLabel;
	},
	/**
	 * 获取本地下拉框数据源
	 * @param {} datasource 数据源
	 */
	getLCboDataSource : function(datasource){
		if(!datasource) return null;
		var data = [];
		var arr = datasource.split(",");
		for(var i=0,count=arr.length; i<count; i++){
			var val = arr[i];
			data[i] = [val,val];
		}
		return data;
	},
	/**
	 * 获取单选或复选框元素项
	 */
	getRorCItems : function(name,dval,datasource){
		if(!datasource) return null;
		var data = [];
		var arr = datasource.split(",");
		for(var i=0,count=arr.length; i<count; i++){
			var val = arr[i];
			var checked = (dval == val);
			data[i] = {boxLabel : val, name:name,inputValue:val, checked: checked};
		}
		return data;
	},
	/**
	 * 复制自定义字段值到详情面板数据对象中
	 *   FormDiyManager.copyFormDiyData2Detail(formDiyCfg,json_data,callback);
	 * @param {} formDiyCfg 表单个性化配置
	 * @param {} json_data 详情面板传回的数据
	 * @param {} callback 回调函数  
	 */
	copyFormDiyData2Detail : function(formDiyCfg,json_data,callback){
		var isReturn = false;
		if(!formDiyCfg){
			if(callback) callback(json_data);
			return
		}
		var formdiyId = formDiyCfg.formdiyId;
		if(!formdiyId){
			if(callback) callback(json_data);
			return
		}
		
		var	formIdName =  formDiyCfg.formIdName;
		var fieldNames = formDiyCfg.fieldNames;
		var dataProps = formDiyCfg.dataProps;
		var formId = json_data[formIdName];
//		var errMsg = [];
//		if(!formId){
//			errMsg[errMsg.length] = '参数 '+formIdName+'的值为空，无法加载自定义字段数据!';
//		}
//		if(errMsg && errMsg.length > 0){
//			ExtUtil.alert({msg:errMsg.join("<br/>")});
//			if(callback) callback(json_data);
//			return;
//		}
		if(!formId){
			if(callback) callback(json_data);
			return;
		}
		var _this = this;
		var params = {formdiyId:formdiyId,formId:formId};
		EventManager.get(this.getUrl,{params:params,sfn:function(data){
			if(data){
				if(dataProps && dataProps.length > 0){
					for(var i=0,count=dataProps.length; i<count; i++){
						var dataProp = dataProps[i];
						var controlType = dataProp.controlType;
						var name = dataProp.name;
						var val = data[name];
						if(controlType && parseInt(controlType) == 3 && val){/*金额输入框*/
							data[name] = Cmw.getThousandths(val);
						}
					}
				}
				Ext.applyIf(json_data, data);
			}
			if(callback) callback(json_data);
		}});
	},
	getDetailHtmlArr : function(params,htmArr){
		var sysId = params.sysId;
		var formdiyCode = params.formdiyCode;
		var maxCount = params.maxCount;
		if(!StringHandler.isNull(sysId,'参数 sysId 不能为空!')) return {htms:this.replaceCustFieldsHtmls(htmArr,"")};
		if(!StringHandler.isNull(formdiyCode,'参数 formdiyCode 不能为空!')) return {htms:this.replaceCustFieldsHtmls(htmArr,"")};
		if(!StringHandler.isNull(maxCount,'参数 maxCount 不能为空!')) return {htms:this.replaceCustFieldsHtmls(htmArr,"")};
		
		var key = sysId+"_"+formdiyCode;
		var diyData = FormdiyDatas[key];
		if(!diyData) return {htms:this.replaceCustFieldsHtmls(htmArr,"")};
		var propsFields = diyData.propsFields;
		var customFields = diyData.customFields;
		this.setPropsToHtmlArr(propsFields,htmArr);
		var htmlsCfg = this.getCustFieldsHtmls(customFields,maxCount);
		var htms = htmlsCfg.htms;
		htms = this.replaceCustFieldsHtmls(htmArr, htms);
		htmlsCfg.htms = htms;
		htmlsCfg.formdiyId = diyData.id;
		return htmlsCfg;
	},
	/**
	 * 替换HTML Arr 中的自定义字段HTML 替换标记 FORMDIY_DETAIL_KEY 此值参见 : constant.js
	 * @param {} htmArr
	 * @param {} cfhtmls
	 * @return {}
	 */
	replaceCustFieldsHtmls : function(htmArr,cfhtmls){
		if(!cfhtmls) cfhtmls = "  ";
		for(var i=0,count=htmArr.length; i<count; i++){
			var html = htmArr[i];
			if(html && html == FORMDIY_DETAIL_KEY){
				htmArr[i] = cfhtmls;
			}
		}
		return htmArr;
	},
	getCustFieldsHtmls : function(customFields, maxCount){
		if(null == customFields || customFields.length == 0) return null;
		var rowNum = 1;
		var htmlArr = ['<tr>'];
		var count = customFields.length;
		var lastCell = 0;
		var dataProps = [];
		for(var i=0; i<count; i++){
			var cf = customFields[i];
			var isRequired = cf.isRequired;
			var allowBlank = (isRequired && parseInt(isRequired) == 1);
			var name = cf.name;
			var controlType = cf.controlType;
			var datasource = cf.datasource;
			dataProps[i] = {name:name,controlType:controlType,datasource:datasource};
			var fieldLabel = this.getFieldLabel(cf);
			var row = cf.row;
			lastCell = cf.col;
			if(rowNum == parseInt(row)){
				htmlArr[htmlArr.length] = this.getCellHtml(allowBlank, name, fieldLabel);
			}else{
				if(i>0){
					var prev_cf = customFields[i-1];
					var prev_col = prev_cf.col;
					this.addColSpan(maxCount,prev_col,htmlArr);
				}
				htmlArr[htmlArr.length] = '</tr>';
				htmlArr[htmlArr.length] = '<tr>';
				htmlArr[htmlArr.length] = this.getCellHtml(allowBlank, name, fieldLabel);
				rowNum = row;
			}
		}
		if(lastCell > 0){
			this.addColSpan(maxCount,lastCell,htmlArr);
		}
		htmlArr[htmlArr.length] = '</tr>';
		var htms = htmlArr.join(" ");
		htms = htms.replaceAll(this.colSpan_Sigins," ");
		return {htms:htms,dataProps:dataProps};
	},
	addColSpan : function(maxCount,lastCell,htmlArr){
		var colSpanStr = this.getColSpan(lastCell, maxCount);
		var prevHtml = htmlArr[htmlArr.length-1];
		prevHtml = prevHtml.replace(this.colSpan_Sigins,colSpanStr);
		htmlArr[htmlArr.length-1] = prevHtml;
	},
	getCellHtml : function(allowBlank,name,fieldLabel){
		var allowBlankHtml = '';
		if(allowBlank){
			allowBlankHtml = '<span>*</span>';
		}
		var html =  '<th>'+allowBlankHtml+'<label col="'+name+'">'+fieldLabel+'：</label></th><td col="'+name+'" '+this.colSpan_Sigins+'>&nbsp;</td>';
		return html;
	},
	/**
	 * 获取列合并数
	 * 注：表单DIY 中规定的最大列数必须小于等于详情面板中规定的 cmns 列数
	 * @param {} maxCmncount 表单DIY 中规定的每行的最大列数
	 * @param {} maxCount 详情面板最大列数 THREE : 3, TWO : 2, ONE : 1
	 */
	getColSpan : function(maxCmncount, maxCount){
		var colSpan = "";
		var result = maxCount - maxCmncount;
		if(result <= 0) return colSpan;
		result = result * 2 + 1;
		colSpan = ' colspan="5" ';
		return colSpan;
	},
	setPropsToHtmlArr : function(propsFields,htmArr){
		if(null == propsFields || propsFields.length == 0) return;
		for(var i=0,count=propsFields.length; i<count; i++){
			var prop = propsFields[i];
			var name = prop.name;
			if(!name) continue;
			var standName = prop.standName;
			if(!standName) continue;
			var fieldLabel = this.getFieldLabel(prop);
			var isRequired = prop.isRequired;
			switch(parseInt(isRequired)){
				case 0 :{
					this.replaceHtmls(fieldLabel,[standName],htmArr);
					break;
				}case 1 :{
					fieldLabel = '<span>*</span><label col="'+name+'">'+fieldLabel+'：</label>';
					var repArr = ['<span>*</span><label col="'+name+'">'+standName+'：</label>',
								  '<label col="'+name+'">'+standName+'：</label>'];
					this.replaceHtmls(fieldLabel,repArr,htmArr);
					break;
				}case 2 :{
					fieldLabel = '<label col="'+name+'">'+fieldLabel+'：</label>';
					var repArr = ['<span>*</span><label col="'+name+'">'+standName+'：</label>',
								  '<label col="'+name+'">'+standName+'：</label>'];
					this.replaceHtmls(fieldLabel,repArr,htmArr);
					break;
				}
			}
		}
	},
	/**
	 * 替换 HTML 中的旧值
	 * @param {} fieldLabel
	 * @param {} standName
	 * @param {} htmArr
	 */
	replaceHtmls : function(fieldLabel,replaceArr,htmArr){
		var len = replaceArr.length;
		for(var i=0,count=htmArr.length; i<count; i++){
			var html = htmArr[i];
			var str = null;
			for(j=0; j<len; j++){
				var repStr = replaceArr[j];
				if(repStr && html.indexOf(repStr)  != -1){
					str = repStr;
					break;
				}
			}
			if(str){
				html = html.replace(repStr,fieldLabel);
				htmArr[i] = html;
				break;
			}
		}
	} 
}