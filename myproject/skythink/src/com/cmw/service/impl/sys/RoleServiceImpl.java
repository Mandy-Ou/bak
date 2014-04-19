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
import com.cmw.dao.inter.sys.RoleDaoInter;
import com.cmw.entity.sys.RoleEntity;
import com.cmw.service.impl.cache.RoleCache;
import com.cmw.service.inter.sys.RoleService;


/**
 * 角色  Service实现类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="角色业务实现类",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Service("roleService")
public class RoleServiceImpl extends AbsService<RoleEntity, Long> implements  RoleService {
	@Autowired
	private RoleDaoInter roleDao;
	@Override
	public GenericDaoInter<RoleEntity, Long> getDao() {
		return roleDao;
	}
	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		RoleCache.removeRoles(id.toString());
	}
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		RoleCache.putRoles(null);
	}
	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		RoleCache.removeRoles(StringHandler.join(ids));
	}
	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		RoleCache.removeRoles(ids);
	}
	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
		RoleCache.removeRoles(id.toString());
	}
	
	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		RoleCache.removeRoles(StringHandler.join(ids));
	}
	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
	}
	@Override
	public RoleEntity getEntity(Long id) throws ServiceException {
		RoleEntity role = RoleCache.getRole(id);
		if(null != role) return role;
		return super.getEntity(id);
	}
	

	@Override
	public Long saveEntity(RoleEntity obj) throws ServiceException {
		Long id = super.saveEntity(obj);
		RoleCache.addRole(obj);
		return id;
	}
	@Override
	public void saveOrUpdateEntity(RoleEntity obj) throws ServiceException {
		boolean isUpdate = obj.getId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			RoleCache.updateRole(obj);
		}else{
			RoleCache.addRole(obj);
		}
	}
	@Override
	public void updateEntity(RoleEntity obj) throws ServiceException {
		super.updateEntity(obj);
		RoleCache.updateRole(obj);
	}
}
