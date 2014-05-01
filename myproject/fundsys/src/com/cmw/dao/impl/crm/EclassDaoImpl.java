package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.EclassEntity;
import com.cmw.dao.inter.crm.EclassDaoInter;


/**
 * 企业领导班子  DAO实现类
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="企业领导班子DAO实现类",createDate="2012-12-26T00:00:00",author="pdh")
@Repository("eclassDao")
public class EclassDaoImpl extends GenericDaoAbs<EclassEntity, Long> implements EclassDaoInter {

}
