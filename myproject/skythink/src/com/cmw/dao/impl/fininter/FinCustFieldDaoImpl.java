package com.cmw.dao.impl.fininter;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.fininter.FinCustFieldDaoInter;
import com.cmw.entity.fininter.FinCustFieldEntity;


/**
 * 财务自定义字段  DAO实现类
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
@Description(remark="财务自定义字段DAO实现类",createDate="2013-03-28T00:00:00",author="程明卫")
@Repository("finCustFieldDao")
public class FinCustFieldDaoImpl extends GenericDaoAbs<FinCustFieldEntity, Long> implements FinCustFieldDaoInter {

}
