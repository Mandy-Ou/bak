package com.cmw.dao.impl.fininter;


import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.cmw.constant.BussStateConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.fininter.AmountLogDaoInter;
import com.cmw.entity.fininter.AmountLogEntity;


/**
 * 实收金额日志  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="实收金额日志DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("amountLogDao")
public class AmountLogDaoImpl extends GenericDaoAbs<AmountLogEntity, Long> implements AmountLogDaoInter {
	
	
	/* (non-Javadoc)
	 * @see com.cmw.dao.inter.fininter.AmountLogDaoInter#bussTaglist(com.cmw.core.util.SHashMap, int, int)
	 */
	@Override
	public <K, V> DataTable bussTaglist(SHashMap<K, V> params, int offset,
			int pageSize) throws DaoException {
		try{
				params = SqlUtil.getSafeWhereMap(params);
				Long amountLogId = params.getvalAsLng("amountLogId");
				StringBuffer sql = new StringBuffer();
				sql.append("select A.id,bussTag,amount,ramount,mamount,")
				.append("pamount,oamount,famount,sumamount,opdate,")
				.append("A.refId,A.accountId,B.bankName,B.account ")
				.append(" from fs_AmountLog A left join ts_Account B on A.accountId=B.id ")
				.append(" where A.id='"+amountLogId+"' "); 
				String cmns = "id,bussTag,amount,ramount,mamount,"+
							"pamount,oamount,famount,sumamount,opdate#yyyy-MM-dd,"+
							"refId,accountId,bankName,account";
				long totalCount = getTotalCountBySql(sql.toString());
				DataTable dt = findBySqlPage(sql.toString(),cmns, offset, pageSize,totalCount);
				return dt;
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public void updateRecordsAmountLogId(String tabEntity,SHashMap<String, Object> params) throws DaoException {
		String amountLogId = params.getvalAsStr("amountLogId");
		Long userId = params.getvalAsLng("userId");
		String ids = params.getvalAsStr("ids");
		StringBuffer sb = new StringBuffer(); 
		sb.append("update ").append(tabEntity)
		.append(" set fcnumber=?, modifier=?, modifyTime=? ")
		.append(" where id in ("+ids+")");
		try {
			Query query = getSession().createQuery(sb.toString());
			query.setParameter(0, amountLogId);
			query.setParameter(1, userId);
			query.setParameter(2, new Date());
			query.executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}

	@Override
	public <K, V> DataTable getDsByAmountLogId(SHashMap<K, V> params,
			int offset, int pageSize) throws DaoException {
		try{
			return super.getResultList(params, offset, pageSize);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
	
	@Override
	public DataTable getDtBySql(String sql,String colNames) throws DaoException {
		try{
			return findBySql(sql, colNames);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new DaoException(e);
		}
	}
}
