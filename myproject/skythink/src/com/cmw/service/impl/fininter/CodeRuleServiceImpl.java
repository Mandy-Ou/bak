package com.cmw.service.impl.fininter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.service.AbsService;

import com.cmw.entity.fininter.CodeRuleEntity;
import com.cmw.dao.inter.fininter.CodeRuleDaoInter;
import com.cmw.service.inter.fininter.CodeRuleService;


/**
 * 	
 * 凭证编号规则  Service实现类
 * @author 程明卫
 * @date 2013-09-01T00:00:00
 */
@Description(remark="凭证编号规则业务实现类",createDate="2013-09-01T00:00:00",author="程明卫")
@Service("codeRuleService")
public class CodeRuleServiceImpl extends AbsService<CodeRuleEntity, Long> implements  CodeRuleService {
	@Autowired
	private CodeRuleDaoInter codeRuleDao;
	@Override
	public GenericDaoInter<CodeRuleEntity, Long> getDao() {
		return codeRuleDao;
	}

}
