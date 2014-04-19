package com.cmw.service.inter.crm;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.crm.CustomerInfoEntity;


/**
 * 个人客户基本信息  Service接口
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="个人客户基本信息业务接口",createDate="2012-12-12T00:00:00",author="程明卫")
public interface CustomerInfoService extends IService<CustomerInfoEntity, Long> {
}
