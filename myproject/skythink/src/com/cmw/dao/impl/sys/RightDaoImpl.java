package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.RightEntity;
import com.cmw.dao.inter.sys.RightDaoInter;


/**
 * 角色菜单权限  DAO实现类
 * @author chengmingwei
 * @date 2011-09-24T00:00:00
 */
@Description(remark="角色菜单权限DAO实现类",createDate="2011-09-24T00:00:00",author="chengmingwei")
@Repository("rightDao")
public class RightDaoImpl extends GenericDaoAbs<RightEntity, Long> implements RightDaoInter {

}
