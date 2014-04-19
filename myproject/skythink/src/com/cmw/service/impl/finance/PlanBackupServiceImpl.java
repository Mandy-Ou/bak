package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.PlanBackupEntity;
import com.cmw.dao.inter.finance.PlanBackupDaoInter;
import com.cmw.service.inter.finance.PlanBackupService;


/**
 * 还款计划备份表  Service实现类
 * @author 程明卫
 * @date 2014-01-07T00:00:00
 */
@Description(remark="还款计划备份表业务实现类",createDate="2014-01-07T00:00:00",author="程明卫")
@Service("planBackupService")
public class PlanBackupServiceImpl extends AbsService<PlanBackupEntity, Long> implements  PlanBackupService {
	@Autowired
	private PlanBackupDaoInter planBackupDao;
	@Override
	public GenericDaoInter<PlanBackupEntity, Long> getDao() {
		return planBackupDao;
	}

}
