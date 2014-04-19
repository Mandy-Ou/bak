package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.ReportTempEntity;
import com.cmw.dao.inter.sys.ReportTempDaoInter;
import com.cmw.service.inter.sys.ReportTempService;


/**
 * 报表模板表  Service实现类
 * @author 程明卫
 * @date 2013-01-19T00:00:00
 */
@Description(remark="报表模板表业务实现类",createDate="2013-01-19T00:00:00",author="程明卫")
@Service("reportTempService")
public class ReportTempServiceImpl extends AbsService<ReportTempEntity, Long> implements  ReportTempService {
	@Autowired
	private ReportTempDaoInter reportTempDao;
	@Override
	public GenericDaoInter<ReportTempEntity, Long> getDao() {
		return reportTempDao;
	}

}
