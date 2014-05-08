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
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.funds.AmountApplyEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.entity.sys.VarietyEntity;
import com.cmw.service.impl.cache.BussProccCache;
import com.cmw.service.impl.workflow.BussProccFlowService;
import com.cmw.service.inter.funds.AmountApplyService;
import com.cmw.service.inter.sys.BussProccService;
import com.cmw.service.inter.sys.FormCfgService;
import com.cmw.service.inter.sys.VarietyService;


/**
 * 增资申请  ACTION类
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="委托合同ACTION",createDate="2014-01-20T00:00:00",author="李听",defaultVals="fuAmountApply_")
@SuppressWarnings("serial")
public class AmountApplyAction extends BaseAction {
	@Resource(name="amountApplyService")
	private AmountApplyService amountApplyService;
	private String result = ResultMsg.GRID_NODATA;
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	@Resource(name="bussProccFlowService") 
	private BussProccFlowService bussProccFlowService;
	@Resource(name="bussProccService")
	private BussProccService bussProccService;
	@Resource(name="varietyService")
	private VarietyService varietyService;
	
	/**
	 * 获取 委托合同 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("payAccount,doDate,payDate,endDate,code,eqopAmount,accName,appAmount");	
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0);
			DataTable dt = amountApplyService.getResultList(map,getStart(),getLimit());
			if(null != dt || dt.getRowCount() > 0){
				setNameProce(dt);
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
	 * 
	 * @param dt
	 * @throws ServiceException
	 */
	private void setNameProce(DataTable dt) throws ServiceException {
		SHashMap<Object, Object> params = new SHashMap<Object, Object>();
		params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
		for(int i=0,count = dt.getRowCount();i<count;i++){
			String productsId = dt.getString(i, "productsId");
			if(StringHandler.isValidStr(productsId)){
				if(params.validKey("id")){
					params.remove("id");
				}
				params.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+productsId);
				List<VarietyEntity>  bussProccEntityList = varietyService.getEntityList(params);
				if( bussProccEntityList != null && bussProccEntityList.size()>0){
					StringBuffer sb = new StringBuffer();
					for(VarietyEntity x : bussProccEntityList){
						String name = x.getName();
						sb.append(name+",");
					}
					String dtName = StringHandler.RemoveStr(sb);
					dt.setCellData(i, "productsId", dtName);
				}
				
			}
		}
	}
/**
 * 获取 展期审批待办 列表
 * @return		
 * @throws Exception
 */
public String auditlist()throws Exception {
	try {
		UserEntity user = this.getCurUser();
//		SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
		SHashMap<String, Object> map = getQParams("payAccount,doDate,payDate,endDate,code,eqopAmount,accName,appAmount");
		map.put(SysConstant.USER_KEY, user);
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1);
		String procIds = bussProccFlowService.getProcIdsByUser(user);
		if(StringHandler.isValidStr(procIds)){
			map.put("procIds", procIds);
		}
		DataTable dt = amountApplyService.getResultList(map,getStart(),getLimit());
		if(null != dt || dt.getRowCount() > 0){
			setNameProce(dt);
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
 * 获取 展期审批 一览表
 * @return		
 * @throws Exception
 */
public String  auditAll()throws Exception {
	try {
		UserEntity user = this.getCurUser();
//		SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
		SHashMap<String, Object> map = getQParams("payAccount,doDate,payDate,endDate,code,eqopAmount,accName,appAmount");
		map.put(SysConstant.USER_KEY, user);
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3);
		String procIds = bussProccFlowService.getProcIdsByUser(user);
		if(StringHandler.isValidStr(procIds)){
			map.put("procIds", procIds);
		}
		DataTable dt = amountApplyService.getResultList(map,getStart(),getLimit());
		if(null != dt || dt.getRowCount() > 0){
			setNameProce(dt);
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
 * 获取 历史 列表
 * @return		
 * @throws Exception
 */
public String auditHistory()throws Exception {
	try {
		UserEntity user = this.getCurUser();
//		SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
		SHashMap<String, Object> map = getQParams("payAccount,doDate,payDate,endDate,code,eqopAmount,accName,appAmount");
		map.put(SysConstant.USER_KEY, user);
		map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2);
		String procIds = bussProccFlowService.getProcIdsByUser(user);
		if(StringHandler.isValidStr(procIds)){//productsId
			map.put("procIds", procIds);
		}
		DataTable dt = amountApplyService.getResultList(map,getStart(),getLimit());
		for(int i=0,count=dt.getRowCount();i<count;i++){
			String productsId=	dt.getString("productsId");
			if(null!=productsId&&StringHandler.isValidObj(productsId)){
				VarietyEntity vEntiey=	varietyService.getEntity(Long.parseLong(productsId));
				dt.setCellData(i, "productsId", vEntiey.getName());
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
	 * 获取 委托合同 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			AmountApplyEntity entity=	amountApplyService.getEntity(Long.parseLong(id));
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
	 * 保存 委托合同 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			AmountApplyEntity entity = BeanUtil.copyValue(AmountApplyEntity.class,getRequest());
			amountApplyService.saveOrUpdateEntity(entity);
			Map<String,Object> appnendMap = new HashMap<String, Object>();
			appnendMap.put("applyId", entity.getId());
			appnendMap.put("entrustCustId", entity.getEntrustCustId());
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
			Long num = amountApplyService.getMaxID();
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
			amountApplyService.enabledEntitys(ids, isenabled);
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
			AmountApplyEntity entity = amountApplyService.navigationPrev(params);
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
			AmountApplyEntity entity = amountApplyService.navigationNext(params);
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
	public String lget()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params=new SHashMap<String, Object>();
			params.put("id", id);
			DataTable dt = amountApplyService.getResultList(params, -1, -1);
			if(null != dt || dt.getRowCount() > 0){
				setNameProce(dt);
			}
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonObjStr();
//			result =FastJsonUtil.convertJsonToStr(dtResult,new Callback(){
//				@Override
//				public void execute(JSONObject jsonObj) {
//					Long productsId = jsonObj.getLong("productsId");
//					try {
//						if(StringHandler.isValidObj(productsId)){
//							VarietyEntity creatorObj=varietyService.getEntity(productsId);
//							if(null != creatorObj) jsonObj.put("productsId", creatorObj.getName());
//						}
//					} catch (ServiceException e) {
//						e.printStackTrace();
//					}
//				}
//			});
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
			DataTable dtResult = amountApplyService.detail(id);
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
			getOn(dtResult);
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
	private void getOn(DataTable dtResult) throws ServiceException {
		for(int i=0,count = dtResult.getRowCount();i<count;i++){
			String productsId = dtResult.getString(i, "productsId");
			if(StringHandler.isValidStr(productsId)){
				Long breed=Long.parseLong(productsId);
				VarietyEntity creatorObj=varietyService.getEntity(breed);
//							StringBuffer sb = new StringBuffer();
//							String dtName = StringHandler.RemoveStr(sb);
				if(StringHandler.isValidObj(creatorObj)){
				 dtResult.setCellData(i, "productsId", creatorObj.getName());
				}
			}
		}
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
