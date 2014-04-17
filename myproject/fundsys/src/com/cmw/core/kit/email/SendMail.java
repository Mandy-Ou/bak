package com.cmw.core.kit.email;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMail {

	  //定义发件人、收件人、SMTP服务器、用户名、密码、主题、内容等  
     private String [] toDisplayName;  
     private String [] to;  
     private String [] ccDisplayName;
     private String [] cc;
     private String [] bccDisplayName;
     private String [] bcc;
     private String from;  
     private String fromDisplayName;
     private String smtpServer;  
     private String username;  
     private String password;  
     private String subject;  
     private String content;  
     private String realPath;
     private boolean ifAuth = true; //服务器是否要身份认证  
     private String filename="";  
     private List<Attachments> atts = new ArrayList<Attachments>(); //用于保存发送附件的文件名的集合  
     
   /** 
       * 设置SMTP服务器地址 
       */  
      public void setSmtpServer(String smtpServer){  
          this.smtpServer=smtpServer;  
      }  
       
      /** 
       * 设置发件人的地址 
       */  
      public void setFrom(String from){  
          this.from=from;  
      }  
     
       
      /** 
       * 设置服务器是否需要身份认证 
       */  
      public void setIfAuth(boolean ifAuth){  
          this.ifAuth=ifAuth;  
      }  
       
      /** 
       * 设置E-mail用户名 
       */  
      public void setUserName(String username){  
          this.username=username;  
      }  
       
      /** 
       * 设置E-mail密码 
       */  
      public void setPassword(String password){  
          this.password=password;  
      }  
       
     
      /** 
       * 设置主题 
       */  
      public void setSubject(String subject){  
          this.subject=subject;  
      }  
       
      /** 
       * 设置主体内容 
       */  
      public void setContent(String content){  
          this.content=content;  
      }  
       
      /** 
       * 该方法用于收集附件名 
       */  
      public void addAttachfile(String fname, String fpath){  
    	  Attachments att = new Attachments(fname, fpath);
    	  atts.add(att);
      }  
       
      public SendMail(){  
           
      }  
      
      /** 
       * 初始化SMTP服务器地址、发送者E-mail地址、用户名、密码、接收者、主题、内容 
       * @param smtpServer SMTP服务器地址
       * @param from 发送者E-mail地址
       * @param fromDisplayName 邮件发送者显示名
       * @param username 用户名
       * @param password 密码
       */  
        public SendMail(String smtpServer,String from,String fromDisplayName,
     		   String username,String password){  
            this.smtpServer=smtpServer;  
            this.from=from;  
            this.fromDisplayName = fromDisplayName;
            this.username=username;  
            this.password=password;  
        }  
        
    /** 
      * 初始化SMTP服务器地址、发送者E-mail地址、用户名、密码、接收者、主题、内容 
      * @param smtpServer SMTP服务器地址
      * @param from 发送者E-mail地址
      * @param fromDisplayName 邮件发送者显示名
      * @param ifAuth 是否权限验证 ,默认 true ： 验证
      * @param to 发送的 Email 地址 
      * @param toDisplayName 显示接收者名称
      * @param cc 抄送 Email
      * @param ccDisplayName  抄送接收者名称
       *@param bcc 密送 Email
      * @param bccDisplayName  密送接收者名称 
      * @param username 用户名
      * @param password 密码
      * @param subject 邮件标题
      * @param content 邮件内容
      */  
       public SendMail(String smtpServer,String from,String fromDisplayName,boolean ifAuth,
    		   String [] to,String [] toDisplayName,String [] cc,String [] ccDisplayName, 
    		   String [] bcc,String [] bccDisplayName,
    		   String username,String password,
    		   String subject,String content){  
           this.smtpServer=smtpServer;  
           this.from=from;  
           this.fromDisplayName = fromDisplayName;
           this.ifAuth=ifAuth;  
           this.username=username;  
           this.password=password;  
           this.subject=subject;  
           this.content=content;  
           this.toDisplayName = toDisplayName;
           this.to=to;  
           this.ccDisplayName = ccDisplayName;
           this.cc = cc;
           this.bcc = bcc;
           this.bccDisplayName = bccDisplayName;
       }  
       
       /** 
        * 设置收件人，抄送，暗送 
        * @throws MessagingException 
        */  
       public void setAddress(Message msg) throws MessagingException{
    	   //收件人
           InternetAddress[] address = getAddress(to, toDisplayName);  
           if (address != null)
        	   msg.setRecipients(Message.RecipientType.TO,address);  
           //抄送
           address = getAddress(cc, ccDisplayName);  
           if (address != null)
        	   msg.setRecipients(Message.RecipientType.CC,address);  
           //暗送
           address = getAddress(bcc, bccDisplayName);  
           if (address != null)
        	   msg.setRecipients(Message.RecipientType.BCC,address);  
       }
       /** 
        * 组装邮件地址 
        */  
       public InternetAddress[] getAddress(String [] addressArray, String [] diaplayNameArray){
    	   if (addressArray == null || addressArray.length <1)return null;
    	   InternetAddress[] address = new InternetAddress[addressArray.length];
    	   for (int i=0; i<addressArray.length; i++){
    		   try {
    			    address[i] = new InternetAddress(addressArray[i], diaplayNameArray[i]);
    		   } catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					continue;
    		   }  
    	   }
    	   return address;
       }
       
       /** 
        * 发送邮件
        * @param toEmails	接收者Email 数组
        * @param toDisplayNames 接收者显示名称
        * @param subject 邮件标题
        * @param content 邮件内容
        */  
      @SuppressWarnings("unchecked")
      public HashMap send(String[] toEmails,String[] toDisplayNames,String subject,String content)throws Exception{  
    	  this.to = toEmails;
    	  this.toDisplayName = toDisplayNames;
    	  this.subject = subject;
    	  this.content = content;
    	  return send();
      }
      
      /** 
       * 定时发送邮件
       * @param longtime	定时发送时间
       * @param toEmails	接收者Email 数组
       * @param toDisplayNames 接收者显示名称
       * @param subject 邮件标题
       * @param content 邮件内容
       */  
     @SuppressWarnings("unchecked")
     public HashMap send(Long longtime,String[] toEmails,String[] toDisplayNames,String subject,String content)throws Exception{  
   	  this.to = toEmails;
   	  this.toDisplayName = toDisplayNames;
   	  this.subject = subject;
   	  this.content = content;
   	  /*-------- 开始定时发送  --------*/
	  EmailTimeTask emailTask = new EmailTimeTask(new Date(longtime));
	  emailTask.schedule();
   	  return emailTask.getParams();
     }
     
     /**
      * 定时邮件发送器类
      * @author ddd
      *	Sep 2, 2010
      */
     class EmailTimeTask extends TimerTask{
    	 private HashMap params;
    	 private Date taskDate;
    	 Timer timer = new Timer();
		public EmailTimeTask(Date taskDate) {
			this.taskDate = taskDate;
		}

		@Override
		public void run() {
			try {
				params = send();
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				timer.cancel();
			}
		}
		
		public HashMap getParams() {
			return params;
		}
		public void setParams(HashMap params) {
			this.params = params;
		}
		
		/**
		 * 开始定时发送
		 */
		public void schedule(){
			timer.schedule(this, taskDate);
		}
     }
        /** 
          * 发送邮件 
          */  
        @SuppressWarnings("unchecked")
		public HashMap send()throws Exception{  
             HashMap map = new HashMap();  
             map.put("state", "success");  
             String message="邮件发送成功！";  
             Session session=null;  
             Properties props = System.getProperties();  
             props.put("mail.smtp.host", smtpServer);  
             if(ifAuth){ //服务器需要身份认证  
                 props.put("mail.smtp.auth","true");     
                 SmtpAuth smtpAuth=new SmtpAuth(username,password);  
                 session=Session.getDefaultInstance(props, smtpAuth);   
             }else{  
                 props.put("mail.smtp.auth","false");  
                 session=Session.getDefaultInstance(props, null);  
             }  
             //调试用，正式使用时要注释掉
//             session.setDebug(true);  
             Transport trans = null;    
             try {  
                 Message msg = new MimeMessage(session); 
                 try{  
                     Address from_address = new InternetAddress(from, fromDisplayName);  
                     msg.setFrom(from_address);  
                 }catch(java.io.UnsupportedEncodingException e){  
                     e.printStackTrace();  
                 }  
                 setAddress(msg);
                
                 msg.setSubject(subject);  
                 Multipart mp = new MimeMultipart();  
                 MimeBodyPart mbp = new MimeBodyPart();  
                 List<MimeBodyPart> relateds = generateRelated();
                
                 mbp.setContent(content.toString(), "text/html;charset=gb2312");  
                 mp.addBodyPart(mbp);    
                 if (relateds != null){
                	 for (MimeBodyPart part : relateds){
                		 mp.addBodyPart(part); 
                	 }
                	 
                 }
                 if(!atts.isEmpty()){//有附件  
                	 MimeBodyPart attachment;
                	//选择出每一个附件名  
                     for(Attachments att : atts){   
                    	 attachment = new MimeBodyPart();  
                         FileDataSource fds = new FileDataSource(att.getFilePath()); //得到数据源  
                         attachment.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart  
                         attachment.setFileName(MimeUtility.encodeText(att.getFileName()));  //得到文件名同样至入BodyPart  
                         mp.addBodyPart(attachment);  
                     }    
                     atts.clear();      
                 }   
                 msg.setContent(mp); //Multipart加入到信件  
                 msg.setSentDate(new Date());     //设置信件头的发送日期  
                 
                 //发送信件  
                 msg.saveChanges();   
                 trans = session.getTransport("smtp"); 
                 trans.connect(smtpServer, username, password);  
                 trans.sendMessage(msg, msg.getAllRecipients());  
                 trans.close();  
                  
             }catch(AuthenticationFailedException e){     
                  map.put("state", "failed");  
                  message="邮件发送失败！错误原因：\n"+"身份验证错误!";  
                  e.printStackTrace();   
                  throw e;
             }catch (MessagingException e) {  
                  message="邮件发送失败！错误原因：\n"+e.getMessage();  
                  map.put("state", "failed");  
                  e.printStackTrace();  
                  Exception ex = null;  
                  if ((ex = e.getNextException()) != null) {  
                      ex.printStackTrace();  
                  }   
                  throw e;
             }  
             map.put("message", message);  
             return map;  
         }  
         
         private List<MimeBodyPart> generateRelated()throws Exception {
//        	System.out.println("我要的内容："+content);
        	List<MimeBodyPart> parts = null;
        	MimeBodyPart bodyPart = null;
    		Pattern pattern = Pattern.compile("src=[\'|\"](\\/oa\\/[^\'^\"]*)");
        	Matcher matcher = pattern.matcher(content);
        	List<String> list = new ArrayList<String>();
//        	StringBuffer buffer = new StringBuffer();
    		int counter = 0;
//    		System.out.println("---------------");
    		while(matcher.find()){    
    			String val = matcher.group(1);
    			list.add(val);
//    		    buffer.append(val);        
//    		    buffer.append("\r\n");              
//    		    System.out.println(++counter+"|"+buffer.toString());
    		}
    		if (list.size() > 0){
    			parts = new ArrayList<MimeBodyPart>();
    		}
    		for (int i=0; i<list.size(); i++){
    			String src = list.get(i).toString();
    			String cid = "cid:oa_related_" + i;
    			content = content.replace(src, cid);
    			bodyPart = new MimeBodyPart();
    			src = src.replaceAll("[\\\\]", "/");
         		FileDataSource fds = new FileDataSource(this.realPath+src.substring(4));
         		bodyPart.setDataHandler(new DataHandler(fds));
         		bodyPart.setContentID("oa_related_"+i);
         		parts.add(bodyPart);
    		}
        	
     		return parts;
		}

		static String testContent = "<div align='center'><strong><span style='font-size: 16pt'>履约计划流程</span></strong></div>" +
         		"<div style='margin: 0cm 0cm 0pt 36pt; text-indent: -36pt'><span style='font-size: 16pt'>一，<span style='font: 7pt 'Times New Roman''>&nbsp;&nbsp; </span></span><span style='font-size: 16pt'>登记合同后履约计划流程</span></div>" +
         		"<div style='margin: 0cm 0cm 0pt 21pt'><span style='font-size: 14pt'>开始：登记合同</span></div>" +
         		"<div style='margin: 0cm 0cm 0pt 57pt; text-indent: -36pt'><span style='font-size: 14pt'>1，<span style='font: 7pt 'Times New Roman''>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style='font-size: 14pt'>获取合同标的额，利率，贷款期限。</span></div>" +
         		"<div style='margin: 0cm 0cm 0pt 57pt; text-indent: -36pt'><span style='font-size: 14pt'>2，<span style='font: 7pt 'Times New Roman''>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style='font-size: 14pt'>计算应还利息（万元），精确到小数点后六位，即精确到分。根据合同标的额（万元），利率和贷款期限计算应还利息</span></div>" +
         		"<div style='margin: 0cm 0cm 0pt 57pt'><span style='font-size: 14pt; color: purple'>年：合同标的额</span><span style='font-size: 14pt; color: purple'>*</span><span style='font-size: 14pt; color: purple'>利率</span><span style='font-size: 14pt; color: purple'>*</span><span style='font-size: 14pt; color: purple'>贷款期限（年数）</span></div>" +
         		"<div style='margin: 0cm 0cm 0pt 57pt'><span style='font-size: 14pt; color: purple'>月：合同标的额</span><span style='font-size: 14pt; color: purple'>*</span><span style='font-size: 14pt; color: purple'>利率</span><span style='font-size: 14pt; color: purple'>/12*</span><span style='font-size: 14pt; color: purple'>贷款期限（月数）</span></div>" +
         		"<div style='margin: 0cm 0cm 0pt 57pt'><span style='font-size: 14pt; color: purple'>日：合同标的额</span><span style='font-size: 14pt; color: purple'>*</span><span style='font-size: 14pt; color: purple'>利率</span><span style='font-size: 14pt; color: purple'>/365*</span><span style='font-size: 14pt; color: purple'>贷款期限（天数）</span></div>" +
         		"<div style='margin: 0cm 0cm 0pt 57pt; text-indent: -36pt'><span style='font-size: 14pt'>3，<span style='font: 7pt 'Times New Roman''>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span><span style='font-size: 14pt'>如果还息方式是分期还息，进入步骤</span><span style='font-size: 14pt'>4.</span></div>";
         
         private class Attachments{
        	private String fileName;
        	private String filePath;
        	
        	public Attachments(String fileName, String filePath){
        		this.fileName = fileName;
        		this.filePath = filePath;
        	}
			public String getFileName() {
				return fileName;
			}
			public void setFileName(String fileName) {
				this.fileName = fileName;
			}
			public String getFilePath() {
				return filePath;
			}
			public void setFilePath(String filePath) {
				this.filePath = filePath;
			}
        	 
         }
         
         public static void main(String [] args)throws Exception{
//        	 SendMail mail = new SendMail("smtp.sina.com.cn", "zqmfjl@sina.com","庆明",true,
//        			 new String[]{"340360491@qq.com"}, new String[]{"程明卫"},null,null,null,null,
//        			 "zqmfjl","123456",
//        			 "邮件测试5",testContent);
////        	 mail.addAttachfile("D:\\tables\\authorized.sql");
////        	 mail.addAttachfile("D:\\tables\\finance_branch.sql");
////        	 mail.addAttachfile("D:\\tables\\module.sql");
//        	 Map msg = mail.send();
        	 Map msg = null;
        	 SendMail mail = new SendMail("smtp.126.com","niujiangjun_server@126.com","牛将军系统平台","niujiangjun_server","niujiangjun_2013");
        	 mail.send(new String[]{"340360491@qq.com"},  new String[]{"程明卫"},  "邮件测试5",testContent);
        	 
        	 Long longtime = System.currentTimeMillis()+(1*60*1000);
        	 msg = mail.send(new String[]{"340360491@qq.com"},  new String[]{"程明卫"},  "邮件测试5",testContent);
         }

		public String getRealPath() {
			return realPath;
		}

		public void setRealPath(String realPath) {
			this.realPath = realPath;
		}
}
