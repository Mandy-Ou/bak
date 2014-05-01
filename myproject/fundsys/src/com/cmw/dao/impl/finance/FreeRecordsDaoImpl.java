package com.cmw.dao.impl.finance;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.dao.inter.finance.FreeRecordsDaoInter;
import com.cmw.entity.finance.FreeRecordsEntity;


/**
 * 实收手续费  DAO实现类
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */

@Description(remark="实收手续费DAO实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Repository("freeRecordsDao")
public class FreeRecordsDaoImpl extends GenericDaoAbs<FreeRecordsEntity, Long> implements FreeRecordsDaoInter {
	@Override
	public <K, V> DataTable getDsByAmountLogId(SHashMap<K, V> params,
			int offset, int pageSize) throws DaoException {
		try{
			return super.getResultList(params, offset, pageSize);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	/**
	 * 正常手续费收取,
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {

		try {
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(" select C.id,E.account,E.bankName,A.freeamount,C.code,A.yamount,A.lastDate ")
					/*-->fc_LoanInvoce:放款单表, fc_LoanContract:借款合同表,fc_Apply:贷款申请,crm_CustomerInfo:个人客户基本信息表	<--*/
					.append(" from fc_Free A inner join fc_LoanInvoce B on A.loanInvoceId=B.id ")
					.append(" inner join ts_Account E on E.id = B.accountId ")
					.append(" inner join fc_LoanContract C on B.contractId = C.id ")
					.append(" where A.isenabled = '"+SysConstant.OPTION_ENABLED+"'  ")
					.append(" and A.status in("+BussStateConstant.FREE_STATUS_1+","+BussStateConstant.FREE_STATUS_2+")  ");
				String cmns = null;
				cmns = "id,account,bankName,freeamount,code,yamount,lastDate#yyyy-MM-dd";
			long totalCount = getTotalCountBySql(sqlSb.toString());
			DataTable dt = findBySqlPage(sqlSb.toString(), cmns, offset,pageSize, totalCount);
			dt.setColumnNames(cmns);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}