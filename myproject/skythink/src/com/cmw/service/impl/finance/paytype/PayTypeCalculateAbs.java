package com.cmw.service.impl.finance.paytype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.kit.ikexpression.FormulaUtil;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SpringContextUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.finance.ChildPlanEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.PayTypeEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.finance.PlanHandler;

/**
 * 
 * @author chengmingwei
 *	
 */
public abstract class PayTypeCalculateAbs {
	
	/**
	 * 放款单ID
	 */
	protected Long loanInvoceId;
	/**
	 * 当前用户
	 */
	protected UserEntity user;
	/**
	 * 还款方式实体
	 */
	protected PayTypeEntity payTypeEntity;
	/**
	 * 借款合同实体
	 */
	protected LoanContractEntity loanContractEntity;
	/**
	 * 放款金额
	 */
	protected Double payAmount;
	/**
	 * 每期结算日
	 */
	protected Integer payDay;
	/**
	 * 每期固定还本额
	 */
	protected Double phAmount;
	/**
	 * 实际放款日期
	 */
	protected Date realDate;
	/**
	 * 第一期还款日期
	 */
	protected Date firstDate;
	/**
	 * 最后一期还款日期
	 */
	protected Date endDate;
	/**
	 * 是否当月还款 
	 */
	protected Boolean iscurrmonth = null;
	/**
	 * 总期数
	 */
	protected Integer phases=0;
	/**
	 * 利率调整日期
	 */
	protected Date cycledate;
	/**
	 * 旧的贷款利率
	 */
	protected Double oldrate;
	/**
	 * 自定义还款子类 ----- 存放自定义还本期数 
	 */
	protected Map<Integer,Double> custamountMap = new HashMap<Integer, Double>();
	/**
	 * 存放所有期数的 Map 对象
	 */
	protected Map<Integer,Date> phasesMap = new LinkedHashMap<Integer,Date>();
	
	//存放还款计划日期
	protected Date[] payDateArr = null;
	
	/**
	 * 还款计划列表
	 */
	protected List<ChildPlanEntity> planAmountList = null;
	/**
	 * 还款计划总表
	 */
	protected List<PlanEntity> planList = null;
	/**
	 * 控制器类型：用来标识是调利息或是根据贷款余额变更还款计划
	 */
	protected ControlerType bussType = null;
	/**
	 * 从借款合同中获取的参数
	 */
	protected Map<String,Object> params;
	
	/**
	 * 计算并返回还款计划子列表
	 * @return
	 */
	public abstract List<ChildPlanEntity> calculate()throws ServiceException;
	
	/**
	 * 初始化参数
	 */
	@SuppressWarnings("unchecked")
	protected void init() throws ServiceException{
		loanInvoceId = (Long)params.get("loanInvoceId");
		realDate = (Date)params.get("realDate");
		payDay = getPayDay();
		phAmount = loanContractEntity.getPhAmount();
		endDate = getEndDate();
		BigDecimal payAmountVal = (BigDecimal)params.get("payAmount");
		payAmount = payAmountVal.doubleValue();
		
		cycledate = (Date)params.get("cycledate");
		oldrate = (Double)params.get("oldrate");
		if(null != oldrate) oldrate = oldrate/SysConstant.PERCENT_DENOMINATOR;	//将百分比转成小数形式
		if(null == bussType){	//非利率调整时
			//自定义还款期数和本金
			custamountMap = (Map<Integer,Double>)params.get("custplan");
			if(null == payDay) throw new ServiceException("在借款合同中未设置每月还款日，无法生成还款计划！");
			calculatePhases();
		}
		if(this.params.containsKey("isExt") && (Boolean)this.params.get("isExt")){
			Long extcontractId = (Long)this.params.get("extcontractId");
			if(null == extcontractId) throw new ServiceException("生成展期还款计划时，参数展期协议书ID：“extcontractId”不能为空！");
		}
	}
	
	private void clear(){
		if(null != planAmountList && planAmountList.size() > 0) planAmountList.clear();
		if(null != planList && planList.size() > 0) planList.clear();
		if(null != this.params && this.params.size() > 0) this.params.clear();
		if(null != this.custamountMap && this.custamountMap.size() > 0) this.custamountMap.clear();
		if(null != this.bussType) this.bussType = null; 
	}
	
	/**
	 * 获取结算日
	 * @return
	 * @throws ServiceException 
	 */
	private Integer getPayDay() throws ServiceException{
		Integer payDay = 0;
		Integer setdayType = loanContractEntity.getSetdayType();
		if(setdayType.intValue() == BussStateConstant.LOANCONTRACT_SETDAYTYPE_1){
			int[] ymd = DateUtil.getYMD(realDate);
			payDay = ymd[2];
			//if(loanContractEntity.getPayDay() == null) loanContractEntity.setPayDay(payDay);
		}else{
			payDay = loanContractEntity.getPayDay();
			if(null == payDay) throw new ServiceException("当不是以实际放款日作为结算日时，结算日 \"payDay\" 不能为空!");
		}
		return payDay;
	}
	/**
	 * 获取贷款截止日期
	 * @return
	 */
	private Date getEndDate(){
		Date endDate = null;
		Integer yearLoan = loanContractEntity.getYearLoan();
		Integer monthLoan = loanContractEntity.getMonthLoan();
		Integer dayLoan = loanContractEntity.getDayLoan();
		if(null != yearLoan && yearLoan.intValue() > 0){
			endDate = DateUtil.calculateEndDate(realDate, yearLoan, DateUtil.YEAR);
		}
		if(null != monthLoan && monthLoan.intValue() > 0){
			Date startDate = (null == endDate) ? realDate : endDate;
			endDate = DateUtil.calculateEndDate(startDate, monthLoan, DateUtil.MONTH);
		}
		if(null != dayLoan && dayLoan.intValue() > 0){
			Date startDate = (null == endDate) ? realDate : endDate;
			endDate = DateUtil.calculateEndDate(startDate, dayLoan, DateUtil.DAY);
		}
		int lastDay = DateUtil.getMonthTotalDays(endDate);
		/*如果结算日减一天小于最后日期所在月份的最后一天，则要减1 .否则，就不减. 
		 * 例如：结算日为 31 号,最后一期假设是在4月份，则最后期应为 4月30号 而不是 4月29号*/
		if(payDay-1 < lastDay){
			endDate = DateUtil.minusDaysToDate(endDate, 1);/*减一天，算头不算尾*/
		}
		return endDate;
	}
	/**
	 * 计算并返回还款计划列表
	 * @param user 	当前用户对象
	 * @param loanContractEntity	借款合同 对象
	 * @param params	存放放款单或其它参数的 Map 对象
	 * @return
	 */
	public List<ChildPlanEntity> make(UserEntity user,LoanContractEntity loanContractEntity, Map<String,Object> params,List<PlanEntity> planList) throws ServiceException{
		clear();
		this.user = user;
		this.loanContractEntity = loanContractEntity;
		this.params = params;
		this.planList = planList;
		init();
		planAmountList = calculate();
		deviation();
		if(this.params.containsKey("isExt") && (Boolean)this.params.get("isExt")){/*展期计算*/
			extPlans();
		}else{/*正常还款计划的计算*/
			mergerPlans();
		}
		return planAmountList;
	}
	
	/**
	 * 计算并返回还款计划列表
	 * @param user 	当前用户对象
	 * @param loanContractEntity	借款合同 对象
	 * @param params	存放放款单或其它参数的 Map 对象
	 * @return
	 */
	public void rebuildMake(UserEntity user,LoanContractEntity loanContractEntity, Map<String,Object> params,List<PlanEntity> planList) throws ServiceException{
		clear();
		this.bussType = ControlerType.TOTALAMOUNT_CHANGE;
		this.user = user;
		this.loanContractEntity = loanContractEntity;
		this.params = params;
		this.planList = planList;
		init();
		calculatePhasesByPlans();
		planAmountList =  calculate();
		deviation();
		setPlanListAmounts();
	}
	
	
	private void setPlanListAmounts(){
		for(int i=0,count=planList.size(); i<count; i++){
			PlanEntity planObj = planList.get(i);
			ChildPlanEntity childPlan = getChildPlanByPhase(planObj);
			planObj.setInterest(childPlan.getInterest());
			planObj.setMgrAmount(childPlan.getMgrAmount());
			if(i == 0){
				makeCurrPlan(planObj);
			}else{
				planObj.setPrincipal(childPlan.getPrincipal());
				planObj.setReprincipal(childPlan.getReprincipal());
			}
			BigDecimal totalAmount = BigDecimalHandler.add2BigDecimal(2, planObj.getPrincipal(),planObj.getInterest(),
					planObj.getMgrAmount());
			planObj.setTotalAmount(totalAmount);
		}
	}
	
	private ChildPlanEntity getChildPlanByPhase(PlanEntity planObj){
		if(null == planAmountList || planAmountList.size() == 0) return null;
		ChildPlanEntity sameChildPlan = null;
		Integer p_phases = planObj.getPhases();
		for(ChildPlanEntity childPlan : planAmountList){
			Integer phases = childPlan.getPhases();
			if(p_phases.equals(phases)){
				sameChildPlan = childPlan;
				break;
			}
		}
		return sameChildPlan;
	}
	
	
	private void makeCurrPlan(PlanEntity planObj){
		BigDecimal c_iamount = (BigDecimal)params.get("c_iamount");
		BigDecimal c_mamount = (BigDecimal)params.get("c_mamount");
		BigDecimal interest = planObj.getInterest();
		BigDecimal mgrAmount = planObj.getMgrAmount();
		interest = BigDecimalHandler.add2BigDecimal(interest, c_iamount, 2);
		mgrAmount = BigDecimalHandler.add2BigDecimal(mgrAmount, c_mamount, 2);
		planObj.setInterest(interest);
		planObj.setMgrAmount(mgrAmount);
		BigDecimal totalAmount = BigDecimalHandler.add2BigDecimal(2, planObj.getPrincipal(),
				planObj.getInterest(), planObj.getMgrAmount(),
				planObj.getPenAmount(), planObj.getDelAmount());
		BigDecimal ytotalAmount = BigDecimalHandler.add2BigDecimal(2, planObj.getYprincipal(),
				planObj.getYinterest(), planObj.getYmgrAmount(),
				planObj.getYpenAmount(), planObj.getYdelAmount());
		Date xpayDate = planObj.getXpayDate();
		int result = DateUtil.compareDate(xpayDate, new Date());
		
		Integer pistatus = BussStateConstant.PLAN_PISTATUS_2;
		Integer mgrstatus = BussStateConstant.PLAN_MGRSTATUS_2;
		Integer status = BussStateConstant.PLAN_STATUS_2;
		if(ytotalAmount.doubleValue() == 0d && result < 0){
			pistatus = BussStateConstant.PLAN_PISTATUS_0;
			mgrstatus = BussStateConstant.PLAN_MGRSTATUS_0;
			status = BussStateConstant.PLAN_STATUS_0;
		}else{
			BigDecimal ztotalAmount = BigDecimalHandler.sub2BigDecimal(totalAmount, ytotalAmount, 2);
			if(ztotalAmount.doubleValue() > 0){
				pistatus = BussStateConstant.PLAN_PISTATUS_1;
				mgrstatus = BussStateConstant.PLAN_MGRSTATUS_1;
				status = BussStateConstant.PLAN_STATUS_1;
			}
		}
		planObj.setPistatus(pistatus);
		planObj.setMgrstatus(mgrstatus);
		planObj.setStatus(status);
		String empName = user.getEmpName();
		String userName = user.getUserName();
		String now = DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT,new Date());
		planObj.setRemark("["+now+"]员工["+empName+"]使用帐号:["+userName+"] 在系统中进行了随借随还收款");
	}
	
	/**
	 * 根据已有还款计划表期数重新生成期数Map
	 */
	protected void calculatePhasesByPlans(){
		Date _endDate = null;
		for(int i=0,count=this.planList.size(); i<count; i++){
			PlanEntity planObj = this.planList.get(i);
			Integer _phases = planObj.getPhases();
			Date xpayDate = planObj.getXpayDate();
			_endDate = xpayDate;
			phasesMap.put(_phases, xpayDate);
		}
		this.phases = phasesMap.size();
		this.endDate = _endDate;
	}
	
	
	
	/**
	 * 期数计算
	 */
	protected void calculatePhases(){
		phases = 0;
		int isadvance = loanContractEntity.getIsadvance();
		Date date = null;
		if(isadvance == BussStateConstant.ISADVANCE_1){
			phases++;
			date = realDate;
			phasesMap.put(phases, date);
		}
		date = DateUtil.replaceDayToDate(realDate, payDay);
		iscurrmonth = DateUtil.eqSameYearMonth(realDate, date);
		int result = realDate.compareTo(date);
		if(iscurrmonth && result >= 0){/*如果在同一个月，且实际放款日大于 date 时，date 要加1,算到放款日的下一个月*/
			date = DateUtil.plusMonthToDate(realDate,1);
			date = DateUtil.replaceDayToDate(date, payDay);
			iscurrmonth = false;
		}
		
		//---> 第1种情况： 只有一期
		//截止日期小于第一期还款日期时，例：还款日为30号，2010年1月1号的放款日,只贷了20天,截止日为 2010年1月20号。小于第一期 2010/1/30 号的情况
		if(date.after(endDate)){	
			phases++;
			phasesMap.put(phases, endDate);
			return;
		}
		while(date.before(endDate)){
			phases++;
//			System.out.println("第"+phases+"期:"+DateUtil.dateFormatToStr(date));
			phasesMap.put(phases, date);
			date = DateUtil.plusMonthToDate(date, 1, payDay);
		}
		//---> 最后一期 ,如果起始还款日期是次月的情况下，最后一期的上一期不能合并
		if(!iscurrmonth){
			phases++;
		}
		phasesMap.put(phases, endDate);
		printPhases();
	}
	
	private void printPhases(){
		Set<Integer> keys = phasesMap.keySet();
		for(Integer key : keys){
			System.out.println(key+" = "+DateUtil.dateFormatToStr(phasesMap.get(key)));
		}
	}
	
	/**
	 * 创建还款计划实体
	 * @param phases	期数	
	 * @param paydate	还款日期
	 * @param rateamount	利息金额
	 * @param xamount	还款金额
	 * @param selfAmount	还本金额
	 * @param mgrAmount 管理费金额
	 * @param bamount	贷款余额
	 * @return 返回还款计划实体
	 */
	protected ChildPlanEntity createEntity(Integer phases,Date paydate, double rateamount,
			double xamount,double selfAmount,double mgrAmount,double bamount) {
		ChildPlanEntity entity = new ChildPlanEntity();
		entity.setLoanInvoceId(loanInvoceId);
		entity.setPhases(phases);
		entity.setXpayDate(paydate);
		entity.setInterest(StringHandler.Round2BigDecimal(rateamount, 2));
		entity.setPrincipal(StringHandler.Round2BigDecimal(selfAmount, 2));
		entity.setMgrAmount(StringHandler.Round2BigDecimal(mgrAmount,2));
		entity.setTotalAmount(StringHandler.Round2BigDecimal(xamount,2));
		entity.setReprincipal(StringHandler.Round2BigDecimal(bamount,2));
		BeanUtil.setCreateInfo(user, entity);
		return entity;
	}
	
	/**
	 * 处理子还款计划表的误差
	 */
	private void deviation(){
		if(null == planAmountList || planAmountList.size() == 0) return;
		double _totalAmount = 0;
		int i = 0;
		int index = -1;
		for(ChildPlanEntity entity : planAmountList){
			double principal = entity.getPrincipal().doubleValue();
			_totalAmount += principal;
			if(principal>0) index = i;
			i++;
		}
		double xamount = payAmount - _totalAmount;
		if(xamount == 0) return;
		if(index < 0) return;
		ChildPlanEntity cEntity = planAmountList.get(index);
		double principal = cEntity.getPrincipal().doubleValue();
		principal += xamount;
		cEntity.setPrincipal(StringHandler.Round2BigDecimal(principal, 2));
		double totalAmount = cEntity.getTotalAmount().doubleValue();
		totalAmount += xamount;
		cEntity.setTotalAmount(StringHandler.Round2BigDecimal(totalAmount, 2));
	}
	
	/**
	 * 对还款计划展期
	 */
	private void extPlans(){
		Long extcontractId = (Long)this.params.get("extcontractId");
		Integer extNo = 1;
		if(null != planList && planList.size() > 0){
			int lastIndex = planList.size() - 1;
			PlanEntity lastPlanEntity = planList.get(lastIndex);
			BigDecimal pricipal = lastPlanEntity.getPrincipal();
			BigDecimal yprincipal = lastPlanEntity.getYprincipal();
			if(null == yprincipal) yprincipal = new BigDecimal("0");
			BigDecimal totalAmount = lastPlanEntity.getTotalAmount();
			if(null == totalAmount) totalAmount = new BigDecimal("0");
			//将未开始展期前还款计划表中的最后一期还款计划本金重置为0
			if(null != pricipal && pricipal.doubleValue() > 0){
				lastPlanEntity.setPrincipal(yprincipal);
				totalAmount = BigDecimalHandler.add2BigDecimal(2,lastPlanEntity.getPrincipal(),lastPlanEntity.getInterest(),lastPlanEntity.getMgrAmount());
				if(totalAmount.doubleValue() < 0) totalAmount = new BigDecimal("0");
				lastPlanEntity.setTotalAmount(totalAmount);
				BigDecimal reprincipal = BigDecimalHandler.get(payAmount);
				lastPlanEntity.setReprincipal(reprincipal);
				PlanHandler planHandler = (PlanHandler)SpringContextUtil.getBean("planHandler");
				planHandler.setTotalAmountByPlan(lastPlanEntity);
			}
			extNo = lastPlanEntity.getExtNo();
			if(null != extNo) extNo++;
		}
		
		int index = -1;//用来保存本金大于0的那一期的还款计划
		double totalPrincipal = 0d;
		Long contractId = loanContractEntity.getId();
		for(int i=0, count=planAmountList.size(); i<count; i++){
			ChildPlanEntity childPlan = planAmountList.get(i);
			Integer _phases = planList.get(planList.size()-1).getPhases() + 1;
			childPlan.setPhases(_phases);
			PlanEntity planEntity = createPlanEntity(contractId, _phases);
			planEntity.setExtNo(extNo);
			planEntity.setExtcontractId(extcontractId);
			planList.add(planEntity);
			totalPrincipal = setPlanByChildPlan(planEntity, childPlan, totalPrincipal);
			BigDecimal principal = planEntity.getPrincipal();
			if(null != principal && principal.doubleValue() > 0){
				index = i;
			}
		}
		BigDecimal appAmount = loanContractEntity.getAppAmount();
		double d_appAmount = appAmount.doubleValue();
		if(totalPrincipal == d_appAmount) return;
		
		/*-------- 处理本金小数误差问题 ---------*/
		PlanEntity lastEntity =  null;
		if(index != -1) lastEntity = planList.get(index);
		doPlandeviation(lastEntity, totalPrincipal, d_appAmount);
	}
	
	/**
	 * 合并还款计划表
	 */
	private void mergerPlans(){
		if(null == planList || planList.size() == 0){
			int size = planAmountList.size();
			Long contractId = loanContractEntity.getId();
			planList = new ArrayList<PlanEntity>(size);
			for(int i=0; i<size; i++){
				int phases = i+1;
				PlanEntity entity = createPlanEntity(contractId, phases);
				planList.add(entity);
			}
		}else{
			for(PlanEntity entity : planList){
				BeanUtil.setModifyInfo(user, entity);
			}
		}
		updatePlans();
	}

	/**
	 * 创建还款计划总表对象
	 * @param contractId	合同ID
	 * @param phases	期数
	 * @return	返回还款计划总表对象
	 */
	private PlanEntity createPlanEntity(Long contractId, int phases) {
		PlanEntity entity = new PlanEntity();
		entity.setPhases(phases);
		entity.setContractId(contractId);
		BeanUtil.setCreateInfo(user, entity);
		return entity;
	}
	
	private void updatePlans(){
		int index = -1;//用来保存本金大于0的那一期的还款计划
		double totalPrincipal = 0d;
		int size = planList.size();
		
		for(int i=0; i<size; i++){
			PlanEntity eqPlanEntity = planList.get(i);
			int planPhases = eqPlanEntity.getPhases();
			ChildPlanEntity childPlan = null;
			for(ChildPlanEntity childEntity : planAmountList){
				Integer phases = childEntity.getPhases();
				if(phases.equals(planPhases)){
					childPlan = childEntity;
					break;
				}
			}
			if(null == childPlan) continue;
			totalPrincipal = setPlanByChildPlan(eqPlanEntity, childPlan, totalPrincipal);
			BigDecimal principal = eqPlanEntity.getPrincipal();
			if(null != principal && principal.doubleValue() > 0){
				index = i;
			}
			
		}
		BigDecimal appAmount = loanContractEntity.getAppAmount();
		double d_appAmount = appAmount.doubleValue();
		if(totalPrincipal == d_appAmount) return;
		
		/*-------- 处理本金小数误差问题 ---------*/
		PlanEntity lastEntity =  null;
		if(index != -1) lastEntity = planList.get(index);
		doPlandeviation(lastEntity, totalPrincipal, d_appAmount);
	}

	/**
	 * 处理还款计划总表的误差
	 * @param entity	最后一次本金大于0的还款计划对象。
	 * @param totalPrincipal 累计的每期贷款本金
	 * @param d_appAmount	贷款或展期金额
	 */
	private void doPlandeviation(PlanEntity entity , double totalPrincipal,double d_appAmount) {
		if(null == entity) return;
		double xamount = totalPrincipal - d_appAmount;
		if(xamount <= 0) return;
		double ld_principal = entity.getPrincipal().doubleValue();
		ld_principal -= xamount;
		ld_principal = StringHandler.Round(ld_principal, 2);
		entity.setPrincipal(StringHandler.Round2BigDecimal(ld_principal, 2));
		
		double ld_totalAmount = entity.getTotalAmount().doubleValue();
		ld_totalAmount -= xamount;
		entity.setTotalAmount(StringHandler.Round2BigDecimal(ld_totalAmount, 2));
		
		double ld_reprincipal = entity.getReprincipal().doubleValue();
		if(ld_reprincipal > 0){
			ld_reprincipal += xamount;
			entity.setReprincipal(StringHandler.Round2BigDecimal(ld_reprincipal, 2));
		}
	}
	
	/**
	 * 将子表中的数据加入到还款计划总表中
	 * @param eqPlanEntity	还款计划总表对象
	 * @param childPlan	 还款计划子表对象
	 * @param totalPrincipal	累计贷款金额
	 * @return 返回累计的贷款金额
	 */
	private double setPlanByChildPlan(PlanEntity eqPlanEntity,ChildPlanEntity childPlan,double totalPrincipal){
		Date xpayDate = childPlan.getXpayDate();
		if(null == eqPlanEntity.getId()){
			eqPlanEntity.setXpayDate(xpayDate);
		}
		
		BigDecimal c_principal = childPlan.getPrincipal();
		if(null == c_principal) c_principal = new BigDecimal(0);
		BigDecimal c_interest = childPlan.getInterest();
		if(null == c_interest) c_interest = new BigDecimal(0);
		BigDecimal c_mgrAmount = childPlan.getMgrAmount();
		if(null == c_mgrAmount) c_mgrAmount = new BigDecimal(0);
		BigDecimal c_totalAmount = childPlan.getTotalAmount();
		if(null == c_totalAmount) c_totalAmount = new BigDecimal(0);
		BigDecimal c_reprincipal = childPlan.getReprincipal();
		if(null == c_reprincipal) c_reprincipal = new BigDecimal(0);
		
		BigDecimal principal = eqPlanEntity.getPrincipal();
		if(null == principal) principal = new BigDecimal(0);
		BigDecimal interest = eqPlanEntity.getInterest();
		if(null == interest) interest = new BigDecimal(0);
		BigDecimal mgrAmount = eqPlanEntity.getMgrAmount();
		if(null == mgrAmount) mgrAmount = new BigDecimal(0);
		BigDecimal totalAmount = eqPlanEntity.getTotalAmount();
		if(null == totalAmount) totalAmount = new BigDecimal(0);
		BigDecimal reprincipal = eqPlanEntity.getReprincipal();
		if(null == reprincipal) reprincipal = new BigDecimal(0);
		
		double d_principal = c_principal.doubleValue()+principal.doubleValue();
		totalPrincipal += d_principal;
		
		double d_interest = c_interest.doubleValue()+interest.doubleValue();
		double d_mgrAmount = c_mgrAmount.doubleValue()+mgrAmount.doubleValue();
		double d_totalAmount = c_totalAmount.doubleValue()+totalAmount.doubleValue();
		double d_reprincipal = c_reprincipal.doubleValue()+reprincipal.doubleValue();
		eqPlanEntity.setPrincipal(StringHandler.Round2BigDecimal(d_principal, 2));
		
		eqPlanEntity.setInterest(StringHandler.Round2BigDecimal(d_interest,2));
		eqPlanEntity.setMgrAmount(StringHandler.Round2BigDecimal(d_mgrAmount,2));
		eqPlanEntity.setTotalAmount(StringHandler.Round2BigDecimal(d_totalAmount,2));
		eqPlanEntity.setReprincipal(StringHandler.Round2BigDecimal(d_reprincipal,2));
		return totalPrincipal;
	}
	
	/**
	 * 获取还款计划总表列表
	 * @return
	 */
	public List<PlanEntity> getPlanList() {
		return planList;
	}

	public void setPlanList(List<PlanEntity> planList) {
		this.planList = planList;
	}

	/**
	 * 调整当期利率 并返回上一期贷款余额
	 * @param reviseEntity 要调整的当期还款计划实体
	 * @return 返回上一期贷款余额
	 * @throws ServiceException 
	 */
	protected double doCurrRate(ChildPlanEntity reviseEntity) throws ServiceException {
		Date currDate = reviseEntity.getXpayDate();
		Date prevDate = DateUtil.minusMonthToDate(currDate, 1);
		if(null == cycledate) throw new ServiceException("基准利率调整日期为空，无法进行还款计划利息调整！");
		BigDecimal reprincipal = reviseEntity.getReprincipal();
		double preBamount = (null == reprincipal) ? 0d : reprincipal.doubleValue();	//默认上期贷款余额 == 本期贷款余额
		if(cycledate.after(prevDate) && cycledate.before(currDate)){
			Double rate = getRate(RateType.YEAR_RATE,false);
			int prevDays = DateUtil.minusDateToDays(cycledate, prevDate);
			int currDays = DateUtil.minusDateToDays(currDate, prevDate);
			//上期贷款余额
			BigDecimal principal = reviseEntity.getPrincipal();
			double selfAmount = (null == principal) ? 0d : principal.doubleValue();
			preBamount = selfAmount+preBamount;
			double rateamount = (preBamount*(prevDays*oldrate + currDays*rate))/360;
			rateamount = StringHandler.Round(rateamount, 2);
			reviseEntity.setInterest(new BigDecimal(rateamount));
			reviseEntity.setRemark(DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT, new Date())+"进行了利率调整," +
					"调整之前的贷款利率为:"+(oldrate*100)+"%,当前新利率为："+(rate*100)+"%");
			reviseEntity.setModifier(SysConstant.ADMIN_ID);	
			reviseEntity.setModifytime(new Date());
		}
		return preBamount;
	}
	
	/**
	 * 根据公式获取公式计算结果.
	 * @param formulaParams 公式参数
	 * @param formulaType	公式类型
	 * @return 返回公式计算的结果
	 * @throws ServiceException
	 */
	protected Object getResultByFormula(Map<String,Object> formulaParams,FormulaType formulaType) throws ServiceException {
		String formulaName = null;
		String formula = null;
		String rateParams = null;
		switch (formulaType) {
		case RATE_FORMULA:{/*当期应还利息公式*/
			formulaName = "当期应还利息";
			formula = payTypeEntity.getRateFormula();
			rateParams = payTypeEntity.getRateParams();
			break;
		}case AMOUNT_FORMULA:{/*当期应还本金公式*/
			formulaName = "当期应还本金";
			formula = payTypeEntity.getAmountFormula();
			rateParams = payTypeEntity.getAmountParams();
			break;
		}case TOTALAMOUNT_FORMULA:{/*当期还本付息公式*/
			formulaName = "当期还本付息";
			formula = payTypeEntity.getRaFormula();
			rateParams = payTypeEntity.getRaParams();
			break;
		}default:
			break;
		}
		String[] paramsArr = rateParams.split(",");
		HashMap<String,Object> expParams = new HashMap<String, Object>();
		for(String field : paramsArr){
			if(!formulaParams.containsKey(field)) throw new ServiceException("在调用计算\""+formulaName+"\"的公式时，找不到该公式所需的字段:\""+field+"\" ");
			expParams.put(field, formulaParams.get(field));
		}
		Object result = FormulaUtil.parseExpression(formula, expParams);
		return result;
	}
	
	/**
	 * 設置还款方式实体数据
	 * @param payTypeEntity 还款方式实体
	 */
	public void setPayTypeEntity(PayTypeEntity payTypeEntity) {
		this.payTypeEntity = payTypeEntity;
	}

	/**
	 * 根据利率类型返回相对应的利率
	 * @param type	利率类型，参考RateType 枚举对象
	 * @param isMgrRate 是否取管理费利率   true : 是, false : 否
	 * @return
	 */
	public double getRate(RateType type,boolean isMgrRate){
		double rateVal = isMgrRate ? loanContractEntity.getMrate() : loanContractEntity.getRate();
		double rate = rateVal/SysConstant.PERCENT_DENOMINATOR;
		int rateType = loanContractEntity.getRateType();
		switch (type) {
		case YEAR_RATE:{
			if(rateType == BussStateConstant.RATETYPE_1){//月利率
				rate = rate * 12;
			}else if(rateType == BussStateConstant.RATETYPE_2){//日利率
				rate = rate * 360;
			}
			break;
		}case MONTH_RATE:{
			if(rateType == BussStateConstant.RATETYPE_2){//日利率
				rate = rate * 30;
			}else if(rateType == BussStateConstant.RATETYPE_3){//年利率
				rate = rate / 12;
			} 
			break;
		}case DAY_RATE:{
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
	 * 获取利息金额
	 * @return
	 * @throws ServiceException 
	 */
	protected double getRateAmount(Map<String,Object> formulaParams,FormulaType formulaType) throws ServiceException{
		double rateAmount = 0d;
		Object result = getResultByFormula(formulaParams,formulaType);
		if(!StringHandler.isValidObj(result)) result = "0";
		rateAmount = Double.parseDouble(result.toString());
		//if(rateAmount > 0) rateAmount = StringHandler.Round(rateAmount, 2);
		return rateAmount;
	}
	
	/**
	 * 获取第一期的利息和管理费
	 * @param paydate 第一期的还款日
	 * @param formulaParams 还款公式参数
	 * @return 返回利息和管理费数组
	 * @throws ServiceException
	 */
	public double[] getFirstRateAndMgrAmounts(Date paydate,Map<String,Object> formulaParams) throws ServiceException{
		return getFirstRateAndMgrAmounts(paydate, formulaParams, false);
	}

	/**
	 * 获取第一期的利息和管理费
	 * @param paydate 第一期的还款日
	 * @param formulaParams 还款公式参数
	 * @param isAddOneDay 当是最后一期，且按天算时。是否，要把截止日那一天也算一天利息和管理费
	 * @return 返回利息和管理费数组
	 * @throws ServiceException
	 */
	public double[] getFirstRateAndMgrAmounts(Date paydate,Map<String,Object> formulaParams,boolean isAddOneDay) throws ServiceException{
		int isadvance = loanContractEntity.getIsadvance();
		/*如果预收息，则要拿第一期的下一期来算两个的区间利息*/
		Date nextDate = getNextDate(isadvance);
		double rate = 0d;
		double mrate = 0;
		int rateType = loanContractEntity.getRateType();
		if(rateType == BussStateConstant.RATETYPE_2){
			 int days = DateUtil.calculateLimitDate(realDate, nextDate, DateUtil.DAY);
			 rate = getRate(RateType.DAY_RATE,false);
			 rate = rate * days;
			 mrate = getRate(RateType.DAY_RATE,true);
			 mrate = mrate * days;
		}else{
			 int setdayType = loanContractEntity.getSetdayType();
			 if(setdayType == BussStateConstant.LOANCONTRACT_SETDAYTYPE_1 && null == bussType){/*以实际放款日作结算日时,且不是随借随还变动还款计划时，按月算息*/
				 rate = getRate(RateType.MONTH_RATE,false);
				 mrate = getRate(RateType.MONTH_RATE,true);
			 }else{
				 int days = DateUtil.calculateLimitDate(realDate, nextDate, DateUtil.DAY);
				 if(isAddOneDay) days++;
				 rate = getRate(RateType.DAY_RATE,false);
				 rate = rate * days;
				 mrate = getRate(RateType.DAY_RATE,true);
				 mrate = mrate * days;
			 }
		}
		return getImAmountByRate(formulaParams, rate, mrate);
	}
	
	private Date getNextDate(int isadvance){
		Date nextDate = null;
		Set<Integer> phasesSet = phasesMap.keySet();
		Integer next_phases = null;
		int breakIndex = 0;
		if(isadvance == BussStateConstant.ISADVANCE_1){
			breakIndex = 1;
		}else{
			breakIndex = 0;
		}
		int i = 0;
		for(Integer _phases : phasesSet){
			if(i == breakIndex){
				next_phases = _phases;
				break;
			}
			i++;
		}
		nextDate =  phasesMap.get(next_phases);
		return nextDate;
	}
	
	/**
	 * 根据贷款利率、管理费率获了利息和管理费金额
	 * @param formulaParams	公式参数对象
	 * @param rate	贷款利率
	 * @param mrate	管理费率
	 * @return
	 * @throws ServiceException
	 */
	public double[] getImAmountByRate(Map<String, Object> formulaParams,
			double rate, double mrate) throws ServiceException {
		formulaParams.put("rate", rate);
		double rateamount = getRateAmount(formulaParams,FormulaType.RATE_FORMULA);
		
		double mgramount = 0d;
		int mrgtype = null == loanContractEntity.getMgrtype() ? 0 : loanContractEntity.getMgrtype();
		if(mrgtype == BussStateConstant.MGRTYPE_1 && mrate>0){/*收管理费的情况下*/
			formulaParams.put("rate", mrate);
			mgramount = getRateAmount(formulaParams,FormulaType.RATE_FORMULA);
		}
		return new double[]{rateamount,mgramount};
	}
	
	/**
	 * 获取中间期的利息和管理费
	 * @param paydate 当前还款日期
	 * @param prevOrNextPaydate 当前还款日的上一个还款日或下一还款日
	 * @param preOrNextIndex 上一个或下一个的索引偏移值
	 * @param formulaParams 公式所需的参数
	 * @return  返回利息和管理费数组
	 * @throws ServiceException
	 */
	public double[] getCenterRateAndMgrAmounts(Date paydate,Date prevOrNextPaydate,int preOrNextIndex,Map<String,Object> formulaParams) throws ServiceException{
		double rate = 0d;
		double mrate = 0d;
		if(loanContractEntity.getRateType().intValue() == BussStateConstant.RATETYPE_2){
			int days = (preOrNextIndex == 1) ? DateUtil.calculateLimitDate(paydate, prevOrNextPaydate, DateUtil.DAY)
					: DateUtil.calculateLimitDate(prevOrNextPaydate, paydate, DateUtil.DAY);
			rate = getRate(RateType.DAY_RATE, false);
			mrate = getRate(RateType.DAY_RATE,true);
			rate *= days;
			mrate*= days;
		}else{
			rate = getRate(RateType.MONTH_RATE, false);
			mrate = getRate(RateType.MONTH_RATE,true);
		}
		/*
		formulaParams.put("rate", rate);
		double rateamount = getRateAmount(formulaParams,FormulaType.RATE_FORMULA);
		formulaParams.put("rate", mrate);
		double mgramount = getRateAmount(formulaParams,FormulaType.RATE_FORMULA);
		return new double[]{rateamount,mgramount};*/
		return getImAmountByRate(formulaParams, rate, mrate);
	}
	

	/**
	 * 获得实际期数
	 * @param isPrev	是否取最后一期的上一期	[true:取最后一期的前一期,false或为null:最后一期]
	 */
	public Date getLastDate(boolean isPrev){
		Set<Integer> phasesSet = phasesMap.keySet();
		Integer[] phasesArr = new Integer[0];
		phasesArr = phasesSet.toArray(phasesArr);
		int index = phasesArr.length-1;
		if(isPrev) index--;
		Integer realPhases = phasesArr[index];
		Date lastDate = phasesMap.get(realPhases);
		return lastDate;
	}
	
	/**
	 * 获得指定期数对应的日期
	 * @param index	指定索引处的期数
	 */
	public Date getDateByPhases(int index){
		Set<Integer> phasesSet = phasesMap.keySet();
		Integer[] phasesArr = new Integer[0];
		phasesArr = phasesSet.toArray(phasesArr);
		Integer realPhases = phasesArr[index];
		Date lastDate = phasesMap.get(realPhases);
		return lastDate;
	}
	
	/**
	 * 获取指定的期数值
	 * @param index	期数索引
	 * @return	获取期数值
	 */
	public Integer getPhases(int index){
		Set<Integer> phasesSet = phasesMap.keySet();
		Integer[] phasesArr = new Integer[0];
		phasesArr = phasesSet.toArray(phasesArr);
		Integer realPhases = phasesArr[index];
		return realPhases;
	}
	
	
	/**
	 * 利率类型
	 * @author chengmingwei
	 *
	 */
	public enum RateType{
		/**
		 * 年利率
		 */
		YEAR_RATE,
		/**
		 * 季利率
		 */
		SEASON_RATE,
		/**
		 * 月利率
		 */
		MONTH_RATE,
		/**
		 * 日利率
		 */
		DAY_RATE
	}
	
	/**
	 * 公式类型
	 * @author chengmingwei
	 *
	 */
	public enum FormulaType{
		/**
		 * 利息公式
		 */
		RATE_FORMULA,
		/**
		 * 还本公式
		 */
		AMOUNT_FORMULA,
		/**
		 * 还款公式(本息合计)
		 */
		TOTALAMOUNT_FORMULA
	}
	
	/**
	 * 控制器类型
	 * @author chengmingwei
	 *	
	 */
	public enum ControlerType{
		/**
		 * 利率调整
		 */
		RATE_CHANGE,
		/**
		 * 贷款余额调整[随借随还、提前还款]
		 */
		TOTALAMOUNT_CHANGE
	}
}
