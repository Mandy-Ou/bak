package com.cmw.service.inter.funds;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.funds.AmountApplyEntity;
import com.cmw.entity.funds.EntrustContractEntity;


/**
 * 增资申请Service接口
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="增资申请业务接口",createDate="2014-01-20T00:00:00",author="李听")
public interface AmountApplyService extends IService<AmountApplyEntity, Long> {
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
	DataTable detail(Long id) throws ServiceException;
}
