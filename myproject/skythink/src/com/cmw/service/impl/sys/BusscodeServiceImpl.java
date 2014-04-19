package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.BusscodeDaoInter;
import com.cmw.entity.sys.BusscodeEntity;
import com.cmw.service.inter.sys.BusscodeService;


/**
 * 业务编号配置  Service实现类
 * @author 彭登浩
 * @date 2012-11-21T00:00:00
 */
@Description(remark="业务编号配置业务实现类",createDate="2012-11-21T00:00:00",author="彭登浩")
@Service("busscodeService")
public class BusscodeServiceImpl extends AbsService<BusscodeEntity, Long> implements  BusscodeService {
	@Autowired
	private BusscodeDaoInter busscodeDao;
	@Override
	public GenericDaoInter<BusscodeEntity, Long> getDao() {
		return busscodeDao;
	}

}
