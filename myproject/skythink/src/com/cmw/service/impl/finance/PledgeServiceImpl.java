package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.finance.PledgeDaoInter;
import com.cmw.entity.finance.PledgeEntity;
import com.cmw.service.inter.finance.PledgeService;


/**
 * 质押物  Service实现类
 * @author pdh
 * @date 2013-01-08T00:00:00
 */
@Description(remark="质押物业务实现类",createDate="2013-01-08T00:00:00",author="pdh")
@Service("pledgeService")
public class PledgeServiceImpl extends AbsService<PledgeEntity, Long> implements  PledgeService {
	@Autowired
	private PledgeDaoInter pledgeDao;
	@Override
	public GenericDaoInter<PledgeEntity, Long> getDao() {
		return pledgeDao;
	}

}
