package com.cmw.dao.inter.funds;


import java.util.HashMap;
import java.util.List;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DataTable;
import com.cmw.entity.funds.EntrustCustEntity;
import com.cmw.entity.funds.InterestEntity;



/**
 * 委托客户资料  DAO接口
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
 @Description(remark="利息支付Dao接口",createDate="2014-01-15T00:00:00",author="李听")
public interface InterestDaoInter  extends GenericDaoInter<InterestEntity, Long>{
}
