package com.cmw.action.sys;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.RoleModEntity;
import com.cmw.entity.sys.UroleEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.UserModEntity;
import com.cmw.service.inter.sys.DeskModService;
import com.cmw.service.inter.sys.RoleModService;
import com.cmw.service.inter.sys.UroleService;
import com.cmw.service.inter.sys.UserModService;


/**
 * 角色模块配置  ACTION类
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="角色模块配置ACTION",createDate="2013-03-08T00:00:00",author="程明卫",defaultVals="sysRoleMod_")
@SuppressWarnings("serial")
public class RoleModAction extends BaseAction {
	@Resource(name="deskModService")
	private DeskModService deskModService;
	
	@Resource(name="roleModService")
	private RoleModService roleModService;
	
	@Resource(name="userModService")
	private UserModService userModService;
	@Resource(name="uroleService")
	private UroleService uroleService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 角色模块配置 列表
	 * @return	
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("sysId#L,roleId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			JSONObject json = new JSONObject();
			DataTable deskModDt = deskModService.getResultList(map);
			if(null != deskModDt && deskModDt.getRowCount() > 0){
				JSONArray deskModArr = deskModDt.getJsonList();
				json.put("deskMods", deskModArr);
			}
			map.remove("sysId");
			DataTable dt = roleModService.getResultList(map);
			if(null != dt && dt.getRowCount() > 0){
				JSONArray roleModArr = dt.getJsonList();
				json.put("roleMods", roleModArr);
			}
			
			result = json.toJSONString();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取 角色模块配置 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long roleId = getLVal("roleId");
			Long sysId = getLVal("sysId");
			if(!StringHandler.isValidObj(roleId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			JSONObject json = new JSONObject();
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("roleId", roleId);
			map.put("sysId", sysId);
			DataTable dt = roleModService.getResultList(map);
			if(null != dt && dt.getRowCount() > 0){
				JSONArray roleModArr = dt.getJsonList();
				json.put("roleMods", roleModArr);
			}
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : json.toJSONString();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 保存 角色模块配置 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			Long roleId = getLVal("roleId");
			Long sysId = getLVal("sysId");
			String modeCodes = getVal("modeCodes");
			if(!StringHandler.isValidStr(modeCodes)) throw new ServiceException("modeCodes is null!");
			List<RoleModEntity> list = new ArrayList<RoleModEntity>();
			String[] codeArr = modeCodes.split(",");
			for(String code : codeArr){
				RoleModEntity entity = new RoleModEntity();
				entity.setSysId(sysId);
				entity.setRoleId(roleId);
				entity.setModCode(code);
				list.add(entity);
			}
			
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("roleId", roleId);
			params.put("sysId", sysId);
			roleModService.deleteEntitys(params);
			roleModService.batchSaveEntitys(list);
//			params.remove("sysId");
//			List<UroleEntity> urolEntityList  = uroleService.getEntityList(params);
//			LinkedList<UserModEntity> userModList= new LinkedList<UserModEntity>();
//			SHashMap<String, Object> delParams = new SHashMap<String, Object>();
//			if(!urolEntityList.isEmpty() && urolEntityList.size()>0){
//				for(UroleEntity x : urolEntityList){
//					Long userId = x.getUserId();
//					if(userId != null){
//						UserModEntity userMod = new UserModEntity();
//						delParams.put("userId", userId);
//						userMod.setUserId(userId);
//						userModList.add(userMod);
//					}
//				}
//				userModService.deleteEntitys(delParams);
//			}
//			
//			List<UserModEntity> needSaveList = new ArrayList<UserModEntity>();
//			if(!list.isEmpty() && list.size()>0){
//				if(!userModList.isEmpty() && userModList.size()>0){
//					Iterator<UserModEntity> userIteror = userModList.iterator();
//					while (userIteror.hasNext()) {
//						UserModEntity userMod = userIteror.next();
//						forLabel : for(RoleModEntity x : list){
//							String modCode = x.getModCode();
//							userMod.setModCode(modCode);
//							needSaveList.add(userMod);
//							list.remove(modCode);
//							break forLabel;
//						}
//					}
//				}
//				
//				userModService.batchSaveOrUpdateEntitys(needSaveList);
//			}
			
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增  角色模块配置 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = roleModService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("R", num);
			result = JsonUtil.getJsonString("code",code);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 删除  角色模块配置 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("roleId#L,sysId#L");
			roleModService.deleteEntitys(params);
			result = ResultMsg.getSuccessMsg(this,ResultMsg.DELETE_SUCCESS);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 启用  角色模块配置 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  角色模块配置 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  角色模块配置 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			roleModService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this,sucessMsg);
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 角色模块配置 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RoleModEntity entity = roleModService.navigationPrev(params);
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
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 获取指定的 角色模块配置 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RoleModEntity entity = roleModService.navigationNext(params);
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
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
}
