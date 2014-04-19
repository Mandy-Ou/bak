package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.ProvinceEntity;
import com.cmw.dao.inter.sys.ProvinceDaoInter;
import com.cmw.service.inter.sys.ProvinceService;


/**
 * 省份  Service实现类
 * @author 彭登浩
 * @date 2012-12-10T00:00:00
 */
@Description(remark="省份业务实现类",createDate="2012-12-10T00:00:00",author="彭登浩")
@Service("provinceService")
public class ProvinceServiceImpl extends AbsService<ProvinceEntity, Long> implements  ProvinceService {
	@Autowired
	private ProvinceDaoInter provinceDao;
	@Override
	public GenericDaoInter<ProvinceEntity, Long> getDao() {
		return provinceDao;
	}

}
