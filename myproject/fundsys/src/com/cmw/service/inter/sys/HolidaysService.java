package com.cmw.service.inter.sys;


import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.sys.HolidaysEntity;


/**
 * 节假日设置  Service接口
 * @author 程明卫
 * @date 2012-11-20T00:00:00
 */
@Description(remark="节假日设置业务接口",createDate="2012-11-20T00:00:00",author="程明卫")
public interface HolidaysService extends IService<HolidaysEntity, Long> {
	<K, V> List<HolidaysEntity> getQuery(Map<K, V> map) throws ServiceException;
	
}
