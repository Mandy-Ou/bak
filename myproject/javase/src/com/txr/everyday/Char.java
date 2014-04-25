package com.txr.everyday;

public class Char{
	public static void main(String[] args) {
		System.setProperty("file.encodint", "gb2312");//设置字符串属性的值
		/**
		 * servlet对乱码的处理:
		 * 向浏览器传送中文:response.setContentType("text/html",charset=gb2312");
		 *或者使用:response.setContentType("text/html"); response.setHeader("charset","gb2312");
		 设置之后可以使用HttpservletResponse对象的getEcnoding()方法来产看系统编码
		 */
		
		
	}
	

}
