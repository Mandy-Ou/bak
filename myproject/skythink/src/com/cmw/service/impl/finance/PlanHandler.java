package com.cmw.service.impl.finance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.dao.inter.sys.CommonDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.CasualRecordsEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.CasualRecordsService;
import com.cmw.service.inter.finance.LoanContractService;

/**
 * 还款计划处理辅助类  Service实现类
 * @author 程明卫
 * @date 2014-02-06T00:00:00
 */
@Description(remark="放款单业务实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Service("planHandler")
public class PlanHandler {
	@Autowired
	private CommonDaoInter commonDao;
	
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	@Resource(name="applyService")
	private ApplyService applyService;
	@Resource(name="casualRecordsService")
	private CasualRecordsService casualRecordsService;
	@Resource(name="amountRecordsService")
	private AmountRecordsService amountRecordsService;
	
	/**
	 * 重新设置金额合计
	 * @param planEntity
	 */
	public void setTotalAmountByPlan(PlanEntity planEntity){
		BigDecimal principal = planEntity.getPrincipal();
		BigDecimal interest = planEntity.getInterest();
		BigDecimal mgrAmount = planEntity.getMgrAmount();
		BigDecimal yprincipal = planEntity.getYprincipal();
		BigDecimal yinterest = planEntity.getYinterest();
		BigDecimal ymgrAmount = planEntity.getYmgrAmount();
		
		BigDecimal totalAmount = BigDecimalHandler.add2BigDecimal(2, principal,interest, mgrAmount);
		planEntity.setTotalAmount(totalAmount);
		totalAmount = BigDecimalHandler.add2BigDecimal(2, totalAmount, planEntity.getPenAmount(),planEntity.getDelAmount());
		
		/* 返还利息、管理费、罚息字段信息  CODE START */
		BigDecimal riamount = planEntity.getRiamount();
		BigDecimal rmamount = planEntity.getRmamount();
		BigDecimal rpamount = planEntity.getRpamount();
		BigDecimal rdamount = planEntity.getRdamount();
		if(null == riamount) riamount = new BigDecimal("0");
		if(null == rmamount) rmamount = new BigDecimal("0");
		if(null == rpamount) rpamount = new BigDecimal("0");
		if(null == rdamount) rdamount = new BigDecimal("0");
		
		/* 豁免利息、管理费、罚息字段信息  CODE START */
		BigDecimal trinterAmount = planEntity.getTrinterAmount();
		BigDecimal trmgrAmount = planEntity.getTrmgrAmount();
		BigDecimal trpenAmount = planEntity.getTrpenAmount();
		BigDecimal trdelAmount = planEntity.getTrdelAmount();
		
		if(null == trinterAmount) trinterAmount = new BigDecimal("0");
		if(null == trmgrAmount) trmgrAmount = new BigDecimal("0");
		if(null == trpenAmount) trpenAmount = new BigDecimal("0");
		if(null == trdelAmount) trdelAmount = new BigDecimal("0");
		
		boolean changeState = false;
		boolean mgr_changeState = false;
		BigDecimal piAmount = BigDecimalHandler.add2BigDecimal(principal, interest);
		//piAmount = BigDecimalHandler.sub2BigDecimal(piAmount, t_ramount, 2);
		BigDecimal ypiAmount = BigDecimalHandler.add2BigDecimal(yprincipal, yinterest,trinterAmount);  //实收本息 = 实收本息 + 豁免利息
		ypiAmount = BigDecimalHandler.sub2BigDecimal(ypiAmount, riamount, 2); //实收本息 = 实收本息 - 返还利息
		double result = BigDecimalHandler.sub(ypiAmount, piAmount);
		if(result >= 0){
			planEntity.setPistatus(BussStateConstant.PLAN_PISTATUS_2);
			mgr_changeState = true;
		}
		ymgrAmount = BigDecimalHandler.add2BigDecimal(ymgrAmount,trmgrAmount);
		ymgrAmount = BigDecimalHandler.sub2BigDecimal(ymgrAmount, rmamount, 2);
		result = BigDecimalHandler.sub(ymgrAmount, mgrAmount);
		if(result >= 0){
			planEntity.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_2);
			mgr_changeState = true;
		}
		
		BigDecimal ytotalAmount = BigDecimalHandler.add2BigDecimal(2, yprincipal,
				yinterest, ymgrAmount,planEntity.getYpenAmount(),planEntity.getYdelAmount());
		/* 实收合计 = 实收本、利息、管理费、罚息、滞纳金之和 - 返还利息、管理费、罚息、滞纳金之和 */
		BigDecimal t_ramount = new BigDecimal("0");
		t_ramount = BigDecimalHandler.add2BigDecimal(riamount, rmamount, rpamount, rdamount);
		ytotalAmount = BigDecimalHandler.sub2BigDecimal(ytotalAmount, t_ramount, 2);
		planEntity.setYtotalAmount(ytotalAmount);
		/* 实收合计 = 实收合计 + 豁免利息、管理费、罚息、滞纳金之和 */
		ytotalAmount = BigDecimalHandler.add2BigDecimal(2, ytotalAmount, trinterAmount,trmgrAmount,trpenAmount,trdelAmount);
		result = BigDecimalHandler.sub(ytotalAmount, totalAmount);
		if(result >= 0){
			planEntity.setPistatus(BussStateConstant.PLAN_PISTATUS_2);
			planEntity.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_2);
			planEntity.setStatus(BussStateConstant.PLAN_STATUS_2);
			return;
		}
		if(ytotalAmount.doubleValue() > 0){/*部分收款*/
			if(!changeState) planEntity.setPistatus(BussStateConstant.PLAN_PISTATUS_1);
			if(!mgr_changeState) planEntity.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_1);
			planEntity.setStatus(BussStateConstant.PLAN_STATUS_1);
		}
	}
	
	/**
	 * 获取豁免、返还计算结果
	 * @param rat	实收豁免或返还的金额
	 * @param amt	未收的金额
	 * @return	返回 Double 数组。[0] -> 实收豁免 - 未收后的剩余金额， [1] -> 未收金额
	 */
	public double[] getAmountResult(double rat, double amt){
		double[] amountArr = new double[2];
		double r_rat = rat - amt;
		if(r_rat >= 0){
			rat = r_rat;
			amountArr[0] = rat;
			amountArr[1] = amt;
		}else{
			if(rat > 0){
				amountArr[1] = rat;
			}
			rat = 0d;
			amountArr[0] = rat;
		}
		return amountArr;
	}
	
	
	/**
	 * 获取变更了状态和还款期数的贷款申请单对象
	 * @param planList	还款计划列表(所有还款计划表)
	 * @param user	当前用户	
	 * @return
	 * @throws ServiceException
	 */
	public ApplyEntity getApplyEntity(List<PlanEntity> planList,UserEntity user) throws ServiceException {
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
		if(status.intValue() == BussStateConstant.PLAN_STATUS_2 ||
		   status.intValue() == BussStateConstant.PLAN_STATUS_3	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_6	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_7	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_8	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_9	||
		   status.intValue() == BussStateConstant.PLAN_STATUS_10){	/*最后一期且结清*/
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
	 * 获取贷款申请单总期数
	 * @param planList	还款计划列表(所有还款计划表)
	 * @param user	当前用户	
	 * @return
	 * @throws ServiceException
	 */
	public void updateTotalPhases(Long contractId,UserEntity user) throws ServiceException {
		LoanContractEntity contractEntity = loanContractService.getEntity(contractId);
		ApplyEntity applyEntity = applyService.getEntity(contractEntity.getFormId());
		Integer totalPhases = null;
		String hql = "select count(id) as totalPhases from PlanEntity A where A.isenabled='"+SysConstant.OPTION_ENABLED+"' and contractId='"+contractId+"' ";
		DataTable dt = null;
		try {
			dt = commonDao.getDatasByHql(hql, "totalPhases");
			Long _totalPhases = dt.getLong("totalPhases");
			if(null != _totalPhases && _totalPhases.longValue() > 0) totalPhases = _totalPhases.intValue();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		if(null == totalPhases || totalPhases.intValue() <=0 ) return;
		applyEntity.setTotalPhases(totalPhases);
		BeanUtil.setModifyInfo(user, applyEntity);
		applyService.updateEntity(applyEntity);
	}
	
	/**
	 * 创建豁免/返还的实收金额记录
	 * @param data	实收金额数据 [rat,mat,pat,dat,rectDate,contractId,invoceId,bussTag,accountId]
	 * @param user 当前用户
	 * @return	返回创建好的实收金额对象
	 */
	public AmountRecordsEntity createAmountRecords(SHashMap<String, Object> data, UserEntity user){
		BigDecimal rat = data.getvalAsBig("rat");
		if(null == rat) rat = new BigDecimal("0");
		
		BigDecimal mat = data.getvalAsBig("mat");
		if(null == mat) mat = new BigDecimal("0");
		
		BigDecimal pat = data.getvalAsBig("pat");
		if(null == pat) pat = new BigDecimal("0");
		
		BigDecimal dat = data.getvalAsBig("dat");
		if(null == dat) dat = new BigDecimal("0");
		
		BigDecimal tat = BigDecimalHandler.add2BigDecimal(2, rat, mat, pat, dat);
		
		Date rectDate = (Date)data.getvalAsObj("rectDate");
		if(rat.doubleValue() == 0 && mat.doubleValue() == 0 &&
		  pat.doubleValue() == 0 && dat.doubleValue() == 0) return null;
		Long contractId = data.getvalAsLng("contractId");
		Long invoceId = data.getvalAsLng("invoceId");
		Integer bussTag = data.getvalAsInt("bussTag");
		Long accountId = data.getvalAsLng("accountId");
		AmountRecordsEntity obj = new AmountRecordsEntity();
		obj.setContractId(contractId);
		obj.setInvoceId(invoceId);
		obj.setRat(rat);
		obj.setMat(mat);
		obj.setPat(pat);
		obj.setDat(dat);
		obj.setTat(tat);
		obj.setRectDate(rectDate);
		obj.setBussTag(bussTag);
		obj.setAccountId(accountId);
		BeanUtil.setCreateInfo(user, obj);
		return obj;
	}
	
	/**
	 * 保存豁免或返还的实收金额数据和随借随还记录
	 * @param arList
	 * @param bussTag 业务标识
	 * @throws ServiceException 
	 */
	public String saveAmountRecords(List<AmountRecordsEntity> arList, Integer bussTag, UserEntity user) throws ServiceException{
		if(null == arList || arList.size() == 0) return null;
		BigDecimal t_rat = new BigDecimal("0");
		BigDecimal t_mat = new BigDecimal("0");
		BigDecimal t_pat = new BigDecimal("0");
		BigDecimal t_dat = new BigDecimal("0");
		BigDecimal t_tat = new BigDecimal("0");
		AmountRecordsEntity f_arObj = arList.get(0);
		Long contractId = f_arObj.getContractId();
		Date rectDate = f_arObj.getRectDate();
		Long accountId = f_arObj.getAccountId();
		for(AmountRecordsEntity arObj : arList){
			t_rat = BigDecimalHandler.add2BigDecimal(t_rat, arObj.getRat());
			t_mat = BigDecimalHandler.add2BigDecimal(t_mat, arObj.getMat());
			t_pat = BigDecimalHandler.add2BigDecimal(t_pat, arObj.getPat());
			t_dat = BigDecimalHandler.add2BigDecimal(t_dat, arObj.getDat());
			t_tat = BigDecimalHandler.add2BigDecimal(t_tat, arObj.getTat());
		}
		
		CasualRecordsEntity crObj = new CasualRecordsEntity();
		crObj.setBussTag(bussTag);
		crObj.setRat(BigDecimalHandler.roundToBigDecimal(t_rat, 2));
		crObj.setMat(BigDecimalHandler.roundToBigDecimal(t_mat, 2));
		crObj.setPat(BigDecimalHandler.roundToBigDecimal(t_pat, 2));
		crObj.setDat(BigDecimalHandler.roundToBigDecimal(t_dat, 2));
		crObj.setTat(BigDecimalHandler.roundToBigDecimal(t_tat, 2));
		crObj.setRectDate(rectDate);
		crObj.setAccountId(accountId);
		crObj.setContractId(contractId);
		BeanUtil.setCreateInfo(user, crObj);
		casualRecordsService.saveEntity(crObj);
		Long casualId = crObj.getId();
		for(AmountRecordsEntity arObj : arList){
			arObj.setCasualId(casualId);
		}
		amountRecordsService.batchSaveEntitys(arList);
		return crObj.getId().toString();
	}
	
}
