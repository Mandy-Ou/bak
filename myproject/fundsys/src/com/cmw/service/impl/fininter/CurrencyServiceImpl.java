package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.CurrencyEntity;
import com.cmw.dao.inter.fininter.CurrencyDaoInter;
import com.cmw.service.inter.fininter.CurrencyService;


/**
 * 币别  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="币别业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("currencyService")
public class CurrencyServiceImpl extends AbsService<CurrencyEntity, Long> implements  CurrencyService {
	@Autowired
	private CurrencyDaoInter currencyDao;
	@Override
	public GenericDaoInter<CurrencyEntity, Long> getDao() {
		return currencyDao;
	}

}
