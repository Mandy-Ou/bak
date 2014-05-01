package com.cmw.dao.impl.funds;


import java.math.BigDecimal;

import org.springframework.dao.DataAccessException;
import org.springframework.expression.AccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.AmountProofDaoInter;
import com.cmw.entity.funds.AmountProofEntity;


/**
 * 资金追加申请DAO实现类
 * @author 彭登浩
 * @date 2014-02-08T00:00:00
 */
@Description(remark="资金追加申请DAO实现类",createDate="2014-02-08T00:00:00",author="彭登浩")
@Repository("amountProofDao")
public class AmountProofDaoImpl extends GenericDaoAbs<AmountProofEntity, Long> implements AmountProofDaoInter{

	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getResultList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params =  SqlUtil.getSafeWhereMap(params);
		try{
			StringBuffer sqlSb = new StringBuffer();
			StringBuffer colmns = new StringBuffer();
			colmns.append(" id,contractId,bdate#yyyy-MM-dd,amount,bamount,tamount,appDate#yyyy-MM-dd,procId,status,appManName,custName,custType,code,loanCode");
			sqlSb.append(" select A.id ,A.contractId,A.bdate,A.amount,A.bamount,A.tamount,A.appDate,A.procId,A.status, ")
			.append(" D.empName as appManName ,C.custName,C.custType,C.code,B.code loanCode ")
			.append(" from fu_AmountProof A inner join fc_LoanContract B on B.id = A.contractId  ")
			.append(" inner join ts_user D on A.appManId = D.userId ")
			.append("inner join  ( ")
			.append("(select XX.custType,XX.name as custName,YY.id as customerId,XX.code   ")
			.append("from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId  where XX.custType = 0  ") 	
			.append(") ")
			.append("union ")  
			.append("(select XX.custType,XX.name as custName ,YY.id as customerId ,XX.code ")
			.append("from   crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType = 1  ")  
			.append(") ")
			.append(")C on B.custType = C.custType and B.customerId = C.customerId  where A.isenabled = '"+SysConstant.OPTION_ENABLED+"' ");
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){
				sqlSb.append(" and C.custName like '%"+name+"%' ");
			}
			String bdate = params.getvalAsStr("bdate");
			if(StringHandler.isValidStr(bdate)){
				sqlSb.append(" and A.bdate = '"+bdate+"' ");
			}
			Double amount = params.getvalAsDob("amount");
			if(StringHandler.isValidObj(amount)){
				sqlSb.append(" and A.amount = '"+amount+"' ");
			}
			Double bamount = params.getvalAsDob("bamount");
			if(StringHandler.isValidObj(bamount)){
				sqlSb.append(" and A.bamount = '"+bamount+"' ");
			}
			Double tamount  = params.getvalAsDob("tamount");
			if(StringHandler.isValidObj(tamount)){
				sqlSb.append(" and A.tamount = '"+tamount+"' ");
			}
			String appDate  = params.getvalAsStr("appDate");
			if(StringHandler.isValidObj(appDate)){
				sqlSb.append(" and A.appDate = '"+appDate+"' ");
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append(" order by A.id desc");
			DataTable dt= findBySqlPage(sqlSb.toString(), colmns.toString(), offset, pageSize);
			dt.setSize(totalCount);
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
		
		
	}
	
}
