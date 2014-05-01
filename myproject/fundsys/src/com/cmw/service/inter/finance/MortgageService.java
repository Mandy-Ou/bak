package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.MortgageEntity;


/**
 * 抵押物  Service接口
 * @author pdh
 * @date 2013-01-06T00:00:00
 */
@Description(remark="抵押物业务接口",createDate="2013-01-06T00:00:00",author="pdh")
public interface MortgageService extends IService<MortgageEntity, Long> {
}
