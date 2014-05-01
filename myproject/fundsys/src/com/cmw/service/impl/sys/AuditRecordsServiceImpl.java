package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.AuditRecordsEntity;
import com.cmw.dao.inter.sys.AuditRecordsDaoInter;
import com.cmw.service.inter.sys.AuditRecordsService;


/**
 * 审批记录表  Service实现类
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批记录表业务实现类",createDate="2012-12-26T00:00:00",author="程明卫")
@Service("auditRecordsService")
public class AuditRecordsServiceImpl extends AbsService<AuditRecordsEntity, Long> implements  AuditRecordsService {
	@Autowired
	private AuditRecordsDaoInter auditRecordsDao;
	@Override
	public GenericDaoInter<AuditRecordsEntity, Long> getDao() {
		return auditRecordsDao;
	}

	@Override
	public Long getStartor(String procId) throws ServiceException {
		try {
			return auditRecordsDao.getStartor(procId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
