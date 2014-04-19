package com.cmw.dao.impl.finance;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.finance.OrderDaoInter;
import com.cmw.entity.finance.OrderEntity;


/**
 * 扣款优先级  DAO实现类
 * @author pdt
 * @date 2012-12-22T00:00:00
 */
@Description(remark="扣款优先级DAO实现类",createDate="2012-12-22T00:00:00",author="pdt")
@Repository("orderDao")
public class OrderDaoImpl extends GenericDaoAbs<OrderEntity, Long> implements OrderDaoInter {

	@Override
	public <K, V> DataTable getResultList(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		params = SqlUtil.getSafeWhereMap(params);
		String hql = " FROM OrderEntity A WHERE 1=1 ";
		Integer isenabled = params.getvalAsInt("isenabled");
		if(!StringHandler.isValidObj(isenabled)){
			hql += " and A.isenabled != '"+SysConstant.OPTION_DEL+"'";
		}
		hql += SqlUtil.buildWhereStr("A",params,false);
		hql += " order by A.level desc,A.orders asc ";
		@SuppressWarnings("unchecked")
		List<OrderEntity> list = findByPage(hql,offset,pageSize);
		DataTable dt = getDTByEntityList(list);
		return dt;
	}

	@Override
	public <K, V> DataTable getResultList() throws DaoException {
		SHashMap<String, Integer> params = new SHashMap<String, Integer>();
		params.put("isenabled", SysConstant.OPTION_ENABLED);
		return this.getResultList(params,-1,-1);
	}
	
}
