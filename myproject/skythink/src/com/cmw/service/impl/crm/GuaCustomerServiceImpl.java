package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.GuaCustomerEntity;
import com.cmw.dao.inter.crm.GuaCustomerDaoInter;
import com.cmw.service.inter.crm.GuaCustomerService;


/**
 * 第三方担保人  Service实现类
 * @author 李听
 * @date 2013-12-31T00:00:00
 */
@Description(remark="第三方担保人业务实现类",createDate="2013-12-31T00:00:00",author="李听")
@Service("guaCustomerService")
public class GuaCustomerServiceImpl extends AbsService<GuaCustomerEntity, Long> implements  GuaCustomerService {
	@Autowired
	private GuaCustomerDaoInter guaCustomerDao;
	@Override
	public GenericDaoInter<GuaCustomerEntity, Long> getDao() {
		return guaCustomerDao;
	}

}
