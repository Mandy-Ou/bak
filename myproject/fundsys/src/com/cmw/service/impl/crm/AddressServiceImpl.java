package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.crm.AddressEntity;
import com.cmw.dao.inter.crm.AddressDaoInter;
import com.cmw.service.inter.crm.AddressService;


/**
 * 客户住宅信息  Service实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="客户住宅信息业务实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Service("addressService")
public class AddressServiceImpl extends AbsService<AddressEntity, Long> implements  AddressService {
	@Autowired
	private AddressDaoInter addressDao;
	@Override
	public GenericDaoInter<AddressEntity, Long> getDao() {
		return addressDao;
	}

}
