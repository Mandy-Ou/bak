package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.ConsortEntity;
import com.cmw.dao.inter.crm.ConsortDaoInter;
import com.cmw.service.inter.crm.ConsortService;


/**
 * 个人客户配偶信息  Service实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人客户配偶信息业务实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Service("consortService")
public class ConsortServiceImpl extends AbsService<ConsortEntity, Long> implements  ConsortService {
	@Autowired
	private ConsortDaoInter consortDao;
	@Override
	public GenericDaoInter<ConsortEntity, Long> getDao() {
		return consortDao;
	}

}
