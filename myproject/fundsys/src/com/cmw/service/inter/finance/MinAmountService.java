package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.MinAmountEntity;


/**
 * 最低金额配置  Service接口
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="最低金额配置业务接口",createDate="2013-02-28T00:00:00",author="程明卫")
public interface MinAmountService extends IService<MinAmountEntity, Long> {
}
