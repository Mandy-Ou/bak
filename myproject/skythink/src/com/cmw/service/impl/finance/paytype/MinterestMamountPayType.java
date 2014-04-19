package com.cmw.service.impl.finance.paytype;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmw.constant.BussStateConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DateUtil;
import com.cmw.entity.finance.ChildPlanEntity;
/**
 * 按月付息，分期还本计算类
 * @author chengmingwei
 *
 */
public class MinterestMamountPayType extends PayTypeCalculateAbs {
	protected Map<String,Object> formulaParams = new HashMap<String, Object>();
	@Override
	public List<ChildPlanEntity> calculate()throws ServiceException {
		putFormulaParams();
		if(null != planAmountList && planAmountList.size()>0){//利率调整处理
			return resetCalculate();
		}else{
			return doCalculate();
		}
	}
	
	/**
	 * 添加公式参数
	 */
	private void putFormulaParams(){
		formulaParams.put("reprincipal", payAmount);
		double rate = getRate(RateType.MONTH_RATE,false);
		formulaParams.put("rate", rate);
	}
	
	/**
	 * 还款计划计算
	 * @return
	 * @throws ServiceException 
	 */
	private List<ChildPlanEntity> doCalculate() throws ServiceException{
		doPlanAmounts();
		return planAmountList;
	}
	
	/**
	 * 生成还款计划列表
	 * @throws ServiceException 
	 */
	private void doPlanAmounts() throws ServiceException{
		if(null == planAmountList) planAmountList = new ArrayList<ChildPlanEntity>();
		//只有一期的情况,按最后一期公式计算
		if(phases.intValue() == 1){
			doOnlyLast();
			return;
		}else{
			//---> 第一期还款计划
			ChildPlanEntity entity = null;
			entity = getFirstPlanAmount(); 
			planAmountList.add(entity);
			
			//---> 中间期还款计划
			double bamount = getCenterPlanAmounts(entity);
			
			//---> 最后一期还款计划
			entity = getLastPlanAmount(bamount);
			planAmountList.add(entity);
		}
	}
	
	/**
	 * 获取第一期还款计划实体
	 * @return
	 * @throws ServiceException 
	 */
	private ChildPlanEntity getFirstPlanAmount() throws ServiceException {
		Date paydate =  phasesMap.get(1);
		formulaParams.put("reprincipal", payAmount);
		double[] rAndMamounts = getFirstRateAndMgrAmounts(paydate,formulaParams);
		double rateamount = rAndMamounts[0];
		double mgramount = rAndMamounts[1];
		double selfamount = (loanContractEntity.getIsadvance().intValue() == BussStateConstant.ISADVANCE_1) ? 0.0 : phAmount;
		double xamount = selfamount + rateamount + mgramount; /*还款金额*/
		double bamount = payAmount - selfamount;
		ChildPlanEntity entity = createEntity(1,paydate, rateamount, xamount,selfamount,mgramount,bamount);
		return entity;
	}
	
	
	
	/**
	 * 中间期还款计划列表
	 * @param entity	第一期还款计划实体
	 * @return	返回上一期贷款余额
	 * @throws ServiceException 
	 */
	private double getCenterPlanAmounts(ChildPlanEntity entity) throws ServiceException {
		double bamount = entity.getReprincipal().doubleValue();	//放款金额
		/*如果是预收息，则要拿第当期的下一期来算两个的区间利息;否则,就拿当期的上一期来算区间利息*/
		int isadvance = loanContractEntity.getIsadvance();
		int preOrNextIndex = 0;
		double fixAmount = phAmount;
		boolean isOdd = false;
		if(isadvance == BussStateConstant.ISADVANCE_1){
			preOrNextIndex = 1;
			if(bamount<0) bamount = 0;
			if((bamount-fixAmount)<=0){
				fixAmount = bamount;
			}else{
				bamount -= fixAmount;
			}
			isOdd = true;
		}else{
			preOrNextIndex = -1;
		}
		
		for(int i=2; i< phases; i++){
			Date prevOrNextPaydate =  phasesMap.get(i+preOrNextIndex);
			Date paydate = phasesMap.get(i);
			double reprincipal = isOdd ? bamount : bamount-fixAmount;
			formulaParams.put("reprincipal", reprincipal);
			
			double[] rAndMamounts = getCenterRateAndMgrAmounts(paydate,prevOrNextPaydate,preOrNextIndex,formulaParams);
			double rateamount = rAndMamounts[0];
			double mgramount = rAndMamounts[1];
			double selfamount = fixAmount;
			if(isadvance == BussStateConstant.ISADVANCE_1 && isOdd){
				bamount+=fixAmount;
				isOdd = false;
			}
			double xamount = selfamount + rateamount + mgramount;
			double result = bamount - selfamount; 
			if(result>0){
				bamount -= selfamount;
			}else{
				if(bamount>0){
					selfamount = bamount;
				}
				bamount = 0;
				fixAmount = 0;
			}
			entity = createEntity(i,paydate, rateamount, xamount,selfamount,mgramount,bamount);
			planAmountList.add(entity);
		}
		return bamount;
	}
	
	
	/**
	 * 获取最后一期还款计划实体
	 * @param prebamount 上期贷款余额
	 * @return
	 * @throws ServiceException 
	 */
	private ChildPlanEntity getLastPlanAmount(double prebamount) throws ServiceException {
		Date prePaydate = phasesMap.get(phases-1);
		Date paydate = phasesMap.get(phases);
		double[] rmamounts = getLastRateAndMgrAmount(prePaydate,paydate,prebamount);
		double rateamount = rmamounts[0];
		double mgramount = rmamounts[1];
		double selfamount = prebamount;
		double xamount = selfamount + rateamount + mgramount;
		double bamount = 0d; //---> 最后一期贷款余额为 0
		ChildPlanEntity entity = createEntity(phases,paydate, rateamount, xamount,selfamount,mgramount,bamount);
		return entity;
	}
	
	/**
	 * 处理只有最后一期的情况
	 * @throws ServiceException 
	 */
	private void doOnlyLast() throws ServiceException {
		Date paydate = phasesMap.get(phases);
		double[] rmamounts = getLastRateAndMgrAmount(realDate,endDate,payAmount);
		double rateamount = rmamounts[0];
		double mgramount = rmamounts[1];
		double selfamount = payAmount;
		double xamount =  selfamount + rateamount + mgramount;
		ChildPlanEntity entity = createEntity(phases,paydate, rateamount, xamount, selfamount,mgramount,0.0);
		planAmountList.add(entity);
	}

	/**
	 * 获取最后一期还息金额 和 管理费金额 按日息计算
	 * @param amount 贷款本金或贷款余额
	 * @param eqDate 放款日期或最后一期的上一期还款日期
	 * @param paydate	还款日期
	 * @param reprincipal 剩余本金
	 * @return 返回还息金额 和管理费金额 [还息金额,管理费金额]
	 * @throws ServiceException 
	 */
	private double[] getLastRateAndMgrAmount(Date eqDate,Date paydate,double reprincipal) throws ServiceException {
		int days = DateUtil.calculateLimitDate(eqDate, paydate, DateUtil.DAY);
		double rate = 0d;
		double mrate = 0;
		int isadvance = loanContractEntity.getIsadvance();
		if(isadvance == BussStateConstant.ISADVANCE_1){/*如果预收息，最后一期不收利息和管理费*/
			return new double[]{0d,0d};
		}
		int month = DateUtil.getMonth(paydate);
		int lastDay = DateUtil.getMonthTotalDays(paydate);
		// (month == 2 && (days==lastDay || days == (lastDay-1))) 2月份最大天数是 28 天，要特殊处理
		if((month == 2 && (days==lastDay || days == (lastDay-1))) || days == 29){/*刚好一个月按月算息*/
			 rate = getRate(RateType.MONTH_RATE,false);
			 mrate = getRate(RateType.MONTH_RATE,true);
		}else{ /*按日算息和管理费*/
			 rate = getRate(RateType.DAY_RATE,false) * days;
			 mrate = getRate(RateType.DAY_RATE,true) * days;
		}
		formulaParams.put("reprincipal", reprincipal);
		formulaParams.put("rate", rate);
		double rateamount = getRateAmount(formulaParams,FormulaType.RATE_FORMULA);
		
		double mgramount = 0d;
		int mrgtype = null == loanContractEntity.getMgrtype() ? 0 : loanContractEntity.getMgrtype();
		if(mrgtype == BussStateConstant.MGRTYPE_1 && mrate>0){/*收管理费的情况下*/
			formulaParams.put("rate", mrate); /*日利率剩以天数*/
			mgramount = getRateAmount(formulaParams,FormulaType.RATE_FORMULA);
		}
		return new double[]{rateamount,mgramount};
	}
	
	/**
	 * 利率调整后计算
	 * @return
	 */
	private List<ChildPlanEntity> resetCalculate() throws ServiceException{
//		ChildPlanEntity reviseEntity = planAmountList.get(0);	//获取要调整利率的那一期还款期数
//		int currPhases = reviseEntity.getPhases();
//		int lastIndex = planAmountList.size() - 1;
//		ChildPlanEntity lastEntity = planAmountList.get(lastIndex);
//		
//		
//		//------> step 1 ：当期还款计划调整
//		Date prePaydate = reviseEntity.getXpayDate();
//		Date paydate = null;
//		double bamount = doCurrRate(reviseEntity);	
//		double xamount = reviseEntity.getPrincipal().doubleValue()+reviseEntity.getInterest().doubleValue()+reviseEntity.getMgrAmount().doubleValue();
//		reviseEntity.setTotalAmount(new BigDecimal(StringHandler.Round(xamount, 2)));
//		bamount = reviseEntity.getReprincipal().doubleValue();
//		
//		formulaParams.put("rate", loanContractEntity.getRate());
//		double rateamount = getRateAmount(formulaParams,FormulaType.RATE_FORMULA);
//		double mrate = getRate(RateType.DAY_RATE,true);
//		formulaParams.put("rate", mrate);
//		double mgramount = getRateAmount(formulaParams,FormulaType.RATE_FORMULA);
//		if(mgramount > 0) mgramount = StringHandler.Round(mgramount, 2);
//		//------> step 2 ：中间期还款计划调整	,排除最后一期
//		for(int i=currPhases+1; i < lastIndex; i++){
//			ChildPlanEntity centerEntity = planAmountList.get(i);
//			paydate = centerEntity.getXpayDate();
//			rateamount = StringHandler.Round(rateamount, 2);
//			centerEntity.setInterest(new BigDecimal(rateamount));
//			centerEntity.setMgrAmount(new BigDecimal(mgramount));
//			xamount = centerEntity.getPrincipal().doubleValue()+rateamount+mgramount;
//			xamount = StringHandler.Round(xamount, 2); 
//			centerEntity.setTotalAmount(new BigDecimal(xamount));
//			prePaydate = centerEntity.getXpayDate();
//		}
//		
//		//-----> step 3 : 最后一期还款计划调整
//		paydate = lastEntity.getXpayDate();
//		double rateamount = getLastRateAndMgrAmount(eqDate, paydate);
//		xamount = lastEntity.getSelfamount() + rateamount;
//		bamount = 0.0; //---> 最后一期贷款余额为 0
//		lastEntity.setRateamount(StringHandler.Round(rateamount, 2));
//		lastEntity.setXamount(StringHandler.Round(xamount, 2));
//		lastEntity.setBamount(bamount);
		return planAmountList;
	}
	
}
