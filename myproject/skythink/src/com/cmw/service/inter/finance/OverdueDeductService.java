package com.cmw.service.inter.finance;


import java.util.HashMap;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.finance.TaboutsideEntity;
import com.cmw.entity.sys.UserEntity;

/**
 * 逾期还款  Service接口
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="逾期还款 业务接口",createDate="2013-02-28T00:00:00",author="程明卫")
public interface OverdueDeductService extends IService<TaboutsideEntity, Long> {
	
	/**
	 * 批量计算罚息、滞纳金数据，需要持久化到数据库的方法
	 * @param map  算逾期的截止日期
	 * @return
	 * @throws ServiceException
	 */
	public <K, V> void calculateBatchLateDatas(SHashMap<K, V> map) throws ServiceException;
	
	/**
	 * 计算罚息、滞纳金数据，且不需要持久化到数据库的方法
	 * @param map 
	 * @return
	 * @throws ServiceException
	 */
	public <K, V> JSONObject calculateLateDatas(SHashMap<K, V> map) throws ServiceException;
	
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws ServiceException;
	/**
	 * Excel 逾期还款数据导出
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
	/**
	 * 提供给系统定时器每天计算罚息和滞纳金的方法
	 * @throws ServiceException
	 */
	void calculateLateDatas() throws ServiceException;
	/**
	 * 逾期还款的grid数据
	 * @param map
	 * @param start
	 * @param limit
	 * @return
	 * @throws ServiceException
	 */
	public DataTable repDetail(SHashMap<String, Object> map, int start, int limit) throws ServiceException;
	/**
	 * 处理表内表外数据
	 * @param contractIds	合同ID
	 * @param planList	所有还款计划
	 * @param user 当前用户
	 * @return
	 * @throws ServiceException
	 */
	public List<TaboutsideEntity> saveTaboutsideDatas(String contractIds,List<PlanEntity> planList, UserEntity user) throws ServiceException;
	
}