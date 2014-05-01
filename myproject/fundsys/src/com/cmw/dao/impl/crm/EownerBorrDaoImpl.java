package com.cmw.dao.impl.crm;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.crm.EownerBorrEntity;
import com.cmw.dao.inter.crm.EownerBorrDaoInter;


/**
 * 所有者借款情况  DAO实现类
 * @author pdh
 * @date 2012-12-26T00:00:00
 */
@Description(remark="所有者借款情况DAO实现类",createDate="2012-12-26T00:00:00",author="pdh")
@Repository("eownerBorrDao")
public class EownerBorrDaoImpl extends GenericDaoAbs<EownerBorrEntity, Long> implements EownerBorrDaoInter {

}
