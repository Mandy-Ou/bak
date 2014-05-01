package com.cmw.service.inter.finance;

import java.util.HashMap;

import com.cmw.core.base.annotation.Description;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.base.service.IService;
import com.cmw.core.util.DataTable;

/**
 * 民汇明细表报
 *Title: LedgerDeatilService.java
 *@作者： 彭登浩
 *@ 创建时间：2013-12-11上午11:59:17
 *@ 公司：	同心日信科技有限公司
 */
@Description(remark="明细表报接口",createDate="2013-12-06 20:10:00",author="彭登浩")
public interface LedgerDeatilService extends IService<Object, Long> {

	/**
	 * @param params
	 * @return
	 * @throws ServiceException
	 */
	DataTable getDataSource(HashMap<String, Object> params)throws ServiceException;

}
