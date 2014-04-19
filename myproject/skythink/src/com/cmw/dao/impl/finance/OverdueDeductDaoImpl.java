package com.cmw.dao.impl.finance;

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
import com.cmw.dao.inter.finance.OverdueDeductDaoInter;
import com.cmw.entity.finance.TaboutsideEntity;

/**
 * 逾期还款 DAO实现类
 * 
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark = "逾期还款DAO实现类", createDate = "2013-02-28T00:00:00", author = "程明卫")
@Repository("overdueDeductDao")
public class OverdueDeductDaoImpl extends GenericDaoAbs<TaboutsideEntity, Long>
		implements OverdueDeductDaoInter {

	/**
	 * 获取逾期扣收数据
	 * 
	 * @param params
	 *            查询参数
	 * @param offset
	 *            分页起始位
	 * @param pageSize
	 *            每页大小
	 * @return
	 * @throws DaoException
	 */
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try {
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(
					"select A.monthPharses,A.totalPharses,A.amounts,A.iamounts,A.mamounts, ")
					.append(" A.pamounts,A.damounts,ROUND(A.amounts+A.iamounts+A.mamounts+A.pamounts+A.damounts,2) as totalAmounts,"
							+ "A.flevel,A.inouttype,ROUND(B.totalAmount+B.penAmount+B.delAmount-B.ytotalAmount,2) as flamount,")
					.append(" B.xpayDate as fldate,C.code as code,C.appAmount,C.payBank,C.payAccount,")
					.append(" C.accName,D.custType,D.name,D.paydPhases,D.totalPhases, ")
					.append(" E.name riskLevel,E.color,A.contractId,A.id ")
					.append(" from fc_taboutside A inner join fc_plan B  on A.planId=B.id ")
					.append(" inner join fc_LoanContract C on A.contractId = C.id ")
					.append(" inner join (select X.id as applyId,X.custType,X.paydPhases,X.totalPhases, ")
					.append(" (case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId) ")
					.append(" when X.custType=1 then (select name from crm_Ecustomer where id = X.customerId) else '' end) as name from fc_Apply X) D ")
					.append(" on C.formId = D.applyId left join fc_riskLevel E on A.flevel = E.id where A.isenabled='"
							+ SysConstant.OPTION_ENABLED + "' ")
					.append(" and A.planId is not null ");
			params = SqlUtil.getSafeWhereMap(params);
			Long id = params.getvalAsLng("id");
			if (StringHandler.isValidObj(id)) {//
				sqlSb.append(" and A.id = " + id + " ");
			}
			String ids = params.getvalAsStr("ids");
			if (StringHandler.isValidStr(ids)) {//
				sqlSb.append(" and A.id in (" + ids + ") ");
			}
			String custType = params.getvalAsStr("custType");
			if (StringHandler.isValidStr(custType)) {// 客户类型
				sqlSb.append(" and D.custType= '" + custType + "' ");
			}
			String name = params.getvalAsStr("name");
			if (StringHandler.isValidStr(name)) {// 客户名称
				sqlSb.append(" and D.name like '" + name + "%' ");
			}

			String code = params.getvalAsStr("code");
			if (StringHandler.isValidStr(code)) {// 借款合同号
				sqlSb.append(" and C.code like '" + code + "%' ");
			}
			String payBank = params.getvalAsStr("payBank");
			if (StringHandler.isValidStr(payBank)) {// 还款银行
				sqlSb.append(" and C.payBank like '" + payBank + "%' ");
			}

			String payAccount = params.getvalAsStr("payAccount");
			if (StringHandler.isValidStr(payAccount)) {// 还款帐号
				sqlSb.append(" and C.payAccount like '" + payAccount + "%' ");
			}

			Integer inouttype = params.getvalAsInt("inouttype");
			if (StringHandler.isValidIntegerNull(inouttype)) {// 表内表外
				sqlSb.append(" and A.inouttype = '" + inouttype + "' ");
			}

			Long flevel = params.getvalAsLng("flevel");
			if (StringHandler.isValidObj(flevel)) {// 风险等级
				sqlSb.append(" and A.flevel = '" + flevel + "' ");
			}
			long totalCount = getTotalCountBySql(sqlSb.toString()); //
			sqlSb.append(" ORDER BY A.inouttype,A.id ");
			String sqlStr = sqlSb.toString();
			String colNames = "monthPharses,totalPharses,amounts,iamounts,mamounts,"
					+ "pamounts,damounts,totalAmounts,flevel,inouttype,"
					+ "flamount,fldate#yyyy-MM-dd,code,appAmount,payBank,"
					+ "payAccount,accName,custType,name,paydPhases,"
					+ "totalPhases,riskLevel,color,contractId,id";
			DataTable dt = findBySqlPage(sqlStr, colNames, offset, pageSize,
					totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	/**
	 * 获取逾期扣流水情况
	 * 
	 * @param params
	 *            查询参数
	 * @param offset
	 *            分页起始位
	 * @param pageSize
	 *            每页大小
	 * @return
	 * @throws DaoException
	 */
	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> map)
			throws DaoException {
		return getLoanRecordsList(map, -1, -1);
	}
	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> params,
			int offset, int pageSize) throws DaoException {
		try {
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(
					"select A.id, A.cat,A.rat,A.mat,A.pat,A.dat,A.tat,B.formId,B.code,B.borAccount,B.payBank,B.payAccount, ")
					.append("B.payDay,B.endDate,A.contractId,A.bussTag,A.invoceId,A.rectDate")
					.append(" ,A.accountId,A.fcnumber,A.remark,A.empId,A.creator,A.createTime,C.custType,C.accName,C.appAmount from fc_AmountRecords")
					.append(" A inner join fc_LoanContract B on A.contractId=B.id inner join fc_LoanContract C on  A.contractId=C.id where A.bussTag in ("
							+ BussStateConstant.AMOUNTRECORDS_BUSSTAG_4
							+ ","
							+ BussStateConstant.AMOUNTRECORDS_BUSSTAG_5
							+ ","
							+ ""
							+ BussStateConstant.AMOUNTRECORDS_BUSSTAG_6
							+ ") and A.isenabled='"
							+ SysConstant.OPTION_ENABLED + "' ");
			long totalCount = getTotalCountBySql(sqlSb.toString()); //
			String sqlStr = sqlSb.toString();
			String colNames = "id,cat,rat,mat,pat,dat,tat,formId,code,borAccount,payBank,payAccount,"
					+ "payDay,endDate,contractId,bussTag,invoceId,rectDate,accountId,"
					+ "fcnumber,remark,empId,creator,createTime,custType,accName,appAmount,";
			DataTable dt = findBySqlPage(sqlStr, colNames, offset, pageSize,
					totalCount);
			params = SqlUtil.getSafeWhereMap(params);
			Long contractId = params.getvalAsLng("contractId");
			if (StringHandler.isValidObj(contractId)) {//
				sqlSb.append(" and A.contractId = " + contractId + " ");
			}
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	@Override
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws DaoException {
		String codes = map.getvalAsStr("codes");
		String hql = null;
		if (StringHandler.isValidStr(codes)) {
			hql = "select A.id,A.code from LoanContractEntity A where code in ("
					+ codes + ")";
			return find(hql, "id,code");
		} else {
			hql = "select A.contractId from TaboutsideEntity A  where A.isenabled='"
					+ SysConstant.OPTION_ENABLED
					+ "' and A.planId is not null ";
			return find(hql, "contractId");
		}
	}
	/**
	 * 逾期还款的流水
	 * 
	 */
	@Override
	public DataTable RepDetail(SHashMap<String, Object> params, int offset,
			int pageSize) throws DaoException {
		try {
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append(
					"select C.code as code,D.custType,C.accName,C.appAmount, C.payBank,C.payAccount,A.monthPharses,A.totalPharses,A.amounts,A.iamounts,A.mamounts, ")
					.append(" A.pamounts,A.damounts,ROUND(A.amounts+A.iamounts+A.mamounts+A.pamounts+A.damounts,2) as totalAmounts,"
							+ "A.flevel,A.inouttype,")
				//---------------->	fc_Taboutside(表内表外表),<------------------------//
					.append(" D.name,D.paydPhases,D.totalPhases, ")
					.append(" A.contractId,A.id ")
					.append(" from fc_taboutside A inner join fc_LoanContract C on A.contractId = C.id ")
					.append(" INNER JOIN fs_AmountLog f on A.planId = f.invoceIds ")
					.append(" inner join (select X.id as applyId,X.custType,X.paydPhases,X.totalPhases, ")
					.append(" (case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId) ")
					.append(" when X.custType=1 then (select name from crm_Ecustomer where id = X.customerId) else '' end) as name from fc_Apply X) D ")
					.append(" on C.formId = D.applyId where A.isenabled='"
							+ SysConstant.OPTION_ENABLED + "' ")
					.append(" and f.bussTag in("+BussStateConstant.AMOUNTLOG_BUSSTAG_5+","+BussStateConstant.AMOUNTLOG_BUSSTAG_6+","+BussStateConstant.AMOUNTLOG_BUSSTAG_7+") ");
			params = SqlUtil.getSafeWhereMap(params);
			Long id = params.getvalAsLng("id");
			if (StringHandler.isValidObj(id)) {//
				sqlSb.append(" and A.id = " + id + " ");
			}
			String ids = params.getvalAsStr("ids");
			if (StringHandler.isValidStr(ids)) {//
				sqlSb.append(" and A.id in (" + ids + ") ");
			}
			String custType = params.getvalAsStr("custType");
			if (StringHandler.isValidStr(custType)) {// 客户类型
				sqlSb.append(" and D.custType= '" + custType + "' ");
			}
			String name = params.getvalAsStr("name");
			if (StringHandler.isValidStr(name)) {// 客户名称
				sqlSb.append(" and D.name like '" + name + "%' ");
			}

			String code = params.getvalAsStr("code");
			if (StringHandler.isValidStr(code)) {// 借款合同号
				sqlSb.append(" and C.code like '" + code + "%' ");
			}
			String payBank = params.getvalAsStr("payBank");
			if (StringHandler.isValidStr(payBank)) {// 还款银行
				sqlSb.append(" and C.payBank like '" + payBank + "%' ");
			}

			String payAccount = params.getvalAsStr("payAccount");
			if (StringHandler.isValidStr(payAccount)) {// 还款帐号
				sqlSb.append(" and C.payAccount like '" + payAccount + "%' ");
			}

			Integer inouttype = params.getvalAsInt("inouttype");
			if (StringHandler.isValidIntegerNull(inouttype)) {// 表内表外
				sqlSb.append(" and A.inouttype = '" + inouttype + "' ");
			}

			Long flevel = params.getvalAsLng("flevel");
			if (StringHandler.isValidObj(flevel)) {// 风险等级
				sqlSb.append(" and A.flevel = '" + flevel + "' ");
			}

			long totalCount = getTotalCountBySql(sqlSb.toString()); //
			sqlSb.append(" ORDER BY A.inouttype,A.id ");
			String sqlStr = sqlSb.toString();
			String colNames = "code,custType,accName,appAmount,payBank,payAccount,monthPharses,totalPharses,amounts,iamounts,mamounts,"
					+ "pamounts,damounts,totalAmounts,flevel,inouttype,"
					+ "name,paydPhases,"
					+ "totalPhases,contractId,id";
			DataTable dt = findBySqlPage(sqlStr, colNames, offset, pageSize,
					totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}}
}
