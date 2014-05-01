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
import com.cmw.core.util.TreeUtil;
import com.cmw.dao.inter.sys.RoleDaoInter;
import com.cmw.entity.sys.RoleEntity;


/**
 * 角色  DAO实现类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="角色DAO实现类",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Repository("roleDao")
public class RoleDaoImpl extends GenericDaoAbs<RoleEntity, Long> implements RoleDaoInter {
	

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		try{
			map = SqlUtil.getSafeWhereMap(map);
			String hqlStr = "select  A.id,concat(A.name,(case when A.isenabled="+SysConstant.OPTION_DISABLED+" then '【已禁用】' else '' end)) as text," +
					"'0' as pid,'"+SysConstant.ROLE_ICONPATH+"' as icon,'true' as leaf,0 as type from RoleEntity A where 1=1  ";
			Integer isenabled = map.getvalAsInt("isenabled");
			if(StringHandler.isValidIntegerNull(isenabled)){
				hqlStr += " and A.isenabled = '"+isenabled +"' ";
			}else{
				hqlStr += " and A.isenabled <> '"+SysConstant.OPTION_DEL+"'  ";
			}
			
			return findByPage(hqlStr, "id,text,pid,icon,leaf,type",NOPAGE_PARAM,0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
