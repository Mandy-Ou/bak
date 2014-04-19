package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.finance.PrepaymentRecordsDaoInter;
import com.cmw.entity.finance.PrepaymentRecordsEntity;
import com.cmw.service.inter.finance.PrepaymentRecordsService;


/**
 * 实收提前还款金额  Service实现类
 * @author 程明卫
 * @date 2013-11-03T00:00:00
 */
@Description(remark="实收金额业务实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Service("prepaymentRecordsService")
public class PrepaymentRecordsServiceImpl extends AbsService<PrepaymentRecordsEntity, Long> implements  PrepaymentRecordsService {
	@Autowired
	private PrepaymentRecordsDaoInter prepaymentRecordsDao;
	@Override
	public GenericDaoInter<PrepaymentRecordsEntity, Long> getDao() {
		return prepaymentRecordsDao;
	}

}
