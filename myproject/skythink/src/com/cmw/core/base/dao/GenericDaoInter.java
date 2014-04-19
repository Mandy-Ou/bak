package com.cmw.core.base.dao;

import java.util.List;

import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.sys.UserEntity;
/**
 * 公共泛型DAO接口
 * @author chengmingwei
 *
 */
public interface GenericDaoInter<T,PK> {
	
	/**
	 * 根据ID删除实体对象
	 * @param entityClass
	 * @param id 根据主键ID删除实体对象
	 * @throws DaoException 抛出DaoE	xception 
	 */
	void deleteEntity(final PK id) throws DaoException;
	/**
	 * 根据指定的参数，删除实体
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @param params 删除实体时的过滤参数
	 * @throws DaoException 抛出DaoE	xception 
	 */
	<K,V> void deleteEntitys(final SHashMap<K,V> params) throws DaoException;
	/**
	 * 根据 主键ID数组批量删除实体
	 * @param ids 主键ID数组
	 * @throws DaoException 抛出DaoE	xception 
	 */
	void deleteEntitys(final PK[] ids) throws DaoException;
	/**
	 * 根据 主键ID字符串【多个主键ID以“，”分隔】批量删除实体
	 * @param ids 以逗号拼接的主键ID字符串
	 * @throws DaoException  抛出DaoException 
	 */
	void deleteEntitys(final String ids)throws DaoException;
	/**
	 * 根据主键ID禁用实体
	 * @param id 主键ID
	  * @param isenabled 禁用值, isenabled : 0 禁用 1: 起用
	 * @throws DaoException
	 */
	void enabledEntity(final PK id,Integer isenabled)throws DaoException;
	/**
	 * 根据指定的参数，禁用实体
	 * @param <K> 键类型
	 * @param <V> 值类型
	 * @param params 删除实体时的过滤参数
	 * @param isenabled 禁用值, isenabled : 0 禁用 1: 起用
	 * @throws DaoException 抛出DaoE	xception 
	 */
	<K,V> void enabledEntitys(final SHashMap<K,V> params,Integer isenabled) throws DaoException;
	/**
	 * 根据 主键ID数组批量禁用实体
	 * @param ids 主键ID数组
	 * @throws DaoException 抛出DaoE	xception 
	 */
	void enabledEntitys(final PK[] ids,Integer isenabled) throws DaoException;
	/**
	 * 根据 主键ID字符串【多个主键ID以“，”分隔】批量禁用实体
	 * @param ids 以逗号拼接的主键ID字符串
	 * @throws DaoException  抛出DaoException 
	 */
	void enabledEntitys(final String ids,Integer isenabled)throws DaoException;
	/**
	 * 根据 主键ID字符串【多个主键ID以“，”分隔】批量禁用实体
	 * @param ids 以逗号拼接的主键ID字符串
	  * @param isenabled 禁用值, isenabled : 0 禁用 1: 起用 ,-1:假删除
	  * @param opUser 执行当前操作的用户
	 * @throws DaoException  抛出DaoException 
	 */
	void enabledEntitys(final String ids,Integer isenabled,UserEntity opUser)throws DaoException;
	/**
	 * 根据主键ID获取单个对象
	 * @param id 主键ID
	 * @return 返回符合条件的实体对象
	 * @throws DaoException  抛出DaoException 
	 */
	T getEntity(final PK id) throws DaoException;
	/**
	 * 根据指定的参数 获取上一条或下一条记录对象
	 * @param <K>
	 * @param <V>
	 * @param params 查找时过滤参数
	 * @return 返回符合条件的实体对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> T navigation(final SHashMap<K, V> params) throws DaoException;
	/**
	 * 根据指定的参数 获取单个对象
	 * @param <K>
	 * @param <V>
	 * @param params 查找时过滤参数
	 * @return 返回符合条件的实体对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> T getEntity(final SHashMap<K, V> params) throws DaoException;
	/**
	 * 根据指定的参数 以List 对象返回符合条件的数据
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return 返回List对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> List<T> getEntityList(final SHashMap<K, V> map) throws DaoException;
	/**
	 * 返回所有符合条件的数据
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return 返回List对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> List<T> getEntityList() throws DaoException;
	/**
	 * 根据指定的参数 以List 对象返回符合条件的数据
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return 返回List对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> List<T> getEntityList(final SHashMap<K, V> map,final int offset,final int pageSize) throws DaoException;
	
	/**
	 * 无条件获取所有数据，并以DataTable 对象返回 
	 * 须重写 Entity 类的   getFields() 和   getDatas()
	 * @param <K>
	 * @param <V>
	 * @param map 过滤参数
	 * @return 返回符合条件的 DataTable 对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> DataTable getResultList() throws DaoException;
	/**
	 * 根据指定的参数 获取结果集列表
	 * 注：【该方法在抽象类中未实现，子类须重写】
	 * @param <K>
	 * @param <V>
	 * @param map 过滤参数
	 * @return 返回符合条件的 DataTable 对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> DataTable getLoanRecordsList(SHashMap<K, V> map) throws DaoException;
	/**
	 * 根据指定的参数查询符合条件数据，并以DataTable 对象返回
	 * 注：【该方法在抽象类中未实现，子类须重写】
	 * @param params SHashMap 参数对象
	 * @return 返回符合条件的DataTable 对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> DataTable getLoanRecordsList(final SHashMap<K, V> params,final int offset,final int pageSize) throws DaoException;
	
	
	/**
	 * 根据指定的参数 获取结果集列表
	 * 注：【该方法在抽象类中未实现，子类须重写】
	 * @param <K>
	 * @param <V>
	 * @param map 过滤参数
	 * @return 返回符合条件的 DataTable 对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> DataTable getResultList(SHashMap<K, V> map) throws DaoException;
	/**
	 * 根据指定的参数查询符合条件数据，并以DataTable 对象返回
	 * 注：【该方法在抽象类中未实现，子类须重写】
	 * @param params SHashMap 参数对象
	 * @return 返回符合条件的DataTable 对象
	 * @throws DaoException  抛出DaoException 
	 */
	<K, V> DataTable getResultList(final SHashMap<K, V> params,final int offset,final int pageSize) throws DaoException;
	
	
	/**
	 * 根据 hql 查询指定大小的结果
	 * @param params hql hql 查询语句
	 * @return 返回符合条件的指定大小记录
	 * @throws DaoException  抛出DaoException 
	 */
	DataTable getResultList(final String hql,final int offset,final int pageSize) throws DaoException;
	/**
	 * 保存对象
	 * @param <T> 
	 * @param obj 要保存的实体
	 * @return 返回该实体的主键ID
	 * @throws DaoException 抛出DaoException 
	 */
	 PK saveEntity(T obj) throws DaoException;
	 /**
	  * 批量保存实体(默认 50 个提交一次)
	  * @param objs	要保存的实体 List
	  * @return 返回所有保存成功的实体的 ID 列表
	  * @throws DaoException
	  */
	 List<PK> batchSaveEntitys(List<T> objs) throws DaoException;
	 /**
	 * 保存或更新对象
	 * @param <T>
	 * @param obj 要保存或更新的对象
	 * @return 返回该实体的主键ID
	 * @throws DaoException 抛出DaoException 
	 */
	 void saveOrUpdateEntity(T obj) throws DaoException;
	 /**
	 * 批量保存或更新对象 (默认 50 个提交一次)
	 * @param <T>
	 * @param obj 要保存或更新的对象
	 * @return 返回该实体的主键ID
	 * @throws DaoException 抛出DaoException 
	 */
	 void batchSaveOrUpdateEntitys(List<T> objs) throws DaoException;
	 /**
	  * 更新对象
	  * @param <T>
	  * @param obj 要更新的对象
	  * @throws DaoException 抛出DaoException 
	  */
	 void updateEntity(T obj) throws DaoException;
	 /**
	  * 批量更新对象
	  * @param <T>
	  * @param objs 要批量更新的对象列表
	  * @throws DaoException 抛出DaoException 
	  */
	 void batchUpdateEntitys(List<T> objs) throws DaoException;
	 
	/**
	 * 获取总记录集
	 * @return 总记录数
	 * @throws DaoException 抛出DaoException
	 */
	Long getTotals()throws DaoException;
	/**
	 * 获取总记录集
	 * @param param 过滤参数
	 * @return 返回记录总数
	 * @throws DaoException 抛出DaoException
	 */
	<K, V>long getTotals(SHashMap<K, V> param)throws DaoException;
	/**
	 * 返回数据库中最大的主键ID
	 * @return 最大的ID
	 * @throws DaoException  抛出DaoException
	 */
	Long getMaxID()throws DaoException;
	/**
	 * 导航 --- 上一条 : 1
	 */
	public static final int VAL_NAV_PRE = 1;
	/**
	 * 导航 --- 下一条 : 2
	 */
	public static final int VAL_NAV_NEXT = 2;
	/**
	 *  导航 --- key : _navigation_
	 */
	public static final String KEY_NAVIGATION = "_navigation_";
}
