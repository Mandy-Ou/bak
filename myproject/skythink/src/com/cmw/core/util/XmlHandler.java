package com.cmw.core.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class XmlHandler {
	
	 DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
	
	 /**
	  * 获取一个 Document 对象
	  * @param xmlFilePath  XML 文件路径
	  * @return Document
	  */
	 public Document getDocument(String xmlFilePath){
		Document document = null; 
		DocumentBuilder dombuilder=null;
		InputStream is = null;
		try {
			dombuilder = factory.newDocumentBuilder();
			is = new FileInputStream(xmlFilePath);
			document=dombuilder.parse(is);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(null != is){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return document;
	 }
	 
	 public String getRootContent(String xmlFilePath){
		 Document doc = getDocument(xmlFilePath);
		 String content = null;
		 Element root = doc.getDocumentElement();
		 content = root.getTextContent();
		 return content;
	 }
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		XmlHandler xmlHandler = new XmlHandler();
		String content = xmlHandler.getRootContent("F:/dev/skythink/resources/LoanReportItems.xml");
		System.out.println(content);
	}

}
