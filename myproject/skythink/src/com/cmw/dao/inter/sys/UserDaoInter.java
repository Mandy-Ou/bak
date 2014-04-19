package com.cmw.dao.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.sys.UserEntity;


/**
 * 用户  DAO接口
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
 @Description(remark="用户Dao接口",createDate="2011-09-24T00:00:00",author="chengmingwei")
public interface UserDaoInter  extends GenericDaoInter<UserEntity, Long>{
		public <K, V> DataTable getSerch(SHashMap<K, V> params, int offset,
				int pageSize) throws DaoException ;

}
