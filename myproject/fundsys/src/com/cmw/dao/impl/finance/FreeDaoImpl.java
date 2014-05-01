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
import com.cmw.entity.finance.FreeEntity;
import com.cmw.dao.inter.finance.FreeDaoInter;

/**
 * 放款手续费 DAO实现类
 * 
 * @author pdh
 * @date 2013-01-17T00:00:00
 */
@Description(remark = "放款手续费DAO实现类", createDate = "2013-01-17T00:00:00", author = "pdh")
@Repository("freeDao")
public class FreeDaoImpl extends GenericDaoAbs<FreeEntity, Long> implements
		FreeDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {

		try {
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(
					" select A.id,A.status,A.exempt,D.custType,D.name,C.id as contractId,")
					.append(" B.prate,A.yamount,A.freeamount,(A.freeamount-A.yamount) as notamount,A.lastDate,")
					.append(" B.realDate,B.code as ccode,B.payAmount,C.code,")
					.append(" D.appAmount,C.yearLoan,C.monthLoan,C.dayLoan,C.accName,")
					.append(" B.regBank,C.payAccount")

					/*-->fc_LoanInvoce:放款单表, fc_LoanContract:借款合同表,fc_Apply:贷款申请,crm_CustomerInfo:个人客户基本信息表	<--*/
					.append(" from fc_Free A inner join fc_LoanInvoce B on A.loanInvoceId=B.id ")
					.append(" inner join fc_LoanContract C on B.contractId = C.id ")
					.append(" inner join (select X.id as applyId,X.custType ,X.appAmount,")
					.append(" (case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId)")
					.append(" when X.custType=1 then (select name from crm_Ecustomer where id = X.customerId) else '' end) as name from fc_Apply X) D ")
					.append(" on C.formId = D.applyId where A.isenabled='"
							+ SysConstant.OPTION_ENABLED + "' and A.status !='"
							+ BussStateConstant.FREE_STATUS_2 + "'");

			params = SqlUtil.getSafeWhereMap(params);

			Integer custType = params.getvalAsInt("custType");
			if (StringHandler.isValidObj(custType)) {// 客户类型
				sqlSb.append(" and D.custType in (" + custType + ") ");
			}

			String ids = params.getvalAsStr("ids");
			if (StringHandler.isValidStr(ids)) {// 放款单ID
				sqlSb.append(" and A.id in (" + ids + ") ");
			}
			String name = params.getvalAsStr("name");
			if (StringHandler.isValidObj(name)) {// 客户名称
				sqlSb.append(" and D.name like '" + name + "%' ");
			}

			String accName = params.getvalAsStr("accName");
			if (StringHandler.isValidObj(accName)) {// 帐户户名
				sqlSb.append(" and C.accName like '" + accName + "%' ");
			}

			Double payAmount = params.getvalAsDob("payAmount");
			if (StringHandler.isValidObj(payAmount) && payAmount > 0) {// 放款金额
				String eqopAmount = params.getvalAsStr("eqopAmount");
				if (!StringHandler.isValidStr(eqopAmount))
					eqopAmount = " = ";
				sqlSb.append(" and B.payAmount " + eqopAmount + " '"
						+ payAmount + "' ");
			}

			String startDate = params.getvalAsStr("startDate");
			if (StringHandler.isValidStr(startDate)) {// 合约放款日 起始日期
				sqlSb.append(" and B.payDate >= '" + startDate + "' ");
			}

			String endDate = params.getvalAsStr("endDate");
			if (StringHandler.isValidStr(endDate)) {// 合约放款日 结束日期
				endDate = DateUtil.addDays(endDate, 1);
				sqlSb.append(" and B.payDate <= '" + endDate + "' ");
			}
			String cmns = null;
			cmns = "id,status,exempt,custType,name,contractId,"
					+ "prate,yamount,freeamount,notamount,lastDate#yyyy-MM-dd,"
					+ "realDate#yyyy-MM-dd,ccode,payAmount,code,appAmount,"
					+ "yearLoan,monthLoan,dayLoan,accName,regBank,payAccount";
			long totalCount = getTotalCountBySql(sqlSb.toString());
			DataTable dt = findBySqlPage(sqlSb.toString(), cmns, offset,
					pageSize, totalCount);
			dt.setColumnNames(cmns);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		return getResultList(map, -1, -1);
	}

	/**
	 * 贷款发放手续费收取记录
	 */

	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> map)
			throws DaoException {
		// TODO Auto-generated method stub
		return getLoanRecordsList(map, -1, -1);
	}

	/**
	 * 贷款发放手续费收取流水
	 */
	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> params,
			int offset, int pageSize) throws DaoException {

		try {
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(" select A.id,A.status,A.exempt,D.custType,D.name,C.id as contractId, ")
					.append(" B.prate,A.yamount,A.freeamount,(A.freeamount-A.yamount) as notamount,A.lastDate,")
					.append(" B.realDate,B.code as ccode,B.payAmount,C.code,")
					.append(" D.appAmount,C.yearLoan,C.monthLoan,C.dayLoan,C.accName,E.empName as creator, ")
					.append(" B.regBank,C.payAccount")

					/*-->fc_LoanInvoce:放款单表, fc_LoanContract:借款合同表,fc_Apply:贷款申请,crm_CustomerInfo:个人客户基本信息表	<--*/
					.append(" from fc_Free A inner join fc_LoanInvoce B on A.loanInvoceId=B.id ")
					.append(" inner join ts_user E on E.userId = A.creator ")
					.append(" inner join fc_LoanContract C on B.contractId = C.id ")
					.append(" inner join (select X.id as applyId,X.custType ,X.appAmount,")
					.append(" (case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId)")
					.append(" when X.custType=1 then (select name from crm_Ecustomer where id = X.customerId) else '' end) as name from fc_Apply X) D ")
					.append(" on C.formId = D.applyId where A.isenabled='"+ SysConstant.OPTION_ENABLED + "' and A.status !='"+ BussStateConstant.FREE_STATUS_0 + "'");

			params = SqlUtil.getSafeWhereMap(params);

			Integer custType = params.getvalAsInt("custType");
			if (StringHandler.isValidObj(custType)) {// 客户类型
				sqlSb.append(" and D.custType in (" + custType + ") ");
			}

			String ids = params.getvalAsStr("ids");
			if (StringHandler.isValidStr(ids)) {// 放款单ID
				sqlSb.append(" and A.id in (" + ids + ") ");
			}
			String name = params.getvalAsStr("name");
			if (StringHandler.isValidObj(name)) {// 客户名称
				sqlSb.append(" and D.name like '" + name + "%' ");
			}

			String accName = params.getvalAsStr("accName");
			if (StringHandler.isValidObj(accName)) {// 帐户户名
				sqlSb.append(" and C.accName like '" + accName + "%' ");
			}

			Double payAmount = params.getvalAsDob("payAmount");
			if (StringHandler.isValidObj(payAmount) && payAmount > 0) {// 放款金额
				String eqopAmount = params.getvalAsStr("eqopAmount");
				if (!StringHandler.isValidStr(eqopAmount))
					eqopAmount = " = ";
				sqlSb.append(" and B.payAmount " + eqopAmount + " '"
						+ payAmount + "' ");
			}

			String startDate = params.getvalAsStr("startDate");
			if (StringHandler.isValidStr(startDate)) {// 合约放款日 起始日期
				sqlSb.append(" and B.payDate >= '" + startDate + "' ");
			}

			String endDate = params.getvalAsStr("endDate");
			if (StringHandler.isValidStr(endDate)) {// 合约放款日 结束日期
				endDate = DateUtil.addDays(endDate, 1);
				sqlSb.append(" and B.payDate <= '" + endDate + "' ");
			}
			String cmns = null;
			cmns = "id,status,exempt,custType,name,contractId,"
					+ "prate,yamount,freeamount,notamount,lastDate#yyyy-MM-dd,"
					+ "realDate#yyyy-MM-dd,ccode,payAmount,code,appAmount,"
					+ "yearLoan,monthLoan,dayLoan,accName,regBank,payAccount";
			long totalCount = getTotalCountBySql(sqlSb.toString());
			DataTable dt = findBySqlPage(sqlSb.toString(), cmns, offset,
					pageSize, totalCount);
			dt.setColumnNames(cmns);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cmw.dao.inter.finance.FreeDaoInter#getIds(com.cmw.core.util.SHashMap)
	 * code
	 * ,realDate#yyyy-MM-dd,ccode,payAmount,name,prate,freeamount,amount,yamount
	 */

	@Override
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws DaoException {
		String id = map.getvalAsStr("id");
		// if(!StringHandler.isValidStr(ccodes)) throw new
		// DaoException("invoke getIds methos : codes is null ");
		StringBuffer hql = new StringBuffer();
		String whereStr = null;
		hql.append("select B.account,B.bankName,A.bussTag,A.amount, A.rectDate ,C.empName as creator from fc_FreeRecords A inner join ts_Account B on A.accountId=B.id inner join fc_Free C on A.invoceId=C.id");
		hql.append(" inner join ts_user on C.userId = A.creator ");
		hql.append(" where A.Isenabled in (" + 1 + ") ");
		hql.append(" where A.contractId in (" + id + ") ");
		return find(hql.toString(), "id,ccode,contractId");
	}

	/**
	 * 手续费收取的记录表单查询
	 */

	@Override
	public <K, V> DataTable getLoanRecord(SHashMap<K, V> map)
			throws DaoException {
		String id = map.getvalAsStr("id");
		// if(!StringHandler.isValidStr(ccodes)) throw new
		// DaoException("invoke getIds methos : codes is null ");
		StringBuffer hql = new StringBuffer();
		String whereStr = null;
		hql.append("select A.id ,B.code as ccode,B.contractId as contractId from FreeEntity A  inner join  LoanInvoceEntity B on");
		hql.append("  A.loanInvoceId=B.id where  B.code in (" + id + ") ");
		return find(hql.toString(), "id,ccode,contractId");
	}

}
