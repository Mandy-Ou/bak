package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.sys.DepartmentEntity;


/**
 * 部门  Service接口
 * @author 彭登浩
 * @date 2012-11-09T00:00:00
 */
@Description(remark="部门业务接口",createDate="2012-11-09T00:00:00",author="彭登浩")
public interface DepartmentService extends IService<DepartmentEntity, Long> {
}
