package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.FreeRecordsEntity;
import com.cmw.dao.inter.finance.FreeRecordsDaoInter;
import com.cmw.service.inter.finance.FreeRecordsService;


/**
 * 实收手续费  Service实现类
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="实收手续费业务实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Service("freeRecordsService")
public class FreeRecordsServiceImpl extends AbsService<FreeRecordsEntity, Long> implements  FreeRecordsService {
	@Autowired
	private FreeRecordsDaoInter freeRecordsDao;
	@Override
	public GenericDaoInter<FreeRecordsEntity, Long> getDao() {
		return freeRecordsDao;
	}

}
