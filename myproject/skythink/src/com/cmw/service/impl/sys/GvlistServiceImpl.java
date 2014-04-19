package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.sys.GvlistEntity;
import com.cmw.dao.inter.sys.GvlistDaoInter;
import com.cmw.service.inter.sys.GvlistService;


/**
 * 基础数据  Service实现类
 * @author 彭登浩
 * @date 2012-11-19T00:00:00
 */
@Description(remark="基础数据业务实现类",createDate="2012-11-19T00:00:00",author="彭登浩")
@Service("gvlistService")
public class GvlistServiceImpl extends AbsService<GvlistEntity, Long> implements  GvlistService {
	@Autowired
	private GvlistDaoInter gvlistDao;
	@Override
	public GenericDaoInter<GvlistEntity, Long> getDao() {
		return gvlistDao;
	}
	
	/**
	 * 根据指定的参数 获取下拉框数据源
	 * @param <K>
	 * @param <V>
	 * @param map 过滤参数
	 * @return 返回符合条件的 DataTable 对象
	 * @throws ServiceException  抛出DaoException 
	 */
	public <K, V> DataTable getDataSource(SHashMap<K, V> map) throws ServiceException{
		try {
			return gvlistDao.getDataSource(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public <K, V> DataTable getReportDt(SHashMap<K, V> map)
			throws ServiceException {
		try {
			return gvlistDao.getReportDt(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	
}
