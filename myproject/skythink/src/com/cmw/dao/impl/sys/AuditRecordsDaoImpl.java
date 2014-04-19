package com.cmw.dao.impl.sys;


import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.dao.inter.sys.AuditRecordsDaoInter;


/**
 * 审批记录表  DAO实现类
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批记录表DAO实现类",createDate="2012-12-26T00:00:00",author="程明卫")
@Repository("auditRecordsDao")
public class AuditRecordsDaoImpl extends GenericDaoAbs<AuditRecordsEntity, Long> implements AuditRecordsDaoInter {

	@Override
	public <K, V> List<AuditRecordsEntity> getEntityList(SHashMap<K, V> map)
			throws DaoException {
		try {
			String whereStr = SqlUtil.buildWhereStr("A", map);
			if(StringHandler.isValidStr(whereStr)){
				whereStr += " and A.isenabled = '"+SysConstant.OPTION_ENABLED+"' order by A.id ";
			}
			List<AuditRecordsEntity> list = getList("from AuditRecordsEntity A "+whereStr);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	 /**
	  * 获取流程发起人ID
	  * @return
	  * @throws DaoException
	  */
	@SuppressWarnings("unchecked")
	@Override
	 public Long getStartor(String procId) throws DaoException{
		 String hql = "select A.creator from AuditRecordsEntity A where A.isenabled="+SysConstant.OPTION_ENABLED+" " +
		 		" and A.procId='"+procId+"' order by A.id ";
		try{
			List<Long> list = getSession().createQuery(hql).list();
			 return (null == list || list.size() == 0) ? null : list.get(0);
		}catch (DataAccessException e) {
			throw new DaoException(e.getMessage());
		}
	 }
}
