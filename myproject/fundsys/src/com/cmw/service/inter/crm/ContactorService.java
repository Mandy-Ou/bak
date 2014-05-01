package com.cmw.service.inter.crm;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.crm.ContactorEntity;


/**
 * 联系人资料  Service接口
 * @author pdh
 * @date 2012-12-18T00:00:00
 */
@Description(remark="联系人资料业务接口",createDate="2012-12-18T00:00:00",author="pdh")
public interface ContactorService extends IService<ContactorEntity, Long> {
}
