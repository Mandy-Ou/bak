package com.cmw.core.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cmw.entity.sys.MenuEntity;

/**
 * 
 * @author cmw_1984122
 *
 */
public class GenericsUtil {
	 /** 
     * 通过反射，获得定义Class时声明的父类的第一个范型参数的类型。  
     */  
	@SuppressWarnings("unchecked")
	public static Class getSupClassGenericType(Class clzz){
		return getSupClassGenericType(clzz,0);
	}
	  /** 
     * 通过反射，获得定义Class时声明的父类的范型参数的类型。  
     * 如没有找到符合要求的范型参数，则递归向上直到Object。  
     *  
     * @param clazz 要进行查询的类  
     * @param index 如有多个范型声明该索引从0开始  
     * @return 在index位置的范型参数的类型，如果无法判断则返回<code>Object.class</code>  
     */  
	@SuppressWarnings("unchecked")
	public static Class getSupClassGenericType(Class clzz,int index){
		Type genType = clzz.getGenericSuperclass();
		Type[] types = null;
		boolean flag = true;
		if(!(genType instanceof ParameterizedType)) flag = false;
		if(flag){
			ParameterizedType ptype = (ParameterizedType)genType;
			types = ptype.getActualTypeArguments();
		}
		if(null != types){
			return (Class) types[index];
		}else{
			return (Class)genType;
		}
	}
	
	/**
	 * 获取指定类的所有父类
	 * @param cls 要获取父类的子类
	 * @return 如果存在多个父类就以数组形式返回。不存在父类返回空
	 */
	@SuppressWarnings("unchecked")
	public static Class[] getSupClasses(Class cls){
		List<Class> list = new ArrayList<Class>();
		addSupCls(cls,list);
		if(null == list || list.size() == 0) return null;
		Class[] clses = new Class[list.size()];
		clses = list.toArray(clses);
		return clses;
	}
	
	@SuppressWarnings("unchecked")
	public static void addSupCls(Class cls,List<Class> list){
		Class supCls = cls.getSuperclass();
		if(null == supCls) return;
		list.add(supCls);
		addSupCls(supCls, list);
	}
	
	
	/**
	 * 获取目标类和所有父类的 Field 的 并能Map 返回
	 * Map 格式 key : field Name ,value : field 
	 * @param tagCls 
	 * @return 以Map 对象返回所有Field 对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> Map<String , Field> getAllFNames(Class<T> tagCls){
		Class[] supClses = getSupClasses(tagCls);
		Map<String,Field> map = new HashMap<String, Field>();
		Field[] flds = tagCls.getDeclaredFields();
		if(null != flds && flds.length > 0){
			for(Field fld : flds){
				map.put(fld.getName(), fld);
			}
		}
		if(null != supClses && supClses.length > 0){
			for(Class cls : supClses){
				 flds = cls.getDeclaredFields();
				if(null != flds && flds.length > 0){
					for(Field fld : flds){
						map.put(fld.getName(), fld);
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 判断指定的字段在实体类中是否存在，存在就返回 true ,否则，为 false
	 * @param <T>
	 * @param tagCls	要查找的目标实体类
	 * @param fieldName	要比较的字段
	 * @return   返回 boolean 值， 
	 */
	public static <T> boolean hasTheField(Class<T> tagCls,String fieldName){
		Map<String , Field> fields = getAllFNames(tagCls);
		Set<String> keys = fields.keySet();
		for(String key : keys){
			if(fieldName.equals(key)) return true;
		}
		return false;
	}
	
	/**
	 * 获取指定类的所有父类
	 * @param cls 要获取父类的子类
	 * @return 如果存在多个父类就以数组形式返回。不存在父类返回空
	 */
	@SuppressWarnings("unchecked")
	public static Class getSupClass(Class cls){
		Class[] clses = getSupClasses(cls);
		return null == clses ? null : clses[0];
	}
	
	/**
	 * 获取指定类的主键字符串名称
	 * @param cls 要获取主键名称的类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getPkName(Class cls){
		Map<String,Field> map = getAllFNames(cls);
		String pkName = "";
		Set<String> keys = map.keySet();
		for(String key : keys){
			Field field = map.get(key);
			Annotation[] annos = field.getAnnotations();
			for(Annotation anno : annos){
				String simpleName = anno.annotationType().getSimpleName();
				if(simpleName.equals("Id")){	//如果注解标有ID，就是主键
					return key;
				}
			}
		}
		return pkName;
	}
	
	/**
	 * 获取指定类的主键字符串名称
	 * @param cls 要获取主键名称的类
	 * @return
	 */
	public static Map<String,Object> getPkName(Object object){
		Class<? extends Object> cls = object.getClass();
		Map<String,Field> map = getAllFNames(cls);
		Map<String,Object> pkMap = new HashMap<String, Object>();
		Set<String> keys = map.keySet();
		for(String key : keys){
			Field field = map.get(key);
			Annotation[] annos = field.getAnnotations();
			for(Annotation anno : annos){
				String simpleName = anno.annotationType().getSimpleName();
				if(simpleName.equals("Id")){	//如果注解标有ID，就是主键
					try {
						field.setAccessible(true);
						pkMap.put(key, field.get(object));
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
					return pkMap;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args){
		String pkName = getPkName(MenuEntity.class);
		System.out.println(pkName);
	}
}
