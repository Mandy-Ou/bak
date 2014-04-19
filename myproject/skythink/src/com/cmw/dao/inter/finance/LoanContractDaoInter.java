package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.LoanContractEntity;


/**
 * 借款合同  DAO接口
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
 @Description(remark="借款合同Dao接口",createDate="2013-01-11T00:00:00",author="pdh")
public interface LoanContractDaoInter  extends GenericDaoInter<LoanContractEntity, Long>{
	 /**
	 * 根据合同ID获取 合约入款日，罚息利率，滞纳金利率
	 * @param contractIds 合同ID字符串列表
	 * @return
	 * @throws DaoException
	 */
	 public <K, V> DataTable getRates(String contractIds)throws DaoException;

	/**获取客户合同 列表
	 * @param map
	 * @param start
	 * @param limit
	 * @return
	 */
	public DataTable getContractResultList(SHashMap<String, Object> map, int start, int limit) throws DaoException;

	/**
	 * @param dtParams
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	public DataTable getSuperList(SHashMap<String, Object> dtParams,
			int offset, int pageSize) throws DaoException;
}
