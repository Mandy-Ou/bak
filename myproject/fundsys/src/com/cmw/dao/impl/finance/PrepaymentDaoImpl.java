package com.cmw.dao.impl.finance;


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
import com.cmw.dao.inter.finance.PrepaymentDaoInter;
import com.cmw.entity.finance.PrepaymentEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 提前还款申请  DAO实现类
 * @author 程明卫
 * @date 2013-09-11T00:00:00
 */
@Description(remark="提前还款申请DAO实现类",createDate="2013-09-11T00:00:00",author="程明卫")
@Repository("prepaymentDao")
public class PrepaymentDaoImpl extends GenericDaoAbs<PrepaymentEntity, Long> implements PrepaymentDaoInter {
	
	private static final String SQL_CMNS = "A.id,A.code,A.breed,A.procId,A.contractId," +
			"A.ptype,A.adDate,A.isretreat,A.imamount,A.adamount," +
			"A.treatment,A.appMan,A.appDate,A.tel,B.empName as managerName," +
			"A.mgrDate,A.predDate,A.frate,A.freeamount,A.totalAmount," +
			"C.code as loanCode,D.custType,D.custName,D.cardType,D.cardNum,C.formId as applyId," +
			"C.appAmount,C.rateType,C.rate,C.isadvance,C.mgrtype," +
			"C.mrate,C.prate,C.arate,C.urate,C.payType," +
			"D.baseId,D.customerId,A.managerId ";
		
		private static final String DT_CMNS = "id,code,breed,procId,contractId," +
				"ptype,adDate#yyyy-MM-dd,isretreat,imamount,adamount," +
				"treatment,appMan,appDate#yyyy-MM-dd,tel,managerName," +
				"mgrDate#yyyy-MM-dd,predDate#yyyy-MM-dd,frate,freeamount,totalAmount," +
				"loanCode,custType,custName,cardType,cardNum,applyId," +
				"appAmount,rateType,rate,isadvance,mgrtype," +
				"mrate,prate,arate,urate,payType," +
				"baseId,customerId,manager ";
		
		@Override
		public <K,V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
			params = SqlUtil.getSafeWhereMap(params);
			StringBuilder sb = new StringBuilder();
			sb.append("select ").append(SQL_CMNS)
			.append(" from fc_Prepayment A ")
			.append(" left join ts_user B on A.managerId=B.userId ")
			.append(" left join fc_LoanContract C on A.contractId=C.id ")
			.append(" left join (")
			.append(" (select XX.custType,XX.name as custName,ZZ.name as cardType,XX.cardNum,YY.id as customerId,YY.baseId as baseId ")
			.append("	from crm_CustBase XX inner join crm_CustomerInfo YY ON XX.id=YY.baseId")
			.append("   left join ts_Gvlist ZZ on XX.cardType=ZZ.id  where XX.custType='"+SysConstant.CUSTTYPE_0+"' )")
			.append("    union ")
			.append(" (select  XX.custType,XX.name as custName,'工商登记号' as cardType,XX.cardNum,YY.id as customerId ,YY.baseId as baseId ")
			.append("    from crm_CustBase XX inner join crm_ECustomer YY ON XX.id=YY.baseId where XX.custType='"+SysConstant.CUSTTYPE_1+"' )")
			.append(") D on C.custType=D.custType and C.customerId=D.customerId ")
			.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
			
			try {
				UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
				addWhereByActionType(params, sb, user);
				
				Long id = params.getvalAsLng("id");
				if(StringHandler.isValidObj(id)){
					sb.append(" and A.id  = '"+id+"' ");
				}
				
					Integer custType = params.getvalAsInt("custType");
					if(StringHandler.isValidObj(custType)){
						sb.append(" and D.custType = '"+custType+"' ");
					}
					
					String custName = params.getvalAsStr("custName");
					if(StringHandler.isValidStr(custName)){
						sb.append(" and D.custName like '%"+custName+"%' ");
					}
					
					String ptype = params.getvalAsStr("ptype");
					if(StringHandler.isValidObj(ptype)){
						sb.append(" and A.ptype = '"+ptype+"' ");
					}
					
					String startDate1 = params.getvalAsStr("startDate1");
					if(StringHandler.isValidStr(startDate1)){
						sb.append(" and A.adDate >= '"+startDate1+"' ");
					}
					
					String endDate1 = params.getvalAsStr("endDate1");
					if(StringHandler.isValidStr(endDate1)){
						endDate1 = DateUtil.addDays(endDate1, 1);
						sb.append(" and A.adDate <= '"+endDate1+"' ");
					}
					
					String startDate2 = params.getvalAsStr("startDate2");
					if(StringHandler.isValidStr(startDate2)){
						sb.append(" and A.eendDate >= '"+startDate2+"' ");
					}
					
					String endDate2 = params.getvalAsStr("endDate2");
					if(StringHandler.isValidStr(endDate2)){
						endDate2 = DateUtil.addDays(endDate2, 1);
						sb.append(" and A.eendDate <= '"+endDate2+"' ");
					}
					
					String appMan = params.getvalAsStr("appMan");
					if(StringHandler.isValidObj(appMan)){
						sb.append(" and A.appMan like '%"+appMan+"%' ");
					}
					
					String status = params.getvalAsStr("status");
					if(StringHandler.isValidStr(status)){
						sb.append(" and  A.status in ("+status+") ");
					}
					
				long totalCount = -1;
				if(offset >= 0) totalCount = getTotalCountBySql(sb.toString());
				sb.append(" order by A.id desc ");
				DataTable dt = findBySqlPage(sb.toString(), DT_CMNS, offset, pageSize);
				if(totalCount>-1) dt.setSize(totalCount);
				return dt;
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}catch (Exception e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
		}
		
	/**
	 * 根据动作类型，添加过滤条件
	 * @param params
	 * @param sb
	 * @param user
	 */
	private <K, V> void addWhereByActionType(SHashMap<K, V> params,
			StringBuilder sb, UserEntity user) {
		Integer actionType = params.getvalAsInt("actionType");
		if(null != actionType){
			switch (actionType) {
			case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0:{/*暂存*/
				sb.append(" and A.creator = '"+user.getUserId()+"' ");
				break;
			}case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1:{/*待办*/
				String procIds = params.getvalAsStr("procIds");
				if(!StringHandler.isValidStr(procIds)) procIds = "-1";
				sb.append(" and A.procId in ("+procIds+") ");
				break;
			}case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2 :{
				appendAuditHistoryWhereStr(sb,user);
				break;
			}case SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3 :{
				sb.append(" and A.status <> '"+BussStateConstant.FIN_APPLY_STATE_0+"' ");
				break;
			}default:
				break;
			}
		}
	}
	/**
	 * 审批历史需加的过滤条件
	 * @param sqlSb 条件SQL 
	 * @param user	当前用户
	 */
	private void appendAuditHistoryWhereStr(StringBuilder sqlSb,UserEntity user){
		Long userId = user.getUserId();
		sqlSb.append(" and A.status <> '"+BussStateConstant.FIN_APPLY_STATE_0+"' ");
		sqlSb.append(" and A.procId in (SELECT procId FROM ts_auditrecords B where creator='"+userId+"')");
	}
	
	private static final String DT_CALCMNS = "planId,contractId,phases,xpayDate#yyyy-MM-dd,principal," +
			"interest,mgrAmount,reprincipal,xphases,yphases," +
			"yprincipals,overCount,zinterests,zmgrAmounts,zpenAmounts,zdelAmounts";
	@Override
	public DataTable getCalculateDt(SHashMap<String, Object> params) throws DaoException{
		StringBuilder sbSql = new StringBuilder();
		try{
			String contractId = params.getvalAsStr("contractId");
			String xpayDate = params.getvalAsStr("xpayDate");
			//结清状态
			String jq_status = BussStateConstant.PLAN_STATUS_2+","+BussStateConstant.PLAN_STATUS_3
					+","+BussStateConstant.PLAN_STATUS_9+","+BussStateConstant.PLAN_STATUS_10;
			//逾期状态
			String yq_status = BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5;
			sbSql.append("select top 1 A.id as planId,A.contractId,A.phases,A.xpayDate,(A.principal-A.yprincipal) as principal,")
				.append(" (A.interest-A.yinterest-A.trinterAmount) as interest,(A.mgrAmount-A.ymgrAmount-A.trmgrAmount) as mgrAmount,A.reprincipal,")
				.append(" B.xphases,C.yphases,C.yprincipals,D.overCount,D.zinterests,D.zmgrAmounts,D.zpenAmounts,D.zdelAmounts ")
				.append(" from fc_Plan A ")
				.append(" left join ")
				.append(" (select contractId,count(1) xphases ")
				.append(" from fc_Plan X  group by contractId) B ON A.contractId = B.contractId ")
				.append(" left join ")
				.append(" (select contractId,count(1) yphases,sum(X.yprincipal) as yprincipals from fc_Plan X ")
				.append(" where  status in ("+jq_status+") group by contractId) C ON A.contractId = C.contractId ")
				.append(" left join ( ")
				.append(" select B.contractId,count(1) as overCount,sum((B.interest-B.yinterest-B.trinterAmount)) as zinterests,")
				.append(" sum((B.mgrAmount-B.ymgrAmount-B.trmgrAmount)) as zmgrAmounts, sum((B.penAmount-B.ypenAmount-B.trpenAmount)) as zpenAmounts,")
				.append(" sum((B.delAmount-B.ydelAmount-B.trdelAmount)) as zdelAmounts ")
				.append(" from fc_Plan B where status in ("+yq_status+")  group by contractId ")
				.append(" ) D  ON A.contractId = D.contractId ")
				.append(" where A.contractId='"+contractId+"' and A.xpayDate>='"+xpayDate+"' ");

			return findBySql(sbSql.toString(), DT_CALCMNS);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	private static final String DT_ZAMOUNTCMNS = "reprincipal,zinterest,zmgrAmount,zpenAmounts,zdelAmounts,id,contractId,phases,xpayDate,xphases,yphases";
	public DataTable getZamountDt(SHashMap<String, Object> params) throws DaoException{
		try{
			String payplanId = params.getvalAsStr("payplanId");
			String whereStr = "";
			String subWhereStr = "";
			if(StringHandler.isValidStr(payplanId)){
				whereStr = " and A.id='"+payplanId+"' ";
				subWhereStr = " and id<'"+payplanId+"' ";
			}
			String yjq_status = BussStateConstant.PLAN_STATUS_0+","+ BussStateConstant.PLAN_STATUS_1
					+","+BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5;
			String jq_status = BussStateConstant.PLAN_STATUS_2+","+ BussStateConstant.PLAN_STATUS_3+","+ BussStateConstant.PLAN_STATUS_9;
			StringBuilder sbSql = new StringBuilder();
			sbSql.append("select (A.reprincipal+A.principal-A.yprincipal+ISNULL(B.lzprincipal,0)) as reprincipal,")
			.append("(A.interest-A.yinterest-A.trinterAmount+ISNULL(B.lzinterest,0)) as zinterest,")
			.append("(A.mgrAmount-A.ymgrAmount-A.trmgrAmount+ISNULL(B.lzmgrAmount,0)) as zmgrAmount,")
			.append("(A.penAmount-A.ypenAmount-A.trpenAmount+ISNULL(B.lzpenAmounts,0)) as zpenAmounts,")
			.append("(A.delAmount-A.ydelAmount-A.trdelAmount+ISNULL(B.lzdelAmounts,0)) as zdelAmounts,")
			.append("A.id,A.contractId,A.phases,A.xpayDate,C.xphases,ISNULL(D.yphases,0) AS yphases  from fc_Plan A ")
			.append(" left join (select  contractId,sum(principal-yprincipal) as lzprincipal,")
			.append(" sum(interest-yinterest-trinterAmount) as lzinterest,")
			.append("sum(mgrAmount-ymgrAmount-trmgrAmount) as lzmgrAmount,")
			.append("sum(penAmount-ypenAmount-trpenAmount) as lzpenAmounts,")
			.append("sum(delAmount-ydelAmount-trdelAmount) as lzdelAmounts")
			.append(" from fc_Plan where status in ("+yjq_status+") "+subWhereStr+" group by contractId ")
			.append(") as B on A.contractId=B.contractId ")
			.append(" left join (")
			.append(" select contractId,count(id) as xphases from fc_Plan group by contractId ")
			.append(") as C on C.contractId=A.contractId ")
			.append(" left join (")
			.append(" select contractId,count(id) as yphases from fc_Plan ")
			.append("  where status in ("+jq_status+") group by contractId ")
			.append(") as D on A.contractId=D.contractId ")
			.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
			sbSql.append(whereStr);
			return findBySql(sbSql.toString(), DT_ZAMOUNTCMNS);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cmw.dao.inter.finance.PrepaymentDaoInter#detail(java.lang.Long)
	 */
	@Override
	public DataTable detail(Long id) throws DaoException {
		try{
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("id", id);
			DataTable dtResult = getResultList(params, -1, -1);
			return dtResult;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
