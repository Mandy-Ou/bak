package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.FieldPropDaoInter;
import com.cmw.entity.sys.FieldPropEntity;
import com.cmw.service.inter.sys.FieldPropService;


/**
 * 字段属性表  Service实现类
 * @author pengdenghao
 * @date 2012-11-23T00:00:00
 */
@Description(remark="字段属性表业务实现类",createDate="2012-11-23T00:00:00",author="pengdenghao")
@Service("fieldPropService")
public class FieldPropServiceImpl extends AbsService<FieldPropEntity, Long> implements  FieldPropService {
	@Autowired
	private FieldPropDaoInter fieldPropDao;
	@Override
	public GenericDaoInter<FieldPropEntity, Long> getDao() {
		return fieldPropDao;
	}

}
