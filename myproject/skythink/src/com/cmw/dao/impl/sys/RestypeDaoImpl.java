package com.cmw.dao.impl.sys;


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
import com.cmw.dao.inter.sys.RestypeDaoInter;
import com.cmw.entity.sys.RestypeEntity;


/**
 * 资源  DAO实现类
 * @author 彭登浩
 * @date 2012-11-19T00:00:00
 */
@Description(remark="资源DAO实现类",createDate="2012-11-19T00:00:00",author="彭登浩")
@Repository("restypeDao")
public class RestypeDaoImpl extends GenericDaoAbs<RestypeEntity, Long> implements RestypeDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		try{
			map = SqlUtil.getSafeWhereMap(map);
			String hqlStr = "select  R.id,concat(R.name,(case when R.isenabled="+SysConstant.OPTION_DISABLED+" then '【已禁用】' else '' end)) as text," +
					"'0' as pid,'"+SysConstant.RESTYPE_ICONPATH+"' as icon,'true' as leaf,0 as type from RestypeEntity R where 1=1  ";
			Integer isenabled = map.getvalAsInt("isenabled");
			if(StringHandler.isValidIntegerNull(isenabled)){
				hqlStr += " and R.isenabled = '"+isenabled +"' ";
			}else{
				hqlStr += " and R.isenabled <> '"+SysConstant.OPTION_DEL+"'  ";
			}
			String recode = map.getvalAsStr("recode");
			if(StringHandler.isValidStr(recode)){
				hqlStr += "and R.recode in ("+recode+")";
			}
			return findByPage(hqlStr, "id,text,pid,icon,leaf,type",NOPAGE_PARAM,0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
