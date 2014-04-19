package com.cmw.core.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.LinkLoopException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cmw.core.base.exception.UtilException;

/**
 * 存放类似于表格数据的类
 * @author chengmingwei
 *
 */
public class DataTable implements Serializable {
	private static StringHandler log = new StringHandler();
	private Long baseId;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Object> dataSource = null;
	
	private String columnNames = null;
	
	private long size = -1;
	
	private RowAction action;
	
	private int decimal = 2;
	// 是否需要进行将 byte 数组转字符串处理
	private boolean isByteChange = false;
	//标识是否只有一列数据
	private boolean isOneCmn = false;
	
	public DataTable() {
	}
	
	public DataTable(List<Object> dataSource, String columnNames) {
		this.dataSource = dataSource;
		this.columnNames = this.parseToColumnsNames(columnNames);
	}
	
	public DataTable(String columnNames) {
		this.columnNames = this.parseToColumnsNames(columnNames);
		dataSource = new ArrayList<Object>();
	}
	
	/**
	 * 
	 * @param appendCmns
	 */
	public void appendCmns(String appendCmns){
		this.columnNames += ","+appendCmns;
	}
	
	/**
	 * 添加一行数据
	 * @param vals 要添加的数据，以数组形式填入
	 */
	public void addRowData(Object... vals){
		this.dataSource.add(vals);
	}
	
	/**
	 * 将指定的 DataTable 数据 插入到当前DataTable 最前面。
	 * 当前DataTable 对象中的数据后移
	 * 注意：两个DataTable column 必须相同
	 * @param dtNew 
	 */
	public void insertDataTableToFirst(DataTable dtNew){
		List<Object> _dataSource = dtNew.getDataSource();
		_dataSource.addAll(this.dataSource);
		this.dataSource = _dataSource;
	}
	
	public List<Object> getDataSource() {
		return dataSource;
	}

	public void setDataSource(List<Object> dataSource) {
		this.dataSource = dataSource;
	}
	
	public void setSize(long size){
		this.size = size;
	}
	public long getSize(){
		return (0 < this.size) ? this.size : (null == dataSource) ? 0 : dataSource.size();
	}

	public String getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String columnNames) {
		this.columnNames = parseToColumnsNames(columnNames);
	}

	public void setHqlAsColumnNames(String hql) throws UtilException{
		if(null== hql || "".equals(hql)) return;
		hql = hql.trim().toLowerCase();
		int start = hql.indexOf("select");
		if(start == -1) throw new UtilException("hql语句必须以 select 开头的格式，如： select A.id from ts_user ");
		int end = hql.indexOf("from");
		if( start!=-1 || end != -1){
			hql = hql.substring(start+"select".length(),end);
		}
		this.columnNames = parseToColumnsNames(hql);
	}

	/**
	 * 将指定的字符串解析为DataTable 列名
	 * @param columnNames 要解析的字符串
	 * @return	解析后的列名字符串
	 */
	private String parseToColumnsNames(String columnNames) {
		int start;
		//System.out.println("---------->columnNames="+columnNames);
		//如果有别名的，只取别名
		String as = " as ";
		String[] cls = columnNames.split(",");
		if(cls ==null || cls.length==0) return null;
		columnNames = "";
		for(String cl : cls){
			if(cl.equals("")) continue;
			cl = cl.trim();
			int asIndex = cl.indexOf(as);
			start = -1;
		
			int dotIndex = cl.indexOf(".");
			if(dotIndex != -1){
				start = dotIndex+1;
			}
			
			if(asIndex != -1 || asIndex>dotIndex){
				start = asIndex+as.length();
			}
			
			if(start != -1){
				cl = cl.substring(start);
			}
			columnNames += cl+",";
		}
		columnNames = columnNames.substring(0,columnNames.length()-1);
		this.isOneCmn = (columnNames.split(",").length == 1);
		//System.out.println("---------->End columnNames="+columnNames);
		return columnNames;
	}
	/**
	 * 获取记录数
	 * @return
	 */
	public Integer getRowCount()
	{
		return (null != dataSource) ? dataSource.size() : 0;
	}
	/**
	 * 返回某一行的数据
	 * @param rowIndex	要返回的行的索引
	 * @return　Object 数组形式
	 */
	public Object[] getRowData(Integer rowIndex)
	{
		Object[] row = isOneCmn ? new Object[]{this.dataSource.get(rowIndex)} : (Object[])this.dataSource.get(rowIndex);
		return row;
	}
	
	public Object[] getCellDatas(Integer rowIndex){
		Object[] data = this.getRowData(rowIndex);
		if (data == null) return null;
		if(null != action) data = action.makeRow(data);
		return data;
	}
	
	/**
	 * 向 DataTable对象的第一行中追加数据
	 * @param cols 追加的字段名，多个以逗号隔开
	 * @param vals	要追加的值，字符串数组
	 */
	public void appendData(String cols,Object[] vals){
		if(null == this.dataSource || this.dataSource.size()==0) return;
		if(null == cols || "".equals(cols)) return;
		this.columnNames += ","+cols;
		Object[] firstData = (Object[])this.dataSource.get(0);
		Object[] newData = new Object[firstData.length+vals.length];
		int i=0;
		for(; i < firstData.length; i++){
			newData[i] = firstData[i];
		}
		int k=0;
		for(; i<newData.length; i++){
			newData[i] = vals[k];
			k++;
		}
		this.dataSource.remove(0);
		this.dataSource.add(0, newData);
	}
	
	/**
	 * 将指定行中指定列以日期格式字符串返回，例如："2007-01-12"
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param columnName
	 *            列名
	 * @return
	 * @throws UtilException
	 */
	public String getDateString(Integer rowIndex, String columnName,Object[] data) {
		String pattern = "#yyyy-MM-dd";
		columnName = (columnName.indexOf(pattern) != -1) ? columnName
				: columnName + pattern;
		Object val = data[rowIndex];
		if(!StringHandler.isValidObj(val)) return "";
		if(val.getClass().getSimpleName().equals("Timestamp")){
			String dateVal = val.toString();
			dateVal = dateVal.substring(0, dateVal.indexOf(" "));
			val = dateVal+"";
		}else{
			pattern = pattern.replace("#", "");
			System.out.println("getDateString="+val+",type="+val.getClass().getSimpleName());
			val = StringHandler.dateFormatToStr(pattern, (Date) val);
		}
		return (String)val;
	}
	
	/**
	 * 将指定行中指定列以日期格式字符串返回，例如："2007-01-12"
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param columnName
	 *            列名
	 * @return
	 * @throws UtilException
	 */
	public String getDateString(Integer rowIndex, String columnName) {
		String pattern = "#yyyy-MM-dd";
		columnName = (columnName.indexOf(pattern) != -1) ? columnName
				: columnName + pattern;
		Object val = getCellData(rowIndex, columnName);
		if(!StringHandler.isValidObj(val)) return "";
		if(val.getClass().getSimpleName().equals("Timestamp")){
			String dateVal = val.toString();
			dateVal = dateVal.substring(0, dateVal.indexOf(" "));
			val = dateVal+"";
		}else{
			pattern = pattern.replace("#", "");
			val = StringHandler.dateFormatToStr(pattern, (Date) val);
			System.out.println("getDateString="+val+",type="+val.getClass().getSimpleName());
		}
		return (String)val;
	}
	
	
	/**
	 * 返回指定行中指定列的整数值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	@SuppressWarnings("static-access")
	public Integer getInteger(Integer rowIndex,String columnName) 
	{
		Integer val = 0;
		try {
			Object objVal = getCellData(rowIndex, columnName);
			if(StringHandler.isValidObj(objVal)){
				val = Integer.parseInt(objVal.toString());
			}
		} catch (ClassCastException e) {
			e.printStackTrace();
			log.P("类型转换错误：不能将"+columnName+"获取的值转换成整型!!!!!!!");
		}
		return val;
	}
	
	/**
	 * 返回第一行中指定列的整数值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	@SuppressWarnings("static-access")
	public Integer getInteger(String columnName) 
	{
		Integer val = 0;
		try {
			val = (Integer)getCellData(0, columnName);
		} catch (ClassCastException e) {
			e.printStackTrace();
			log.P("类型转换错误：不能将"+columnName+"获取的值转换成整型!!!!!!!");
		}
		return val;
	}
	
	/**
	 * 返回指定行中指定列的 Long 值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return 
	 * @throws UtilException 
	 */
	@SuppressWarnings("static-access")
	public Long getLong(Integer rowIndex,String columnName) 
	{
		Long val = 0l;
		try {
			val = (Long)getCellData(rowIndex, columnName);
		} catch (ClassCastException e) {
			e.printStackTrace();
			log.P("类型转换错误：不能将"+columnName+"获取的值转换成Long型!!!!!!!");
		}
		return val;
	}
	
	/**
	 * 返回第一行中指定列的 Long 值
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	@SuppressWarnings("static-access")
	public Long getLong(String columnName) 
	{
		Long val = 0l;
		try {
			val = (Long)getCellData(0, columnName);
		} catch (ClassCastException e) {
			e.printStackTrace();
			log.P("类型转换错误：不能将"+columnName+"获取的值转换成Long型!!!!!!!");
		}
		return val;
	}
	
	public String getEmptyJsonArr(){
		return "{list:[],totalSize:0,success:true}";
	}
	
	/**
	 * 获取 JSON 字符串数据
	 * Map<String,String> map = new HashMap<String,String>();
	 * 	map.put("empName","name");	//--> 将 DataTable 对象中的 empName 替换成 name 返回
	 * String jsonData = dt.getJsonArr(map);
	 * @param replaceCmnMap 要替换的列 
	 * @return 
	 */
	public String getJsonArr(Map<String,String> replaceCmnMap){
		JSONArray jsonArr = this.getJsonList(replaceCmnMap,null);
		return getJsonListStr(jsonArr);
	}
	
	/**
	 * 获取指定数组列的 JSON 字符串数据
	 *  String[] outCmns = new String[]{"id","name"};
	 * String jsonData = dt.getJsonArr(outCmns);
	 *  将指将 dataTable 对象中的 id,name 数据作为JSON字符串数据返回
	 * @param outCmns 要输出到JSON字符串中的列数组
	 * @return 返回指定列的 JSON字符串数据
	 */
	public String getJsonArr( String[] outCmns){
		JSONArray jsonArr = this.getJsonList(null,outCmns);
		return getJsonListStr(jsonArr);
	}
	
	/**
	 * 获取指定数组列的 JSON 字符串数据(在获取指定列之前，通过 replaceMap 对象先替换DataTable 中的旧列)
	 * Map<String,String> map = new HashMap<String,String>();
	 * 	map.put("empName","name");	//--> 将 DataTable 对象中的 empName 替换成 name 返回
	 * 
	 *  String[] outCmns = new String[]{"id","name"};
	 * String jsonData = dt.getJsonArr(map,outCmns);
	 *  将指将 dataTable 对象中的 id,name 数据作为JSON字符串数据返回
	 * @param outCmns 要输出到JSON字符串中的列数组
	 * @return 返回指定列的 JSON字符串数据
	 */
	public String getJsonArr(Map<String,String> replaceCmnMap, String... outCmns){
		JSONArray jsonArr = this.getJsonList(replaceCmnMap,outCmns);
		return getJsonListStr(jsonArr);
	}
	
	/**
	 * 获取JSON数据,在对 DataTable 行数据循环时，调用 callback 处理相应的行数据
	 * @param callback  JSONObject 数据回调接口
	 * @return
	 */
	public String getJsonArr(JsonDataCallback callback){
		JSONArray jsonArr = this.getJsonList(callback);
		return getJsonListStr(jsonArr);
	}
	
	public String getJsonArr(){
		JSONArray jsonArr = this.getJsonList();
		return getJsonListStr(jsonArr);
	}
	
	
	public String getJsonListStr(JSONArray jsonArr){
		JSONObject json = new JSONObject();
		json.put("list", jsonArr);
		json.put("totalSize", this.getSize());
		json.put("success", true);
		return json.toJSONString();
//		return "{list:"+jsonStr+",totalSize:"+this.getSize()+",success:true}";
	}
	
	/**
	 * 获取 JSON字符串数组
	 * @return
	 */
	public JSONArray getJsonList(){
		return getJsonList(null,null);
	}
	
	/**
	 * 获取 JSON字符串数组
	 * @return
	 */
	public JSONArray getJsonList(Map<String,String> replaceCmnMap, String[] outCmns){
		if(null != replaceCmnMap && replaceCmnMap.size() > 0) replaceCmns(replaceCmnMap);
		String[] cns = (null != outCmns && outCmns.length > 0) ? outCmns : this.getColumnNames().split(",");
		System.out.println(this.getColumnNames());
		JSONArray jsonArr = new JSONArray();
		int size = this.dataSource.size();
		for(int i=0; i<size;i++){
			JSONObject obj = new JSONObject();
			Object[] data = this.getRowData(i);
			for(int j=0;j<cns.length;j++){
				String cn = cns[j];
				Integer cellIndex = getCellIndex(cn);
				Object val =  data[cellIndex];
				if(cn.indexOf("'#'")>=0) continue;
				Object[] kv = parseDateVal(cn, val);
				cn = kv[0].toString();
				val = kv[1];
				//System.out.println(cn+" : "+val+",  type="+val.getClass());
				if(null == val) val = "";
				obj.put(cn, val);
			}
			jsonArr.add(obj);
		}
		return jsonArr;
	}
	
	/**
	 * 解析DataTable 数据中需要格式化的日期值
	 * @param cn
	 * @param val
	 * @return
	 */
	private Object[] parseDateVal(String cn, Object val){
		if(cn.indexOf("#") != -1){
			String[] cnArr = cn.split("#");
			String pattern = cnArr[1];
			cn = cnArr[0];
			if(val instanceof java.lang.String){
				return new Object[]{cn,val};
			}else if(val instanceof java.lang.Integer){
				Integer int_Val = (Integer)val;
				Date date = new Date(int_Val.longValue());
				val = DateUtil.dateFormatToStr(date);
				return new Object[]{cn,val};
			}else{
				val = StringHandler.dateFormatToStr(pattern, (Date)val);
			}
			
		}
		return new Object[]{cn,val};
	}
	
	/**
	 * 替换DataTable 中的指定列
	 * @param map  map [key : DataTable 对象中的旧列，value:新的列]
	 */
	public void replaceCmns(Map<String,String> map){
		StringBuffer sb = new StringBuffer();
		String[] cmns = this.columnNames.split(",");
		for(int i=0,count=cmns.length; i<count; i++){
			String cmn = cmns[i];
			Set<String> keys = map.keySet();
			for(String key : keys){
				if(cmn.equals(key)){
					String newCmn = map.get(key);
					cmn = newCmn;
					break;
				}
			}
			sb.append(cmn+",");
		}
		this.columnNames = StringHandler.RemoveStr(sb);
	}
	
	
	/**
	 * 获取 JSON字符串数组
	 * @return
	 */
	public JSONArray getJsonList(JsonDataCallback callback){
		String[] cns = this.getColumnNames().split(",");
		JSONArray jsonArr = new JSONArray();
		int size = this.dataSource.size();
		for(int i=0; i<size;i++){
			JSONObject obj = new JSONObject();
			Object[] data = this.getRowData(i);
			for(int j=0;j<cns.length;j++){
				String cn = cns[j];
				Integer cellIndex = getCellIndex(cn);
				Object val =  data[cellIndex];
				if(cn.indexOf("'#'")>=0) continue;
				Object[] kv = parseDateVal(cn, val);
				cn = kv[0].toString();
				val = kv[1];
				if(null == val) val = "";
				obj.put(cn, val);
			}
			callback.makeJson(obj);
			jsonArr.add(obj);
		}
		return jsonArr;
	}
	
	public String[] getColumnArr(){
		return this.getColumnNames().split(",");
	}
	
	/**
	 * 将当前DataTable 对象转换为 JSON对象字符串
	 * 当DataTable size 为1时，将返回当前对象
	 * @return
	 */
	public String getJsonObjStr(){
		JSONObject obj = getJsonObj();
		return obj.toString();
	}
	
	/**
	 * 将当前DataTable 对象转换为 JSON对象字符串
	 * 当DataTable size 为1时，将返回当前对象
	 * @return
	 */
	public String getJsonObjStr(Map<String,Object> appendParams){
		JSONObject obj = getJsonObj();
		obj.putAll(appendParams);
		return obj.toString();
	}
	
	/**
	 * 将当前DataTable 对象转换为 JSON对象字符串
	 * 当DataTable size 为1时，将返回当前对象
	 * @return
	 */
	public JSONObject getJsonObj(){
		String[] cns = this.getColumnNames().split(",");
		int size = this.dataSource.size();
		if(size==0) return null;
		String prefix = "";
		if(null != this.baseId && this.baseId>0){
			prefix = "_"+this.baseId;
		}
		
		JSONObject obj = new JSONObject();
			Object[] data = this.getRowData(0);
			for(int j=0;j<cns.length;j++){
				String cn = cns[j];
				Integer cellIndex = getCellIndex(cn);
				Object val =  data[cellIndex];
				if(cn.indexOf("#") != -1){
					String[] cnArr = cn.split("#");
					String pattern = cnArr[1];
					cn = cnArr[0];
					val = StringHandler.dateFormatToStr(pattern, (Date)val);
				}
				//System.out.println(cn+" : "+val+",  type="+val.getClass());
				obj.put(cn+prefix, (!StringHandler.isValidObj(val)) ? "" : val.toString());
			}
			obj.put("success", "true");
		return obj;
	}
	
	/**
	 * 返回指定行中指定列的浮点数值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	@SuppressWarnings("static-access")
	public Float getFloat(Integer rowIndex,String columnName) 
	{
		Float val = 0.0f;
		try {
			val = (Float)getCellData(rowIndex, columnName);
		} catch (ClassCastException e) {
			log.P("类型转换错误：不能将"+columnName+"获取的值转换成浮点型!!!!!!!!");
		}
		return val;
	}
	
	/**
	 * 返回第一行中指定列的双精度值
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	public Double getDouble(String columnName){
		return getDouble(0,columnName);
	} 
	
	/**
	 * 返回指定行中指定列的双精度值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	@SuppressWarnings("static-access")
	public Double getDouble(Integer rowIndex,String columnName) 
	{
		Double val = 0.0;
		try {
			Object objVal = getCellData(rowIndex, columnName);
			if(objVal instanceof java.math.BigDecimal){
				BigDecimal bigVal = (BigDecimal)objVal;
				val = (null != bigVal) ? bigVal.doubleValue() : 0d;
			}else{
				val = (Double)objVal;
			}
			
		} catch (ClassCastException e) {
			e.printStackTrace();
			log.P("类型转换错误：不能将"+columnName+"获取的值转换成Double型!!!!!!!");
		}
		return val;
	}
	
	//BigDecimal
	/**
	 * 返回指定行中指定列的双精度值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	@SuppressWarnings("static-access")
	public BigDecimal getBigDecimal(Integer rowIndex,String columnName) 
	{
		BigDecimal val = new BigDecimal("0");
		try {
			val = (BigDecimal)getCellData(rowIndex, columnName);
		} catch (ClassCastException e) {
			e.printStackTrace();
			log.P("类型转换错误：不能将"+columnName+"获取的值转换成Double型!!!!!!!");
		}
		return val;
	}
	/**
	 * 返回指定行中指定列的byte值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	@SuppressWarnings("static-access")
	public Byte getByte(Integer rowIndex,String columnName) 
	{
		Byte val = 0;
		try {
			val = (Byte)getCellData(rowIndex, columnName);
		} catch (ClassCastException e) {
			log.P("类型转换错误：不能将"+columnName+"获取的值转换成 Byte 型!!!!!!!");
		}
		return val;
	}
	
	/**
	 * 返回指定行中指定列的布尔值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	@SuppressWarnings("static-access")
	public Boolean getBoolean(Integer rowIndex,String columnName) 
	{
		Boolean val = null;
		try {
			val = (Boolean)getCellData(rowIndex, columnName);
		} catch (ClassCastException e) {
			e.printStackTrace();
			log.P("类型转换错误：不能将"+columnName+"获取的值转换成 Boolean 型!!!!!!");
		}
		return val;
	}
	
	/**
	 * 返回第一行中指定列的字符串值
	 * @param columnName
	 *            列名
	 * @return
	 * @throws UtilException
	 */
	public String getString(String columnName) {
		Object value = getCellData(0, columnName);
		return (null == value) ? "" : value.toString();
	}
	
	/**
	 * 返回指定行中指定列的字符串值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	public String getString(Integer rowIndex,String columnName) 
	{
		Object val = getCellData(rowIndex, columnName);
		return (null == val) ? "" : val.toString();
	}
	
	/**
	 * 返回指定行中指定列的字符串值
	 * 
	 * @param rowIndex
	 *            行索引
	 * @param columnName
	 *            列名
	 * @return
	 * @throws UtilException
	 */
	public String getString(Integer rowIndex, String columnName,Object[] data) {
		Object value = null;
		try{
			 value = data[rowIndex];
		}catch (Exception e) {
			System.out.println("Error: columnName="+columnName);
			e.printStackTrace();
		}
		
		if(null==value)return "";
		if (value.toString().matches("\\d*\\.\\d{3,}")) {
			// 如果匹配正则表达式 \d*\.\d{3,}，即小数点前只能有数字，后大于3位数字则进行去小数点转换。
			try {
				// 四舍五入，并保留两位小数
				Double val = null;
				if(value.getClass().getSimpleName().equals("String")){
					val = Double.parseDouble((String)value);
				}else{
					val = (Double) value;
				}
				value = StringHandler.Round(val, decimal);
			} catch (Exception e) {
				System.out.println("将double转换为小数点后2位时出错:");
				System.out.println("----------->" + e);
			}
		}
		if (isByteChange) {
			// 当 SQL 或 HQL 中使用 concat 函数时，会把字符串转成 byte[] 对象，
			// 故此处就是处理 Hibernate 的 Bug
			if ("byte[]".equals(value.getClass().getSimpleName())) {
				try {
					value = new String((byte[]) value, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return value.toString();
	}
	
	/**
	 * 返回指定行中指定列的日期值
	 * @param rowIndex	行索引
	 * @param columnName	列名
	 * @return
	 * @throws UtilException 
	 */
	public Date getDate(Integer rowIndex,String columnName) 
	{
		Date val = null;
		val = (Date)getCellData(rowIndex, columnName);
		return val;
	}
	/**
	 * 获取某列数据
	 * @param rowIndex
	 * @param columnName
	 * @return
	 */
	@SuppressWarnings("static-access")
	private Object getCellData(Integer rowIndex,String columnName)
	{
		Object[] data = this.getRowData(rowIndex);
//		log.P(Arrays.toString(data));
		Integer cellIndex = getCellIndex(columnName);
//		log.P("columnName="+columnName+",index="+cellIndex+",length="+data.length);
		if(-1 == cellIndex)
			try {
				throw new UtilException(UtilException.CELL_NAME_ISNULL);
			} catch (UtilException e) {
				log.P("通过getCellData获取第"+rowIndex+"行列名为:"+columnName+"的数据时,发现列名"+columnName+"不存在!");
			}
		return data[cellIndex];
	}
	/**
	 * 根据列名获取列索引
	 * @param columnName
	 * @return
	 */
	public Integer getCellIndex(String columnName)
	{
		Integer cellIndex = -1;
		String[] cns = StringHandler.splitStr(columnNames.toLowerCase(), ",");
		columnName = columnName.toLowerCase();
		for(int i=0;i<cns.length;i++)
		{
			if(columnName.equals(cns[i].trim())) 
			{
				cellIndex = i;
				break;
			}
		}
		return cellIndex;
	}
	
	/**
	 * 获取以 parentId 作为父节点的所有子节点，并以“，”拼接返回一个ID字符串,
	 * 注：如果返回的节点ID少了或不正确。请在调用此方法之前，
	 * 	  先调用 sort 方法按ID进行排序(在SQL查询中没有排序的情况下)
	 * @param parentId	要查找子节点的节点ID
	 * @param columns	比较的ID/PID列，例如："id,pid"  逗号前面是 ID，后面是父ID，须按此顺序
	 * @return
	 */
	public String getCascadeSubIds(Integer parentId,String columns){
		StringBuffer sb = new StringBuffer();
		String[] cmnArr = columns.split(",");
		String idCmn = cmnArr[0];	// ID列名
		String pidCmn = cmnArr[1];	// PID列名
		//存放符合条件的 所有子节点ID集合
		List<Integer> aList = new ArrayList<Integer>();
		aList.add(parentId);
		//临时存放在内部循环中找到的所有子节点ID。
		List<Integer> tempList = new ArrayList<Integer>();
		for(int i=0,count=(int)this.getSize();i<count; i++){
			int id = this.getInteger(i, idCmn);
			int pid = this.getInteger(i, pidCmn);
			for(int j=0,len=aList.size(); j<len; j++){
				int currid = aList.get(j);
				if(id==currid || pid==currid){
					tempList.add(id);
					sb.append(id+",");
				}
				if(j==(len-1) && tempList.size()>0){
					aList = tempList;
				}
			}
		}
		int len = sb.length();
		if(len>0) sb = sb.deleteCharAt(len-1);
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	public Long getBaseId() {
		return baseId;
	}

	public void setBaseId(Long baseId) {
		this.baseId = baseId;
	}
	
	/**
	 * 对当前DataTable 对象以 groupCmn 作为分组列,返回分组后的Map 对象
	 * @param groupCmn
	 * @return
	 */
	public Map<String, DataTable> groupDT(String groupCmn){
		Map<String, DataTable> map = new HashMap<String, DataTable>();
		long count = this.getSize();
		if(0 == count) return null;
		for(int i=0; i<count; i++){
			String key = this.getString(i, groupCmn);
			Object[] datas  = this.getRowData(i);
			if(map.containsKey(key)){
				DataTable currDt = map.get(key);
				currDt.addRowData(datas);
			}else{
				DataTable dt = new DataTable(this.columnNames);
				dt.addRowData(datas);
				map.put(key, dt);
			}
		}
		return map;
	}
	/**
	 * 对当前DataTable 对象以 groupCmn 作为分组列分组,并返回分组后的JSON字符串
	 * @param groupCmn
	 * @return
	 */
	public String groupDTtoJson(String groupCmn){
		StringBuffer sb = new StringBuffer("{");
		Map<String, DataTable> map = this.groupDT(groupCmn);
		Set<String> keys = map.keySet();
		for(String key : keys){
			DataTable dt = map.get(key);
			String json = dt.getJsonList().toString();
			sb.append("\""+key+"\":"+json+",");
		}
		sb = sb.deleteCharAt(sb.length()-1);
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 设置某列数据
	 * @param rowIndex 行索引
	 * @param columnName	列名
	 * @param val 要设置的单元格值
	 */
	public void setCellData(Integer rowIndex,String columnName,Object val)
	{
		Object[] data = this.getRowData(rowIndex);
		Integer cellIndex = getCellIndex(columnName);
		Object[] copyArray = Arrays.copyOf(data, data.length+1);//复制数组给添加列数据
		copyArray[cellIndex] = val;
		this.dataSource.set(rowIndex, copyArray);
//		data[cellIndex] = val;
		
	}
	
	/**
	 * 将 List<Object> 数据源添加到当前 DataTable 对象的最前面，	
	 * 当前  DataTable 对象 数据源均往后移
	 * @param _dataSource 要添加的 List 数据源对象
	 */
	public void addDtToFirst(List<Object> _dataSource){
		this.dataSource.addAll(0,_dataSource);
	}
	
	/**
	 * 将 dt 对象添加到当前 DataTable 对象的最后面，	
	 * @param dt 要添加的 DataTable 对象
	 */
	public void addDtToEnd(List<Object> _dataSource){
		this.dataSource.addAll(_dataSource);
	}
	
	public void addDtToFirst(DataTable dt){
		List<Object> _dataSource = dt.getDataSource();
		this.dataSource.addAll(0,_dataSource);
	}
	
	/**
	 * 将 dt 对象添加到当前 DataTable 对象的最后面，	
	 * @param dt 要添加的 DataTable 对象
	 */
	public void addDtToEnd(DataTable dt){
		List<Object> _dataSource = dt.getDataSource();
		this.dataSource.addAll(_dataSource);
	}
	
	public void setAction(RowAction action) {
		this.action = action;
	}

	public RowAction getAction() {
		return action;
	}
	
	public void setDecimal(int decimal) {
		this.decimal = decimal;
	}
	
	public boolean isByteChange() {
		return isByteChange;
	}

	public void setByteChange(boolean isByteChange) {
		this.isByteChange = isByteChange;
	}
	public interface RowAction{
		Object[] makeRow(Object[] rowData);
	}

	/**
	 * JSON数据处时回调函数
	 * @author chengmingwei
	 *
	 */
	public interface JsonDataCallback{
		void makeJson(JSONObject jsonObj);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(Arrays.toString(this.getColumnArr())).append("\n");
		Integer count = this.getRowCount();
		if(null == count || count.intValue() == 0){
			sb.append("没有数据...");
		}else{
			for(Object rowData : this.dataSource){
				sb.append(Arrays.toString((Object[])rowData)).append("\n");
			}
		}
		return sb.toString();
	}
	
}
