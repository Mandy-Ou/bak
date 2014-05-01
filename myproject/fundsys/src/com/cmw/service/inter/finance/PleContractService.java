package com.cmw.service.inter.finance;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.finance.PleContractEntity;


/**
 * 质押合同  Service接口
 * @author pdh
 * @date 2013-02-02T00:00:00
 */
@Description(remark="质押合同业务接口",createDate="2013-02-02T00:00:00",author="pdh")
public interface PleContractService extends IService<PleContractEntity, Long> {

	/**质押合同导出报表
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
}
