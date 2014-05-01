package com.cmw.dao.impl.funds;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.funds.BackInvoceEntity;
import com.cmw.dao.inter.funds.BackInvoceDaoInter;


/**
 * 汇票回款单表  DAO实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="汇票回款单表DAO实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Repository("backInvoceDao")
public class BackInvoceDaoImpl extends GenericDaoAbs<BackInvoceEntity, Long> implements BackInvoceDaoInter {

}
