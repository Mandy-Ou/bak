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

import com.cmw.entity.finance.RiskLevelEntity;
import com.cmw.dao.inter.finance.RiskLevelDaoInter;
import com.cmw.service.inter.finance.RiskLevelService;


/**
 * 风险等级  Service实现类
 * @author pdt
 * @date 2012-12-23T00:00:00
 */
@Description(remark="风险等级业务实现类",createDate="2012-12-23T00:00:00",author="pdt")
@Service("riskLevelService")
public class RiskLevelServiceImpl extends AbsService<RiskLevelEntity, Long> implements  RiskLevelService {
	@Autowired
	private RiskLevelDaoInter riskLevelDao;
	@Override
	public GenericDaoInter<RiskLevelEntity, Long> getDao() {
		return riskLevelDao;
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
			return riskLevelDao.getDataSource(map);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
