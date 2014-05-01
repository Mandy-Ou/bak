package com.cmw.action.funds;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysCodeConstant;
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
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.AmountApplyEntity;
import com.cmw.entity.funds.EntrustContractEntity;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.impl.cache.BussProccCache;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.inter.funds.AmountApplyService;
import com.cmw.service.inter.funds.EntrustContractService;
import com.cmw.service.inter.funds.EntrustCustService;
import com.cmw.service.inter.sys.FormCfgService;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 委托合同  ACTION类
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="委托合同ACTION",createDate="2014-01-20T00:00:00",author="李听",defaultVals="fuEntrustContract_")
@SuppressWarnings("serial")
public class EntrustContractAction extends BaseAction {
	@Resource(name="entrustContractService")
	private EntrustContractService entrustContractService;
	@Resource(name="amountApplyService")
	private AmountApplyService amountApplyService;
	@Resource(name="entrustCustService")
	private EntrustCustService entrustCustService;
	@Resource(name="varietyService")
	private VarietyService varietyService;
	private String result = ResultMsg.GRID_NODATA;
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	/**
	 * 获取 委托合同 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			Integer status=getIVal("status");
			String payAccount=getVal("payAccount");
			String accName=getVal("accName");
			String productsId=getVal("productsId");
			String code=getVal("code");
			String payDate=getVal("payDate");
			String endDate=getVal("endDate");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("status", status);
			map.put("payAccount", payAccount);
			map.put("accName", accName);
			map.put("productsId", productsId);
			map.put("code", code);
			map.put("payDate", payDate);
			map.put("endDate", endDate);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = entrustContractService.getResultList(map,getStart(),getLimit());
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
	 * 获取 委托合同 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String applyId = getVal("applyId");
			if(!StringHandler.isValidStr(applyId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<Object, Object> map=new SHashMap<Object, Object>();
			map.put("applyId", applyId);
			EntrustContractEntity entity = entrustContractService.getEntity(map);
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				@Override
				public void execute(JSONObject jsonObj) {
					Long productsId = jsonObj.getLong("productsId");
					try {
						if(StringHandler.isValidObj(productsId)){
							VarietyEntity creatorObj=varietyService.getEntity(productsId);
							if(null != creatorObj) jsonObj.put("productsId", creatorObj.getName());
						}
					} catch (ServiceException e) {
						e.printStackTrace();
					}
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
	 * 获取 委托合同 详情
	 * @return
	 * @throws Exception
	 */
	public String getEcus()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			AmountApplyEntity entity = amountApplyService.getEntity(Long.parseLong(id));
			Long Eid= entity.getEntrustCustId();
			EntrustCustEntity entityAmount = entrustCustService.getEntity(Eid);
			result = FastJsonUtil.convertJsonToStr(entityAmount,new Callback(){
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
	 * 保存 委托合同 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			EntrustContractEntity entity = BeanUtil.copyValue(EntrustContractEntity.class,getRequest());
			entrustContractService.saveOrUpdateEntity(entity);
			Map<String,Object> appnendMap = new HashMap<String, Object>();
			appnendMap.put("id", entity.getId());
			result = ResultMsg.getSuccessMsg(appnendMap);
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
	 * 新增  委托合同 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = entrustContractService.getMaxID();
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
	 * 删除  委托合同 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  委托合同 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  委托合同 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  委托合同 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			entrustContractService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 委托合同 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			EntrustContractEntity entity = entrustContractService.navigationPrev(params);
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
	 * 获取指定的 委托合同 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			EntrustContractEntity entity = entrustContractService.navigationNext(params);
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
	 * 获取委托合同申请单的 详情
	 * @return	./fuEntrustContract_detail.action
	 * @throws Exception
	 */
	
	public String detail()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			DataTable dtResult = entrustContractService.detail(id);
			//breed,procId
			String breed = dtResult.getString("breed");
			String pdid = null;
			if(StringHandler.isValidStr(breed)){
				List<BussProccEntity> bussProccList = BussProccCache.getBussProccs(breed);
				if(null != bussProccList && bussProccList.size() > 0){
					BussProccEntity bussProccEntity = bussProccList.get(0);
					pdid = bussProccEntity.getPdid();
					dtResult.appendData("pdid", new Object[]{pdid});
				}
			}
			String procId = dtResult.getString("procId");
			if(StringHandler.isValidStr(pdid) || StringHandler.isValidStr(procId)){
				final JSONObject bussFormDatas = getBussFormDatas(id, pdid,procId);
				if(null != bussFormDatas && bussFormDatas.size() > 0){/*流程业务表单*/
					dtResult.appendData("bussFormDatas", new Object[]{bussFormDatas});
				}
			}
			result = dtResult.getJsonObjStr();
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
	 * 获取业务表单菜单数据
	 * @param id
	 * @param pdid
	 * @param procId
	 * @return
	 * @throws ServiceException
	 */
	private JSONObject getBussFormDatas(Long id, String pdid, String procId)
			throws ServiceException {
		SHashMap<String, Object> formParams = new SHashMap<String, Object>();
		formParams.put("pdid", pdid);
		formParams.put("procId", procId);
		formParams.put(SysConstant.USER_KEY, this.getCurUser());
		formParams.put("bussType", SysConstant.SYSTEM_BUSSTYPE_BUSSPROCC_APPLY);
		formParams.put("bussCode", SysCodeConstant.BUSS_PROCC_CODE_EXTENSION);
		formParams.put("formId", id);
		final JSONObject bussFormDatas = formCfgService.getBussFormDatas(formParams);
		return bussFormDatas;
	}
	
	
	
	
}
