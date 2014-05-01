package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.entity.finance.CmnMappingEntity;
import com.cmw.dao.inter.finance.CmnMappingDaoInter;


/**
 * 列映射关系表  DAO实现类
 * @author 赵世龙
 * @date 2013-11-22T00:00:00
 */
@Description(remark="列映射关系表DAO实现类",createDate="2013-11-22T00:00:00",author="赵世龙")
@Repository("cmnMappingDao")
public class CmnMappingDaoImpl extends GenericDaoAbs<CmnMappingEntity, Long> implements CmnMappingDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String hql = "select concat('C',cast(C.id as string)) as id, C.name as text," +
				" concat('D',cast(C.tdsId as string)) as pid,'true' as leaf"+
		" from CmnMappingEntity C where C.isenabled<>-1 ";
		Long tdsId=map.getvalAsLng("tdsId");
		if(tdsId!=null){
			hql+="and C.tdsId="+tdsId;
		}
		return find(hql, "id,text,pid,leaf");
		
	}
	

	@Override
	public <K, V> DataTable getCmnList(SHashMap<K, V> map) throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String hql = "select C.id, C.name as text,C.fmt,C.dataType" +
		" from CmnMappingEntity C where C.isenabled<>-1 ";
		Long tdsId=map.getvalAsLng("tdsId");
		if(tdsId!=null){
			hql+="and C.tdsId="+tdsId;
		}
		return find(hql, "id,text,fmt,dataType");
		
	}	
	
}
