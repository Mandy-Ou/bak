package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.finance.AppraiseEntity;
import com.cmw.dao.inter.finance.AppraiseDaoInter;


/**
 * 审贷评审  DAO实现类
 * @author 李听
 * @date 2014-01-04T00:00:00
 */
@Description(remark="审贷评审DAO实现类",createDate="2014-01-04T00:00:00",author="李听")
@Repository("appraiseDao")
public class AppraiseDaoImpl extends GenericDaoAbs<AppraiseEntity, Long> implements AppraiseDaoInter {

}
