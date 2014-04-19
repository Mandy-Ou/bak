package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.MortgageEntity;
import com.cmw.dao.inter.finance.MortgageDaoInter;
import com.cmw.service.inter.finance.MortgageService;


/**
 * 抵押物  Service实现类
 * @author pdh
 * @date 2013-01-06T00:00:00
 */
@Description(remark="抵押物业务实现类",createDate="2013-01-06T00:00:00",author="pdh")
@Service("mortgageService")
public class MortgageServiceImpl extends AbsService<MortgageEntity, Long> implements  MortgageService {
	@Autowired
	private MortgageDaoInter mortgageDao;
	@Override
	public GenericDaoInter<MortgageEntity, Long> getDao() {
		return mortgageDao;
	}

}
