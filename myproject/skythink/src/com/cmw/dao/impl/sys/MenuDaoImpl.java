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
import com.cmw.dao.inter.sys.MenuDaoInter;
import com.cmw.entity.sys.MenuEntity;
import com.cmw.entity.sys.UserEntity;

/**
 * 菜单管理DAO 实现类
 * @author Administrator
 *
 */
@Description(remark="菜单管理DAO",createDate="2011-08-13")
@Repository("menuDao")        //声明此类为数据持久层的类
public class MenuDaoImpl extends GenericDaoAbs<MenuEntity, Long> implements MenuDaoInter {
	
	/**
	 * 通过SQL查询菜单 DataTable 数据
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		DataTable dt = null;
		Integer action = map.getvalAsInt("action");
		try {
		//首页导航菜单
			// SysConstant.MENU_ACTION_SYS 代表菜单管理功能取数据
		if(null == action || action.intValue() == SysConstant.MENU_ACTION_NAV){
			dt = getNavMenus(map);
		}else if(action.intValue() == SysConstant.MENU_ACTION_SYS){//菜单管理功能取数据 ----- 菜单树
			dt = getAllMenus(map);
		}else if(action.intValue() == SysConstant.MENU_ACTION_ROLE){//角色管理权限配置 ----- 菜单树
			dt = getRightMenus(map);
		}else if(action.intValue() == SysConstant.MENU_ACTION_PROCESSFORM){//业务品种管理  ----- 流程配置，业务表单树所传参数
			dt = getProccessFormMenus(map);
		}
//		else{	//角色权限配置 ----- 菜单树 
//			dt = getAllMenus(map);
//		}
		return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	private <K, V> DataTable getNavMenus(SHashMap<K, V> map){
		//SQL查询语句
		//菜单ID,系统ID,父菜单ID,菜单编码,菜单名称,菜单类型,菜单样式,链接,Js文件
		//menuId,sysId,pid,code,name,type,iconCls,link,jsArray
		String hql = "select m.menuId as id,m.name as text," +
				"(case when m.type = "+MenuEntity.MENU_TYPE_1+" then concat('C',cast(m.accordionId as string)) else cast(m.pid as string) end) as pid," +
				"m.jsArray,m.iconCls,m.leaf,m.type,m.accordionId,m.loadType,m.tabId,m.cached,m.params,'' as modDatas " +
		" from MenuEntity m where m.isenabled="+SysConstant.OPTION_ENABLED+" ";
		
		Integer type = map.getvalAsInt("type");
		if(StringHandler.isValidObj(type)){
			hql += " and m.type='"+type+"' ";
		}
		Long accordionId = map.getvalAsLng("accordionId");
		if(StringHandler.isValidObj(accordionId)){
			hql += " and m.accordionId='"+accordionId+"' ";
		}
		
		String tabId = map.getvalAsStr("tabId");
		if(StringHandler.isValidStr(tabId)){
			hql += " and m.tabId='"+tabId+"' ";
		}
		
		UserEntity user = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
		boolean isFiter = false;
		if(null != user && (user.getIsSystem() != null && user.getIsSystem().intValue() != UserEntity.ISSYSTEM_1)){
			isFiter = true;
		}
		
		String menuIds = map.getvalAsStr("menuIds");
		if(isFiter && StringHandler.isValidStr(menuIds)){
			hql += " and m.menuId in ("+menuIds+") ";
		}
		return find(hql, "id,text,pid,jsArray,icon,leaf,type,accordionId,loadType,tabId,cached,params,modDatas");
	}
	
	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	private <K, V> DataTable getAllMenus(SHashMap<K, V> map){
		//SQL查询语句
		//菜单ID,系统ID,父菜单ID,菜单编码,菜单名称,菜单类型,菜单样式,链接,Js文件
		//menuId,sysId,pid,code,name,type,iconCls,link,jsArray
		String hql = "select m.menuId as id,concat(m.name,(case when m.isenabled=0 then '【已禁用】' else '' end)) as text," +
				"(case when m.type = "+MenuEntity.MENU_TYPE_1+" then concat('C',cast(m.accordionId as string)) else cast(m.pid as string) end) as pid," +
				"m.jsArray,m.iconCls,m.leaf,m.type,m.accordionId " +
		" from MenuEntity m where m.isenabled <> "+SysConstant.OPTION_DEL+" ";
		
		Integer type = map.getvalAsInt("type");
		if(StringHandler.isValidObj(type)){
			hql += " and m.type='"+type+"' ";
		}
		Long accordionId = map.getvalAsLng("accordionId");
		if(StringHandler.isValidObj(accordionId)){
			hql += " and m.accordionId='"+accordionId+"' ";
		}
		return find(hql, "id,text,pid,jsArray,icon,leaf,type,accordionId");
	}
	
	/**
	 * 获取业务流程表单菜单
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	private <K, V> DataTable getProccessFormMenus(SHashMap<K, V> map){
		String hql = "select concat('M',cast(A.menuId as string)) as id,A.name as text," +
				"concat((case when A.type = "+TreeUtil.MENU_TYPE_ACCORDION+" then 'C' else 'M' end),cast(A.pid as string)) as pid," +
						" A.iconCls as icon,'false' as leaf,'false' as checked,A.type,A.accordionId from MenuEntity A" +
						" where A.isenabled= "+SysConstant.OPTION_ENABLED ;
		String dtCmns = "id,text,pid,icon,leaf,checked,type";
		
		//查询条件
		String accordionId = map.getvalAsStr("accordionId");
		if(StringHandler.isValidStr(accordionId)){
			hql += " and A.accordionId = '"+accordionId+"' ";
		}
		return findByPage(hql, dtCmns,NOPAGE_PARAM,0);
	}
	

	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	private <K, V> DataTable getRightMenus(SHashMap<K, V> map){
		String hql = "select concat('M',cast(A.menuId as string)) as id,A.name as text," +
				"concat((case when A.type = "+TreeUtil.MENU_TYPE_ACCORDION+" then 'C' else 'M' end),cast(A.pid as string)) as pid," +
						" A.iconCls as icon,A.leaf,'false' as checked,A.type,A.accordionId from MenuEntity A" +
						" where A.isenabled= "+SysConstant.OPTION_ENABLED;
		String dtCmns = "id,text,pid,icon,leaf,checked,type";
		
		//查询条件
		String accordionIds = map.getvalAsStr("accordionIds");
		if(StringHandler.isValidStr(accordionIds)){
			hql += " and A.accordionId in ("+accordionIds+") ";
		}
		return findByPage(hql, dtCmns,NOPAGE_PARAM,0);
	}
	
}
