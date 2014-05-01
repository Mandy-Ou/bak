package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.sys.CountersignEntity;


/**
 * 会签记录  Service接口
 * @author 程明卫
 * @date 2013-11-22T00:00:00
 */
@Description(remark="会签记录业务接口",createDate="2013-11-22T00:00:00",author="程明卫")
public interface CountersignService extends IService<CountersignEntity, Long> {
}
