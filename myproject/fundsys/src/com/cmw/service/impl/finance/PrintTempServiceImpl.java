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

import com.cmw.entity.finance.PrintTempEntity;
import com.cmw.entity.finance.TdsCfgEntity;
import com.cmw.dao.inter.finance.PrintTempDaoInter;
import com.cmw.service.inter.finance.PrintTempService;


/**
 * 合同模板表  Service实现类
 * @author 赵世龙
 * @date 2013-11-19T00:00:00
 */
@Description(remark="合同模板表业务实现类",createDate="2013-11-19T00:00:00",author="赵世龙")
@Service("printTempService")
public class PrintTempServiceImpl extends AbsService<PrintTempEntity, Long> implements  PrintTempService {
	@Autowired
	private PrintTempDaoInter printTempDao;
	@Override
	public GenericDaoInter<PrintTempEntity, Long> getDao() {
		return printTempDao;
	}
	@Override
	public <K, V> DataTable getTempDetail(TdsCfgEntity entity,String cmns,SHashMap<String, Object> map)
			throws DaoException {
		return printTempDao.getTempDetail(entity, cmns, map);
	}

	
}
