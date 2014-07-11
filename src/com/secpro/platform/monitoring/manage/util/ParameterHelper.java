package com.secpro.platform.monitoring.manage.util;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

@Entity
public class ParameterHelper {
	public final static String UTF_8 = "UTF-8";

	public static String getParameterWithUTF8(HttpServletRequest request, String parameterName) {
		try {
			return java.net.URLDecoder.decode(request.getParameter(parameterName), UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
