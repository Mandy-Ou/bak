package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.PayTypeEntity;


/**
 * 还款方式  DAO接口
 * @author 程明卫
 * @date 2013-01-23T00:00:00
 */
 @Description(remark="还款方式Dao接口",createDate="2013-01-23T00:00:00",author="程明卫")
public interface PayTypeDaoInter  extends GenericDaoInter<PayTypeEntity, Long>{
	 public <K, V> DataTable getDataSource(SHashMap<K, V> map) throws DaoException;
}
