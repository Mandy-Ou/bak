package com.cmw.service.inter.fininter;


import java.util.Map;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.fininter.AmountLogEntity;


/**
 * 实收金额日志  Service接口
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="实收金额日志业务接口",createDate="2013-03-28T00:00:00",author="程明卫")
public interface AmountLogService extends IService<AmountLogEntity, Long> {
	/**
	 * 保存财务凭证
	 * @param logDataMap	Map<AmountLogEntity,DataTable>  map 对象
	 * @param map  财务处理时，用到的参数 
	 * @throws ServiceException
	 */
	public void saveVouchers(Map<AmountLogEntity,DataTable> logDataMap,SHashMap<String, Object> map) throws ServiceException;
	/**
	 * 重新生成财务凭证
	 */
	public void saveVouchers(SHashMap<String, Object> map) throws ServiceException;
	/**
	 * 根据业务分类保存实收金额日志数据并以 Map 对象的方式返回这些数据
	 * @param params
	 * @return Map<AmountLogEntity,DataTable>  map 对象
	 * @throws ServiceException
	 */
	Map<AmountLogEntity,DataTable> saveByBussTag(SHashMap<String, Object> params) throws ServiceException;
	/**
	 * 根据多个实收金额日志ID业务标识获取实收金额日志和收款记录数据并以 Map 对象返回
	 * @param alList	原实收金额日志列表
	 * @return 返回 Map<AmountLogEntity,DataTable> 对象
	 * @throws ServiceException 
	 */
	public Map<AmountLogEntity,DataTable> getBussAmountRecords(String amountLogIds) throws ServiceException;
	/**
	 *  根据 SQL 语句获取 DataTable 对象
	 * @param sql
	 * @param colNames
	 * @return
	 * @throws DaoException
	 */
	public DataTable getDtBySql(String sql, String colNames) throws ServiceException;
	/** 根据不同的业务表示来查询不 同的数据
	 * @param map
	 * @param start
	 * @param limit
	 * @return
	 */
	public DataTable bussTaglist(SHashMap<String, Object> map, int start,
			int limit);
	
}
