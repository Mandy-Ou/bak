package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.EstateEntity;
import com.cmw.dao.inter.crm.EstateDaoInter;
import com.cmw.service.inter.crm.EstateService;


/**
 * 房产物业信息  Service实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="房产物业信息业务实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Service("estateService")
public class EstateServiceImpl extends AbsService<EstateEntity, Long> implements  EstateService {
	@Autowired
	private EstateDaoInter estateDao;
	@Override
	public GenericDaoInter<EstateEntity, Long> getDao() {
		return estateDao;
	}

}
