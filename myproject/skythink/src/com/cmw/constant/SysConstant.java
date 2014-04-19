package com.cmw.constant;
/**
 * 系统常量类
 * @author cmw_1984122
 *
 */
public class SysConstant {
	/**
	 *Excel 到数据传递的参数，区分传递的方法的参数 1:	直接调用datasource 		
	 */
	public static final Integer EXCEL_METHOD_1 = 1;
	/**
	 *Excel 到数据传递的参数，区分传递的方法的参数 2:	调用 自己的方法		
	 */
	public static final Integer EXCEL_METHOD_2 = 2;
	/**
	 * 判断是否是超级管理员用户
	 */
	public static final Integer USER_ISSYSTEM = 1; 
	/**
	 * 菜单节点的父ID
	 */
	public static final long PID = 0l;
	/**
	 * 用来标识一个不存在的ID (例如：系统定时作业时，代表系统行为的ID)
	 */
	public static final long NOEXIST_ID = -99999999;
	/**
	 * 系统管理员 ID 编号[虚拟ID]
	 */
	public static final long ADMIN_ID = 99999999; 
	/**
	 * 利率换算时的 100分比
	 */
	public final static int PERCENT_DENOMINATOR = 100;
	
	/*["-1","无"],["0","本人数据"],["1","自定义用户数据"],["2","本部门数据"],["3","本部门及子部门"],
				  ["4","自定义部门"],["5","本公司数据"],["6","自定义公司"],["7","无限制"]*/
	/**
	 * 加载方式 	[1:sql]
	 */
	public static final int LOADTYPE_1 = 1;
	/**
	 * 加载方式 	[2:hql]
	 */
	public static final int LOADTYPE_2 = 2;
	/**
	 * 加载方式 	[3:业务方法]
	 */
	public static final int LOADTYPE_3 = 3;
	
	/**
	 * 数据展示方式 	[1:单条显示]
	 */
	public static final int DISPTYPE_1 = 1;
	/**
	 * 数据展示方式 	[2:多条显示]
	 */
	public static final int DISPTYPE_2 = 2;
	
	/**
	 * 打印类型 [1:根据模板打印空白文档]
	 */
	public static final int PRINTTYPE_1 = 1;
	/**
	 * 打印类型 [12:打印填充数据后的文档]
	 */
	public static final int PRINTTYPE_2 = 2;
	/**
	 * 数据访问级别 -1:无	
	 */
	public static final byte DATA_LEVEL_NO = -1;
	/**
	 * 数据访问级别  0:本人数据
	 */
	public static final byte DATA_LEVEL_SELF = 0;
	/**
	 * 数据访问级别  1:自定义用户数据
	 */
	public static final byte DATA_LEVEL_CUSTUSER = 1;
	/**
	 * 数据访问级别  2: 本部门数据
	 */
	public static final byte DATA_LEVEL_CURRDEPT = 2;
	/**
	 * 数据访问级别  3: 本部门及所有子部门数据
	 */
	public static final byte DATA_LEVEL_CURR_CHILDS_DEPT = 3;
	/**
	 * 数据访问级别 4: 自定义部门数据
	 */
	public static final byte DATA_LEVEL_CUSTOMDEPT = 4;
	/**
	 * 数据访问级别  5: 本公司数据
	 */
	public static final byte DATA_LEVEL_CURRCOMPANY = 5;
	/**
	 * 数据访问级别  6: 自定义公司数据
	 */
	public static final byte DATA_LEVEL_CUSTCOMPANY = 6;
	/**
	 * 数据访问级别  7: 无限制
	 */
	public static final byte DATA_LEVEL_ALL = 7;
	/**
	 * 登录限制0:无限制, 
	 */
	public static final Integer LOGLIMIT_0 = 0;
	/**
	 * 登录限制 1:限制到IP段,
	 */
	public static final Integer LOGLIMIT_1 = 1;
	/**
	 * 登录限制 2:限制到机器
	 */
	public static final Integer LOGLIMIT_2 = 2;
	
	/**
	 * 当前用户 KEY 标识
	 */
	public static final String USER_KEY = "user";
	/**
	 * 生成财务凭证的标识
	 */
	public static final int SAVE_VOUCHER_1 = 1;
	/**
	 * 根节点ID
	 */
	public static final int MENU_ROOT_ID = 0;
	/**
	 * 菜单类型 ------ 卡片菜单
	 */
	public static final int MENU_TYPE_CARD = 1;
	/**
	 * 菜单类型 ------ 节点菜单
	 */
	public static final int MENU_TYPE_NODE = 2;
	
	/**
	 * 实体类从父类继承的字段  ----- isenabled [可用标识]
	 */
	public static final String ISENABLED = "isenabled";
	/**
	 * 实体类从父类继承的字段  -----  remark[备注]
	 */
	public static final String REMARK = "remark";
	/**
	 * 实体类从父类继承的字段  -----  creator[创建人]
	 */
	public static final String CREATOR = "creator";
	/**
	 * 实体类从父类继承的字段  -----  createTime[创建时间]
	 */
	public static final String CREATETIME = "createTime";
	/**
	 * 实体类从父类继承的字段  -----  deptId[部门ID]
	 */
	public static final String DEPTID = "deptId";
	/**
	 * 实体类从父类继承的字段  -----  orgid[机构ID]
	 */
	public static final String ORGID = "orgid";
	/**
	 * 实体类从父类继承的字段  -----  empId[员工ID]
	 */
	public static final String EMPID = "empId";
	//
	/**
	 * 实体类从父类继承的字段  -----  modifier[修改人ID]
	 */
	public static final String MODIFIER = "modifier";
	/**
	 * 实体类从父类继承的字段  -----  modifytime[修改时间]
	 */
	public static final String MODIFYTIME = "modifytime";
	/**
	 * 首页  ----- 左边导航菜单
	 */
	public static final int MENU_ACTION_NAV = 0;
	/**
	 * 菜单管理  ----- 菜单所传参数
	 */
	public static final int MENU_ACTION_SYS = 1;
	/**
	 * 角色管理  ----- 菜单权限树所传参数
	 */
	public static final int MENU_ACTION_ROLE = 2;
	/**
	 * 业务品种管理  ----- 流程配置，业务表单树所传参数
	 */
	public static final int MENU_ACTION_PROCESSFORM = 3;
	
	/**
	 * 组织架构树   ----- [3:公司]用户自定义权限所传参数
	 */
	public static final int ORG_ACTION_COMPANY_3 = 3;
	/**
	 * 组织架构树   ----- [1:部门]用户自定义权限所传参数
	 */
	public static final int ORG_ACTION_DEPT_1 = 1;
	/**
	 * 组织架构树   ----- [2:用户]用户自定义权限所传参数
	 */
	public static final int ORG_ACTION_USER_2 = 2;
	/**
	 * 操作类型  ---- 添加
	 */
	public static final int OPTION_ADD = 1;
	/**
	 * 操作类型  ---- 修改
	 */
	public static final int OPTION_EDIT = 2;
	/**
	 * 操作类型  ---- 删除
	 */
	public static final int OPTION_DEL = -1;
	/**
	 * 操作类型  ---- 起用
	 */
	public static final int OPTION_ENABLED = 1;
	
	
	
	/**
	 * 操作类型  ---- 禁用
	 */
	public static final int OPTION_DISABLED = 0;
	/**
	 * 在卡片菜单中，编号以 "FORM_" 开头的默认是流程业务表单功能
	 */
	public static final String ACCORDION_CODE_TYPE_FORM_PREFIX = "FORM_";
	/**
	 * 在卡片菜单中，编号以 "SUBFORM_" 开头的默认是子业务流程业务表单功能
	 */
	public static final String ACCORDION_CODE_TYPE_SUBFORM_PREFIX = "SUBFORM_";
	
	
	/**
	 * 是否为财务放款人员 【1 ： 是】
	 * 
	 */
	public static final int POST_ISLOAN_1 = 1;
	/**
	 * 是否为财务放款人员 【2 ： 否】
	 */
	public static final int POST_ISLOAN_2 = 2;
	
	
	/**
	 * 客户类型  ---- 个人客户：0
	 */
	public static final int CUSTTYPE_0 = 0;
	/**
	 * 客户类型  ---- 企业客户：1
	 */
	public static final int CUSTTYPE_1 = 1;
	/**
	 * 客户分类
	 * 客户分类[企业法人 ---- 企业法人：0]
	 */
	public static final int CATEGORY_0 = 0;
	
	/**
	 * 客户分类
	 * 客户分类[共同借款人 ---- 共同借款人：1]
	 */
	public static final int CATEGORY_1 = 1;
	/**
	 * 客户分类
	 * 客户分类[第三方担保人 ---- 第三方担保人：2]
	 */
	public static final int CATEGORY_2 = 2;
	/**
	 * 暂存申请单 功能用到
	 * 动作类型[暂存申请单 ---- 贷款审批：0]
	 */
	public static final int ACTION_TYPE_APPLYFORM_AUDIT_0 = 0;
	/**
	 * 个人/企业客户贷款审批 功能用到
	 * 动作类型[待办审批单 ---- 贷款审批：1]
	 */
	public static final int ACTION_TYPE_APPLYFORM_AUDIT_1 = 1;
	
	/**
	 * 个人/企业客户贷款审批历史 功能用到
	 * 动作类型[待办审批历史 ---- 贷款审批历史：2]
	 */
	public static final int ACTION_TYPE_APPLYFORM_AUDIT_2 = 2;
	/**
	 * 个人/企业客户贷款一览表 功能用到
	 * 动作类型[一览表 ---- 个人/企业客户贷款一览表：3]
	 */
	public static final int ACTION_TYPE_APPLYFORM_AUDIT_3 = 3;
	
	/**
	 * 消息通知类型  ---- 手机短信通知：2
	 */
	public static final int NOTYFY_TYPE_PHONE_2 = 2;
	/**
	 * 消息通知类型  ---- 邮件通知：3
	 */
	public static final int NOTYFY_TYPE_EMAIL_3 = 3;
	
	
	/**
	 * 动作类型[1: 表示是从BussFlowService 调用  see:  NodeCfgDaoImpl ]
	 */
	public static final int ACTION_TYPE_NODECFG_1 = 1;
	/**
	 * 动作类型[1: 表示是从BussFlowService 调用   see:  TransCfgDaoImpl ]
	 */
	public static final int ACTION_TYPE_TRANSCFG_1 = 1;
	/**
	 * 系统业务类型 [1:小额贷款业务主流程]
	 */
	public static final int SYSTEM_BUSSTYPE_FINANCE_APPLY = 1;
	/**
	 * 系统业务类型 [2:子业务流程]
	 */
	public static final int SYSTEM_BUSSTYPE_BUSSPROCC_APPLY = 2;
	/**
	 * 系统业务类型 [10:担保业务主流程]
	 */
	public static final int SYSTEM_BUSSTYPE_ASSURANCE_APPLY = 10;
	
	/**
	 * 扣收顺序 [L0001:利息]
	 */
	public static final String ORDER_L0001 = "L0001";
	/**
	 * 扣收顺序 [L0002:本金]
	 */
	public static final String ORDER_L0002 = "L0002";
	/**
	 * 扣收顺序 [L0003:管理费]
	 */
	public static final String ORDER_L0003 = "L0003";
	/**
	 * 扣收顺序 [L0004:罚息]
	 */
	public static final String ORDER_L0004 = "L0004";
	/**
	 * 扣收顺序 [L0005:滞纳金]
	 */
	public static final String ORDER_L0005 = "L0005";
	/**
	 * 日利率转换为年利率或年利率转日利率的基数 [360]
	 */
	public static final int RATE_DAY2YEAR = 360;
	/**
	 * 月利率转换为年利率或年利率转月利率的基数 [12]
	 */
	public static final int RATE_MONTH2YEAR = 12;
	/**
	 * 月利率转换为日利率或日利率转月利率的基数 [30]
	 */
	public static final int RATE_MONTH2DAY = 30;
	
	
	/**
	 * 组织架构树 ---- 公司图标节点路径
	 */
	//public static final String COMPANY_ICONPATH_0 = "extlibs/ext-3.3.0/resources/images/icons/application_home.png";
	public static final String COMPANY_ICONPATH_0 = "extlibs/ext-3.3.0/resources/images/icons/company.png";
	
	/**
	 * 组织架构树 ---- 分公司图标节点路径
	 */
	public static final String COMPANY_ICONPATH_1 = "extlibs/ext-3.3.0/resources/images/icons/star.png";
	
	/**
	 * 组织结构树————>部门节点图标路径
	 */
	public static final String DEPARTMENT_ICONPATH = "extlibs/ext-3.3.0/resources/images/icons/television-txr.png";
	/**
	 * 组织结构树————>职位节点图标路径
	 */
	public static final String POST_ICONPATH = "extlibs/ext-3.3.0/resources/images/icons/award_star_bronze_1-txr.png";
	
	/**
	 * 角色树 ---- 角色图标节点路径
	 */
//	public static final String ROLE_ICONPATH = "extlibs/ext-3.3.0/resources/images/icons/vcard_edit-txr.png";
	public static final String ROLE_ICONPATH = "extlibs/ext-3.3.0/resources/images/icons/role.png";
	/**
	 * 资源树---- 资源图标节点路径
	 */
	public static final String RESTYPE_ICONPATH = "extlibs/ext-3.3.0/resources/images/icons/rainbow.png";
	/**
	 * 表单个性化————表单图标
	 */
	public static final String FORMDIY_ICONPATH ="extlibs/ext-3.3.0/resources/images/icons/formdiy.png";
	/**
	 * 模板————表单图标
	 */
	public static final String MATTEMP_ICONPATH ="extlibs/ext-3.3.0/resources/images/icons/tem.png";
	/**
	 * 区域列表----国家图片
	 */
	public static final String COUNTRY_ICONPATH ="extlibs/ext-3.3.0/resources/images/icons/world.png";
	
	/**
	 * 区域列表----省份图片
	 */
	public static final String PROVINCE_ICONPATH ="extlibs/ext-3.3.0/resources/images/icons/award_star_gold_3.png";
	/**
	 * 区域列表----城市图片
	 */
	public static final String CITY_ICONPATH ="extlibs/ext-3.3.0/resources/images/icons/rosette.png";
	
	/**
	 * 区域列表----地区图片
	 */
	public static final String REGION_ICONPATH ="extlibs/ext-3.3.0/resources/images/icons/map.png";
	/**
	 * 财务系统配置----财务系统图片
	 */
	public static final String FINANCESYS_ICONPATH ="extlibs/ext-3.3.0/resources/images/icons/financesys.png";
	/**
	 * 菜单----菜单图片
	 */
	public static final String MENUS_ICONPATH ="extlibs/ext-3.3.0/resources/images/icons/menus.png";
}
