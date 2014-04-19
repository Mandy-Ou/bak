package com.cmw.service.impl.fininter.external.generic;

import java.util.List;

import com.cmw.core.base.exception.ServiceException;
import com.cmw.entity.fininter.CurrencyEntity;
import com.cmw.entity.fininter.EntryTempEntity;
import com.cmw.entity.fininter.FinBussObjectEntity;
import com.cmw.entity.fininter.FinCustFieldEntity;
import com.cmw.entity.fininter.ItemTempEntity;
import com.cmw.entity.fininter.SettleEntity;
import com.cmw.entity.fininter.VoucherTempEntity;
import com.cmw.entity.sys.AccountEntity;
import com.cmw.entity.sys.FormulaEntity;

public class FinSysBaseDataCache {
	/*-------------------- 缓存更新 CODE START  -----------------------*/
	/**
	 * 更新缓存操作
	 */
	public static final int OPTYPE_ADD = 1;
	/**
	 * 更新缓存操作
	 */
	public static final int OPTYPE_UPDATE = 2;
	/**
	 * 添加或更新缓存操作
	 */
	public static final int OPTYPE_ADD_0R_UPDATE = 4;
	/**
	 * 删除缓存操作
	 */
	public static final int OPTYPE_DEL = 3;
	/**
	 * 凭证缓存新增、更新、删除
	 * @param newVoucherTemp	新的凭证模板
	 * @param opType	操作类型 [OPTYPE_ADD:新增，OPTYPE_UPDATE : 更新，OPTYPE_DEL:删除]
	 * @throws ServiceException 找不到参数 opType 对应的常量将抛异常
	 */
	public static final void updateVoucherTempCache(VoucherTempEntity newVoucherTemp,int opType) throws ServiceException{
		Long finsysId = newVoucherTemp.getFinsysId();
		String vtempCode = newVoucherTemp.getCode();
		String key = finsysId + "_" + vtempCode;
		switch (opType) {
		case OPTYPE_ADD:
			VoucherTransform.voucheTempCache.put(key, newVoucherTemp);
			break;
		case OPTYPE_UPDATE:
			if(VoucherTransform.voucheTempCache.containsKey(key)) VoucherTransform.voucheTempCache.put(key, newVoucherTemp);
			break;
		case OPTYPE_DEL:
			if(VoucherTransform.voucheTempCache.containsKey(key)) VoucherTransform.voucheTempCache.remove(key);
			break;
		default:
			throw new ServiceException("["+opType+"]未定义的缓存操作类型!");
			//break;
		}
	}
	
	/**
	 * 分录缓存新增、更新、删除
	 * @param newVoucherTemp	新的凭证模板
	 * @param opType	操作类型 [OPTYPE_ADD:新增，OPTYPE_UPDATE : 更新，OPTYPE_DEL:删除]
	 * @throws ServiceException 找不到参数 opType 对应的常量将抛异常
	 */
	public static final void updateEntryTempCache(EntryTempEntity entryTemp,int opType) throws ServiceException{
		Long voucherId = entryTemp.getVoucherId();
		switch (opType) {
		case OPTYPE_ADD:
			if(VoucherTransform.entryTempCache.containsKey(voucherId)) VoucherTransform.entryTempCache.get(voucherId).add(entryTemp);
			break;
		case OPTYPE_UPDATE:
			updateOrDelEntryTemp(voucherId,entryTemp,false);
			break;
		case OPTYPE_DEL:
			updateOrDelEntryTemp(voucherId,entryTemp,true);
			break;
		default:
			throw new ServiceException("["+opType+"]未定义的缓存操作类型!");
			//break;
		}
	}
	
	/**
	 * 更新或删除缓存中的分录对象
	 * @param voucherId	凭证模板ID
	 * @param entryTemp	要更新、删除的新的分录对象
	 * @param isDel	是否删除 [true:删除缓存中的对象，false:更新缓存中的分录对象]
	 */
	private static final void updateOrDelEntryTemp(Long voucherId, EntryTempEntity entryTemp,boolean isDel){
		if(VoucherTransform.entryTempCache.containsKey(voucherId)){
			List<EntryTempEntity> entrys = VoucherTransform.entryTempCache.get(voucherId);
			int index = -1;
			for(int i=0,count=entrys.size(); i<count; i++){
				EntryTempEntity entry = entrys.get(i);
				Long entryId = entry.getId();
				if(entryTemp.getId().equals(entryId)){
					index = i;
					break;
				}
			}
			if(isDel){
				entrys.remove(index);
			}else{
				entrys.set(index, entryTemp);
			}
		}
	}
	
	/**
	 * 核算项缓存新增、更新、删除
	 * @param newItemTemp	新的核算项模板
	 * @param opType	操作类型 [OPTYPE_ADD:新增，OPTYPE_UPDATE : 更新，OPTYPE_DEL:删除]
	 * @throws ServiceException 找不到参数 opType 对应的常量将抛异常
	 */
	public static final void updateItemTempCache(ItemTempEntity newItemTemp,int opType) throws ServiceException{
		Long key = newItemTemp.getEntryId();
		switch (opType) {
		case OPTYPE_ADD:
			VoucherTransform.itemTempCache.put(key, newItemTemp);
			break;
		case OPTYPE_UPDATE:
			if(VoucherTransform.itemTempCache.containsKey(key)) VoucherTransform.itemTempCache.put(key, newItemTemp);
			break;
		case OPTYPE_DEL:
			if(VoucherTransform.itemTempCache.containsKey(key)) VoucherTransform.itemTempCache.remove(key);
			break;
		default: 
			throw new ServiceException("["+opType+"]未定义的缓存操作类型!");
			//break;
		}
	}
	
	/**
	 * 结算方式缓存新增、更新、删除
	 * @param newItemTemp	新的核算项模板
	 * @param opType	操作类型 [OPTYPE_ADD:新增，OPTYPE_UPDATE : 更新，OPTYPE_DEL:删除]
	 * @throws ServiceException 找不到参数 opType 对应的常量将抛异常
	 */
	public static final void updateSettleCache(SettleEntity settleEntity,int opType) throws ServiceException{
		Long finsysId = settleEntity.getFinsysId();
		Long refId = settleEntity.getRefId();
		String key = finsysId + "_" + refId;
		switch (opType) {
		case OPTYPE_ADD:
			VoucherTransform.settleCache.put(key, settleEntity);
			break;
		case OPTYPE_UPDATE:
			if(VoucherTransform.settleCache.containsKey(key)) VoucherTransform.settleCache.put(key, settleEntity);
			break;
		case OPTYPE_DEL:
			if(VoucherTransform.settleCache.containsKey(key)) VoucherTransform.settleCache.remove(key);
			break;
		default: 
			throw new ServiceException("["+opType+"]未定义的缓存操作类型!");
			//break;
		}
	}
	
	/**
	 * 币别缓存新增、更新、删除
	 * @param newItemTemp	新的核算项模板
	 * @param opType	操作类型 [OPTYPE_ADD:新增，OPTYPE_UPDATE : 更新，OPTYPE_DEL:删除]
	 * @throws ServiceException 找不到参数 opType 对应的常量将抛异常
	 */
	public static final void updateCurrencyCache(CurrencyEntity currencyEntity,int opType) throws ServiceException{
		Long finsysId = currencyEntity.getFinsysId();
		Long refId = currencyEntity.getRefId();
		String key = finsysId + "_" + refId;
		switch (opType) {
		case OPTYPE_ADD:
			VoucherTransform.currencyCache.put(key, currencyEntity);
			break;
		case OPTYPE_UPDATE:
			if(VoucherTransform.currencyCache.containsKey(key)) VoucherTransform.currencyCache.put(key, currencyEntity);
			break;
		case OPTYPE_DEL:
			if(VoucherTransform.currencyCache.containsKey(key)) VoucherTransform.currencyCache.remove(key);
			break;
		default: 
			throw new ServiceException("["+opType+"]未定义的缓存操作类型!");
			//break;
		}
	}
	
	/**
	 * 币别缓存新增、更新、删除
	 * @param newItemTemp	新的核算项模板
	 * @param opType	操作类型 [OPTYPE_ADD:新增，OPTYPE_UPDATE : 更新，OPTYPE_DEL:删除]
	 * @throws ServiceException 找不到参数 opType 对应的常量将抛异常
	 */
	public static final void updateBussObjCache(FinBussObjectEntity bussObjectEntity,int opType) throws ServiceException{
		Long id = bussObjectEntity.getId();
		switch (opType) {
		case OPTYPE_ADD:
			VoucherTransform.bussObjCache.put(id, bussObjectEntity);
			break;
		case OPTYPE_UPDATE:
			if(VoucherTransform.bussObjCache.containsKey(id)) VoucherTransform.bussObjCache.put(id, bussObjectEntity);
			break;
		case OPTYPE_DEL:
			if(VoucherTransform.bussObjCache.containsKey(id)) VoucherTransform.bussObjCache.remove(id);
			break;
		default: 
			throw new ServiceException("["+opType+"]未定义的缓存操作类型!");
			//break;
		}
	}
	
	/**
	 * 自定义字段缓存新增、更新、删除
	 * @param newItemTemp	新的核算项模板
	 * @param opType	操作类型 [OPTYPE_ADD:新增，OPTYPE_UPDATE : 更新，OPTYPE_DEL:删除]
	 * @throws ServiceException 找不到参数 opType 对应的常量将抛异常
	 */
	public static final void updateCustFieldCache(FinCustFieldEntity custFieldEntity,int opType) throws ServiceException{
		Long id = custFieldEntity.getId();
		switch (opType) {
		case OPTYPE_ADD:
			VoucherTransform.custFieldCache.put(id, custFieldEntity);
			break;
		case OPTYPE_UPDATE:
			if(VoucherTransform.custFieldCache.containsKey(id)) VoucherTransform.custFieldCache.put(id, custFieldEntity);
			break;
		case OPTYPE_DEL:
			if(VoucherTransform.custFieldCache.containsKey(id)) VoucherTransform.custFieldCache.remove(id);
			break;
		default: 
			throw new ServiceException("["+opType+"]未定义的缓存操作类型!");
			//break;
		}
	}
	
	/**
	 * 公式缓存新增、更新、删除
	 * @param formulaEntity	新的公式对象
	 * @param opType	操作类型 [OPTYPE_ADD:新增，OPTYPE_UPDATE : 更新，OPTYPE_DEL:删除]
	 * @throws ServiceException 找不到参数 opType 对应的常量将抛异常
	 */
	public static final void updateFormulaCache(FormulaEntity formulaEntity,int opType) throws ServiceException{
		Long id = formulaEntity.getId();
		switch (opType) {
		case OPTYPE_ADD:
			VoucherTransform.formulaCache.put(id, formulaEntity);
			break;
		case OPTYPE_UPDATE:
			if(VoucherTransform.formulaCache.containsKey(id)) VoucherTransform.formulaCache.put(id, formulaEntity);
			break;
		case OPTYPE_DEL:
			if(VoucherTransform.formulaCache.containsKey(id)) VoucherTransform.formulaCache.remove(id);
			break;
		default: 
			throw new ServiceException("["+opType+"]未定义的缓存操作类型!");
			//break;
		}
	}
	
	/**
	 * 公司帐号缓存新增、更新、删除
	 * @param formulaEntity	新的公式对象
	 * @param opType	操作类型 [OPTYPE_ADD:新增，OPTYPE_UPDATE : 更新，OPTYPE_DEL:删除]
	 * @throws ServiceException 找不到参数 opType 对应的常量将抛异常
	 */
	public static final void updateAccountCache(AccountEntity accountEntity,int opType) throws ServiceException{
		Long id = accountEntity.getId();
		switch (opType) {
		case OPTYPE_UPDATE:
			VoucherTransform.accountCache.put(id, accountEntity);
			break;
		case OPTYPE_DEL:
			if(VoucherTransform.accountCache.containsKey(id)) VoucherTransform.accountCache.remove(id);
			break;
		default: 
			throw new ServiceException("["+opType+"]未定义的缓存操作类型!");
			//break;
		}
	}
	/*-------------------- 缓存更新 CODE END  -----------------------*/
}
