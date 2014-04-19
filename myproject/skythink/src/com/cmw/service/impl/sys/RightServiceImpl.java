package com.cmw.service.impl.sys;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;

import com.cmw.entity.sys.RightEntity;
import com.cmw.entity.sys.SysRightEntity;
import com.cmw.dao.inter.sys.RightDaoInter;
import com.cmw.service.inter.sys.RightService;
import com.cmw.service.inter.sys.SysRightService;


/**
 * 角色菜单权限  Service实现类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="角色菜单权限业务实现类",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Service("rightService")
public class RightServiceImpl extends AbsService<RightEntity, Long> implements  RightService {
	@Autowired
	private RightDaoInter rightDao;
	
	@Resource(name="sysRightService")
	private SysRightService sysRightService;
	@Override
	public GenericDaoInter<RightEntity, Long> getDao() {
		return rightDao;
	}
	
	@Transactional
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		Long sysId = (Long)complexData.get("sysId");
		Long roleId = (Long)complexData.get("roleId");
		String accordionRights = (String)complexData.get("accordionRights");
		String menuRights = (String)complexData.get("menuRights");
		String moduleRights = (String)complexData.get("moduleRights");
		try {
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("roleId", roleId);
			params.put("sysId", sysId);
			params.put("objtype", RightEntity.OBJTYPE_0);
			this.deleteEntitys(params);	//--> 先删除权限数据
			
			List<RightEntity> list = createRightEntitys(sysId, roleId, accordionRights, RightEntity.TYPE_0);
			boolean insertSysRight = false;
			if(null != list && list.size() > 0){/*卡片数据插入*/
				rightDao.batchSaveEntitys(list);
				insertSysRight = true;
			} 
			
			list = createRightEntitys(sysId, roleId, menuRights, RightEntity.TYPE_1);
			if(null != list && list.size() > 0) rightDao.batchSaveEntitys(list);
			
			list = createRightEntitys(sysId, roleId, moduleRights, RightEntity.TYPE_2);
			if(null != list && list.size() > 0) rightDao.batchSaveEntitys(list);
			
			if(!insertSysRight) return;
			saveSysRightData(SysRightEntity.OBJTYPE_0,roleId,sysId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(ServiceException.OBJECT_BATCH_SAVE_FAILURE);
		}
	}
	
	/**
	 * 保存系统权限数据
	 * @param objtype
	 * @param objId
	 * @param sysId
	 * @throws ServiceException
	 */
	private void saveSysRightData(Integer objtype, Long objId, Long sysId) throws ServiceException{
		/*----> Step 1 删除当前角色的旧系统权限数据 <----*/
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("objtype", objtype);
		map.put("objId", objId);
		map.put("sysId", sysId);
		sysRightService.deleteEntitys(map);
		
		SysRightEntity sysRightEntity = new SysRightEntity();
		sysRightEntity.setObjtype(objtype);
		sysRightEntity.setObjId(objId);
		sysRightEntity.setSysId(sysId);
		sysRightService.saveEntity(sysRightEntity);
	}
	
	private List<RightEntity> createRightEntitys(Long sysId,Long roleId,String rightData,int type){
		if(!StringHandler.isValidStr(rightData)) return null;
		List<RightEntity> list = null;
		String[] rightIds = rightData.split(",");
		list = new ArrayList<RightEntity>(rightIds.length);
		RightEntity entity = null;
		for(String rightId : rightIds){
			entity = new RightEntity();
			entity.setSysId(sysId);
			entity.setRoleId(roleId);
			entity.setObjtype(RightEntity.OBJTYPE_0);
			entity.setMmId(Long.parseLong(rightId));
			entity.setType(type);
			list.add(entity);
		}
		return list;
	}
}
