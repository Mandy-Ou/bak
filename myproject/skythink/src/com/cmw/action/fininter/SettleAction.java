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
import com.cmw.entity.fininter.BussFinCfgEntity;
import com.cmw.entity.fininter.FinBussObjectEntity;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.entity.fininter.SettleEntity;
import com.cmw.service.impl.fininter.external.FinSysFactory;
import com.cmw.service.impl.fininter.external.FinSysService;
import com.cmw.service.impl.fininter.external.generic.FinSysBaseDataCache;
import com.cmw.service.inter.fininter.BussFinCfgService;
import com.cmw.service.inter.fininter.FinSysCfgService;
import com.cmw.service.inter.fininter.SettleService;


/**
 * 结算方式  ACTION类	
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="结算方式ACTION",createDate="2013-03-28T00:00:00",author="程明卫",defaultVals="fsSettle_")
@SuppressWarnings("serial")
public class SettleAction extends BaseAction {
	@Resource(name="settleService")
	private SettleService settleService;
	@Resource(name="bussFinCfgService")
	private BussFinCfgService bussFinCfgService;
	@Resource(name="finSysCfgService")
	private FinSysCfgService finSysCfgService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 结算方式 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("code,name");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = settleService.getResultList(map,getStart(),getLimit());
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
	 * 财务系统数据同步
	 * @return
	 * @throws Exception
	 */
	public String synchronous()throws Exception {
		try {
			String finsysId = getVal("finsysId");
			if(!StringHandler.isValidStr(finsysId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			BussFinCfgEntity bussFinCfgEntity = bussFinCfgService.getEntity(Long.parseLong(finsysId));
			if(null == bussFinCfgEntity) throw new ServiceException(ServiceException.BUSSFINCFGENTITY_IS_NULL);
			FinSysCfgEntity entity = finSysCfgService.getEntity(bussFinCfgEntity.getFinsysId());
			String finsysCode = entity.getCode();
			FinSysService fservice = FinSysFactory.getInstance(finsysCode);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put(SysConstant.USER_KEY, getCurUser());
			params.put("objectName", "SettleEntity");
			List<SettleEntity> list = fservice.getSettles(params);
			settleService.batchSaveEntitys(list);
			result = ResultMsg.getSuccessMsg();
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
			DataTable dt = settleService.getResultList(map);
			Map<String,String> repCmnMap = new HashMap<String,String>();
			repCmnMap.put("id", "settleId");
			repCmnMap.put("refId", "id");
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
	
	/**
	 * 获取 结算方式 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SettleEntity entity = settleService.getEntity(Long.parseLong(id));
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
	 * 保存 结算方式 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			SettleEntity entity = BeanUtil.copyValue(SettleEntity.class,getRequest());
			settleService.saveOrUpdateEntity(entity);
			FinSysBaseDataCache.updateSettleCache(entity, FinSysBaseDataCache.OPTYPE_UPDATE);
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
	 * 新增  结算方式 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = settleService.getMaxID();
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
	 * 删除  结算方式 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  结算方式 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  结算方式 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  结算方式 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			settleService.enabledEntitys(ids, isenabled);
			if(!sucessMsg.equals(ResultMsg.ENABLED_SUCCESS)){/*删除、禁用时移出缓存*/
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
				List<SettleEntity> list = settleService.getEntityList(map);
				for(SettleEntity entity : list){
					FinSysBaseDataCache.updateSettleCache(entity,FinSysBaseDataCache.OPTYPE_DEL);
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
	 * 获取指定的 结算方式 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			SettleEntity entity = settleService.navigationPrev(params);
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
	 * 获取指定的 结算方式 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			SettleEntity entity = settleService.navigationNext(params);
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
