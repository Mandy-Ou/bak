package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.OwnFundsEntity;


/**
 * 自有资金  Service接口
 * @author pdh
 * @date 2013-08-13T00:00:00
 */
@Description(remark="自有资金业务接口",createDate="2013-08-13T00:00:00",author="pdh")
public interface OwnFundsService extends IService<OwnFundsEntity, Long> {
}
