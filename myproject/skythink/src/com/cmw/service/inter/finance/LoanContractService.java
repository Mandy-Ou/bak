package com.cmw.service.inter.finance;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.LoanContractEntity;


/**
 * 借款合同  Service接口
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
@Description(remark="借款合同业务接口",createDate="2013-01-11T00:00:00",author="pdh")
public interface LoanContractService extends IService<LoanContractEntity, Long> {

	/**借款合同查询报表
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;

	/**获取客户合同 列表
	 * @param map
	 * @param start
	 * @param limit
	 * @return
	 */
	DataTable getContractResultList(SHashMap<String, Object> map, int start,int limit)  throws ServiceException;

	/**实收及应收表报查询
	 * @param map
	 * @param start
	 * @param limit
	 * @return
	 */
	String getReportList(SHashMap<String, Object> map, int start, int limit) throws ServiceException;

	/**
	 * 调用父类ResultList
	 * @param dtParams
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	DataTable getSuperList(SHashMap<String, Object> dtParams, int offset,int pageSize)throws ServiceException;
}
