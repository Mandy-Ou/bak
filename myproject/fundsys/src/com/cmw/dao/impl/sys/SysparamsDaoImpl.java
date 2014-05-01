package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.dao.inter.sys.SysparamsDaoInter;
import com.cmw.entity.sys.SysparamsEntity;


/**
 * 系统参数  DAO实现类
 * @author pengdenghao
 * @date 2012-11-28T00:00:00
 */
@Description(remark="系统参数DAO实现类",createDate="2012-11-28T00:00:00",author="pengdenghao")
@Repository("sysparamsDao")
public class SysparamsDaoImpl extends GenericDaoAbs<SysparamsEntity, Long> implements SysparamsDaoInter {

}
