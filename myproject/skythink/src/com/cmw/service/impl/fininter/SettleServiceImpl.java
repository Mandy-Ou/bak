package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.SettleEntity;
import com.cmw.dao.inter.fininter.SettleDaoInter;
import com.cmw.service.inter.fininter.SettleService;


/**
 * 结算方式  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="结算方式业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("settleService")
public class SettleServiceImpl extends AbsService<SettleEntity, Long> implements  SettleService {
	@Autowired
	private SettleDaoInter settleDao;
	@Override
	public GenericDaoInter<SettleEntity, Long> getDao() {
		return settleDao;
	}

}
