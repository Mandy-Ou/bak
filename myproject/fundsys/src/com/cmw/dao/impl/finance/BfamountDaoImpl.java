package com.cmw.dao.impl.finance;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.finance.BfamountEntity;
import com.cmw.dao.inter.finance.BfamountDaoInter;


/**
 * 预收帐款  DAO实现类
 * @author 程明卫
 * @date 2013-02-28T00:00:00
 */
@Description(remark="预收帐款DAO实现类",createDate="2013-02-28T00:00:00",author="程明卫")
@Repository("bfamountDao")
public class BfamountDaoImpl extends GenericDaoAbs<BfamountEntity, Long> implements BfamountDaoInter {

}
