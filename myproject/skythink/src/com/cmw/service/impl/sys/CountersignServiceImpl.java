package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.CountersignEntity;
import com.cmw.dao.inter.sys.CountersignDaoInter;
import com.cmw.service.inter.sys.CountersignService;


/**
 * 会签记录  Service实现类
 * @author 程明卫
 * @date 2013-11-22T00:00:00
 */
@Description(remark="会签记录业务实现类",createDate="2013-11-22T00:00:00",author="程明卫")
@Service("countersignService")
public class CountersignServiceImpl extends AbsService<CountersignEntity, Long> implements  CountersignService {
	@Autowired
	private CountersignDaoInter countersignDao;
	@Override
	public GenericDaoInter<CountersignEntity, Long> getDao() {
		return countersignDao;
	}

}
