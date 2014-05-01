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

import com.cmw.entity.funds.EntrustContractEntity;
import com.cmw.dao.inter.funds.EntrustContractDaoInter;
import com.cmw.service.inter.funds.EntrustContractService;


/**
 * 委托合同  Service实现类
 * @author 李听
 * @date 2014-01-20T00:00:00
 */
@Description(remark="委托合同业务实现类",createDate="2014-01-20T00:00:00",author="李听")
@Service("entrustContractService")
public class EntrustContractServiceImpl extends AbsService<EntrustContractEntity, Long> implements  EntrustContractService {
	@Autowired
	private EntrustContractDaoInter entrustContractDao;
	@Override
	public GenericDaoInter<EntrustContractEntity, Long> getDao() {
		return entrustContractDao;
	}
	@Override
	public DataTable getDataSource(HashMap<String, Object> params)
			throws ServiceException {
		try {
			DataTable dt = entrustContractDao.getResultList(new SHashMap<String, Object>(params), -1, -1);
			return dt;
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	/**
	 * 获取展期申请单详情
	 * @param id	申请单ID
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public DataTable detail(Long id) throws ServiceException{
		try {
			return entrustContractDao.detail(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

}
