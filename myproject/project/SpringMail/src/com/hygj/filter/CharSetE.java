package com.hygj.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CharSetE implements Filter {
	
	private FilterConfig filterConfig;
	private String trargetEncoding=null;

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	public String getTrargetEncoding() {
		return trargetEncoding;
	}

	public void setTrargetEncoding(String trargetEncoding) {
		this.trargetEncoding = trargetEncoding;
	}

	@Override
	public void destroy() {

		this.filterConfig=null;
		this.trargetEncoding=null;//清空资源
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String encodes=System.getProperty("file.encoding");//获得系统编码
		try {
			System.out.println("目标编码： "+trargetEncoding);//在部署描述符中的编码//可以在部署描述符中修改：
			request.setCharacterEncoding(encodes);//这里选择设置系统默认的编码
			chain.doFilter(request, response);
		} catch (Exception e) {
			filterConfig.getServletContext().log(e.getMessage());
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig=filterConfig;
		this.trargetEncoding=this.filterConfig.getInitParameter("encoding");
		
	}
	
	
}
