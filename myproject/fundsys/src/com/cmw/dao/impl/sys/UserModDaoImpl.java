package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.UserModEntity;
import com.cmw.dao.inter.sys.UserModDaoInter;


/**
 * 用户模块配置  DAO实现类
 * @author 程明卫
 * @date 2013-03-08T00:00:00
 */
@Description(remark="用户模块配置DAO实现类",createDate="2013-03-08T00:00:00",author="程明卫")
@Repository("userModDao")
public class UserModDaoImpl extends GenericDaoAbs<UserModEntity, Long> implements UserModDaoInter {

}
