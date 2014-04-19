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

import com.cmw.entity.finance.MortContractEntity;
import com.cmw.dao.inter.finance.MortContractDaoInter;
import com.cmw.service.inter.finance.MortContractService;


/**
 * 抵押合同  Service实现类
 * @author pdh
 * @date 2013-01-11T00:00:00
 */
@Description(remark="抵押合同业务实现类",createDate="2013-01-11T00:00:00",author="pdh")
@Service("mortContractService")
public class MortContractServiceImpl extends AbsService<MortContractEntity, Long> implements  MortContractService {
	@Autowired
	private MortContractDaoInter mortContractDao;
	@Override
	public GenericDaoInter<MortContractEntity, Long> getDao() {
		return mortContractDao;
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params) throws ServiceException {
		try {
			return mortContractDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
