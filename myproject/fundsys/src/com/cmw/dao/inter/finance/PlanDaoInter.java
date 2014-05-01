package com.cmw.dao.inter.finance;


import java.util.List;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.PlanEntity;


/**
 * 还款计划  DAO接口
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
 @Description(remark="还款计划Dao接口",createDate="2013-01-15T00:00:00",author="程明卫")
public interface PlanDaoInter  extends GenericDaoInter<PlanEntity, Long>{
	 /**
	 * 获取正常扣收数据
	 * @param params	查询参数
	 * @param offset	分页起始位
	 * @param pageSize  每页大小
	 * @return 返回 DataTable 对象
	 * @throws DaoException
	 */
	 public DataTable getNomalPlans(SHashMap<String, Object> params,int offset, int pageSize) throws DaoException;
	
	 /**
	  * 正常还款流水
	  * @param params
	  * @param offset
	  * @param pageSize
	  * @return
	  * @throws DaoException
	  */
	 DataTable RepDetail(SHashMap<String, Object> params,int offset, int pageSize) throws DaoException;

	DataTable getIds(SHashMap<String, Object> params) throws DaoException;
	
	/**
	 * 获取逾期还款计划条数
	 * @return
	 * @throws DaoException 
	 */
	public Long getLatePlansCount() throws DaoException;
	/**
	 * 分批获取还款计划逾期数据
	 * @param params	过滤参数
	 * @return
	 * @throws DaoException 
	 */
	public List<PlanEntity> getEntityListByBatch(SHashMap<String, Object> params) throws DaoException;
	/**
	 * 正常转逾期
	 * @param params 参数 值：lastDate,holidays
	 * @return
	 * @throws DaoException 
	 */
	public void updateSateToLate(SHashMap<String, Object> params) throws DaoException;
	/**
	 * 获取逾期还款计划汇总数据
	 * @param contractIds 合同ID列表
	 * @return 返回 DataTable 对象 "contractId,planId,monthPharses,totalPharses,amounts,iamounts,mamounts,pamounts,damounts"
	 * @throws DaoException 
	 */
	public DataTable getLateSumDatas(String contractIds) throws DaoException;
}
