package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.sys.DataAccessEntity;


/**
 * 数据访问权限  Service接口
 * @author 程明卫
 * @date 2012-12-28T00:00:00
 */
@Description(remark="数据访问权限业务接口",createDate="2012-12-28T00:00:00",author="程明卫")
public interface DataAccessService extends IService<DataAccessEntity, Long> {
}
