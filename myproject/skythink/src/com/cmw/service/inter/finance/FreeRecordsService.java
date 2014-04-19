package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.FreeRecordsEntity;


/**
 * 实收手续费  Service接口
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="实收手续费业务接口",createDate="2013-01-15T00:00:00",author="程明卫")
public interface FreeRecordsService extends IService<FreeRecordsEntity, Long> {
}
