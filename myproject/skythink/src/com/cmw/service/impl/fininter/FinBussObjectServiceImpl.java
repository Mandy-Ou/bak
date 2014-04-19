package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.FinBussObjectEntity;
import com.cmw.dao.inter.fininter.FinBussObjectDaoInter;
import com.cmw.service.inter.fininter.FinBussObjectService;


/**
 * 自定义业务对象  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="自定义业务对象业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("finBussObjectService")
public class FinBussObjectServiceImpl extends AbsService<FinBussObjectEntity, Long> implements  FinBussObjectService {
	@Autowired
	private FinBussObjectDaoInter finBussObjectDao;
	@Override
	public GenericDaoInter<FinBussObjectEntity, Long> getDao() {
		return finBussObjectDao;
	}

}
