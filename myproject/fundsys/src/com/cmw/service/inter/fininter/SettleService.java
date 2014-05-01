package com.cmw.service.inter.fininter;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.fininter.SettleEntity;


/**
 * 结算方式  Service接口
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="结算方式业务接口",createDate="2013-03-28T00:00:00",author="程明卫")
public interface SettleService extends IService<SettleEntity, Long> {
}
