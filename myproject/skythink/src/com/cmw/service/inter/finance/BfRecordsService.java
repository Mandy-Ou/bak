package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.BfRecordsEntity;


/**
 * 预收帐款实收记录  Service接口
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="预收帐款实收记录业务接口",createDate="2013-02-28T00:00:00",author="程明卫")
public interface BfRecordsService extends IService<BfRecordsEntity, Long> {
}
