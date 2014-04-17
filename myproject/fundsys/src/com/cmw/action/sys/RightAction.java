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
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.RightEntity;
import com.cmw.service.inter.sys.RightService;
import com.cmw.service.inter.sys.SysRightService;


/**
 * 角色菜单权限  ACTION类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="角色菜单权限ACTION",createDate="2011-09-24T00:00:00",author="chengmingwei",defaultVals="sysRight_")
@SuppressWarnings("serial")
public class RightAction extends BaseAction {
	@Resource(name="rightService")
	private RightService rightService;
	@Resource(name="sysRightService")
	private SysRightService sysRightService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 角色菜单权限 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = rightService.getResultList(map,getStart(),getLimit());
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
	 * 获取 角色菜单权限 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long roleId = getLVal("roleId");
			Long sysId = getLVal("sysId");
			if(!StringHandler.isValidObj(roleId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("roleId", roleId);
			map.put("sysId", sysId);
			List<RightEntity> entitys = rightService.getEntityList(map);
			if(null == entitys || entitys.size() == 0){
				result = ResultMsg.NODATA;
			}else{
				Map<String,Object> dataMap = new HashMap<String, Object>();
				StringBuffer sb = new StringBuffer();
				int totalSize = entitys.size();
				for(int i=0; i<totalSize; i++){
					RightEntity entity = entitys.get(i);
					Long mmId = entity.getMmId();
					Integer type = entity.getType();
					if(null == type) continue;
					char c = 0;
					if(type.intValue() == RightEntity.TYPE_0){
						c = 'C';
					}else if(type.intValue() == RightEntity.TYPE_1){
						c = 'M';
					}else if(type.intValue() == RightEntity.TYPE_2){
						c = 'D';
					}
					sb.append(c).append(mmId).append(',');
				}
				String rightDatas = StringHandler.RemoveStr(sb, ",");
				dataMap.put("list", new String[]{rightDatas});
				dataMap.put("totalSize", totalSize);
				result = ResultMsg.getSuccessMsg(dataMap);
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
	 * 保存 角色菜单权限 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			Long sysId = getLVal("sysId");
			Long roleId = getLVal("roleId");
			String accordionRights = getVal("accordionRights");
			String menuRights = getVal("menuRights");
			String moduleRights = getVal("moduleRights");
			Map<String,Object> complexData = new HashMap<String, Object>();
			complexData.put("sysId", sysId);
			complexData.put("roleId", roleId);
			complexData.put("accordionRights", accordionRights);
			complexData.put("menuRights", menuRights);
			complexData.put("moduleRights", moduleRights);
			rightService.doComplexBusss(complexData);
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
	 * 取消 角色菜单权限 
	 * @return
	 * @throws Exception
	 */
	public String cancel()throws Exception {
		try {
			Long sysId = getLVal("sysId");
			Long roleId = getLVal("roleId");
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("roleId", roleId);
			params.put("sysId", sysId);
			params.put("objtype", RightEntity.OBJTYPE_0);
			rightService.deleteEntitys(params);	//--> 先删除权限数据
			
			/*----> Step 2 删除当前角色的旧系统权限数据 <----*/
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("objtype", RightEntity.OBJTYPE_0);
			map.put("objId", roleId);
			map.put("sysId", sysId);
			sysRightService.deleteEntitys(map);
			
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
		}catch (ServiceException ex){
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
	 * 新增  角色菜单权限 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = rightService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("R", num);
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
	 * 删除  角色菜单权限  
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用 角色菜单权限 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  角色菜单权限  
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 起用角色菜单权限 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			rightService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 角色菜单权限 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RightEntity entity = rightService.navigationPrev(params);
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
	 * 获取指定的 角色菜单权限 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			RightEntity entity = rightService.navigationNext(params);
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
