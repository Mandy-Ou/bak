package com.cmw.core.base.cache;


/**
 * 单个元素比较接口
 * @author chengmingwei
 *
 */
public interface ElementCompare {
	/**
	 * 元素比较方法 [ true : 是符合条件的元素,false:不符合条件 ]
	 * @param obj	要比较的对象
	 * @return
	 */
	public <T> boolean equals(T obj);
	
}
