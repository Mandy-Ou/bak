package com.cmw.service.impl.finance;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.PlanDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.LoanContractEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.ApplyService;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.finance.LoanContractService;
import com.cmw.service.inter.finance.OrderService;
import com.cmw.service.inter.finance.PlanService;
import com.cmw.service.inter.fininter.AmountLogService;


/**
 * 还款计划  Service实现类
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="还款计划业务实现类",createDate="2013-01-15T00:00:00",author="程明卫")
@Service("planService")
public class PlanServiceImpl extends AbsService<PlanEntity, Long> implements  PlanService {
	@Autowired
	private PlanDaoInter planDao;
	@Resource(name="orderService")
	private OrderService orderService;
	@Resource(name="amountRecordsService")
	private AmountRecordsService amountRecordsService;
	@Resource(name="loanContractService")
	private LoanContractService loanContractService;
	@Resource(name="applyService")
	private ApplyService applyService;
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	@Resource(name = "fundsWaterService")
	private FundsWaterService fundsWaterService;
	
	@Override
	public GenericDaoInter<PlanEntity, Long> getDao() {
		return planDao;
	}

	 /**
	 * 获取正常扣收数据
	 * @param params	查询参数
	 * @param offset	分页起始位
	 * @param pageSize  每页大小
	 * @return 返回 DataTable 对象
	 * @throws DaoException
	 */
	 public DataTable getNomalPlans(SHashMap<String, Object> params,int offset, int pageSize) throws ServiceException{
		 try {
			return planDao.getNomalPlans(params, offset, pageSize);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	 }
	 
	 @Override
	 public DataTable getIds(SHashMap<String, Object> params) throws ServiceException{
		 try {
				return planDao.getIds(params);
			} catch (DaoException e) {
				throw new ServiceException(e);
			}
	 }
	 
	public DataTable getDataSource(HashMap<String, Object> params)throws ServiceException {
		try {
			DataTable dt  = null;
			Object exceltype = (Object) params.get("excelType");
			if(StringHandler.isValidObj(exceltype)){
				Integer type =Integer.parseInt(exceltype.toString());
				switch (type) {
				case 1:
					 dt = planDao.getNomalPlans(new SHashMap<String, Object>(params),-1,-1);
					break;
				case 2:
					 dt = planDao.RepDetail(new SHashMap<String, Object>(params),-1,-1);
					break;
				}
			}
			
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
		
	 /**
	  * 正常扣收 单笔或批量收款
	  */
	@Override
	@Transactional
	public Object doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
		Long id = (Long)complexData.get("id");
		Map<String,Object> mapData = complexData.getMap();
		String recordIds = null;
		if(StringHandler.isValidObj(id)){/*单笔收款*/
			recordIds = receivSingleAmounts(mapData);
		}else{/*批量收款*/
			recordIds = receivBatchAmounts(mapData);
		}
		
		//---> step 2 : 保存实收金额日志<---//
		if(!StringHandler.isValidStr(recordIds)) return null;
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("ids", recordIds);
		Long sysId = complexData.getvalAsLng("sysId");
		params.put("sysId", sysId);
		String vtempCode = complexData.getvalAsStr("vtempCode");
		params.put("vtempCode", vtempCode);
		params.put(SysConstant.USER_KEY, user);
		params.put("bussTag", BussStateConstant.AMOUNTLOG_BUSSTAG_2);
		Map<AmountLogEntity,DataTable> logDataMap = amountLogService.saveByBussTag(params);
		
		//----> ste 3 : 保存资金流水 和更新自有资金<---//
		fundsWaterService.calculate(logDataMap,user);
		return logDataMap;
	}
	
	/**
	 * 批量正常扣收
	 * @param complexData
	 * @throws DaoException 
	 * @throws ServiceException 
	 * @return 返回收款记录ID列表
	 */
	private String receivBatchAmounts(Map<String, Object> complexData) throws ServiceException{
		DataTable dtOrder = orderService.getResultList();
		
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		//ids,accountId,rectDate,batchDatas
		String ids = (String)complexData.get("ids");
		String batchDatas = (String)complexData.get("batchDatas");
		JSONArray dataArr = FastJsonUtil.convertStrToJSONArr(batchDatas);
		complexData.remove("batchDatas");
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		List<PlanEntity> planEntitys = this.getEntityList(map);
		
		Map<Long,ApplyEntity> applyEntityMap = applyService.getApplyEntitysByPlanIds(ids);
		
		List<PlanEntity> new_planEntitys = new ArrayList<PlanEntity>();
		List<AmountRecordsEntity> amountRecordsEntitys = new ArrayList<AmountRecordsEntity>();
		List<ApplyEntity> applyEntitys = new ArrayList<ApplyEntity>();
	
		for(PlanEntity planEntity : planEntitys){
			Integer status = planEntity.getStatus();
			if(null != status && status.intValue() == BussStateConstant.PLAN_STATUS_2) return null;/*如果结清，则返回*/
			Long id = planEntity.getId();
			Object[] datas = this.getRealTotalAmount(id, dataArr);
			double rtotalAmount = (null == datas[0]) ? 0 : (Double)datas[0];
			Long contractId = (null == datas[1]) ? null : (Long)datas[1];
			if(rtotalAmount <= 0) continue;
			complexData.put("contractId", contractId);
			calculateAmountByOrder(planEntity,rtotalAmount,dtOrder,complexData);
			
			/*======== step 1 : 保存收款记录 =======*/
			//id,user,accountId,rectDate,phases,cat,rat,mat,tat
			AmountRecordsEntity amEntity = createAmountRecordsEntity(complexData,user,id);
			amountRecordsEntitys.add(amEntity);
			
			/*======== step 2 : 更新还款计划表状态和实收项  =======*/
			setPlanEntity(planEntity,complexData,user);
			new_planEntitys.add(planEntity);
			
			/*======== step 3 : 更新贷款申请单状态  =======*/
			contractId = planEntity.getContractId();
			ApplyEntity applyEntity = applyEntityMap.get(contractId);
			applyEntity = getApplyEntity(planEntity, applyEntity, user);
			applyEntitys.add(applyEntity);
			
			/*======== step 4 : 如果应找回金额大于0，则存入预收款帐户  =======*/
			saveAdvanceAmounts(applyEntity,complexData);
		}
		
		/*------- 批量保存或更新 ------*/
		amountRecordsService.batchSaveEntitys(amountRecordsEntitys);
		batchUpdateEntitys(new_planEntitys);
		applyService.batchUpdateEntitys(applyEntitys);
		
		StringBuilder sbRecordIds = new StringBuilder();
		if(null == amountRecordsEntitys || amountRecordsEntitys.size() == 0) return null;
		for(AmountRecordsEntity amountRecord : amountRecordsEntitys){
			Long amountRecordId = amountRecord.getId();
			sbRecordIds.append(amountRecordId).append(",");
		}
		return StringHandler.RemoveStr(sbRecordIds);
	}
	
	/**
	 * 根据扣收顺序计算本金，利息，管理费应收金额
	 * @param totalAmount 本次实收合计金额
	 * @param dtOrder 扣收顺序数据
	 * @param complexData
	 */
	private void calculateAmountByOrder(PlanEntity planEntity, double totalAmount,DataTable dtOrder,Map<String, Object> complexData){
		//id,user,accountId,rectDate,phases,cat,rat,mat,tat
		double cat = 0d,rat = 0d,mat = 0d,tat = 0d;
		tat = totalAmount;
		double[] amountArr = null;
		for(int i=0,count=dtOrder.getRowCount(); i<count; i++){
			String code = dtOrder.getString(i, "code");
			if(SysConstant.ORDER_L0001.equals(code)){
				double zinterest = BigDecimalHandler.sub(planEntity.getInterest(), planEntity.getYinterest());
				if(zinterest <=0) continue;
				amountArr = calculateAmounts(totalAmount,zinterest);
				rat = amountArr[0];
				totalAmount = amountArr[1];
			}else if(SysConstant.ORDER_L0002.equals(code)){
				double zprincipal = BigDecimalHandler.sub(planEntity.getPrincipal(), planEntity.getYprincipal());
				if(zprincipal <=0) continue;
				amountArr = calculateAmounts(totalAmount,zprincipal);
				cat = amountArr[0];
				totalAmount = amountArr[1];
			}else if(SysConstant.ORDER_L0003.equals(code)){
				double zmgrAmount = BigDecimalHandler.sub(planEntity.getMgrAmount(), planEntity.getYmgrAmount());
				if(zmgrAmount <=0) continue;
				amountArr = calculateAmounts(totalAmount,zmgrAmount);
				mat = amountArr[0];
				totalAmount = amountArr[1];
			}
			if(totalAmount <=0) break;
		}
		complexData.put("cat", cat);
		complexData.put("rat", rat);
		complexData.put("mat", mat);
		complexData.put("tat", tat);
		if(totalAmount < 0) totalAmount = 0;
		double oddAmount = totalAmount;
		complexData.put("oddAmount", oddAmount);
	}
	
	
	/**
	 * 按扣收顺序计算 本金，利息，管理费的实际收款金额
	 * @param tat 本次实收合计
	 * @param minusAmount 本次应收金额
	 * @return 返回 本金，利息，管理费的实际收款金额
	 */
	private double[] calculateAmounts(double tat, double minusAmount){
		double result = tat - minusAmount;
		double amount = 0;
		if(result>=0){
			amount = minusAmount;	
		}
		if(result<0 && tat>0){
			amount = tat;
		}
		tat = result;
		return new double[]{amount,tat};
	}
	
	/**
	 * 根据还款计划ID获取 本次实收合计金额
	 * @param id 还款计划ID
	 * @param dataArr 
	 * @return 返回  实收合计金额 和合同ID
	 */
	private Object[] getRealTotalAmount(Long id,JSONArray dataArr){
		double amount = 0d;
		Long contractId = null;
		for(int i=0,count=dataArr.size(); i<count; i++){
			JSONObject obj = dataArr.getJSONObject(i);
			Long _id = obj.getLong("id");
			if(id.equals(_id)){
				contractId = obj.getLong("contractId");
				amount = obj.getDoubleValue("rtotalAmount");
				break;
			}
		}
		return new Object[]{amount,contractId};
	}
	
	/**
	 * 单笔正常扣收
	 * @param complexData
	 * @throws DaoException 
	 * @throws ServiceException 
	 * @return 返回收款记录ID
	 */
	private String receivSingleAmounts(Map<String, Object> complexData) throws ServiceException{
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		Long id = (Long)complexData.get("id");
		PlanEntity planEntity= this.getEntity(id);
		Integer status = planEntity.getStatus();
		if(null != status && status.intValue() == BussStateConstant.PLAN_STATUS_2) return null;/*如果结清，则返回*/
		
		/*======== step 1 : 保存收款记录 =======*/
		//id,user,accountId,rectDate,phases,cat,rat,mat,tat
		AmountRecordsEntity amEntity = createAmountRecordsEntity(complexData,user,id);
		amountRecordsService.saveEntity(amEntity);
		/*======== step 2 : 更新还款计划表状态和实收项  =======*/
		setPlanEntity(planEntity,complexData,user);
		this.updateEntity(planEntity);
		
		/*======== step 3 : 更新贷款申请单状态  =======*/
		ApplyEntity applyEntity = getApplyEntity(planEntity,user);
		applyService.updateEntity(applyEntity);
		
		/*======== step 4 : 如果应找回金额大于0，则存入预收款帐户  =======*/
		saveAdvanceAmounts(applyEntity,complexData);
		
		/*======== step 5 : 返回收款记录ID  =======*/
		return amEntity.getId().toString();
	}
	
	/**
	 * 预收保存未实现
	 * @param applyEntity
	 * @param complexData
	 */
	private void saveAdvanceAmounts(ApplyEntity applyEntity,Map<String, Object> complexData){
		// to do ...
		Double oddAmount = (Double)complexData.get("oddAmount");
		if(oddAmount > 0){
			// 保存预收金额 ... 
		}
		log.info("预收保存未实现 ... ");
	} 
	
	private ApplyEntity getApplyEntity(PlanEntity planEntity,UserEntity user) throws ServiceException {
		Long contractId = planEntity.getContractId();
		LoanContractEntity contractEntity = loanContractService.getEntity(contractId);
		ApplyEntity applyEntity = applyService.getEntity(contractEntity.getFormId());
		return getApplyEntity(planEntity,applyEntity,user);
	}
	
	private ApplyEntity getApplyEntity(PlanEntity planEntity,ApplyEntity applyEntity,UserEntity user) throws ServiceException {
		Integer phases = planEntity.getPhases();
		Integer status = planEntity.getStatus();
		Integer totalPhases = applyEntity.getTotalPhases();
		if(phases.intValue() == totalPhases.intValue() && status.intValue() == BussStateConstant.PLAN_STATUS_2){	/*最后一期且结清*/
			applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_6);
		}else{
			applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_4);
		}
		if(phases.intValue() != totalPhases.intValue() && status.intValue() == BussStateConstant.PLAN_STATUS_2){
			applyEntity.setPaydPhases(phases);
			applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_4);
		}
		BeanUtil.setModifyInfo(user, applyEntity);
		return applyEntity;
	}

	private void setPlanEntity(PlanEntity planEntity,Map<String, Object> complexData,UserEntity user) {
		String rectDate = (String)complexData.get("rectDate");
		Double cat = (Double)complexData.get("cat");
		Double rat = (Double)complexData.get("rat");
		Double mat = (Double)complexData.get("mat");
		Double tat = (Double)complexData.get("tat");
		double principal=0d,interest=0d,mgrAmount=0d,totalAmount = 0d;
		
		if(null != planEntity.getPrincipal()){
			principal = planEntity.getPrincipal().doubleValue();
		}
		if(null != planEntity.getInterest()){
			interest = planEntity.getInterest().doubleValue();
		}
		if(null != planEntity.getMgrAmount()){
			mgrAmount = planEntity.getMgrAmount().doubleValue();
		}
		if(null != planEntity.getTotalAmount()){
			totalAmount = planEntity.getTotalAmount().doubleValue();
		}
		
		double yprincipal=0d,yinterest=0d,ymgrAmount=0d,ytotalAmount = 0d;
		if(null != planEntity.getYprincipal()){
			yprincipal = planEntity.getYprincipal().doubleValue();
		}
		if(null != planEntity.getYinterest()){
			yinterest = planEntity.getYinterest().doubleValue();
		}
		if(null != planEntity.getYmgrAmount()){
			ymgrAmount = planEntity.getYmgrAmount().doubleValue();
		}
		if(null != planEntity.getYtotalAmount()){
			ytotalAmount = planEntity.getYtotalAmount().doubleValue();
		}
		yprincipal += cat;
		yinterest += rat;
		ymgrAmount += mat;
		ytotalAmount += tat;
		if(StringHandler.Round(yprincipal, 2) >= principal){
			yprincipal = principal;
		}
		planEntity.setYprincipal(StringHandler.Round2BigDecimal(yprincipal));
		
		if(StringHandler.Round(yinterest, 2) >= interest){
			yinterest = interest;
		}
		planEntity.setYinterest(StringHandler.Round2BigDecimal(yinterest));
		double piAmount = StringHandler.Round(principal+interest);
		double ypiAmount = StringHandler.Round(yprincipal+yinterest);
		if(ypiAmount >= piAmount){/*实收本息费大于应还本息费，本息状态为结清*/
			planEntity.setPistatus(BussStateConstant.PLAN_PISTATUS_2);
		}else{
			planEntity.setPistatus(BussStateConstant.PLAN_PISTATUS_1);
		}
		
		if(StringHandler.Round(ymgrAmount, 2) >= mgrAmount){
			ymgrAmount = mgrAmount;
			planEntity.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_2);
		}else{
			planEntity.setMgrstatus(BussStateConstant.PLAN_PISTATUS_1);
		}
		planEntity.setYmgrAmount(StringHandler.Round2BigDecimal(ymgrAmount));
		
		boolean flag = false;
		if(StringHandler.Round(ytotalAmount, 2) > totalAmount){
			ytotalAmount = totalAmount;
			flag = true;
		}
		planEntity.setYtotalAmount(StringHandler.Round2BigDecimal(ytotalAmount));
		
		if(StringHandler.Round(ytotalAmount, 2) == totalAmount){
			flag = true;
		}
		if(flag){
			planEntity.setStatus(BussStateConstant.PLAN_STATUS_2);
		}else{
			planEntity.setStatus(BussStateConstant.PLAN_STATUS_1);
		}
	
		planEntity.setLastDate(DateUtil.dateFormat(rectDate));
		BeanUtil.setModifyInfo(user, planEntity);
	}
	

	private AmountRecordsEntity createAmountRecordsEntity(
			Map<String, Object> complexData, UserEntity user, Long id) {
		Long accountId = (Long)complexData.get("accountId");
		Long contractId =  (Long)complexData.get("contractId");
		String rectDate = (String)complexData.get("rectDate");
		Double cat = (Double)complexData.get("cat");
		Double rat = (Double)complexData.get("rat");
		Double mat = (Double)complexData.get("mat");
		Double tat = (Double)complexData.get("tat");
		AmountRecordsEntity amEntity = new AmountRecordsEntity();
		amEntity.setContractId(contractId);
		amEntity.setInvoceId(id);
		amEntity.setBussTag(BussStateConstant.AMOUNTRECORDS_BUSSTAG_0);
		amEntity.setCat(StringHandler.Round2BigDecimal(cat));
		amEntity.setRat(StringHandler.Round2BigDecimal(rat));
		amEntity.setMat(StringHandler.Round2BigDecimal(mat));
		amEntity.setTat(StringHandler.Round2BigDecimal(tat));
		amEntity.setRectDate(DateUtil.dateFormat(rectDate));
		amEntity.setAccountId(accountId);
		BeanUtil.setCreateInfo(user, amEntity);
		return amEntity;
	}
	/**
	 * 正常还款流水
	 */
	@Override
	public DataTable RepDetail(SHashMap<String, Object> params, int offset,
			int pageSize) throws ServiceException {
		 try {
			return planDao.RepDetail(params, offset, pageSize);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	 }
	 
}
