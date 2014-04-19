package com.cmw.core.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.sys.UserEntity;

/**
 * 业务层通用接口
 * @author Administrator
 *
 */
public interface IService <T,PK extends Serializable>{
	/**
	 * 根据ID删除实体对象
	 * @param entityClass
	 * @param id 根据主键ID删除实体对象
	 * @throws ServiceException 抛出DaoE	xception 
	 */
	void deleteEntity(PK id) throws ServiceException;
	/**
	 * 根据指定的参数，删除实体
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @param params 删除实体时的过滤参数
	 * @throws ServiceException 抛出DaoE	xception 
	 */
	<K,V> void deleteEntitys(SHashMap<K,V> params) throws ServiceException;
	/**
	 * 根据 主键ID数组批量删除实体
	 * @param ids 主键ID数组
	 * @throws ServiceException 抛出DaoE	xception 
	 */
	void deleteEntitys(PK[] ids) throws ServiceException;
	/**
	 * 根据 主键ID字符串【多个主键ID以“，”分隔】批量删除实体
	 * @param ids 以逗号拼接的主键ID字符串
	 * @throws ServiceException  抛出ServiceException 
	 */
	void deleteEntitys(String ids)throws ServiceException;
	/**
	 * 根据主键ID禁用实体
	 * @param id 主键ID
	  * @param isenabled 禁用值, isenabled : 0 禁用 1: 起用
	 * @throws ServiceException
	 */
	void enabledEntity(PK id,Integer isenabled)throws ServiceException;
	/**
	 * 根据指定的参数，禁用实体
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @param params 删除实体时的过滤参数
	 * @param isenabled 禁用值, isenabled : 0 禁用 1: 起用
	 * @throws ServiceException 抛出DaoE	xception 
	 */
	<K,V> void enabledEntitys(SHashMap<K,V> params,Integer isenabled) throws ServiceException;
	/**
	 * 根据 主键ID数组批量禁用实体
	 * @param ids 主键ID数组
	 * @throws ServiceException 抛出DaoE	xception 
	 */
	void enabledEntitys(PK[] ids,Integer isenabled) throws ServiceException;
	/**
	 * 根据 主键ID字符串【多个主键ID以“，”分隔】批量禁用实体
	 * @param ids 以逗号拼接的主键ID字符串
	 * @throws ServiceException  抛出ServiceException 
	 */
	void enabledEntitys(String ids,Integer isenabled)throws ServiceException;
	/**
	 * 根据 主键ID字符串【多个主键ID以“，”分隔】批量禁用实体
	 * @param ids 以逗号拼接的主键ID字符串
	  * @param isenabled 禁用值, isenabled : 0 禁用 1: 起用 ,-1:假删除
	  * @param opUser 执行当前操作的用户
	 * @throws ServiceException  抛出ServiceException 
	 */
	void enabledEntitys(final String ids,Integer isenabled,UserEntity opUser)throws ServiceException;
	/**
	 * 根据主键ID获取单个对象
	 * @param id 主键ID
	 * @return 返回符合条件的实体对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	T getEntity(PK id) throws ServiceException;
	/**
	 * 根据指定的参数 获取单个对象
	 * @param <K>
	 * @param <V>
	 * @param params 查找时过滤参数
	 * @return 返回符合条件的实体对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	<K, V> T getEntity(SHashMap<K, V> params) throws ServiceException;
	/**
	 * 根据指定的参数 获取其上一条记录
	 * @param <K>
	 * @param <V>
	 * @param params 查找时过滤参数
	 * @return 返回符合条件的实体对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	<K, V> T navigationPrev(SHashMap<K, V> params) throws ServiceException;
	/**
	 * 根据指定的参数 获取其下一条记录
	 * @param <K>
	 * @param <V>
	 * @param params 查找时过滤参数
	 * @return 返回符合条件的实体对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	<K, V> T navigationNext(SHashMap<K, V> params) throws ServiceException;
	
	/**
	 * 根据指定的参数 以List 对象返回符合条件的数据
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return 返回List对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	<K, V> List<T> getEntityList(SHashMap<K, V> map) throws ServiceException;
	/**
	 * 返回所有符合条件的数据
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return 返回List对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	<K, V> List<T> getEntityList() throws ServiceException;
	/**
	 * 无条件获取所有数据，并以DataTable 对象返回
	 * @param <K>
	 * @param <V>
	 * @param map 过滤参数
	 * @return 返回符合条件的 DataTable 对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	<K, V> DataTable getResultList() throws ServiceException;
	/**
	 * 根据指定的参数 获取结果集列表
	 * 注：【该方法在抽象类中未实现，子类须重写】
	 * @param <K>
	 * @param <V>
	 * @param map 过滤参数
	 * @return 返回符合条件的 DataTable 对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	<K, V> DataTable getResultList(SHashMap<K, V> map) throws ServiceException;
	/**
	 * 根据指定的参数查询符合条件数据，并以DataTable 对象返回
	 * 注：【该方法在抽象类中未实现，子类须重写】
	 * @param params SHashMap 参数对象
	 * @return 返回符合条件的DataTable 对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	<K, V> DataTable getLoanRecordsList(SHashMap<K, V> map) throws ServiceException;
	
	<K, V> DataTable getResultList(final SHashMap<K, V> params,final int offset,final int pageSize) throws ServiceException;
	/**
	 * 根据指定的参数 获取结果集列表
	 * 注：【该方法在抽象类中未实现，子类须重写】
	 * @param <K>
	 * @param <V>
	 * @param map 过滤参数
	 * @return 返回符合条件的 DataTable 对象
	 * @t
	 * hrows ServiceException  抛出ServiceException 
	 */
	/**
	 * 根据指定的参数查询符合条件数据，并以DataTable 对象返回
	<K, V> DataTable getLoanRecordsList(SHashMap<K, V> map) throws ServiceException;
	 * 注：【该方法在抽象类中未实现，子类须重写】
	 * @param params SHashMap 参数对象
	 * @return 返回符合条件的DataTable 对象
	 * @throws ServiceException  抛出ServiceException 
	 */
	<K, V> DataTable getLoanRecordsList(final SHashMap<K, V> params,final int offset,final int pageSize) throws ServiceException;
	
	/**
	 * 保存对象
	 * @param <T> 
	 * @param obj 要保存的实体
	 * @return 返回该实体的主键ID
	 * @throws ServiceException 抛出ServiceException 
	 */
	 PK saveEntity(T obj) throws ServiceException;
	 /**
	  * 批量保存实体(默认 50 个提交一次)
	  * 如果要更新每次提交的大小，可调用 dao 的 setBatchSize 方法
	  * @param objs	要保存的实体 List
	  * @return 返回所有保存成功的实体的 ID 列表
	  * @throws DaoException
	  */
	 List<PK> batchSaveEntitys(List<T> objs) throws ServiceException;
	 /**
	 * 保存或更新对象
	 * @param <T>
	 * @param obj 要保存或更新的对象
	 * @return 返回该实体的主键ID
	 * @throws ServiceException 抛出ServiceException 
	 */
	 void saveOrUpdateEntity(T obj) throws ServiceException;
	 /**
	 * 批量保存或更新对象 (默认 50 个提交一次)
	 * 如果要更新每次提交的大小，可调用 dao 的 setBatchSize 方法
	 * @param <T>
	 * @param obj 要保存或更新的对象
	 * @return 返回该实体的主键ID
	 * @throws DaoException 抛出DaoException 
	 */
	 void batchSaveOrUpdateEntitys(List<T> objs) throws ServiceException;
	 /**
	  * 更新对象
	  * @param <T>
	  * @param obj 要更新的对象
	  * @throws ServiceException 抛出ServiceException 
	  */
	 void updateEntity(T obj) throws ServiceException;
	 /**
	  * 批量更新对象 (默认 50 个提交一次)
	  * 如果要更新每次提交的大小，可调用 dao 的 setBatchSize 方法
	  * @param <T>
	  * @param objs 要批量更新的对象列表
	  * @throws DaoException 抛出DaoException 
	  */
	 void batchUpdateEntitys(List<T> objs) throws ServiceException;
	 /**
	  * 处理复杂业务的方法。（可将不同类型的数据放入Map中）
	  * 当要在同一个事务中执行时多个不同Service对象业务方法时，使用此方法
	  * @param complexData	复杂数据 Map对象
	  * @return 返回指定的 Object 对象
	  * @throws ServiceException
	  */
	 void doComplexBusss(Map<String,Object> complexData)throws ServiceException;
	 /**
	  * 处理复杂业务的方法。（可将不同类型的数据放入SHashMap中）
	  * 当要在同一个事务中执行时多个不同Service对象业务方法时，使用此方法
	  * @param complexData	复杂数据 Map对象
	  * @return 返回指定的 Object 对象
	  * @throws ServiceException
	  */
	 Object doComplexBusss(SHashMap<String,Object> complexData)throws ServiceException;
	/**
	 * 获取总记录集
	 * @return 总记录数
	 * @throws ServiceException 抛出ServiceException
	 */
	Long getTotals()throws ServiceException;
	/**
	 * 获取总记录集
	 * @param param 过滤参数
	 * @return 返回记录总数
	 * @throws ServiceException 抛出ServiceException
	 */
	<K, V>long getTotals(SHashMap<K, V> param)throws ServiceException;
	/**
	 * 返回数据库中最大的主键ID
	 * @return 最大的ID
	 * @throws ServiceException  抛出ServiceException
	 */
	Long getMaxID()throws ServiceException;
}
