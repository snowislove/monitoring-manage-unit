package com.secpro.platform.monitoring.manage.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.secpro.platform.monitoring.manage.services.AuthenticationService;
import com.secpro.platform.monitoring.manage.services.DataBaseOperationService;
import com.secpro.platform.monitoring.manage.services.ModelService;
import com.secpro.platform.monitoring.manage.services.ResourceService;
import com.secpro.platform.monitoring.manage.util.HttpStatusCode;
import com.secpro.platform.monitoring.manage.util.service.ServiceHelper;

/**
 * @author baiyanwei
 * 
 *         测试接口是否可用
 */
@Entity
public class InServiceServlet extends ResIFSlet {
	private static final ArrayList<Class<?>> NEED_SERVICE_LIST = new ArrayList<Class<?>>();
	static {
		// CHECK SERVICE LIST
		NEED_SERVICE_LIST.add(AuthenticationService.class);
		NEED_SERVICE_LIST.add(ModelService.class);
		NEED_SERVICE_LIST.add(DataBaseOperationService.class);
		NEED_SERVICE_LIST.add(ResourceService.class);
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -4851300466408359956L;

	/**
	 * Constructor of the object.
	 */
	public InServiceServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		if (checkStartupService(NEED_SERVICE_LIST) == false) {
			writeMessage(resp, HttpStatusCode.INTERNAL_SERVER_ERROR, "Service is not ready");
		} else {
			writeMessage(resp, 200, "succeed");
		}
	}

	/**
	 * 检查服务是否已经启动
	 * 
	 * @param resp
	 * @param needClassList
	 * @return
	 */
	private boolean checkStartupService(ArrayList<Class<?>> needClassList) {
		for (int i = 0; i < needClassList.size(); i++) {
			if (ServiceHelper.findService(needClassList.get(i)) == null) {
				return false;
			}
		}
		return true;
	}
}
