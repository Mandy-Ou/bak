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
import com.cmw.dao.inter.finance.FreeDaoInter;
import com.cmw.entity.finance.FreeEntity;
import com.cmw.entity.finance.FreeRecordsEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.finance.FreeRecordsService;
import com.cmw.service.inter.finance.FreeService;
import com.cmw.service.inter.finance.FundsWaterService;
import com.cmw.service.inter.fininter.AmountLogService;

/**
 * 放款手续费  Service实现类
 * @author pdh
 * @date 2013-01-17T00:00:00
 */
@Description(remark="放款手续费业务实现类",createDate="2013-01-17T00:00:00",author="pdh")
@Service("freeService")
public class FreeServiceImpl extends AbsService<FreeEntity, Long> implements  FreeService {
	@Autowired
	private FreeDaoInter freeDao;
	
	@Resource(name="freeRecordsService")
	private FreeRecordsService freeRecordsService;
	
	@Resource(name = "fundsWaterService")
	private FundsWaterService fundsWaterService;
	
	@Override
	public GenericDaoInter<FreeEntity, Long> getDao() {
		return freeDao;
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
					 dt = freeDao.getResultList(new SHashMap<String, Object>(params),-1,-1);
					break;
				case 2:
					 dt = freeDao.getLoanRecordsList(new SHashMap<String, Object>(params),-1,-1);
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
	@Transactional
	public Object doComplexBusss(SHashMap<String, Object> complexData)
			throws ServiceException {
		BigDecimal yamount ;
		Long id = (Long)complexData.get("id");
		Long contractId = (Long)complexData.get("contractId");
		String ids = (String) complexData.get("ids");
		Long accountId = (Long)complexData.get("accountId");
		String lastDate = (String)(complexData.get("lastDate"));
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		String recordIds = null;
		if(StringHandler.isValidObj(id)){/*单笔收费*/
			yamount = BigDecimal.valueOf((Double)complexData.get("yamount"));
			BigDecimal amount = BigDecimal.valueOf((Double) complexData.get("amount"));
			if(yamount.compareTo(yamount)==0){
				yamount = BigDecimal.valueOf(BigDecimalHandler.round(yamount, 2));
			}
			FreeEntity freeEntity = getEntity(id);
			freeEntity.setYamount(yamount);
			freeEntity.setLastDate(DateUtil.dateFormat(lastDate));
			BigDecimal freeamount= freeEntity.getFreeamount();
			if(yamount.compareTo(freeamount)==0){
				freeEntity.setStatus(BussStateConstant.FREE_STATUS_2);
			}else if(yamount.compareTo(BigDecimal.valueOf(0))==0){
				freeEntity.setStatus(BussStateConstant.FREE_STATUS_0);
			}else if(yamount.compareTo(freeamount)==-1){
				freeEntity.setStatus(BussStateConstant.FREE_STATUS_1);
			}
			BeanUtil.setModifyInfo(user, freeEntity);
			saveOrUpdateEntity(freeEntity);
			FreeRecordsEntity freeRecordsEntity = makeFreeRecords(freeEntity,user,amount,accountId,contractId);
			if(null != freeRecordsEntity){
				freeRecordsService.saveEntity(freeRecordsEntity);
				recordIds = freeRecordsEntity.getId().toString();
			}
		}else{/*批量收费*/
			if(!StringHandler.isValidObj(ids)) throw new ServiceException("批量收取手续必须传入 ids 参数!");
			String batchDatas = (String)complexData.get("batchDatas");
			List<FreeRecordsEntity> recordsList = makeFreeAmountss(ids,user,accountId,lastDate,batchDatas);
			if(null != recordsList && recordsList.size()>0){
				freeRecordsService.batchSaveEntitys(recordsList);
				StringBuilder sbRecordIds = new StringBuilder();
				if(null == recordsList || recordsList.size() == 0) return null;
				for(FreeRecordsEntity amountRecord : recordsList){
					Long amountRecordId = amountRecord.getId();
					sbRecordIds.append(amountRecordId).append(",");
				}
				recordIds = StringHandler.RemoveStr(sbRecordIds);
			}
		}
		//---> step 2 : 保存实收金额日志<---//
		Map<AmountLogEntity,DataTable> logDataMap = savAmountLogs(complexData, recordIds);
		
		//----> ste 3 : 保存资金流水 和更新自有资金<---//
		fundsWaterService.calculate(logDataMap,user);
		return logDataMap;
	}
	@Resource(name="amountLogService")
	private AmountLogService amountLogService;
	private Map<AmountLogEntity,DataTable> savAmountLogs(SHashMap<String, Object> complexData,
			String recordIds) throws ServiceException {
		if(!StringHandler.isValidStr(recordIds)) return null;
		UserEntity user = (UserEntity)complexData.get(SysConstant.USER_KEY);
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("ids", recordIds);
		Long sysId = complexData.getvalAsLng("sysId");
		params.put("sysId", sysId);
		String vtempCode = complexData.getvalAsStr("vtempCode");
		params.put("vtempCode", vtempCode);
		params.put(SysConstant.USER_KEY, user);
		params.put("bussTag", BussStateConstant.AMOUNTLOG_BUSSTAG_1);
		Map<AmountLogEntity,DataTable> logDataMap = amountLogService.saveByBussTag(params);
		return logDataMap;
	}
	
	/**
	 * @param ids 批量收取手续必须传入 ids 参数
	 * @param user 当期用户
	 * @param accountId 收款账号id
	 * @param lastDate 最后收款日期
	 * @throws ServiceException 
	 */
	public List<FreeRecordsEntity> makeFreeAmountss(String ids, UserEntity user,Long accountId ,String lastDate,String batchDatas) throws ServiceException {
		try {
			JSONArray dataArr = FastJsonUtil.convertStrToJSONArr(batchDatas);
			SHashMap<String, Object> map = new SHashMap<String, Object>();
			map.put("id", SqlUtil.LOGIC_IN + SqlUtil.LOGIC + ids);
			List<FreeEntity> freeentitys = this.getEntityList(map);
			List<FreeRecordsEntity> recordsList = new ArrayList<FreeRecordsEntity>();
			for(FreeEntity freeentity : freeentitys){
				BeanUtil.setCreateInfo(user, freeentity);
				Long id  = freeentity.getId();
				Integer status = freeentity.getStatus();
				BigDecimal yamount = freeentity.getYamount();
				if(null != status && status.intValue() == BussStateConstant.FREE_STATUS_2) continue;/*如果结清，则跳出循环*/
				BigDecimal freeamount = freeentity.getFreeamount();
				Object[] datas = this.totalyamount(id,dataArr);
				double ym = (Double)datas[0];
				Long contractId = (Long)datas[1];
				BigDecimal totalyamount= BigDecimal.valueOf(BigDecimalHandler.add(yamount, BigDecimal.valueOf(ym)));
				freeentity.setYamount(totalyamount);
				if(totalyamount.compareTo(freeamount)==0){
					freeentity.setStatus(BussStateConstant.FREE_STATUS_2);
				}else if(totalyamount.compareTo(BigDecimal.valueOf(0))==0){
					freeentity.setStatus(BussStateConstant.FREE_STATUS_0);
				}else if(totalyamount.compareTo(freeamount)==-1){
					freeentity.setStatus(BussStateConstant.FREE_STATUS_1);
				}
				freeentity.setLastDate(DateUtil.dateFormat(lastDate));
				saveOrUpdateEntity(freeentity);
				FreeRecordsEntity freeRecordsEntity = makeFreeRecords(freeentity,user,totalyamount,accountId, contractId);
				if(null != freeRecordsEntity) recordsList.add(freeRecordsEntity);
			}
			return recordsList;
		} catch (ServiceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * @param id 得到 FreeEntity： 手续费中的id
	 * @param yamount -----> 根据id得到实际手续费并进行累加
	 * @return 返回指定的实收手续费
	 */
	public Object[] totalyamount(Long id, JSONArray dataArr)throws ServiceException {
		double yamount = 0 ;
		Long contractId = null;
		for(int i=0,count=dataArr.size(); i<count; i++){
			JSONObject obj = dataArr.getJSONObject(i);
			Long _id = obj.getLong("id");
			if(id.equals(_id)){
				yamount += obj.getDoubleValue("yamount");
				contractId = obj.getLong("contractId");
				break;
			}
		}
		yamount = BigDecimalHandler.round(yamount, 2);
		return new Object[]{yamount,contractId};
	}
	/**
	 * @param freeEntity
	 * @param user
	 * @return
	 */
	private FreeRecordsEntity makeFreeRecords(FreeEntity freeEntity, UserEntity user,BigDecimal amount,Long accountId,Long contractId
			) throws ServiceException{
			Long invoceId = freeEntity.getId();
			Date lastDate = freeEntity.getLastDate();
			FreeRecordsEntity frEntity = new FreeRecordsEntity();
			frEntity.setAmount(amount);
			frEntity.setContractId(contractId);
			frEntity.setInvoceId(invoceId);
			frEntity.setRectDate(lastDate);
			frEntity.setAccountId(accountId);
			BeanUtil.setCreateInfo(user, frEntity);
			return frEntity;
	}


	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.FreeService#getIds(com.cmw.core.util.SHashMap)
	 */
	@Override
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws ServiceException {
		try {
			DataTable dt = freeDao.getIds(map);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
/* (non-Javadoc)
	 * @see com.cmw.core.base.service.AbsService#getLoanRecordsList(com.cmw.core.util.SHashMap)
	 */
	@Override
	public <K, V> DataTable getLoanRecordsList(SHashMap<K, V> map)
			throws ServiceException {
			try {
				DataTable dt = freeDao.getLoanRecordsList(map);
				return dt;
			} catch (DaoException e) {
				e.printStackTrace();
				throw new ServiceException(e);
			}
	}

/**
 * 手续费收流水
 */
	
	@Override
	public <K, V> DataTable getLoanRecord(SHashMap<K, V> map)
			throws ServiceException {
		try {
			DataTable dt = freeDao.getIds(map);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
}