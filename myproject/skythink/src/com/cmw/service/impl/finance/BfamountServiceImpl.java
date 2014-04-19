package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.BfamountEntity;
import com.cmw.dao.inter.finance.BfamountDaoInter;
import com.cmw.service.inter.finance.BfamountService;


/**
 * 预收帐款  Service实现类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="预收帐款业务实现类",createDate="2013-02-28T00:00:00",author="程明卫")
@Service("bfamountService")
public class BfamountServiceImpl extends AbsService<BfamountEntity, Long> implements  BfamountService {
	@Autowired
	private BfamountDaoInter bfamountDao;
	@Override
	public GenericDaoInter<BfamountEntity, Long> getDao() {
		return bfamountDao;
	}

}
