package com.cmw.dao.impl.funds;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.funds.ReceiptBookAttachmentEntity;
import com.cmw.dao.inter.funds.ReceiptBookAttachmentDaoInter;


/**
 * 承诺书附件表  DAO实现类
 * @author 郑符明
 * @date 2014-02-22T00:00:00
 */
@Description(remark="承诺书附件表DAO实现类",createDate="2014-02-22T00:00:00",author="郑符明")
@Repository("receiptBookAttachmentDao")
public class ReceiptBookAttachmentDaoImpl extends GenericDaoAbs<ReceiptBookAttachmentEntity, Long> implements ReceiptBookAttachmentDaoInter {

}
