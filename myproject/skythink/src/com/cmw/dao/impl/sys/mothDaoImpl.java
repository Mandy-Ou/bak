package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.mothEntity;
import com.cmw.dao.inter.sys.mothDaoInter;


/**
 * 月份  DAO实现类
 * @author 彭登涛
 * @date 2013-02-25T00:00:00
 */
@Description(remark="月份DAO实现类",createDate="2013-02-25T00:00:00",author="彭登涛")
@Repository("mothDao")
public class mothDaoImpl extends GenericDaoAbs<mothEntity, Long> implements mothDaoInter {

}
