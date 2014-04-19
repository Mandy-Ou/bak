package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.PrepaymentEntity;


/**
 * 提前还款申请  DAO接口
 * @author 程明卫
 * @date 2013-09-11T00:00:00
 */
 @Description(remark="提前还款申请Dao接口",createDate="2013-09-11T00:00:00",author="程明卫")
public interface PrepaymentDaoInter  extends GenericDaoInter<PrepaymentEntity, Long>{

	 /**
	  * 获取提前还款计算的数据
	  * @param params	参数[contractId,xpayDate]
	  * @return
	  * @throws DaoException
	  */
	DataTable getCalculateDt(SHashMap<String, Object> params)throws DaoException;

	/**
	 * @param id
	 * @return
	 */
	DataTable detail(Long id) throws DaoException;
	/**
	 * 从还款计划表中，获取提前还款应收金额
	 * @param params	过滤参数
	 * @return	
	 * @throws DaoException
	 */
	DataTable getZamountDt(SHashMap<String, Object> params) throws DaoException;

}
