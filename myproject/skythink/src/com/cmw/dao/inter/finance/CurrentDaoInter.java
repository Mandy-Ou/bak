package com.cmw.dao.inter.finance;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.dao.GenericDaoInter;
import com.cmw.core.base.exception.DaoException;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.SHashMap;

/**
 * 随借随还 dao 接口
 *Title: currentDaoInter.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-25下午12:54:40
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="随借随还 dao 接口",createDate="2013-11-22T00:00:00",author="彭登浩")
public interface CurrentDaoInter  extends GenericDaoInter<Object, Long>{

	/**随借随还dao 接口
	 * @param map
	 * @param start
	 * @param limit
	 * @return dt
	 */
	DataTable getCurrentList(SHashMap<String, Object> map, int start, int limit) throws DaoException;
	
}
