package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.CountryEntity;
import com.cmw.dao.inter.sys.CountryDaoInter;
import com.cmw.service.inter.sys.CountryService;


/**
 * 国家  Service实现类
 * @author 彭登浩
 * @date 2012-12-10T00:00:00
 */
@Description(remark="国家业务实现类",createDate="2012-12-10T00:00:00",author="彭登浩")
@Service("countryService")
public class CountryServiceImpl extends AbsService<CountryEntity, Long> implements  CountryService {
	@Autowired
	private CountryDaoInter countryDao;
	@Override
	public GenericDaoInter<CountryEntity, Long> getDao() {
		return countryDao;
	}

}
