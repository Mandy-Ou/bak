package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.ModuleDaoInter;
import com.cmw.entity.sys.ModuleEntity;
import com.cmw.service.inter.sys.ModuleService;


/**
 * 模块  Service实现类
 * @author chengmingwei
 * @date 2012-10-31T00:00:00
 */
@Description(remark="模块业务实现类",createDate="2012-10-31T00:00:00",author="chengmingwei")
@Service("moduleService")
public class ModuleServiceImpl extends AbsService<ModuleEntity, Long> implements  ModuleService {
	@Autowired
	private ModuleDaoInter moduleDao;
	@Override
	public GenericDaoInter<ModuleEntity, Long> getDao() {
		return moduleDao;
	}

}
