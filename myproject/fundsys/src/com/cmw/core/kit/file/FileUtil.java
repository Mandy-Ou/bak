package com.cmw.core.kit.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.core.util.StringHandler;


/**
 * 文件操作类
 * @author chengmingwei
 *
 */
public class FileUtil {

	/**
	 * 判断指定的目录是否存在
	 * @param pathname	目录路径
	 * @return	返回  boolean 值 , true : 目录存在 ; false : 不存在
	 */
	public static boolean exist(String pathname){
		File file = new File(pathname);
		return file.exists();
	}
	
	/**
	 * 根据资源文件名返回一个资源文件对象
	 * @param properties_fileName   资源文件文件名
	 * @return	返回 ResourceBundle(资源文件对象)
	 * @throws Exception 
	 */
	public static ResourceBundle getResourceObj(String properties_fileName) throws Exception
	{
		if(null == properties_fileName || "".equals(properties_fileName)) throw new Exception("请传入一个有效的资源文件名!");
		String ext = properties_fileName.substring(properties_fileName.lastIndexOf(".")+1);
		String prefix = properties_fileName.substring(0,properties_fileName.lastIndexOf("."));
		if(!ext.toLowerCase().equals("properties")) throw new Exception("您传入的文件名,不是一个有效的资源文件!");
		
		ResourceBundle resourseObj = null;
		try {
			resourseObj = ResourceBundle.getBundle(prefix);  //只需通过文件名前缀来读取资源文件即可
		} catch (Exception e) {
			System.out.println("查找文件错误:找不到\""+properties_fileName+"\",请确定您的\""+properties_fileName+"\"是否放在src目录下!");
			throw e;
		}
		return resourseObj;
	}
	
	/**
	 * 将字符串写入文件
	 * @param filePath  文本文件存放的目录
	 * @param fileName  生成的txt和html的文件名
	 * @param content	要写入文件的内容
	 * 
	 */
	public static File writeStrToFile(String filePath,String fileName,String content)
	{
		File file = null;
		try {
			filePath = filePath.replaceAll("[\\\\]","/");
			File thePath = new File(filePath);
			//创建目录
			if(thePath.isDirectory()==false)
			{
				thePath.mkdirs();
			}			
			String fullFileName = filePath+fileName;
			System.out.println(fullFileName);
			//创建文件
			file = new File(fullFileName);
			if(file.exists()==true) file.delete();
			file.createNewFile();
			BufferedWriter wObj = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			wObj.write(content, 0, content.length());
			wObj.flush();
			wObj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * 将字符串写入文件
	 * @param filePath  文本文件存放的目录
	 * @param absFileName  绝对路径的文件名
	 * @param content	要写入文件的内容
	 * 
	 */
	public static File writeStrToFile(String absFileName,String content)
	{
		File file = null;
		try {
			absFileName = absFileName.replaceAll("[\\\\]","/");
			//创建文件
			file = new File(absFileName);
			BufferedWriter wObj = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8"));
			wObj.write(content, 0, content.length());
			wObj.flush();
			wObj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * 构造文件存放路径
	 * @example 示例: StringHandler.GetFilePath(request,"log");
	 * 	将返回一个绝对文件路径: F:\h2erp_fw\log\2009\3\
	 * @param request	HttpServletRequest 对象,用来取服务器真实路径
	 * @param parentDirectory 文件要存放的父目录
	 * @return	构造好的文件目录
	 */
	public static String getFilePath(HttpServletRequest request,String parentDirectory){
	    String sCurrentPath =request.getSession(true).getServletContext().getRealPath ("/");
        sCurrentPath = sCurrentPath.replaceAll("[\\\\]", "/");
		String filePath = sCurrentPath + parentDirectory;
        System.out.println("-->filePath:"+filePath);
        return filePath;
	}
	/**
	 * 	读取images 下面所有的文件夹,并以tree类型放回
	 */
	public static JSONObject readImgFolder(String path, JSONObject folder){
		File f = null;  
        f = new File(path);
        File[] files = f.listFiles(); // 得到文件夹下面的所有文件。 
        Boolean leaf = false;
        // 遍历file数组
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                    readImgFolder(files[i].getPath(),folder);
            }
        }
		return folder;
	} 
	 /**
	  * 读取一个文件夹下所有文件及子文件夹下的所有文件  
	  * @param filePath
	  */
    public static void ReadAllFile(String filePath,JSONObject objImages) {  
    	JSONArray jsonImages = objImages.getJSONArray("images");
        File f = null;  
        f = new File(filePath);
        File[] files = f.listFiles(); // 得到文件夹下面的所有文件。  
        Arrays.asList(files);
       
        for (File file : files) {  
            if(file.isDirectory()) {  
                //如何当前路劲是文件夹，则循环读取这个文件夹下的所有文件  
            	ReadAllFile(file.getAbsolutePath(),objImages);  
            } else { 
            	JSONObject  json =  new JSONObject();
            	String filename = file.getName(); 
            	Long FileSize = file.length();
            	long   modify	=   f.lastModified();	 //   修改时间 
            	json.put("name", filename);
            	json.put("size", FileSize);
            	json.put("lastmod",new SimpleDateFormat("yyyy-MM-dd").format(modify));
            	Integer start = file.getAbsolutePath().indexOf("images");
            	String absPath = file.getAbsolutePath().substring(start);
            	json.put("url", (absPath).replaceAll("[\\\\]", "/"));
            	jsonImages.add(json);
            }  
        }  
    } 
	/**
	 * 读取文件到字符串中
	 * @param filePathName 绝对路径的文件名
	 */
	public static String ReadFileToStr(String filePathName)
	{
		return readFileToStr(filePathName,"UTF-8");
	}
	/**
	 * 读取文件到字符串中
	 * @param filePathName 绝对路径的文件名
	 */
	public static String readFileToStr(String filePathName,String charEncoding)
	{
		filePathName = filePathName.replaceAll("[\\\\]","/");
		File file = new File (filePathName);
		StringBuffer content = new StringBuffer();
		try {
			if(!file.exists()) return "";
			
			BufferedReader bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),charEncoding));
			
			String data = null;
			try {
				while((data=bufReader.readLine()) != null)
				{
					content.append(data+"\n");
				}
				//关闭流
				bufReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		return content.toString();
	}
	
	public byte[] readFile(File f) throws FileNotFoundException, IOException{
		FileInputStream fis = new FileInputStream(f);
		 
		Long len = f.length();		 
		if(len>0){
			 byte[] b = new byte[len.intValue()];
			 fis.read(b, 0, len.intValue());
			 fis.close();
			 return b;
		}
		return null;
	}
	
	/**
	 * 删除文件
	 * @param fileName
	 */
	public static void delFile(String fileName)
	{
		fileName = fileName.replaceAll("[\\\\]","/");
		File delFile = new File(fileName);
	  	  if(delFile.exists())
	  	  {
	  		delFile.delete();
	  	  }
	}
	
	/**
	 * 创建目录
	 * @param absaboutePath  要创建的目录文件名 
	 *	例如:要在F盘创建excel目录.则absaboutePath="F:\excel"
	 *  
	 */
	public static void creatDictory(String absaboutePath) {
		java.io.File dir = new java.io.File(absaboutePath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	/**
	 * 文件复制
	 * @param in	要复制的文件
	 * @param out	复制的新文件
	 * @throws IOException
	 */
	public static void copyFile(File in, File out)throws IOException{
        FileChannel inChannel = new FileInputStream(in).getChannel();
        FileChannel outChannel = new FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(),outChannel);
        }catch (IOException e) {
            throw e;
        } finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
    }
	
	/**
	 * 文件复制
	 * @param sourcePath	要复制的文件名路径
	 * @param tagPath	复制的新文件名路径
	 * @throws IOException
	 * @throws NotFoundException 
	 */
	public static void copyFile(String sourcePath, String tagPath)throws IOException, NotFoundException{
	   if(!exist(sourcePath)) throw new NotFoundException("not find file ["+sourcePath+"]!"); 
	   File in = new File(sourcePath);
       File out = new File(tagPath);
		copyFile(in, out);
    }
	
	/**
	 * 文件重命名
	 * @param sourcePath	源文件名路径
	 * @param tagPath	新文件名
	 * @throws IOException
	 * @throws NotFoundException 
	 * @return 返回重命名后的文件的全绝对路径。例如：C:\windows\config.ini
	 */
	public static String rename(String sourcePath, String newFileName)throws IOException, NotFoundException{
	   if(!exist(sourcePath)) throw new NotFoundException("not find file ["+sourcePath+"]!"); 
	   File in = new File(sourcePath);
	   boolean isFile = false;
	   if(newFileName.indexOf(".") != -1){	//如果带扩展名，则说明是文件
		   isFile = true;
	   }
	   File dest = null;
	  boolean isSourceFile = in.isFile();
	  if(isFile == isSourceFile){
		  String destPath = in.getAbsolutePath();
		  int offset = destPath.lastIndexOf(File.separator);
		  destPath = destPath.substring(0,offset+1);
		  destPath+= newFileName;
		  dest = new File(destPath);
		  in.renameTo(dest);
	  }else{
		  String errMsg = null;
		  if(isFile){
			  errMsg = "源文件文件类型是[文件夹]，不能重命名为文件！";
		  }else{
			  errMsg = "源文件文件类型是[文件]，不能重命名为文件夹！";
		  }
		 if(StringHandler.isValidStr(errMsg)) throw new IOException(errMsg);
	  }
	  return (null != dest) ? dest.getAbsolutePath() : "";
    }
	
	public static void main(String[] args){
		String sourcePath = "F:\\dev\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\smartplatform\\pages\\app\\demo\\";
//		File file = new File("F:\\dev\\documents\\设计文档\\公共平台设计文档\\同心日基础平台设计文档.doc");
//		System.out.println(file.getName());
//		System.out.println(file.getPath());
//		String absPath = file.getAbsolutePath();
//		int offset = absPath.lastIndexOf(File.separator);
//		System.out.println("offset="+offset);
//		absPath = absPath.substring(0,offset+1);
//		System.out.println(absPath);
			File demoDir = new File(sourcePath);
			String[] list = demoDir.list();
			for(String f : list){
				System.out.println(f);
			}
			
	}
}
