package com.secpro.platform.monitoring.manage.services;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

import com.secpro.platform.monitoring.manage.util.service.IService;
import com.secpro.platform.monitoring.manage.util.service.ServiceInfo;


/**
 * @author baiyanwei
 * 鉴权服务
 */
@Entity
@ServiceInfo(description = "provide the authentication ability")
public class AuthenticationService implements IService {

	
	public void start() throws Exception {

	}

	
	public void stop() throws Exception {

	}

	public boolean hasAuthentication(HttpServletRequest request) {
//		Object userBean = request.getSession().getAttribute(ApplicationConfiguration.CURRENT_USER);
//		if (userBean == null) {
//			return false;
//		} else {
//			return true;
//		}
		return true;
	}

	public boolean setAuthentication(HttpServletRequest request) {
//		if (request == null || request == null) {
//			return false;
//		}
		// request.getSession().setAttribute(ApplicationConfiguration.CURRENT_USER,
		// userBean);
		// request.getSession().setAttribute(ApplicationConfiguration.CURRENT_ROLES, getRoleByUser(userBean));
		return true;
	}

	/**
	 * @param request
	 * @return
	 * 注销用户
	 */
	public boolean offAuthentication(HttpServletRequest request) {
		// if (request == null) {
		// return false;
		// }
		// request.getSession().removeAttribute(ApplicationConfiguration.CURRENT_USER);
		// request.getSession().removeAttribute(ApplicationConfiguration.CURRENT_ROLES);
		return true;
	}
}
