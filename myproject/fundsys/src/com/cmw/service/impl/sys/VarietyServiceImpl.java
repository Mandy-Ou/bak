package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;

import com.cmw.entity.sys.VarietyEntity;
import com.cmw.dao.inter.sys.VarietyDaoInter;
import com.cmw.service.impl.cache.RoleCache;
import com.cmw.service.impl.cache.VarietyCache;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 业务品种  Service实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="业务品种业务实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Service("varietyService")
public class VarietyServiceImpl extends AbsService<VarietyEntity, Long> implements  VarietyService {
	@Autowired
	private VarietyDaoInter varietyDao;
	@Override
	public GenericDaoInter<VarietyEntity, Long> getDao() {
		return varietyDao;
	}
	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		VarietyCache.removeVarietys(id.toString());
	}
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		VarietyCache.putVarietys(null);
	}
	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		VarietyCache.removeVarietys(StringHandler.join(ids));
	}
	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		VarietyCache.removeVarietys(ids);
	}
	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
		VarietyCache.removeVarietys(id.toString());
	}
	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(params, isenabled);
		VarietyCache.putVarietys(null);
	}
	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		VarietyCache.removeVarietys(StringHandler.join(ids));
	}
	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		if(null != isenabled && isenabled.intValue() != SysConstant.OPTION_ENABLED) VarietyCache.removeVarietys(ids);
	}
	@Override
	public Long saveEntity(VarietyEntity obj) throws ServiceException {
		Long id = super.saveEntity(obj);
		VarietyCache.addVariety(obj);
		return id;
	}
	@Override
	public void saveOrUpdateEntity(VarietyEntity obj) throws ServiceException {
		boolean isUpdate = obj.getId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			VarietyCache.updateVariety(obj);
		}else{
			VarietyCache.addVariety(obj);
		}
	}
	@Override
	public void updateEntity(VarietyEntity obj) throws ServiceException {
		super.updateEntity(obj);
		VarietyCache.updateVariety(obj);
	}
	
	
}
