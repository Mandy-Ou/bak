package com.cmw.service.impl.funds;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;
import com.cmw.dao.inter.funds.CapitalPairDaoInter;
import com.cmw.dao.inter.funds.EntrustContractDaoInter;
import com.cmw.dao.inter.funds.InterestDaoInter;
import com.cmw.dao.inter.funds.InterestRecordsDaoInter;
import com.cmw.entity.funds.CapitalPairEntity;
import com.cmw.entity.funds.InterestEntity;
import com.cmw.entity.funds.InterestRecordsEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.funds.CapitalPairService;
import com.cmw.service.inter.funds.InterestRecordsService;
import com.cmw.service.inter.funds.InterestService;


/**
 * 资金配对实现类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="资金配对实现类",createDate="2014-01-15T00:00:00",author="李听")
@Service("capitalPairService")
public class CapitalPairServiceImpl extends AbsService<CapitalPairEntity, Long> implements  CapitalPairService {
	@Autowired
	private CapitalPairDaoInter capitalPairDao;
	@Autowired
	private EntrustContractDaoInter entrustContractDao;
	@Override
	public GenericDaoInter<CapitalPairEntity, Long> getDao() {
		return capitalPairDao;
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = capitalPairDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void doComplexBusss(Map<String, Object> complexData)
			throws ServiceException {
		List<CapitalPairEntity> list=(List<CapitalPairEntity>) complexData.get("list");
		this.batchSaveOrUpdateEntitys(list);
		
		
	}
}
