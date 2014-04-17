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
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.GvlistEntity;
import com.cmw.entity.sys.RestypeEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.GvlistService;
import com.cmw.service.inter.sys.RestypeService;


/**
 * 基础数据  ACTION类
 * @author 彭登浩
 * @date 2012-11-19T00:00:00
 */
@Description(remark="基础数据ACTION",createDate="2012-11-19T00:00:00",author="彭登浩",defaultVals="sysGvlist_")
@SuppressWarnings("serial")
public class GvlistAction extends BaseAction {
	@Resource(name="gvlistService")
	private GvlistService gvlistService;
	@Resource(name="restypeService")
	private RestypeService restypeService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 基础数据 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("restypeId", getLVal("restypeId"));
			map.put("isenabled", getIVal("isenabled"));
			map.put("name", getVal("name"));
			map.put("id", getLVal("id"));
			String recode = getVal("recode");
			if(recode!=null && recode.length()!=0){
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("recode", recode);
				RestypeEntity restypeentity = restypeService.getEntity(params);
				Long restypeId =  restypeentity.getId();
				map.put("restypeId", restypeId);
			}
			DataTable dt = gvlistService.getResultList(map,getStart(),getLimit());
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
	 * 获取 基础数据 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			GvlistEntity entity = gvlistService.getEntity(Long.parseLong(id));
			Long rId = entity.getRestypeId();
			String rName = null;
			if(null!=rId){
				RestypeEntity restypeEntity = restypeService.getEntity(rId);
				 rName = restypeEntity.getName();
			}
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("rname", rName);
			result = ResultMsg.getSuccessMsg(entity, params);
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
	 * 保存 基础数据 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			UserEntity user = getCurUser();
			GvlistEntity entity = BeanUtil.copyValue(GvlistEntity.class,getRequest());
			Long  restypeId = entity.getRestypeId();
			String gname = entity.getName();
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			Map<String,Object> appendParams  = null;
			map.put("restypeId", restypeId);
			map.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
			List<GvlistEntity>  GvlistEntity = gvlistService.getEntityList(map);
			boolean flag = false;
			Long gvlistId = entity.getId();
			if(!GvlistEntity.isEmpty() && GvlistEntity.size()>0){
				for(GvlistEntity gEntity : GvlistEntity){
					String existName = gEntity.getName();
					if(existName.equals(gname)){
						Long gid = gEntity.getId();
						if(gid != gvlistId){
							flag = true;
						}
						break;
					}
				}
			}
			if(flag){
				appendParams = new HashMap<String, Object>();
				appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "gvlist.exist.error",gname));
				result = ResultMsg.getFailureMsg(appendParams);
			}else{
				gvlistService.saveOrUpdateEntity(entity);
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS);
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
	 * 新增  基础数据 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = gvlistService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("G", num);
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
	 * 删除  基础数据 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  基础数据 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  基础数据 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  基础数据 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			gvlistService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 基础数据 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			GvlistEntity entity = gvlistService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				Long restypeId  = entity.getRestypeId();
				if(!StringHandler.isValidObj(restypeId)) throw new ServiceException(ServiceException.ID_IS_NULL);
				RestypeEntity restypeEntity = restypeService.getEntity(restypeId);
				String rname = restypeEntity.getName();
				appendParams.put("rname", rname);
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
	 * 获取指定的 基础数据 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			GvlistEntity entity = gvlistService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				Long restypeId  = entity.getRestypeId();
				if(!StringHandler.isValidObj(restypeId)) throw new ServiceException(ServiceException.ID_IS_NULL);
				RestypeEntity restypeEntity = restypeService.getEntity(restypeId);
				String rname = restypeEntity.getName();
				appendParams.put("rname", rname);
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
	 * 获取 下拉框基础数据 
	 * @return
	 * @throws Exception
	 */
	public String cbodatas()throws Exception {
		try {
			String restypeId = getVal("restypeId");
			if(!StringHandler.isValidStr(restypeId)) throw new ServiceException(ServiceException.RESTYPEID_IS_NULL);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("restypeIds", restypeId);
			DataTable dt = gvlistService.getDataSource(map);
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
	
	
}
