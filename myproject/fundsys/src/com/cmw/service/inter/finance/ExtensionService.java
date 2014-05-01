package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.ExtensionEntity;


/**
 * 展期申请  Service接口
 * @author 程明卫
 * @date 2013-09-08T00:00:00
 */
@Description(remark="展期申请业务接口",createDate="2013-09-08T00:00:00",author="程明卫")
public interface ExtensionService extends IService<ExtensionEntity, Long> {

	/**
	 * 获取展期客户合同信息
	 * @param params
	 * @param offset
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	DataTable getContractResultList(SHashMap<String, Object> params, int offset,int pageSize) throws ServiceException;
	/**
	 * 获取展期客户合同详情
	 * @param contractId	借款合同ID
	 * @return
	 * @throws ServiceException
	 */
	DataTable getContractInfo(Long contractId) throws ServiceException;
	
	/**
	 * 获取展期申请单详情
	 * @param id	申请单ID
	 * @return
	 * @throws ServiceException
	 */
	DataTable detail(Long id) throws ServiceException;
}
