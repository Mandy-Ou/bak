package com.cmw.service.inter.finance;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.ApplyEntity;


/**
 * 贷款申请  Service接口
 * @author 程明卫
 * @date 2012-12-16T00:00:00
 */
@Description(remark="贷款申请业务接口",createDate="2012-12-16T00:00:00",author="程明卫")
public interface ApplyService extends IService<ApplyEntity, Long> {
	/**
	  * 获取已经开始并且未完结的流程实例ID列表
	  * @return 以 List 返回
	  * @throws DaoException
	  */
	List<String> getProcessInstanceIds() throws ServiceException;
	/**
	 * 根据还款计划ID获取贷款申请单列表
	 * @param planIds 还款计划表ID集合字符串
	 * @return
	 * @throws DaoException 
	 */
	public Map<Long,ApplyEntity> getApplyEntitysByPlanIds(String planIds) throws ServiceException;
	/**
	 * 更新项目的状态
	 * data 值 state,user,contractIds
	 * @param data
	 * @throws DaoException
	 */
	public void updateState(SHashMap<String, Object> data) throws ServiceException;
	/**
	 * Excel 逾期还款数据导出
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
	/**
	 * 调用父类的getResultList方法
	 * @param <V>
	 * @param <K>
	 * 
	 */
	<K, V> DataTable getApplyDt(final SHashMap<K, V> params,final int offset,final int pageSize) throws ServiceException;
}
