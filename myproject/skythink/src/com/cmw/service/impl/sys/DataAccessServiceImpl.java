package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.DataAccessEntity;
import com.cmw.dao.inter.sys.DataAccessDaoInter;
import com.cmw.service.inter.sys.DataAccessService;


/**
 * 数据访问权限  Service实现类
 * @author 程明卫
 * @date 2012-12-28T00:00:00
 */
@Description(remark="数据访问权限业务实现类",createDate="2012-12-28T00:00:00",author="程明卫")
@Service("dataAccessService")
public class DataAccessServiceImpl extends AbsService<DataAccessEntity, Long> implements  DataAccessService {
	@Autowired
	private DataAccessDaoInter dataAccessDao;
	@Override
	public GenericDaoInter<DataAccessEntity, Long> getDao() {
		return dataAccessDao;
	}

}
