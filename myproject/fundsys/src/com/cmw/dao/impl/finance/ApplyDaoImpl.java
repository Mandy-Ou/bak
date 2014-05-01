package com.cmw.dao.impl.finance;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.ApplyDaoInter;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 贷款申请单  DAO实现类
 * @author 程明卫
 * @date 2012-12-15T00:00:00
 */
@Description(remark="贷款申请单DAO实现类",createDate="2012-12-15T00:00:00",author="程明卫")
@Repository("applyDao")
public class ApplyDaoImpl extends GenericDaoAbs<ApplyEntity, Long> implements ApplyDaoInter {
	
	 /* (non-Javadoc)
	 * @see com.cmw.dao.inter.finance.ApplyDaoInter#getApplyDt(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getApplyDt(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		DataTable dt = null;
		try {
			 dt =  super.getResultList(params, offset, pageSize);
		} catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
		return dt;
	}


	/**
	  * 获取已经开始并且未完结的流程实例ID列表
	  * @return
	  * @throws DaoException
	  */
	@SuppressWarnings("unchecked")
	 public List<String> getProcessInstanceIds() throws DaoException{
		 String hql = "select A.procId from ApplyEntity A where A.isenabled="+SysConstant.OPTION_ENABLED+" " +
		 		" and A.state not in ("+BussStateConstant.FIN_APPLY_STATE_0+","+
						BussStateConstant.FIN_APPLY_STATE_14+","+
						BussStateConstant.FIN_APPLY_STATE_15+")";
		try{
			List<String> list = getSession().createQuery(hql).list();
			 return list;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	 }
	 
	 
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuffer sqlSb = new StringBuffer();
		String colNames = null;
		Integer custType = params.getvalAsInt("custType");
		UserEntity user = (UserEntity) params.getvalAsObj(SysConstant.USER_KEY);
		
		long totalCount ;
		if(StringHandler.isValidObj(custType) && custType.intValue() == SysConstant.CUSTTYPE_0){	//个人客户
			colNames = getOneCustApplySql(params, sqlSb);
			totalCount = getTotalCountBySql(sqlSb.toString());	
		}else{	//企业客户
			colNames = getEntCustApplySql(params, sqlSb);
			totalCount = getTotalCountBySql(sqlSb.toString());	
			
		}
//		if(user != null){
//			sqlSb.append("and A.creator = '"+user.getUserId()+"' ");
//		}
//		String rightFilter = SqlUtil.getRightFilter(user, "A");
//		if(StringHandler.isValidStr(rightFilter)){
//			sqlSb.append(" and "+rightFilter);
//		}
		sqlSb.append(" order by A.appdate desc,A.id desc");
		try{
			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, offset, pageSize,totalCount);
			return dt;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	/**
	 * 获取个人客户 SQL 查询语句
	 * @param params
	 * @param sqlSb
	 * @return
	 */
	private <K, V> String getOneCustApplySql(SHashMap<K, V> params,
			StringBuffer sqlSb) {

		String colNames;
		colNames = "id,custType,customerId,code,custCode," +
				"name,cardType,cardNum,phone,contactTel,"+
				"breed,appAmount,"+
				"loanLimit,payType,rateType,rate,limitType," +
				"loanType,inType,loanMain,state";
		
		sqlSb.append("select A.id,A.custType,A.customerId,A.code,C.code as custCode,")
			.append("B.name,E.name as cardType,B.cardNum,B.phone,B.contactTel,")
			.append("D.name as breed,A.appAmount,")
			.append(" (case when A.yearLoan>0 then cast(A.yearLoan as varchar(5))+'年' else '' end)+")
			.append(" (case when A.monthLoan>0 then cast(A.monthLoan as varchar(3))+'个月' else '' end)+")
			.append(" (case when A.dayLoan>0 then cast(A.dayLoan as varchar(3))+'天' else '' end) as loanLimit,")
			.append(" F.name as payType,A.rateType,A.rate,G.name as limitType,")
			.append("A.loanType,I.name as inType,K.name as loanMain,A.state ")
			.append(" from fc_apply A left join crm_customerInfo B on A.customerId = B.id ")
			
			.append(" left join crm_custbase C on B.baseId = C.id ")
			.append(" left join ts_variety D on A.breed = D.id ")
			.append(" left join ts_Gvlist E on B.cardType = E.id ")
			.append(" left join fc_PayType F on A.payType = F.code ")
			.append(" left join ts_Gvlist G on A.limitType = G.id ")
//			.append(" left join ts_Gvlist H on A.loanType in( H.id ) ")
			.append(" left join ts_Gvlist I on A.inType = I.id ")
			.append(" left join ts_Gvlist K on A.loanMain = K.id ")
			.append(" where A.isenabled="+SysConstant.OPTION_ENABLED);
		
		Integer actionType = params.getvalAsInt("actionType");
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		Long userId = user.getUserId();
		if(null == userId) userId = -1l;
		
		if(actionType == null){
			sqlSb.append(" and A.state='"+BussStateConstant.FIN_APPLY_STATE_0+"'  ");
		}else{
			if(actionType.intValue() == SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1){/*个人/企业客户贷款审批*/
				sqlSb.append(" and A.state not in ("+BussStateConstant.FIN_APPLY_STATE_0+","+
						BussStateConstant.FIN_APPLY_STATE_14+","+
						BussStateConstant.FIN_APPLY_STATE_15+") ");
				
				String procIds = params.getvalAsStr("procIds");
				if(!StringHandler.isValidStr(procIds)) procIds = "-1"; /*给一个不存在的东东*/
				sqlSb.append(" and A.procId in ("+procIds+") ");
			}else if(actionType.intValue() == SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2){/*个人/企业客户贷款审批历史*/
				appendAuditHistoryWhereStr(sqlSb,user);
			}else if(actionType.intValue() == SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3){/*人/企业客户贷款一览表*/
				sqlSb.append(" and A.state <> '"+BussStateConstant.FIN_APPLY_STATE_0+"' ");
			}
		}
		//"custType#I,name,cardType#I,cardNum,phone,contactTel,eqopLoanLimit,yearLoan#I,monthLoan#I,dayLoan#I,"+
		//"eqopAmount,appAmount#D,payType#I,breed#L";
		Integer custType = params.getvalAsInt("custType");
		if(StringHandler.isValidObj(custType)){
			sqlSb.append(" and A.custType = '"+custType+"' ");
		}
		
		String name = params.getvalAsStr("name");
		if(StringHandler.isValidStr(name)){
			sqlSb.append(" and B.name like '"+name+"%' ");
		}
		
		Integer cardType = params.getvalAsInt("cardType");
		if(StringHandler.isValidObj(cardType)){
			sqlSb.append(" and B.cardType = '"+cardType+"' ");
		}
		
		String cardNum = params.getvalAsStr("cardNum");
		if(StringHandler.isValidStr(cardNum)){
			sqlSb.append(" and B.cardNum like '"+cardNum+"%' ");
		}
		
		String phone = params.getvalAsStr("phone");
		if(StringHandler.isValidStr(phone)){
			sqlSb.append(" and B.phone like '"+phone+"%' ");
		}
		
		String contactTel = params.getvalAsStr("contactTel");
		if(StringHandler.isValidStr(contactTel)){
			sqlSb.append(" and B.contactTel like '"+contactTel+"%' ");
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
		
		String eqopAmount = params.getvalAsStr("eqopAmount");
		if(StringHandler.isValidStr(eqopAmount)){
			Double appAmount = params.getvalAsDob("appAmount");
			if(null != appAmount && appAmount.doubleValue() > 0){
				sqlSb.append(" and A.appAmount "+eqopAmount+" '"+appAmount+"' ");
			}
		}
		
		String payType = params.getvalAsStr("payType");
		if(StringHandler.isValidObj(payType)){
			sqlSb.append(" and A.payType like '"+payType+"%' ");
		}
		
		Long breed = params.getvalAsLng("breed");
		if(StringHandler.isValidObj(breed)){
			sqlSb.append(" and A.breed = '"+breed+"' ");
		}
		
		return colNames;
	}
	
	/**
	 * 审批历史需加的过滤条件
	 * @param sqlSb 条件SQL 
	 * @param user	当前用户
	 */
	private void appendAuditHistoryWhereStr(StringBuffer sqlSb,UserEntity user){
		Long userId = user.getUserId();
		sqlSb.append(" and A.state <> '"+BussStateConstant.FIN_APPLY_STATE_0+"' ");
		sqlSb.append(" and A.procId in (SELECT procId FROM ts_auditrecords B where creator='"+userId+"')");
	}
	
	/**
	 * 获取企业客户 SQL 查询语句
	 * @param params
	 * @param sqlSb
	 * @return
	 */
	private <K, V> String getEntCustApplySql(SHashMap<K, V> params,StringBuffer sqlSb) {
		String colNames;
		colNames = "id,custType,customerId,code,custCode," +
				"name,cardNum,contactor,phone,contactTel,"+
				"breed,appAmount,"+
				"loanLimit,payType,rateType,rate,limitType," +
				"loanType,inType,loanMain,state";
		
		sqlSb.append("select A.id,A.custType,A.customerId,A.code,C.code as custCode,")
			.append("B.name,B.tradNumber,B.contactor,B.phone,B.contactTel,")
			.append("D.name as breed,A.appAmount,")
			
			.append(" (case when A.yearLoan>0 then cast(A.yearLoan as varchar(5))+'年' else '' end)+")
			.append(" (case when A.monthLoan>0 then cast(A.monthLoan as varchar(3))+'个月' else '' end)+")
			.append(" (case when A.dayLoan>0 then cast(A.dayLoan as varchar(3))+'天' else '' end) as loanLimit,")
			
			.append("E.name as payType,A.rateType,A.rate,F.name as limitType,")
			.append("A.loanType,H.name as inType,I.name as loanMain,A.state ")
			.append(" from fc_apply A left join crm_ecustomer B on A.customerId = B.id ")
			.append(" left join crm_custbase C on B.baseId = C.id ")
			.append(" left join ts_variety D on A.breed = D.id ")
			
			.append(" left join fc_PayType E on A.payType = E.code ")
			.append(" left join ts_Gvlist F on A.limitType = F.id ")
//			.append(" left join ts_Gvlist G on A.loanType = G.id ")
			.append(" left join ts_Gvlist H on A.inType = H.id ")
			.append(" left join ts_Gvlist I on A.loanMain = I.id ")
			
			.append(" where A.isenabled="+SysConstant.OPTION_ENABLED);
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		Long userId = user.getUserId();
		if(null == userId) userId = -1l;
		Integer actionType = params.getvalAsInt("actionType");/*业务动作类型*/
		if(actionType == null){
			sqlSb.append(" and A.state='"+BussStateConstant.FIN_APPLY_STATE_0+"' ");
		}else{
			if(actionType.intValue() == SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1){/*个人/企业客户贷款审批*/
				sqlSb.append(" and A.state not in ("+BussStateConstant.FIN_APPLY_STATE_0+","+
						BussStateConstant.FIN_APPLY_STATE_14+","+
						BussStateConstant.FIN_APPLY_STATE_15+") ");
				String procIds = params.getvalAsStr("procIds");
				if(!StringHandler.isValidStr(procIds)) procIds = "-1"; /*给一个不存在的东东*/
				sqlSb.append(" and A.procId in ("+procIds+") ");
			}else if(actionType.intValue() == SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2){/*个人/企业客户贷款审批历史*/
			
				appendAuditHistoryWhereStr(sqlSb,user);
			}else if(actionType.intValue() == SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3){/*人/企业客户贷款一览表*/
				sqlSb.append(" and A.state <> '"+BussStateConstant.FIN_APPLY_STATE_0+"' ");
			}
		}
		//"custType#I,name,cardType#I,cardNum,phone,contactTel,eqopLoanLimit,yearLoan#I,monthLoan#I,dayLoan#I,"+
		//"eqopAmount,appAmount#D,payType#I,breed#L";
		Integer custType = params.getvalAsInt("custType");
		if(StringHandler.isValidObj(custType)){
			sqlSb.append(" and A.custType = '"+custType+"' ");
		}
		
		String name = params.getvalAsStr("name");
		if(StringHandler.isValidStr(name)){
			sqlSb.append(" and B.name like '"+name+"%' ");
		}
		
		String tradNumber = params.getvalAsStr("tradNumber");
		if(StringHandler.isValidStr(tradNumber)){
			sqlSb.append(" and B.tradNumber like '"+tradNumber+"%' ");
		}
		
		String contactor = params.getvalAsStr("contactor");
		if(StringHandler.isValidStr(contactor)){
			sqlSb.append(" and B.contactor like '"+contactor+"%' ");
		}
		
		String phone = params.getvalAsStr("phone");
		if(StringHandler.isValidStr(phone)){
			sqlSb.append(" and B.phone like '"+phone+"%' ");
		}
		
		String contactTel = params.getvalAsStr("contactTel");
		if(StringHandler.isValidStr(contactTel)){
			sqlSb.append(" and B.contactTel like '"+contactTel+"%' ");
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
		
		String eqopAmount = params.getvalAsStr("eqopAmount");
		if(StringHandler.isValidStr(eqopAmount)){
			Double appAmount = params.getvalAsDob("appAmount");
			if(null != appAmount && appAmount.doubleValue() > 0){
				sqlSb.append(" and A.appAmount "+eqopAmount+" '"+appAmount+"' ");
			}
		}
		
		Long payType = params.getvalAsLng("payType");
		if(StringHandler.isValidObj(payType)){
			sqlSb.append(" and A.payType = '"+payType+"' ");
		}
		
		Long breed = params.getvalAsLng("breed");
		if(StringHandler.isValidObj(breed)){
			sqlSb.append(" and A.breed = '"+breed+"' ");
		}
		
		return colNames;
	}

	/**
	 * 根据还款计划ID获取贷款申请单列表
	 * @param contractIds 合同ID集合字符串
	 * @return
	 * @throws DaoException 
	 */
	@Override
	public Map<Long,ApplyEntity> getApplyEntitysByContractIds(String contractIds) throws DaoException{
		try{
			if(!StringHandler.isValidStr(contractIds)) throw new DaoException(" invoke getApplyEntitysByContractIds by [contractIds] is null");
			Map<Long,ApplyEntity> applyMap = new HashMap<Long, ApplyEntity>();
			StringBuffer sb = new StringBuffer();
			sb.append("select id as contractId,formId from LoanContractEntity where id in ("+contractIds+")");
			DataTable dtContract = this.find(sb.toString(), "contractId,formId");
			if(null == dtContract || dtContract.getRowCount() == 0) return applyMap;
			
			sb = new StringBuffer();
			int count = dtContract.getRowCount();
			for(int i=0; i<count; i++){
				String formId = dtContract.getString(i, "formId");
				sb.append(formId+",");
			}
			
			String applyIds = StringHandler.RemoveStr(sb);
			sb = new StringBuffer();
			sb.append(" from ApplyEntity where id in ("+applyIds+")");
			List<ApplyEntity> list = this.getList(sb.toString());
			for(ApplyEntity entity : list){
				for(int i=0; i<count; i++){
					Long contractId = dtContract.getLong(i, "contractId");
					Long formId = dtContract.getLong(i, "formId");
					Long applyId = entity.getId();
					if(applyId.equals(formId)){
						applyMap.put(contractId, entity);
						break;
					}
				}
			}
			return applyMap;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}
	
	
	/**
	 * 根据还款计划ID获取贷款申请单列表
	 * @param planIds 还款计划表ID集合字符串
	 * @return
	 * @throws DaoException 
	 */
	@Override
	public Map<Long,ApplyEntity> getApplyEntitysByPlanIds(String planIds) throws DaoException{
		try{
			Map<Long,ApplyEntity> applyMap = new HashMap<Long, ApplyEntity>();
			StringBuffer sb = new StringBuffer();
			sb.append("select id as contractId,formId from LoanContractEntity where id in (select contractId from PlanEntity where id in ("+planIds+"))");
			DataTable dtContract = this.find(sb.toString(), "contractId,formId");
			if(null == dtContract || dtContract.getRowCount() == 0) return applyMap;
			
			sb = new StringBuffer();
			int count = dtContract.getRowCount();
			for(int i=0; i<count; i++){
				String formId = dtContract.getString(i, "formId");
				sb.append(formId+",");
			}
			
			String applyIds = StringHandler.RemoveStr(sb);
			sb = new StringBuffer();
			sb.append(" from ApplyEntity where id in ("+applyIds+")");
			List<ApplyEntity> list = this.getList(sb.toString());
			for(ApplyEntity entity : list){
				for(int i=0; i<count; i++){
					Long contractId = dtContract.getLong(i, "contractId");
					Long formId = dtContract.getLong(i, "formId");
					Long applyId = entity.getId();
					if(applyId.equals(formId)){
						applyMap.put(contractId, entity);
						break;
					}
				}
			}
			return applyMap;
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}

	/**
	 * 更新项目的状态
	 * data 值 state,user
	 * @param data
	 * @throws DaoException
	 */
	public void updateState(SHashMap<String, Object> data) throws DaoException{
		try{
			Integer state = (Integer)data.getvalAsInt("state");
			UserEntity user = (UserEntity)data.get(SysConstant.USER_KEY);
			Long userId = null == user ? SysConstant.NOEXIST_ID : user.getUserId();
			String hql = "update ApplyEntity set state=?,modifier=?,modifytime=? where 1=1 ";
			String contractIds = data.getvalAsStr("contractIds");
			if(StringHandler.isValidStr(contractIds)){
				hql += " and id in (select formId from LoanContractEntity where id in("+contractIds+")) ";
			}
			String loanInvoceIds = data.getvalAsStr("loanInvoceIds");
			if(StringHandler.isValidStr(loanInvoceIds)){/*放款单ID列表*/
				hql += " and id in (select formId from LoanInvoceEntity where id in("+loanInvoceIds+")) ";
			}
			
			Query query = getSession().createQuery(hql);
			query.setParameter(0, state);
			query.setParameter(1, userId);
			query.setParameter(2, new Date());
			query.executeUpdate();
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	}
}
