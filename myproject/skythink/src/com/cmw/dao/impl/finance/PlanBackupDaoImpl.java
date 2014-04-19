package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.finance.PlanBackupEntity;
import com.cmw.dao.inter.finance.PlanBackupDaoInter;


/**
 * 还款计划备份表  DAO实现类
 * @author 程明卫
 * @date 2014-01-07T00:00:00
 */
@Description(remark="还款计划备份表DAO实现类",createDate="2014-01-07T00:00:00",author="程明卫")
@Repository("planBackupDao")
public class PlanBackupDaoImpl extends GenericDaoAbs<PlanBackupEntity, Long> implements PlanBackupDaoInter {

}
