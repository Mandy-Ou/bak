package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.CasualRecordsEntity;
import com.cmw.dao.inter.finance.CasualRecordsDaoInter;
import com.cmw.service.inter.finance.CasualRecordsService;


/**
 * 实收随借随还表  Service实现类
 * @author 程明卫
 * @date 2014-01-07T00:00:00
 */
@Description(remark="实收随借随还表业务实现类",createDate="2014-01-07T00:00:00",author="程明卫")
@Service("casualRecordsService")
public class CasualRecordsServiceImpl extends AbsService<CasualRecordsEntity, Long> implements  CasualRecordsService {
	@Autowired
	private CasualRecordsDaoInter casualRecordsDao;
	@Override
	public GenericDaoInter<CasualRecordsEntity, Long> getDao() {
		return casualRecordsDao;
	}

}
