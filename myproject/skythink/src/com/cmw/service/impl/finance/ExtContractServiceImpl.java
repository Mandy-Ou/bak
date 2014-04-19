package com.cmw.service.impl.finance;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.ExtContractDaoInter;
import com.cmw.dao.inter.finance.ExtPlanDaoInter;
import com.cmw.dao.inter.sys.CommonDaoInter;
import com.cmw.entity.finance.ChildPlanEntity;
import com.cmw.entity.finance.ExtContractEntity;
import com.cmw.entity.finance.ExtPlanEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.finance.paytype.PayTypeCalculateAbs;
import com.cmw.service.impl.finance.paytype.PayTypeCalculateFactory;
import com.cmw.service.inter.finance.ExtContractService;
import com.cmw.service.inter.finance.ExtPlanService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.PayTypeService;
import com.cmw.service.inter.finance.PlanService;


/**
 * 展期协议书  Service实现类
 * @author pdh
 * @date 2013-09-23T00:00:00
 */
@Description(remark="展期协议书业务实现类",createDate="2013-09-23T00:00:00",author="pdh")
@Service("extContractService")
public class ExtContractServiceImpl extends AbsService<ExtContractEntity, Long> implements  ExtContractService {
	@Autowired
	private ExtContractDaoInter extContractDao;
	@Resource(name="extPlanService")
	private ExtPlanService extPlanService;
	
	@Resource(name="payTypeService")
	private PayTypeService payTypeService;
	
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	
	@Resource(name="planService")
	private PlanService planService;
	
	@Autowired
	private ExtPlanDaoInter extPlanDao;
	
	@Autowired
	private CommonDaoInter	commonDao;
	
	@Resource(name="planHandler")
	private PlanHandler planHandler;
	
	@Override
	public GenericDaoInter<ExtContractEntity, Long> getDao() {
		return extContractDao;
	}
	/* (non-Javadoc)
	 * @see com.cmw.core.base.service.AbsService#doComplexBusss(java.util.Map)
	 */
	@Override
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		
		Integer actionType = (Integer)complexData.get("actionType");
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		ExtContractEntity entity = (ExtContractEntity)complexData.get("Entity");
		saveOrUpdateEntity(entity);
		Long id = entity.getId();
		BigDecimal extAmount = entity.getExtAmount();//展期金额
		Long contractId = entity.getContractId();//借款合同id
		Date estartDate = entity.getEstartDate();//展期起始日期
		Date eendDate = entity.getEendDate();//展期截止日期
		String payType = entity.getPayType();//还款方式
		
		// 借款合同
		LoanContractEntity loanContractEntity = loanContractService.getEntity(contractId);
		loanContractEntity.setExteDate(eendDate);/*保存展期日期*/
		BeanUtil.setModifyInfo(user, loanContractEntity);
		loanContractService.updateEntity(loanContractEntity);
		//--> 将展期协议书中的数据复制到借款合同对象中，以方便生成还款计划表 <--//
		LoanContractEntity copyLoanContractEntity = (LoanContractEntity)loanContractEntity.clone();
		copyLoanContractEntity.setYearLoan(entity.getYearLoan());
		copyLoanContractEntity.setMonthLoan(entity.getMonthLoan());
		copyLoanContractEntity.setDayLoan(entity.getDayLoan());
		copyLoanContractEntity.setIsadvance(entity.getIsadvance());
		copyLoanContractEntity.setAppAmount(extAmount);
		copyLoanContractEntity.setPhAmount(entity.getPhAmount().doubleValue());
		copyLoanContractEntity.setRateType(entity.getRateType());
		copyLoanContractEntity.setRate(entity.getRate());
		copyLoanContractEntity.setMgrtype(entity.getMgrtype());
		copyLoanContractEntity.setMrate(entity.getMrate());
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("isExt", true);
		params.put("extcontractId", entity.getId());
		params.put("realDate", DateUtil.dateFormat(estartDate));//invoceEntity.getRealDate()
		params.put("payAmount", entity.getExtAmount());
		
		removeOldDatas(actionType,id);//清除之前旧数据
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put(SqlUtil.ORDERBY_KEY,"asc:phases,id");
		map.put("contractId", contractId);
		List<PlanEntity> planList = planService.getEntityList(map);
		
	    PayTypeCalculateFactory factory = PayTypeCalculateFactory.getInstance();
		factory.setPayTypeService(payTypeService);
		PayTypeCalculateAbs  paytypeObj = factory.creator(payType);
		
		java.util.List<ChildPlanEntity> ePlansList = paytypeObj.make(user, copyLoanContractEntity, params,planList);
		changExtPlan(user,ePlansList,actionType,id,contractId);
		planList = paytypeObj.getPlanList();
		planService.batchSaveOrUpdateEntitys(planList);
		planHandler.updateTotalPhases(contractId, user);
	}
	
	
	
	/**保存展期计划表
	 * @param ePlansList 
	 * @throws ServiceException 
	 * 
	 */
	private void changExtPlan(UserEntity user,List<ChildPlanEntity> ePlansList,Integer actionType,Long formId,Long contractId) throws ServiceException {
		if(null == ePlansList || ePlansList.size() == 0) return;
		List<ExtPlanEntity> extPlanList = new ArrayList<ExtPlanEntity>();
		for(ChildPlanEntity plan : ePlansList){
			ExtPlanEntity  extPlanEntity = new ExtPlanEntity();
			extPlanEntity.setContractId(contractId);
			extPlanEntity.setActionType(actionType);
			extPlanEntity.setFormId(formId);
			extPlanEntity.setPhases(plan.getPhases());
			extPlanEntity.setXpayDate(plan.getXpayDate());
			extPlanEntity.setPrincipal(plan.getPrincipal());
			extPlanEntity.setInterest(plan.getInterest());
			extPlanEntity.setMgrAmount(plan.getMgrAmount());
			extPlanEntity.setTotalAmount(plan.getTotalAmount());
			BeanUtil.setCreateInfo(user, extPlanEntity);
			extPlanList.add(extPlanEntity);
		}
		extPlanService.batchSaveEntitys(extPlanList);
	}
	
	/**
	 * 如果存在旧的展期数据，则先要删除。
	 * @param actionType
	 * @param formId
	 * @throws ServiceException
	 */
	private void removeOldDatas(Integer actionType, Long formId)
			throws ServiceException {
		StringBuffer sbHql = new StringBuffer();
		try {
			sbHql.append("select count(A.id) as totalCount from ExtPlanEntity A ")
				.append(" where A.isenabled !='"+SysConstant.OPTION_DEL+"' ")
				.append(" and A.actionType='"+actionType+"' and A.formId='"+formId+"' ");
			DataTable dtCount = commonDao.getDatasByHql(sbHql.toString(),"totalCount");
			boolean isDel = true;
			if(null == dtCount || dtCount.getRowCount() == 0){
				isDel = false;
			}
			Long totalCount = dtCount.getLong("totalCount");
			if(null == totalCount || totalCount == 0) isDel = false;
			if(isDel){
				SHashMap<String, Object> params = new SHashMap<String, Object>();
				params.put("actionType", actionType);
				params.put("formId", formId);
				extPlanService.deleteEntitys(params);
				params.clear();
				//-->如果还款计划总表中存在旧数据，则删除//
				params.put("extcontractId", formId);
				planService.deleteEntitys(params);
			}
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			Object extContractId = (Object)params.get("extContractId");
			DataTable dtExtContract = null;
			if(StringHandler.isValidObj(extContractId)) {
				SHashMap<String, Object> loancontractParams = new SHashMap<String, Object>();
				loancontractParams.put("id", extContractId);
				 dtExtContract = this.getResultList(loancontractParams, -1, -1);
			}
			HashMap<String, Object> conMap = getloanContractParams(dtExtContract);
			params.putAll(conMap);
			params.put("formId", extContractId);
			DataTable dt = extPlanDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/**
	 * @param dtExtContract
	 * @return
	 */
	private HashMap<String, Object> getloanContractParams(
			DataTable dtExtContract) {
		HashMap<String, Object> vals = new HashMap<String, Object>();
		String code = dtExtContract.getString("code");
		String loanCode = dtExtContract.getString("loanCode");
		
		String ostartDate = dtExtContract.getString( "ostartDate#yyyy-MM-dd");
		String oendDate = dtExtContract.getString( "oendDate#yyyy-MM-dd");
		String estartDate = dtExtContract.getString( "estartDate#yyyy-MM-dd");
		String eendDate = dtExtContract.getString( "eendDate#yyyy-MM-dd");
		String payType = dtExtContract.getString( "payType");
		String phAmount = dtExtContract.getString( "phAmount");
		String rateType = dtExtContract.getString("rateType");
		if(StringHandler.isValidStr(rateType)){
			if(rateType.equals("1")){
				rateType = "月利率";
			}else if(rateType.equals("2")){
				rateType = "日利率";
			}else{
				rateType="年利率";
			}
		}
		String rate = dtExtContract.getString("rate");
		String isadvance = dtExtContract.getString("isadvance");
		if(StringHandler.isValidStr(isadvance)){
			if(isadvance.equals("0")){
				isadvance = "否";
			}else{
				isadvance = "是";
			}
		}
		String mgrtype = dtExtContract.getString("mgrtype");
		if(StringHandler.isValidStr(mgrtype)){
			if(mgrtype.equals("0")){
				mgrtype = "不收管理费";
			}else{
				mgrtype = "按还款方式算法收取";
			}
		}
		String mrate = dtExtContract.getString("mrate");
		String applyMan = dtExtContract.getString("applyMan");
		String asignDate = dtExtContract.getString("asignDate#yyyy-MM-dd");
		String guarantor = dtExtContract.getString("guarantor");
		String gsignDate = dtExtContract.getString("gsignDate#yyyy-MM-dd");
		String loanLimit = dtExtContract.getString("loanLimit");
		String signDate = dtExtContract.getString("signDate#yyyy-MM-dd");
		String otherRemark = dtExtContract.getString("otherRemark");
		
		String endAmount = dtExtContract.getString("endAmount");
		String extAmount = dtExtContract.getString("extAmount");
		
		vals.put("endAmount", endAmount);
		vals.put("extAmount", extAmount);
		vals.put("mgrtype", mgrtype);
		
		vals.put("code", code);
		vals.put("loanCode", loanCode);
		vals.put("isadvance", isadvance);
		vals.put("payType", payType);
		vals.put("ostartDate", ostartDate);
		vals.put("loanLimit", loanLimit);
		vals.put("rateType", rateType);
		vals.put("rate", rate);
		vals.put("applyMan", applyMan);
		vals.put("otherRemark", otherRemark);
		vals.put("signDate", signDate);
		vals.put("gsignDate", gsignDate);
		vals.put("guarantor", guarantor);
		vals.put("asignDate", asignDate);
		vals.put("phAmount", phAmount);
		vals.put("eendDate", eendDate);
		vals.put("estartDate", estartDate);
		vals.put("mrate", mrate);
		vals.put("oendDate", oendDate);
		return vals;
	}
	
	
	
}
