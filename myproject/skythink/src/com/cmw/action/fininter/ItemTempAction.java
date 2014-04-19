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
import com.cmw.entity.fininter.ItemClassEntity;
import com.cmw.entity.fininter.ItemTempEntity;
import com.cmw.service.impl.fininter.external.generic.FinSysBaseDataCache;
import com.cmw.service.inter.fininter.EntryTempService;
import com.cmw.service.inter.fininter.ItemClassService;
import com.cmw.service.inter.fininter.ItemTempService;


/**
 * 核算项  ACTION类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="核算项ACTION",createDate="2013-03-28T00:00:00",author="程明卫",defaultVals="fsItemTemp_")
@SuppressWarnings("serial")
public class ItemTempAction extends BaseAction {
	@Resource(name="itemTempService")
	private ItemTempService itemTempService;
	@Resource(name="itemClassService")
	private ItemClassService itemClassService;
	@Resource(name="entryTempService")
	private EntryTempService entryTempService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 核算项 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("entryId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = itemTempService.getResultList(map,getStart(),getLimit());
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
	 * 获取 核算项 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			ItemTempEntity entity = itemTempService.getEntity(Long.parseLong(id));
			final String itemClassId = entity.getItemClassId();
			final String itemClassName = getItemClass(itemClassId);
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					if(StringHandler.isValidStr(itemClassName)) jsonObj.put("itemClassId", itemClassId+"##"+itemClassName);
					String fieldIds = jsonObj.getString("fieldIds");
					String fieldNames = jsonObj.getString("fieldNames");
					jsonObj.put("fieldIds", fieldIds+"##"+fieldNames);
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

	private String getItemClass(String itemClassId)throws ServiceException {
		if(!StringHandler.isValidStr(itemClassId)) return null;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("refId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + itemClassId);
		ItemClassEntity itemClass = itemClassService.getEntity(map);
		if(null == itemClass) return null;
		return itemClass.getName();
	}
	
	/**
	 * 保存 核算项 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			ItemTempEntity entity = BeanUtil.copyValue(ItemTempEntity.class,getRequest());
			Long entryId1 = entity.getEntryId();
			if(!StringHandler.isValidObj(entryId1)){
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("entryId", entryId1);
				EntryTempEntity etEntity1 = entryTempService.getEntity(entryId1);
				if(etEntity1!=null){
					result = ResultMsg.getFailureMsg();
					return null;
				}
			}
			String itemClassId = entity.getItemClassId();
			if(StringHandler.isValidStr(itemClassId) && itemClassId.indexOf("##") != -1){
				String[] itemClasses = itemClassId.split("##");
				itemClassId = itemClasses[0];
				entity.setItemClassId(itemClassId);
			}
			
			String fieldIds = entity.getFieldIds();
			if(StringHandler.isValidStr(fieldIds) && fieldIds.indexOf("##") != -1){
				String[] fields = fieldIds.split("##");
				fieldIds = fields[0];
				String fieldNames = fields[1];
				entity.setFieldIds(fieldIds);
				entity.setFieldNames(fieldNames);
			}
			itemTempService.saveOrUpdateEntity(entity);
			Long entryId = entity.getEntryId();
			EntryTempEntity etEntity = entryTempService.getEntity(entryId);
			Byte isItemClass = etEntity.getIsItemClass();
			if(null == isItemClass || isItemClass.byteValue() == EntryTempEntity.ISITEMCLASS_1){
				etEntity.setIsItemClass(EntryTempEntity.ISITEMCLASS_2);
				entryTempService.updateEntity(etEntity);
			}
			FinSysBaseDataCache.updateItemTempCache(entity, FinSysBaseDataCache.OPTYPE_UPDATE);
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
	 * 新增  核算项 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = itemTempService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("I", num);
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
	 * 删除  核算项 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  核算项 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  核算项 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  核算项 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
			List<ItemTempEntity> itemTemps = itemTempService.getEntityList(map);
			ItemTempEntity itEntity = itemTemps.get(0);
			Integer isenabled = getIVal("isenabled");
			itemTempService.enabledEntitys(ids, isenabled);
			if(sucessMsg.equals(ResultMsg.DELETE_SUCCESS)){/*删除*/
				map.clear();
				Long entryId = itEntity.getEntryId();
				map.put("entryId", entryId);
				map.put("isenabled", SqlUtil.LOGIC_NOT_EQ + SqlUtil.LOGIC + SysConstant.OPTION_DEL);
				itemTemps = itemTempService.getEntityList(map);
				if(null == itemTemps || itemTemps.size() == 0){
					EntryTempEntity etEntity = entryTempService.getEntity(entryId);
					etEntity.setIsItemClass(EntryTempEntity.ISITEMCLASS_1);
					entryTempService.updateEntity(etEntity);
				}
			}
			if(!sucessMsg.equals(ResultMsg.ENABLED_SUCCESS)){/*删除、禁用时移出缓存*/
				for(ItemTempEntity entity : itemTemps){
					FinSysBaseDataCache.updateItemTempCache(entity,FinSysBaseDataCache.OPTYPE_DEL);
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
	 * 获取指定的 核算项 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ItemTempEntity entity = itemTempService.navigationPrev(params);
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
	 * 获取指定的 核算项 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ItemTempEntity entity = itemTempService.navigationNext(params);
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
