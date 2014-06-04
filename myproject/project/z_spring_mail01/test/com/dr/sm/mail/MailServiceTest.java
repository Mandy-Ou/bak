package com.dr.sm.mail;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration("classpath:applicationContext.xml")
public class MailServiceTest extends AbstractJUnit4SpringContextTests {

    @Resource
    private MailUtil mailUtil;

    @Test
    public void testSendMail() {

        // 创建邮件
        MailBean mailBean = new MailBean();
        mailBean.setFrom("l1152695512@163.com");
        mailBean.setSubject("hello world");
        mailBean.setToEmails(new String[] { "1334889580@qq.com","1152695512@qq.com" });
        mailBean.setTemplate("hello ${userName} !<a href='http://www.baidu.com' >baidu</a>");
        Map map = new HashMap();
        map.put("userName", "liting");//这里定义之后可以动态获取上面输出了${userName}
        mailBean.setData(map);
        // 发送邮件
        try {
            mailUtil.send(mailBean);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
