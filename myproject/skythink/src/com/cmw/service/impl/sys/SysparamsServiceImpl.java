package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.SysparamsDaoInter;
import com.cmw.entity.sys.SysparamsEntity;
import com.cmw.service.inter.sys.SysparamsService;


/**
 * 系统参数  Service实现类
 * @author pengdenghao
 * @date 2012-11-28T00:00:00
 */
@Description(remark="系统参数业务实现类",createDate="2012-11-28T00:00:00",author="pengdenghao")
@Service("sysparamsService")
public class SysparamsServiceImpl extends AbsService<SysparamsEntity, Long> implements  SysparamsService {
	@Autowired
	private SysparamsDaoInter sysparamsDao;
	@Override
	public GenericDaoInter<SysparamsEntity, Long> getDao() {
		return sysparamsDao;
	}
	
	
}
