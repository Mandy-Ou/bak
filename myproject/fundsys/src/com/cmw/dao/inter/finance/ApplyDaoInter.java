package com.cmw.dao.inter.finance;


import java.util.List;
import java.util.Map;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.ApplyEntity;


/**
 * 贷款申请单  DAO接口
 * @author 程明卫
 * @date 2012-12-15T00:00:00
 */
 @Description(remark="贷款申请单Dao接口",createDate="2012-12-15T00:00:00",author="程明卫")
public interface ApplyDaoInter  extends GenericDaoInter<ApplyEntity, Long>{
	 /**
	  * 获取已经开始并且未完结的流程实例ID列表
	  * @return 以DataTable 返回
	  * @throws DaoException
	  */
	 List<String> getProcessInstanceIds() throws DaoException;
	 
	 /**
	 * 根据还款计划ID获取贷款申请单 Map 对象
	 * @param planIds 还款计划表ID集合字符串
	 * @return
	 * @throws DaoException 
	 */
	public Map<Long,ApplyEntity> getApplyEntitysByPlanIds(String planIds) throws DaoException;
	/**
	 * 更新项目的状态
	 * data 值 state,user
	 * @param data
	 * @throws DaoException
	 */
	public void updateState(SHashMap<String, Object> data) throws DaoException;
	 /**
	 * 根据合同ID获取贷款申请单 Map 对象
	 * @param planIds 还款计划表ID集合字符串
	 * @return
	 * @throws DaoException 
	 */
	Map<Long, ApplyEntity> getApplyEntitysByContractIds(String contractIds) throws DaoException;
	/**
	 * 调用父类的getResultList方法
	 * @param <V>
	 * @param <K>
	 * @throws DaoException 
	 * 
	 */
	<K, V> DataTable getApplyDt(final SHashMap<K, V> params,final int offset,final int pageSize) throws  DaoException;
}
