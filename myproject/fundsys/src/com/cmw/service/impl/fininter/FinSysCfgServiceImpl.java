package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.AbsService;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.dao.inter.fininter.FinSysCfgDaoInter;
import com.cmw.entity.fininter.FinSysCfgEntity;
import com.cmw.service.inter.fininter.FinSysCfgService;


/**
 * 财务系统配置  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="财务系统配置业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("finSysCfgService")
public class FinSysCfgServiceImpl extends AbsService<FinSysCfgEntity, Long> implements  FinSysCfgService {
	@Autowired
	private FinSysCfgDaoInter finSysCfgDao;
	@Override
	public GenericDaoInter<FinSysCfgEntity, Long> getDao() {
		return finSysCfgDao;
	}
	@Override
	public FinSysCfgEntity getCfgByCode(String code) throws ServiceException {
		try{
			SHashMap<String, Object> params = new SHashMap<String, Object>();
			params.put("isenabled", SysConstant.OPTION_ENABLED);
			params.put("code", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + code);
			FinSysCfgEntity entity = finSysCfgDao.getEntity(params);
			return entity;
		}catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}
	
	
}
