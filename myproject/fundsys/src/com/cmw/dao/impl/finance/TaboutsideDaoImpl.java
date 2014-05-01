package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.TaboutsideDaoInter;
import com.cmw.entity.finance.TaboutsideEntity;


/**
 * 表内表外  DAO实现类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="表内表外DAO实现类",createDate="2013-02-28T00:00:00",author="程明卫")
@Repository("taboutsideDao")
public class TaboutsideDaoImpl extends GenericDaoAbs<TaboutsideEntity, Long> implements TaboutsideDaoInter {
	
	@Override
	public <K, V> DataTable getIds(SHashMap<K, V> map)
			throws DaoException {
		map = SqlUtil.getSafeWhereMap(map);
		String codes = map.getvalAsStr("codes");
		if(!StringHandler.isValidStr(codes)) throw new DaoException("invoke getIds methos : codes is null ");
		String hql = "select A.id,code from TaboutsideEntity A,LoanContractEntity B where" +
				" A.contractId=B.id and code in ("+codes+")";
		return find(hql, "id,code");
	}
	
}
