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
import com.cmw.entity.finance.ExtPlanEntity;
import com.cmw.dao.inter.finance.ExtPlanDaoInter;


/**
 * 展期计划  DAO实现类
 * @author pdh
 * @date 2013-09-23T00:00:00
 */
@Description(remark="展期计划DAO实现类",createDate="2013-09-23T00:00:00",author="pdh")
@Repository("extPlanDao")
public class ExtPlanDaoImpl extends GenericDaoAbs<ExtPlanEntity, Long> implements ExtPlanDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			params = SqlUtil.getSafeWhereMap(params);
			StringBuffer sqlSb = new StringBuffer();
			String colNames = "id ,phases,contractId,actionType,formId,xpayDate#yyyy-MM-dd,interest,principal,mgrAmount,totalAmount,reprincipal";
			sqlSb.append("select A.id, A.phases,A.contractId,A.actionType,A.formId,A.xpayDate,A.interest,A.principal,A.mgrAmount,A.totalAmount,A.reprincipal ")
			.append("from fc_ExtPlan A where A.isenabled !='"+SysConstant.OPTION_DEL+"' ");
			String contractId = params.getvalAsStr("contractId");
			if(StringHandler.isValidStr(contractId)){
				sqlSb.append(" and A.contractId = '"+contractId+"' ");
			}
			Integer actionType = params.getvalAsInt("actionType");
			if(StringHandler.isValidObj(actionType)){
				sqlSb.append(" and A.actionType = '"+actionType+"' ");
			}
			Integer formId = params.getvalAsInt("formId");
			if(StringHandler.isValidObj(formId)){
				sqlSb.append(" and A.formId = '"+formId+"' ");
			}
			long totalCount = getTotalCountBySql(sqlSb.toString());	//
			sqlSb.append(" order by A.phases ASC");
			DataTable dt = findBySqlPage(sqlSb.toString(),colNames, offset, pageSize,totalCount);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
