package com.cmw.dao.impl.sys;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.sys.FormdiyDaoInter;
import com.cmw.entity.sys.FormdiyEntity;


/**
 * 表单DIY表  DAO实现类
 * @author pengdenghao
 * @date 2012-11-23T00:00:00
 */
@Description(remark="表单DIY表DAO实现类",createDate="2012-11-23T00:00:00",author="pengdenghao")
@Repository("formdiyDao")
public class FormdiyDaoImpl extends GenericDaoAbs<FormdiyEntity, Long> implements FormdiyDaoInter {
	/**
	 * 通过SQL查询菜单 DataTable 数据	
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String whereStr = null;
		String leafStr = "true";
		Integer action = map.getvalAsInt("action");
		if(null == action){
			whereStr = " and  F.isenabled <> "+SysConstant.OPTION_DEL+" ";
		}else{
			whereStr = " and  F.isenabled = "+SysConstant.OPTION_ENABLED+" ";
			leafStr = "false";
		}
		
		String hql = "select concat('F',cast(id as string)) as id, concat(F.name,(case when F.isenabled=0 then '【已禁用】' else '' end)) as text," +
				"F.sysid as pid,'"+SysConstant.FORMDIY_ICONPATH+"' as icon," +
				" '"+leafStr+"' as leaf, 2 as type,-1 as potype " +
				" from FormdiyEntity F where 1=1 "+whereStr;
		
		return find(hql, "id,text,pid,icon,leaf,type,potype");
	}
	
	
	/**
	 * 通过SQL查询菜单 DataTable 数据	
	 */
	@Override
	public <K, V> DataTable getResultList()throws DaoException {
		StringBuffer hqlSb = new StringBuffer();
		hqlSb.append("select id,sysid,recode,name,funcs,frecode,isRestype,maxCmncount from FormdiyEntity A ");
		hqlSb.append(" where A.isenabled='"+SysConstant.OPTION_ENABLED+"' ");
		return find(hqlSb.toString(), "id,sysid,recode,name,funcs,frecode,isRestype,maxCmncount");
	}


	@Override
	public <K, V> List<FormdiyEntity> getEntityList() throws DaoException {
		StringBuffer hqlSb = new StringBuffer();
		hqlSb.append(" from FormdiyEntity A where A.isenabled='"+SysConstant.OPTION_ENABLED+"' order by A.id asc ");
		return getList(hqlSb.toString());
	}
	
}
