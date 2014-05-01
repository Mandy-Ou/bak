package com.cmw.dao.impl.fininter;


import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.dao.inter.fininter.FinSysCfgDaoInter;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.entity.sys.MenuEntity;


/**
 * 财务系统配置  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="财务系统配置DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("finSysCfgDao")
public class FinSysCfgDaoImpl extends GenericDaoAbs<FinSysCfgEntity, Long> implements FinSysCfgDaoInter {

	@Override
	public <K, V> DataTable getResultList() throws DaoException {
		String hql = "select m.id as id,concat(m.name,(case when m.isenabled=0 then '【已禁用】' else '' end)) as text," +
				"'0' as pid,'"+SysConstant.FINANCESYS_ICONPATH+"' as icon,'true' as leaf, '"+MenuEntity.MENU_TYPE_2+"' as type " +
				" from FinSysCfgEntity m where m.isenabled <> "+SysConstant.OPTION_DEL+" " +
				" order by m.id ";
		
		return find(hql, "id,text,pid,icon,leaf,type");
	}
	
}
