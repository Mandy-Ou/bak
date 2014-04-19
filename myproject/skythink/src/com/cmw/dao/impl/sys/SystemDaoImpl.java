package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.sys.SystemDaoInter;
import com.cmw.entity.sys.SystemEntity;


/**
 * 系统  DAO实现类
 * @author chengmingwei
 * @date 2012-10-28T00:00:00
 */
@Description(remark="系统DAO实现类",createDate="2012-10-28T00:00:00",author="chengmingwei")
@Repository("systemDao")
public class SystemDaoImpl extends GenericDaoAbs<SystemEntity, Long> implements SystemDaoInter {

}
