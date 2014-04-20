/**
 * 登录窗口国际化消息
 */
Msg_LoginWindow = {
	win_title:'同心日协同办公系统',
	tab_one_title : '身份认证',
	tab_two_title : '关于本系统',
	tab_two_html : '<b>同心日OA系统V1.0&nbsp;&nbsp;&nbsp; 技术支持 : 程明卫</b<br><br>' +
					' 版权所有:  广州同心日信息科技有限责任公司  <br><br><b>技术支持 : 程明卫</b><br><br>' +
					'电话:18064179050 &nbsp;&nbsp;QQ:340360491&nbsp;&nbsp; Email:340360491@qq.com<br>' +
					'访问官方网站:<a href="http://www.tongxinri.com" '+
					'target="_blank" title="点此访问广州同心日信息科技有限责任公司官网">http://www.tongxinri.com/</a>'+
					'<p><br/><div id="txr_tools" style="font-size:16px;font-weight:bold;text-align:center;"><a id="down_chorme" href="#">下载谷歌浏览器</a>&nbsp;&nbsp;'
					+'<a id="down_help" href="tools/browser/chorme_helpDocument.pdf" target="_blank">下载谷歌浏览器使用帮助</a></div></p>',
	field_userName : '用户名',
	field_passWord : '密     码',
	field_oldpassWord : '旧密码',
	field_SetPassWord : '新密码',
	field_ConfirmPassWord : '确认密码',
	field_code : '验证码',
	field_isaudot : '自动登录',
	field_themeSelect : '系统风格',
	store_themeSelect : [["default","默认风格"],["access","棕色风格"],["gray","灰色风格"],["blue","蓝色风格"],["black","黑色风格"],
						["red03","红色风格"],["green","緑色风格"],["brown02", "棕色2"],["blue03", "蓝色2"],
						["gray2", "灰色"],["pink", "粉红色"],["purple", "紫色"],["calista","浅黄色"],["green2","浅绿色"]],
	field_language : '系统语言',
	store_language :  [["zh_CN","中文简体"],["en","英文"]],
	button_login : '登录(Ctrl+L)',
	button_clear : '清空(Ctrl+C)'
};

/**
 * 系统消息提示[提示框内容] 
 * Msg_SysTip.msg_delFmAnswer
 */
Msg_SysTip = {
	title_apperr : '程序调用错误',
	title_appconfirm : '系统提示',
	title_appwarn : '系统警告',
	msg_user_exist : '<span style="color:red;font-weight:bold;">CURENT_EMP</span>，您好！<br/>由于你长时间未使用系统，您的会话已过期，请退出并重新登录!',
	msg_loadingDatas : '正在加载数据...',
	msg_saveDatas : '正在保存数据...',
	msg_nopassdata : '数据输入不合法，请检查！',
	msg_nohavadata : '服务器没有任何数据返回！',
	msg_nohaveParentCfg : '在 show Window 窗口 时，必须提供 setParentCfg(parentCfg) 方法，并传入 parentCfg 参数！',
	msg_nohaveParent : '在 show Window 窗口 时， parentCfg 对象没有设置  parent 属性值！',
	msg_nohaveAction : '在调用  EventManager.get(url,action) 时没有提供 action 参数！',
	msg_nohaveReloadObj : '要加载的对象没有 roload() 方法！',
	msg_dataSave : '数据保存成功！',
	msg_dataSucess : '数据处理成功！',
	msg_dataFailure : '数据处理失败！',
	msg_deleteData : '确定删除选中的记录?',
	msg_enabledData : '确定起用选中的记录?',
	msg_disabledData : '确定禁用选中的记录?',
	msg_noSelNode : '请选择节点！',
	msg_noSelRecord : '请选择记录！',
	msg_noSelUser : '请选择用户！',
	msg_noMenuNode : '你选择的是卡片菜单节点,能不能进行修改！',
	msg_noSelBreed : '请选择业务品种！',
	msg_enabledstate : '选择的节点已经是起用状态',
	msg_disabledstate : '选择的节点已经是禁用状态',
	msg_record_enabledstate : '选中的记录已经是起用状态',
	msg_record_disabledstate : '选中的记录已经是禁用状态',
	msg_selDeleteData : '请勾选要删除的记录！',
	msg_actorGridTip : '[提示:<span style="color:red;">先选择参与者类型，再选择参与者列表</span>]',
	msg_formallowBlank : '[提示:带&nbsp;<span style="color:red;">*</span>&nbsp;为必填项]',
	msg_orderNoEdit : '[提示:<span style="color:red;">业务顺序可编辑</span>]',
	msg_addCustomer_noparams : '添加客户资料时，请提供参数  {params : {formDatas : {}}} !',
	msg_editCustomer_noparams : '编辑客户资料时，请提供参数  {params : {customerInfoId : 1001} !',
	msg_doSaveFileCofirm : '确定要生成文件?',
	msg_doSaveResFile : '正在生成文件...',
	msg_saveResourceSucess : '文件生成成功!',
	msg_toAllLate : '确定将应还款日期小于当前日期的记录全部转为逾期?',
	msg_toLate : '可转为逾期，确定将符合条件的记录转为逾期?',
	msg_deleteAttachmentData : '确定删除此附件?',
	msg_deleteAllAttachmentData : '确定删除所有附件?',
	msg_nohaveAttachmentData : '没有任何附件可删除',
	msg_downloadAttachmentData : '确定下载此附件?',
	msg_paramsisnull : '参数"params"不能为空!',
	msg_formTypeisnull : '参数 params 中的 "formType" 不能为空!',
	msg_formIdisnull : '参数 params 中的 "formId" 不能为空!',
	msg_sysIdisnull : '参数 params 中的 "sysId" 不能为空!',
	msg_checkFormulaOk : '公式正确',
	msg_checkFormulaNo : '公式有错误',
	msg_delFmAnswer : '确定删除当前公式?',
	msg_synchronouspic : '图片同步成功!'
};
/**
 *提供一个简单的、易于扩展的、可重写的表单验证功能,中显示的文本	
 * @type 
 */
RegExpText = {
	ageText :'年龄格式出错！！格式例如：20', // 年龄
	repasswordText : "密码输入不一致！！",// 密码验证
	postcodeText : "该输入项目必须是邮政编码格式，例如：438304",
	ipText : "该输入项目必须是IP地址格式，例如：222.192.42.12",
	telephoneText : "该输入项目必须是电话号码格式，例如：0513-89500414,051389500414,89500414",
	mobileText : "该输入项目必须是手机号码格式，例如：13485135075",
	IDCardText_1 : "身份证号码地区非法!!,格式例如:32",
	IDCardText_2 : "身份证号码出生日期超出范围,格式例如:19860817",
	IDCardText_3 : "身份证号码末位校验位校验出错,请注意x的大小写,格式例如:201X",
	IDCardText_4 : "身份证号码出生日期超出范围,格式例如:19860817",
	IDCardText_5 : "身份证号码位数不对,应该为15位或是18位",
	IDCardText_6 : "该输入项目必须是身份证号码格式，例如：421181199011131318",
	qqText : "请输入正确的qq号码或者MSN号！"
} ;
/**
 * 系统首页[国际化] 
 * 
 */
Msg_AppCaption = {
		menu_user : '当前用户:',
		menu_start : '开始',
		menu_sysset : '系统设置',
		menu_changelog : '切换用户',
		menu_logout : '退出系统',
		menu_refresh : '刷新',
		disabled_text : '【已禁用】'
};

/**
 * 按钮配置管理选项
 * Btn_Cfgs.CONFIRM_BTN_TXT
 */
var Btn_Cfgs = {
		/*==================系统基础平台按钮配置==========*/
		/*--->组织架构<---*/
		COMPANY_ADD_BTN_TXT : '添加公司',
		COMPANY_ADD_BTN_FASTKEY : '',
		COMPANY_ADD_BTN_CLS : 'page_add',
		COMPANY_ADD_TIP_BTN_TXT :'添加公司',
		
		COMPANY_EDIT_BTN_TXT : '编辑公司',
		COMPANY_EDIT_BTN_FASTKEY : '',
		COMPANY_EDIT_BTN_CLS : 'page_edit',
		COMPANY_EDIE_TIP_BTN_TXT :'添加公司',
		
		COMPANY_DEL_BTN_TXT : '删除公司',
		COMPANY_DEL_BTN_FASTKEY : '',
		COMPANY_DEL_BTN_CLS : 'page_delete',
		COMPANY_DEL_TIP_BTN_TXT :'删除公司',
		
		DEPARTMENT_ADD_BTN_TXT : '添加部门',
		DEPARTMENT_ADD_BTN_FASTKEY : '',
		DEPARTMENT_ADD__BTN_CLS : 'page_add',
		DEPARTMENT_ADD_TIP_BTN_TXT :'添加部门',
		
		DEPARTMENT_EDIT_BTN_TXT : '编辑部门',
		DEPARTMENT_EDIT_BTN_FASTKEY : '',
		DEPARTMENT_EDIT_BTN_CLS : 'page_edit',
		DEPARTMENT_EDIT_TIP_BTN_TXT :'编辑部门',
		
		DEPARTMENT_DEL_BTN_TXT : '删除部门',
		DEPARTMENT_DEL_BTN_FASTKEY : '',
		DEPARTMENT_DEL_BTN_CLS : 'page_delete',
		DEPARTMENT_DEL_TIP_BTN_TXT :'删除部门',
		
		POST_ADD_BTN_TXT : '添加职位',
		POST_ADD_BTN_FASTKEY : '',
		POST_ADD_BTN_CLS : 'page_add',
		POST_ADD_TIP_BTN_TXT :'添加职位',
		
		POST_EDIT_BTN_TXT : '编辑职位',
		POST_EDIT_BTN_FASTKEY : '',
		POST_EDIT_BTN_CLS : 'page_edit',
		POST_EDIT_TIP_BTN_TXT :'编辑职位',
		
		POST_ENABLED_BTN_TXT : '启用职位',
		POST_ENABLED_BTN_FASTKEY : '',
		POST_ENABLED_BTN_CLS : 'page_enabled',
		POST_ENABLED_TIP_BTN_TXT :'启用职位',
		
		POST_DISABLED_BTN_TXT : '禁用职位',
		POST_DISABLED_BTN_FASTKEY : '',
		POST_DISABLED_BTN_CLS : 'page_disabled',
		POST_DISABLED_TIP_BTN_TXT :'禁用职位',
		
		POST_DEL_BTN_TXT : '删除职位',
		POST_DEL_BTN_FASTKEY : '',
		POST_DEL_BTN_CLS : 'page_delete',
		POST_DEL_TIP_BTN_TXT :'删除职位',
		
		/*--->角色管理<---*/
		ROLE_ADD_BTN_TXT : '添加角色',
		ROLE_ADD_BTN_FASTKEY : '',
		ROLE_ADD_BTN_CLS : 'page_add_role',
		ROLE_ADD_TIP_BTN_TXT :'添加角色',
		
		ROLE_EDIT_BTN_TXT : '编辑角色',
		ROLE_EDIT_BTN_FASTKEY : '',
		ROLE_EDIT_BTN_CLS : 'page_edit_role',
		ROLE_EDIT_TIP_BTN_TXT :'编辑角色',
		
		ROLE_COPY_BTN_TXT : '复制角色',
		ROLE_COPY_BTN_FASTKEY : '',
		ROLE_COPY_BTN_CLS : 'page_copy',
		ROLE_COPY_TIP_BTN_TXT :'复制角色',
		
		ROLE_SAVE_BTN_TXT : '保存角色',
		ROLE_SAVE_BTN_FASTKEY : '',
		ROLE_SAVE_BTN_CLS : 'page_save',
		ROLE_SAVE_TIP_BTN_TXT :'保存角色',
		
		ROLE_ENABLED_BTN_TXT : '启用角色',
		ROLE_ENABLED_BTN_FASTKEY : '',
		ROLE_ENABLED_BTN_CLS : 'page_enabled',
		ROLE_ENABLED_TIP_BTN_TXT :'启用角色',
		
		ROLE_DISABLED_BTN_TXT : '禁用角色',
		ROLE_DISABLED_BTN_FASTKEY : '',
		ROLE_DISABLED_BTN_CLS : 'page_disabled',
		ROLE_DISABLED_TIP_BTN_TXT :'禁用角色',
		
		ROLE_DEL_BTN_TXT : '删除角色',
		ROLE_DEL_BTN_FASTKEY : '',
		ROLE_DEL_BTN_CLS : 'page_del_role',
		ROLE_DEL_TIP_BTN_TXT :'删除角色',
		
		/*--->用户管理<---*/
		USER_LABEL_TEXT:'姓名',
		
		USER_ADD_BTN_TXT : '添加用户',
		USER_ADD_BTN_FASTKEY : '',
		USER_ADD_BTN_CLS : 'page_adduser',
		USER_ADD_TIP_BTN_TXT :'添加用户',
		
		USER_EDIT_BTN_TXT : '编辑用户',
		USER_EDIT_BTN_FASTKEY : '',
		USER_EDIT_BTN_CLS : 'page_eidtuser',
		USER_EDIT_TIP_BTN_TXT :'编辑用户',
		
		USER_ENABLED_BTN_TXT : '启用用户',
		USER_ENABLED_BTN_FASTKEY : '',
		USER_ENABLED_BTN_CLS : 'page_enabuser',
		USER_ENABLED_TIP_BTN_TXT :'启用用户',
		
		USER_DISABLED_BTN_TXT : '禁用用户',
		USERE_DISABLED_BTN_FASTKEY : '',
		USER_DISABLED_BTN_CLS : 'page_disuse',
		USER_DISABLED_TIP_BTN_TXT :'禁用用户',
		
		USER_DEL_BTN_TXT : '删除用户',
		USER_DEL_BTN_FASTKEY : '',
		USER_DEL_BTN_CLS : 'page_deluser',
		USER_DEL_TIP_BTN_TXT :'删除用户',
		
		/*--->基础数据<---*/
		RESTYPE_ADD_BTN_TXT : '添加资源',
		RESTYPE_ADD_BTN_FASTKEY : '',
		RESTYPE_ADD_BTN_CLS : 'page_add',
		RESTYPE_ADD_TIP_BTN_TXT :'添加资源',
		
		RESTYPE_EDIT_BTN_TXT : '编辑资源',
		RESTYPE_EDIT_BTN_FASTKEY : '',
		RESTYPE_EDIT_BTN_CLS : 'page_edit',
		RESTYPE_EDIT_TIP_BTN_TXT :'编辑资源',
		
		RESTYPE_DEL_BTN_TXT : '删除资源',
		RESTYPE_DEL_BTN_FASTKEY : '',
		RESTYPE_DEL_BTN_CLS : 'page_delete',
		RESTYPE_DEL_TIP_BTN_TXT :'删除资源',
		
		RESTYPE_PRODUCED_BTN_TXT : '生成资源',
		RESTYPE_PRODUCED_BTN_FASTKEY : '',
		RESTYPE_PRODUCED_BTN_CLS : 'page_add',
		RESTYPE_PRODUCED_TIP_BTN_TXT :'生成资源',
		
		GVLIST_ADD_BTN_TXT : '添加数据',
		GVLIST_ADD_BTN_FASTKEY : '',
		GVLIST_ADD_BTN_CLS : 'page_add',
		GVLIST_ADD_TIP_BTN_TXT :'添加数据',
		
		GVLIST_EDIT_BTN_TXT : '编辑数据',
		GVLIST_EDIT_BTN_FASTKEY : '',
		GVLIST_EDIT_BTN_CLS : 'page_edit',
		GVLIST_EDIT_TIP_BTN_TXT :'编辑数据',
		
		GVLIST_DEL_BTN_TXT : '删除数据',
		GVLIST_DEL_BTN_FASTKEY : '',
		GVLIST_DEL_BTN_CLS : 'page_delete',
		GVLIST_DEL_TIP_BTN_TXT :'删除数据',
		
		GVLIST_DISENABLED_BTN_TXT : '禁用数据',
		GVLIST_DISENABLED_BTN_FASTKEY : '',
		GVLIST_DISENABLED_BTN_CLS : 'page_disabled',
		GVLIST_DISENABLED_TIP_BTN_TXT :'禁用数据',
		
		GVLIST_ENABLED_BTN_TXT : '启用数据',
		GVLIST_ENABLED_BTN_FASTKEY : '',
		GVLIST_ENABLED_BTN_CLS : 'page_enabled',
		GVLIST_ENABLED_TIP_BTN_TXT :'启用数据',
		
		GVLIST_LABEL_TEXT:'默认姓名',
		
		/*--->基础数据<---*/
		SYSTEM_LABEL_TEXT:'系统名称',
		
		/*--->工资项数据<---*/
		WAGEPPLAN_LABEL_TEXT:'工资项名称',
		
		/*--->卡片菜单<---*/
		ACCORDION_LABEL_TEXT:'卡片名称',
		
		/*--->费用项名称<---*/
		EXPENSES_LABEL_TEXT : '费用项名称',
		/*--->菜单管理<---*/
		MENU_ADD_BTN_TXT : '新增菜单',
		MENU_ADD_BTN_FASTKEY : '',
		MENU_ADD_BTN_CLS : 'page_add',
		MENU_ADD_TIP_BTN_TXT :'新增菜单',
		
		MENU_EDIT_BTN_TXT : '修改菜单',
		MENU_EDIT_BTN_FASTKEY : '',
		MENU_EDIT_BTN_CLS : 'page_edit',
		MENU_EDIT_TIP_BTN_TXT :'修改菜单',
		
		MENU_detail_BTN_TXT : '菜单详情',
		MENU_detail_BTN_FASTKEY : '',
		MENU_detail_BTN_CLS : 'page_detail',
		MENU_detail_TIP_BTN_TXT :'菜单详情',
		
		MENU_ENABLED_BTN_TXT : '启用菜单',
		MENU_ENABLED_BTN_FASTKEY : '',
		MENU_ENABLED_BTN_CLS : 'page_enabled',
		MENU_ENABLED_TIP_BTN_TXT :'启用菜单',
		
		MENU_DISABLED_BTN_TXT : '禁用菜单',
		MENU_DISABLED_BTN_FASTKEY : '',
		MENU_DISABLED_BTN_CLS : 'page_disabled',
		MENU_DISABLED_TIP_BTN_TXT :'禁用菜单',
		
		MENU_DEL_BTN_TXT : '删除菜单',
		MENU_DEL_BTN_FASTKEY : '',
		MENU_DEL_BTN_CLS : 'page_delete',
		MENU_DEL_TIP_BTN_TXT :'删除菜单',
		
		MODULE_ADD_BTN_TXT : '新增按钮',
		MODULE_ADD_BTN_FASTKEY : '',
		MODULE_ADD_BTN_CLS : 'page_add',
		MODULE_ADD_TIP_BTN_TXT :'新增按钮',
		
		MODULE_EDIT_BTN_TXT : '修改按钮',
		MODULE_EDIT_BTN_FASTKEY : '',
		MODULE_EDIT_BTN_CLS : 'page_edit',
		MODULE_EDIT_TIP_BTN_TXT :'修改按钮',
		
		MODULE_DEL_BTN_TXT : '删除按钮',
		MODULE_DEL_BTN_FASTKEY : '',
		MODULE_DEL_BTN_CLS : 'page_delete',
		MODULE_DEL_TIP_BTN_TXT :'删除按钮',
		
		/*--->节假日设置<---*/
		HOLIDAYS_INIT_BTN_TXT :'节假日初始化',
		
		HOLIDAYS_ADD_BTN_TXT :'添加节假日',
		HOLIDAYS_ADD_BTN_FASTKEY : '',
		HOLIDAYS_ADD_BTN_CLS : 'page_addholiday',
		HOLIDAYS_ADD_TIP_BTN_TXT :'添加节假日',
		
		HOLIDAYS_EDIT_BTN_TXT : '编辑节假日',
		HOLIDAYS_EDIT_BTN_FASTKEY : '',
		HOLIDAYS_EDIT_BTN_CLS : 'page_eidholiday',
		HOLIDAYS_EDIT_TIP_BTN_TXT :'编辑节假日',
		
		HOLIDAYS_ENABLED_BTN_TXT : '启用节假日',
		HOLIDAYS_ENABLED_BTN_FASTKEY : '',
		HOLIDAYS_ENABLED_BTN_CLS : 'page_enabholiay',
		HOLIDAYS_ENABLED_TIP_BTN_TXT :'启用节假日',
		
		HOLIDAYS_DISABLED_BTN_TXT : '禁用节假日',
		HOLIDAYS_DISABLED_BTN_FASTKEY : '',
		HOLIDAYS_DISABLED_BTN_CLS : 'page_disholiday',
		HOLIDAYS_DISABLED_TIP_BTN_TXT :'禁用节假日',
		
		HOLIDAYS_DEL_BTN_TXT : '删除节假日',
		HOLIDAYS_DEL_BTN_FASTKEY : '',
		HOLIDAYS_DEL_BTN_CLS : 'page_delholiday',
		HOLIDAYS_DEL_TIP_BTN_TXT :'删除节假日',
		
		HOLIDAYS_QUERY_BTN_TXT : '查询节假日',
		HOLIDAYS_QUERY_BTN_FASTKEY : '',
		HOLIDAYS_QUERY_BTN_CLS : 'page_holidaysearch',
		HOLIDAYS_QUERY_TIP_BTN_TXT :'查询节假日',
		
		/*---->编号规则<------*/
		BUDSSDCODE_LABEL_TXT : '名称',
		
		/*----->表单个性化设置<------*/
		FORMDIY_ADD_BTN_TXT : "添加表单",
		FORMDIY_ADD_BTN_FASTKEY : '',
		FORMDIY_ADD_TIP_BTN_TXT : "添加表单",
		
		FORMDIY_EDIT_ADD_TXT : "编辑表单",
		FORMDIY_EDIT_BTN_FASTKEY : '',
		FORMDIY_EDIT_TIP_BTN_TXT : "编辑表单",
		
		FORMDIY_ENABLED_BTN_TXT : '启用表单',
		FORMDIY_ENABLED_BTN_FASTKEY : '',
		FORMDIY_ENABLED_TIP_BTN_TXT :'启用表单',
		
		FORMDIY_DISABLED_BTN_TXT : '禁用表单',
		FORMDIY_DISABLED_BTN_FASTKEY : '',
		FORMDIY_DISABLED_TIP_BTN_TXT :'禁用表单',
		
		FORMDIY_DEL_BTN_TXT : '删除表单',
		FORMDIY_DEL_BTN_FASTKEY : '',
		FORMDIY_DEL_TIP_BTN_TXT :'删除表单',
		
		FORMDIY_MAKEFILE_BTN_TXT : '生成文件',
		FORMDIY_MAKEFILE_BTN_FASTKEY : '',
		FORMDIY_MAKEFILE_TIP_BTN_TXT :'生成表单DIY js文件',
		
		
		FIELDCUSTOM_QUERY_BTN_TXT : '刷新字段列表',
		FIELDCUSTOM_QUERY_FASTKEY : 'Ctrl+Q',
		FIELDCUSTOM_QUERY_CLS : 'page_formdiyRefresh',
		FIELDCUSTOM_QUERY_TIP_BTN_TXT : '刷新字段列表[Ctrl+Q]',
		
		FIELDCUSTOM_ADD_BTN_TXT : "添加字段",
		FIELDCUSTOM_ADD_BTN_FASTKEY : '',
		FIELDCUSTOM_ADD_TIP_BTN_TXT : "添加字段",
		
		FIELDCUSTOM_EDIT_ADD_TXT : "编辑字段",
		FIELDCUSTOM_ADD_BTN_FASTKEY : '',
		FIELDCUSTOM_EDIT_TIP_BTN_TXT : "编辑字段",
		
		FIELDCUSTOM_ENABLED_BTN_TXT : '启用字段',
		FIELDCUSTOM_ENABLED_BTN_FASTKEY : '',
		FIELDCUSTOM_ENABLED_TIP_BTN_TXT :'启用字段',
		
		FIELDCUSTOM_DISABLED_BTN_TXT : '禁用字段',
		FIELDCUSTOMY_DISABLED_BTN_FASTKEY : '',
		FIELDCUSTOM_DISABLED_TIP_BTN_TXT :'禁用字段',
		
		FIELDCUSTOM_DEL_BTN_TXT : '删除字段',
		FIELDCUSTOM_DEL_BTN_FASTKEY : '',
		FIELDCUSTOM_DEL_TIP_BTN_TXT :'删除字段',
		
		FIELDPROP_QUERY_BTN_TXT : '刷新属性列表',
		FIELDPROP_QUERY_FASTKEY : 'Ctrl+Q',
		FIELDPROP_QUERY_CLS : 'page_formdiyRefresh',
		FIELDPROP_QUERY_TIP_BTN_TXT : '刷新属性列表[Ctrl+Q]',
		
		FIELDPROP_ADD_BTN_TXT : "添加字段属性",
		FIELDPROP_ADD_BTN_FASTKEY : '',
		FIELDPROP_ADD_TIP_BTN_TXT : "添加字段属性",
		
		FIELDPROP_EDIT_ADD_TXT : "编辑字段属性",
		FIELDPROP_ADD_BTN_FASTKEY : '',
		FIELDPROP_EDIT_TIP_BTN_TXT : "编辑字段属性",
		
		FIELDPROP_ENABLED_BTN_TXT : '启用字段属性',
		FIELDPROP_ENABLED_BTN_FASTKEY : '',
		FIELDPROP_ENABLED_TIP_BTN_TXT :'启用字段属性',
		
		FIELDPROP_DISABLED_BTN_TXT : '禁用字段属性',
		FIELDPROP_DISABLED_BTN_FASTKEY : '',
		FIELDPROP_DISABLED_TIP_BTN_TXT :'禁用字段属性',
		
		FIELDPROP_DEL_BTN_TXT : '删除字段属性',
		FIELDPROP_DEL_BTN_FASTKEY : '',
		FIELDPROP_DEL_TIP_BTN_TXT :'删除字段属性',
		
		/*----------->现居区域设置<-----------*/
		HOMES_DEFAULT_BTN_TXT : "设为默认",
		HOMES_DEFAULT_BTN_FASTKEY : '',
		HOMES_Default_TIP_BTN_TXT : "设为默认",
		
		/*------------------->报表模板<------------*/
		REPORTTEMP_LABEL_TXT : '报表名称',
		/*==================系统基础平台按钮配置==========*/
		/*------------------->凭证日志总查询<------------*/
		BINDVTEMP_BTN_TXT : '绑定凭证模板',
		BINDVTEMP_TIP_TXT : '对因“找不到对应凭证模板”生成失败的日志，重新绑定凭证',
		
		VOUCHEROPLOG_BTN_TXT : '重新生成凭证',
		VOUCHEROPLOG_TIP_TXT : '重新生成凭证',
		/*==================系统基础平台按钮配置==========*/
	
		/*-->刷新配置 <--*/
		REFRESH_BTN_TXT : '刷新',
		REFRESH_FASTKEY : 'Ctrl+R',
		REFRESH_CLS : 'page_refresh',
		REFRESH_TIP_BTN_TXT : '刷新[Ctrl+R]',
		
		/*-->同步图片<--*/
		SYNCHRONOUSPIC_BTN_TXT : '同步图片',
		SYNCHRONOUSPIC_FASTKEY : 'Ctrl+R',
		SYNCHRONOUSPIC_CLS : 'page_refresh',
		SYNCHRONOUSPIC_TIP_BTN_TXT : '将服务器上所有图片重新读取一次，以保持图片数据的最新',
		
		
		/*--> 展开配置 <--*/
		EXPAND_BTN_TXT : '展开',
		EXPAND_FASTKEY : 'Ctrl+A',
		EXPAND_CLS : 'page_expand',
		EXPAND_TIP_BTN_TXT : '展开[Ctrl+A]',
		
		/*--> 收起配置 <--*/
		COLLAPSE_BTN_TXT : '收起',
		COLLAPSE_FASTKEY : 'Ctrl+X',
		COLLAPSE_CLS : 'page_away',
		COLLAPSE_TIP_BTN_TXT : '收起[Ctrl+X]',
		
		/*--> 全选配置 <--*/
		CHECKALL_BTN_TXT : '全选',
		CHECKALL_FASTKEY : 'Alt+A',
		CHECKALL_CLS : 'page_checkall',
		CHECKALL_TIP_BTN_TXT : '全选[Alt+A]',
		
		/*--> 反选配置 <--*/
		UNCHECKALL_BTN_TXT : '反选',
		UNCHECKALL_FASTKEY : 'Alt+U',
		UNCHECKALL_CLS : 'page_uncheckall',
		UNCHECKALL_TIP_BTN_TXT : '反选[Alt+U]',
		
		/*--> 查询配置 <--*/
		QUERY_BTN_TXT : '查询',
		QUERY_FASTKEY : 'Ctrl+Q',
		QUERY_CLS : 'page_query',
		QUERY_TIP_BTN_TXT : '查询[Ctrl+Q]',
		
		/*--> 重置配置 <--*/
		RESET_BTN_TXT : '重置',
		RESET_FASTKEY : 'Ctrl+C',
		RESET_CLS : 'page_reset',
		RESET_TIP_BTN_TXT : '重置[Ctrl+C]',
		
		/*--> 清空配置 <--*/
		CLEAR_BTN_TXT : '清空',
		CLEAR_FASTKEY : 'Ctrl+C',
		CLEAR_CLS : 'page_reset',
		CLEAR_TIP_BTN_TXT : '清空[Ctrl+C]',
		
		/*--> 检查公式配置 <--*/
		CHECKFM_BTN_TXT : '检查公式',
		CHECKFM_FASTKEY : 'Ctrl+C',
		CHECKFM_CLS : 'page_enabled',
		CHECKFM_TIP_BTN_TXT : '检查公式[Ctrl+C]',
		
		
		/*--> 添加配置 <--*/
		INSERT_BTN_TXT : '添加',
		INSERT_FASTKEY : 'Ctrl+I',
		INSERT_CLS : 'page_add',
		INSERT_TIP_BTN_TXT :'添加[Ctrl+I]',
		
		/*--> 编辑配置 <--*/
		MODIFY_BTN_TXT : '编辑',
		MODIFY_FASTKEY : 'Ctrl+M',
		MODIFY_CLS : 'page_edit',
		MODIFY_TIP_BTN_TXT : '编辑[Ctrl+M]',
		
		/*--> 详情配置 <--*/
		DETAIL_BTN_TXT : '详情',
		DETAIL_FASTKEY : 'Ctrl+M',
		DETAIL_CLS : 'page_detail',
		DETAIL_TIP_BTN_TXT : '详情[Ctrl+X]',
		
		/*--> 查看贷款详情 <--*/
		DETAIL_LOAN_BTN_TXT : '查看贷款详情',
		DETAIL_LOAN_FASTKEY : 'Ctrl+M',
		DETAIL_LOAN_CLS : 'page_detail',
		DETAIL_LOAN_TIP_BTN_TXT : '查看贷款详情',
		
		/*--> 同步 <--*/
		SYNCHRONOUS_BTN_TXT : '同步',
		SYNCHRONOUS_FASTKEY : 'Ctrl+M',
		SYNCHRONOUS_CLS : 'page_detail',
		SYNCHRONOUS_TIP_BTN_TXT : '同步客户数据到财务系统',
		
		/*--> 起用配置 <--*/
		ENABLED_BTN_TXT : '启用',
		ENABLED_FASTKEY : 'Ctrl+E',
		ENABLED_CLS : 'page_enabled',
		ENABLED_TIP_BTN_TXT : '启用[Ctrl+E]',
		
		/*--> 禁用配置 <--*/
		DISABLED_BTN_TXT : '禁用',
		DISABLED_FASTKEY : 'Ctrl+F',
		DISABLED_CLS : 'page_disabled',
		DISABLED_TIP_BTN_TXT : '禁用[Ctrl+F]',
		
		/*--> 删除配置 <--*/
		DELETE_BTN_TXT : '删除',
		DELETE_FASTKEY : 'Ctrl+D',
		DELETE_CLS : 'page_delete',
		DELETE_TIP_BTN_TXT :'删除[Ctrl+D]',
		
		/*--> 上一条配置 <--*/
		PREVIOUS_BTN_TXT : '上一条',
		PREVIOUS_FASTKEY : 'Ctrl+P',
		PREVIOUS_CLS : 'page_previous',
		PREVIOUS_TIP_BTN_TXT :'上一条[Ctrl+P]',
		
		/*--> 下一条配置 <--*/
		NEXT_BTN_TXT : '下一条',
		NEXT_FASTKEY : 'Ctrl+N',
		NEXT_CLS : 'page_next',
		NEXT_TIP_BTN_TXT :'下一条[Ctrl+N]',
		
			
		/*--> 下一步配置 <--*/
		NEXT_BTN_TXT : '下一步',
		NEXT_FASTKEY : 'Ctrl+N',
		NEXT_CLS : 'page_next',
		NEXT_TIP_BTN_TXT :'下一步[Ctrl+N]',
		
		/*--> 保存配置 <--*/
		SAVE_BTN_TXT : '保存',
		SAVE_FASTKEY : 'Ctrl+S',
		SAVE_CLS : 'page_save',
		SAVE_TIP_BTN_TXT :'保存[Ctrl+S]',
		
		/*--> 暂存配置 <--*/
		TEMP_SAVE_BTN_TXT : '暂存',
		TEMP_SAVE_FASTKEY : 'Ctrl+S',
		TEMP_SAVE_CLS : 'page_save',
		TEMP_SAVE_TIP_BTN_TXT :'暂存',
		
		/*--> 提交 <--*/
		SUBMIT_BTN_TXT : '提交',
		SUBMIT_FASTKEY : 'Alt+S',
		SUBMIT_CLS : 'page_confirm',
		SUBMIT_TIP_BTN_TXT :'提交[Alt+S]',
		
		/*--> 审批配置 <--*/
		AUDIT_BTN_TXT : '审批',
		AUDIT_FASTKEY : 'Ctrl+Z',
		AUDIT_CLS : 'page_audit',
		AUDIT_TIP_BTN_TXT :'审批[Ctrl+Z]',
		
		/*--> 审批通过配置 <--*/
		VIAAUDIT_BTN_TXT : '审批通过',
		VIAAUDIT_FASTKEY : 'Ctrl+Z',
		VIAAUDIT_CLS : 'page_audit',
		VIAAUDIT_TIP_BTN_TXT :'审批通过[Ctrl+Z]',
		
		/*--> 取消审批配置 <--*/
		CANCELAUDIT_BTN_TXT : '取消审批',
		CANCELAUDIT_FASTKEY : 'Ctrl+Z',
		CANCELAUDIT_CLS : 'page_audit',
		CANCELAUDIT_TIP_BTN_TXT :'取消审批[Ctrl+Z]',
		
		/*--> 转办配置 <--*/
		TURN_BTN_TXT : '转办',
		TURN_FASTKEY : 'Ctrl+Z',
		TURN_CLS : 'page_edit',
		TURN_TIP_BTN_TXT :'转办',
		
		
		/*--> 关闭配置 <--*/
		CLOSE_BTN_TXT : '关闭',
		CLOSE_FASTKEY : 'Ctrl+H',
		CLOSE_CLS : 'page_close',
		CLOSE_TIP_BTN_TXT :'关闭[Ctrl+H]',
		
		/*--> 复制配置 <--*/
		COPY_BTN_TXT : '复制',
		COPY_FASTKEY : 'Ctrl+S',
		COPY_CLS : 'page_COPY',
		COPY_TIP_BTN_TXT :'复制[Ctrl+S]',
		
		/*--> 上传配置 <--*/
		UPLOAD_BTN_TXT : '上传',
		UPLOAD_FASTKEY : 'Ctrl+U',
		UPLOAD_CLS : 'page_upload',
		UPLOAD_TIP_BTN_TXT :'上传[Ctrl+U]',
		
		/*--> 下载配置 <--*/
		DOWNLOAD_BTN_TXT : '下载',
		DOWNLOAD_FASTKEY : 'Ctrl+O',
		DOWNLOAD_CLS : 'page_download',
		DOWNLOAD_TIP_BTN_TXT :'下载[Ctrl+O]',
		
		/*--> 导出EXCEL配置 <--*/
		EXPORT_BTN_TXT : '导出',
		EXPORT_FASTKEY : 'Ctrl+Y',
		EXPORT_CLS : 'page_exportxls',
		EXPORT_TIP_BTN_TXT :'导出[Ctrl+Y]',
		
		/*--> 导入EXCEL配置 <--*/
		EXPORT_IMPORT_BTN_TXT : '导入',
		EXPORT_IMPORTFASTKEY : 'Ctrl+Y',
		EXPORT_TIP_IMPORT_BTN_TXT :'导入[Ctrl+Y]',
		
		/*--> 打印预览 <--*/	
		PRINT_PREVIEW_BTN_TXT:'打印预览',
		PRINT_PREVIEW_FASTKEY : 'Ctrl+T',
		PRINT_PREVIEW_CLS : 'page_query',
		PRINT_PREVIEW_TIP_BTN_TXT :'打印预览',
		
		/*--> 打印配置 <--*/	
		PRINT_BTN_TXT : '打印',
		PRINT_FASTKEY : 'Ctrl+T',
		PRINT_CLS : 'page_print',
		PRINT_TIP_BTN_TXT :'打印[Ctrl+T]',
		
		/*--> 打印设计 <--*/	
		PRINT_DESIGN_BTN_TXT:'打印设计',
		PRINT_DESIGN_FASTKEY  : 'Ctrl+T',
		PRINT_DESIGN_CLS :  'page_edit',
		PRINT_DESIGN_TIP_BTN_TXT : '打印设计',
		
		
		/*--> 设置数据访问权限 配置 <--*/	
		SETDATARIGHT_BTN_TXT : '设置数据访问权限',
		SETDATARIGHT_FASTKEY : '',
		SETDATARIGHT_CLS : 'page_add',
		SETDATARIGHT_TIP_BTN_TXT :'设置数据访问权限',
		
		/*--> 修改密码 配置 <--*/	
		MODPWD_BTN_TXT : '修改密码',
		MODPWD_FASTKEY : '',
		MODPWD_CLS : 'page_password',
		MODPWD_TIP_BTN_TXT :'修改密码',
		
		/*--> 职位调整 <--*/	
		CHGPOST_BTN_TXT : '职位调整',
		CHGPOST_FASTKEY : '',
		CHGPOST_CLS : 'page_edit',
		CHGPOST_TIP_BTN_TXT :'职位调整',
		
		/*--> 下拉框树和Grid等 确定按钮 配置 <--*/	
		CONFIRM_BTN_TXT : '确定',
		CONFIRM_FASTKEY : '',
		CONFIRM_CLS : 'page_confirm',
		CONFIRM_TIP_BTN_TXT :'确定',
		
		/*--> 业务品种按钮 配置 <--*/	
		INSERT_VARIETY_BTN_TXT : '添加品种',
		INSERT_VARIETY_FASTKEY : '',
		INSERT_VARIETY_CLS : 'page_add',
		INSERT_VARIETY_TIP_BTN_TXT :'添加品种',
		
		EDIT_VARIETY_BTN_TXT : '编辑品种',
		EDIT_VARIETY_FASTKEY : '',
		EDIT_VARIETY_CLS : 'page_edit',
		EDIT_VARIETY_TIP_BTN_TXT :'编辑品种',
		
		COPY_VARIETY_BTN_TXT : '复制品种',
		COPY_VARIETY_FASTKEY : '',
		COPY_VARIETY_CLS : 'page_save',
		COPY_VARIETY_TIP_BTN_TXT :'复制品种',
		
		/*--> 子业务流程按钮 配置 <--*/	
		INSERT_BUSSPROCC_BTN_TXT : '添加子流程',
		INSERT_BUSSPROCC_FASTKEY : '',
		INSERT_BUSSPROCC_CLS : 'page_add',
		INSERT_BUSSPROCC_TIP_BTN_TXT :'添加子流程',
		
		/*--> 加班申请单按钮 配置 <--*/	
		INSERT_OVERTIME_BTN_TXT : '选择加班申请单',
		INSERT_OVERTIME_FASTKEY : '',
		INSERT_OVERTIME_CLS : 'page_add',
		INSERT_OVERTIME_TIP_BTN_TXT :'选择加班申请单',
		
			
		INSERT_APPLY_BTN_TXT : '添加',
		INSERT_APPLY_FASTKEY : '',
		INSERT_APPLY_CLS : 'page_add',
		INSERT_APPLY_TIP_BTN_TXT :'添加',
		
		INSERT_DEL_BTN_TXT : '删除',
		INSERT_DEL_BTN_FASTKEY : '',
		INSERT_DEL_BTN_CLS : 'page_delete',
		INSERT_DEL_TIP_BTN_TXT :'删除',
		
		
		EDIT_BUSSPROCC_BTN_TXT : '编辑子流程',
		EDIT_BUSSPROCC_FASTKEY : '',
		EDIT_BUSSPROCC_CLS : 'page_edit',
		EDIT_BUSSPROCC_TIP_BTN_TXT :'编辑子流程',
		
		COPY_BUSSPROCC_BTN_TXT : '复制子流程',
		COPY_BUSSPROCC_FASTKEY : '',
		COPY_BUSSPROCC_CLS : 'page_save',
		COPY_BUSSPROCC_TIP_BTN_TXT :'复制子流程',
		
		
		VIEW_PROCESS_BTN_TXT : '查看流程图',
		VIEW_PROCESS_FASTKEY : '',
		VIEW_PROCESS_CLS : 'page_detail',
		VIEW_PROCESS_TIP_BTN_TXT :'查看流程图',
		
		CFG_PROCESS_BTN_TXT : '配置流程',
		CFG_PROCESS_FASTKEY : '',
		CFG_PROCESS_CLS : 'page_save',
		CFG_PROCESS_TIP_BTN_TXT :'配置流程',
		
		INSERT_CLAUSE_BTN_TXT : '添加条款',
		INSERT_CLAUSE_FASTKEY : '',
		INSERT_CLAUSE_CLS : 'page_add',
		INSERT_CLAUSE_TIP_BTN_TXT :'添加条款',
		
		EDIT_CLAUSE_BTN_TXT : '编辑条款',
		EDIT_CLAUSE_FASTKEY : '',
		EDIT_CLAUSE_CLS : 'page_edit',
		EDIT_CLAUSE_TIP_BTN_TXT :'编辑条款',
		
		DELETE_CLAUSE_BTN_TXT : '删除条款',
		DELETE_CLAUSE_FASTKEY : 'Ctrl+D',
		DELETE_CLAUSE_CLS : 'page_delete',
		DELETE_CLAUSE_TIP_BTN_TXT :'删除条款',
		
		DBLCLICK_TIP_BTN_TXT :'双击也可赋值',
		
		/*--> 个人/企业贷款发放按钮 配置 <--*/	
		DO_LOAN_BTN_TXT : '放款',
		DO_LOAN_FASTKEY : '',
		DO_LOAN_CLS : 'page_confirm',
		DO_LOAN_TIP_BTN_TXT :'放款',
		
		FILE_LOAN_BTN_TXT : '导入文件放款',
		FILE_LOAN_FASTKEY : '',
		FILE_LOAN_CLS : 'page_detail',
		FILE_LOAN_TIP_BTN_TXT :'导入文件放款',
		
		FILE_IMPORT_BTN_TXT : '导入文件',
		FILE_IMPORT_FASTKEY : '',
		FILE_IMPORT_CLS : 'page_exportxls',
		FILE_IMPORT_TIP_BTN_TXT :'导入文件',
		
		/*--> 正常/逾期收款按钮 配置 <--*/	
		DO_NOMAL_BTN_TXT : '收款',
		DO_NOMAL_FASTKEY : '',
		DO_NOMAL_CLS : 'page_save',
		DO_NOMAL_TIP_BTN_TXT :'收款',
		
		FILE_NOMAL_BTN_TXT : '导入文件收款',
		FILE_NOMAL_FASTKEY : '',
		FILE_NOMAL_CLS : 'page_detail',
		FILE_NOMAL_TIP_BTN_TXT :'导入文件收款',
		
		DO_LATE_BTN_TXT : '转逾期',
		DO_LATE_FASTKEY : '',
		DO_LATE_CLS : 'page_save',
		DO_LATE_TIP_BTN_TXT :'将选中的记录转为逾期',
		
		DO_ALLLATE_BTN_TXT : '全部转逾期',
		DO_ALLLATE_FASTKEY : '',
		DO_ALLLATE_CLS : 'page_save',
		DO_ALLLATE_TIP_BTN_TXT :'将所有符合逾期条件的客户转为逾期',
		
		/*-->手续费收取<---*/
		DO_FREE_BTN_TXT : '放款手续费收取',
		DO_FREE_FASTKEY : '',
		DO_FREE_CLS : 'page_confirm',
		DO_FREE_TIP_BTN_TXT : '放款手续费收取',
		
		FILE_FREE_BTN_TXT : '导入放款手续费文件收款',
		FILE_FREE_FASTKEY : '',
		FILE_FREE_CLS : 'page_detail',
		FILE_FREE_TIP_BTN_TXT :'导入放款手续费文件收款',
		
		/*--> 导出放款手续费文件配置 <--*/
		EXPORT_FREE_BTN_TXT : '导出放款手续费文件',
		EXPORT_FREE_FASTKEY : 'Ctrl+Y',
		EXPORT_FREE_CLS : 'page_exportxls',
		EXPORT_FREE_TIP_BTN_TXT :'导出放款手续费文件[Ctrl+Y]',
		
		/*--->利率设置(公式)<---*/
		FORMULA_ADD_BTN_TXT : '添加公式',
		FORMULA_ADD_BTN_FASTKEY : '',
		FORMULA_ADD_BTN_CLS : 'page_add',
		FORMULA_ADD_TIP_BTN_TXT :'添加公式',
		
		FORMULA_EDIT_BTN_TXT : '编辑公式',
		FORMULA_EDIT_BTN_FASTKEY : '',
		FORMULA_EDIT_BTN_CLS : 'page_edit',
		FORMULA_EDIE_TIP_BTN_TXT :'编辑公式',
		
		FORMULA_ENABLED_BTN_TXT : '启用公式',
		FORMULA_ENABLED_BTN_FASTKEY : '',
		FORMULA_ENABLED_BTN_CLS : 'page_enabled',
		FORMULA_ENABLED_TIP_BTN_TXT :'启用公式',
		
		FORMULA_DISABLED_BTN_TXT : '禁用公式',
		FORMULA_DISABLED_BTN_FASTKEY : '',
		FORMULA_DISABLED_BTN_CLS : 'page_disabled',
		FORMULA_DISABLED_TIP_BTN_TXT :'禁用公式',
		
		/*--> 公司帐户 --- 财务系统账号映射 <--*/
		BANK_ACCOUNT_MAPPING_BTN_TXT : '账号映射',
		BANK_ACCOUNT_MAPPING_BTN_CLS : 'page_edit',
		BANK_ACCOUNT_MAPPING_TIP_BTN_TXT :'财务系统账号映射',
		
		/*--> 配置流程中的按钮 <--*/
		/*保证合同*/
		GUA_ADD_TXT : '添加保证合同',
		GUA_ADD_BTN_FASTKEY : '',
		GUA_ADD_TIP_BTN_TXT :'添加保证合同',
		
		GUA_EDIT_BTN_TXT : '编辑保证合同',
		GUA_EDIT_BTN_FASTKEY : '',
		GUA_EDIE_TIP_BTN_TXT :'编辑保证合同',
		
		GUA_DEL_BTN_TXT : '删除保证合同',
		GUA_DEL_BTN_FASTKEY : '',
		GUA_DEL_TIP_BTN_TXT :'删除保证合同',
		
		GUA_SC_BTN_TXT : '生成保证合同',
		GUA_SC_BTN_FASTKEY : '',
		GUA_SC_TIP_BTN_TXT :'生成保证合同',
		
		GUA_DATEIL_BTN_TXT : '打印保证合同',
		GUA_DATEIL_BTN_FASTKEY : '',
		GUA_DATEIL_TIP_BTN_TXT :'打印保证合同',
		
		GUA_DOWNLOAD_BTN_TXT : '保证合同模板下载',
		GUA_DOWNLOAD_BTN_FASTKEY : '',
		GUA_DOWNLOAD_TIP_BTN_TXT :'保证合同模板下载',
		
		/*借款合同*/
		LOAN_ADD_TXT : '添加借款合同',
		LOAN_ADD_BTN_FASTKEY : '',
		LOAN_ADD_TIP_BTN_TXT :'添加借款合同',
		
		LOAN_EDIT_BTN_TXT : '编辑借款合同',
		LOAN_EDIT_BTN_FASTKEY : '',
		LOAN_EDIE_TIP_BTN_TXT :'编辑借款合同',
		
		LOAN_DEL_BTN_TXT : '删除借款合同',
		LOAN_DEL_BTN_FASTKEY : '',
		LOAN_DEL_TIP_BTN_TXT :'删除借款合同',
		
		LOAN_SC_BTN_TXT : '生成借款合同',
		LOAN_SC_CLS : 'page_save',
		LOAN_SC_BTN_FASTKEY : '',
		LOAN_SC_TIP_BTN_TXT :'根据已添加的合同数据生成借款合同并打印',
		
		LOAN_DATEIL_BTN_TXT : '打印借款合同',
		LOAN_DATEIL_BTN_FASTKEY : '',
		LOAN_DATEIL_TIP_BTN_TXT :'打印空白的借款合同',
		
		LOAN_DOWNLOAD_BTN_TXT : '借款合同模板下载',
		LOAN_DOWNLOAD_BTN_FASTKEY : '',
		LOAN_DOWNLOAD_TIP_BTN_TXT :'借款合同模板下载',
		
		/*放款单*/
		LOANINVOCE_ADD_TXT : '添加放款单',
		LOANINVOCE_ADD_BTN_FASTKEY : '',
		LOANINVOCE_ADD_TIP_BTN_TXT :'添加放款单',
		
		LOANINVOCE_EDIT_BTN_TXT : '编辑放款单',
		LOANINVOCE_EDIT_BTN_FASTKEY : '',
		LOANINVOCE_EDIE_TIP_BTN_TXT :'编辑放款单',
		
		LOANINVOCE_TJ_BTN_TXT : '提交放款单',
		LOANINVOCE_TJ_BTN_FASTKEY : '',
		LOANINVOCE_TJ_TIP_BTN_TXT :'提交放款单',
		
		
		LOANINVOCE_DEL_BTN_TXT : '删除放款单',
		LOANINVOCE_DEL_BTN_FASTKEY : '',
		LOANINVOCE_DEL_TIP_BTN_TXT :'删除放款单',
		
		LOANINVOCE_DATEIL_BTN_TXT : '打印放款单',
		LOANINVOCE_DATEIL_BTN_FASTKEY : '',
		LOANINVOCE_DATEIL_TIP_BTN_TXT :'打印放款单',
		
		/*抵押合同*/
		MORTCONTRACT_ADD_TXT : '添加抵押合同',
		MORTCONTRACT_ADD_BTN_FASTKEY : '',
		MORTCONTRACT_ADD_TIP_BTN_TXT :'添加抵押同',
		
		MORTCONTRACT_EDIT_BTN_TXT : '编辑抵押合同',
		MORTCONTRACT_EDIT_BTN_FASTKEY : '',
		MORTCONTRACT_EDIE_TIP_BTN_TXT :'编辑抵押合同',
		
		MORTCONTRACT_DEL_BTN_TXT : '删除抵押合同',
		MORTCONTRACT_DEL_BTN_FASTKEY : '',
		MORTCONTRACT_DEL_TIP_BTN_TXT :'删除抵押合同',
		
		MORTCONTRACT_SC_BTN_TXT : '生成抵押合同',
		MORTCONTRACT_SC_BTN_FASTKEY : '',
		MORTCONTRACT_SC_TIP_BTN_TXT :'生成抵押合同',
		
		MORTCONTRACT_DATEIL_BTN_TXT : '打印抵押合同',
		MORTCONTRACT_DATEIL_BTN_FASTKEY : '',
		MORTCONTRACT_DATEIL_TIP_BTN_TXT :'打印抵押合同',
		
		MORTCONTRACT_DOWNLOAD_BTN_TXT : '抵押合同模板下载',
		MORTCONTRACT_DOWNLOAD_BTN_FASTKEY : '',
		MORTCONTRACT_DOWNLOAD_TIP_BTN_TXT :'抵押合同模板下载',
		/*质押合同*/
		PLECONTRACT_ADD_TXT : '添加质押合同',
		PLECONTRACT_ADD_BTN_FASTKEY : '',
		PLECONTRACT_ADD_TIP_BTN_TXT :'添加质押同',
		
		PLECONTRACT_EDIT_BTN_TXT : '编辑质押合同',
		PLECONTRACT_EDIT_BTN_FASTKEY : '',
		PLECONTRACT_EDIE_TIP_BTN_TXT :'编辑质押合同',
		
		PLECONTRACT_DEL_BTN_TXT : '删除质押合同',
		PLECONTRACT_DEL_BTN_FASTKEY : '',
		PLECONTRACT_DEL_TIP_BTN_TXT :'删除质押合同',
		
		PLECONTRACT_SC_BTN_TXT : '生成质押合同',
		PLECONTRACT_SC_BTN_FASTKEY : '',
		PLECONTRACT_SC_TIP_BTN_TXT :'生成质押合同',
		
		PLECONTRACT_DATEIL_BTN_TXT : '打印质押合同',
		PLECONTRACT_DATEIL_BTN_FASTKEY : '',
		PLECONTRACT_DATEIL_TIP_BTN_TXT :'打印质押合同',
		
		PLECONTRACT_DOWNLOAD_BTN_TXT : '质押合同模板下载',
		PLECONTRACT_DOWNLOAD_BTN_FASTKEY : '',
		PLECONTRACT_DOWNLOAD_TIP_BTN_TXT :'质押合同模板下载',
		
		FUNDSWATER_DATEIL_BIN_TXT : "资金流水明细",
		FUNDSWATER_DATEIL_BTN_FASTKEY : "",
		FUNDSWATER_DATEIL_TIP_BTN_TXT  : "资金流水明细",
		
		LOANINVOCE_DATEIL_BTN_TXT : '放款单详情',
		LOANINVOCE_DATEIL_BTN_FASTKEY : "",
		LOANINVOCE_DATEIL_TIP_BTN_TXT : "放款单详情",
		
		PANLE_EXCEL_BTN_TXT : '还款计划导出',
		PANLE_EXCEL_BTN_FASTKEY : "",
		PANLE_EXCEL__TIP_BTN_TXT : "放款单详情",
		
		//展期协议书
		EXTCONTRACT_ADD_BTN_TXT:"添加展期申请单",
		EXTCONTRACT_ADD_BTN_FASTKEY :'',
		EXTCONTRACT_ADD_BTN_TIP_TXT:"添加展期申请单",
		
		EXTCONTRACT_EDIT_BTN_TXT:"编辑展期协议书",
		EXTCONTRACT_EDIT_BTN_FASTKEY :'',
		EXTCONTRACT_EDIT_BTN_TIP_TXT:"编辑展期协议书",
		
		EXTCONTRACT_SAVE_BTN_TXT:"保存展期协议书",
		EXTCONTRACT_SAVE_BTN_FASTKEY :'',
		EXTCONTRACT_SAVE_BTN_TIP_TXT:"保存展期协议书",
		
		EXTCONTRACT_DETAIL_BTN_TXT : '展期详情',
		EXTCONTRACT_DETAIL_BTN_FASTKEY : '',
		EXTCONTRACT_DETAIL_BTN_TIP_TXT : '展期详情',
		
		EXTPLAN_EXCEL_BTN_TXT : '展期还款计划导出',
		EXTPLAN_EXCEL_BTN_FASTKEY : '',
		EXTPLAN_EXCEL_BTN_TIP_TXT : '展期还款计划导出',
		
		AUDIT_LOOK_DETAIL_BTN_TXT : '查看详情',
		AUDIT_LOOK_DETAIL_BTN_FASTKEY : '',
		AUDIT_LOOK_DETAIL_TIP_TXT : '查看详情',
		
		/*******----------- OA 系统 CODE START -------------******/
		/*--> 查看配置 <--*/
		VIEW_BTN_TXT : '查看',
		VIEW_FASTKEY : 'Ctrl+M',
		VIEW_CLS : 'page_detail',
		VIEW_TIP_BTN_TXT : '查看[Ctrl+X]',
		
		/*--->商系管理<---*/
		Trad_ADD_BTN_TXT : '添加分类',
		Trad_ADD_BTN_FASTKEY : '',
		Trad_ADD_BTN_CLS : 'page_add',
		Trad_ADD_TIP_BTN_TXT :'添加分类',
		
		Trad_EDIT_BTN_TXT : '编辑分类',
		Trad_EDIT_BTN_FASTKEY : '',
		Trad_EDIT_BTN_CLS : 'page_edit',
		Trad_EDIT_TIP_BTN_TXT :'编辑分类',
	
		Trad_ENABLED_BTN_TXT : '启用分类',
		Trad_ENABLED_BTN_FASTKEY : '',
		Trad_ENABLED_BTN_CLS : 'page_enabled',
		Trad_ENABLED_TIP_BTN_TXT :'启用分类',
		
		Trad_DISABLED_BTN_TXT : '禁用分类',
		Trad_DISABLED_BTN_FASTKEY : '',
		Trad_DISABLED_BTN_CLS : 'page_disabled',
		Trad_DISABLED_TIP_BTN_TXT :'禁用分类',
		
		Trad_DEL_BTN_TXT : '删除分类',
		Trad_DEL_BTN_FASTKEY : '',
		Trad_DEL_BTN_CLS : 'page_delete',
		Trad_DEL_TIP_BTN_TXT :'删除分类',
		
		
		/*--->分区管理<---*/
		Partition_ADD_BTN_TXT : '添加分区',
		Partition_ADD_BTN_FASTKEY : '',
		Partition_ADD_BTN_CLS : 'page_add',
		Partition_ADD_TIP_BTN_TXT :'添加分区',
		
		Partition_EDIT_BTN_TXT : '编辑分区',
		Partition_EDIT_BTN_FASTKEY : '',
		Partition_EDIT_BTN_CLS : 'page_edit',
		Partition_EDIT_TIP_BTN_TXT :'编辑分区',
	
		Partition_ENABLED_BTN_TXT : '启用分区',
		Partition_ENABLED_BTN_FASTKEY : '',
		Partition_ENABLED_BTN_CLS : 'page_enabled',
		Partition_ENABLED_TIP_BTN_TXT :'启用分区',
		
		Partition_DISABLED_BTN_TXT : '禁用分区',
		Partition_DISABLED_BTN_FASTKEY : '',
		Partition_DISABLED_BTN_CLS : 'page_disabled',
		Partition_DISABLED_TIP_BTN_TXT :'禁用分区',
		
		Partition_DEL_BTN_TXT : '删除分区',
		Partition_DEL_BTN_FASTKEY : '',
		Partition_DEL_BTN_CLS : 'page_delete',
		Partition_DEL_TIP_BTN_TXT :'删除分区',
		
		/*--->门店图片<---*/
		UPLOAD_IMG_LABEL_TEXT:'上传图片',
		DELETE_IMG_LABEL_TEXT:'删除图片',
		CLOSE_IMG_LABEL_TEXT:'关闭',
		
		//门店数据
		STORE_NUMBER_LABEL_TEXT:'门店编号',
		STORE_NAME_LABEL_TEXT:'门店名称',
		
		/*--> 提成方案按钮配置 <--*/	
		ADD_DEDUCT_PLAN_BTN_TXT : '添加提成方案',
		ADD_DEDUCT_PLAN_CLS : 'page_add',
		ADD_DEDUCT_PLAN_TIP_BTN_TXT :'添加提成方案',
		
		EDIT_DEDUCT_PLAN_BTN_TXT : '编辑提成方案',
		EDIT_DEDUCT_PLAN_CLS : 'page_edit',
		EDIT_DEDUCT_PLAN_TIP_BTN_TXT :'编辑提成方案',
		
		DELETE_DEDUCT_PLAN_BTN_TXT : '删除提成方案',
		DELETE_DEDUCT_PLAN_CLS : 'page_delete',
		DELETE_DEDUCT_PLAN_TIP_BTN_TXT :'删除提成方案',
		
		/*--> 专柜按钮配置 <--*/	
		ADD_CABINET_PLAN_BTN_TXT : '添加专柜',
		ADD_CABINET_PLAN_CLS : 'page_add',
		ADD_CABINET_PLAN_TIP_BTN_TXT :'添加专柜',
		
		EDIT_CABINET_PLAN_BTN_TXT : '编辑专柜',
		EDIT_CABINET_PLAN_CLS : 'page_edit',
		EDIT_CABINET_PLAN_TIP_BTN_TXT :'编辑专柜',
		
		DELETE_CABINET_PLAN_BTN_TXT : '删除专柜',
		DELETE_CABINET_PLAN_CLS : 'page_delete',
		DELETE_CABINET_PLAN_TIP_BTN_TXT :'删除专柜',
		
			/*--> 新闻配置《》 <--*/	
		WHOLE_NEWS_PLAN_BTN_TXT : '全部新闻',
		WHOLE_NEWS_PLAN_TIP_BTN_TXT :'全部新闻',
		
		UNREAD_NEWS_PLAN_BTN_TXT : '未读新闻',
		UNREAD_NEWS_PLAN_TIP_BTN_TXT :'未读新闻',
		
		QUERY_NEWS_PLAN_BTN_TXT : '新闻查询',
		QUERY_NEWS_CLS : 'page_query',
		QUERY_NEWS_PLAN_TIP_BTN_TXT :'新闻查询',
		
		MANAGE_NEWS_PLAN_BTN_TXT : '新闻管理',
		MANAGE_NEWS_PLAN_TIP_BTN_TXT :'新闻管理',
		
		CREATE_NEWS_PLAN_BTN_TXT : '添加新闻',
		CREATE_NEWS_BTN_CLS : 'page_add',
		CREATE_NEWS_PLAN_TIP_BTN_TXT :'添加新闻',
		
		NEWS_EDIT_BTN_TXT : '编辑新闻',
		NEWS_EDIT_BTN_FASTKEY : '',
		NEWS_EDIT_BTN_CLS : 'page_edit',
		NEWS_EDIT_TIP_BTN_TXT :'编辑新闻',
		
		NEWS_ENABLED_BTN_TXT : '发布新闻',
		NEWS_ENABLED_BTN_FASTKEY : '',
		NEWS_ENABLED_BTN_CLS : 'page_enabled',
		NEWS_ENABLED_TIP_BTN_TXT :'发布新闻',
		
		NEWS_DISABLED_BTN_TXT : '取消发布',
		NEWS_DISABLED_BTN_FASTKEY : '',
		NEWS_DISABLED_BTN_CLS : 'page_disabled',
		NEWS_DISABLED_TIP_BTN_TXT :'取消发布',
		
		NEWS_DEL_BTN_TXT : '删除新闻',
		NEWS_DEL_BTN_FASTKEY : '',
		NEWS_DEL_BTN_CLS : 'page_delete',
		NEWS_DEL_TIP_BTN_TXT :'删除新闻',
		
		NEWS_DETAIL_BTN_TXT : '查看详情',
		NEWS_DETAIL_BTN_FASTKEY : '',
		NEWS_DETAIL_BTN_CLS : 'page_detail',
		NEWS_DETAIL_TIP_BTN_TXT :'查看详情',
		
		NEWS_TOP_BTN_TXT : '置顶',
		NEWS_TOP_BTN_FASTKEY : '',
		NEWS_TOP_BTN_CLS : 'page_detail',
		NEWS_TOP_TIP_BTN_TXT :'置顶',
		
		NEWS_NOTOP_BTN_TXT : '取消置顶',
		NEWS_NOTOP_BTN_FASTKEY : '',
		NEWS_NOTOP_BTN_CLS : 'page_detail',
		NEWS_NOTOP_TIP_BTN_TXT :'取消置顶',
		
		NEWS_COMMENT_BTN_TXT : '评论',
		NEWS_COMMENT_BTN_FASTKEY : '',
		NEWS_COMMENT_BTN_CLS : 'page_detail',
		NEWS_COMMENT_TIP_BTN_TXT :'评论',
		
		/*--> 公告配置《》 <--*/	
		WHOLE_BULLETIN_PLAN_BTN_TXT : '全部公告',
		WHOLE_BULLETIN_PLAN_TIP_BTN_TXT :'全部公告',
		
		UNREAD_BULLETIN_PLAN_BTN_TXT : '未读公告',
		UNREAD_BULLETIN_PLAN_TIP_BTN_TXT :'未读公告',
		
		QUERY_BULLETIN_PLAN_BTN_TXT : '公告查询',
		QUERY_BULLETIN_CLS : 'page_query',
		QUERY_BULLETIN_PLAN_TIP_BTN_TXT :'公告查询',
		
		MANAGE_BULLETIN_PLAN_BTN_TXT : '公告管理',
		MANAGE_BULLETIN_PLAN_TIP_BTN_TXT :'公告管理',
		
		CREATE_BULLETIN_PLAN_BTN_TXT : '添加公告',
		CREATE_BULLETIN_BTN_CLS : 'page_add',
		CREATE_BULLETIN_PLAN_TIP_BTN_TXT :'添加公告',
		
		BULLETIN_EDIT_BTN_TXT : '编辑公告',
		BULLETIN_EDIT_BTN_FASTKEY : '',
		BULLETIN_EDIT_BTN_CLS : 'page_edit',
		BULLETIN_EDIT_TIP_BTN_TXT :'编辑公告',
		
		BULLETIN_ENABLED_BTN_TXT : '发布公告',
		BULLETIN_ENABLED_BTN_FASTKEY : '',
		BULLETIN_ENABLED_BTN_CLS : 'page_enabled',
		BULLETIN_ENABLED_TIP_BTN_TXT :'发布公告',
		
		BULLETIN_DISABLED_BTN_TXT : '取消发布',
		BULLETIN_DISABLED_BTN_FASTKEY : '',
		BULLETIN_DISABLED_BTN_CLS : 'page_disabled',
		BULLETIN_DISABLED_TIP_BTN_TXT :'取消发布',
		
		BULLETIN_DEL_BTN_TXT : '删除公告',
		BULLETIN_DEL_BTN_FASTKEY : '',
		BULLETIN_DEL_BTN_CLS : 'page_delete',
		BULLETIN_DEL_TIP_BTN_TXT :'删除公告',
		
		BULLETIN_DETAIL_BTN_TXT : '查看详情',
		BULLETIN_DETAIL_BTN_FASTKEY : '',
		BULLETIN_DETAIL_BTN_CLS : 'page_detail',
		BULLETIN_DETAIL_TIP_BTN_TXT :'查看详情',
		
		BULLETIN_TOP_BTN_TXT : '置顶',
		BULLETIN_TOP_BTN_FASTKEY : '',
		BULLETIN_TOP_BTN_CLS : 'page_detail',
		BULLETIN_TOP_TIP_BTN_TXT :'置顶',
		
		BULLETIN_NOTOP_BTN_TXT : '取消置顶',
		BULLETIN_NOTOP_BTN_FASTKEY : '',
		BULLETIN_NOTOP_BTN_CLS : 'page_detail',
		BULLETIN_NOTOP_TIP_BTN_TXT :'取消置顶',
		
			/*--->工资方案管理<---*/
		WagePlan_ADD_BTN_TXT : '添加工资方案',
		WagePlan_ADD_BTN_FASTKEY : '',
		WagePlan_ADD_BTN_CLS : 'page_add',
		WagePlan_ADD_TIP_BTN_TXT :'添加工资方案',
		
		WagePlan_EDIT_BTN_TXT : '编辑工资方案',
		WagePlan_EDIT_BTN_FASTKEY : '',
		WagePlan_EDIT_BTN_CLS : 'page_edit',
		WagePlan_EDIT_TIP_BTN_TXT :'编辑工资方案',
	
		WagePlan_ENABLED_BTN_TXT : '启用工资方案',
		WagePlan_ENABLED_BTN_FASTKEY : '',
		WagePlan_ENABLED_BTN_CLS : 'page_enabled',
		WagePlan_ENABLED_TIP_BTN_TXT :'启用工资方案',
		
		WagePlan_DISABLED_BTN_TXT : '禁用工资方案',
		WagePlan_DISABLED_BTN_FASTKEY : '',
		WagePlan_DISABLED_BTN_CLS : 'page_disabled',
		WagePlan_DISABLED_TIP_BTN_TXT :'禁用工资方案',
		
		WagePlan_DEL_BTN_TXT : '删除工资方案',
		WagePlan_DEL_BTN_FASTKEY : '',
		WagePlan_DEL_BTN_CLS : 'page_delete',
		WagePlan_DEL_TIP_BTN_TXT :'删除工资方案',
		
		
		/*--->方案项管理<---*/
		PlanItem_ADD_BTN_TXT : '添加方案项',
		PlanItem_ADD_BTN_FASTKEY : '',
		PlanItem_ADD_BTN_CLS : 'page_add',
		PlanItem_ADD_TIP_BTN_TXT :'添加方案项',
		
		PlanItem_EDIT_BTN_TXT : '编辑方案项',
		PlanItem_EDIT_BTN_FASTKEY : '',
		PlanItem_EDIT_BTN_CLS : 'page_edit',
		PlanItem_EDIT_TIP_BTN_TXT :'编辑方案项',
	
		PlanItem_ENABLED_BTN_TXT : '启用方案项',
		PlanItem_ENABLED_BTN_FASTKEY : '',
		PlanItem_ENABLED_BTN_CLS : 'page_enabled',
		PlanItem_ENABLED_TIP_BTN_TXT :'启用方案项',
		
		PlanItem_DISABLED_BTN_TXT : '禁用方案项',
		PlanItem_DISABLED_BTN_FASTKEY : '',
		PlanItem_DISABLED_BTN_CLS : 'page_disabled',
		PlanItem_DISABLED_TIP_BTN_TXT :'禁用方案项',
		
		PlanItem_DEL_BTN_TXT : '删除方案项',
		PlanItem_DEL_BTN_FASTKEY : '',
		PlanItem_DEL_BTN_CLS : 'page_delete',
		PlanItem_DEL_TIP_BTN_TXT :'删除方案项',
		
		
			
		DETAIL_EMP_BTN_TXT : '查看员工详情',
		DETAIL_EMP_CLS : 'page_detail',
		DETAIL_EMP_TIP_BTN_TXT :'查看员工详情'
		
		
		/*-->添加费用报销单<--*/	
		
		/*******----------- OA 系统 CODE END -------------******/
		
		
};

//**************** -- 本地下拉框或单选框或复选框数据源国际化 -- ******************//
var Lcbo_dataSource = {
	voustatus_datas :[["0","凭证生成成功"],["1","凭证生成失败"]],
	vouerrCode_datas :[["0","无错误"],["1","服务器连接失败"],["2","用户账号未映射"],["3","找不到对应的凭证模板"],["4","系统业务异常"],["5","业务系统与财务接口未映射"],["6","财务接口未配置或已禁用"],["7","凭证模板中分录未配置或已禁用"]],
	/**
	 * 流程审批状态
	 * Lcbo_dataSource.auditStatus_datas
	 * @type 
	 */
	auditStatus_datas :[["1","审批中"],["2","审批通过"],["3","审批未通过"]],
	/**
	 * 流程全部审批状态
	 * Lcbo_dataSource.auditStatus_datas
	 * @type 
	 */
	auditStatusAll_datas :[["0","未提交"],["1","审批中"],["2","审批通过"],["3","审批未通过"]],
	/**
	 * 
	 * @type String
	 */
	allDispTxt : "--请选择--",
	/**
	 *  获取逻辑运算符下拉框数据源	
	 * @type 	
	 */
	eqOp_datas : [["=","等于"],[">","大于"],["<","小于"],[">=","大于等于"],["<=","小于等于"],["<>","不等于"]],
	/**
	 * 获取一个包含有 "--请选择--" 的下拉框数据源
	 * 例：Lcbo_dataSource.getAllDs(Lcbo_dataSource.rate_types_datas);
	 * @param {} dsArr	下拉框数据源
	 * @param {} allLbl	为空元素项显示名。例：“请选择”
	 */
	getAllDs : function(dsArr,allLbl){
		var dispLblArr = ["",this.allDispTxt];
		if(allLbl) dispLblArr[1] = allLbl;
		//dsArr.unshift(dispLblArr);
		var newDsArr = [dispLblArr].concat(dsArr);
		return newDsArr;
	}
}; 

//**************** -- Grid render 数据源国际化 -- ******************//
/**
 * OA 系统转用渲染器对象
 * @type 
 */
var OaRender_dataSource = {
	 /**
     * 基础数据 render 
     * OaRender_dataSource.gvlistRender('100003',1);
     * @param {} val
     * @return {String}
     */
    gvlistRender : function(restypeId,val) {
     	var row = Cmw.getRenderDispVal(REGISTER.GvlistDatas,restypeId,val);
     	if(!row || row.length<2) return '暂未设置';
        return row[1];
    },
    /**
	 * OaRender_dataSource.flowStatusRender(val);
	 * 流程状态
	 * @param {} val
	 * @return {}
	 */
	flowStatusRender : function(val){
		switch (val) {
	        case "0":{
	            val = '未提交';
	            break;
	        }
	        case "1":{
	            val = '审批中';
	            break;
	        }
	        case "2":{
	            val = '审批通过';
	            break;
	        }
	        case "3":{
	            val = '审批未通过';
	            break;
	        }
		 }
        return val;
	},
     /**
     * moneyRender 涉及金钱的转换
     * @param {} val
     * @return {}
     */
    moneyRender :function(val){
   		return (val && val>0) ?Cmw.getThousandths(val)+'元' : '';
	}, 
    /**
     * moneyRender 涉及金钱的转换
     * @param {} val
     * @return {}
     */
    wmoneyRender :function(val){
   		return (val && val>0) ?Cmw.getThousandths(val)+'万元' : '';
	},
	/**
	 * OaRender_dataSource.rendTypeRender(val);
	 * 借款类型
	 * @param {} val
	 * @return {}
	 */
	rendTypeRender : function(val){
		switch (val) {
	        case "1":{
	            val = '备用金';
	            break;
	        }
	        case "2":{
	            val = '借款';
	            break;
	        }
		 }
        return val;
	},
	/**
	 * OaRender_dataSource.sexRender(val);
	 * 错误代码
	 * @param {} val
	 * @return {}
	 */
	sexRender : function(val){
		switch (val) {
			 case "-1":{
	            val = '未知';
	            break;
	        }case "1":{
	            val = '男';
	            break;
	        }case "2":{
	            val = '女';
	            break;
	        }
		 }
        return val;
	},
	/**
	 * OaRender_dataSource.empTypeRender(val);
	 * 错误代码
	 * @param {} val
	 * @return {}
	 */
	empTypeRender : function(val){
		switch (val) {
	        case "1":{
	            val = '公司员工';
	            break;
	        }
	        case "2":{
	            val = '临促';
	            break;
	        }
		 }
        return val;
	},
	/**
     * 数据访问级别权限
     * @param {} val
     * @return {}
     */
    dataLevelRender : function(val){
		switch (val) {
			case "-1":
				val = "无";
				break;
			case "0":
				val = "本人数据";
				break;
			case "1":
				val = "自定义用户数据";
				break;
			case "2":
				val = "本部门数据";
				break;
			case "4":
				val = "自定义部门";
				break;
			case "7":
				val = "无限制";
				break;
			default:
				val = "暂未设置";
				break;
			}
		return val;
    },
	 /**
     * 门店访问权限
     * @param {} val
     * @return {}
     */
    ssLevelRender : function(val){
		switch (val) {
			case "-1":
				val = "无门店访问权限";
				break;
			case "1":
				val = "本门店数据";
				break;
			case "2":
				val = "多门店数据";
				break;
			default:
				val = "暂未设置";
				break;
			}
		return val;
    }
 }
/**
 * 
 * @type 
 */
var Render_dataSource = {
	 /**
     * 基础数据 render DEL
     * Render_dataSource.gvlistRender('100003',1);
     * @param {} val
     * @return {String}
     */
    gvlistRender : function(restypeId,val) {
     	var row = Cmw.getRenderDispVal(REGISTER.GvlistDatas,restypeId,val);
     	if(!row || row.length<2) return '暂未设置';
        return row[1];
    },
      /**
     * 获取参与者类型
     * Render_dataSource.acTypeRender
     * @param {} val
     * @return {String}
     */
    acTypeRender : function(val){
    	if(!val) return "";
     	switch (val){
     		case '0' :{
     			val = "不需要参与者";
     			break;
     		}case '1' :{
     			val = "角色";
     			break;
     		}case '2' :{
     			val = "用户";
     			break;
     		}case '3' :{
     			val = "上一环节处理人";
     			break;
     		}case '4' :{
     			val = "上级领导";
     			break;
     		}case '5' :{
     			val = "流程发起人";
     			break;
     		}
     	}
     	return val;
    },
    /**
     * moneyRender 涉及金钱的转换  DEL
     * @param {} val
     * @return {}
     */
    moneyRender :function(val){
   		return (val && val>0) ?Cmw.getThousandths(val)+'元' : '';
	}, 
    /**
     * moneyRender 涉及金钱的转换  DEL
     * @param {} val
     * @return {}
     */
    wmoneyRender :function(val){
   		return (val && val>0) ?Cmw.getThousandths(val)+'万元' : '';
	},
	/**
	 * 错误代码
	 * @param {} val
	 * @return {}
	 */
	voustatus : function(val){
		 switch (val) {
        case"0":
            val = '成功';
            break;
        case "1":
            val = '失败';
            break;
        }
        return val;
	},
	vouerrCode: function(val){
		 switch (val) {
        case"0":
            val = '无错误';
            break;
        case "1":
            val = '服务器连接失败';
            break;
        case"2":
            val = '用户账号未映射';
            break;
        case "3":
            val = '找不到对应的凭证模板';
            break;
        case"4":
            val = '系统业务异常';
            break;
        case "5":
            val = '业务系统与财务接口未映射';
            break;
        case"6":
            val = '财务接口未配置或已禁用';
            break;
        case "7":
            val = '凭证模板中分录未配置或已禁用';
            break;
        }
        return val;
	},
	/**
	 * 资金流水流水类型
	 * @param {} val
	 * @return {}
	 */
	waterTypeRenders : function(val){
		 switch (val) {
        case"1":
            val = '放款';
            break;
        case "2":
            val = '收款';
            break;
        }
        return val;
	},
	/**
	 * 
	 * @param {} val
	 * @return {}
	 */
	statussRenders : function(val){
		 switch (val) {
        case"0":
            val = '未核对';
            break;
        case "1":
            val = '已核对';
            break;
        }
        return val;
	},
	accountTyperenderer: function(val) {
        switch (val) {
        case "1":
            val = '基本结算帐户';
            break;
        case "2":
            val = '一般结算帐户';
            break;
        case "3":
            val = '外汇帐户';
            break;
        }
        return val;
    },
	accountTyperDetailenderer: function(val) {
        switch (val) {
        case 1:
            val = '基本结算帐户';
            break;
        case 2:
            val = '一般结算帐户';
            break;
        case 3:
            val = '外汇帐户';
            break;
        }
        return val;
    },
    /**
     * 个性化设置中的控件类型
     * @param {} val
     * @return {}
     */
    controlTypeRender:function(val){
    	switch (val){
			case "0": val="文本框";break;
			case "1": val="整数输入框";break;
			case "2": val="小数点输入框";break;
			case "3": val="金额输入框";break;
			case "4": val="日期输入框";break;
			case "5": val="本地下拉框";break;
			case "6": val="远程下拉框";break;
			case "7": val="多行文本框";break;
			case "8": val="单选框";break;
			case "9": val="复选框";break;
		}
		return val;
    },
     /**
     * 实收金额日志中业务标识
     * @param {} val
     * @return {}
     */
    bussTagRender:function(val){
    	switch (val){
			case "0": val="放款";break;
			case "1": val="放款手续费";break;
			case "2": val="正常收款";break;
			case "3": val="正常收款豁免";break;
			case "4": val="预收结清";break;
			case "5": val="表外核销";break;
			case "6": val="表内愈期收款";break;
			case "7": val="表外愈期收款";break;
			case "8": val="逾期豁免";break;
			case "9": val="提前还款";break;
			case "10": val="提前还款豁免";break;
		}
		return val;
    },
    /**
     * 实收金额日志中业务标识
     * @param {} val
     * @return {}
     */
   categoryRender:function(val){
    	switch (val){
			case "0": val="放款";break;
			case "1": val="放款手续费";break;
			case "2": val="还款计划";break;
			case "3": val="预收";break;
		}
		return val;
    },
    /**
     * 年龄后面的单位
     * @param {} val
     * @return {}
     */
    ageRender : function(val){
    	return val  = (val && val>0) ? val+'岁': "";
    },
    /**
     * 单位人
     * @param {} val
     * @return {}
     */
    manCountRender : function(val){
    	return val  = (val && val>0) ? val+'人': "";
    },
    /**
     * 单位年
     * @param {} val
     * @return {}
     */
    houseYearRender : function(val){
    	return val  = (val && val>0) ? val+'年': "";
    },
    /**
     * 单位日
     * @param {} val
     * @return {}
     */
    payDayRender : function(val){
    	return val  = (val && val>0) ? val+'日': "";
    },
    /**
     * 客户类型
     * @param {} val
     * @return {}
     */
    custTypeRender: function(val) {
        switch (val) {
        case "0":
            val = '个人';
            break;
         case "1":
            val = '国企';
            break;
            }
         return val;
     },
    /**
     * 按揭和一次性
     */
    buyTypeRender: function(val){
    	switch (val) {
	        case "0":
	            val = "按揭";
	            break;
	       	case "1":
	       	val = "一次性";
	            break;
	        }
	        return val;
	    },
	/**
	 * 滚动方式渲染器
	 * Render_dataSource.dispTypeRender(val);
	 * @param {} val
	 */
	dispTypeRender : function(val){
		switch (val) {
			case "1":
				val = "不滚动";
				break;
			case "2":
				val = "上下滚动";
				break;
			case "3":
				val = "上下滚动";
				break;
			default:
				val = "暂未设置";
				break;
			}
		return val;
	},
	/**
	 * 默认模块渲染器
	 * Render_dataSource.isdefaultRender(val);
	 * @param {} val
	 */
	isdefaultRender : function(val){
		switch (val) {
			case "1":
				val = "默认";
				break;
			case "2":
				val = "非默认";
				break;
			default:
				val = "暂未设置";
				break;
			}
		return val;
	},
	/**
	 * 显示更多渲染器
	 * Render_dataSource.ismoreRender(val);
	 * @param {} val
	 */
	ismoreRender : function(val){
		switch (val) {
			case "1":
				val = "是";
				break;
			case "2":
				val = "否";
				break;
			default:
				val = "暂未设置";
				break;
			}
		return val;
	},
	isRequiredRender:function(val){
		switch (val) {
			case "0":
				val = "保留原设置";
				break;
			case "1":
				val = "是";
				break;
			case "2":
				val = "否";
				break;
			default:
				val = "暂未设置";
				break;
			}
		return val;
	},
	FormdiyRender:function(val){
		switch (val) {
			case "0":
				val = "否";
				break;
			case "1":
				val = "是";
				break;
			}
		return val;
	},
	isRequiredRender:function(val){
		switch (val) {
			case "0":
				val = "否";
				break;
			case "1":
				return "<span style='color:red;font-weight:bold;'>是</span>";
				break;
			}
		return val;
	},
	isDefaultRenders:function(val){
		switch (val) {
			case 0:
				val = "否";
				break;
			case 1:
				val = "是";
				break;
			}
		return val;
	},
	/**
	 * 加载方式 渲染器
	 * Render_dataSource.loadTypeRender(val);
	 * @param {} val
	 */
	loadTypeRender : function(val){
		switch (val) {
			case "1":
				val = "sql";
				break;
			case "2":
				val = "hql";
				break;
			case "3":
				val = "业务方法";
				break;
			default:
				val = "暂未设置";
				break;
			}
		return val;
	},
    /**
	 * 可用标识渲染器
	 * Render_dataSource.isenabledRender(val);
	 * @param {} val
	 */
	isenabledRender : function(val){
		switch (val) {
			case "0":
				val = "<span>禁用   </span><img src='extlibs/ext-3.3.0/resources/images/extend/disable.png' />";
				break;
			case "1":
				val = "可用";
				break;
			default:
				val = "暂未设置";
				break;
			}
		return val;
	},
	/**
	 * 客户类型
	 * @param {} val
	 * @return {}
	 * Render_dataSource.custTypeRender(val);
	 */
	custTypeRender : function(val) {
	    switch(val){
		  	case '0' :{
		  		val = '个人客户';
		  		break;
		  	}case '1' :{
		  		val = '企业客户';
		  		break;
		  	}
		}
        return val;
    },
	/**
	 * 客户级别
	 * @param {} val
	 * @return {}
	 * Render_dataSource.sexRender(0);
	 */
	custLevelRender : function(val) {
	    switch(val){
		  	case '0' :{
		  		val = '黑名单客户';
		  		break;
		  	}case '1' :{
		  		val = '潜在客户';
		  		break;
		  	}case '2' :{
		  		val = '正式客户';
		  		break;
		  	}case '3' :{
		  		val = '优质客户';
		  		break;
		  	}case '4'	 :{
		  		val = '授信客户';
		  		break;
		  	}
		}
        return val;
    },
    /**
     * 
     * @param {} val
     * @return {}
     */
    systypeRenderer :function(val){
    	switch (val) {
				case "0":
					val = "试运行系统";
					break;
				case "1":
					val = "正式上线系统";
					break;
				case "2":
					val = "SAS在线系统";
					break;
				case "3":
					val = "同心日在线试用系统";
					break;
				default:
					val = "暂未设置";
					break;
				}
				return val;
    },
    typeupRenderer : function(val){
    	switch (val) {
				case "0":
					val = "自动更新";
					break;
				case "1":
					val = "手动更新";
					break;
				default:
					val = "暂未设置";
					break;
				}
		return val;
    },
    cardTypeRender : function(val){
   		 switch(val){
	    		case "7": 
	    		val = "身份证";
	    		break;
	    		case "8": 
	    		val = "驾驶证";
	    		break;
	    		case "9": 
	    		val = "护照";
	    		break;
	    	}
	    	return val;
	    },
	/**
	 * 性别
	 * @param {} val
	 * @return {}
	 * Render_dataSource.sexRender(0);
	 */
	 sexRender: function(val) {
        switch (val) {
	        case "0":
	            val = '男';
	            break;
	        case "1":
	            val = '女';
	            break;
        }
        return val;
    },
    /**
	 * 详情页面性别
	 * @param {} val
	 * @return {}
	 * Render_dataSource.sexRender(0);
	 */
	 sexDetailRender: function(val) {
        switch (val) {
	        case 0:
	            val = '男';
	            break;
	        case 1:
	            val = '女';
	            break;
        }
        return val;
    },
    /**
	 * 户口性质
	 * @param {} val
	 * @return {}
	 * Render_dataSource.accNatureRender(0);
	 */
	accNatureRender : function(val) {
		if(!val && val != 0) return  '暂未设置';
		val += '';
        switch (val) {
	        case '0':
	            val = '常住';
	            break;
	        case '1':
	            val = '暂住';
	            break;
			default : {
	        	val = '暂未设置';
	        }
        }
        return val;
    },
	accNatureDetailRender : function(val) {
        switch (val) {
	        case 0:
	            val = '常住';
	            break;
	        case 1:
	            val = '暂住';
	            break;
        }
        return val;
    },
     /**
	 * 贷款申请单状态
	 * @param {} val
	 * @return {}
	 * Render_dataSource.auditStateRender(0);
	 */
	applyStateRender : function(val) {
		if(!val && val != 0) return  '暂未设置';
		val += '';
        switch (val) {
        	case '0':
	            val = '未申请';
	            break;
	        case '1':
	            val = '审批中';
	            break;
	        case '2':
	            val = '已签合同';
	            break;
			case '3':
	            val = '已放款';
	            break;
	         case '4':
	            val = '收款中';
	            break;
            case '14':
	            val = '审批不通过';
	            break;
            case '15':
	            val = '已结束';
	            break;
			default : {
	        	val = '暂未设置';
	        }
        }
        return val;
    },
    /**
	 * 利率类型
	 * @param {} val
	 * @return {}
	 * Render_dataSource.rateTypeRender(0);
	 */
	rateTypeRender : function(val) {
		if(!val && val != 0) return  '暂未设置';
		val += '';
        switch (val) {
	        case '1':
	            val = '月利率';
	            break;
            case '2':
	            val = '日利率';
	            break;
            case '3':
	            val = '年利率';
	            break;
			default : {
	        	val = '暂未设置';
	        }
        }
        return val;
    },
     /**
	 * 管理费收取方式
	 * @param {} val
	 * @return {}
	 * Render_dataSource.mgrtypeRender(0);
	 */
	mgrtypeRender : function(val) {
		if(!val && val != 0) return  '暂未设置';
		val += '';
        switch (val) {
	        case '0':
	            val = '不收管理费';
	            break;
            case '1':
	            val = '按还款方式算法收取';
	            break;
			default : {
	        	val = '暂未设置';
	        }
        }
        return val;
    },
    
    /**
	 * 销售方式
	 * @param {} val
	 * @return {}
	 * Render_dataSource.sale_type_render;
	 */
	sale_type_render : function(val) {
        switch (val) {
	        case '1':
	            val = '联营';
	            break;
            case '2':
	            val = '购销';
	            break;
			default : {
	        	val = '暂未设置';
	        }
        }
        return val;
    },
      /**
	 * 是否预收息
	 * @param {} val
	 * @return {}
	 * Render_dataSource.isadvanceRender(0);
	 */
	isadvanceRender : function(val) {
		if(!val && val != 0) return  '暂未设置';
		val += '';
        switch (val) {
	        case '0':
	            val = '否';
	            break;
            case '1':
	            val = '是';
	            break;
			default : {
	        	val = '暂未设置';
	        }
        }
        return val;
    },
     /**
	 * 还款方式
	 * @param {} val
	 * @return {}
	 * Render_dataSource.accNatureRender(0);
	 */
	payTypeRender : function(val) {
		if(!val && val != 0) return  '暂未设置';
		val += '';
        switch (val) {
	        case 'P0001':
	            val = '按月付息，到期一次性还本';
	            break;
            case 'P0002':
	            val = '按月付息，分期还本';
	            break;
            case 'P0003':
	            val = '按季付息，到期一次性还本';
	            break;
	        case 'P0004':
	            val = '按月付息，分季还本';
	            break;
            case 'P0005':
	            val = '等额本金';
	            break;
            case 'P0006':
	            val = '等额本息';
	            break;
	        case 'P0007':
	            val = '到期一次还本付息';
	            break;
            case 'P0008':
	            val = '自定义还款付息';
	            break;
			default : {
	        	val = '暂未设置';
	        }
        }
        return val;
    },
    /**
     * 贷款期限渲染器
     * @param {} jsonData  记录对象 须包含 "yearLoan","monthLoan","dayLoan" 属性
     * @return {} 返回格式化后的年月日
     */
    loanLimitRender : function(jsonData){
    	var yearLoan = jsonData["yearLoan"];
	 	var monthLoan = jsonData["monthLoan"];
	 	var dayLoan = jsonData["dayLoan"];
	 	var arr = [];
	 	if(yearLoan && yearLoan>0){
	 		arr[arr.length] = yearLoan+'年';
	 	}
	 	if(monthLoan && monthLoan>0){
	 		arr[arr.length] = monthLoan+'个月';
	 	}
	 	if(dayLoan && dayLoan>0){
	 		arr[arr.length] = dayLoan+'天';
	 	}
       return (arr.length > 0) ? arr.join("") : "";
    },
	/**
	 * 扣收状态
	 * @param {} val
	 * @return {}
	 * Render_dataSource.planStatusRender(0);
	*/
	planStatusRender : function(val) {
        switch (val) {
        	case '0':
	            val = '未到还款日';
	            break;
	        case '1':
	            val = '部分收款';
	            break;
            case '2':
	            val = '结清';
	            break;
            case '3':
	            val = '预收';
	            break;
	        case '4':
	            val = '表内逾期';
	            break;
            case '5':
	            val = '表外逾期';
	            break;
            case '6':
	            val = '核销';
	            break;
	        case '7':
	            val = '表外核销结清';
	            break;
            case '8':
	            val = '部分提前还款';
	            break;
	        case '9':
	            val = '提前结清';
	            break;
	        case '10':
	            val = '其它期提前还款';
	            break;
//			default : {
//	        	val = '暂未设置';
//	        }
        }
        return val;
    },
    /**
	 * 豁免状态
	 * @param {} val
	 * @return {}
	 * Render_dataSource.exemptRender(0);
	*/
	exemptRender : function(val) {
        switch (val) {
        	case '0':
	            val = '未豁免';
	            break;
	        case '1':
	            val = '已豁免';
	            break;
//			default : {
//	        	val = '暂未设置';
//	        }
        }
        return val;
    },
    /**
     * 收款状态
     */
    statusRender : function(val){
    	 switch (val) {
        	case '0':
	            val = '未收';
	            break;
	        case '1':
	            val = '部分收款';
	            break;
            case '2':
	            val = '结清';
	            break;
//			default : {
//	        	val = '暂未设置';
//	        }
        }
        return val;
    }, 
    /**
     * 新闻审批状态
     */
    statusrenderer : function(val){
		 switch (val) {
        	case '0':
	            val = '未提交';
	            break;
	        case '1':
	            val = '审批中';
	            break;
            case '2':
	            val = '审批通过 ';
	            break;
	         case '3':
	            val = '审批通过';
	            break;
			default : {
	        	val = '审批未通过';
	        }
        }
        return val;	
	},
    /**
     * 最低金额配置审批状态
     */
    minAmountStatusRender : function(val){
    	 switch (val) {
        	case '0':
	            val = '未提交';
	            break;
	        case '1':
	            val = '审批中';
	            break;
            case '2':
	            val = '取消审批';
	            break;
	         case '3':
	            val = '审批通过';
	            break;
			default : {
	        	val = '暂未设置';
	        }
        }
        return val;
    },
    /**
     * 表内表外
     * Render_dataSource.inouttypeRender(val)
     */
    inouttypeRender : function(val){
    	 switch (val) {
        	case '0':
	            val = '表内';
	            break;
	        case '1':
	            val = '表外';
	            break;
			default : {
	        	val = '';
	        }
        }
        return val;
    },
    /**
     * 账户类型(公司帐户功能用到)
    */
    account_atypeRender : function(val){
    	 switch (val) {
        	case '0':
	            val = '通用账户';
	            break;
            case '1':
	            val = '扣收账户';
	            break;
	        case '2':
	            val = '预收款专户';
	            break;
	         case '3':
	            val = '豁免专户';
	            break;
			default : {
	        	val = '暂未设置';
	        }
        }
        return val;
    },
    /**
     * 利率类型(利率设置用到)
     * Render_dataSource.rate_typesRender(val)
    */
    rate_typesRender : function(val) {
        switch (val) {
        case '1':
            val = '贷款利率';
            break;
        case '2':
            val = '管理费率';
            break;
        case '3':
            val = '罚息利率';
            break;
        case '4':
            val = '滞纳金利率';
            break;
        case '5':
            val = '放款手续费率';
            break;
        case '6':
            val = '提前还款手续费率';
            break;
        }
        return val;
    },
    /**
     * 利率期限(利率设置用到)
     * Render_dataSource.rate_limitsRender(val)
    */
    rate_limitsRender : function(val) {
        switch (val) {
        case '1':
            val = '月利率';
            break;
        case '2':
            val = '日利率';
            break;
        case '3':
            val = '年利率';
            break;
        case '4':
            val = '无';
            break;
        }
        return val;
    },
    mgrtype_render : function(val){
    	switch (val) {
    		case '0' :
    			val = '不收管理费';
    			break ;
			case '1' :
				val = '按还息方式收取';
				break;
    	}
    	return val;
    },
   /**
     * 是否启用公式(利率设置用到)
     * Render_dataSource.rate_isFormulaRender(val)
    */
    rate_isFormulaRender : function(val) {
        switch (val) {
	        case '0':
	            val = '未配置公式';
	            break;
	        case '1':
	            val = '否';
	            break;
	          case '2':
	            val = '是';
	            break;
	        default:
				val = "暂未设置";
        }
        return val;
    },
    /**
     * Department表中部门类型（dtype）
     * Render_dataSource.dep_dtypeRender(val)
     */
    dep_dtypeRender : function(val) {
        switch (val) {
	        case '1':
	            val = '行政部门';
	            break;
	        case '2':
	            val = '营销部门';
	            break;
	        default:
				val = "";
        }
        return val;
    },
    /**
	 * 性别
	 * @param {} val
	 * @return {}
	 * Render_dataSource.sex_hrRender(0);
	 */
	 sex_hrRender: function(val) {
        switch (val) {
	        case "-1":
	            val = '未知';
	            break;
	        case "1":
	            val = '男';
	            break;
	        case "2":
	        	val="女";
	        	break;
	        default:
	        	val=""
        }
        return val;
    },
    
    /**
     * 员工状态
     * Render_dataSource.emp_statusRender(val)
     */
    emp_statusRender : function(val) {
        switch (val) {
	        case '1':
	            val = '在职';
	            break;
	        case '2':
	            val = '离职';
	            break;
	        default:
				val = "";
        }
        return val;
    },
    
     /**
     * 员工类型
     * Render_dataSource.empType(val)
     */
    emp_empType : function(val) {
        switch (val) {
	        case '1':
	            val = '正式员工';
	            break;
	        case '2':
	            val = '临促';
	            break;
	        default:
				val = "";
        }
        return val;
    },
    
    /**
     * 门店属性
     * Render_dataSource.stores_ptypeRender(val)
     */
    stores_ptypeRender : function(val) {
        switch (val) {
	        case '1':
	            val = '商场门店';
	            break;
	        case '2':
	            val = '独立店';
	            break;
	        default:
				val = "";
        }
        return val;
    },
    
     /**
     * 是否兼职导购
     * Render_dataSource.emp_ispartRender(val)
     */
    emp_ispartRender : function(val) {
        switch (val) {
        	 case '-1':
	            val = '无';
	            break;
	        case '1':
	            val = '是';
	            break;
	        case '2':
	            val = '否';
	            break;
	        default:
				val = "";
        }
        return val;
    },
    
    /**
     * 是否创建登陆账号
     * Render_dataSource.emp_iscreateRender(val)
     */
    emp_iscreateRender : function(val) {
        switch (val) {
	        case '1':
	            val = '是';
	            break;
	        case '2':
	            val = '否';
	            break;
	        default:
				val = "";
        }
        return val;
    },
    
    /**
     * 下拉树或下拉表格去掉##前（如：2##销售--->销售）
     * Render_dataSource.format_treeOrgrid(val)
     */
    format_treeOrGrid : function(val) {
    	if('string'==typeof(val)){
			if(val.indexOf("##")!=-1){
				val=val.split("##")[1];
			}
    	}
		return val;
	},
    
    
    /**
     * 提成方案业务分类
     * Render_dataSource.deductPlan_category(val)
     */
    deductPlan_category : function(val) {
        switch (val) {
	        case '1':
	            val = '按门店提成';
	            break;
	        case '2':
	            val = '按产品类型提成';
	            break;
	        case '3':
	            val = '按门店（销售部门）提成';
	            break;
	        default:
				val = "";
        }
        return val;
    },
    /**
     * 添加百分号
     * Render_dataSource.percent_add_render
     * @param {} val
     * @return {}
     */
    percent_add_render : function(val) {
    	return val+"%"; 
    },
     
    /**
     * 门店类型
     * Render_dataSource.stores_stypeRender(val)
     */
    stores_stypeRender : function(val) {
        switch (val) {
	        case '1':
	            val = '自营店';
	            break;
	        case '2':
	            val = '直营店';
	            break;
            case '3':
	            val = '加盟店';
	            break;
	        default:
				val = "";
        }
        return val;
    }
};
//**************** -- 业务界面UI国际化 -- ******************//
/**
 * 基础数据国际化
 * Ui_Gvlist.queryFrm.
 * Ui_Gvlist.appGrid.
 * @type 
 */
var Ui_Gvlist = {
	/**
	 * 查询面板字段国际化
	 * @type 
	 */
	queryFrm:{
		lbl_code : '编号',
		lbl_name : '名称'
	},
	/**
	 * 查询Grid列国际化
	 * @type 
	 */
	appGrid:{
		hd_code : '编号',
		hd_name : '名称'
	}
};

/**
 * 菜单资源国际化
 * 
 * @type 
 */
var UI_Menu = {
	appDefault : {
		code:'菜单编码',
		parentName:'父菜单',
		name:'菜单名称',
		leaf:'是否叶子', 
		iconCls:'菜单样式',
		isSystem:'是否系统菜单',
		biconCls:'系统大图标',
		jsArray:'URL',
		params:'附加参数',
		remark:'备注'
	}
}
