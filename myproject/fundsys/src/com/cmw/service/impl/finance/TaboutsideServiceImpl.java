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

import com.cmw.entity.finance.TaboutsideEntity;
import com.cmw.dao.inter.finance.TaboutsideDaoInter;
import com.cmw.service.inter.finance.TaboutsideService;


/**
 * 表内表外  Service实现类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="表内表外业务实现类",createDate="2013-02-28T00:00:00",author="程明卫")
@Service("taboutsideService")
public class TaboutsideServiceImpl extends AbsService<TaboutsideEntity, Long> implements  TaboutsideService {
	@Autowired
	private TaboutsideDaoInter taboutsideDao;
	@Override
	public GenericDaoInter<TaboutsideEntity, Long> getDao() {
		return taboutsideDao;
	}

	@Override
	public <K, V> DataTable getIds(SHashMap<K, V> map) throws ServiceException {
		try {
			DataTable dt = taboutsideDao.getIds(map);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		return null;
	}
}
