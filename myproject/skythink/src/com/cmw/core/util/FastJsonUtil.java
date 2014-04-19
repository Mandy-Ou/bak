package com.cmw.core.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * 
 * 底层支持：基于 阿里  温 少 的 FastJson 库 
 * FastJson 地址：http://119.38.217.15/wiki/display/fastjson 
 * @author chengmingwei
 *
 */
public class FastJsonUtil {
	/**
	 * 将JSON格式字符串转为 指定的类 对象
	 * @param jsonStr 要转换的JSON格式字符串
	 * @param clz 转换的目标类
	 * @return	转换成功的目标类对象
	 */
	public static <T> T convertJsonToObj(String jsonStr,Class<T> clz){
		T bean = JSON.parseObject(jsonStr, clz);
		return bean;
	}
	
	/**
	 * 将JSON格式字符串转为  JSONArray 对象
	 * @param jsonStr 要转换的JSON格式字符串
	 * @return	转换成功的目标类对象
	 */
	public static JSONArray convertStrToJSONArr(String jsonStr){
		return JSON.parseArray(jsonStr);
	}
	
	/**
	 * 将JSON格式字符串转为  JSONObject 对象
	 * @param jsonStr 要转换的JSON格式字符串
	 * @return	JSONObject 对象
	 */
	public static JSONObject convertStrToJSONObj(String jsonStr){
		return JSON.parseObject(jsonStr);
	}
	
	/**
	 * 将JSON格式字符串转为  JSONObject 对象
	 * @param jsonStr 要转换的JSON格式字符串
	 * @return	JSONObject 对象
	 */
	public static Map<String,Object> convertMap4Json(String jsonStr){
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		return jsonObj;
	}
	
	/**
	 * 将JSON格式字符串转为  JSONObject 对象
	 * @param jsonStr 要转换的JSON格式字符串
	 * @return	Map<String,Object> 对象
	 */
	public static Map<String,Object> convertStrToMap(String jsonStr){
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		if(null == jsonObj) return null;
		 Map<String,Object> map = new HashMap<String, Object>();
		 Set<String> keys = jsonObj.keySet();
		 for(String key : keys){
			 map.put(key, jsonObj.get(key));
		 }
		 return map;
	}
	
	/**
	 * 将 JAVA 对象转换成 JSONObject 对象
	 * @param obj	JAVA 对象
	 * @return	 JSONObject 对象
	 */
	public static<T> JSONObject convertObjToJsonObj(T obj){
		JSONObject jsonObj = (JSONObject)JSONObject.toJSON(obj);
		return jsonObj;
	}
	
	/**
	 * 将 JAVA 对象转换成 JSONObject 对象
	 * @param obj	JAVA 对象
	 * @param appendParams 要添加到 JSONObject 中的属性值数据
	 * @return	 JSONObject 对象
	 */
	public static<T> JSONObject convertObjToJsonObj(T obj,Map<String,Object> appendParams){
		JSONObject jsonObj = (JSONObject)JSONObject.toJSON(obj);
		jsonObj.putAll(appendParams);
//		if(null != appendParams && appendParams.size() > 0){
//			Set<String> keys = appendParams.keySet();
//			for(String key : keys){
//				jsonObj.put(key, appendParams.get(key));
//			}
//		}
		return jsonObj;
	}
	
	/**
	 * 将批定的对象指定的字段值以 Map 对象返回
	 * @param obj	JAVA 对象
	 * @param fields 对象的属性字段字符串列表，用","分隔
	 * @return	 Map 将对象指定的字段以 Map 对象返回
	 */
	public static <T> Map<String,Object> getMapByObject(T obj,String fields){
		if(!StringHandler.isValidStr(fields)) return null;
		JSONObject jsonObj = (JSONObject)JSONObject.toJSON(obj);
		Map<String,Object> map = new HashMap<String, Object>();
		String[] fieldArr = fields.split(",");
		for(String field : fieldArr){
			Object val = jsonObj.get(field);
			if(!StringHandler.isValidObj(val)) val = "";
			map.put(field, val);
		}
		return map;
	}
	
	/**
	 * 将JSON格式字符串转为 指定的类 对象 List
	 * @param jsonStr 要转换的JSON格式字符串
	 * @param clz 转换的目标类
	 * @return	转换成功的目标类对象 List
	 */
	public static <T> List<T> convertJsonToList(String jsonStr,Class<T> clz){
		List<T> beans = JSON.parseArray(jsonStr,clz);
		return beans;
	}
	
	/**
	 * 将指定的类 对象 转换成 JSON格式字符串
	 * @param obj 要转换为JSON格式的对象
	 * @return	转换成功的JSON字符串
	 */
	public static <T> String convertJsonToStr(T obj){
		String jsonStr = JSON.toJSONString(obj);
		return jsonStr;
	}
	
	/**
	 * 将指定的类 对象 转换成 JSON格式字符串
	 * @param obj 要转换为JSON格式的对象
	 * @return	转换成功的JSON字符串
	 */
	public static <T> String convertJsonToStr(T obj,Callback callback){
		String jsonStr = null;
		if(null == callback){
			jsonStr = JSON.toJSONString(obj);
		}else{
			JSONObject jsonObj = convertObjToJsonObj(obj);
			callback.execute(jsonObj);
			if(jsonObj!=null){
				jsonStr = jsonObj.toJSONString();
			}else{
				jsonStr="";
			}
			
		}
		return jsonStr;
	}
	
	/**
	 * 将指定的类 对象 转换成 JSON格式字符串
	 * @param obj 要转换为JSON格式的对象
	 * @param appendParams 要添加到 JSON 字符串中的属性值数据
	 * @return	转换成功的JSON字符串
	 */
	public static <T> String convertJsonToStr(T obj,Map<String,Object> appendParams){
		JSONObject jsonObject = convertObjToJsonObj(obj,appendParams);
		return jsonObject.toString();
	}
	
	/**
	 * 将指定的 Map 对象 转换成 JSON格式字符串
	 * @param obj 要转换为JSON格式的 Map 对象
	 * @return	转换成功的JSON字符串
	 */
	public static <T> String convertMapToJsonStr(Map<String,Object> appendParams){
		return  JSON.toJSONString(appendParams);
	}
	
	/**
	 * 将 jsonObj 中指定列的日期数据格式化为日期字符串
	 * @param jsonObj	要格式化的 JSONObject 对象
	 * @param fmtCmns	要格式化的 key 数组
	 */
	public static void fmtDate2str(JSONObject jsonObj,String[] fmtCmns){
		for(String cmn : fmtCmns){
			Date date = jsonObj.getDate(cmn);
			jsonObj.put(cmn, DateUtil.dateFormatToStr(date));
		}
	}
	
	/**
	 * 处理特殊数据时的自定义回调接口
	 * @author chengmingwei
	 *
	 */
	public interface Callback{
		void execute(JSONObject jsonObj);
	}
}
