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
import com.cmw.dao.inter.sys.BussTransDaoInter;
import com.cmw.entity.sys.BussTransEntity;
import com.cmw.service.impl.cache.BussNodeCache;
import com.cmw.service.impl.cache.BussTransCache;
import com.cmw.service.inter.sys.BussTransService;


/**
 * 流转路径  Service实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="流转路径业务实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Service("bussTransService")
public class BussTransServiceImpl extends AbsService<BussTransEntity, Long> implements  BussTransService {
	@Autowired
	private BussTransDaoInter bussTransDao;
	@Override
	public GenericDaoInter<BussTransEntity, Long> getDao() {
		return bussTransDao;
	}
	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		BussTransCache.removes(id.toString());
	}
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		BussTransCache.puts(null);
	}
	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		BussTransCache.removes(StringHandler.join(ids));
	}
	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		BussTransCache.removes(ids);
	}
	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
		BussTransCache.removes(id.toString());
	}
	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(params, isenabled);
		BussTransCache.puts(null);
	}
	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		BussTransCache.removes(StringHandler.join(ids));
	}
	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		BussTransCache.removes(ids);
	}
	@Override
	public Long saveEntity(BussTransEntity obj) throws ServiceException {
		Long id = super.saveEntity(obj);
		BussTransCache.add(obj);
		return id;
	}
	@Override
	public void saveOrUpdateEntity(BussTransEntity obj) throws ServiceException {
		boolean isUpdate = obj.getId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			BussTransCache.update(obj);
		}else{
			BussTransCache.add(obj);
		}
	}
	@Override
	public void updateEntity(BussTransEntity obj) throws ServiceException {
		super.updateEntity(obj);
		BussTransCache.update(obj);
	}
	@Override
	public List<Long> batchSaveEntitys(List<BussTransEntity> objs)
			throws ServiceException {
		List<Long> idList = super.batchSaveEntitys(objs);
		BussTransCache.add(objs);
		return idList;
	}

	
	
}
