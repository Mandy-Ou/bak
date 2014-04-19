package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.PlanBackupEntity;


/**
 * 还款计划备份表  Service接口
 * @author 程明卫
 * @date 2014-01-07T00:00:00
 */
@Description(remark="还款计划备份表业务接口",createDate="2014-01-07T00:00:00",author="程明卫")
public interface PlanBackupService extends IService<PlanBackupEntity, Long> {
}
