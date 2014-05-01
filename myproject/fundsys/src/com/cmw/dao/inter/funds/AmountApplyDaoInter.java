package com.cmw.dao.inter.funds;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;

import com.cmw.entity.funds.AmountApplyEntity;
import com.cmw.entity.funds.EntrustContractEntity;


/**
 * 增资申请  DAO接口
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
 @Description(remark="增资申请Dao接口",createDate="2014-01-20T00:00:00",author="李听")
public interface AmountApplyDaoInter  extends GenericDaoInter<AmountApplyEntity, Long>{
		DataTable detail(Long id) throws DaoException;
}
