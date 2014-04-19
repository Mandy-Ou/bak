package com.cmw.dao.inter.finance;


import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;


/**
 *  业务统计报表DAO接口
 * @author 彭登浩
 * @date 2013-08-05T00:00:00
 */
 @Description(remark="业务统计报表 DAO接口",createDate="2013-08-05T00:00:00",author="彭登浩")
public interface LoanBusinessDaoInter  extends GenericDaoInter<Object, Long>{

	/**根据查询贷款业务
	 * @param params
	 * @return
	 */
	DataTable getLoanBusinessList(SHashMap<String, Object> params) throws DaoException;
	 
}