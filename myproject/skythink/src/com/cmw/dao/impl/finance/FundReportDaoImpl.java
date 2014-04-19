package com.cmw.dao.impl.finance;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.finance.FundReportDaoInter;


/**
 * Report  DAO实现类
 * @author 程明卫
 * @date 2013-08-05T00:00:00
 */
@Description(remark="ReportDAO实现类",createDate="2013-08-05T00:00:00",author="程明卫")
@Repository("fundReportDao")
public class FundReportDaoImpl extends GenericDaoAbs<Object, Long> implements FundReportDaoInter {
	
	

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		try{
			StringBuilder sbHql = new StringBuilder();
			sbHql.append(" SELECT sum(uamount) FROM OwnFundsEntity A WHERE isenabled='"+SysConstant.OPTION_ENABLED+"'");
			DataTable dt = find(sbHql.toString(),"uamount");
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getResultList(com.cmw.core.util.SHashMap)
	 * 
	 * 
	 */
	@Override
	public <K, V> DataTable getResultPlan(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String currDate = map.getvalAsStr("currDate");
		String endDate = map.getvalAsStr("endDate");
		try{
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("SELECT xpayDate,(principal-yprincipal) AS zprincipal,")
			.append("(interest-yinterest) AS zinterest,(mgrAmount-ymgrAmount) AS zmgrAmount ")
			.append(" FROM PlanEntity A ")
			.append(" WHERE isenabled='"+SysConstant.OPTION_ENABLED+"' ")
			.append(" AND (A.xpayDate>='"+currDate+"' AND A.xpayDate<='"+endDate+"')")
			.append(" order by A.xpayDate ");
			return find(sbSql.toString(), "xpayDate,zprincipal,zinterest,zmgrAmount");
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.cmw.dao.inter.finance.FundReportDaoInter#getResultLoan(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultLoan(SHashMap<K, V> map) throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String currDate = map.getvalAsStr("currDate");
		String endDate = map.getvalAsStr("endDate");
		try{
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("SELECT (appAmount-ISNULL(B.payAmounts,0)) AS uappAmount,A.payDate ")
			.append(" FROM fc_LoanContract A  LEFT JOIN ")
			.append("(SELECT contractId,ISNULL(SUM(payAmount),0) AS payAmounts FROM dbo.fc_LoanInvoce GROUP BY contractId) AS B ")
			.append(" ON A.id=B.contractId ")
			.append("WHERE isenabled='")
			.append(SysConstant.OPTION_ENABLED)
			.append("' AND (A.payDate>='"+currDate+"' AND A.payDate<='"+endDate+"')")
			.append(" order by A.payDate ");
			DataTable dt = findBySql(sbSql.toString(), "uappAmount,payDate");
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	

	
}
