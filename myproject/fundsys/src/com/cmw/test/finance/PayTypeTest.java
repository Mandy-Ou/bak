package com.cmw.test.finance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.test.AbstractTestCase;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.ChildPlanEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.finance.paytype.PayTypeCalculateAbs;
import com.cmw.service.impl.finance.paytype.PayTypeCalculateFactory;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.LoanInvoceService;
import com.cmw.service.inter.finance.PayTypeService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.sys.UserService;

public class PayTypeTest extends AbstractTestCase {
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	@Resource(name="loanInvoceService")
	private LoanInvoceService loanInvoceService;
	@Resource(name="payTypeService")
	private PayTypeService payTypeService;
	@Resource(name="planService")
	private PlanService planService;
	/**
	 * 按月还息，到期一次性还本
	 * @throws ServiceException
	 */
	@Test
	public void testMinterestEamount() throws ServiceException{
		Long userId = 45L;
		UserEntity user = userService.getEntity(userId);
		Long id = 1L;
		LoanContractEntity loanContractEntity = loanContractService.getEntity(id);
//		loanContractEntity.setPhAmount(10000d);
//		loanContractEntity.setRateType(BussStateConstant.RATETYPE_2);
//		loanContractEntity.setYearLoan(1);
//		loanContractEntity.setMonthLoan(1);
//		loanContractEntity.setDayLoan(15);
//		loanContractEntity.setRate(0.03);
//		loanContractEntity.setMrate(0.6);
//		loanContractEntity.setIsadvance(1);
		//loanContractEntity.setMonthLoan(5);
//		loanContractEntity.setAppAmount(new BigDecimal(300000));
		Long invoceId = 1L;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId", loanContractEntity.getId());
		LoanInvoceEntity invoceEntity = loanInvoceService.getEntity(map);//loanInvoceService.getEntity(invoceId);
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("loanInvoceId", invoceEntity.getId());
		params.put("realDate", DateUtil.dateFormat("2013-12-24"));//invoceEntity.getRealDate()
		params.put("payAmount", invoceEntity.getPayAmount());
		
		
		List<PlanEntity> planList = planService.getEntityList(map);
		
		PayTypeCalculateFactory factory = PayTypeCalculateFactory.getInstance();
		factory.setPayTypeService(payTypeService);
		PayTypeCalculateAbs  paytypeObj = factory.creator("P0001");
		
		List<ChildPlanEntity> childPlans = paytypeObj.make(user, loanContractEntity, params,null);
		planList = paytypeObj.getPlanList();
		printDatas(childPlans,planList);
	}
	
	/**
	 * 测试根据贷款余额重新生成还款计划表
	 * @throws ServiceException
	 */
	@Test
	public void testRebuildMake() throws ServiceException{
		Long userId = 45L;
		UserEntity user = userService.getEntity(userId);
		Long id = 1L;
		LoanContractEntity loanContractEntity = loanContractService.getEntity(id);
//		loanContractEntity.setPhAmount(10000d);
//		loanContractEntity.setRateType(BussStateConstant.RATETYPE_2);
//		loanContractEntity.setYearLoan(1);
//		loanContractEntity.setMonthLoan(1);
//		loanContractEntity.setDayLoan(15);
//		loanContractEntity.setRate(0.03);
//		loanContractEntity.setMrate(0.6);
//		loanContractEntity.setIsadvance(1);
		//loanContractEntity.setMonthLoan(5);
//		loanContractEntity.setAppAmount(new BigDecimal(300000));
//		Long invoceId = 1L;
//		SHashMap<String, Object> map = new SHashMap<String, Object>();
//		map.put("contractId", loanContractEntity.getId());
//		LoanInvoceEntity invoceEntity = loanInvoceService.getEntity(map);//loanInvoceService.getEntity(invoceId);
//		Map<String,Object> params = new HashMap<String, Object>();
//		params.put("loanInvoceId", invoceEntity.getId());
//		params.put("realDate", DateUtil.dateFormat("2013-12-24"));//invoceEntity.getRealDate()
//		params.put("payAmount", invoceEntity.getPayAmount());
//		
//		
//		List<PlanEntity> planList = planService.getEntityList(map);
//		
//		PayTypeCalculateFactory factory = PayTypeCalculateFactory.getInstance();
//		factory.setPayTypeService(payTypeService);
//		PayTypeCalculateAbs  paytypeObj = factory.creator("P0001");
//		
//		params.put("c_iamount", c_iamount);
//		params.put("c_mamount", c_mamount);
//		params.put("realDate", realDate);
//		params.put("payAmount", reprincipal);//贷款余额
//		paytypeObj.rebuildMake(user, loanContractEntity, params,newPlanList);
//		planList = paytypeObj.getPlanList();
//		printDatas(childPlans,planList);
	}
	
	private void printDatas(List<ChildPlanEntity> childPlans,List<PlanEntity> planList) {
		System.out.println("------------------  还款计划子表  --------------------");
		double childPayAmount = 0d;
		StringBuffer sb = new StringBuffer("期数\t\t应还款日\t\t本金\t\t利息\t\t管理费\t\t应付合计\t\t剩余本金\t\t\n");
		for(ChildPlanEntity plan : childPlans){
			Integer phases = plan.getPhases();
			Date xpayDate = plan.getXpayDate();
			BigDecimal principal = plan.getPrincipal();
			childPayAmount += principal.doubleValue();
			BigDecimal interest = plan.getInterest();
			BigDecimal mgrAmount = plan.getMgrAmount();
			BigDecimal totalAmount = plan.getTotalAmount();
			BigDecimal reprincipal = plan.getReprincipal();
			sb.append(phases+"\t\t"+DateUtil.dateFormatToStr(xpayDate)+"\t\t"+principal+"\t\t"
					+interest+"\t\t"+mgrAmount+"\t\t"+totalAmount+"\t\t"+reprincipal+"\t\t\n");
		}
		System.out.println(sb.toString());
		System.out.println("========__分表总放款金额:_"+childPayAmount+"_元__");
		
		System.out.println("===========================================================");
		
		printPlanList(planList);
	}

	private void printPlanList(List<PlanEntity> planList) {
		System.out.println("------------------  还款计划总表  --------------------");
		double payAmount = 0d;
		StringBuffer sb = new StringBuffer("期数\t\t应还款日\t\t本金\t\t利息\t\t管理费\t\t应付合计\t\t剩余本金\t\t\n");
		for(PlanEntity plan : planList){
			Integer phases = plan.getPhases();
			Date xpayDate = plan.getXpayDate();
			BigDecimal principal = plan.getPrincipal();
			payAmount += principal.doubleValue();
			BigDecimal interest = plan.getInterest();
			BigDecimal mgrAmount = plan.getMgrAmount();
			BigDecimal totalAmount = plan.getTotalAmount();
			BigDecimal reprincipal = plan.getReprincipal();
			sb.append(phases+"\t\t"+DateUtil.dateFormatToStr(xpayDate)+"\t\t"+principal+"\t\t"
					+interest+"\t\t"+mgrAmount+"\t\t"+totalAmount+"\t\t"+reprincipal+"\t\t\n");
		}
		System.out.println(sb.toString());
		System.out.println("========__总放款金额:_"+payAmount+"_元__");
	}
	
}
