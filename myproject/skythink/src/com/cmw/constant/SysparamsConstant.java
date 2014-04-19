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
	
	/**
	 * Sysparams 随借随还利息算法引用键 [ RANDOM_ALGORITHM_KEY : RANDOM_ALGORITHM ]
	 */
	public static final String  RANDOM_ALGORITHM_KEY = "RANDOM_ALGORITHM";
	/**
	 * Sysparams 随借随还利息算法值 [ RANDOM_ALGORITHM_1 : 1(按银行方式全部按天算 ) ]
	 */
	public static final String  RANDOM_ALGORITHM_VAL_1 = "1";
	
	/**
	 * Sysparams 随借随还利息算法值 [ RANDOM_ALGORITHM_2 : 2(从上一结息日到随借随还收款日期按天算,之前如果足月按月算 ) ]
	 */
	public static final String  RANDOM_ALGORITHM_VAL_2 = "2";
	/**
	 * Sysparams 随借随还利息算法值 [ RANDOM_ALGORITHM_3 : 3(以还款计划表为标准（即根据还款计划表中的应收利息和管理费作为参考值；如果使用该参数，系统不会重新生成还款计划） ) ]
	 */
	public static final String  RANDOM_ALGORITHM_VAL_3 = "3";
	/**
	 * Sysparams 随借随还利息算法引用键 [ ISNOT_SETTLE : ISNOT_SETTLE ]
	 */
	public static final String ISNOT_SETTLE = "ISNOT_SETTLE";
	/**
	 * 当还款计划表中的最大期数结清是的时候自动更新贷款申请表中的状态为结清
	 */
	public static final String ISNOT_SETTLE_1 = "1";
	/**
	 *当还款计划表中的最大期数结清是的时候需要手动更新贷款申请表中的状态为结清
	 */
	public static final String ISNOT_SETTLE_2 = "2";
}
