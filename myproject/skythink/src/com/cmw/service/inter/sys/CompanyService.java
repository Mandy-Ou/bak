package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.sys.CompanyEntity;


/**
 * 公司  Service接口
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="公司业务接口",createDate="2011-09-24T00:00:00",author="chengmingwei")
public interface CompanyService extends IService<CompanyEntity, Long> {
}
