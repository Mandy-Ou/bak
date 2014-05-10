package com.cmw.action.sys;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.groovy.runtime.StringBufferWriter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.RoleEntity;
import com.cmw.entity.sys.UroleEntity;
import com.cmw.service.inter.sys.RoleService;
import com.cmw.service.inter.sys.UroleService;


/**
 * 用户角色关联  ACTION类
 * @author chengmingwei
 * @date 2012-12-08T00:00:00
 */
@Description(remark="用户角色关联ACTION",createDate="2012-12-08T00:00:00",author="chengmingwei",defaultVals="sysUrole_")
@SuppressWarnings("serial")
public class UroleAction extends BaseAction {
	@Resource(name="uroleService")
	private UroleService uroleService;
	
	@Resource(name="roleService")
	private RoleService roleService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 用户角色关联 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			List<RoleEntity> roles = roleService.getEntityList();
			List<UroleEntity> uRoles = null;
			Long userId = getLVal("userId");
			if(null != userId){
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				//map.put(SysConstant.USER_KEY, this.getCurUser());
				map.put("userId", userId);
				uRoles = uroleService.getEntityList(map);
			}
			result = getRoleCheckBoxItems(roles, uRoles);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 *  打印角色信息
	 */
	@SuppressWarnings("unused")
	public String role()throws Exception {
		try {
			List<RoleEntity> roles = roleService.getEntityList();
			List<UroleEntity> uRoles = null;
			Long userId = getLVal("userId");
			
			if(null != userId){
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				map.put(SysConstant.USER_KEY, this.getCurUser());
				map.put("userId", userId);
				uRoles = uroleService.getEntityList(map);
			}
			
			StringBuffer bf=new StringBuffer();
			if(uRoles!= null){
			for(RoleEntity role : roles){
					for(UroleEntity uRole : uRoles){
						if(role.getId().equals(uRole.getRoleId())){
							String roleName= role.getName();
							bf.append(roleName+",");
						}
					}
				}
			}
			if(null == bf){
				return null;
			}else{
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("roles", StringHandler.RemoveStr(bf,","));
				 result =ResultMsg.getSuccessMsg(map);
			}
			
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	} 
	
	
	
	private String getRoleCheckBoxItems(List<RoleEntity> roles,List<UroleEntity> uRoles){
		String json = "[]";
		if(null == roles || roles.size() == 0) return json;
		JSONArray arr = new JSONArray(roles.size());
		JSONObject obj = null;
		if(null == uRoles || uRoles.size() == 0){
			for(RoleEntity role : roles){
				obj = createObj(role,false);
				arr.add(obj);
			}
		}else{
			for(RoleEntity role : roles){
				boolean flag = false;
				for(UroleEntity uRole : uRoles){
					if(role.getId().equals(uRole.getRoleId())){
						flag = true;
						break;
					}
				}
				obj = createObj(role,flag);
				arr.add(obj);
			}
		}
		json = arr.toJSONString();
		return json;
	}

	private JSONObject createObj(RoleEntity role,boolean flag) {
		JSONObject obj = new JSONObject();
		obj.put("boxLabel", role.getName());
		obj.put("inputValue", role.getId());
		obj.put("checked", flag);
		return obj;
	}
	
	
	/**
	 * 获取 用户角色关联 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			UroleEntity entity = uroleService.getEntity(Long.parseLong(id));
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
	 * 保存 用户角色关联 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			UroleEntity entity = BeanUtil.copyValue(UroleEntity.class,getRequest());
			uroleService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增  用户角色关联 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = uroleService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("U", num);
			result = JsonUtil.getJsonString("code",code);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	  
	
	/**
	 * 删除  用户角色关联 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			uroleService.enabledEntity(Long.parseLong(id), SysConstant.OPTION_DEL);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 启用  用户角色关联 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			uroleService.enabledEntity(Long.parseLong(id), SysConstant.OPTION_ENABLED);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 禁用  用户角色关联 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			uroleService.enabledEntity(Long.parseLong(id), SysConstant.OPTION_DISABLED);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 用户角色关联 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			UroleEntity entity = uroleService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			result = ResultMsg.getSuccessMsg(entity, appendParams);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 用户角色关联 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			UroleEntity entity = uroleService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			result = ResultMsg.getSuccessMsg(entity, appendParams);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
}
