package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.sys.RegionDaoInter;
import com.cmw.entity.sys.RegionEntity;


/**
 * 地区  DAO实现类
 * @author 彭登浩
 * @date 2012-12-10T00:00:00
 */
@Description(remark="地区DAO实现类",createDate="2012-12-10T00:00:00",author="彭登浩")
@Repository("regionDao")
public class RegionDaoImpl extends GenericDaoAbs<RegionEntity, Long> implements RegionDaoInter {
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
		
		String hql = "select concat('R',cast(R.id as string)) as id, concat(R.name,(case when R.isenabled=0 then '【已禁用】' else '' end)," +
				"(case when R.isdefault=1 then '【默认地区】' else '' end)) as text," +
				"concat('Y',cast(R.cityId as string)) as pid,'"+SysConstant.REGION_ICONPATH+"' as icon," +
				" '"+leafStr+"' as leaf, 2 as type" +
		" from RegionEntity R where 1=1 "+wherStr;
		if(null == action){
			hql += " and  R.isenabled <> "+SysConstant.OPTION_DEL+" ";
		}else{
			hql += " and R.isenabled = "+SysConstant.OPTION_ENABLED+" ";
		}
		
		return find(hql, "id,text,pid,icon,leaf,type");
	}
}
