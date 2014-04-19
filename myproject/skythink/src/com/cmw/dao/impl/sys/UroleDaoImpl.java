package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.UroleEntity;
import com.cmw.dao.inter.sys.UroleDaoInter;


/**
 * 用户角色关联  DAO实现类
 * @author chengmingwei
 * @date 2012-12-08T00:00:00
 */
@Description(remark="用户角色关联DAO实现类",createDate="2012-12-08T00:00:00",author="chengmingwei")
@Repository("uroleDao")
public class UroleDaoImpl extends GenericDaoAbs<UroleEntity, Long> implements UroleDaoInter {

}
