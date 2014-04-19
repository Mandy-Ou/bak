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
	
	@Resource(name="planHandler")
	private PlanHandler planHandler;
	
	@Override
	public GenericDaoInter<Object, Long> getDao() {
		return currentDao;
	}
	
	/**
	 * 随借随还数据豁免，结清处理
	 * 如果 bussType = 1 --> 豁免
	 *     bussType = 2 --> 结清
	 * @param params 豁免参数
	 */
	@Override
	@Transactional
	public void settle(SHashMap<String, Object> params) throws Exception {
		try {
			Integer bussType = params.getvalAsInt("bussType");
			if(null == bussType) throw new ServiceException("参数:\"bussType\" 不能为空!");
			Long contractId = params.getvalAsLng("contractId");
			
			if(!StringHandler.isValidObj(contractId)) throw new ServiceException(ServiceException.ID_IS_NULL);
			List<PlanEntity> planList = getUnplanList(contractId);
			if(null == planList || planList.size() == 0) return;
			switch (bussType.intValue()) {
			case 1:{/*豁免*/
				exemptBatch(planList, params);
				break;
			}case 2:{/*豁免并结清*/
				exemptFinish(planList, params);
				break;
			}default:
				throw new ServiceException("bussType="+bussType+",无法预知的豁免业务类型!");
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	
	/**
	 * 获取未收完的还款计划列表
	 * @param contractId
	 * @return
	 * @throws ServiceException
	 */
	private List<PlanEntity> getUnplanList(Long contractId)
			throws ServiceException {
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId",SqlUtil.LOGIC_EQ+SqlUtil.LOGIC+contractId);
		map.put("isenabled",SysConstant.OPTION_ENABLED);
		String statusStr = BussStateConstant.PLAN_STATUS_0+","+BussStateConstant.PLAN_STATUS_1+","+BussStateConstant.PLAN_STATUS_2+","+BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5;
		map.put("status",SqlUtil.LOGIC_IN + SqlUtil.LOGIC + statusStr);
		map.put(SqlUtil.ORDERBY_KEY,"asc:id,phases");
		List<PlanEntity> planList= planService.getEntityList(map);
		return planList;
	}

	/**
	 * 部分息费豁免
	 * @param planList
	 * @param params
	 * @throws ServiceException
	 */
	private void exemptBatch(List<PlanEntity> planList, SHashMap<String, Object> params) throws ServiceException {
		Long contractId = params.getvalAsLng("contractId");
		UserEntity user = (UserEntity)params.get(SysConstant.USER_KEY);
		String rectDateStr = params.getvalAsStr("rectDate");
		Date rectDate = DateUtil.dateFormat(rectDateStr);
		double rat = params.getvalAsDob("rat");
		double mat = params.getvalAsDob("mat");
		double pat = params.getvalAsDob("pat");
		double dat = params.getvalAsDob("dat");
		List<AmountRecordsEntity> arList = new ArrayList<AmountRecordsEntity>();
		for(PlanEntity x : planList){
			if(rat <= 0d && mat <= 0d && pat <= 0d && dat <= 0d) break;
			BigDecimal interest = x.getInterest();
			BigDecimal mgrAmount = x.getMgrAmount();
			BigDecimal penAmount  = x.getMgrAmount();
			BigDecimal delAmount = x.getDelAmount();
			
			BigDecimal yinterest = x.getYinterest();
			BigDecimal ymgrAmount = x.getYmgrAmount();
			BigDecimal ypenAmount  = x.getYmgrAmount();
			BigDecimal ydelAmount = x.getYdelAmount();
			
			//---> 返还利息、管理费、罚息、滞纳金
			BigDecimal riamount = x.getRiamount();
			BigDecimal rmamount = x.getRmamount();
			BigDecimal rpamount = x.getRpamount();
			BigDecimal rdamount = x.getRdamount();
			if(null == riamount) riamount = new BigDecimal("0");
			if(null == rmamount) rmamount = new BigDecimal("0");
			if(null == rpamount) rpamount = new BigDecimal("0");
			if(null == rdamount) rdamount = new BigDecimal("0");
			
			//---> 豁免利息、管理费、罚息、滞纳金
			BigDecimal trinterAmount = x.getTrinterAmount();
			BigDecimal trmgrAmount = x.getTrmgrAmount();
			BigDecimal trpenAmount = x.getTrpenAmount();
			BigDecimal trdelAmount = x.getTrdelAmount();
			
			double[] amoutArr = null;
			double d_tramount = 0;
			SHashMap<String, Object> data = new SHashMap<String, Object>();
			data.put("contractId", x.getContractId());
			data.put("invoceId", x.getId());
			data.put("bussTag", BussStateConstant.AMOUNTRECORDS_BUSSTAG_10);
			data.put("rectDate",rectDate);
			//--> 豁免利息计算  <--//
			if(rat > 0){
				BigDecimal ziamount = BigDecimalHandler.sub2BigDecimal(2, interest, yinterest, trinterAmount);
				ziamount = BigDecimalHandler.add2BigDecimal(2, ziamount, riamount);
				amoutArr = planHandler.getAmountResult(rat, ziamount.doubleValue());
				rat = amoutArr[0];
				d_tramount = amoutArr[1];
				if(d_tramount > 0){
					BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
					data.put("rat", b_tramount);
					trinterAmount = BigDecimalHandler.add2BigDecimal(trinterAmount, b_tramount);
					x.setTrinterAmount(trinterAmount);
				}
			}
			
			//--> 豁免管理费计算  <--//
			if(mat > 0){
				BigDecimal zmamount = BigDecimalHandler.sub2BigDecimal(2, mgrAmount, ymgrAmount, trmgrAmount);
				zmamount = BigDecimalHandler.add2BigDecimal(2, zmamount, rmamount);
				amoutArr = planHandler.getAmountResult(mat, zmamount.doubleValue());
				mat = amoutArr[0];
				d_tramount = amoutArr[1];
				if(d_tramount > 0){
					BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
					data.put("mat", b_tramount);
					trmgrAmount = BigDecimalHandler.add2BigDecimal(trmgrAmount, b_tramount);
					x.setTrmgrAmount(trmgrAmount);
				}
			}
		
			//--> 豁免罚息计算  <--//
			if(pat > 0){
				BigDecimal zpamount = BigDecimalHandler.sub2BigDecimal(2, penAmount, ypenAmount, trpenAmount);
				zpamount = BigDecimalHandler.add2BigDecimal(2, zpamount, rpamount);
				amoutArr = planHandler.getAmountResult(pat, zpamount.doubleValue());
				pat = amoutArr[0];
				d_tramount = amoutArr[1];
				if(d_tramount > 0){
					BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
					data.put("pat", b_tramount);
					trpenAmount = BigDecimalHandler.add2BigDecimal(trpenAmount, b_tramount);
					x.setTrpenAmount(trpenAmount);
				}
			}
			
			//--> 豁免滞纳金计算  <--//
			if(dat > 0){
				BigDecimal zdamount = BigDecimalHandler.sub2BigDecimal(2, delAmount, ydelAmount, trdelAmount);
				zdamount = BigDecimalHandler.add2BigDecimal(2, zdamount, rdamount);
				amoutArr = planHandler.getAmountResult(dat, zdamount.doubleValue());
				pat = amoutArr[0];
				d_tramount = amoutArr[1];
				if(d_tramount > 0){
					BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
					data.put("dat", b_tramount);
					trdelAmount = BigDecimalHandler.add2BigDecimal(trdelAmount, b_tramount);
					x.setTrdelAmount(trdelAmount);
				}
			}
			x.setTrDate(rectDate);
			x.setLastDate(rectDate);
			BeanUtil.setModifyInfo(user, x);
			planHandler.setTotalAmountByPlan(x);
			AmountRecordsEntity obj = planHandler.createAmountRecords(data,user);
			if(null != obj) arList.add(obj);
		}
		planHandler.saveAmountRecords(arList, BussStateConstant.CASUALRECORDS_BUSSTAG_1, user);
		planService.batchSaveOrUpdateEntitys(planList);
		updateInnerMrate(contractId, user,false,null);
	}

	
	/**
	 * 结清豁免
	 * @param planList
	 * @param params
	 * @throws ServiceException
	 */
	private void exemptFinish(List<PlanEntity> planList, SHashMap<String, Object> params) throws ServiceException {
		Long contractId = params.getvalAsLng("contractId");
		String realDate = params.getvalAsStr("realDate");
		UserEntity user = (UserEntity)params.get(SysConstant.USER_KEY);
		List<AmountRecordsEntity> arList = new ArrayList<AmountRecordsEntity>();
		Date lastDate = new Date();
		if(StringHandler.isValidStr(realDate)){
			lastDate = DateUtil.dateFormat(realDate);
		}
		
		for(PlanEntity x : planList){
			AmountRecordsEntity amountRecordsObj = setExemptDatas(lastDate, x, user);
			if(null != amountRecordsObj) {
				arList.add(amountRecordsObj);
			}
		}
		planService.batchSaveOrUpdateEntitys(planList);
		planHandler.saveAmountRecords(arList, BussStateConstant.CASUALRECORDS_BUSSTAG_1, user);
		updateInnerMrate(contractId, user,true,realDate);
		
		
	}
	
	
	/**
	 * 当结清时，更新贷款申请单、表内表外状态，借款合同、内部管理费利率
	 * @param contractId
	 * @param user	当前用户对象
	 * @param falg 
	 * @param realDate 
	 * @throws ServiceException
	 */
	private void updateInnerMrate(Long contractId, UserEntity user, boolean falg, String realDate) throws ServiceException {
		
		/*======== step 1 : 如果有逾期，则更新表内表外  =======*/
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId", contractId);
		map.put(SqlUtil.ORDERBY_KEY,"asc:phases,id");
		List<PlanEntity> planList = planService.getEntityList(map);
		overdueDeductService.saveTaboutsideDatas(contractId.toString(), planList, user);
		
		/*======== step 2 : 更新贷款申请单状态  =======*/
		ApplyEntity applyEntity = null;
		if(falg){
			 applyEntity = getApplyEntity(planList,user,true);
			 if(StringHandler.isValidStr(realDate)){
				 applyEntity.setCurrentDate(DateUtil.dateFormat(realDate));
			 }
			 
		}else{
			 applyEntity = planHandler.getApplyEntity(planList,user);
		}
		
		applyService.updateEntity(applyEntity);
	}
	
	/**
	 * 设置豁免数据
	 * @param lastDate
	 * @param x 豁免的还款计划对象
	 * @param user 当前用户
	 */
	private AmountRecordsEntity setExemptDatas(Date lastDate, PlanEntity x, UserEntity user) {
		BigDecimal interest = x.getInterest();
		BigDecimal mgrAmount = x.getMgrAmount();
		BigDecimal penAmount  = x.getMgrAmount();
		BigDecimal delAmount = x.getDelAmount();
		
		BigDecimal yinterest = x.getYinterest();
		BigDecimal ymgrAmount = x.getYmgrAmount();
		BigDecimal ypenAmount  = x.getYmgrAmount();
		BigDecimal ydelAmount = x.getYdelAmount();
		
		//---> 返还利息、管理费、罚息、滞纳金
		BigDecimal riamount = x.getRiamount();
		BigDecimal rmamount = x.getRmamount();
		BigDecimal rpamount = x.getRpamount();
		BigDecimal rdamount = x.getRdamount();
		if(null == riamount) riamount = new BigDecimal("0");
		if(null == rmamount) rmamount = new BigDecimal("0");
		if(null == rpamount) rpamount = new BigDecimal("0");
		if(null == rdamount) rdamount = new BigDecimal("0");
		
		//---> 豁免利息、管理费、罚息、滞纳金
		BigDecimal trinterAmount = x.getTrinterAmount();
		BigDecimal trmgrAmount = x.getTrmgrAmount();
		BigDecimal trpenAmount = x.getTrpenAmount();
		BigDecimal trdelAmount = x.getTrdelAmount();
		
		BigDecimal totalAmount = x.getTotalAmount();
		BigDecimal ytotalAmount = x.getYtotalAmount();
		ytotalAmount = BigDecimalHandler.add2BigDecimal(2, ytotalAmount,trinterAmount,trmgrAmount,trpenAmount,trdelAmount);
		
		x.setPistatus(BussStateConstant.PLAN_PISTATUS_2);
		x.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_2);
		x.setStatus(BussStateConstant.PLAN_STATUS_2);
		BeanUtil.setModifyInfo(user, x);
		
		double r_tat = BigDecimalHandler.sub(ytotalAmount, totalAmount);
		AmountRecordsEntity obj = null;
		if(r_tat >= 0){
			
			return null;
		}else{
			BigDecimal ziamount = BigDecimalHandler.sub2BigDecimal(2 ,interest, yinterest,trinterAmount);
			ziamount = BigDecimalHandler.add2BigDecimal(2, ziamount, riamount);
			trinterAmount = BigDecimalHandler.add2BigDecimal(2, trinterAmount, ziamount);
			
			BigDecimal zmamount = BigDecimalHandler.sub2BigDecimal(2, mgrAmount, ymgrAmount,trmgrAmount);
			zmamount = BigDecimalHandler.add2BigDecimal(2, zmamount, rmamount);
			trmgrAmount = BigDecimalHandler.add2BigDecimal(trmgrAmount, zmamount);
			
			BigDecimal zpamount = BigDecimalHandler.sub2BigDecimal(2, penAmount, ypenAmount,trpenAmount);
			zpamount = BigDecimalHandler.add2BigDecimal(2, zpamount, rpamount);
			trpenAmount = BigDecimalHandler.add2BigDecimal(trpenAmount, zpamount);
			
			BigDecimal zdamount = BigDecimalHandler.sub2BigDecimal(2, delAmount, ydelAmount, trdelAmount);
			zdamount = BigDecimalHandler.add2BigDecimal(2, zdamount, rdamount);
			trdelAmount = BigDecimalHandler.add2BigDecimal(trdelAmount, zdamount);
			
			x.setTrinterAmount(trinterAmount);
			x.setTrmgrAmount(trmgrAmount);
			x.setTrpenAmount(trpenAmount);
			x.setTrdelAmount(trdelAmount);
			x.setTrDate(lastDate);
			x.setLastDate(lastDate);
			
			SHashMap<String, Object> data = new SHashMap<String, Object>();
			data.put("contractId", x.getContractId());
			data.put("invoceId", x.getId());
			data.put("rat", ziamount);
			data.put("mat", zmamount);
			data.put("pat", zpamount);
			data.put("dat", zdamount);
			data.put("rectDate", lastDate);
			data.put("bussTag", BussStateConstant.AMOUNTRECORDS_BUSSTAG_10);
			obj = planHandler.createAmountRecords(data,user);
		}
		return obj;
	}
	
	
	

	/**
	 * 随借随还 dt业务处理类
	 */
	@Override
	public DataTable getCurrentList(SHashMap<String, Object> map, int start,int limit) throws ServiceException {
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
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("contractId", contractId);
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		params.put(SqlUtil.ORDERBY_KEY,"asc:id");
		List<PlanEntity> planList = planService.getEntityList(params);
		if(null == planList || planList.size() == 0) throw new ServiceException("当前合同没有还款还息计划表，请检查是否已经放完款!");
		JSONObject json = null;
		SHashMap<String, Object> pars = new SHashMap<String, Object>();
		pars.put("recode", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + SysparamsConstant.RANDOM_ALGORITHM_KEY);
		SysparamsEntity sysparamsObj = sysparamsService.getEntity(pars);
		if(null == sysparamsObj ||	/*默认按天算息*/
		(null != sysparamsObj.getVal() && sysparamsObj.getVal().equals(SysparamsConstant.RANDOM_ALGORITHM_VAL_1))){
			json = calculateImAmountsByAllDays(planList,rectDate);
		}else{/*如果满一个月就按月算息，截止随借随还收款日期按天算息*/
			json = calculateImAmounts(planList,rectDate);
		}
		return json;
	}
	
	/**
	 * 全部按天算，银行算法
	 * @param planList
	 * @param rectDateStr
	 * @return
	 * @throws ServiceException
	 */
	private JSONObject calculateImAmountsByAllDays(List<PlanEntity> planList, String rectDateStr) throws ServiceException {
		JSONObject json = null;
		Long contractId = planList.get(0).getContractId();
		LoanContractEntity loanContract = loanContractService.getEntity(contractId);
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("contractId", contractId);
		params.put("cat", SqlUtil.LOGIC_GT + SqlUtil.LOGIC + "0");
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		params.put(SqlUtil.ORDERBY_KEY,"asc:rectDate");
		List<AmountRecordsEntity> amountRecordsList = amountRecordsService.getEntityList(params);
		Date rectDate = DateUtil.dateFormat(rectDateStr);
		if(null == amountRecordsList || amountRecordsList.size() == 0){/*没有收取任何本金的情况下*/
			json = caculateAmountByDays(planList, loanContract, rectDate);
		}else{/*有收本金的情况*/
			json = caculateAmountByDays(planList, loanContract, rectDate, amountRecordsList);
		}
		JSONArray lateDatas = getLateDatas(planList, rectDate);
		if(null != lateDatas && lateDatas.size() > 0) json.put("lateDatas", lateDatas);
		return json;
	}
	
	/**
	 * 获取按天算的利息总金额
	 * @param loanContract	
	 * @param rectDate
	 * @return
	 * @throws ServiceException 
	 */
	private double getIAmountsByDays(LoanContractEntity loanContract,Date rectDate) throws ServiceException{
		Long contractId = loanContract.getId();
		Date payDate = getPayDate(contractId);
		int days = getDays(payDate, rectDate);
		BigDecimal appAmount = loanContract.getAppAmount();
		double rate = getRate(RATETYPE_DAY, false, loanContract);
		double days_t_iamount = appAmount.doubleValue() * rate * days;
		return days_t_iamount;
	}
	
	/**
	 * 获取按天算的管理费总金额
	 * @param loanContract	
	 * @param rectDate
	 * @return
	 * @throws ServiceException 
	 */
	private double getMAmountsByDays(LoanContractEntity loanContract,Date rectDate) throws ServiceException{
		Integer mgrtype = loanContract.getMgrtype();
		if(null == mgrtype || mgrtype.intValue() == BussStateConstant.MGRTYPE_0) return 0d;/*不收管理费*/
		Long contractId = loanContract.getId();
		Date payDate = getPayDate(contractId);
		int days = getDays(payDate, rectDate);
		BigDecimal appAmount = loanContract.getAppAmount();
		double rate = getRate(RATETYPE_DAY, true, loanContract);
		double days_t_iamount = appAmount.doubleValue() * rate * days;
		return days_t_iamount;
	}
	
	private double[] getIMamountsByDays(LoanContractEntity loanContract, Date rectDate, List<AmountRecordsEntity> amountRecordsList) throws ServiceException{
		BigDecimal appAmount = loanContract.getAppAmount();
		double d_appAmount = appAmount.doubleValue();
		double t_iamount = 0d;
		double t_mamount = 0d;
		Long contractId = loanContract.getId();
		Date payDate = getPayDate(contractId);
		int days = 0;
		double rate = getRate(RATETYPE_DAY, false, loanContract);
		double mrate = getRate(RATETYPE_DAY, true, loanContract);
		for(int i=0,count=amountRecordsList.size(); i<count; i++){
			AmountRecordsEntity amountRecordsObj = amountRecordsList.get(i);
			Date endDate = amountRecordsObj.getRectDate();
			BigDecimal cat = amountRecordsObj.getCat();
			days = getDays(payDate, endDate);
			double days_iamount = d_appAmount * rate * days;
			double days_mamount = d_appAmount * mrate * days;
			t_iamount += days_iamount;
			t_mamount += days_mamount;
			payDate = DateUtil.addDaysToDate(endDate, 1);
			if(null != cat && cat.doubleValue() > 0) d_appAmount -= cat.doubleValue();
		}
		int result = DateUtil.compareDate(payDate, rectDate);
		if(result < 0){
			days = getDays(payDate, rectDate);
			double days_iamount = d_appAmount * rate * days;
			double days_mamount = d_appAmount * mrate * days;
			t_iamount += days_iamount;
			t_mamount += days_mamount;
		}
		return new double[]{t_iamount, t_mamount};
	}
	
	/**
	 * 以银行算法为主：按天算
	 * 以放款日做为起始日期算随借随还利息和管理费
	 * @param planList
	 * @param loanContract
	 * @param rectDate
	 * @param amountRecordsList 收款记录列表
	 * @throws ServiceException
	 */
	private JSONObject caculateAmountByDays(List<PlanEntity> planList,LoanContractEntity loanContract, Date rectDate, List<AmountRecordsEntity> amountRecordsList)
			throws ServiceException {
		JSONObject json = new JSONObject();
		double[] imArr = getIMamountsByDays(loanContract, rectDate, amountRecordsList);
		double days_t_iamount = imArr[0];
		JSONObject data = caculateIamounts(planList, loanContract, rectDate, days_t_iamount);
		if(null != data && data.size() > 0) json.putAll(data);
		double days_t_mamount = imArr[1];
		data = caculateMamounts(planList, loanContract, rectDate, days_t_mamount);
		if(null != data && data.size() > 0) json.putAll(data);
		return json;
	}
	
	/**
	 * 以银行算法为主：按天算
	 * 以放款日做为起始日期算随借随还利息和管理费
	 * @param planList
	 * @param loanContract
	 * @param rectDate
	 * @throws ServiceException
	 */
	private JSONObject caculateAmountByDays(List<PlanEntity> planList,LoanContractEntity loanContract, Date rectDate)
			throws ServiceException {
		JSONObject json = new JSONObject();
		double days_t_iamount = getIAmountsByDays(loanContract, rectDate);
		JSONObject data = caculateIamounts(planList, loanContract, rectDate, days_t_iamount);
		if(null != data && data.size() > 0) json.putAll(data);
		days_t_iamount = getMAmountsByDays(loanContract, rectDate);
		data = caculateMamounts(planList, loanContract, rectDate, days_t_iamount);
		if(null != data && data.size() > 0) json.putAll(data);
		return json;
	}
	
	/**
	 * 计算应收利息和本次计划利息
	 * @param planList
	 * @param loanContract
	 * @param rectDate
	 * @param days
	 * @return
	 */
	private JSONObject caculateIamounts(List<PlanEntity> planList,LoanContractEntity loanContract, Date rectDate, double days_t_iamount) {
		BigDecimal t_iamount = new BigDecimal(days_t_iamount);
		BigDecimal c_t_iamount =  new BigDecimal(days_t_iamount);
		BigDecimal c_iamount = null;
		int index = -1;
		for(int i=0,count=planList.size(); i<count; i++){
			PlanEntity planObj = planList.get(i);
			Date xpayDate = planObj.getXpayDate();
			BigDecimal interest = planObj.getInterest();
			BigDecimal yinterest = planObj.getYinterest();
			BigDecimal trinterAmount = planObj.getTrinterAmount();
			int result = DateUtil.compareDate(xpayDate, rectDate);
			t_iamount = BigDecimalHandler.sub2BigDecimal(t_iamount, yinterest);
			t_iamount = BigDecimalHandler.sub2BigDecimal(t_iamount, trinterAmount);
			if(result>=0){
				index = i;
			}else{
				c_t_iamount = BigDecimalHandler.sub2BigDecimal(c_t_iamount, interest);
			}
		}
		c_iamount = c_t_iamount;
		if(c_iamount.doubleValue() < 0){
			c_iamount = new BigDecimal("0");
		}else{
			c_iamount = BigDecimalHandler.roundToBigDecimal(c_iamount, 2);
		}
		if(t_iamount.doubleValue() < 0){
			t_iamount = new BigDecimal("0");
		}else{
			t_iamount = BigDecimalHandler.roundToBigDecimal(t_iamount, 2);
		}
		Long planId = null;
		if(index == -1){
			planId = planList.get(planList.size()-1).getId();
		}else{
			planId = planList.get(index).getId();
		}
		JSONObject json = new JSONObject();
		json.put("t_iamount", t_iamount);
		json.put("c_iamount", c_iamount);
		json.put("planId", planId);
		return json;
	}
	
	private JSONObject caculateMamounts(List<PlanEntity> planList,LoanContractEntity loanContract, Date rectDate, double days_t_iamount) {
		Integer mgrtype = loanContract.getMgrtype();
		if(null == mgrtype || mgrtype.intValue() == BussStateConstant.MGRTYPE_0) return null;/*不收管理费*/
		BigDecimal t_iamount = new BigDecimal(days_t_iamount);
		BigDecimal c_t_iamount =  new BigDecimal(days_t_iamount);
		BigDecimal c_iamount = null;
		for(int i=0,count=planList.size(); i<count; i++){
			PlanEntity planObj = planList.get(i);
			Date xpayDate = planObj.getXpayDate();
			BigDecimal mrgAmount = planObj.getMgrAmount();
			BigDecimal ymgrAmount = planObj.getYmgrAmount();
			BigDecimal trmgrAmount = planObj.getTrmgrAmount();
			int result = DateUtil.compareDate(xpayDate, rectDate);
			t_iamount = BigDecimalHandler.sub2BigDecimal(t_iamount, ymgrAmount);
			t_iamount = BigDecimalHandler.sub2BigDecimal(t_iamount, trmgrAmount);
			if(result>=0){
				continue;
			}else{
				c_t_iamount = BigDecimalHandler.sub2BigDecimal(c_t_iamount, mrgAmount);
			}
		}
		
		c_iamount = c_t_iamount;
		if(c_iamount.doubleValue() < 0){
			c_iamount = new BigDecimal("0");
		}else{
			c_iamount = BigDecimalHandler.roundToBigDecimal(c_iamount, 2);
		}
		JSONObject json = new JSONObject();
		json.put("t_mamount", BigDecimalHandler.round(t_iamount, 2));
		json.put("c_mamount", c_iamount);
		return json;
	}
	
	/**
	 * 根据利率类型返回相对应的利率
	 * @param type	利率类型，参考RateType 枚举对象
	 * @param isMgrRate 是否取管理费利率   true : 是, false : 否
	 * @return
	 */
	public double getRate(int type,boolean isMgrRate,LoanContractEntity loanContractEntity){
		double rateVal = isMgrRate ? loanContractEntity.getMrate() : loanContractEntity.getRate();
		double rate = rateVal/SysConstant.PERCENT_DENOMINATOR;
		int rateType = loanContractEntity.getRateType();
		switch (type) {
		case RATETYPE_YEAR:{
			if(rateType == BussStateConstant.RATETYPE_1){//月利率
				rate = rate * 12;
			}else if(rateType == BussStateConstant.RATETYPE_2){//日利率
				rate = rate * 360;
			}
			break;
		}case RATETYPE_MONTH:{
			if(rateType == BussStateConstant.RATETYPE_2){//日利率
				rate = rate * 30;
			}else if(rateType == BussStateConstant.RATETYPE_3){//年利率
				rate = rate / 12;
			} 
			break;
		}case RATETYPE_DAY:{
			if(rateType == BussStateConstant.RATETYPE_1){//月利率
				rate = rate / 30;
			}else if(rateType == BussStateConstant.RATETYPE_3){//年利率
				rate = rate / 360;
			} 
			break;
		}default:
			break;
		}
		return rate;
	}
	
	/**
	 * 利率类型 RATETYPE_YEAR [1:年利率]
	 */
	public static final int RATETYPE_YEAR = 1;
	/**
	 * 利率类型 RATETYPE_MONTH [2:月利率]
	 */
	public static final int RATETYPE_MONTH = 2;
	/**
	 * 利率类型 RATETYPE_DAY [3:日利率]
	 */
	public static final int RATETYPE_DAY = 3;
	
	/**
	 * *如果满一个月就按月算息，截止随借随还收款日期按天算息 算法
	 * @param planList
	 * @param rectDateStr
	 * @return
	 * @throws ServiceException
	 */
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
		LoanInvoceEntity firstLoanInvoce = getLoanInvoce(contractId);
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
	 * 获取第一笔放款单
	 * @param contractId	合同ID
	 * @return
	 * @throws ServiceException
	 */
	private LoanInvoceEntity getLoanInvoce(Long contractId)
			throws ServiceException {
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId", contractId);
		map.put(SqlUtil.ORDERBY_KEY,"asc:realDate");
		List<LoanInvoceEntity> loanInvoceList = loanInvoceService.getEntityList(map);
		LoanInvoceEntity firstLoanInvoce = loanInvoceList.get(0);
		return firstLoanInvoce;
	}
	
	/**
	 * 获取用来计算还款计划的起始放款日期
	 * @param firstLoanInvoce
	 * @return
	 * @throws ServiceException 
	 */
	private Date getPayDate(Long contractId, Date rectDate) throws ServiceException {
		LoanInvoceEntity firstLoanInvoce = getLoanInvoce(contractId);
		
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
					continue;
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
		String random_algorithm = complexData.getvalAsStr(SysparamsConstant.RANDOM_ALGORITHM_KEY);
		String recordIds = null;
		if(StringHandler.isValidStr(random_algorithm) && random_algorithm.equals(SysparamsConstant.RANDOM_ALGORITHM_VAL_3)){/*直接扣取还款计划，不需要重新生成还款计划*/
			recordIds = receivSingleAmountsByNomal(complexData);
		}else{
			recordIds = receivSingleAmounts(complexData);
		}
		 
		
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
	 * (适用于系统参数  ALGORITHM = 3 [以还款计划表为标准（即根据还款计划表中的应收利息和管理费作为参考值；如果使用该参数，系统不会重新生成还款计划）])
	 * @param complexData
	 * @throws DaoException 
	 * @throws ServiceException 
	 * @return 返回收款记录ID
	 */
	private String receivSingleAmountsByNomal(SHashMap<String, Object> complexData) throws ServiceException{
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		Long contractId = complexData.getvalAsLng("contractId");
		Long sysId = complexData.getvalAsLng("sysId");
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId", contractId);
		map.put(SqlUtil.ORDERBY_KEY,"asc:phases,id");
		List<PlanEntity> planList = planService.getEntityList(map);
		/*======== step 1 : 保存随借随还收款记录 =======*/
		CasualRecordsEntity casualRecordsObj = createCasualRecordsEntity(complexData);
		casualRecordsService.saveEntity(casualRecordsObj);
		
		//id,user,accountId,rectDate,phases,cat,rat,mat,tat
		Long casualId = casualRecordsObj.getId();
		/*======== step 2 : 更新还款计划表状态和实收项  =======*/
		updatePlanAmounts(planList, complexData, casualId);
		
		/*======== step 3 : 如果有逾期，则更新表内表外  =======*/
		map.put("contractId", contractId);
		map.put(SqlUtil.ORDERBY_KEY,"asc:phases,id");
		planList = planService.getEntityList(map);
		overdueDeductService.saveTaboutsideDatas(contractId.toString(), planList, user);
		
		ApplyEntity applyEntity = updataApplyState(user, sysId, planList);
		/*======== step 4 : 如果应找回金额大于0，则存入预收款帐户  =======*/
		saveAdvanceAmounts(applyEntity,complexData);
		
		/*======== step 5 : 返回随借随还收款记录ID  =======*/
		return casualRecordsObj.getId().toString();
	}
	
	/**
	 * 随借随还扣收
	 * (适用于系统参数  ALGORITHM = 1 [按银行模式即全部按天算]或 2[从上一结息日到随借随还收款日期按天算,之前如果足月按月算] 两种模式)
	 * @param complexData
	 * @throws DaoException 
	 * @throws ServiceException 
	 * @return 返回收款记录ID
	 */
	private String receivSingleAmounts(SHashMap<String, Object> complexData) throws ServiceException{
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		Long contractId = complexData.getvalAsLng("contractId");
		Long sysId = complexData.getvalAsLng("sysId");
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
		updatePlanAmounts(planList,complexData,casualId);
		
		/*======== step 3 : 如果有逾期，则更新表内表外  =======*/
		map.put("contractId", contractId);
		map.put(SqlUtil.ORDERBY_KEY,"asc:phases,id");
		planList = planService.getEntityList(map);
		overdueDeductService.saveTaboutsideDatas(contractId.toString(), planList, user);
		
		/*======== step 3 : 更新贷款申请单状态  =======*/
		ApplyEntity applyEntity = updataApplyState(user, sysId, planList);
		/*======== step 4 : 如果应找回金额大于0，则存入预收款帐户  =======*/
		saveAdvanceAmounts(applyEntity,complexData);
		
		/*======== step 5 : 返回随借随还收款记录ID  =======*/
		return casualRecordsObj.getId().toString();
	}

	/**更新贷款申请单状态
	 * @param user
	 * @param sysId
	 * @param planList
	 * @return
	 * @throws ServiceException
	 */
	private ApplyEntity updataApplyState(UserEntity user, Long sysId,
			List<PlanEntity> planList) throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("sysid", sysId);
		params.put("recode", SqlUtil.LOGIC_EQ+SqlUtil.LOGIC+SysparamsConstant.ISNOT_SETTLE);
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		SysparamsEntity sysparamsEntity = sysparamsService.getEntity(params);
		String val = null;
		if(sysparamsEntity != null){
			val = sysparamsEntity.getVal();
		}
		ApplyEntity applyEntity = null;
		boolean changeState = false;
		if(val.equals(SysparamsConstant.ISNOT_SETTLE_1)){
			changeState = true;
		}
		applyEntity = getApplyEntity(planList,user,changeState);
		applyService.updateEntity(applyEntity);
		return applyEntity;
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
	
	/**
	 * 获取变更了状态和还款期数的贷款申请单对象
	 * @param planList	还款计划列表
	 * @param user	当前用户	
	 * @param changeState	当结清时，是否将贷款申请单状态改为：“结清”
	 * @return
	 * @throws ServiceException
	 */
	private ApplyEntity getApplyEntity(List<PlanEntity> planList,UserEntity user, boolean changeState) throws ServiceException {
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
		if((status.intValue() == BussStateConstant.PLAN_STATUS_2 ||
		   status.intValue() == BussStateConstant.PLAN_STATUS_3	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_6	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_7	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_8	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_9	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_10) && changeState){	/*最后一期且结清*/
			applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_6);
			Double mrate = applyEntity.getMrate();
			if(mrate==0){
				setInnerMrate(planList, contractId,applyEntity, user);/*如果结清，计算并更新内部管理费率*/
			}
		}else{
			applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_4);
		}
		Integer paydPhases = getPaydPhases(planList);
		applyEntity.setPaydPhases(paydPhases);
		BeanUtil.setModifyInfo(user, applyEntity);
		return applyEntity;
	}

	/**
	 * 计算并设置贷款申请单、借款合同的内部管理费率
	 * @param planList
	 * @param contractId
	 * @param applyEntity
	 * @throws ServiceException
	 */
	private void setInnerMrate(List<PlanEntity> planList, Long contractId,ApplyEntity applyEntity,UserEntity user)
			throws ServiceException {
		SHashMap<String, Object> params  = new SHashMap<String, Object>();
		params.put("contractId", contractId);
		BigDecimal t_ymgrAmount = new BigDecimal(0);
		for(PlanEntity x :planList){
			BigDecimal ymgrAmount = x.getYmgrAmount();
			BigDecimal rmamount = x.getRmamount();
			if(null != rmamount && rmamount.doubleValue() > 0) ymgrAmount = BigDecimalHandler.sub2BigDecimal(ymgrAmount, rmamount);
			t_ymgrAmount = BigDecimalHandler.add2BigDecimal(t_ymgrAmount,ymgrAmount);
		}
		List<CasualRecordsEntity> casualRecordsEntityList = casualRecordsService.getEntityList(params);
		if(null != casualRecordsEntityList && casualRecordsEntityList.size()>0){
			for(CasualRecordsEntity y: casualRecordsEntityList){
				t_ymgrAmount = BigDecimalHandler.add2BigDecimal(t_ymgrAmount, y.getFat());
			}
		}
		if(null == t_ymgrAmount || t_ymgrAmount.doubleValue() == 0) return;
		LoanContractEntity loanContractObj = loanContractService.getEntity(contractId);
		Double mrate =BigDecimalHandler.round(BigDecimalHandler.div(t_ymgrAmount, loanContractObj.getAppAmount(),4), 4)*100;
		
		applyEntity.setMrate(mrate);
		loanContractObj.setMrate(mrate);
		BeanUtil.setModifyInfo(user, loanContractObj);
		loanContractService.updateEntity(loanContractObj);
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
	private void updatePlanAmounts(List<PlanEntity> planList, SHashMap<String, Object> complexData, Long casualId) throws ServiceException{
		if(null == planList || planList.size() == 0) return;
		BigDecimal t_principal = complexData.getvalAsBig("principal");
		BigDecimal t_interest = complexData.getvalAsBig("interest");
		BigDecimal t_mgrAmount = complexData.getvalAsBig("mgrAmount");
		BigDecimal t_penAmount = complexData.getvalAsBig("penAmount");
		BigDecimal t_delAmount = complexData.getvalAsBig("delAmount");
		
		String rectDate = (String)complexData.get("rectDate");
		Date lastDate = DateUtil.dateFormat(rectDate);
		UserEntity currUser = (UserEntity)complexData.get(SysConstant.USER_KEY);
		List<PlanEntity> newplanList = new ArrayList<PlanEntity>(); 
		List<AmountRecordsEntity> recordsList = new ArrayList<AmountRecordsEntity>();
		SHashMap<String, Object> mapData = null;
		for(int i=0,count=planList.size(); i<count; i++){
			PlanEntity planEntity = planList.get(i);
			Long planId = planEntity.getId();
			if(t_principal.doubleValue() <= 0D && t_interest.doubleValue() <= 0D 
					&& t_mgrAmount.doubleValue() <=0D && t_penAmount.doubleValue() <= 0D && t_delAmount.doubleValue() <= 0D) break;
			if(!isPayAmount(planEntity)) continue;
			mapData = getInitMapDatas(complexData);
			if(t_principal.doubleValue() > 0 ) t_principal = caclutePrincipal(planEntity,t_principal,mapData);
			if(t_interest.doubleValue() > 0 ) t_interest = cacluteInterest(planEntity,t_interest,mapData);
			if(t_mgrAmount.doubleValue() > 0 ) t_mgrAmount = cacluteMgramount(planEntity,t_mgrAmount,mapData);
			if(((null != t_penAmount && t_penAmount.doubleValue() > 0) ||
				(null != t_delAmount && t_delAmount.doubleValue() > 0))){
				if(t_penAmount.doubleValue() > 0 ) t_penAmount = caclutePenAmount(planEntity,t_penAmount,mapData);
				if(t_delAmount.doubleValue() > 0 ) t_delAmount = cacluteDelAmount(planEntity,t_delAmount,mapData);
				t_penAmount = new BigDecimal("0");
				t_delAmount = new BigDecimal("0");
			}
			if(!isCreateRecords(mapData)) continue;
			AmountRecordsEntity amountRecordsObj = createAmountRecordsEntity(mapData, currUser, planId, casualId);
			recordsList.add(amountRecordsObj);
			planEntity.setLastDate(lastDate);
			planHandler.setTotalAmountByPlan(planEntity);
			BeanUtil.setModifyInfo(currUser, planEntity);
			newplanList.add(planEntity);
		}
		if(null != newplanList && newplanList.size() > 0) planService.batchUpdateEntitys(newplanList);
		if(null != recordsList && recordsList.size() > 0) amountRecordsService.batchSaveOrUpdateEntitys(recordsList);
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
			if(t_principal.doubleValue() <= 0D && t_interest.doubleValue() <= 0D 
					&& t_mgrAmount.doubleValue() <=0D && t_penAmount.doubleValue() <= 0D && t_delAmount.doubleValue() <= 0D) break;
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
			planHandler.setTotalAmountByPlan(planEntity);
			BeanUtil.setModifyInfo(currUser, planEntity);
			newplanList.add(planEntity);
			if(newIndex != -1) break;
		}
		Date lastPlanDate = planList.get(planList.size()-1).getXpayDate();
		int result = DateUtil.compareDate(lastPlanDate, lastDate);
		//--> 如果随借随还收款日期超过了贷款截止日期，就不需要重新生成还款计划表
		if(newIndex != -1 && result>0) remakePlans(planList,newIndex,newplanList,complexData,casualId);
		if(null != newplanList && newplanList.size() > 0) planService.batchUpdateEntitys(newplanList);
		if(null != recordsList && recordsList.size() > 0) amountRecordsService.batchSaveOrUpdateEntitys(recordsList);
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
		//if(totalResult >= 0 || pricipalResult >= 0){/*实收合计-应收合计 >=0 表示结清 或  实收本金-应收本金 >=0 表示结清*/
		if(totalResult >= 0){/*实收合计-应收合计 >=0 表示结清 或  实收本金-应收本金 >=0 表示结清*/
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
				planHandler.setTotalAmountByPlan(planEntity);
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
			
			planHandler.setTotalAmountByPlan(planObj);
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
		
		double[] amoutArr = planHandler.getAmountResult(t_principal.doubleValue(), p_zprincipal.doubleValue());
		double d_tamount = amoutArr[0];
		double d_yamount = amoutArr[1];
		t_principal = BigDecimalHandler.get(d_tamount);
		if(d_yamount > 0){
			BigDecimal b_yamt = BigDecimalHandler.get(d_yamount);
			b_yamt = BigDecimalHandler.add2BigDecimal(planEntity.getYprincipal(), b_yamt, 2);
			planEntity.setYprincipal(b_yamt);
			mapData.put("cat", b_yamt);
		}
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
		BigDecimal p_triamount = planEntity.getTrinterAmount();
		BigDecimal p_riamount = planEntity.getRiamount();
		if(null == p_riamount) p_riamount = new BigDecimal("0");
		BigDecimal p_zamount = BigDecimalHandler.sub2BigDecimal(2, p_amount, p_yamount,p_triamount);
		p_zamount = BigDecimalHandler.add2BigDecimal(p_zamount, p_riamount, 2);
		
		double[] amoutArr = planHandler.getAmountResult(t_amount.doubleValue(), p_zamount.doubleValue());
		double d_tamount = amoutArr[0];
		double d_yamount = amoutArr[1];
		t_amount = BigDecimalHandler.get(d_tamount);
		if(d_yamount > 0){
			BigDecimal b_yamt = BigDecimalHandler.get(d_yamount);
			b_yamt = BigDecimalHandler.add2BigDecimal(planEntity.getYinterest(), b_yamt, 2);
			planEntity.setYinterest(b_yamt);
			mapData.put("rat", b_yamt);
		}
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
		BigDecimal p_triamount = planEntity.getTrmgrAmount();
		BigDecimal p_zamount = BigDecimalHandler.sub2BigDecimal(2,p_amount, p_yamount,p_triamount);
		BigDecimal p_riamount = planEntity.getRmamount();
		if(null == p_riamount) p_riamount = new BigDecimal("0");
		p_zamount = BigDecimalHandler.add2BigDecimal(p_zamount, p_riamount, 2);
		
		double[] amoutArr = planHandler.getAmountResult(t_amount.doubleValue(), p_zamount.doubleValue());
		double d_tamount = amoutArr[0];
		double d_yamount = amoutArr[1];
		t_amount = BigDecimalHandler.get(d_tamount);
		if(d_yamount > 0){
			BigDecimal b_yamt = BigDecimalHandler.get(d_yamount);
			b_yamt = BigDecimalHandler.add2BigDecimal(planEntity.getYmgrAmount(), b_yamt, 2);
			planEntity.setYmgrAmount(b_yamt);
			mapData.put("mat", b_yamt);
		}
		return t_amount;
	}
	
	/**
	 * 批扣罚息
	 * @param planEntity
	 * @param t_amount	本次实收罚息
	 * @param mapData	存放本次从还息计划表实际扣掉的罚息金额
	 * @return	返回本次扣取本金剩余罚息金额
	 */
	private BigDecimal caclutePenAmount(PlanEntity planEntity, BigDecimal t_amount,SHashMap<String, Object> mapData){
		BigDecimal p_amount = planEntity.getPenAmount();
		BigDecimal p_yamount = planEntity.getYpenAmount();
		BigDecimal p_triamount = planEntity.getTrpenAmount();
		BigDecimal p_zamount = BigDecimalHandler.sub2BigDecimal(2, p_amount, p_yamount,p_triamount);
		
		BigDecimal p_riamount = planEntity.getRpamount();
		if(null == p_riamount) p_riamount = new BigDecimal("0");
		p_zamount = BigDecimalHandler.add2BigDecimal(p_zamount, p_riamount, 2);
		
		double[] amoutArr = planHandler.getAmountResult(t_amount.doubleValue(), p_zamount.doubleValue());
		double d_tamount = amoutArr[0];
		double d_yamount = amoutArr[1];
		t_amount = BigDecimalHandler.get(d_tamount);
		if(d_yamount > 0){
			BigDecimal b_yamt = BigDecimalHandler.get(d_yamount);
			b_yamt = BigDecimalHandler.add2BigDecimal(planEntity.getYpenAmount(), b_yamt, 2);
			planEntity.setYpenAmount(b_yamt);
			mapData.put("pat", b_yamt);
		}
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
		BigDecimal p_triamount = planEntity.getTrdelAmount();
		BigDecimal p_zamount = BigDecimalHandler.sub2BigDecimal(2, p_amount, p_yamount, p_triamount);
		
		BigDecimal p_riamount = planEntity.getRdamount();
		if(null == p_riamount) p_riamount = new BigDecimal("0");
		p_zamount = BigDecimalHandler.add2BigDecimal(p_zamount, p_riamount, 2);
		
		double[] amoutArr = planHandler.getAmountResult(t_amount.doubleValue(), p_zamount.doubleValue());
		double d_tamount = amoutArr[0];
		double d_yamount = amoutArr[1];
		t_amount = BigDecimalHandler.get(d_tamount);
		if(d_yamount > 0){
			BigDecimal b_yamt = BigDecimalHandler.get(d_yamount);
			b_yamt = BigDecimalHandler.add2BigDecimal(planEntity.getYdelAmount(), b_yamt, 2);
			planEntity.setYdelAmount(b_yamt);
			mapData.put("dat", b_yamt);
		}
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
