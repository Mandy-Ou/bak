package com.cmw.core.base.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.cmw.constant.ResultMsg;
import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.UserEntity;
import com.opensymphony.xwork2.ActionSupport;
/**
 * Action 父类
 * @author cmw_1984122
 * Spring2.5通过@Controller @Scope("prototype") 这样的注解，将Struts的Action纳为自己的控制范围之内
 * Struts2.0自带的@ParentPackage和@Results等注解，注明了要继承的父包和响应结果。还有@RequiredStringValidator，@RequiredFieldValidator等注解，大大方便了我们处理表单信息的校验。
 */
//@Controller		@Scope("prototype")   //声明此类为控制层的类,且为prototype模式调用
//@ParentPackage(value="struts-default")
@SuppressWarnings("serial")
public class BaseAction extends ActionSupport{
	public HttpSession session;
	public HttpServletRequest request;
	private String params;
	private int start=0;
	private int limit = 20;
	protected static Logger log;
	
	
	
	public BaseAction() {
		super();
		 log = Logger.getLogger(this.getClass());
	}

	public int getStart() {
		return  start;
	}

	public HttpSession getSession() {
		session = getRequest().getSession();
		return  session;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public HttpServletRequest getRequest() {
		request = ServletActionContext.getRequest();
		return request;
	}

	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	
	public void outJsonString(String str) {
//		getResponse().setContentType("text/javascript;charset=UTF-8");
		getResponse().setCharacterEncoding("UTF-8");  
		getResponse().setContentType("application/json");  
		System.out.println("outJsonString="+str);
		outString(str);
	}

	public void outJson(Object obj) {
		outJsonString(JSONObject.fromObject(obj).toString());
	}

	public void outJsonArray(Object array) {
		outJsonString(JSONArray.fromObject(array).toString());
	}

	public void outString(String str) {
		try {
			PrintWriter out = getResponse().getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void outXMLString(String xmlStr) {
		getResponse().setContentType("application/xml;charset=UTF-8");
		outString(xmlStr);
	}
	
	/**
	 * 向客户端输出二进制数据，如图像
	 * @param bytes 要输出的 byte 数组
	 */
	public void outByteDatas(byte[] bytes){
		try {
			OutputStream out = getResponse().getOutputStream();
			out.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getVal(String colName){
		if(null == colName || "".equals(colName)) return "";
		String val = getRequest().getParameter(colName);
		return (null == val) ? "" : val;
	}
	
	/**
	 * 获取 Integer 型值
	 * @param colName
	 * @return
	 */
	public Integer getIVal(String colName){
		String val = getVal(colName);
		return (StringHandler.isValidStr(val)) ? Integer.parseInt(val) : null;
	}
	
	/**
	 * 获取 Long 型值
	 * @param colName
	 * @return
	 */
	public Long getLVal(String colName){
		String val = getVal(colName);
		return (StringHandler.isValidStr(val)) ? Long.parseLong(val) : null;
	}
	
	/**
	 * 获取 Double 型值
	 * @param colName
	 * @return
	 */
	public Double getDVal(String colName){
		String val = getVal(colName);
		return (StringHandler.isValidStr(val)) ? Double.parseDouble(val) : null;
	}
	
	/**
	 * 获取 Float 型值
	 * @param colName
	 * @return
	 */
	public Float getFVal(String colName){
		String val = getVal(colName);
		return (StringHandler.isValidStr(val)) ? Float.parseFloat(val) : null;
	}
	
	/**
	 * 获取 Byte 型值
	 * @param colName
	 * @return
	 */
	public Byte getBVal(String colName){
		String val = getVal(colName);
		return (StringHandler.isValidStr(val)) ? Byte.parseByte(val) : null;
	}
	
	
	/**
	 * 根据指定的参数获取页面中要查询的值，通过 request 去取值
	 * 并以 SHashMap 对象返回
	 * 例如：
	 * 	String args = "name,minunit#I,total#L,amount#F,money#O,date#D";
	 * 如果没有加"#"号则表示是字符串，如果“#” 后有：
	 * 则依次是以下意思：
	 * 		I : Integer	,	L : Long
	 * 		F : Float	,	O : Double
	 * 		D : Date	,	B : Boolean
	 * 	SHashMap params = getQParams(args);
	 * @param args 要从 request 中获取的值 多个以逗号分隔
	 * @return 返回查询参数 SHashMap 对象
	 */
	public SHashMap<String, Object> getQParams(String args){
		return getQParams(args,null);
	}
	
	/**
	 * 根据指定的参数获取页面中要查询的值，
	 * 并以 SHashMap 对象返回
	 * 例如：
	 * 	String args = "name,minunit#I,total#L,amount#F,money#O,date#D";
	 * 如果没有加"#"号则表示是字符串，如果“#” 后有：
	 * 则依次是以下意思：
	 * 		I : Integer	,	L : Long
	 * 		F : Float	,	O : Double
	 * 		D : Date	,	B : Boolean
	 * 	SHashMap params = getQParams(args);
	 * @param args 要从 request 中获取的值 多个以逗号分隔
	 * @param map 封装页面数据的 map 对象，如果此参数为空，就通过 request 对象直接从请求中取值
	 * @return 返回查询参数 SHashMap 对象
	 */
	public SHashMap<String, Object> getQParams(String args,Map<String,String> map){
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		String[] arrStr = StringHandler.Split(",", args);
		String logic = "";
		String key = "";
		Object val=null;
		int len = arrStr.length;
		if(null == map || map.size() == 0){
			this.getRequest();	//实例化 request 对象
			for(int i=0; i < len; i++){
				if(arrStr[i] == null) continue;
				//以下为获取页面值，并转型
				String[] fieldArr = parseArgs(arrStr[i],logic);
				key = fieldArr[0];
				if(!StringHandler.isValidStr(key)) continue;
				String keyPrefix = parseKey(key);
				val = request.getParameter(keyPrefix);
				System.out.println(keyPrefix+"="+val);
				if(null == val || "".equals(val)) continue;
				put(params,fieldArr,val,logic);
			}
		}else{
			for(int i=0; i < len; i++){
				if(arrStr[i] == null) continue;
				//以下为获取页面值，并转型
				String[] fieldArr = parseArgs(arrStr[i],logic);
				key = fieldArr[0];
				if(!StringHandler.isValidStr(key)) continue;
				String keyPrefix = parseKey(key);
				val = map.get(keyPrefix);
				if(null == val || "".equals(val)) continue;
				put(params,fieldArr,val,logic);
			}
		}
		return params;
	}
	
	/**
	 * 解析 key 值
	 * @param key 
	 * @return 返回解析后的KEY值
	 */
	private String parseKey(String key){
		return (-1==key.indexOf(".")) ? key : key.substring(key.indexOf(".")+1);
	}
	/**
	 * 解析字符串KEYS
	 * @param kv 待解析的 key 名称
	 * @param logic 逻辑表达式变量
	 * @return 返回解析后的字符串数组 数组, 第一个为KEY，第二个为 此键将要转换的数据类型
	 */
	public String[] parseArgs(String kv,String logic){
		int index = kv.indexOf(SqlUtil.LOGIC);
		if(-1 != index){
			logic = kv.substring(index);
			kv = kv.substring(0,index);
		}
		//以下为获取页面值，并转型
		return kv.split("#");
	}
	
	/**
	 * 往 params 对象中放指定的值
	 * @param params
	 * @param val
	 * @param logic
	 */
	private void put(SHashMap<String, Object> params,String[] fieldArr,Object val,String logic){
		String filterVal = val.toString();
		filterVal = StringHandler.replaceSqlSign(filterVal);
		if(fieldArr.length>1){	//如果需要转型
			val = StringHandler.getValByType(filterVal,fieldArr[1]);
		}else{
			val = filterVal;
		}
		if(StringHandler.isValidStr(logic)){
			val = filterVal+logic;
		}
		params.put(fieldArr[0], val);
	}
	
	/**
	 * 验证是否需要加载数据。
	 *  isLoad = "false" 不需要加载数据
	 * @return true : 需要加载数据 , false : 不需要加载
	 */
	
	
	public boolean isLoad(){
		String isLoad = getVal("isLoad");
		String result = null;
		if(StringHandler.isValidStr(isLoad) && "false".equals(isLoad)){
			result = ResultMsg.GRID_NODATA;
			outJsonString(result);
			return false;
		}
		return true;
	}
	
	/**
	 * 获取当前登录的用户
	 * @return
	 * @throws ServiceException 
	 */
	public UserEntity getCurUser(){
		UserEntity user =(UserEntity)this.getSession().getAttribute(SysConstant.USER_KEY);
		if(null==user){
//			throw new ServiceException(ServiceException.USER_IS_NULL);
//			user = getTestUser();
		}
		return user;
	}
	
	/**
	 * 创建一个测试用户
	 * @return
	 */
	public static UserEntity getTestUser(){
		UserEntity user = new UserEntity();
		user.setUserId(-1L);
		user.setOrgid(0L);
		user.setDeptId(-1L);
		user.setEmpId(-1l);
		user.setIsSystem(1);
		user.setIncompId(0L);
		user.setIndeptId(-1L);
		return user;
	}
	
	
	/**
	 * 获取查询页面所有表单元素数据
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getParams() {
		Map map = BeanUtil.parseVals(params);
		return map;
	}

	/**
	 * 根据指定的KEY，从 params 中获取相对应的值并以 SHashMap 对象返回
	 * @param keys 要获取值的 键字符串列表
	 * @return 返回 SHashMap 对象
	 */
	public SHashMap<String, Object> getParams(String keys) {
		Map<String, String> map = getParams();
		return getQParams(keys,map);
	}
	
	/**
	 * 根据指定的KEY，从 map 中获取相对应的值并以 SHashMap 对象返回
	 * @param keys 要获取值的 键字符串列表
	 * @return 返回 SHashMap 对象
	 */
	public SHashMap<String, Object> getParams(String keys,Map<String, String> map) {
		return getQParams(keys,map);
	}
	
	public void setParams(String params) {
		this.params = params;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

}
