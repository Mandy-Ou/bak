package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.CasualRecordsEntity;


/**
 * 实收随借随还表  Service接口
 * @author 程明卫
 * @date 2014-01-07T00:00:00
 */
@Description(remark="实收随借随还表业务接口",createDate="2014-01-07T00:00:00",author="程明卫")
public interface CasualRecordsService extends IService<CasualRecordsEntity, Long> {
}
