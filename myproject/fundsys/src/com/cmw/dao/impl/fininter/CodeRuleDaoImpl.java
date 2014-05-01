package com.cmw.dao.impl.fininter;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.fininter.CodeRuleEntity;
import com.cmw.dao.inter.fininter.CodeRuleDaoInter;


/**
 * 凭证编号规则  DAO实现类
 * @author 程明卫
 * @date 2013-09-01T00:00:00
 */
@Description(remark="凭证编号规则DAO实现类",createDate="2013-09-01T00:00:00",author="程明卫")
@Repository("codeRuleDao")
public class CodeRuleDaoImpl extends GenericDaoAbs<CodeRuleEntity, Long> implements CodeRuleDaoInter {

}
