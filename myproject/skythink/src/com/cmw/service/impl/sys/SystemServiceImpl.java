package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.SystemDaoInter;
import com.cmw.entity.sys.SystemEntity;
import com.cmw.service.inter.sys.SystemService;


/**
 * 系统  Service实现类
 * @author chengmingwei
 * @date 2012-10-28T00:00:00
 */
@Description(remark="系统业务实现类",createDate="2012-10-28T00:00:00",author="chengmingwei")
@Service("systemService")
public class SystemServiceImpl extends AbsService<SystemEntity, Long> implements  SystemService {
	@Autowired
	private SystemDaoInter systemDao;
	@Override
	public GenericDaoInter<SystemEntity, Long> getDao() {
		return systemDao;
	}

}
