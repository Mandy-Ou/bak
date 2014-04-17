package com.cmw.core.security.certificate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet implementation class for Servlet: AuthImage
 *
 */
 public class AuthImage extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
   private static Log log = LogFactory.getLog(AuthImage.class);
   /**
    * 验证码KEY 
    */
   public static final String VALID_KEY = "validCode";

//设置图形验证码中的字符串的字体的大小

   private Font mFont = new Font("Arial Black", Font.PLAIN, 16);



   public void init() throws ServletException {

       super.init();

   }



   /**

    * 生成随机颜色

    * @param fc

    * @param bc

    * @return

    */

   Color getRandColor(int fc, int bc) {

       Random random = new Random();

       if (fc > 255)

           fc = 255;

       if (bc > 255)

           bc = 255;

       int r = fc + random.nextInt(bc - fc);

       int g = fc + random.nextInt(bc - fc);

       int b = fc + random.nextInt(bc - fc);

       return new Color(r, g, b);

   }



   /* (non-Javadoc)

    * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)

    */

   public void service(HttpServletRequest request, HttpServletResponse response)

           throws ServletException, IOException {

       //生成服务器相应的service方法

       //阻止生成的页面内容被缓存，保证每次重新生成随机验证码

       response.setHeader("Pragma", "No-cache");

       response.setHeader("Cache-Control", "no-cache");

       response.setDateHeader("Expires", 0);

       response.setContentType("image/jpeg");
     
       //指定图形验证码图片的大小；

       int width = 55;//宽度

       int height = 18;//高度

       BufferedImage image = new BufferedImage(width, height,

               BufferedImage.TYPE_INT_RGB);



//      准备在图片中绘制内容

       Graphics g = image.getGraphics();

       Random random = new Random();

       g.setColor(getRandColor(200, 250));

       g.fillRect(1, 1, width - 1, height - 1);

       g.setColor(new Color(102, 102, 102));

       g.drawRect(0, 0, width - 1, height - 1);

       g.setFont(mFont);

       g.setColor(getRandColor(160, 200));

       //生成随机线条

       for (int i = 0; i < 155; i++) {

           int x = random.nextInt(width - 1);

           int y = random.nextInt(height - 1);

           int xl = random.nextInt(6) + 1;

           int yl = random.nextInt(12) + 1;

           g.drawLine(x, y, x + xl, y + yl);

       }

       for (int i = 0; i < 70; i++) {

           int x = random.nextInt(width - 1);

           int y = random.nextInt(height - 1);

           int xl = random.nextInt(12) + 1;

           int yl = random.nextInt(6) + 1;

           g.drawLine(x, y, x - xl, y - yl);

       }



       String sRand = "";

       //生成随机的字符串并加入到图片中

       int LEN = 3; //控制随机码的长度

       for (int i = 0; i < LEN; i++) {

           String tmp = getRandomChar();

           sRand += tmp;

           g.setColor(new Color(20 + random.nextInt(110), 20 + random

                   .nextInt(110), 20 + random.nextInt(110)));

           g.drawString(tmp, 15 * i + 10, 15);

       }



       HttpSession session = request.getSession(true);

       log.debug("随机生成的字符串为"+sRand);

       //将其自动转换为小写。也就是说用户在输入验证码的时候，不需要区分大小写，方便输入。
       session.setAttribute(VALID_KEY, sRand.toLowerCase());
       log.debug("从session中取出来"+session.getAttribute("rand"));
       g.dispose();

       ImageIO.write(image, "JPEG", response.getOutputStream());

   }



   /**

    * 随机生成字符串

    * @return

    */

   private String getRandomChar() {

       int rand = (int) Math.round(Math.random() * 2);

       long itmp = 0;

       char ctmp = '\u0000';

       switch (rand) {

       case 1:

           itmp = Math.round(Math.random() * 25 + 65);

           ctmp = (char) itmp;

           return String.valueOf(ctmp);

       case 2:

           itmp = Math.round(Math.random() * 25 + 97);

           ctmp = (char) itmp;

           return String.valueOf(ctmp);

       default:

           itmp = Math.round(Math.random() * 9);

           return String.valueOf(itmp);

       }

   }
    
}