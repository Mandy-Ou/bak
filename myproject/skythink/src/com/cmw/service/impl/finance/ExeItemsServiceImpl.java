package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.ExeItemsEntity;
import com.cmw.dao.inter.finance.ExeItemsDaoInter;
import com.cmw.service.inter.finance.ExeItemsService;


/**
 * 息费豁免列表  Service实现类
 * @author 程明卫
 * @date 2013-09-14T00:00:00
 */
@Description(remark="息费豁免列表业务实现类",createDate="2013-09-14T00:00:00",author="程明卫")
@Service("exeItemsService")
public class ExeItemsServiceImpl extends AbsService<ExeItemsEntity, Long> implements  ExeItemsService {
	@Autowired
	private ExeItemsDaoInter exeItemsDao;
	@Override
	public GenericDaoInter<ExeItemsEntity, Long> getDao() {
		return exeItemsDao;
	}

}
