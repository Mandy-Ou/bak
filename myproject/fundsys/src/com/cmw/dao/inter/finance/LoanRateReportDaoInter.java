package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.LoanInvoceEntity;


/**
 *  贷款发放利率报表  DAO接口
 * @author 程明卫
 * @date 2013-08-05T00:00:00
 */
 @Description(remark="款发放利率报表 DAO接口",createDate="2013-08-05T00:00:00",author="程明卫")
public interface LoanRateReportDaoInter  extends GenericDaoInter<LoanInvoceEntity, Long>{
	/**
	 * 获取还款日在指定年份或之前的合同ID
	 * @param map
	 * @return
	 * @throws DaoException 
	 */
	public  <K, V>  String getContractIds(SHashMap<K, V> map) throws DaoException;
	/**
	 * 获取每月的放贷总金额 
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	public <K, V> DataTable getMonthAppAmounts(SHashMap<K, V> map) throws DaoException;
}
