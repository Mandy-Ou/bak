package com.cmw.service.impl.finance.paytype;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmw.constant.BussStateConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.entity.finance.ChildPlanEntity;
/**
 * 到期一次性还本付息计算类
 * @author chengmingwei
 *
 */
public class EinterestAmountPayType extends PayTypeCalculateAbs {
	protected Map<String,Object> formulaParams = new HashMap<String, Object>();
	@Override
	public List<ChildPlanEntity> calculate()throws ServiceException {
		putFormulaParams();
		if(null == bussType || bussType == ControlerType.TOTALAMOUNT_CHANGE){//正常还款计划和随借随还、部分提前还款调用此方法
			return doCalculate();
		}else{
			switch (bussType) {
				case RATE_CHANGE:{
					
					break;
				}
				default:
				break;
			}
			return null;
		}
	}
	
	/**
	 * 添加公式参数
	 */
	private void putFormulaParams(){
		formulaParams.put("appAmount", payAmount);
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
			//---> 第一期还款计划
			List<ChildPlanEntity> childPlanList = getFirstPlanAmount(); 
			planAmountList.addAll(childPlanList);
	}
	
	/**
	 * 获取第一期还款计划实体
	 * @return
	 * @throws ServiceException 
	 */
	private List<ChildPlanEntity> getFirstPlanAmount() throws ServiceException {
		Date paydate = getDateByPhases(0);
		int totalPhases = phasesMap.size();
		List<ChildPlanEntity> childList = new ArrayList<ChildPlanEntity>();
		boolean flag = false;
		if(loanContractEntity.getIsadvance().intValue() == BussStateConstant.ISADVANCE_1){
			totalPhases -= 1;
			flag = true;
		}else{
			paydate = realDate;
		}
	
		formulaParams.put("totalPhases", totalPhases);
		double[] rAndMamounts = getFirstRateAndMgrAmounts(paydate,formulaParams, true);
		double rateamount = rAndMamounts[0];
		double mgramount = rAndMamounts[1];
		double selfamount = flag ? 0.0 : payAmount;
		double xamount = selfamount + rateamount + mgramount; /*还款金额*/
		double bamount = payAmount - selfamount;
		ChildPlanEntity entity = createEntity(1,paydate, rateamount, xamount,selfamount,mgramount,bamount);
		childList.add(entity);
		if(flag){/*当预收息时要多加一期*/
			selfamount = payAmount;
			xamount = payAmount;
			bamount = 0d;
			entity = createEntity(2,endDate, 0d, xamount,selfamount,0d,bamount);
			childList.add(entity);
		}
		return childList;
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
