package com.cmw.core.kit.email;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.util.BASE64DecoderStream;

public class ReceiveMail {

	private MimeMessage mimeMessage = null;   
	private String saveAttachPath = ""; //附件下载后的存放目录   
	private StringBuffer bodytext = new StringBuffer();//存放邮件内容   
	private String dateformat = "yy-MM-dd HH:mm"; //默认的日前显示格式 

	public ReceiveMail(MimeMessage mimeMessage) {   
         this.mimeMessage = mimeMessage;   
     }    
	
	public void setMimeMessage(MimeMessage mimeMessage) {   
         this.mimeMessage = mimeMessage;
    }  
	public MimeMessage getMimeMessage() {
		return mimeMessage;
	}
	//设置保持附件的回调函数
	private SaveAttachment saveAttachment;
	/**  
	 * 获得发件人的地址和姓名  
	 */  
     public String getFrom() throws Exception {   
         InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();   
         if (address == null || address.length == 0)return "";
         String from = address[0].getAddress();   
         
         if (from == null)   
             from = "";   
         String encodedPersonal = getEncodePerson(address[0]);
         String personal = address[0].getPersonal(); 
        
         if (!checkEncodeInfo(encodedPersonal)){
        	 personal = new String(personal.getBytes("iso-8859-1"),"gb2312");
         }
         if (personal == null)   
             personal = "";   
         
         String fromaddr = personal + "<" + from + ">";   
         return fromaddr;   
     }    
     
     //
     public String getEncodePerson(InternetAddress address){
    	 Class addressClass = InternetAddress.class;
    	 
    	 Field [] fields = addressClass.getDeclaredFields();
    	 if (fields == null)return null;
    	 for (Field field : fields){
    		 field.setAccessible(true);
    		 String name = field.getName();
    		 if ("encodedPersonal".equals(name)){
    			 Object value;
				try {
					value = field.get(address);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					continue;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					continue;
				}
    			 if (value == null)return null;
    			 else return (String)value;
    		 }
    	 }
    	 return null;
     }
      /**  
        * 获得邮件的收件人，抄送，和密送的姓名和地址，
        * String [] result;result[0] 名称， result[1] 地址。
        * 根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址  
        * 
        *  
        */  
      public String [] getMailAddress(String type) throws Exception {   
          String mailaddr = "";   
          String mailname = "";
          String addtype = type.toUpperCase();   
          InternetAddress[] address = null;   
          if (addtype.equals("TO") || addtype.equals("CC")|| addtype.equals("BCC")) {   
              if (addtype.equals("TO")) {   
                  address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);   
              } else if (addtype.equals("CC")) {   
                  address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);   
              } else {   
                  address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);   
              }   
              if (address != null) {   
                  for (int i = 0; i < address.length; i++) {   
                      String email = address[i].getAddress();   
                      if (email == null)   
                          email = "";   
                      else {   
                          email = MimeUtility.decodeText(email);   
                      }   
                      String personal = address[i].getPersonal();   
                      if (personal == null)   
                          personal = "";   
                      else {   
                          personal = MimeUtility.decodeText(personal);   
                      }   
                      String compositeto;
                      if (personal != null && personal.trim().length() > 0){
                    	  compositeto = personal + "<" + email + ">"; 
                      }else{
                    	  compositeto = email; 
                      }
                      mailname += ";" + compositeto; 
//                      mailaddr += ";" + compositeto;  
                      mailaddr += ";" + email;
                  }   
                  if (mailaddr.length()>0){
                	  mailaddr = mailaddr.substring(1);   
                	  mailname = mailname.substring(1);
                  }
                	  
              }   
          }/* else {   
              throw new Exception("Error emailaddr type!");   
          }   */
          return new String[]{mailname, mailaddr};   
      }    
      
       /**  
         * 获得邮件主题  
         */  
       public String getSubject() throws MessagingException {   
           String subject = null;   
           try {   
        	   subject = mimeMessage.getHeader("Subject", null);
        		
//        	   subject = mimeMessage.getSubject();
        	   if (subject == null || subject.trim().equals("")){
        		   subject = "此邮件无主题";
        	   }else{
        		   subject = unfold(subject);
        		   //判断是否有编码，如果没有编码就用gbk解码。
        		   if (!checkEncodeInfo(subject)){
        			   subject = new String(subject.getBytes("iso-8859-1"),"gbk");
        	       }
//        		   if (subject.indexOf(" ") != -1){
//        			   subject = subject.replace(" ", "");
//        		   }
        		   subject = MimeUtility.decodeText(subject); 
        		   if (checkEncodeInfo(subject) && subject.indexOf(" ") != -1){
            			subject = subject.replace(" ", "");
            			subject = MimeUtility.decodeText(subject); 
        	       }
        	   }
               
           } catch (Exception exce) {}   
           return subject;   
       }    
       
       static String unfold(String s) {
    		StringBuffer sb = null;
    		int i;
    		while ((i = indexOfAny(s, "\r\n")) >= 0) {
    		    int start = i;
    		    int l = s.length();
    		    i++;		// skip CR or NL
    		    if (i < l && s.charAt(i - 1) == '\r' && s.charAt(i) == '\n')
    			i++;	// skip LF
    		    if (start == 0 || s.charAt(start - 1) != '\\') {
    			char c;
    			// if next line starts with whitespace, skip all of it
    			// XXX - always has to be true?
    			if (i < l && ((c = s.charAt(i)) == ' ' || c == '\t')) {
    			    i++;	// skip whitespace
    			    while (i < l && ((c = s.charAt(i)) == ' ' || c == '\t'))
    				i++;
    			    if (sb == null)
    				sb = new StringBuffer(s.length());
    			    if (start != 0) {
    				sb.append(s.substring(0, start));
    				sb.append(' ');
    			    }
    			    s = s.substring(i);
    			    continue;
    			}
    			// it's not a continuation line, just leave it in
    			if (sb == null)
    			    sb = new StringBuffer(s.length());
    			sb.append(s.substring(0, i));
    			s = s.substring(i);
    		    } else {
    			// there's a backslash at "start - 1"
    			// strip it out, but leave in the line break
    			if (sb == null)
    			    sb = new StringBuffer(s.length());
    			sb.append(s.substring(0, start - 1));
    			sb.append(s.substring(start, i));
    			s = s.substring(i);
    		    }
    		}
    		if (sb != null) {
    		    sb.append(s);
    		    return sb.toString();
    		} else
    		    return s;
       }
       
       private static int indexOfAny(String s, String any) {
    		return indexOfAny(s, any, 0);
       }

	    private static int indexOfAny(String s, String any, int start) {
			try {
			    int len = s.length();
			    for (int i = start; i < len; i++) {
				if (any.indexOf(s.charAt(i)) >= 0)
				    return i;
			    }
			    return -1;
			} catch (StringIndexOutOfBoundsException e) {
			    return -1;
			}
	    }
       
       //判断邮件主题，或者发件人的编码信息
       public boolean checkEncodeInfo(String data){
    	   if (data == null)return true;
    	   if (data.startsWith("=?") && data.endsWith("?=")){
    		   return true;
    	   }
    	   return false;
       }
      /**  
        * 获得邮件发送日期  
        */  
       public String getSentDate() throws Exception {   
            Date sentDate = mimeMessage.getSentDate();   
            if (sentDate == null)return "";
            SimpleDateFormat format = new SimpleDateFormat(dateformat);   
            return format.format(sentDate);   
       }   
       
       public Date getSentDate(boolean isDate) throws MessagingException{
    	   Date sentDate = mimeMessage.getSentDate();  
    	   return sentDate;
       }
       
       /**  
         * 获得邮件正文内容  
         */  
        public String getBodyText() {   
            return bodytext.toString();   
        }    
        
      /**  
        * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析  
        */  
     public void getMailContent(Part part) throws Exception {   
         String contentType = part.getContentType().toLowerCase();   
         int nameindex = contentType.indexOf("name");   
         boolean conname = false;   
         if (nameindex != -1)   
             conname = true;   
         if (part.isMimeType("text/plain") && !conname) {   
//             bodytext.append((String) part.getContent()); 
        	 String ct = (String) part.getContent();
        	 String ecct = ct;
        	 if (contentType.indexOf("charset") == -1){
        		 ecct = new String( ct.getBytes("iso-8859-1"),"gb2312");
        	 }
             bodytext.append(ecct);   
         } else if (part.isMimeType("text/html") && !conname) {   
        	 //如果有html的内容就只保存html的内容
        	 String ct = (String) part.getContent();
        	 String ecct = ct;
        	 if (contentType.indexOf("charset") == -1){
        		 ecct = new String( ct.getBytes("iso-8859-1"),"gb2312");
        	 }
        	 if (ecct != null && ecct.trim().length() > 0){
        		 bodytext.delete(0, bodytext.length());
        		 bodytext.append(ecct);  
        	 }
              
         } else if (part.isMimeType("multipart/*")) {   
             Multipart multipart = (Multipart) part.getContent();   
             int counts = multipart.getCount();   
             for (int i = 0; i < counts; i++) {   
                 getMailContent(multipart.getBodyPart(i));   
             }   
         } else if (part.isMimeType("message/rfc822")) {   
             getMailContent((Part) part.getContent());   
         }
     }    
     
     public boolean isRelated(Part part) throws MessagingException{
    	 return part.isMimeType("multipart/related");
     }
     
     /**  
      * 解析邮件中的嵌入资源
      */  
   public List<RelatedMap> getMailRelated(Part part, String realpath, String path, Long id) throws Exception { 
	   List<RelatedMap> list = new ArrayList<RelatedMap>();
//       String contentType = part.getContentType().toLowerCase(); 
//       String ct = null;
      
       if ((part.isMimeType("application/octet-stream") || part.isMimeType("image/*")) 
    		   && part instanceof MimeBodyPart) { 
    	   String cid = ((MimeBodyPart)part).getContentID();
    	   cid = cid.replace("<", "").replace(">", "");
//    	   System.out.println("---cid:" + cid);
           InputStream is = part.getInputStream();
           BASE64DecoderStream bds = new BASE64DecoderStream(is);

           int buffer = bds.available();
           //文件名称=邮件id+cid
           String filename = id+cid;
           String filepath = "related"+"/"+filename;
           File dir = new File(realpath+"related");
           if (!dir.exists())dir.mkdirs();
           String fullpath = realpath + filepath;
           FileOutputStream fos = new FileOutputStream(fullpath);
           BufferedOutputStream dest = new BufferedOutputStream(fos, buffer);
           int count = 0;
           byte[] bt = new byte[part.getSize()];
           while((count = is.read(bt, 0, buffer)) != -1)
           {
             bds.decode(bt);
             dest.write(bt,0,buffer);
           }
           dest.flush();
           dest.close();
           is.close();
           fos.close();
           cid = "cid:"+cid;
           RelatedMap related = new RelatedMap(cid, filepath);
           list.add(related);
       } else if (part.isMimeType("multipart/*")) {   
           Multipart multipart = (Multipart) part.getContent();   
           int counts = multipart.getCount();   
           for (int i = 0; i < counts; i++) {   
        	   list.addAll(getMailRelated(multipart.getBodyPart(i), realpath, path, id));
           }   
       }
       return list;
   }    
      /**   
       * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"  
       */   
      public boolean getReplySign() throws MessagingException {   
          boolean replysign = false;   
          String needreply[] = mimeMessage.getHeader("Disposition-Notification-To");   
          if (needreply != null) {   
              replysign = true;   
          }   
          return replysign;   
      }    
      
       /**  
        * 获得此邮件的Message-ID  
        */  
       public String getMessageId() throws MessagingException {   
           return mimeMessage.getMessageID();   
       }    
       
        /**  
         * 【判断此邮件是否已读，如果未读返回返回false,反之返回true】  pop3不支持此方法
         */  
        public boolean isRead() throws MessagingException {   
            boolean isnew = false;   
            Flags flags = ((Message) mimeMessage).getFlags();   
            Flags.Flag[] flag = flags.getSystemFlags();   
            for (int i = 0; i < flag.length; i++) {   
                if (flag[i] == Flags.Flag.SEEN) {   
                    isnew = true;   
                    break;   
                }   
            }   
            return isnew;
        }    
        
        /**  
         * 获取邮件的uuid，uidl命令
         */  
        public String getUUID(Folder folder, Message message) throws MessagingException{
        	String uid = "";
        	if (folder instanceof POP3Folder) {
        		POP3Folder inbox = (POP3Folder) folder;
        		MimeMessage mimeMessage = (MimeMessage) message;
        		uid = inbox.getUID(mimeMessage);
        	} else if (folder instanceof IMAPFolder) {
        		IMAPFolder inbox = (IMAPFolder) folder;
        		MimeMessage mimeMessage = (MimeMessage) message;
        		uid = Long.toString(inbox.getUID(mimeMessage));
        	}
        	return uid;
        }
        
         /**  
          * 判断此邮件是否包含附件  
          */  
         public boolean isContainAttach(Part part) throws Exception {   
             boolean attachflag = false;   
             String contentType = part.getContentType();   
             if (part.isMimeType("multipart/*")) {   
                 Multipart mp = (Multipart) part.getContent();   
                 for (int i = 0; i < mp.getCount(); i++) {   
                     BodyPart mpart = mp.getBodyPart(i);   
                     String disposition = mpart.getDisposition();   
                     if ((disposition != null)   
                             && ((disposition.equals(Part.ATTACHMENT)) || (disposition   
                                     .equals(Part.INLINE))))   
                         attachflag = true;   
                     else if (mpart.isMimeType("multipart/*")) {   
                         attachflag = isContainAttach((Part) mpart);   
                     } else {   
                         String contype = mpart.getContentType();   
                         if (contype.toLowerCase().indexOf("application") != -1)   
                             attachflag = true;   
                         if (contype.toLowerCase().indexOf("name") != -1)   
                             attachflag = true;   
                     }   
                 }   
             } else if (part.isMimeType("message/rfc822")) {   
                 attachflag = isContainAttach((Part) part.getContent());   
             }   
             return attachflag;   
         }    
         
         
          /**   
           * 【保存附件】   
           */   
          public void saveAttachMent(Part part) throws Exception {   
              String fileName = "";   
              if (part.isMimeType("multipart/*")) {   
                  Multipart mp = (Multipart) part.getContent();   
                  for (int i = 0; i < mp.getCount(); i++) {   
                      BodyPart mpart = mp.getBodyPart(i);   
                      String disposition = mpart.getDisposition();   
                      if ((disposition != null)   
                              && ((disposition.equals(Part.ATTACHMENT)) || (disposition   
                                      .equals(Part.INLINE)))) {   
                          fileName = mpart.getFileName(); 
                          if (fileName != null){//fileName有可能为null，不知道为什么？！
                        	  
                        	  if (fileName.toLowerCase().indexOf("gb2312") != -1  
                        			  || fileName.toLowerCase().indexOf("gbk") != -1) {   
                                  fileName = MimeUtility.decodeText(fileName);   
                              }else{
                            	  //转码
                            	  fileName = new String(fileName.getBytes("iso-8859-1"),"gbk");
                              }
                              saveFile(fileName, mpart.getInputStream());  
                          }
                           
                      } else if (mpart.isMimeType("multipart/*")) {   
                          saveAttachMent(mpart);   
                      } else {   
                          fileName = mpart.getFileName();   
                          if ((fileName != null)   
                                  && ((fileName.toLowerCase().indexOf("gb2312") != -1) ||
                                  (fileName.toLowerCase().indexOf("gbk") != -1))) {   
                              fileName = MimeUtility.decodeText(fileName);   
                              saveFile(fileName, mpart.getInputStream());   
                          }   
                      }   
                  }   
              } else if (part.isMimeType("message/rfc822")) {   
                  saveAttachMent((Part) part.getContent());   
              }   
          }    
          
            /**   
            * 【设置附件存放路径】   
            */   
         
           public void setAttachPath(String attachpath) {   
               this.saveAttachPath = attachpath;   
           }   
         
           /**  
            * 【设置日期显示格式】  
            */  
           public void setDateFormat(String format) throws Exception {   
               this.dateformat = format;   
           }   
         
           /**  
            * 【获得附件存放路径】  
            */  
           public String getAttachPath() {   
               return saveAttachPath;   
           }   
         
           //将附件保存到数据库的回调接口
           public interface SaveAttachment{
        	   public void excute(String fileName, Long fileSize);
           }
           
           /**  
            * 【真正的保存附件到指定目录里】  
            */  
           private void saveFile(String fileName, InputStream in) throws Exception {   
               String osName = System.getProperty("os.name");   
               String storedir = getAttachPath();   
               String separator = "";   
               if (osName == null)   
                   osName = "";   
               if (osName.toLowerCase().indexOf("win") != -1) {   
                   separator = "\\";  
                   if (storedir == null || storedir.equals(""))  
                       storedir = "c:\\tmp";  
               } else {  
                   separator = "/";  
                   if (storedir == null || storedir.equals("")) 
                	   storedir = "/tmp";  
               }  
               File storeDir = new File(storedir);
               //如果目录不存在，则需创建
               if (!storeDir.exists())storeDir.mkdirs();
               File storefile = new File(storedir + separator + fileName);  

               BufferedOutputStream bos = null;  
               BufferedInputStream bis = null;  
               try {  
                   bos = new BufferedOutputStream(new FileOutputStream(storefile));  
                   bis = new BufferedInputStream(in);  
                   int c;  
                   while ((c = bis.read()) != -1) {  
                       bos.write(c);  
                       bos.flush();  
                   }  
                   if (saveAttachment != null){
                	   saveAttachment.excute(storefile.getName(), storefile.length());
                   }
               } catch (Exception exception) {  
                   exception.printStackTrace();  
                   throw new Exception("文件保存失败!");  
//                   return;
               } finally {  
                   if (bos != null)bos.close();  
                   if (bis != null)bis.close();  
               }  
           }   
           
           
        //返回uuid和邮件对象map  
        public static Map<String, Message>  receiveMails(String popServer, int popPort, 
        		String userName, String pass, IsNew isnew)throws Exception{
        	Map<String, Message>  uuidToMessage = new HashMap<String, Message>();
        	Properties props = System.getProperties();  
            Session session = Session.getInstance(props, null);  
            URLName urln = new URLName("pop3", popServer, popPort, null,  
            		userName, pass); 
            Store store = session.getStore(urln);  
            store.connect();  
            POP3Folder folder = (POP3Folder)store.getFolder("INBOX");  
            folder.open(Folder.READ_ONLY);  
            FetchProfile profile = new FetchProfile(); 
            profile.add(UIDFolder.FetchProfileItem.UID); 
            profile.add(FetchProfile.Item.ENVELOPE);
            Message [] message = folder.getMessages(); 
            folder.fetch(message, profile);
            for (int i = 0; i < message.length; i++) {
            	String uuid = folder.getUID(message[i]);
            	 if (isnew != null){
            		 if (isnew.isNew(uuid))
            			 uuidToMessage.put(uuid, message[i]);
            	 }else{
            		 uuidToMessage.put(uuid, message[i]);
            	 }
            }
            return uuidToMessage;
        }
        
        public interface IsNew{
        	public boolean isNew(String msgUUID);
        }
        
        public SaveAttachment getSaveAttachment() {
    		return saveAttachment;
    	}

    	public void setSaveAttachment(SaveAttachment saveAttachment) {
    		this.saveAttachment = saveAttachment;
    	}    
    	
    public static void main(String [] args)throws Exception{
    	Map<String, Message> uuidToMessage = ReceiveMail.receiveMails("pop.sina.com.cn", 110,
    			"zqmfjl@sina.com", "123456", new IsNew(){

			@Override
			public boolean isNew(String uuid) {
				return true;
			}
			
		});
    	Set<Entry<String, Message>> set = uuidToMessage.entrySet();
		Iterator it = set.iterator();
		int count = 0;
    	while (it.hasNext()){
			try {
				Entry<String, Message> entry = (Entry<String, Message>)it.next();
				String key = entry.getKey();
				Message value = entry.getValue();
				InputStream is = ((MimeMessage)value).getInputStream();
				FileOutputStream fos = new FileOutputStream("D:\\test.txt");
				
				
//				ByteArrayOutputStream   out   =   new   ByteArrayOutputStream(); 
				value.writeTo(fos); 
				String   s   =   fos.toString(); 
//				out.close(); 
				
				
//				byte [] data = new byte[((MimeMessage)value).getSize()];
//				int b = -1;
//				while ((b=is.read()) != -1){
//					System.out.print((char)b);
//					fos.write(b);
//				}
//				System.out.println("邮件原文："+s);
//				ReceiveMail receiver = new ReceiveMail((MimeMessage) value); 
				count++;
				if (count == 1)break;
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }
}
