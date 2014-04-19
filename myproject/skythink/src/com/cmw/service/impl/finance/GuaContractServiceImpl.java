package com.cmw.service.impl.finance;


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

import com.cmw.entity.finance.GuaContractEntity;
import com.cmw.dao.inter.finance.GuaContractDaoInter;
import com.cmw.service.inter.finance.GuaContractService;


/**
 * 保证合同  Service实现类
 * @author pdt
 * @date 2013-01-13T00:00:00
 */
@Description(remark="保证合同业务实现类",createDate="2013-01-13T00:00:00",author="pdt")
@Service("guaContractService")
public class GuaContractServiceImpl extends AbsService<GuaContractEntity, Long> implements  GuaContractService {
	@Autowired
	private GuaContractDaoInter guaContractDao;
	@Override
	public GenericDaoInter<GuaContractEntity, Long> getDao() {
		return guaContractDao;
	}
	/* (non-Javadoc)
	 * @see com.cmw.service.inter.finance.GuaContractService#getDataSource(java.util.HashMap)
	 */
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			return guaContractDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	
}
