package com.cmw.dao.impl.finance;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.FuntempCfgEntity;
import com.cmw.dao.inter.finance.FuntempCfgDaoInter;


/**
 * 模板功能配置表  DAO实现类
 * @author 赵世龙
 * @date 2013-11-19T00:00:00
 */
@Description(remark="模板功能配置表DAO实现类",createDate="2013-11-19T00:00:00",author="赵世龙")
@Repository("funtempCfgDao")
public class FuntempCfgDaoImpl extends GenericDaoAbs<FuntempCfgEntity, Long> implements FuntempCfgDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("select A.id,A.menuID,C.name ,A.funTag,A.remark ")
			.append("from fc_FuntempCfg A inner join fc_PrintTemp B on A.tempId= B.id ")
			.append("left join ts_Menu C on A.menuId=C.menuId ");
			String tempId = params.getvalAsStr("tempId");
			if(StringHandler.isValidStr(tempId)){
				sqlSb.append(" where A.tempId= "+tempId);
				sqlSb.append(" and A.isenabled <>-1");
			}
			String cmns="id,menuID,name,funTag,remark";
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append("  order by  A.id DESC ");
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public <K,V>DataTable getTempId(Long menuId) throws DaoException{
		try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("select top(1) A.tempId from fc_FuntempCfg A where A.menuId="+menuId);
			sqlSb.append("  order by A.id DESC ");
			String cmns="tempId";
			DataTable dt = findBySql(sqlSb.toString(),cmns);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
