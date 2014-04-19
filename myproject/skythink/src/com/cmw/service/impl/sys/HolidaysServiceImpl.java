package com.cmw.service.impl.sys;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.sys.HolidaysEntity;
import com.cmw.dao.inter.sys.HolidaysDaoInter;
import com.cmw.service.inter.sys.HolidaysService;


/**
 * 节假日设置  Service实现类
 * @author 程明卫
 * @date 2012-11-20T00:00:00
 */
@Description(remark="节假日设置业务实现类",createDate="2012-11-20T00:00:00",author="程明卫")
@Service("holidaysService")
public class HolidaysServiceImpl extends AbsService<HolidaysEntity, Long> implements  HolidaysService {
	@Autowired
	private HolidaysDaoInter holidaysDao;
	@Override
	public GenericDaoInter<HolidaysEntity, Long> getDao() {
		return holidaysDao;
	}
	

	/* (non-Javadoc)
	 * @see com.cmw.service.inter.sys.HolidaysService#getQuery(com.cmw.core.util.SHashMap)
	 */
	@Override
	public <K, V> List<HolidaysEntity> getQuery(Map<K, V> map)
			throws ServiceException {
		List<HolidaysEntity> holidayEnitiyList = null;
		try {
			String iniYear = (String)map.get("iniYear");
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("Sdate", iniYear);
			 holidayEnitiyList = holidaysDao.getInitEntity(params);
			if(holidayEnitiyList.isEmpty()){
				params.clear();
				params.put("Edate", iniYear);
				holidayEnitiyList = holidaysDao.getInitEntity(params);
			}
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return holidayEnitiyList;
	}
	
}
