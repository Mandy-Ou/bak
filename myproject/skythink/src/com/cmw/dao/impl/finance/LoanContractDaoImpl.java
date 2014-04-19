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
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.LoanContractDaoInter;
import com.cmw.entity.finance.LoanContractEntity;


/**
 * 借款合同  DAO实现类
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
@Description(remark="借款合同DAO实现类",createDate="2013-01-11T00:00:00",author="pdh")
@Repository("loanContractDao")
public class LoanContractDaoImpl extends GenericDaoAbs<LoanContractEntity, Long> implements LoanContractDaoInter {
	
	/* (non-Javadoc)
	 * @see com.cmw.dao.inter.finance.LoanContractDaoInter#getSuperList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public DataTable getSuperList(SHashMap<String, Object> dtParams,
			int offset, int pageSize) throws DaoException {
		try {
				return super.getResultList(dtParams, offset, pageSize);
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}catch (Exception e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
	}


	/**
	 * 根据合同ID获取 合同ID, 合约入款日，罚息利率，滞纳金利率
	 * @param contractIds 合同ID字符串列表
	 * @return
	 * @throws DaoException
	 */
	@Override
	public <K, V> DataTable getRates(String contractIds)throws DaoException {
		if(!StringHandler.isValidStr(contractIds)) throw new DaoException("invoke getIds methos : contractIds is null ");
		String hql = "select id,payDate,urate,frate from LoanContractEntity A where A.id in ("+contractIds+")";
		return find(hql, "id,payDate,urate,frate");
	}


	/**
	 * 借款合同报表查询
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append(" select A.id, A.formId,A.code,A.custType,A.accName,A.doDate,A.mgrtype, ")
			 .append(" A.isadvance,A.rateType,B.name as payType,A.endDate,A.payDay,A.payDate,")
			 .append(" (case when A.yearLoan>0 then cast(A.yearLoan as varchar(5))+'年' else '' end)+")
			 .append(" (case when A.monthLoan>0 then cast(A.monthLoan as varchar(3))+'个月' else '' end)+")
			 .append(" (case when A.dayLoan>0 then cast(A.dayLoan as varchar(3))+'天' else '' end) as loanLimit,")
			 .append(" A.payAccount,A.payBank,A.borAccount,A.borBank,A.appAmount,A.frate,A.urate,A.arate,A.prate,A.mrate,A.rate,A.clause ")
			 .append(" from fc_LoanContract A inner join fc_PayType B on A.payType = B.code")
			 .append(" where A.isenabled != "+SysConstant.OPTION_DEL);
		String code = params.getvalAsStr("code");
		String accName = params.getvalAsStr("accName");
		String payAccount = params.getvalAsStr("payAccount");
		String payBank = params.getvalAsStr("payBank");
		String borAccount = params.getvalAsStr("borAccount");
		String borBank = params.getvalAsStr("borBank");
		Integer mgrtype = params.getvalAsInt("mgrtype");
		Integer isadvance = params.getvalAsInt("isadvance");
		Integer rateType = params.getvalAsInt("rateType");
		String payType = params.getvalAsStr("payType");
		Long contractId = params.getvalAsLng("contractId");
		if(StringHandler.isValidObj(contractId)){
			sqlSb.append(" and A.id = '"+contractId+"' ");
		}
		if(StringHandler.isValidStr(code)){
			sqlSb.append(" and A.code like '"+code+"%' ");
		}
		if(StringHandler.isValidStr(accName)){
			sqlSb.append(" and A.accName like '"+accName+"%' ");
		}
		if(StringHandler.isValidStr(payAccount)){
			sqlSb.append(" and A.payAccount like '"+payAccount+"%' ");
		}
		if(StringHandler.isValidStr(payAccount)){
			sqlSb.append(" and A.payBank like '"+payBank+"%' ");
		}
		if(StringHandler.isValidStr(borAccount)){
			sqlSb.append(" and A.borAccount like '"+borAccount+"%' ");
		}
		if(StringHandler.isValidStr(borBank)){
			sqlSb.append(" and A.borBank like '"+borBank+"%' ");
		}
		if(StringHandler.isValidObj(mgrtype)){
			sqlSb.append(" and A.mgrtype = '"+mgrtype+"' ");
		}
		if(StringHandler.isValidObj(isadvance)){
			sqlSb.append(" and A.isadvance = '"+isadvance+"' ");
		}
		if(StringHandler.isValidObj(rateType)){
			sqlSb.append(" and A.rateType = '"+rateType+"' ");
		}
		if(StringHandler.isValidObj(payType)){
			sqlSb.append(" and A.payType like '"+payType+"%' ");
		}
		String cmms = "id,formId,code,custType,accName,doDate#yyyy-MM-dd,mgrtype,isadvance,rateType,payType,endDate#yyyy-MM-dd,payDay,payDate#yyyy-MM-dd,loanLimit,payAccount,payBank,borAccount,borBank,appAmount,frate,urate,arate,prate,mrate,rate,clause";
		Long totalCount = getTotalCountBySql(sqlSb.toString());	
		sqlSb.append(" order by A.id desc");
		DataTable dt = findBySqlPage(sqlSb.toString(),cmms, offset, pageSize,totalCount);
		return dt;
	}


	/* (non-Javadoc)
	 * @see com.cmw.dao.inter.finance.LoanContractDaoInter#getContractResultList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public DataTable getContractResultList(SHashMap<String, Object> params,int start, int limit) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuffer sqlSb = new StringBuffer();
		
			sqlSb.append("select  A.id as contractId,D.custName,D.custType,D.code as custCode,A.borBank,A.borAccount,B.referrals,C.empName as managerName, ")
			.append("B.code applyCode,A.code as contractCode,A.payDate, ")
			.append("(case when A.yearLoan>0 then cast(A.yearLoan as varchar(5))+'年' else '' end)+  ")
			.append("(case when A.monthLoan>0 then cast(A.monthLoan as varchar(3))+'个月' else '' end)+ ")
			.append("(case when A.dayLoan>0 then cast(A.dayLoan as varchar(3))+'天' else '' end) as loanLimit, ")
			.append("A.endDate,A.rateType,A.rate,A.payDay,A.clause,A.appAmount ")
			.append("from  ")
			.append("fc_LoanContract A Left join fc_Apply  B on A.formId  = B.id ")
			.append("inner join ts_user C on B.manager = C.userId  ")
			.append("inner join  ( ")
			.append("(select XX.custType,XX.name as custName,YY.id as customerId,XX.code   ")
			.append("from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId  where XX.custType = 0  ") 	
			.append(") ")
			.append("union ")  
			.append("(select XX.custType,XX.name as custName ,YY.id as customerId ,XX.code ")
			.append("from   crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType = 1  ")  
			.append(") ")
			.append(")D on B.custType = D.custType and B.customerId = D.customerId  where A.isenabled = 1 ");
			try {
				Integer custType = params.getvalAsInt("custType");
				if(StringHandler.isValidObj(custType)){
					sqlSb.append(" and D.custType = '"+custType+"'");
				}
				
				String custName = params.getvalAsStr("custName");
				if(StringHandler.isValidStr(custName)){
					sqlSb.append(" and D.name like '%"+custName+"%'");
				}
				Long contractId = params.getvalAsLng("contractId");
				if(StringHandler.isValidObj(contractId)){
					sqlSb.append(" and A.id in ("+contractId+")");
				}
				String cmms = "contractId,custName,custType,custCode,borBank,borAccount,referrals,managerName,applyCode,contractCode," +
						"payDate#yyyy-MM-dd,loanLimit,endDate#yyyy-MM-dd,rateType,rate,payDay,clause,appAmount";
				Long totalCount = getTotalCountBySql(sqlSb.toString());	
				DataTable dt = findBySqlPage(sqlSb.toString(),cmms, start, limit,totalCount);
				return dt;
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}catch (Exception e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
	}
}
