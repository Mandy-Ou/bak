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
//		Lesson1.testS();//调用静态方法
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
	public static void testS(){
		/**
		 * 
		 *static关键字修饰的方法是:静态方法~属于整个类 的方法调用的时候直接使用类名.静态方法名();
		 */
		System.out.println("静态方法");
	}
	final void testF(){
		
		//=最终的方法:使用该方法的时候父类定义了 子类就不能够重写:此方法是最终的
	}
}
