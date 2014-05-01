package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.OveralLogEntity;
import com.cmw.dao.inter.finance.OveralLogDaoInter;
import com.cmw.service.inter.finance.OveralLogService;


/**
 * 还款计划备份总表  Service实现类
 * @author 程明卫
 * @date 2014-01-07T00:00:00
 */
@Description(remark="还款计划备份总表业务实现类",createDate="2014-01-07T00:00:00",author="程明卫")
@Service("overalLogService")
public class OveralLogServiceImpl extends AbsService<OveralLogEntity, Long> implements  OveralLogService {
	@Autowired
	private OveralLogDaoInter overalLogDao;
	@Override
	public GenericDaoInter<OveralLogEntity, Long> getDao() {
		return overalLogDao;
	}

}
