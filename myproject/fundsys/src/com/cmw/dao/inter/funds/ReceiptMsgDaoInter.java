package com.cmw.dao.inter.funds;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.entity.funds.ReceiptMsgEntity;



/**
 * 汇票信息登记Dao接口
 * @author 彭登浩
 * @date 2014-01-15T00:00:00
 */
 @Description(remark="汇票信息登记Dao接口",createDate="2014-01-15T00:00:00",author="彭登浩")
public interface ReceiptMsgDaoInter  extends GenericDaoInter<ReceiptMsgEntity, Long>{
 }
