package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.BussNodeEntity;
import com.cmw.dao.inter.sys.BussNodeDaoInter;


/**
 * 业务流程节点  DAO实现类
 * @author 程明卫
 * @date 2012-11-28T00:00:00
 */
@Description(remark="业务流程节点DAO实现类",createDate="2012-11-28T00:00:00",author="程明卫")
@Repository("bussNodeDao")
public class BussNodeDaoImpl extends GenericDaoAbs<BussNodeEntity, Long> implements BussNodeDaoInter {

}
