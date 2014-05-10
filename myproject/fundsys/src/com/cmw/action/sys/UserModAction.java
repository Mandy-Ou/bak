package com.cmw.action.sys;


import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
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
 * 用户模块配置  ACTION类
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="用户模块配置ACTION",createDate="2013-03-08T00:00:00",author="程明卫",defaultVals="sysUserMod_")
@SuppressWarnings("serial")
public class UserModAction extends BaseAction {
	@Resource(name="uroleService")
	private UroleService uroleService;
	@Resource(name="userModService")
	private UserModService userModService;
	@Resource(name="deskModService")
	private DeskModService deskModService;
	@Resource(name="roleModService")
	private RoleModService roleModService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 用户模块配置 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = userModService.getResultList(map,getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	 * 获取 用户模块配置 列表
	 * @return
	 * @throws Exception
	 */
	public String getDatas()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("userId", user.getUserId());
			List<UroleEntity> uroles = uroleService.getEntityList(map);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			if(null == uroles || uroles.size() == 0){
				params.put("isdefault", 1);
			}else{
				StringBuffer sbRoles = new StringBuffer();
				for(UroleEntity urole : uroles){
					Long roleId = urole.getRoleId();
					sbRoles.append(roleId+",");
				}
				String roleIds = StringHandler.RemoveStr(sbRoles);
				SHashMap<String, Object> modParams = new SHashMap<String, Object>();
				modParams.put("roleId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + roleIds);
				List<RoleModEntity> roleMods = roleModService.getEntityList(modParams);
				StringBuffer modCodes = new StringBuffer();
				if(null != roleMods && roleMods.size() > 0){
					for(RoleModEntity roleMod : roleMods){
						String modCode = roleMod.getModCode();
						modCodes.append(modCode+",");
					}
				}
				String codes = StringHandler.RemoveStr(modCodes);
				if(StringHandler.isValidStr(codes)){
//					codes = "'"+modCodes+"'";
					params.put("code", codes);
				}
			}
			params = addFilterCodes(user,params);
			params.put(SysConstant.USER_KEY, user);
			DataTable dt = deskModService.getDeskModByCodes(params);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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

	private SHashMap<String, Object> addFilterCodes(UserEntity user, SHashMap<String, Object> params) throws ServiceException {
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("userId", user.getUserId());
		StringBuffer sb = new StringBuffer();
		List<UserModEntity> userMods = userModService.getEntityList(map);
		if(null != userMods && userMods.size() > 0){
			for(UserModEntity userMod : userMods){
				String modCode = userMod.getModCode();
				if(!StringHandler.isValidStr(modCode)) continue;
				sb.append(modCode+",");
			}
		}
		String codes = StringHandler.RemoveStr(sb);
		if(StringHandler.isValidStr(codes))	{
//			codes = "'"+codes+"'";
			params.put("notCodes", codes);
		}
		return params;
	}
	
	/**
	 * 获取 用户模块配置 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			UserModEntity entity = userModService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					
				}
			});
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
	 * 获取 模块内容数据 列表
	 * @return
	 * @throws Exception
	 */
//	public String getContents()throws Exception {
//		try {
//			UserEntity user = this.getCurUser();
//			Integer loadType = getIVal("loadType");
//			String dataCode = getVal("dataCode");
//			String dispcmns = getVal("dispcmns");
//
//			SHashMap<String, Object> map = new SHashMap<String, Object>();
//			map.put("loadType", loadType);
//			map.put("dataCode", dataCode);
//			map.put("dispcmns", dispcmns);
//			map.put(SysConstant.USER_KEY, user);
//			DataTable dt = deskModService.getContents(map);
//			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
//		} catch (ServiceException ex){
//			result = ResultMsg.getFailureMsg(this,ex.getMessage());
//			if(null == result) result = ex.getMessage();
//			ex.printStackTrace();
//		}catch (Exception ex){
//			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
//			if(null == result) result = ex.getMessage();
//			ex.printStackTrace();
//		}
//		outJsonString(result);
//		return null;
//	}
	
	/**
	 * 获取 模块内容数据 列表
	 * @return
	 * @throws Exception
	 */
	public String getContents()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			Integer loadType = getIVal("loadType");
			String dataCode = getVal("dataCode");
			String dispcmns = getVal("dispcmns");
			Integer msgCount = getIVal("msgCount");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("loadType", loadType);
			map.put("dataCode", dataCode);
			map.put("dispcmns", dispcmns);
			map.put("msgCount", msgCount);
			map.put(SysConstant.USER_KEY, user);
			DataTable dt = deskModService.getContents(map);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
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
	 * 删除  用户模块配置 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	
	/**
	 * 删除/起用/禁用  用户模块配置 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			userModService.enabledEntitys(ids, isenabled);
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
	
}
