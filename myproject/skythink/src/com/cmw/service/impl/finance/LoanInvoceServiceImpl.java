package com.cmw.service.impl.finance;


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
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
import com.cmw.dao.inter.finance.LoanInvoceDaoInter;
import com.cmw.dao.inter.sys.CommonDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.CasualRecordsEntity;
import com.cmw.entity.finance.ChildPlanEntity;
import com.cmw.entity.finance.FreeEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.entity.finance.OwnFundsEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.AccountEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.finance.paytype.PayTypeCalculateAbs;
import com.cmw.service.impl.finance.paytype.PayTypeCalculateFactory;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.CasualRecordsService;
import com.cmw.service.inter.finance.ChildPlanService;
import com.cmw.service.inter.finance.FreeService;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.LoanInvoceService;
import com.cmw.service.inter.finance.OwnFundsService;
import com.cmw.service.inter.finance.PayTypeService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.sys.AccountService;


/**
 * 放款单  Service实现类
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="放款单业务实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Service("loanInvoceService")
public class LoanInvoceServiceImpl extends AbsService<LoanInvoceEntity, Long> implements  LoanInvoceService {
	@Autowired
	private LoanInvoceDaoInter loanInvoceDao;
	@Autowired
	private CommonDaoInter commonDao;
	@Resource(name="ownFundsService")
	private OwnFundsService ownFundsService;
	
	@Resource(name="accountService")
	private AccountService accountService;
	
	@Resource(name="freeService")
	private FreeService freeService;
	
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="applyService")
	private ApplyService applyService;
	
	@Resource(name="planService")
	private PlanService planService;
	
	@Resource(name="childPlanService")
	private ChildPlanService childPlanService;
	
	@Resource(name="payTypeService")
	private PayTypeService payTypeService;
	
	@Resource(name="amountRecordsService")
	private AmountRecordsService amountRecordsService;
	
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	
	@Resource(name="fundsWaterService")
	private FundsWaterService fundsWaterService;
	
	@Resource(name="casualRecordsService")
	private CasualRecordsService casualRecordsService;
	
	@Resource(name="planHandler")
	private PlanHandler planHandler;
	
	@Override
	public GenericDaoInter<LoanInvoceEntity, Long> getDao() {
		return loanInvoceDao;
	}

	public void update(Map<String,Object> dataMap) throws ServiceException{
		try {
			loanInvoceDao.update(dataMap);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 保存预收金额
	 */
	@Override
	@Transactional
	public void saveYs(SHashMap<String, Object> map) throws ServiceException {
		UserEntity user = (UserEntity)map.get(SysConstant.USER_KEY);
		String vtempCode = map.getvalAsStr("vtempCode");
		Long sysId = map.getvalAsLng("sysId"); 
		Long  loanInvoceId = map.getvalAsLng("id");
		Long contractId = map.getvalAsLng("contractId");
		Long accountId = map.getvalAsLng("accountId");
		String rectDateStr = map.getvalAsStr("rectDate");
		Date rectDate = DateUtil.dateFormat(rectDateStr);
		LoanContractEntity loanContractEntity = loanContractService.getEntity(contractId);
		Integer mgrtype = null;
		if(loanContractEntity != null){
			mgrtype = loanContractEntity.getMgrtype();
		}
		LoanInvoceEntity loanInvoceEntity = this.getEntity(loanInvoceId);
		if(null == loanInvoceEntity) throw new ServiceException(" loanInvoceEntity is null !");
		loanInvoceEntity.setIsNotys(2);
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("contractId", contractId);
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		params.put(SqlUtil.ORDERBY_KEY,"asc:phases");
		List<PlanEntity> planEntityList = planService.getEntityList(params);
		Integer ysRatMonth = loanInvoceEntity.getYsRatMonth();
		Integer ysMatMonth = loanInvoceEntity.getYsMatMonth();
		double ysRat = loanInvoceEntity.getYsRat().doubleValue();
		double ysMat = loanInvoceEntity.getYsMat().doubleValue();
		if(null == planEntityList || planEntityList.size() == 0) return;
		
		//---> step 1 : 保存实收随借随还<---//
		CasualRecordsEntity casualRecordsEntity = new CasualRecordsEntity();
		casualRecordsEntity.setBussTag(BussStateConstant.CASUALRECORDS_BUSSTAG_0);
		casualRecordsEntity.setContractId(contractId);
		if(ysRat > 0) casualRecordsEntity.setRat(BigDecimalHandler.get(ysRat));
		if(ysMat > 0){
			if(null != mgrtype && mgrtype==BussStateConstant.MGRTYPE_1){
				casualRecordsEntity.setMat(BigDecimalHandler.get(ysMat));
			}else{
				casualRecordsEntity.setFat(BigDecimalHandler.get(ysMat));
			}
		}
		casualRecordsEntity.setTat(BigDecimalHandler.add2BigDecimal(BigDecimalHandler.get(ysMat), BigDecimalHandler.get(ysRat)));
		casualRecordsEntity.setRectDate(rectDate);
		casualRecordsEntity.setAccountId(accountId);
		BeanUtil.setCreateInfo(user, casualRecordsEntity);
		casualRecordsService.saveOrUpdateEntity(casualRecordsEntity);
		Long casualId = casualRecordsEntity.getId();
	
		//---> step 2 : 保存实收金额日志<---//
		List<PlanEntity> newPlanList = new ArrayList<PlanEntity>();
		List<AmountRecordsEntity> arList = new ArrayList<AmountRecordsEntity>();
		
		for(PlanEntity x : planEntityList){
			if(ysRat <= 0d && ysMat <= 0d) break;
			Long planId = x.getId();
			SHashMap<String, Object> data = new SHashMap<String, Object>();
			data.put("contractId", contractId);
			data.put("invoceId", planId);
			data.put("casualId", casualId);
			data.put("accountId", accountId);
			data.put("rectDate", rectDate);
			//contractId,invoceId,casualId,accountId,rectDate,rat,mat
			double[] amoutArr = null;
			double d_tramount = 0;
			if(ysRatMonth != null && ysRatMonth >0 && ysRat>0d){
				BigDecimal ziamount = BigDecimalHandler.sub2BigDecimal(2,x.getInterest(), x.getYinterest(),x.getTrinterAmount());
				BigDecimal riamount = x.getRiamount();
				if(null != riamount && riamount.doubleValue() > 0) ziamount = BigDecimalHandler.add2BigDecimal(ziamount, riamount);
				amoutArr = planHandler.getAmountResult(ysRat, ziamount.doubleValue());
				ysRat = amoutArr[0];
				d_tramount = amoutArr[1];
				if(d_tramount > 0){
					BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
					data.put("rat", b_tramount);
					BigDecimal yinterest = x.getYinterest();
					yinterest = BigDecimalHandler.add2BigDecimal(yinterest, b_tramount, 2);
					x.setYinterest(yinterest);
				}
			}
			if(ysMatMonth!=null && ysMatMonth>0 && ysMat>0d){
				if(mgrtype==BussStateConstant.MGRTYPE_1){
					BigDecimal zmamount = BigDecimalHandler.sub2BigDecimal(2,x.getMgrAmount(), x.getYmgrAmount(),x.getTrmgrAmount());
					BigDecimal rmamount = x.getRmamount();
					if(null != rmamount && rmamount.doubleValue() > 0) zmamount = BigDecimalHandler.add2BigDecimal(zmamount, rmamount);
					amoutArr = planHandler.getAmountResult(ysMat, zmamount.doubleValue());
					ysMat = amoutArr[0];
					d_tramount = amoutArr[1];
					if(d_tramount > 0){
						BigDecimal b_tramount = BigDecimalHandler.get(d_tramount);
						data.put("mat", b_tramount);
						BigDecimal ymgrAmount = x.getYmgrAmount();
						ymgrAmount = BigDecimalHandler.add2BigDecimal(ymgrAmount, b_tramount, 2);
						x.setYmgrAmount(ymgrAmount);
					}
				}else{
					ysMat = 0d;
				}
			}
			x.setLastDate(DateUtil.dateFormat(rectDate));// 
			BigDecimal ytotalAmount = BigDecimalHandler.add2BigDecimal(x.getYprincipal(), x.getYinterest(),x.getYmgrAmount(),x.getYpenAmount(), x.getYdelAmount());
			BigDecimal riamount = x.getRiamount();
			BigDecimal rmamount = x.getRmamount();
			BigDecimal rpamount = x.getRpamount();
			BigDecimal rdamount = x.getRdamount();
			if(null == riamount) riamount = new BigDecimal("0");
			if(null == rmamount) rmamount = new BigDecimal("0");
			if(null == rpamount) rpamount = new BigDecimal("0");
			if(null == rdamount) rdamount = new BigDecimal("0");
			BigDecimal t_ramount = BigDecimalHandler.add2BigDecimal(riamount,rmamount,rpamount,rdamount);
			ytotalAmount = BigDecimalHandler.sub2BigDecimal(ytotalAmount, t_ramount);//实收合计 = 实收-返还合计
			x.setYtotalAmount(ytotalAmount);
			planHandler.setTotalAmountByPlan(x);
			newPlanList.add(x);
			
			//---> step 2 : 保存实收金额日志<---//
			AmountRecordsEntity amountRecordsEntity = createAmountRecords(data,user);
			if(null != amountRecordsEntity) arList.add(amountRecordsEntity);
		}
		if(null != newPlanList && newPlanList.size() > 0) planService.batchUpdateEntitys(newPlanList);
		if(null != arList && arList.size() > 0) amountRecordsService.batchSaveEntitys(arList);
		
		//---> step 3 : 更新贷款申请单状态和已还及总期数等信息<---//
		 ApplyEntity applyEntity = planHandler.getApplyEntity(planEntityList, user);
		 applyService.updateEntity(applyEntity);
		 
		//---> step 4 : 保存实收金额日志<---//
		SHashMap<String, Object> amountParams = new SHashMap<String, Object>();
		amountParams.put("ids", casualId.toString());
		amountParams.put("sysId", sysId);
		amountParams.put("vtempCode",  SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + vtempCode);
		amountParams.put(SysConstant.USER_KEY, user);
		amountParams.put("bussTag", BussStateConstant.AMOUNTLOG_BUSSTAG_12);
		Map<AmountLogEntity,DataTable> logDataAmountMap = amountLogService.saveByBussTag(amountParams);
		
		//----> ste 5 : 保存资金流水 和更新自有资金<---//
		fundsWaterService.calculate(logDataAmountMap,user);
		
	}

	
	/**
	 * 创建实收金额记录
	 * @param data
	 * @return
	 */
	private AmountRecordsEntity createAmountRecords(SHashMap<String, Object> data, UserEntity user){
		BigDecimal rat = data.getvalAsBig("rat");
		if(null == rat) rat = new BigDecimal("0");
		
		BigDecimal mat = data.getvalAsBig("mat");
		if(null == mat) mat = new BigDecimal("0");
		
		BigDecimal tat = BigDecimalHandler.add2BigDecimal(2, rat, mat);
		
		Date rectDate = (Date)data.getvalAsObj("rectDate");
		if(rat.doubleValue() == 0 && mat.doubleValue() == 0) return null;
		Long contractId = data.getvalAsLng("contractId");
		Long invoceId = data.getvalAsLng("invoceId");
		Long casualId = data.getvalAsLng("casualId");
		
		AmountRecordsEntity obj = new AmountRecordsEntity();
		obj.setContractId(contractId);
		obj.setInvoceId(invoceId);
		obj.setCasualId(casualId);
		obj.setRat(rat);
		obj.setMat(mat);
		obj.setTat(tat);
		obj.setRectDate(rectDate);
		obj.setBussTag(BussStateConstant.AMOUNTRECORDS_BUSSTAG_0);
		BeanUtil.setCreateInfo(user, obj);
		return obj;
	}
	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.LoanInvoceService#getNeedYsList(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable getNeedYsList(SHashMap<K, V> params, int offset,
			int pageSize) throws ServiceException {
		try {
			return loanInvoceDao.getNeedYsList(params, offset, pageSize);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * 更新放款单状态
	 */
	
	public void rever(Map<String,Object> dataMap) throws ServiceException{
		try {
			loanInvoceDao.rever(dataMap);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	@Transactional
	public Object doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
		Long id = complexData.getvalAsLng("id");
		String ids = complexData.getvalAsStr("ids");
		Long accountId = complexData.getvalAsLng("accountId");
		String realDate = complexData.getvalAsStr("realDate");
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		
		checkOwnAmounts(complexData);
		
		if(StringHandler.isValidObj(id)){/*单笔放款*/
			LoanInvoceEntity loanEntity = getEntity(id);
			loanEntity.setState(BussStateConstant.LOANINVOCE_STATE_1);
			loanEntity.setAuditState(BussStateConstant.LOANINVOCE_AUDITSTATE_2);
			loanEntity.setAccountId(accountId);
			loanEntity.setRealDate(DateUtil.dateFormat(realDate));
			BeanUtil.setModifyInfo(user, loanEntity);
			saveOrUpdateEntity(loanEntity);
//			FreeEntity freeEntity = makeFreeAmount(loanEntity,user);
//			if(null != freeEntity) freeService.saveEntity(freeEntity);
			updateApplyState(user,loanEntity.getId().toString());
		}else{/*批量放款*/
			if(!StringHandler.isValidStr(ids)) throw new ServiceException("批量放款必须传入 ids 参数!");
			Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put(SysConstant.USER_KEY, user);
			dataMap.put("ids", ids);
			dataMap.put("accountId", accountId);
			dataMap.put("realDate", realDate);
			update(dataMap);
//			makeFreeAmounts(ids,user);
			updateApplyState(user,ids);
		}
		//---> step 2 : 保存实收金额日志<---//
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		if(!StringHandler.isValidStr(ids)) ids = id.toString();
		params.put("ids", ids);
		Long sysId = complexData.getvalAsLng("sysId");
		params.put("sysId", sysId);
		String vtempCode = complexData.getvalAsStr("vtempCode");
		params.put("vtempCode", vtempCode);
		params.put(SysConstant.USER_KEY, user);
		params.put("bussTag", BussStateConstant.AMOUNTLOG_BUSSTAG_0);
		Map<AmountLogEntity,DataTable> logDataMap = amountLogService.saveByBussTag(params);
		//----> ste 3 : 保存资金流水 和更新自有资金<---//
		fundsWaterService.calculate(logDataMap,user);
		return logDataMap;
	}

	/**
	 * 检查自有资金帐户可用余额情况
	 * @throws ServiceException
	 */
	
	private void checkOwnAmounts(SHashMap<String, Object> complexData) throws ServiceException {
		Long id = complexData.getvalAsLng("id");
		String ids = complexData.getvalAsStr("ids");
		Long accountId = complexData.getvalAsLng("accountId");
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		if(!StringHandler.isValidStr(ids)) ids = id.toString();
		String hql = "select sum(payAmount) from LoanInvoceEntity A WHERE A.id in ("+ids+") ";
		Double totalPayAmount = 0d;
		try {
			DataTable dtDatas = commonDao.getDatasByHql(hql,"totalPayAmount");
			if(null != dtDatas && dtDatas.getRowCount() > 0) totalPayAmount = dtDatas.getDouble("totalPayAmount");
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
		if(totalPayAmount == 0d) throw new ServiceException(ServiceException.LOANAMOUNT_ERROR);
		AccountEntity accountEntity = accountService.getEntity(accountId);
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("accountId", accountId);
		OwnFundsEntity ownFunds = ownFundsService.getEntity(params);
		if(null == ownFunds) throw new ServiceException(user,ServiceException.FUNDS_ERROR,accountEntity.getAccount());
		BigDecimal uamount = ownFunds.getUamount();
		if(null == uamount || uamount.doubleValue() < totalPayAmount)
			throw new ServiceException(user,ServiceException.INSUFFICIENT_BALANCE,accountEntity.getAccount());
	}
	
	private void updateApplyState(UserEntity user, String loanInvoceIds) throws ServiceException {
		SHashMap<String, Object> data = new SHashMap<String, Object>();
		data.put("state", BussStateConstant.FIN_APPLY_STATE_3);
		data.put(SysConstant.USER_KEY, user);
		data.put("loanInvoceIds", loanInvoceIds);
		applyService.updateState(data);
	}
	
	private FreeEntity makeFreeAmount(LoanInvoceEntity loanEntity,UserEntity user){
		Double prate = loanEntity.getPrate();
		if(null == prate  || prate == 0d) return null;/*如果手续费为 0 则不需要计算手续费*/
		Long loanInvoceId = loanEntity.getId();
		Long formId = loanEntity.getFormId();
		BigDecimal payAmount = loanEntity.getPayAmount();
		BigDecimal freeAmount = BigDecimal.valueOf(payAmount.doubleValue() * (prate/100));
		freeAmount.round(new MathContext(2));/*四舍五入保留两位小数*/
		FreeEntity fEntity = new FreeEntity();
		fEntity.setFormId(formId);
		fEntity.setLoanInvoceId(loanInvoceId);
		fEntity.setFreeamount(freeAmount);
		BeanUtil.setCreateInfo(user, fEntity);
		return fEntity;
	}

	/**
	 * 批处理并保存手续费数据
	 * @param ids
	 * @param user
	 * @throws ServiceException
	 */
	private void makeFreeAmounts(String ids,UserEntity user) throws ServiceException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		List<LoanInvoceEntity> list = getEntityList(map);
		List<FreeEntity> freeEntitys = new ArrayList<FreeEntity>();
		for(LoanInvoceEntity liEntity : list){
			FreeEntity fEntity = makeFreeAmount(liEntity,user);
			if(null == fEntity) continue;
			freeEntitys.add(fEntity);
		}
		if(null != freeEntitys && freeEntitys.size() > 0) freeService.batchSaveEntitys(freeEntitys);
	}

	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
			DataTable dt  = null;
			Object exceltype = (Object) params.get("excelType");
			try {
				if(StringHandler.isValidObj(exceltype)){
					Integer type =Integer.parseInt(exceltype.toString());
					switch (type) {
					case 1:
						 dt = loanInvoceDao.getResultList(new SHashMap<String, Object>(params),-1,-1);
						break;
					case 2:
						 dt = loanInvoceDao.getLoanInvoceQueryDetail(new SHashMap<String, Object>(params),-1,-1);
						break;
					}
				}
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws ServiceException {
		try {
			DataTable dt = loanInvoceDao.getIds(map);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/**
	 * 获取放款单里面的预收利息，管理费情况
	 * @param invoceEntity 
	 */
	public void getys(LoanInvoceEntity invoceEntity) throws ServiceException{
		try {
			double ysMat = invoceEntity.getYsMat().doubleValue();
			double  ysRat = invoceEntity.getYsRat().doubleValue();
			Integer ysMatMonth = invoceEntity.getYsMatMonth();
			Integer ysRatMonth = invoceEntity.getYsRatMonth();
			if((StringHandler.isValidObj(ysMatMonth) && ysMatMonth>0) || 
					(StringHandler.isValidObj(ysRatMonth) && ysRatMonth>0 )|| ysMat>0d || ysRat > 0d){
				invoceEntity.setIsNotys(1);
				this.saveOrUpdateEntity(invoceEntity);
			}
		}catch (ServiceException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/**
	 * 审批放款单
	 */
	
	@Override
	@Transactional
	public void doAuditInvoce(Map<String, Object> complexData) throws ServiceException {
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		Long id = (Long)complexData.get("id");
		Integer auditState =  (Integer)complexData.get("auditState");
		SHashMap<String, Object> queryParams = new SHashMap<String, Object>();
		queryParams.put("id", id);
		queryParams.put("auditState", SqlUtil.LOGIC_NOT_EQ + SqlUtil.LOGIC + auditState);
		LoanInvoceEntity invoceEntity = getEntity(queryParams);
		if(null == invoceEntity) return;
		invoceEntity.setAuditState(auditState);
		BeanUtil.setModifyInfo(user, invoceEntity);
		/*====== step 1 : 更新放款单状态为：审核通过或不通过  ====*/
		this.updateEntity(invoceEntity);
		if(auditState.intValue() == BussStateConstant.LOANCONTRACT_AUDITSTATE_LEN1) return;
		
		this.getys(invoceEntity);
    		
		/*====== step 2 : 根据还款方式生成还款计划表  ====*/
		Long contractId = invoceEntity.getContractId();
		LoanContractEntity loanContractEntity = loanContractService.getEntity(contractId);

		Map<String,Object> params = new HashMap<String, Object>();
		params.put("loanInvoceId", invoceEntity.getId());
		params.put("realDate", invoceEntity.getPayDate());//invoceEntity.getRealDate()
		params.put("payAmount", invoceEntity.getPayAmount());
		
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("contractId", loanContractEntity.getId());
		List<PlanEntity> planList = planService.getEntityList(map);
		
		PayTypeCalculateFactory factory = PayTypeCalculateFactory.getInstance();
		factory.setPayTypeService(payTypeService);
		String bussCode = loanContractEntity.getPayType();
		if(!StringHandler.isValidStr(bussCode)) throw new ServiceException("还款方式不能为空!");
		PayTypeCalculateAbs  paytypeObj = factory.creator(bussCode);
		
		List<ChildPlanEntity> childPlans = paytypeObj.make(user, loanContractEntity, params,planList);
		/*====== step 3: 保存还款计划子表和总表  ====*/
		if(null != childPlans && childPlans.size() > 0){
			childPlanService.batchSaveEntitys(childPlans);
		}
		planList = paytypeObj.getPlanList();
		if(null != planList && planList.size() > 0){
			planService.batchSaveOrUpdateEntitys(planList);
		}
		
		/*====== step 4: 保存合同数据,当生成第一张放款单还款计划时，如果是 放款日当结算日时，会更新合同表中的 payDay 值  ====*/
		BeanUtil.setModifyInfo(user, loanContractEntity);
		String remark = loanContractEntity.getRemark();
		if(!StringHandler.isValidStr(remark)){
			remark = "";
		}
		remark = "放款单审批后，生成了放款单["+DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT, new Date())+"]";
		loanContractEntity.setRemark(remark);
		loanContractService.saveOrUpdateEntity(loanContractEntity);
		
		/*====== step 5: 更新贷款申请单据,当生成第一张放款单还款计划时，会更新申请单状态和总期数  ====*/
		invoceEntity.setStartWay(BussStateConstant.LOANINVOCE_STARTWAY_1);/*标识以合约放款日做为结息日*/
		makePlanList(user, invoceEntity, loanContractEntity, planList, remark);
	}

	private void makePlanList(UserEntity user, LoanInvoceEntity invoceEntity,
			LoanContractEntity loanContractEntity, List<PlanEntity> planList,
			String remark) throws ServiceException {
		Long applyId = loanContractEntity.getFormId();
		ApplyEntity applyEntity = applyService.getEntity(applyId);
		Integer totalPhases = applyEntity.getTotalPhases();
		if(null == totalPhases){
			totalPhases = planList.size();
			applyEntity.setTotalPhases(totalPhases);
		}
		//applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_3);
		BeanUtil.setModifyInfo(user, applyEntity);
		applyEntity.setRemark(remark);
		applyService.updateEntity(applyEntity);
		
		
		/**/
		Long formId = invoceEntity.getFormId();
		if(invoceEntity != null){
			SHashMap<String, Object> formParams = new SHashMap<String, Object>();
			formParams.put("formId", formId);
			formParams.put("isenabled", SysConstant.OPTION_ENABLED);
			List<LoanInvoceEntity> invoceEntityList = this.getEntityList(formParams);
			if(!invoceEntityList.isEmpty()){
				Collections.sort(invoceEntityList,new Comparator<LoanInvoceEntity>(){   
	                public int compare(LoanInvoceEntity arg0, LoanInvoceEntity arg1) {   
	                    return arg0.getId().compareTo(arg1.getId());   
	                 }   
	             });
				LoanInvoceEntity loanInvoceEntityFrist = invoceEntityList.get(0);
				Date fristPayDate = loanInvoceEntityFrist.getPayDate();
				
				if(!planList.isEmpty()){
					Collections.sort(planList,new Comparator<PlanEntity>(){   
		                public int compare(PlanEntity arg0, PlanEntity arg1) {   
		                    return arg0.getPhases().compareTo(arg1.getPhases());   
		                 }   
		             });
					PlanEntity lastEntity = planList.get(planList.size()-1);
					Date larstXpayDate = lastEntity.getXpayDate();
					if(larstXpayDate != null){
						LoanContractEntity loanContract = loanContractService.getEntity(formParams);
						if(loanContract != null){
							Integer setdayType = loanContract.getSetdayType();
							if(setdayType == BussStateConstant.LOANCONTRACT_SETDAYTYPE_1){
								Date payDate = loanContract.getPayDate();
								Date endDate = loanContract.getEndDate();
								loanContract.setOldpayDate(payDate);
								loanContract.setEndDate(endDate);
								loanContract.setPayDate(fristPayDate);
								loanContract.setEndDate(larstXpayDate);
								loanContractService.saveOrUpdateEntity(loanContract);
							}
						}
					}
				}
			}
			
		}
	}

	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.LoanInvoceService#getLoanInvoceQuery(java.util.HashMap, int, int)
	 */
	@Override
	public DataTable getLoanInvoceQuery(SHashMap<String, Object> params,
			int offset, int pageSize) throws ServiceException {
		DataTable  dt = null;
		try {
			dt = loanInvoceDao.getLoanInvoceQuery(params, offset, pageSize);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return dt;
	}
	/* (non-放款单查询)
	 * @see com.cmw.service.inter.finance.LoanInvoceService#getLoanInvoceQuery(java.util.HashMap, int, int)
	 */
	@Override
	public DataTable getLoanInvoceQueryDetail(SHashMap<String, Object> params,
			int offset, int pageSize) throws ServiceException {
		DataTable  dt = null;
		try {
			dt = loanInvoceDao.getLoanInvoceQueryDetail(params, offset, pageSize);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return dt;
	}

}
