package com.cmw.dao.impl.funds;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.funds.RbrelationEntity;
import com.cmw.dao.inter.funds.RbrelationDaoInter;


/**
 * 收条-汇票承诺书关联表  DAO实现类
 * @author 郑符明
 * @date 2014-02-20T00:00:00
 */
@Description(remark="收条-汇票承诺书关联表DAO实现类",createDate="2014-02-20T00:00:00",author="郑符明")
@Repository("rbrelationDao")
public class RbrelationDaoImpl extends GenericDaoAbs<RbrelationEntity, Long> implements RbrelationDaoInter {

}
