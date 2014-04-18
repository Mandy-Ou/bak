package com.txr.dom;

import org.dom4j.io.SAXReader;
import org.junit.After;
import org.junit.Before;

public class Document {
	SAXReader saxR = null;

	@Before
	public void init() {
		saxR = new SAXReader();
	}

	@After
public 	void destory() {
		saxR = null;
		System.gc();
	}
public void fiale(Object o){
	if(null!=o){
		System.out.println(o);
	}
}
}
