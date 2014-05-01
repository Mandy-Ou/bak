package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.EbankEntity;
import com.cmw.dao.inter.crm.EbankDaoInter;
import com.cmw.service.inter.crm.EbankService;


/**
 * 企业开户  Service实现类
 * @author pdh
 * @date 2012-12-25T00:00:00
 */
@Description(remark="企业开户业务实现类",createDate="2012-12-25T00:00:00",author="pdh")
@Service("ebankService")
public class EbankServiceImpl extends AbsService<EbankEntity, Long> implements  EbankService {
	@Autowired
	private EbankDaoInter ebankDao;
	@Override
	public GenericDaoInter<EbankEntity, Long> getDao() {
		return ebankDao;
	}

}
