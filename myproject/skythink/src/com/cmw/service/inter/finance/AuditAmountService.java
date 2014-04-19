package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.AuditAmountEntity;


/**
 * 审批金额建议  Service接口
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批金额建议业务接口",createDate="2012-12-26T00:00:00",author="程明卫")
public interface AuditAmountService extends IService<AuditAmountEntity, Long> {
}
