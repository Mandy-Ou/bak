package com.cmw.action.finance;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder.In;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.excel.importer.POIXlsImporter;
import com.cmw.core.kit.excel.importer.POIXlsImporter.BreakAction;
import com.cmw.core.kit.file.FileUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.CodeRule;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DataTable.JsonDataCallback;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.FastJsonUtil.Callback;
import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.lock.LockManager;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.LoanInvoceService;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.sys.UserService;


/**
 * 放款单  ACTION类
 * @author 程明卫	
 * @date 2013-01-15T00:00:00
 */
@Description(remark="放款单ACTION",createDate="2013-01-15T00:00:00",author="程明卫",defaultVals="fcLoanInvoce_")
@SuppressWarnings("serial")
public class LoanInvoceAction extends BaseAction {
	@Resource(name="loanInvoceService")
	private LoanInvoceService loanInvoceService;
	LockManager lockMgr = LockManager.getInstance();
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="applyService")
	private ApplyService applyService;
	
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	
	@Resource(name="ecustomerService")
	private EcustomerService ecustomerService;
	
	private String result = ResultMsg.GRID_NODATA;
	/**
	 * 获取 放款单 列表
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception {
		try {
			Integer custType = getIVal("custType");
			Integer auditState = getIVal("auditState");
			Long formId = getLVal("formId");
			Integer isLoan = getIVal("isLoan");
			SHashMap<String, Object> map =  new SHashMap<String, Object>();
			if(null != custType && custType.intValue() == SysConstant.CUSTTYPE_1){
				/*-->企业贷款发放<--*/
				//name,kind,ccode,payName,regBank,account,eqopAmount,payAmount,startDate,endDate
				map = getQParams("id#L,auditState#I,state#I,custType#I,name,kind,ccode,payName,regBank,account,eqopAmount,payAmount#O,startDate,endDate");
			}else{
				/*个人贷款发放*/
				
				//name,cardType,cardNum,payName,regBank,account,eqopAmount,payAmount,startDate,endDate
				 map = getQParams("id#L,auditState#I,state#I,custType#I,name,cardType#I,cardNum,payName,regBank,account,eqopAmount,payAmount#O,startDate,endDate");
			}
			 map.put("isLoan", isLoan);
			 map.put("custType", custType);
			 map.put("auditState", auditState);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			map.put("formId", formId);
			DataTable dt = loanInvoceService.getResultList(map,getStart(),getLimit());
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
	 * 放款合同查询
	 */
	public String loanInvoceQuery() throws Exception {
		try {
			Integer auditState = getIVal("auditState");
			SHashMap<String, Object> map =  getQParams("code,ccode,appAmount,payName,regBank,account,payAmount,prate,eqopAmount,cashier");
		    map.put("auditState", auditState);
			map.put(SysConstant.USER_KEY, this.getCurUser());
			DataTable dt = loanInvoceService.getLoanInvoceQuery(map,getStart(),getLimit());
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
	 * 提交放款通知书
	 */
	public String update() throws Exception{
		try{
			UserEntity user = getCurUser();
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			LoanInvoceEntity entity = loanInvoceService.getEntity(id);
			entity.setAuditState(BussStateConstant.LOANINVOCE_AUDITSTATE_1);
			loanInvoceService.updateEntity(entity);
			String code = entity.getCode();
			HashMap<String, Object> appendParams = new HashMap<String, Object>();
			appendParams.put("msg", StringHandler.formatFromResource(user.getI18n(), "loanInvoce.auditState_1",code));
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
	/**
	 * 同意放款
	 */
	public String agreeloan() throws Exception{
		try{
			Long id = getLVal("id");
			Integer auditState = getIVal("auditState");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			if(!StringHandler.isValidObj(auditState)) throw new ServiceException("审批状态不能为空!");
			Map<String, Object> complexData = new HashMap<String, Object>();
			complexData.put(SysConstant.USER_KEY, getCurUser());
			complexData.put("id", id);
			complexData.put("auditState", auditState);
			loanInvoceService.doAuditInvoce(complexData);
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
	 * 获取 放款单 详情
	 * @return
	 * @throws Exception
	 */
	public String get()throws Exception {
		try {
			Long id = getLVal("id");
			if(!StringHandler.isValidObj(id)) throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("id", id);
			LoanInvoceEntity entity = loanInvoceService.getEntity(map);
				if(entity==null){
					outJsonString("-1");
					return null;
				}else{
					getloaninvice(entity);
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
	 * 保存 放款单 
	 * @return
	 * @throws Exception
	 */
	public String save()throws Exception {
		try {
			LoanInvoceEntity entity = BeanUtil.copyValue(LoanInvoceEntity.class,getRequest());
			String cashier = entity.getCashier().toString();
			if(StringHandler.isValidStr(cashier)){
				String[] leaderIdName = cashier.split("##");
				if(null != leaderIdName && leaderIdName.length>1){
					Long leader = Long.parseLong(leaderIdName[0]);
					entity.setCashier(leader);
				}
			}
			entity.setUnAmount(new BigDecimal(0.00));
			entity.setRealDate(entity.getPayDate());
			loanInvoceService.saveOrUpdateEntity(entity);
			result = ResultMsg.getSuccessMsg(this, entity,ResultMsg.SAVE_SUCCESS);
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
	 * 获取 放款单据数据(贷款资金收付管理 ---> 个人/企业贷款发放)
	 * @return
	 * @throws Exception
	 */
	public String obtain()throws Exception {
		try {
			Long id = getLVal("id");
			String ids = getVal("ids");
			Integer custType = getIVal("custType");
			Integer auditState = BussStateConstant.LOANINVOCE_AUDITSTATE_2;
			if(!StringHandler.isValidObj(id) && !StringHandler.isValidStr(ids)) throw new ServiceException("参数 id 和 ids 必须至少有一个不能为空!");
			String key = getVal("key");
			if(!StringHandler.isValidStr(key)) throw new ServiceException("参数 key 不能为空!");
			UserEntity currUser = getCurUser();
			boolean isBatch = false;
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			if(StringHandler.isValidObj(id)){
				ids = id.toString();
			}else{
				isBatch = true;
			}
			map.put("ids", ids);
			map.put("custType", custType);
			map.put("auditState", auditState);
			DataTable dt = loanInvoceService.getResultList(map);
			lockMgr.applyLock(currUser, dt, "name", key);/*申请锁定指定的客户*/
			if(dt != null && dt.getRowCount()>0){
				result = isBatch ? dt.getJsonArr() : dt.getJsonObjStr();
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
	 * 获取指定的菜单对象
	 * @return
	 * @throws Exception
	 */
	public String gets()throws Exception {
		try {
			SHashMap<String,Object> map = getQParams("id#L");
			DataTable dt = loanInvoceService.getLoanInvoceQuery(map,-1, -1);
			result = (null == dt || dt.getRowCount() == 0) ? ResultMsg.NODATA : dt.getJsonObjStr();
		} catch (ServiceException ex){
			result = ResultMsg.getFailureMsg(this,ex.getMessage());
			if(null == result) result = ex.getMessage();
			ex.printStackTrace();
		}
		outJsonString(result);
		return null;
	}
	
	
	
	
	/**
	 * 从导入的Excel 文件中获取 放款单据数据(贷款资金收付管理 ---> 个人/企业贷款发放)
	 * @return 
	 * @throws Exception
	 */
	public String readxls()throws Exception {
		try {
			String filePath = getVal("filePath");
			if(!StringHandler.isValidStr(filePath)) throw new ServiceException("参数 filePath 不能为空!");
			String key = getVal("key");
			if(!StringHandler.isValidStr(key)) throw new ServiceException("参数 key 不能为空!");
			UserEntity currUser = getCurUser();
			filePath = FileUtil.getFilePath(getRequest(), filePath);
			POIXlsImporter importer = new POIXlsImporter(filePath);
			BreakAction breakAction = new BreakAction(){
				public boolean exit(int rowNum, Object cellVal) {/*返回 true : 退出读取Excel数据*/
					return (null != cellVal && cellVal.equals("合计")) ? true : false;
				}
			};
			DataTable dtXls = importer.readDatasToDt(7,breakAction,"code,name,appAmount,payName,regBank,account,payAmount,payDate");
			if(null != dtXls && dtXls.getRowCount() > 0){
				StringBuffer sb = new StringBuffer();
				for(int i=0,count=dtXls.getRowCount(); i<count; i++){
					String code = dtXls.getString(i, "code");
					sb.append("'"+code+"'").append(",");
				}
				SHashMap<String, Object> map = new SHashMap<String, Object>();
				String codes = StringHandler.RemoveStr(sb);
				map.put("codes", codes);
				final DataTable dt = loanInvoceService.getIds(map);
				JsonDataCallback callback = lockCustomers(key, currUser, dtXls,dt);
				result = dtXls.getJsonArr(callback);
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
	 * 锁住业务客户
	 * @param key 业务钥匙
	 * @param currUser 当前用户
	 * @param dtXls 装有 Excel 数据的 DataTable 对象 
	 * @param dt 还有 id,code 列的 DataTable 对象	
	 * @return	返回 JsonDatCallback 对象
	 * @throws ServiceException
	 */
	private JsonDataCallback lockCustomers(String key, UserEntity currUser,
			DataTable dtXls,final DataTable dt)
			throws ServiceException {
		//--> 获取锁住的数据
		final JSONArray lockCustomers = lockMgr.getLockData(key, dtXls,"name");
		final List<String> lockList = new ArrayList<String>();
		JsonDataCallback callback = new JsonDataCallback() {
			public void makeJson(JSONObject jsonObj) {
				/* step 1 : 通过比较相同的 code 获取放款单ID 并存入 jsonObj 中*/
				String sourceCode = jsonObj.getString("code");
				Long id = null;
				for(int i=0,count=dt.getRowCount(); i<count; i++){
					String code = dt.getString(i, "code");
					if(sourceCode.equals(code)){
						id = dt.getLong(i, "id");
						break;
					}
				}
				if(null == id || id <= 0) id = System.currentTimeMillis();
				jsonObj.put("id", id);
				
				/* step 2 : 通过比较相同的 客户姓名(name) 获取客户是否锁定 并存入 jsonObj 中*/
				boolean locked = false;
				String customerName = jsonObj.getString("name");
				if(null != lockCustomers && lockCustomers.size() > 0){
					for(int i=0,count=lockCustomers.size(); i<count; i++){
						JSONObject obj = lockCustomers.getJSONObject(i);
						String customers = obj.getString("customers");
						String[] customerArr = customers.split(",");
						for(String customer : customerArr){
							if(customerName.equals(customer)){
								locked = true;
								break;
							}
						}
						if(locked) break;
					}
				}
				jsonObj.put("locked", locked);
				
				/* step 3 : 获取未被锁定的用户*/
				if(!locked) lockList.add(customerName);
			}
		};
		lockMgr.applyLock(currUser, lockList, key);/*申请锁定指定的客户*/
		return callback;
	}
	
	/**
	 * 财务放款/批量放款 
	 * @return
	 * @throws Exception
	 */
	
	public String loans()throws Exception {
		try {
			Long id = getLVal("id");
			String ids = getVal("ids");
			Long accountId = getLVal("accountId");
			String realDate = getVal("realDate");
			if(!StringHandler.isValidObj(accountId)) throw new ServiceException("放款银行不能为空!");
			if(!StringHandler.isValidStr(realDate)) throw new ServiceException("实际放款日期不能为空!");
			UserEntity user = getCurUser();
			SHashMap<String,Object> complexData = new SHashMap<String, Object>();
			complexData.put("id", id);
			complexData.put(SysConstant.USER_KEY, user);
			if(StringHandler.isValidStr(ids)) ids = getUnDoIdDatas(ids);
			complexData.put("ids", ids);
			complexData.put("accountId", accountId);
			complexData.put("realDate", realDate);
			
			Long sysId = getLVal("sysId");
			String vtempCode = getVal("vtempCode");
			complexData.put("sysId", sysId);
			complexData.put("vtempCode", vtempCode);
			@SuppressWarnings("unchecked")
			Map<AmountLogEntity,DataTable> logDataMap = (Map<AmountLogEntity,DataTable>) loanInvoceService.doComplexBusss(complexData);
			/*--->2:生成财务凭证*/
			Integer isVoucher = getIVal("isVoucher");
			if(null != isVoucher && isVoucher.intValue() == SysConstant.SAVE_VOUCHER_1) saveVouchers(logDataMap);
			//--> 解锁指定用户的业务数据
			//lockMgr.releaseLock(user, key);
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
	 * 找出未放过款的放款单ID数据
	 * 原因：客户有可能将Excel文件导多次，这样就会存在重复放款的问题
	 *      该方法处理重复放款的问题。
	 * @param ids
	 * @return
	 * @throws ServiceException
	 */
	private String getUnDoIdDatas(String ids) throws ServiceException{
		//String ids = map.getvalAsStr("ids");
		String newIds = null;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		map.put("state", BussStateConstant.LOANINVOCE_STATE_0);
		DataTable undoDt = loanInvoceService.getIds(map);
		if(null == undoDt || undoDt.getRowCount() == 0) throw new ServiceException("导入的数据之前已经放完款，请不要重复放款!");
		StringBuilder sb = new StringBuilder();
		for(int i=0,count=undoDt.getRowCount(); i<count; i++){
			String id = undoDt.getString(i, "id");
			sb.append(id).append(",");
		}
		if(null != sb && sb.length() > 0){
			newIds = StringHandler.RemoveStr(sb);
		}
		return newIds;
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
	 * 新增  放款单 
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception {
		try {
			Long formId = getLVal("formId");
			String code = null;
			if(!StringHandler.isValidObj(formId))throw new ServiceException(ServiceException.ID_IS_NULL);
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("formId", formId);
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			HashMap<String, Object>  map = new HashMap<String, Object>();
			ApplyEntity ApplyEntity = applyService.getEntity(formId);
			Integer custType = ApplyEntity.getCustType();
			String name = "";
			if(StringHandler.isValidObj(custType)){
				Long customerId = ApplyEntity.getCustomerId();
				if(custType == SysConstant.CUSTTYPE_0){
					CustomerInfoEntity customerInfoEntity = customerInfoService.getEntity(customerId);
					if(customerInfoEntity != null){}{
						name = customerInfoEntity.getName();
					}
				}else{
					EcustomerEntity ecustomerEntity = ecustomerService.getEntity(customerId);
					name = ecustomerEntity.getName();
				}
			}
			/*--> 根据formId 找到借款合同实体<--*/
			LoanContractEntity  loanEntity= loanContractService.getEntity(params);
			if(loanEntity==null) {
				outJsonString("-1");
				return null;
			}
			String accName = loanEntity.getAccName();
			String payBank = loanEntity.getPayBank();
			String payAccount = loanEntity.getPayAccount();
			Long contractId = loanEntity.getId();
			BigDecimal appAmount = loanEntity.getAppAmount();
			Double prate = loanEntity.getPrate();
			Date payDate =loanEntity.getPayDate();
			String pdate = DateUtil.dateFormatToStr(payDate);
			String loancode =loanEntity.getCode();
			Double rate =loanEntity.getRate();
			Date doDate =loanEntity.getDoDate();
			Integer yearLoan = loanEntity.getYearLoan();
			Integer monthLoan = loanEntity.getMonthLoan();
			Integer dayLoan = loanEntity.getDayLoan();
			Double mrate = loanEntity.getMrate();
			String dodt = DateUtil.dateFormatToStr(doDate);
			map .put("contractId", contractId);
			map .put("appAmount", appAmount);
			map .put("prate", prate);
			map .put("payDate", pdate);
			map .put("loancode", loancode);
			map .put("rate", rate);
			map .put("payDate", dodt);
			map.put("payName", accName);
			map.put("regBank", payBank);
			map.put("account", payAccount);
			map.put("name", name);
			map.put("yearLoan", yearLoan);
			map.put("monthLoan", monthLoan);
			map.put("dayLoan", dayLoan);
			map.put("mrate", mrate);
			noLoanMoney(params, map, appAmount,prate);
			
			/*-->随机放款单code编号<--*/
			Long num = loanInvoceService.getMaxID();
			if(null == num) throw new ServiceException(ServiceException.OBJECT_MAXID_FAILURE);
			code = CodeRule.getCode("L", num);
			
			map .put("code", code);
			result = JsonUtil.getJsonString(map);
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

	/**根据贷款接算出未放款金额
	 * @param params formId--->得到放款单实体
	 * @param map
	 * @param appAmount：贷款金额
	 * @throws ServiceException
	 */
	public void noLoanMoney(SHashMap<String, Object> params,
			HashMap<String, Object> map, BigDecimal appAmount,Double prate)
			throws ServiceException {
		/*--> 根据formId 找到放款单实体<--*/
		List<LoanInvoceEntity> loaninvoceEntity = loanInvoceService.getEntityList(params);
		DecimalFormat df=new DecimalFormat("#.#");
		if(loaninvoceEntity.isEmpty() || loaninvoceEntity.size()== 0){
			Double fee = BigDecimalHandler.mul(appAmount, BigDecimal.valueOf(prate));
			BigDecimal fe = BigDecimal.valueOf(BigDecimalHandler.div(fee, 100, 2));
			map.put("unAmount", df.format(appAmount));
			map.put("payAmount", df.format(appAmount));
			map.put("fee", df.format(fe));
		}else{
			BigDecimal PayAmount = new BigDecimal(0);
			BigDecimal PayA = new BigDecimal(0);
			for(LoanInvoceEntity loanvoceEntity : loaninvoceEntity){
				PayA =loanvoceEntity.getPayAmount();
				PayAmount = BigDecimal.valueOf(BigDecimalHandler.add(PayA, PayAmount));
			}
			BigDecimal unAmount = BigDecimal.valueOf(BigDecimalHandler.sub(appAmount, PayAmount));
			
			Double fee = BigDecimalHandler.mul(unAmount, BigDecimal.valueOf(prate));
			BigDecimal fe = BigDecimal.valueOf(BigDecimalHandler.div(fee, 100, 2));
			map.put("unAmount", df.format(unAmount));
			map.put("payAmount", df.format(unAmount));
			map.put("fee", df.format(fe));
		}
	}
	
	/**
	 * 获取指定的 扣款优先级 上一个对象
	 * @return
	 * @throws Exception
	 */
	public String prev()throws Exception {
		try {
			SHashMap<String,Object> param = getQParams("id");
			LoanInvoceEntity entity = loanInvoceService.navigationPrev(param);
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getFirstMsg(appendParams);
			}else{
				getloaninvice(entity);
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
	 * @param entity
	 * @throws ServiceException
	 */
	public void getloaninvice(LoanInvoceEntity entity) throws ServiceException {
		final BigDecimal payAmount = entity.getPayAmount();
		Long formId = entity.getFormId();
		Date payDate = entity.getPayDate();
		Date realDate = entity.getRealDate();
		final String paydate = DateUtil.dateFormatToStr(payDate);
		final String realdate = DateUtil.dateFormatToStr(realDate);
		Long  contractId = entity.getContractId();
		if(!StringHandler.isValidObj(contractId))throw new ServiceException(ServiceException.ID_IS_NULL);
		/*-->借款单实体<--*/
		LoanContractEntity loanContractEntity = loanContractService.getEntity(contractId);
		Integer yearLoan = loanContractEntity.getYearLoan();
		Integer monthLoan = loanContractEntity.getMonthLoan();
		Integer dayLoan = loanContractEntity.getDayLoan();
		Double mrate = loanContractEntity.getMrate();
		final Integer year = yearLoan;
		final Integer month = monthLoan;
		final Integer day = dayLoan;
		final Double mr = mrate;
		final BigDecimal appAmount = loanContractEntity.getAppAmount();//得到贷金额
		/*--> 根据formId 找到放款单实体<--*/
		final Double rate =loanContractEntity.getRate();
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("formId", formId);
		Double prate = loanContractEntity.getPrate();
//		final BigDecimal unAmount = entity.getUnAmount();
		double fee =  BigDecimalHandler.mul(payAmount,BigDecimal.valueOf(prate)); 
		final BigDecimal fe = BigDecimal.valueOf(BigDecimalHandler.div(fee, 100, 2));
		final DecimalFormat df=new DecimalFormat("#.#");
		final String loancode = loanContractEntity.getCode();
		Long cashier = entity.getCashier();
		UserEntity cashierer = userService.getEntity(cashier);
		final String cas = cashierer.getUserId()+"##"+cashierer.getEmpName();
		result = FastJsonUtil.convertJsonToStr(entity,new Callback(){
			public void execute(JSONObject jsonObj) {
				jsonObj.put("payAmount", payAmount);
				jsonObj.put("cashier", cas);
				jsonObj.put("loancode", loancode);
				jsonObj.put("payDate", paydate);
				jsonObj.put("realDate", realdate);
				jsonObj .put("appAmount", appAmount);
				jsonObj .put("rate", rate);
				jsonObj .put("fee", df.format(fe));
				jsonObj .put("yearLoan",year);
				jsonObj .put("monthLoan",month);
				jsonObj .put("dayLoan",day);
				jsonObj .put("mrate",mr);
			}
		});
	}
	
	/**
	 * 获取指定的 扣款优先级 下一个对象
	 * @return
	 * @throws Exception
	 */
	public String next()throws Exception {
		try {
			SHashMap<String,Object> params = getQParams("id");
			LoanInvoceEntity entity = loanInvoceService.navigationNext(params);
			/*------> 可通过  appendParams 加入附加参数<--------*/
			Map<String,Object> appendParams = new HashMap<String, Object>();
			if(null == entity){
				result = ResultMsg.getLastMsg(appendParams);
			}else{
				getloaninvice(entity);
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
	 * 删除  放款单 
	 * @return
	 * @throws Exception
	 */
	public String delete()throws Exception {
		return enabled(ResultMsg.DELETE_SUCCESS);
	}
	
	/**
	 * 启用  放款单 
	 * @return
	 * @throws Exception
	 */
	public String enabled()throws Exception {
		return enabled(ResultMsg.ENABLED_SUCCESS);
	}
	
	/**
	 * 禁用  放款单 
	 * @return
	 * @throws Exception
	 */
	public String disabled()throws Exception {
		return enabled(ResultMsg.DISABLED_SUCCESS);
	}
	
	/**
	 * 删除/起用/禁用  放款单 
	 * @return
	 * @throws Exception
	 */
	public String enabled(String sucessMsg)throws Exception {
		try {
			String ids = getVal("ids");
			Integer isenabled = getIVal("isenabled");
			loanInvoceService.enabledEntitys(ids, isenabled);
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
	
}
