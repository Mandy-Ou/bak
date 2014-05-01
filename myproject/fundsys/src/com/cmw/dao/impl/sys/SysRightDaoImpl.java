package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.SysRightEntity;
import com.cmw.dao.inter.sys.SysRightDaoInter;


/**
 * 系统权限  DAO实现类
 * @author 程明卫
 * @date 2012-12-28T00:00:00
 */
@Description(remark="系统权限DAO实现类",createDate="2012-12-28T00:00:00",author="程明卫")
@Repository("sysRightDao")
public class SysRightDaoImpl extends GenericDaoAbs<SysRightEntity, Long> implements SysRightDaoInter {

}
