package com.cmw.dao.impl.finance;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.dao.inter.finance.BalanceReportDaoInter;


/**
 * 贷款余额汇总表  DAO实现类
 * @author 程明卫
 * @date 2013-08-17T00:00:00
 */
@Description(remark="贷款余额汇总表DAO实现类",createDate="2013-08-17T00:00:00",author="程明卫")
@Repository("balanceReportDao")
public class BalanceReportDaoImpl extends GenericDaoAbs<Object, Long> implements BalanceReportDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		try{
			DataTable dt = getReportByYear(map);
			return dt;
		}catch(DataAccessException e){
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 获取本年累计数据
	 * @param map
	 * //借款主体,行业分类,贷款方式,业务品种,贷款期限(月),贷款金额,贷款余额,放款金额,放贷笔数
	 * //loanMain,inType,loanType,breed,loanLimit,appAmount,zprincipals,payAmounts,loanCount
	 * @return
	 */
	private <K, V> DataTable getReportByYear(SHashMap<K, V> map){
		StringBuilder sbSql = new StringBuilder();
		String yearLoan = map.getvalAsStr("yearLoan");
		String monthLoan = map.getvalAsStr("monthLoan");
		Integer state = map.getvalAsInt("state");
		String bussTags = BussStateConstant.AMOUNTLOG_BUSSTAG_0+","+BussStateConstant.AMOUNTLOG_BUSSTAG_2
				+","+BussStateConstant.AMOUNTLOG_BUSSTAG_4+","+BussStateConstant.AMOUNTLOG_BUSSTAG_6
				+","+BussStateConstant.AMOUNTLOG_BUSSTAG_7;
		sbSql.append("select  A.loanMain, A.inType, A.loanType, A.breed,")
		.append("DATEDIFF(MM, B.payDate, B.endDate) AS loanLimit,ROUND(B.appAmount / 10000, 2) AS appAmount,")
		.append("ROUND(( B.appAmount - ISNULL(D.yprincipals,0)), 4) AS zprincipals,C.loanCount ")
		.append(" FROM fc_Apply A INNER JOIN fc_LoanContract B ON A.id = B.formId ")
		.append("  INNER JOIN ( SELECT COUNT(1) AS loanCount ,formId FROM   fc_LoanInvoce ")
		.append(" where year(realDate)='").append(yearLoan)
		.append("' and month(realDate)<='").append(monthLoan)
		.append("' and state='").append(state).append("' ")
		.append("  GROUP BY formId ")
		.append(" ) C ON A.id = C.formId ")
		.append("  LEFT JOIN ( SELECT ISNULL(SUM(cat), 0) AS yprincipals,contractId FROM   fc_AmountRecords ")
		.append("  WHERE  YEAR(rectDate) = '").append(yearLoan).append("' ")
		.append(" AND MONTH(rectDate) <= '").append(monthLoan).append("' ")
		.append(" AND bussTag IN ("+bussTags+") ")
		.append(" GROUP BY contractId ")
		.append(") D ON B.id = D.contractId ")
		.append(" where 1=1 ");
		String colNames = "loanMain,inType,loanType,breed,loanLimit,appAmount,zprincipals,loanCount";
		DataTable dt = findBySql(sbSql.toString(), colNames);
		return dt;
	}
}
