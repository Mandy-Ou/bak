package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.crm.ConsortDaoInter;
import com.cmw.entity.crm.ConsortEntity;


/**
 * 个人客户配偶信息  DAO实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="个人客户配偶信息DAO实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Repository("consortDao")
public class ConsortDaoImpl extends GenericDaoAbs<ConsortEntity, Long> implements ConsortDaoInter {
	
}
