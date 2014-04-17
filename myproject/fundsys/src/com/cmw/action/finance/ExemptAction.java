package com.cmw.action.finance;


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
import com.cmw.entity.finance.ExemptEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.DepartmentEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.BussProccCache;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.impl.workflow.BussProccFlowService;
import com.cmw.service.inter.finance.ExemptService;
import com.cmw.service.inter.sys.DepartmentService;
import com.cmw.service.inter.sys.FormCfgService;


/**
 * 息费豁免申请  ACTION类
 * @author 程明卫
 * @date 2013-09-14T00:00:00
 */
@Description(remark="息费豁免申请ACTION",createDate="2013-09-14T00:00:00",author="程明卫",defaultVals="fcExempt_")
@SuppressWarnings("serial")
public class ExemptAction extends BaseAction {
	@Resource(name="exemptService")
	private ExemptService exemptService;
	@Resource(name="departmentService")
	private DepartmentService departmentService;
	
	@Resource(name="bussProccFlowService")
	private BussProccFlowService bussProccFlowService;
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 息费豁免申请 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("status,custType#I,custName,etype#I,isBackAmount#I,eqAmount,totalAmount,startDate1,endDate1");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0);
			DataTable dt = exemptService.getResultList(map,getStart(),getLimit());
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
	 * 获取 息费豁免申请 列表
	 * @return
	 * @throws Exception
	 */
	public String auditlist()throws Exception {
		try {
			DataTable dt = setParams(SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_1);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/** 获取 息费豁免申请 历史记录
	 * auditHistory
	 */
	public String auditHistory()throws Exception {
		try {
			DataTable dt = setParams(SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_2);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/** 获取 息费豁免申请一览表
	 * auditHistory
	 */
	public String auditAll()throws Exception {
		try {
			DataTable dt = setParams(SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_3);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * 设置查询参数
	 */
	public DataTable setParams(Integer actionType){
		DataTable dt  = null;
		try {
			UserEntity user = this.getCurUser();
			SHashMap<String, Object> map = getQParams("status,custType#I,custName,etype#I,isBackAmount#I,eqAmount,totalAmount,startDate1,endDatestatus,custType#I,custName,etype#I,isBackAmount#I,eqAmount,totalAmount,startDate1,endDate");
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", actionType);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			dt = exemptService.getResultList(map,getStart(),getLimit());
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}catch (Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		return dt;
	}
	/**
	 * 获取 息费豁免申请 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			ExemptEntity entity = exemptService.getEntity(Long.parseLong(id));
			Long managerId = entity.getManagerId();
			UserEntity managerUser = UserCache.getUser(managerId);
			final String manager = (null == managerUser) ? null : managerUser.getEmpName();
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					jsonObj.put("manager", manager);
					FastJsonUtil.fmtDate2str(jsonObj,new String[]{"startDate","endDate","appDate"});
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
	 * 保存 息费豁免申请 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			ExemptEntity entity = BeanUtil.copyValue(ExemptEntity.class,getRequest());
			String batchDatas = getVal("batchDatas");
			Map<String,Object> complexData = new HashMap<String, Object>();
			complexData.put(SysConstant.USER_KEY, this.getCurUser());
			complexData.put("exemptEntity", entity);
			complexData.put("batchDatas", batchDatas);
			exemptService.doComplexBusss(complexData);
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
	 * 获取展期申请单的 详情
	 * @return	./fcExtension_detail.action
	 * @throws Exception
	 */
	public String detail()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			DataTable dtResult = exemptService.detail(id);
			
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
		formParams.put("bussCode", SysCodeConstant.BUSS_PROCC_CODE_EXEMPT);
		formParams.put("formId", id);
		final JSONObject bussFormDatas = formCfgService.getBussFormDatas(formParams);
		return bussFormDatas;
	}
	
	/**
	 * 新增  息费豁免申请 
	 * ./fcExempt_add.action
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = exemptService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("E", num);
			UserEntity userEntity = this.getCurUser();
			Long indeptId = userEntity.getIndeptId();
			DepartmentEntity departmentEntity =	departmentService.getEntity(indeptId);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			appendParams.put("code", code);
			appendParams.put("managerId", userEntity.getUserId());
			String manager = userEntity.getEmpName();
			if(!StringHandler.isValidStr(manager)) manager = userEntity.getUserName();
			appendParams.put("manager", manager);
			String appDept = "";
			if(null != departmentEntity) appDept = departmentEntity.getName();
			appendParams.put("appDept", appDept);
			result = JsonUtil.getJsonString(appendParams);
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
	 * 获取豁免的客户合同 列表
	 * 	
	 * @return
	 * @throws Exception
	 */
	public String listContracts()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("custType#I,custName");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = exemptService.getContractResultList(map,getStart(),getLimit());
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
	 * 获取豁免的客户合同 详情
	 * @return	./fcExtension_getContract.action
	 * @throws Exception
	 */
	public String getContract()throws Exception {
		try {
			Long contractId = getLVal("contractId");
			if(!StringHandler.isValidObj(contractId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			DataTable dtContractInfo = exemptService.getContractInfo(contractId);
			result = dtContractInfo.getJsonObjStr();
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
	 * 删除  息费豁免申请 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  息费豁免申请 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  息费豁免申请 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  息费豁免申请 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			exemptService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 息费豁免申请 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ExemptEntity entity = exemptService.navigationPrev(params);
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
	 * 获取指定的 息费豁免申请 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			ExemptEntity entity = exemptService.navigationNext(params);
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
