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

import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.PamountRecordsEntity;
import com.cmw.entity.finance.ProjectAmuntEntity;
import com.cmw.dao.inter.finance.AmountRecordsDaoInter;
import com.cmw.dao.inter.finance.PamountRecordsDaoInter;
import com.cmw.dao.inter.finance.ProjectAmuntDaoInter;
import com.cmw.service.inter.finance.AmountRecordsService;
import com.cmw.service.inter.finance.PamountRecordsService;
import com.cmw.service.inter.finance.ProjectAmuntService;


/**
 * 项目费用表)Service实现类
 * @author liting
 * @date 2013-01-15T00:00:00
 */
@Description(remark="项目费用表业务实现类",createDate="2013-01-15T00:00:00",author="liting")
@Service("pamountRecordsService")
public class PamountRecordsServiceImpl extends AbsService<PamountRecordsEntity, Long> implements  PamountRecordsService {
	@Autowired
	private PamountRecordsDaoInter pamountRecordsDao;
	@Override
	public GenericDaoInter<PamountRecordsEntity, Long> getDao() {
		return pamountRecordsDao;
		}
}
