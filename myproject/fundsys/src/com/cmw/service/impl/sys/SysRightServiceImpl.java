package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.SysRightEntity;
import com.cmw.dao.inter.sys.SysRightDaoInter;
import com.cmw.service.inter.sys.SysRightService;


/**
 * 系统权限  Service实现类
 * @author 程明卫
 * @date 2012-12-28T00:00:00
 */
@Description(remark="系统权限业务实现类",createDate="2012-12-28T00:00:00",author="程明卫")
@Service("sysRightService")
public class SysRightServiceImpl extends AbsService<SysRightEntity, Long> implements  SysRightService {
	@Autowired
	private SysRightDaoInter sysRightDao;
	@Override
	public GenericDaoInter<SysRightEntity, Long> getDao() {
		return sysRightDao;
	}

}
