package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.CityEntity;
import com.cmw.dao.inter.sys.CityDaoInter;
import com.cmw.service.inter.sys.CityService;


/**
 * 城市  Service实现类
 * @author 彭登浩
 * @date 2012-12-10T00:00:00
 */
@Description(remark="城市业务实现类",createDate="2012-12-10T00:00:00",author="彭登浩")
@Service("cityService")
public class CityServiceImpl extends AbsService<CityEntity, Long> implements  CityService {
	@Autowired
	private CityDaoInter cityDao;
	@Override
	public GenericDaoInter<CityEntity, Long> getDao() {
		return cityDao;
	}

}
