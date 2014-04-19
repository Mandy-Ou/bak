package com.cmw.service.impl.sys;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.sys.UserDaoInter;
import com.cmw.entity.sys.SysRightEntity;
import com.cmw.entity.sys.UroleEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.sys.SysRightService;
import com.cmw.service.inter.sys.UroleService;
import com.cmw.service.inter.sys.UserService;


/**
 * 用户  Service实现类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="用户业务实现类",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Service("userService")
public class UserServiceImpl extends AbsService<UserEntity, Long> implements  UserService {
	@Autowired
	private UserDaoInter userDao;
	
	@Resource(name="uroleService")
	private UroleService uroleService;
	
	@Resource(name="sysRightService")
	private SysRightService sysRightService;
	
	
	
	@Override
	public GenericDaoInter<UserEntity, Long> getDao() {
		return userDao;
	}
	@Override
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		UserEntity userEntity = (UserEntity)complexData.get("userEntity");
		boolean isEdit = (null != userEntity.getUserId());
		if(null == userEntity.getIncompId()){
			
		}
		this.saveOrUpdateEntity(userEntity);
		String uroles = (String)complexData.get("uroles");
		
		Long userId = userEntity.getUserId();
		if(isEdit){	//如果修改,先清理用户之前的旧数据
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("userId", userId);
			uroleService.deleteEntitys(params);
		}
		if(!StringHandler.isValidStr(uroles)) return;
		
		List<UroleEntity> objs = createUroles(userId, uroles);
		if(null == objs || objs.size() == 0) return;
		uroleService.batchSaveEntitys(objs);
		saveSysRightDatas(userId,uroles);
	}
	
	private List<UroleEntity> createUroles(Long userId, String uroles){
		String[] arr = uroles.split(",");
		List<UroleEntity> entitys = new ArrayList<UroleEntity>(arr.length);
		UroleEntity entity = null;
		for(String roleId : arr){
			entity = new UroleEntity();
			entity.setUserId(userId);
			entity.setRoleId(Long.parseLong(roleId));
			entitys.add(entity);
		}
		return entitys;
	}
	
	/**
	 * 保存系统权限数据
	 * @param userId 
	 * @param uroles 角色ID字符串列表
	 * @throws ServiceException
	 */
	private void saveSysRightDatas(Long userId, String uroles) throws ServiceException{
		/*----> Step 1 删除当前用户的旧系统权限数据 <----*/
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("objtype", SysRightEntity.OBJTYPE_1);
		map.put("objId", userId);
		sysRightService.deleteEntitys(map);
		
		/*----> Step 2 找出用户角色所拥有的 系统ID <----*/
		map.put("objtype", SysRightEntity.OBJTYPE_0);
		map.put("objId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + uroles);
		List<SysRightEntity> sysRights = sysRightService.getEntityList(map);
		if(null == sysRights || sysRights.size() == 0) return;
		
		Map<Long,Long> sysMap = new HashMap<Long, Long>();
		for(SysRightEntity srEntity : sysRights){
			Long sysId = srEntity.getSysId();
			if(sysMap.containsKey(sysId)) continue;
			sysMap.put(sysId, sysId);
		}
		if(sysMap.size() == 0) return;
		
		/*----> Step 3 保存用户系统权限数据 <----*/
		Set<Long> keys = sysMap.keySet();
		List<SysRightEntity> usrSysRights = new ArrayList<SysRightEntity>();
		for(Long sysId : keys){
			SysRightEntity sysRightEntity = new SysRightEntity();
			sysRightEntity.setObjtype(SysRightEntity.OBJTYPE_1);
			sysRightEntity.setObjId(userId);
			sysRightEntity.setSysId(sysId);
			usrSysRights.add(sysRightEntity);
		}
		sysRightService.batchSaveEntitys(usrSysRights);
	}
	
	@Override
	public void deleteEntity(Long id) throws ServiceException {
		super.deleteEntity(id);
		UserCache.removeUsers(id.toString());
	}
	
	@Override
	public <K, V> void enabledEntitys(SHashMap<K, V> params, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(params, isenabled);
		UserCache.putUsers(null);
	}
	@Override
	public void enabledEntitys(String ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		UserCache.removeUsers(ids);
	}
	@Override
	public void deleteEntitys(Long[] ids) throws ServiceException {
		super.deleteEntitys(ids);
		UserCache.removeUsers(StringHandler.join(ids));
	}
	@Override
	public void deleteEntitys(String ids) throws ServiceException {
		super.deleteEntitys(ids);
		UserCache.removeUsers(ids);
	}
	
	@Override
	public <K, V> void deleteEntitys(SHashMap<K, V> params)
			throws ServiceException {
		super.deleteEntitys(params);
		UserCache.putUsers(null);
	}
	
	@Override
	public void enabledEntity(Long id, Integer isenabled)
			throws ServiceException {
		super.enabledEntity(id, isenabled);
	  UserCache.removeUsers(id.toString());
	}
	
	@Override
	public void enabledEntitys(Long[] ids, Integer isenabled)
			throws ServiceException {
		super.enabledEntitys(ids, isenabled);
		UserCache.removeUsers(StringHandler.join(ids));
	}
	@Override
	public Long saveEntity(UserEntity obj) throws ServiceException {
		Long userId = super.saveEntity(obj);
		UserCache.addUser(obj);
		return userId;
	}
	@Override
	public void saveOrUpdateEntity(UserEntity obj) throws ServiceException {
		boolean isUpdate = obj.getUserId() != null;
		super.saveOrUpdateEntity(obj);
		if(isUpdate){
			UserCache.updateUser(obj);
		}else{
			UserCache.addUser(obj);
		}
	}
	@Override
	public void updateEntity(UserEntity obj) throws ServiceException {
		super.updateEntity(obj);
		UserCache.updateUser(obj);
	}
	@Override
	public <K, V> DataTable getSerch(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		return userDao.getSerch(params, offset, pageSize);
	}
	
	
}
