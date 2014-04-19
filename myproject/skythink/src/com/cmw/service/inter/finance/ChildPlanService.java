package com.cmw.service.inter.finance;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.finance.ChildPlanEntity;


/**
 * 还款计划子表  Service接口
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="还款计划子表业务接口",createDate="2013-01-15T00:00:00",author="程明卫")
public interface ChildPlanService extends IService<ChildPlanEntity, Long> {
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
}
