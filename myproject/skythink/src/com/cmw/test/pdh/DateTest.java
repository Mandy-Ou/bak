package com.cmw.test.pdh;

import java.util.Date;

import com.cmw.core.util.DateUtil;

/**
 *Title: DateTest.java
 *@作者： 彭登浩
 *@ 创建时间：2012-11-18下午5:11:45
 *@ 公司：	同心日信科技有限公司
 */
public class DateTest {
	public static void main(String[] args) {
		Date  newDate= DateUtil.dateFormat(DateUtil.DATE_TIME_FORMAT, "2012-12-12 12:12:12");
		System.out.println(newDate);
		
		String newDate1 = DateUtil.dateFormatToStr(DateUtil.DATE_TIME_FORMAT,newDate);
		System.out.println(newDate1);
		String newDate2 = DateUtil.dateFormatToStr(newDate);
		System.out.println(newDate2);
	}
}
