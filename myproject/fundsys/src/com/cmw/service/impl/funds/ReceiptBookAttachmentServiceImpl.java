package com.cmw.service.impl.funds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.funds.ReceiptBookAttachmentEntity;
import com.cmw.dao.inter.funds.ReceiptBookAttachmentDaoInter;
import com.cmw.service.inter.funds.ReceiptBookAttachmentService;


/**
 * 承诺书附件表  Service实现类
 * @author 郑符明
 * @date 2014-02-22T00:00:00
 */
@Description(remark="承诺书附件表业务实现类",createDate="2014-02-22T00:00:00",author="郑符明")
@Service("receiptBookAttachmentService")
public class ReceiptBookAttachmentServiceImpl extends AbsService<ReceiptBookAttachmentEntity, Long> implements  ReceiptBookAttachmentService {
	@Autowired
	private ReceiptBookAttachmentDaoInter receiptBookAttachmentDao;
	@Override
	public GenericDaoInter<ReceiptBookAttachmentEntity, Long> getDao() {
		return receiptBookAttachmentDao;
	}

}
