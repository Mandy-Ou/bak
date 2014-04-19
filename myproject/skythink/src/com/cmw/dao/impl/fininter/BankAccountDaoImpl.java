package com.cmw.dao.impl.fininter;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.fininter.BankAccountEntity;
import com.cmw.dao.inter.fininter.BankAccountDaoInter;


/**
 * 财务系统银行账号  DAO实现类
 * @author 程明卫
 * @date 2013-04-20T00:00:00
 */
@Description(remark="财务系统银行账号DAO实现类",createDate="2013-04-20T00:00:00",author="程明卫")
@Repository("bankAccountDao")
public class BankAccountDaoImpl extends GenericDaoAbs<BankAccountEntity, Long> implements BankAccountDaoInter {

}
