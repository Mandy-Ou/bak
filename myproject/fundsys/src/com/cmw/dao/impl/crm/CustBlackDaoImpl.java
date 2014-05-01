package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.CustBlackEntity;
import com.cmw.dao.inter.crm.CustBlackDaoInter;


/**
 * 客户黑名单  DAO实现类
 * @author 程明卫
 * @date 2012-12-12T00:00:00
 */
@Description(remark="客户黑名单DAO实现类",createDate="2012-12-12T00:00:00",author="程明卫")
@Repository("custBlackDao")
public class CustBlackDaoImpl extends GenericDaoAbs<CustBlackEntity, Long> implements CustBlackDaoInter {

}
