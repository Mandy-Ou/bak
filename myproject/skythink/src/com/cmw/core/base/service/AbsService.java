package com.cmw.core.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.UserEntity;

public abstract class AbsService<T,PK extends Serializable> implements IService<T, PK> {
	protected Logger log = Logger.getLogger(this.getClass());
	/**
	 * 实现此类时，须重写此方法
	 * @param dao
	 */
	public abstract GenericDaoInter<T, PK> getDao();

	public AbsService() {
	}

	public void deleteEntity(PK id) throws ServiceException {
		try {
			if(null == id) throw new ServiceException(ServiceException.ID_IS_NULL);
			getDao().deleteEntity(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		try {
			if(null == params) throw new ServiceException(ServiceException.FILTER_PARAMS_NULL);
			getDao().deleteEntitys(params);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteEntitys(PK[] ids) throws ServiceException {
		try {
			if(null == ids || ids.length == 0) throw new ServiceException(ServiceException.IDS_IS_NULL);
			getDao().deleteEntitys(ids);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		try {
			if(!StringHandler.isValidStr(ids)) throw new ServiceException(ServiceException.IDS_IS_NULL);
			getDao().deleteEntitys(ids);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void enabledEntity(PK id, Integer isenabled) throws ServiceException {
		try {
			if(id == null) throw new ServiceException(ServiceException.ID_IS_NULL);
			getDao().enabledEntity(id, isenabled);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}


	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		try {
			if(null == params) throw new ServiceException(ServiceException.FILTER_PARAMS_NULL);
			getDao().enabledEntitys(params, isenabled);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void enabledEntitys(PK[] ids, Integer isenabled)
			throws ServiceException {
		try {
			if(null == ids || ids.length == 0) throw new ServiceException(ServiceException.IDS_IS_NULL);
			getDao().enabledEntitys(ids,isenabled);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		try {
			if(!StringHandler.isValidStr(ids)) throw new ServiceException(ServiceException.IDS_IS_NULL);
			getDao().enabledEntitys(ids, isenabled);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	
	@Override
	public void enabledEntitys(String ids, Integer isenabled, UserEntity opUser)
			throws ServiceException {
		try {
			if(!StringHandler.isValidStr(ids)) throw new ServiceException(ServiceException.IDS_IS_NULL);
			getDao().enabledEntitys(ids, isenabled,opUser);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public T getEntity(PK id) throws ServiceException {
		try {
			if(id == null) throw new ServiceException(ServiceException.ID_IS_NULL);
			return getDao().getEntity(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> T getEntity(SHashMap<K, V> params) throws ServiceException {
		try {
			if(null == params) throw new ServiceException(ServiceException.QUERY_PARAMS_NULL);
			return getDao().getEntity(params);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	/**
	 * 获取上一条记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> T navigationPrev(SHashMap<K, V> params)
			throws ServiceException {
		if(null == params) throw new ServiceException(ServiceException.QUERY_PARAMS_NULL);
		String id = (String)params.get((K)"id");
		if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
		params.put((K)GenericDaoInter.KEY_NAVIGATION, (V) new Integer(GenericDaoInter.VAL_NAV_PRE));
		try {
			return getDao().navigation(params);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 获取下一条记录
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <K, V> T navigationNext(SHashMap<K, V> params)
			throws ServiceException {
		if(null == params) throw new ServiceException(ServiceException.QUERY_PARAMS_NULL);
		String id = (String)params.get((K)"id");
		if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
		params.put((K)GenericDaoInter.KEY_NAVIGATION, (V) new Integer(GenericDaoInter.VAL_NAV_NEXT));
		try {
			return getDao().navigation(params);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> List<T> getEntityList(SHashMap<K, V> map)
			throws ServiceException {
		try {
			if(null == map) throw new ServiceException(ServiceException.QUERY_PARAMS_NULL);
			return getDao().getEntityList(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> List<T> getEntityList() throws ServiceException {
		try {
			return getDao().getEntityList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public Long getMaxID() throws ServiceException {
		try {
			return getDao().getMaxID();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> DataTable getResultList() throws ServiceException {
		try {
			return getDao().getResultList();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.QUERY_DATA_IS_NULL);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> map)
			throws ServiceException {
		try {
			return getDao().getResultList(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.QUERY_DATA_IS_NULL);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws ServiceException {
		try {
			return getDao().getResultList(params,offset,pageSize);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.QUERY_DATA_IS_NULL);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}


	
	
	
	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> map)throws ServiceException {
		try {
			return getDao().getLoanRecordsList(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.QUERY_DATA_IS_NULL);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> params, int offset,
			int pageSize) throws ServiceException {
		try {
			return getDao().getLoanRecordsList(params,offset,pageSize);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.QUERY_DATA_IS_NULL);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public Long getTotals() throws ServiceException {
		try {
			return getDao().getTotals();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_TOTAL_FAILURE);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> long getTotals(SHashMap<K, V> param) throws ServiceException {
		try {
			return getDao().getTotals(param);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_TOTAL_FAILURE);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public PK saveEntity(T obj) throws ServiceException {
		try {
			return getDao().saveEntity(obj);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_SAVE_FAILURE);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void saveOrUpdateEntity(T obj) throws ServiceException {
		try {
			 getDao().saveOrUpdateEntity(obj);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_SAVEORUPDATE_FAILURE);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void updateEntity(T obj) throws ServiceException {
		try {
			 getDao().updateEntity(obj);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_UPDATE_FAILURE);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public List<PK> batchSaveEntitys(List<T> objs) throws ServiceException {
		try {
			return getDao().batchSaveEntitys(objs);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_BATCH_SAVE_FAILURE);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void batchSaveOrUpdateEntitys(List<T> objs) throws ServiceException {
		try {
			getDao().batchSaveOrUpdateEntitys(objs);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_BATCH_SAVEORUPDATE_FAILURE);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void batchUpdateEntitys(List<T> objs) throws ServiceException {
		try {
			getDao().batchUpdateEntitys(objs);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_BATCH_UPDATE_FAILURE);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}


	@Override
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		throw new ServiceException("doComplexBusss 方法在 AbsService 类中未实现，请在实现类中重写此方法！");
	}

	@Override
	public Object doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
		throw new ServiceException("doComplexBusss 方法在 AbsService 类中未实现，请在实现类中重写此方法！");
	}
}
