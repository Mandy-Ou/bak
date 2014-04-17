package com.cmw.constant;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.FastJsonUtil;
import com.cmw.core.util.GenericsUtil;
import com.cmw.core.util.StringHandler;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 消息处理常量类
 * @author chengmingwei
 *
 */
public class ResultMsg {
	
	/**
	 * 系统错误
	 */
	public static final String SYSTEM_ERROR = "system.error";
	/**
	 * 登录成功
	 */
	public static final String LOGIN_SUCCESS = "login.success";
	/**
	 * 登录失败
	 */
	public static final String LOGIN_FAILURE = "login.failure";
	/**
	 * 查询失败
	 */
	public static final String QUERY_FAILURE = "query.failure";
	/**
	 * 保存成功
	 */
	public static final String SAVE_SUCCESS = "save.success";
	/**
	 * 保存失败
	 */
	public static final String SAVE_FAILURE = "save.failure";
	/**
	 * 启用成功  : enabled.success
	 */
	public static final String ENABLED_SUCCESS = "enabled.success";
	/**
	 * 禁用成功  : disabled.success
	 */
	public static final String DISABLED_SUCCESS = "disabled.success";
	
	/**
	 * 删除成功
	 */
	public static final String DELETE_SUCCESS = "delete.success";
	/**
	 * 删除失败
	 */
	public static final String DELETE_FAILURE = "delete.failure";
	/**
	 * 删除的数据存在关联的设置不能进行删除！
	 */
	public static final String DELETE_Link_Not = "DELETE.Link.Not";
	/**
	 * 修改成功
	 */
	public static final String MODIFY_SUCCESS = "modify.success";
	/**
	 * 用户名已存在
	 */
	public static final String USERNAME_HAVE = "username.have";
	/**
	 * 用户名不存在
	 */
	public static final String ERROR_USERNAME_NOHAVE = "error.username.nohave";
	/**
	 * 此登录账号只能使用指定计算机登录
	 */
	public static final String ERROR_COMPUTERNAME = "ERROR_COMPUTERNAME";
	/**
	 * 此登录账号只能在指定Ip段登录
	 */
	public static final String ERROR_IPLIMIT= "ERROR_IPLIMIT";
	/**
	 * 密码错误
	 */
	public static final String ERROR_PASSWORD = "error.password";
	/**
	 * 验证码错误
	 */
	public static final String ERROR_VALID_CODE = "error.valid.code";
	/**
	 * 没有查询到任何数据,并标识 success=true 以成功形式返回客户端
	 */
	public static final String NODATA = "{list:[],totalSize:0,success:true}";
	
	/**
	 * 没有查询到任何Grid数据
	 */
	public static final String GRID_NODATA = "{list:[],totalSize:0,success:true}";
	/**
	 * 用户会话过期
	 */
	public static final String USERSESSION_TIMEOUT = "{\"msg\":\"user.session.timeout.break\",\"success\":false}";
	/**
	 * 上一条下一条游标标识KEY
	 */
	public static final String CURSOR_KEY = "cursor$data";
	
	/**
	 * 游标 已经是第一条的标识	
	 */
	public static final String CURSOR_FIRST = "FIRST";
	/**
	 * 游标 已经是最后一条的标识
	 */
	public static final String CURSOR_LAST = "LAST";
	/**
	 * 成功标识码
	 */
	public static final boolean SUCCESS_STATE = true;
	/**
	 * 失败标识码
	 */
	public static final boolean FAILURE_STATE = false;
	/**
	 * 获取数据响应成功后的JSON消息字符串
	 * @param msg 成功后的消息字符串
	 * @return 返回JSON字符串{success:true,msg:'传入的msg'}
	 */
	public static final String getSuccessMsg(String msg){
		JSONObject obj = createJsonObject(SUCCESS_STATE,msg);
		return obj.toString();
	}
	
	/**
	 * 获取数据响应成功后的JSON消息字符串
	 * @param appendObj JSONObject 对象
	 * @return 返回JSON字符串{success:true,appendObj中的值}
	 */
	public static final String getSuccessMsg(JSONObject appendObj){
		JSONObject obj = createJsonObject(SUCCESS_STATE,null);
		obj.putAll(appendObj);
		return obj.toString();
	}
	
	
	/**
	 * 返回指定对象和参数转换成功后的 JSON 字符串
	 * @param object 要转换为 JSON字符串 的对象
	 * @param appendParams 要追加到JSON中的数据
	 * @return 返回转换成功后的JSON字符串
	 */
	public static final String getSuccessMsg(Object object,	Map<String,Object> appendParams){
		return getSuccessMsg(object,appendParams,false);
	}
	
	public static final String [] SUPER_ENTITY_FIELDS = {"isenabled","creator","createTime","deptId","orgid","modifier","modifytime"};
	
	public static final String FIELDS_KEY = "fields";
	
	public static final String DATAS_KEY = "datas";
	
	/**
	 * 返回已经是第一条的 JSON 字符串
	 * @param appendParams 要追加到JSON中的数据
	 * @return 返回转换成功后的JSON字符串
	 */
	public static final String getFirstMsg(Map<String,Object> appendParams){
		JSONObject obj = new JSONObject();
		obj.putAll(appendParams);
		obj.put(CURSOR_KEY, CURSOR_FIRST);
		obj.put("success", "true");
		return obj.toString();
	}
	
	
	/**
	 * 返回已经是最后一条的 JSON 字符串
	 * @param appendParams 要追加到JSON中的数据
	 * @return 返回转换成功后的JSON字符串
	 */
	public static final String getLastMsg(Map<String,Object> appendParams){
		JSONObject obj = new JSONObject();
		obj.putAll(appendParams);
		obj.put(CURSOR_KEY, CURSOR_LAST);
		obj.put("success", "true");
		return obj.toString();
	}
	
	/**
	 * 返回指定对象和参数转换成功后的 JSON 字符串
	 * @param appendParams 要追加到JSON中的数据
	 * @return 返回转换成功后的JSON字符串
	 */
	public static final String getSuccessMsg(Map<String,Object> appendParams){
		JSONObject obj = new JSONObject();
		obj.putAll(appendParams);
		obj.put("success", "true");
		return obj.toString();
	}
	
	/**
	 * 返回指定对象和参数转换成功后的 JSON 字符串
	 * @param object 要转换为 JSON字符串 的对象
	 * @param appendParams 要追加到JSON中的数据
	 * @return 返回转换成功后的JSON字符串
	 */
	public static final String getSuccessMsg(Object object,	Map<String,Object> appendParams,boolean isSuper){
		JSONObject obj = getJsonObect(object, appendParams, isSuper);
		return obj.toString();
	}
	
	/**
	 * 将指定的对象和参数转换成 JSON 对象
	 * @param object 要转换的对象
	 * @param appendParams	要追加到 JSON 对象中的附加值
	 * @param isSuper	是否要移除 BaseEntity 父类中定义的对象 <br/>
	 * 	  true : 移除 ["isenabled","creator","createTime","deptId","orgid","modifier","modifytime"] 下列字段
	 *    false : 不移除 
	 * @return 返回JSONObject 对象
	 */
	public static JSONObject getJsonObect(Object object,Map<String, Object> appendParams, boolean isSuper) {
		JSONObject obj =FastJsonUtil.convertObjToJsonObj(object, appendParams);
		if(isSuper){
			for(String key : SUPER_ENTITY_FIELDS){
				obj.remove(key);
			}
		}else{
			Date createTime = (Date)obj.get(SUPER_ENTITY_FIELDS[2]);
			if(null != createTime){
				obj.put(SUPER_ENTITY_FIELDS[2], DateUtil.dateFormatToStr(createTime));
			}
			
			Date modifytime = (Date)obj.get(SUPER_ENTITY_FIELDS[6]);
			if(null != modifytime){
				obj.put(SUPER_ENTITY_FIELDS[3], DateUtil.dateFormatToStr(modifytime));
			}
		}
		if(null != obj.get(FIELDS_KEY)) obj.remove(FIELDS_KEY);
		if(null != obj.get(DATAS_KEY)) obj.remove(DATAS_KEY);
		obj.put("success", "true");
		return obj;
	}
	/**
	 * 获取数据响应成功后的JSON消息字符串
	 * @param action Action 对象，通过此对象的 getText 方法获取资源文件中定义的消息
	 * @param msg 消息KEY
	 * @return 返回JSON字符串{success:false,msg:'传入的msg'}
	 */
	public static final String getSuccessMsg(ActionSupport action,String msg){
		msg = action.getText(msg);
		JSONObject obj = createJsonObject(SUCCESS_STATE,msg);
		return obj.toString();
	}
	
	/**
	 * 获取数据响应成功后的JSON消息字符串
	 * @param action Action 对象，通过此对象的 getText 方法获取资源文件中定义的消息
	 * @param msg 消息KEY
	 * @return 返回JSON字符串{success:false,msg:'传入的msg'}
	 */
	public static final String getSuccessMsg(ActionSupport action,String msg,Map<String,Object> attachParams){
		msg = action.getText(msg);
		JSONObject obj = createJsonObject(SUCCESS_STATE,msg);
		
		JSONObject subObj = new JSONObject();
		subObj.putAll(attachParams);
		obj.put("data", subObj.toString());
		return obj.toString();
	}
	
	/**
	 * 获取数据响应成功后的JSON消息字符串
	 * @param action Action 对象，通过此对象的 getText 方法获取资源文件中定义的消息
	 * @param entity 要获取ID值的 实体对象
	 * @param msg 消息KEY
	 * @return 返回JSON字符串{success:false,msg:'传入的msg'}
	 */
	public static final String getSuccessMsg(ActionSupport action,Object entity,String msg){
		msg = action.getText(msg);
		JSONObject obj = createJsonObject(SUCCESS_STATE,msg);
		Map<String,Object> pkMap = GenericsUtil.getPkName(entity);
		Set<String> keys = pkMap.keySet();
		for(String key : keys){
			obj.put(key, pkMap.get(key).toString());
		}
		return obj.toString();
	}
	
	/**
	 * 获取数据响应成功后的JSON消息字符串
	 * @return 返回JSON字符串{success:true}
	 */
	public static final String getSuccessMsg(){
		JSONObject obj = createJsonObject(SUCCESS_STATE,null);
		return obj.toString();
	}
	
	/**
	 * 获取数据响应失败后的JSON消息字符串
	 * @return 返回JSON字符串{success:false,msg:'传入的msg'}
	 */
	public static final String getFailureMsg(String msg){
		JSONObject obj = createJsonObject(FAILURE_STATE,msg);
		return obj.toString();
	}
	
	/**
	 * 获取数据响应失败后的JSON消息字符串
	 * @param action Action 对象，通过此对象的 getText 方法获取资源文件中定义的消息
	 * @param msg 消息KEY
	 * @return 返回JSON字符串{success:false,msg:'传入的msg'}
	 */
	public static final String getFailureMsg(ActionSupport action,String msg){
		Map<String,Object[]> argsMap = ServiceException.getArgsByKey(msg, true);
		String  errmsg = null;
		if(null != argsMap && argsMap.size() > 0){
			String i18n = null;
			Set<String> keys = argsMap.keySet();
			for(String key : keys){
				i18n = key;
				break;
			}
			Object[] args = argsMap.get(i18n);
			errmsg = StringHandler.formatFromResource(i18n, msg, args);
		}else{
			errmsg = action.getText(msg);
		}
		
		if(StringHandler.isValidStr(errmsg)){
			msg = errmsg;
		}
		JSONObject obj = createJsonObject(FAILURE_STATE,msg);
		return obj.toString();
	}
	
	/**
	 * 获取数据响应失败后的JSON消息字符串
	 * @return 返回JSON字符串{success:false}
	 */
	public static final String getFailureMsg(){
		JSONObject obj = createJsonObject(FAILURE_STATE,null);
		return obj.toString();
	}
	
	/**
	 * 获取数据响应失败后的JSON消息字符串
	 * @param attachParams 要追加到失败中的数据
	 * @return 返回JSON字符串{success:false}
	 */
	public static final String getFailureMsg(Map<String,Object> attachParams){
		JSONObject obj = createJsonObject(FAILURE_STATE,null);
		obj.putAll(attachParams);
		return obj.toString();
	}
	
	/**
	 * 根据传入参数创建  JSONObject 对象
	 * @param success
	 * @param msg
	 * @return
	 */
	public static JSONObject createJsonObject(boolean success,String msg){
		JSONObject json = new JSONObject();
		json.put("success", success);
		if(StringHandler.isValidStr(msg))
			json.put("msg", msg);
		return json;
	}
}
