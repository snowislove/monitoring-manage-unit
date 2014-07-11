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

import com.secpro.platform.monitoring.manage.util.MD5Builder;

public class CompleteCheckFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest servletrequest = (HttpServletRequest) request;
		java.util.Enumeration params = request.getParameterNames();
		boolean status = true;
		String nexturl = ((HttpServletRequest) request).getServletPath();
		long paramValueHash = 0;
		String param = "";
		if ("/saveUser.action".equals(nexturl)) {
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (!param.equals("user.enableAccount")
						&& !param.equals("user.userDesc")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						paramValueHash += values[i].hashCode();
					}
				}
			}
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/modifyUser.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (!param.equals("user.enableAccount")
						&& !param.equals("user.userDesc")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						paramValueHash += values[i].hashCode();
					}
				}
			}
			
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/saveOrg.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (!param.equals("org.orgDesc")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						paramValueHash += values[i].hashCode();
					}
				}
			}
			
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/modifyOrg.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (!param.equals("org.orgDesc")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						paramValueHash += values[i].hashCode();
					}
				}
			}
			
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/saveRole.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (!param.equals("role.roleDesc")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						paramValueHash += values[i].hashCode();
					}
				}
			}
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/modifyRole.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (!param.equals("role.roleDesc")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						paramValueHash += values[i].hashCode();
					}
				}
			}
			
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/modifyAccessRule.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (!param.equals("accessRule.isLimitTime")&&!param.equals("accessRule.isLimitIp")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						paramValueHash += values[i].hashCode();
					}
				}
			}
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/modifyPasswdRule.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (!param.equals("passwdRule.hasChar")&&!param.equals("passwdRule.hasNum")&&!param.equals("passwdRule.hasSpecialChar")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
					
						paramValueHash += values[i].hashCode();
					}
				}
			}
		
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/modifySelf.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (!param.equals("user.userDesc")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						paramValueHash += values[i].hashCode();
					}
				}
			}		
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/saveSysObj.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (param.equals("resObjForm.cityCode")||param.equals("resObjForm.resName")||param.equals("resObjForm.username")||param.equals("resObjForm.password")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						
						paramValueHash += values[i].hashCode();
					}
				}
			}
			
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}else if("/modifySysObj.action".equals(nexturl)){
			while (params.hasMoreElements()) {
				param = (String) params.nextElement();
				if (param.equals("resObjForm.cityCode")||param.equals("resObjForm.resName")||param.equals("resObjForm.username")||param.equals("resObjForm.password")) {
					String[] values = request.getParameterValues(param);
					for (int i = 0; i < values.length; i++) {
						
						paramValueHash += values[i].hashCode();
					}
				}
			}
			
			String md5Check = MD5Builder.getMD5("" + paramValueHash);
			
			String md5Last = (String) ((HttpServletRequest) request)
					.getSession().getAttribute("md5check");
			if (!md5Check.equals(md5Last)) {
				status = false;
			}
		}
			
		if (status) {
			chain.doFilter(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.print("<script language='javascript'>alert(\"数据发送服务器途中出现错误，请重新提交！\");"
					+ "window.history.go(-1);</script>");
			return;
		}

	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		System.out.println("123!a456".hashCode());
	}
}
