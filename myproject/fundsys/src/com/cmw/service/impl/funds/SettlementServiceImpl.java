package com.cmw.service.impl.funds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.funds.SettlementEntity;
import com.cmw.dao.inter.funds.SettlementDaoInter;
import com.cmw.service.inter.funds.SettlementService;


/**
 * 汇票结算单表  Service实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票结算单表业务实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Service("settlementService")
public class SettlementServiceImpl extends AbsService<SettlementEntity, Long> implements  SettlementService {
	@Autowired
	private SettlementDaoInter settlementDao;
	@Override
	public GenericDaoInter<SettlementEntity, Long> getDao() {
		return settlementDao;
	}

}
