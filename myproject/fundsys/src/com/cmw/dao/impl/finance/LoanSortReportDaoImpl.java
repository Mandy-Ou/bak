package com.cmw.dao.impl.finance;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.dao.inter.finance.LoanSortReportDaoInter;
import com.cmw.entity.finance.LoanInvoceEntity;


/**
 * 贷款发放分类报表  DAO实现类
 * @author 程明卫
 * @date 2013-08-08T00:00:00
 */
@Description(remark="贷款发放分类报表DAO实现类",createDate="2013-08-08T00:00:00",author="程明卫")
@Repository("loanSortReportDao")
public class LoanSortReportDaoImpl extends GenericDaoAbs<LoanInvoceEntity, Long> implements LoanSortReportDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		try{
			Integer actionType = map.getvalAsInt("actionType");
			map.removes("actionType");
			DataTable dt = null;
			if(null != actionType && actionType.intValue() == 1){/*取月份数据*/
				dt = getReportByMonth(map);
			}else{/*取年份数据*/
				dt = getReportByYear(map);
			}
			return dt;
		}catch(DataAccessException e){
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 获取本月数据
	 * @param map
	 * //借款主体,行业分类,贷款方式,业务品种,贷款期限(月),放款金额,放贷笔数
	 * //loanMain,inType,loanType,breed,loanLimit,appAmount,payAmounts,loanCount
	 * @return
	 */
	private <K, V> DataTable getReportByMonth(SHashMap<K, V> map){
		StringBuilder sbSql = new StringBuilder();
		String yearLoan = map.getvalAsStr("yearLoan");
		String monthLoan = map.getvalAsStr("monthLoan");
		Integer state = map.getvalAsInt("state");
		sbSql.append("select A.loanMain,A.inType,A.loanType,A.breed,Datediff(MM,B.payDate,B.endDate) as loanLimit,")
		.append(" ROUND(B.appAmount/10000,2) as appAmount,ROUND(C.payAmounts,4) as payAmounts,C.loanCount ")
		.append(" from fc_Apply A inner join fc_LoanContract B on A.id = B.formId ")
		.append(" inner join (select count(1) as loanCount,sum(payAmount) as payAmounts,formId from fc_LoanInvoce ")
		.append(" where year(realDate)='").append(yearLoan).append("' and month(realDate)='").append(monthLoan).append("' and state='").append(state).append("' ")
		.append(" group by formId) C on A.id = C.formId ")
		.append(" inner join (select contractId ")
		.append(" from fc_Plan group by contractId) D on B.id = D.contractId ")
		.append(" where 1=1 ");
		String colNames = "loanMain,inType,loanType,breed,loanLimit,appAmount,payAmounts,loanCount";
		DataTable dt = findBySql(sbSql.toString(), colNames);
		return dt;
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
		sbSql.append("select A.loanMain,A.inType,A.loanType,A.breed,Datediff(MM,B.payDate,B.endDate) as loanLimit,")
		.append("ROUND(B.appAmount/10000,2) as appAmount,ROUND(C.payAmounts,4) as payAmounts,C.loanCount ")
		.append(" from fc_Apply A inner join fc_LoanContract B on A.id = B.formId ")
		.append(" inner join (select count(1) as loanCount,sum(payAmount) as payAmounts,formId from fc_LoanInvoce ")
		.append(" where year(realDate)='").append(yearLoan).append("' and month(realDate)<='").append(monthLoan).append("' and state='").append(state).append("' ")
		.append(" group by formId) C on A.id = C.formId ")
		.append(" where 1=1 ");
		String colNames = "loanMain,inType,loanType,breed,loanLimit,appAmount,payAmounts,loanCount";
		DataTable dt = findBySql(sbSql.toString(), colNames);
		return dt;
	}
}
