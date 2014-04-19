package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;


/**
 *  资金头寸分析表  DAO接口
 * @author 程明卫
 * @date 2013-08-05T00:00:00
 */
 @Description(remark="款发放利率报表 DAO接口",createDate="2013-08-05T00:00:00",author="程明卫")
public interface FundReportDaoInter  extends GenericDaoInter<Object, Long>{

	/**
	 * 现在到指定截止日期的还款计划应收的 [本金、利息、管理费]
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	 <K, V> DataTable getResultPlan(SHashMap<K, V> map) throws DaoException;
	 /**
	  * 现在到指定截止日期的合同放款单金额和合约放款日期
	  */
	 <K, V> DataTable getResultLoan(SHashMap<K, V> map) throws DaoException;
}