package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.RoleModEntity;
import com.cmw.dao.inter.sys.RoleModDaoInter;


/**
 * 角色模块配置  DAO实现类
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="角色模块配置DAO实现类",createDate="2013-03-08T00:00:00",author="程明卫")
@Repository("roleModDao")
public class RoleModDaoImpl extends GenericDaoAbs<RoleModEntity, Long> implements RoleModDaoInter {

}
