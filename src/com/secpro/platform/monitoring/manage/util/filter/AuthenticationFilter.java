package com.secpro.platform.monitoring.manage.util.filter;

import java.io.IOException;

import javax.persistence.Entity;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.secpro.platform.monitoring.manage.services.AuthenticationService;
import com.secpro.platform.monitoring.manage.util.HttpStatusCode;
import com.secpro.platform.monitoring.manage.util.service.ServiceHelper;

/**
 * @author baiyanwei 权限验证
 */
@Entity
public class AuthenticationFilter implements Filter {
	protected FilterConfig config;
	
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		AuthenticationService authenticationService = ServiceHelper.findService(AuthenticationService.class);
		if (authenticationService.hasAuthentication((HttpServletRequest) request) == false) {
			((HttpServletResponse) response).sendError(HttpStatusCode.FORBIDDEN, "访问受限");
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}
}
