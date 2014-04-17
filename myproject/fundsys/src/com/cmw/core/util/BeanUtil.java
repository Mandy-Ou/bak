package com.cmw.core.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.action.BaseAction;
import com.cmw.core.base.entity.BaseEntity;
import com.cmw.core.base.entity.IdBaseEntity;
import com.cmw.core.base.exception.UtilException;
import com.cmw.entity.sys.UserEntity;

/**
 * Bean 转换工具类
 * @author chengmingwei
 *
 */
public class BeanUtil {
	/*------------------------- MY CODE START -----------------------------*/	
	private static HttpServletRequest request = null;
	public static String encode = "UTF-8"; 
	public static void setRequest(HttpServletRequest req) {
		request = req;
	}
	
	
	/**
	 * 根据前缀和指定的字段名，以及开始索引和结束索引，以指定的目标类。生成装载此类对象的 Set 对象
	 * @param <T>
	 * @param prefix	
	 * @param fieldNames
	 * @param tagCls
	 * @param sIndex
	 * @param eIndex
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static<T> Set fillSet(String prefix,String fieldNames,Class<T> tagCls,int sIndex,int eIndex){
		Set<T> vals = new HashSet<T>();
		String[] fNames = fieldNames.split(",");
		StringBuffer sb = new StringBuffer();
		int count=0;
		for(int i=sIndex;i<=eIndex; i++){
			for(String fName : fNames){
				sb.append(prefix+"."+fName+i+",");
			}
			count++;
		}
		sb = sb.deleteCharAt(sb.length()-1);
		fieldNames = sb.toString();
		for(int i=0; i<=count;i++){
			T obj = copyValue(fieldNames,tagCls);
			vals.add(obj);
		}
		return vals;
	}
	
	/**
	 * 将指定的字符串值转换成 List 列表
	 *  list 的第一个值为 keys
	 *  	  第二个值为 vals
	 * @param valsStr	要转换或解析的字符串
	 * @return	返回 list 对象
	 */
	public static Map<String, Object> parseVals(String valsStr){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String[] keyVals = valsStr.split("&");
			int len = keyVals.length;
			System.out.println("-------- parseVals of map val list START -------");
			for(int i=0; i<len; i++){
				String keyVal = keyVals[i];
				String[] kv = keyVal.split("=");
				String key = kv[0];
				String val = kv == null || kv.length == 1 ? null : kv[1];
				if(StringHandler.isValidStr(val)){
					val =java.net.URLDecoder.decode(val,encode);
				}
				map.put(key, val);
				System.out.println(key+"="+val);
			}
			System.out.println("-------- parseVals of map val list END -------");
		return map;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将指定的字符串值复制到指定类的对象中，
	 * 并反回一个对象
	 * @param <T>
	 * @param valsStr	要解打的字符串值
	 * @param targetCls	目标对象类
	 * @return	返回 targetCls 的对象
	 */
	public static<T> T copyValToEntity(Map<String, Object> map,Class<T> targetCls){
		return copyValToEntity(map,null,targetCls);
	}
	
	/**
	 * 根据类全限定名获取该类的实例对象
	 * @param className  类全限定名
	 * @return 返回该类的实例对象
	 */
	@SuppressWarnings("rawtypes")
	public static Object getInstance(String className){
		Object tagObj = null;
		try {
			Class targetCls = Class.forName(className);
			tagObj = targetCls.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return tagObj;
	}
	
	/**
	 * 将  map 对象中存储的值复制到指定类的对象中，
	 * 并反回此`* ++ 对象
	 * @param <T>  map 要复制到实体的 map 对象
	 * @param user	当前用户对象
	 * @param targetCls	目标对象类
	 * @return	返回 targetCls 的对象
	 */
	@SuppressWarnings("unchecked")
	public static<T> T copyValToEntity(Map<String, Object> map,UserEntity user,Class<T> targetCls){
		Object tagObj;
		try {
			tagObj = targetCls.newInstance();
			/*--------- 为 Object 赋值 CODE START ---------*/
			String pkName = GenericsUtil.getPkName(targetCls);
			String val = (String)map.get(pkName);
			//isAdd = false --> 修改 / 反之新增
			boolean isAdd = StringHandler.isValidStr(val) ? false : true;
			
			Map<String,Field> fields = GenericsUtil.getAllFNames(targetCls);
			Set<String> keys = fields.keySet();
			for(String key : keys){
				Field field = fields.get(key);
				@SuppressWarnings("rawtypes")
				Class typeCls = field.getType();
				val = map.get(key)+"";
				if(!StringHandler.isValidStr(val) && null != user){	//新增或修改时，对继承字段赋值
					val = getSupEntityVal(user,key,isAdd);
				}
				if(!StringHandler.isValidObj(val)) continue;
				if(!isSType(typeCls)) continue;
				field.setAccessible(true);
				Object value = castTheVal(typeCls,val);
				try {
					field.set(tagObj,value);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}/*--------- 为 Object 赋值 CODE END ---------*/
			return (T)tagObj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将指定的字符串值复制到指定类的对象中，
	 * 并反回一个对象
	 * @param <T>
	 * @param valsStr	要解板的字符串值
	 * @param targetCls	目标对象类
	 * @return	返回 赋过值的 targetCls 的对象
	 */
	public static<T> T copyValToEntity(String valStrs,Class<T> targetCls){
		Map<String, Object> map = parseVals(valStrs);
		return copyValToEntity(map,targetCls);
	}
	/**
	 * 根据指定的字符串返回一个实例化并赋值的对象
	 * @param <T>
	 * @param fieldNames
	 * @param tagCls
	 * @return
	 */
	public static<T> T copyValue(String fieldNames,Class<T> tagCls){
		if(null == fieldNames || "".equals(fieldNames)) return null;
		String[] sFields = StringHandler.splitStr(fieldNames, ",");
		return copyValue(sFields,tagCls);
	}
	
	/**
	 * 将目标对象的指定字段值存入 SHashMap 对象中并返回
	 * @param mapKeys	 SHashMap 对象 键字符串 ,多个以","分隔
	 * @param entityFields	对象字段值字符串,多个以","分隔
	 * @param sourceObj	要取值的目标对象
	 * @return	返回 SHashMap 对象
	 */
	@SuppressWarnings("rawtypes")
	public static SHashMap<String, Object> copyToMap(String mapKeys,String entityFields, Object sourceObj){
		SHashMap<String, Object> map = new SHashMap<String, Object>();
		String[] keys = mapKeys.split(",");
		String[] fieldNames = entityFields.split(",");
		try {	
			Class clz = sourceObj.getClass();
			int i=0;
			for(String fieldName : fieldNames){
				Field fld = clz.getDeclaredField(fieldName);
				Class type = fld.getType();
				if(isSType(type)){
					fld.setAccessible(true);
					Object value = fld.get(sourceObj);
					value = (null == value) ? "" : convertTheVal(type,value);
					map.put(keys[i], value);
				}
				i++;
			}
			return map;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据指定的字符串返回一个实例化并赋值的对象
	 * @param <T>
	 * @param fieldNames
	 * @param tagCls
	 * @return
	 * @throws UtilException 
	 */
	public static<T> T copyValue(Class<T> tagCls,HttpServletRequest request) throws UtilException{
		BeanUtil.request = request;
		String[] sFields = getFieldsNames(tagCls);
		return copyValue(sFields,tagCls);
	}
	
	/**
	 * 根据指定的字符串返回一个实例化并赋值的对象
	 * @param <T>
	 * @param fieldNames
	 * @param tagCls
	 * @param excludeFields 不需要赋值的字段数组
	 * @return
	 * @throws UtilException 
	 */
	public static<T> T copyValue(Class<T> tagCls,HttpServletRequest request,String... excludeFields) throws UtilException{
		BeanUtil.request = request;
		if(null == excludeFields || excludeFields.length == 0) throw new UtilException("参数：excludeFields 必须提供!");
		String[] sFields = getFieldsNames(tagCls);
		sFields = StringHandler.Removes(sFields, excludeFields);
		return copyValue(sFields,tagCls);
	}
	
	/**
	 * 根据指定的字符串返回一个实例化并赋值的对象
	 * @param <T>
	 * @param fieldNames
	 * @param tagCls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> T copyValue(String[] sFields,Class<T> tagCls){
		Object tagObj = null;
		/*--------- 创建Object CODE START ---------*/
		try {
			tagObj = tagCls.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}/*--------- 创建Object CODE END ---------*/
		
		/*--------- 为 Object 赋值 CODE START ---------*/
		//获取当前登录用户
		UserEntity user = (UserEntity)request.getSession().getAttribute(SysConstant.USER_KEY);
		if(null == user) user = BaseAction.getTestUser();
		//通过主键值判断是新增还是修改
		String pkName = GenericsUtil.getPkName(tagCls);
		String val = request.getParameter(pkName);
		//isAdd = false --> 修改 / 反之新增
		boolean isAdd = StringHandler.isValidStr(val) ? false : true;
		Map<String,Field> map = GenericsUtil.getAllFNames(tagCls);
		Set<String> keys = map.keySet();
		for(String key : keys){
			boolean isContinue = true;
			if(null != sFields && sFields.length > 0){
				for(String fieldName : sFields){
					if(key.equals(fieldName)){
						isContinue = false;
						break;
					}
				}
			}
		
			if(isContinue) continue;
			Field field = map.get(key);
			@SuppressWarnings("rawtypes")
			Class typeCls = field.getType();
			val = request.getParameter(key);
			
			if(!StringHandler.isValidStr(val) && null != user){	//新增或修改时，对继承字段赋值
				val = getSupEntityVal(user,key,isAdd);
			}
			if(!StringHandler.isValidStr(val)) continue;
			if(!isSType(typeCls)) continue;
			field.setAccessible(true);
			System.out.println(key+" = "+val);
			Object value = castTheVal(typeCls,val);
			try {
				field.set(tagObj,value);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}/*--------- 为 Object 赋值 CODE END ---------*/
		return (T)tagObj;
	}
	
	/**
	 * 获取继承类的值
	 * @return
	 */
	public static String getSupEntityVal(UserEntity user,String key,boolean isAdd){
		String val = null;
		if(isAdd){	//新增
			if(SysConstant.CREATOR.equals(key)){
				val = user.getUserId().toString();
			}else if(SysConstant.CREATETIME.equals(key)){
				val = StringHandler.dateFormatToStr(StringHandler.DATE_TIME_FORMAT, new Date());
			}else if(SysConstant.DEPTID.equals(key)){
				val = (null == user.getIndeptId()) ? -1+"" :  user.getIndeptId().toString();
			}else if(SysConstant.ORGID.equals(key)){
				val = (null == user.getIncompId()) ? -1+"" :  user.getIncompId().toString();
			}else if(SysConstant.EMPID.equals(key)){
				val = (null == user.getInempId()) ? -1+"" :  user.getInempId().toString();
			}
		}else{//修改
			if(SysConstant.MODIFIER.equals(key)){
				val = user.getUserId().toString();
			}else if(SysConstant.MODIFYTIME.equals(key)){
				val = StringHandler.dateFormatToStr(StringHandler.DATE_TIME_FORMAT, new Date());
			}
		}
		return val;
	}
	
	/**
	 * 将一个对象(sourceObject)的值拷贝到另一个对象中(tagCls 的实例中)
	 * @param sourceObject 要拷贝的对象
	 * @param tagCls   目标类
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static<T> T copyValue(Object sourceObject,Class<T> tagCls){
		Class sCls = sourceObject.getClass();
		Field[] sFields = sCls.getDeclaredFields();
		Field[] tFields = tagCls.getDeclaredFields();
		try {
			List<String> sNames = getSameFieldsNames(getFieldsNames(sFields), getFieldsNames(tFields));
			Object tagObj = tagCls.newInstance();
			for(String fName : sNames){
				Field f1 = sCls.getDeclaredField(fName);
				Field f2 = tagCls.getDeclaredField(fName);
				Class fType1 = f1.getType();
				Class fType2 = f2.getType();
				if(fType1==fType2){
					f1.setAccessible(true);
					f2.setAccessible(true);
					Object value = f1.get(sourceObject);
					if(null != value){
						if(isSType(fType2)){
							f2.set(tagObj, value);
						}
					}
				}
			}	
			return (T)tagObj;
		}catch (UtilException e) {
			e.printStackTrace();
		}catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static final boolean isSType(Class fieldType){
		if(fieldType.isPrimitive()){
			return true;
		}else if(fieldType == Byte.class){
			return true;
		}else if(fieldType == Integer.class){
			return true;
		}else if(fieldType == Float.class){
			return true;
		}else if(fieldType == Double.class){
			return true;
		}else if(fieldType == Long.class){
			return true;
		}else if(fieldType == String.class){
			return true;
		}else if(fieldType == Date.class){
			return true;
		}else if(fieldType == BigDecimal.class){
			return true;
		}
		return false;
	}
	
	/**
	 * 将当前对象转换为JSON字符串
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final String getJSON(Object... objs){
		Object obj = null;
		Object baseId = null;
		if(null == objs) return "";
		obj = objs[0];
		if(objs.length==1){
			baseId = "";
		}else{
			baseId = "_"+objs[1];
		}
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		JSONObject jsonObj = new JSONObject();
		try {	
			for(Field fld : fields){
				Class type = fld.getType();
				if(isSType(type)){
					fld.setAccessible(true);
					String key = fld.getName();
					Object value = fld.get(obj);
					value = (null == value) ? "" : convertTheVal(type,value);
					System.out.println("key="+key+baseId);
					jsonObj.put(key+baseId, value);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return jsonObj.toString();
	}
	
	/**
	 * 将当前对象转换为JSON字符串,并将要追加的字符串
	 * 也加入到转换后的JSON字符串中，构成新的JSON字符串
	 * @param appendVal 要追加的 JSON 字符串值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final String getJSON(String appendVal,Object... objs){
		String jsonStr = getJSON(objs);
		jsonStr = jsonStr.substring(0,jsonStr.length()-1)+","+appendVal+"}";
		return jsonStr;
	}
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Object castTheVal(Class fieldType,String val,String... params){
		if(fieldType.isPrimitive()){
			return val;
		}else if(fieldType == Byte.class){
			return Byte.parseByte(val);
		}else if(fieldType == Integer.class){
			return Integer.parseInt(val);
		}else if(fieldType == Float.class){
			return Float.parseFloat(val);
		}else if(fieldType == Double.class){
			return Double.parseDouble(val);
		}else if(fieldType == Long.class){
			return Long.parseLong(val);
		}else if(fieldType == Date.class){
			String fmtStr = "yyyy-mm-dd";
			if(null != params && !"".equals(params)) fmtStr = params[0];
			return StringHandler.dateFormat(fmtStr,val);
		}else {
			return val;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Object castTheVal(Class fieldType,String val){
		if(fieldType.isPrimitive()){
			return val;
		}else if(fieldType == Byte.class){
			return Byte.parseByte(val);
		}else if(fieldType == Integer.class){
			return Integer.parseInt(val);
		}else if(fieldType == Float.class){
			return Float.parseFloat(val);
		}else if(fieldType == Double.class){
			return Double.parseDouble(val);
		}else if(fieldType == Long.class){
			return Long.parseLong(val);
		}else if(fieldType == Date.class){
			return StringHandler.dateFormat(DateUtil.DATE_FORMAT,val);
		}else if(fieldType == BigDecimal.class){
			return new BigDecimal(val);
		}else {
			return val.toString();
		}
	}
	
	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Object convertTheVal(Class fieldType,Object val){
		if(fieldType.isPrimitive()){
			return val;
		}else if(fieldType == Byte.class){
			return Byte.parseByte(val.toString());
		}else if(fieldType == Integer.class){
			return Integer.parseInt(val.toString());
		}else if(fieldType == Float.class){
			return Float.parseFloat(val.toString());
		}else if(fieldType == Double.class){
			return Double.parseDouble(val.toString());
		}else if(fieldType == Long.class){
			return Long.parseLong(val.toString());
		}else if(fieldType == Date.class){
			return StringHandler.dateFormatToStr("yyyy-MM-dd",(Date)val);
		}else {
			return val;
		}
	}
	
	/**
	 * 根据Fields 数组获取字段名
	 * @param fields
	 * @return
	 * @throws UtilException
	 */
	public static String[] getFieldsNames(Class<?> cls) throws UtilException{
		Map<String,Field> fmaps = GenericsUtil.getAllFNames(cls);
		Set<String> fnameSet = fmaps.keySet();
		String[] fNames = new String[fnameSet.size()];
		fNames = fnameSet.toArray(fNames);
		return fNames;
	}
	
	
	/**
	 * 根据Fields 数组获取字段名
	 * @param fields
	 * @return
	 * @throws UtilException
	 */
	public static String[] getFieldsNames(Field[] fields) throws UtilException{
		if(null == fields) throw new UtilException("Exception : To Do Reflect Fields Array, Array Fields is null !");
		String[] fNames = new String[fields.length]; 
		int k=0;
		for(Field f : fields){
			fNames[k] = f.getName();
			k++;
		}
		return fNames;
	}
	
	public static List<String> getSameFieldsNames(String[] FirstFNames,String[] TwoFNames){
		List<String> sameArr = new ArrayList<String>();
		for(String ffName : FirstFNames){
			for(String tfName : TwoFNames){
				if(ffName.equals(tfName)){
					sameArr.add(ffName);
				}
			}
		}
		return sameArr;
	}
	
	public static void main(String[] args){
		
		String keys = "amount#FLOGIC>=";
		int index = keys.indexOf("LOGIC");
		if(-1 != index){
			String logic = keys.substring(index);
			keys = keys.substring(0,index);
			System.out.println(logic+"	"+keys);
		}
	}

	
	/*------------------------- MY CODE END -----------------------------*/	
	
	 @SuppressWarnings("unchecked")
	public static <T> T copyValue(T sourceObject)   
	  {   
	    return (T) copyValue(sourceObject, sourceObject.getClass());   
	  }   
	  
//	  public static <T> T copyValue(Object sourceObject, Class<T> targetObjectClass)   
//	  {   
//	    return copyValue(sourceObject, targetObjectClass, null);   
//	  }   
	  
	  @SuppressWarnings("unchecked")
	public static <T> T copyValue(Object sourceObject, Class<T> targetObjectClass, HashMap classMap)   
	  {   
	    try  
	    {   
	      Object targetObject = targetObjectClass.newInstance();   
	  
	      return (T) copyValue(sourceObject, targetObject, classMap);   
	    }   
	    catch (Exception e)   
	    {   
	      return null;   
	    }   
	  }   
	  
	  @SuppressWarnings("unchecked")
	public static <T> T copyValue(Object sourceObject, Object targetObject, HashMap classMap)   
	  {   
	    Class sourceObjectClass = sourceObject.getClass();   
	    Class targetObjectClass = targetObject.getClass();   
	  
	    List<String> sourceClassFieldNameList = getClassFieldNameList(sourceObjectClass);   
	    List<String> targetClassFieldNameList = getClassFieldNameList(targetObjectClass);   
	    List<String> fieldNameList = getSameFieldNameList(sourceClassFieldNameList, targetClassFieldNameList);   
	  
	    try  
	    {   
	      for (String fieldName : fieldNameList)   
	      {   
	        Field sourceField = sourceObjectClass.getDeclaredField(fieldName);   
	        Field targetField = targetObjectClass.getDeclaredField(fieldName);   
	  
	        Class sourceFieldType = sourceField.getType();   
	        Class targetFieldType = targetField.getType();   
	  
	        if (sourceFieldType == targetFieldType)   
	        {   
	          sourceField.setAccessible(true);   
	          targetField.setAccessible(true);   
	  
	          Object sourceValue = sourceField.get(sourceObject);   
	  
	          if (sourceValue != null)   
	          {   
	            if (isSimpleType(sourceFieldType))   
	            {   
	              targetField.set(targetObject, sourceValue);   
	            }   
	            else  
	            {   
	              Object copiedObject = copyComplicatedType(sourceValue, classMap);   
	  
	              targetField.set(targetObject, copiedObject);   
	            }   
	          }   
	        }   
	      }   
	  
	      return (T) targetObject;   
	    }   
	    catch (Exception e)   
	    {   
	      return null;   
	    }   
	  }   
	  
	  @SuppressWarnings("unchecked")
	private static List<String> getClassFieldNameList(Class cla)   
	  {   
	    Field[] fields = cla.getDeclaredFields();   
	    List<String> fieldNameList = new ArrayList();   
	  
	    for (int i = 0; i < fields.length; i++)   
	    {   
	      Field field = fields[i];   
	      String fieldName = field.getName();   
	  
	      fieldNameList.add(fieldName);   
	    }   
	  
	    return fieldNameList;   
	  }   
	  
	  @SuppressWarnings("unchecked")
	private static List<String> getSameFieldNameList(List<String> sourceList, List<String> targetList)   
	  {   
	    List<String> resultList = new ArrayList();   
	  
	    for (String source : sourceList)   
	    {   
	      for (String target : targetList)   
	      {   
	        if (source.equals(target))   
	        {   
	          resultList.add(source);   
	        }   
	      }   
	    }   
	  
	    return resultList;   
	  }   
	  
	  @SuppressWarnings("unchecked")
	private static boolean isSimpleType(Class cla)   
	  {   
	    if (cla.isPrimitive())   
	    {   
	      return true;   
	    }   
	    if (cla == Date.class)   
	    {   
	      return true;   
	    }   
	  
	    return false;   
	  }   
	  
	  @SuppressWarnings("unchecked")
	private static Object copyComplicatedType(Object sourceObject, HashMap classMap)   
	  {   
//	    System.out.println("========= copyComplicatedType =========");   
	    Class sourceClass = sourceObject.getClass();   
	  
	    if (sourceClass.isArray())   
	    {   
	      return copyArray(sourceObject, classMap);   
	    }   
	    else if (sourceObject instanceof List)   
	    {   
	      return copyList(sourceObject, classMap);   
	    }   
	    else if (sourceObject instanceof Map)   
	    {   
	      return copyMap(sourceObject, classMap);   
	    }   
	  
	    return copySubObject(sourceObject, classMap);   
	  }   
	  
	  @SuppressWarnings("unchecked")
	private static Object copySubObject(Object subSourceObject, HashMap classMap)   
	  {   
//	    System.out.println("========= copySubobject =========");   
	    Object mapClassObject = classMap.get(subSourceObject.getClass());   
	  
	    if (mapClassObject != null)   
	    {   
	      Class mapClass = (Class) mapClassObject;   
	  
	      Object subTargetObject = copyValue(subSourceObject, mapClass, classMap);   
	  
	      return subTargetObject;   
	    }   
	  
	    return subSourceObject;   
	  }   
	  
	  @SuppressWarnings("unchecked")
	private static Object copyArray(Object sourceObjectArray, HashMap classMap)   
	  {   
//	    System.out.println("========= copyArray =========");   
	    int arraySize = Array.getLength(sourceObjectArray);   
	    if (arraySize > 0)   
	    {   
	      Object testObject = Array.get(sourceObjectArray, 0);   
	      Object targetObjectArray = Array.newInstance(testObject.getClass(), arraySize);   
	  
	      for (int i = 0; i < arraySize; i++)   
	      {   
	        Object subSourceObject = Array.get(sourceObjectArray, i);   
	        Object subTargetObject = copySubObject(subSourceObject, classMap);   
	  
	        Array.set(targetObjectArray, i, subTargetObject);   
	      }   
	    }   
	  
	    return sourceObjectArray;   
	  }   
	  
	  @SuppressWarnings("unchecked")
	private static Object copyMap(Object sourceObject, HashMap classMap)   
	  {   
//	    System.out.println("========= copyMap =========");   
	    try  
	    {   
	      Class sourceObjectClass = sourceObject.getClass();   
	      Map targetObjectMap = (Map) sourceObjectClass.newInstance();   
	      Map sourceObjectMap = (Map) sourceObject;   
	  
	      Set set = sourceObjectMap.entrySet();   
	      Iterator<Entry> iterator = set.iterator();   
	      while (iterator.hasNext())   
	      {   
	        Entry sourceEntry = iterator.next();   
	  
	        Object subSourceObject = sourceEntry.getValue();   
	        Object subTargetObject = copySubObject(subSourceObject, classMap);   
	  
	        targetObjectMap.put(sourceEntry.getKey(), subTargetObject);   
	      }   
	  
	      return targetObjectMap;   
	    }   
	    catch (Exception e)   
	    {   
	      e.printStackTrace();   
	    }   
	  
	    return sourceObject;   
	  }   
	  
	  @SuppressWarnings("unchecked")
	private static Object copyList(Object sourceObject, HashMap classMap)   
	  {   
//	    System.out.println("========= copyList =========");   
	    try  
	    {   
	      Class sourceObjectClass = sourceObject.getClass();   
	      List targetObjectList = (List) sourceObjectClass.newInstance();   
	      List sourceObjectList = (List) sourceObject;   
	  
	      for (Object subObject : sourceObjectList)   
	      {   
	        Object subTargetObject = copySubObject(subObject, classMap);   
	  
	        targetObjectList.add(subTargetObject);   
	      }   
	  
	      return targetObjectList;   
	    }   
	    catch (Exception e)   
	    {   
	      e.printStackTrace();   
	    }   
	  
	    return sourceObject;   
	  }   

	  /**
			 * @作用:通过对象,方法名称以及参数 来 调用方法
			 * @dateTime: May 29, 2009
			 * @param owner
			 * @param methodName
			 * @param args
			 * @return
			 * @throws Exception
			 */
			@SuppressWarnings("unchecked")
			public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {
				Class ownerClass = owner.getClass();
				Class[] argsClass = new Class[args.length];
				int i = 0;
				for (int j = args.length; i < j; ++i) {
					argsClass[i] = args[i].getClass();
				}

				Method method = ownerClass.getDeclaredMethod(methodName, argsClass);
				method.setAccessible(true);
				return method.invoke(owner, args);
			}
			
			/**
			 * @作用:通过对象,方法名称以及参数 来 调用方法
			 * @dateTime: May 29, 2009
			 * @param owner
			 * @param methodName
			 * @param args
			 * @return
			 * @throws Exception
			 */
			@SuppressWarnings("unchecked")
			public static Object invokeMethod(Object owner, String methodName) throws Exception {
				Class ownerClass = owner.getClass();
				Method method = ownerClass.getDeclaredMethod(methodName, null);
				method.setAccessible(true);
				return method.invoke(owner, null);
			}

			/**
			 * @作用:调用静态方法
			 * @dateTime: May 29, 2009
			 * @param className
			 * @param methodName
			 * @param args
			 * @return
			 * @throws Exception
			 */
			@SuppressWarnings("unchecked")
			public static Object invokeStaticMethod(String className, String methodName, Object[] args) throws Exception {
				Class ownerClass = Class.forName(className);
				Class[] argsClass = new Class[args.length];
				int i = 0;
				for (int j = args.length; i < j; ++i)
					argsClass[i] = args[i].getClass();

				Method method = ownerClass.getMethod(methodName, argsClass);
				method.setAccessible(true);
				return method.invoke(null, args);
			}
	
	
	/**
	 * 设置创建用户信息
	 * @param user
	 * @param entity
	 */
	public static void setCreateInfo(UserEntity user, IdBaseEntity entity){
		Long userId = -1l;
		Long indempId = -1l;
		Long indeptId = -1l;
		Long incompId = -1l;
		Integer isSystem = null;
		if(null != user){
			userId = user.getUserId();
			indempId = user.getInempId();
			indeptId = user.getIndeptId();
			incompId = user.getIncompId();
			isSystem = user.getIsSystem();
		}
		entity.setCreateTime(new Date());
		entity.setCreator(userId);
		entity.setEmpId(indempId);
		if(null != isSystem && isSystem.intValue() == UserEntity.ISSYSTEM_1){/*超管*/
			if(null == indeptId) indeptId = SysConstant.ADMIN_ID;
			if(null == incompId) incompId = SysConstant.ADMIN_ID;
		}
		entity.setDeptId(indeptId);
		entity.setOrgid(incompId);
	}
	
	/**
	 * 设置修改用户信息
	 * @param user
	 * @param entity
	 */
	public static void setModifyInfo(UserEntity user, BaseEntity entity){
		Long userId = -1l;
		if(null != user) userId = user.getUserId();
		entity.setModifier(userId);
		entity.setModifytime(new Date());
	}
}
