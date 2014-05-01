package com.cmw.service.inter.sys;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.service.IService;
import com.cmw.entity.sys.AccountEntity;


/**
 * 公司账户  Service接口
 * @author 彭登浩
 * @date 2012-12-08T00:00:00
 */
@Description(remark="公司账户业务接口",createDate="2012-12-08T00:00:00",author="彭登浩")
public interface AccountService extends IService<AccountEntity, Long> {
	
}
