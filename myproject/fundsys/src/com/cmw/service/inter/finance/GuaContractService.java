package com.cmw.service.inter.finance;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.finance.GuaContractEntity;


/**
 * 保证合同  Service接口
 * @author pdt
 * @date 2013-01-13T00:00:00
 */
@Description(remark="保证合同业务接口",createDate="2013-01-13T00:00:00",author="pdt")
public interface GuaContractService extends IService<GuaContractEntity, Long> {
	
	/**
	 * Excel  保证合同数据导出
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
}
