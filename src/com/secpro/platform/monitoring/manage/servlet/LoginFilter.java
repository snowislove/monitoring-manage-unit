package com.secpro.platform.monitoring.manage.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.secpro.platform.monitoring.manage.entity.SysUserInfo;
import com.secpro.platform.monitoring.manage.util.AccessRuleUtil;

public class LoginFilter implements Filter{
	private FilterConfig config;
	private ServletContext context;
	public void init(FilterConfig config) throws ServletException {
		this.config=config;
		this.context=config.getServletContext();
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession s;
		s=((HttpServletRequest)request).getSession(false);
		
		String nexturl=((HttpServletRequest)request).getServletPath();
		if(s==null){
			if("/login.action".equals(nexturl)||"/checkOldPasswd.action".equals(nexturl)||"/checkNewPasswd.action".equals(nexturl)||"/resetPasswd.action".equals(nexturl)){
				
				chain.doFilter(request,response);
				
			}else if("/login.jsp".equals(nexturl)){
				chain.doFilter(request,response);
			}else if(nexturl.endsWith(".css")||nexturl.endsWith(".js")||nexturl.endsWith(".png")||nexturl.endsWith(".bmp")||nexturl.endsWith(".jpg")||nexturl.endsWith(".jpeg")){
				chain.doFilter(request,response);
				
			}else{
				
				request.getRequestDispatcher("/tologin.jsp").forward(request,response);
			}
			return ;
		}
		SysUserInfo user=(SysUserInfo)s.getAttribute("user");
		//add HTTPOlny属性 20140228
		HttpServletResponse res=(HttpServletResponse)response;
		res.addHeader("Set-Cookie",  "__wsidd=hhghgh;Path=/;Domain=wap.domain.cn;Max-Age=36000;HTTPOnly");
		if(nexturl.equals("/dwr")||nexturl.equals("/topology/topologyservlet")){
			chain.doFilter(request,response);
			return;
		}
		
		if(user!=null){
			long lastLoginTime=(Long)s.getAttribute("lastLoginTime");
			long currentTime=System.currentTimeMillis();
			
			if((currentTime-lastLoginTime)/1000/60>AccessRuleUtil.accessTimeOut){
				s.removeAttribute("user");
				s.removeAttribute("lastLoginTime");
				s.invalidate();
				request.getRequestDispatcher("/tologin.jsp").forward(request,response);
			}else{
				s.setAttribute("lastLoginTime", System.currentTimeMillis());
				chain.doFilter(request,response);
			}
		}else{
			
			if("/login.action".equals(nexturl)||"/checkOldPasswd.action".equals(nexturl)||"/checkNewPasswd.action".equals(nexturl)||"/resetPasswd.action".equals(nexturl)){
				
				chain.doFilter(request,response);
			}else if("/login.jsp".equals(nexturl)){
				chain.doFilter(request,response);
			}else if(nexturl.endsWith(".css")||nexturl.endsWith(".js")||nexturl.endsWith(".png")||nexturl.endsWith(".bmp")||nexturl.endsWith(".jpg")||nexturl.endsWith(".jpeg")){
				chain.doFilter(request,response);
				
			}else{
				
				request.getRequestDispatcher("/tologin.jsp").forward(request,response);
			}
		}
	}

	public void destroy() {
		this.config=null;
		this.context=null;
	}
}
