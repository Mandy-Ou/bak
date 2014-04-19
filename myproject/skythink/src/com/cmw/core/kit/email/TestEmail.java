package com.cmw.core.kit.email;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Part;
import javax.mail.internet.MimeMessage;

public class TestEmail {

	public static void test() throws Exception{
    	Map<String, Message> data = ReceiveMail.receiveMails("pop.qq.com", 110, "340360491", "cmw_1984122", null);
    	Set set = data.keySet();
    	Iterator it = set.iterator();
       
        ReceiveMail pmm = null;  
        while (it.hasNext()) {  
            String key = (String)it.next();
            MimeMessage msg = (MimeMessage)data.get(key) ;
            pmm = new ReceiveMail(msg);  
            // 获得邮件内容===============  
            pmm.getMailContent((Part) msg);  
//            pmm.setAttachPath("c:\\");   
//            pmm.saveAttachMent((Part) msg);           		
        }   
	}
	
	public static void testSBRevert(){
		StringBuffer sb = new StringBuffer();
//		sb.append("aaa");
		sb.delete(0, sb.length());
		sb.append("bbb");
	}
	
	public static void sendMail(){
		SendMail sendMail = new SendMail("smtp.qq.com","340360491@qq.com","程明卫","340360491","cmw_1984122");
		try {
			sendMail.send(new String[]{"340360491@qq.com"}, new String[]{"程明强"}, "邮件测试", "哥们，看能收到不？");
			System.out.println("邮件已发送成功！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   /**  
     * PraseMimeMessage类测试  
     */  
    public static void main(String args[]) throws Exception {  
//    	
//    	String test = "=?gb2312?B?suLK1NPKvP4x?=";
////    	Pattern pattern = Pattern.compile("=\\?*\\?*\\?=");
////    	Matcher matcher = pattern.matcher(test);
////    	boolean b = matcher.find();
//    	boolean b = test.startsWith("=?") && test.endsWith("?=");
    	
    	
//    	test();
//    	testSBRevert();
    	sendMail();
    }


}
