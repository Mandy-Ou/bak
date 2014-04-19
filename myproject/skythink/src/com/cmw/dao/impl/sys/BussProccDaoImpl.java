package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.dao.inter.sys.BussProccDaoInter;


/**
 * 子业务流程  DAO实现类
 * @author 程明卫
 * @date 2013-08-26T00:00:00
 */
@Description(remark="子业务流程DAO实现类",createDate="2013-08-26T00:00:00",author="程明卫")
@Repository("bussProccDao")
public class BussProccDaoImpl extends GenericDaoAbs<BussProccEntity, Long> implements BussProccDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		StringBuffer sbSql = new StringBuffer();
		sbSql.append("select A.id,A.code,A.name,A.bussType,A.useorg,")
			.append("A.companyNames,A.pdid,B.name as menuId,A.formUrl,A.txtPath,A.isenabled ")
			.append(" from ts_BussProcc A left join ts_Menu B on A.menuId=B.menuId ")
			.append(" where A.isenabled <> '"+SysConstant.OPTION_DEL+"'");
		String menuName = params.getvalAsStr("menuName");
		if(StringHandler.isValidStr(menuName)){
			params.removes("menuName");
			sbSql.append(" and B.name like '"+menuName+"' ");
		}
		String whereStr = SqlUtil.buildWhereStr("A", params,false);
		if(StringHandler.isValidStr(whereStr)){
			sbSql.append(whereStr);
		}
		long totalCount = getTotalCountBySql(sbSql.toString());
		sbSql.append(" order by A.id desc ");
		String colNames = "id,code,name,bussType,useorg,companyNames,pdid,menuId,formUrl,txtPath,isenabled";
		DataTable dt = findBySqlPage(sbSql.toString(), colNames, offset, pageSize);
		dt.setSize(totalCount);
		return dt;
	}
}
