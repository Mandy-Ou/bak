package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.DataTable;

import com.cmw.entity.finance.FuntempCfgEntity;
import com.cmw.dao.inter.finance.FuntempCfgDaoInter;
import com.cmw.service.inter.finance.FuntempCfgService;


/**
 * 模板功能配置表  Service实现类
 * @author 赵世龙
 * @date 2013-11-19T00:00:00
 */
@Description(remark="模板功能配置表业务实现类",createDate="2013-11-19T00:00:00",author="赵世龙")
@Service("funtempCfgService")
public class FuntempCfgServiceImpl extends AbsService<FuntempCfgEntity, Long> implements  FuntempCfgService {
	@Autowired
	private FuntempCfgDaoInter funtempCfgDao;
	@Override
	public GenericDaoInter<FuntempCfgEntity, Long> getDao() {
		return funtempCfgDao;
	}

	@Override
	public <K,V> DataTable getTempId(Long menuId)
			throws ServiceException {
		try {
			return this.funtempCfgDao.getTempId(menuId);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
