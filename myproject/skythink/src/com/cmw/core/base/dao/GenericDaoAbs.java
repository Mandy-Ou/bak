package com.cmw.core.base.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.justobjects.pushlet.util.Log;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.entity.BaseEntity;
import com.cmw.core.base.entity.BaseInter;
import com.cmw.core.base.entity.IdBaseEntity;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.GenericsUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.UserEntity;
/**
 * 泛型DAO抽象类，新的DAO须实现此类
 * @author cmw_1984122
 * @param <T> 实体类型
 * @param <PK> 实体主键类型
 */
@SuppressWarnings("unchecked")
@Transactional
public abstract class GenericDaoAbs<T, PK > extends PageHibernateDaoSupport<T>
		implements GenericDaoInter<T, PK> {
	
	private Class<T> entityClass = null;
	private String entityName = "";
	private String entityPKname = "";
	private int batchSize = 50;	//批处理大小
	public GenericDaoAbs() {
		entityClass = GenericsUtil.getSupClassGenericType(getClass());
		entityName = getEntityName();
		entityPKname = getEntityPk();
	}
	
	/**  
     * 获得该DAO对应的POJO类型  
     */  
	public Class<T> getEntityClass(){
		return this.entityClass;
	}
	
	/**
	 * 返回实体类的全类名称
	 * @return 实体名称
	 */
	public String getEntityName(){
		return getEntityClass().getName();
	}
	
	public String getEntityPk(){
		return GenericsUtil.getPkName(entityClass);
	}
	@Override
	public void deleteEntity(PK id) throws DaoException {
		try {
			getSession().delete(getEntity(id));
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public void deleteEntitys(PK[] ids) throws DaoException {
		try {
			if(null  == ids || ids.length == 0) return;
			for(PK id : ids){
				deleteEntity(id);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params) throws DaoException {
		try {
			String hql = "delete from "+entityName+" A "+SqlUtil.buildWhereStr("A", params);
			getSession().createQuery(hql).executeUpdate();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	


	@Override
	public void deleteEntitys(String ids) throws DaoException {
		try {
			String hql = "delete from "+entityName+" A where A."+entityPKname+" in ("+ids+")";
			getSession().createQuery(hql).executeUpdate();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public void enabledEntity(PK id,Integer isenabled) throws DaoException {
		try {
			enabledEntitys((String)id, isenabled);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public void enabledEntitys(PK[] ids,Integer isenabled) throws DaoException {
	try {
			if(null == ids || ids.length == 0) return;
			StringBuffer sb = new StringBuffer();
			for(PK id : ids){
				sb.append(id+",");
			}
			String idsStr = StringHandler.RemoveStr(sb, ",");
			enabledEntitys(idsStr, isenabled);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params,Integer isenabled)
			throws DaoException {
	try {
		String hql = "update "+entityName+" A  set A.isenabled="+isenabled+SqlUtil.buildWhereStr("A", params);
		getSession().createQuery(hql).executeUpdate();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public void enabledEntitys(String ids,Integer isenabled) throws DaoException {
		try {
			String hql = "update "+entityName+" A  set A.isenabled="+isenabled+" where A."+entityPKname+" in ("+ids+")";
			getSession().createQuery(hql).executeUpdate();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	
	
	@Override
	public void enabledEntitys(String ids, Integer isenabled, UserEntity opUser)
			throws DaoException {
		String modifytime = DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT, new Date());
		String hql = "update "+entityName+"  set isenabled="+isenabled+",modifier='"+opUser.getUserId()+"',modifytime='"+modifytime+"' where "+entityPKname+" in ("+ids+")";
		executeHql(hql);
	}

	@Override
	public T getEntity(PK id) throws DaoException {
		try {
			return (T)getSession().get(entityClass, (Serializable)id);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	

	@Override
	public <K, V> T getEntity(SHashMap<K, V> params) throws DaoException {
		try {
			List<T> list = getEntityList(params);
			return (null == list || list.size()==0) ? null : list.get(0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	
	/**
	 * 上一条，下一条导航
	 */
	@Override
	public <K, V> T navigation(SHashMap<K, V> params) throws DaoException {
		try {
			int val_nav = (Integer)params.get((K)KEY_NAVIGATION);
			String id = params.getvalAsStr("id");
			String whereStr = "";
			if(val_nav == VAL_NAV_PRE){	//--> 上一条  <--//
				whereStr = " AND A."+entityPKname+" =(" +
						"SELECT MAX("+entityPKname+") FROM "+entityName+" WHERE "+entityPKname+" < '"+id+"' )";
			}else if(val_nav == VAL_NAV_NEXT){	//--> 下一条  <--//
				whereStr = " AND A."+entityPKname+" =(" +
						"SELECT MIN("+entityPKname+") FROM "+entityName+" WHERE "+entityPKname+" > '"+id+"' )";
			}
			String hql = "from "+entityName+" A ";
			if(isExtendBeaseEntiy()){
				hql += " where A.isenabled != '"+SysConstant.OPTION_DEL+"' ";
			}else{
				hql += " where 1=1 ";
			}
			if(StringHandler.isValidStr(whereStr)){
				hql += whereStr;
			}
			hql += " order by A."+entityPKname+" desc";
			List<T> list = (List<T>)getSession().createQuery(hql).list();
			return (null == list || list.size()==0) ? null : list.get(0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public <K, V> List<T> getEntityList() throws DaoException {
		try {
			String hql = "from "+entityName+" A ";
			if(isExtendBeaseEntiy()){
				hql += " where A.isenabled != '"+SysConstant.OPTION_DEL+"' ";
			}
			hql += " order by A."+entityPKname+" desc";
			List<T> list = (List<T>)getSession().createQuery(hql).list();
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public <K, V> List<T> getEntityList(SHashMap<K, V> map) throws DaoException {
		try {
			String hql = getEntityListHql(map);
			List<T> list = getList(hql);
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	private <K, V> String getEntityListHql(SHashMap<K, V> map) {
		String orderbyStr = SqlUtil.buildOrderByStr("A",map);
		if(!StringHandler.isValidStr(orderbyStr) || orderbyStr.length() == 0){
			orderbyStr = "A."+entityPKname+" desc";
		}
		String whereStr = SqlUtil.buildWhereStr("A", map);
		orderbyStr = " order by " + orderbyStr;
		String hql = "from "+entityName+" A "+whereStr+orderbyStr;
		return hql;
	}

	@Override
	public <K, V> List<T> getEntityList(SHashMap<K, V> map, int offset,
			int pageSize) throws DaoException {
		try {
			String hql = getEntityListHql(map);
			Query query = getSession().createQuery(hql);
			List<T> list = (offset == -1 || offset == -1) ? query.list() : query.setFirstResult(offset).setMaxResults(pageSize).list();
			return list;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	public  <K, V> List<T> getList(String hql) {
		return  (List<T>)getSession().createQuery(hql).list();
	}

	@Override
	public Long getMaxID() throws DaoException {
		try {
			List<Long> list = (List<Long>)getSession().createQuery("select max(A."+entityPKname+") from "+entityName+" A ").list();
			return (null == list || list.size()==0) ? 0 : (null == list.get(0) ? 0 : list.get(0));
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public <K, V> DataTable getResultList() throws DaoException {
		try {
			List<T> list = getEntityList();
			DataTable dt = getDTByEntityList(list);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 根据实体对象列表返回DataTable 对象
	 * @param list	Entity 对象列表
	 * @return	返回   DataTable 对象
	 * @throws DaoException
	 */
	protected DataTable getDTByEntityList(List<T> list) throws DaoException {
		if(null == list || list.size() == 0) return null;
		BaseInter obj = (BaseInter)list.get(0);
		String[] cmns = obj.getFields();
		StringBuffer sb = new StringBuffer();
		for(String cmn : cmns){	//拼接列
			sb.append(cmn+",");
		}
		if(null == sb || sb.length() == 0) throw new DaoException("Entity not have overwrite getFields method ...!");
		String cmnStrs = StringHandler.RemoveStr(sb, ",");
		List<Object> dataSources = new ArrayList<Object>();
		for(int i=0,count = list.size(); i<count; i++){
			dataSources.add(((BaseInter)list.get(i)).getDatas());
		}
		DataTable dt = new DataTable(dataSources,cmnStrs);
		return dt;
	}
	
	/**
	 * 根据实体对象列表返回DataTable 对象
	 * @param list	Entity 对象列表
	 * @return	返回   DataTable 对象
	 * @throws DaoException
	 */
	protected DataTable getDTByObjList(List<Object> list) throws DaoException {
		if(null == list || list.size() == 0) return null;
		BaseInter obj = (BaseInter)list.get(0);
		String[] cmns = obj.getFields();
		StringBuffer sb = new StringBuffer();
		for(String cmn : cmns){	//拼接列
			sb.append(cmn+",");
		}
		if(null == sb || sb.length() == 0) throw new DaoException("Entity not have overwrite getFields method ...!");
		String cmnStrs = StringHandler.RemoveStr(sb, ",");
		List<Object> dataSources = new ArrayList<Object>();
		for(int i=0,count = list.size(); i<count; i++){
			dataSources.add(((BaseInter)list.get(i)).getDatas());
		}
		DataTable dt = new DataTable(dataSources,cmnStrs);
		return dt;
	}
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try {
			boolean hasTotalCount = true;
			if(null != params && params.getMap().containsKey(SqlUtil.TOTALCOUNT_KEY)){
				hasTotalCount = params.getvalAsBln(SqlUtil.TOTALCOUNT_KEY);
				params.removes(SqlUtil.TOTALCOUNT_KEY);
			}
			String hql = " from "+entityName+" A ";
			boolean hasWhereKeyWords = false;
			if(isExtendBeaseEntiy()){
				hql += " where A.isenabled != '"+SysConstant.OPTION_DEL+"' ";
			}else{
				//hql += " where 1=1 ";
				hasWhereKeyWords = true;
			}
			
//			Object[] values = null;
			String orderbyStr = SqlUtil.buildOrderByStr("A",params);
			if(!StringHandler.isValidStr(orderbyStr) || orderbyStr.length() == 0){
				orderbyStr = "A."+entityPKname+" desc";
			}
			hql += SqlUtil.buildWhereStr("A",params,hasWhereKeyWords);
//			SqlUtil.WhereCondition condition = SqlUtil.setWhereValue("A", params);
//			if(null != condition && StringHandler.isValidStr(condition.whereStr)){
//				hql += condition.whereStr;
//				values = condition.values;
//			}
			
			long totalCount = -1;
			if(hasTotalCount) totalCount = getTotalCount(hql);
			hql += " order by "+orderbyStr;
			Log.info("hql="+hql);
			List<T> list = findByPage(hql,offset,pageSize);
			DataTable dt = getDTByEntityList(list);
			if(null != dt && dt.getRowCount()>0 && totalCount>0){
				dt.setSize(totalCount);
			}
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	/**
	 * 还款手续费收取记录流水
	 */

	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try {
			boolean hasTotalCount = true;
			if(null != params && params.getMap().containsKey(SqlUtil.TOTALCOUNT_KEY)){
				hasTotalCount = params.getvalAsBln(SqlUtil.TOTALCOUNT_KEY);
				params.removes(SqlUtil.TOTALCOUNT_KEY);
			}
			String hql = " from "+entityName+" A ";
			boolean hasWhereKeyWords = false;
			if(isExtendBeaseEntiy()){
				hql += " where A.isenabled != '"+SysConstant.OPTION_DEL+"' ";
			}else{
				//hql += " where 1=1 ";
				hasWhereKeyWords = true;
			}
//			Object[] values = null;
			String orderbyStr = SqlUtil.buildOrderByStr("A",params);
			if(!StringHandler.isValidStr(orderbyStr) || orderbyStr.length() == 0){
				orderbyStr = "A."+entityPKname+" desc";
			}
			hql += SqlUtil.buildWhereStr("A",params,hasWhereKeyWords);
//			SqlUtil.WhereCondition condition = SqlUtil.setWhereValue("A", params);
//			if(null != condition && StringHandler.isValidStr(condition.whereStr)){
//				hql += condition.whereStr;
//				values = condition.values;
//			}
			long totalCount = -1;
			if(hasTotalCount) totalCount = getTotalCount(hql);
			hql += " order by "+orderbyStr;
			Log.info("hql="+hql);
			List<T> list = findByPage(hql,offset,pageSize);
			DataTable dt = getDTByEntityList(list);
			if(null != dt && dt.getRowCount()>0 && totalCount>0){
				dt.setSize(totalCount);
			}
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	
	public long getTotalCount(String hql) {
		String sql = toSql(hql);
		return getTotalCountBySql(sql);
	}

	public long getTotalCountBySql(String sql) {
		sql = "SELECT count(1) FROM ("+sql+") TOTAL_TAB ";
		Object result = getSession().createSQLQuery(sql).uniqueResult();
		Integer totalCount = (Integer)result;
		return (null == totalCount) ? 0l : totalCount.longValue();
	}
	

	/**
	 * 还款手续费收取记录流水
	 */

	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> map)
			throws DaoException {
		try {
			List<T> list = getEntityList(map);
			DataTable dt = getDTByEntityList(list);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws DaoException {
		try {
			List<T> list = getEntityList(map);
			DataTable dt = getDTByEntityList(list);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	

	@Override
	public DataTable getResultList(String hql, int offset, int pageSize)
			throws DaoException {
		try {
			long totalCount = getTotalCount(hql);
			DataTable dt = findByPage(hql,new int[]{offset,pageSize});
			dt.setSize(totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	public DataTable getResultList(String hql,String totalHql, int offset, int pageSize)
			throws DaoException {
		try {
			long totalCount = getTotalCount(totalHql);
			DataTable dt = findByPage(hql,new int[]{offset,pageSize});
			dt.setSize(totalCount);
			return dt;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	

	@Override
	public <K, V> long getTotals(SHashMap<K, V> param)
			throws DaoException {
		try {
			String whereStr = SqlUtil.buildWhereStr("A", param);
			List<Long> list = (List<Long>)getSession().createQuery("select count(A."+entityPKname+") from "+entityName+" A "+whereStr).list();
			return (null == list || list.size()==0) ? 0 : list.get(0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public Long getTotals() throws DaoException {
		try {
			List<Long> list = (List<Long>)getSession().createQuery("select count(A."+entityPKname+") from "+entityName+" A order by A."+entityPKname+" desc").list();
			return (null == list || list.size()==0) ? 0 : list.get(0);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public PK saveEntity(T obj) throws DaoException {
		try {
			PK p = (PK)getSession().save(obj);
			return p;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public void saveOrUpdateEntity(T obj) throws DaoException {
		try {
			getSession().saveOrUpdate(obj);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public void updateEntity(T obj) throws DaoException {
		try {
			getSession().update(obj);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 执行HQL语句，进行插入、删除、更新操作
	 * @param hql hql语句
	 * @throws DaoException
	 */
	public void executeHql(String hql) throws DaoException{
		try {
			Query query = getSession().createQuery(hql);
			query.executeUpdate();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	/**
	 * 执行SQL语句，进行插入、删除、更新操作
	 * @param sql sql语句
	 * @throws DaoException
	 */
	public void executeSql(String sql) throws DaoException{
		try {
			Query query = getSession().createSQLQuery(sql);
			query.executeUpdate();
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public List<PK> batchSaveEntitys(List<T> objs) throws DaoException {
		List<PK> list = new ArrayList<PK>();
		int i=0;
		Session session = getSession();
		//session.beginTransaction();
		for(T obj : objs){
			session.save(obj);
			if(i%batchSize==0){	// 50 个提交一次
				session.flush();
				session.clear();
			}
			i++;
		}
	//	session.getTransaction().commit();
		return list;
	}

	@Override
	public void batchSaveOrUpdateEntitys(List<T> objs) throws DaoException {
		int i=0;
		Session session = getSession();
		
		for(T obj : objs){
			session.saveOrUpdate(obj);
			if(i%batchSize==0){	// 50 个提交一次
				session.flush();
				session.clear();
			}
			i++;
		}
	}

	@Override
	public void batchUpdateEntitys(List<T> objs) throws DaoException {
		int i=0;
		Session session = getSession();
		
		for(T obj : objs){
			session.update(obj);
			if(i%batchSize==0){	// 50 个提交一次
				session.flush();
				session.clear();
			}
			i++;
		}
	}

	/**
	 * 判断实体类是否继承了 IdBaseEntity 或  BaseEntity
	 * @return  true : 继承  IdBaseEntity 或  BaseEntity， false : 没有继承  IdBaseEntity 或  BaseEntity
	 */
	@SuppressWarnings("rawtypes")
	private boolean isExtendBeaseEntiy(){
		Class[] classes = GenericsUtil.getSupClasses(entityClass);
		for(Class cls : classes){
			if(cls.getSimpleName().equals(IdBaseEntity.class.getSimpleName()) ||
				cls.getSimpleName().equals(BaseEntity.class.getSimpleName()))
				return true;
		}
		return false;
	}
	
	/**
	 * 设置批处理大小
	 * @param batchSize  批处理大小,默认为50个批量提交一次
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
}
