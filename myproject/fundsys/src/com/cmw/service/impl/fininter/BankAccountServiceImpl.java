package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.BankAccountEntity;
import com.cmw.dao.inter.fininter.BankAccountDaoInter;
import com.cmw.service.inter.fininter.BankAccountService;


/**
 * 财务系统银行账号  Service实现类
 * @author 程明卫
 * @date 2013-04-20T00:00:00
 */
@Description(remark="财务系统银行账号业务实现类",createDate="2013-04-20T00:00:00",author="程明卫")
@Service("bankAccountService")
public class BankAccountServiceImpl extends AbsService<BankAccountEntity, Long> implements  BankAccountService {
	@Autowired
	private BankAccountDaoInter bankAccountDao;
	@Override
	public GenericDaoInter<BankAccountEntity, Long> getDao() {
		return bankAccountDao;
	}

}
