package com.cmw.core.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import com.cmw.constant.SysConstant;
import com.cmw.entity.sys.UserEntity;


/**
 * SQL 条件并装类
 * @author chengmingwei
 *
 */
public class SqlUtil {
	/**
	 * 日志输出对象
	 */
	private static Logger log = Logger.getLogger(SqlUtil.class);
	/**
	 * SQL 或 HQL 中的列替换标记
	 */
	public static final String CMN_SIGINS = "QUERY_CMN_SIGINS";
	/**
	 * 排序KEY标识
	 */
	public static final String ORDERBY_KEY = "ORDERBY_CMNS_KEY";
	/**
	 * 是否需要获取总记录数 KEY标识
	 */
	public static final String TOTALCOUNT_KEY = "TOTALCOUNT_KEY";
	/**
	 * 需要获取总记录数 标识  [TOTALCOUNT_TRUE:true]
	 */
	public static final boolean TOTALCOUNT_TRUE = true;
	/**
	 * 需要获取总记录数 标识  [TOTALCOUNT_FALSE:false]
	 */
	public static final boolean TOTALCOUNT_FALSE = false;
	
	public static final String LOGIC = "LOGIC";
	/**
	 * = 逻辑运算符
	 */
	public static final String LOGIC_EQ = " = ";
	
	/**
	 * != 逻辑运算符
	 */
	public static final String LOGIC_NOT_EQ = " <> ";
	/**
	 * like 逻辑运算符
	 */
	public static final String LOGIC_LIKE = " LIKE ";
	
	/**
	 * not like 逻辑运算符
	 */
	public static final String LOGIC_NOT_LIKE = " NOT LIKE ";
	
	/**
	 * in 逻辑运算符
	 */
	public static final String LOGIC_IN = " IN ";
	
	/**
	 * not in 逻辑运算符
	 */
	public static final String LOGIC_NOT_IN = " NOT IN ";
	
	/**
	 * >(大于) 逻辑运算符
	 */
	public static final String LOGIC_GT = ">";
	/**
	 * >=(大于等于) 逻辑运算符
	 */
	public static final String LOGIC_GTEQ = ">=";
	/**
	 * <(小于等于) 逻辑运算符
	 */
	public static final String LOGIC_LTEQ = "<=";
	
	
	/**
	 * 构建where 条件语句子句
	 * @param orderBy	属性名为 ASC/DESC 形式的 LinkedHashMap 对象
	 * @return
	 */
	public static <K,V> String buildWhereStr(String objectName,SHashMap<K, V> map){
		return buildWhereStr(objectName,map,true);
	}
	
	//SHashMap<K, V> map,
	/**
	 * 获取用户权限控制条件
	 * @param map	含有当前用户的 SHashMap 对象
	 * @param tabOtherName 表别名
	 * @param isHql 是否HQL  true : 是 , false : SQL查询
	 * @return
	 */
	public static <K,V> String getRightFilter(SHashMap<K, V> map,String tabOtherName){
		UserEntity user =  (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
		if(null == user) return null;
		return getRightFilter(user, tabOtherName);
	}
	
	/**
	 * 获取用户权限控制条件
	 * @param user	当前用户
	 * @param tabOtherName 表别名
	 * @return
	 */
	public static String getRightFilter(UserEntity user,String tabOtherName){
		Integer isSystem = user.getIsSystem();
		if(null != isSystem && isSystem.intValue() == 1) return null;	//系统用户不加过滤条件
		StringBuffer sb = new StringBuffer();
		String accessIds = user.getAccessIds();
		Byte dataLevel = user.getDataLevel();
		switch (dataLevel) {
			case SysConstant.DATA_LEVEL_NO:{/*无数据访问权限*/
				sb.append(tabOtherName+".creator=-1 ");
				break;
			}case SysConstant.DATA_LEVEL_SELF:{
				sb.append(tabOtherName+".creator='"+user.getUserId()+"' ");
				break;
			}case SysConstant.DATA_LEVEL_CUSTUSER:{
				if(!StringHandler.isValidStr(accessIds)) accessIds = "-1";
				sb.append(tabOtherName+".creator in ("+accessIds+") ");
				break;
			}case SysConstant.DATA_LEVEL_CURRDEPT:{
				Long indeptId = user.getIndeptId();
				sb.append(tabOtherName+".deptId ='"+indeptId+"' ");
				break;
			}case SysConstant.DATA_LEVEL_CURR_CHILDS_DEPT:
			case  SysConstant.DATA_LEVEL_CUSTOMDEPT:{
				if(!StringHandler.isValidStr(accessIds)) accessIds = "-1";
				sb.append(tabOtherName+".deptId in ("+accessIds+") ");
				break;
			}case SysConstant.DATA_LEVEL_CURRCOMPANY:{	//本公司数据
				Long org =  user.getOrgid();
				String orgId = null;
				if(org==-1){
					orgId = org.toString()+",1";
				}else{
					orgId = org.toString()+",-1";
				}
				sb.append(tabOtherName+".orgid in ("+orgId+") ");
				break;
			}case SysConstant.DATA_LEVEL_CUSTCOMPANY:{	//自定义公司数据
				if(!StringHandler.isValidStr(accessIds)) accessIds = "-1";
				sb.append(tabOtherName+".orgid in ("+accessIds+") ");
				break;
			}default:{
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 替换 SQL 查询语句中的 一个单引号为两个单引号，以防止非法注入
	 * 例如：StringHandler.replaceSqlSign("'王兰'") ----> ''王兰''
	 * @param content
	 * @return
	 */
	public static String replaceSqlSign(String content){
		return (null != content) ? content = content.replaceAll(".*([';]+|(--)+).*", "")  : "";
	}
	
	/**
	 * 获取一个以防止非法注入的  map 对象
	 * @param map 查询条件参数  SHashMap 对象
	 * @return 返回    SHashMap 对象
	 */
	@SuppressWarnings("unchecked")
	public static <K,V> SHashMap<K, V> getSafeWhereMap(SHashMap<K, V> map){
		Set<K> keys = map.keySet();
		for(K key : keys){
			String simpleName = key.getClass().getSimpleName();
			if(!simpleName.equals("String")) continue;
			V val = map.get(key);
			if(null == val || !val.getClass().getSimpleName().equals("String")) continue;
			String valStr = (String)val;
			if(!StringHandler.isValidStr(valStr)) continue;
			if((valStr.indexOf(SqlUtil.LOGIC_IN) != -1) || (valStr.indexOf(SqlUtil.LOGIC_NOT_IN) != -1)) continue;
			map.put(key, (V)(replaceSqlSign(valStr)));
		}
		return map;
	}
	
	/**
	 * 获取排序的列字符串， map对象中须是如下格式: map.put(SqlUtil.ORDERBY_KEY,"asc:id,creator;desc:createTime,empId");
	 * @param objectName
	 * @param map 
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public static <K,V> String buildOrderByStr(String objectName,SHashMap<K, V> map){
		StringBuffer orderbySb = new StringBuffer();
		String orderByParas = map.getvalAsStr(ORDERBY_KEY);
		map.remove((K)ORDERBY_KEY);
		if(!StringHandler.isValidStr(orderByParas)) return null;
		String[] orderArr =  orderByParas.split(";");
		if(null == orderArr || orderArr.length == 0) return null;
		for(String orderGp : orderArr){
			StringBuffer sb = new StringBuffer();
			String[] orderSgp = orderGp.split(":");
			if(null == orderSgp || orderSgp.length <= 1) continue;
			String orderKeyWord = orderSgp[0];
			String[] orderCmns = orderSgp[1].split(",");
			for(String cmn : orderCmns){
				if(cmn.indexOf(".") == -1){
					cmn = objectName +"."+ cmn;
				}
				sb.append(cmn+",");
			}
			String str = StringHandler.RemoveStr(sb);
			orderbySb.append(str).append(" ").append(orderKeyWord).append(",");
		}
		return StringHandler.RemoveStr(orderbySb);
	}
	
	/**
	 * 构建where 条件语句子句
	 * @param orderBy	属性名为 ASC/DESC 形式的 LinkedHashMap 对象
	 * @return
	 */
	public static <K,V> String buildWhereStr(String objectName,SHashMap<K, V> map,boolean hasWhereKeyWords) 
	{
		log.debug("SqlWhereUtil.buildWhere start....");
		StringBuffer sb = new StringBuffer();
		map = getSafeWhereMap(map);
		if(null != map && map.size()>0) 
		{
			Set<K> whereSet = map.keySet();
			String whereStr = hasWhereKeyWords ? " where " : " and ";
			sb.append(whereStr);
			int size = whereSet.size();
			int offset = 0;
			UserEntity user = (UserEntity)map.getvalAsObj(SysConstant.USER_KEY);
			for(K key : whereSet)
			{
				offset++;
				if(null == key || key.equals("")) continue;
				Object val = map.get(key);
				if(null != key && key.toString().equals(SysConstant.USER_KEY)) continue;
				if(null == val || "".equals(val)) continue;
				if(key.toString().indexOf(".")==-1){
					sb.append(objectName+"."+key);
				}else{
					sb.append(key);
				}
				String tempVal = val.toString();
				String logic = " like ";
				String[] valArr = StringHandler.splitStrToArr(tempVal, LOGIC);
				boolean isLogic = false; //是否提供了比较符号
				if(valArr.length==2){
					isLogic = true;
					logic = valArr[0];
					val = valArr[1];
				}
				if(null == val || "".equals(val)) continue;
				if(isValidType(val,"Integer") || isValidType(val,"Byte")|| isValidType(val,"Long")){
					if(!isLogic && logic.equals(" like ")) logic = " = ";
					sb.append(" "+logic+" "+val);
				}else if(isValidType(val, "Date")){
					val = StringHandler.dateFormatToStr(null,(Date)val);
					if(!isLogic && logic.equals(" like ")) logic = ">=";
					sb.append(" "+logic+"'"+val+"'");
				}else{
					if(isLogic){
						if(logic.equals(LOGIC_IN) || logic.equals(LOGIC_NOT_IN)){
							sb.append(" "+logic+" ("+val+") ");
						}else{
							sb.append(" "+logic+"'"+val+"'");
						}
					}else{
						sb.append(" "+logic+"'"+val+"%'");
					}
				}
				//sb.append(" and ");
				if(offset<size){
					sb.append(" and ");
				}
			}
			if(sb.length()>0){
				String temp = sb.toString().trim();
				int start = temp.lastIndexOf("where");
				if(start+"where".length()==temp.length()){
					sb = sb.delete(start,sb.length());
				}
				start = temp.lastIndexOf("and");
				if(start+"and".length()==temp.length()){
					sb = sb.delete(start,sb.length());
				}
			}
			
			if((null != sb && sb.length() > 0) && null != user){
				String str = getRightFilter(user,objectName);
				if(StringHandler.isValidStr(str)) sb.append(" and "+str);
			}
			log.info("SqlWhereUtil.buildWhere success ....");
		}
		return sb.toString();
	}
	
	/**
	 * 追加用户过滤参数
	 * @param objectName
	 * @param user
	 * @return
	 */
	public static String appendUserFilter(String objectName,UserEntity user){
		StringBuffer sb = new StringBuffer(" and ");
		Integer isSystem = user.getIsSystem();
		if(null != isSystem && isSystem.intValue() == 1) return null;	//系统用户不加过滤条件
		Byte dataLevel = user.getDataLevel();
		switch (dataLevel) {
		case 0:{
			Long creator = user.getUserId();
			if(null == creator) creator = SysConstant.NOEXIST_ID;
			sb.append(objectName+".creator="+creator);
			break;
		}
		case 1://本部门，本部门及所有子部门，自定义部门
		case 2:
		case 3:{
			String accessUsers = user.getAccessIds();
			if(!StringHandler.isValidStr(accessUsers)) accessUsers = SysConstant.NOEXIST_ID+"";
			sb.append(objectName+".creator in ("+accessUsers+")");
			break;
		}
		case 4:{	//本公司数据
			Long orgid = user.getOrgid();
			if(null == orgid) orgid = SysConstant.NOEXIST_ID;
			sb.append(objectName+".orgid="+orgid);
			break;
		}default:
			break;
		}
		return sb.toString();
	}
	/**
	 * 判断对象是否是指定的类型
	 * @param obj	要判断的对象
	 * @param typeName	类型
	 * @return
	 */
	public static boolean isValidType(Object obj,String typeName){
		String simpleName = obj.getClass().getSimpleName();
		return (simpleName.indexOf(typeName) == -1) ? false : true;
	}
	/**
	 * 构建排序语句子句
	 * @param orderBy	属性名为 ASC/DESC 形式的 LinkedHashMap 对象
	 * @return
	 */
	public static String buildOrderBy(LinkedHashMap<String, String> orderBy)
	{
		log.debug("SqlWhereUtil.bbuildOrderBy start....");
		StringBuffer sb = new StringBuffer();
		if(null != orderBy && orderBy.size() > 0)
		{
			sb.append(" order by ");
			for(String key : orderBy.keySet())
			{
				sb.append(" o."+key+" "+orderBy.get(key)+",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		log.debug("SqlWhereUtil.buildOrderBy success ....");
			return sb.toString();
	}
	
	/**
	 * 给参数赋值
	 * @param <K>
	 * @param <V>
	 * @param query
	 * @param map
	 */
	public static <K,V> void setWhereValue(Query query,SHashMap<K, V> map)
	{
		log.debug("SqlWhereUtil.setWhereValue start....");
		if(null != map && map.keySet().size() >0)
		{
			for(K key : map.keySet())
			{
				System.out.println((String) key+ " :"+map.get(key));
				map.setParameterValue(query, key);
			}
		}
		log.debug("SqlWhereUtil.setWhereValue success ....");
	}
	
	/**
	 * 返回一个 Where 条件表达式 
	 * @param <K>
	 * @param <V>
	 * @param map	过滤参数
	 * @return
	 */
	public static  <K,V> WhereCondition setWhereValue(String objectName,SHashMap<K, V> map){
		StringBuffer sb = new StringBuffer();
		if(null == map || map.size() == 0) return null;
		Set<K> keys = map.keySet();
		List<Object> listVal = new ArrayList<Object>();
		for(K key : keys){
			String filed = (String)key;
			Object val = map.get(key);
			if(SysConstant.USER_KEY.equals(filed)){	//如果有传入当前用户，则根据用户过滤
				UserEntity user = (UserEntity)map.get(key);
				if(null == user)  continue;
				String whereStr = appendUserFilter(objectName, user);
				if(StringHandler.isValidStr(whereStr)){
					sb.append(whereStr);
				}
			}else{
				if(!StringHandler.isValidStr(filed) || !StringHandler.isValidObj(val)) continue;
				sb.append(" and "+objectName+"."+filed +" =:"+filed);
				listVal.add(val);
			}
		}
		SqlUtil.WhereCondition condition =  new WhereCondition();
		condition.make(sb.toString(), map);
		return condition;
	}
	
	/**
	 * 条件表达式封装类
	 * @author chengmingwei
	 *
	 */
	public static class WhereCondition{
		public String whereStr;	//放 HQL where 条件字符串
		public Object[] values;	//与  where 对应的参数值
		public SHashMap<String, Object> map;
		public void make(String whereStr,List<Object> listVal){
			this.whereStr = whereStr;
			values = new Object[1];
			values = listVal.toArray(values);
		}
		
		@SuppressWarnings("unchecked")
		public  <K,V> void make(String whereStr, SHashMap<K, V> map){
			this.whereStr = whereStr;
			this.map = (SHashMap<String, Object>) map;
		}
	}
}
