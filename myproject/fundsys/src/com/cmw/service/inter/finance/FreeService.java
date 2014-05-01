package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.FreeEntity;


/**
 * 放款手续费  Service接口
 * @author pdh
 * @date 2013-01-17T00:00:00
 */
@Description(remark="放款手续费业务接口",createDate="2013-01-17T00:00:00",author="pdh")
public interface FreeService extends IService<FreeEntity, Long> {

	/**
	 * @param map
	 * @return
	 */
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws ServiceException;
	public <K, V> DataTable getLoanRecord(SHashMap<K, V> map) throws ServiceException;
}
