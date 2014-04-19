package com.cmw.service.inter.fininter;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.fininter.BankAccountEntity;


/**
 * 财务系统银行账号  Service接口
 * @author 程明卫
 * @date 2013-04-20T00:00:00
 */
@Description(remark="财务系统银行账号业务接口",createDate="2013-04-20T00:00:00",author="程明卫")
public interface BankAccountService extends IService<BankAccountEntity, Long> {
}
