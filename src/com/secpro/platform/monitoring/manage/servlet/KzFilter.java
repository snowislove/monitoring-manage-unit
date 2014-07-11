package com.secpro.platform.monitoring.manage.servlet;

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

import com.sun.corba.se.impl.legacy.connection.USLPort;

public class KzFilter implements Filter {
	private String[] characterParams = null;
	private boolean OK = true;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub

		HttpServletRequest servletrequest = (HttpServletRequest) request;
		String url = java.net.URLDecoder.decode(servletrequest.getRequestURI(),
				"ISO-8859-1");

		HttpServletResponse servletresponse = (HttpServletResponse) response;
		boolean status = false;
		java.util.Enumeration params = request.getParameterNames();
		String param = "";
		String paramValue = "";
		servletresponse.setContentType("text/html");
		servletresponse.setCharacterEncoding("utf-8");
		/*while (params.hasMoreElements()) {
			param = (String) params.nextElement();
			String[] values = request.getParameterValues(param);
			paramValue = "";
			if (OK) {// 过滤字符串为0个时 不对字符过滤
				for (int i = 0; i < values.length; i++) {
					paramValue = paramValue + values[i];
				}
				System.out.println(paramValue);
				System.out.println(url);
				if (!url.contains("saveEventRule.action")&&!url.contains("modifyEventRule.action")) {
					for (int i = 0; i < characterParams.length; i++) {
						if (paramValue.indexOf(characterParams[i]) >= 0) {
							System.out.println(characterParams[i]
									+ "================1==============");
							status = true;
							break;
						}
						if (status)
							break;
					}
				}
			}
		}*/

		for (int i = 0; i < characterParams.length; i++) {
			if (!url.toLowerCase().contains("/mmu/index.jsp;jsessionid")
					&& !characterParams[i].equals(";")) {
				if (url.toLowerCase().indexOf(characterParams[i]) > 0) {
					System.out.println(characterParams[i]
							+ "================2==============" + url);
					status = true;
					break;
				}
			}
		}

		if (status) {

			PrintWriter out = servletresponse.getWriter();
			out.print("<script language='javascript'>alert(\"对不起！您输入内容含有非法字符。如：<,>\\\"'\\\".等\");"
					// + servletrequest.getRequestURL()
					+ "window.history.go(-1);</script>");
			return;
		} else

			arg2.doFilter(request, response);

	}

	public void init(FilterConfig config) throws ServletException {
		// TODO Auto-generated method stub
		String character = "%,[,],{,},;,+,\",(,),<,>,1=1";
		if (character.length() < 1)
			OK = false;
		else
			this.characterParams = character.split(",");
	}

}
