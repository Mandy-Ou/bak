package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.AccordionDaoInter;
import com.cmw.entity.sys.AccordionEntity;
import com.cmw.service.inter.sys.AccordionService;
/**
 * 卡片菜单业务实现类
 * @author ddd
 *
 */
@Description(remark="卡片菜单业务实现类",createDate="2011-08-13")
@Service("accordionService")
@Transactional
public class AccordionServiceImpl extends AbsService<AccordionEntity, Long> implements AccordionService {
	@Autowired
	private AccordionDaoInter accordionDao;
	@Override
	public GenericDaoInter<AccordionEntity, Long> getDao() {
		return accordionDao;
	}
}
