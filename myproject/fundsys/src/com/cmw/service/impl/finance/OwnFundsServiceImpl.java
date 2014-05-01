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
import com.cmw.dao.inter.finance.OwnFundsDaoInter;
import com.cmw.entity.finance.OwnFundsEntity;
import com.cmw.service.inter.finance.OwnFundsService;


/**
 * 自有资金  Service实现类
 * @author pdh
 * @date 2013-08-13T00:00:00
 */
@Description(remark="自有资金业务实现类",createDate="2013-08-13T00:00:00",author="pdh")
@Service("ownFundsService")
public class OwnFundsServiceImpl extends AbsService<OwnFundsEntity, Long> implements  OwnFundsService {
	@Autowired
	private OwnFundsDaoInter ownFundsDao;
	@Override
	public GenericDaoInter<OwnFundsEntity, Long> getDao() {
		return ownFundsDao;
	}
	/**
	 * 用于配置execel导出模版的数据
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = ownFundsDao.getResultList(new SHashMap<String, Object>(params),-1,-1);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
