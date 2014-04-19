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
import com.cmw.dao.inter.sys.GvlistDaoInter;
import com.cmw.entity.sys.GvlistEntity;


/**
 * 基础数据  DAO实现类
 * @author 彭登浩
 * @date 2012-11-19T00:00:00
 */

@Description(remark="基础数据DAO实现类",createDate="2012-11-19T00:00:00",author="彭登浩")
@Repository("gvlistDao")
public class GvlistDaoImpl extends GenericDaoAbs<GvlistEntity, Long> implements GvlistDaoInter {
	/**
	 * 通过SQL查询菜单 DataTable 数据	
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String hql = "select concat('G',cast(G.id as string)) as id, concat(G.name,(case when G.isenabled=0 then '【已禁用】' else '' end)) as text," +
				"concat('R',cast(G.restypeId as string)) as pid,'"+SysConstant.ROLE_ICONPATH+"' as icon," +
				" 'false' as leaf,'false' as checked,2 as type,-1 as potype " +
		" from GvlistEntity G where 1=1";
		hql += " and  G.isenabled = "+SysConstant.OPTION_ENABLED+" ";
		
		return find(hql, "id,text,pid,icon,leaf,checked,type,potype");
	}

	@Override
	public <K, V> DataTable getDataSource(SHashMap<K, V> map)
			throws DaoException {
		try {
			map = SqlUtil.getSafeWhereMap(map);
			String hql = "select A.id,A.name,A.remark as others from GvlistEntity A where  A.isenabled = "+SysConstant.OPTION_ENABLED+" ";
			String restypeIds = map.getvalAsStr("restypeIds");
			if(StringHandler.isValidStr(restypeIds)){
				hql += " and A.restypeId in (select B.id from RestypeEntity B where  A.isenabled = "+SysConstant.OPTION_ENABLED
					+" and B.recode in (CAST("+restypeIds+" as string)) ) ";
			}
			return find(hql, "id,name,others");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public <K, V> DataTable getReportDt(SHashMap<K, V> map)
			throws DaoException {
		try {
			map = SqlUtil.getSafeWhereMap(map);
			String hql = "select A.id,A.name,A.restypeId from GvlistEntity A where  A.isenabled = "+SysConstant.OPTION_ENABLED+" ";
			String restypeIds = map.getvalAsStr("restypeIds");
			if(StringHandler.isValidStr(restypeIds)){
				hql += " and A.restypeId in ("+restypeIds+") ";
			}
			hql += " order by A.id ";
			return find(hql, "id,name,restypeId");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getResultList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("select A.id,A.isenabled,A.code,B.name as rname,A.name,")
				.append(" A.ename,A.jname ,A.twname,A.fname,A.koname,A.biconCls ")
				.append(" from ts_gvlist A inner join ts_restype B on A.restypeId = B.id ")
				.append(" where 1=1  ");
			
			Integer isenabled =  params.getvalAsInt("isenabled");
			if(StringHandler.isValidObj(isenabled)){//
				sqlSb.append(" and A.isenabled = "+isenabled+" ");
			}else{
				sqlSb.append(" and A.isenabled != "+SysConstant.OPTION_DEL+" ");
			}
			
			Long restypeId = params.getvalAsLng("restypeId");
			if(StringHandler.isValidObj(restypeId)){//
				sqlSb.append(" and A.restypeId in ("+restypeId+") ");
			}
			Long id = params.getvalAsLng("id");
			if(StringHandler.isValidObj(id)){//
				sqlSb.append(" and A.id in ("+id+") ");
			}
			
			String name = params.getvalAsStr("name");
			if(StringHandler.isValidStr(name)){
				sqlSb.append(" and A.name like '"+name+"%' ");
			}
			String cmns =null;
			cmns = "id,isenabled,code,rname,name,ename,jname,twname,fname,koname,biconCls";
			long totalCount = getTotalCountBySql(sqlSb.toString());
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
//			dt.setColumnNames(cmns);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
