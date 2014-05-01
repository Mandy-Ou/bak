package com.cmw.dao.inter.fininter;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.fininter.AcctGroupEntity;


/**
 * 科目组  DAO接口
 * @author 程明卫
 * @date 2013-03-28T00:00:00
 */
 @Description(remark="科目组Dao接口",createDate="2013-03-28T00:00:00",author="程明卫")
public interface AcctGroupDaoInter  extends GenericDaoInter<AcctGroupEntity, Long>{
}
