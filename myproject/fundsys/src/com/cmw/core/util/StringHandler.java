package com.cmw.core.util;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import com.cmw.core.kit.file.FileUtil;
import com.cmw.entity.sys.UserEntity;

public class StringHandler {
	// 存放特殊的数组
	private static final char[] chArr = new char[]{'$','|','[',']','(',')','*','+','?','.'};
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";
	/**
	 * 接收从资源文件中读取的键的值
	 */
	public static String propreties_key_value = "";
	
	
	
	/**
	 * 将字符串数组用“，”连接成字符串
	 * @param arr	Object 数组
	 * @return	连接后的字符串
	 */
	public static String join(Object[] arr){
		StringBuffer sb = new StringBuffer();
		for(Object str : arr){
			if(null == str || str.equals("")) continue;
			sb.append(str+",");
		}
		return RemoveStr(sb);
	}
	
	/**
	 * 将字符串数组用“，”连接成字符串
	 * @param arr	字符串数组
	 * @return	连接后的字符串
	 */
	public static String join(String[] arr){
		StringBuffer sb = new StringBuffer();
		for(String str : arr){
			if(null == str || str.equals("")) continue;
			sb.append(str+",");
		}
		return RemoveStr(sb);
	}
	/**
     * 字符串按字节截取
     * @param str 原字符
     * @param len 截取长度
     * @since 2006.07.20
     */
	public static String SplitString(String str, int len) {
		return SplitString(str, len, "...");
	}
     
    /**
     * 字符串按字节截取
     * @param str 原字符
     * @param len 截取长度
     * @param elide 省略符
     * @since 2006.07.20
     */
	public static String SplitString(String str,int len,String elide) {
		if (str == null) {
			return "";
		}
		byte[] strByte = str.getBytes();
		int strLen = strByte.length;
		int elideLen = (elide.trim().length() == 0) ? 0 : elide.getBytes().length;
		if(len>=strLen || len<1) {
			return str;
		}
		if(len-elideLen > 0) {
			len = len-elideLen;
		}
		int count = 0;
		for(int i=0; i<len; i++) {
			int value = (int)strByte[i];
			if(value < 0){
				count++;
			}
		}
		if(count % 2 != 0) {
			len = (len == 1) ? len + 1 : len - 1;
		}
		return new String(strByte, 0, len) + elide.trim();
	}
	
	/**
	 * 打印输出方法
	 * @param msg
	 */
	public void P(Object msg)
	{
		System.out.println(msg);
	}
	
	/**
	 * 分割字符串为字符串数组
	 * @param strs
	 * @param op
	 * @return
	 */
	public static String[] splitStr(String strs,String op)
	{
		if(isValidStr(strs) && isValidStr(op))
		{
			return strs.split(op);
		}
		return null;
	}
	
	/**
	 * 验证字符串是否为 null 或 "" 
	 * 不为 null 或 "" 返回 True 否则返回 false
	 * @param str
	 * @return
	 */
	public static boolean isValidStr(String str)
	{
		return (null != str && !"".equals(str) && !"null".equals(str)) ? true : false;
	}
	
	/**
	 * 验证字符串是否为 null 或 "" 
	 * 不为 null 或 "" 返回 True 否则返回 false
	 * @param obj
	 * @return
	 */
	public static boolean isValidObj(Object obj)
	{
		return (null != obj && !"".equals(obj.toString())) ? true : false;
	}
	
	/**
	 * 验证整型对象是否为空
	 * @param <T>
	 * @param obj
	 * @return 如果为空就返回 false 否则为 true
	 */
	public static <T> boolean isValidIntegerNull(Integer obj){
		if(null != obj && 0 !=obj) return true;
		return false;
	}
	/**
	 * 将字符串转换为整型，如果字符串为 空 或 “” 就为0
	 * @param val
	 * @return
	 */
	public static Integer convertInt(String val){
		return (null == val || "".equals(val)) ? 0 : Integer.parseInt(val);
	}
	/**
	 * 将字符串转换为日期格式
	 * 举例: Date date = StringHandler.dateFormat("yyyy-mm-dd","2008-1-22");
	 * @param pattern	日期格式："yyyy-mm-dd"
	 * @param strDate
	 * @return 返回一个 Date对象
	 */
	public static Date dateFormat(String pattern,String strDate)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将日期格式化为字符串
	 * @param pattern	日期格式："yyyy-MM-dd"等
	 * @param date	日期对象
	 * @return
	 */
	public static String dateFormatToStr(String pattern,Date date){
		if(null==pattern || "".equals(pattern)) pattern="yyyy-MM-dd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		if(null == date) return "";
		return sdf.format(date);
	}
	/**
	 *	将字符串以特定的符号转换为数组 (如果未提供就将以“,”进行分割)
	 * @param strs 一个字符串类型的变长参数。<br/>\n
	 *            如果提供分割自己的分割符号，请将它作为第一个参数传入,
	 *            第二个就是要分割成数组的字符串
	 *            例如：name="peng,deng,hao";
	 *            		StringHandler.convertStrToArr(",",name);
	 * @return
	 */
	public static String[] convertStrToArr(String... strs){
		String[] arrStr = null;
		String  sigin = ",";
		if(strs==null) return null;
		if(strs.length==1) return strs[0].split(sigin);
		if(strs.length >= 2){
			if(isValidSigin(strs[0])){
				strs[1] = replaceSigins(strs[1],strs[0], sigin);
			}else{
				sigin = strs[0]; //过滤不能作为字符串分割的符号
			}
			return strs[1].split(sigin);
		}
		return arrStr;
	}
	
	/**
	 * 替换特殊字符以便分割成字符串数组
	 * @param strs
	 * @param sourceSigin：要被替换的符号字符
	 * @param targetSigin：准备用符号替换字符
	 * @return
	 */
	public static String replaceSigins(String strs,String sourceSigin,String targetSigin){
		if(strs==null) return "";
		StringBuffer sb = new StringBuffer(strs);
		int sindex = sb.indexOf(sourceSigin);
		if(sindex<0) return sb.toString();
		int start = 0,len = sb.length();
		while(start != len){
			int index = sb.indexOf(sourceSigin,start);
			if(index != -1){
				start = index+1;
				sb.deleteCharAt(index).insert(index,targetSigin);
			}else{
				break;
			}
		}
		return sb.toString();
	}
	/**
	 * 验证指定的符号是否能作为分割符进行字符串转换为字符串数组的分割
	 * @param sigin	要验证的符号
	 * @return 返回 true 则为非法字符 false 合法 
	 */
	public static boolean isValidSigin(String sigin){
		char sourceCh = sigin.charAt(0);
		for(char ch : chArr){
			if(ch == sourceCh) return true;
		}
		return false;
	}
	
	/***
	 * 获取真实数据，回车字段保留
	 * @param str
	 * @return
	 */
	public static String GetTrueStr(String str){
		String tempstr = "";
		if(str==null) return tempstr;
		for(int j=0;j<str.length();j++){ 
			 char ch = str.charAt(j); 
			 if(ch=='\n')
				 tempstr += "\\n";
			 else if(ch == '\r' || ch == '\t') 
				 tempstr += "";
			 else 
				 tempstr += String.valueOf(ch);
			// System.out.println(tempstr+"<<"+j);
		}
		return tempstr;
	}
	/**
	 * 中文乱码问题 
	 * @param str
	 * @return
	 */
	public static String getStr(String str){
		try
	    {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("ISO-8859-1");
			String temp = new String(temp_t);
			return temp;
	    }
	    catch(Exception e)
	    {}
	    return null;
	}
	/**
	 * 中文乱码问题  ISO-8859-1 转 UTF-8
	 * @param str
	 * @return
	 */
	public static String GetUTF_8(String str){
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("ISO-8859-1");
			String temp = new String(temp_t,"UTF-8");
			return temp;
	    }catch(Exception e) {}
		return null;
	}
	/**
	 * 中文乱码问题  ISO-8859-1 转 GBK
	 * @param str
	 * @return
	 */
	public static String GetGBK(String str){
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes("ISO-8859-1");
			String temp = new String(temp_t,"GBK");
			return temp;
	    }catch(Exception e) {}
		return null;
	}
	/*****
	 * 打印
	 * @param str：打印值
	 * @param msg：打印说明
	 */
	public static void P(String str,String msg)
	{
		System.out.println(msg+" 值："+str);
	}
	
	/*****
	 * 打印
	 * @param str：打印值
	 */
	public static void P(String str)
	{
		System.out.println(str);
	}
	
	/***
	 * 校验多值
	 * @param str：待校验的字符串
	 * @param iscase：是否检验大小写：true:大小写敏感
	 * @param values：比较的值
	 * @return
	 */
	public static boolean Equals(String str,boolean iscase,String... values){
		for(int i=0;i<values.length;i++){
			if(!iscase){
				str = str.toLowerCase();
				values[i] = values[i].toLowerCase();
			}
			if(str.equals(values[i]))
				return true;
		}
		return false;
	}
	
	/***
	 * 校验多值，不区分大小写
	 * @param str
	 * @param values
	 * @return
	 */
	public static boolean Equals(String str,String... values){
		return Equals(str,false,values);
	}

    /*********
  	 * 空处理字段
  	 * @param valueStr：接收值
  	 * @param nullvalue：为空时默认的值
  	 * @return：为不空的值
  	 */
  	public static String GetValue(String valueStr,String nullvalue)
  	{
  		return (valueStr==null || "".equals(valueStr))?nullvalue:valueStr;
  	}
  	
  	/**
  	 * 将指定的值转换成""空符串
  	 * @param valueStr 要转换的字符
  	 * @param nullStr 用来比较的值
  	 * @param nullvalue
  	 * @return
   	*/
  	public static String GetValue(String valueStr,String eqStr,String nullvalue)
  	{
  		return (valueStr.equals(eqStr))?nullvalue:valueStr;
  	}
  	
  	/*********
  	 * 空处理字段,默认为""
  	 * @param valueStr：接收值
  	 * @return：为不空的值
  	 */
  	public static String GetValue(String valueStr)
  	{
  		return (null==valueStr)?"":valueStr;
  	}

  	/**
     * 判断是否为数字型:
     * "33" "+33" "033.30" "-.33" ".33" " 33." " 000.000 "
     * @param str：接收值
     * @return boolean
    */
    public static boolean IsNumeric(String str) {
    	int begin = 0;
        boolean once = true;
        if (str == null || str.trim().equals("")) {
        	return false;
        }
        str = str.trim();
        if (str.startsWith("+") || str.startsWith("-")) {
        	if (str.length() == 1) {
        		return false;
            }
            begin = 1;
        }
        for (int i = begin; i < str.length(); i++) {
        	if (!Character.isDigit(str.charAt(i))) {
        		if (str.charAt(i) == '.' && once) {
        			// '.' can only once
                    once = false;
                }else {
                	return false;
                }
            }
        }
        if (str.length() == (begin + 1) && !once) {
        	// "." "+." "-."
            return false;
        }
        return true;
    }

    /**
     * 判断是否为整形:
     * "33" "003300" "+33" " -0000 "
     * @param str：接收值
     * @return boolean
     */
    public static boolean IsInteger(String str) {
    	int begin = 0;
    	if (str == null || str.trim().equals("") || str.length()>7) {
    		return false;
    	}
    	str = str.trim();
    	if (str.startsWith("+") || str.startsWith("-")) {
    		if (str.length() == 1) {
    			// "+" "-"
    			return false;
    		}
    		begin = 1;
    	}
    	for (int i = begin; i < str.length(); i++) {
    		if (!Character.isDigit(str.charAt(i))) {
    			return false;
    		}
    	}
    	return true;
   }
   
	/**
  	 * 字符串分割函数
  	 * @param sign  分割符号
  	 * @param splids 要进行分割的字符串
  	 * @return  返回分割好的字符串数组
  	 * @throws Exception
  	 */
  	public static String[] Split(String sign,String splids)
	{
		if (null != splids && !"".equals(splids)) {
			String[] ids = splids.split(sign);
		return ids;
		}
		return null;
		}
  	
  	/**
  	 * 将字符串分割成字符串数组，在分割时根据参数
  	 * removeStr从分割后的字符数组元素中移出指定字符
  	 * 示例：  StringHandler.Split(",",vmodelid,"B,S,M");
  	 * @param sign
  	 * @param splids
  	 * @param removeStr
  	 * @return
  	 * @throws Exception
  	 */
  	public static String[] Split(String sign,String splids,String removeStr) {
  		String[] ids = Split(sign,splids);
  		String[] rChars = removeStr.split(sign);
  		int i=0;
  		for(String id : ids){
  			int offset = id.indexOf(rChars[i]);
  			if(offset != -1){
  				ids[i] = id.substring(offset+1,id.length());
  			}
  			i++;
  		}
  		return ids;
  	}
	/**
  	 * 根据资源文件和资源文件定义的键获取对应的值
  	 * @param properties_fileName_prefix   资源文件名,如果此参数为null 或 "" 则默认为 hh.com.resource.resource.properties
  	 * @param resourceKey  用来查找资源文件对应值的键的名字
  	 * @return  
  	 * @throws Exception 
  	 */
  	public static String[] GetResValue(String properties_fileName_prefix,String resourceKey,String sign) 
  	{
  		String properties_fileName = ((properties_fileName_prefix==null || "".equals(properties_fileName_prefix)) ? "resource" : properties_fileName_prefix)+".properties";
		StringHandler.P("资源文件名:"+properties_fileName);
  		ResourceBundle rb = null;
		try {
			rb = FileUtil.getResourceObj(properties_fileName);
	  		String key_value =  rb.getString(resourceKey);
	  		if(null == key_value || "".equals(key_value)) throw new Exception("根据您所提供的键:"+resourceKey+",无法从"+properties_fileName+"文件中找到对应的值,请检查是否添加了此键!");
	  		P(resourceKey +" = "+ key_value);
	  		propreties_key_value = key_value;
	  		return (null != sign && !"".equals(sign)) ? Split(sign, key_value) : new String[] {key_value};
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
  	}
  	
	/**
  	 * 根据资源文件和资源文件定义的键获取对应的值
  	 * @param properties_fileName_prefix   资源文件名,如果此参数为null 或 "" 则默认为 hh.com.resource.resource.properties
  	 * @param resourceKey  用来查找资源文件对应值的键的名字
  	 * @return  
  	 * @throws Exception 
  	 */
  	public static String GetResValue(String properties_fileName_prefix,String resourceKey) 
  	{
  		String properties_fileName = ((properties_fileName_prefix==null || "".equals(properties_fileName_prefix)) ? "resource" : properties_fileName_prefix)+".properties";
		StringHandler.P("资源文件名:"+properties_fileName);
  		ResourceBundle rb = null;
		try {
			rb = FileUtil.getResourceObj(properties_fileName);
	  		String key_value =  rb.getString(resourceKey);
	  		if(null == key_value || "".equals(key_value)) throw new Exception("根据您所提供的键:"+resourceKey+",无法从"+properties_fileName+"文件中找到对应的值,请检查是否添加了此键!");
	  		P(resourceKey +" = "+ key_value);
	  		return key_value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
  	}
  	
  	/**
  	 * 根据键获取config/resource.properties 资源文件中对应值
  	 * @param resourceKey  键
  	 * @return
  	 */
  	public static String GetResValue(String resourceKey){
  		return GetResValue(null, resourceKey);
  	}
  	/**
  	 * 将一个字符串加入到剪贴板
  	 * @param str
  	 */
  	public static void Paste(String str){
  		Clipboard sysClip = Toolkit.getDefaultToolkit().getSystemClipboard();
  		Transferable text = new StringSelection(str); 
  		sysClip.setContents(text, null); 
  	}

	
	/**  
	* 提供小数位四舍五入处理
	* @param v 需要四舍五入的数字
	* @param scale 小数点后保留几位
	* @return 四舍五入后的结果
	*/
	public static String Round(String v,int scale){
		boolean isNum = IsNumeric(v);
		if(!isNum) return v;
		if(v.indexOf(".") != -1){
			String xiaoshu = v.split("\\.")[1];
			int unints = scale-xiaoshu.length();
			if(unints>0){
				xiaoshu = "";
				for(int i=0;i<unints;i++){
					xiaoshu+="0";
				}
				v+=xiaoshu;
			}
		}else{
			return v;
		}
		String temp="###0.";
		for (int i=0;i<scale;i++ ){
			temp+="0";
		}
		return String.valueOf(new java.text.DecimalFormat(temp).format(Double.parseDouble(v)));  
	}
	
	/**  
	* 提供小数位四舍五入处理(保留两位小数)
	* @param v 需要四舍五入的数字
	* @return 四舍五入后的结果
	*/
	public static Double Round(Double v){
		return Round(v,2);  
	}
	
	/**  
	* 提供小数位四舍五入处理
	* @param v 需要四舍五入的数字
	* @param scale 小数点后保留几位
	* @return 四舍五入后的结果
	*/
	public static Double Round(Double v,int scale){
		if(null==v) return 0d;
		String temp="###0.";
		for (int i=0;i<scale;i++ ){
			temp+="0";
		}
		return Double.parseDouble(new java.text.DecimalFormat(temp).format(v));  
	}
	
	
	/**  
	* 提供小数位四舍五入处理并以字符串返回
	* @param v 需要四舍五入的数字
	* @param scale 小数点后保留几位
	* @return 四舍五入后的结果
	*/
	public static String Round2Str(Double v,int scale){
		if(null==v) return "0.00";
		String temp="###0.";
		for (int i=0;i<scale;i++ ){
			temp+="0";
		}
		v = Double.parseDouble(new java.text.DecimalFormat(temp).format(v));
		return String.valueOf(v);  
	}
	
	/**  
	* 提供小数位四舍五入处理并以 BigDecimal返 回(保留两位小数)
	* @param v 需要四舍五入的数字
	* @return 四舍五入后的 BigDecimal
	*/
	public static BigDecimal Round2BigDecimal(Double v){
		if(null==v) return new BigDecimal(0);
		String temp="###0.";
		for (int i=0;i<2;i++ ){
			temp+="0";
		}
		v = Double.parseDouble(new java.text.DecimalFormat(temp).format(v));
		return new BigDecimal(String.valueOf(v));  
	}
	
	/**  
	* 提供小数位四舍五入处理并以 BigDecimal返 回
	* @param v 需要四舍五入的数字
	* @param scale 小数点后保留几位
	* @return 四舍五入后的 BigDecimal
	*/
	public static BigDecimal Round2BigDecimal(Double v,int scale){
		if(null==v) return new BigDecimal(0);
		String temp="###0.";
		for (int i=0;i<scale;i++ ){
			temp+="0";
		}
		v = Double.parseDouble(new java.text.DecimalFormat(temp).format(v));
		return new BigDecimal(String.valueOf(v));  
	}
	
	/** 
	 * 格式化数字为千分位显示； 
	 * @param 要格式化的数字； 
	 * @return 
	 */  
	public static String fmtMicrometer(Object text)  
	{
		if(isValidObj(text)){
			return fmtMicrometer(text.toString());
		}
		return "";
	}
	/** 
	 * 格式化数字为千分位显示； 
	 * @param 要格式化的数字； 
	 * @return 
	 */  
	public static String fmtMicrometer(String text)  
	{  
	    DecimalFormat df = null;  
	    if(text.indexOf(".") > 0)  
	    {  
	        if(text.length() - text.indexOf(".")-1 == 0)  
	        {  
	            df = new DecimalFormat("###,##0.");  
	        }else if(text.length() - text.indexOf(".")-1 == 1)  
	        {  
	            df = new DecimalFormat("###,##0.0");  
	        }else  
	        {  
	            df = new DecimalFormat("###,##0.00");  
	        }  
	    }else   
	    {  
	        df = new DecimalFormat("###,##0");  
	    }  
	    double number = 0.0;  
	    try {  
	         number = Double.parseDouble(text);  
	    } catch (Exception e) {  
	        number = 0.0;  
	    }  
	    return df.format(number);  
	}  
	
	/**  
	* 提供小数位四舍五入处理
	*  返回最小的（最接近负无穷大）double 值，该值大于或等于参数，并且等于某个整数。
	* @param v 需要四舍五入的数字
	* @param scale 小数点后保留几位
	* @return 四舍五入后的结果
	*/
	public static Double ceil(Double v,int scale){
		if(null==v) return 0d;
		String temp="###0.";
		for (int i=0;i<scale;i++ ){
			temp+="0";
		}
		return Double.parseDouble(new java.text.DecimalFormat(temp).format(v));  
	}
	
	/**
	 * 根据传入的字符串，返回一个整型值
	 * @param val 字符串值
	 * @return 返回一个整型值
	 */
	public static Integer getIntegerVal(String val){
		if(!isValidStr(val)) val = "0";
		Integer intVal = Integer.parseInt(val);
		return intVal;
	}
	
	/**
	 * 根据传入的字符串，返回一个长整型值
	 * @param val 字符串值
	 * @return 返回一个长整型值
	 */
	public static Long getLongVal(String val){
		if(!isValidStr(val)) val = "0";
		Long intVal = Long.parseLong(val);
		return intVal;
	}
	
	/**
	 * 根据传入的字符串，返回一个双精度 Double 值
	 * @param val 字符串值
	 * @return 返回一个Double 值
	 */
	public static Double getDoubleVal(String val){
		if(!isValidStr(val)) val = "0.0";
		Double dVal = Double.parseDouble(val);
		return dVal;
	}
	
	/**
	 * 根据传入的字符串，返回一个 Float 值
	 * @param val 字符串值
	 * @return 返回一个Float 值
	 */
	public static Float getFloateVal(String val){
		if(!isValidStr(val)) val = "0.0";
		Float fVal = Float.parseFloat(val);
		return fVal;
	}
	
	  /**
     * 当Double 类型数据为科学计算法显示时，将其转换成正常的形式显示
     * 例如： 1.0E7 ---> 10000000
     * @param val	要转换的科学计数法数据
     * @return 返回正常的数据显示
     */
    public static String getScienceVal(double val){
    	NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        
       String str = numberFormat.format(val);
       String[] strArray = str.split(",");
       StringBuffer buffer = new StringBuffer();
       for(int i=0;i<strArray.length;i++){
        buffer.append(strArray[i]);
       }
       return buffer.toString();
    }
    
	/**
	 * 判断是否手机号码 
	 * @param mobilePhone
	 * @return
	 */
	public static boolean IsMobile(String mobilePhone){
		if(mobilePhone.length()==11 && (mobilePhone.startsWith("13") || mobilePhone.startsWith("15") || mobilePhone.startsWith("18")))   
	    	return true;
		else 
	    	return false;
	}
  	
	/**
	 * 移除指定的字符
	 * @param sb  StringBuffer对象
	 * @param remove_str	要移除的对象
	 * 使用示例:
	 * 		StringBuffer sb = new StringBuffer("ab,cd,ef,");
	 *  	String str = StringHandler.RemoveStr(sb,",");
	 *  	str 结果就是 "ab,cd,ef"
	 * @return
	 */
	public static String RemoveStr(StringBuffer sb,String remove_str)
	{
		return (sb != null && remove_str != null) ? sb.deleteCharAt(sb.lastIndexOf(remove_str)).toString() : "";
	}
	
	/**
	 * 从源数组中移除指定的多个数据
	 * @param sourceArr  String[] 对象
	 * @param removeArr	String[] 要移除的对象
	 * 使用示例:
	 * 		String sourceArr = new String[]{"ab","cd","ef,"};
	 *  	String removeArr = new String[]{"ab"};
	 *  	str 结果就是 "cd","ef"
	 * @return
	 */
	public static String[] Removes(String[] sourceArr, String[] removeArr){
		List<String> list = Arrays.asList(sourceArr);
		if(null == list || list.size() == 0) return null;
		if(null == removeArr || removeArr.length == 0) return sourceArr;
		List<String> newList = new ArrayList<String>(list);
//		List<Integer> delIndexList = new ArrayList<Integer>();
		for(int i=0,count=list.size(); i<count; i++){
//			boolean isDel = false;
			String sourceStr = list.get(i);
			for(String removeStr : removeArr){
				if(sourceStr.equals(removeStr)){
					newList.remove(removeStr);
//					isDel = true;
					break;
				}
			}
//			if(isDel) delIndexList.add(i);
		}
//		if(null == delIndexList || delIndexList.size() == 0) return sourceArr;
//		for(int i=0,count = delIndexList.size(); i<count; i++){
//			int delIndex = delIndexList.get(i);
//			newList.remove(delIndex);
//		}
		String[] newArr = new String[1];
		newArr = newList.toArray(newArr);
		return newArr;
	}
	
	
	/**
	 * 移除最后一个逗号
	 * @param sb  StringBuffer对象
	 * 使用示例:
	 * 		StringBuffer sb = new StringBuffer("ab,cd,ef,");
	 *  	String str = StringHandler.RemoveStr(sb);
	 *  	str 结果就是 "ab,cd,ef"
	 * @return
	 */
	public static String RemoveStr(StringBuffer sb)
	{
		return (sb != null && sb.length()>0) ? sb.deleteCharAt(sb.lastIndexOf(",")).toString() : "";
	}
	
	/**
	 * 移除最后一个逗号
	 * @param sb  StringBuilder对象
	 * 使用示例:
	 * 		StringBuffer sb = new StringBuffer("ab,cd,ef,");
	 *  	String str = StringHandler.RemoveStr(sb);
	 *  	str 结果就是 "ab,cd,ef"
	 * @return
	 */
	public static String RemoveStr(StringBuilder sb)
	{
		return (sb != null && sb.length()>0) ? sb.deleteCharAt(sb.lastIndexOf(",")).toString() : "";
	}
	
	/**
	 * 移除指定的字符
	 * @param sb  StringBuffer对象
	 * @param remove_str	要移除的对象
	 * 使用示例:
	 * 		String strids ="ab,cd,ef,";
	 *  	String str = StringHandler.RemoveStr(strids,",");
	 *  	str 结果就是 "ab,cd,ef"
	 * @return
	 */
	public static String RemoveStr(String str,String remove_str)
	{
		StringBuffer sb = new StringBuffer(str);
		return RemoveStr(sb,remove_str);
	}
	
	/**
	 * 替换回车等字符为空字符
	 * @param content
	 * @return
	 */
	public static String replaceEnter(String content)
	{
		//去掉回车和换行符，否则页面无法显示
		return (null != content) ? content.replaceAll("[\\n\\r]*","") : "";
	}
	
	/**
	 * 替换双引号和单引号为HTML 标记的双引号和单引号
	 * @param content
	 * @return
	 */
	public static String replaceBothSign(String content)
	{
		return (null != content) ? content = content.replaceAll("\\\"",   "&quot").replace("\\\'", "&apos;"): "";
	}
	
	/**
	 * 替换 SQL 查询语句中的 一个单引号为两个单引号，以防止非法注入
	 * 例如：StringHandler.replaceSqlSign("'王兰'") ----> ''王兰''
	 * @param content
	 * @return
	 */
	public static String replaceSqlSign(String content){
		return (null != content) ? content = content.replaceAll("'",   "''"): "";
	}
	/**
	 * 用 indexOf 和 subString 方法将一个字符串以指定的分隔符
	 * 拆分为有两个长度的字符串数组
	 * @param val 要进行拆分的字符串
	 * @param sigin 分隔符
	 * @return 返回两个长度的字符串数组
	 */
	public static String[] splitStrToArr(String val,String sigin){
		int offset = val.indexOf(sigin);
		if(-1 == offset) return new String[]{val};
		String[] arr = {val.substring(0, offset),val.substring(offset+sigin.length(),val.length())};
		return arr;
	}
	
	/**
	 * 将 arr 转化为 JavaScript 数组格式的字符串
	 * 例如："["btn_1_add","btn_1_del"]"
	 * @param <T>
	 * @param arr 要转换的集合类
	 * @return 返回转化后的 JavaScript 数组格式的字符串
	 */
	public static <T> String getJsArrStr(List<T> arr){
		StringBuffer sb = new StringBuffer("[");
		for(T item : arr){
			sb.append("\""+item+"\",");
		}
		String arrStr = RemoveStr(sb, ",");
		arrStr += "]";
		return arrStr;
	}
	
	/**
	 * 将指定的字符串值以指定的类型转型后并返回
	 * @param val	要转型的字符串值
	 * @param type	数据类型的简写，相对应数据类型如下
	 * * 则依次是以下意思：
	 * 		I : Integer	,	L : Long
	 * 		F : Float	,	O : Double
	 * 		D : Date	,	B : Boolean
	 * @return	返回一个转型后的对象值
	 */
	public static Object getValByType(String val,String type){
		type = type.toUpperCase();
		Object objVal = null;
		if("I".equals(type)){
			objVal = getIntegerVal(val);
		}else if("L".equals(type)){
			objVal = getLongVal(val);
		}else if("F".equals(type)){
			objVal = getFloateVal(val);
		}else if("O".equals(type)){
			objVal = getDoubleVal(val);
		}else if("D".equals(type)){
			objVal = dateFormat(DATE_FORMAT,val);
		}else if("B".equals(type)){
			objVal = new Boolean(val.toLowerCase());
		}
		return objVal;
	}
	
	/**
	 * 获取 classes 所在的真实物理路径
	 * @return
	 */
	public static String getClassPath(){
		String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
		int index = classPath.indexOf("/");
		if(index == 0) classPath = classPath.substring(1);
		return classPath;
	}
	
	/**
	 * 将 Windows 中文件路径分隔符替换成正斜杠分隔符。
	 * 例如： E:\\j2ee_proj\\test  ---->  E:/j2ee_proj/test
	 * @param path	要替换的Window 分隔符
	 * @return
	 */
	public static String getSpeatorPath(String path){
		return path.replaceAll("[\\\\]", "/");
	}
	
	/**
	 * 从 message_*.properties 资源文件中加载字符串并 格式化字符串
	 * 例如：
	 *   String val = "\"{0}\" 是我的弟的女朋友，她现在在{1}工作!";
	 *	val = formatFromResource("customer.black.error", "程明强","IBM");
	 *	输出：val = ""程明强" 是我的弟的女朋友，她现在在IBM工作";
	 * @param source	要格式化的源字符串
	 * @param args	字符串中填充的参数值
	 * @return 返回格式化后的字符串
	 */
	public static String formatFromResource(String i18n,String resouceKey , Object... args){
		if(!isValidStr(i18n)) i18n = UserEntity.I18N_ZH_CN;
		String source = GetResValue("message_"+i18n,resouceKey);
		return format(source, args);
	}
	
	/**
	 * 格式化字符串
	 * 例如：
	 *   String val = "\"{0}\" 是我的弟的女朋友，她现在在{1}工作!";
	 *	val = format(val, "程明强","IBM");
	 *	输出：val = ""程明强" 是我的弟的女朋友，她现在在IBM工作";
	 * @param source	要格式化的源字符串
	 * @param args	字符串中填充的参数值
	 * @return 返回格式化后的字符串
	 */
	public static String format(String source , Object... args){
		if(!isValidStr(source)) return null;
		for(int i=0,count=args.length; i<count; i++){
			Object argVal = args[i];
			if(!isValidObj(argVal)) argVal = "";
			source = source.replace("{"+i+"}", argVal.toString());
		}
		return source;
	}
	
	public static String getClassesPath(String fileName){
		String resFilePath = isValidStr(fileName) ? fileName : "";
		return Thread.currentThread().getContextClassLoader().getResource(resFilePath).getPath();
	}
		
	public static void main(String[] args){
		String[] sourceArr = new String[]{"ab","cd","ef"};
		String[] removeArr = new String[]{"ab"};
		String whereStr = " A.creator=1 and A.creator=2 and";
		 System.out.print((whereStr.lastIndexOf("and")+"and".length())+",length="+whereStr.length());
	}
	
}
