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
import com.cmw.entity.finance.ChildPlanEntity;
import com.cmw.dao.inter.finance.ChildPlanDaoInter;


/**
 * 还款计划子表  DAO实现类
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="还款计划子表DAO实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Repository("childPlanDao")
public class ChildPlanDaoImpl extends GenericDaoAbs<ChildPlanEntity, Long> implements ChildPlanDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			params = SqlUtil.getSafeWhereMap(params);
			StringBuffer sqlSb = new StringBuffer();
			String colNames = "id ,phases,loanInvoceId,xpayDate#yyyy-MM-dd,interest,principal,mgrAmount,totalAmount,reprincipal";
			sqlSb.append("select A.id, A.phases,A.loanInvoceId,A.xpayDate,A.interest,A.principal,A.mgrAmount,A.totalAmount,A.reprincipal ")
			.append("from fc_ChildPlan A where A.isenabled!='"+SysConstant.OPTION_DEL+"' ");
			String loanInvoceId = params.getvalAsStr("loanInvoceId");
			if(StringHandler.isValidStr(loanInvoceId)){
				sqlSb.append(" and A.loanInvoceId = '"+loanInvoceId+"' ");
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
