package com.cmw.dao.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;

import com.cmw.entity.sys.AuditRecordsEntity;


/**
 * 审批记录表  DAO接口
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
 @Description(remark="审批记录表Dao接口",createDate="2012-12-26T00:00:00",author="程明卫")
public interface AuditRecordsDaoInter  extends GenericDaoInter<AuditRecordsEntity, Long>{
	 Long getStartor(String procId) throws DaoException;
}
