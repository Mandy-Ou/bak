package com.cmw.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.cmw.core.base.annotation.Description;

/**
 * 文件加载类
 * 可根据MyFileClassLoader 从文件中动态生成类
 * @author chengmingwei
 *
 */
public class FileClassLoader extends ClassLoader {
	
	
	public FileClassLoader() {
		  this(getSystemClassLoader());  
	}


	public FileClassLoader(ClassLoader systemClassLoader) {
		super(systemClassLoader);
	}


	private String classPath;
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		FileClassLoader fileClsLoader = new FileClassLoader();
		fileClsLoader.setClassPath("E:\\j2ee_proj\\skythink\\WebContent\\WEB-INF\\classes");
		Class cls = fileClsLoader.loadClass("com.cmw.entity.sys.AccordionEntity");
//		Object obj = cls.newInstance();
		Description des = (Description)cls.getAnnotation(Description.class);
		System.out.println(des.remark()+" , "+des.author()+" , "+des.createDate());
//		Field[] flds = cls.getDeclaredFields();
//		for(Field fld : flds){
//			System.out.println(fld.getName()+","+fld.getAnnotations());
//		}
		System.out.println("======================================");
		cls.getInterfaces();
		Method[] mthds = cls.getMethods();
		for(Method mthd : mthds){
			String methodName = mthd.getName();
			System.out.println("mthd.name="+methodName);
		}
//		System.out.println("obj.class="+obj.getClass().getName());
//		System.out.println("obj.class="+cls.getClassLoader().toString());
//		System.out.println("obj.class="+cls.getClassLoader().getParent().toString());
	}
	
	/**
	 * 根据类名字符串从指定的目录查找类，并返回类对象
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = null;
		Class<?> cls = null;
		try {
			classData = loadClassData(name);
			cls = super.defineClass(name, classData, 0, classData.length);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("exception:"+name);
			cls = Class.forName(name);
		}
		return cls;
	}
	
	/**
	 * 根据类名字符串加载类 byte 数据流
	 * @param name	类名字符串  例如： com.cmw.entity.SysEntity
	 * @return	返回类文件 byte 流数据
	 * @throws IOException
	 */
	private byte[] loadClassData(String name) throws IOException{
		File file = getFile(name);
		FileInputStream fis = new FileInputStream(file);
		byte[] arrData = new byte[(int)file.length()];
		fis.read(arrData);
		return arrData;
	}
	
	/**
	 * 根据类名字符串返回一个 File 对象
	 * @param name	类名字符串	
	 * @return	 File 对象
	 * @throws FileNotFoundException
	 */
	private File getFile(String name) throws FileNotFoundException {
		File dir = new File(classPath);
		if(!dir.exists()) throw new FileNotFoundException(classPath+" 目录不存在！");
		String _classPath = classPath.replaceAll("[\\\\]", "/");
		int offset = _classPath.lastIndexOf("/");
		name = name.replaceAll("[.]", "/");
		if(offset != -1 && offset < _classPath.length()-1){
			_classPath += "/";
		}
		_classPath += name +".class";
		dir = new File(_classPath);
		if(!dir.exists()) throw new FileNotFoundException(dir+" 不存在！");
		return dir;
	}

	public String getClassPath() {
		return classPath;
	}

	public void setClassPath(String classPath) {
		this.classPath = classPath;
	}

	
}
