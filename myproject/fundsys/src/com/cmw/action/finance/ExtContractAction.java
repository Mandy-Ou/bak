package com.cmw.action.finance;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.ExtContractEntity;
import com.cmw.entity.finance.ExtPlanEntity;
import com.cmw.entity.finance.PayTypeEntity;
import com.cmw.service.inter.finance.ExtContractService;
import com.cmw.service.inter.finance.ExtPlanService;
import com.cmw.service.inter.finance.ExtensionService;
import com.cmw.service.inter.finance.PayTypeService;


/**
 * 展期协议书  ACTION类
 * @author pdh
 * @date 2013-09-23T00:00:00
 */
@Description(remark="展期协议书ACTION",createDate="2013-09-23T00:00:00",author="pdh",defaultVals="fcExtContract_")
@SuppressWarnings("serial")
public class ExtContractAction extends BaseAction {
	@Resource(name="extContractService")
	private ExtContractService extContractService;
	@Resource(name="extensionService")
	private ExtensionService extensionService;
	@Resource(name="payTypeService")
	private PayTypeService payTypeService;
	@Resource(name="extPlanService")
	private ExtPlanService extPlanService;
	
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 展期协议书 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("code,loanCode," +
					"yearLoan#I,monthLoan#I,dayLoan#I,payType,isadvance#I," +
					"rateType#I,eqopLoanLimit,endAmount#O,extAmount#O,eqopendAmount,eqopextAmountt");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = extContractService.getResultList(map,getStart(),getLimit());
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
	 * 客户明细资料详情
	 * @return
	 * @throws Exception
	 */
	public String ledgerlist()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("contractId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = extContractService.getResultList(map,getStart(),getLimit());
			 List<ExtPlanEntity>  extPlanList = null;
			if( null != dt &&  dt.getRowCount()>0 ){
				dt.appendCmns("extPlanList");
				for(Integer i = 0,count = dt.getRowCount();i<count;i++){
					BigDecimal id = dt.getBigDecimal(i, "id");
					if(StringHandler.isValidObj(id)){
						map.put("actionType", BussStateConstant.EXTPLAN_ACTIONTYPE_2);
						map.put("formId", id.longValue());
						extPlanList = extPlanService.getEntityList(map);
						dt.setCellData(i, "extPlanList", extPlanList);
					}
				}
			}
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
	 * 获取 展期协议书 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			String extensionId = getVal("extensionId");
			if(!(!StringHandler.isValidStr(id) || !StringHandler.isValidStr(extensionId))) throw new ServiceException(ServiceException.ID_IS_NULL);
			ExtContractEntity entity = null;
			if(StringHandler.isValidStr(extensionId)){
				SHashMap<String, Object> params  = new SHashMap<String, Object>();
				params.put("extensionId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + extensionId);
				params.put("isenabled", SysConstant.OPTION_ENABLED);
				 entity = extContractService.getEntity(params);
			}else{
				entity = extContractService.getEntity(Long.parseLong(id));
			}
			if(entity ==null){
				result = "-1";
			}else{
				result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
					public void execute(JSONObject jsonObj) {
						if(jsonObj != null){
							jsonDateFormat2str(jsonObj);
						}
					}
				});
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
	 * 申请单详情
	 */
	public String getDetail()throws Exception {
		try {
			String id = getVal("id");
			if(id.equals("-1")){
				outJsonString("-1");
				return null;
			}
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			ExtContractEntity entity = extContractService.getEntity(Long.parseLong(id));
			HashMap<String,Object> params = getJsonParams(entity);
			result = ResultMsg.getSuccessMsg(entity,params);
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
	protected HashMap<String,Object> getJsonParams(ExtContractEntity entity) throws ServiceException{
		
		Long contractId = null;
		String payType = null;
		String payName = "";
		if(entity!=null){
			 contractId  = entity.getContractId();
			  payType = entity.getPayType();
			 
		}
		String contract = "";
		if(null != contractId){
			DataTable dtContractInfo = extensionService.getContractInfo(contractId);
			 contract = dtContractInfo.getJsonObjStr();
		}
		SHashMap<String, Object> payTypeParams = new SHashMap<String, Object>();
		if(StringHandler.isValidStr(payType)){
			payTypeParams.put("code", payType);
			payTypeParams.put("isenabled", SysConstant.OPTION_ENABLED);
			PayTypeEntity payTypeEntity= payTypeService.getEntity(payTypeParams);
			if(payTypeEntity != null){
				payName = payTypeEntity.getName();
			}
		}
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("contract", JSON.parse(contract));
		params.put("payType", payName);
		Date estartDate = entity.getEstartDate();
		String estart = "";
		String eend = "";
		if(estartDate != null){
			estart = DateUtil.dateFormatToStr(estartDate);
		}
		Date eendDate = entity.getEendDate();
		if(eendDate != null){
			eend = DateUtil.dateFormatToStr(eendDate);
		}
		params.put("estartDate", estart);
		params.put("eendDate", eend);
		return params;
	}
	/**
	 * @param jsonObj
	 */
	protected void jsonDateFormat2str(JSONObject jsonObj) {
		String[] cmns = {"ostartDate","oendDate","estartDate","eendDate","asignDate","gsignDate","signDate"};
		for(String cmn : cmns){
			Date date = jsonObj.getDate(cmn);
			jsonObj.put(cmn, DateUtil.dateFormatToStr(date));
		}
		
	}

	/**
	 * 保存 展期协议书 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			ExtContractEntity entity = BeanUtil.copyValue(ExtContractEntity.class,getRequest());
			Map<String, Object> complexData = new HashMap<String, Object>();
			complexData.put(SysConstant.USER_KEY, getCurUser());
			complexData.put("Entity", entity);
			complexData.put("actionType", BussStateConstant.EXTPLAN_ACTIONTYPE_2);
			extContractService.doComplexBusss(complexData);
			HashMap<String, Object> params = new HashMap<String, Object>();
			Long id = entity.getId();
			params.put("id", id);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,params);
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
	 * 新增  展期协议书 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = extContractService.getMaxID();
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
	 * 删除  展期协议书 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  展期协议书 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  展期协议书 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  展期协议书 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			extContractService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 展期协议书 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ExtContractEntity entity = extContractService.navigationPrev(params);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				appendParams = getJsonParams(entity);
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
	 * 获取指定的 展期协议书 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ExtContractEntity entity = extContractService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				appendParams = getJsonParams(entity);
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
