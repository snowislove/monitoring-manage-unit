package com.secpro.platform.monitoring.manage.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import com.secpro.platform.monitoring.manage.util.log.PlatformLogger;

/**
 * @author baiyanwei
 * 
 *         HTTP 接口抽象类
 */
public abstract class ResIFSlet extends HttpServlet {
	private final static PlatformLogger _logger = PlatformLogger.getLogger(ResIFSlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 379118737551637763L;

	/**
	 * 向请求HTTP CLIENT写入返回内容
	 * 
	 * @param response
	 * @param httpStatusCode
	 * @param context
	 * @throws IOException
	 */
	public void writeMessage(HttpServletResponse response, int httpStatusCode, String context) throws IOException {
		PrintWriter out = null;
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			response.setStatus(httpStatusCode);
			out.print(context);
			out.flush();
		} catch (IOException e) {
			// 忽略HTTP RESPONSE影响，BROWSER可以关闭
		} finally {
			if (out != null) {
				out.close();
			}
			out = null;
		}
		_logger.debug(context);
	}
}
