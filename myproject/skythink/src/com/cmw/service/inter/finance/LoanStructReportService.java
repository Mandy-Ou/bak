package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.LoanInvoceEntity;


/**
 * 贷款业务结构报表  Service接口
 * @author 程明卫
 * @date 2013-08-09T00:00:00
 */
@Description(remark="贷款业务结构报表业务接口",createDate="2013-08-09T00:00:00",author="程明卫")
public interface LoanStructReportService extends IService<LoanInvoceEntity, Long> {
}
