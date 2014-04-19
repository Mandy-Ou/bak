package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.EclassEntity;
import com.cmw.dao.inter.crm.EclassDaoInter;
import com.cmw.service.inter.crm.EclassService;


/**
 * 企业领导班子  Service实现类
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="企业领导班子业务实现类",createDate="2012-12-26T00:00:00",author="pdh")
@Service("eclassService")
public class EclassServiceImpl extends AbsService<EclassEntity, Long> implements  EclassService {
	@Autowired
	private EclassDaoInter eclassDao;
	@Override
	public GenericDaoInter<EclassEntity, Long> getDao() {
		return eclassDao;
	}

}
