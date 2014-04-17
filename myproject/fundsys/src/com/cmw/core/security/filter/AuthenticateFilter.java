package com.cmw.core.security.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cmw.core.util.StringHandler;
import com.cmw.entity.sys.UserEntity;

/**
 * Servlet implementation class for Servlet: AuthenticateFilter
 *  用户权限过滤	
 */
 public class AuthenticateFilter implements Filter  {
   static final long serialVersionUID = 1L;
	public static String[] NOTLOGINTYPE = null;
	public static final String INDEX_JSP = "index.jsp"; 
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpSession session = request.getSession();
		String url = request.getRequestURI();
		UserEntity user  = (UserEntity)session.getAttribute("user");
		if(!exclusionLoginType(url) &&  null == user){
			String referer = request.getHeader("referer");
//			System.out.println("referer="+referer);
			String TXR_SWITCHINGSYSTEM = request.getParameter("TXR_SWITCHINGSYSTEM");
			if(!StringHandler.isValidStr(TXR_SWITCHINGSYSTEM) &&
				(StringHandler.isValidStr(referer) && referer.indexOf(INDEX_JSP) != -1)){
				outJsonString(response,"{success : false,msg:'user.session.timeout.break'}");
				return;
			}
			response.sendRedirect(request.getContextPath()+"/gotoLogin.action");
			return;
		}
		chain.doFilter(request, response);
	}
	
	public void outJsonString(HttpServletResponse response,String str) {
//		getResponse().setContentType("text/javascript;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");  
		response.setContentType("application/json");  
		System.out.println("outJsonString="+str);
		try {
			PrintWriter out =response.getWriter();
			out.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 不需要登录验证的页面
	 * @param url
	 * @return
	 */
	public boolean exclusionLoginType(String url){
		url = url.substring(url.lastIndexOf("/")+1);
		if(null == url || url.equals("")) return false;
		url=url.indexOf(".") != -1 ? url.substring(0,url.indexOf(".")) : url;
		if(null == NOTLOGINTYPE || NOTLOGINTYPE.length == 0) return true;
		for(String nurl : NOTLOGINTYPE){
			if(url.equals(nurl)) return true;
		}
		return false;
	}
	
	@Override   
	public void init(FilterConfig config) throws ServletException {
		String noValidUrls = config.getInitParameter("noValidUrls");
		if(StringHandler.isValidStr(noValidUrls)){
			NOTLOGINTYPE = noValidUrls.split(",");
		}
	}
	
	@Override
	public void destroy() {
		
	}
}