package com.cmw.constant;
/**
 * 系统参数常量定义
 * @author chengmingwei
 *
 */
public class SysparamsConstant {
	/**
	 * 系统参数 KEY [自动转逾期:OVER_TO_LATE_HOUR]
	 * -1:不需要自动转逾期,[0-24] 则在指定的小时转逾期
	 */
	public static final String OVER_TO_LATE_HOUR = "OVER_TO_LATE_HOUR";
	/**
	 * 系统参数 KEY [自动计算罚息滞纳金时间:CACLUTE_PEN_DEL_HOUR]
	 */
	public static final String CACLUTE_PEN_DEL_HOUR = "CACLUTE_PEN_DEL_HOUR";
	
	/**
	 * 系统参数 KEY [最低罚息金额:MIN_PEN_AMOUNT]
	 */
	public static final String MIN_PEN_AMOUNT = "MIN_PEN_AMOUNT";
	/**
	 * 系统参数 KEY [最低滞纳金:MIN_DEL_AMOUNT]
	 */
	public static final String MIN_DEL_AMOUNT = "MIN_DEL_AMOUNT";
	/**
	 * 系统参数 KEY [批量提取逾期数据条数:BATCH_LATE_DATAS_COUNT]
	 */
	public static final String BATCH_LATE_DATAS_COUNT = "BATCH_LATE_DATAS_COUNT";
	/**
	 * Sysparams 系统参数项 [ LOAN_ONE_COUNT : 一笔放款单作为一笔投放笔数 ]
	 */
	public static final String  LOAN_ONE_COUNT = "LOAN_ONE_COUNT";
	/**
	 * Sysparams 随借随还利息头尾计算方式 [ RANDOM_SE_TYPE : 随借随还利息头尾计算方式 取值：(1:算头又算尾,2:算头不算尾 ) ]
	 */
	public static final String  RANDOM_SE_TYPE = "RANDOM_SE_TYPE";
	/**
	 * Sysparams 随借随还利息头尾计算方式值 [ RANDOM_SE_TYPE_VAL_1 : 1(算头又算尾)]
	 */
	public static final String  RANDOM_SE_TYPE_VAL_1 = "1";
	/**
	 * Sysparams 随借随还利息头尾计算方式值 [ RANDOM_SE_TYPE_VAL_2 : 2(算头不算尾 ) ]
	 */
	public static final String  RANDOM_SE_TYPE_VAL_2 = "2";
}
