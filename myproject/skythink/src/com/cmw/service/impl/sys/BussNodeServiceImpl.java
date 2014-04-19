package com.cmw.service.impl.sys;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.BussNodeDaoInter;
import com.cmw.entity.sys.BussNodeEntity;
import com.cmw.service.impl.cache.BussNodeCache;
import com.cmw.service.inter.sys.BussNodeService;


/**
 * 业务流程节点  Service实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="业务流程节点业务实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Service("bussNodeService")
public class BussNodeServiceImpl extends AbsService<BussNodeEntity, Long> implements  BussNodeService {
	@Autowired
	private BussNodeDaoInter bussNodeDao;
	@Override
	public GenericDaoInter<BussNodeEntity, Long> getDao() {
		return bussNodeDao;
	}
	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		BussNodeCache.removeBussNodes(id.toString());
	}
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		BussNodeCache.putBussNodes(null);
	}
	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		BussNodeCache.removeBussNodes(StringHandler.join(ids));
	}
	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		BussNodeCache.removeBussNodes(ids);
	}
	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
		BussNodeCache.removeBussNodes(id.toString());
	}
	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(params, isenabled);
		BussNodeCache.putBussNodes(null);
	}
	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		BussNodeCache.removeBussNodes(StringHandler.join(ids));
	}
	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		BussNodeCache.removeBussNodes(ids);
	}
	@Override
	public Long saveEntity(BussNodeEntity obj) throws ServiceException {
		Long id = super.saveEntity(obj);
		BussNodeCache.addBussNode(obj);
		return id;
	}
	@Override
	public void saveOrUpdateEntity(BussNodeEntity obj) throws ServiceException {
		boolean isUpdate = obj.getId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			BussNodeCache.updateBussNode(obj);
		}else{
			BussNodeCache.addBussNode(obj);
		}
		
	}
	@Override
	public void updateEntity(BussNodeEntity obj) throws ServiceException {
		super.updateEntity(obj);
		BussNodeCache.updateBussNode(obj);
	}
	@Override
	public List<Long> batchSaveEntitys(List<BussNodeEntity> objs)
			throws ServiceException {
		List<Long> idList = super.batchSaveEntitys(objs);
		BussNodeCache.add(objs);
		return idList;
	}
}
