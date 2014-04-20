var CLIENTHEIGHT = document.body.clientHeight;
/**
 * 
 * 项目根路径
 */
var BASE_PATH = "${pageContext.request.contextPath}";
/**
 * 当前登录用户
 */
var CURENT_USER =  "${sessionScope.user.userName}";

var CURENT_EMP =  "${sessionScope.user.empName}";
if(!CURENT_EMP || CURENT_EMP == "null"){
	CURENT_EMP = CURENT_USER;
}
/**
 * 当前用户的用户ID
 * @type String
 */
var CURRENT_USERID = "${sessionScope.user.userId}";
/**
 * 当前用户是否是系统管理员 0: 非系统管理员, 1: 系统管理员
 * @type String
 */
var CURRENT_ISADMIN = "${sessionScope.user.isSystem}";
/**
 * 必填项星号标记
 */
var NEED = '<span style="color:red">*</span>';
/**
 * 表单个性化自定义详情字段标识KEY
 * @type String
 */
var FORMDIY_DETAIL_KEY = '##FORMDIY_DETAIL_HTML##';
/**
 * 分页大小
 * @type String
 */
var PAGESIZE = 20;
/**
 * 最大 z-index 值 用于Window 的居顶显示，
 * 	主要处理 Window modal = true 时,chorme,firefox,safair不能庶住的BUG
 * @type Number
 */
var maxZseed = 10000;
/**
 * 最小 z-index 值 用于Window 的隐藏时，还原的默认值
 * 	主要处理 Window modal = true 时,chorme,firefox,safair不能庶住的BUG
 * @type Number
 */
var minZseed = 9000;
/**
 * 点击的 TreeNode 的 key 名称
 * @type 
 */
var CURR_NODE_KEY = "CURR_NODE_"+CURRENT_USERID;

Ext.BLANK_IMAGE_URL = BASE_PATH+'/images/public/s.gif';
Ext.QuickTips.init();	/*加载快速提示框*/

/**
 * 获取当前日期
 */
function CURENT_DATE(){
	var date = new Date();
	var year = date.getFullYear();
	var month = parseInt(date.getMonth())+1;
	month = month<10 ? "0"+month : month;
	var day = date.getDate();
	day = day<10 ? "0"+day : day;
	return year+"-"+month+"-"+day;
}


var EXPORT_HREF_ID = 'EXPORT_HREF_ID';
/**
 * 桌面首页
 * @type String
 */
var HOME_ID = 'TXR_SYSTEM_HOME_';
/**
 * 新建工作流申请单Tab面板ID前缀
 * @type String
 */
var OA_NEWWORK_ID_PREFIX = "OA_NEWWORK_ID_";
/**
 * 用来标识是由点击桌面模块中的数据跳转时所带的查询参数 KE
 * @type String
 */
var DESK_MOD_TAG_QUERYPARAMS_KEY = 'DESK_MOD_TAG_QUERYPARAMS';

/**
 * 在卡片菜单中，编号以 "FORM_" 开头的默认是流程业务表单功能
 */
var ACCORDION_CODE_TYPE_FORM_PREFIX = "FORM_";
/**
 * 在卡片菜单中，编号以 "SUBFORM_" 开头的默认是子业务流程业务表单功能
 */
var ACCORDION_CODE_TYPE_SUBFORM_PREFIX = "SUBFORM_";

/**
 * 添加相应初始化数据
 */
(function initData(){
	/* 添加下载标记 */
	//Ext.getBody().insertHtml('<a id="'+EXPORT_HREF_ID+'" href="#" target="_blank" style="display:none;">导出或下载Excel或报表使用</a>');
	var body = Ext.getBody();
	body.createChild({id:EXPORT_HREF_ID,tag:'A',href:'#',target:"_blank",style:"display:none;",html:'XXXX'});
})();

/**
 * 系统类型标识
 * @type 
 */
var SYSTEM_MARK = {
	/**
	 *  FC 小额贷款
	 * @type String
	 */
	FINANCE : 'FC',
	/**
	 *  AS 担保
	 * @type String
	 */
	ASSURANCE : 'AS'
}

/**
 * 操作类型
 * OPTION_TYPE.ADD: 新增
 * OPTION_TYPE.EDIT: 修改
 * OPTION_TYPE.DEL: 删除
 * OPTION_TYPE.DISABLED: 禁用
 * OPTION_TYPE.ENABLED: 起用
 * OPTION_TYPE.DETAIL: 详情
 * @type 
 */
var OPTION_TYPE = {
 ADD : 1,
 EDIT : 2,
 DEL : 3,
 DISABLED : 4,
 ENABLED : 5,
 DETAIL : 6,
 ISDEFAULT: 7,//地区区域中设置成默认状态
 ACTIVITE : 8, /*Tab选项开打开并激活时的操作类型*/
 SAVE : 9
}

/**
 * 控件类型标识
 * CMPT_TYPE.TREE	: 树类型标识
 * CMPT_TYPE.GRID	：grid 类型标识
 * CMPT_TYPE.WINDOW  : Window 类型标识
 * @type 
 */
var CMPT_TYPE = {
	TREE : 'tree',
	GRID : 'grid',
	WINDOW : 'win'
}

/**
 * 编辑或详情窗口 URL 配置 KEY 常量
 * URLCFG_KEYS.ADDURLCFG	:	添加KEY
 * URLCFG_KEYS.GETURLCFG	:	取某条数据KEY
 * URLCFG_KEYS.PREURLCFG	：	上一条KEY
 * URLCFG_KEYS.NEXTURLCFG	: 	下一条KEY
 * @type 
 */
var URLCFG_KEYS = {
	ADDURLCFG : 'addUrlCfg',
	GETURLCFG : 'getUrlCfg',
	PREURLCFG : 'preUrlCfg',
	NEXTURLCFG : 'nextUrlCfg'
}

/**
 * 上一条下一条数据游标管理
 * CURSOR_MGR.CURSOR_KEY	:	游标KEY
 * CURSOR_MGR.CURSOR_FIRST	:	已经到第一条的标识
 * CURSOR_MGR.CURSOR_LAST	:	已经到最后一条的标识
 * @type 
 */
var CURSOR_MGR = {
	CURSOR_KEY  :  "cursor$data",	//游标KEY
	CURSOR_FIRST  :  "FIRST", 		//已经到第一条的标识
	CURSOR_LAST  :  "LAST"			//已经到最后一条的标识
}

/**
 * 注册器对象
 * 主要用来加载本地 js/datas 文件夹中的基础数据定义项而设定的规范
 * 
 * @type  
 */
var REGISTER = {
	/**
	 * 基础数据源加载标识
	 * @type String
	 */
	GvlistDatas : 'GvlistDatas'
};

/**
 * 凭证模板编号常量
 * @type 
 */
var VOUCHERTEMP_CODE = {
	/**
	 * 利息计提凭证编号
	 * VOUCHERTEMP_CODE.PROVISION
	 * @type String
	 */
	PROVISION : 'V00001',
	/**
	 * 放款凭证模板编号
	 * VOUCHERTEMP_CODE.LOANINVOCE
	 * @type String
	 */
	LOANINVOCE :'V00005',
	/**
	 * 放款手续费收取模板编号
	 * VOUCHERTEMP_CODE.LOANFREE
	 * @type String
	 */
	LOANFREE :'V00006',
	/**
	 * 正常扣收凭证模板编号
	 * VOUCHERTEMP_CODE.NOMALDEDUCT
	 * @type String
	 */
	NOMALDEDUCT :'V00008',
	/**
	 * 提前还款凭证模板编号,
	 */
	PREPAYMENTCODE : 'V00012'
	
}

/**
 * 附件业务类型常量
 * ATTACHMENT_FORMTYPE.FType_32
 * @type 
 */
var ATTACHMENT_FORMTYPE = {
	FType_1:1, //业务品种流程附件(ts_Varitey),
	FType_2:2,//子业务流程附件(ts_BussProcc),
	FType_3:3,//审批记录附件(ts_AuditRecords),
	FType_4:4,//扣点合同附件(ts_Deduction),
	FType_5:5,//公告附件(Oa_Bulletin),
	FType_6:6,//新闻附件(Oa_News)
	FType_7:7,//员工相关附件(Hr_Employee)
	FType_8:8,//门店图片附件((Oa_Stores)
	FType_9:9,//价格扣点附件(Oa_Deduction)
	FType_10:10,//客户档案附件((Oa_BillUnit)
	FType_11:11,//借款单附件(Oa_LoanApply)
	FType_12:12,//合同申请单(Oa_ContractApply)
	FType_13:13,//加班申请单附件(Oa_OvertimeApply)
	FType_14:14,//请假申请单附件(Oa_LeaveApply)
	FType_15:15//还款申请单表附件(Oa_PayApply)
};

/**
 * 国际化常量对象
 * @type 
 */
var LOCALE_NAME = {
	/**
	 * 中文简体
	 * @type String
	 */
	zh_CN : 'name',
	/**
	 * 英文
	 * @type String
	 */
	en : 'ename',
	/**
	 * 繁体中文 台湾 
	 * @type String
	 */
	zh_TW : 'twname',
	/**
	 * 日文
	 * @type String
	 */
	ja : 'jname',
	/**
	 * 韩文
	 * @type String
	 */
	ko : 'koname'
};

/**
 * 扣收顺序常量 
 * @type 
 */
var ORDER_ENUM = {
	L0001 : 'L0001',/*利息编号*/	
	L0002 : 'L0002',/*本金编号*/
	L0003 : 'L0003',/*管理费编号*/
	L0004 : 'L0004',/*罚息编号*/
	L0005 : 'L0005'/*滞纳金编号*/
}
/**------------------------------表单个性化自定义业务引用键 常量---------------------------**/
FORMDIY_IND = {
	/**
	 * 保证合同
	 * @type 
	 */
	FORMDIY_GUA : 'FORMDIY_GUA_85',
	/**
	 * 借款合同
	 * @type 
	 */
	FORMDIY_LOAN :'FORMDIY_LOAN_86',
	/**
	 * 放款通知书	
	 * @type 
	 */
	FORMDIY_LOANINVOCE : 'FORMDIY_LOANINVOCE_87',
	/**
	 * 抵押合同
	 * @type 
	 */
	FORMDIY_MORT : 'FORMDIY_MORT_88',
	/**
	 * 质押合同
	 * @type 
	 */
	FORMDIY_PLE : 'FORMDIY_PLE_89'
}
/**------------------ 小额贷款业务常量定义 CODE START  -------------------**/
/**
 * Tab 对象处理方式
 * 
 * @type 
 */
var OA_UIActiveType = {
	/**
	 * 当 Tab 对象存在时，激活 Tab 对象。此时，如果此 Tab 有绑定 notify 方法。则调用 notify 方法 
	 * OA_UIActiveType.activeInstance
	 * @type Number
	 */
	activeInstance : 1,
	/**
	 * 当 Tab 对象存在时,先销毁此对象。然后，再重新创建该 Tab 对象
	 * OA_UIActiveType.newInstance
	 * @type Number
	 */
	newInstance : 2,
	/**
	 * 当 Tab 对象存在时,给出此UI存在的消息提示
	 * OA_UIActiveType.tipInstance
	 * @type Number
	 */
	tipInstance : 3
}

/**
 * Oa 自定义Tab Id 
 * 
 * @type 	CUSTTAB_ID.tempCustApplyMgrTabId
 */
OA_CUSTTAB_ID = {
	
	/**
	 * 子业务流程审批主面板 TAB ID
	 * @type String
	 */
	bussProcc_auditMainUITab : {id:'flow_auditMainUI_tabid_1', url : 'pages/app/workflow/bussProcc/oa/mainUIs/AuditMainUI.js'},
	/**
	 * 编辑新闻 TAB ID
	 * @type String
	 */
	newsEdit_auditMainUITab : {
		id:'news_auditMainUI_tabid_30', 
		url : 'pages/app/oa/newsmanage/NewsEdit.js'
	}
	
}

/**
 * 组件ID管理对象
 * @type 
 * ComptIdMgr.tempCustApplyMgrId
 * ComptIdMgr.tempEntCustApplyMgrId
 * ComptIdMgr.auditCustApplyMgrId
 * ComptIdMgr.auditEntCustApplyMgrId
 * ComptIdMgr.auditCustApplyAllId
 * ComptIdMgr.auditEntCustApplyAllId
 * ComptIdMgr.auditCustApplyHistoryId
 * ComptIdMgr.auditEntCustApplyHistoryId
 * ComptIdMgr.desk_mod_fin_1
 * ComptIdMgr.desk_mod_fin_2
 * ComptIdMgr.desk_mod_fin_3
 * ComptIdMgr.desk_mod_fin_4
 * ComptIdMgr.desk_mod_fin_5
 * ComptIdMgr.desk_mod_fin_6
 * ComptIdMgr.desk_mod_fin_7
 * ComptIdMgr.desk_mod_fin_8
 */
var ComptIdMgr = {
	/**
	 * 暂存借款申请单面板ID
	 * ComptIdMgr.tempLoanApplyMgrId
	 */
	tempLoanApplyMgrId : Ext.id(null,'TempLoanApplyMgrId'),
	/**
	 * 待审借款申请单面板ID
	 */
	auditLoanApplyMgrId : Ext.id(null,'AuditLoanApplyMgrId'),
	/**
	 * 子业务流程表单申请时要刷新的UI
	 * 凡是像与子业务流程相关的UI 只要在此数组中注册，当申请表单保存后就会自动刷新指定的页面
	 * @type 
	 */
	getBussProccApplyRefreshIds : function(){
		return [this.tempLoanApplyMgrId,this.auditLoanApplyMgrId];
	},
	/**
	 * 桌面模块 --> 待审批的个人贷款
	 * 
	 * @type String
	 */
	desk_mod_fin_1 : 'DESK_MOD_FIN_1',
	/**
	 * 桌面模块 --> 待审批的企业贷款
	 * 
	 * @type String
	 */
	desk_mod_fin_2 : 'DESK_MOD_FIN_2',
	/**
	 * 桌面模块 --> 超时未审批的个人贷款
	 * 
	 * @type String
	 */
	desk_mod_fin_3 : 'DESK_MOD_FIN_3',
	/**
	 * 桌面模块 --> 超时未审批的企业贷款
	 * 
	 * @type String
	 */
	desk_mod_fin_4 : 'DESK_MOD_FIN_4',
	/**
	 * 桌面模块 --> 即将到还款日的还款计划列表
	 * 
	 * @type String
	 */
	desk_mod_fin_5 : 'DESK_MOD_FIN_5',
	/**
	 * 桌面模块 --> 逾期客户列表
	 * 
	 * @type String
	 */
	desk_mod_fin_6 : 'DESK_MOD_FIN_6',
	/**
	 * 桌面模块 --> 待放款的个人客户贷款
	 * 
	 * @type String
	 */
	desk_mod_fin_7 : 'DESK_MOD_FIN_7',
	/**
	 * 桌面模块 --> 待放款的企业客户贷款
	 * 
	 * @type String
	 */
	desk_mod_fin_8 : 'DESK_MOD_FIN_8',
	/**
	 * 桌面模块 --> 待提交的展期申请单
	 * @type 
	 */
	desk_mod_fin_9 : 'DESK_MOD_FIN_9',
	/**
	 * 桌面模块 --> 待提交息费豁免申请单
	 * @type 
	 */
	desk_mod_fin_10 : 'DESK_MOD_FIN_10',
	/**
	 * 桌面模块 --> 待提交提前还款申请单
	 * @type 
	 */
	desk_mod_fin_11 : 'DESK_MOD_FIN_11',
	/**
	 * 桌面模块 --> 待审批的展期申请单
	 * @type 
	 */
	desk_mod_fin_12 : 'DESK_MOD_FIN_12',
	/**
	 * 桌面模块 --> 待审批息费豁免申请单
	 * @type 
	 */
	desk_mod_fin_13 : 'DESK_MOD_FIN_13',
	/**
	 * 桌面模块 --> 待审批提前还款申请单
	 * @type 
	 */
	desk_mod_fin_14 : 'DESK_MOD_FIN_14',
	
	
	/**
	 * 当业务审批后有可能要刷新的功能对象ID列表
	 * @type 
	 */
	flowRefreshIdsArr : [
		 this.tempCustApplyMgrId,
		 this.tempEntCustApplyMgrId,
		 this.auditCustApplyMgrId,
		 this.auditEntCustApplyMgrId,
		 this.auditCustApplyAllId,
		 this.auditEntCustApplyAllId,
		 this.auditCustApplyHistoryId,
		 this.auditEntCustApplyHistoryId,
		 this.desk_mod_fin_1,
		 this.desk_mod_fin_2,
		 this.desk_mod_fin_3,
		 this.desk_mod_fin_4
	]
};

/**
 * 子流程自定义表单路径
 * 规则:{流程编号:业务表单路径}
 * @type 
 */
var Flow_CustForm_Url = {
	/**
	 * 展期内容对应的审批详情表单
	 * @type String
	 */
	B0001 : 'pages/app/workflow/bussProcc/oa/formUIs/ExtensionDetailMod',
	/**
	 * 提前还款对应的审批详情表单
	 * @type String
	 */
	B0002 : 'pages/app/workflow/bussProcc/oa/formUIs/PrepaymentDetailMod',
	/**
	 * 息费豁免业务审批详情表单
	 * @type String
	 */
	B0003 : 'pages/app/workflow/bussProcc/oa/formUIs/ExemptDetailMod',
	/**------------ OA 流程表单 编号 CODE START  -----------**/
	/**
	 * 申请表单基础父页面JS文件
	 * 
	 * @type String
	 */
	OaBussProccBase : 'pages/app/workflow/bussProcc/oa/OaBussProccBase.js',
	/**
	 * 借款申请单详情
	 * @type String
	 */
	OA0001 : 'pages/app/workflow/bussProcc/oa/formUIs/simpleDetail/LoanApplySdetail'
	/**------------ OA 流程表单 CODE END -----------**/
}

/**
 * 打印标识
 * 此常量数据将会插入到 ts_PrintLog(打印日志表)中
 * @type 
 */
var PrintLog_FunTag = {
	A0001 : 'A0001'/*提前结清单据打印标识 ---> 对应提前还款收款后的单据*/	
}

/**------------------ 小额贷款业务常量定义 CODE END  -------------------**/
/**
 * 业务常量定义
 * 
 * @type 
 */
var Buss_Constant = {
	/*---------- 子业务流程状态   CODE START ----------*/
	/**
	 * 子业务流程状态 [0:待提交] 
	 * @type Number
	 */
	BUSS_PROCC_DJZT_0 : 0,
	/**
	 * 子业务流程状态 [1:审批中] 
	 * @type Number
	 */
	BUSS_PROCC_DJZT_1 : 1,
	/**
	 * 子业务流程状态 [2:审批通过] 
	 * @type Number
	 */
	BUSS_PROCC_DJZT_2 : 2,
	/**
	 * 子业务流程状态 [3:审批未通过] 
	 * @type Number
	 */
	BUSS_PROCC_DJZT_3 : 3,
	/*---------- 子业务流程状态   CODE END ----------*/
	/**
	 * 审批记录表(ts_auditrecords)
	 * @type 
	 */
	AuditRecords : {
		/*----- 消息通知类型  ------*/
		notifys_2 : 2, /*2:手机短消息*/
		notifys_3 : 3 /*3:邮件通知*/
	},
	/**
	 * Buss_Constant.CustType_0
	 * 客户类型-->[0:个人客户]
	 * @type Number
	 */
	CustType_0 : 0,
	/**
	 * Buss_Constant.CustType_1
	 * 客户类型-->[1:企业客户]
	 * @type Number
	 */
	CustType_1 : 1,
	/**
	 * 动作类型 ActionType_0:0[暂存]
	 * Buss_Constant.ActionType_0
	 * @type Number
	 */
	ActionType_0 : 0,
	/**
	 * 动作类型 ActionType_1:1[提交]
	 *  Buss_Constant.ActionType_1
	 * @type Number
	 */
	ActionType_1 : 1
}

