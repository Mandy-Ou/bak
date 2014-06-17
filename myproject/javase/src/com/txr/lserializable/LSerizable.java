package com.txr.lserializable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.Test;

public class LSerizable implements Serializable {
	//一个类为什么要实现序列化：
	/**
	 * 1.保存对象的状态：
	 * 		由于java中的对象是存放在堆中的。而堆内存随时可以被垃圾回收器回收（以为着对象和对象所占的
	 * 成员变量也会被回收：）这样我们无法保存对象被回收之前的状态：（我们可以重新创建对象 ：不过创建的对象各种属性
	 * 又被重新初始化了----所以我们要保存对象的状态就需要在对象创建之后和被垃圾回收器处理的过程中保存对象的状态
	 * 可以使用序列化来保存java对象的状态：
	 * 2.序列化使用的对象：保存java对象状态---java的远程方法调用RMI时----在网络传输java对象的时候必须要使用序列化
	 * 总结：
	 * 	对象序列化就是为了持久化java对象和在网络中传输java对象
	 * 
	 */
	
	
	/**
	 * 通过实现serializable可以发现序列化接口是个空的接口 改接口中没有任何方法
	 * 进行对象序列化主要目的是为了保存对象的状态（成员变量）
	 * 要将某类的对象序列化，则该类必须实现Serializable接口，该接口仅是一个标志，告诉JVM该类的对象可以被序列化。如果某类未实现Serializable接口，则该类对象不能实现序列化。
	 * 
	 */
	int age ;
	String name ;
	public LSerizable() {
	}
	public LSerizable(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

		
		
	
	

}
