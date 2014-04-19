package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.FieldCustomDaoInter;
import com.cmw.entity.sys.FieldCustomEntity;
import com.cmw.service.inter.sys.FieldCustomService;


/**
 * 自定义字段表  Service实现类
 * @author pengdenghao
 * @date 2012-11-26T00:00:00
 */
@Description(remark="自定义字段表业务实现类",createDate="2012-11-26T00:00:00",author="pengdenghao")
@Service("fieldCustomService")
public class FieldCustomServiceImpl extends AbsService<FieldCustomEntity, Long> implements  FieldCustomService {
	@Autowired
	private FieldCustomDaoInter fieldCustomDao;
	@Override
	public GenericDaoInter<FieldCustomEntity, Long> getDao() {
		return fieldCustomDao;
	}

}
