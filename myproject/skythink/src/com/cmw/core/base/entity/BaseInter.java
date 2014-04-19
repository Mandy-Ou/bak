package com.cmw.core.base.entity;
/**
 * BaseInter 接口提供了获取对象的字段数组，和值数组
 * @author cmw_1984122
 *
 */
public interface BaseInter {
	/**
	 * 获取Entity 对象的字段信息
	 * @return 以字符串数组返回对象的 name 
	 */
	 String[] getFields();
	/**
	 * 获取Entity 对象的数据
	 * @return 以Object 数组返回对象的数据
	 */
	 Object[] getDatas();
}
