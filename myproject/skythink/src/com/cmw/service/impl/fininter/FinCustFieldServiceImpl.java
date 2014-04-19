package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.fininter.FinCustFieldDaoInter;
import com.cmw.entity.fininter.FinCustFieldEntity;
import com.cmw.service.inter.fininter.FinCustFieldService;


/**
 * 财务自定义字段  Service实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="财务自定义字段业务实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Service("finCustFieldService")
public class FinCustFieldServiceImpl extends AbsService<FinCustFieldEntity, Long> implements  FinCustFieldService {
	@Autowired
	private FinCustFieldDaoInter finCustFieldDao;
	@Override
	public GenericDaoInter<FinCustFieldEntity, Long> getDao() {
		return finCustFieldDao;
	}

}
