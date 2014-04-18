/**
 * 登录窗口国际化消息
 */
Msg_LoginWindow = {
	win_title:'Login TianSi onLine Platform',
	tab_one_title : 'Identity Authenticate',
	tab_two_title : 'About Current System',
	tab_two_html : 'TianSi onLine Platform V1.0 (TsCmw_CIP&reg)<br><br>' +
					' Copyright All &copy 2010  TianSi Workshop (TsCmw_CIP&reg) <br><br><b> Technical Support :  TianSi Technology </b><br><br>' +
					'Telephone: &nbsp;&nbsp;QQ:340360491&nbsp;&nbsp; Email:cmw_1984122@126.com<br><br>' +
					'Visit the official site :<a href="http://user.qzone.qq.com/340360491/infocenter?ptlang=2052" '+
					'target="_blank" title="Click here to visit TsCmw HomePage ">http://user.qzone.qq.com</a>',
	field_userName : 'userName',
	field_passWord : 'passWord',
	field_code : 'code',
	field_isaudot : 'Auto Login',
	field_themeSelect : 'System Style',
	store_themeSelect : [["default","Default Style"],["access","Brown Style"],
	 					["gray","Gray Style"],["blue","Blue Style"],["yourtheme","Green Style"]],
	field_language : 'system Language',
	store_language :  [["zh_CN","Chinese Simple"],["en","English"]],
	button_login : 'Login',
	button_clear : 'Clear'
}

/**
 * 系统消息提示[提示框内容] 
 */
Msg_SysTip = {
	title_apperr : 'Procedure call error',
	title_appconfirm : 'System Prompt',
	title_appwarn : 'System Warn',
	msg_nopassdata : 'Data entry is not valid,Please check!',
	msg_nohaveAction : 'in call  EventManager.get(url,action) time Not provided action Parameter!'	
}
/**
 * 系统首页[国际化] 
 */
Msg_AppCaption = {
		menu_user : 'Current User:',
		menu_start : 'Start',
		menu_sysset : 'System Settings',
		menu_logout : 'Exit System',
		menu_refresh : 'Refresh',
		disabled_text : '【disabled】'
}


/**
 * 按钮配置管理选项
 * Btn_Cfgs.
 */
var Btn_Cfgs = {
		/*-->刷新配置 <--*/
		REFRESH_BTN_TXT : 'Refresh',
		REFRESH_FASTKEY : 'Ctrl+R',
		REFRESH_CLS : 'page_refresh',
		REFRESH_TIP_BTN_TXT : '刷新[Ctrl+R]',
		
		/*--> 展开配置 <--*/
		EXPAND_BTN_TXT : 'Expand',
		EXPAND_FASTKEY : 'Ctrl+A',
		EXPAND_CLS : 'page_expand',
		EXPAND_TIP_BTN_TXT : '展开[Ctrl+A]',
		
		/*--> 收起配置 <--*/
		COLLAPSE_BTN_TXT : 'Away',
		COLLAPSE_FASTKEY : 'Ctrl+X',
		COLLAPSE_CLS : 'page_away',
		COLLAPSE_TIP_BTN_TXT : '收起[Ctrl+X]',
		
		/*--> 全选配置 <--*/
		CHECKALL_BTN_TXT : 'CheckAll',
		CHECKALL_FASTKEY : 'Alt+A',
		CHECKALL_CLS : 'page_checkall',
		CHECKALL_TIP_BTN_TXT : '全选[Alt+A]',
		
		/*--> 反选配置 <--*/
		UNCHECKALL_BTN_TXT : 'UnCheckAll',
		UNCHECKALL_FASTKEY : 'Alt+U',
		UNCHECKALL_CLS : 'page_uncheckall',
		UNCHECKALL_TIP_BTN_TXT : '反选[Alt+U]',
		
		/*--> 查询配置 <--*/
		QUERY_BTN_TXT : 'Query',
		QUERY_FASTKEY : 'Ctrl+Q',
		QUERY_CLS : 'page_query',
		QUERY_TIP_BTN_TXT : '查询[Ctrl+Q]',
		
		/*--> 重置配置 <--*/
		RESET_BTN_TXT : 'Reset',
		RESET_FASTKEY : 'Ctrl+C',
		RESET_CLS : 'page_reset',
		RESET_TIP_BTN_TXT : '重置[Ctrl+C]',
		
		/*--> 新增配置 <--*/
		INSERT_BTN_TXT : 'Add',
		INSERT_FASTKEY : 'Ctrl+I',
		INSERT_CLS : 'page_add',
		INSERT_TIP_BTN_TXT :'新增[Ctrl+I]',
		
		/*--> 修改配置 <--*/
		MODIFY_BTN_TXT : 'Edit',
		MODIFY_FASTKEY : 'Ctrl+M',
		MODIFY_CLS : 'page_edit',
		MODIFY_TIP_BTN_TXT : '修改[Ctrl+M]',
		
		/*--> 详情配置 <--*/
		DETAIL_BTN_TXT : 'Detail',
		DETAIL_FASTKEY : 'Ctrl+M',
		DETAIL_CLS : 'page_detail',
		DETAIL_TIP_BTN_TXT : '详情[Ctrl+X]',
		
		/*--> 起用配置 <--*/
		ENABLED_BTN_TXT : 'Enabled',
		ENABLED_FASTKEY : 'Ctrl+E',
		ENABLED_CLS : 'page_enabled',
		ENABLED_TIP_BTN_TXT : '起用[Ctrl+E]',
		
		/*--> 禁用配置 <--*/
		DISABLED_BTN_TXT : 'Disabled',
		DISABLED_FASTKEY : 'Ctrl+F',
		DISABLED_CLS : 'page_disabled',
		DISABLED_TIP_BTN_TXT : '禁用[Ctrl+F]',
		
		/*--> 删除配置 <--*/
		DELETE_BTN_TXT : 'Delete',
		DELETE_FASTKEY : 'Ctrl+D',
		DELETE_CLS : 'page_delete',
		DELETE_TIP_BTN_TXT :'删除[Ctrl+D]',
		
		/*--> 上一条配置 <--*/
		PREVIOUS_BTN_TXT : 'Previous',
		PREVIOUS_FASTKEY : 'Ctrl+P',
		PREVIOUS_CLS : 'page_previous',
		PREVIOUS_TIP_BTN_TXT :'上一条[Ctrl+P]',
		
		/*--> 下一条配置 <--*/
		NEXT_BTN_TXT : 'Next',
		NEXT_FASTKEY : 'Ctrl+N',
		NEXT_CLS : 'page_next',
		NEXT_TIP_BTN_TXT :'下一条[Ctrl+N]',
		
		/*--> 保存配置 <--*/
		SAVE_BTN_TXT : 'Save',
		SAVE_FASTKEY : 'Ctrl+S',
		SAVE_CLS : 'page_save',
		SAVE_TIP_BTN_TXT :'保存[Ctrl+S]',
		
		/*--> 审核配置 <--*/
		AUDIT_BTN_TXT : 'Audit',
		AUDIT_FASTKEY : 'Ctrl+Z',
		AUDIT_CLS : 'page_audit',
		AUDIT_TIP_BTN_TXT :'审核[Ctrl+Z]',
		
		/*--> 关闭配置 <--*/
		CLOSE_BTN_TXT : 'Close',
		CLOSE_FASTKEY : 'Ctrl+H',
		CLOSE_CLS : 'page_close',
		CLOSE_TIP_BTN_TXT :'关闭[Ctrl+H]',
		
		/*--> 上传配置 <--*/
		UPLOAD_BTN_TXT : 'Upload',
		UPLOAD_FASTKEY : 'Ctrl+U',
		UPLOAD_CLS : 'page_upload',
		UPLOAD_TIP_BTN_TXT :'上传[Ctrl+U]',
		
		/*--> 下载配置 <--*/
		DOWNLOAD_BTN_TXT : 'Download',
		DOWNLOAD_FASTKEY : 'Ctrl+O',
		DOWNLOAD_CLS : 'page_download',
		DOWNLOAD_TIP_BTN_TXT :'下载[Ctrl+O]',
		
		/*--> 导出EXCEL配置 <--*/
		EXPORT_BTN_TXT : 'Export',
		EXPORT_FASTKEY : 'Ctrl+Y',
		EXPORT_CLS : 'page_exportxls',
		EXPORT_TIP_BTN_TXT :'导出[Ctrl+Y]',
		
		/*--> 打印配置 <--*/
		PRINT_BTN_TXT : 'Print',
		PRINT_FASTKEY : 'Ctrl+T',
		PRINT_CLS : 'page_print',
		PRINT_TIP_BTN_TXT :'[Ctrl+T]'
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
		code:'code',
		parentName:'parentName',
		name:'name',
		leaf:'leaf', 
		iconCls:'iconCls',
		isSystem:'isSystem',
		biconCls:'biconCls',
		jsArray:'jsArray',
		params:'params',
		remark:'remark'
	}
}
