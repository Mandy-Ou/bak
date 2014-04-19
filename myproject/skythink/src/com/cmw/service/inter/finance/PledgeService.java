package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.PledgeEntity;


/**
 * 质押物  Service接口
 * @author pdh
 * @date 2013-01-08T00:00:00
 */
@Description(remark="质押物业务接口",createDate="2013-01-08T00:00:00",author="pdh")
public interface PledgeService extends IService<PledgeEntity, Long> {
}
