package com.cmw.action.sys;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.SysRightEntity;
import com.cmw.entity.sys.SystemEntity;
import com.cmw.entity.sys.UroleEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.lock.LockManager;
import com.cmw.service.inter.fininter.BussFinCfgService;
import com.cmw.service.inter.sys.SysRightService;
import com.cmw.service.inter.sys.SystemService;
import com.cmw.service.inter.sys.UroleService;


/**
 * 系统  ACTION类
 * @author chengmingwei
 * @date 2012-10-28T00:00:00
 */
@Description(remark="系统ACTION",createDate="2012-10-28T00:00:00",author="chengmingwei",defaultVals="sysSystem_")
@SuppressWarnings("serial")
public class SystemAction extends BaseAction {
	@Resource(name="systemService")
	private SystemService systemService;
	@Resource(name="uroleService")
	private UroleService uroleService;
	@Resource(name="sysRightService")
	private SysRightService sysRightService;
	@Resource(name="bussFinCfgService")
	private BussFinCfgService bussFinCfgService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 系统 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
//			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = null;
			final DataTable dtBussFin = getBussFinCfgData(map);
			map.put("name", getVal("name"));
			map.put("isenabled", getVal("isenabled"));
			dt = systemService.getResultList(map,getStart(),getLimit());
			if(null == dt || dt.getRowCount() == 0){
				result = ResultMsg.NODATA;
			}else{
				if(null != dtBussFin && dtBussFin.getRowCount() > 0){
					result = dt.getJsonArr(new JsonDataCallback(){
						@Override
						public void makeJson(JSONObject jsonObj) {
							String id = jsonObj.getString("id");
							for(int i=0,count=dtBussFin.getRowCount(); i<count; i++){
								String finsyscfgId = dtBussFin.getString(i, "id");
								String sysId = dtBussFin.getString(i, "sysId");
								String finName = dtBussFin.getString(i, "finName");
								if(id.equals(sysId)){
									jsonObj.put("finsyscfgId", finsyscfgId);
									jsonObj.put("finName", finName);
									break;
								}
							}
						}
					});
				}else{
					result = dt.getJsonArr();
				}
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

	private DataTable getBussFinCfgData(SHashMap<String, Object> map) throws ServiceException {
		String restype = getVal("restype");
		DataTable dtBussSys = null;
		if(!StringHandler.isValidStr(restype) && !restype.equals("FINSYS")) return null;
		/*表示加载与财务系统相关的数据*/
		dtBussSys = bussFinCfgService.getResultList();
		String sysIds = "-1";
		if(null != dtBussSys && dtBussSys.getRowCount() > 0){
			StringBuffer sb = new StringBuffer();
			for(int i=0,count=dtBussSys.getRowCount(); i<count; i++){
				String sysId = dtBussSys.getString(i, "sysId");
				sb.append(sysId).append(",");
			}
			sysIds = StringHandler.RemoveStr(sb, ",");
			if(!StringHandler.isValidStr(sysIds)) sysIds = "-1";
		}
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + sysIds);
		return dtBussSys;
	}
	
	/**
	 * 获取 系统 列表
	 * @return
	 * @throws Exception
	 */
	public String desklist()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			UserEntity user = this.getCurUser();
//			map.put(SysConstant.USER_KEY, user);
			LockManager.getInstance().removeLock(user);
			map.put("isenabled", (byte)1);
			if(null != user.getIsSystem() && user.getIsSystem().intValue() == UserEntity.ISSYSTEM_0){
				String sysIds = getSysRights(user.getUserId());
				if(StringHandler.isValidStr(sysIds)){
					map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + sysIds);
				}else{
					map.put("id",-1L);
				}
			}
			DataTable dt = systemService.getResultList(map);
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
	
	private String getSysRights(Long userId) throws ServiceException{
		StringBuffer sb = new StringBuffer();
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("userId", userId);
		List<UroleEntity> uroles = uroleService.getEntityList(map);
		if(null == uroles || uroles.size() == 0) return null;
		StringBuffer sbObjIds = new StringBuffer();
		for(UroleEntity urole : uroles){
			sbObjIds.append(urole.getRoleId()).append(",");
		}
		map.clear();
		map.put("objtype", SysRightEntity.OBJTYPE_0);
		map.put("objId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + StringHandler.RemoveStr(sbObjIds));
		List<SysRightEntity> sysRights = sysRightService.getEntityList(map);
		if(null == sysRights || sysRights.size() == 0) return null;
		for(SysRightEntity sysRight : sysRights){
			sb.append(sysRight.getSysId()+",");
		}
		return StringHandler.RemoveStr(sb);
	}
	/**
	 * 获取 系统 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SystemEntity entity = systemService.getEntity(Long.parseLong(id));
			result = JsonUtil.toJson(entity);
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
	 * 保存 系统 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			SystemEntity entity = BeanUtil.copyValue(SystemEntity.class,getRequest());
			systemService.saveOrUpdateEntity(entity);
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
	 * 新增  系统 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = systemService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("S", num);
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
	 * 删除  系统 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		try {
			String ids = getVal("ids");
			if(!StringHandler.isValidStr(ids)) throw new ServiceException(ServiceException.ID_IS_NULL);
			systemService.enabledEntitys(ids, SysConstant.OPTION_DEL);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
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
	 * 启用  系统 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		try {
			String ids = getVal("ids");
			if(!StringHandler.isValidStr(ids)) throw new ServiceException(ServiceException.ID_IS_NULL);
			systemService.enabledEntitys(ids, SysConstant.OPTION_ENABLED);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
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
	 * 禁用  系统 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		try {
			String ids = getVal("ids");
			if(!StringHandler.isValidStr(ids)) throw new ServiceException(ServiceException.ID_IS_NULL);
			systemService.enabledEntitys(ids, SysConstant.OPTION_DISABLED);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.DELETE_SUCCESS);
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
	 * 获取指定的 系统 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			SystemEntity entity = systemService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			result = ResultMsg.getSuccessMsg(entity, appendParams);
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
	 * 获取指定的 系统 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			SystemEntity entity = systemService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			result = ResultMsg.getSuccessMsg(entity, appendParams);
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
