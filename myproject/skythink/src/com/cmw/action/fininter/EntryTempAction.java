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
import com.cmw.entity.fininter.EntryTempEntity;
import com.cmw.entity.fininter.SubjectEntity;
import com.cmw.service.impl.fininter.external.generic.FinSysBaseDataCache;
import com.cmw.service.inter.fininter.EntryTempService;
import com.cmw.service.inter.fininter.SubjectService;


/**
 * 分录模板  ACTION类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="分录模板ACTION",createDate="2013-03-28T00:00:00",author="程明卫",defaultVals="fsEntryTemp_")
@SuppressWarnings("serial")
public class EntryTempAction extends BaseAction {
	@Resource(name="entryTempService")
	private EntryTempService entryTempService;
	@Resource(name="subjectService")
	private SubjectService subjectService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 分录模板 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("voucherId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = entryTempService.getResultList(map,getStart(),getLimit());
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
	 * 获取 分录模板 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			EntryTempEntity entity = entryTempService.getEntity(Long.parseLong(id));
			String accountIds = entity.getAccountId();
			if(StringHandler.isValidStr(entity.getAccountId2())){
				accountIds += ","+entity.getAccountId2();
			}
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("refId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + accountIds);
			List<SubjectEntity> subjects = subjectService.getEntityList(map);
			if(null != subjects && subjects.size() > 0){
				for(SubjectEntity subject : subjects){
					Long refId = subject.getRefId();
					String subjectName = subject.getName();
					if(null == refId) continue;
					String accountId = entity.getAccountId();
					if(StringHandler.isValidStr(accountId) && refId.toString().equals(accountId)){
						accountId += "##"+subjectName;
					}
					entity.setAccountId(accountId);
					
					String accountId2 = entity.getAccountId2();
					if(StringHandler.isValidStr(accountId2) && refId.toString().equals(accountId2)){
						accountId2 += "##"+subjectName;
					}
					entity.setAccountId2(accountId2);
				}
			}
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
	 * 保存 分录模板 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			EntryTempEntity entity = BeanUtil.copyValue(EntryTempEntity.class,getRequest());
			String accountId = entity.getAccountId();
			String accountId2 = entity.getAccountId2();
			if(StringHandler.isValidStr(accountId) && accountId.indexOf("##") != -1){
				entity.setAccountId(accountId.split("##")[0]);
			}
			if(StringHandler.isValidStr(accountId2) && accountId2.indexOf("##") != -1){
				entity.setAccountId2(accountId2.split("##")[0]);
			}
			entryTempService.saveOrUpdateEntity(entity);
			FinSysBaseDataCache.updateEntryTempCache(entity, FinSysBaseDataCache.OPTYPE_UPDATE);
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
	 * 新增  分录模板 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = entryTempService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("E", num);
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
	 * 删除  分录模板 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  分录模板 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  分录模板 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  分录模板 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			entryTempService.enabledEntitys(ids, isenabled);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
			List<EntryTempEntity> list = entryTempService.getEntityList(map);
			for(EntryTempEntity entity : list){
				FinSysBaseDataCache.updateEntryTempCache(entity,FinSysBaseDataCache.OPTYPE_DEL);
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
	 * 获取指定的 分录模板 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			EntryTempEntity entity = entryTempService.navigationPrev(params);
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
	 * 获取指定的 分录模板 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			EntryTempEntity entity = entryTempService.navigationNext(params);
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
