package com.cmw.dao.impl.finance;


import java.util.Date;
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
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.LoanInvoceDaoInter;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 放款单  DAO实现类
 * @author 程明卫  
 * @date 2013-01-15T00:00:00
 */
@Description(remark="放款单DAO实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Repository("loanInvoceDao")
public class LoanInvoceDaoImpl extends GenericDaoAbs<LoanInvoceEntity, Long> implements LoanInvoceDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		Integer custType = params.getvalAsInt("custType");
		try{
			StringBuffer sqlSb = new StringBuffer();
			if(StringHandler.isValidObj(custType) && custType.intValue() == SysConstant.CUSTTYPE_1 ){
				sqlSb.append(" select A.id,A.code,B.code as ccode,D.name,D.kind,")
				.append(" F.name as vname,C.appAmount,C.yearLoan,C.monthLoan,C.dayLoan,")
				.append(" A.payName,A.regBank,A.account,A.payAmount,A.unAmount,A.prate,")
				.append(" A.payDate,C.rateType,C.rate ,")
				.append(" A.formId,A.contractId, A.auditState,E.empName as cashier,A.realDate,C.inRate,C.mrate,A.ysMatMonth,A.ysMat,A.ysRatMonth,A.ysRat,C.inRateType")
				
				/*-->fc_LoanInvoce:放款单表, fc_LoanContract:借款合同表,fc_Apply:贷款申请,crm_Ecustomer:企业客户基本信息表	ts_Variety:业务品种表<--*/
				.append(" from fc_LoanInvoce A inner join fc_LoanContract B on A.contractId=B.id ")
				.append(" inner join fc_Apply C on B.formId = C.id ")
				.append(" inner join crm_Ecustomer D on C.customerId = D.id ")
				.append(" inner join ts_User E on A.cashier = E.userId ")
				.append(" inner join ts_Variety F on C.breed = F.id ")
				.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and C.custType='"+SysConstant.CUSTTYPE_1+"' ");
				
			}else{
				sqlSb.append(" select A.id,A.code,B.code as ccode,D.name,D.cardType,")
				.append(" D.cardNum,C.appAmount,C.yearLoan,C.monthLoan,C.dayLoan,")
				.append(" A.payName,A.regBank,A.account,A.payAmount,A.unAmount,A.prate,")
				.append(" A.payDate,C.rateType,C.rate ,")
				.append(" A.formId,A.contractId, A.auditState,E.empName as cashier,A.realDate,C.inRate,C.mrate,A.ysMatMonth,A.ysMat,A.ysRatMonth,A.ysRat,C.inRateType")
				/*-->fc_LoanInvoce:放款单表, fc_LoanContract:借款合同表,fc_Apply:贷款申请,crm_CustomerInfo:个人客户基本信息表	<--*/
				.append(" from fc_LoanInvoce A inner join fc_LoanContract B on A.contractId=B.id ")
				.append(" inner join fc_Apply C on B.formId = C.id ")
				.append(" inner join crm_CustomerInfo D on C.customerId = D.id ")
				.append(" inner join ts_User E on A.cashier = E.userId ")
				.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and C.custType='"+SysConstant.CUSTTYPE_0+"' ");
			}
			query(params, sqlSb);
			sqlSb.append("and A.state = '"+BussStateConstant.LOANINVOCE_STATE_0+"'");
			String cmns =null;
			if(StringHandler.isValidObj(custType) && custType.intValue() == SysConstant.CUSTTYPE_1){
				cmns = "id,code,ccode,name,kind," +
					"vname,appAmount,yearLoan,monthLoan,dayLoan," +
					"payName,regBank,account,payAmount,unAmount,prate," +
					"payDate#yyyy-MM-dd,rateType,rate,formId,contractId," +
					"auditState,cashier,realDate#yyyy-MM-dd,inRate,mrate,ysMatMonth,ysMat,ysRatMonth,ysRat,inRateType";
			}else{
				cmns = "id,code,ccode,name,cardType," +
						"cardNum,appAmount,yearLoan,monthLoan,dayLoan," +
						"payName,regBank,account,payAmount,unAmount,prate," +
						"payDate#yyyy-MM-dd,rateType,rate,formId,contractId," +
						"auditState,cashier,realDate#yyyy-MM-dd,inRate,mrate,ysMatMonth,ysMat,ysRatMonth,ysRat,inRateType";
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append(" ORDER BY A.auditState  ASC , A.payDate DESC,A.id DESC");
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
			return dt;
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
	}
	/**
	 * 获取需要预收的数据
	 * @param params
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws DaoException
	 */
	@Override
	public <K, V> DataTable getNeedYsList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
				params = SqlUtil.getSafeWhereMap(params);
				StringBuffer sqlSb = new StringBuffer();
				String cmns  = "id,contractId,code,ccode,name,custType,appAmount,payAmount,"+
							"ysRatMonth,ysRat,ysMatMonth,ysMat,mrate,payType,rateType,mgrtype,urate,"+
							"rate,inRate,loanLimit,payName,regBank,account,payDate#yyyy-MM-dd,inRateType";
				sqlSb.append(" select A.id,B.id as contractId,A.code,B.code as ccode,")
					.append(" C.name as name,B.custType,")
					.append("B.appAmount,A.payAmount, ")
					.append("A.ysRatMonth,A.ysRat, A.ysMatMonth,A.ysMat,B.mrate,B.payType,B.rateType,B.mgrtype,B.urate,")
					.append(" B.rate,C.inRate,")
					.append(" (case when B.yearLoan>0 then cast(B.yearLoan as varchar(5))+'年' else '' end)+")
					.append(" (case when B.monthLoan>0 then cast(B.monthLoan as varchar(3))+'个月' else '' end)+")
					.append(" (case when B.dayLoan>0 then cast(B.dayLoan as varchar(3))+'天' else '' end) as loanLimit,")
					.append(" A.payName,A.regBank,A.account,A.payDate ,C.inRateType")
					.append(" from fc_LoanInvoce A inner join fc_LoanContract B on A.contractId=B.id ")
					.append(" inner join (select X.id,X.inRate,X.custType,X.inRateType,(case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId) when X.custType=1 then (select name from crm_Ecustomer where id = X.customerId) else '' end ) as  name from fc_Apply AS X) AS  C on B.formId = C.id ")
					.append(" inner join ts_User E on A.cashier = E.userId ")
					.append(" where  A.isenabled = '"+SysConstant.OPTION_ENABLED+"' and B.isenabled = '"+SysConstant.OPTION_ENABLED+"'  ")
					.append(" and A.isNotys = 1");
				
				UserEntity user = (UserEntity) params.getvalAsObj(SysConstant.USER_KEY);
				if(user != null){
					Long userId = user.getUserId();
					sqlSb.append(" and A.cashier = '"+userId+"' ");
				}
				Long id = params.getvalAsLng("id");
				if(StringHandler.isValidObj(id)){
					sqlSb.append(" and A.id = '"+id+"' ");
				}
				String name =  params.getvalAsStr("name");
				if(StringHandler.isValidStr(name)){
					sqlSb.append(" and C.name like '"+name+"' ");
				}
				Integer custType = params.getvalAsInt("custType");
				if(StringHandler.isValidObj(custType)){
					sqlSb.append(" and B.custType = '"+custType+"' ");
				}
				String payName = params.getvalAsStr("payName");
				if(StringHandler.isValidStr(payName)){
					sqlSb.append(" and A.payName like '"+payName+"' ");
				}
				String regBank = params.getvalAsStr("regBank");
				if(StringHandler.isValidStr(regBank)){
					sqlSb.append(" and A.regBank like '"+regBank+"' ");
				}
				String account = params.getvalAsStr("account");
				if(StringHandler.isValidStr(account)){
					sqlSb.append(" and A.regBank like '"+regBank+"' ");
				}
				
				Double payAmount = params.getvalAsDob("payAmount");
				if(StringHandler.isValidObj(payAmount) && payAmount > 0){//放款金额
					String eqopAmount = params.getvalAsStr("eqopAmount");
					if(!StringHandler.isValidStr(eqopAmount)) eqopAmount = " = ";
					sqlSb.append(" and A.payAmount "+eqopAmount+" '"+payAmount+"' ");
				}
				String startDate = params.getvalAsStr("startDate");
				if(StringHandler.isValidStr(startDate)){//合约放款日  起始日期
					sqlSb.append(" and A.payDate >= '"+startDate+"' ");
  				}
				
				String endDate = params.getvalAsStr("endDate");
				if(StringHandler.isValidStr(endDate)){//合约放款日  结束日期
					endDate = DateUtil.addDays(endDate, 1);
					sqlSb.append(" and A.payDate <= '"+endDate+"' ");
				}
				Integer ysRatMonth = params.getvalAsInt("ysRatMonth");
				if(StringHandler.isValidObj(ysRatMonth)){
					sqlSb.append(" and A.ysRatMonth = '"+ysRatMonth+"' ");
				}
				
				Double ysRat = params.getvalAsDob("ysRat");
				if(StringHandler.isValidObj(ysRat) && ysRat>0){
					sqlSb.append(" and A.ysRat = '"+ysRat+"' ");
				}
				Integer ysMatMonth = params.getvalAsInt("ysMatMonth");
				if(StringHandler.isValidObj(ysMatMonth) ){
					sqlSb.append(" and A.ysMatMonth = '"+ysMatMonth+"' ");
				}
				
				Double ysMat = params.getvalAsDob("ysMat");
				if(StringHandler.isValidObj(ysMat) && ysMat>0){
					sqlSb.append(" and A.ysMat = '"+ysMat+"' ");
				}
				
				long totalCount = getTotalCountBySql(sqlSb.toString());
				sqlSb.append(" ORDER BY A.auditState  ASC , A.payDate DESC,A.id DESC");
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
		Long id = params.getvalAsLng("id");
		if(StringHandler.isValidObj(id)){
			sqlSb.append(" and A.id in ("+id+") ");
		}
		Integer auditState = params.getvalAsInt("auditState");
		String kind =  params.getvalAsStr("kind");
		if(StringHandler.isValidStr(kind)){//企业性质
			sqlSb.append(" and D.kind in ("+kind+") ");
		}
		
		String ccode =  params.getvalAsStr("ccode");
		if(StringHandler.isValidStr(ccode)){//借款合同编号
			sqlSb.append(" and B.code like '%"+ccode+"%' ");
		}
		
		
		String ids = params.getvalAsStr("ids");
		if(StringHandler.isValidStr(ids)){//放款单ID
			sqlSb.append(" and A.id in ("+ids+") ");
		}
		
		String state = params.getvalAsStr("state");
		if(StringHandler.isValidStr(state)){//放款单状态
			sqlSb.append(" and A.state in ("+state+") ");
		}
		
		String name = params.getvalAsStr("name");
		if(StringHandler.isValidStr(name)){//客户姓名
			sqlSb.append(" and D.name like '"+name+"%' ");
		}
		
		String cardType = params.getvalAsStr("cardType");
		if(StringHandler.isValidStr(cardType)){//证件类型
			sqlSb.append(" and D.cardType = '"+cardType+"' ");
		}
		
		String cardNum = params.getvalAsStr("cardNum");
		if(StringHandler.isValidStr(cardNum)){//证件号码
			sqlSb.append(" and D.cardNum like '"+cardNum+"%' ");
		}
		
		String payName = params.getvalAsStr("payName");
		if(StringHandler.isValidStr(payName)){//收款人名称
			sqlSb.append(" and A.payName like '"+payName+"%' ");
		}
		
		String regBank = params.getvalAsStr("regBank");
		if(StringHandler.isValidStr(regBank)){//开户行
			sqlSb.append(" and A.regBank like '"+regBank+"%' ");
		}
		
		String account = params.getvalAsStr("account");
		if(StringHandler.isValidStr(account)){//收款帐号
			sqlSb.append(" and A.account like '"+account+"%' ");
		}
		
		Double appAmount = params.getvalAsDob("appAmount");
		if(StringHandler.isValidObj(appAmount) && appAmount > 0){//贷款金额
			String eqopAmount = params.getvalAsStr("eqopAmount");
			if(!StringHandler.isValidStr(eqopAmount)) eqopAmount = " = ";
			sqlSb.append(" and B.appAmount "+eqopAmount+" '"+appAmount+"' ");
		}
		

		Double payAmount = params.getvalAsDob("payAmount");
		if(StringHandler.isValidObj(payAmount) && payAmount > 0){//放款金额
			String eqopAmount = params.getvalAsStr("eqopAmount");
			if(!StringHandler.isValidStr(eqopAmount)) eqopAmount = " = ";
			sqlSb.append(" and A.payAmount "+eqopAmount+" '"+payAmount+"' ");
		}
		
		String startDate = params.getvalAsStr("startDate");
		if(StringHandler.isValidStr(startDate)){//合约放款日  起始日期
			sqlSb.append(" and A.payDate >= '"+startDate+"' ");
		}
		
		String endDate = params.getvalAsStr("endDate");
		if(StringHandler.isValidStr(endDate)){//合约放款日  结束日期
			endDate = DateUtil.addDays(endDate, 1);
			sqlSb.append(" and A.payDate <= '"+endDate+"' ");
		}
		
		String code = params.getvalAsStr("code");
		if(StringHandler.isValidStr(code)){
			sqlSb.append(" and A.code like '"+code+"%' ");
		}
		String prate = params.getvalAsStr("prate");
		if(StringHandler.isValidStr(prate)){
			sqlSb.append(" and A.prate = '"+prate+"' ");
		}
		String cashier = params.getvalAsStr("cashier");
		if(StringHandler.isValidStr(cashier)){
			sqlSb.append(" and E.empName = '"+cashier+"%' ");
		}
		
		if(StringHandler.isValidObj(auditState) && auditState==1){
			sqlSb.append(" and A.auditState >'"+BussStateConstant.LOANINVOCE_AUDITSTATE_0+"' ");
		}else if(StringHandler.isValidObj(auditState) && auditState==2){
			sqlSb.append(" and A.auditState = '"+BussStateConstant.LOANINVOCE_AUDITSTATE_2+"' ");
			UserEntity user = (UserEntity) params.getvalAsObj(SysConstant.USER_KEY);
			if(user != null){
				Long userId = user.getUserId();
				Integer isLoan = params.getvalAsInt("isLoan");
				if(StringHandler.isValidObj(isLoan) && isLoan == 1){
					sqlSb.append(" and A.cashier = '"+userId+"' ");
				}
			}
		}
		
		String formId =  params.getvalAsStr("formId");
		if(StringHandler.isValidStr(formId)){//放款单ID
			sqlSb.append(" and A.formId in ("+formId+") ");
		}
	}
	
	
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		return getResultList(map, -1, -1);
	}
	
	public void rever(Map<String,Object> dataMap) throws DaoException{
		String id = (String)dataMap.get("id");
		String hql = "update LoanInvoceEntity set state=?" +
				" where state=? and id in ("+id+")";
		try{
			Query query = getSession().createQuery(hql);
			query.setParameter(0, BussStateConstant.LOANINVOCE_STATE_0);
			query.setParameter(1, BussStateConstant.LOANINVOCE_AUDITSTATE_2);
			query.executeUpdate();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 批量更新为已放款
	 * @param dataMap 更新的参数
	 * @throws ServiceException
	 */

	public void update(Map<String,Object> dataMap) throws DaoException{
		UserEntity user = (UserEntity)dataMap.get(SysConstant.USER_KEY);
		String ids = (String)dataMap.get("ids");
		Long accountId = (Long)dataMap.get("accountId");
		String realDate = (String)dataMap.get("realDate");
		String hql = "update LoanInvoceEntity set accountId=?,realDate=?,state=?,modifier=?,modifytime=? " +
				" where state=? and id in ("+ids+")";
			
		try{
			Query query = getSession().createQuery(hql);
			query.setParameter(0, accountId);
			query.setParameter(1, DateUtil.dateFormat(realDate));
			query.setParameter(2, BussStateConstant.LOANINVOCE_STATE_1);
			query.setParameter(3, user.getUserId());
			query.setParameter(4, new Date());
			query.setParameter(5, BussStateConstant.LOANINVOCE_STATE_0);/*防并发问题*/
			query.executeUpdate();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	@Override
	public <K, V> DataTable getIds(SHashMap<K, V> map)
			throws DaoException {
		String codes = map.getvalAsStr("codes");
		//if(!StringHandler.isValidStr(codes)) throw new DaoException("invoke getIds methos : codes is null ");
		String hql = "select id,code from LoanInvoceEntity A ";
		String whereStr = null;
		if(StringHandler.isValidStr(codes)){
			whereStr = " where code in ("+codes+") ";
		}else{
			whereStr = SqlUtil.buildWhereStr("A", map);
		}
		hql += whereStr;
		return find(hql, "id,code");
	}
	
	/* (non-Javadoc)
	 * @see com.cmw.dao.inter.finance.LoanInvoceDaoInter#getLoanInvoceQuery(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public DataTable getLoanInvoceQuery(SHashMap<String, Object> params,
			int offset, int pageSize)  throws DaoException{
		params = SqlUtil.getSafeWhereMap(params);
		Long id =  params.getvalAsLng("id");
		try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(" select A.id,A.code,B.code as ccode,C.name,C.custType,C.rateType,C.rate,C.appAmount,")
			.append(" A.payName,A.regBank,A.account,A.payAmount,A.unAmount,A.prate,")
			.append(" A.payDate,")
			.append(" A.formId,A.contractId, A.auditState,E.empName as cashier,A.realDate")
			/*-->fc_LoanInvoce:放款单表, fc_LoanContract:借款合同表,fc_Apply:贷款申请,crm_Ecustomer:企业客户基本信息表	ts_Variety:业务品种表<--*/
			.append(" from fc_LoanInvoce A inner join fc_LoanContract B on A.contractId=B.id ")
			.append(" inner join (select X.id as applyId ,X.custType as custType ,X.rateType as rateType ,X.rate as rate,X.appAmount as appAmount, (case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId) ")
			.append(" when X.custType=1 then (select name from crm_Ecustomer where id = X.customerId) else '' end) as name  from fc_Apply X ) C ")
			.append(" on B.formId = C.applyId  ")
			.append(" inner join ts_User E on A.cashier = E.userId ")
			.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
			query(params, sqlSb);
			sqlSb.append("and A.state = '"+BussStateConstant.LOANINVOCE_STATE_1+"'");
			sqlSb.append("and A.auditState = '"+BussStateConstant.LOANINVOCE_AUDITSTATE_2+"'");
			if(StringHandler.isValidObj(id)){//放款单ID
				sqlSb.append(" and A.id in ("+id+") ");
			}
			String cmns =null;
				cmns = "id,code,ccode,name,custType,rateType,rate,appAmount," +
					"payName,regBank,account,payAmount,unAmount,prate," +
					"payDate#yyyy-MM-dd,formId,contractId," +
					"auditState,cashier,realDate#yyyy-MM-dd";
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append(" ORDER BY A.auditState  ASC , A.payDate DESC,A.id DESC");
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	@Override
	public <K, V> DataTable getDsByAmountLogId(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
		try{
			return super.getResultList(params, offset, pageSize);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 放款单流水
	 */
	@Override
	public DataTable getLoanInvoceQueryDetail(SHashMap<String, Object> params,
			int offset, int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		Integer custType = params.getvalAsInt("custType");
		try{
			StringBuffer sqlSb = new StringBuffer();
			if(StringHandler.isValidObj(custType) && custType.intValue() == SysConstant.CUSTTYPE_1 ){
				sqlSb.append(" select A.id,A.code,B.code as ccode,B.payAccount,B.accName,D.name,D.kind,")
				.append(" F.name as vname,C.appAmount,C.yearLoan,C.monthLoan,C.dayLoan,")
				.append(" A.payName,A.regBank,A.account,A.payAmount,A.unAmount,A.prate,")
				.append(" A.payDate,C.rateType,C.rate ,")
				.append(" A.formId,A.contractId, A.auditState,E.empName as cashier,A.realDate,G.account as Gaccouont,G.bankName as GbankName,C.inRate,C.mrate,C.inRateType,A.ysMatMonth,A.ysMat,A.ysRatMonth,A.ysRat")
				/*-->fc_LoanInvoce:放款单表, fc_LoanContract:借款合同表,fc_Apply:贷款申请,crm_Ecustomer:企业客户基本信息表	ts_Variety:业务品种表<--*/
				.append(" from fc_LoanInvoce A inner join fc_LoanContract B on A.contractId=B.id ")
				.append(" inner join fc_Apply C on B.formId = C.id ")
				.append(" inner join crm_Ecustomer D on C.customerId = D.id ")
				.append(" inner join ts_User E on A.cashier = E.userId ")
				.append(" inner join ts_Variety F on C.breed = F.id ")
				.append(" inner join ts_Account G on A.accountId = G.id ")
				.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and C.custType='"+SysConstant.CUSTTYPE_1+"' ");
				
			}else{
				sqlSb.append(" select A.id,A.code,B.code as ccode,B.payAccount,B.accName,D.name,D.cardType,")
				.append(" D.cardNum,C.appAmount,C.yearLoan,C.monthLoan,C.dayLoan,")
				.append(" A.payName,A.regBank,A.account,A.payAmount,A.unAmount,A.prate,")
				.append(" A.payDate,C.rateType,C.rate ,")
				.append(" A.formId,A.contractId, A.auditState,E.empName as cashier,A.realDate,G.account as Gaccouont,G.bankName as GbankName,C.inRate,C.mrate,C.inRateType,A.ysMatMonth,A.ysMat,A.ysRatMonth,A.ysRat")
				/*-->fc_LoanInvoce:放款单表, fc_LoanContract:借款合同表,fc_Apply:贷款申请,crm_CustomerInfo:个人客户基本信息表	<--*/
				.append(" from fc_LoanInvoce A inner join fc_LoanContract B on A.contractId=B.id ")
				.append(" inner join fc_Apply C on B.formId = C.id ")
				.append(" inner join crm_CustomerInfo D on C.customerId = D.id ")
				.append(" inner join ts_User E on A.cashier = E.userId ")
				.append(" inner join ts_Account G on A.accountId = G.id ")
				.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and C.custType='"+SysConstant.CUSTTYPE_0+"' ");
			}
			query(params, sqlSb);
			sqlSb.append("and A.state = '"+BussStateConstant.LOANINVOCE_STATE_1+"'");
			sqlSb.append("and A.auditState = '"+BussStateConstant.LOANINVOCE_AUDITSTATE_2+"'");//放款状态是已经放款的
			String cmns =null;
			if(StringHandler.isValidObj(custType) && custType.intValue() == SysConstant.CUSTTYPE_1){
				cmns = "id,code,ccode,payAccount,accName,name,kind," +
					"vname,appAmount,yearLoan,monthLoan,dayLoan," +
					"payName,regBank,account,payAmount,unAmount,prate," +
					"payDate#yyyy-MM-dd,rateType,rate,formId,contractId," +
					"auditState,cashier,realDate#yyyy-MM-dd,Gaccouont,GbankName,inRate,mrate,inRateType,ysMatMonth,ysMat,ysRatMonth,ysRat";
			}else{
				cmns = "id,code,ccode,payAccount,accName,name,cardType," +
						"cardNum,appAmount,yearLoan,monthLoan,dayLoan," +
						"payName,regBank,account,payAmount,unAmount,prate," +
						"payDate#yyyy-MM-dd,rateType,rate,formId,contractId," +
						"auditState,cashier,realDate#yyyy-MM-dd,Gaccouont,GbankName,inRate,mrate,inRateType,ysMatMonth,ysMat,ysRatMonth,ysRat";
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append(" ORDER BY A.auditState  ASC , A.payDate DESC,A.id DESC");
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
			return dt;
			} catch (DataAccessException e) {
				e.printStackTrace();
				throw new DaoException(e);
			}
		}
}
