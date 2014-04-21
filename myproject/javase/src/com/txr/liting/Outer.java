package com.txr.liting;
import static java.lang.System.out;

public class Outer {
	/**
	 * 访问类的私有的属性：
	 */
	private int x=0;
	
	class inner{//内部类
		
		public Integer getX(){
			
			return x;
		}
		
		
	}
	public inner getInner(){
		return new inner();
	}
	public static void main(String[] args) {
		Outer o=new Outer();
		Integer x= o.getInner().getX();
		System.out.println(x);
		out.print(x);//静态导入 import static  java.lang.system.out
	}
	

}
