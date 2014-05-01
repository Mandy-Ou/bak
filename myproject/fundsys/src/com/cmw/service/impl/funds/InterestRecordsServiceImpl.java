package com.cmw.service.impl.funds;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.BussStateConstant;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.StringHandler;
import com.cmw.dao.inter.funds.InterestDaoInter;
import com.cmw.dao.inter.funds.InterestRecordsDaoInter;
import com.cmw.entity.finance.FreeEntity;
import com.cmw.entity.finance.LoanInvoceEntity;
import com.cmw.entity.fininter.AmountLogEntity;
import com.cmw.entity.funds.InterestEntity;
import com.cmw.entity.funds.InterestRecordsEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.funds.InterestRecordsService;


/**
 * 利息记录实现类Service实现类
 * @author 李听
 * @date 2014-01-15T00:00:00
 */
@Description(remark="利息记录实现类",createDate="2014-01-15T00:00:00",author="李听")
@Service("interestRecordsService")
public class InterestRecordsServiceImpl extends AbsService<InterestRecordsEntity, Long> implements  InterestRecordsService {
	@Autowired
	private InterestRecordsDaoInter interestRecordsDao;
	@Autowired
	private InterestDaoInter interestDao;
	@Override
	public GenericDaoInter<InterestRecordsEntity, Long> getDao() {
		return interestRecordsDao;
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = interestRecordsDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
