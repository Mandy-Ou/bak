package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.RateEntity;
import com.cmw.dao.inter.finance.RateDaoInter;
import com.cmw.service.inter.finance.RateService;


/**
 * 利率  Service实现类
 * @author 彭登浩
 * @date 2012-12-06T00:00:00
 */
@Description(remark="利率业务实现类",createDate="2012-12-06T00:00:00",author="彭登浩")
@Service("rateService")
public class RateServiceImpl extends AbsService<RateEntity, Long> implements  RateService {
	@Autowired
	private RateDaoInter rateDao;
	@Override
	public GenericDaoInter<RateEntity, Long> getDao() {
		return rateDao;
	}

}
