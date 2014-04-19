package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.EassureEntity;
import com.cmw.dao.inter.crm.EassureDaoInter;
import com.cmw.service.inter.crm.EassureService;


/**
 * 企业担保  Service实现类
 * @author pdt
 * @date 2012-12-24T00:00:00
 */
@Description(remark="企业担保业务实现类",createDate="2012-12-24T00:00:00",author="pdt")
@Service("eassureService")
public class EassureServiceImpl extends AbsService<EassureEntity, Long> implements  EassureService {
	@Autowired
	private EassureDaoInter eassureDao;
	@Override
	public GenericDaoInter<EassureEntity, Long> getDao() {
		return eassureDao;
	}

}
