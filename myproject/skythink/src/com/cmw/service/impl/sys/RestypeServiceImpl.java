package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.RestypeDaoInter;
import com.cmw.entity.sys.RestypeEntity;
import com.cmw.service.inter.sys.RestypeService;


/**
 * 资源  Service实现类
 * @author 彭登浩
 * @date 2012-11-19T00:00:00
 */
@Description(remark="资源业务实现类",createDate="2012-11-19T00:00:00",author="彭登浩")
@Service("restypeService")
public class RestypeServiceImpl extends AbsService<RestypeEntity, Long> implements  RestypeService {
	@Autowired
	private RestypeDaoInter restypeDao;
	@Override
	public GenericDaoInter<RestypeEntity, Long> getDao() {
		return restypeDao;
	}

}
