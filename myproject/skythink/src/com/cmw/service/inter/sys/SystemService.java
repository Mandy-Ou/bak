package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.sys.SystemEntity;


/**
 * 系统  Service接口
 * @author chengmingwei
 * @date 2012-10-28T00:00:00
 */
@Description(remark="系统业务接口",createDate="2012-10-28T00:00:00",author="chengmingwei")
public interface SystemService extends IService<SystemEntity, Long> {
}
