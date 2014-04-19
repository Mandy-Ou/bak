package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.RoleModEntity;
import com.cmw.dao.inter.sys.RoleModDaoInter;
import com.cmw.service.inter.sys.RoleModService;


/**
 * 角色模块配置  Service实现类
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="角色模块配置业务实现类",createDate="2013-03-08T00:00:00",author="程明卫")
@Service("roleModService")
public class RoleModServiceImpl extends AbsService<RoleModEntity, Long> implements  RoleModService {
	@Autowired
	private RoleModDaoInter roleModDao;
	@Override
	public GenericDaoInter<RoleModEntity, Long> getDao() {
		return roleModDao;
	}

}
