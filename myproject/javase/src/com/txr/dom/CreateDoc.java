package com.txr.dom;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.tree.BaseElement;
import org.junit.Test;

public class CreateDoc {
	@Test
	public void createDoc(){
Document doc=DocumentHelper.createDocument();//创建文档
Element e= doc.addElement("element1元素1");///为文档添加一个元素
e.addComment("为元素添加注释");///为元素添加注释
e.addProcessingInstruction("target", "intsdrouct");//为元素添加标记

//创建元素
Element e1=new BaseElement("E2元素");//创建元素
//添加属性
e1.addAttribute("title", "liting的dom4j");
e1.addAttribute("这是属性", "shuxing");
e.add(e1);
//添加元素
Element e2= e1.addElement("e3");
e2.addAttribute("shuxing2", "属性2");
 Element e2s= e2.addElement("title");
 e2s.setText("设置文本内容");
//可以使用 addDocType() 方法添加文档类型说明。   
 doc.addDocType("catalog", null,"file://c:/Dtds/catalog.dtd");   
	}

}
