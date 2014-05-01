package com.cmw.service.impl.funds;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.funds.ReceiptMsgDaoInter;
import com.cmw.entity.funds.ReceiptMsgEntity;
import com.cmw.service.inter.funds.ReceiptMsgService;


/**
 * 汇票信息登记实现类
 * @author 彭登浩
 * @date 2014-01-15T00:00:00
 */
@Description(remark="汇票信息登记实现类",createDate="2014-01-15T00:00:00",author="彭登浩")
@Service("receiptMsgService")
public class ReceiptMsgServiceImpl extends AbsService<ReceiptMsgEntity, Long> implements  ReceiptMsgService {
	@Autowired
	private ReceiptMsgDaoInter receiptMsgDao;
	@Override
	public GenericDaoInter<ReceiptMsgEntity, Long> getDao() {
		return receiptMsgDao;
	}
	
}
