package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.LoanInvoceEntity;


/**
 * 贷款发放利率报表  Service接口
 * @author 程明卫
 * @date 2013-08-05 20:10:00
 */
@Description(remark="贷款发放利率报表接口",createDate="2013-08-05 20:10:00",author="程明卫")
public interface LoanRateReportService extends IService<LoanInvoceEntity, Long> {
	
}
