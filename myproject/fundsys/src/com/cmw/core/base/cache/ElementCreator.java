package com.cmw.core.base.cache;

import net.sf.ehcache.Element;

/**
 * 缓存元素创建接口
 * @author chengmingwei
 *
 */
public interface ElementCreator{
	/**
	 * 缓存元素创建接口
	 * @param obj
	 * @return
	 */
	<T> Element create(T obj);
}
