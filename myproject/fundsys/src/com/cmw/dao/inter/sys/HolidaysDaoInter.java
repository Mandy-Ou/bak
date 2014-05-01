package com.cmw.dao.inter.sys;


import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.sys.HolidaysEntity;


/**
 * 节假日设置  DAO接口
 * @author 程明卫
 * @date 2012-11-20T00:00:00
 */
 @Description(remark="节假日设置Dao接口",createDate="2012-11-20T00:00:00",author="程明卫")
public interface HolidaysDaoInter  extends GenericDaoInter<HolidaysEntity, Long>{
	 <K, V> List<HolidaysEntity> getInitEntity(final SHashMap<K, V> map) throws DaoException;

}
