package com.cmw.core.kit.excel.exporter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cmw.constant.SysConstant;
import com.cmw.core.base.exception.ServiceException;
import com.cmw.core.util.BeanUtil;
import com.cmw.core.util.DataTable;
import com.cmw.core.util.DateUtil;
import com.cmw.core.util.SHashMap;
import com.cmw.core.util.SqlUtil;
import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.ReportTempEntity;
import com.cmw.entity.sys.UserEntity;
import com.cmw.service.inter.sys.ReportTempService;
/**
 * Excel 导出助手类
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class XlsExportHandler extends HibernateDaoSupport implements Serializable {
	private XlsTempCfgModel cfg;
	private String outfileName;
	private DataTable dt;
	private HttpSession session;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private WebApplicationContext ctx = null;
	// Excel 导出对象
	private XlsExporter exporter;
	//报表模板业务类
	private ReportTempService reportTempService;
	
	public XlsExportHandler() {
	
	}


	public XlsExportHandler(HttpServletRequest request,HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		ServletContext sc =this.session.getServletContext();
		this.ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
	}

	
	/**
	 * 导出数据
	 */
	public void export(){
		createXlsTempCfgModel();
		String mode = cfg.getMode();
		if(XlsTempCfgModel.MODE_LIST_VAL.equals(mode)){
			exporter = new POIListTempExporter();
		}else{
			exporter = new POISingleTempExporter();
		}
		loadData();
		writeOutfile();
	}

	/**
	 * 创建 模板配置模型对象
	 */
	private void createXlsTempCfgModel(){
		reportTempService = (ReportTempService)ctx.getBean("reportTempService");
		String token = request.getParameter("reportTemplat_token");
		String type = request.getParameter("reportTemplat_type");
		int intType = (!StringHandler.isValidStr(type)) ? ReportTempEntity.TYPE_1 : Integer.parseInt(type);
		SHashMap<String, Object> params = new SHashMap<String, Object>();
		params.put("token", SqlUtil.LOGIC_EQ + SqlUtil.LOGIC + token);
		params.put("type", intType);
		ReportTempEntity reptemplate = null;
		try {
			reptemplate = reportTempService.getEntity(params);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		
		if(null==reptemplate){
			try {
				response.getWriter().write("<script>alert('当前功能的没有上传Excel模板，无法导出数据!');</script>");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		String funname = reptemplate.getFunName();
		outfileName = StringHandler.isValidStr(funname) ? funname : System.currentTimeMillis()+"";
		String templatePath = reptemplate.getUrl();
		cfg = new XlsTempCfgModel();
		String serverPath= request.getSession().getServletContext().getRealPath(templatePath);
		cfg.readTemplate(serverPath);
	}
	
	/**
	 * 加载数据
	 */
	public void loadData(){
		String conntype = cfg.getConntype();
		String source = cfg.getDatasource();
		HashMap<String, Object> params=null;
		try {
			params = getParams();
			if(this.session != null){
				UserEntity user = (UserEntity) this.session.getAttribute(SysConstant.USER_KEY);
				params.put(SysConstant.USER_KEY, user);
			}else {
				try {
					response.getWriter().write("<script>alert('由于长时间未操作，<br/>当前用户已过期，请重新登录！');</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if(XlsTempCfgModel.CONNTYPE_DT_VAL.equals(conntype)){
			Object owner = ctx.getBean(source);
			try {
				Object returnObj = BeanUtil.invokeMethod(owner, "getDataSource", new Object[]{params});
				this.dt = (DataTable)returnObj;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Session session = getSession();
			Query query = null;
			source = makeHqlOrSql(source,params);
			if(XlsTempCfgModel.CONNTYPE_HQL_VAL.equals(conntype)){
				query = session.createQuery(source);
			}else{
				query = session.createSQLQuery(source);
			}
			@SuppressWarnings("unchecked")
			List<Object> list = query.list();
			String columnNames = cfg.getCmns();
			this.dt = new DataTable(list, columnNames);
		}
		cfg.bindParams(params);
	}

	/**
	 *  处理 HQL 或 SQL 语句中的 查询参数条件
	 * @param hqlOrsql	HQL 或 SQL 语句
	 * @param params	查询条件值
	 * @return	返回一个可用的SQL 或 HQL 语句
	 */
	private String makeHqlOrSql(String hqlOrsql,Map<String,Object> params){
		Set<String> keys = params.keySet();
		for(String key : keys){
			String val = (String)params.get(key);
			hqlOrsql = hqlOrsql.replace("{"+key+"}", val);
		}
		return hqlOrsql;
	}
	
	/**
	 * 获取要调用的参数
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private HashMap<String,Object> getParams() throws UnsupportedEncodingException{
		HashMap<String,Object> params = new HashMap<String, Object>();
		
		String paramsCmns = cfg.getParams();
		
		if(StringHandler.isValidStr(paramsCmns)){
			String[] args = paramsCmns.split(",");
			for(String arg : args){
				String tempVal=null;
				String val = request.getParameter(arg);
				if(!StringHandler.isValidStr(val)) val = "";
				try {
					val = URLDecoder.decode(val,"utf8");
					tempVal = StringHandler.GetUTF_8(val);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				params.put(arg, tempVal);
			}
		}
		
		Map<String,Object> systemParams = getSystemParams();
		String sysparamsCmns = cfg.getSysparams();
		if(!StringHandler.isValidStr(sysparamsCmns)) return params;
		String[] sysCmns = sysparamsCmns.split(",");
		for(String sysCmn : sysCmns){
			if(!systemParams.containsKey(sysCmn)) continue;
			params.put(sysCmn, systemParams.get(sysCmn));
		}
		return params;
	}
	
	/**
	 * 获取系统定义参数
	 * @return
	 */
	public Map<String,Object> getSystemParams(){
		Date today = new Date();
		String currdate = DateUtil.dateFormatToStr(today);
		String currmonth = DateUtil.dateFormatToStr(DateUtil.DATE_YYYYMM_FORMAT,today);
		Calendar cnd = Calendar.getInstance();
		cnd.setTime(today);
		int year =  cnd.get(Calendar.YEAR);
		int day = cnd.get(Calendar.DATE);
		Map<String,Object> sysParams = new HashMap<String, Object>();
		sysParams.put(CURRDATE_KEY, currdate);
		sysParams.put(CURRYEAR_KEY,year);
		sysParams.put(CURRMONTH_KEY, currmonth);
		sysParams.put(CURRDAY_KEY, day);
		HttpSession session = request.getSession();
		UserEntity user = (UserEntity)session.getAttribute(SysConstant.USER_KEY);
		sysParams.put(CURRUSER_KEY, user);
		return sysParams;
	}
	
	private void writeOutfile() {
		OutputStream os = null;
		try {
			if(null != response){
				String filename = makeOutfileName();
					response.setHeader("Content-Disposition", "attachment; filename=\""
							+ java.net.URLEncoder.encode(filename + ".xls", "utf-8") + "\"");
				response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
				response.setHeader("Pragma", "no-cache"); // HTTP 1.0
				response.setContentType("application/x-download");// 设置为下载application/x-download
				os =  response.getOutputStream();
			}else{
				os = new FileOutputStream(outfileName);
			}
			exporter.exporter(os, dt, cfg);
			os.flush();
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private String makeOutfileName(){
		String fileName = outfileName.replace("[\\\\]", "/");
		int offset = outfileName.lastIndexOf("/");
		if(-1 != offset){
			fileName = fileName.substring(offset);
		}
		offset = fileName.indexOf(".");
		if(offset != -1){
			fileName = fileName.substring(0,offset);
		}
		return fileName;
	}
	
	
	
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	
	public HttpServletRequest getRequest() {
		return request;
	}


	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}


	/**
	 * 当前日期  KEY --> CURRDATE_KEY : CURRDATE
	 */
	public static final String CURRDATE_KEY = "CURRDATE";
	/**
	 * 当前年份   KEY  --> CURRDATE_KEY : CURRDATE
	 */
	public static final String CURRYEAR_KEY = "CURRYEAR";
	/**
	 * 当前月份   KEY  -->  CURRMONTH_KEY : CURRMONTH [yyyy-mm ]
	 */
	public static final String CURRMONTH_KEY = "CURRMONTH";
	/**
	 * 当前是多少号  KEY  -->  CURRDAY_KEY :  CURRDAY
	 */
	public static final String CURRDAY_KEY = "CURRDAY";
	/**
	 * 当前用户   KEY  -->  CURRUSER_KEY : CURRUSER
	 */
	public static final String CURRUSER_KEY = "CURRUSER";
}
