package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.EfinanceEntity;
import com.cmw.dao.inter.crm.EfinanceDaoInter;
import com.cmw.service.inter.crm.EfinanceService;


/**
 * 企业财务状况  Service实现类
 * @author 彭登浩
 * @date 2012-12-24T00:00:00
 */
@Description(remark="企业财务状况业务实现类",createDate="2012-12-24T00:00:00",author="彭登浩")
@Service("efinanceService")
public class EfinanceServiceImpl extends AbsService<EfinanceEntity, Long> implements  EfinanceService {
	@Autowired
	private EfinanceDaoInter efinanceDao;
	@Override
	public GenericDaoInter<EfinanceEntity, Long> getDao() {
		return efinanceDao;
	}

}
