package com.cmw.action.sys;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.RightEntity;
import com.cmw.entity.sys.RoleEntity;
import com.cmw.entity.sys.TransCfgEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.RightService;
import com.cmw.service.inter.sys.RoleService;
import com.cmw.service.inter.sys.UserService;
import com.mysql.jdbc.authentication.Sha256PasswordPlugin;


/**
 * 角色  ACTION类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="角色ACTION",createDate="2011-09-24T00:00:00",author="chengmingwei",defaultVals="sysRole_")
@SuppressWarnings("serial")
public class RoleAction extends BaseAction {
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="rightService")
	private RightService rightService;
	
	private String result = ResultMsg.GRID_NODATA;
	
	/**
	 * 获取 角色 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("isenabled#I");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = roleService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 获取 角色或用户 列表
	 * @return
	 * @throws Exception
	 */
	public String ruList()throws Exception {
		try {
			Integer acType = getIVal("acType");
			String name = getVal("name");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("isenabled", (byte)1);
			DataTable dt = null;
			if(null != acType && acType.intValue() == TransCfgEntity.ACTYPE_1){
				map.put("name", name);
				dt = roleService.getResultList(map,getStart(),getLimit());
				result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
			}else{
				map.put("empName", name);
				dt = userService.getResultList(map,getStart(),getLimit());
				Map<String,String> repMap = new HashMap<String, String>();
				repMap.put("empName", "name");
				repMap.put("userId", "id");
				String[] outCmns = {"id","name"};
				result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr(repMap, outCmns);
			}
		
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 角色 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			RoleEntity entity = roleService.getEntity(Long.parseLong(id));
			result = JsonUtil.toJson(entity);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 角色 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			UserEntity user = getCurUser();
			RoleEntity entity = BeanUtil.copyValue(RoleEntity.class,getRequest());
			String name = entity.getName();
			Long id = entity.getId();
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("name", name);
			params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
			List<RoleEntity> rentity  = roleService.getEntityList(params);
			
			boolean flag = false;
			if(!rentity.isEmpty() && rentity.size()>0){
				for(RoleEntity rEntity : rentity){
					String existName = rEntity.getName();
					if(existName.equals(name)){
						Long roleid = rEntity.getId();
						if(roleid != id){
							flag = true;
						}
						break;
					}
				}
			}
			
			
			if(flag){
				Map<String,Object> appendParams = new HashMap<String, Object>();
				appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "role.have", name));
				result = ResultMsg.getFailureMsg(appendParams);
			}else{
				roleService.saveOrUpdateEntity(entity);
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 角色权限拷贝 
	 * @return
	 * @throws Exception
	 */
	public String copy()throws Exception {
		try {
			UserEntity user = getCurUser();
			RoleEntity entity = BeanUtil.copyValue(RoleEntity.class,getRequest());
			String name = entity.getName();
			SHashMap<String, Object> Params = new SHashMap<String, Object>();
			Params.put("name", name);
			RoleEntity rentity  = roleService.getEntity(Params);
			
			if(null != rentity){
				String rname = rentity.getName();
				Map<String,Object> appendParams = new HashMap<String, Object>();
				appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "role.have", rname));
				result = ResultMsg.getFailureMsg(appendParams);
			}else{
				roleService.saveOrUpdateEntity(entity);
				
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				Long oldRoleId = getLVal("oldid");
				params.put("roleId", oldRoleId);
				Long roleId = entity.getId();
				//--->获取旧角色的权限数据
				List<RightEntity> rightEntitys = rightService.getEntityList(params);
				if(null != rightEntitys && rightEntitys.size() > 0){
					for(RightEntity rightEntity : rightEntitys){
						rightEntity.setId(null);
						rightEntity.setRoleId(roleId);
					}
					rightService.batchSaveEntitys(rightEntitys);
				}
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 新增  角色 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = roleService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("R", num);
			Long roleId = getLVal("roleId");
			Map<String,Object> oldMap = new HashMap<String, Object>();
			if(StringHandler.isValidObj(roleId)){
				RoleEntity role = roleService.getEntity(roleId);
				oldMap.put("oldid",role.getId());
				oldMap.put("oldcode",role.getCode());
				oldMap.put("oldname",role.getName());
				oldMap.put("oldremark",role.getRemark());
			}
			oldMap.put("code",code);
			result = JsonUtil.getJsonString(oldMap);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 删除  角色 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用 角色
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  角色 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 起用角色
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			roleService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 角色 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RoleEntity entity = roleService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 角色 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RoleEntity entity = roleService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
}
