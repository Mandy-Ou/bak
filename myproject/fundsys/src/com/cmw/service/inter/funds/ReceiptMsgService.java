package com.cmw.service.inter.funds;


import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.funds.ReceiptMsgEntity;


/**
 * 汇票信息登记  Service接口
 * @author 彭登浩
 * @date 2014-01-15T00:00:00
 */
@Description(remark="汇票信息登记业务接口",createDate="2014-01-15T00:00:00",author="彭登浩")
public interface ReceiptMsgService extends IService<ReceiptMsgEntity, Long> {
	/**
	 * Excel 放款数据导出				
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	
	DataTable getDataSource(HashMap<String, Object> params) throws ServiceException;
}
