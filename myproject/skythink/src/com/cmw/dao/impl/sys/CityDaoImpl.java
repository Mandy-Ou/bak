package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.sys.CityDaoInter;
import com.cmw.entity.sys.CityEntity;


/**
 * 城市  DAO实现类
 * @author 彭登浩
 * @date 2012-12-10T00:00:00
 */
@Description(remark="城市DAO实现类",createDate="2012-12-10T00:00:00",author="彭登浩")
@Repository("cityDao")
public class CityDaoImpl extends GenericDaoAbs<CityEntity, Long> implements CityDaoInter {
	/**
	 * 通过SQL查询菜单 DataTable 数据	
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String wherStr = "";
		String leafStr = "false";
		Integer action = map.getvalAsInt("action");
		
		String hql = "select concat('Y',cast(Y.id as string)) as id, concat(Y.name,(case when Y.isenabled=0 then '【已禁用】' else '' end)," +
				"(case when Y.isdefault=1 then '【默认城市】' else '' end)) as text," +
				"concat('P',cast(Y.provinceId as string)) as pid,'"+SysConstant.CITY_ICONPATH+"' as icon," +
				" '"+leafStr+"' as leaf, 2 as type" +
		" from CityEntity Y where 1=1 "+wherStr;
		if(null == action){
			hql += " and  Y.isenabled <> "+SysConstant.OPTION_DEL+" ";
		}else{
			hql += " and Y.isenabled = "+SysConstant.OPTION_ENABLED+" ";
		}
		
		return find(hql, "id,text,pid,icon,leaf,type");
	}
}
