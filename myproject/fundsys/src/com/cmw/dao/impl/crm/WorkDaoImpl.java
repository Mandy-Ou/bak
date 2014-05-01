package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.WorkEntity;
import com.cmw.dao.inter.crm.WorkDaoInter;


/**
 * 职业信息  DAO实现类
 * @author pdh
 * @date 2012-12-15T00:00:00
 */
@Description(remark="职业信息DAO实现类",createDate="2012-12-15T00:00:00",author="pdh")
@Repository("workDao")
public class WorkDaoImpl extends GenericDaoAbs<WorkEntity, Long> implements WorkDaoInter {

}
