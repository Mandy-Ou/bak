package com.cmw.service.impl.funds;


import java.rmi.ServerException;
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
import com.cmw.dao.inter.funds.AmountProofDaoInter;
import com.cmw.entity.funds.AmountProofEntity;
import com.cmw.service.inter.funds.AmountProofService;


/**
 * 资金追加申请实现类
 * @author 彭登浩
 * @date 2014-01-15T00:00:00
 */
@Description(remark="资金追加申请实现类",createDate="2014-01-15T00:00:00",author="彭登浩")
@Service("amountProofService")
public class AmountProofServiceImpl extends AbsService<AmountProofEntity, Long> implements  AmountProofService {
	@Autowired
	private AmountProofDaoInter amountProofDao;
	@Override
	public GenericDaoInter<AmountProofEntity, Long> getDao() {
		return amountProofDao;
	}
	
	@Override
	public DataTable getDataSource(HashMap<String, Object> map)
			throws ServiceException {
		try {
			return amountProofDao.getResultList(new SHashMap<String, Object>(map),-1,-1);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	
}
