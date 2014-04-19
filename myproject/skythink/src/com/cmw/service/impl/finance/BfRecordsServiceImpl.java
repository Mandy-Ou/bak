package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.BfRecordsEntity;
import com.cmw.dao.inter.finance.BfRecordsDaoInter;
import com.cmw.service.inter.finance.BfRecordsService;


/**
 * 预收帐款实收记录  Service实现类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="预收帐款实收记录业务实现类",createDate="2013-02-28T00:00:00",author="程明卫")
@Service("bfRecordsService")
public class BfRecordsServiceImpl extends AbsService<BfRecordsEntity, Long> implements  BfRecordsService {
	@Autowired
	private BfRecordsDaoInter bfRecordsDao;
	@Override
	public GenericDaoInter<BfRecordsEntity, Long> getDao() {
		return bfRecordsDao;
	}

}
