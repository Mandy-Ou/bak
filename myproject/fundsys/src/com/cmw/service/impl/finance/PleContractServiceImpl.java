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
import com.cmw.dao.inter.finance.PleContractDaoInter;
import com.cmw.entity.finance.PleContractEntity;
import com.cmw.service.inter.finance.PleContractService;


/**
 * 质押合同  Service实现类
 * @author pdh
 * @date 2013-02-02T00:00:00
 */
@Description(remark="质押合同业务实现类",createDate="2013-02-02T00:00:00",author="pdh")
@Service("pleContractService")
public class PleContractServiceImpl extends AbsService<PleContractEntity, Long> implements  PleContractService {
	@Autowired
	private PleContractDaoInter pleContractDao;
	@Override
	public GenericDaoInter<PleContractEntity, Long> getDao() {
		return pleContractDao;
	}
	
	@Override
	public DataTable getDataSource(HashMap<String, Object> params) throws ServiceException {
		try {
			return pleContractDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
