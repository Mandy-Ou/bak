package com.cmw.service.impl.finance;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

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
import com.cmw.dao.inter.finance.AmountRecordsDaoInter;
import com.cmw.dao.inter.finance.ApplyDaoInter;
import com.cmw.dao.inter.finance.LoanContractDaoInter;
import com.cmw.dao.inter.finance.MinAmountDaoInter;
import com.cmw.dao.inter.finance.OverdueDeductDaoInter;
import com.cmw.dao.inter.finance.PlanDaoInter;
import com.cmw.dao.inter.sys.CommonDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ApplyEntity;
import com.cmw.entity.finance.PlanEntity;
import com.cmw.entity.finance.TaboutsideEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.HolidaysEntity;
import com.cmw.entity.sys.SysparamsEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.finance.OverdueDeductService;
import com.cmw.service.inter.finance.RiskLevelService;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.sys.HolidaysService;
import com.cmw.service.inter.sys.SysparamsService;


/**
 * 表内表外  Service实现类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="表内表外业务实现类",createDate="2013-02-28T00:00:00",author="程明卫")
@Service("overdueDeductService")
public class OverdueDeductServiceImpl extends AbsService<TaboutsideEntity, Long> implements  OverdueDeductService {
	private static final int BATCH_DATAS_COUNT=1000;
	private int batchCount = BATCH_DATAS_COUNT;/*每次提取逾期数据大小*/
	private double minPenAmount = 0d;/*默认最低罚息*/
	private double minDelAmount = 0d;/*默认最低滞纳金*/
	private DataTable riskLevelDt = null;/*五级分类 DT*/
	private DataTable minAmountDt = null;/*最低罚息，滞纳金设定数据*/
	@Autowired
	private CommonDaoInter commonDao;
	@Autowired
	private OverdueDeductDaoInter overdueDeductDao;
	@Autowired
	private MinAmountDaoInter minAmountDao;
	@Autowired
	private PlanDaoInter planDao;
	@Autowired
	private AmountRecordsDaoInter amountRecordsDao;
	@Autowired
	private ApplyDaoInter applyDao;
	@Autowired
	private LoanContractDaoInter loanContractDao;
	@Resource(name="sysparamsService")
	private SysparamsService sysparamsService;
	@Resource(name="riskLevelService")
	private RiskLevelService riskLevelService;
	@Resource(name="holidaysService")
	private HolidaysService holidaysService;
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	@Resource(name = "fundsWaterService")
	private FundsWaterService fundsWaterService;
	
	@Override
	public GenericDaoInter<TaboutsideEntity, Long> getDao() {
		return overdueDeductDao;
	}

	@Override
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws ServiceException {
		try {
			DataTable dt = overdueDeductDao.getIds(map);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	public DataTable getDataSource(HashMap<String, Object> params)throws ServiceException {
		try {
			DataTable  dt = null;
			Object exceltype = (Object) params.get("excelType");
			if(StringHandler.isValidObj(exceltype)){
				Integer type =Integer.parseInt(exceltype.toString());
				switch (type) {
				case 1:
					 dt = overdueDeductDao.getResultList(new SHashMap<String, Object>(params),-1,-1);
					break;
				case 2:
					 dt = overdueDeductDao.RepDetail(new SHashMap<String, Object>(params),-1,-1);
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
	 * 提供给系统定时器每天计算罚息和滞纳金的方法
	 * @throws ServiceException
	 */
	@Override
	public void calculateLateDatas() throws ServiceException {
		List<String> list = null;
		String hql = "SELECT contractId FROM TaboutsideEntity WHERE planId IS NOT NULL ";
		try {
			list = commonDao.getDatasByHql(hql);
			if(null == list || list.size() == 0) return;
			String contractIds = StringHandler.join(list.toArray());
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("contractIds", contractIds);
			List<PlanEntity> planList = planDao.getEntityListByBatch(params);
			if(null == planList || planList.size() == 0){
				throw new ServiceException("当前处理的收款数据状态已经发生变更，请检查是否已经收过款了!");
			}
			Date lastDate = new Date();
			loadSysParams();
			loadMinAmounts();
			loadRiskLevels();
			makeLatePlans(planList, lastDate);
		    planDao.batchUpdateEntitys(planList);
		    batchSaveDatas(planList,contractIds,null);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	@Override
	public <K, V> JSONObject calculateLateDatas(SHashMap<K, V> map)
			throws ServiceException {
		try {
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			String contractIds = map.getvalAsStr("contractorIds");
			Date lastDate = (Date)map.getvalAsObj("lastDate");
			params.put("contractIds", contractIds);
			List<PlanEntity> planList = planDao.getEntityListByBatch(params);
			if(null == planList || planList.size() == 0){
				throw new ServiceException("当前处理的收款数据状态已经发生变更，请检查是否已经收过款了!");
			}
			loadSysParams();
			loadMinAmounts();
			loadRiskLevels();
			makeLatePlans(planList, lastDate);
			return convertToJsonDatas(planList);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	
	
	/**
	 * 找出未收款的放款单ID数据
	 * 原因：客户有可能将Excel文件导多次，这样就会存在重复放款的问题
	 *      该方法处理重复放款的问题。
	 * @param contractIds
	 * @return
	 * @throws ServiceException
	 */
	private String getUnDoIdDatas(String contractIds) throws ServiceException{
		String newIds = null;
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + contractIds);
		map.put("state", BussStateConstant.LOANINVOCE_STATE_0);
		DataTable undoDt = this.getIds(map);
		if(null == undoDt || undoDt.getRowCount() == 0) throw new ServiceException("导入的数据之前已经放完款，请不要重复放款!");
		StringBuilder sb = new StringBuilder();
		for(int i=0,count=undoDt.getRowCount(); i<count; i++){
			String id = undoDt.getString(i, "contractId");
			sb.append(id).append(",");
		}
		if(null != sb && sb.length() > 0){
			newIds = StringHandler.RemoveStr(sb);
		}
		return newIds;
	}
	@Override
	@Transactional
	public Object doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
		//contractId,contractIds,accountId,rectDate,batchDatas
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		Long contractId = (Long)complexData.get("contractId");
		String contractIds = (String)complexData.get("contractIds");
		String batchDatas = (String)complexData.get("batchDatas");
		try {
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			List<PlanEntity> planEntitys = null;
			List<AmountRecordsEntity> amountRecordsList = null;
			Map<Long,ApplyEntity> applyEntitys = null;
			if(StringHandler.isValidStr(contractIds)){
				contractIds = this.getUnDoIdDatas(contractIds);
			}
			if(StringHandler.isValidObj(contractId)){/*单批收款*/
				contractIds = contractId.toString();
			}
			params.put("contractIds", contractIds);
			planEntitys = planDao.getEntityListByBatch(params);
			applyEntitys = applyDao.getApplyEntitysByContractIds(contractIds);
			amountRecordsList = setPlans(planEntitys,batchDatas,complexData,user);
			planDao.batchUpdateEntitys(planEntitys);
			amountRecordsDao.batchSaveEntitys(amountRecordsList);
			List<TaboutsideEntity> taboutsideList = saveTaboutsideDatas(contractIds, planEntitys,user);
			if(null != applyEntitys && applyEntitys.size() > 0){
				List<ApplyEntity> applyEntityList = setApplys(applyEntitys,planEntitys,user);
				applyDao.batchUpdateEntitys(applyEntityList);
			}
			if(null == amountRecordsList || amountRecordsList.size() == 0) return null;
			Map<String,Map<AmountLogEntity,DataTable>> vtlogDataMap = new HashMap<String, Map<AmountLogEntity,DataTable>>();
			String[] inouttypeRecordIdGroup = getAmountLogByGroup(taboutsideList,amountRecordsList);
			String inRecorIds = inouttypeRecordIdGroup[0];
			if(StringHandler.isValidStr(inRecorIds)){/*表内收款记录凭证数据*/
				complexData.put("recordIds", inRecorIds);
				complexData.put("vtempCode", BussStateConstant.VOUCHERTEMP_CODE_V00017);
				Map<AmountLogEntity,DataTable> amountLogDatas = saveAmountLogDatas(complexData,BussStateConstant.AMOUNTLOG_BUSSTAG_6);
				//----> 保存资金流水 和更新自有资金<---//
				fundsWaterService.calculate(amountLogDatas,user);
				
				vtlogDataMap.put(BussStateConstant.VOUCHERTEMP_CODE_V00017, amountLogDatas);
			}
			
			String outRecorIds = inouttypeRecordIdGroup[1];
			if(StringHandler.isValidStr(outRecorIds)){/*表外收款记录凭证数据*/
				complexData.put("recordIds", outRecorIds);
				complexData.put("vtempCode", BussStateConstant.VOUCHERTEMP_CODE_V00037);
				Map<AmountLogEntity,DataTable> amountLogDatas = saveAmountLogDatas(complexData,BussStateConstant.AMOUNTLOG_BUSSTAG_7);
				//----> 保存资金流水 和更新自有资金<---//
				fundsWaterService.calculate(amountLogDatas,user);
				
				vtlogDataMap.put(BussStateConstant.VOUCHERTEMP_CODE_V00037, amountLogDatas);
				//表外逾期扣收-备查登记类账户余额
				//amountLogDatas = saveAmountLogDatas(complexData,BussStateConstant.AMOUNTLOG_BUSSTAG_7);
				vtlogDataMap.put(BussStateConstant.VOUCHERTEMP_CODE_V00036, amountLogDatas);
			}
			return vtlogDataMap;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	private String[] getAmountLogByGroup(List<TaboutsideEntity> taboutsideList,List<AmountRecordsEntity> amountRecordsList) throws ServiceException{
		StringBuilder sbTabin = new StringBuilder();
		StringBuilder sbTabout = new StringBuilder();
		for(TaboutsideEntity tabEntity : taboutsideList){
			 Long contractId = tabEntity.getContractId();
			 Integer inouttype = tabEntity.getInouttype();
			 if(null == inouttype) inouttype = BussStateConstant.TABOUTSIDE_INOUTTYPE_0;
			 for(AmountRecordsEntity amountRecord : amountRecordsList){
				 Long _contractId = amountRecord.getContractId();
				 Long recordId = amountRecord.getId();
				 if(!contractId.equals(_contractId)) continue;
				 switch (inouttype) {
				 	case BussStateConstant.TABOUTSIDE_INOUTTYPE_0:
				 		sbTabin.append(recordId).append(",");
						break;
				 	case BussStateConstant.TABOUTSIDE_INOUTTYPE_1:
				 		sbTabout.append(recordId).append(",");
						break;
					default:
						throw new ServiceException("["+inouttype+"]是未知的表内表外转化类型");
						//break;
				}
			 }
		}
		return new String[]{StringHandler.RemoveStr(sbTabin),StringHandler.RemoveStr(sbTabout)};
	}
	
	private Map<AmountLogEntity,DataTable> saveAmountLogDatas(SHashMap<String, Object> complexData,Integer bussTag) throws ServiceException{
		String recordIds = complexData.getvalAsStr("recordIds");
		if(!StringHandler.isValidStr(recordIds)) return null;
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("ids", recordIds);
		Long sysId = complexData.getvalAsLng("sysId");
		params.put("sysId", sysId);
		String vtempCode = complexData.getvalAsStr("vtempCode");
	
		params.put("vtempCode", vtempCode);
		params.put(SysConstant.USER_KEY, user);
		params.put("bussTag", bussTag);
		Map<AmountLogEntity,DataTable> logDataMap = amountLogService.saveByBussTag(params);
		return logDataMap;
	}
	
	private List<ApplyEntity> setApplys(Map<Long,ApplyEntity> applyEntitys,List<PlanEntity> planEntitys,UserEntity user){
		Set<Long> keys = applyEntitys.keySet();
		for(Long contractId : keys){
			ApplyEntity applyEntity = applyEntitys.get(contractId);
			StringBuffer sb = new StringBuffer();
			for(PlanEntity planEntity : planEntitys){
				Long _contractId = planEntity.getContractId();
				if(!contractId.equals(_contractId)) continue;
				Integer status = planEntity.getStatus();
				Integer phases = planEntity.getPhases();
				Integer paydPhases= applyEntity.getPaydPhases();
				Integer totalPhases = applyEntity.getTotalPhases();
				if(status.intValue() == BussStateConstant.PLAN_STATUS_2){
					if(null == paydPhases || paydPhases.intValue() == 0){
						applyEntity.setPaydPhases(phases);
					}
					if(null != paydPhases && paydPhases < phases){
						paydPhases = phases;
						applyEntity.setPaydPhases(paydPhases);
					}
					if(null != totalPhases && totalPhases == phases){
						applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_6);
					}
					sb.append(1);
				}else{
					sb.append(0);
				}
				BeanUtil.setModifyInfo(user, applyEntity);
			}
			
			
			if((null != sb && sb.length() > 0)){
				String result = sb.toString();
				/*如果字符串中没有数字0，就代表曾逾期还款计划是已全部结清的，如果已还期数小于总期数，就代表还存在没有到还款日的计划*/
				if(result.indexOf("0") == -1 && (null != applyEntity.getPaydPhases() && applyEntity.getPaydPhases().intValue() < applyEntity.getTotalPhases())){
					applyEntity.setState(BussStateConstant.FIN_APPLY_STATE_4);/*设为收款中*/
				}
			}
		}
		Collection<ApplyEntity> applyList = applyEntitys.values();
		List<ApplyEntity> list = new ArrayList<ApplyEntity>();
		list.addAll(applyList);
		return list;
	}
	
	
	/**
	 * 设置逾期还款计划数据
	 * @param planEntitys
	 * @param batchDatas
	 * @param complexData
	 * @return
	 */
	private List<AmountRecordsEntity> setPlans(List<PlanEntity> planEntitys,String batchDatas,SHashMap<String, Object> complexData,UserEntity user){
		
		Map<String,Object> data = new HashMap<String, Object>();
		Date rectDate = (Date)complexData.get("rectDate");
		data.put("accountId", complexData.get("accountId"));
		
		data.put("rectDate", rectDate);
		JSONArray jsonArr = FastJsonUtil.convertStrToJSONArr(batchDatas);
		List<AmountRecordsEntity> recordsList = new ArrayList<AmountRecordsEntity>(planEntitys.size());
		for(int i=0,count=jsonArr.size(); i<count; i++){
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			Long id = jsonObj.getLong("id");
			Integer pdays = jsonObj.getInteger("pdays");
			Integer ratedays = jsonObj.getInteger("ratedays");
			Integer mgrdays = jsonObj.getInteger("mgrdays");
			Double cat = jsonObj.getDouble("cat");
			Double rat = jsonObj.getDouble("rat");
			Double mat = jsonObj.getDouble("mat");
			Double pat = jsonObj.getDouble("pat");
			Double dat = jsonObj.getDouble("dat");
			Double tat = jsonObj.getDouble("tat");
			Double penAmount = jsonObj.getDouble("penAmount");
			Double delAmount = jsonObj.getDouble("delAmount");
			 
			data.put("cat", cat);
			data.put("rat", rat);
			data.put("mat", mat);
			data.put("pat", pat);
			data.put("dat", dat);
			data.put("tat", tat);
			
			Long contractId = null;
			for(PlanEntity entity : planEntitys){
				Long planId = entity.getId();
				if(!planId.equals(id)) continue;
				contractId = entity.getContractId();
				double yprincipal = BigDecimalHandler.add(entity.getYprincipal().doubleValue(), cat);
				entity.setYprincipal(BigDecimalHandler.roundToBigDecimal(yprincipal, 2));
				double yinterest = BigDecimalHandler.add(entity.getYinterest().doubleValue(),rat);
				entity.setYinterest(BigDecimalHandler.roundToBigDecimal(yinterest, 2));
				double ymgrAmount = BigDecimalHandler.add(entity.getYmgrAmount().doubleValue(),mat);
				entity.setYmgrAmount(BigDecimalHandler.roundToBigDecimal(ymgrAmount, 2));
				entity.setPenAmount(BigDecimalHandler.get(penAmount));
				entity.setDelAmount(BigDecimalHandler.get(delAmount));
				double ypenAmount =  BigDecimalHandler.add(entity.getYpenAmount().doubleValue(),pat);
				entity.setYpenAmount(BigDecimalHandler.roundToBigDecimal(ypenAmount, 2));
				double ydelAmount =  BigDecimalHandler.add(entity.getYdelAmount().doubleValue(),dat);
				entity.setYdelAmount(BigDecimalHandler.roundToBigDecimal(ydelAmount, 2));
				double ytotalAmount = BigDecimalHandler.add(entity.getYtotalAmount().doubleValue(),tat);
				entity.setYtotalAmount(BigDecimalHandler.roundToBigDecimal(ytotalAmount, 2));
				double piAmounts = BigDecimalHandler.add(entity.getPrincipal(),entity.getInterest());
				double ypiAmounts = BigDecimalHandler.add(entity.getYprincipal(),entity.getYinterest());
				if(BigDecimalHandler.sub(piAmounts, ypiAmounts) <= 0){
					entity.setPistatus(BussStateConstant.PLAN_PISTATUS_2);
				}
				if(BigDecimalHandler.sub(entity.getPrincipal(), entity.getYprincipal()) <= 0){
					pdays = 0;
				}
				if(BigDecimalHandler.sub(entity.getInterest(), entity.getYinterest()) <= 0){
					ratedays = 0;
				}
				
				if(BigDecimalHandler.sub(entity.getMgrAmount(), entity.getYmgrAmount()) <= 0){
					entity.setMgrstatus(BussStateConstant.PLAN_MGRSTATUS_2);
					mgrdays=0;
				}
				double totalAmount = BigDecimalHandler.add(entity.getTotalAmount().doubleValue(), penAmount+delAmount) ;
				if(BigDecimalHandler.sub(totalAmount, ytotalAmount) <= 0){
					entity.setStatus(BussStateConstant.PLAN_STATUS_2);
				}
				entity.setPdays(pdays);
				entity.setRatedays(ratedays);
				entity.setMgrdays(mgrdays);
				if(tat>0) entity.setLastDate(rectDate);
				BeanUtil.setModifyInfo(user, entity);
			}
			data.put("contractId", contractId);
			if(tat>0){
				AmountRecordsEntity recordsEntity = createAmountRecordsEntity(data,user,id);
				recordsList.add(recordsEntity);
			}
		}
		return recordsList;
	}
	
	private AmountRecordsEntity createAmountRecordsEntity(
			Map<String, Object> complexData, UserEntity user, Long id) {
		Long contractId = (Long)complexData.get("contractId");
		Long accountId = (Long)complexData.get("accountId");
		Date rectDate = (Date)complexData.get("rectDate");
		Double cat = (Double)complexData.get("cat");
		Double rat = (Double)complexData.get("rat");
		Double mat = (Double)complexData.get("mat");
		Double pat = (Double)complexData.get("pat");
		Double dat = (Double)complexData.get("dat");
		Double tat = (Double)complexData.get("tat");
		AmountRecordsEntity amEntity = new AmountRecordsEntity();
		amEntity.setInvoceId(id);
		amEntity.setBussTag(BussStateConstant.AMOUNTRECORDS_BUSSTAG_4);
		amEntity.setCat(StringHandler.Round2BigDecimal(cat));
		amEntity.setRat(StringHandler.Round2BigDecimal(rat));
		amEntity.setMat(StringHandler.Round2BigDecimal(mat));
		amEntity.setPat(StringHandler.Round2BigDecimal(pat));
		amEntity.setDat(StringHandler.Round2BigDecimal(dat));
		amEntity.setTat(StringHandler.Round2BigDecimal(tat));
		amEntity.setRectDate(rectDate);
		amEntity.setAccountId(accountId);
		if(StringHandler.isValidObj(contractId)){
			amEntity.setContractId(contractId);
		}
		BeanUtil.setCreateInfo(user, amEntity);
		return amEntity;
	}
	
	
	private JSONObject convertToJsonDatas(List<PlanEntity> planList){
		JSONObject jsonObj = new JSONObject();
		for(PlanEntity planEntity : planList){
			Long contractId = planEntity.getContractId();
			String contractIdStr = contractId.toString();
			JSONObject planJsonObj = new JSONObject();
			planJsonObj.put("id", planEntity.getId().toString());
			planJsonObj.put("principal", planEntity.getPrincipal());
			planJsonObj.put("yprincipal", planEntity.getYprincipal());
			planJsonObj.put("interest", planEntity.getInterest());
			planJsonObj.put("yinterest", planEntity.getYinterest());
			planJsonObj.put("mgrAmount", planEntity.getMgrAmount());
			planJsonObj.put("ymgrAmount", planEntity.getYmgrAmount());
		
			planJsonObj.put("penAmount", planEntity.getPenAmount());
			planJsonObj.put("delAmount", planEntity.getDelAmount());
			planJsonObj.put("ypenAmount", planEntity.getYpenAmount());
			planJsonObj.put("ydelAmount", planEntity.getYdelAmount());
			planJsonObj.put("totalAmount", planEntity.getTotalAmount());
			planJsonObj.put("ytotalAmount", planEntity.getYtotalAmount());
		
			Integer pdays = planEntity.getPdays();
			if(null == pdays) pdays = 0;
			Integer rdays = planEntity.getRatedays();
			if(null == rdays) rdays = 0;
			Integer latedays = pdays > rdays ? pdays : rdays;
			Integer mgrdays = planEntity.getMgrdays();
			if(null == mgrdays) mgrdays = 0;
			planJsonObj.put("latedays", latedays);
			planJsonObj.put("pdays", pdays);
			planJsonObj.put("ratedays", rdays);
			planJsonObj.put("mgrdays", mgrdays);
			planJsonObj.put("exempt", planEntity.getExempt());
			
			if(!jsonObj.containsKey(contractIdStr)){
				JSONArray jsonArr = new JSONArray();
				jsonArr.add(planJsonObj);
				jsonObj.put(contractIdStr, jsonArr);
			}else{
				jsonObj.getJSONArray(contractIdStr).add(planJsonObj);
			}
		}
		
		return jsonObj;
	}
	@Override
	@Transactional
	public <K, V> void calculateBatchLateDatas(SHashMap<K, V> map)throws ServiceException {
		try{
			 String ids = map.getvalAsStr("ids");
			 Date lastDate = (Date)map.getvalAsObj("lastDate");
			 if(null == lastDate) lastDate = new Date();
			 UserEntity user = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
			 loadSysParams();
			 loadMinAmounts();
			 loadRiskLevels();
			 toLateState(lastDate,ids);
			 SHashMap<String, Object> params = new SHashMap<String, Object>();
			 params.put("status", BussStateConstant.PLAN_STATUS_4+","+BussStateConstant.PLAN_STATUS_5);
			 params.put("ids", ids);
			 Long totalCount = planDao.getLatePlansCount();
			 if(totalCount == 0) return;
			 if(totalCount > batchCount){
					params.put("limit", batchCount);
					int pages = (int)(totalCount/batchCount);
					//---> 如果小于总数，分页就要加1
					if(pages*batchCount < totalCount) pages++;
					int start = 0;
					for(int i=0; i<pages; i++){
						Date tempDate = new Date(System.currentTimeMillis());
						params.put("start", start);
						Date XstartDate = new Date(System.currentTimeMillis());
						List<PlanEntity> planList = planDao.getEntityListByBatch(params);
						System.out.println("第"+(i+1)+"次查询"+batchCount+"条数据用时："+(new Date(System.currentTimeMillis()).getTime()-XstartDate.getTime()));
						if(null == planList || planList.size() == 0) return;
						String contractIds = makeLatePlans(planList, lastDate);
						batchSaveDatas(planList,contractIds,user);
						start+=batchCount;
						System.out.println("逾期罚息共需计算"+pages+"次，第"+(i+1)+"次计算共用时："+(new Date(System.currentTimeMillis()).getTime()-tempDate.getTime()));
					}
			 }else{
				List<PlanEntity> planList = planDao.getEntityListByBatch(params);
				String contractIds = makeLatePlans(planList, lastDate);
				batchSaveDatas(planList,contractIds,user);
			 }
		}catch (DaoException e) {
			throw new ServiceException(e);
		}catch (ServiceException e) {
			throw e;
		}
	}
	
	private void toLateState(Date lastDate, String ids) throws ServiceException{
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			Date prevDate = DateUtil.minusDaysToDate(lastDate, 1);
			map.put("isenabled", SysConstant.OPTION_ENABLED);
			map.put("eqDate", prevDate);
			//map.put("edate", SqlUtil.LOGIC_LTEQ + SqlUtil.LOGIC + lastDateStr);
			List<HolidaysEntity> holidays = holidaysService.getEntityList(map);
			map.clear();
			
			if(null != holidays && holidays.size() > 0){
				StringBuffer betweenSb = new StringBuffer();
				boolean isHolidays = false;
				for(int i=0,count=holidays.size(); i<count; i++){
					HolidaysEntity holiday = holidays.get(i);
					Date startDate = holiday.getSdate();
					Date endDate = holiday.getEdate();
					startDate = DateUtil.dateFormat(startDate);
					endDate = DateUtil.dateFormat(endDate);
					int result = DateUtil.compareDate(startDate, endDate);
					if(DateUtil.compareDate(prevDate, startDate) == 0 || DateUtil.compareDate(prevDate, endDate) == 0){
						isHolidays = true;
					}
					
					if(result < 0){
						betweenSb.append("'"+DateUtil.dateFormatToStr(startDate)+"',");
						while(true){
							startDate = DateUtil.addDaysToDate(startDate, 1);
							if(DateUtil.compareDate(prevDate, startDate) == 0){
								isHolidays = true;
							}
							betweenSb.append("'"+DateUtil.dateFormatToStr(startDate)+"',");
							result = DateUtil.compareDate(startDate, endDate);
							if(result>=0) break;
						}
					}
				}
				if(isHolidays) 	map.put("holidays", StringHandler.RemoveStr(betweenSb));
			}
			
			map.put("lastDate", DateUtil.dateFormatToStr(lastDate));
			if(StringHandler.isValidStr(ids)){
				map.put("ids", ids);
			}
			planDao.updateSateToLate(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 批量保存数据
	 * @param planList
	 * @param tabList
	 * @param contractIds
	 * @param user
	 * @throws ServiceException
	 */
	private void batchSaveDatas(List<PlanEntity> planList,String contractIds, UserEntity user) throws ServiceException{
		try{
			planDao.batchSaveOrUpdateEntitys(planList);
			SHashMap<String, Object> data = new SHashMap<String, Object>();
			data.put("contractIds", contractIds);
			data.put("state", BussStateConstant.FIN_APPLY_STATE_5);
			data.put(SysConstant.USER_KEY, user);
			applyDao.updateState(data);
			saveTaboutsideDatas(contractIds,planList,user);
		}catch(DaoException e){
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 处理表内表外数据
	 * @param contractIds
	 * @param planList
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<TaboutsideEntity> saveTaboutsideDatas(String contractIds,List<PlanEntity> planList, UserEntity user) throws ServiceException{
		try {
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("contractId", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + contractIds);
			List<TaboutsideEntity> entitys = this.getEntityList(map);
			DataTable planDt = planDao.getLateSumDatas(contractIds);
			if(null == entitys || entitys.size() == 0){
				if(null == planDt || planDt.getRowCount() == 0) return null;
				entitys = new ArrayList<TaboutsideEntity>();
				for(int i=0,count=planDt.getRowCount(); i<count; i++){
					Long contractId = planDt.getLong(i,"contractId");
					TaboutsideEntity entity = createTaboutsideEntity(planDt,i,user);
					setMonthPharsesAndRiskLevel(entity,contractId,planList);
					entitys.add(entity);
				}
			}else{
				if(null == planDt || planDt.getRowCount() == 0){
					for(TaboutsideEntity entity : entitys){
						setTaboutside2finish(entity, user);
					}
				}else{
					/**============ step 1 : 先根据逾期还款计划创建或修改表内表外实体对象   ============**/
					int count=planDt.getRowCount();
					for(int i=0; i<count; i++){
						Long contractId = planDt.getLong(i,"contractId");
						TaboutsideEntity upEntity = null;
						for(TaboutsideEntity entity : entitys){
							Long _contractId = entity.getContractId();
							if(contractId.equals(_contractId)){
								upEntity = entity;
								break;
							}
						}
						if(null == upEntity){
							TaboutsideEntity entity = createTaboutsideEntity(planDt,i,user);
							setMonthPharsesAndRiskLevel(entity,contractId,planList);
							entitys.add(entity);
						}else{
							setTaboutsideEntity(planDt, i, upEntity);
							setMonthPharsesAndRiskLevel(upEntity,contractId,planList);
							BeanUtil.setModifyInfo(user, upEntity);
						}
					}
					
					/**== step 2 : 根据表内表外与逾期还款计比较。如果，找不到逾期还款计划，则应将表内表外标识为结清   =======**/
					for(TaboutsideEntity entity : entitys){
						Long contractId = entity.getContractId();
						boolean isFinish = true;//是否结清
						for(int i=0; i<count; i++){
							Long _contractId = planDt.getLong(i,"contractId");
							if(contractId.equals(_contractId)){
								isFinish = false;
								break;
							}
						}
						//标识为结清
						if(isFinish) setTaboutside2finish(entity,user);
					}
				}
			}
			batchSaveOrUpdateEntitys(entitys);
			return entitys;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 当逾期结果时，设置表内表外状态为完成
	 * @param entity 表内表外实体
	 */
	private void setTaboutside2finish(TaboutsideEntity entity,UserEntity user){
		entity.setPlanId(null);/*将首期逾期还款计划ID设为 null ，不逾期*/
		BeanUtil.setModifyInfo(user, entity);
	}
	
	private TaboutsideEntity createTaboutsideEntity(DataTable planDt,int index,UserEntity user){
		TaboutsideEntity entity = new TaboutsideEntity();
		//contractId,planId,monthPharses,totalPharses,amounts,iamounts,mamounts,pamounts,damounts
		setTaboutsideEntity(planDt, index,entity);
		BeanUtil.setCreateInfo(user, entity);
		return entity;
	}

	private void setTaboutsideEntity(DataTable planDt, int index,TaboutsideEntity entity) {
		Long contractId = planDt.getLong(index, "contractId");
		Long planId = planDt.getLong(index, "planId");
		Integer monthPharses = planDt.getInteger(index, "monthPharses");
		Integer totalPharses = planDt.getInteger(index, "totalPharses");
		Double amounts= planDt.getDouble(index, "amounts");
		Double iamounts = planDt.getDouble(index, "iamounts");
		Double mamounts = planDt.getDouble(index, "mamounts");
		Double pamounts = planDt.getDouble(index, "pamounts");
		Double damounts = planDt.getDouble(index, "damounts");
		entity.setContractId(contractId);
		entity.setPlanId(planId);
		entity.setMonthPharses(monthPharses);
		entity.setTotalPharses(totalPharses);
		entity.setAmounts(BigDecimalHandler.roundToBigDecimal(amounts, 2));
		entity.setIamounts(BigDecimalHandler.roundToBigDecimal(iamounts, 2));
		entity.setMamounts(BigDecimalHandler.roundToBigDecimal(mamounts, 2));
		entity.setPamounts(BigDecimalHandler.roundToBigDecimal(pamounts, 2));
		entity.setDamounts(BigDecimalHandler.roundToBigDecimal(damounts, 2));
		
	}
	/**
	 * 设置本月收款期次和五级分类
	 * @param entity
	 * @param contractId
	 * @param planList
	 */
	private void setMonthPharsesAndRiskLevel(TaboutsideEntity entity,Long contractId,List<PlanEntity> planList){
		for(PlanEntity planEntity : planList){
			Long _contractId = planEntity.getContractId();
			if(contractId.equals(_contractId)){
				Date xpayDate = planEntity.getXpayDate();
				if(DateUtil.eqSameYearMonth(xpayDate, new Date())){
					Integer monthPharses = planEntity.getPhases();
					entity.setMonthPharses(monthPharses);
				}
				Long id = planEntity.getId();
				Long planId = entity.getPlanId();
				if(id.equals(planId)){
					Long riskLevelId = getRiskLevel(planEntity);
					entity.setFlevel(riskLevelId);
				}
			}
		}
	}
	
	/**
	 * 获取五级分类ID
	 * @param planEntity
	 * @return
	 */
	private Long getRiskLevel(PlanEntity planEntity){
		Long riskLevelId = 0L;
		int pdays = planEntity.getPdays();
		int ratedays = planEntity.getRatedays();
		if(pdays == 0 && ratedays == 0) return riskLevelId;
		int maxlateDays = pdays > ratedays ? pdays : ratedays;
		if(null == riskLevelDt || riskLevelDt.getRowCount() == 0) return riskLevelId;
		for(int i=0,count=riskLevelDt.getRowCount(); i<count; i++){
			int mindays = riskLevelDt.getInteger(i, "mindays");
			int maxdays = riskLevelDt.getInteger(i, "maxdays");
			if(maxlateDays > mindays && maxlateDays <= maxdays){
				riskLevelId = riskLevelDt.getLong(i, "id");
				break;
			}
			if(maxlateDays > mindays && maxdays == -1){
				riskLevelId = riskLevelDt.getLong(i, "id");
				break;
			}
		}
		return riskLevelId;
	}
	
	
	/**
	 * 计算处理逾期还款计划列表
	 * @param planList
	 * @param lastDate
	 * @return 返回合同ID列表
	 */
	private String makeLatePlans(List<PlanEntity> planList,Date lastDate)throws ServiceException{
		/**--------- step 1 : 合并还款计划ID ---------**/
		String idsArr[] = getPlanAndContractIds(planList);
		String planIds = idsArr[0];
		String contractIds = idsArr[1];
		
		/**--------- step 2 : 获取合同中的 放款日期,1:罚息利率,2:滞纳金利率 ---------**/
		Map<Long,Object[]> contractMap = getContractDatas(contractIds);
		
		/**--------- step 3 : 获取实收金额数据 ---------**/
		Map<Long,List<Object[]>> recordsMap = getAmountRecords(planIds);
		
		 /**--------- step 4 : 计算罚息、滞纳金并更新还款计划列表 ---------**/
		 calculatePdAmounts(planList, lastDate, contractMap, recordsMap);
		 
		 return contractIds;
	}
	
	/**
	 * 批量计算罚息和滞纳金
	 * @param planList 逾期还款计划列表
	 * @param lastDate	截止日期
	 * @param contractMap 合同数据 Map
	 * @param recordsMap  实收数据 Map
	 */
	private void calculatePdAmounts(List<PlanEntity> planList,Date lastDate,Map<Long,Object[]> contractMap, Map<Long,List<Object[]>> recordsMap){
		for(PlanEntity entity : planList){
			Long id = entity.getId();
			Long contractId = entity.getContractId();
			Object[] contractData = contractMap.get(contractId);
			List<Object[]> records = recordsMap.get(id);
			calculatePdAmounts(entity,lastDate,contractData,records);
		}
	}
	
	/**
	 * 计算单条还款计划罚息和滞纳金
	 * @param planList 逾期还款计划对象
	 * @param lastDate	截止日期
	 * @param contractMap 合同数据
	 * @param recordsMap  实收数据
	 */
	private void calculatePdAmounts(PlanEntity planEntity,Date lastDate,Object[] contractData, List<Object[]> records){
		//payDate,urate,frate
		Date payDate = (Date)contractData[0];
		Double urate = (Double)contractData[1];
		Double frate = (Double)contractData[2];
		double[] minAmounts = getMinAmounts(payDate);
		double[] arrTotalAmounts = sumRealAmounts(records);
		if(null != urate && urate>0){/*罚息利率大于0时*/
			double minpenAmount = minAmounts[0];
			calculatePenAmount(planEntity, lastDate, records, urate, minpenAmount,arrTotalAmounts);
		}
		if(null != frate && frate>0){/*滞纳金利率大于0时*/
			double mindelAmount = minAmounts[1];
			calculateDelAmount(planEntity, lastDate, records, frate, mindelAmount,arrTotalAmounts);
		}
	}
	
	/**
	 * 计算罚息金额
	 * @param planEntity
	 * @param lastDate
	 * @param records
	 * @param urate
	 * @param minpenAmount
	 */
	private void calculatePenAmount(PlanEntity planEntity,Date lastDate,List<Object[]> records,
			double urate,double minpenAmount,double[] arrTotalAmounts){
		Integer latedays = 0;
		Integer rateldays = 0;
		Double zamount = BigDecimalHandler.sub(planEntity.getPrincipal(), planEntity.getYprincipal());
		Double zrateamount = BigDecimalHandler.sub(planEntity.getInterest(), planEntity.getYinterest());
		Double zsumamount = BigDecimalHandler.add(zamount, zrateamount);
		Date startDate = planEntity.getXpayDate();
		Date endDate = null;
		
		if(zsumamount>0d){	//应收本息合计>0 要计算罚息
			double sumamounts = BigDecimalHandler.add(zsumamount, arrTotalAmounts[0]);
			double sumpamounts = 0d;
			if(null != records && records.size() > 0){
				for(Object[] record : records){
					 endDate = (Date)record[0];
					double amount = (Double)record[1];
					double rateamount = (Double)record[2];
					 latedays = DateUtil.calculateLimitDate(startDate, endDate, DateUtil.DAY);
					 double currPenAmount = getAmountByFormula(urate, latedays, sumamounts);
					 sumpamounts = BigDecimalHandler.add(sumpamounts, currPenAmount);	//计算罚息金额
					 sumamounts = BigDecimalHandler.sub(sumamounts, BigDecimalHandler.add(amount, rateamount));
					 startDate = endDate;
					 if(sumamounts <= 0) break;
				}
			}
		
			latedays = 0;
			boolean flag = false;  //--> 用来标识当前payDate 是否小于最后一次收款日期 startDate ; 如果小于则为 true ,反之为 false
			if(sumamounts>0){
				 latedays = DateUtil.calculateLimitDate(startDate, lastDate, DateUtil.DAY);
				 if(latedays<0){
					 flag = true;
					 latedays=0; 
				 } 
				 sumpamounts = BigDecimalHandler.add(sumpamounts, getAmountByFormula(urate, latedays, sumamounts));	//计算罚息金额
			}
			double penAmount=0d;
		    if (latedays > 0 && sumpamounts <= minpenAmount){
		    	penAmount = minpenAmount;
            }else {
            	penAmount =  sumpamounts;
            }
		    penAmount = BigDecimalHandler.round(penAmount, 2);
		    planEntity.setPenAmount(BigDecimalHandler.get(penAmount));
			 
			 //算本金和利息逾期天数
		    endDate = flag ? startDate : lastDate;
			latedays = DateUtil.calculateLimitDate(planEntity.getXpayDate(), endDate, DateUtil.DAY);
			if(latedays<0) latedays = 0;
			rateldays = latedays;
			
			if(zamount<=0d){	//应收本金为0时，本金逾期天数为0
				latedays = 0;
			}
			if(zrateamount<=0d){	//应收利息为0时，利息逾期天数为0
				rateldays = 0;
			}
		}
		planEntity.setPdays(latedays);
		planEntity.setRatedays(rateldays);
	}

	/**
	 * 根据公式计算罚息或滞纳金金额
	 * @param urate
	 * @param latedays
	 * @param sumamounts
	 * @return
	 */
	private double getAmountByFormula(double urate, Integer latedays,double sumamounts) {
		return sumamounts * (urate/SysConstant.PERCENT_DENOMINATOR) * latedays;
	}
	
	/**
	 * 计算滞纳金额
	 * @param planEntity
	 * @param lastDate
	 * @param records
	 * @param urate
	 * @param minpenAmount
	 */
	private void calculateDelAmount(PlanEntity planEntity,Date lastDate,List<Object[]> records,
			double frate,double mindelAmount,double[] arrTotalAmounts){
		Integer mgrdays=0;
		Date startDate;
		Date endDate;
		double zmgramount = BigDecimalHandler.sub(planEntity.getMgrAmount(), planEntity.getYmgrAmount());
		if(zmgramount>0){//应收管理费>0 要计算滞纳金
			double sumMamounts = BigDecimalHandler.add(zmgramount, arrTotalAmounts[1]);
			double sumOamounts = 0d;
			startDate = planEntity.getXpayDate();
			if(null != records && records.size() > 0){
				for(Object[] record : records){
					endDate = (Date)record[0];
					double mAmount = (Double)record[2];
					mgrdays = DateUtil.calculateLimitDate(startDate, endDate, DateUtil.DAY);
					 if(mgrdays<0) mgrdays=0;
					 double delAmount = getAmountByFormula(frate,mgrdays,sumMamounts);//计算罚息金额
					sumOamounts = BigDecimalHandler.add(sumOamounts, delAmount);
					sumMamounts = BigDecimalHandler.sub(sumMamounts, mAmount);
					 startDate = endDate;
					 if(sumMamounts <= 0 ) break;
				}
			}
			boolean flag = false;  //--> 用来标识当前payDate 是否小于最后一次收款日期 startDate ; 如果小于则为 true ,反之为 false
			if(sumMamounts>0){
				mgrdays = DateUtil.calculateLimitDate(startDate, lastDate, DateUtil.DAY);
				 if(mgrdays<0){
					 flag = true;
					 mgrdays=0;
				 }
				 double delAmount = getAmountByFormula(frate,mgrdays,sumMamounts);//计算滞纳金金额
				 sumOamounts = BigDecimalHandler.add(sumOamounts, delAmount);
			}
			double overamount= (mgrdays>0 && sumOamounts <= mindelAmount) ? mindelAmount : sumOamounts;
		    overamount = BigDecimalHandler.round(overamount, 2);
			planEntity.setDelAmount(BigDecimalHandler.get(overamount));
			 //算管理费逾期天数
			endDate = flag ? startDate : lastDate;
			mgrdays = DateUtil.calculateLimitDate(planEntity.getXpayDate(), endDate, DateUtil.DAY);
			if(mgrdays<0) mgrdays = 0;
			planEntity.setMgrdays(mgrdays);
		}else{	//应收管理费为0时，管理费逾期天数也为0
			planEntity.setMgrdays(0);
		}
	}
	
	/**
	 * 获取实收合计 以数组形式返回合计金额
	 * 分别是： [实收本金合计,实收管理费合计,实收罚息合计,实收滞纳金合计]
	 * @param records 收款记录列表对象
	 * @return 
	 */
	public double[] sumRealAmounts(List<Object[]> records){
		double sumamount=0d,summamount=0d,sumpamount=0d,sumoamount=0d;
		if(null == records || records.size() == 0) return new double[]{sumamount,summamount,sumpamount,sumoamount};
		//cat,rat,mat,pat,dat
		for(Object[] record : records){
			double amount = (Double)record[1];
			double rateamount = (Double)record[2];
			double artotalAmount = BigDecimalHandler.add(amount, rateamount);
			sumamount = BigDecimalHandler.add(sumamount, artotalAmount);
			double mamount = (Double)record[3];
			summamount = BigDecimalHandler.add(summamount, mamount);
			double pamount= (Double)record[4];
			sumpamount = BigDecimalHandler.add(sumpamount, pamount);
			double oamount = (Double)record[5];
			sumoamount = BigDecimalHandler.add(sumoamount, oamount);
		}
		
		return new double[]{sumamount,summamount,sumpamount,sumoamount};
	}
	

	private Map<Long,List<Object[]>> getAmountRecords(String planIds) throws ServiceException{
		 Map<Long,List<Object[]>> recordsMap = new HashMap<Long, List<Object[]>>();
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("bussTag", BussStateConstant.AMOUNTRECORDS_BUSSTAG_4+","+BussStateConstant.AMOUNTRECORDS_BUSSTAG_5+","+BussStateConstant.AMOUNTRECORDS_BUSSTAG_6);
		params.put("invoceIds", planIds);
		try {
			DataTable dtRecords = amountRecordsDao.getLateRecords(params);
			if(null == dtRecords || dtRecords.getRowCount() == 0) return recordsMap;
			for(int i=0,count=dtRecords.getRowCount(); i<count; i++){
				//invoceId,cat,rat,mat,pat,dat,rectDate
				Long invoceId = dtRecords.getLong(i, "invoceId");
				Date rectDate = dtRecords.getDate(i, "rectDate");
				Double cat = dtRecords.getDouble(i, "cat");
				Double rat = dtRecords.getDouble(i, "rat");
				Double mat = dtRecords.getDouble(i, "mat");
				Double pat = dtRecords.getDouble(i, "pat");
				Double dat = dtRecords.getDouble(i, "dat");
				Object[] data = new Object[]{rectDate,cat,rat,mat,pat,dat};
				if(recordsMap.containsKey(invoceId)){
					recordsMap.get(invoceId).add(data);
				}else{
					List<Object[]> datas = new ArrayList<Object[]>();
					datas.add(data);
					recordsMap.put(invoceId, datas);
				}
			}
			return recordsMap;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 获取还款计划ID和合同ID字符串列表
	 * @param planList 还款计划列表
	 * @return
	 */
	private String[] getPlanAndContractIds(List<PlanEntity> planList){
		StringBuffer sbPlanId = new StringBuffer();
		StringBuffer sbContractId = new StringBuffer();
		Map<Long,Long> map = new HashMap<Long, Long>();
		for(PlanEntity entity : planList){
			Long id = entity.getId();
			Long contractId = entity.getContractId();
			sbPlanId.append(id).append(",");
			if(map.containsKey(contractId)) continue;
			map.put(contractId, contractId);
			sbContractId.append(contractId).append(",");
		}
	
		String ids = StringHandler.RemoveStr(sbPlanId);
		String contractIds = null;
		if(null != map && map.size() > 0){
			String _contractIds = map.keySet().toString();
			contractIds = _contractIds.replace("[", "").replace("]", "");
		}
		return new String[]{ids,contractIds};
	}
	
	/**
	 * 根据逾期还款计划ID获取其合同数据
	 * 
	 * @param contractIds 合同ID列表
	 * @return 返回 Map 对象  格式： key = 合同ID, value = Object[] 元素[ 0:合约放款日,1:罚息利率,2:滞纳金利率]
	 * @throws ServiceException
	 */
	private Map<Long,Object[]> getContractDatas(String contractIds)throws ServiceException{
		Map<Long,Object[]> contractMap = new HashMap<Long, Object[]>();
		try {
			DataTable dtContract = loanContractDao.getRates(contractIds);
			if(null == dtContract || dtContract.getRowCount() == 0) return contractMap;
			for(int i=0,count=dtContract.getRowCount(); i<count; i++){
				Long contractId = dtContract.getLong(i, "id");
				Date payDate = dtContract.getDate(i, "payDate");
				Double urate = dtContract.getDouble(i, "urate");
				Double frate = dtContract.getDouble(i, "frate");
				contractMap.put(contractId, new Object[]{payDate,urate,frate});
			}
			return contractMap;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	} 
	
	/**
	 * 根据合同中合约放款日获取 罚息、滞纳金 最低收费标准
	 * @param loandt	合约放款日
	 * @return 返回数组 [罚息，滞纳金]
	 */
	public double[] getMinAmounts(Date payDate){
		double[] poamounts = {minPenAmount,minDelAmount};
		if(null == minAmountDt || minAmountDt.getRowCount() == 0) return poamounts;
		for(int i=minAmountDt.getRowCount()-1; i>=0; i--){
			Date opdate = minAmountDt.getDate(i, "opdate");
			double mpamount = minAmountDt.getDouble(i, "mpamount");
			double moamount = minAmountDt.getDouble(i, "moamount");
			int result = payDate.compareTo(opdate);
			if(result>=0){
				poamounts[0] = mpamount;
				poamounts[1] = moamount;
				break;
			}
		}
		return poamounts;
	}
	
	
	
	/**
	 * 加载系统参数
	 * @throws ServiceException
	 */
	private void loadSysParams() throws ServiceException {
		batchCount = BATCH_DATAS_COUNT;
		minPenAmount = 0d;
		minDelAmount = 0d;
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		 params.put("recode", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + 
				 "'"+SysparamsConstant.BATCH_LATE_DATAS_COUNT+
				 "','"+SysparamsConstant.MIN_PEN_AMOUNT+
				 "','"+SysparamsConstant.MIN_DEL_AMOUNT+"'");
		 params.put("isenabled", SysConstant.OPTION_ENABLED);
		 List<SysparamsEntity> sysParams = sysparamsService.getEntityList(params);
		 for(SysparamsEntity sysParam : sysParams){
			 String recode = sysParam.getRecode();
			 String val = sysParam.getVal();
			 if(!StringHandler.isValidStr(recode)) continue;
			 if(!StringHandler.isValidStr(val)) continue;
			 if(SysparamsConstant.BATCH_LATE_DATAS_COUNT.equals(recode)){
				 batchCount = Integer.parseInt(val);
			 }else if(SysparamsConstant.MIN_PEN_AMOUNT.equals(recode)){
				 minPenAmount = Double.parseDouble(val);
			 }else if(SysparamsConstant.MIN_DEL_AMOUNT.equals(recode)){
				 minDelAmount = Double.parseDouble(val);
			 }
		 }
	}
	
	/**
	 * 加载五级分类数据
	 * @throws ServiceException
	 */
	private void loadRiskLevels() throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		 params.put("isenabled", SysConstant.OPTION_ENABLED);
		 riskLevelDt = riskLevelService.getResultList(params);
	}

	/**
	 * 加载最低罚息，滞纳金设定数据列表
	 * @throws ServiceException
	 */
	private void loadMinAmounts() throws ServiceException {
		 try {
			minAmountDt = minAmountDao.getEnabledAmounts();
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	
	@Override
	public DataTable repDetail(SHashMap<String, Object> map, int start,
			int limit) throws ServiceException {
		 try {
			return overdueDeductDao.RepDetail(map, start, limit);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	 }
	
}
