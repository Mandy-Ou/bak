package com.cmw.service.impl.funds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.entity.funds.BamountRecordsEntity;
import com.cmw.dao.inter.funds.BamountRecordsDaoInter;
import com.cmw.service.inter.funds.BamountRecordsService;


/**
 * 撤资付款记录  Service实现类
 * @author zsl
 * @date 2014-05-06T00:00:00
 */
@Description(remark="撤资付款记录业务实现类",createDate="2014-05-06T00:00:00",author="zsl")
@Service("bamountRecordsService")
public class BamountRecordsServiceImpl extends AbsService<BamountRecordsEntity, Long> implements  BamountRecordsService {
	@Autowired
	private BamountRecordsDaoInter bamountRecordsDao;
	@Override
	public GenericDaoInter<BamountRecordsEntity, Long> getDao() {
		return bamountRecordsDao;
	}

}
