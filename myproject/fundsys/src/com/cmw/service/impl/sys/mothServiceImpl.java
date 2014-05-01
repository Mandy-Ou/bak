package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.mothEntity;
import com.cmw.dao.inter.sys.mothDaoInter;
import com.cmw.service.inter.sys.mothService;


/**
 * 月份  Service实现类
 * @author 彭登涛
 * @date 2013-02-25T00:00:00
 */
@Description(remark="月份业务实现类",createDate="2013-02-25T00:00:00",author="彭登涛")
@Service("mothService")
public class mothServiceImpl extends AbsService<mothEntity, Long> implements  mothService {
	@Autowired
	private mothDaoInter mothDao;
	@Override
	public GenericDaoInter<mothEntity, Long> getDao() {
		return mothDao;
	}

}
