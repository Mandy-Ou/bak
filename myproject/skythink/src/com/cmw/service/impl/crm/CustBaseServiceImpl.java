package com.cmw.service.impl.crm;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.dao.inter.crm.CustBaseDaoInter;
import com.cmw.entity.crm.CustBaseEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.crm.CustBaseService;


/**
 * 客户基础信息  Service实现类
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="客户基础信息业务实现类",createDate="2012-12-12T00:00:00",author="程明卫")
@Service("custBaseService")
public class CustBaseServiceImpl extends AbsService<CustBaseEntity, Long> implements  CustBaseService {
	@Autowired
	private CustBaseDaoInter custBaseDao;
	@Override
	public GenericDaoInter<CustBaseEntity, Long> getDao() {
		return custBaseDao;
	}
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = custBaseDao.getResultList(new SHashMap<String, Object>(params));
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	@Override
	public <K, V> DataTable getDialogResultList(SHashMap<K, V> map,final int offset, final int pageSize)
			throws ServiceException {
		try {
			return custBaseDao.getDialogResultList(map,offset,pageSize);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	@Override
	public <K, V> List<CustBaseEntity> getSynsCustBaseList(SHashMap<K, V> map)
			throws ServiceException {
		try {
			return custBaseDao.getSynsCustBaseList(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	@Override
	public void updateCustBaseRefIds(SHashMap<String, Object> refMap,UserEntity currUser)
			throws ServiceException {
		try {
			custBaseDao.updateCustBaseRefIds(refMap, currUser);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

}
