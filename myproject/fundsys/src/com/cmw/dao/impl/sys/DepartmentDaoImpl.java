package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.TreeUtil;
import com.cmw.dao.inter.sys.DepartmentDaoInter;
import com.cmw.entity.sys.DepartmentEntity;


/**
 * 部门  DAO实现类
 * @author 彭登浩
 * @date 2012-11-09T00:00:00
 */
@Description(remark="部门DAO实现类",createDate="2012-11-09T00:00:00",author="彭登浩")
@Repository("departmentDao")
public class DepartmentDaoImpl extends GenericDaoAbs<DepartmentEntity, Long> implements DepartmentDaoInter {
	/**
	 * 通过SQL查询菜单 DataTable 数据	
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String wherStr = "";
		String cmn = "checked,";
		Integer action = map.getvalAsInt("action");
		if(null != action && action.intValue() == SysConstant.ORG_ACTION_DEPT_1){
			wherStr = "'false' as "+cmn;
		}else{
			wherStr = "'"+TreeUtil.BLANK_NULL+"' as "+cmn;
		}
		
		String hql = "select concat('D',cast(D.id as string)) as id, D.name as text," +
				"(case when D.potype = "+DepartmentEntity.DEPARTMENT_POTYPE_1+" then concat('C',cast(D.poid as string)) else concat('D',cast(D.poid as string)) end) as pid,'"+SysConstant.DEPARTMENT_ICONPATH+"' as icon," +
				" 'false' as leaf,"+wherStr+" 2 as type,D.potype " +
		" from DepartmentEntity D where 1=1 ";
		if(null == action){
			hql += " and  D.isenabled <> "+SysConstant.OPTION_DEL+" ";
		}else{
			hql += " and  D.isenabled = "+SysConstant.OPTION_ENABLED+" ";
		}
		
		return find(hql, "id,text,pid,icon,leaf,"+cmn+"type,potype");
	}
}
