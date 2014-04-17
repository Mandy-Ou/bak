package com.txr.liting;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

import org.junit.Test;

/**
 * 
 * @ClassName: Lesson1  @author: liting
 * @date: 2014年4月17日 上午11:58:32  @Description: 使用hashtable来进行重复字符串的过滤
 */
public class Lesson1 {
	public static void main(String[] args) {
		
	}
	@Test
	public void test1(){
	String[] s={"1","2333333333","44","33","44","3424234"};
		for(int i=0;i<s.length;i++){
		System.out.println(chackArr(s)[i]);
		}
	}
	public String[] chackArr(String[] str){
	Hashtable<String, String> table=new Hashtable<String,String>();
	for(int i=0;i<str.length;i++){
		if(!table.containsKey(str[i])){
			table.put(str[i], str[i]);
		}
	}
	Enumeration<String> e=table.keys();
	String[] new_str=new String[table.size()];
	int i=0;
	while(e.hasMoreElements()){
		new_str[i]=e.nextElement().toString();
		i++;
		
	}
		return new_str;
	}
}
