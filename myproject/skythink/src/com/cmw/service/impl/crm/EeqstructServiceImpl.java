package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.EeqstructEntity;
import com.cmw.dao.inter.crm.EeqstructDaoInter;
import com.cmw.service.inter.crm.EeqstructService;


/**
 * 银行借款情况  Service实现类
 * @author pdh
 * @date 2012-12-25T00:00:00
 */
@Description(remark="银行借款情况业务实现类",createDate="2012-12-25T00:00:00",author="pdh")
@Service("eeqstructService")
public class EeqstructServiceImpl extends AbsService<EeqstructEntity, Long> implements  EeqstructService {
	@Autowired
	private EeqstructDaoInter eeqstructDao;
	@Override
	public GenericDaoInter<EeqstructEntity, Long> getDao() {
		return eeqstructDao;
	}

}
