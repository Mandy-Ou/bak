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
import com.cmw.dao.inter.sys.BusscodeDaoInter;
import com.cmw.entity.sys.BusscodeEntity;


/**
 * 业务编号配置  DAO实现类
 * @author 彭登浩
 * @date 2012-11-21T00:00:00
 */
@Description(remark="业务编号配置DAO实现类",createDate="2012-11-21T00:00:00",author="彭登浩")
@Repository("busscodeDao")
public class BusscodeDaoImpl extends GenericDaoAbs<BusscodeEntity, Long> implements BusscodeDaoInter {
	

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		DataTable dt = null;
		Integer action = map.getvalAsInt("action");
		try {
			map = SqlUtil.getSafeWhereMap(map);
			//首页导航菜单
				// SysConstant.MENU_ACTION_SYS 代表菜单管理功能取数据
			if(null == action || action == SysConstant.MENU_ACTION_NAV || action == SysConstant.MENU_ACTION_SYS){
				dt = getNavMenus(map);
			}
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@SuppressWarnings("unused")
	private <K, V> DataTable getNavMenus(SHashMap<K, V> map)
			throws DaoException{
		try{
			String hqlStr = "select concat('B',cast(id as string)) as id,concat(B.name,((case when B.isenabled=0 then '【已禁用】' else '' end))as text,'" +
					"B.express as express,'false' as leaf,1 as type";
			
			String dtCmns = "id,text,pidexpress,leaf,type";
			
			String sysid = map.getvalAsStr("sysid");
			if(StringHandler.isValidStr(sysid)){
				hqlStr += " and B.sysid = '"+sysid +"' ";
			}
			hqlStr += " order by b.orderNo ";
			return findByPage(hqlStr, dtCmns,NOPAGE_PARAM,0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
