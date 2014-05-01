package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.ContactorEntity;
import com.cmw.dao.inter.crm.ContactorDaoInter;
import com.cmw.service.inter.crm.ContactorService;


/**
 * 联系人资料  Service实现类
 * @author pdh
 * @date 2012-12-18T00:00:00
 */
@Description(remark="联系人资料业务实现类",createDate="2012-12-18T00:00:00",author="pdh")
@Service("contactorService")
public class ContactorServiceImpl extends AbsService<ContactorEntity, Long> implements  ContactorService {
	@Autowired
	private ContactorDaoInter contactorDao;
	@Override
	public GenericDaoInter<ContactorEntity, Long> getDao() {
		return contactorDao;
	}

}
