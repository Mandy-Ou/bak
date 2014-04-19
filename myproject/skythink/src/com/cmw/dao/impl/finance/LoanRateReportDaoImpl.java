package com.cmw.dao.impl.finance;


import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.LoanRateReportDaoInter;
import com.cmw.entity.finance.LoanInvoceEntity;


/**
 * Report  DAO实现类
 * @author 程明卫
 * @date 2013-08-05T00:00:00
 */
@Description(remark="ReportDAO实现类",createDate="2013-08-05T00:00:00",author="程明卫")
@Repository("loanRateReportDao")
public class LoanRateReportDaoImpl extends GenericDaoAbs<LoanInvoceEntity, Long> implements LoanRateReportDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		try{
			String contractIds = map.getvalAsStr("contractIds");
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("select month(A.realdate) as monthItem,")
			.append(" B.rateType,B.rate,B.appAmount ")
			.append(" from fc_LoanInvoce A join fc_LoanContract B ")
			.append(" on A.contractId=B.id where 1=1 ");
			
			if(!StringHandler.isValidStr(contractIds)) contractIds = "-1";
			sbSql.append(" and A.contractId in ("+contractIds+") ");
			
			String loanYear = map.getvalAsStr("loanYear");
			if(!StringHandler.isValidStr(loanYear)) loanYear = "-1";
			sbSql.append(" and year(A.realdate) = '"+loanYear+"' ");
			
			String monthItems = map.getvalAsStr("monthItems");
			if(!StringHandler.isValidStr(monthItems)) monthItems = "-1";
			sbSql.append(" and month(A.realdate) in ("+monthItems+") ");
			
			String state = map.getvalAsStr("state");
			if(!StringHandler.isValidStr(state)) state = "-1";
			sbSql.append(" and A.state = '"+state+"' ");
			
			sbSql.append(" order by A.realdate ");
			DataTable dt = findBySql(sbSql.toString());
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public <K, V> DataTable getMonthAppAmounts(SHashMap<K, V> map)
			throws DaoException {
		try{
			String contractIds = map.getvalAsStr("contractIds");
			StringBuilder sbSql = new StringBuilder();
			
			sbSql.append("select month(A.realdate) as monthItem,SUM(B.appAmount) as appAmounts ")
			.append(" from fc_LoanInvoce A join fc_LoanContract B on  A.contractId=B.id ")
			.append(" where 1=1 ");
			if(!StringHandler.isValidStr(contractIds)) contractIds = "-1";
			sbSql.append(" and A.contractId in ("+contractIds+") ");
			
			String loanYear = map.getvalAsStr("loanYear");
			if(!StringHandler.isValidStr(loanYear)) loanYear = "-1";
			sbSql.append(" and year(A.realdate) = '"+loanYear+"' ");
			
			String monthItems = map.getvalAsStr("monthItems");
			if(!StringHandler.isValidStr(monthItems)) monthItems = "-1";
			sbSql.append(" and month(A.realdate) in ("+monthItems+") ");
			
			String state = map.getvalAsStr("state");
			if(!StringHandler.isValidStr(state)) state = "-1";
			sbSql.append(" and A.state = '"+state+"' ");
			
			sbSql.append(" group by month(A.realdate) order by  month(A.realdate) ");
			DataTable dt = findBySql(sbSql.toString());
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 获取还款日在指定年份或之前的合同ID
	 * @param map
	 * @return
	 * @throws DaoException 
	 */
	public  <K, V>  String getContractIds(SHashMap<K, V> map) throws DaoException{
		try{
			StringBuilder sbSql = new StringBuilder();
			String loanYear = map.getvalAsStr("loanYear");
			sbSql.append("select distinct contractId from PlanEntity where year(xpayDate)<='"+loanYear+"'");
			@SuppressWarnings("unchecked")
			List<Object> list = (List<Object>)getSession().createQuery(sbSql.toString()).list();
			return (null == list || list.size() == 0) ? null : StringHandler.join(list.toArray());
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
