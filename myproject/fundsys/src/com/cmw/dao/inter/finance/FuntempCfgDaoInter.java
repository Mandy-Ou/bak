package com.cmw.dao.inter.finance;



import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;

import com.cmw.entity.finance.FuntempCfgEntity;


/**
 * 模板功能配置表  DAO接口
 * @author 赵世龙
 * @date 2013-11-19T00:00:00
 */
 @Description(remark="模板功能配置表Dao接口",createDate="2013-11-19T00:00:00",author="赵世龙")
public interface FuntempCfgDaoInter  extends GenericDaoInter<FuntempCfgEntity, Long>{

	 public <K,V>DataTable getTempId(Long menuId) throws DaoException;
}
