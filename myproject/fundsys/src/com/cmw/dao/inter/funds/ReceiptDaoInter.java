package com.cmw.dao.inter.funds;


import org.springframework.dao.DataAccessException;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.funds.ReceiptEntity;


/**
 * 汇票收条表  DAO接口
 * @author 郑符明
 * @date 2014-02-08T00:00:00
 */
 @Description(remark="汇票收条表Dao接口",createDate="2014-02-08T00:00:00",author="郑符明")
public interface ReceiptDaoInter  extends GenericDaoInter<ReceiptEntity, Long>{
	 public DataTable detail(Long id) throws DaoException;
}
