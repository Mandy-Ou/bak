package com.cmw.core.base.cache;

import com.cmw.core.base.exception.ServiceException;

/**
 * 数据加载通知器接口
 * @author chengmingwei
 *
 */
public interface DataNotify{
	/**
	 * 加载数据的方法
	 * @param ids	要加载的对象的ID列表	
	 * @throws ServiceException 
	 */
	void load(String ids) throws ServiceException;
}
