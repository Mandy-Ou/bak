package com.cmw.service.inter.crm;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.crm.EcustomerEntity;


/**
 * 企业客户基本信息  Service接口
 * @author pdh
 * @date 2012-12-21T00:00:00
 */
@Description(remark="企业客户基本信息业务接口",createDate="2012-12-21T00:00:00",author="pdh")
public interface EcustomerService extends IService<EcustomerEntity, Long> {
}
