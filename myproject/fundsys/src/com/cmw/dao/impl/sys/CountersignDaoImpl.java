package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.CountersignEntity;
import com.cmw.dao.inter.sys.CountersignDaoInter;


/**
 * 会签记录  DAO实现类
 * @author 程明卫
 * @date 2013-11-22T00:00:00
 */
@Description(remark="会签记录DAO实现类",createDate="2013-11-22T00:00:00",author="程明卫")
@Repository("countersignDao")
public class CountersignDaoImpl extends GenericDaoAbs<CountersignEntity, Long> implements CountersignDaoInter {

}
