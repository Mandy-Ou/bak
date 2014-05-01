package com.cmw.service.impl.funds;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.dao.inter.funds.ReceiptDaoInter;
import com.cmw.entity.funds.ReceiptBookAttachmentEntity;
import com.cmw.entity.funds.ReceiptBookEntity;
import com.cmw.entity.funds.ReceiptEntity;
import com.cmw.entity.funds.SettlementEntity;
import com.cmw.service.inter.funds.ReceiptService;


/**
 * 汇票收条表  Service实现类
 * @author 郑符明
 * @date 2014-02-08T00:00:00
 */
@Description(remark="汇票收条表业务实现类",createDate="2014-02-08T00:00:00",author="郑符明")
@Service("receiptService")
public class ReceiptServiceImpl extends AbsService<ReceiptEntity, Long> implements  ReceiptService {
	@Autowired
	private ReceiptDaoInter receiptDao;
	@Override
	public GenericDaoInter<ReceiptEntity, Long> getDao() {
		return receiptDao;
	}
	public DataTable detail(Long id) throws ServiceException{
		try {
			return receiptDao.detail(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
