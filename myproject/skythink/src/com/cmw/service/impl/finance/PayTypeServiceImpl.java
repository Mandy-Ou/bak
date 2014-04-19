package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.PayTypeEntity;
import com.cmw.dao.inter.finance.PayTypeDaoInter;
import com.cmw.service.inter.finance.PayTypeService;


/**
 * 还款方式  Service实现类
 * @author 程明卫
 * @date 2013-01-23T00:00:00
 */
@Description(remark="还款方式业务实现类",createDate="2013-01-23T00:00:00",author="程明卫")
@Service("payTypeService")
public class PayTypeServiceImpl extends AbsService<PayTypeEntity, Long> implements  PayTypeService {
	@Autowired
	private PayTypeDaoInter payTypeDao;
	@Override
	public GenericDaoInter<PayTypeEntity, Long> getDao() {
		return payTypeDao;
	}
	
	@Override
	public <K, V> DataTable getDataSource(SHashMap<K, V> map)
			throws ServiceException {
		try {
			return payTypeDao.getDataSource(map);
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

}
