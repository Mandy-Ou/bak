package com.cmw.service.inter.funds;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.funds.ReceiptEntity;


/**
 * 汇票收条表  Service接口
 * @author 郑符明
 * @date 2014-02-08T00:00:00
 */
@Description(remark="汇票收条表业务接口",createDate="2014-02-08T00:00:00",author="郑符明")
public interface ReceiptService extends IService<ReceiptEntity, Long> {
	
	DataTable detail(Long id) throws ServiceException;
}
