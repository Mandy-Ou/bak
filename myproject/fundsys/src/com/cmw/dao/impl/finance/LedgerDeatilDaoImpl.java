package com.cmw.dao.impl.finance;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.dao.inter.finance.LedgerDeatilDaoInter;
import com.cmw.entity.finance.ApplyEntity;

/**
 *Title: LedgerDeatilDaoImpl.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-11下午12:12:00
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="台账明细报表Dao实现类",createDate="2013-01-15T00:00:00",author="彭登浩")
@Repository("ledgerDeatilDao")
public class LedgerDeatilDaoImpl  extends GenericDaoAbs<Object, Long> implements LedgerDeatilDaoInter{
	

	@Override
	public  <K, V> DataTable getResultList(SHashMap<K, V> map, int offset,
			int pageSize) throws DaoException {
		try{
			String colNames = "id,code,custType,customerId,loanMain,inType,loanType,breed,procId," +
					"appAmount,limitType,yearLoan,monthLoan,dayLoan,payType," +
					"phAmount,rateType,Rate,isadvance,mgrtype,mrate,prate,arate,urate,appdate,manager,reason," +
					"sourceType,referrals,payremark,sourceDesc,totalPhases,paydPhases,State,GuaId,LoanInvoceId,realDate#yyyy-MM-dd";
			
			String sqlName = "A.id,A.code,A.custType,A.customerId,A.loanMain,A.inType,A.loanType,A.breed,A.procId," +
					"A.appAmount,A.limitType,A.yearLoan,A.monthLoan,A.dayLoan,A.payType," +
					"A.phAmount,A.rateType,A.Rate,A.isadvance,A.mgrtype,A.mrate,A.prate,A.arate,A.urate,A.appdate,A.manager,A.reason," +
					"A.sourceType,A.referrals,A.payremark,A.sourceDesc,A.totalPhases,A.paydPhases,A.State,A.GuaId,B.id as LoanInvoceId ,"+
					" (select max(lastDate) from fc_plan x where x.isenabled='"+SysConstant.OPTION_ENABLED+"' and B.contractId = x.id and A.State='"+BussStateConstant.FIN_APPLY_STATE_6+"' ) as realDate" ;
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append( " select  "+sqlName)
			.append(" from fc_apply A  inner join fc_LoanInvoce B on A.id = B.formId  where A.isenabled != '"+SysConstant.OPTION_DEL+"' " )
			.append(" and B.isenabled != '"+SysConstant.OPTION_DEL+"' and A.State >= '"+BussStateConstant.FIN_APPLY_STATE_3+"' ")
			.append(" and B.auditState = '"+BussStateConstant.LOANINVOCE_AUDITSTATE_2+"' and B.state = '"+BussStateConstant.LOANINVOCE_STATE_1+"' ");
			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, offset, pageSize);
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
}
