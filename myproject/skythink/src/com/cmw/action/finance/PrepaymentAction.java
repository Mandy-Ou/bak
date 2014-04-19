package com.cmw.action.finance;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysCodeConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.ikexpression.FormulaUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.finance.PrepaymentEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.AccountEntity;
import com.cmw.entity.sys.BussProccEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.cache.BussProccCache;
import com.cmw.service.impl.cache.UserCache;
import com.cmw.service.impl.workflow.BussProccFlowService;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.finance.PrepaymentService;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.sys.AccountService;
import com.cmw.service.inter.sys.FormCfgService;


/**
 * 提前还款申请  ACTION类
 * @author 程明卫
 * @date 2013-09-11T00:00:00
 */
@Description(remark="提前还款申请ACTION",createDate="2013-09-11T00:00:00",author="程明卫",defaultVals="fcPrepayment_")
@SuppressWarnings("serial")
public class PrepaymentAction extends BaseAction {
	@Resource(name="prepaymentService")
	private PrepaymentService prepaymentService;
	@Resource(name="planService")
	private PlanService planService;
	@Resource(name="bussProccFlowService")
	private BussProccFlowService bussProccFlowService;
	
	@Resource(name="formCfgService")
	private FormCfgService formCfgService;
	
	
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="ecustomerService")
	private EcustomerService ecustomerService;
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="accountService")
	private AccountService accountService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 提前还款申请 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			SHashMap<String, Object> map = getQParams("status,custType#I,custName,ptype#I,startDate1,endDate1,appMan,startDate2,endDate2");
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("actionType", SysConstant.ACTION_TYPE_APPLYFORM_AUDIT_0);
			DataTable dt = prepaymentService.getResultList(map,getStart(),getLimit());
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
	 * 获取 提前还款申请 列表
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
	/**
	 * 获取 提前还款历史记录 列表
	 * @return
	 * @throws Exception
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
	/**
	 * 获取 提前还款一览表
	 * @return
	 * @throws Exception
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
			SHashMap<String, Object> map = getQParams("status,custType#I,custName,ptype#I,startDate1,endDate1,appMan,startDate2,endDate2,actionType#I");
			map.put(SysConstant.USER_KEY, user);
			map.put("actionType", actionType);
			String procIds = bussProccFlowService.getProcIdsByUser(user);
			if(StringHandler.isValidStr(procIds)){
				map.put("procIds", procIds);
			}
			dt = prepaymentService.getResultList(map,getStart(),getLimit());
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
	 * 获取展期申请单的 详情
	 * @return	./fcExtension_detail.action
	 * @throws Exception
	 */
	public String detail()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			DataTable dtResult = prepaymentService.detail(id);
			
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
		formParams.put("bussCode", SysCodeConstant.BUSS_PROCC_CODE_PREPAYMENT);
		formParams.put("formId", id);
		final JSONObject bussFormDatas = formCfgService.getBussFormDatas(formParams);
		return bussFormDatas;
	}
	/**
	 * 获取 提前还款申请 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			PrepaymentEntity entity = prepaymentService.getEntity(Long.parseLong(id));
			Long managerId = entity.getManagerId();
			UserEntity managerUser = UserCache.getUser(managerId);
			final String manager = (null == managerUser) ? null : managerUser.getEmpName();
			result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
				public void execute(JSONObject jsonObj) {
					jsonObj.put("manager", manager);
					FastJsonUtil.fmtDate2str(jsonObj,new String[]{"adDate","predDate","mgrDate","appDate"});
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
	 * 财务提前申请单详情
	 */
	public String getfinancePreMent() throws Exception{
		try{
			String id = getVal("id");
			if(!StringHandler.isValidStr(id)) new ServiceException(ServiceException.ID_IS_NULL);
			PrepaymentEntity entity = prepaymentService.getEntity(Long.parseLong(id));
			JSONObject obj = new JSONObject();
			if(null != entity){
				Date predDate = entity.getPredDate();
				String pdate = "";
				String bankName = "";
				String bankAccount ="";
				if(StringHandler.isValidObj(predDate)){
					pdate = DateUtil.dateFormatToStr(predDate);
					Long accountId = entity.getAccountId();
					if(StringHandler.isValidObj(accountId)){
						AccountEntity accountEntity = accountService.getEntity(accountId);
						if(accountEntity!=null){
							bankName = accountId+"##"+accountEntity.getBankName();
							bankAccount = accountEntity.getAccount();
						}
					}
				}
				 
				
				Double frate = entity.getFrate();
				BigDecimal  freeAmount = entity.getFreeamount();
				BigDecimal yfreeamount = entity.getYfreeamount();
				if(null == yfreeamount) yfreeamount = new BigDecimal("0");
				if(null != freeAmount && freeAmount.doubleValue()>0){
					freeAmount = BigDecimalHandler.sub2BigDecimal(freeAmount, yfreeamount);
				}
				BigDecimal  imamount = entity.getImamount();
				Integer ptype = entity.getPtype();
				Integer isretreat = entity.getIsretreat();
				Long contractId = entity.getContractId();
				Long payplanId = entity.getPayplanId();
				LoanContractEntity loanEntity = loanContractService.getEntity(contractId);//借款合同ID
				Integer xstatus = entity.getXstatus();
				SHashMap<String, Object> params = new SHashMap<String, Object>();
//				params.put("contractId", contractId);
//				params.put("xpayDate", pdate);
				params.put("payplanId", payplanId);
				DataTable dt = prepaymentService.getZamountDt(params);
				obj = dt.getJsonObj();
				//
				String loanCode = loanEntity.getCode();
				Integer custType = loanEntity.getCustType();
				Long customerId = loanEntity.getCustomerId();
				String name = null;
				if(custType == SysConstant.CUSTTYPE_0){
					CustomerInfoEntity customerInfoEntity= customerInfoService.getEntity(customerId);
					if(customerInfoEntity != null){
						name = customerInfoEntity.getName();
					}
				}else{
					EcustomerEntity ecustomerEntity= ecustomerService.getEntity(customerId);
					if(ecustomerEntity != null){
						name = ecustomerEntity.getName();
					}
				}
				String adDate = "";
				if(null != entity.getAdDate()) adDate = DateUtil.dateFormatToStr(entity.getAdDate());
				String payBank = loanEntity.getPayBank();
				String payAccount = loanEntity.getPayAccount();
				String accName = loanEntity.getAccName();
				obj.put("bankAccount", bankAccount);
				obj.put("accountId", bankName);
				obj.put("adDate", adDate);
				obj.put("predDate", pdate);
				obj.put("frate", frate);
				obj.put("isretreat", isretreat);
				obj.put("ptype", ptype);
				obj.put("freeAmount",freeAmount.doubleValue());
				obj.put("imamount", imamount.doubleValue());
				obj.put("loanCode", loanCode);
				obj.put("name", name);
				obj.put("payBank", payBank);
				obj.put("payAccount", payAccount);
				obj.put("accName", accName);
				obj.put("xstatus", xstatus);
				if(null != xstatus && xstatus.intValue() != BussStateConstant.PREPAYMENT_XSTATUS_2){//如果未结清，则要实时计算应收合计
					double totalAmount = getTotalAmount(obj);
					obj.put("totalAmount", totalAmount);
				}
			}
			result = ResultMsg.getSuccessMsg(obj);
		}catch(ServiceException ex){
			result = ResultMsg.getFailureMsg(this, ex.getMessage());
			ex.printStackTrace();
		}catch(Exception ex){
			result = ResultMsg.getFailureMsg(this,ResultMsg.SYSTEM_ERROR);
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	private double getTotalAmount(JSONObject jsonObj){
		double totalAmount = 0d;
		Double reprincipal = jsonObj.getDouble("reprincipal");
		Double zinterest = jsonObj.getDouble("zinterest");
		Double zmgrAmount = jsonObj.getDouble("zmgrAmount");
		Double zpenAmounts = jsonObj.getDouble("zpenAmounts");
		Double zdelAmounts = jsonObj.getDouble("zdelAmounts");
		Double freeAmount = jsonObj.getDouble("freeAmount");
		Double imamount = jsonObj.getDouble("imamount");
		if(null == reprincipal) reprincipal = 0d;
		if(null == zinterest) zinterest = 0d;
		if(null == zmgrAmount) zmgrAmount = 0d;
		if(null == zpenAmounts) zpenAmounts = 0d;
		if(null == zdelAmounts) zdelAmounts = 0d;
		if(null == freeAmount) freeAmount = 0d;
		if(null == imamount) imamount = 0d;
		totalAmount = reprincipal + zinterest + zmgrAmount + zpenAmounts + zdelAmounts + freeAmount - imamount;
		totalAmount = StringHandler.Round(totalAmount);
		return totalAmount;
	}
	
	/**
	 * 获取 提前还款计算数据 详情
	 * @return	./fcPrepayment_getCal.action
	 * @throws Exception
	 */
	public String getCal()throws Exception {
		try {
			String contractId = getVal("contractId");
			if(!StringHandler.isValidStr(contractId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			String xpayDate = getVal("xpayDate");
			if(!StringHandler.isValidStr(xpayDate)) throw new ServiceException("xpayDate.isnull");
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("contractId", contractId);
			params.put("xpayDate", xpayDate);
			DataTable dt = prepaymentService.getCalculateDt(params);
			result = (null == dt || dt.getRowCount() == 0) ? "{}" : dt.getJsonObjStr();
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
	 * 获取 提前还款手续费或应退息费金额 详情
	 * @return	./fcPrepayment_getFreeAmount.action
	 * @throws Exception
	 */
	public String getFreeAmount()throws Exception {
		try {
			Long planId = getLVal("planId");
			if(!StringHandler.isValidObj(planId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			PlanEntity planEntity = planService.getEntity(planId);
			if(!StringHandler.isValidObj(planEntity)) throw new ServiceException(ServiceException.OBJECT_IS_NULL);
			Integer freeType = getIVal("freeType");
			Map<String,Object> vars = new HashMap<String, Object>();
			String resourceKey = null;
			if(null != freeType && freeType.intValue() == 1){/*获取提前还款手续费*/
				Double frate = getDVal("frate");
				vars.put("frate", frate);
				resourceKey = "prepayment_formula";
			}else{/*获取息费*/
				String adDateStr = getVal("predDate");
				Date adDate = DateUtil.dateFormat(adDateStr);
				Date xpayDate = planEntity.getXpayDate();
				Date preXpayDate = DateUtil.minusMonthToDate(xpayDate, 1);
				Integer udays = DateUtil.calculateLimitDate(preXpayDate, adDate, DateUtil.DAY);
				vars.put("udays", udays);
				resourceKey = "retreatim_formula";
			}
			Map<String, Object> data = getFormulaVal(planEntity, vars,resourceKey);
			result = ResultMsg.getSuccessMsg(data);
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

	private Map<String, Object> getFormulaVal(PlanEntity planEntity,Map<String, Object> vars, String resourceKey) {
		String formulaStr = StringHandler.GetResValue("formula", resourceKey);
		String[] formulaMeteas = formulaStr.split("##");
		String expression = null;
		String cmns = null;
		if(null != formulaMeteas && formulaMeteas.length > 0){
			expression = formulaMeteas[0];
			if(formulaMeteas.length > 1){
				 cmns = formulaMeteas[1];
			}
		}
		if(StringHandler.isValidStr(cmns)){
			String[] cmnArr = cmns.split(",");
			String[] fieldsArr = planEntity.getFields();
			Object[] datas = planEntity.getDatas();
			for(int i=0,count=fieldsArr.length; i<count; i++){
				String field = fieldsArr[i];
				for(String cmn : cmnArr){
					if(!StringHandler.isValidStr(cmn)) continue;
					String[] kvArr = cmn.split(":");
					String dataType = null;
					cmn = kvArr[0];
					if(kvArr.length > 1){
						dataType = kvArr[1];
					}
					if(!cmn.equals(field)) continue;
					Object value = datas[i];
					if(StringHandler.isValidStr(dataType)){
						if(dataType.equals("I")){
							vars.put(field, (null == value) ? 0 : Integer.parseInt(value.toString()));
						}else if(dataType.equals("B")){
							vars.put(field, (null == value) ? 0 : new BigDecimal(value.toString()));
						}else if(dataType.equals("D")){
							vars.put(field, (null == value) ? 0 : Double.parseDouble(value.toString()));
						}else if(dataType.equals("F")){
							vars.put(field, (null == value) ? 0 : Float.parseFloat(value.toString()));
						}else{
							vars.put(field, value);
						}
					}else{
						vars.put(field, value);
					}
					break;
				}
			}
		}
		
		Object freeAmountresult = FormulaUtil.parseExpression(expression, vars);
		Map<String,Object> data = new HashMap<String, Object>();
		if(null == freeAmountresult) freeAmountresult = "0";
		data.put("amount", StringHandler.Round(freeAmountresult.toString(), 2));
		return data;
	}
	
	
	
	/**
	 * 保存 提前还款申请 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			PrepaymentEntity entity = BeanUtil.copyValue(PrepaymentEntity.class,getRequest());
			boolean isAdd = (null == entity.getId());
			if(isAdd){
				String code = getCode();
				entity.setCode(code);
				Long managerId = this.getCurUser().getUserId();
				entity.setManagerId(managerId);
			}
			
			prepaymentService.saveOrUpdateEntity(entity);
			Map<String,Object> appnendMap = new HashMap<String, Object>();
			appnendMap.put("applyId", entity.getId());
			result = ResultMsg.getSuccessMsg(this, ResultMsg.SAVE_SUCCESS,appnendMap);
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
		Long num = prepaymentService.getMaxID();
		if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
		String code = CodeRule.getCode("P", num);
		return code;
	}
	
	
	/**
	 * 新增  提前还款申请 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long num = prepaymentService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			String code = CodeRule.getCode("P", num);
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
	 * 保存计算结果
	 */
	public String saveResult() throws Exception{
		try {
			String id = getVal("id");
			BigDecimal freeamount = StringHandler.Round2BigDecimal(getDVal("freeamount"));
			BigDecimal imamount = StringHandler.Round2BigDecimal(getDVal("imamount"));
			BigDecimal totalAmount = StringHandler.Round2BigDecimal(getDVal("totalAmount"));
			Double frate = getDVal("frate");
			Integer isretreat = getIVal("isretreat");
			Long payplanId = getLVal("payplanId");
			Date reviewDate =DateUtil.dateFormat(getVal("reviewDate"));
			Date predDate =DateUtil.dateFormat(getVal("predDate"));
			Long reviewer = getLVal("reviewer");
			if(!StringHandler.isValidStr(id)) new  ServiceException(ServiceException.ID_IS_NULL);
			PrepaymentEntity prepaymentEntity =  prepaymentService.getEntity(Long.parseLong(id));
			prepaymentEntity.setFreeamount(freeamount);
			prepaymentEntity.setPayplanId(payplanId);
			prepaymentEntity.setReviewer(reviewer);
			prepaymentEntity.setReviewDate(reviewDate);
			prepaymentEntity.setImamount(imamount);
			prepaymentEntity.setTotalAmount(totalAmount);
			prepaymentEntity.setIsretreat(isretreat);
			prepaymentEntity.setPredDate(predDate);
			prepaymentEntity.setFrate(frate);
			BeanUtil.setModifyInfo(getCurUser(), prepaymentEntity);
			prepaymentService.saveOrUpdateEntity(prepaymentEntity);
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
	 * 保存财务提前还款结果
	 */
	@SuppressWarnings("unchecked")
	public String  financeSave() throws Exception{
		try {
			String qcmns = "id#L,payplanId#L,freeAmount#O,imamount#O,interest#O,mgrAmount#O,totalAmount#O,accountId#L,rectDate,vtempCode,isVoucher#I,sysId#L,detail#I";
			SHashMap<String, Object> params = getQParams(qcmns);
			params.put(SysConstant.USER_KEY, getCurUser());
			Map<AmountLogEntity, DataTable> logDataMap = (Map<AmountLogEntity, DataTable>)prepaymentService.doComplexBusss(params);
			Integer isVoucher = params.getvalAsInt("isVoucher");
			if(null != isVoucher && isVoucher.intValue() == SysConstant.SAVE_VOUCHER_1) saveVouchers(logDataMap);
			Map<String, Object> appendParams = new HashMap<String, Object>();
			String amountLogIds = getAmountLogIds(logDataMap);
			appendParams.put("amountLogIds", amountLogIds);
			result = ResultMsg.getSuccessMsg(appendParams);
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
	
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	private void saveVouchers(Map<AmountLogEntity,DataTable> logDataMap) throws ServiceException{
		SHashMap<String,Object> params = new SHashMap<String, Object>();
		Long sysId = getLVal("sysId");
		String vtempCode = getVal("vtempCode");
		params.put("sysId", sysId);
		params.put("vtempCode", vtempCode);
		UserEntity user = getCurUser();
		params.put(SysConstant.USER_KEY, user);
		amountLogService.saveVouchers(logDataMap, params);
	}
	
	/**
	 * 获取实收金额日志记录表
	 * @param logDataMap	
	 * @return
	 */
	private String getAmountLogIds(Map<AmountLogEntity, DataTable> logDataMap){
		if(null == logDataMap || logDataMap.size() == 0) return null;
		StringBuffer sb = new StringBuffer();
		Set<AmountLogEntity> keys = logDataMap.keySet();
		for(AmountLogEntity amountLogObj : keys){
			Long amountLogId = amountLogObj.getId();
			sb.append(amountLogId+",");
		}
		return StringHandler.RemoveStr(sb);
	}
	
	/**
	 * 删除  提前还款申请 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  提前还款申请 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  提前还款申请 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  提前还款申请 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			prepaymentService.enabledEntitys(ids, isenabled);
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
	 * 获取指定的 提前还款申请 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			PrepaymentEntity entity = prepaymentService.navigationPrev(params);
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
	 * 获取指定的 提前还款申请 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			PrepaymentEntity entity = prepaymentService.navigationNext(params);
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

