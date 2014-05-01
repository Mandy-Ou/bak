package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.FreeEntity;


/**
 * 放款手续费  DAO接口
 * @author pdh
 * @date 2013-01-17T00:00:00
 */
 @Description(remark="放款手续费Dao接口",createDate="2013-01-17T00:00:00",author="pdh")
public interface FreeDaoInter  extends GenericDaoInter<FreeEntity, Long>{
	 public <K, V> DataTable getIds(SHashMap<K, V> map)throws DaoException;
	 /**
	  * 手续费收取记录的流水
	  * @param map
	  * @return
	  * @throws DaoException
	  */
	  <K, V> DataTable getLoanRecord(SHashMap<K, V> map)throws DaoException;
}
