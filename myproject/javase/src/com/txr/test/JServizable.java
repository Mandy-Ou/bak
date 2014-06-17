package com.txr.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;

import com.txr.lserializable.LSerizable;

public class JServizable {
	@Test
	public void testSer() {
		//java对象的序列化
		LSerizable ser=new LSerizable(11, "李听");
		try {
			ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("D:/a.txt"));
			out.writeObject(ser);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	@Test
	public void testConSer() throws FileNotFoundException, IOException, ClassNotFoundException{
	/**
	 * 使用ObjectOutPutStream（）和fileoutputstream将对象序列化：
	 * 使用ObjectInputStream（）和fileinputstream将对象反序列化
	 */
		LSerizable o1=new LSerizable(1, "李听");
		LSerizable o2=new LSerizable(2, "赵世龙");
		LSerizable o3=new LSerizable(3, "程敏伟");
		ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream("D:/out.txt"));
		out.writeObject(o1);
		out.writeObject(o2);
		out.writeObject(o3);
//		out.close();
		try {
			ObjectInputStream in=new ObjectInputStream(new FileInputStream("D:/out.txt"));
			LSerizable l1= (LSerizable) in.readObject();
			LSerizable l2=(LSerizable) in.readObject();
			LSerizable l3=(LSerizable) in.readObject();
			System.out.println(l1.getName()+l1.getAge());
			System.out.println(l2.getName()+l2.getAge());
			System.out.println(l3.getName()+l3.getAge());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	
	
}