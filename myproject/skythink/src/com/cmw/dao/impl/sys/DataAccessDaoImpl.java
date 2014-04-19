package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.DataAccessEntity;
import com.cmw.dao.inter.sys.DataAccessDaoInter;


/**
 * 数据访问权限  DAO实现类
 * @author 程明卫
 * @date 2012-12-28T00:00:00
 */
@Description(remark="数据访问权限DAO实现类",createDate="2012-12-28T00:00:00",author="程明卫")
@Repository("dataAccessDao")
public class DataAccessDaoImpl extends GenericDaoAbs<DataAccessEntity, Long> implements DataAccessDaoInter {

}
