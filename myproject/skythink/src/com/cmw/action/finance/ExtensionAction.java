package com.cmw.action.finance;


import java.util.Date;
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
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.ExtensionEntity;
import com.cmw.entity.finance.GopinionEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.BussProccCache;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.impl.workflow.BussProccFlowService;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.crm.GuaCustomerService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.ExtContractService;
import com.cmw.service.inter.finance.ExtensionService;
import com.cmw.service.inter.finance.GopinionService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.sys.FormCfgService;
import com.cmw.service.inter.sys.UserService;


/**
 * 展期申请  ACTION类
 * @author 程明卫	
 * @date 2013-09-08T00:00:00
 */
@Description(remark="展期申请ACTION",createDate="2013-09-08T00:00:00",author="程明卫",defaultVals="fcExtension_")
@SuppressWarnings("serial")
public class ExtensionAction extends BaseAction {
	@Resource(name="extensionService")
	private ExtensionService extensionService;
	@Resource(name="gopinionService")
	private GopinionService gopinionService;
	@Resource(name="bussProccFlowService")
	private BussProccFlowService bussProccFlowService;
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	@Resource(name="extContractService")
	private ExtContractService extContractService;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	@Resource(name="applyService")
	private ApplyService applyService;
	
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="ecustomerService")
	private EcustomerService ecustomerService;
	
	@Resource(name = "guaCustomerService")
	private GuaCustomerService guaCustomerService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 展期申请 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0);
			DataTable dt = extensionService.getResultList(map,getStart(),getLimit());
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
	 * 获取 展期审批待办 列表
	 * @return		
	 * @throws Exception
	 */
	public String auditlist()throws Exception {
		try {
			UserEntity user = this.getCurUser();
			SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			DataTable dt = extensionService.getResultList(map,getStart(),getLimit());
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
			SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			DataTable dt = extensionService.getResultList(map,getStart(),getLimit());
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
			SHashMap<String, Object> map = getQParams("custType#I,custName,eqopAmount,endAmount,startDate1,endDate1,startDate2,endDate2,eqextAmount,extAmount,status");
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			DataTable dt = extensionService.getResultList(map,getStart(),getLimit());
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
	 * 获取 展期申请 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			ExtensionEntity entity = extensionService.getEntity(Long.parseLong(id));
			
			Long num = extContractService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			final String ecode = CodeRule.getCode("E", num);
			
			Long managerId = entity.getManagerId();
			UserEntity managerUser = UserCache.getUser(managerId);
			String empName = "";
			 if(managerUser == null){
				 UserEntity user = userService.getEntity(managerId);
				 if(user != null){
					 empName =  user.getEmpName(); 
				 }
			 }else{
				 empName =  managerUser.getEmpName();
			 }  
			 final String manager = empName;
			 
			 Long contractId = entity.getContractId();
			 Long baseId = null;
			
			 StringBuffer guaNameSb = new StringBuffer();
			 if(contractId != null){
				 LoanContractEntity loanContractEntity = loanContractService.getEntity(contractId);
				 if(loanContractEntity != null){
					 Long customerId = loanContractEntity.getCustomerId();
					 Integer custType = loanContractEntity.getCustType();
					 SHashMap<String, Object> params = new SHashMap<String, Object>();
					 params.put("id", customerId);
					 params.put("isenabled", SysConstant.OPTION_ENABLED);
					 if(StringHandler.isValidObj(custType) && custType == SysConstant.CUSTTYPE_0){
						CustomerInfoEntity customerInfoEntity =  customerInfoService.getEntity(params);
						baseId = customerInfoEntity.getBaseId();
					 }else if(custType == SysConstant.CATEGORY_1){
						 EcustomerEntity ecustomerEntity =  ecustomerService.getEntity(params);
						 baseId = ecustomerEntity.getBaseId();
					 }
					 params.clear();
					 params.put("baseId", baseId);
					 
					DataTable dt = guaCustomerService.getResultList(params,getStart(),getLimit());
					if(null != dt && dt.getRowCount() > 0){
						for(int i = 0 ,cunt = dt.getRowCount();i<cunt;i++){
							String name = dt.getString(i, "name");
							guaNameSb.append(name+",");
						}
					}
				 }
			 }
			 final Long baId = baseId;
			 final String guaName = StringHandler.RemoveStr(guaNameSb);
			 
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					jsonObj.put("manager", manager);
					fmtDate2str(jsonObj);
					jsonObj.put("ecode", ecode);
					jsonObj.put("baseId", baId);
					jsonObj.put("guarantor", guaName);
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
	
	private void fmtDate2str(JSONObject jsonObj){
		String[] cmns = {"ostartDate","oendDate","estartDate","eendDate","applyDate","comeDate"};
		for(String cmn : cmns){
			Date date = jsonObj.getDate(cmn);
			jsonObj.put(cmn, DateUtil.dateFormatToStr(date));
		}
	}
	
	/**
	 * 保存 展期申请 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			ExtensionEntity entity = BeanUtil.copyValue(ExtensionEntity.class,getRequest());
			UserEntity user = this.getCurUser();
			String code = entity.getCode();
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			boolean flag = false;
			if(!StringHandler.isValidStr(code)){
				code = getCode();
			}else {
				params.put("code", code);
				params.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
				List<ExtensionEntity> entityList = extensionService.getEntityList(params);
				if(!entityList.isEmpty() && entityList.size()>0){
					for(ExtensionEntity x : entityList){
						String validCode = x.getCode();
						if(validCode.equals(code)){
							Long id = entity.getId();
							Long validId = x.getId();
							if(id != validId){
								flag = true;
							}
							break;
						}
					}
				}
			}
			if(flag){
				HashMap<String, Object> appendParams = new HashMap<String, Object>();
				appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "extension.havaCode",code));
				result = ResultMsg.getFailureMsg(appendParams);
			}else{
				entity.setCode(code);
				boolean isAdd = (null == entity.getId());
				if(isAdd){
					Long managerId = this.getCurUser().getUserId();
					entity.setManagerId(managerId);
				}
				extensionService.saveOrUpdateEntity(entity);
				updateGopinion(entity, isAdd);
				Map<String,Object> appnendMap = new HashMap<String, Object>();
				appnendMap.put("applyId", entity.getId());
				result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,appnendMap);
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
	
	private String getCode() throws ServiceException {
		Long num = extensionService.getMaxID();
		if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
		String code = CodeRule.getCode("E", num);
		return code;
	}
	
	/**
	 * 更新担保人意见表 中展期申请单ID
	 * @param entity  展期申请单
	 * @throws ServiceException
	 */
	private void updateGopinion(ExtensionEntity entity, boolean isAdd) throws ServiceException {
		Long uuid = getLVal("uuid");
		if(!isAdd) return;
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("extensionId", uuid);
		List<GopinionEntity> glist = gopinionService.getEntityList(params);
		if(null == glist || glist.size() == 0) return;
		UserEntity user = getCurUser();
		Long extensionId = entity.getId();
		for(GopinionEntity gentity : glist){
			gentity.setExtensionId(extensionId);
			BeanUtil.setModifyInfo(user, gentity);
		}
		gopinionService.batchUpdateEntitys(glist);
	}
	
	/**
	 * 新增  展期申请 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = extensionService.getMaxID();
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
	 * 获取 展期客户合同 列表
	 * 	
	 * @return
	 * @throws Exception
	 */
	public String listContracts()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("custType#I,custName");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = extensionService.getContractResultList(map,getStart(),getLimit());
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
	 * 获取待展期的客户合同 详情
	 * @return	./fcExtension_getContract.action
	 * @throws Exception
	 */
	public String getContract()throws Exception {
		try {
			Long contractId = getLVal("contractId");
			if(!StringHandler.isValidObj(contractId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			if(-1 == contractId){
				result = ResultMsg.NODATA;
			}else{
				DataTable dtContractInfo = extensionService.getContractInfo(contractId);
				result = dtContractInfo.getJsonObjStr();
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
	 * 获取展期申请单的 详情
	 * @return	./fcExtension_detail.action
	 * @throws Exception
	 */
	public String detail()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			DataTable dtResult = extensionService.detail(id);
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
	
	/**
	 * 删除  展期申请 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  展期申请 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  展期申请 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  展期申请 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			extensionService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 展期申请 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ExtensionEntity entity = extensionService.navigationPrev(params);
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
	 * 获取指定的 展期申请 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ExtensionEntity entity = extensionService.navigationNext(params);
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
