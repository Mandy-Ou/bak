package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.sys.CompanyEntity;
import com.cmw.dao.inter.sys.CompanyDaoInter;
import com.cmw.service.inter.sys.CompanyService;


/**
 * 公司  Service实现类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="公司业务实现类",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Service("companyService")
public class CompanyServiceImpl extends AbsService<CompanyEntity, Long> implements  CompanyService {
	@Autowired
	private CompanyDaoInter companyDao;
	@Override
	public GenericDaoInter<CompanyEntity, Long> getDao() {
		return companyDao;
	}

}
