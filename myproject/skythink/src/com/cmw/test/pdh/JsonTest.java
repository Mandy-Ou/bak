package com.cmw.test.pdh;

import java.util.HashMap;

import com.cmw.core.util.JsonUtil;
import com.cmw.core.util.SHashMap;

/**
 *Title: JsonTest.java
 *@作者： 彭登浩
 *@ 创建时间：2012-11-18下午5:48:42
 *@ 公司：	同心日信科技有限公司
 */
public class JsonTest {
	public static void main(String[] args) {
		Object name="彭登浩";
		String jsonname=JsonUtil.toJson(name);
		System.out.println(jsonname);
		
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		map.put("name", "pdh");
		String xingming=JsonUtil.getJsonString("name" ,"pdh");
		System.out.println(xingming);
		HashMap<String , Object>  map1= new HashMap<String , Object>();
		map1.put("age", 12);
		map1.put("name", "pdh");
		String xingxin = JsonUtil.getJsonString(map1);
		System.out.println(xingxin);
		
	}
}
