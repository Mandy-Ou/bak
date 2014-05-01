package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.PlanEntity;


/**
 * 还款计划  Service接口
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="还款计划业务接口",createDate="2013-01-15T00:00:00",author="程明卫")
public interface PlanService extends IService<PlanEntity, Long> {
	 /**
	 * 获取正常扣收数据
	 * @param params	查询参数
	 * @param offset	分页起始位
	 * @param pageSize  每页大小
	 * @return 返回 DataTable 对象
	 * @throws DaoException
	 */
	 public DataTable getNomalPlans(SHashMap<String, Object> params,int offset, int pageSize) throws ServiceException;

	 /**
	  * 
	  * @param params
	  * @return
	  * @throws ServiceException
	  */
	DataTable getIds(SHashMap<String, Object> params) throws ServiceException;

	/**
	 * 
	 * @param map
	 * @param start
	 * @param limit
	 * @return
	 */
	public DataTable RepDetail(SHashMap<String, Object> map, int start, int limit) throws ServiceException;
}
