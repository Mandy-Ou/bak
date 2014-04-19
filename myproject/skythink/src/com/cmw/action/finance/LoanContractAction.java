package com.cmw.action.finance;


import java.util.Date;
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
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.LoanInvoceService;
import com.cmw.service.inter.sys.SysparamsService;


/**
 * 借款合同  ACTION类
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
@Description(remark="借款合同ACTION",createDate="2013-01-11T00:00:00",author="pdh",defaultVals="fcLoanContract_")
@SuppressWarnings("serial")
public class LoanContractAction extends BaseAction {
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="applyService")
	private ApplyService applyService;
	
	@Resource (name = "ecustomerService")
	private EcustomerService ecustomerService;
	
	@Resource (name = "customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource (name = "sysparamsService")
	private SysparamsService sysparamsService;
	
	@Resource (name = "loanInvoceService")
	private LoanInvoceService loanInvoceService;
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 借款合同 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("code", getVal("code"));
			map.put("accName", getVal("accName"));
			map.put("payAccount", getVal("payAccount"));
			map.put("payBank", getVal("payBank"));
			map.put("borAccount", getVal("borAccount"));
			map.put("borBank", getVal("borBank"));
			map.put("mgrtype", getIVal("mgrtype"));
			map.put("isadvance", getIVal("isadvance"));
			map.put("rateType", getIVal("rateType"));
			map.put("payType", getVal("payType"));
			DataTable dt = loanContractService.getResultList(map,getStart(),getLimit());
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
	 * 实收及应收利息表报查询
	 */
	public String rateRecord()throws Exception {
		try {
			String queryDate = getVal("queryDate");
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("queryDate", queryDate);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			String htm = loanContractService.getReportList(map,getStart(),getLimit());
			result = ResultMsg.getSuccessMsg(htm);
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
	 * 获取客户合同 列表
	 * 	
	 * @return
	 * @throws Exception
	 */
	public String listContracts()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("custType#I,custName,contractId#L");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = loanContractService.getContractResultList(map,getStart(),getLimit());
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
	 * 获取 借款合同 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("formId");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("formId", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + id);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			LoanContractEntity entity = loanContractService.getEntity(params);
			if(entity==null){
				outJsonString("-1");
				return null;
			}
			Date payDate = entity.getPayDate();
			Date endDate = entity.getEndDate();
			Date doDate = entity.getDoDate();
			final String paydate = DateUtil.dateFormatToStr(payDate);
			final String enddate = DateUtil.dateFormatToStr(endDate);
			final String dodate = DateUtil.dateFormatToStr(doDate);
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					jsonObj.put("payDate", paydate);
					jsonObj.put("endDate", enddate);
					jsonObj.put("doDate", dodate);
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
	 * 保存 借款合同 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			String payDaySet = getVal("payDaySet");
			SHashMap<String, Object> args= new SHashMap<String,Object>();
			LoanContractEntity entity = BeanUtil.copyValue(LoanContractEntity.class,getRequest());
			if(entity != null){
				String codeParams =  entity.getCode();
				if(StringHandler.isValidStr(codeParams)){
					args.put("isenabled", SqlUtil.LOGIC_NOT_EQ+SqlUtil.LOGIC+SysConstant.OPTION_DEL);
					args.put("code", codeParams);
					LoanContractEntity loanContEntity = loanContractService.getEntity(args);
					if(loanContEntity != null){
						Long loanContId = loanContEntity.getId();
						if(entity.getId() != loanContId){
							outJsonString("-1");
							return null;
						}
					}
				}else{
					setCode(entity);
				}
			}
			
			Integer custType = getIVal("custType");
			entity.setCustType(custType);
			loanContractService.saveOrUpdateEntity(entity);
			Long id = entity.getId();
			HashMap<String, Object> Params = new HashMap<String,Object>();
			Params.put("id", id);
			Long applyId = entity.getFormId();
			String borAccount = entity.getBorAccount();
			String borBank = entity.getBorBank();
			String payAccount = entity.getPayAccount();
			String payBank = entity.getPayBank();
			ApplyEntity applyentity = applyService.getEntity(applyId);
			applyentity.setBorAccount(borAccount);
			applyentity.setBorBank(borBank);
			applyentity.setPayAccount(payAccount);
			applyentity.setPayBank(payBank);
			applyentity.setAppAmount(entity.getAppAmount());
			applyentity.setYearLoan(entity.getYearLoan());
			applyentity.setMonthLoan(entity.getMonthLoan());
			applyentity.setDayLoan(entity.getDayLoan());
			applyentity.setPayType(entity.getPayType());
			applyentity.setPhAmount(entity.getPhAmount());
			applyentity.setRateType(entity.getRateType());
			applyentity.setRate(entity.getRate());
			applyentity.setIsadvance(entity.getIsadvance());
			applyentity.setMgrtype(entity.getMgrtype());
			applyentity.setMrate(entity.getMrate());
			applyentity.setPrate(entity.getPrate());
			applyentity.setArate(entity.getArate());
			applyentity.setUrate(entity.getUrate());
			applyentity.setFrate(entity.getFrate());
			applyService.saveOrUpdateEntity(applyentity);
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,Params);
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
	 * @param entity
	 * @throws ServiceException
	 */
	private void setCode(LoanContractEntity entity) throws ServiceException {
		Long num = loanContractService.getMaxID();
		if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
		 String code = CodeRule.getCode("B", num);
		 entity.setCode(code);
	}
	
	
	/**
	 * 新增  借款合同 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long applyId = getLVal("formId");
			Long num = loanContractService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			if(!StringHandler.isValidStr(applyId.toString())) throw new ServiceException(ServiceException.ID_IS_NULL);
			ApplyEntity applyentity = applyService.getEntity(applyId);
			String accName = "";
			if(applyentity != null){
				Integer custType = applyentity.getCustType();
				Long customerId= applyentity.getCustomerId();
				if(custType==SysConstant.CUSTTYPE_0){
					CustomerInfoEntity customerInfoEntity = customerInfoService.getEntity(customerId);
					accName  = customerInfoEntity.getName();
				}else {
					EcustomerEntity ecustomerEntity = ecustomerService.getEntity(customerId);
					accName  = ecustomerEntity.getName();
				}
			}
			final String acc = accName;
			result =  FastJsonUtil.convertJsonToStr(applyentity,new Callback(){
				public void execute(JSONObject jsonObj) {
					jsonObj.put("accName", acc);
					jsonObj.remove("id");
					jsonObj.remove("code");
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
	 * 删除  借款合同 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  借款合同 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  借款合同 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  借款合同 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String formId = getVal("formId");
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			if(!StringHandler.isValidStr(formId)) new ServiceException(ServiceException.ID_IS_NULL);
			params.put("formId", Long.parseLong(formId));
			LoanContractEntity entity = loanContractService.getEntity(params);
			String id = null;
			if(entity != null){
				 id = entity.getId().toString();
				 SHashMap<String, Object> loanparams = new SHashMap<String, Object>();
				 params.put("contractId", Long.parseLong(id));
				 params.put("formId", Long.parseLong(formId));
				 params.put("isenabled", SqlUtil.LOGIC_NOT_EQ + SqlUtil.LOGIC+SysConstant.OPTION_DEL);
				 List<LoanInvoceEntity> loanInvoceEntity = loanInvoceService.getEntityList(loanparams);
				 if(!loanInvoceEntity.isEmpty() && loanInvoceEntity.size()>0){
					 outJsonString("-1");
					 return null;
				 }
			}
			
			
			String ids = getVal("ids");
			Integer isenabled = SysConstant.OPTION_DEL;
			loanContractService.enabledEntitys(id, isenabled);
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
	 * 获取指定的 借款合同 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			LoanContractEntity entity = loanContractService.navigationPrev(params);
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
	 * 获取指定的 借款合同 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			LoanContractEntity entity = loanContractService.navigationNext(params);
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
