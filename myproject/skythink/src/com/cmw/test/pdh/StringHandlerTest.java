package com.cmw.test.pdh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cmw.core.util.StringHandler;

/**
 *Title: StringHandlerTest.java
 *@作者： 彭登浩
 *@ 创建时间：2012-11-16下午8:31:30
 *@ 公司：	同心日信科技有限公司
 */
public class StringHandlerTest {

	/**
	 * @param args
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "unchecked", "unchecked" })
	public static void main(String[] args) {
		String Country = "中 华 人 民 共 和 国";
		String PCountry= StringHandler.SplitString(Country, Country.length(), "...");
		System.out.println(PCountry);
		String Pcountry1 = StringHandler.SplitString(Country, Country.length());
		System.out.println(Pcountry1);
		StringHandler.P(Country);
		String[]  Pcountry2 = StringHandler.splitStr(Country, " ");
		for(int i=0;i<Pcountry2.length;i++){
			System.out.println(Pcountry2[i]);
		}
			
		boolean www=StringHandler.isValidStr(Pcountry1);
		System.out.println(www);
		
		/**
		 * 验证字符串是否为空
		 */
		int i=111;
		boolean eeee= StringHandler.isValidIntegerNull(i);
		System.out.println(eeee);
		/**
		 * 将字符串转换为整型，如果字符串为 空 或 “” 就为0
		 */
		String i1="111";
		Integer eeee1= StringHandler.convertInt(i1);
		System.out.println(eeee1);
		/**
		 * 将字符串转换成日期
		 */
		 String strdate = "2009-1-22";
		 String parrent = "yyyy-mm-dd";
		 Date date=StringHandler.dateFormat(parrent, strdate);
		 System.out.println(date);
		 /**
		  * 将日期转换成字符串
		  */
		 Date date1 = new Date(2009-1-22);
		 String parrent1 = "yyyy-mm-dd";
		 String  strdate1=StringHandler.dateFormatToStr(parrent1, date);
		 System.out.println(strdate1);
		  
		 String name="peng,deng,hao";
		 String[] myname=StringHandler.convertStrToArr(",",name);
		 boolean qqq=StringHandler.isValidSigin(",");
		 System.out.println(qqq);
		 for(String x : myname){
			 System.out.println(x);
		 }
		 
		 String myname1=StringHandler.replaceSigins(name, ",", ";");
		 System.out.println(myname1);
		 
		 String ooo =StringHandler.GetTrueStr(name);
		 System.out.println(ooo);
		 
		 String lm="不懂就要问";
		 String ss=StringHandler.getStr(lm);
		 StringHandler.P(lm, name);
		 
		 
		 /**
		  *验证大小写
		  */
		 String  check="wowkdiewjiwnc";
		 boolean pdh=StringHandler.Equals(check, true, "wowkdiewjiwnc");
		 System.out.println(pdh);
		 
		 String qwe="";
		 String GetValue=StringHandler.GetValue(qwe, "这是处理空值时候的默认值");
		 System.out.println(GetValue);
		 
		 String GetValue1 = StringHandler.GetValue(lm, qwe,"www");
		 System.out.println(GetValue1);
		 
		 String IsNumeric="111";
		 boolean isNumeric=StringHandler.IsNumeric(IsNumeric);
		 System.out.println(isNumeric);
	
		 
		 String isint="222.00";
		 boolean isints = StringHandler.IsInteger(isint);
		 System.out.println(isints);
		 
		 String pdh2="wweee";
		  StringHandler.Paste(pdh2);
		 String[] split= StringHandler.Split(",", pdh2);
		 String[] split1=StringHandler.Split(",", "ee", "pdh2");
		 for(String x: split1){
			 System.out.println(x);
		 }
		 
		 String pdh3="45.362635";
		 String i2=StringHandler.Round(pdh3, 3);
		 System.out.println(i2);
		 
		 String pdh4="11";
		 Integer i3=StringHandler.getIntegerVal(pdh4);
		 System.out.println(i3);
		 
		 String tel="13317505278";
		 boolean isornotel=StringHandler.IsMobile(tel);
		 System.out.println(isornotel);
		 
		 String pdh5="wojdiejci,wowowowo";
		 String[] pdh6=StringHandler.splitStrToArr(pdh5, ",");
		 for(String x : pdh6){
			 System.out.println(x);
		 }
		 
		 List javaArr= new ArrayList();
		 javaArr.add("111");
		 javaArr.add("deedje");
		 javaArr.add("www");
		 String jsArr = StringHandler.getJsArrStr(javaArr);
		 System.out.println(jsArr);
		 
		 String isfloat ="2222.33";
		 Object zhuanxin = 	StringHandler.getValByType(isfloat,"F");
		 System.out.println(zhuanxin);
		 
		 String getclasspath= StringHandler.getClassPath();
		 System.out.println(getclasspath);
		 
		
	}

}
