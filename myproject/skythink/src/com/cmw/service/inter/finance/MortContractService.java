package com.cmw.service.inter.finance;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.finance.MortContractEntity;


/**
 * 抵押合同  Service接口
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
@Description(remark="抵押合同业务接口",createDate="2013-01-11T00:00:00",author="pdh")
public interface MortContractService extends IService<MortContractEntity, Long> {

	/**抵押合同导出数据
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
}
