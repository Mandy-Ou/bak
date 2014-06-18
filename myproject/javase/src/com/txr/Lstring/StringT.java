package com.txr.Lstring;

import org.junit.Test;

public class StringT {
	String SValu="ABCDEabcde";
	@Test
	public void testM(){
		/**string提供很多常用的方法：*/
		System.out.println(SValu.charAt(1));//参数：从零开始最后一个是String.length-1
		System.out.println(SValu.codePointAt(1));
		System.out.println(SValu.compareTo("abcde"));//按照字典顺序如果参数里面的字符串在String之前就会返回一个负数之后就是会返回一个整数：相等返回零
		System.out.println(SValu.endsWith("cde"));//如果Stirng以参数结尾就返回true否者返回false
		System.out.println(SValu.equals("asd"));//字符串的值进行比较
		System.out.println(SValu.equalsIgnoreCase("abcdeabcde"));//不区分大小写的方式进行比较
		System.out.println(SValu.length());//返回字符串的长度
		System.out.println(SValu.toLowerCase());//吧大写转换小写
		System.out.println(SValu.trim());//去空格
	}
	@Test 
	public void testStringbuilder(){
		/*字符串创建器StringBuilder
		 * 
		 * */
		StringBuilder sBil=new StringBuilder();
		sBil.append(SValu);
		sBil.append("|");
		sBil.append(SValu);//追加
		sBil.insert(1, "1231");//在制定位置插入字符串
		sBil.delete(1, 2);//在制定位置删除字符串
		System.out.println(sBil.toString());//使用toString（）方法输出
		
	}

}
