package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.ExtPlanEntity;


/**
 * 展期计划  Service接口
 * @author pdh
 * @date 2013-09-23T00:00:00
 */
@Description(remark="展期计划业务接口",createDate="2013-09-23T00:00:00",author="pdh")
public interface ExtPlanService extends IService<ExtPlanEntity, Long> {
}
