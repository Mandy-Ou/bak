package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.PamountRecordsEntity;
import com.cmw.entity.finance.ProjectAmuntEntity;


/**
 * 项目费用表  DAO接口
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
 @Description(remark="项目费用表Dao接口",createDate="2013-01-15T00:00:00",author="liting")
public interface PamountRecordsDaoInter  extends GenericDaoInter<PamountRecordsEntity, Long>{
	}
