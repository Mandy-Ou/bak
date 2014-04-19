package com.cmw.core.util;

import java.util.Calendar;

import com.alibaba.fastjson.JSONObject;

public class CodeRule {
	
	public static String getDateString(){
		Calendar cObj = Calendar.getInstance();
		return cObj.get(Calendar.YEAR)+formatDate(cObj.get(Calendar.MONTH)+1)+formatDate(cObj.get(Calendar.DATE))
				+formatDate(cObj.get(Calendar.HOUR))+formatDate(cObj.get(Calendar.MINUTE))+formatDate(cObj.get(Calendar.SECOND));
	}
	
	public static String formatDate(long date){
		return (date < 10) ? "0"+date : ""+date;
	}
	
	/**
	 * 以指定的编码前缀和数据库中最后条记录的主键ID生成编号
	 * @param prefix
	 * @param num
	 * @return
	 */
	public static String getCode(String prefix,Long num){
		num = (null == num) ? 0 : num;
		num+=num;
		return prefix+getDateString()+formatDate(num);
	}

	/**
	 * 以指定的编码前缀加系统时间缀作为编码并以JSON字符串格式返回
	 * @param prefix	编码前缀
	 * @return 以JSON字符串格式返回编码  例：{code : 'M201204201990190'}
	 */
	public static String getCode(String prefix){
		String code = prefix+getDateString();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("success", "true");
		return jsonObject.toString();
	}
	
	/**
	 * 以指定的编码前缀加系统时间缀作为编码并以JSON字符串格式返回
	 * @param prefix	编码前缀
	 * @return 以JSON字符串格式返回编码  例：{code : 'M201204201990190'}
	 */
	public static String getCode(String prefix,SHashMap<String, Object> attactParams){
		String code = prefix+getDateString();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("success", "true");
		jsonObject.putAll(attactParams.getMap());
		return jsonObject.toString();
	}
}
