package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.CountersignCfgDaoInter;
import com.cmw.entity.sys.CountersignCfgEntity;
import com.cmw.service.impl.cache.CountersignCfgCache;
import com.cmw.service.inter.sys.CountersignCfgService;


/**
 * 会签配置  Service实现类
 * @author 程明卫
 * @date 2013-11-22T00:00:00
 */
@Description(remark="会签配置业务实现类",createDate="2013-11-22T00:00:00",author="程明卫")
@Service("countersignCfgService")
public class CountersignCfgServiceImpl extends AbsService<CountersignCfgEntity, Long> implements  CountersignCfgService {
	@Autowired
	private CountersignCfgDaoInter countersignCfgDao;
	@Override
	public GenericDaoInter<CountersignCfgEntity, Long> getDao() {
		return countersignCfgDao;
	}

	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		CountersignCfgCache.removes(id.toString());
	}
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		CountersignCfgCache.puts(null);
	}
	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		CountersignCfgCache.removes(StringHandler.join(ids));
	}
	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		CountersignCfgCache.removes(ids);
	}
	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
		CountersignCfgCache.removes(id.toString());
	}
	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(params, isenabled);
		CountersignCfgCache.puts(null);
	}
	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		CountersignCfgCache.removes(StringHandler.join(ids));
	}
	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		CountersignCfgCache.removes(ids);
	}
	@Override
	public Long saveEntity(CountersignCfgEntity obj) throws ServiceException {
		Long id = super.saveEntity(obj);
		CountersignCfgCache.add(obj);
		return id;
	}
	@Override
	public void saveOrUpdateEntity(CountersignCfgEntity obj) throws ServiceException {
		boolean isUpdate = obj.getId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			CountersignCfgCache.update(obj);
		}else{
			CountersignCfgCache.add(obj);
		}
	}
	@Override
	public void updateEntity(CountersignCfgEntity obj) throws ServiceException {
		super.updateEntity(obj);
		CountersignCfgCache.update(obj);
	}
	
}
