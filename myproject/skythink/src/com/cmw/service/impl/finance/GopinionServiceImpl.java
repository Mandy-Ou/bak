package com.cmw.service.impl.finance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.finance.GopinionEntity;
import com.cmw.dao.inter.finance.GopinionDaoInter;
import com.cmw.service.inter.finance.GopinionService;


/**
 * 担保人意见  Service实现类
 * @author 程明卫
 * @date 2013-09-08T00:00:00
 */
@Description(remark="担保人意见业务实现类",createDate="2013-09-08T00:00:00",author="程明卫")
@Service("gopinionService")
public class GopinionServiceImpl extends AbsService<GopinionEntity, Long> implements  GopinionService {
	@Autowired
	private GopinionDaoInter gopinionDao;
	@Override
	public GenericDaoInter<GopinionEntity, Long> getDao() {
		return gopinionDao;
	}

}
