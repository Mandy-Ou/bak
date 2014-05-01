package com.cmw.service.impl.finance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.constant.SysparamsConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.CurrentDaoInter;
import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.CasualRecordsEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.SysparamsEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.finance.paytype.PayTypeCalculateAbs;
import com.cmw.service.impl.finance.paytype.PayTypeCalculateFactory;
import com.cmw.service.inter.crm.CustomerInfoService;
import com.cmw.service.inter.crm.EcustomerService;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.CasualRecordsService;
import com.cmw.service.inter.finance.CurrentService;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.LoanInvoceService;
import com.cmw.service.inter.finance.OverdueDeductService;
import com.cmw.service.inter.finance.PayTypeService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.sys.SysparamsService;

/**
 *Title: CurrentServiceImpl.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-25下午12:49:48
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="列映射关系表业务实现类",createDate="2013-11-22T00:00:00",author="赵世龙")
@Service("currentService")
public class CurrentServiceImpl  extends AbsService<Object, Long> implements CurrentService {
	static Logger logger = Logger.getLogger(CurrentServiceImpl.class);
	@Autowired
	private CurrentDaoInter currentDao;
	
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="loanInvoceService")
	private LoanInvoceService loanInvoceService;
	
	@Resource(name="planService")
	private PlanService planService;
	
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	
	@Resource(name = "fundsWaterService")
	private FundsWaterService fundsWaterService;
	
	@Resource(name="overdueDeductService")
	private OverdueDeductService overdueDeductService;

	@Resource(name="casualRecordsService")
	private CasualRecordsService casualRecordsService;
	
	@Resource(name="payTypeService")
	private PayTypeService payTypeService;
	
	@Resource(name="amountRecordsService")
	private AmountRecordsService amountRecordsService;
	
	@Resource(name="applyService")
	private ApplyService applyService;
	
	@Resource(name="sysparamsService")
	private SysparamsService sysparamsService;
	
	@Autowired
	private CustomerInfoService customerInfoService;
	
	@Autowired
	private EcustomerService ecustomerService;
	
	
	@Override
	public GenericDaoInter<Object, Long> getDao() {
		return currentDao;
	}
	/**
	 * 随借随还 dt业务处理类
	 */
	@Override
	public DataTable getCurrentList(SHashMap<String, Object> map, int start,
			int limit) throws ServiceException {
		DataTable dt = null;
		try {
			dt = currentDao.getCurrentList(map,start,limit);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		return dt;
	}
	
	/**
	 * 随借随还计算利息、管理费、罚息、滞纳金方法
	 * @param map 参数值
	 */
	@Override
	public JSONObject calculate(SHashMap<String, Object> map) throws ServiceException {
		
		//contractId,rectDate,SysConstant.USER_KEY
		Long contractId = map.getvalAsLng("contractId");
		String rectDate = map.getvalAsStr("rectDate");
	
//		UserEntity user = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
//		LoanContractEntity loanContract = loanContractService.getEntity(contractId);
		//根据合同ID和收款日期找应还款日最近的那一期。
		SHashMap<String, Object> pars = new SHashMap<String, Object>();
		pars.put("contractId", contractId);
		pars.put("isenabled", SysConstant.OPTION_ENABLED);
		pars.put(SqlUtil.ORDERBY_KEY,"asc:id");
		List<PlanEntity> planList = planService.getEntityList(pars);
		JSONObject json = calculateImAmounts(planList,rectDate);
		return json;
	}
	
	private JSONObject calculateImAmounts(List<PlanEntity> planList, String rectDateStr) throws ServiceException {
		Date rectDate = DateUtil.dateFormat(rectDateStr);
		BigDecimal t_iamount = new BigDecimal("0");
		BigDecimal t_mamount = new BigDecimal("0");
		PlanEntity prevPlan = null;
		PlanEntity nextPlan = null;
		Object[] arr = calculateTamouts(planList, t_iamount, t_mamount, rectDate);
		prevPlan = (PlanEntity)arr[0];
		nextPlan = (PlanEntity)arr[1];
		JSONObject json = getJsonData(arr);
		if(null == prevPlan && null == nextPlan){
			return json;
		}
		t_iamount = (BigDecimal)arr[2];
		t_mamount = (BigDecimal)arr[3];
		
		int pointDays = 1;
		int days = 0;
		if(null != nextPlan && null != prevPlan){
			Date beginDate = prevPlan.getXpayDate();
			days = getDays(beginDate, rectDate);
			pointDays = 30;
			if(isAdvanceAndLast(planList, nextPlan)){
				nextPlan = prevPlan;//如果是最后一期且是预收就拿上一期的利息和管理费进行计算
			}else{//判断是否是最后一期（非预收的情况）
				Date endDate = nextPlan.getXpayDate();
				int beginDays = DateUtil.getDays(beginDate);
				Date newEndDate = DateUtil.addDaysToDate(endDate, 1);
				int endDays = DateUtil.getDays(newEndDate);
				int _endDays = endDays+1;
				//拿上一期的应还款日的号数与最后一期截止日期号数比较，如果号数相同，则表明是以实际放款日作为结算日。反之，以公司规定的日期作为结算日
				boolean flag = (beginDays == endDays || beginDays == _endDays);
				if(isLastPhases(planList, nextPlan) && !flag){
					int realDays = getDays(beginDate, endDate);
					pointDays = realDays;
				}
			}
		}else{
			if(null != nextPlan && null == prevPlan){
				Long contractId = nextPlan.getContractId();
				Date realDate = getPayDate(contractId, rectDate);
				days = getDays(realDate, rectDate);
				if(isLastPlanByOneMonth(planList, nextPlan, realDate)){
					pointDays = 30;
				}else{
					pointDays = getDays(realDate, nextPlan.getXpayDate());
				}
			}
		}
		double[] c_amountArr = calculateCamount(nextPlan, pointDays, days, rectDate);
		double c_iamount = c_amountArr[0];
		double c_mamount = c_amountArr[1];
		double z_iamount = c_amountArr[2];
		double z_mamount = c_amountArr[3];
		if(null == t_iamount) t_iamount = new BigDecimal("0");
		if(null == t_mamount) t_mamount = new BigDecimal("0");
		t_iamount = BigDecimalHandler.add2BigDecimal(t_iamount, new BigDecimal(z_iamount));
		t_mamount = BigDecimalHandler.add2BigDecimal(t_mamount, new BigDecimal(z_mamount));
		t_iamount = BigDecimalHandler.roundToBigDecimal(t_iamount, 2);
		t_mamount = BigDecimalHandler.roundToBigDecimal(t_mamount, 2);
		json.put("t_iamount", t_iamount);
		json.put("t_mamount", t_mamount);
		json.put("planId", nextPlan.getId());
		json.put("c_iamount", c_iamount);
		json.put("c_mamount", c_mamount);
		//json.put("z_iamount", z_iamount);
		//json.put("z_mamount", z_mamount);
		JSONArray lateDatas = getLateDatas(planList, rectDate);
		if(null != lateDatas && lateDatas.size() > 0) json.put("lateDatas", lateDatas);
		return json;
	}
	
	/**
	 * 验证是否是最后一期，且最后一期是否是按月算的利息
	 * @param planList	
	 * @param nextPlan
	 * @param payDate
	 * @return
	 */
	private boolean isLastPlanByOneMonth(List<PlanEntity> planList, PlanEntity nextPlan, Date payDate){
		boolean flag = false;
		PlanEntity lastPlan = planList.get(planList.size() - 1);
		if(nextPlan.getId().equals(lastPlan.getId())){
			Date xpayDate = nextPlan.getXpayDate();
			int payDay = DateUtil.getDays(payDate);
			int xpayDay = DateUtil.getDays(xpayDate);
			if(payDay == xpayDay || payDay == (xpayDay+1)) flag = true;
		}
		return flag;
	}
	
	/**
	 * 获取用来计算还款计划的放款日期
	 * @param contractId	合同ID
	 * @return	返回合同放款日期
	 * @throws ServiceException 
	 */
	@Override
	public Date getPayDate(Long contractId) throws ServiceException {
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId", contractId);
		map.put(SqlUtil.ORDERBY_KEY,"asc:realDate");
		List<LoanInvoceEntity> loanInvoceList = loanInvoceService.getEntityList(map);
		LoanInvoceEntity firstLoanInvoce = loanInvoceList.get(0);
		
		Integer startWay = firstLoanInvoce.getStartWay();
		Date payDate = null;
		if(null == startWay || startWay.intValue() == BussStateConstant.LOANINVOCE_STARTWAY_1){
			payDate = firstLoanInvoce.getPayDate();
		}else{
			payDate = firstLoanInvoce.getRealDate();
		}
		return payDate;
	}
	
	/**
	 * 获取用来计算还款计划的起始放款日期
	 * @param firstLoanInvoce
	 * @return
	 * @throws ServiceException 
	 */
	private Date getPayDate(Long contractId, Date rectDate) throws ServiceException {
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId", contractId);
		map.put(SqlUtil.ORDERBY_KEY,"asc:realDate");
		List<LoanInvoceEntity> loanInvoceList = loanInvoceService.getEntityList(map);
		LoanInvoceEntity firstLoanInvoce = loanInvoceList.get(0);
		
		Integer startWay = firstLoanInvoce.getStartWay();
		Date payDate = null;
		if(null == startWay || startWay.intValue() == BussStateConstant.LOANINVOCE_STARTWAY_1){
			payDate = firstLoanInvoce.getPayDate();
		}else{
			payDate = firstLoanInvoce.getRealDate();
		}
		
		int result = DateUtil.compareDate(payDate, rectDate);
		if(result > 0){
			validPayDate(payDate, rectDate, firstLoanInvoce);
		}
		
		return payDate;
	}
	
	private JSONObject getJsonData(Object[] arr){
		JSONObject jsonObj = new JSONObject();
		BigDecimal t_iamount = (BigDecimal)arr[2];
		BigDecimal t_mamount = (BigDecimal)arr[3];
		Long planId = (Long)arr[4];
		BigDecimal c_iamount = (BigDecimal)arr[5];
		BigDecimal c_mamount = (BigDecimal)arr[6];
		jsonObj.put("t_iamount", t_iamount);
		jsonObj.put("t_mamount", t_mamount);
		jsonObj.put("planId", planId);
		jsonObj.put("c_iamount", c_iamount);
		jsonObj.put("c_mamount", c_mamount);
		return jsonObj;
	}
	
	/**
	 * 是否预收息并且 nextPlan 是最后一期
	 * @param planList	
	 * @param nextPlan
	 * @return
	 */
	private boolean isAdvanceAndLast(List<PlanEntity> planList, PlanEntity nextPlan){
		boolean flag = false;
		PlanEntity lastPlan = planList.get(planList.size() - 1);
		Integer lastPhases = lastPlan.getPhases();
		Integer phases = nextPlan.getPhases();
		BigDecimal interest = nextPlan.getInterest();
		BigDecimal mgrAmount = nextPlan.getMgrAmount();
		BigDecimal principal = nextPlan.getPrincipal();
		if(lastPhases.equals(phases) && (null == interest || interest.doubleValue() == 0d)
				&& (null == mgrAmount || mgrAmount.doubleValue() == 0d)
				&& (null != principal && principal.doubleValue() > 0d)){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 判断 nextPlan 是否为最后一期
	 * @param planList	
	 * @param nextPlan
	 * @return
	 */
	private boolean isLastPhases(List<PlanEntity> planList, PlanEntity nextPlan){
		boolean flag = false;
		PlanEntity lastPlan = planList.get(planList.size() - 1);
		Integer lastPhases = lastPlan.getPhases();
		Integer phases = nextPlan.getPhases();
		if(lastPhases.equals(phases)){
			flag = true;
		}
		return flag;
	}
	
	private void validPayDate(Date realDate, Date rectDate, LoanInvoceEntity firstLoanInvoce) throws ServiceException{
		LoanContractEntity loanContract = loanContractService.getEntity(firstLoanInvoce.getContractId());
		Integer custType = loanContract.getCustType();
		Long customerId = loanContract.getCustomerId();
		String custName = null;
		if(custType.intValue() == 0){/*个人客户*/
			CustomerInfoEntity oneCustomer = customerInfoService.getEntity(customerId);
			custName = oneCustomer.getName();
		}else{/*企业客户*/
			EcustomerEntity entCustomer = ecustomerService.getEntity(customerId);
			custName = entCustomer.getName();
		}
		Integer startWay = firstLoanInvoce.getStartWay();
		String str = "合约放款日期";
		if(null == startWay || startWay.intValue() == BussStateConstant.LOANINVOCE_STARTWAY_1){
			 str = "合约放款日期";
		}else{
			 str = "实际放款日期";
		}
		throw new ServiceException("系统检查到客户["+getHighlight(custName)+"]的"+str+"为：\""+getHighlight(DateUtil.dateFormatToStr(realDate))+"\"，" +
				"本次收款日期为:\""+getHighlight(DateUtil.dateFormatToStr(rectDate))+"\"，本次收款日期小于"+str+"，无法完成收款!");
	}
	
	private String getHighlight(String str){
		return "<span style='color:red;font-weight:bold;'>"+str+"</span>";
	}
	
	private JSONArray getLateDatas(List<PlanEntity> planList, Date rectDate) throws ServiceException{
		if(!isLate(planList) ||(null == planList || planList.size() == 0)) return null;
		Long contractId = planList.get(0).getContractId();
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractorIds", contractId);
		map.put("lastDate", DateUtil.dateFormat(rectDate));
		JSONObject jsonObj =  overdueDeductService.calculateLateDatas(map);
		if(null == jsonObj || jsonObj.size() == 0) return null;
		JSONArray jsonArr = jsonObj.getJSONArray(contractId.toString());
		if(null == jsonArr || jsonArr.size() == 0) return null;
		JSONArray arr = new JSONArray();
		for(int i=0,count=jsonArr.size(); i<count; i++){
			JSONObject data = jsonArr.getJSONObject(i);
			Long planId = data.getLong("id");
			BigDecimal penAmount = data.getBigDecimal("penAmount");
			BigDecimal delAmount = data.getBigDecimal("delAmount");
			BigDecimal ypenAmount = data.getBigDecimal("ypenAmount");
			BigDecimal ydelAmount = data.getBigDecimal("ydelAmount");
			Integer latedays = data.getInteger("latedays");
			Integer pdays = data.getInteger("pdays");
			Integer ratedays = data.getInteger("ratedays");
			Integer mgrdays = data.getInteger("mgrdays");
			JSONObject obj = new JSONObject();
			obj.put("planId", planId);
			obj.put("penAmount", penAmount);
			obj.put("delAmount", delAmount);
			obj.put("ypenAmount", ypenAmount);
			obj.put("ydelAmount", ydelAmount);
			obj.put("latedays", latedays);
			obj.put("pdays", pdays);
			obj.put("ratedays", ratedays);
			obj.put("mgrdays", mgrdays);
			arr.add(obj);
		}
		return arr;
	}
	
	private boolean isLate(List<PlanEntity> planList){
		boolean flag = false;
		if(null == planList || planList.size() == 0) return false;
		for(PlanEntity entity : planList){
			Integer status = entity.getStatus();
			if(null != status && (status.intValue() == BussStateConstant.PLAN_STATUS_4 ||
					status.intValue() == BussStateConstant.PLAN_STATUS_5)){
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	private int getDays(Date beginDate, Date endDate) throws ServiceException{
		int days = DateUtil.calculateLimitDate(beginDate, endDate, DateUtil.DAY);
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("isenabled", SysConstant.OPTION_ENABLED);
		map.put("recode", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + SysparamsConstant.RANDOM_SE_TYPE);
		SysparamsEntity sysparams = sysparamsService.getEntity(map);
		if(null != sysparams && StringHandler.isValidStr(sysparams.getVal()) &&
				sysparams.getVal().equals(SysparamsConstant.RANDOM_SE_TYPE_VAL_1)){/*算头又算尾*/
			if(days>0) days++;
		}
		return days;
	}
	
	private double[] calculateCamount(PlanEntity nextPlan,int pointDays, int days, Date rectDate){
		double c_iamount = 0D;
		double c_mamount = 0D;
		double z_iamount = 0d;
		double z_mamount = 0d;
		double niamout = nextPlan.getInterest().doubleValue();
		double nyiamount = nextPlan.getYinterest().doubleValue();
		double nmamout = nextPlan.getMgrAmount().doubleValue();
		double nymamount = nextPlan.getYmgrAmount().doubleValue();
		Date xpayDate = nextPlan.getXpayDate();
		int result = DateUtil.compareDate(xpayDate, rectDate);
		if(result == 0){
			c_iamount = niamout;
			c_mamount = nmamout;
		}else{
			c_iamount = (niamout/pointDays)*days;
			c_mamount = (nmamout/pointDays)*days;
		}
		
		if(c_iamount < 0){
			c_iamount = 0;
		}else{
			c_iamount = StringHandler.Round(c_iamount);
		}
		z_iamount = StringHandler.Round(c_iamount - nyiamount);
		if(z_iamount < 0) z_iamount = 0d;
		
		//-->算管理费
		if(c_mamount < 0){
			c_mamount = 0;
		}else{
			c_mamount = StringHandler.Round(c_mamount);
		}
		z_mamount = StringHandler.Round(c_mamount - nymamount);
		if(z_mamount < 0) z_mamount = 0d;
		return new double[]{c_iamount,c_mamount,z_iamount, z_mamount};
	}
	
	/**
	 * 计算未收的总利息和管理费
	 * @param planList
	 * @param t_iamount
	 * @param t_mamount
	 * @param rectDate
	 * @return
	 */
	private Object[] calculateTamouts(List<PlanEntity> planList,BigDecimal t_iamount, BigDecimal t_mamount, Date rectDate){
		PlanEntity prevPlan = null;
		PlanEntity nextPlan = null;
		int count=planList.size(); 
		for(int i=0; i<count; i++){
			PlanEntity entity = planList.get(i);
			Integer status = entity.getStatus();
			Date xpayDate = entity.getXpayDate();
			BigDecimal interest = entity.getInterest();
			BigDecimal yinterest = entity.getYinterest();
			BigDecimal mgrAmount = entity.getMgrAmount();
			BigDecimal ymgrAmount = entity.getYmgrAmount();
			BigDecimal zinterest = new BigDecimal("0");
			BigDecimal zmgrAmount = new BigDecimal("0");
			int result = DateUtil.compareDate(xpayDate, rectDate);
			if(result >= 0){
				if(null != status && (status.intValue() == BussStateConstant.PLAN_STATUS_2 || 
						status.intValue() == BussStateConstant.PLAN_STATUS_3 || 
						status.intValue() == BussStateConstant.PLAN_STATUS_8 || 
						status.intValue() == BussStateConstant.PLAN_STATUS_9|| 
						status.intValue() == BussStateConstant.PLAN_STATUS_10)){
					return null;
				}
				int prevIndex = i-1;
				if(prevIndex>=0) prevPlan = planList.get(prevIndex);
				nextPlan = entity;
				break;
			}
			if(null != status && (status.intValue() == BussStateConstant.PLAN_STATUS_0 ||
					status.intValue() == BussStateConstant.PLAN_STATUS_1 || 
					status.intValue() == BussStateConstant.PLAN_STATUS_4 || 
					status.intValue() == BussStateConstant.PLAN_STATUS_5)){
				zinterest = BigDecimalHandler.sub2BigDecimal(interest, yinterest);
				zmgrAmount = BigDecimalHandler.sub2BigDecimal(mgrAmount, ymgrAmount);
				if(null != zinterest && zinterest.doubleValue() < 0) zinterest = new BigDecimal("0");
				if(null != zmgrAmount && zmgrAmount.doubleValue() < 0) zmgrAmount = new BigDecimal("0");
			}
			t_iamount = BigDecimalHandler.add2BigDecimal(t_iamount, zinterest);
			t_mamount = BigDecimalHandler.add2BigDecimal(t_mamount, zmgrAmount);
			logger.info("期数:"+entity.getPhases()+",应还款日期="+DateUtil.dateFormatToStr(xpayDate)+",rectDate="+DateUtil.dateFormatToStr(rectDate)
					+",应收利息:"+zinterest+",应收管理费="+zmgrAmount
					+",累计利息="+t_iamount+",累计管理费="+t_mamount);
		}
		
		Object[] dataArr = new Object[]{prevPlan, nextPlan, t_iamount, t_mamount,null,null,null};
		if(null == prevPlan && null  == nextPlan && count == 1){/*只有一期的情况*/
			setLastPlanImamounts(planList, dataArr);
		}
		return dataArr;
	}
	private void setLastPlanImamounts(List<PlanEntity> planList,
			Object[] dataArr) {
		PlanEntity lastPlan = planList.get(0);
		Long planId = lastPlan.getId();
		BigDecimal c_iamount = lastPlan.getInterest();
		BigDecimal c_mamount = lastPlan.getMgrAmount();
		dataArr[4] = planId;
		dataArr[5] = c_iamount;
		dataArr[6] = c_mamount;
	}
	
	
	
	 /**
	  * 正常扣收 单笔或批量收款
	  */
	@Override
	@Transactional
	public Object doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
		String recordIds = receivSingleAmounts(complexData);
		
		//---> step 2 : 保存实收金额日志<---//
		if(!StringHandler.isValidStr(recordIds)) return null;
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("ids", recordIds);
		Long sysId = complexData.getvalAsLng("sysId");
		params.put("sysId", sysId);
		String vtempCode = complexData.getvalAsStr("vtempCode");
		params.put("vtempCode", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + vtempCode);
		params.put(SysConstant.USER_KEY, user);
		params.put("bussTag", BussStateConstant.AMOUNTLOG_BUSSTAG_12);
		Map<AmountLogEntity,DataTable> logDataMap = amountLogService.saveByBussTag(params);
		
		//----> ste 3 : 保存资金流水 和更新自有资金<---//
		fundsWaterService.calculate(logDataMap,user);
		return logDataMap;
	}
	
	/**
	 * 随借随还扣收
	 * @param complexData
	 * @throws DaoException 
	 * @throws ServiceException 
	 * @return 返回收款记录ID
	 */
	private String receivSingleAmounts(SHashMap<String, Object> complexData) throws ServiceException{
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		Long contractId = complexData.getvalAsLng("contractId");
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId", contractId);
		map.put(SqlUtil.ORDERBY_KEY,"asc:phases,id");
		List<PlanEntity> planList = planService.getEntityList(map);
		/*======== step 1 : 备份还款计划表 =======*/
		backPlanDatas(planList);
		
		/*======== step 2 : 保存随借随还收款记录 =======*/
		CasualRecordsEntity casualRecordsObj = createCasualRecordsEntity(complexData);
		casualRecordsService.saveEntity(casualRecordsObj);
		
		//id,user,accountId,rectDate,phases,cat,rat,mat,tat
		Long casualId = casualRecordsObj.getId();
		/*======== step 2 : 更新还款计划表状态和实收项  =======*/
		updateAmounts(planList,complexData,casualId);
		
		/*======== step 3 : 如果有逾期，则更新表内表外  =======*/
		map.put("contractId", contractId);
		map.put(SqlUtil.ORDERBY_KEY,"asc:phases,id");
		planList = planService.getEntityList(map);
		overdueDeductService.saveTaboutsideDatas(contractId.toString(), planList, user);
		
		/*======== step 3 : 更新贷款申请单状态  =======*/
		ApplyEntity applyEntity = getApplyEntity(planList,user);
		applyService.updateEntity(applyEntity);
		
		/*======== step 4 : 如果应找回金额大于0，则存入预收款帐户  =======*/
		saveAdvanceAmounts(applyEntity,complexData);
		
		/*======== step 5 : 返回随借随还收款记录ID  =======*/
		return casualRecordsObj.getId().toString();
	}
	
	
	/**
	 * 预收保存未实现
	 * @param applyEntity
	 * @param complexData
	 */
	private void saveAdvanceAmounts(ApplyEntity applyEntity,SHashMap<String, Object> complexData){
		// to do ...
		Double oddAmount = complexData.getvalAsDob("oddAmount");
		if(oddAmount > 0){
			// 保存预收金额 ... 
		}
		log.info("预收保存未实现 ... ");
	} 
	
	private ApplyEntity getApplyEntity(List<PlanEntity> planList,UserEntity user) throws ServiceException {
		Long contractId = planList.get(0).getContractId();
		LoanContractEntity contractEntity = loanContractService.getEntity(contractId);
		ApplyEntity applyEntity = applyService.getEntity(contractEntity.getFormId());
		PlanEntity lastPlanEntity = planList.get(planList.size()-1);
		Integer phases = lastPlanEntity.getPhases();
		Integer status = lastPlanEntity.getStatus();
		Integer totalPhases = applyEntity.getTotalPhases();
		if(null == totalPhases){
			totalPhases = phases;
			applyEntity.setTotalPhases(totalPhases);
		}
		if(status.intValue() == BussStateConstant.PLAN_STATUS_2){	/*最后一期且结清*/
			applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_6);
		}else{
			applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_4);
		}
		Integer paydPhases = getPaydPhases(planList);
		applyEntity.setPaydPhases(paydPhases);
		BeanUtil.setModifyInfo(user, applyEntity);
		
		return applyEntity;
	}
	
	/**
	 * 获取已结清的期数
	 * @param planList
	 * @return
	 */
	private Integer getPaydPhases(List<PlanEntity> planList){
		Integer paydPhases = 0;
		for(PlanEntity entity : planList){
			Integer status = entity.getStatus();
			if(null != status && (status.intValue() == BussStateConstant.PLAN_STATUS_2 || 
					status.intValue() == BussStateConstant.PLAN_STATUS_3 ||
					status.intValue() == BussStateConstant.PLAN_STATUS_7 ||
					status.intValue() == BussStateConstant.PLAN_STATUS_8 ||
					status.intValue() == BussStateConstant.PLAN_STATUS_9 ||
					status.intValue() == BussStateConstant.PLAN_STATUS_10)){
				paydPhases++;
			}
		}
		return paydPhases;
	}
	
	/**
	 * 备份还款计划表数据
	 * @param contractID	
	 */
	private void backPlanDatas(List<PlanEntity> planList){
		logger.info("备份还款计划表数据...");
	}
	
	private CasualRecordsEntity createCasualRecordsEntity(SHashMap<String, Object> complexData){
	
		Long contractId = complexData.getvalAsLng("contractId");
		BigDecimal principal = complexData.getvalAsBig("principal");
		BigDecimal interest = complexData.getvalAsBig("interest");
		BigDecimal mgrAmount = complexData.getvalAsBig("mgrAmount");
		BigDecimal penAmount = complexData.getvalAsBig("penAmount");
		BigDecimal delAmount = complexData.getvalAsBig("delAmount");
		BigDecimal fat = complexData.getvalAsBig("fat");
		BigDecimal tat = complexData.getvalAsBig("tat");
		Long accountId = complexData.getvalAsLng("accountId");
		String rectDateStr = complexData.getvalAsStr("rectDate");
		Date rectDate = DateUtil.dateFormat(rectDateStr);
		UserEntity currUser = (UserEntity)complexData.get(SysConstant.USER_KEY);
		CasualRecordsEntity entity = new CasualRecordsEntity();
		BeanUtil.setCreateInfo(currUser, entity);
		entity.setContractId(contractId);
		entity.setBussTag(BussStateConstant.CASUALRECORDS_BUSSTAG_0);
		entity.setCat(principal);
		entity.setRat(interest);
		entity.setMat(mgrAmount);
		entity.setPat(penAmount);
		entity.setDat(delAmount);
		entity.setFat(fat);
		entity.setTat(tat);
		entity.setAccountId(accountId);
		entity.setRectDate(rectDate);
		return entity;
	}
	
	/**
	 *  更新还款计划和创建收款记录
	 * @param planList
	 * @param complexData
	 * @param casualId
	 * @throws ServiceException 
	 */
	private void updateAmounts(List<PlanEntity> planList, SHashMap<String, Object> complexData, Long casualId) throws ServiceException{
		if(null == planList || planList.size() == 0) return;
		BigDecimal t_principal = complexData.getvalAsBig("principal");
		BigDecimal t_interest = complexData.getvalAsBig("interest");
		BigDecimal t_mgrAmount = complexData.getvalAsBig("mgrAmount");
		BigDecimal t_penAmount = complexData.getvalAsBig("penAmount");
		BigDecimal t_delAmount = complexData.getvalAsBig("delAmount");
		
		Long currPlanId = complexData.getvalAsLng("planId");
		BigDecimal c_iamount = complexData.getvalAsBig("c_iamount");
		BigDecimal c_mamount = complexData.getvalAsBig("c_mamount");
		String rectDate = (String)complexData.get("rectDate");
		Date lastDate = DateUtil.dateFormat(rectDate);
		int newIndex = -1;
		String jsonStr = complexData.getvalAsStr("lateDatas");
		JSONArray lateDatasArr = FastJsonUtil.convertStrToJSONArr(jsonStr);
		UserEntity currUser = (UserEntity)complexData.get(SysConstant.USER_KEY);
		List<PlanEntity> newplanList = new ArrayList<PlanEntity>(); 
		List<AmountRecordsEntity> recordsList = new ArrayList<AmountRecordsEntity>();
		SHashMap<String, Object> mapData = null;
		for(int i=0,count=planList.size(); i<count; i++){
			PlanEntity planEntity = planList.get(i);
			Long planId = planEntity.getId();
			if(t_principal.doubleValue() <= 0d && t_interest.doubleValue() <= 0d 
					&& t_mgrAmount.doubleValue() <=0d && t_penAmount.doubleValue() <= 0d && t_delAmount.doubleValue() <= 0d) break;
			if(!isPayAmount(planEntity)) continue;
			mapData = getInitMapDatas(complexData);
			if(null != currPlanId && currPlanId.equals(planId)){
				newIndex = i;
				boolean isLastPhases = (i == count-1);
				setCurrentPrincipal(t_principal, c_iamount, c_mamount,planEntity, isLastPhases);
			}
			if(t_principal.doubleValue() > 0 ) t_principal = caclutePrincipal(planEntity,t_principal,mapData);
			if(t_interest.doubleValue() > 0 ) t_interest = cacluteInterest(planEntity,t_interest,mapData);
			if(t_mgrAmount.doubleValue() > 0 ) t_mgrAmount = cacluteMgramount(planEntity,t_mgrAmount,mapData);
			if(isLateState(planEntity)){
				setPlanLateInfo(planEntity, lateDatasArr);
				if(t_penAmount.doubleValue() > 0 ) t_penAmount = caclutePenAmount(planEntity,t_penAmount,mapData);
				if(t_delAmount.doubleValue() > 0 ) t_delAmount = cacluteDelAmount(planEntity,t_delAmount,mapData);
			}
			if(!isCreateRecords(mapData)) continue;
			AmountRecordsEntity amountRecordsObj = createAmountRecordsEntity(mapData, currUser, planId, casualId);
			recordsList.add(amountRecordsObj);
			planEntity.setLastDate(lastDate);
			setTotalAmountByPlan(planEntity);
			BeanUtil.setModifyInfo(currUser, planEntity);
			newplanList.add(planEntity);
			if(newIndex != -1) break;
		}
		Date lastPlanDate = planList.get(planList.size()-1).getXpayDate();
		int result = DateUtil.compareDate(lastPlanDate, lastDate);
		//--> 如果随借随还收款日期超过了贷款截止日期，就不需要重新生成还款计划表
		if(newIndex != -1 && result>=0) remakePlans(planList,newIndex,newplanList,complexData,casualId);
		if(null != newplanList && newplanList.size() > 0) planService.batchUpdateEntitys(newplanList);
		if(null != recordsList && recordsList.size() > 0) amountRecordsService.batchSaveOrUpdateEntitys(recordsList);
	}
	
	/**
	 * 重新设置金额合计
	 * @param planEntity
	 */
	private void setTotalAmountByPlan(PlanEntity planEntity){
		BigDecimal principal = planEntity.getPrincipal();
		BigDecimal interest = planEntity.getInterest();
		BigDecimal mgrAmount = planEntity.getMgrAmount();
		BigDecimal yprincipal = planEntity.getYprincipal();
		BigDecimal yinterest = planEntity.getYinterest();
		BigDecimal ymgrAmount = planEntity.getYmgrAmount();
		
		BigDecimal totalAmount = BigDecimalHandler.add2BigDecimal(2, principal,
				interest, mgrAmount);
		planEntity.setTotalAmount(totalAmount);
		BigDecimal ytotalAmount = BigDecimalHandler.add2BigDecimal(2, yprincipal,
				yinterest, ymgrAmount,planEntity.getYpenAmount(),planEntity.getYdelAmount());
		
		totalAmount = BigDecimalHandler.add2BigDecimal(2, totalAmount, planEntity.getPenAmount(),planEntity.getDelAmount());
		
		ytotalAmount = BigDecimalHandler.add2BigDecimal(2, ytotalAmount, planEntity.getTrinterAmount(),planEntity.getTrmgrAmount(),
				planEntity.getTrpenAmount(),planEntity.getTrdelAmount());
		
		boolean changeState = false;
		BigDecimal piAmount = BigDecimalHandler.add2BigDecimal(principal, interest);
		BigDecimal ypiAmount = BigDecimalHandler.add2BigDecimal(yprincipal, yinterest);
		double result = BigDecimalHandler.sub(ypiAmount, piAmount);
		if(result >= 0){
			planEntity.setPistatus(BussStateConstant.PLAN_PISTATUS_2);
			changeState = true;
		}
		result = BigDecimalHandler.sub(ymgrAmount, mgrAmount);
		if(result >= 0){
			planEntity.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_2);
			changeState = true;
		}
		
		result = BigDecimalHandler.sub(ytotalAmount, totalAmount);
		if(result >= 0){
			planEntity.setPistatus(BussStateConstant.PLAN_PISTATUS_2);
			planEntity.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_2);
			planEntity.setStatus(BussStateConstant.PLAN_STATUS_2);
			changeState = true;
		}
		if(!changeState && ytotalAmount.doubleValue() > 0){/*部分收款*/
			planEntity.setPistatus(BussStateConstant.PLAN_PISTATUS_1);
			planEntity.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_1);
			planEntity.setStatus(BussStateConstant.PLAN_STATUS_1);
		}
	}
	
	/**
	 * 重新设置随借随还那一期的应还本金和贷款余额 
	 * @param t_principal	本次金额扣掉的剩余金额
	 * @param c_iamount		随借随还算到收款日的利息
	 * @param c_mamount		随借随还算到收款日的管理费
	 * @param planEntity	随借随还那一期的还款计划
	 * @param isLastPhases	标识当前期数是否是最后一期
	 */
	private void setCurrentPrincipal(BigDecimal t_principal, BigDecimal c_iamount, BigDecimal c_mamount, PlanEntity planEntity,
			boolean isLastPhases) {
		BigDecimal reprincipal = planEntity.getReprincipal();
		BigDecimal principal = planEntity.getPrincipal();
		planEntity.setInterest(c_iamount);
		planEntity.setMgrAmount(c_mamount);
		if(isLastPhases && (null != reprincipal && reprincipal.doubleValue() == 0d)
			&& (null != principal && principal.doubleValue() > 0)){
			t_principal = principal;
		}else{
			reprincipal = BigDecimalHandler.add2BigDecimal(principal, reprincipal);
			reprincipal = BigDecimalHandler.sub2BigDecimal(reprincipal, t_principal);
			if(reprincipal.doubleValue() < 0) reprincipal = new BigDecimal("0");
			planEntity.setPrincipal(t_principal);
		}
		BigDecimal totalAmount = BigDecimalHandler.add2BigDecimal(2,t_principal, c_iamount,c_mamount);
		planEntity.setReprincipal(BigDecimalHandler.roundToBigDecimal(reprincipal, 2));
		planEntity.setTotalAmount(totalAmount);
	}
	
	private void remakePlans(List<PlanEntity> planList,Integer newIndex, 
			List<PlanEntity> newplanList,SHashMap<String, Object> complexData,Long casualId) throws ServiceException{
		BigDecimal zprincipal = complexData.getvalAsBig("zprincipal");
		BigDecimal principal = complexData.getvalAsBig("principal");
		BigDecimal ztotalAmount = complexData.getvalAsBig("ztotalAmount");
		BigDecimal t_tat = complexData.getvalAsBig("tat");
		double totalResult = BigDecimalHandler.sub(t_tat, ztotalAmount);
		double pricipalResult = BigDecimalHandler.sub(principal, zprincipal);
		if(totalResult >= 0 || pricipalResult >= 0){/*实收合计-应收合计 >=0 表示结清 或  实收本金-应收本金 >=0 表示结清*/
			makePayFinish(planList, newIndex, newplanList,complexData, casualId);
		}else{/*重新根据贷款余额生成还款计划表*/
			Integer lastIndex = newplanList.size()-1;
			PlanEntity currentPlan = newplanList.get(lastIndex);/*随借随还当期*/
			Long currentPlanId = currentPlan.getId();
			List<PlanEntity> changePlanList = new ArrayList<PlanEntity>();
			changePlanList.add(currentPlan);
			for(int i=newIndex+1,count=planList.size(); i<count; i++){
				PlanEntity planObj = planList.get(i);
				changePlanList.add(planObj);
			}
			BigDecimal reprincipal = BigDecimalHandler.sub2BigDecimal(zprincipal,principal,2);
			if(null == reprincipal || reprincipal.doubleValue() == 0) reprincipal = new BigDecimal("0");
			makePlans(complexData,changePlanList,reprincipal);
			newplanList.remove(lastIndex);
			setCurrPlanStatus(currentPlanId, changePlanList);
			newplanList.addAll(changePlanList);
		}
	}
	
	/**
	 * 重新设置随借随还那一期的还款状态
	 * @param currentPlanId
	 * @param changePlanList
	 */
	private void setCurrPlanStatus(Long currentPlanId, List<PlanEntity> changePlanList){
		if(null == changePlanList || changePlanList.size() == 0) return;
		for(PlanEntity planEntity : changePlanList){
			Long id = planEntity.getId();
			if(currentPlanId.equals(id)){
				setTotalAmountByPlan(planEntity);
				break;
			}
		}
		
	}
	
	/**
	 * 根据贷款余额，重新生成还款计划
	 * @param complexData
	 * @param newPlanList
	 * @param pricipalResult
	 * @throws ServiceException
	 */
	private void makePlans(SHashMap<String, Object> complexData, List<PlanEntity> newPlanList,BigDecimal reprincipal) throws ServiceException{
		Long contractId = newPlanList.get(0).getContractId();
		LoanContractEntity loanContractEntity = loanContractService.getEntity(contractId);
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		PayTypeCalculateFactory factory = PayTypeCalculateFactory.getInstance();
		factory.setPayTypeService(payTypeService);
		String bussCode = loanContractEntity.getPayType();
		if(!StringHandler.isValidStr(bussCode)) throw new ServiceException("还款方式不能为空!");
		PayTypeCalculateAbs  paytypeObj = factory.creator(bussCode);
		String rectDate = complexData.getvalAsStr("rectDate");
		Date realDate = DateUtil.addDaysToDate(rectDate, 1);
		Map<String, Object> params = new HashMap<String, Object>();
		BigDecimal c_iamount = complexData.getvalAsBig("c_iamount");
		BigDecimal c_mamount = complexData.getvalAsBig("c_mamount");
		params.put("c_iamount", c_iamount);
		params.put("c_mamount", c_mamount);
		params.put("realDate", realDate);
		params.put("payAmount", reprincipal);//贷款余额
		paytypeObj.rebuildMake(user, loanContractEntity, params,newPlanList);
	}
	
	/**
	 * 处理结清的还款还息计划表
	 * @param planList
	 * @param newIndex
	 * @param newplanList
	 * @param complexData
	 * @param casualId
	 * @param currUser
	 * @param mapData
	 * @return
	 */
	private void makePayFinish(List<PlanEntity> planList,
			Integer newIndex, List<PlanEntity> newplanList,
			SHashMap<String, Object> complexData, Long casualId) {
		if(null == planList || planList.size() == 0) return;
		PlanEntity newLastPlan = newplanList.get(newplanList.size()-1);
		PlanEntity lastPlan = planList.get(planList.size()-1);
		if(null != newLastPlan && newLastPlan.getId().equals(lastPlan.getId())) return;
		
		UserEntity currUser = (UserEntity)complexData.get(SysConstant.USER_KEY);
		int i = (newplanList.size() == 1) ? 0 : newIndex+1;
		int count = newplanList.size();
		for(; i<count; i++){
			PlanEntity planObj = planList.get(i);
			BigDecimal p_yprincipal = planObj.getYprincipal();
			BigDecimal p_yinterest = planObj.getYinterest();
			BigDecimal p_ymgrAmount = planObj.getYmgrAmount();
			BigDecimal p_ypenAmount = planObj.getYpenAmount();
			BigDecimal p_ydelAmount = planObj.getYdelAmount();
			
			BigDecimal trinterAmount = planObj.getTrinterAmount();
			BigDecimal trmgrAmount = planObj.getTrmgrAmount();
			BigDecimal trpenAmount = planObj.getTrpenAmount();
			BigDecimal trdelAmount = planObj.getTrdelAmount();
			
			p_yinterest = BigDecimalHandler.add2BigDecimal(p_yinterest, trinterAmount, 2);
			p_ymgrAmount = BigDecimalHandler.add2BigDecimal(p_ymgrAmount, trmgrAmount, 2);
			p_ypenAmount = BigDecimalHandler.add2BigDecimal(p_ypenAmount, trpenAmount, 2);
			p_ydelAmount = BigDecimalHandler.add2BigDecimal(p_ydelAmount, trdelAmount, 2);
			
			planObj.setPrincipal(p_yprincipal);
			planObj.setInterest(p_yinterest);
			planObj.setMgrAmount(p_ymgrAmount);
			planObj.setPenAmount(p_ypenAmount);
			planObj.setDelAmount(p_ydelAmount);
			
			setTotalAmountByPlan(planObj);
			BeanUtil.setModifyInfo(currUser, planObj);
			newplanList.add(planObj);
		}
	}
	
	private void setPlanLateInfo(PlanEntity planEntity,JSONArray lateDatasArr){
		if(null == lateDatasArr || lateDatasArr.size() == 0) return;
		Long id = planEntity.getId();
		for(int i=0,count=lateDatasArr.size(); i<count; i++){
			JSONObject jsonObj = lateDatasArr.getJSONObject(i);
			Long planId = jsonObj.getLong("planId");
			if(id.equals(planId)){
				BigDecimal penAmount = jsonObj.getBigDecimal("penAmount");
				BigDecimal delAmount = jsonObj.getBigDecimal("delAmount");
				Integer pdays = jsonObj.getInteger("pdays");
				Integer ratedays = jsonObj.getInteger("ratedays");
				Integer mgrdays = jsonObj.getInteger("mgrdays");
				planEntity.setPenAmount(penAmount);
				planEntity.setDelAmount(delAmount);
				planEntity.setPdays(pdays);
				planEntity.setRatedays(ratedays);
				planEntity.setMgrdays(mgrdays);
				break;
			}
		}
	}
	
	private SHashMap<String, Object> getInitMapDatas(SHashMap<String, Object> complexData) {
		SHashMap<String, Object> mapData = new SHashMap<String, Object>();
		mapData.put("cat", 0d);
		mapData.put("rat", 0d);
		mapData.put("mat", 0d);
		mapData.put("pat", 0d);
		mapData.put("dat", 0d);
		mapData.put("contractId",complexData.getvalAsLng("contractId"));
		mapData.put("accountId",complexData.getvalAsLng("accountId"));
		mapData.put("rectDate",complexData.getvalAsStr("rectDate"));
		return mapData;
	}
	
	private boolean isCreateRecords(SHashMap<String, Object> mapData){
		boolean flag = false;
		double cat = mapData.getvalAsDob("cat");
		double rat = mapData.getvalAsDob("rat");
		double mat = mapData.getvalAsDob("mat");
		double pat = mapData.getvalAsDob("pat");
		double dat = mapData.getvalAsDob("dat");
		double tat = cat + rat + mat + pat + dat;
		if(tat > 0) flag = true;
		return flag;
	}
	
	/**
	 * 批扣本金
	 * @param planEntity
	 * @param t_principal	本次实收本金
	 * @param mapData	存放本次从还息计划表实际扣掉的金额
	 * @return	返回本次扣取本金剩余金额
	 */
	private BigDecimal caclutePrincipal(PlanEntity planEntity, BigDecimal t_principal,SHashMap<String, Object> mapData){
		BigDecimal p_principal = planEntity.getPrincipal();
		BigDecimal p_yprincipal = planEntity.getYprincipal();
		BigDecimal p_zprincipal = BigDecimalHandler.sub2BigDecimal(p_principal, p_yprincipal);
		BigDecimal result_principal = BigDecimalHandler.sub2BigDecimal(t_principal, p_zprincipal);
		BigDecimal cat = new BigDecimal("0");
		if(result_principal.doubleValue() >= 0){
			planEntity.setYprincipal(p_principal);
			cat = p_zprincipal;
			t_principal = result_principal;
		}else{
			if(t_principal.doubleValue() > 0){
				cat = t_principal;
				p_yprincipal = BigDecimalHandler.add2BigDecimal(p_yprincipal,t_principal);
				p_yprincipal = BigDecimalHandler.roundToBigDecimal(p_yprincipal, 2);
				planEntity.setYprincipal(p_yprincipal);
			}
			t_principal = new BigDecimal("0");
		}
		mapData.put("cat", cat);
		return t_principal;
	}
	
	/**
	 * 批扣利息
	 * @param planEntity
	 * @param t_amount	本次实收利息
	 * @param mapData	存放本次从还息计划表实际扣掉的利息金额
	 * @return	返回本次扣取本金剩余利息金额
	 */
	private BigDecimal cacluteInterest(PlanEntity planEntity, BigDecimal t_amount,SHashMap<String, Object> mapData){
		BigDecimal p_amount = planEntity.getInterest();
		BigDecimal p_yamount = planEntity.getYinterest();
		BigDecimal p_zamount = BigDecimalHandler.sub2BigDecimal(p_amount, p_yamount);
		BigDecimal result_amount = BigDecimalHandler.sub2BigDecimal(t_amount, p_zamount);
		
		BigDecimal cat = new BigDecimal("0");
		if(result_amount.doubleValue() >= 0){
			planEntity.setYinterest(p_amount);//默认等于计划金额
			cat = t_amount;
			t_amount = result_amount;
		}else{
			if(t_amount.doubleValue() > 0){
				cat = t_amount;
				p_yamount = BigDecimalHandler.add2BigDecimal(p_yamount,t_amount);
				p_yamount = BigDecimalHandler.roundToBigDecimal(p_yamount, 2);
				planEntity.setYinterest(p_yamount);
			}
			t_amount = new BigDecimal("0");
		}
		mapData.put("rat", cat);
		return t_amount;
	}
	
	/**
	 * 批扣管理费
	 * @param planEntity
	 * @param t_amount	本次实收管理费
	 * @param mapData	存放本次从还息计划表实际扣掉的管理费金额
	 * @return	返回本次扣取本金剩余管理费金额
	 */
	private BigDecimal cacluteMgramount(PlanEntity planEntity, BigDecimal t_amount,SHashMap<String, Object> mapData){
		BigDecimal p_amount = planEntity.getMgrAmount();
		BigDecimal p_yamount = planEntity.getYmgrAmount();
		BigDecimal p_zamount = BigDecimalHandler.sub2BigDecimal(p_amount, p_yamount);
		BigDecimal result_amount = BigDecimalHandler.sub2BigDecimal(t_amount, p_zamount);
		BigDecimal cat = new BigDecimal("0");
		if(result_amount.doubleValue() >= 0){
			planEntity.setYmgrAmount(p_amount);//默认等于计划金额
			cat = t_amount;
			t_amount = result_amount;
		}else{
			if(t_amount.doubleValue() > 0){
				cat = t_amount;
				p_yamount = BigDecimalHandler.add2BigDecimal(p_yamount,t_amount);
				p_yamount = BigDecimalHandler.roundToBigDecimal(p_yamount, 2);
				planEntity.setYmgrAmount(p_yamount);
			}
			t_amount = new BigDecimal("0");
		}
		mapData.put("mat", cat);
		return t_amount;
	}
	
	/**
	 * 批扣罚息
	 * @param planEntity
	 * @param t_amount	本次实收罚息
	 * @param mapData	存放本次从还息计划表实际扣掉的罚息金额
	 * @return	返回本次扣取本金剩余管理费金额
	 */
	private BigDecimal caclutePenAmount(PlanEntity planEntity, BigDecimal t_amount,SHashMap<String, Object> mapData){
		BigDecimal p_amount = planEntity.getPenAmount();
		BigDecimal p_yamount = planEntity.getYpenAmount();
		BigDecimal p_zamount = BigDecimalHandler.sub2BigDecimal(p_amount, p_yamount);
		BigDecimal result_amount = BigDecimalHandler.sub2BigDecimal(t_amount, p_zamount);
		BigDecimal cat = new BigDecimal("0");
		if(result_amount.doubleValue() >= 0){
			planEntity.setYpenAmount(p_amount);//默认等于计划金额
			cat = t_amount;
			t_amount = result_amount;
		}else{
			if(t_amount.doubleValue() > 0){
				cat = t_amount;
				p_yamount = BigDecimalHandler.add2BigDecimal(p_yamount,t_amount);
				p_yamount = BigDecimalHandler.roundToBigDecimal(p_yamount, 2);
				planEntity.setYpenAmount(p_yamount);
			}
			t_amount = new BigDecimal("0");
		}
		mapData.put("pat", cat);
		return t_amount;
	}
	
	/**
	 * 批扣滞纳金
	 * @param planEntity
	 * @param t_amount	本次实收滞纳金
	 * @param mapData	存放本次从还息计划表实际扣掉的滞纳金金额
	 * @return	返回本次扣取剩余滞纳金
	 */
	private BigDecimal cacluteDelAmount(PlanEntity planEntity, BigDecimal t_amount,SHashMap<String, Object> mapData){
		BigDecimal p_amount = planEntity.getDelAmount();
		BigDecimal p_yamount = planEntity.getYdelAmount();
		BigDecimal p_zamount = BigDecimalHandler.sub2BigDecimal(p_amount, p_yamount);
		BigDecimal result_amount = BigDecimalHandler.sub2BigDecimal(t_amount, p_zamount);
		BigDecimal cat = new BigDecimal("0");
		if(result_amount.doubleValue() >= 0){
			planEntity.setYdelAmount(p_amount);//默认等于计划金额
			cat = t_amount;
			t_amount = result_amount;
		}else{
			if(t_amount.doubleValue() > 0){
				cat = t_amount;
				p_yamount = BigDecimalHandler.add2BigDecimal(p_yamount,t_amount);
				p_yamount = BigDecimalHandler.roundToBigDecimal(p_yamount, 2);
				planEntity.setYdelAmount(p_yamount);
			}
			t_amount = new BigDecimal("0");
		}
		mapData.put("dat", cat);
		return t_amount;
	}
	
	/**
	 * 判断状态是否可以收款
	 * @param planEntity
	 * @return
	 */
	private boolean isPayAmount(PlanEntity planEntity){
		boolean flag = false;
		Integer status = planEntity.getStatus();
		flag = (null != status && (status.intValue() == BussStateConstant.PLAN_STATUS_0 ||
				status.intValue() == BussStateConstant.PLAN_STATUS_1 ||
				status.intValue() == BussStateConstant.PLAN_STATUS_4 ||
				status.intValue() == BussStateConstant.PLAN_STATUS_5));
		return flag;
	}
	
	/**
	 * 判断状态是否逾期状态
	 * @param planEntity
	 * @return
	 */
	private boolean isLateState(PlanEntity planEntity){
		boolean flag = false;
		Integer status = planEntity.getStatus();
		flag = (null != status && (status.intValue() == BussStateConstant.PLAN_STATUS_4 ||
				status.intValue() == BussStateConstant.PLAN_STATUS_5));
		return flag;
	}
	
	private AmountRecordsEntity createAmountRecordsEntity(SHashMap<String, Object> complexData, UserEntity user, Long id,Long casualId) {
		Long accountId = complexData.getvalAsLng("accountId");
		Long contractId = complexData.getvalAsLng("contractId");
		String rectDate = (String)complexData.get("rectDate");
		Double cat = complexData.getvalAsDob("cat");
		Double rat = complexData.getvalAsDob("rat");
		Double mat = complexData.getvalAsDob("mat");
		Double pat = complexData.getvalAsDob("pat");
		Double dat = complexData.getvalAsDob("dat");
		Double tat = cat + rat + mat + pat + dat;
		AmountRecordsEntity amEntity = new AmountRecordsEntity();
		amEntity.setContractId(contractId);
		amEntity.setInvoceId(id);
		amEntity.setCasualId(casualId);
		amEntity.setBussTag(BussStateConstant.AMOUNTRECORDS_BUSSTAG_8);
		amEntity.setCat(StringHandler.Round2BigDecimal(cat));
		amEntity.setRat(StringHandler.Round2BigDecimal(rat));
		amEntity.setMat(StringHandler.Round2BigDecimal(mat));
		amEntity.setPat(StringHandler.Round2BigDecimal(pat));
		amEntity.setDat(StringHandler.Round2BigDecimal(dat));
		amEntity.setTat(StringHandler.Round2BigDecimal(tat));
		amEntity.setRectDate(DateUtil.dateFormat(rectDate));
		amEntity.setAccountId(accountId);
		BeanUtil.setCreateInfo(user, amEntity);
		return amEntity;
	}
	@Override
	public Date getLastDate(Long contractId) throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("contractId", contractId);
		params.put(SqlUtil.ORDERBY_KEY,"desc:rectDate");
		AmountRecordsEntity amountRecordsObj = amountRecordsService.getEntity(params);
		Date lastDate = null;
		if(null != amountRecordsObj) lastDate = amountRecordsObj.getRectDate();
		params.put(SqlUtil.ORDERBY_KEY,"desc:rectDate");
		Date rectDate = null;
		CasualRecordsEntity casualRecordsObj = casualRecordsService.getEntity(params);
		if(null != casualRecordsObj){
			rectDate = casualRecordsObj.getRectDate();
		}
		if(null != lastDate && null != rectDate){
			int result = DateUtil.compareDate(lastDate, rectDate);
			if(result < 0) lastDate = rectDate;
		}else{
			if(null != rectDate  && null == lastDate) lastDate = rectDate;
		}
		return lastDate;
	}
}
