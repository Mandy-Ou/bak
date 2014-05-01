package com.cmw.service.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.finance.PrepaymentEntity;


/**
 * 提前还款申请  Service接口
 * @author 程明卫
 * @date 2013-09-11T00:00:00
 */
@Description(remark="提前还款申请业务接口",createDate="2013-09-11T00:00:00",author="程明卫")
public interface PrepaymentService extends IService<PrepaymentEntity, Long> {
	 /**
	  * 获取提前还款计算的数据
	  * @param params	参数[contractId,xpayDate]
	  * @return
	  * @throws ServiceException
	  */
	DataTable getCalculateDt(SHashMap<String, Object> params)throws ServiceException;
	
	/**
	 * 获取展期申请单详情
	 * @param id	申请单ID
	 * @return
	 * @throws ServiceException
	 */
	DataTable detail(Long id) throws ServiceException;
	
	/**
	 * 从还款计划表中，获取提前还款应收金额
	 * @param params	过滤参数
	 * @return	
	 * @throws ServiceException
	 */
	DataTable getZamountDt(SHashMap<String, Object> params) throws ServiceException;

	
}
