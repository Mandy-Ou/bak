package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.ExtPlanEntity;
import com.cmw.dao.inter.finance.ExtPlanDaoInter;
import com.cmw.service.inter.finance.ExtPlanService;


/**
 * 展期计划  Service实现类
 * @author pdh
 * @date 2013-09-23T00:00:00
 */
@Description(remark="展期计划业务实现类",createDate="2013-09-23T00:00:00",author="pdh")
@Service("extPlanService")
public class ExtPlanServiceImpl extends AbsService<ExtPlanEntity, Long> implements  ExtPlanService {
	@Autowired
	private ExtPlanDaoInter extPlanDao;
	@Override
	public GenericDaoInter<ExtPlanEntity, Long> getDao() {
		return extPlanDao;
	}

}
