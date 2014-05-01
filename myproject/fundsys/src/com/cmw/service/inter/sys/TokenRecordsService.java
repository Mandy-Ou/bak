package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.sys.TokenRecordsEntity;


/**
 * 并行令牌记录  Service接口
 * @author 程明卫
 * @date 2013-12-07T00:00:00
 */
@Description(remark="并行令牌记录业务接口",createDate="2013-12-07T00:00:00",author="程明卫")
public interface TokenRecordsService extends IService<TokenRecordsEntity, Long> {
}
