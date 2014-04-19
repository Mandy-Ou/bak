package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.sys.FieldPropDaoInter;
import com.cmw.entity.sys.FieldPropEntity;


/**
 * 字段属性表  DAO实现类
 * @author pengdenghao
 * @date 2012-11-23T00:00:00
 */
@Description(remark="字段属性表DAO实现类",createDate="2012-11-23T00:00:00",author="pengdenghao")
@Repository("fieldPropDao")
public class FieldPropDaoImpl extends GenericDaoAbs<FieldPropEntity, Long> implements FieldPropDaoInter {

}
