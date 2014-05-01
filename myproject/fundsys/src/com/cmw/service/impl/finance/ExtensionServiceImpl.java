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
import com.cmw.dao.inter.finance.ExtensionDaoInter;
import com.cmw.entity.finance.ExtensionEntity;
import com.cmw.service.inter.finance.ExtensionService;


/**
 * 展期申请  Service实现类
 * @author 程明卫
 * @date 2013-09-08T00:00:00
 */
@Description(remark="展期申请业务实现类",createDate="2013-09-08T00:00:00",author="程明卫")
@Service("extensionService")
public class ExtensionServiceImpl extends AbsService<ExtensionEntity, Long> implements  ExtensionService {
	@Autowired
	private ExtensionDaoInter extensionDao;
	@Override
	public GenericDaoInter<ExtensionEntity, Long> getDao() {
		return extensionDao;
	}
	
	@Override
	public DataTable getContractResultList(SHashMap<String, Object> params, int offset,int pageSize) throws ServiceException {
		try {
			return extensionDao.getContractResultList(params, offset, pageSize);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public DataTable getContractInfo(Long contractId)throws ServiceException{
		try {
			return extensionDao.getContractInfo(contractId);
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
			return extensionDao.detail(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
