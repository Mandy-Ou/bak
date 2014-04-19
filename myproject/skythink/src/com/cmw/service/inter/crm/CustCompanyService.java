package com.cmw.service.inter.crm;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.crm.CustCompanyEntity;


/**
 * 个人旗下/企业关联公司  Service接口
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人旗下/企业关联公司业务接口",createDate="2012-12-15T00:00:00",author="pdh")
public interface CustCompanyService extends IService<CustCompanyEntity, Long> {
}
