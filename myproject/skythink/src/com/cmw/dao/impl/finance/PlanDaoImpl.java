package com.cmw.dao.impl.finance;


import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.PlanDaoInter;
import com.cmw.entity.finance.PlanEntity;


/**
 * 还款计划  DAO实现类
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="还款计划DAO实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Repository("planDao")
public class PlanDaoImpl extends GenericDaoAbs<PlanEntity, Long> implements PlanDaoInter {
	
	
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		StringBuffer sb = new StringBuffer();
		//zprincipal,zinterest,zpenAmount,zdelAmount,ztotalAmount,lightweights,mgrdays,exempt
		sb.append("SELECT A.id,A.contractId,phases,xpayDate,ROUND(principal-yprincipal,2) as zprincipal,")
		.append("ROUND(interest-yinterest,2) as zinterest,ROUND(mgrAmount-ymgrAmount,2) as zmgrAmount,")
		.append("penAmount,delAmount,ROUND(penAmount-ypenAmount,2) as zpenAmount,ROUND(delAmount-ydelAmount,2) as zdelAmount,")
		.append("ROUND(totalAmount+penAmount+delAmount-ytotalAmount,2) as ztotalAmount,A.pdays,A.ratedays,A.mgrdays,A.lastDate,A.exempt")
		.append(" FROM PlanEntity A where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
		String whereStr = SqlUtil.buildWhereStr("A", map,false);
		sb.append(whereStr);
		
		String cmns = "id,contractId,phases,xpayDate#yyyy-MM-dd,zprincipal," +
				"zinterest,zmgrAmount,penAmount,delAmount,zpenAmount,zdelAmount,ztotalAmount,pdays,ratedays,mgrdays,lastDate#yyyy-MM-dd,exempt";
		return find(sb.toString(), cmns);
	}

	
	/**
	 * 获取正常扣收数据流水
	 * @param params	查询参数
	 * @param offset	分页起始位
	 * @param pageSize  每页大小
	 * @return
	 * @throws DaoException
	 */
	public DataTable getNomalPlans(SHashMap<String, Object> params,int offset, int pageSize) throws DaoException{
		try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(" select ROUND(A.principal-A.yprincipal,2) as zprincipal, B.code,C.custType,C.name,B.payBank,B.payAccount,A.penAmount,A.delAmount,A.ypenAmount,A.ydelAmount,")
			.append(" B.accName,A.xpayDate,A.phases,C.paydPhases,C.totalPhases,")
			.append(" A.principal,A.yprincipal,A.interest,A.yinterest,A.mgrAmount,")
			.append(" A.ymgrAmount,A.totalAmount,A.ytotalAmount,")
			.append(" A.trinterAmount,A.trmgrAmount,(A.trinterAmount+A.trmgrAmount) as trtotalAmount,")
			.append("(A.totalAmount-A.ytotalAmount-A.trinterAmount-A.trmgrAmount) as ztotalAmount,")
			.append(" A.status,A.exempt,A.lastDate,B.appAmount,")
			.append(" (case when B.yearLoan>0 then cast(B.yearLoan as varchar(5))+'年' else '' end)+")
			.append(" (case when B.monthLoan>0 then cast(B.monthLoan as varchar(3))+'个月' else '' end)+")
			.append(" (case when B.dayLoan>0 then cast(B.dayLoan as varchar(3))+'天' else '' end) as loanLimit,")
			.append(" A.id,A.contractId ,C.inRate  ")
			.append(" from fc_plan A inner join fc_LoanContract B on A.contractId = B.id ")
			.append(" inner join (select X.id as applyId,X.custType,X.paydPhases,X.totalPhases,")
			.append(" (case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId)")
			.append(" when X.custType=1 then (select name from crm_Ecustomer where id = X.customerId) else '' end) as name,X.state,X.inRate from fc_Apply X) C ")
			.append(" on B.formId = C.applyId where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ")
			.append(" and A.status in ("+BussStateConstant.PLAN_STATUS_0+","+BussStateConstant.PLAN_STATUS_1+","+BussStateConstant.PLAN_STATUS_8+") ")
			.append(" and exists (select 1 from fc_loanInvoce where state=1 and contractId = B.id) ");
			params = SqlUtil.getSafeWhereMap(params);
			String ids = params.getvalAsStr("ids");
			if(StringHandler.isValidStr(ids)){//
				sqlSb.append(" and A.id in ("+ids+") ");
			}
			
			String custType = params.getvalAsStr("custType");
			if(StringHandler.isValidStr(custType)){//客户类型
				sqlSb.append(" and C.custType= '"+custType+"' ");
			}
			
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){//客户名称
				sqlSb.append(" and C.name like '"+name+"%' ");
			}
			
			String accName = params.getvalAsStr("accName");
			if(StringHandler.isValidStr(accName)){//还款人名称
				sqlSb.append(" and B.accName like '"+accName+"%' ");
			}
			
			String code = params.getvalAsStr("code");
			if(StringHandler.isValidStr(code)){//借款合同号
				sqlSb.append(" and B.code like '"+code+"%' ");
			}
			
			String payBank = params.getvalAsStr("payBank");
			if(StringHandler.isValidStr(payBank)){//还款银行
				sqlSb.append(" and B.payBank like '"+payBank+"%' ");
			}
			
			String payAccount = params.getvalAsStr("payAccount");
			if(StringHandler.isValidStr(payAccount)){//还款帐号
				sqlSb.append(" and B.payAccount like '"+payAccount+"%' ");
			}
			
			String startDate = params.getvalAsStr("startDate");
			if(StringHandler.isValidStr(startDate)){//应还款款日  起始日期
				sqlSb.append(" and A.xpayDate >= '"+startDate+"' ");
			}
			
			String endDate = params.getvalAsStr("endDate");
			if(StringHandler.isValidStr(endDate)){//应还款款日  结束日期
				endDate = DateUtil.addDays(endDate, 1);
				sqlSb.append(" and A.xpayDate < '"+endDate+"' ");
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());	//
			sqlSb.append(" ORDER BY A.xpayDate ");
			String sqlStr = sqlSb.toString();
			String colNames = "zprincipal,code,custType,name,payBank,payAccount,penAmount,delAmount,ypenAmount,ydelAmount,"+
							"accName,xpayDate#yyyy-MM-dd,phases,paydPhases,totalPhases,"+
							"principal,yprincipal,interest,yinterest,mgrAmount,"+
							"ymgrAmount,totalAmount,ytotalAmount,trinterAmount,trmgrAmount,trtotalAmount,ztotalAmount,"+
							"status,exempt,lastDate#yyyy-MM-dd,B.appAmount,loanLimit,id,contractId,inRate";
			DataTable dt = findBySqlPage(sqlStr, colNames, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public DataTable getIds(SHashMap<String, Object> params)throws DaoException {
		String codes = params.getvalAsStr("codes");
		String phases = params.getvalAsStr("phases");
//		if(!StringHandler.isValidStr(codes)) throw new DaoException("invoke getIds methos : codes is null ");
		if(StringHandler.isValidStr(codes)){
			String hql = "select A.id,A.phases,B.code,A.contractId," +
					"A.status,A.lastDate,(A.totalAmount-A.ytotalAmount-A.trinterAmount-A.trmgrAmount) as sysztotalAmount " +
					" from PlanEntity A,LoanContractEntity B " +
					" where A.contractId=B.id and B.code in ("+codes+") and A.phases in ("+phases+")";
			return find(hql, "id,phases,code,contractId,status,lastDate,sysztotalAmount");
		}else{
			String hql = "select A.id from PlanEntity A"+SqlUtil.buildWhereStr("A", params);
			return find(hql, "id");
		}
		
	}
	
	/**
	 * 正常收取费用流水
	 */

	
	
	/**
	 * 正常转逾期
	 * @param params 参数 值：lastDate,holidays
	 * @return
	 * @throws DaoException 
	 */
	public void updateSateToLate(SHashMap<String, Object> params) throws DaoException{
		try{
			String lastDate = params.getvalAsStr("lastDate");
			String holidays =  params.getvalAsStr("holidays");
			String ids = params.getvalAsStr("ids");
			StringBuffer hqlSb = new StringBuffer(); 
			hqlSb.append("update fc_plan set status="+BussStateConstant.PLAN_STATUS_4+"")
				  .append(" where status in ("+BussStateConstant.PLAN_STATUS_0+","+BussStateConstant.PLAN_STATUS_1+") " )
				  .append(" and xpaydate < '"+lastDate+"'" );
			if(StringHandler.isValidStr(holidays)){
				hqlSb.append(" and CONVERT(varchar(100), xpaydate, 23) not in ("+holidays+")");
			}
			if(StringHandler.isValidStr(ids)){
				hqlSb.append(" and id in ("+ids+")");
			}
			
			getSession().createSQLQuery(hqlSb.toString()).executeUpdate();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	
	/**
	 * 获取逾期还款计划条数
	 * @return
	 * @throws DaoException 
	 */
	public Long getLatePlansCount() throws DaoException{
		try{
			Long count = 0L;
			String hql = "select count(A.id) from PlanEntity A where A.isenabled=1" +
					" and A.status in ("+BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5+")";
			Object obj = getSession().createQuery(hql).uniqueResult();
			if(null != obj) count = (Long)obj;
			return count;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 获取逾期还款计划汇总数据
	 * @return 返回 DataTable 对象 "contractId,planId,monthPharses,totalPharses,amounts,iamounts,mamounts,pamounts,damounts"
	 * @throws DaoException 
	 */
	public DataTable getLateSumDatas(String contractIds) throws DaoException{
		try{
			StringBuffer sbHql = new StringBuffer();
			sbHql.append("select contractId,min(id) as planId,min(phases) as monthPharses,")
			.append("count(A.id) as totalPharses,sum(principal-yprincipal) as amounts,")
			.append("sum(interest-yinterest-trinterAmount) as iamounts,sum(mgrAmount-ymgrAmount-trmgrAmount) as mamounts,")
			.append("sum(penAmount-ypenAmount-trpenAmount) as pamounts,sum(delAmount-ydelAmount-trdelAmount) as damounts")
			.append(" from PlanEntity A where contractId in ("+contractIds+")")
			.append(" and  status in ("+BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5+") group by contractId");
			String cmns = "contractId,planId,monthPharses,totalPharses,amounts,iamounts,mamounts,pamounts,damounts";
			DataTable dt = find(sbHql.toString(),cmns);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	/**
	 * 分批获取还款计划逾期数据
	 * @param params	过滤参数
	 * @return
	 * @throws DaoException 
	 */
	@SuppressWarnings("unchecked")
	public List<PlanEntity> getEntityListByBatch(SHashMap<String, Object> params) throws DaoException {
		try{
			String whereString = "";
			Object contractIds = params.get("contractIds");
			if (StringHandler.isValidObj(contractIds)) {
				whereString += " and X.contractId in (" + contractIds + ") ";
			}
			
			Object ids = params.get("ids");
			if (StringHandler.isValidObj(ids)) {
				whereString += " and X.id in (" + ids + ") ";
			}
			
			Object status = params.get("status");
			if(!StringHandler.isValidObj(status)){
				status = BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5;
			}
			String hql = "select * from (select *,  ROW_NUMBER() OVER(order by id desc) as page_row_num " +
					" from fc_plan X where  status in ("+status+") "+whereString+" ) as A where 1=1 ";
			
			Integer start = (Integer)params.get("start");
			Integer limit = (Integer)params.get("limit");
			if(null != start){
				if(null == limit) limit = 0;
				hql += " and page_row_num between "+(start+1)+" and "+(start+limit)+" ";
			}
			
			hql += " order by A.contractId,A.phases ";
			SQLQuery query = getSession().createSQLQuery(hql);
			List<PlanEntity> list = query.addEntity(PlanEntity.class ).list();
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
/**
 * 正常还款的流水
 */
	@Override
	public DataTable RepDetail(SHashMap<String, Object> params, int offset,
			int pageSize) throws DaoException {
		try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("select A.code,D.custType,D.name,A.payBank,A.payAccount,")
			.append("A.accName,D.paydPhases,D.totalPhases,C.principal,")
			.append("C.yprincipal,C.interest,C.yinterest,C.mgrAmount,")
			.append("C.ymgrAmount,C.totalAmount,C.ytotalAmount,ROUND((C.totalAmount - C.ytotalAmount),2) as ztotalAmount ,D.state as state,")
			.append("C.lastDate,A.appAmount,")
			.append("(case when A.yearLoan>0 then cast(A.yearLoan as varchar(5))+'年' else '' end)+")
			.append("(case when A.monthLoan>0 then cast(A.monthLoan as varchar(3))+'个月' else '' end)+")
			.append("(case when A.dayLoan>0 then cast(A.dayLoan as varchar(3))+'天' else '' end) as loanLimit,")
			.append("A.id as contractId,D.inRate ")
			.append(" From fc_LoanContract A inner join fc_LoanInvoce B on B.contractId = A.id ")
			.append("inner join ( ")
			.append("SELECT contractId,max(lastDate) as lastDate,SUM(principal) as principal,SUM(interest) as interest, ")
			.append("SUM(mgrAmount) as mgrAmount,SUM(penAmount) as penAmount,SUM(delAmount) as delAmount, ")
			.append("SUM(totalAmount) as totalAmount,SUM(ytotalAmount) as ytotalAmount, ")
			.append("SUM(yprincipal) as yprincipal, SUM(yinterest) as yinterest, ")
			.append("SUM(ymgrAmount) as ymgrAmount,SUM(ypenAmount) as ypenAmount,SUM(ydelAmount) as ydelAmount ")
			.append("FROM fc_plan where status in(1,2)  GROUP BY contractId  ")
			.append(") as  C on C.contractId = A.id ")
			.append("inner join ( ")
			.append("select X.id as applyId,X.custType, X.paydPhases,X.totalPhases,X.inRate ,")
			.append("(case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId) ") 
			.append("when X.custType=1 then(select name from crm_Ecustomer where id = X.customerId)else ''end)as name,X.state ") 
		    .append("from  fc_Apply X) D on A.formId = D.applyId where B.Isenabled = 1 and B.auditState = 2 and B.state = 1  ");
			params = SqlUtil.getSafeWhereMap(params);
			String id = params.getvalAsStr("id");
			if(StringHandler.isValidStr(id)){//
				sqlSb.append(" and A.id in ("+id+") ");
			}
			String custType = params.getvalAsStr("custType");
			if(StringHandler.isValidStr(custType)){//客户类型
				sqlSb.append(" and D.custType= '"+custType+"' ");
			}
			
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){//客户名称
				sqlSb.append(" and D.name like '"+name+"%' ");
			}
			
			String code = params.getvalAsStr("code");
			if(StringHandler.isValidStr(code)){//借款合同号
				sqlSb.append(" and A.code like '"+code+"%' ");
			}

			String accName = params.getvalAsStr("accName");
			if(StringHandler.isValidStr(accName)){//还款人名称
				sqlSb.append(" and A.accName like '"+accName+"%' ");
			}
			
			String payBank = params.getvalAsStr("payBank");
			if(StringHandler.isValidStr(payBank)){//还款银行
				sqlSb.append(" and A.payBank like '"+payBank+"%' ");
			}
			
			String payAccount = params.getvalAsStr("payAccount");
			if(StringHandler.isValidStr(payAccount)){//还款帐号
				sqlSb.append(" and A.payAccount like '"+payAccount+"%' ");
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());	//
			sqlSb.append(" ORDER BY C.lastDate ");
			String sqlStr = sqlSb.toString();
			String colNames = "code,custType,name,payBank,payAccount,"+
							"accName,paydPhases,totalPhases,"+
							"principal,yprincipal,interest,yinterest,mgrAmount,"+
							"ymgrAmount,totalAmount,ytotalAmount,ztotalAmount,"+
							"state,lastDate#yyyy-MM-dd,appAmount,loanLimit,contractId,inRate";
			DataTable dt = findBySqlPage(sqlStr, colNames, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	
}
