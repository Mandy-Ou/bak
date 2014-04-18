package com.cmw.test.pdh;

import java.util.HashMap;
import java.util.Set;

import com.cmw.core.util.SHashMap;

/**
 *Title: ShashMap.java
 *@作者： 彭登浩
 *@ 创建时间：2012-11-18下午3:55:22
 *@ 公司：	同心日信科技有限公司
 */
public class ShashMapTest {

	public static void main(String[] args) {
		
		SHashMap<String,Object> map= new SHashMap<String, Object>();
		map.put("name", "彭登浩");
		map.put("sex", "男");
		map.put("age", 22);
		System.out.println("name:"+map.getvalAsStr("name"));
		System.out.println("sex:"+map.getvalAsStr("sex"));
//		map.remove("name");
//		System.out.println("name:"+map.getvalAsStr("name"));
//		map.removes("name,sex");
//		System.out.println("name:"+map.getvalAsStr("name"));
//		System.out.println("sex:"+map.getvalAsStr("sex"));
		map.get("name");
		System.out.println("name:"+map.getvalAsStr("name"));
		
		Long age=map.getvalAsLng("age");
		System.out.println(age);
		Float age1=map.getvalAsFlt("age");
		System.out.println(age1);
		Double age2=map.getvalAsDob("age");
		System.out.println(age2);
		
		Set<String> key=map.keySet();
		System.out.println(key);
		
		System.out.println(map.size());
		
		HashMap<String,Object> val = map.getMap();
		System.out.println(val);
		
	}

}
