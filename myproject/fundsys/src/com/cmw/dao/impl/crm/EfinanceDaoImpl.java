package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.EfinanceEntity;
import com.cmw.dao.inter.crm.EfinanceDaoInter;


/**
 * 企业财务状况  DAO实现类
 * @author 彭登浩
 * @date 2012-12-24T00:00:00
 */
@Description(remark="企业财务状况DAO实现类",createDate="2012-12-24T00:00:00",author="彭登浩")
@Repository("efinanceDao")
public class EfinanceDaoImpl extends GenericDaoAbs<EfinanceEntity, Long> implements EfinanceDaoInter {

}
