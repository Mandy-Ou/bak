package com.cmw.dao.impl.fininter;


import java.util.HashMap;

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
import com.cmw.dao.inter.fininter.BussFinCfgDaoInter;
import com.cmw.entity.fininter.BussFinCfgEntity;


/**
 * 业务财务映射  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="业务财务映射DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("bussFinCfgDao")
public class BussFinCfgDaoImpl extends GenericDaoAbs<BussFinCfgEntity, Long> implements BussFinCfgDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,int pageSize) throws DaoException {
		StringBuffer sb = new StringBuffer();
		sb.append(" select A.id,B.name as systemName ,C.name as finName,A.createTime,D.empName as creator,A.isenabled,A.remark,A.sysId,A.finsysId ")
		 .append(" from fs_BussFinCfg A,ts_system B,fs_FinSysCfg C,ts_User D ")
		 .append(" where A.sysId=B.id and A.finsysId=C.id and A.creator=D.userId ")
		 .append(" and A.isenabled != '"+SysConstant.OPTION_DEL+"' ");
		try{
			String whereStr = SqlUtil.buildWhereStr("A", params, false);
			if(StringHandler.isValidStr(whereStr)){
				sb.append(whereStr);
			}
			long count = getTotalCountBySql(sb.toString());
			sb.append(" order by A.isenabled, A.id desc ");
			DataTable dt = findBySqlPage(sb.toString(), offset, pageSize);
			dt.setColumnNames("id,systemName,finName,createTime#yyyy-MM-dd,creator,isenabled,remark,sysId,finsysId");
			dt.setSize(count);
			return dt;
		}catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public <K, V> DataTable getResultList() throws DaoException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		return getResultList(params,-1,-1);
	}
	
}
