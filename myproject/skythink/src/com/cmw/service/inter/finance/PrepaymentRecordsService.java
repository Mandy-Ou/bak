package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.PrepaymentRecordsEntity;


/**
 * 实收提前还款金额  Service接口
 * @author 程明卫
 * @date 2013-11-03 T00:00:00
 */
@Description(remark="实收提前还款金额业务接口",createDate="2013-11-03 T00:00:00",author="程明卫")
public interface PrepaymentRecordsService extends IService<PrepaymentRecordsEntity, Long> {
}
