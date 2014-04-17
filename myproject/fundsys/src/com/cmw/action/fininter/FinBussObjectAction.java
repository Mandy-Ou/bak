package com.cmw.action.fininter;


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
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.core.util.TreeUtil;
import com.cmw.entity.fininter.FinBussObjectEntity;
import com.cmw.service.impl.fininter.external.generic.FinSysBaseDataCache;
import com.cmw.service.inter.fininter.FinBussObjectService;


/**
 * 自定义业务对象  ACTION类
 * @author 程明卫  
 * @date 2013-03-28T00:00:00
 */
@Description(remark="自定义业务对象ACTION",createDate="2013-03-28T00:00:00",author="程明卫",defaultVals="fsFinBussObject_")
@SuppressWarnings("serial")
public class FinBussObjectAction extends BaseAction {
	@Resource(name="finBussObjectService")
	private FinBussObjectService finBussObjectService;
	
	//树工具类
		private TreeUtil treeUtil = new TreeUtil();
		private String result = ResultMsg.GRID_NODATA;
		/**
		 * 获取 科目组 列表
		 * @return
		 * @throws Exception
		 */
		public String list()throws Exception {
			try {
				SHashMap<String, Object> map = getQParams("finsysId#L,isenabled#I");
				DataTable dt = finBussObjectService.getResultList(map);
				map.put(SysConstant.USER_KEY, this.getCurUser());
				treeUtil.setIconPath("./");
				treeUtil.setDt(dt);
				result = treeUtil.getJsonArr("0");
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
	 * 获取 自定义业务对象 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			FinBussObjectEntity entity = finBussObjectService.getEntity(Long.parseLong(id));
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
	 * 保存 自定义业务对象 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			FinBussObjectEntity entity = BeanUtil.copyValue(FinBussObjectEntity.class,getRequest());
			finBussObjectService.saveOrUpdateEntity(entity);
			FinSysBaseDataCache.updateBussObjCache(entity, FinSysBaseDataCache.OPTYPE_UPDATE);
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
	 * 新增  自定义业务对象 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = finBussObjectService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("F", num);
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
	 * 删除  自定义业务对象 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  自定义业务对象 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  自定义业务对象 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  自定义业务对象 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			finBussObjectService.enabledEntitys(ids, isenabled);
			if(!sucessMsg.equals(ResultMsg.ENABLED_SUCCESS)){/*删除、禁用时移出缓存*/
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
				List<FinBussObjectEntity> list = finBussObjectService.getEntityList(map);
				for(FinBussObjectEntity entity : list){
					FinSysBaseDataCache.updateBussObjCache(entity, FinSysBaseDataCache.OPTYPE_DEL);
				}
			}
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
	 * 获取指定的 自定义业务对象 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FinBussObjectEntity entity = finBussObjectService.navigationPrev(params);
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
	 * 获取指定的 自定义业务对象 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			FinBussObjectEntity entity = finBussObjectService.navigationNext(params);
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
	
	/**
	 * 获取 下拉框基础数据 
	 * @return
	 * @throws Exception
	 */
	public String cbodatas()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("isenabled", SysConstant.OPTION_ENABLED);
			Long finsysId = getLVal("finsysId");
			if(!StringHandler.isValidObj(finsysId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			map.put("finsysId", finsysId);
			DataTable dt = finBussObjectService.getResultList(map);
			Map<String,String> repCmnMap = new HashMap<String,String>();
			repCmnMap.put("text", "name");
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr(repCmnMap,new String[]{"id","name"});
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
