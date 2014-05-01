package com.cmw.dao.impl.fininter;


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
import com.cmw.entity.fininter.EntryTempEntity;
import com.cmw.dao.inter.fininter.EntryTempDaoInter;


/**
 * 分录模板  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="分录模板DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("entryTempDao")
public class EntryTempDaoImpl extends GenericDaoAbs<EntryTempEntity, Long> implements EntryTempDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select A.id,A.summary,B.name as account,C.name as account2,D.name as settle, ")
		  .append(" E.name as currency,A.fdc,F.dispexpress as formula,G.dispexpress as condition ")
		  .append(" from fs_EntryTemp A left join fs_Subject B on A.accountId = B.refId ")
		  .append(" left join fs_Subject C on A.accountId2=C.refId ")
		  .append(" left join fs_Settle D on A.settleId=D.refId ")
		  .append(" left join fs_Currency E on A.currencyId=E.refId ")
		  .append(" left join ts_Formula F on A.formulaId = F.id ")
		  .append(" left join ts_Formula G on A.conditionId = G.id ")
		 .append(" where A.isenabled = '"+SysConstant.OPTION_ENABLED+"' ");
		try{
			String whereStr = SqlUtil.buildWhereStr("A", params, false);
			if(StringHandler.isValidStr(whereStr)){
				sb.append(whereStr);
			}
			long count = getTotalCountBySql(sb.toString());
			sb.append(" order by A.isenabled, A.id desc ");
			DataTable dt = findBySqlPage(sb.toString(), offset, pageSize);
			dt.setColumnNames("id,summary,account,account2,settle,currency,fdc,formula,condition");
			dt.setSize(count);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
