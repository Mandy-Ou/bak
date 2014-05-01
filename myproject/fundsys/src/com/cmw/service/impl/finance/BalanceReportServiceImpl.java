package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.finance.BalanceReportDaoInter;
import com.cmw.service.inter.finance.BalanceReportService;


/**
 * 贷款余额汇总表  Service实现类
 * @author 程明卫
 * @date 2013-08-17T00:00:00
 */
@Description(remark="贷款余额汇总表业务实现类",createDate="2013-08-17T00:00:00",author="程明卫")
@Service("balanceReportService")
public class BalanceReportServiceImpl extends AbsService<Object, Long> implements  BalanceReportService {
	@Autowired
	private BalanceReportDaoInter balanceReportDao;
	@Override
	public GenericDaoInter<Object, Long> getDao() {
		return balanceReportDao;
	}

}
