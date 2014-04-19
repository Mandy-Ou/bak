package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.TaboutsideEntity;


/**
 * 表内表外  DAO接口
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
 @Description(remark="表内表外Dao接口",createDate="2013-02-28T00:00:00",author="程明卫")
public interface TaboutsideDaoInter  extends GenericDaoInter<TaboutsideEntity, Long>{
	 public <K, V> DataTable getIds(SHashMap<K, V> map)throws DaoException;
}
