package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.BussProccDaoInter;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.service.impl.cache.BussProccCache;
import com.cmw.service.inter.sys.BussProccService;


/**
 * 子业务流程  Service实现类
 * @author 程明卫
 * @date 2013-08-26T00:00:00
 */
@Description(remark="子业务流程业务实现类",createDate="2013-08-26T00:00:00",author="程明卫")
@Service("bussProccService")
public class BussProccServiceImpl extends AbsService<BussProccEntity, Long> implements  BussProccService {
	@Autowired
	private BussProccDaoInter bussProccDao;
	@Override
	public GenericDaoInter<BussProccEntity, Long> getDao() {
		return bussProccDao;
	}
	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		BussProccCache.removeBussProccs(id.toString());
	}
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		BussProccCache.putBussProccs(null);
	}
	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		BussProccCache.removeBussProccs(StringHandler.join(ids));
	}
	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		BussProccCache.removeBussProccs(ids);
	}
	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
		BussProccCache.removeBussProccs(id.toString());
	}
	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(params, isenabled);
		BussProccCache.putBussProccs(null);
	}
	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		BussProccCache.removeBussProccs(StringHandler.join(ids));
	}
	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		BussProccCache.removeBussProccs(ids);
	}
	@Override
	public Long saveEntity(BussProccEntity obj) throws ServiceException {
		Long id = super.saveEntity(obj);
		BussProccCache.addBussProcc(obj);
		return id;
	}
	@Override
	public void saveOrUpdateEntity(BussProccEntity obj) throws ServiceException {
		boolean isUpdate = obj.getId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			BussProccCache.updatBussProcc(obj);
		}else{
			BussProccCache.addBussProcc(obj);
		}
		
	}
	@Override
	public void updateEntity(BussProccEntity obj) throws ServiceException {
		super.updateEntity(obj);
		BussProccCache.updatBussProcc(obj);
	}
}
