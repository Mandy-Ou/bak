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

import com.cmw.entity.finance.CmnMappingEntity;
import com.cmw.dao.inter.finance.CmnMappingDaoInter;
import com.cmw.service.inter.finance.CmnMappingService;


/**
 * 列映射关系表  Service实现类
 * @author 赵世龙
 * @date 2013-11-22T00:00:00
 */
@Description(remark="列映射关系表业务实现类",createDate="2013-11-22T00:00:00",author="赵世龙")
@Service("cmnMappingService")
public class CmnMappingServiceImpl extends AbsService<CmnMappingEntity, Long> implements  CmnMappingService {
	@Autowired
	private CmnMappingDaoInter cmnMappingDao;
	@Override
	public GenericDaoInter<CmnMappingEntity, Long> getDao() {
		return cmnMappingDao;
	}
	@Override
	public <K, V> DataTable getCmnList(SHashMap<K, V> map)
			throws ServiceException {
		try {
			return cmnMappingDao.getCmnList(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new  ServiceException();
		}
	}

}
