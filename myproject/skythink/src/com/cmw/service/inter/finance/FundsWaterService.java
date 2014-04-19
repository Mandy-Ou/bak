package com.cmw.service.inter.finance;


import java.util.Map;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.FundsWaterEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.sys.UserEntity;


/**
 * 资金流水  Service接口
 * @author pdh
 * @date 2013-08-13T00:00:00
 */
@Description(remark="资金流水业务接口",createDate="2013-08-13T00:00:00",author="pdh")
public interface FundsWaterService extends IService<FundsWaterEntity, Long> {
	/**
	 * 计算银行账户中的金额
	 * @param logDataMap
	 * @param fundsWater 
	 * @throws ServiceException
	 */
	<K,V> void calculate(Map<AmountLogEntity, DataTable> logDataMap, UserEntity user) throws ServiceException;
	/**
	 * 计算并更新银行账户中的金额记录
	 * @param logDataMap	<财务接口实收金额日志,小额贷款实收记录表> 
	 * @param user	当前用户
	 * @param	isOut 是否是支出[true : 支出,false 或 空 : 收入] 
	 * @throws ServiceException
	 */
	void calculate(Map<AmountLogEntity, DataTable> logDataMap, UserEntity user,
			Boolean isOut) throws ServiceException;
	
	public FundsWaterEntity doUpdate(SHashMap<String, Object> complexData)throws ServiceException;


}
