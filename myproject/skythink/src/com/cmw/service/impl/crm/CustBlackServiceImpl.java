package com.cmw.service.impl.crm;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.crm.CustBlackDaoInter;
import com.cmw.entity.crm.CustBlackEntity;
import com.cmw.service.inter.crm.CustBlackService;
import com.cmw.service.inter.crm.CustomerInfoService;


/**
 * 客户黑名单  Service实现类
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="客户黑名单业务实现类",createDate="2012-12-12T00:00:00",author="程明卫")
@Service("custBlackService")
public class CustBlackServiceImpl extends AbsService<CustBlackEntity, Long> implements  CustBlackService {
	@Autowired
	private CustBlackDaoInter custBlackDao;
	
	@Resource(name="customerInfoService")
	private CustomerInfoService customerInfoService;
	@Override
	public GenericDaoInter<CustBlackEntity, Long> getDao() {
		return custBlackDao;
	}
	
}
