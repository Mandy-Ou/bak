package com.cmw.action.funds;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
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
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CustBaseEntity;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.funds.AmountProofEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.crm.CustBaseService;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.funds.AmountProofService;
import com.cmw.service.inter.sys.BussProccService;
import com.cmw.service.inter.sys.UserService;

/**
 * 资金追加申请ACTION
 * 
 * @author 彭登浩
 * @date 2014-01-15T00:00:00
 */

@Description(remark = "资金追加申请ACTION", createDate = "2014-01-15T00:00:00", author = "彭登浩", defaultVals = "fuAmountProof_")
@SuppressWarnings("serial")
public class AmountProofAction extends BaseAction {
	@Resource(name = "amountProofService")
	private AmountProofService amountProofService;
	
	@Resource(name = "loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name = "applyService")
	private ApplyService applyService;
	
	@Resource(name = "bussProccService")
	private BussProccService bussProccService;
	
	@Resource(name = "ecustomerService")
	private EcustomerService ecustomerService;
	
	@Resource(name = "custBaseService")
	private CustBaseService custBaseService;
	
	@Resource(name = "customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	private String result = ResultMsg.GRID_NODATA;

	/**
	 * 获取 资金追加申请 列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception {
		try {
			
			SHashMap<String, Object> map =getQParams("name,loanCode,bdate#D,amount#O,bamount#O,tamount#O,appDate#D ");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = amountProofService.getResultList(map, getStart(),getLimit());
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonArr();
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}


	
	/**
	 * 获取 资金追加申请 详情
	 * 
	 * @return
	 * @throws Exception
	 */
	public String detail() throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) new ServiceException(ServiceException.ID_IS_NULL);
			final AmountProofEntity amountProofEntity = amountProofService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(amountProofEntity, new Callback() {
				@Override
				public void execute(JSONObject jsonObj) {
					try {
						LoanContractEntity loancon=loanContractService.getEntity(amountProofEntity.getContractId());
						jsonObj.put("appDate", StringHandler.dateFormatToStr("yyyy-MM-dd", amountProofEntity.getAppDate()));
						jsonObj.put("bdate", StringHandler.dateFormatToStr("yyyy-MM-dd", amountProofEntity.getBdate()));
						UserEntity user=userService.getEntity(amountProofEntity.getAppManId());
						if(user!=null)
							jsonObj.put("appManName", user.getEmpName());
						if(loancon!=null){
							jsonObj.put("custType",loancon.getCustType());
							jsonObj.put("loanId", loancon.getId());
							jsonObj.put("customerId", loancon.getCustomerId());
							jsonObj.put("loanCode", loancon.getCode());
							jsonObj.put("applyId", loancon.getId());
							jsonObj.put("manager", amountProofEntity.getCreator());
							ApplyEntity apply=applyService.getEntity(loancon.getFormId());
							if(apply!=null){
								jsonObj.put("appid", apply.getId());
							}
							BussProccEntity bussProccEntity = bussProccService.getEntity(amountProofEntity.getBreed());
							if(bussProccEntity!=null){
								String pdid=bussProccEntity.getPdid();
								jsonObj.put("pdid", pdid);
							}
							if(loancon.getCustType()==1){
								EcustomerEntity ecustomer=ecustomerService.getEntity(loancon.getCustomerId());
								jsonObj.put("custName", ecustomer.getName());
								jsonObj.put("baseId", ecustomer.getBaseId());
								CustBaseEntity cust=custBaseService.getEntity(ecustomer.getBaseId());
								jsonObj.put("code", cust.getCode());
								
							}else{
								CustomerInfoEntity customer=customerInfoService.getEntity(loancon.getCustomerId());
								jsonObj.put("custName", customer.getName());
								jsonObj.put("baseId", customer.getBaseId());
								CustBaseEntity cust=custBaseService.getEntity(customer.getBaseId());
								jsonObj.put("code", cust.getCode());
							}
						}
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
			});
			
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	/**
	 * 获取 资金追加申请 详情
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public String get() throws Exception {
		try {
			String id = getVal("id");
			if(StringHandler.isValidStr(id)) new ServiceException(ServiceException.ID_IS_NULL);
			final AmountProofEntity amountProofEntity = amountProofService.getEntity(Long.parseLong(id));
			result = FastJsonUtil.convertJsonToStr(amountProofEntity, new Callback() {
				@Override
				public void execute(JSONObject jsonObj) {
					try {
						LoanContractEntity loancon=loanContractService.getEntity(amountProofEntity.getContractId());
						jsonObj.put("appDate", StringHandler.dateFormatToStr("yyyy-MM-dd", amountProofEntity.getAppDate()));
						jsonObj.put("bdate", StringHandler.dateFormatToStr("yyyy-MM-dd", amountProofEntity.getBdate()));
						if(loancon!=null){
							jsonObj.put("custType",loancon.getCustType());
							jsonObj.put("loanId", loancon.getId());
							jsonObj.put("customerId", loancon.getCustomerId());
						}
					} catch (ServiceException e) {
						e.printStackTrace();
					}
				}
			});
			
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	/**
	 * @return 
	 * @throws ServiceException
	 */
	private String getCode() throws ServiceException {
		Long num = amountProofService.getMaxID();
		if (null == num) throw new ServiceException( ServiceException.OBJECT_MAXID_FAILURE);
		String code = CodeRule.getCode("R", num);
		return code;
	}


	/**
	 * 保存 资金追加申请资料
	 * 
	 * @return
	 * @throws Exception
	 */
	
	public String save() throws Exception {
		try {
			AmountProofEntity entity = BeanUtil.copyValue(AmountProofEntity.class,getRequest());
			amountProofService.saveOrUpdateEntity(entity);
			Long id = entity.getId();
			HashMap<String, Object> appendMap  =  new HashMap<String, Object>();
			appendMap.put("id", id);
			result = ResultMsg.getSuccessMsg(appendMap);
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
	public String getContract() throws Exception{
		try{
			String contractId = getVal("contractId");
			if(!StringHandler.isValidStr(contractId)) new ServiceException(ServiceException.ID_IS_NULL);
			LoanContractEntity loanContractEntity = loanContractService.getEntity(Long.parseLong(contractId));
			HashMap<String, Object> appParams = new HashMap<String, Object>();
			if(loanContractEntity != null){
				BigDecimal appAmount = loanContractEntity.getAppAmount();
				appParams.put("amount", appAmount);
				Date payDate = loanContractEntity.getPayDate();
				String pDate = DateUtil.dateFormatToStr(payDate);
 				appParams.put("bdate", pDate);
				appParams.put("contractId", contractId);
			}
			result = ResultMsg.getSuccessMsg(appParams);
		}catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	/**
	 * 新增 资金追加申请
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		try {
			String code = getCode();
			result = JsonUtil.getJsonString("code", code);
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 删除 资金追加申请
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}

	/**
	 * 启用 资金追加申请
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enabled() throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}

	/**
	 * 禁用 资金追加申请
	 * 
	 * @return
	 * @throws Exception
	 */
	public String disabled() throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}

	/**
	 * 删除/起用/禁用 资金追加申请
	 * 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg) throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			amountProofService.enabledEntitys(ids, isenabled);
			result = ResultMsg.getSuccessMsg(this, sucessMsg);
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 获取指定的 资金追加申请 上一个对象
	 * 
	 * @return
	 * @throws Exception
	 */
	public String prev() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			AmountProofEntity entity = amountProofService.navigationPrev(params);
			Map<String, Object> appendParams = new HashMap<String, Object>();
			if (null == entity) {
				result = ResultMsg.getFirstMsg(appendParams);
			} else {
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

	/**
	 * 获取指定的 资金追加申请下一个对象
	 * 
	 * @return
	 * @throws Exception
	 * 
	 */
	public String next() throws Exception {
		try {
			SHashMap<String, Object> params = getQParams("id");
			AmountProofEntity entity = amountProofService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String, Object> appendParams = new HashMap<String, Object>();
			if (null == entity) {
				result = ResultMsg.getLastMsg(appendParams);
			} else {
				result = ResultMsg.getSuccessMsg(entity, appendParams);
			}
		} catch (ServiceException ex) {
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		} catch (Exception ex) {
			result = ResultMsg.getFailureMsg(this, ResultMsg.SYSTEM_ERROR);
			if (null == result)
				result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}

}
