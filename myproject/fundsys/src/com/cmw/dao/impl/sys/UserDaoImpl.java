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
import com.cmw.dao.inter.sys.UserDaoInter;
import com.cmw.entity.sys.UserEntity;


/**
 * 用户  DAO实现类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="用户DAO实现类",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Repository("userDao")
public class UserDaoImpl extends GenericDaoAbs<UserEntity, Long> implements UserDaoInter {
	/**
	 * 通过SQL查询菜单 DataTable 数据	
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String hql = "select concat('U',cast(A.userId as string)) as id, A.empName as text," +
				"concat('D',cast(A.indeptId as string)) as pid,'"+SysConstant.ROLE_ICONPATH+"' as icon," +
				" 'false' as leaf,'false' as checked,2 as type,-1 as potype " +
		" from UserEntity A where 1=1";
		hql += " and  A.isenabled = "+SysConstant.OPTION_ENABLED+" ";
		
		return find(hql, "id,text,pid,icon,leaf,checked,type,potype");
	}

	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getResultList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			params = SqlUtil.getSafeWhereMap(params);
			StringBuffer sqlSb = new StringBuffer();
			sqlSb.append("select A.userId,A.isenabled,A.empName,A.sex,A.userName,")
				.append(" A.dataLevel,C.name as postName,B.empName as leaderName,A.phone,A.tel, ")
				.append(" A.email,D.name indeptName,A.indeptId  from ts_user A left join ts_user B on A.leader = B.userId ")
				.append(" left join ts_post C on A.postId = C.id ")
				.append(" left join ts_Department D on A.indeptId = D.id ")
				.append(" where A.isenabled !='"+SysConstant.OPTION_DEL+"' ");
			Long indeptId = params.getvalAsLng("indeptId");
			if(StringHandler.isValidObj(indeptId)){//
				sqlSb.append(" and A.indeptId in ("+indeptId+") ");
			}
			
			String empName = params.getvalAsStr("empName");
			if(StringHandler.isValidStr(empName)){
				sqlSb.append(" and A.empName like '"+empName+"%' ");
			}
			
			Integer isenabled = params.getvalAsInt("isenabled");
			if(StringHandler.isValidObj(isenabled)){//
				sqlSb.append(" and A.isenabled = '"+isenabled+"' ");
			}
			Integer isLoan = params.getvalAsInt("isLoan");
			if(StringHandler.isValidObj(isenabled)){//
				sqlSb.append(" and C.isLoan = '"+isLoan+"' ");
			}
			String cmns =null;
			cmns = "userId,isenabled,empName,sex,userName,dataLevel,postName,leaderName,phone,tel,email,indeptName,indeptId";
			long totalCount = getTotalCountBySql(sqlSb.toString());
			sqlSb.append(" order by A.userId desc ");
			DataTable dt = findBySqlPage(sqlSb.toString(),cmns, offset, pageSize,totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	
}
