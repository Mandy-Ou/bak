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
import com.cmw.dao.inter.sys.ModuleDaoInter;
import com.cmw.entity.sys.ModuleEntity;


/**
 * 模块  DAO实现类
 * @author chengmingwei
 * @date 2012-10-31T00:00:00
 */
@Description(remark="模块DAO实现类",createDate="2012-10-31T00:00:00",author="chengmingwei")
@Repository("moduleDao")
public class ModuleDaoImpl extends GenericDaoAbs<ModuleEntity, Long> implements ModuleDaoInter {
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		DataTable dt = null;
		Integer action = map.getvalAsInt("action");
		try {
			//首页导航菜单
				// SysConstant.MENU_ACTION_SYS 代表菜单管理功能取数据
			if(null == action || action == SysConstant.MENU_ACTION_NAV || action == SysConstant.MENU_ACTION_SYS){
//				dt = getNavMenus(map);
			}else if(action == SysConstant.MENU_ACTION_ROLE || action == SysConstant.MENU_ACTION_PROCESSFORM){//角色管理权限配置 ----- 菜单树
				dt = getRightMenus(map);
			}
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
//	private <K, V> DataTable getNavMenus(SHashMap<K, V> map)
//			throws DaoException{
//		try{
//			String hqlStr = "select concat('C',id) as id,name as text,'"+SysConstant.MENU_ROOT_ID+"' as pid,url as jsArray," +
//					"A.iconCls as icon,'false' as leaf,1 as type,id as accordionId from AccordionEntity A where A.isenabled='"+SysConstant.OPTION_ENABLED+"'  ";
//			
//			String dtCmns = "id,text,pid,jsArray,icon,leaf,type,accordionId";
//			
//			String sysid = map.getvalAsStr("sysid");
//			if(StringHandler.isValidStr(sysid)){
//				hqlStr += " and A.sysid = '"+sysid +"' ";
//			}
//			hqlStr += " order by A.orderNo ";
//			return findByPage(hqlStr, dtCmns,NOPAGE_PARAM,0);
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//			throw new DaoException(e);
//		}
//	}
	
	/**
	 * 角色管理--->菜单权限授权树
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	private <K, V> DataTable getRightMenus(SHashMap<K, V> map)
			throws DaoException{
		try{
			String hqlStr = "select concat('D',cast(A.id as string)) as id,name as text,concat('M',cast(A.menuId as string)) as pid," +
					"A.iconCls as icon,'true' as leaf,'false' as checked," +
					""+TreeUtil.MENU_TYPE_ACCORDION+" as type from ModuleEntity A where A.isenabled='"+SysConstant.OPTION_ENABLED+"'  ";
			
			String dtCmns = "id,text,pid,icon,leaf,checked,type";
			
			String menuIds = map.getvalAsStr("menuIds");
			if(StringHandler.isValidStr(menuIds)){
				hqlStr += " and A.menuId in ("+menuIds +") ";
			}
			return findByPage(hqlStr, dtCmns,NOPAGE_PARAM,0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
