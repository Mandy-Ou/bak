package com.cmw.service.inter.funds;



import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;
import com.cmw.entity.funds.AmountProofEntity;


/**
 * 资金追加申请  Service接口
 * @author 彭登浩
 * @date 2014-01-15T00:00:00
 */
@Description(remark="资金追加申请业务接口",createDate="2014-01-15T00:00:00",author="彭登浩")
public interface AmountProofService extends IService<AmountProofEntity, Long> {
	
	DataTable getDataSource(HashMap< String, Object> map) throws ServiceException;
}
