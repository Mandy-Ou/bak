package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.UserModEntity;
import com.cmw.dao.inter.sys.UserModDaoInter;
import com.cmw.service.inter.sys.UserModService;


/**
 * 用户模块配置  Service实现类
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="用户模块配置业务实现类",createDate="2013-03-08T00:00:00",author="程明卫")
@Service("userModService")
public class UserModServiceImpl extends AbsService<UserModEntity, Long> implements  UserModService {
	@Autowired
	private UserModDaoInter userModDao;
	@Override
	public GenericDaoInter<UserModEntity, Long> getDao() {
		return userModDao;
	}

}
