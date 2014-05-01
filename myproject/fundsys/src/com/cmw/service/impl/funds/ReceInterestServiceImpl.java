package com.cmw.service.impl.funds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.funds.ReceInterestDaoInter;
import com.cmw.entity.funds.ReceInterestEntity;
import com.cmw.service.inter.funds.ReceInterestService;


/**
 * 汇票利润提成记实现类
 * @author 彭登浩
 * @date 2014-01-15T00:00:00
 */
@Description(remark="汇票利润提成记实现类",createDate="2014-01-15T00:00:00",author="彭登浩")
@Service("receInterestService")
public class ReceInterestServiceImpl extends AbsService<ReceInterestEntity, Long> implements  ReceInterestService {
	@Autowired
	private ReceInterestDaoInter receInterestDao;
	@Override
	public GenericDaoInter<ReceInterestEntity, Long> getDao() {
		return receInterestDao;
	}
}
