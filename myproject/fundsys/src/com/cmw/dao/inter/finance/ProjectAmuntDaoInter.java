package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

import com.cmw.entity.finance.AmountRecordsEntity;
import com.cmw.entity.finance.ProjectAmuntEntity;


/**
 * 项目费用表  DAO接口
 * @author 程明卫
 * @date 2013-01-15T00:00:00
 */
 @Description(remark="项目费用表Dao接口",createDate="2013-01-15T00:00:00",author="liting")
public interface ProjectAmuntDaoInter  extends GenericDaoInter<ProjectAmuntEntity, Long>{
	 /**
		 * 获取正常扣收数据
		 * @param params	查询参数
		 * @param offset	分页起始位
		 * @param pageSize  每页大小
		 * @return 返回 DataTable 对象
		 * @throws DaoException
		 */
		 public DataTable getNomalPlans(SHashMap<String, Object> params,int offset, int pageSize) throws DaoException;
	 
	 /**
	 * 获取逾期实收金额记录信息
	 * @param params
	 * @return
	 * @throws DaoException 
	 */
	public DataTable getLateRecords(SHashMap<String, Object> params) throws DaoException;
	 /**
	  * 根据实收金额日志ID获取放款数据
	  * @param params	过滤参数
	  * @param offset	分页的起始位置
	  * @param pageSize	每页大小
	  * @return	返回 DataTable 对象
	  * @throws DaoException
	  */
	 <K, V> DataTable getDsByAmountLogId(SHashMap<K, V> params, int offset, int pageSize)throws DaoException;
}
