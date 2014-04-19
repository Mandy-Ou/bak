package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;

import com.cmw.entity.finance.MinAmountEntity;


/**
 * 最低金额配置  DAO接口
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
 @Description(remark="最低金额配置Dao接口",createDate="2013-02-28T00:00:00",author="程明卫")
public interface MinAmountDaoInter  extends GenericDaoInter<MinAmountEntity, Long>{
	 /**
	 * 获取 审核通过的 罚息和滞纳金 数据
	 * @return
	 * @throws DaoException 
	 */
	public DataTable getEnabledAmounts() throws DaoException;
}
