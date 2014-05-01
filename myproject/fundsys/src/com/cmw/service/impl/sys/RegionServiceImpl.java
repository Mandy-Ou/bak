package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.RegionEntity;
import com.cmw.dao.inter.sys.RegionDaoInter;
import com.cmw.service.inter.sys.RegionService;


/**
 * 地区  Service实现类
 * @author 彭登浩
 * @date 2012-12-10T00:00:00
 */
@Description(remark="地区业务实现类",createDate="2012-12-10T00:00:00",author="彭登浩")
@Service("regionService")
public class RegionServiceImpl extends AbsService<RegionEntity, Long> implements  RegionService {
	@Autowired
	private RegionDaoInter regionDao;
	@Override
	public GenericDaoInter<RegionEntity, Long> getDao() {
		return regionDao;
	}

}
