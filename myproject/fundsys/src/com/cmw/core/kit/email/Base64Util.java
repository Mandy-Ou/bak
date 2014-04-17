package com.cmw.core.kit.email;


import com.cmw.core.security.certificate.Base64Code;

public class Base64Util {

	public static String getBASE64(String s) {
   	 if (s == null) return null;
   	 	return 	Base64Code.encode(s);
    } 
    
   
	public static String getFromBASE64(String s) {
		if (s == null) return null;
		return Base64Code.decode2str(s);
	} 
	
	public static void main(String [] args)throws Exception{
//		String b = getBASE64("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqq12");
		String test = "";
		String [] array = test.split(";");
	}
}
