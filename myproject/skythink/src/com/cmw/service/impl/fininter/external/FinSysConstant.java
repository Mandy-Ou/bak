package com.cmw.service.impl.fininter.external;

/**
 * 财务接口相关常量
 * @author chengmingwei
 *
 */
public class FinSysConstant {
	
	/**
	 * 核算项类型 [客户:CustBaseEntity]
	 */
	public static final String ITEM_CLASS_CUSTOMER = "CustBaseEntity";
	
	/**
	 * 业务对象为空
	 */
	public static final String ERROR_OBJECTNAME_ISNULL = "objectName.isnull";
	/**
	 * ERROR:用户为空
	 */
	public static final String ERROR_USER_ISNULL = "user.isnull";
	/**
	 * ERROR:财务业务系统关联ID为空
	 */
	public static final String ERROR_FINSYSID_ISNULL = "finsysId.isnull";
	/**
	 * ERROR:与核算类别关联的自定义业务对象为空
	 */
	public static final String ERROR_BUSSOBJENTITY_ISNULL = "bussObjEntity.isnull";
	/**
	 * ERROR:客户核算类别ID为空
	 */
	public static final String ERROR_CUSTOMER_ITEMCLASSID_ISNULL = "customer.itemClassId.isnull";
	/**
	 * ERROR:财务系统用户映射对象为空
	 */
	public static final String ERROR_USERMAPING_OBJECT_ISNULL = "usermaping.object.isnull";
}
