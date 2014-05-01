package com.cmw.service.impl.fininter;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.entity.IdBaseEntity;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.BigDecimalHandler;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.AmountRecordsDaoInter;
import com.cmw.dao.inter.finance.CasualRecordsDaoInter;
import com.cmw.dao.inter.finance.FreeRecordsDaoInter;
import com.cmw.dao.inter.finance.LoanInvoceDaoInter;
import com.cmw.dao.inter.finance.PrepaymentRecordsDaoInter;
import com.cmw.dao.inter.fininter.AmountLogDaoInter;
import com.cmw.dao.inter.sys.AccountDaoInter;
import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.CasualRecordsEntity;
import com.cmw.entity.finance.FreeRecordsEntity;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.entity.finance.PrepaymentRecordsEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.fininter.BussFinCfgEntity;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.entity.fininter.VoucherOplogEntity;
import com.cmw.entity.fininter.VoucherTempEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.impl.fininter.external.FinSysFactory;
import com.cmw.service.impl.fininter.external.FinSysService;
import com.cmw.service.impl.fininter.external.generic.VoucherModel;
import com.cmw.service.impl.fininter.external.generic.VoucherTransform;
import com.cmw.service.inter.fininter.AmountLogService;
import com.cmw.service.inter.fininter.BussFinCfgService;
import com.cmw.service.inter.fininter.FinSysCfgService;
import com.cmw.service.inter.fininter.VoucherOplogService;
import com.cmw.service.inter.fininter.VoucherTempService;


/**
 * 实收金额日志  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="实收金额日志业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("amountLogService")
public class AmountLogServiceImpl extends AbsService<AmountLogEntity, Long> implements  AmountLogService {
	@Autowired
	private AmountLogDaoInter amountLogDao;
	@Autowired
	private LoanInvoceDaoInter loanInvoceDao;
	@Autowired
	private FreeRecordsDaoInter freeRecordsDao;
	@Autowired
	private AmountRecordsDaoInter amountRecordsDao;
	@Autowired
	private PrepaymentRecordsDaoInter prepaymentRecordsDao;
	@Autowired
	private CasualRecordsDaoInter casualRecordsDao;
	@Autowired
	private AccountDaoInter accountDao;
	
	@Resource(name="bussFinCfgService")
	private BussFinCfgService bussFinCfgService;
	@Resource(name="voucherTempService")
	private VoucherTempService voucherTempService;
	@Resource(name="voucherTransform")
	private VoucherTransform voucherTransform;
	@Resource(name="finSysCfgService")
	private FinSysCfgService finSysCfgService;
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	@Resource(name="voucherOplogService")
	private VoucherOplogService voucherOplogService;
	@Override
	public GenericDaoInter<AmountLogEntity, Long> getDao() {
		return amountLogDao;
	}
	/**
	 * 根据不同的标示来查询业务表中的数据
	 */
	
	@Override
	public DataTable bussTaglist(SHashMap<String, Object> map, int start,int limit) {
		try {
			DataTable dt = amountLogDao.bussTaglist(map, start,  limit);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	@Override
	public void saveVouchers(Map<AmountLogEntity,DataTable> logDataMap,SHashMap<String, Object> map) throws ServiceException{
		SHashMap<String,Object> params = new SHashMap<String, Object>();
		Long sysId = map.getvalAsLng("sysId");
		String vtempCode = map.getvalAsStr("vtempCode");
		params.put("sysId", sysId);
		params.put("vtempCode", vtempCode);
		UserEntity user = (UserEntity)map.get(SysConstant.USER_KEY);
		params.put(SysConstant.USER_KEY, user);
		List<VoucherModel> vouchers = voucherTransform.convert(logDataMap, params);
		if(null == vouchers || vouchers.size() == 0) return;
		
		FinSysService fservice = getFinSysService(sysId);
		params.put(SysConstant.USER_KEY, user);
		fservice.saveVouchers(vouchers, params);
		List<AmountLogEntity> amountLogList = new ArrayList<AmountLogEntity>(logDataMap.keySet());
		for(AmountLogEntity amountLogEntity : amountLogList){
			Long aId = amountLogEntity.getId();
			for(VoucherModel model : vouchers){
				String voucherId = model.getVoucherId();
				Long amountLogId = model.getAmountLogId();
				if(aId.equals(amountLogId)){
					amountLogEntity.setRefId(voucherId);
					BeanUtil.setModifyInfo(user, amountLogEntity);
					break;
				}
			}
		}
		amountLogService.batchUpdateEntitys(amountLogList);
	}
	
	/** 
	 * 	重新生成财务凭证
	 */
	@Override
	public void saveVouchers(SHashMap<String, Object> map)
			throws ServiceException {
		SHashMap<String,Object> params = new SHashMap<String, Object>();
		String amountLogIds = map.getvalAsStr("amountLogIds");
		String opLogIds = map.getvalAsStr("opLogIds");
		Long sysId = map.getvalAsLng("sysId");
		String vtempCode = map.getvalAsStr("vtempCode");
		params.put("sysId", sysId);
		params.put("vtempCode", vtempCode);
		UserEntity user = (UserEntity)map.get(SysConstant.USER_KEY);
		params.put(SysConstant.USER_KEY, user);
		Map<AmountLogEntity,DataTable> logDataMap = getBussAmountRecords(amountLogIds);
		if(null == logDataMap || logDataMap.size() == 0) return;
		saveVouchers(logDataMap,params);
		delOldOpLogs(opLogIds);
	}
	/**
	 * 删除旧的失败日志
	 * @param logIds
	 * @throws ServiceException
	 */
	private void delOldOpLogs(String logIds)
			throws ServiceException {
		if(!StringHandler.isValidStr(logIds)) return;
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("id", SqlUtil.LOGIC_IN+SqlUtil.LOGIC+logIds);
		List<VoucherOplogEntity> voucherOplogList = voucherOplogService.getEntityList(params);
		if(!voucherOplogList.isEmpty()){
			for(VoucherOplogEntity voucherOplogEntity : voucherOplogList){
				voucherOplogEntity.setIsenabled((byte) SysConstant.OPTION_DEL);
				voucherOplogEntity.setStatus(BussStateConstant.VOUCHEROPLOG_STATUS_0);
			}
		}
		voucherOplogService.batchSaveOrUpdateEntitys(voucherOplogList);
	}

	private FinSysService getFinSysService(Long sysId)
			throws ServiceException {
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("sysId", sysId);
		BussFinCfgEntity bussFinCfgEntity = bussFinCfgService.getEntity(params);
		if(null == bussFinCfgEntity) throw new ServiceException(ServiceException.BUSSFINCFGENTITY_IS_NULL);
		FinSysCfgEntity entity = finSysCfgService.getEntity(bussFinCfgEntity.getFinsysId());
		if(null == entity || (null != entity.getIsenabled() &&
			entity.getIsenabled().intValue() != SysConstant.OPTION_ENABLED)) throw new ServiceException(ServiceException.FINSYSCFGENTITY_IS_NULL);
		String finsysCode = entity.getCode();
		FinSysService fservice = FinSysFactory.getInstance(finsysCode);
		return fservice;
	}
	
//	
//	@Override
//	@Transactional
//	public Map<AmountLogEntity,DataTable> saveByBussTag(SHashMap<String, Object> params) throws ServiceException {
//		String ids = params.getvalAsStr("ids");
//		Long sysId = params.getvalAsLng("sysId");
//		Integer bussTag = params.getvalAsInt("bussTag");
//		String vtempCode = params.getvalAsStr("vtempCode");
//		VoucherTempEntity vtempEntity = getVoucherTempByCode(sysId, vtempCode);
//		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
//		if(null == bussTag) throw new ServiceException("bussTag  is null !");
//		Map<AmountLogEntity,DataTable> dataMap = new HashMap<AmountLogEntity, DataTable>();
//		try{
//			String tabEntity = "AmountRecordsEntity";
//			switch (bussTag.intValue()) {
//			case BussStateConstant.AMOUNTLOG_BUSSTAG_0:{
//				tabEntity = "LoanInvoceEntity";
//				dataMap = createAmountLogsByLoanInvoce(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_1:{
//				tabEntity = "FreeRecordsEntity";
//				dataMap = createAmountLogsByFreeRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_2:{
//				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_3:{
//				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_4:{
//				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_5:{
//				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_6:{
//				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_7:{
//				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_8:{
//				dataMap =  createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_9:{
//				tabEntity = "PrepaymentRecordsEntity";
//				dataMap =  createAmountLogsByPrepaymentRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}case BussStateConstant.AMOUNTLOG_BUSSTAG_10:{
//				dataMap =  createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}
//			case BussStateConstant.AMOUNTLOG_BUSSTAG_11:{
//				dataMap =  createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
//				break;
//			}
//			default:
//				throw new ServiceException("bussTag  is no support !");
//			}
//			save(tabEntity,sysId,dataMap);
//			return dataMap;
//		}catch (DaoException e) {
//			e.printStackTrace();
//			throw new ServiceException(e);
//		}
//	}
//	
	
	
	
	
	@Override
	@Transactional
	public Map<AmountLogEntity,DataTable> saveByBussTag(SHashMap<String, Object> params) throws ServiceException {
		String ids = params.getvalAsStr("ids");
		Long sysId = params.getvalAsLng("sysId");
		Integer bussTag = params.getvalAsInt("bussTag");
		String vtempCode = params.getvalAsStr("vtempCode");
		VoucherTempEntity vtempEntity = getVoucherTempByCode(sysId, vtempCode);
		UserEntity user = (UserEntity)params.getvalAsObj(SysConstant.USER_KEY);
		if(null == bussTag) throw new ServiceException("bussTag  is null !");
		Map<AmountLogEntity,DataTable> dataMap = new HashMap<AmountLogEntity, DataTable>();
		try{
			String tabEntity = "AmountRecordsEntity";
			switch (bussTag.intValue()) {
			case BussStateConstant.AMOUNTLOG_BUSSTAG_0:{
				tabEntity = "LoanInvoceEntity";
				dataMap = createAmountLogsByLoanInvoce(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_1:{
				tabEntity = "FreeRecordsEntity";
				dataMap = createAmountLogsByFreeRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_2:{
				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_3:{
				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_4:{
				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_5:{
				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_6:{
				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_7:{
				dataMap = createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_8:{
				dataMap =  createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_9:{
				tabEntity = "PrepaymentRecordsEntity";
				dataMap =  createAmountLogsByPrepaymentRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_10:{
				dataMap =  createAmountLogsByAmountRecord(ids, user, bussTag,vtempEntity);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_11:{
				tabEntity = "FundsWaterEntity";
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_12:{/*随借随还*/
				tabEntity = "PrepaymentRecordsEntity";
				dataMap =  createAmountLogsByCasualRecords(ids, user, bussTag,vtempEntity);
				break;
			}
			default:
				throw new ServiceException("bussTag  is no support !");
			}
			save(tabEntity,sysId,dataMap);
			return dataMap;
		}catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	
	
	
	/**
	 * 保存实收金额日志并更新相应的记录表数据
	 * @param tabEntity
	 * @param sysId
	 * @param dataMap
	 * @throws DaoException
	 */
	private void save(String tabEntity,Long sysId, Map<AmountLogEntity,DataTable>dataMap) throws DaoException{
		if(null == dataMap || dataMap.size() == 0) return;
		Set<AmountLogEntity> amountLogSet = dataMap.keySet();
		setAccountId2Map(amountLogSet);
		fillAccountRefId2Map();
		setAmountLogReffinAccountId(dataMap);
		List<AmountLogEntity> list = new ArrayList<AmountLogEntity>(amountLogSet.size());
		for(AmountLogEntity amountLog : amountLogSet){
			Long accountId = amountLog.getAccountId();
			String reffinAccountId = accountMap.get(accountId);
			if(StringHandler.isValidStr(reffinAccountId)){
				amountLog.setReffinAccountId(reffinAccountId);
			}
			amountLog.setSysId(sysId);
			list.add(amountLog);
		}
		
		amountLogDao.batchSaveEntitys(list);
	
		
		/*---------  更新记录表中的  fcnumber 使其与日志表关联  --------*/
		for(AmountLogEntity amountLog : list){
			Long amountLogId = amountLog.getId();
			DataTable dt = dataMap.get(amountLog);
			if(null == dt || dt.getRowCount() == 0) continue;
			StringBuilder sb = new StringBuilder();
			for(int i=0,count=dt.getRowCount(); i < count; i++){
				String id = dt.getString(i, "id");
				sb.append(id).append(",");
			}
			String ids = StringHandler.RemoveStr(sb);
			if(!StringHandler.isValidStr(ids)) continue;
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("amountLogId", amountLogId);
			params.put("userId", amountLog.getCreator());
			params.put("ids", ids);
			amountLogDao.updateRecordsAmountLogId(tabEntity, params);
		}
	}
	
	/**
	 * 根据多个实收金额日志ID业务标识获取实收金额日志和收款记录数据并以 Map 对象返回
	 * @param alList	原实收金额日志列表
	 * @return 返回 Map<AmountLogEntity,DataTable> 对象
	 * @throws ServiceException 
	 */
	
	public Map<AmountLogEntity,DataTable> getBussAmountRecords(String amountLogIds) throws ServiceException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + amountLogIds);
		List<AmountLogEntity> alList = getEntityList(map);
		Map<AmountLogEntity,DataTable> datasMap = new HashMap<AmountLogEntity,DataTable>();
		for(AmountLogEntity alEntity : alList){
			Long amountLogId = alEntity.getId();
			Integer bussTag = alEntity.getBussTag();
			DataTable dt = getBussAmountRecords(amountLogId.toString(), bussTag);
			datasMap.put(alEntity, dt);
		}
		return datasMap;
	}
	
	/**
	 *  根据 SQL 语句获取 DataTable 对象
	 * @param sql
	 * @param colNames
	 * @return
	 * @throws DaoException
	 */
	@Override
	public DataTable getDtBySql(String sql, String colNames) throws ServiceException{
		try {
			return amountLogDao.getDtBySql(sql, colNames);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	 
	public DataTable getBussAmountRecords(String amountLogIds,Integer bussTag) throws ServiceException{
		DataTable dt = null;
		int offset = -1;
		int pageSize = -1;
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put(SqlUtil.TOTALCOUNT_KEY, false);
		params.put("fcnumber", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + amountLogIds);
		try{
			switch (bussTag.intValue()) {
			case BussStateConstant.AMOUNTLOG_BUSSTAG_0:{
				dt = loanInvoceDao.getDsByAmountLogId(params, offset, pageSize);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_1:{
				dt = freeRecordsDao.getDsByAmountLogId(params, offset, pageSize);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_2:
			case BussStateConstant.AMOUNTLOG_BUSSTAG_3:
			case BussStateConstant.AMOUNTLOG_BUSSTAG_4:
			case BussStateConstant.AMOUNTLOG_BUSSTAG_5:
			case BussStateConstant.AMOUNTLOG_BUSSTAG_6:
			case BussStateConstant.AMOUNTLOG_BUSSTAG_7:
			case BussStateConstant.AMOUNTLOG_BUSSTAG_8:{
				dt = amountRecordsDao.getDsByAmountLogId(params, offset, pageSize);
				break;
			}case BussStateConstant.AMOUNTLOG_BUSSTAG_9:{
				dt = prepaymentRecordsDao.getDsByAmountLogId(params, offset, pageSize);
				break;
			}default:
				throw new ServiceException("bussTag  is no support !");
			}
			return dt;
		}catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	private Map<Long,String> accountMap = new HashMap<Long, String>();
	/**
	 * 创建放款记录的实收金额日志对象
	 * @param ids	放款单ID列表
	 * @param user	当前用户
	 * @return	返回 实收金额日志对象
	 * @throws DaoException
	 */
	private Map<AmountLogEntity,DataTable>  createAmountLogsByLoanInvoce(String ids,UserEntity user,Integer bussTag,VoucherTempEntity vtempEntity) throws DaoException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		long totalCount = loanInvoceDao.getTotals(map);
		if(totalCount == 0) return null;
		Map<AmountLogEntity,DataTable> dataMap = new HashMap<AmountLogEntity, DataTable>();
		Date realDate = null;
		Long accountId = null;
		int custCount = 1;
		Integer tactics = vtempEntity.getTactics();
		if(tactics.intValue() == BussStateConstant.VOUCHERTEMP_TACTICS_1){/*一笔业务生成一张凭证*/
			List<LoanInvoceEntity> loanInvoces = loanInvoceDao.getEntityList(map);
			for(LoanInvoceEntity loanInvoce : loanInvoces){
				realDate = loanInvoce.getRealDate();
				accountId = loanInvoce.getAccountId();
				AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
				BigDecimal amount = loanInvoce.getPayAmount();
				amountLog.setAmount(amount);
				amountLog.setSumamount(amount);
				amountLog.setCategory(BussStateConstant.AMOUNTLOG_CATEGORY_0);
				String invoceIds = loanInvoce.getId().toString();
				amountLog.setInvoceIds(invoceIds);
				DataTable recordDt = convertEntity2DataTable(loanInvoce);
				dataMap.put(amountLog, recordDt);
			}
		}else{
			Integer maxcount = vtempEntity.getMaxcount();
			if(null == maxcount) maxcount = 500;
			if(totalCount > maxcount){/*当大于批处理分录数量时*/
				int offset = 0;
				int pageSize = maxcount;
				do{
					List<LoanInvoceEntity> loanInvoces = loanInvoceDao.getEntityList(map,offset,pageSize);
					if(null == loanInvoces || loanInvoces.size() == 0) break;
					setBatchLoanInvoces(user, bussTag, dataMap,loanInvoces);
					offset += pageSize;
				}while(true);
			
			}else{
				List<LoanInvoceEntity> loanInvoces = loanInvoceDao.getEntityList(map);
				setBatchLoanInvoces(user, bussTag, dataMap,loanInvoces);
			}
		}
		return dataMap;
	}

	/**
	 * 将放/收款帐号放入Map对象中
	 * @param entity
	 */
	private void setAccountId2Map(Set<AmountLogEntity> amountLogSet){
		 for(AmountLogEntity entity : amountLogSet){
			 Long accountId = entity.getAccountId();
			 accountMap.put(accountId, null);
		 }
	}
	
	/**
	 * 将放/收款帐号对应的财务系统银行帐号ID作为 value 放入Map对象中
	 * @param entity
	 * @throws DaoException 
	 */
	private void fillAccountRefId2Map() throws DaoException{
		if(null == accountMap || accountMap.size() == 0) return;
		Set<Long> accountIdKeys = accountMap.keySet();
		Object[] accountIds = accountIdKeys.toArray();
		String accountIdsStr = StringHandler.join(accountIds);
		String hql = "select id as accountId,refId from AccountEntity A where A.id in ("+accountIdsStr+")";
		DataTable dt = accountDao.getResultList(hql, -1, -1);
		for(int i=0,count=dt.getRowCount(); i<count; i++){
			Long accountId = dt.getLong(i,"accountId");
			String refId = dt.getString(i, "refId");
			if(accountMap.containsKey(accountId)) accountMap.put(accountId, refId);
		}
	}
	/**
	 * 设置实收金额日志财务银行帐号ID
	 * @param dataMap
	 */
	private void setAmountLogReffinAccountId(Map<AmountLogEntity,DataTable> dataMap){
		Set<AmountLogEntity> amountLogSet = dataMap.keySet();
		for(AmountLogEntity amountLog : amountLogSet){
			Long accountId = amountLog.getAccountId();
			String reffinAccountId = accountMap.get(accountId);
			if(!StringHandler.isValidStr(reffinAccountId)) continue;
			amountLog.setReffinAccountId(reffinAccountId);
		}
	}
	
	private void setBatchLoanInvoces(UserEntity user, Integer bussTag,
			Map<AmountLogEntity, DataTable> dataMap,
			List<LoanInvoceEntity> loanInvoces) {
		Date realDate = null;
		Long accountId = null;
		int custCount = loanInvoces.size();
		LoanInvoceEntity firstLoanInvoce = loanInvoces.get(0);
		DataTable recordDt = new DataTable();
		String columnNames = StringHandler.join(firstLoanInvoce.getFields());
		recordDt.setColumnNames(columnNames);
		List<Object> dataSource = new ArrayList<Object>();
		realDate = firstLoanInvoce.getRealDate();
		accountId = firstLoanInvoce.getAccountId();
		AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
		BigDecimal amount = null;
		StringBuilder sb = new StringBuilder();
		for(LoanInvoceEntity loanInvoce : loanInvoces){
			BigDecimal payAmount = loanInvoce.getPayAmount();
			amount = BigDecimalHandler.add2BigDecimal(amount, payAmount);
			dataSource.add(loanInvoce.getDatas());
			sb.append(loanInvoce.getId()).append(",");
		}
		String invoceIds = StringHandler.RemoveStr(sb);
		amountLog.setInvoceIds(invoceIds);
		amount = BigDecimalHandler.roundToBigDecimal(amount, BigDecimalHandler.DEFAULT_SCALE);
		amountLog.setAmount(amount);
		amountLog.setSumamount(amount);
		amountLog.setCategory(BussStateConstant.AMOUNTLOG_CATEGORY_0);
		dataMap.put(amountLog, recordDt);
	}

	private DataTable convertEntity2DataTable(IdBaseEntity entity){
		String[] fields = entity.getFields();
		Object[] data = entity.getDatas();
		String columnNames = StringHandler.join(fields);
		List<Object> dataSource = new ArrayList<Object>();
		dataSource.add(data);
		return new DataTable(dataSource, columnNames);
	}
	
	/**
	 * 创建放款手续费的实收金额日志对象
	 * @param ids	放款单ID列表
	 * @param user	当前用户
	 * @return	返回 实收金额日志对象
	 * @throws DaoException
	 */
	private Map<AmountLogEntity,DataTable> createAmountLogsByFreeRecord(String ids,UserEntity user,Integer bussTag,VoucherTempEntity vtempEntity) throws DaoException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		long totalCount = freeRecordsDao.getTotals(map);
		if(totalCount == 0) return null;
		Map<AmountLogEntity,DataTable> dataMap = new HashMap<AmountLogEntity, DataTable>();
		Date realDate = null;
		Long accountId = null;
		int custCount = 1;
		BigDecimal amount = null;
		Integer tactics = vtempEntity.getTactics();
		if(tactics.intValue() == BussStateConstant.VOUCHERTEMP_TACTICS_1){/*一笔业务生成一张凭证*/
			List<FreeRecordsEntity> records = freeRecordsDao.getEntityList(map);
			for(FreeRecordsEntity record : records){
				realDate = record.getRectDate();
				accountId = record.getAccountId();
				amount = record.getAmount();
				AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
				String invoceIds = record.getId().toString();
				amountLog.setInvoceIds(invoceIds);
				amountLog.setFamount(amount);
				amountLog.setSumamount(amount);
				amountLog.setCategory(BussStateConstant.AMOUNTLOG_CATEGORY_1);
				DataTable recordDt = convertEntity2DataTable(record);
				dataMap.put(amountLog, recordDt);
			}
		}else{
			Integer maxcount = vtempEntity.getMaxcount();
			if(null == maxcount) maxcount = 500;
			if(totalCount > maxcount){/*当大于批处理分录数量时*/
				int offset = 0;
				int pageSize = maxcount;
				do{
					List<FreeRecordsEntity> records = freeRecordsDao.getEntityList(map,offset,pageSize);
					if(null == records || records.size() == 0) break;
					setBatchFreeRecords(user, bussTag, dataMap,records);
					offset += pageSize;
				}while(true);
			
			}else{
				List<FreeRecordsEntity> records = freeRecordsDao.getEntityList(map);
				setBatchFreeRecords(user, bussTag, dataMap,records);
			}
		}
		return dataMap;
	}
	
	private void setBatchAmountRecords(UserEntity user, Integer bussTag,
			Map<AmountLogEntity, DataTable> dataMap,
			List<AmountRecordsEntity> records) {
		Date realDate = null;
		Long accountId = null;
		int custCount = records.size();
		AmountRecordsEntity firstObj = records.get(0);
		DataTable recordDt = new DataTable();
		String columnNames = StringHandler.join(firstObj.getFields());
		recordDt.setColumnNames(columnNames);
		List<Object> dataSource = new ArrayList<Object>();
		realDate = firstObj.getRectDate();
		accountId = firstObj.getAccountId();
		AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
		BigDecimal t_amount = new BigDecimal("0");
		BigDecimal t_ramount = new BigDecimal("0");
		BigDecimal t_mamount = new BigDecimal("0");
		BigDecimal t_pamount = new BigDecimal("0");
		BigDecimal t_oamount = new BigDecimal("0");
		BigDecimal t_famount = new BigDecimal("0");
		BigDecimal smuamount = new BigDecimal("0");
		StringBuilder sb = new StringBuilder();
		for(AmountRecordsEntity record : records){
			BigDecimal cat = record.getCat();
			BigDecimal rat = record.getRat();
			BigDecimal mat = record.getMat();
			BigDecimal pat = record.getPat();
			BigDecimal dat = record.getDat();
			BigDecimal fat = record.getFat();
			BigDecimal tat = record.getTat();
			
			t_amount = BigDecimalHandler.add2BigDecimal(t_amount, cat);
			t_ramount = BigDecimalHandler.add2BigDecimal(t_ramount, rat);
			t_mamount = BigDecimalHandler.add2BigDecimal(t_mamount, mat);
			t_pamount = BigDecimalHandler.add2BigDecimal(t_pamount, pat);
			t_oamount = BigDecimalHandler.add2BigDecimal(t_oamount, dat);
			t_famount = BigDecimalHandler.add2BigDecimal(t_famount, fat);
			smuamount = BigDecimalHandler.add2BigDecimal(smuamount, tat);
			
			dataSource.add(record.getDatas());
			sb.append(record.getId()).append(",");
		}
		t_amount = BigDecimalHandler.roundToBigDecimal(t_amount, BigDecimalHandler.DEFAULT_SCALE);
		t_ramount = BigDecimalHandler.roundToBigDecimal(t_ramount, BigDecimalHandler.DEFAULT_SCALE);
		t_mamount = BigDecimalHandler.roundToBigDecimal(t_mamount, BigDecimalHandler.DEFAULT_SCALE);
		t_pamount = BigDecimalHandler.roundToBigDecimal(t_pamount, BigDecimalHandler.DEFAULT_SCALE);
		t_oamount = BigDecimalHandler.roundToBigDecimal(t_oamount, BigDecimalHandler.DEFAULT_SCALE);
		t_famount = BigDecimalHandler.roundToBigDecimal(t_famount, BigDecimalHandler.DEFAULT_SCALE);
		smuamount = BigDecimalHandler.roundToBigDecimal(smuamount, BigDecimalHandler.DEFAULT_SCALE);
		
		String invoceIds = StringHandler.RemoveStr(sb);
		amountLog.setInvoceIds(invoceIds);
		amountLog.setAmount(t_amount);
		amountLog.setRamount(t_ramount);
		amountLog.setMamount(t_mamount);
		amountLog.setPamount(t_pamount);
		amountLog.setOamount(t_oamount);
		amountLog.setFamount(t_famount);
		amountLog.setSumamount(smuamount);
		Integer category = (bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_4) ? BussStateConstant.AMOUNTLOG_CATEGORY_3 : BussStateConstant.AMOUNTLOG_CATEGORY_2;
		amountLog.setCategory(category);
		dataMap.put(amountLog, recordDt);
	}
	
	/**
	 * 设置批量实收随借随还记录数据
	 * @param user
	 * @param bussTag
	 * @param dataMap
	 * @param records
	 */
	private void setBatchCasualRecords(UserEntity user, Integer bussTag,
			Map<AmountLogEntity, DataTable> dataMap,
			List<CasualRecordsEntity> records) {
		Date realDate = null;
		Long accountId = null;
		int custCount = records.size();
		CasualRecordsEntity firstObj = records.get(0);
		DataTable recordDt = new DataTable();
		String columnNames = StringHandler.join(firstObj.getFields());
		recordDt.setColumnNames(columnNames);
		List<Object> dataSource = new ArrayList<Object>();
		realDate = firstObj.getRectDate();
		accountId = firstObj.getAccountId();
		AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
		BigDecimal t_amount = new BigDecimal("0");
		BigDecimal t_ramount = new BigDecimal("0");
		BigDecimal t_mamount = new BigDecimal("0");
		BigDecimal t_pamount = new BigDecimal("0");
		BigDecimal t_oamount = new BigDecimal("0");
		BigDecimal t_famount = new BigDecimal("0");
		BigDecimal smuamount = new BigDecimal("0");
		StringBuilder sb = new StringBuilder();
		for(CasualRecordsEntity record : records){
			BigDecimal cat = record.getCat();
			BigDecimal rat = record.getRat();
			BigDecimal mat = record.getMat();
			BigDecimal pat = record.getPat();
			BigDecimal dat = record.getDat();
			BigDecimal fat = record.getFat();
			BigDecimal tat = record.getTat();
			
			t_amount = BigDecimalHandler.add2BigDecimal(t_amount, cat);
			t_ramount = BigDecimalHandler.add2BigDecimal(t_ramount, rat);
			t_mamount = BigDecimalHandler.add2BigDecimal(t_mamount, mat);
			t_pamount = BigDecimalHandler.add2BigDecimal(t_pamount, pat);
			t_oamount = BigDecimalHandler.add2BigDecimal(t_oamount, dat);
			t_famount = BigDecimalHandler.add2BigDecimal(t_famount, fat);
			smuamount = BigDecimalHandler.add2BigDecimal(smuamount, tat);
			
			dataSource.add(record.getDatas());
			sb.append(record.getId()).append(",");
		}
		t_amount = BigDecimalHandler.roundToBigDecimal(t_amount, BigDecimalHandler.DEFAULT_SCALE);
		t_ramount = BigDecimalHandler.roundToBigDecimal(t_ramount, BigDecimalHandler.DEFAULT_SCALE);
		t_mamount = BigDecimalHandler.roundToBigDecimal(t_mamount, BigDecimalHandler.DEFAULT_SCALE);
		t_pamount = BigDecimalHandler.roundToBigDecimal(t_pamount, BigDecimalHandler.DEFAULT_SCALE);
		t_oamount = BigDecimalHandler.roundToBigDecimal(t_oamount, BigDecimalHandler.DEFAULT_SCALE);
		t_famount = BigDecimalHandler.roundToBigDecimal(t_famount, BigDecimalHandler.DEFAULT_SCALE);
		smuamount = BigDecimalHandler.roundToBigDecimal(smuamount, BigDecimalHandler.DEFAULT_SCALE);
		
		String invoceIds = StringHandler.RemoveStr(sb);
		amountLog.setInvoceIds(invoceIds);
		amountLog.setAmount(t_amount);
		amountLog.setRamount(t_ramount);
		amountLog.setMamount(t_mamount);
		amountLog.setPamount(t_pamount);
		amountLog.setOamount(t_oamount);
		amountLog.setFamount(t_famount);
		amountLog.setSumamount(smuamount);
		Integer category = BussStateConstant.AMOUNTLOG_CATEGORY_6;
		amountLog.setCategory(category);
		dataMap.put(amountLog, recordDt);
	}
	
	/**
	 * 设置批量实收提前记录数据
	 * @param user
	 * @param bussTag
	 * @param dataMap
	 * @param records
	 */
	private void setBatchPrepaymentRecords(UserEntity user, Integer bussTag,
			Map<AmountLogEntity, DataTable> dataMap,
			List<PrepaymentRecordsEntity> records) {
		Date realDate = null;
		Long accountId = null;
		int custCount = records.size();
		PrepaymentRecordsEntity firstObj = records.get(0);
		DataTable recordDt = new DataTable();
		String columnNames = StringHandler.join(firstObj.getFields());
		recordDt.setColumnNames(columnNames);
		List<Object> dataSource = new ArrayList<Object>();
		realDate = firstObj.getRectDate();
		accountId = firstObj.getAccountId();
		AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
		BigDecimal t_amount = new BigDecimal("0");
		BigDecimal t_ramount = new BigDecimal("0");
		BigDecimal t_mamount = new BigDecimal("0");
		BigDecimal t_pamount = new BigDecimal("0");
		BigDecimal t_oamount = new BigDecimal("0");
		BigDecimal t_famount = new BigDecimal("0");
		BigDecimal smuamount = new BigDecimal("0");
		StringBuilder sb = new StringBuilder();
		for(PrepaymentRecordsEntity record : records){
			BigDecimal cat = record.getCat();
			BigDecimal rat = record.getRat();
			BigDecimal mat = record.getMat();
			BigDecimal pat = record.getPat();
			BigDecimal dat = record.getDat();
			BigDecimal fat = record.getFat();
			BigDecimal tat = record.getTat();
			
			t_amount = BigDecimalHandler.add2BigDecimal(t_amount, cat);
			t_ramount = BigDecimalHandler.add2BigDecimal(t_ramount, rat);
			t_mamount = BigDecimalHandler.add2BigDecimal(t_mamount, mat);
			t_pamount = BigDecimalHandler.add2BigDecimal(t_pamount, pat);
			t_oamount = BigDecimalHandler.add2BigDecimal(t_oamount, dat);
			t_famount = BigDecimalHandler.add2BigDecimal(t_famount, fat);
			smuamount = BigDecimalHandler.add2BigDecimal(smuamount, tat);
			
			dataSource.add(record.getDatas());
			sb.append(record.getId()).append(",");
		}
		t_amount = BigDecimalHandler.roundToBigDecimal(t_amount, BigDecimalHandler.DEFAULT_SCALE);
		t_ramount = BigDecimalHandler.roundToBigDecimal(t_ramount, BigDecimalHandler.DEFAULT_SCALE);
		t_mamount = BigDecimalHandler.roundToBigDecimal(t_mamount, BigDecimalHandler.DEFAULT_SCALE);
		t_pamount = BigDecimalHandler.roundToBigDecimal(t_pamount, BigDecimalHandler.DEFAULT_SCALE);
		t_oamount = BigDecimalHandler.roundToBigDecimal(t_oamount, BigDecimalHandler.DEFAULT_SCALE);
		t_famount = BigDecimalHandler.roundToBigDecimal(t_famount, BigDecimalHandler.DEFAULT_SCALE);
		smuamount = BigDecimalHandler.roundToBigDecimal(smuamount, BigDecimalHandler.DEFAULT_SCALE);
		
		String invoceIds = StringHandler.RemoveStr(sb);
		amountLog.setInvoceIds(invoceIds);
		amountLog.setAmount(t_amount);
		amountLog.setRamount(t_ramount);
		amountLog.setMamount(t_mamount);
		amountLog.setPamount(t_pamount);
		amountLog.setOamount(t_oamount);
		amountLog.setFamount(t_famount);
		amountLog.setSumamount(smuamount);
		Integer category = BussStateConstant.AMOUNTLOG_CATEGORY_4;
		amountLog.setCategory(category);
		dataMap.put(amountLog, recordDt);
	}
	
	private void setBatchFreeRecords(UserEntity user, Integer bussTag,
			Map<AmountLogEntity, DataTable> dataMap,
			List<FreeRecordsEntity> records) {
		Date realDate = null;
		Long accountId = null;
		int custCount = records.size();
		FreeRecordsEntity firstObj = records.get(0);
		DataTable recordDt = new DataTable();
		String columnNames = StringHandler.join(firstObj.getFields());
		recordDt.setColumnNames(columnNames);
		List<Object> dataSource = new ArrayList<Object>();
		realDate = firstObj.getRectDate();
		accountId = firstObj.getAccountId();
		AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
		BigDecimal amount = null;
		StringBuilder sb = new StringBuilder();
		for(FreeRecordsEntity record : records){
			BigDecimal xamount = record.getAmount();
			amount = BigDecimalHandler.add2BigDecimal(amount, xamount);
			dataSource.add(record.getDatas());
			sb.append(record.getId()).append(",");
		}
		String invoceIds = StringHandler.RemoveStr(sb);
		amountLog.setInvoceIds(invoceIds);
		amount = BigDecimalHandler.roundToBigDecimal(amount, BigDecimalHandler.DEFAULT_SCALE);
		amountLog.setAmount(amount);
		amountLog.setSumamount(amount);
		amountLog.setCategory(BussStateConstant.AMOUNTLOG_CATEGORY_1);
		dataMap.put(amountLog, recordDt);
	}
	
	/**
	 * 创建正常/逾期 实收金额日志对象
	 * @param ids	放款单ID列表
	 * @param user	当前用户
	 * @return	返回 实收金额日志对象
	 * @throws DaoException
	 */
	private Map<AmountLogEntity,DataTable> createAmountLogsByAmountRecord(String ids,UserEntity user,Integer bussTag,VoucherTempEntity vtempEntity) throws DaoException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		long totalCount = amountRecordsDao.getTotals(map);
		if(totalCount == 0) return null;
		Map<AmountLogEntity,DataTable> dataMap = new HashMap<AmountLogEntity, DataTable>();
		Date realDate = null;
		Long accountId = null;
		int custCount = 1;
		Integer tactics = vtempEntity.getTactics();
		if(tactics.intValue() == BussStateConstant.VOUCHERTEMP_TACTICS_1){/*一笔业务生成一张凭证*/
			List<AmountRecordsEntity> records = amountRecordsDao.getEntityList(map);
			for(AmountRecordsEntity record : records){
				realDate = record.getRectDate();
				accountId = record.getAccountId();
				AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
				String invoceIds = record.getId().toString();
				amountLog.setInvoceIds(invoceIds);
				amountLog.setAmount(record.getCat());
				amountLog.setRamount(record.getRat());
				amountLog.setMamount(record.getMat());
				amountLog.setPamount(record.getPat());
				amountLog.setOamount(record.getDat());
				amountLog.setFamount(record.getFat());
				amountLog.setSumamount(record.getTat());
				Integer category = (bussTag.intValue() == BussStateConstant.AMOUNTLOG_BUSSTAG_4) ? BussStateConstant.AMOUNTLOG_CATEGORY_3 : BussStateConstant.AMOUNTLOG_CATEGORY_2;
				amountLog.setCategory(category);
				DataTable recordDt = convertEntity2DataTable(record);
				dataMap.put(amountLog, recordDt);
			}
		}else{
			Integer maxcount = vtempEntity.getMaxcount();
			if(null == maxcount) maxcount = 500;
			if(totalCount > maxcount){/*当大于批处理分录数量时*/
				int offset = 0;
				int pageSize = maxcount;
				do{
					List<AmountRecordsEntity> records = amountRecordsDao.getEntityList(map,offset,pageSize);
					if(null == records || records.size() == 0) break;
					setBatchAmountRecords(user, bussTag, dataMap,records);
					offset += pageSize;
				}while(true);
			
			}else{
				List<AmountRecordsEntity> records = amountRecordsDao.getEntityList(map);
				setBatchAmountRecords(user, bussTag, dataMap,records);
			}
		}
		return dataMap;
	}
	
	/**
	 * 创建提前还款 实收金额日志对象
	 * @param ids	实收提前还款记录ID列表
	 * @param user	当前用户
	 * @return	返回提前还款 实收金额日志对象
	 * @throws DaoException
	 */
	private Map<AmountLogEntity,DataTable> createAmountLogsByPrepaymentRecord(String ids,UserEntity user,Integer bussTag,VoucherTempEntity vtempEntity) throws DaoException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		long totalCount = prepaymentRecordsDao.getTotals(map);
		if(totalCount == 0) return null;
		Map<AmountLogEntity,DataTable> dataMap = new HashMap<AmountLogEntity, DataTable>();
		Date realDate = null;
		Long accountId = null;
		int custCount = 1;
		Integer tactics = vtempEntity.getTactics();
		if(tactics.intValue() == BussStateConstant.VOUCHERTEMP_TACTICS_1){/*一笔业务生成一张凭证*/
			List<PrepaymentRecordsEntity> records = prepaymentRecordsDao.getEntityList(map);
			for(PrepaymentRecordsEntity record : records){
				realDate = record.getRectDate();
				accountId = record.getAccountId();
				AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
				String invoceIds = record.getId().toString();
				amountLog.setInvoceIds(invoceIds);
				amountLog.setAmount(record.getCat());
				amountLog.setRamount(record.getRat());
				amountLog.setMamount(record.getMat());
				amountLog.setPamount(record.getPat());
				amountLog.setOamount(record.getDat());
				amountLog.setFamount(record.getFat());
				amountLog.setSumamount(record.getTat());
				Integer category = BussStateConstant.AMOUNTLOG_CATEGORY_4;
				amountLog.setCategory(category);
				DataTable recordDt = convertEntity2DataTable(record);
				dataMap.put(amountLog, recordDt);
			}
		}else{
			Integer maxcount = vtempEntity.getMaxcount();
			if(null == maxcount) maxcount = 500;
			if(totalCount > maxcount){/*当大于批处理分录数量时*/
				int offset = 0;
				int pageSize = maxcount;
				do{
					List<PrepaymentRecordsEntity> records = prepaymentRecordsDao.getEntityList(map,offset,pageSize);
					if(null == records || records.size() == 0) break;
					setBatchPrepaymentRecords(user, bussTag, dataMap,records);
					offset += pageSize;
				}while(true);
			
			}else{
				List<PrepaymentRecordsEntity> records = prepaymentRecordsDao.getEntityList(map);
				setBatchPrepaymentRecords(user, bussTag, dataMap,records);
			}
		}
		return dataMap;
	}
	
	/**
	 * 创建随借随还 实收金额日志对象
	 * @param ids	实收随借随还表记录ID列表
	 * @param user	当前用户
	 * @return	返回随借随还 实收金额日志对象
	 * @throws DaoException
	 */
	private Map<AmountLogEntity,DataTable> createAmountLogsByCasualRecords(String ids,UserEntity user,Integer bussTag,VoucherTempEntity vtempEntity) throws DaoException{
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
		long totalCount = casualRecordsDao.getTotals(map);
		if(totalCount == 0) return null;
		Map<AmountLogEntity,DataTable> dataMap = new HashMap<AmountLogEntity, DataTable>();
		Date realDate = null;
		Long accountId = null;
		int custCount = 1;
		Integer tactics = vtempEntity.getTactics();
		if(tactics.intValue() == BussStateConstant.VOUCHERTEMP_TACTICS_1){/*一笔业务生成一张凭证*/
			List<CasualRecordsEntity> records = casualRecordsDao.getEntityList(map);
			for(CasualRecordsEntity record : records){
				realDate = record.getRectDate();
				accountId = record.getAccountId();
				AmountLogEntity amountLog = createAmountLog(user, realDate, accountId, custCount, bussTag);
				String invoceIds = record.getId().toString();
				amountLog.setInvoceIds(invoceIds);
				amountLog.setAmount(record.getCat());
				amountLog.setRamount(record.getRat());
				amountLog.setMamount(record.getMat());
				amountLog.setPamount(record.getPat());
				amountLog.setOamount(record.getDat());
				amountLog.setFamount(record.getFat());
				amountLog.setSumamount(record.getTat());
				Integer category = BussStateConstant.AMOUNTLOG_CATEGORY_6;
				amountLog.setCategory(category);
				DataTable recordDt = convertEntity2DataTable(record);
				dataMap.put(amountLog, recordDt);
			}
		}else{
			Integer maxcount = vtempEntity.getMaxcount();
			if(null == maxcount) maxcount = 500;
			if(totalCount > maxcount){/*当大于批处理分录数量时*/
				int offset = 0;
				int pageSize = maxcount;
				do{
					List<CasualRecordsEntity> records = casualRecordsDao.getEntityList(map,offset,pageSize);
					if(null == records || records.size() == 0) break;
					setBatchCasualRecords(user, bussTag, dataMap,records);
					offset += pageSize;
				}while(true);
			
			}else{
				List<CasualRecordsEntity> records = casualRecordsDao.getEntityList(map);
				setBatchCasualRecords(user, bussTag, dataMap,records);
			}
		}
		return dataMap;
	}
	
	private AmountLogEntity createAmountLog(UserEntity user, Date realDate,Long accountId, int custCount,Integer bussTag) {
		AmountLogEntity entity = new AmountLogEntity();
		entity.setOpdate(realDate);
		entity.setAccountId(accountId);
		entity.setCustCount(custCount);
		entity.setBussTag(bussTag);
		BeanUtil.setCreateInfo(user, entity);
		return entity;
	}

	/**
	 * 根据凭证模板编号获取凭证模板
	 * @param vtempCode 凭证模板编号
	 * @return
	 * @throws ServiceException 
	 */
	private VoucherTempEntity getVoucherTempByCode(Long sysId,String vtempCode) throws ServiceException{
		VoucherTempEntity voucherTemp = null;
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("sysId", sysId);
		BussFinCfgEntity bussFinCfgEntity = bussFinCfgService.getEntity(params);
		params.clear();
		Long bussfinsysId = bussFinCfgEntity.getId();
		if(null == bussfinsysId) return null;
		params.put("finsysId", bussfinsysId);
		params.put("code", vtempCode);
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		voucherTemp = voucherTempService.getEntity(params);
		return voucherTemp;
	}
}
