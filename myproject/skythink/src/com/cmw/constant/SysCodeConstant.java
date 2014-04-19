package com.cmw.constant;
/**
 * 业务系统编号常量
 * @author chengmingwei
 *
 */
public class SysCodeConstant {
	/**
	 * 系统基础平台系统编号
	 */
	public static final String BASEPLATFORM_SYS_CODE = "S00001";
	/**
	 * 财务系统编号
	 */
	public static final String FININTER_SYS_CODE = "S00002";
	/**
	 * 小额贷款系统编号
	 */
	public static final String FINANCE_SYS_CODE = "S00003";
	/**
	 * 担保系统编号
	 */
	public static final String ASSURANCE_SYS_CODE = "S00004";
	/**
	 * 典当系统编号
	 */
	public static final String PAWN_SYS_CODE = "S00005";
	/**
	 *OA系统编号
	 */
	public static final String OA_SYS_CODE = "S00006";
	
	/**========================== 小额贷款系统模块资源编号常量定义(ts_restype：资源表) START CODE  ============================**/
	/**
	 * recode [借款主体:200000]
	 */
	public static final String RESTYPE_RECODE_200000 = "200000";
	/**
	 * recode [行业分类:200001]
	 */
	public static final String RESTYPE_RECODE_200001 = "200001";
	/**
	 * recode [贷款方式:200002]
	 */
	public static final String RESTYPE_RECODE_200002 = "200002";
	/**
	 * recode [期限种类:200003]
	 */
	public static final String RESTYPE_RECODE_200003 = "200003";
	
	
	/**
	 * 资金头寸分析报表 recode [未来周期:200005]
	 */
	public static final String RESTYPE_RECODE_200005 = "200005";
	/**========================== 小额贷款系统模块资源编号常量定义(ts_restype：资源表) END CODE  ============================**/
	
	/*-------------------- 小贷 业务流程编号 ------------------------*/
	/**
	 * 客户贷款审批流程编号
	 */
	public static final String BUSS_PROCC_CODE_FINAPPLY = "B9999";
	/**
	 * 展期审批流程编号
	 */
	public static final String BUSS_PROCC_CODE_EXTENSION = "B0001";
	/**
	 *  提前还款审批流程编号
	 */
	public static final String BUSS_PROCC_CODE_PREPAYMENT = "B0002";
	/**
	 *  息费豁免审批流程编号
	 */
	public static final String BUSS_PROCC_CODE_EXEMPT = "B0003";

}
