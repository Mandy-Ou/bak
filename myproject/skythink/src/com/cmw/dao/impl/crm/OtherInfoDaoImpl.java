package com.cmw.dao.impl.crm;


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
import com.cmw.entity.crm.OtherInfoEntity;
import com.cmw.dao.inter.crm.OtherInfoDaoInter;


/**
 * 其它信息  DAO实现类
 * @author pdh
 * @date 2013-03-31T00:00:00
 */
@Description(remark="其它信息DAO实现类",createDate="2013-03-31T00:00:00",author="pdh")
@Repository("otherInfoDao")
public class OtherInfoDaoImpl extends GenericDaoAbs<OtherInfoEntity, Long> implements OtherInfoDaoInter {

	/* (non-Javadoc)
	 * @see com.cmw.core.base.dao.GenericDaoAbs#getResultList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		
		try{
			Integer custType = params.getvalAsInt("custType");
			Long customerId = params.getvalAsLng("customerId");
			String hql = "select A.id,A.customerId,A.otherName,B.empName as creator,A.createTime,A.modifytime,A.remark from OtherInfoEntity A ,UserEntity B " +
					"where A.creator = B.userId and A.isenabled != '"+SysConstant.OPTION_DEL+"' and A.custType = '"+custType+"' and A.customerId = '"+customerId+"' ";
			
			DataTable dt = findByPage(hql,"id,customerId,otherName,creator,createTime#yyyy-MM-dd,modifytime#yyyy-MM-dd,remark",offset,pageSize);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
}
