package com.cmw.dao.impl.fininter;


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
import com.cmw.entity.fininter.FinBussObjectEntity;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.dao.inter.fininter.FinBussObjectDaoInter;


/**
 * 自定义业务对象  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="自定义业务对象DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("finBussObjectDao")
public class FinBussObjectDaoImpl extends GenericDaoAbs<FinBussObjectEntity, Long> implements FinBussObjectDaoInter {
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)throws DaoException {
		try{
			String hql = "select m.id as id,concat(m.name,(case when m.isenabled=0 then '【已禁用】' else '' end)) as text," +
					"'0' as pid,'"+SysConstant.FINANCESYS_ICONPATH+"' as icon,'true' as leaf, '"+MenuEntity.MENU_TYPE_2+"' as type " +
					" from FinBussObjectEntity m where m.isenabled <> "+SysConstant.OPTION_DEL+" ";
			String whereStr = SqlUtil.buildWhereStr("m", map, false);
			if(StringHandler.isValidStr(whereStr)){
				hql += whereStr;
			}
			hql += " order by m.id ";
			return find(hql, "id,text,pid,icon,leaf,type");
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
