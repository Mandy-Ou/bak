package com.cmw.dao.inter.finance;


import java.util.Map;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.LoanInvoceEntity;


/**
 * 放款单  DAO接口
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
 @Description(remark="放款单Dao接口",createDate="2013-01-15T00:00:00",author="程明卫")
public interface LoanInvoceDaoInter  extends GenericDaoInter<LoanInvoceEntity, Long>{
	 public void update(Map<String,Object> dataMap) throws DaoException;
	 /**
	  * 
	  * 更新放款状态用于可逆操作
	  * @param dataMap
	  * @throws DaoException
	  */
	 public void rever(Map<String,Object> dataMap) throws DaoException;

	 /**
	  * 放款业务管理:更新放款状态为待放款:对应放款管理的可逆操作
	  * @param dataMap
	  * @throws DaoException
	  */
	 public <K, V> DataTable getIds(SHashMap<K, V> map)throws DaoException;
	 
	 /**
	  * 根据实收金额日志ID获取放款数据
	  * @param params	过滤参数
	  * @param offset	分页的起始位置
	  * @param pageSize	每页大小
	  * @return	返回 DataTable 对象
	  * @throws DaoException
	  */
	 <K, V> DataTable getDsByAmountLogId(SHashMap<K, V> params, int offset, int pageSize)throws DaoException;

	/**放款单查询
	 * @param params
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws DaoException 
	 */
	public DataTable getLoanInvoceQuery(SHashMap<String, Object> params,
			int offset, int pageSize) throws DaoException;
	/**
	 * 放款单流水,查询放款情况的方法
	 * @param params
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws DaoException
	 */
	public DataTable getLoanInvoceQueryDetail(SHashMap<String, Object> params,
			int offset, int pageSize) throws DaoException;
	
	/** 获取需要预收的数据接口
	 * @param params
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws DaoException
	 */
	<K, V> DataTable getNeedYsList(SHashMap<K, V> params, int offset, int pageSize)throws DaoException;
	
}
