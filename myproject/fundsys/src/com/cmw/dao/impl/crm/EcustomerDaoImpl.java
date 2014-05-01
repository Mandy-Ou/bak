package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.EcustomerEntity;
import com.cmw.dao.inter.crm.EcustomerDaoInter;


/**
 * 企业客户基本信息  DAO实现类
 * @author pdh
 * @date 2012-12-21T00:00:00
 */
@Description(remark="企业客户基本信息DAO实现类",createDate="2012-12-21T00:00:00",author="pdh")
@Repository("ecustomerDao")
public class EcustomerDaoImpl extends GenericDaoAbs<EcustomerEntity, Long> implements EcustomerDaoInter {

}
