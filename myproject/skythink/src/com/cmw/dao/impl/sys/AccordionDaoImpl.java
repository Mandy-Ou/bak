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
import com.cmw.dao.inter.sys.AccordionDaoInter;
import com.cmw.entity.sys.AccordionEntity;
import com.cmw.entity.sys.RightEntity;
import com.cmw.entity.sys.UserEntity;

/**
 * 卡片菜单DAO 实现类
 * @author Administrator
 *
 */
@Description(remark="卡片菜单DAO",createDate="2011-08-13")
@Repository("accordionDao")        //声明此类为数据持久层的类
public class AccordionDaoImpl extends GenericDaoAbs<AccordionEntity, Long> implements AccordionDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		DataTable dt = null;
		Integer action = map.getvalAsInt("action");
		try {
			map = SqlUtil.getSafeWhereMap(map);
			//首页导航菜单
				// SysConstant.MENU_ACTION_SYS 代表菜单管理功能取数据
			if(null == action || action == SysConstant.MENU_ACTION_NAV ){
				dt = getNavMenus(map);
			}else if(action.intValue() == SysConstant.MENU_ACTION_SYS){//菜单管理功能取数据 ----- 菜单树
				dt = getAllMenus(map);
			}else if(action == SysConstant.MENU_ACTION_ROLE){//角色管理权限配置 ----- 菜单树
				dt = getRightMenus(map);
			}
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		
	
	}
	
	private <K, V> DataTable getNavMenus(SHashMap<K, V> map)
			throws DaoException{
		try{
			String hqlStr = "select concat('C',cast(id as string)) as id,name as text,'"+SysConstant.MENU_ROOT_ID+"' as pid,url as jsArray," +
					"A.iconCls as icon,'false' as leaf,1 as type,id as accordionId" +
					" from AccordionEntity A where A.isenabled='"+SysConstant.OPTION_ENABLED+"'  ";
			
			String dtCmns = "id,text,pid,jsArray,icon,leaf,type,accordionId";
			
			String sysid = map.getvalAsStr("sysid");
			if(StringHandler.isValidStr(sysid)){
				hqlStr += " and A.sysid = '"+sysid +"' ";
			}
			
			String code = map.getvalAsStr("code");
			if(StringHandler.isValidStr(code)){
				String[] codeArr = code.split(",");
				if(codeArr.length == 1){
					hqlStr += " and A.code not like '"+code +"%' ";
				}else{
					StringBuffer sb = new StringBuffer();
					for(String codeStr : codeArr){
						sb.append(" A.code not like '"+codeStr+"%' and");
					}
					int andOffset = sb.lastIndexOf("and")+"and".length();
					if(andOffset == sb.length()){
						sb.delete(sb.lastIndexOf("and"), sb.length());
					}
					hqlStr += " and ("+sb.toString()+") ";
				}
			}
			UserEntity user = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
			if(null != user && (user.getIsSystem() == null || user.getIsSystem().intValue() == UserEntity.ISSYSTEM_0)){
				hqlStr += " and A.id in (select mmId from RightEntity B" +
				" where B.objtype='"+RightEntity.OBJTYPE_0+"' and B.type ='"+RightEntity.TYPE_0+"' and B.roleId in (select roleId from UroleEntity C" +
				" where C.userId='"+user.getUserId()+"')) ";
			}
			hqlStr += " order by A.orderNo ";
			return findByPage(hqlStr, dtCmns,NOPAGE_PARAM,0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	private <K, V> DataTable getAllMenus(SHashMap<K, V> map)
			throws DaoException{
		try{
			String hqlStr = "select concat('C',cast(id as string)) as id,name as text,'"+SysConstant.MENU_ROOT_ID+"' as pid,url as jsArray," +
					"A.iconCls as icon,'false' as leaf,1 as type,id as accordionId,'1' as loadType " +
					" from AccordionEntity A where A.isenabled='"+SysConstant.OPTION_ENABLED+"'  ";
			
			String dtCmns = "id,text,pid,jsArray,icon,leaf,type,accordionId,loadType";
			
			String sysid = map.getvalAsStr("sysid");
			if(StringHandler.isValidStr(sysid)){
				hqlStr += " and A.sysid = '"+sysid +"' ";
			}
			
			String code = map.getvalAsStr("code");
			if(StringHandler.isValidStr(code)){
				hqlStr += " and A.code not like '"+code +"%' ";
			}
			hqlStr += " order by A.orderNo ";
			return findByPage(hqlStr, dtCmns,NOPAGE_PARAM,0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	/**
	 * 角色管理--->菜单权限授权树
	 * @param map
	 * @return
	 * @throws DaoException
	 */
	private <K, V> DataTable getRightMenus(SHashMap<K, V> map)
			throws DaoException{
		try{
			String hqlStr = "select concat('C',cast(id as string)) as id,name as text,'"+SysConstant.MENU_ROOT_ID+"' as pid," +
					"A.iconCls as icon,'false' as leaf,'false' as checked," +
					""+TreeUtil.MENU_TYPE_ACCORDION+" as type from AccordionEntity A where A.isenabled='"+SysConstant.OPTION_ENABLED+"'  ";
			
			String dtCmns = "id,text,pid,icon,leaf,checked,type";
			
			String sysid = map.getvalAsStr("sysid");
			if(StringHandler.isValidStr(sysid)){
				hqlStr += " and A.sysid = '"+sysid +"' ";
			}
			hqlStr += " order by A.orderNo ";
			return findByPage(hqlStr, dtCmns,NOPAGE_PARAM,0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
