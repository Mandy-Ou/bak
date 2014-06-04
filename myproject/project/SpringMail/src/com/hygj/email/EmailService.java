package com.hygj.email;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.hygj.bean.EmailBean;
import com.hygj.service.InfoServiceINF;
import com.hygj.service.impl.InfoServiceImpl;



public class EmailService {

	/******
	 * �����ʼ�
	 * @param email
	 */
	public static void sendEmail(EmailBean email)
	{
        Properties props = new Properties();
        props.put("mail.smtp.host", email.getHost());
        props.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(email.getSender()));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email.getRecipients()));//����
            msg.setSubject(email.getTitle());//�ʼ�����
            msg.setText(email.getContent());//�ʼ�����
            msg.saveChanges();
            
            Transport transport = session.getTransport("smtp");
            transport.connect(email.getHost(), email.getUsername(), email.getPassword());//�ʼ���������֤
            transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
            System.out.println("发送陈宫");
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    
	}
	

	/*****
	 * �����ʼ�
	 * @param email
	 */
	public static void receiveMail(EmailBean email)
    {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        
        try {
            Store store = session.getStore("pop3");
            store.connect(email.getHost(), email.getUsername(),email.getPassword());//��֤
            Folder folder = store.getFolder("INBOX");//ȡ���ռ��ļ���
            folder.open(Folder.READ_WRITE);
            Message msg[] = folder.getMessages();
  
            for(int i=0; i<msg.length; i++)
            {
                Message message = msg[i];
                Address address[] = message.getFrom();
                StringBuffer from = new StringBuffer();
             
                for(int j=0; j<address.length; j++)
                {
                    if (j > 0)
                        from.append(";");
                    from.append(address[j].toString());
        
                }

                EmailBean em = new EmailBean();
                              
                em.setSender(from.toString());
                em.setTitle(message.getSubject());
                em.setRecipients(email.getRecipients());
                
                try {
					em.setContent(message.getContent().toString());
				} catch (IOException e) {
					
					e.printStackTrace();
				}
                em.setThetime(message.getSentDate().toString());
                em.setType(1);
                em.setStatus(0);
        		
        		InfoServiceINF info = new InfoServiceImpl();
        		
        		info.addGet(em);
                
                /*System.out.println(message.getMessageNumber());
                System.out.println("���ԣ�" + from.toString());
                System.out.println("��С��" + message.getSize());
                System.out.println("���⣺" + message.getSubject());
                System.out.println("ʱ�䣺��" + message.getSentDate());
                System.out.println("===================================================");*/
    
            }
            folder.close(true);//���ùر�
            store.close();
            
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
	

	/*****
	 * ɾ���ʼ�
	 * @param email
	 */
	public static void deleteMail(EmailBean email)
    {

        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);
        try {
            Store store = session.getStore("pop3");
            store.connect(email.getHost(), email.getUsername(), email.getPassword());//��֤����
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);//�����Ҷ�д��ʽ��
            int countOfAll = folder.getMessageCount();//ȡ���ʼ�����
            int unReadCount = folder.getUnreadMessageCount();//�Ѷ�����
            int newOfCount = folder.getNewMessageCount();//δ������
            
            System.out.println("�ܸ���" +countOfAll);
            System.out.println("�Ѷ�����" +unReadCount);
            System.out.println("δ������" +newOfCount);
            
            for(int i=1; i<=countOfAll; i++)
            {
                Message message = folder.getMessage(i);
                message.setFlag(Flags.Flag.DELETED, true);//������ɾ��״̬Ϊtrue
                if(message.isSet(Flags.Flag.DELETED))
                    System.out.println("�Ѿ�ɾ���"+i+"�ʼ�������������������");
            }
            folder.close(true);
            store.close();
            
            System.out.println("ɾ��ɹ�");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
}
