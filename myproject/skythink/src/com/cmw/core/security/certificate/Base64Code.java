package com.cmw.core.security.certificate;

import org.apache.commons.codec.binary.Base64;

public class Base64Code{

    /**  
     * 编码  
     * @param filecontent  
     * @return String  
     */ 
    public static String encode(byte[] data){ 
    	return Base64.encodeBase64String(data);  
    }  
 
    /**  
     * 编码 字符串数据
     * @param filecontent  
     * @return String  
     */ 
    public static String encode(String data){  
    	return Base64.encodeBase64String(data.getBytes());  
    }  
    
    /**  
     * 解码  
     * @param filecontent  
     * @return string  
     */ 
    public static byte[] decode(String str){  
	    byte[] bt = null;  
	    bt = Base64.decodeBase64(str);  
        return bt;  
    }  
    
    /**  
     * 将Base64 数据字符串 解码成正常字符串  
     * @param filecontent  
     * @return string  
     */ 
    public static String decode2str(String str){  
    	byte[] bt = Base64.decodeBase64(str);  
        return new String(bt);  
    }  
 
    /**  
     * @param args  
     */ 
    public static void main(String[] args) {  
    	Base64Code te = new Base64Code();  
        String oldStr = "_assurance_acceptting&source=_mainframe_desktop&stage=5&projectId=29&customerId=14&custType=1&projectNumber=HRZ1200001&todo=_assurance_project_projectstate";  
       String  encodeStr = te.encode(oldStr);  
        System.out.println("编码后:"+encodeStr
        		+"\n 长度："+encodeStr.length());  
          
          
        String str2 = new String(te.decode(encodeStr));  
        System.out.println("原字符串长度:"+oldStr.length()+",解码字符串长度:"+str2.length());  
    }  
}
