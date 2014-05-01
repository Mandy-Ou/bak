package com.cmw.service.inter.funds;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.funds.EntrustContractEntity;


/**
 * 委托合同  Service接口
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="委托合同业务接口",createDate="2014-01-20T00:00:00",author="李听")
public interface EntrustContractService extends IService<EntrustContractEntity, Long> {
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
	/**
	 * 获取展期申请单详情
	 * @param id	申请单ID
	 * @return
	 * @throws ServiceException
	 */
	DataTable detail(Long id) throws ServiceException;
}
