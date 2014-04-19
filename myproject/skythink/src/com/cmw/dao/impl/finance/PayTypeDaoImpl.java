package com.cmw.dao.impl.finance;


import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.finance.PayTypeDaoInter;
import com.cmw.entity.finance.PayTypeEntity;


/**
 * 还款方式  DAO实现类
 * @author 程明卫
 * @date 2013-01-23T00:00:00
 */
@Description(remark="还款方式DAO实现类",createDate="2013-01-23T00:00:00",author="程明卫")
@Repository("payTypeDao")
public class PayTypeDaoImpl extends GenericDaoAbs<PayTypeEntity, Long> implements PayTypeDaoInter {
	
	
	@Override
	public <K, V> DataTable getDataSource(SHashMap<K, V> map)
			throws DaoException {
		try {
			map = SqlUtil.getSafeWhereMap(map);
			String hql = "select A.code as id,A.name from PayTypeEntity A where  A.isenabled = "+SysConstant.OPTION_ENABLED+" ";
			return find(hql, "id,name");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		
	}
}
