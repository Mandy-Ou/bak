package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.finance.LoanSortReportDaoInter;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.service.inter.finance.LoanSortReportService;


/**
 * 贷款发放分类报表  Service实现类
 * @author 程明卫
 * @date 2013-08-08T00:00:00
 */
@Description(remark="贷款发放分类报表业务实现类",createDate="2013-08-08T00:00:00",author="程明卫")
@Service("loanSortReportService")
public class LoanSortReportServiceImpl extends AbsService<LoanInvoceEntity, Long> implements  LoanSortReportService {
	@Autowired
	private LoanSortReportDaoInter loanSortReportDao;
	@Override
	public GenericDaoInter<LoanInvoceEntity, Long> getDao() {
		return loanSortReportDao;
	}

}
