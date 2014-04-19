package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.dao.inter.sys.DepartmentDaoInter;
import com.cmw.service.inter.sys.DepartmentService;


/**
 * 部门  Service实现类
 * @author 彭登浩
 * @date 2012-11-09T00:00:00
 */
@Description(remark="部门业务实现类",createDate="2012-11-09T00:00:00",author="彭登浩")
@Service("departmentService")
public class DepartmentServiceImpl extends AbsService<DepartmentEntity, Long> implements  DepartmentService {
	@Autowired
	private DepartmentDaoInter departmentDao;
	@Override
	public GenericDaoInter<DepartmentEntity, Long> getDao() {
		return departmentDao;
	}

}
