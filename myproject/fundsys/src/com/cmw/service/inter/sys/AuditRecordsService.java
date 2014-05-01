package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.entity.sys.AuditRecordsEntity;


/**
 * 审批记录表  Service接口
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批记录表业务接口",createDate="2012-12-26T00:00:00",author="程明卫")
public interface AuditRecordsService extends IService<AuditRecordsEntity, Long> {
	 public Long getStartor(String procId) throws ServiceException;
}
