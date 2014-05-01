package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.ExemptEntity;


/**
 * 息费豁免申请  DAO接口
 * @author 程明卫
 * @date 2013-09-14T00:00:00
 */
 @Description(remark="息费豁免申请Dao接口",createDate="2013-09-14T00:00:00",author="程明卫")
public interface ExemptDaoInter  extends GenericDaoInter<ExemptEntity, Long>{
	 /**
	 * 获取展期客户合同信息
	 * @param params
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	DataTable getContractResultList(SHashMap<String, Object> params,int offset, int pageSize) throws DaoException;
	/**
	 * 获取展期客户合同详情
	 * @param contractId	借款合同ID
	 * @return
	 * @throws DaoException
	 */
	DataTable getContractInfo(Long contractId) throws DaoException;
	/**获取展期客户合同详情
	 * @param id
	 * @return
	 */
	DataTable detail(Long id)throws DaoException;
}