package com.cmw.core.kit.excel.exporter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSONObject;
import com.cmw.core.kit.excel.importer.POIXlsImporter;
import com.cmw.core.util.StringHandler;
/**
 * Excel 导出模板参数配置模型
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class XlsTempCfgModel implements Serializable {
	/**
	 * 数据提供类型。 取值有3种： dt,hql,sql	   dt: 代表数据源是由后台某个业务类来提供，可参考示例1中的 datasource 参数值。
	 *   hql : 代表数据源是一个 HQL 查询语句。
	     sql : 代表数据源是一个 SQL 查询语句。
	 */
	private String conntype;
	/**
	 * 	B. datasource : 数据源提供对象。
	 *		b1). 如果 conntype : dt --> 就如示例1中所示对应的是一个带包的类，当系统导数据时，会调用 该类的       
	 *        getDataSource(Map<String,Object> params) 方法来得到要导出的数据。 
	 *		b2). 如果 conntype : hql --> 就如示例2中提供一条 HQL 查询语句。系统将根据此HQL 语句来读取要导出来擞   
	 *     		据。
	 *		b3). 如果 conntype : sql --> 就如示例3中提供一条SQL 查询语句。系统将根据此SQL 语句来读取要导出来擞   
	 *      		据。
	 */
	private String datasource;
	/**
	 *  mode :  数据导出模式。取值有2种 :  single,list	
	 *  c1).  mode : single  -->  导出的是单条记录。
	 *  c2). mode : list  --> 导出的是多条数据。
	 */
	private String mode;
	/**
	 * cmnRowNum  :  列所在的行 。用这些行来对Excel 单元格值进行定位填充。
	 * 当 mode ：list 或  single 是此值才  必须提供（注：可选参数）
	 */
	private Integer cmnRownum;
	/**
	 * 汇总信息对象
	 */
	private GatherInfo gahter;
	/**
	 *  cmnCellFirst :  第一个要替换的单元格所在的行索引。       cmnCellEnd : 最后一个要替换的单元格所在的行索引。
	 *  只有当 mode : single 时，以上两个值才必须提供。而且，必须两个同时提供。
	 */
	private Integer cmnCellfirst;
	
	private Integer cmnCellend;
	
	
	//字符串参数列，多个以","拼接起来。例如："id,name"
	private String params;
	//字符串参数列，多个以","拼接起来。例如："CURRDATE,CURRYEAR,CURRMONTH"
	private String sysparams;
	//导出模板文件路径
	private String templatePath;
	//Excel 模板第1行1列中的 JSON 字符串配置值
	private String jsonCfg;
	//Excel 文件导入对象
	private POIXlsImporter xlsImporter = null;
	//DataTable 列名
	private String cmns;
	private String[] cmnsArr;
	private Map<String,Integer[]> cellIndexMap = new HashMap<String, Integer[]>();
	//渲杂 Map 对象
	private Map<String, Renderer> renderMap = new HashMap<String, Renderer>();
	//替换参数Map对象
	private Map<String, Object> paramsMap = new HashMap<String, Object>();
	//替换参数值列表对象
	private List<ParamValue> paramValues = new ArrayList<ParamValue>();
	
	public String getConntype() {
		return conntype;
	}
	
	public void setConntype(String conntype) {
		this.conntype = conntype;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Integer getCmnRownum() {
		return cmnRownum;
	}
	public void setCmnRownum(Integer cmnRownum) {
		this.cmnRownum = cmnRownum;
	}
	public Integer getCmnCellfirst() {
		return cmnCellfirst;
	}
	public void setCmnCellfirst(Integer cmnCellfirst) {
		this.cmnCellfirst = cmnCellfirst;
	}
	public Integer getCmnCellend() {
		return cmnCellend;
	}
	public void setCmnCellend(Integer cmnCellend) {
		this.cmnCellend = cmnCellend;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getSysparams() {
		return sysparams;
	}

	public void setSysparams(String sysparams) {
		this.sysparams = sysparams;
	}


	/**
	 * 获取导出模板的文件路径
	 * @return
	 */
	public String getTemplatePath() {
		return templatePath;
	}
	
	/**
	 * 设置导出模板的文件路径
	 * @return
	 */
	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	/**
	 * 获取 Excel 中定义的 DataTable 列
	 * @return
	 */
	public String getCmns() {
		return cmns;
	}

	/**
	 * 当是单条数据时，用来定会 DataTable 列在Excel 单元格中的索引位置
	 * @return 返回 cmn 在Excel 中的位置
	 */
	public Map<String, Integer[]> getCellIndexMap() {
		return cellIndexMap;
	}

	
	public GatherInfo getGahter() {
		return gahter;
	}

	public void setGahter(GatherInfo gahter) {
		this.gahter = gahter;
	}

	/**
	 * 获取要渲染的 Map 对象
	 * @return
	 */
	public Map<String, Renderer> getRenderMap() {
		return renderMap;
	}

	
	public List<ParamValue> getParamValues() {
		return paramValues;
	}

	public void setParamValues(List<ParamValue> paramValues) {
		this.paramValues = paramValues;
	}

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}

	/**
	 * 通过模板文件路径读取模板中的配置参数等内容
	 * @param templatePath 模板路径所在的位置
	 * @return
	 */
	public void readTemplate(String templatePath){
		this.templatePath = templatePath;
		xlsImporter = new POIXlsImporter(templatePath);
		this.jsonCfg = (String)(xlsImporter.getCellData(0, 0));
		setValuesToSelf();
		makeCellIndexes();
	}
	
	/**
	 * 绑定参数并创建 ParamValue 对象列表 
	 * @param params 绑定的
	 */
	public void bindParams(Map<String, Object> params){
		Set<String> keySet = paramsMap.keySet();
		for(String key : keySet ){
			if(!params.containsKey(key)) continue;
			String offsetStr = (String)paramsMap.get(key);
			String value= (String)params.get(key);
			if(!StringHandler.isValidStr(value)) value = "";
			String[] offsets = offsetStr.split(",");
			String rowOffset = offsets[0];
			if(!StringHandler.isValidStr(rowOffset)) rowOffset="0";
			String cellOffset = offsets[1];
			if(!StringHandler.isValidStr(cellOffset)) cellOffset="0";
			ParamValue pValue = new ParamValue();
			pValue.setRowOffset(Integer.parseInt(rowOffset));
			pValue.setCellOffset(Integer.parseInt(cellOffset));
			pValue.setValue(value);
			paramValues.add(pValue);
		}
	}
	
	private void makeCellIndexes(){
		List<Object> list = xlsImporter.getRowData(this.cmnRownum-1);
		Integer index = 0;
		StringBuffer sb = new StringBuffer();
		
		for(int i=0,count=list.size(); i<count; i++){
			String val = (String)list.get(i);
			if(!StringHandler.isValidStr(val)) continue;
			val = val.replace("{", "").replace("}", "");
			sb.append(val+",");
			cellIndexMap.put(val, new Integer[]{cmnRownum,index});
			index++;
		}
		if(null != sb && sb.length() > 0){
			cmns = StringHandler.RemoveStr(sb, ",");
			cmnsArr = cmns.split(",");
		}
		//单行模式情况 ---> 重新查找相应DataTable 列所要填充的单元格索引
		if(MODE_SINGLE_VAL.equals(mode)){	
			 makeSingleMode();
		}
	}
	
	private  void makeSingleMode(){
		cellIndexMap.clear();
		for(int i=cmnCellfirst;i <= cmnCellend; i++){
			List<Object> list = xlsImporter.getRowData(i);
			int len = list.size();
			for(int j=0; j<len; j++){
				String val = (String)list.get(j);
				if(!StringHandler.isValidStr(val)) continue;
				val = isExistCmn(val);
				if(null == val) continue;
				cellIndexMap.put(val, new Integer[]{i,j});
			}
		}
	}
	/**
	 * 判断指定的单元格替换字段是否存在。
	 *  true : 存在 , false : 不存在
	 * @param eqVal	要比较的值
	 * @return	返回 boolean 值  true : 存在 , false : 不存在
	 */
	private String isExistCmn(String eqVal){
		for(String cmn : cmnsArr){
			String  _cmn = "{"+cmn+"}";
			if(_cmn.equals(eqVal)){
				return cmn;
			}
		}
		return null;
	}
	
	/**
	 * 为当前对象字段赋值
	 */
	private void setValuesToSelf(){
		JSONObject jsonObj = (JSONObject)JSONObject.parse(this.jsonCfg);
		String conntype = (String)jsonObj.get(CONNTYPE_KEY);
		String datasource = (String)jsonObj.get(DATASOURCE_KEY);
		String mode = (String)jsonObj.get(MODE_KEY);
		String cmnRowNum = (String)jsonObj.get(CMNROWNUM_KEY);
		String cmnCellFirst = (String)jsonObj.get(CMNCELLFIRST_KEY);
		String cmnCellEnd = (String)jsonObj.get(CMNCELLEND_KEY);
		String params = (String)jsonObj.get(PARAMS_KEY);
		String sysparams = (String)jsonObj.get(SYSPARAMS_KEY);
		this.conntype = conntype;
		this.datasource = datasource;
		this.mode = mode;
		this.cmnRownum = (StringHandler.isValidStr(cmnRowNum)) ? Integer.parseInt(cmnRowNum) : 0;
		this.cmnCellfirst = (StringHandler.isValidStr(cmnCellFirst)) ? Integer.parseInt(cmnCellFirst) : 0;
		this.cmnCellend = (StringHandler.isValidStr(cmnCellEnd)) ? Integer.parseInt(cmnCellEnd) : 0; 
		this.params = params;
		this.sysparams = sysparams;
		
		JSONObject gatherJsonObj = (JSONObject)jsonObj.get(GATHER_KEY);
		if(null != gatherJsonObj){
			this.gahter = createGatherInfo(gatherJsonObj);
		}
		
		JSONObject rendersJsonObj = (JSONObject)jsonObj.get(RENDERS_KEY);
		if(null != rendersJsonObj){
			this.renderMap = createRenderInfo(rendersJsonObj);
		}
		//模板参数对象
		JSONObject tempparamsJsonObj = (JSONObject)jsonObj.get(TEMPPARAMS_KEY);
		if(null != tempparamsJsonObj){
			this.paramsMap = createParamsMap(tempparamsJsonObj);
		}
		
	}
	
	/**
	 * 创建汇总信息对象
	 * @param gatherJson	汇总信息 JSON 字符串
	 * @return 返回汇总信息对象
	 */
	public GatherInfo createGatherInfo(JSONObject gatherJsonObj){
		GatherInfo gatherInfo = new GatherInfo();
		String title = gatherJsonObj.getString(TITLE_KEY);
		String titleIndex = gatherJsonObj.getString(TITLEINDEX_KEY);
		String sumcmns = gatherJsonObj.getString(SUMCMNS_KEY);
		if(StringHandler.isValidStr(title)) gatherInfo.setTitle(title);
		if(StringHandler.isValidStr(titleIndex)) gatherInfo.setTitleIndex(Integer.parseInt(titleIndex));
		if(StringHandler.isValidStr(sumcmns)){
			String[] sumcmnsArr = sumcmns.split(",");
			gatherInfo.setSumcmns(sumcmnsArr);
		} 
		return gatherInfo;
	}
	
	/**
	 * 创建Renderer对象MAP
	 * @param rendersJsonObj	汇总信息 JSON 字符串
	 * @return 返回Renderer对象MAP
	 */
	public Map<String, Renderer> createRenderInfo(JSONObject rendersJsonObj){
		Map<String, Renderer> renderMap = new HashMap<String, Renderer>();
		Set<String> keys = rendersJsonObj.keySet();
		for(String key : keys){
			String srcexpress = rendersJsonObj.getString(key);
			renderMap.put(key, new Renderer(srcexpress));
		}
		return renderMap;
	}
	
	/**
	 * 创建Excel替换参数Map配置对象
	 * @param paramsJsonObj	
	 * @return
	 */
	private Map<String, Object> createParamsMap(JSONObject paramsJsonObj){
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		Set<String> keySet = paramsJsonObj.keySet();
		for(String key : keySet){
			paramsMap.put(key, paramsJsonObj.get(key));
		}
		return paramsMap;
	}
	
	/**
	 * Excel中要替换的值
	 * @author mingwei_cheng
	 *
	 */
	public class ParamValue{
		private int rowOffset;
		private int cellOffset;
		private String value;
		
		public ParamValue() {
		}


		public ParamValue(int rowOffset, int cellOffset, String value) {
			this.rowOffset = rowOffset;
			this.cellOffset = cellOffset;
			this.value = value;
		}


		public int getRowOffset() {
			return rowOffset;
		}


		public void setRowOffset(int rowOffset) {
			this.rowOffset = rowOffset;
		}


		public int getCellOffset() {
			return cellOffset;
		}


		public void setCellOffset(int cellOffset) {
			this.cellOffset = cellOffset;
		}


		public String getValue() {
			return value;
		}


		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	/**
	 * 渲染类
	 * 功能：将指定的类型标识转换为另一种方式显示
	 * @author mingwei_cheng
	 *
	 */
	public class Renderer{
		//原始字符串表达式
		private String srcexpress;
		private Map<String, String> targetMap = new HashMap<String, String>();
		public Renderer(String srcexpress) {
			this.srcexpress = srcexpress;
			makeSrcExpress();
		}

		public void setSrcexpress(String srcexpress) {
			this.srcexpress = srcexpress;
			makeSrcExpress();
		}
		
		private void makeSrcExpress(){
			if(!StringHandler.isValidStr(srcexpress)) return;
			String[] k_valArrs = srcexpress.split(",");
			for(String k_v : k_valArrs){
				String[] kv = k_v.split("#");
				String key = kv[0];
				String val = kv[1];
				if(!StringHandler.isValidStr(key)) continue;
				key+="";
				targetMap.put(key, val);
			}
		}
		
		/**
		 * 根据原始类型返回一个真正显示在EXCEL中客户能看懂的字符串
		 * @param srcTag	原始用来比较的Tag 值
		 * @return
		 */
		public String getTargetVal(String srcTag){
			String tagVal = targetMap.get(srcTag);
			return tagVal;
		}
		
	}
	
	/**
	 * 汇总信息类
	 * @author mingwei_cheng
	 *
	 */
	public class GatherInfo{
		private String title = SUMTITLE_DEFAULT;	//合计列标题
		private Integer titleIndex = 0;	//合计列标题所在列索引
		private String[] sumcmns;//要统计的列
		
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public Integer getTitleIndex() {
			return titleIndex;
		}
		public void setTitleIndex(Integer titleIndex) {
			this.titleIndex = titleIndex;
		}
		public String[] getSumcmns() {
			return sumcmns;
		}
		public void setSumcmns(String[] sumcmns) {
			this.sumcmns = sumcmns;
		}
		
	}
	
	
	
	/**
	 * 数据源提供类型  KEY --> CONNTYPE_KEY ： conntype
	 */
	public static final String CONNTYPE_KEY = "conntype";
	/**
	 * 数据提供类型  取值 -->  CONNTYPE_DT_VAL ： dt
	 */
	public static final String CONNTYPE_DT_VAL = "dt";
	/**
	 * 数据提供类型  取值 -->  CONNTYPE_HQL_VAL ： hql
	 */
	public static final String CONNTYPE_HQL_VAL = "hql";
	/**
	 * 数据提供类型  取值 -->  CONNTYPE_SQL_VAL ： sql
	 */
	public static final String CONNTYPE_SQL_VAL = "sql";
	/**
	 * 数据源  KEY --> DATASOURCE_KEY ： datasource
	 */
	public static final String DATASOURCE_KEY = "datasource";
	/**
	 * 数据导出模式 KEY --> MODE_KEY : mode
	 */
	public static final String MODE_KEY = "mode";
	/**
	 * 数据导出模式 取值 --> MODE_SINGLE_VAL : single
	 */
	public static final String MODE_SINGLE_VAL = "single";
	/**
	 * 数据导出模式 取值 --> MODE_LIST_VAL : list
	 */
	public static final String MODE_LIST_VAL = "list";
	/**
	 * 列所在的行  KEY --> CMNROWNUM_KEY : cmnRowNum
	 */
	public static final String CMNROWNUM_KEY = "cmnRowNum";
	/**
	 * 汇总信息  KEY --> GATHER_KEY : gather
	 */
	public static final String GATHER_KEY = "gather";
	/**
	 * 渲染  KEY --> RENDERS_KEY : renders
	 */
	public static final String RENDERS_KEY = "renders";
	
	/**
	 * 合计标题  KEY --> TITLE_KEY : title
	 */
	public static final String TITLE_KEY = "title";
	/**
	 * 合计列标题所在列索引  KEY --> TITLEINDEX_KEY : titleIndex
	 */
	public static final String TITLEINDEX_KEY = "titleIndex";
	/**
	 * 要统计的列  KEY --> SUMCMNS_KEY : sumcmns
	 */
	public static final String SUMCMNS_KEY = "sumcmns";
	
	/**
	 * 第一个要替换的单元格所在的行索引 KEY --> CMNCELLFIRST_KEY : cmnCellFirst
	 */
	public static final String CMNCELLFIRST_KEY = "cmnCellFirst";
	/**
	 * 最后一个要替换的单元格所在的行索引  KEY --> CMNCELLEND_KEY : cmnCellEnd
	 */
	public static final String CMNCELLEND_KEY = "cmnCellEnd";
	/**
	 * 当导出模板时，为 datasource 中的对象提供的过滤参数   KEY --> PARAMS_KEY : params
	 */
	public static final String PARAMS_KEY = "params";
	/**
	 * 预先定义好的系统参数 KEY --> SYSPARAMS_KEY : sysparams
	 */
	public static final String SYSPARAMS_KEY = "sysparams";
	/**
	 * 预先定义好的Excel模板替换参数 KEY --> TEMPPARAMS_KEY : tempparams
	 */
	public static final String TEMPPARAMS_KEY = "tempparams";
	
	public static final String SUMTITLE_DEFAULT = "合计";
}
