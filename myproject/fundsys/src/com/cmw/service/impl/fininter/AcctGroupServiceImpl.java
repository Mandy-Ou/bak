package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.fininter.AcctGroupDaoInter;
import com.cmw.entity.fininter.AcctGroupEntity;
import com.cmw.service.inter.fininter.AcctGroupService;


/**
 * 科目组  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="科目组业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("acctGroupService")
public class AcctGroupServiceImpl extends AbsService<AcctGroupEntity, Long> implements  AcctGroupService {
	@Autowired
	private AcctGroupDaoInter acctGroupDao;
	@Override
	public GenericDaoInter<AcctGroupEntity, Long> getDao() {
		return acctGroupDao;
	}
}
