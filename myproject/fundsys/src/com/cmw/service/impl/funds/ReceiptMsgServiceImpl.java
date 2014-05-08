package com.cmw.service.impl.funds;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
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
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = receiptMsgDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/*public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
			DataTable dt  = null;
			Object exceltype = (Object) params.get("excelType");
			try {
				if(StringHandler.isValidObj(exceltype)){
					Integer type =Integer.parseInt(exceltype.toString());
					switch (type) {
					case 1:
						 dt = receiptMsgDao.getResultList(new SHashMap<String, Object>(params),-1,-1);
						break;
					}
				}
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}*/
}
