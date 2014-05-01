package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.EassureEntity;
import com.cmw.dao.inter.crm.EassureDaoInter;


/**
 * 企业担保  DAO实现类
 * @author pdt
 * @date 2012-12-24T00:00:00
 */
@Description(remark="企业担保DAO实现类",createDate="2012-12-24T00:00:00",author="pdt")
@Repository("eassureDao")
public class EassureDaoImpl extends GenericDaoAbs<EassureEntity, Long> implements EassureDaoInter {

}
