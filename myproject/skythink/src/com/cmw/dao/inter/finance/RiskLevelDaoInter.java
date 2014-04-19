package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.RiskLevelEntity;


/**
 * 风险等级  DAO接口
 * @author pdt
 * @date 2012-12-23T00:00:00
 */
 @Description(remark="风险等级Dao接口",createDate="2012-12-23T00:00:00",author="pdt")
public interface RiskLevelDaoInter  extends GenericDaoInter<RiskLevelEntity, Long>{
	 <K, V>DataTable getDataSource(SHashMap<K, V> map) throws DaoException;
}
