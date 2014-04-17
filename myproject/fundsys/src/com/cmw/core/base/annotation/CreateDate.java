package com.cmw.core.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Date;

import com.cmw.core.util.StringHandler;


@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateDate {
	public enum DateType{
		CREATEDATE{
			String getDateInfo(){
				return StringHandler.dateFormatToStr(StringHandler.DATE_FORMAT, new Date());
			}
		},
		CREATEDATETIME{
			String getDateInfo(){
				return StringHandler.dateFormatToStr(StringHandler.DATE_TIME_FORMAT, new Date());
			}
		};
		abstract String getDateInfo();
	}
	static final String  DATE_ = DateType.CREATEDATETIME.getDateInfo();
	String value() default  "";
}
