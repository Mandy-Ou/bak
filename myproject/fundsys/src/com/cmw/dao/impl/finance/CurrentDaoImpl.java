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
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.CurrentDaoInter;

/**
 * 随借随还管理
 *Title: currentDaoImpl.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-25下午1:56:18
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="随借随还管理DAO实现类",createDate="2013-11-22T00:00:00",author="彭登浩")
@Repository("currentDao")
public class CurrentDaoImpl  extends GenericDaoAbs<Object, Long> implements CurrentDaoInter {
	
	@Override
	public DataTable getCurrentList(SHashMap<String, Object> params, int start,int limit) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuffer sqlSb = new StringBuffer();
		String colNames = null;
		try{
			colNames = "contractId,code,appAmount,breedName,payBank," +
					"payAccount,accName,totalPhases,paydPhases,totalOverPharses," +
					"state,custType,name,loanLimit,payType," +
					"rateType,rate,isadvance,mgrtype,mrate," +
					"prate,arate,urate,frate,"+//principal," +
					"interest,mgrAmount,penAmount,delAmount,totalAmount," +
					"yprincipal,yinterest,ymgrAmount,ypenAmount,ydelAmount," +
					"ytotalAmount,zprincipal,zinterest,zmgrAmount,zpenAmount," +
					"zdelAmount,ztotalAmount,realDate#yyyy-MM-dd";
			
			sqlSb.append("select A.id as contractId,A.code,A.appAmount,E.name as breedName,A.payBank,")
				.append("A.payAccount,A.accName,B.totalPhases,isnull(B.paydPhases,0) as paydPhases,G.totalOverPharses,")
				.append("B.state,A.custType,F.name,((case when A.yearLoan>0 then cast(A.yearLoan as varchar(5))+'年' else '' end) + ")
				.append("(case when A.monthLoan>0 then cast(A.monthLoan as varchar(3))+'个月' else '' end) + ")
				.append("(case when A.dayLoan>0 then cast(A.dayLoan as varchar(3))+'天' else '' end)) as loanLimit,A.payType,")
				.append("A.rateType,A.rate,A.isadvance,A.mgrtype,A.mrate,")
				.append("A.prate,A.arate,A.urate,A.frate,")
				.append("H.interest,H.mgrAmount,H.penAmount,H.delAmount,H.totalAmount,")
				.append("H.yprincipal,H.yinterest,H.ymgrAmount,H.ypenAmount,H.ydelAmount,H.ytotalAmount,")
				.append("round(H.principal-H.yprincipal,2) as zprincipal,")
				.append("round(H.interest-H.yinterest,2) as zinterest,round(H.mgrAmount-H.ymgrAmount,2) as zmgrAmount,")
				.append("round(H.penAmount-H.ypenAmount,2) as zpenAmount,round(H.delAmount-H.ydelAmount,2) as zdelAmount,")
				.append("round(H.totalAmount-H.ytotalAmount-H.trinterAmount-H.trmgrAmount-H.trpenAmount-H.trdelAmount,2) as ztotalAmount, ")
				.append(" J.realDate ")
				.append(" from fc_LoanContract A left join fc_Apply B ON A.formId=B.id ")
				.append(" left join ts_Variety E ON  E.id = B.breed ")
				.append(" left join (select id,name,0 as custType from crm_CustomerInfo ")
				.append("	UNION ")
				.append("	 select id,name,1 as custType from crm_Ecustomer ) AS F ")
				.append("	 ON F.id = A.customerId and F.custType = A.custType  ")
				.append(" left join (select contractId,count(1) as totalOverPharses from fc_Plan ")
				.append("	where isenabled=1 and status in ("+BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5+") group by contractId) as G on G.contractId=A.id ")
				.append(" left join (select contractId,")
				.append(" round(sum(principal),2) as principal,")
				.append(" round(sum(interest),2) as interest,")
				.append("round(sum(mgrAmount),2) as mgrAmount,round(sum(penAmount),2) as penAmount,")
				.append("round(sum(delAmount),2) as delAmount,round(sum(totalAmount+penAmount+delAmount),2) as totalAmount,")
				.append("round(sum(yprincipal),2) as yprincipal,round(sum(yinterest),2) as yinterest,")
				.append("round(sum(ymgrAmount),2) as ymgrAmount,round(sum(ypenAmount),2) as ypenAmount,")
				.append("round(sum(ydelAmount),2) as ydelAmount,round(sum(ytotalAmount),2) as ytotalAmount,")
				.append("round(sum(trinterAmount),2) as trinterAmount,")
				.append("round(sum(trmgrAmount),2) as trmgrAmount,")
				.append("round(sum(trpenAmount),2) as trpenAmount,")
				.append("round(sum(trdelAmount),2) as trdelAmount ")
				.append(" from fc_Plan where isenabled='"+SysConstant.OPTION_ENABLED+"' " +
						" and status in ("+BussStateConstant.PLAN_STATUS_0+","+BussStateConstant.PLAN_STATUS_1+","+BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5+")" +
						" group by contractId) as H on H.contractId=A.id ")
				.append(" left join (" )
				.append("select min(realDate) as realDate,contractID from fc_LoanInvoce where isenabled='"+SysConstant.OPTION_ENABLED+"' group by contractId) as J on J.contractId=A.id ")
				.append(" where A.isenabled=1 and B.isenabled=1 and B.state in ("+BussStateConstant.FIN_APPLY_STATE_3+","+BussStateConstant.FIN_APPLY_STATE_4+","+BussStateConstant.FIN_APPLY_STATE_5+") ") /*找状态为已结清以上的*/
				.append(" and A.id in (select contractId from fc_Plan where isenabled='"+SysConstant.OPTION_ENABLED+"' and status in ("+BussStateConstant.PLAN_STATUS_0+
						","+BussStateConstant.PLAN_STATUS_1+","+BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5+") group by contractId )");
				//.append(" and exists (select 1 from fc_LoanInvoce where contractId = A.id and state='"+BussStateConstant.LOANINVOCE_STATE_1+"')");
			
			Long contractId = params.getvalAsLng("id");
			if(StringHandler.isValidObj(contractId)){
				sqlSb.append(" and  A.id = '"+contractId+"' ");
			}
			Integer custType = params.getvalAsInt("custType");
			if(StringHandler.isValidObj(custType)){
				sqlSb.append(" and  A.custType = '"+custType+"' ");
			}
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){
				sqlSb.append(" and F.name like '"+name+"%' ");
			}
			String code = params.getvalAsStr("code");
			if(StringHandler.isValidStr(code)){
				sqlSb.append(" and A.code like '"+code+"%' ");
			}
			String accName = params.getvalAsStr("accName");
			if(StringHandler.isValidStr(accName)){
				sqlSb.append(" and A.accName like '"+accName+"%' ");
			}
			String payBank = params.getvalAsStr("payBank");
			if(StringHandler.isValidStr(payBank)){
				sqlSb.append(" and A.payBank like '"+payBank+"%' ");
			}
			String payAccount = params.getvalAsStr("payAccount");
			if(StringHandler.isValidStr(payAccount)){
				sqlSb.append(" and A.payAccount like '"+payAccount+"%' ");
			}
			Long totalCount = getTotalCountBySql(sqlSb.toString());	
			
			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, start, limit,totalCount);
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
}
