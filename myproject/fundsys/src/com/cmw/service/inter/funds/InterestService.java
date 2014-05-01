package com.cmw.service.inter.funds;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.funds.InterestEntity;


/**
 * 委托客户资料  Service接口
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="利息支付业务接口",createDate="2014-01-15T00:00:00",author="李听")
public interface InterestService extends IService<InterestEntity, Long> {
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
}
