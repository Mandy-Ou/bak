package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.FieldValEntity;
import com.cmw.dao.inter.sys.FieldValDaoInter;


/**
 * 自定义字段值  DAO实现类
 * @author 程明卫
 * @date 2013-03-21T00:00:00
 */
@Description(remark="自定义字段值DAO实现类",createDate="2013-03-21T00:00:00",author="程明卫")
@Repository("fieldValDao")
public class FieldValDaoImpl extends GenericDaoAbs<FieldValEntity, Long> implements FieldValDaoInter {

}
