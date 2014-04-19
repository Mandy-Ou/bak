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
import com.cmw.dao.inter.finance.AmountRecordsDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;


/**
 * 实收金额  DAO实现类
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="实收金额DAO实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Repository("amountRecordsDao")
public class AmountRecordsDaoImpl extends GenericDaoAbs<AmountRecordsEntity, Long> implements AmountRecordsDaoInter {
	
	/**
	 * 获取逾期实收金额记录信息
	 * @param params
	 * @return 
	 * @throws DaoException 
	 */
	public DataTable getLateRecords(SHashMap<String, Object> params) throws DaoException{
		try{
			StringBuffer sbhql = new StringBuffer();
			params = SqlUtil.getSafeWhereMap(params);
			sbhql.append("select A.invoceId,cat,rat,mat,pat,dat,rectDate,bussTag from AmountRecordsEntity A ")
				.append(" where isenabled='"+SysConstant.OPTION_ENABLED+"' ");
			Object bussTag = params.get("bussTag");
			if(StringHandler.isValidObj(bussTag)){
				sbhql.append(" and A.bussTag in ("+bussTag+") ");
			}
			
			Object invoceIds = params.get("invoceIds");
			if(StringHandler.isValidObj(invoceIds)){
				sbhql.append(" and A.invoceId in ("+invoceIds+") ");
			}
			sbhql.append(" order by A.invoceId,A.rectDate ");
			return find(sbhql.toString(), "invoceId,cat,rat,mat,pat,dat,rectDate,bussTag");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
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
			sqlSb.append(" select B.code,C.custType,C.name,B.payBank,B.payAccount,")
			.append(" B.accName,A.xpayDate,A.phases,C.paydPhases,C.totalPhases,")
			.append(" A.principal,A.yprincipal,A.interest,A.yinterest,A.mgrAmount,")
			.append(" A.ymgrAmount,A.totalAmount,A.ytotalAmount,")
			.append(" A.trinterAmount,A.trmgrAmount,(A.trinterAmount+A.trmgrAmount) as trtotalAmount,")
			.append("(A.totalAmount-A.ytotalAmount-A.trinterAmount-A.trmgrAmount) as ztotalAmount,")
			.append(" A.status,A.exempt,A.lastDate,B.appAmount,")
			.append(" (case when B.yearLoan>0 then cast(B.yearLoan as varchar(5))+'年' else '' end)+")
			.append(" (case when B.monthLoan>0 then cast(B.monthLoan as varchar(3))+'个月' else '' end)+")
			.append(" (case when B.dayLoan>0 then cast(B.dayLoan as varchar(3))+'天' else '' end) as loanLimit,")
			.append(" A.id,A.contractId ")
			.append(" from fc_plan A inner join fc_LoanContract B on A.contractId = B.id ")
			.append(" inner join (select X.id as applyId,X.custType,X.paydPhases,X.totalPhases,")
			.append(" (case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId)")
			.append(" when X.custType=1 then (select name from crm_Ecustomer where id = X.customerId) else '' end) as name,X.state from fc_Apply X) C ")
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
			String colNames = "code,custType,name,payBank,payAccount,"+
							"accName,xpayDate#yyyy-MM-dd,phases,paydPhases,totalPhases,"+
							"principal,yprincipal,interest,yinterest,mgrAmount,"+
							"ymgrAmount,totalAmount,ytotalAmount,trinterAmount,trmgrAmount,trtotalAmount,ztotalAmount,"+
							"status,exempt,lastDate#yyyy-MM-dd,B.appAmount,loanLimit,id,contractId";
			DataTable dt = findBySqlPage(sqlStr, colNames, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {try{
			params = SqlUtil.getSafeWhereMap(params);
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("select A.bussTag,A.cat,A.rat,A.mat,A.tat,A.rectDate,B.account as account ,B.bankName as bankName ")
				.append(" from fc_AmountRecords A inner join ts_Account B on A.accountId = B.id ")
				.append(" where  A.bussTag in ('"+BussStateConstant.AMOUNTRECORDS_BUSSTAG_0+"') ")
				.append(" and A.isenabled = '"+SysConstant.OPTION_ENABLED+"' ");
			
			Long contractId = params.getvalAsLng("contractId");
			if(StringHandler.isValidObj(contractId)){
				sqlSb.append(" and A.contractId = '"+contractId+"' ");
			}
			String sqlStr = sqlSb.toString();
			String colNames = "bussTag,cat,rat,mat,"
					+ "tat,rectDate#yyyy-MM-dd,account,bankName";
			DataTable dt = findBySqlPage(sqlStr, colNames, offset, pageSize);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
}
	/**
	 * 逾期还款详情页面的grid中取数据的方法
	 */
	@Override
public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> params,
		int offset, int pageSize) throws DaoException {try{
		StringBuffer sqlSb = new StringBuffer();
		sqlSb.append(
				"select A.bussTag,A.cat,A.rat,A.mat,A.pat,A.dat,A.tat,A.rectDate")
		.append(" from fc_AmountRecords A")
		.append(" where  A.bussTag in ("
							+ BussStateConstant.AMOUNTRECORDS_BUSSTAG_4
							+ ","
							+ BussStateConstant.AMOUNTRECORDS_BUSSTAG_5
							+ ","
							+ BussStateConstant.AMOUNTRECORDS_BUSSTAG_6
							+ ")");
		params = SqlUtil.getSafeWhereMap(params);
		String ids = params.getvalAsStr("ids");
		if(StringHandler.isValidStr(ids)){//
		sqlSb.append(" and A.id in ("+ids+") ");
		}
		long totalCount = getTotalCountBySql(sqlSb.toString());	//
		String sqlStr = sqlSb.toString();
		String colNames = "bussTag,cat,rat,mat,pat,dat,"
		+ "tat,rectDate#yyyy-MM-dd";
			DataTable dt = findBySqlPage(sqlStr, colNames, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
}
}
