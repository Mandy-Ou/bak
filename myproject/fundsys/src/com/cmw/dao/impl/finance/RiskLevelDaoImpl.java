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
import com.cmw.dao.inter.finance.RiskLevelDaoInter;
import com.cmw.entity.finance.RiskLevelEntity;


/**
 * 风险等级  DAO实现类
 * @author pdt
 * @date 2012-12-23T00:00:00
 */
@Description(remark="风险等级DAO实现类",createDate="2012-12-23T00:00:00",author="pdt")
@Repository("riskLevelDao")
public class RiskLevelDaoImpl extends GenericDaoAbs<RiskLevelEntity, Long> implements RiskLevelDaoInter {
	@Override
	public <K, V> DataTable getDataSource(SHashMap<K, V> map)
			throws DaoException {
		try {
			map = SqlUtil.getSafeWhereMap(map);
			String hql = "select A.id,A.name,A.remark as others from RiskLevelEntity A where  A.isenabled = "+SysConstant.OPTION_ENABLED+" ";
			return find(hql, "id,name,others");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
		
	}
}
