package com.cmw.core.kit.flexpaper;
import java.io.File;
import java.net.ConnectException;

import org.apache.log4j.Logger;

import com.artofsolving.jodconverter.DefaultDocumentFormatRegistry;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFamily;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class ConvertX2PDFHelper {
	 public static final Logger logger  = Logger.getLogger(ConvertX2PDFHelper.class);  
     
	    public final static int DEFAULT_WIDTH = 600;  
	      
	    private static OpenOfficeConnection connection  = null;  
	      
	    public static void officeToPdf(File srcFile,File destFile){  
	        try {  
	            OpenOfficeConnection connection = getConnection();  
	            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);  
	            String fileExt = "";  
	            String fileName = srcFile.getName();  
	            int i = fileName.indexOf(".");  
	            if(i != -1){  
	                fileExt = fileName.substring(i+1);  
	            }  
	            if("wps".equalsIgnoreCase(fileExt)){                  
	                DocumentFormat df = new DocumentFormat("Kingsoft wps", DocumentFamily.TEXT, "application/wps", "wps");  
	                DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();  
	                DocumentFormat pdf = formatReg.getFormatByFileExtension("pdf");  
	                converter.convert(srcFile, df,destFile,pdf);  
	            }  
	            else if("et".equalsIgnoreCase(fileExt)){                  
	                DocumentFormat df = new DocumentFormat("Kingsoft et", DocumentFamily.TEXT, "application/et", "et");  
	                DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();  
	                DocumentFormat pdf = formatReg.getFormatByFileExtension("pdf");  
	                converter.convert(srcFile, df,destFile,pdf);  
	            }  
	            else if("dps".equalsIgnoreCase(fileExt)){                 
	                DocumentFormat df = new DocumentFormat("Kingsoft dps", DocumentFamily.TEXT, "application/dps", "dps");  
	                DefaultDocumentFormatRegistry formatReg = new DefaultDocumentFormatRegistry();  
	                DocumentFormat pdf = formatReg.getFormatByFileExtension("pdf");  
	                converter.convert(srcFile, df,destFile,pdf);  
	            }  
	            else{  
	                converter.convert(srcFile,destFile);  
	            }  
	            String osname = System.getProperty("os.name");  
	            if (osname.indexOf("Windows") > -1 ){  
	                connection.disconnect();  
	            }  
	        } catch (ConnectException e) {  
	            logger.error("officeToPdf error:",e);  
	        }     
	    }  
	  
	      
	    private static OpenOfficeConnection getConnection() throws ConnectException {  
	        if (connection == null || !connection.isConnected()){  
	            connection = new SocketOpenOfficeConnection("127.0.0.1",8100);                            
	            connection.connect();  
	        }  
	        return connection;  
	    }  
	      
	      
	      
	      
//	    public static void imageToPdf(String image,File pdf){  
//	           Document document = new Document(PageSize.A4, 36, 36, 36, 36);  
//	           try   
//	           {  
//	              PdfWriter writer = PdfWriter.getInstance(document,  
//	                 new FileOutputStream(pdf));  
//	              document.open();  
//	              PdfContentByte cb = writer.getDirectContent();  
//	              Image img = Image.getInstance(image);  
//	                if (img != null) {          
//	                  img.setAbsolutePosition(0, 0);  
//	                    img.scaleAbsolute(595, 838);// 控制图片大小  
//	  
//	                     cb.addImage(img);  
//	                     document.newPage();  
//	                }  
//	              document.close();  
//	              }   
//	              catch (Throwable e) {  
//	                  logger.error("image转pdf错误：",e);  
//	              }  
//	        }  
//	      
	      
	    public static void main(String []s){  
	        File office =  new File("F:/dev/skythink/WebContent/controls/flexpaper/data.wps");  
	        File pdf =  new File("F:/dev/skythink/WebContent/controls/flexpaper/data.pdf");  
	        ConvertX2PDFHelper.officeToPdf(office, pdf);  
	    }  
}
