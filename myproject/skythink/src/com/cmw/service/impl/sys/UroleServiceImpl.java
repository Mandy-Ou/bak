package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.UroleEntity;
import com.cmw.dao.inter.sys.UroleDaoInter;
import com.cmw.service.inter.sys.UroleService;


/**
 * 用户角色关联  Service实现类
 * @author chengmingwei
 * @date 2012-12-08T00:00:00
 */
@Description(remark="用户角色关联业务实现类",createDate="2012-12-08T00:00:00",author="chengmingwei")
@Service("uroleService")
public class UroleServiceImpl extends AbsService<UroleEntity, Long> implements  UroleService {
	@Autowired
	private UroleDaoInter uroleDao;
	@Override
	public GenericDaoInter<UroleEntity, Long> getDao() {
		return uroleDao;
	}

}
