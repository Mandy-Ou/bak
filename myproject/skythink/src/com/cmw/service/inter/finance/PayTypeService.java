package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.PayTypeEntity;


/**
 * 还款方式  Service接口
 * @author 程明卫
 * @date 2013-01-23T00:00:00
 */
@Description(remark="还款方式业务接口",createDate="2013-01-23T00:00:00",author="程明卫")
public interface PayTypeService extends IService<PayTypeEntity, Long> {
	public <K, V> DataTable getDataSource(SHashMap<K, V> map) throws ServiceException;
}
