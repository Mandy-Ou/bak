package com.cmw.core.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * JSON 工具类
 * @author cmw_1984122
 *
 */
public class JsonUtil {
	private static Gson gsonObj = new Gson();
	
	/**
	 * 将指定的对象转换成 JSON 字符串
	 * @param src	要转换的对象
	 * @return	返回JSON 字符串
	 */
	public static String toJson(Object src){
		String json = "{}";
		if(null != src){
			json = gsonObj.toJson(src);
		}
		return json;
	}
	
	/**
	 * 将指定的KEY和VALUE转换成 JSON 字符串
	 * 例如： getJsonString("name","zhaosan") --> {name:"zhaosan"}
	 * @param params 要转换为JSON字符串的 Map 对象
	 * @return 返回一个 JSON 字符串
	 */
	public static String getJsonString(String key ,Object value){
		JsonObject jsonObj = new JsonObject();
		if(null == value){
			value = "";
		}else{
			value = value.toString();
		}
		jsonObj.addProperty(key, (String)value);
		return jsonObj.toString();
	}
	
	/**
	 * 将一个 Map 对象转换成JSON 字符串
	 * 例如： {name:"zhaosan",age:"14"}
	 * @param params 要转换为JSON字符串的 Map 对象
	 * @return 返回一个 JSON 字符串
	 */
	public static String getJsonString(Map<String,Object> params){
		if(null == params || params.size() == 0) return "{}";
		Set<String> keys = params.keySet();
		JsonObject jsonObj = new JsonObject();
		for(String key : keys){
			Object val = params.get(key);
			if(null == val){
				val = "";
			}else{
				val = val.toString();
			}
			jsonObj.addProperty(key, (String)val);
		}
		return jsonObj.toString();
	}
	
	//------------------------> 以下是 未使用 GSON 包的方法，可能存在某些问题
	/**
	 * 将JSON格式字符串转为 指定的类 对象
	 * @param jsonStr 要转换的JSON格式字符串
	 * @param clz 转换的目标类
	 * @return	转换成功的目标类对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T convertJsonToObj(String jsonStr,Class<T> clz){
		JSONObject jObj = JSONObject.fromObject(jsonStr);
	   T beanObj = (T)JSONObject.toBean(jObj, clz);
		return beanObj;
	}
	
	
	
	/**
	 * 将JSON格式字符串转为 指定的类 对象
	 * @param jsonStr 要转换的JSON格式字符串
	 * @param clz 转换的目标类
	 * @return	转换成功的目标类对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] convertJsonToArr(String jsonStr,Class<T> clz){
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		 T[] beanObj = (T[])JSONArray.toArray(jsonArr, clz);
		return beanObj;
	}
	
	/**
	 * 将JSON格式字符串转为 指定的Set 对象
	 * @param jsonStr 要转换的JSON格式字符串
	 * @param clz 转换的目标类
	 * @return	转换成功的目标类对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> Set<T> convertJsonToSet(String jsonStr,Class<T> clz){
		if(!StringHandler.isValidStr(jsonStr)) return null;
		 Set<T> set = new HashSet<T>();
		if(jsonStr.indexOf("[")==0||jsonStr.lastIndexOf("]")==jsonStr.length()-1){
			JSONArray jsonArr = JSONArray.fromObject(jsonStr);
			 Collection<T> co = JSONArray.toCollection(jsonArr, clz);
			 set.addAll(co);
		}else{
			 T obj = convertJsonToObj(jsonStr,clz);
			 set.add(obj);
		}
		return set;
	}
	
	
	@SuppressWarnings("unchecked")
	public static SHashMap<String, Object> convertJsonToSHashMap(String jsonStr){
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		JSONObject jsonObj = JSONObject.fromObject(jsonStr);
		
		Set<String>  keys = jsonObj.keySet();
		for(String key : keys){
			Object val = jsonObj.get(key);
			String cls = val.getClass().getSimpleName();
			if("JSONObject".equals(cls) || null == val || "".equals(val)) continue;
			params.put(key, val);
		}
		return params;
	}
	/**
	 * 将JSON格式字符串转为 xml 字符串
	 * @param jsonStr 要转换的JSON格式字符串
	 * @return	转换后的 xml 字符串
	 */
	@SuppressWarnings("unchecked")
	public static String convertJsonToXml(String jsonStr){
		JSONObject jObj = JSONObject.fromObject(jsonStr);
		XMLSerializer xs = new XMLSerializer();
		String xml = xs.write(jObj);
		return xml;
	}
	
	
	/**
	 * 将JSON格式数组字符串转为 xml 字符串
	 * @param jsonStr 要转换的JSON格式字符串
	 * @return	转换后的 xml 字符串
	 */
	@SuppressWarnings("unchecked")
	public static String convertJsonArrToXml(String jsonStr){
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		XMLSerializer xs = new XMLSerializer();
		String xml = xs.write(jsonArr);
		return xml;
	}
	
	/**
	 * 将xml格式数组字符串转为 JSONArray 对象
	 * @param xmlStr 要转换的xml格式字符串
	 * @return	转换后的 JSONArray 对象
	 */
	@SuppressWarnings("unchecked")
	public static JSONArray convertXmlToJsonArr(String xmlStr){
		XMLSerializer xs = new XMLSerializer();
		JSONArray jsonArr = (JSONArray)xs.read(xmlStr);
		return jsonArr;
	}
	
	public static void main(String[] args){
//		// json 字符串转	指定的对象
//		String jsonStr = "{\"id\":\"1\",\"name\":\"json\",\"isenabled\":\"true\",\"yyyy\":\"1111\"}";
//		ResType rt = convertJsonToObj(jsonStr, ResType.class);
//		System.out.println(rt.getName());
//		
//		// json 字符串数组转	指定的对象数组
//		 jsonStr = "[{\"id\":null,\"name\":\"json\",\"isenabled\":true},{\"id\":2,\"name\":\"arr\",\"isenabled\":false}]";
//		 ResType[] rts =  convertJsonToArr(jsonStr, ResType.class);
//		 for(ResType rtt : rts){
//			 System.out.println(rtt.getName());
//		 }
//		 
//		// json 字符串转 XML字符串
//		 @SuppressWarnings("unused")
//		String jsonStr1 = "{\"id\":1,\"name\":\"json\",\"isenabled\":true}";
//		String xml = convertJsonArrToXml(jsonStr);
//		System.out.println(xml);
//		
//		// xml 字符串转 JSONArray 对象
//		JSONArray jArr = convertXmlToJsonArr(xml);
//		for(int i=0; i<jArr.size(); i++){
//			JSONObject j = (JSONObject)jArr.get(i);
//			System.out.println(j.get("name"));
//		}
	}
}
