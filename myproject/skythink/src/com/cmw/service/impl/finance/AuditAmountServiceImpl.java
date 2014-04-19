package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.AuditAmountEntity;
import com.cmw.dao.inter.finance.AuditAmountDaoInter;
import com.cmw.service.inter.finance.AuditAmountService;


/**
 * 审批金额建议  Service实现类
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批金额建议业务实现类",createDate="2012-12-26T00:00:00",author="程明卫")
@Service("auditAmountService")
public class AuditAmountServiceImpl extends AbsService<AuditAmountEntity, Long> implements  AuditAmountService {
	@Autowired
	private AuditAmountDaoInter auditAmountDao;
	@Override
	public GenericDaoInter<AuditAmountEntity, Long> getDao() {
		return auditAmountDao;
	}

}
