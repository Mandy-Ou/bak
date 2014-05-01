package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.RateEntity;


/**
 * 利率  Service接口
 * @author 彭登浩
 * @date 2012-12-06T00:00:00
 */
@Description(remark="利率业务接口",createDate="2012-12-06T00:00:00",author="彭登浩")
public interface RateService extends IService<RateEntity, Long> {
}
