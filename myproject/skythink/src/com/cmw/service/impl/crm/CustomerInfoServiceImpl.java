package com.cmw.service.impl.crm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.crm.CustomerInfoEntity;
import com.cmw.dao.inter.crm.CustomerInfoDaoInter;
import com.cmw.service.inter.crm.CustomerInfoService;


/**
 * 个人客户基本信息  Service实现类
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="个人客户基本信息业务实现类",createDate="2012-12-12T00:00:00",author="程明卫")
@Service("customerInfoService")
public class CustomerInfoServiceImpl extends AbsService<CustomerInfoEntity, Long> implements  CustomerInfoService {
	@Autowired
	private CustomerInfoDaoInter customerInfoDao;
	@Override
	public GenericDaoInter<CustomerInfoEntity, Long> getDao() {
		return customerInfoDao;
	}

}
