package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.finance.PledgeDaoInter;
import com.cmw.entity.finance.PledgeEntity;


/**
 * 质押物  DAO实现类
 * @author pdh
 * @date 2013-01-08T00:00:00
 */
@Description(remark="质押物DAO实现类",createDate="2013-01-08T00:00:00",author="pdh")
@Repository("pledge Dao")
public class PledgeDaoImpl extends GenericDaoAbs<PledgeEntity, Long> implements PledgeDaoInter {

}
