package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.FormdiyDaoInter;
import com.cmw.entity.sys.FormdiyEntity;
import com.cmw.service.inter.sys.FormdiyService;


/**
 * 表单DIY表  Service实现类
 * @author pengdenghao
 * @date 2012-11-23T00:00:00
 */
@Description(remark="表单DIY表业务实现类",createDate="2012-11-23T00:00:00",author="pengdenghao")
@Service("formdiyService")
public class FormdiyServiceImpl extends AbsService<FormdiyEntity, Long> implements  FormdiyService {
	@Autowired
	private FormdiyDaoInter formdiyDao;
	@Override
	public GenericDaoInter<FormdiyEntity, Long> getDao() {
		return formdiyDao;
	}

}
