package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.CustCompanyEntity;
import com.cmw.dao.inter.crm.CustCompanyDaoInter;
import com.cmw.service.inter.crm.CustCompanyService;


/**
 * 个人旗下/企业关联公司  Service实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人旗下/企业关联公司业务实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Service("custCompanyService")
public class CustCompanyServiceImpl extends AbsService<CustCompanyEntity, Long> implements  CustCompanyService {
	@Autowired
	private CustCompanyDaoInter custCompanyDao;
	@Override
	public GenericDaoInter<CustCompanyEntity, Long> getDao() {
		return custCompanyDao;
	}

}
