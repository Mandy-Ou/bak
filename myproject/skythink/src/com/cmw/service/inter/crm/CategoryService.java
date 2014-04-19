package com.cmw.service.inter.crm;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.crm.CategoryEntity;


/**
 * 客户分类  Service接口
 * @author 程明卫
 * @date 2012-12-17T00:00:00
 */
@Description(remark="客户分类业务接口",createDate="2012-12-17T00:00:00",author="程明卫")
public interface CategoryService extends IService<CategoryEntity, Long> {
}
