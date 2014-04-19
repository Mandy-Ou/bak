package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.finance.BfamountEntity;


/**
 * 预收帐款  Service接口
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="预收帐款业务接口",createDate="2013-02-28T00:00:00",author="程明卫")
public interface BfamountService extends IService<BfamountEntity, Long> {
}
