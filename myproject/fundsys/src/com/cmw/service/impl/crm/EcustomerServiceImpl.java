package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.dao.inter.crm.EcustomerDaoInter;
import com.cmw.service.inter.crm.EcustomerService;


/**
 * 企业客户基本信息  Service实现类
 * @author pdh
 * @date 2012-12-21T00:00:00
 */
@Description(remark="企业客户基本信息业务实现类",createDate="2012-12-21T00:00:00",author="pdh")
@Service("ecustomerService")
public class EcustomerServiceImpl extends AbsService<EcustomerEntity, Long> implements  EcustomerService {
	@Autowired
	private EcustomerDaoInter ecustomerDao;
	@Override
	public GenericDaoInter<EcustomerEntity, Long> getDao() {
		return ecustomerDao;
	}

}
