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
import com.cmw.dao.inter.sys.PostDaoInter;
import com.cmw.entity.sys.PostEntity;


/**
 * 职位  DAO实现类
 * @author 彭登浩
 * @date 2012-11-10T00:00:00
 */
@Description(remark="职位DAO实现类",createDate="2012-11-10T00:00:00",author="彭登浩")
@Repository("postDao")
public class PostDaoImpl extends GenericDaoAbs<PostEntity, Long> implements PostDaoInter {
	/**
	 * 通过SQL查询菜单 DataTable 数据	
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String whereStr = null;
		String leafStr = "true";
//		Integer action = map.getvalAsInt("action");
//		if(null == action){
//			whereStr = " and  P.isenabled <> "+SysConstant.OPTION_DEL+" ";
//		}else{
			whereStr = " and  P.isenabled = "+SysConstant.OPTION_ENABLED+" ";
			leafStr = "false";
//		}
		String hql = "select P.id, P.name as text," +
				"'0' as pid,'"+SysConstant.POST_ICONPATH+"' as icon," +
				" '"+leafStr+"' as leaf,'"+TreeUtil.BLANK_NULL+"' as checked, 2 as type" +
				" from PostEntity P where 1=1 "+whereStr;
		
		return find(hql, "id,text,pid,icon,leaf,checked,type");
	}
	
	/**
	 * 获取部门的职位
	 */
	@Override
	public <K, V> DataTable getPostList(SHashMap<K, V> map)throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String whereStr = null;
		String leafStr = "true";
		whereStr = " and  P.isenabled = "+SysConstant.OPTION_ENABLED+" ";
		String hql = "select P.id, P.name as text," +
				"'0' as pid,'"+SysConstant.POST_ICONPATH+"' as icon," +
				" '"+leafStr+"' as leaf,'"+TreeUtil.BLANK_NULL+"' as checked, 2 as type" +
				" from PostEntity P where 1=1 "+whereStr;
		
		return find(hql, "id,text,pid,icon,leaf,checked,type");
	}
}
