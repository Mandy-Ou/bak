package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.finance.MortgageEntity;
import com.cmw.dao.inter.finance.MortgageDaoInter;


/**
 * 抵押物  DAO实现类
 * @author pdh
 * @date 2013-01-06T00:00:00
 */
@Description(remark="抵押物DAO实现类",createDate="2013-01-06T00:00:00",author="pdh")
@Repository("mortgageDao")
public class MortgageDaoImpl extends GenericDaoAbs<MortgageEntity, Long> implements MortgageDaoInter {

}
