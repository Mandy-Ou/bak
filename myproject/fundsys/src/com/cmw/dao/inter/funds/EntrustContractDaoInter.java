package com.cmw.dao.inter.funds;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;

import com.cmw.entity.funds.EntrustContractEntity;


/**
 * 委托合同  DAO接口
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
 @Description(remark="委托合同Dao接口",createDate="2014-01-20T00:00:00",author="李听")
public interface EntrustContractDaoInter  extends GenericDaoInter<EntrustContractEntity, Long>{
		/**
		 * 获取展期申请单详情
		 * @param id	申请单ID
		 * @return
		 * @throws DaoException
		 */
		DataTable detail(Long id) throws DaoException;

}
