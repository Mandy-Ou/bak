package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.entity.sys.MatTempEntity;
import com.cmw.dao.inter.sys.MatTempDaoInter;


/**
 * 资料模板  DAO实现类
 * @author pdt
 * @date 2012-12-26T00:00:00
 */
@Description(remark="资料模板DAO实现类",createDate="2012-12-26T00:00:00",author="pdt")
@Repository("matTempDao")
public class MatTempDaoImpl extends GenericDaoAbs<MatTempEntity, Long> implements MatTempDaoInter {

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
			whereStr = " and  M.isenabled <> "+SysConstant.OPTION_DEL+" ";
		}else{
			whereStr = " and  M.isenabled = "+SysConstant.OPTION_ENABLED+" ";
			leafStr = "false";
		}
		
		String hql = "select concat('M',cast(id as string)) as id, concat(M.name,(case when M.isenabled=0 then '【已禁用】' else '' end)) as text," +
				"M.sysId as pid,'"+SysConstant.MATTEMP_ICONPATH+"' as icon," +
				" '"+leafStr+"' as leaf, 2 as type,-1 as potype " +
				" from MatTempEntity M where 1=1 "+whereStr;
		
		return find(hql, "id,text,pid,icon,leaf,type,potype");
	}
	
	
}