package com.cmw.core.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 描述说明
 * @author chengmingwei
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Description {
	public enum Type{
		/**
		 * 表示是对类的描述
		 */
		 CLASS,
		 /**
		  * 表示是对方法的描述
		  */
		 METHOD,
		 /**
		  *  表示是对字段的描述
		  */
		 FIELD
	}
	/**
	 * 说明
	 * @return	
	 */
	String remark();
	/**
	 * 标识描述类型
	 * 默认是对 类 进行描述
	 * @return
	 */
	Type type() default Type.CLASS;
	/**
	 * 作者
	 * @return
	 */
	String author() default "chengmingwei";
	/**
	 * 创建日期
	 * @return
	 */
	String createDate() default "";
	/**
	 * 默认值  
	 * 当对字段进行描述时，会用到
	 * @return
	 */
	String defaultVals() default "";
}
