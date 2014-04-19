package com.cmw.core.kit.excel.exporter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cmw.constant.SysConstant;
import com.cmw.entity.sys.UserEntity;
/**
 * Excel报表管理Servlet
 * @author Administrator
 * 
 */
@SuppressWarnings("serial")
public class XlsReportServlet extends HttpServlet {
	private HttpServletRequest request = null;
	
	private HttpServletResponse response = null;
	
	/**
	 * Excel 模板方式报表对象
	 */
	private XlsExportHandler xlsReportHandler = null;
	
	/**
	 * Constructor of the object.
	 */
	public XlsReportServlet() {
		super();
	}

	/**
	 * 处理 GET 请求的方法
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String contractId = request.getParameter("contractId");
		System.out.println(contractId);
		doPost(request, response);
	}
	
    
	/**
	 * 处理 POST 方法
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//初始化 Servlet 参数
			initServletParams(request,response);
			exportXlsReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出 Excel 报表
	 */
	private void exportXlsReport(){
		if(null == xlsReportHandler) xlsReportHandler = new XlsExportHandler(request, response);
		String contractId = request.getParameter("contractId");
		System.out.println(contractId);
		xlsReportHandler.setResponse(response);
		xlsReportHandler.setRequest(request);
		xlsReportHandler.export();
	}
	
	/**
	 * 初始化 Servlet 相关参数
	 * @param request	request 请求对象
	 * @param response	 response 对象
	 */
	private void initServletParams(HttpServletRequest request,HttpServletResponse response){
		this.request = request;
		this.response = response;
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 	从 Session 中获取用户对象
	 * @return 返回从 Session 中获取的用户对象
	 */
	public UserEntity getUser() {
		HttpSession session = request.getSession();
		return (UserEntity)session.getAttribute(SysConstant.USER_KEY);
	}
}
