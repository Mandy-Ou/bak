package com.cmw.service.impl.funds;


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
import com.cmw.dao.inter.funds.AgreeBookDaoInter;
import com.cmw.dao.inter.funds.CapitalPairDaoInter;
import com.cmw.dao.inter.funds.CpairDetailDaoInter;
import com.cmw.dao.inter.funds.InterestDaoInter;
import com.cmw.dao.inter.funds.InterestRecordsDaoInter;
import com.cmw.entity.funds.AgreeBookEntity;
import com.cmw.entity.funds.CapitalPairEntity;
import com.cmw.entity.funds.CpairDetailEntity;
import com.cmw.entity.funds.InterestEntity;
import com.cmw.entity.funds.InterestRecordsEntity;
import com.cmw.service.inter.funds.AgreeBookService;
import com.cmw.service.inter.funds.CapitalPairService;
import com.cmw.service.inter.funds.CpairDetailService;
import com.cmw.service.inter.funds.InterestRecordsService;
import com.cmw.service.inter.funds.InterestService;


/**
 * 资金配对实现类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="资金配对实现类",createDate="2014-01-15T00:00:00",author="李听")
@Service("agreeBookService")
public class AgreeBookServiceImpl extends AbsService<AgreeBookEntity, Long> implements  AgreeBookService {
	@Autowired
	private AgreeBookDaoInter agreeBookDao;
	@Override
	public GenericDaoInter<AgreeBookEntity, Long> getDao() {
		return agreeBookDao;
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = agreeBookDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
