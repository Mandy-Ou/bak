package com.cmw.service.inter.finance;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.TaboutsideEntity;


/**
 * 表内表外  Service接口
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="表内表外业务接口",createDate="2013-02-28T00:00:00",author="程明卫")
public interface TaboutsideService extends IService<TaboutsideEntity, Long> {
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws ServiceException;
	/**
	 * Excel 放款数据导出
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
}
