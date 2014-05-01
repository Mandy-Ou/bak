package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.WorkEntity;
import com.cmw.dao.inter.crm.WorkDaoInter;
import com.cmw.service.inter.crm.WorkService;


/**
 * 职业信息  Service实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="职业信息业务实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Service("workService")
public class WorkServiceImpl extends AbsService<WorkEntity, Long> implements  WorkService {
	@Autowired
	private WorkDaoInter workDao;
	@Override
	public GenericDaoInter<WorkEntity, Long> getDao() {
		return workDao;
	}

}
