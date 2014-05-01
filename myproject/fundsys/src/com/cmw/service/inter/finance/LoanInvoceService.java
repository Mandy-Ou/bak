package com.cmw.service.inter.finance;


import java.util.HashMap;
import java.util.Map;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.LoanInvoceEntity;


/**
 * 放款单  Service接口
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
@Description(remark="放款单业务接口",createDate="2013-01-15T00:00:00",author="程明卫")
public interface LoanInvoceService extends IService<LoanInvoceEntity, Long> {
	void update(Map<String,Object> dataMap) throws ServiceException;
	void rever(Map<String,Object> dataMap) throws ServiceException;
	
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws ServiceException;
	/**
	 * 审批放款单
	 * @param complexData
	 * @throws ServiceException
	 */
	void doAuditInvoce(Map<String, Object> complexData) throws ServiceException;
	/**
	 * Excel 放款数据导出				
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
	
	/**
	 * 放款合同查询
	 */
	DataTable getLoanInvoceQuery(SHashMap<String, Object> params, int offset,int pageSize) throws ServiceException;
	/**
	 * 放款单查询
	 * @param params
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	
	DataTable getLoanInvoceQueryDetail(SHashMap<String, Object> params, int offset,int pageSize) throws ServiceException;
	
	
}
