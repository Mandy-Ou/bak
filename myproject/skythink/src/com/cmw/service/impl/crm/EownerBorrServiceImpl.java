package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.EownerBorrEntity;
import com.cmw.dao.inter.crm.EownerBorrDaoInter;
import com.cmw.service.inter.crm.EownerBorrService;


/**
 * 所有者借款情况  Service实现类
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="所有者借款情况业务实现类",createDate="2012-12-26T00:00:00",author="pdh")
@Service("eownerBorrService")
public class EownerBorrServiceImpl extends AbsService<EownerBorrEntity, Long> implements  EownerBorrService {
	@Autowired
	private EownerBorrDaoInter eownerBorrDao;
	@Override
	public GenericDaoInter<EownerBorrEntity, Long> getDao() {
		return eownerBorrDao;
	}

}
