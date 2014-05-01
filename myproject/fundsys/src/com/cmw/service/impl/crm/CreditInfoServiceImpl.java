package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.CreditInfoEntity;
import com.cmw.dao.inter.crm.CreditInfoDaoInter;
import com.cmw.service.inter.crm.CreditInfoService;


/**
 * 个人信用资料  Service实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人信用资料业务实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Service("creditInfoService")
public class CreditInfoServiceImpl extends AbsService<CreditInfoEntity, Long> implements  CreditInfoService {
	@Autowired
	private CreditInfoDaoInter creditInfoDao;
	@Override
	public GenericDaoInter<CreditInfoEntity, Long> getDao() {
		return creditInfoDao;
	}

}
