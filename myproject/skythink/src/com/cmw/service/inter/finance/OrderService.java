package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.OrderEntity;


/**
 * 扣款优先级  Service接口
 * @author pdt
 * @date 2012-12-22T00:00:00
 */
@Description(remark="扣款优先级业务接口",createDate="2012-12-22T00:00:00",author="pdt")
public interface OrderService extends IService<OrderEntity, Long> {
}
