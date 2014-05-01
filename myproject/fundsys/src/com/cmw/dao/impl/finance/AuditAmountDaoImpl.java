package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.finance.AuditAmountEntity;
import com.cmw.dao.inter.finance.AuditAmountDaoInter;


/**
 * 审批金额建议  DAO实现类
 * @author 程明卫
 * @date 2012-12-26T00:00:00
 */
@Description(remark="审批金额建议DAO实现类",createDate="2012-12-26T00:00:00",author="程明卫")
@Repository("auditAmountDao")
public class AuditAmountDaoImpl extends GenericDaoAbs<AuditAmountEntity, Long> implements AuditAmountDaoInter {

}
