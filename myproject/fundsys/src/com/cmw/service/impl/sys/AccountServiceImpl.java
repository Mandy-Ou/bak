package com.cmw.service.impl.sys;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;
import com.cmw.dao.inter.sys.AccountDaoInter;
import com.cmw.entity.sys.AccountEntity;
import com.cmw.service.inter.sys.AccountService;


/**
 * 公司账户  Service实现类
 * @author 彭登浩
 * @date 2012-12-08T00:00:00
 */
@Description(remark="公司账户业务实现类",createDate="2012-12-08T00:00:00",author="彭登浩")
@Service("accountService")
public class AccountServiceImpl extends AbsService<AccountEntity, Long> implements  AccountService {
	@Autowired
	private AccountDaoInter accountDao;
	@Override
	public GenericDaoInter<AccountEntity, Long> getDao() {
		return accountDao;
	}
	
}
