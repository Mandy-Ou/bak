package com.cmw.service.inter.finance;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.finance.ExtContractEntity;


/**
 * 展期协议书  Service接口
 * @author pdh
 * @date 2013-09-23T00:00:00
 */
@Description(remark="展期协议书业务接口",createDate="2013-09-23T00:00:00",author="pdh")
public interface ExtContractService extends IService<ExtContractEntity, Long> {
	
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
}
