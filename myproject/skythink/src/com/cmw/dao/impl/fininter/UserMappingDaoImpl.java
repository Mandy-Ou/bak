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
import com.cmw.dao.inter.fininter.UserMappingDaoInter;
import com.cmw.entity.fininter.UserMappingEntity;


/**
 * 用户帐号映射  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="用户帐号映射DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("userMappingDao")
public class UserMappingDaoImpl extends GenericDaoAbs<UserMappingEntity, Long> implements UserMappingDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("select A.id,A.userId,A.fuserName,A.fsman,")
			.append("A.forgcode,A.refId,B.userName,B.empName,A.createTime,A.isenabled, ")
			.append("(case A.userId when 0 then '0' else '1' end) as status ")
			.append(" from fs_UserMapping A left join ts_User B on A.userId=B.userId ")
			.append(" where  A.isenabled <> '"+SysConstant.OPTION_DEL+"' ");
			String whereStr = SqlUtil.buildWhereStr("A", params,false);
			if(StringHandler.isValidStr(whereStr)){
				sb.append(" "+whereStr);
			}
			long count = getTotalCountBySql(sb.toString());
			sb.append(" order by A.id desc ");
			String cmns = "id,userId,fuserName,fsman,forgcode,refId,userName,empName,createTime#yyyy-MM-dd HH:mm,isenabled,status";
			DataTable dt = findBySqlPage(sb.toString(), cmns, offset, pageSize);
			dt.setSize(count);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
