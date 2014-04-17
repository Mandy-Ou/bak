var CLIENTWIDTH = document.body.clientWidth;
 var taskbar = Ext.get('ux-taskbar');
 var taskbarHeight  = 0;
if(taskbar){
	taskbarHeight = taskbar.getHeight();// Extjs桌面工具栏高度
}
var CLIENTHEIGHT = document.body.clientHeight -taskbarHeight;//获得浏览器body 高度  - 减去 Extjs桌面工具栏高度

ACCORDPNLID =  Ext.id(null,'ACCORDPNLID');

/**
 * 
 * 项目根路径
 */
var BASE_PATH = "${pageContext.request.contextPath}";
/***
 * Session ID 值
 * @type String
 */
var JSESSIONID = "<%=session.getId()%>";
/**
 * 用户ID
 * @type String
 */
var CURRENT_USERID = "${sessionScope.user.userId}";
/**
 * 当前登录用户
 */
var CURENT_USER = "${sessionScope.user.userName}";

var CURENT_EMP = "${sessionScope.user.empName}";
if (!CURENT_EMP || CURENT_EMP == "null") {
	CURENT_EMP = CURENT_USER;
}
/**
 * 当前用户的用户ID
 * 
 * @type String
 */
var CURRENT_USERID = "${sessionScope.user.userId}";
/**
 * 当前用户是否是系统管理员 0: 非系统管理员, 1: 系统管理员
 * 
 * @type String
 */
var CURRENT_ISADMIN = "${sessionScope.user.isSystem}";
/**
 * 必填项星号标记
 */
var NEED = '<span style="color:red">*</span>';
/**
 * 表单个性化自定义详情字段标识KEY
 * 
 * @type String
 */
var FORMDIY_DETAIL_KEY = '##FORMDIY_DETAIL_HTML##';
/**
 * 分页大小
 * 
 * @type String
 */
var PAGESIZE = 20;
/**
 * 最大 z-index 值 用于Window 的居顶显示， 主要处理 Window modal = true
 * 时,chorme,firefox,safair不能庶住的BUG
 * 
 * @type Number
 */
var maxZseed = 10000;
/**
 * 最小 z-index 值 用于Window 的隐藏时，还原的默认值 主要处理 Window modal = true
 * 时,chorme,firefox,safair不能庶住的BUG
 * 
 * @type Number
 */
var minZseed = 9000;
/**
 * 点击的 TreeNode 的 key 名称
 * 
 * @type
 */
var CURR_NODE_KEY = "CURR_NODE_" + CURRENT_USERID;

Ext.BLANK_IMAGE_URL = BASE_PATH + '/images/public/s.gif';
Ext.QuickTips.init(); /* 加载快速提示框 */

/**
 * 获取当前日期
 */
function CURENT_DATE() {
	var date = new Date();
	var year = date.getFullYear();
	var month = parseInt(date.getMonth()) + 1;
	month = month < 10 ? "0" + month : month;
	var day = date.getDate();
	day = day < 10 ? "0" + day : day;
	return year + "-" + month + "-" + day;
}

var EXPORT_HREF_ID = 'EXPORT_HREF_ID';
/**
 * 桌面首页
 * 
 * @type String
 */
var HOME_ID = 'TXR_SYSTEM_HOME_';
/**
 * 用来标识是由点击桌面模块中的数据跳转时所带的查询参数 KE
 * 
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
(function initData() {
	/* 添加下载标记 */
	// Ext.getBody().insertHtml('<a id="'+EXPORT_HREF_ID+'" href="#"
	// target="_blank" style="display:none;">导出或下载Excel或报表使用</a>');
	var body = Ext.getBody();
	body.createChild({
				id : EXPORT_HREF_ID,
				tag : 'A',
				href : '#',
				target : "_blank",
				style : "display:none;",
				html : 'XXXX'
			});
})();

/**
 * 系统类型标识
 * 
 * @type
 */
var SYSTEM_MARK = {
	/**
	 * FC 小额贷款
	 * 
	 * @type String
	 */
	FINANCE : 'FC',
	/**
	 * AS 担保
	 * 
	 * @type String
	 */
	ASSURANCE : 'AS'
}

/**
 * 操作类型 OPTION_TYPE.ADD: 新增 OPTION_TYPE.EDIT: 修改 OPTION_TYPE.DEL: 删除
 * OPTION_TYPE.DISABLED: 禁用 OPTION_TYPE.ENABLED: 起用 OPTION_TYPE.DETAIL: 详情
 * 
 * @type
 */
var OPTION_TYPE = {
	ADD : 1,
	EDIT : 2,
	DEL : 3,
	DISABLED : 4,
	ENABLED : 5,
	DETAIL : 6,
	ISDEFAULT : 7,// 地区区域中设置成默认状态
	ACTIVITE : 8, /* Tab选项开打开并激活时的操作类型 */
	SAVE : 9
}

/**
 * 控件类型标识 CMPT_TYPE.TREE : 树类型标识 CMPT_TYPE.GRID ：grid 类型标识 CMPT_TYPE.WINDOW :
 * Window 类型标识
 * 
 * @type
 */
var CMPT_TYPE = {
	TREE : 'tree',
	GRID : 'grid',
	WINDOW : 'win'
}

/**
 * 编辑或详情窗口 URL 配置 KEY 常量 URLCFG_KEYS.ADDURLCFG : 添加KEY URLCFG_KEYS.GETURLCFG :
 * 取某条数据KEY URLCFG_KEYS.PREURLCFG ： 上一条KEY URLCFG_KEYS.NEXTURLCFG : 下一条KEY
 * 
 * @type
 */
var URLCFG_KEYS = {
	ADDURLCFG : 'addUrlCfg',
	GETURLCFG : 'getUrlCfg',
	PREURLCFG : 'preUrlCfg',
	NEXTURLCFG : 'nextUrlCfg'
}

/**
 * 上一条下一条数据游标管理 CURSOR_MGR.CURSOR_KEY : 游标KEY CURSOR_MGR.CURSOR_FIRST :
 * 已经到第一条的标识 CURSOR_MGR.CURSOR_LAST : 已经到最后一条的标识
 * 
 * @type
 */
var CURSOR_MGR = {
	CURSOR_KEY : "cursor$data", // 游标KEY
	CURSOR_FIRST : "FIRST", // 已经到第一条的标识
	CURSOR_LAST : "LAST" // 已经到最后一条的标识
}

/**
 * 注册器对象 主要用来加载本地 js/datas 文件夹中的基础数据定义项而设定的规范
 * 
 * @type
 */
var REGISTER = {
	/**
	 * 基础数据源加载标识
	 * 
	 * @type String
	 */
	GvlistDatas : 'GvlistDatas'
};

/**
 * 凭证模板编号常量
 * 
 * @type
 */
var VOUCHERTEMP_CODE = {
	/**
	 * 利息计提凭证编号 VOUCHERTEMP_CODE.PROVISION
	 * 
	 * @type String
	 */
	PROVISION : 'V00001',
	/**
	 * 放款凭证模板编号 VOUCHERTEMP_CODE.LOANINVOCE
	 * 
	 * @type String
	 */
	LOANINVOCE : 'V00005',
	/**
	 * 放款手续费收取模板编号 VOUCHERTEMP_CODE.LOANFREE
	 * 
	 * @type String
	 */
	LOANFREE : 'V00006',
	/**
	 * 正常扣收凭证模板编号 VOUCHERTEMP_CODE.NOMALDEDUCT
	 * 
	 * @type String
	 */
	NOMALDEDUCT : 'V00008',
	/**
	 * 提前还款凭证模板编号,
	 */
	PREPAYMENTCODE : 'V00012'
	
}

/**
 * 附件业务类型常量 ATTACHMENT_FORMTYPE.FType_32
 * 
 * @type
 */
var ATTACHMENT_FORMTYPE = {
	FType_1 : 1, // 业务品种流程附件(ts_Varitey),
	FType_2 : 2,// 子业务流程附件(ts_BussProcc),
	FType_3 : 3,// 审批记录附件(ts_AuditRecords),
	FType_4 : 4,// 抵押附件(fc_Mortgage),
	FType_5 : 5,// 质押附件(fc_Pledge),
	FType_6 : 6,// 相关材料附件
	FType_7 : 7,// 保证合同附件
	FType_8 : 8,// 借款合同附件
	FType_9 : 9,// 抵押合同附件
	FType_10 : 10,// 质押合同附件
	FType_11 : 11,// 个人客户附件
	FType_12 : 12,// 个人客户配偶附件
	FType_13 : 13,// 住宅附件
	FType_14 : 14,// 房产物业附件
	FType_15 : 15,// 职业附件
	FType_16 : 16,// 旗下公司附件
	FType_17 : 17,// 主要联系人附件
	FType_18 : 18,// 个人信用附件
	FType_19 : 19,/* 企业客户附件 */
	FType_20 : 20,/* 公司法人附件 */
	FType_21 : 21,/* 企业财务附件 */
	FType_22 : 22,/* 企业开户附件 */
	FType_23 : 23,/* 股权结构附件 */
	FType_24 : 24,/* 银行贷款附件 */
	FType_25 : 25,/* 所有者贷款附件 */
	FType_26 : 26,/* 领导班子附件 */
	FType_27 : 27,/* 企业担保用附件 */
	FType_28 : 28,// 个人客户其他信息
	FType_29 : 29,// 企业客户其他信息
	MATTEMP_30 : 30, // 模板预览
	FType_31 : 31, // 展期申请附件
	FType_32 : 32, // 提前还款申请附件
	FType_33 : 33, // 息费豁免申请附件,
	FType_34 : 34,
	FType_35 : 35,//第三方担保附件个人
	FType_36 : 36//第三方担保附件企业
};

/**
 * 国际化常量对象
 * 
 * @type
 */
var LOCALE_NAME = {
	/**
	 * 中文简体
	 * 
	 * @type String
	 */
	zh_CN : 'name',
	/**
	 * 英文
	 * 
	 * @type String
	 */
	en : 'ename',
	/**
	 * 繁体中文 台湾
	 * 
	 * @type String
	 */
	zh_TW : 'twname',
	/**
	 * 日文
	 * 
	 * @type String
	 */
	ja : 'jname',
	/**
	 * 韩文
	 * 
	 * @type String
	 */
	ko : 'koname'
};

/**
 * 扣收顺序常量
 * 
 * @type
 */
var ORDER_ENUM = {
	L0001 : 'L0001',/* 利息编号 */
	L0002 : 'L0002',/* 本金编号 */
	L0003 : 'L0003',/* 管理费编号 */
	L0004 : 'L0004',/* 罚息编号 */
	L0005 : 'L0005'/* 滞纳金编号 */
}
/** ------------------------------表单个性化自定义业务引用键 常量---------------------------* */
FORMDIY_IND = {
	/**
	 * 保证合同
	 * 
	 * @type
	 */
	FORMDIY_GUA : 'FORMDIY_GUA_85',
	/**
	 * 借款合同
	 * 
	 * @type
	 */
	FORMDIY_LOAN : 'FORMDIY_LOAN_86',
	/**
	 * 放款通知书
	 * 
	 * @type
	 */
	FORMDIY_LOANINVOCE : 'FORMDIY_LOANINVOCE_87',
	/**
	 * 抵押合同
	 * 
	 * @type
	 */
	FORMDIY_MORT : 'FORMDIY_MORT_88',
	/**
	 * 质押合同
	 * 
	 * @type
	 */
	FORMDIY_PLE : 'FORMDIY_PLE_89',
	/**
	 * 第三方担保合同
	 * @type String
	 */
	
	FORMDIY_CUST : 'FORMDIY_CUST_90',
	
	/*==================个人客户资料表单个性化表单常量 START======================================*/
	/**
	 * 个人客户
	 */
	FORMDIY_CUSTOMER_INFO : 'FORMDIY_CUSTOMER_INFO_90',
	/**
	 * 配偶
	 */
	FORMDIY_CONSORT : 'FORMDIY_CONSORT_91',
	/**
	 * 客户住宅信息
	 */
	FORMDIY_ADDRESS : 'FORMDIY_ADDRESS_92',
	/**
	 * 房产物业信息
	 */
	FORMDIY_ESTATE : 'FORMDIY_ESTATE_93',
	/**
	 * 职业信息
	 */
	FORMDIY_WORK : 'FORMDIY_WORK_94',
	/**
	 * 个人旗下/企业关联公司信息
	 */
	FORMDIY_CUSTCOMPANY : 'FORMDIY_CUSTCOMPANY_95',
	/**
	 * 联系人资料
	 */
	FORMDIY_CONTACTOR : 'FORMDIY_CONTACTOR_96',
	/**
	 * 个人信用资料
	 */
	FORMDIY_CREDITINFO : 'FORMDIY_CREDITINFO_97',
	/**
	 * 个人其它信息
	 */
	FORMDIY_CUSROMER_OTHERINFO : 'FORMDIY_CUSROMER_OTHERINFO_98',
	/*==================个人客户资料表单个性化表单常量 	END======================================*/
	
	
	/*===========================企业客户表单个性化id================*/
	/**
	 * 企业客户
	 */
	FORMDIY_ECUSTOMER_INFO : 'FORMDIY_ECUSTOMER_INFO_99',
	/**
	 * 企业财务状况
	 */
	FORMDIY_EFINANCE : 'FORMDIY_EFINANCE_100',
	/**
	 * 企业开户
	 */
	FORMDIY_EBANK : 'FORMDIY_EBANK_101',
	/**
	 * 银行借款情况
	 */
	FORMDIY_EBANKBORR : 'FORMDIY_EBANKBORR_102',
	/**
	 * 所有者借款情况
	 */
	FORMDIY_EOWNERBORR : 'FORMDIY_EOWNERBORR_103',
	/**
	 * 股权结构
	 */
	FORMDIY_EEQSTRUCT : 'FORMDIY_EEQSTRUCT_104',
	/**
	 *企业领导班子
	 */
	FORMDIY_ECLASS : 'FORMDIY_ECLASS_105',
	/**
	 *企业担保
	 */
	FORMDIY_EASSURE : 'FORMDIY_EASSURE_106',
	/**
	 * 企业其它信息
	 */
	FORMDIY_ECUSROMER_OTHERINFO : 'FORMDIY_ECUSROMER_OTHERINFO_107',
	/**
	 * 企业法人
	 */
	FORMDIY_LEGAL : 'FORMDIY_LEGAL_108',
	/*===========================企业客户表单个性化id End ================*/
	
	/**
	 * 贷款申请
	 */
	FROMDIY_APPLY : 'FROMDIY_APPLY_109',
	/**
	 * 审贷评审
	 */
	FROMDIY_Appraise :'FROMDIY_Appraise_110'
}
/** ------------------ 小额贷款业务常量定义 CODE START -------------------* */
/**
 * 自定义Tab Id
 * 
 * @type CUSTTAB_ID.tempCustApplyMgrTabId
 */
CUSTTAB_ID = {
	/**
	 * 个人客户 TAB ID
	 * 
	 * @type String
	 */
	customerTabId : 'one_customer_tabid_1',
	/**
	 * 企业客户 TAB ID
	 * 
	 * @type String
	 */
	entcustomerTabId : 'ent_customer_tabid_2',
	/**
	 * 贷款申请单 TAB ID
	 * 
	 * @type String
	 */
	applyformTabId : 'apply_form_tabid_3',
	/**
	 * 个人客户暂存申请单 TAB ID
	 * 
	 * @type String
	 */
	tempCustApplyMgrTabId : 'tempCustApplyMgr_tabid_4',

	/**
	 * 业务流程审批主面板 TAB ID
	 * 
	 * @type String
	 */
	flow_auditMainUITab : {
		id : 'flow_auditMainUI_tabid_5',
		url : 'pages/app/workflow/variety/mainUIs/AuditMainUI.js'
	},
	/**
	 * 添加个人客户TAB ID
	 */
	customerInfoEditTabId : {
		id : 'customerInfoEdit_tabid_6',
		url : 'pages/app/crm/incustomer/CustomerInfoEdit.js'
	},
	/**
	 * 用户数据访问权限编辑面板 TAB ID
	 * 
	 * @type String
	 */
	userDataAccessEditTab : {
		id : 'userDataAccessEdit_tabid_7',
		url : 'pages/app/sys/user/UserDataAccessEdit.js'
	},
	/**
	 * 企业客户暂存申请单 TAB ID
	 * 
	 * @type String
	 */
	tempEntCustApplyMgrTabId : 'tempEntCustApplyMgr_tabid_8',
	/**
	 * 个人客户详情TAB ID
	 */
	customerInfoDetailTab : {
		id : 'customerInfoDetail_tabid_9',
		url : 'pages/app/crm/incustomer/CustomerInfoDetail.js'
	},
	/**
	 * 企业客户TAB ID
	 */
	ecustomerInfoEditTabId : 'ecustomerInfoEdit_tabid_10',
	/**
	 * 提交放款通知书TAB ID
	 */
	loanInvoceSubmitTbaId : {
		id : 'submintLoanInvoce_tabid_11',
		url : 'pages/app/workflow/variety/formUIs/loaninvoce/LoanInvoceSubmintMgr.js'
	},
	/**
	 * 企业客户详情TAB ID
	 */
	ecustomerInfoDetailTab : {
		id : 'ecustomerInfoDetail_tabid_12',
		url : 'pages/app/crm/ecustomer/EcustomerInfoDetail.js'
	},
	/**
	 * 个人贷款发放 TAB ID
	 * 
	 * @type String
	 */
	custLoanMgrTabId : 'custLoanMgr_tabid_13',
	/**
	 * 企业贷款发放 TAB ID
	 * 
	 * @type String
	 */
	ecustLoanMgrTabId : 'ecustLoanMgr_tabid_14',
	/**
	 * 正常还款金额收取 TAB ID
	 * 
	 * @type String
	 */
	nomalDeductMgrTabId : 'nomalDeductMgr_tabid_15',
	/**
	 * 逾期还款金额收取 TAB ID
	 * 
	 * @type String
	 */
	overdueDeductMgrTabId : 'overdueDeductMgr_tabid_15',
	/**
	 * 个人客户申请单详情 TABID
	 * 
	 * @type
	 */

	detailApplyFormTabId : {
		id : 'detailApplyForm_tabid_16',
		url : 'pages/app/finance/bloan/applydetail/custApplyDetail.js'
	},
	/**
	 * 个人贷款审批 TAB ID
	 * 
	 * @type String
	 */
	auditCustApplyMgrTabId : 'auditCustApplyMgr_tabid_16',
	/**
	 * 企业贷款审批 TAB ID
	 * 
	 * @type String
	 */
	auditCustApplyMgrTabId : 'auditCustApplyMgr_tabid_17',
	/**
	 * 展期申请单ID
	 * 
	 * @type String
	 */
	extensionApplyTabId : 'extensionApply_tabid_18',
	/**
	 * 暂存的展期申请单
	 * 
	 * @type String
	 */
	tempExtensionApplyMgrTabId : 'tempExtensionApplyMgr_tabid_19',
	/**
	 * 子业务流程审批主面板 TAB ID
	 * 
	 * @type String
	 */
	bussProcc_auditMainUITab : {
		id : 'flow_auditMainUI_tabid_20',
		url : 'pages/app/workflow/bussProcc/mainUIs/AuditMainUI.js'
	},
	/**
	 * 提前还款申请单ID
	 * 
	 * @type String
	 */
	prepaymentApplyTabId : {
		id : 'prepaymentApply_tabid_21',
		url : 'pages/app/workflow/variety/requisition/requisitionDetail.js'
	},
	/**
	 * 暂存的提前还款申请单
	 * 
	 * @type String
	 */
	tempPrepaymentApplyMgrTabId : 'tempPrepaymentApplyMgr_tabid_22',
	/**
	 * 息费豁免申请单ID
	 * 
	 * @type String
	 */
	exemptApplyTabId : 'exemptApply_tabid_23',
	/**
	 * 暂存的息费豁免申请单
	 * 
	 * @type String
	 */
	tempExemptApplyMgrTabId : 'tempExemptApplyMgr_tabid_24',
	/**
	 * 展期协议书Id
	 * 
	 * @type String
	 */
	extContractMgrTabId : 'extContractMgr_tabid_25',
	/**
	 * 展期审批Id
	 */
	AuditExtensionMgrTabId : 'AuditExtensionMgr_tabId_26',
	/**
	 * 提前还款审批Id
	 */
	AuditPrepaymentMgrTabId : 'AuditPrepaymentMgr_tabId_27',
	/**
	 * 息费豁免审批id
	 */
	AuditExemptMgrTabId : 'AuditExemptMgr_tabId_28',

	/**
	 * 新息费豁免申请单ID
	 * 
	 * @type String
	 */
	exemptDetailTabId : {
		id : 'exemptApply_tabid_29',
		url : "pages/app/workflow/bussProcc/formUIs/ExemptDetail.js"
	},
	/**
	 * 提前还款申请单详情
	 */
	PrementDetailTabId : {id:'PrementDetailMgr_TabId_30',url:'pages/app/workflow/bussProcc/formUIs/requisition/PrepaymenteRquisitionDetail.js'}
}

/**
 * 组件ID管理对象
 * 
 * @type ComptIdMgr.tempCustApplyMgrId ComptIdMgr.tempEntCustApplyMgrId
 *       ComptIdMgr.auditCustApplyMgrId ComptIdMgr.auditEntCustApplyMgrId
 *       ComptIdMgr.auditCustApplyAllId ComptIdMgr.auditEntCustApplyAllId
 *       ComptIdMgr.auditCustApplyHistoryId
 *       ComptIdMgr.auditEntCustApplyHistoryId ComptIdMgr.desk_mod_fin_1
 *       ComptIdMgr.desk_mod_fin_2 ComptIdMgr.desk_mod_fin_3
 *       ComptIdMgr.desk_mod_fin_4 ComptIdMgr.desk_mod_fin_5
 *       ComptIdMgr.desk_mod_fin_6 ComptIdMgr.desk_mod_fin_7
 *       ComptIdMgr.desk_mod_fin_8
 */

var ComptIdMgr = {
	/**
	 * 个人客户暂存申请单面板ID
	 */
	tempCustApplyMgrId : Ext.id(null, 'TempCustApplyMgrId'),
	/**
	 * 个企业客户暂存申请单面板ID
	 */
	tempEntCustApplyMgrId : Ext.id(null, 'TempEntCustApplyMgrId'),
	/**
	 * 个人客户贷款审批面板ID
	 */
	auditCustApplyMgrId : Ext.id(null, 'AuditCustApplyMgrId'),
	/**
	 * 个企业客户贷款审批面板ID
	 */
	auditEntCustApplyMgrId : Ext.id(null, 'AuditEntCustApplyMgrId'),
	/**
	 * 个人客户贷款一览表面板ID
	 */
	auditCustApplyAllId : Ext.id(null, 'AuditCustApplyAllId'),
	/**
	 * 企业客户贷款一览表面板ID
	 */
	auditEntCustApplyAllId : Ext.id(null, 'AuditEntCustApplyAllId'),
	/**
	 * 个人客户贷款审批历史面板ID
	 */
	auditCustApplyHistoryId : Ext.id(null, 'AuditCustApplyHistoryId'),
	/**
	 * 企业客户贷款审批历史面板ID
	 */
	auditEntCustApplyHistoryId : Ext.id(null, 'AuditEntCustApplyHistoryId'),
	/**
	 * 暂存的提前还款申请单面板ID
	 * 
	 * @type String
	 */
	tempPrepaymentApplyMgrId : Ext.id(null, 'tempPrepaymentApplyMgrId'),
	/**
	 * 暂存的展期申请单面板ID
	 * 
	 * @type String
	 */
	tempExtensionApplyMgrId : Ext.id(null, 'tempExtensionApplyMgrId'),
	/**
	 * 展期审批面板ID
	 * 
	 * @type String
	 */
	auditExtensionMgrId : Ext.id(null, 'auditExtensionMgrId'),
	/**
	 * 暂存的息费豁免申请单面板ID
	 * 
	 * @type String
	 */
	tempExemptApplyMgrId : Ext.id(null, 'tempExemptApplyMgrId'),
	/**
	 * 贷款审批流程表单申请时要刷新的UI 凡是像与贷款审批流程相关的UI 只要在此数组中注册，当申请表单保存后就会自动刷新指定的页面
	 * 
	 * @type
	 */
	getVarietyApplyRefreshIds : function() {
		return [this.tempCustApplyMgrId, this.tempEntCustApplyMgrId,
				this.auditCustApplyMgrId, this.auditEntCustApplyMgrId,
				this.auditCustApplyAllId, this.auditEntCustApplyAllId,
				this.auditCustApplyHistoryId, this.auditEntCustApplyHistoryId,
				this.desk_mod_fin_1, this.desk_mod_fin_2, this.desk_mod_fin_3,
				this.desk_mod_fin_4];
	},
	/**
	 * 子业务流程表单申请时要刷新的UI 凡是像与子业务流程相关的UI 只要在此数组中注册，当申请表单保存后就会自动刷新指定的页面
	 * 
	 * @type
	 */
	getBussProccApplyRefreshIds : function() {
		return [this.tempExtensionApplyMgrId, this.tempPrepaymentApplyMgrId,
				this.tempExemptApplyMgrId, this.auditExtensionMgrId];
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
	 * 
	 * @type
	 */
	desk_mod_fin_9 : 'DESK_MOD_FIN_9',
	/**
	 * 桌面模块 --> 待提交息费豁免申请单
	 * 
	 * @type
	 */
	desk_mod_fin_10 : 'DESK_MOD_FIN_10',
	/**
	 * 桌面模块 --> 待提交提前还款申请单
	 * 
	 * @type
	 */
	desk_mod_fin_11 : 'DESK_MOD_FIN_11',
	/**
	 * 桌面模块 --> 待审批的展期申请单
	 * 
	 * @type
	 */
	desk_mod_fin_12 : 'DESK_MOD_FIN_12',
	/**
	 * 桌面模块 --> 待审批息费豁免申请单
	 * 
	 * @type
	 */
	desk_mod_fin_13 : 'DESK_MOD_FIN_13',
	/**
	 * 桌面模块 --> 待审批提前还款申请单
	 * 
	 * @type
	 */
	desk_mod_fin_14 : 'DESK_MOD_FIN_14',

	/**
	 * 当业务审批后有可能要刷新的功能对象ID列表
	 * 
	 * @type
	 */
	flowRefreshIdsArr : [this.tempCustApplyMgrId, this.tempEntCustApplyMgrId,
			this.auditCustApplyMgrId, this.auditEntCustApplyMgrId,
			this.auditCustApplyAllId, this.auditEntCustApplyAllId,
			this.auditCustApplyHistoryId, this.auditEntCustApplyHistoryId,
			this.desk_mod_fin_1, this.desk_mod_fin_2, this.desk_mod_fin_3,
			this.desk_mod_fin_4]
};

/**
 * 子流程自定义表单路径 规则:{流程编号:业务表单路径}
 * 
 * @type
 */
var Flow_CustForm_Url = {
	/**
	 * 贷款申请单详情
	 * 
	 * @type String
	 */
	B9999 : 'pages/app/workflow/variety/mainUIs/ApplyDetailMod.js',
	/**
	 * 展期内容对应的审批详情表单
	 * 
	 * @type String
	 */
	B0001 : 'pages/app/workflow/bussProcc/formUIs/ExtensionDetailMod',
	/**
	 * 提前还款对应的审批详情表单
	 * 
	 * @type String
	 */
	B0002 : 'pages/app/workflow/bussProcc/formUIs/PrepaymentDetailMod',
	/**
	 * 息费豁免业务审批详情表单
	 * 
	 * @type String
	 */
	B0003 : 'pages/app/workflow/bussProcc/formUIs/ExemptDetailMod'
}
/**
 * 打印标识 此常量数据将会插入到 ts_PrintLog(打印日志表)中
 * 
 * @type
 */
var PrintLog_FunTag = {
	A0001 : 'A0001'/* 提前结清单据打印标识 ---> 对应提前还款收款后的单据 */
}

/** ------------------ 小额贷款业务常量定义 CODE END -------------------* */
/**
 * 业务常量定义
 * 
 * @type
 */
var Buss_Constant = {
	/**
	 * 审批记录表(ts_auditrecords)
	 * 
	 * @type
	 */
	AuditRecords : {
		/*----- 消息通知类型  ------*/
		notifys_2 : 2, /* 2:手机短消息 */
		notifys_3 : 3
/* 3:邮件通知 */
	},
	/**
	 * Buss_Constant.CustType_0 客户类型-->[0:个人客户]
	 * 
	 * @type Number
	 */
	CustType_0 : 0,
	/**
	 * Buss_Constant.CustType_1 客户类型-->[1:企业客户]
	 * 
	 * @type Number
	 */
	CustType_1 : 1
}

/**
 * 流程常量
 * @type 
 */
var Flow_Constant = {
	/**
	 * 待删除的参与者ID标识
	 */
	DEL_ASSIGNEEID : 'DEL_999999',
	/**
	 * 串行会签任务名称前缀
	 * @type String
	 */
	SERIAL_CSTASK_PREFIX : 'SERIAL_CSTASK_',
	/**
	 * 并行会签任务名称前缀
	 * @type String
	 */
	PARALLEL_CSTASK_PREFIX : 'PARALLEL_CSTASK_',
	/**
	 * 补签任务名称前缀
	 * @type String
	 */
	RETROACTIVE_CSTASK_PREFIX : 'RETROACTIVE_CSTASK_',
	/**
	 * 并行分支节点标识
	 * @type String
	 */
	NODENAME_FORK : 'FORK',
	/**
	 * 并行聚合节点标识
	 * @type String
	 */
	NODENAME_JOIN : 'JOIN',
	/**
	 * 补签节点标识
	 * @type String
	 */
	NODENAME_SIGIN : 'SIGIN',
	/**
	 * 结束节点标识
	 * @type String
	 */
	NODENAME_END : 'END',
	/**
	 * 异常结束节点标识
	 * @type String
	 */
	NODENAME_SERVICE_END : 'SERVICE_END',
	/**
	 * 会签节点标识
	 * @type String
	 */
	NODENAME_COUNTERSIGN : 'COUNTERSIGN'
}
var ApplyActivePnlId = {
	topActivePnlId : Ext.id(null,'topActivePnlId')
}