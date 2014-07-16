package com.txr.annotation;

import org.junit.Test;

@MyAnnotation("我的第一个annotation")
public class TestAnnotation {
	
	@MyAnnotation2(isAnnotation=true,value="我的第一个annotation2")
	@Test
	public void testAn(){
		System.out.println("test");
	}

}
