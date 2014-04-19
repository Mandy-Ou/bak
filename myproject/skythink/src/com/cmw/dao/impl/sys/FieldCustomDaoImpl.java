package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.sys.FieldCustomDaoInter;
import com.cmw.entity.sys.FieldCustomEntity;


/**
 * 自定义字段表  DAO实现类
 * @author pengdenghao
 * @date 2012-11-26T00:00:00
 */
@Description(remark="自定义字段表DAO实现类",createDate="2012-11-26T00:00:00",author="pengdenghao")
@Repository("fieldCustomDao")
public class FieldCustomDaoImpl extends GenericDaoAbs<FieldCustomEntity, Long> implements FieldCustomDaoInter {
}
