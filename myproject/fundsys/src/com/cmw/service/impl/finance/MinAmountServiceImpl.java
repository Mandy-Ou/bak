package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.MinAmountEntity;
import com.cmw.dao.inter.finance.MinAmountDaoInter;
import com.cmw.service.inter.finance.MinAmountService;


/**
 * 最低金额配置  Service实现类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="最低金额配置业务实现类",createDate="2013-02-28T00:00:00",author="程明卫")
@Service("minAmountService")
public class MinAmountServiceImpl extends AbsService<MinAmountEntity, Long> implements  MinAmountService {
	@Autowired
	private MinAmountDaoInter minAmountDao;
	@Override
	public GenericDaoInter<MinAmountEntity, Long> getDao() {
		return minAmountDao;
	}

}
