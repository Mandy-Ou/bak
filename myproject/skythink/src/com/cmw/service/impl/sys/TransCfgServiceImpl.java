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

import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.dao.inter.sys.TransCfgDaoInter;
import com.cmw.service.impl.cache.TransCfgCache;
import com.cmw.service.impl.workflow.BussFlowCacheManager;
import com.cmw.service.inter.sys.TransCfgService;


/**
 * 流转路径配置  Service实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="流转路径配置业务实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Service("transCfgService")
public class TransCfgServiceImpl extends AbsService<TransCfgEntity, Long> implements  TransCfgService {
	@Autowired
	private TransCfgDaoInter transCfgDao;
	@Override
	public GenericDaoInter<TransCfgEntity, Long> getDao() {
		return transCfgDao;
	}
	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		TransCfgCache.removes(id.toString());
	}
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		TransCfgCache.puts(null);
	}
	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		TransCfgCache.removes(StringHandler.join(ids));
	}
	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		TransCfgCache.removes(ids);
	}
	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
		TransCfgCache.removes(id.toString());
	}
	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(params, isenabled);
		TransCfgCache.puts(null);
	}
	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		TransCfgCache.removes(StringHandler.join(ids));
	}
	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		TransCfgCache.removes(ids);
	}
	@Override
	public Long saveEntity(TransCfgEntity obj) throws ServiceException {
		Long id = super.saveEntity(obj);
		TransCfgCache.add(obj);
		return id;
	}
	@Override
	public void saveOrUpdateEntity(TransCfgEntity obj) throws ServiceException {
		boolean isUpdate = obj.getId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			TransCfgCache.update(obj);
		}else{
			TransCfgCache.add(obj);
		}
	}
	@Override
	public void updateEntity(TransCfgEntity obj) throws ServiceException {
		super.updateEntity(obj);
		TransCfgCache.update(obj);
	}
	@Override
	public List<Long> batchSaveEntitys(List<TransCfgEntity> objs)
			throws ServiceException {
		List<Long> idList = super.batchSaveEntitys(objs);
		TransCfgCache.removes(objs);
		TransCfgCache.add(objs);
		BussFlowCacheManager.getInstance().updateNextTransInfos(objs);
		return idList;
	}
	
	
}
