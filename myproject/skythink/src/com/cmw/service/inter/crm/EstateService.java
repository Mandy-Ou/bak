package com.cmw.service.inter.crm;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.crm.EstateEntity;


/**
 * 房产物业信息  Service接口
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="房产物业信息业务接口",createDate="2012-12-15T00:00:00",author="pdh")
public interface EstateService extends IService<EstateEntity, Long> {
}
