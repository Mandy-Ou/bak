package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.finance.LoanStructReportDaoInter;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.service.inter.finance.LoanStructReportService;


/**
 * 贷款业务结构报表  Service实现类
 * @author 程明卫
 * @date 2013-08-09T00:00:00
 */
@Description(remark="贷款业务结构报表业务实现类",createDate="2013-08-09T00:00:00",author="程明卫")
@Service("loanStructReportService")
public class LoanStructReportServiceImpl extends AbsService<LoanInvoceEntity, Long> implements  LoanStructReportService {
	@Autowired
	private LoanStructReportDaoInter loanStructReportDao;
	@Override
	public GenericDaoInter<LoanInvoceEntity, Long> getDao() {
		return loanStructReportDao;
	}

}
