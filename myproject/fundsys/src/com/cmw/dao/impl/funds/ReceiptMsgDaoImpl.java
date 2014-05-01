package com.cmw.dao.impl.funds;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.funds.ReceiptMsgDaoInter;
import com.cmw.entity.funds.ReceiptMsgEntity;


/**
 * 汇票信息登记DAO实现类
 * @author 彭登浩
 * @date 2014-02-08T00:00:00
 */
@Description(remark="汇票信息登记DAO实现类",createDate="2014-02-08T00:00:00",author="彭登浩")
@Repository("receiptMsgDao")
public class ReceiptMsgDaoImpl extends GenericDaoAbs<ReceiptMsgEntity, Long> implements ReceiptMsgDaoInter{
	
}
