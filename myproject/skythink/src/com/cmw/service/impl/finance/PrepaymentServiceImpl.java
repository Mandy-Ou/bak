package com.cmw.service.impl.finance;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.PrepaymentDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.finance.PrepaymentEntity;
import com.cmw.entity.finance.PrepaymentRecordsEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.finance.PrepaymentRecordsService;
import com.cmw.service.inter.finance.PrepaymentService;
import com.cmw.service.inter.fininter.AmountLogService;


/**
 * 提前还款申请  Service实现类
 * @author 程明卫
 * @date 2013-09-11T00:00:00
 */
@Description(remark="提前还款申请业务实现类",createDate="2013-09-11T00:00:00",author="程明卫")
@Service("prepaymentService")
public class PrepaymentServiceImpl extends AbsService<PrepaymentEntity, Long> implements  PrepaymentService {
	@Autowired
	private PrepaymentDaoInter prepaymentDao;
	
	@Resource(name="planService")
	private PlanService planService;

	@Resource(name="amountRecordsService")
	private AmountRecordsService amountRecordsService;
	
	@Resource(name="prepaymentRecordsService")
	private PrepaymentRecordsService prepaymentRecordsService;
	
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="applyService")
	private ApplyService applyService;
	
	@Resource(name="fundsWaterService")
	private FundsWaterService fundsWaterService;
	
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	
	@Override
	public GenericDaoInter<PrepaymentEntity, Long> getDao() {
		return prepaymentDao;
	}
	
	 /**
	  * 获取提前还款计算的数据
	  * @param params	参数[contractId,xpayDate]
	  * @return
	  * @throws ServiceException
	  */
	@Override
	public DataTable getCalculateDt(SHashMap<String, Object> params)throws ServiceException{
		try{
			return prepaymentDao.getCalculateDt(params);
		}catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.PrepaymentService#detail(java.lang.Long)
	 */
	@Override
	public DataTable detail(Long id) throws ServiceException{
		try {
			return prepaymentDao.detail(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 从还款计划表中，获取提前还款应收金额
	 * @param params	过滤参数
	 * @return	
	 * @throws ServiceException
	 */
	@Override
	public DataTable getZamountDt(SHashMap<String, Object> params) throws ServiceException{
		try {
			return prepaymentDao.getZamountDt(params);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.cmw.core.base.service.AbsService#doComplexBusss(com.cmw.core.util.SHashMap)
	 */
	@Override
	@Transactional
	public Object doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		Long sysId = complexData.getvalAsLng("sysId");
		String vtempCode = complexData.getvalAsStr("vtempCode");
		
		//---> step 1 :将提前还款申请单更新为结清<---//
		PrepaymentEntity prepaymentEntity = this.savePrepayment(complexData);
		
		//---> step 2 :更新plan状态并保存收款记录<---//
		PrepaymentRecordsEntity prepaymentRecordsEntity = upldatePlans(prepaymentEntity, user);
		Long contractId = prepaymentEntity.getContractId();
		
		//---> step 3 : 更新 fc_Apply 表中的 paydPhases=totalPhases，并将 state 更新为”6“（结清）<---//
		applyUpdateState(contractId, user);
		
		//step 4 :保存财务实收金额日志数据生成财务凭证
		Map<AmountLogEntity, DataTable> logDataMap = generateVouchers(user, sysId, vtempCode, prepaymentRecordsEntity);
		// step 5 :  根据 accountId 往 fc_FundsWater(资金流水表)中插入一笔收款流水记录
		fundsWaterService.calculate(logDataMap,user);
		return logDataMap;
	}

	/**
	 * 保存提前还款数据
	 * @param complexData
	 * @throws ServiceException
	 */
	private PrepaymentEntity savePrepayment(SHashMap<String, Object> complexData) throws ServiceException{
		Long id = complexData.getvalAsLng("id");//提前还款申请单ID
		String rectDate = complexData.getvalAsStr("rectDate");
		Long accountId = complexData.getvalAsLng("accountId");
		Double totalAmount = complexData.getvalAsDob("totalAmount");
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		if(StringHandler.isValidObj(id)) new ServiceException(ServiceException.ID_IS_NULL);
		PrepaymentEntity prepaymentEntity = this.getEntity(id);
		Date reDate = new Date();
		if(StringHandler.isValidStr(rectDate)){
			reDate = DateUtil.dateFormat(rectDate);
		}
		prepaymentEntity.setPredDate(reDate);
		prepaymentEntity.setAccountId(accountId);
		BigDecimal freeamount = prepaymentEntity.getFreeamount();
		prepaymentEntity.setYfreeamount(freeamount);
		Integer xstatus = prepaymentEntity.getXstatus();
		if(null != xstatus && xstatus.intValue() == BussStateConstant.PREPAYMENT_XSTATUS_0){
			prepaymentEntity.setTotalAmount(new BigDecimal(totalAmount));
		}
		prepaymentEntity.setYtotalAmount(prepaymentEntity.getTotalAmount());
		prepaymentEntity.setXstatus(BussStateConstant.PREPAYMENT_XSTATUS_2);
		BeanUtil.setModifyInfo(user, prepaymentEntity);
		this.saveOrUpdateEntity(prepaymentEntity);
		return prepaymentEntity;
	}
	
	/**step :5生成财务凭证
	 * @param isVoucher
	 * @param user
	 * @param sysId
	 * @param vtempCode
	 * @param amountRecordsEntitys
	 * @return
	 * @throws ServiceException
	 */
	private Map<AmountLogEntity, DataTable> generateVouchers(UserEntity user, Long sysId, String vtempCode,
			PrepaymentRecordsEntity prepaymentRecordsEntity)
			throws ServiceException {
		String ids = prepaymentRecordsEntity.getId().toString();
		SHashMap<String, Object> logDataparams = new SHashMap<String, Object>();
		logDataparams.put("ids", ids);
		logDataparams.put("sysId", sysId);
		logDataparams.put("vtempCode", vtempCode);
		logDataparams.put(SysConstant.USER_KEY, user);
		logDataparams.put("bussTag", BussStateConstant.AMOUNTLOG_BUSSTAG_9);
		Map<AmountLogEntity,DataTable> logDataMap = amountLogService.saveByBussTag(logDataparams);
		return logDataMap;
	}


	/**---> step 3 : 更新 fc_Apply 表中的 paydPhases=totalPhases，并将 state 更新为”6“（结清）<---//
	 * @param contractId 借款合同ID
	 * @param user	当前用户
	 * @throws ServiceException
	 */
	private void applyUpdateState(Long contractId,UserEntity user) throws ServiceException {
		LoanContractEntity loanContractEntity = loanContractService.getEntity(contractId);
		Long formId = null;
		if(null == loanContractEntity) return;
		 formId = loanContractEntity.getFormId();
		if(formId == null) return;
		ApplyEntity applyEntity = applyService.getEntity(formId);
		if(null == applyEntity) return;
		applyEntity.setPaydPhases(applyEntity.getTotalPhases());
		applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_6);
		applyEntity.setRemark("提前结清，收款人:"+user.getEmpName()+"!");
		BeanUtil.setModifyInfo(user, applyEntity);
		applyService.saveOrUpdateEntity(applyEntity);
	}

	/**
	 * 保存实收提前还款金额日志
	 * @param prepaymentObj
	 * @param recordsList
	 * @param user
	 * @return
	 * @throws ServiceException
	 */
	private PrepaymentRecordsEntity savePrepaymentAmountRecords(PrepaymentEntity prepaymentObj,List<AmountRecordsEntity> recordsList,UserEntity user) throws ServiceException {
		PrepaymentRecordsEntity precordEntity = new PrepaymentRecordsEntity();
		BeanUtil.setCreateInfo(user, precordEntity);
		Long contractId = prepaymentObj.getContractId();
		Long invoceId = prepaymentObj.getId();
		BigDecimal fat = prepaymentObj.getYfreeamount();
		Long accountId = prepaymentObj.getAccountId();
		Date rectDate = prepaymentObj.getPredDate();
		precordEntity.setContractId(contractId);
		precordEntity.setInvoceId(invoceId);
		precordEntity.setBussTag(BussStateConstant.PREPAYMENTRECORDS_BUSSTAG_0);
		precordEntity.setAccountId(accountId);
		precordEntity.setRectDate(rectDate);
		BigDecimal b_cat = new BigDecimal("0");
		BigDecimal b_rat = new BigDecimal("0");
		BigDecimal b_mat = new BigDecimal("0");
		BigDecimal b_pat = new BigDecimal("0");
		BigDecimal b_dat = new BigDecimal("0");
		BigDecimal b_tat = new BigDecimal("0");
		
		for(AmountRecordsEntity record : recordsList){
			BigDecimal r_cat = record.getCat();
			BigDecimal r_rat = record.getRat();
			BigDecimal r_mat = record.getMat();	
			BigDecimal r_pat = record.getPat();	
			BigDecimal r_dat = record.getDat();	
			BigDecimal r_tat = record.getTat();
			
			if(null != r_cat) b_cat = BigDecimalHandler.add2BigDecimal(b_cat, r_cat);
			if(null != r_rat) b_rat = BigDecimalHandler.add2BigDecimal(b_rat, r_rat);
			if(null != r_mat) b_mat = BigDecimalHandler.add2BigDecimal(b_mat, r_mat);
			if(null != r_pat) b_pat = BigDecimalHandler.add2BigDecimal(b_pat, r_pat);
			if(null != r_dat) b_dat = BigDecimalHandler.add2BigDecimal(b_dat, r_dat);
			if(null != r_tat) b_tat = BigDecimalHandler.add2BigDecimal(b_tat, r_tat);
		}
		b_cat = BigDecimalHandler.roundToBigDecimal(b_cat, 2);
		b_rat = BigDecimalHandler.roundToBigDecimal(b_rat, 2);
		b_mat = BigDecimalHandler.roundToBigDecimal(b_mat, 2);
		b_pat = BigDecimalHandler.roundToBigDecimal(b_pat, 2);
		b_dat = BigDecimalHandler.roundToBigDecimal(b_dat, 2);
		b_tat = BigDecimalHandler.roundToBigDecimal(b_tat, 2);
		precordEntity.setCat(b_cat);
		precordEntity.setRat(b_rat);
		precordEntity.setMat(b_mat);
		precordEntity.setPat(b_pat);
		precordEntity.setDat(b_dat);
		precordEntity.setTat(b_tat);
		precordEntity.setFat(fat);
		prepaymentRecordsService.saveOrUpdateEntity(precordEntity);
		return precordEntity;
	}
	
	/** 
	 *  A.根据 fc_Prepayment 中的 payplanId 将 fc_Plan 表中指定的当期的还款计划的 status更新为”9“（提前结清），
	 *  将后面其它期的 status全部更新为”10“（其它期提前还款），将逾期的还款计划  status全部更新为”2“（结清），
	 *  并将所有已收金额项的金额设为与应收金额项相等。
	 **/
	private PrepaymentRecordsEntity upldatePlans(PrepaymentEntity prepaymentEntity, UserEntity user) throws ServiceException {
		Long payplanId = prepaymentEntity.getPayplanId();
		Long contractId = prepaymentEntity.getContractId();
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("contractId", contractId);
		params.put("isenabled", SqlUtil.LOGIC_NOT_IN + SqlUtil.LOGIC+SysConstant.OPTION_DEL);
		String statusStr = BussStateConstant.PLAN_STATUS_0+","+ BussStateConstant.PLAN_STATUS_1+","
				+ BussStateConstant.PLAN_STATUS_4+","+ BussStateConstant.PLAN_STATUS_5;
		params.put("status", SqlUtil.LOGIC_IN + SqlUtil.LOGIC+statusStr);
		params.put(SqlUtil.ORDERBY_KEY,"asc:id");
		List<PlanEntity> planList = planService.getEntityList(params);
		if(null == planList || planList.size() == 0) throw new ServiceException(ServiceException.PREPAYMENT_BEFORE_FINISH);
		Date rectDate = prepaymentEntity.getPredDate();
		Long accountId = prepaymentEntity.getAccountId();
		BigDecimal fat = prepaymentEntity.getYfreeamount();
		BigDecimal imamount = prepaymentEntity.getImamount();
		List<AmountRecordsEntity> recordsList = new ArrayList<AmountRecordsEntity>();
		for(PlanEntity entity : planList){
			Long planId = entity.getId();
			BigDecimal reprincipal = entity.getReprincipal();
			BigDecimal principal = entity.getPrincipal();
			BigDecimal yprincipal = entity.getYprincipal();
			
			BigDecimal interest = entity.getInterest();
			BigDecimal yinterest = entity.getYinterest();
			BigDecimal trinterAmount = entity.getTrinterAmount();
			
			BigDecimal mgrAmount = entity.getMgrAmount();
			BigDecimal ymgrAmount = entity.getYmgrAmount();
			BigDecimal trmgrAmount = entity.getTrmgrAmount();
			
			BigDecimal penAmount = entity.getPenAmount();
			BigDecimal ypenAmount = entity.getYpenAmount();
			BigDecimal trpenAmount = entity.getTrpenAmount(); 
			
			BigDecimal delAmount = entity.getDelAmount();
			BigDecimal ydelAmount = entity.getYdelAmount();
			BigDecimal trdelAmount = entity.getTrdelAmount();
			
			BigDecimal cat = BigDecimalHandler.sub2BigDecimal(principal, yprincipal);
			BigDecimal rat = BigDecimalHandler.sub2BigDecimal(interest, BigDecimalHandler.add2BigDecimal(yinterest, trinterAmount));
			BigDecimal mat = BigDecimalHandler.sub2BigDecimal(mgrAmount, BigDecimalHandler.add2BigDecimal(ymgrAmount, trmgrAmount));
			BigDecimal pat = BigDecimalHandler.sub2BigDecimal(penAmount, BigDecimalHandler.add2BigDecimal(ypenAmount, trpenAmount));
			BigDecimal dat = BigDecimalHandler.sub2BigDecimal(delAmount, BigDecimalHandler.add2BigDecimal(ydelAmount, trdelAmount));
		
			AmountRecordsEntity amountRecordsEntity = createAmountRecordsEntity(entity, rectDate, accountId, user);
			boolean isSetYamount = false;
			if(planId < payplanId){
				setPlanStatus(entity, null);
				isSetYamount = true;
				fat = new BigDecimal("0");
			}else if(planId == payplanId){
				setPlanStatus(entity, BussStateConstant.PLAN_STATUS_9);
				cat = BigDecimalHandler.add2BigDecimal(cat, reprincipal);
				fat = prepaymentEntity.getYfreeamount();
				rat = BigDecimalHandler.sub2BigDecimal(rat, imamount);
				isSetYamount = true;
			}else{
				setPlanStatus(entity, BussStateConstant.PLAN_STATUS_10);
				fat = new BigDecimal("0");
			}
		
			//*** 设置还款计划表已收项  ***//
			if(isSetYamount){
				setYItems(entity);
				
				/*当是当前及当前以前提前还款时，应记录实收金额记录数据*/
				amountRecordsEntity.setCat(cat);
				amountRecordsEntity.setRat(rat);
				amountRecordsEntity.setMat(mat);
				amountRecordsEntity.setFat(fat);
				amountRecordsEntity.setPat(pat);
				amountRecordsEntity.setDat(dat);
				double d_tat = cat.doubleValue() + rat.doubleValue() + mat.doubleValue() + fat.doubleValue() + pat.doubleValue() + dat.doubleValue();
				BigDecimal tat = BigDecimalHandler.roundToBigDecimal(d_tat, 2);
				amountRecordsEntity.setTat(tat);
				recordsList.add(amountRecordsEntity);
			}
			BeanUtil.setModifyInfo(user, entity);
		}
		planService.batchUpdateEntitys(planList);
		amountRecordsService.batchSaveEntitys(recordsList);
		PrepaymentRecordsEntity prepaymentRecordsEntity = savePrepaymentAmountRecords(prepaymentEntity,recordsList,user);
		return prepaymentRecordsEntity;
	}

	/**
	 * 设置已还金额
	 * @param entity
	 */
	private void setYItems(PlanEntity entity) {
		BigDecimal yprincipal = entity.getPrincipal();
		entity.setYprincipal(yprincipal);
		BigDecimal yinterest = BigDecimalHandler.sub2BigDecimal(entity.getInterest(), entity.getTrinterAmount());
		entity.setYinterest(yinterest);
		BigDecimal ymgrAmount = BigDecimalHandler.sub2BigDecimal(entity.getMgrAmount(), entity.getTrmgrAmount());
		entity.setYmgrAmount(ymgrAmount);
		BigDecimal ypenAmount = BigDecimalHandler.sub2BigDecimal(entity.getPenAmount(), entity.getTrpenAmount());
		entity.setYpenAmount(ypenAmount);
		BigDecimal ydelAmount = BigDecimalHandler.sub2BigDecimal(entity.getDelAmount(), entity.getTrdelAmount());
		entity.setYdelAmount(ydelAmount);
		double d_ytotalAmount = yprincipal.doubleValue() + yinterest.doubleValue() + ymgrAmount.doubleValue() + ypenAmount.doubleValue() + ydelAmount.doubleValue();
		BigDecimal ytotalAmount = BigDecimalHandler.roundToBigDecimal(d_ytotalAmount, 2);
		entity.setYtotalAmount(ytotalAmount);
	}
	
	private AmountRecordsEntity createAmountRecordsEntity(PlanEntity planEntity,Date rectDate, Long accountId,UserEntity user){
		AmountRecordsEntity entity = new AmountRecordsEntity();
		entity.setContractId(planEntity.getContractId());
		entity.setInvoceId(planEntity.getId());
		entity.setBussTag(BussStateConstant.AMOUNTLOG_BUSSTAG_9);
		entity.setRectDate(rectDate);
		entity.setAccountId(accountId);
		BeanUtil.setCreateInfo(user, entity);
		return entity;
	}
	
	
	private void setPlanStatus(PlanEntity entity,Integer custStatus){
		Integer status = entity.getStatus();
		if(null != custStatus){
			status = custStatus;
		}else{
			switch (status.intValue()) {
			case BussStateConstant.PLAN_STATUS_4:
			case BussStateConstant.PLAN_STATUS_5:{
				status = BussStateConstant.PLAN_STATUS_2;
				break;
			}default:
				status = BussStateConstant.PLAN_STATUS_9;
				break;
			}
		}
		entity.setPistatus(BussStateConstant.PLAN_PISTATUS_2);
		entity.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_2);
		entity.setStatus(status);
	}
}
