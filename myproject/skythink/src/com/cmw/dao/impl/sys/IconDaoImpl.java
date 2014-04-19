package com.cmw.dao.impl.sys;


import org.springframework.stereotype.Repository;
import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoAbs;
import com.cmw.entity.sys.IconEntity;
import com.cmw.dao.inter.sys.IconDaoInter;


/**
 * 系统图标  DAO实现类
 * @author 程明卫
 * @date 2013-08-24T00:00:00
 */
@Description(remark="系统图标DAO实现类",createDate="2013-08-24T00:00:00",author="程明卫")
@Repository("iconDao")
public class IconDaoImpl extends GenericDaoAbs<IconEntity, Long> implements IconDaoInter {

}
