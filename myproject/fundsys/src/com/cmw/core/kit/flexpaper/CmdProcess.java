package com.cmw.core.kit.flexpaper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.cmw.core.kit.file.FileUtil;


/** 
 * 该类启动doc界面 
 * @author Administrator 
 * 
 */  
public class CmdProcess extends Thread{  
	protected static Logger log = Logger.getLogger(CmdProcess.class);
    InputStream inputStream;  
  
    public InputStream getInputStream() {  
        return inputStream;  
    }  
  
    public void setInputStream(InputStream inputStream) {  
        this.inputStream = inputStream;  
    }  
  
    public CmdProcess() {  
        super();  
    }  
      
    public CmdProcess(InputStream inputStream) {  
        super();  
        this.inputStream = inputStream;  
    }  
  
    public void run() {  
        try {  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
            String line = null;  
            while ((line = bufferedReader.readLine()) != null){  
            //while ((bufferedReader.readLine()) != null) {//此处是关键,原因我也不知道，望牛人解答  
                System.out.println(line);
            }  
            try{                  
            }finally{  
                if(bufferedReader!=null) bufferedReader.close();  
                if(inputStreamReader!=null) inputStreamReader.close();  
                if(inputStream!=null) inputStream.close();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    
    /**
     * 开始命令服务
     * @param command 命令行字符串
     */
    public void startService(String command){
    	  try {
    		  String batFilePath = this.createBatFile(command);
              Process process = Runtime.getRuntime().exec(batFilePath);  
              CmdProcess cmdProcess = new CmdProcess(process.getInputStream());  
              cmdProcess.start();  
              process.waitFor();
          }catch(Exception e){  
              e.printStackTrace();  
          }  
    }
    
    private String createBatFile(String command){
    	 String path = CmdProcess.class.getResource("/").getPath();
         String batFileName = "startOpenOfficeService.bat";
         File batFile = FileUtil.writeStrToFile(path, batFileName, command);
         String batFilePath =  batFile.getAbsolutePath();;
         log.info("CmdProcess,startOpenOfficeService.bat ---> "+batFilePath);
    	return batFilePath;
    }
    
    /**
     * 判断指定的端口是否打开
     * @param host 主机IP地址
     * @param port	端口号
     * @return true : 该主机端口已打开, false ： 未打开
     */
    public static boolean isOpen(String host, int port){
        try{
            Socket socket = new Socket(host, port);
            socket.close();
            return true;
        }catch (IOException e){
            return false;
        }
    }
    
    /** 
     * @param args 
     */  
    public static void main(String[] args) { 
    	String command = "cd /d D:\\Program Files\\OpenOffice.org 3\\program \n soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
    	CmdProcess cmdProcess = new CmdProcess();
//    	cmdProcess.startService(command);
    	System.out.println(isOpen("127.0.0.1", 8100));
//        String command = "cd /d D:\\Program Files\\OpenOffice.org 3\\program \n soffice -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\" -nofirststartwizard";
////        String resourceName = cmdProcess.getClass().getName();
//        String path = CmdProcess.class.getResource("/").getPath();
//        System.out.println(path);
//        String batFileName = "startOpenOfficeService.bat";
//        String batAbsPath = path + batFileName;
//        FileUtil.writeStrToFile(path, batFileName, command);
//        System.out.println(batAbsPath);
//    	 String command = "F:/dev/skythink/src/com/cmw/core/kit/flexpaper/startOpenOfficeService.bat";
//        try {  
//            Process process = Runtime.getRuntime().exec(command);  
//            CmdProcess cmdProcess = new CmdProcess(process.getInputStream());  
//            cmdProcess.start();  
//            process.waitFor();
//        }catch(Exception e){  
//            e.printStackTrace();  
//        }  
    }  
}  