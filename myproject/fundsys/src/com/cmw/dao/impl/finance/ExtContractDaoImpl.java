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
import com.cmw.dao.inter.finance.ExtContractDaoInter;
import com.cmw.entity.finance.ExtContractEntity;


/**
 * 展期协议书  DAO实现类
 * @author pdh
 * @date 2013-09-23T00:00:00
 */
@Description(remark="展期协议书DAO实现类",createDate="2013-09-23T00:00:00",author="pdh")
@Repository("extContractDao")
public class ExtContractDaoImpl extends GenericDaoAbs<ExtContractEntity, Long> implements ExtContractDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(" select A.id,A.code,A.extensionId,A.contractId,A.loanCode,A.guaCode,A.ostartDate,A.oendDate,")
			.append(" A.endAmount,A.extAmount,A.estartDate,A.eendDate,B.name as payType,A.phAmount,")
			 .append(" (case when A.yearLoan>0 then cast(A.yearLoan as varchar(5))+'年' else '' end)+")
			 .append(" (case when A.monthLoan>0 then cast(A.monthLoan as varchar(3))+'个月' else '' end)+")
			 .append(" (case when A.dayLoan>0 then cast(A.dayLoan as varchar(3))+'天' else '' end) as loanLimit,")
			.append(" A.rateType,A.rate,A.isadvance,A.mgrtype,A.mrate,A.applyMan,")
			.append(" A.asignDate,A.guarantor, A.gsignDate,A.signDate,A.otherRemark")
			
			.append(" from fc_ExtContract A inner join fc_PayType B on A.payType = B.code ")
			.append(" where A.isenabled= '"+SysConstant.OPTION_ENABLED+"' ");
			query(params, sqlSb);
			String cmns =null;
				cmns = "id,code,extensionId,contractId,loanCode,guaCode,ostartDate#yyyy-MM-dd,oendDate#yyyy-MM-dd," +
					"endAmount,extAmount,estartDate#yyyy-MM-dd,eendDate#yyyy-MM-dd,payType,phAmount," +
					"loanLimit,rateType,rate,isadvance,mgrtype,mrate,applyMan," +
					"asignDate#yyyy-MM-dd,guarantor,gsignDate#yyyy-MM-dd,signDate#yyyy-MM-dd,otherRemark";
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append("  order by  A.id DESC ");
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
		
		/**
		 * @param params
		 * @param sqlSb
		 */
		private <K, V> void query(SHashMap<K, V> params, StringBuffer sqlSb) {
			String code = params.getvalAsStr("code");
			if(StringHandler.isValidStr(code)){
				sqlSb.append(" and A.code  like '"+code+"%' ");
			}
			Long contractId = params.getvalAsLng("contractId");
			if(StringHandler.isValidObj(contractId)){
				sqlSb.append(" and A.contractId  = "+contractId+" ");
			}
			String loanCode =  params.getvalAsStr("loanCode");
			if(StringHandler.isValidStr(loanCode)){//借款合同编号
				sqlSb.append(" and A.loanCode like '"+loanCode+"%' ");
			}
			
			String eqopendAmount = params.getvalAsStr("eqopendAmount");
			if(StringHandler.isValidStr(eqopendAmount)){
				Double endAmount = params.getvalAsDob("endAmount");
				if(null != endAmount && endAmount.doubleValue() > 0){
					sqlSb.append(" and A.endAmount "+eqopendAmount+" '"+endAmount+"' ");
				}
			}
			
			String eqopextAmountt = params.getvalAsStr("eqopextAmountt");
			if(StringHandler.isValidStr(eqopextAmountt)){
				Double extAmount = params.getvalAsDob("extAmount");
				if(null != extAmount && extAmount.doubleValue() > 0){
					sqlSb.append(" and A.extAmount "+eqopextAmountt+" '"+extAmount+"' ");
				}
			}
			
			String payType = params.getvalAsStr("payType");
			if(StringHandler.isValidStr(payType)){
				sqlSb.append(" and A.payType = '"+payType+"' ");
			}
			
			Integer isadvance = params.getvalAsInt("isadvance");
			if(StringHandler.isValidObj(isadvance)){
				sqlSb.append(" and A.isadvance  = '"+isadvance+"' ");
			}
			
			Integer rateType = params.getvalAsInt("rateType");
			if(StringHandler.isValidObj(rateType)){
				sqlSb.append(" and A.rateType = '"+rateType+"' ");
			}
			
			String eqopLoanLimit = params.getvalAsStr("eqopLoanLimit");
			if(StringHandler.isValidStr(eqopLoanLimit)){
				Integer yearLoan = params.getvalAsInt("yearLoan");
				if(null != yearLoan && yearLoan.intValue() > 0){
					sqlSb.append(" and A.yearLoan "+eqopLoanLimit+" '"+yearLoan+"' ");
				}
				Integer monthLoan = params.getvalAsInt("monthLoan");
				if(null != monthLoan && monthLoan.intValue() > 0){
					sqlSb.append(" and A.monthLoan "+eqopLoanLimit+" '"+monthLoan+"' ");
				}
				Integer dayLoan = params.getvalAsInt("dayLoan");
				if(null != dayLoan && dayLoan.intValue() > 0){
					sqlSb.append(" and A.dayLoan "+eqopLoanLimit+" '"+dayLoan+"' ");
				}
				
			}
		}
}
