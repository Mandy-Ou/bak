package com.cmw.service.impl.funds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.funds.RbrelationEntity;
import com.cmw.dao.inter.funds.RbrelationDaoInter;
import com.cmw.service.inter.funds.RbrelationService;


/**
 * 收条-汇票承诺书关联表  Service实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="收条-汇票承诺书关联表业务实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Service("rbrelationService")
public class RbrelationServiceImpl extends AbsService<RbrelationEntity, Long> implements  RbrelationService {
	@Autowired
	private RbrelationDaoInter rbrelationDao;
	@Override
	public GenericDaoInter<RbrelationEntity, Long> getDao() {
		return rbrelationDao;
	}

}
