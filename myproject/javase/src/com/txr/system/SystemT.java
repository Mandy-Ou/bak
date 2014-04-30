package com.txr.system;


import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Scanner;

import org.junit.Test;

public class SystemT {
	public final Scanner sc=new Scanner(System.in);
	@Test
	public void outAerror(){
		/*system类的输出流*/
		System.out.println("输出调试信息");//system类的静态方法不用实例化:输出调试信息黑色字体
		System.err.println("输出错误信息");//输出错误信息:红色字体:
		Scanner scanner=new Scanner(System.in);
		System.err.println("请输入信息:");
		String s=	scanner.nextLine();
		System.out.println(s.length());
		/*System类的setOut(PrintOut out)方法*/
		PrintStream out=System.out;//保存原输出流
		try {
			PrintStream ps=new PrintStream("D:/log.log");
			System.setOut(ps);//设置使用新的输出流
			System.out.println("这是开始:......");
			String info="这是日志信息info";
			System.out.println("这是开始:......"+info);
			System.setOut(out);//恢复原来的输出流
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void Scanner(){
		Scanner sc=new Scanner(System.in);
		System.err.println("请输入信息");
		String sInt=sc.nextLine();
		char[] ch=sInt.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			ch[i]=(char) (ch[i]^200);//对每个数组进行异或运算
		}
		System.err.println("加密后的信息"+new String(ch));
		
	}
	@Test
	public void checkNum(){
		Scanner sc=new Scanner(System.in);
		System.err.println("请输入信息");
		Long num= sc.nextLong();
		String out=(num%2==0) ? "您输入的是偶数":"您输入的是奇数";
		System.out.println(out);
		
	}
	@Test
 public void bigdemical(){
		/*商业算法中应用bigDecimal这样才能把金额精确*/
		double num1=2-1.1;
		System.err.println(num1);
		BigDecimal num2=new BigDecimal(2);
		BigDecimal num3=new BigDecimal(1.1);
		BigDecimal num4= num2.subtract(num3);
		System.err.println(num4);
	}
@Test
	public void bigS(){
		System.out.println("请输入一个数字:");
		Long num=	sc.nextLong();
		System.out.println("输入的数字:"+num);
		System.err.println("输入的数字乘以2的结果是:"+(num<<1));
		System.err.println("输入的数字乘以4的结果是:"+(num<<2));
		System.err.println("输入的数字乘以8的结果是:"+(num<<3));
		System.err.println("输入的数字乘以16的结果是:"+(num<<4));
		/*从上面可以看出一个数字执行左移n次运算就相当于这个数乘与2的n次方*/
	}
@Test
public void changePass(){
	System.err.println("输入用户名:");
	String uname=sc.nextLine();
	System.err.println("输入密码:");
	String upass=sc.nextLine();
	if(!uname.equals("123")){
		System.out.println("用户名错误");
		
		
	}else if(!upass.equals("123")){
		System.out.println("密码错误");
	}else{
		System.out.println("欢迎"+uname);
	}
	
}
}
